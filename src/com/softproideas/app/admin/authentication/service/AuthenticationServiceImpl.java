/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/
package com.softproideas.app.admin.authentication.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.authenticationpolicy.AllAuthenticationPolicysELO;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyEditorSessionSSO;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyImpl;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
import com.cedar.cp.dto.user.UsersWithSecurityStringELO;
import com.cedar.cp.ejb.api.authenticationpolicy.AuthenticationPolicyEditorSessionServer;
import com.softproideas.app.admin.authentication.mapper.AuthenticationMapper;
import com.softproideas.app.admin.authentication.model.AdminUserDTO;
import com.softproideas.app.admin.authentication.model.AuthenticationDTO;
import com.softproideas.app.admin.authentication.model.AuthenticationDetailsDTO;
import com.softproideas.app.admin.authentication.util.AuthenticationValidatorUtil;
import com.softproideas.app.admin.datatypes.service.DataTypesServiceImpl;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

@Service("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {
    private static Logger logger = LoggerFactory.getLogger(DataTypesServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    @Override
    public List<AuthenticationDTO> browseAuthentications() throws ServiceException {
        AllAuthenticationPolicysELO allAuthentications = cpContextHolder.getListSessionServer().getAllAuthenticationPolicys();
        List<AuthenticationDTO> authenticationsDTOList = AuthenticationMapper.mapAllAuthenticationsELO(allAuthentications);
        return authenticationsDTOList;
    }

    @Override
    public AuthenticationDetailsDTO fetchAuthenticationDetails(int authenticationId) throws ServiceException {
        AuthenticationPolicyEditorSessionServer server = cpContextHolder.getAuthenticationPolicyEditorSessionServer();
        AuthenticationPolicyImpl authentication = getAuthenticationFromServer(authenticationId, server);
        AuthenticationDetailsDTO authenticationDetailsDTO = AuthenticationMapper.mapAuthentication(authentication);
        return authenticationDetailsDTO;
    }

    /**
     * Get Authentication details (for update) or new Authentication details object (for insert)
     */
    private AuthenticationPolicyImpl getAuthenticationFromServer(int authenticationId, AuthenticationPolicyEditorSessionServer server) throws ServiceException {
        String errorMsg = "Error during browsing Authentication Policy with id =" + authenticationId + "!";
        try {
            AuthenticationPolicyPK authenticationPolicyPK = new AuthenticationPolicyPK((short) authenticationId);
            AuthenticationPolicyEditorSessionSSO authenticationPolicyEditorSessionSSO;
            if (authenticationId != -1) {
                // edit Authentication
                authenticationPolicyEditorSessionSSO = server.getItemData(authenticationPolicyPK);
            } else {
                // create Authentication
                authenticationPolicyEditorSessionSSO = server.getNewItemData();
            }
            AuthenticationPolicyImpl authentication = authenticationPolicyEditorSessionSSO.getEditorData();
            return authentication;
        } catch (CPException e) {
            logger.error(errorMsg);
            throw new ServiceException(errorMsg);
        } catch (ValidationException e) {
            logger.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
    }

    /**
     * Validate and map Authentication details (for update or insert)
     */
    @Override
    public ResponseMessage save(AuthenticationDetailsDTO authentication) throws ServiceException {
        ResponseMessage message = null;
        AuthenticationPolicyEditorSessionServer server = cpContextHolder.getAuthenticationPolicyEditorSessionServer();
        AuthenticationPolicyImpl authenticationImpl = getAuthenticationFromServer(authentication.getAuthenticationId(), server);
        ValidationError error = AuthenticationValidatorUtil.validateAuthenticationDetails(authentication);
        String method;
        if (authentication.getAuthenticationId() != -1) {
            // edit Authentication
            method = "edit";
        } else {
            // create Authentication
            method = "create";
        }
        if (error.getFieldErrors().isEmpty()) {
            authenticationImpl = AuthenticationMapper.mapAuthenticationDetailsDTOToAuthenticationDetailsImpl(authenticationImpl, authentication);
            message = save(authenticationImpl, method, server);
        } else {
            error.setMessage("Error during " + method + " Authentication.");
            message = error;
        }
        return message;
    }

    /**
     * Save Authentication details: update or insert
     */
    private ResponseMessage save(AuthenticationPolicyImpl authenticationImpl, String operation, AuthenticationPolicyEditorSessionServer server) throws ServiceException {
        String errorMsg = "Error during " + operation + " Authentication.";
        try {
            if (operation.equals("edit")) {
                server.update(authenticationImpl);
            } else if (operation.equals("create")) {
                server.insert(authenticationImpl);
            }
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Validation " + errorMsg);
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
    }

    /**
     * Activate Authentication
     */
    @Override
    public ResponseMessage activate(List<Integer> activation) throws ServiceException {
        Integer oldActiveAuthenticationId = activation.get(0);
        Integer newActiveAuthenticationId = activation.get(1);
        if (oldActiveAuthenticationId == newActiveAuthenticationId) {
            ValidationError error = new ValidationError();
            error.setMessage("Authentication is active.");
            return error;
        } else {
            AuthenticationPolicyEditorSessionServer server = cpContextHolder.getAuthenticationPolicyEditorSessionServer();
            ResponseMessage messageOld = null;
            // try to remove previous active element (if is set)
            if (oldActiveAuthenticationId != null) {
                AuthenticationPolicyImpl oldActiveAuthenticationImpl = getAuthenticationFromServer(oldActiveAuthenticationId, server);
                oldActiveAuthenticationImpl.setActive(false);
                messageOld = save(oldActiveAuthenticationImpl, "edit", server);
            }
            if (messageOld != null && messageOld.isError()) {
                return messageOld;
            } else {
                AuthenticationPolicyImpl newActiveAuthenticationImpl = getAuthenticationFromServer(newActiveAuthenticationId, server);
                newActiveAuthenticationImpl.setActive(true);
                ResponseMessage messageNew = save(newActiveAuthenticationImpl, "edit", server);
                return messageNew;
            }
        }
    }

    /**
     * Delete Authentication
     */
    @Override
    public ResponseMessage delete(int authenticationId) throws ServiceException {
        String errorMsg = "Error during delete Authentication.";
        try {
            AuthenticationPolicyEditorSessionServer server = cpContextHolder.getAuthenticationPolicyEditorSessionServer();
            AuthenticationPolicyPK authenticationPK = new AuthenticationPolicyPK((short) authenticationId);
            server.delete(authenticationPK);
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Validation " + errorMsg);
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
    }

    @Override
    public ArrayList<AdminUserDTO> getAdminUsers() throws ServiceException {
        UsersWithSecurityStringELO usersWithSecurityStringELO = cpContextHolder.getListSessionServer().getUsersWithSecurityString("AUTHENTICATION_POLICY_PROCESS.Save");
        return AuthenticationMapper.mapUsersWithSecurityStringELO(usersWithSecurityStringELO);
    }

}
