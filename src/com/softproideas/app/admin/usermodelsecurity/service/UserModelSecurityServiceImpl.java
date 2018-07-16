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
package com.softproideas.app.admin.usermodelsecurity.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityEditorSessionSSO;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityImpl;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.api.budgetlocation.UserModelSecurityEditorSessionServer;
import com.softproideas.app.admin.modelusersecurity.model.UserModelElementAssignmentDTO;
import com.softproideas.app.admin.modelusersecurity.services.ModelUserSecurityService;
import com.softproideas.app.admin.usermodelsecurity.mapper.UserModelSecurityMapper;
import com.softproideas.app.admin.usermodelsecurity.model.UserModelSecurityDTO;
import com.softproideas.app.admin.usermodelsecurity.model.UserModelSecurityDetailsDTO;
import com.softproideas.app.admin.usermodelsecurity.model.UserModelSecuritySaveData;
import com.softproideas.app.core.profile.service.ProfileService;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;
import com.softproideas.commons.model.tree.NodeStaticWithIdAndDescriptionDTO;

@Service("userModelSecurityService")
public class UserModelSecurityServiceImpl implements UserModelSecurityService {

    private static Logger logger = LoggerFactory.getLogger(UserModelSecurityServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    @Autowired
    ModelUserSecurityService modelUserSecurityService;

    @Autowired
    ProfileService profileService;
    /**
     * Get responsibility list of all Users.
     */
    @Override
    public List<UserModelSecurityDTO> browseUsersModelSecurity() {
        List<UserModelSecurityDTO> listModelUserSecurity = new ArrayList<UserModelSecurityDTO>();
        EntityList list = cpContextHolder.getListHelper().getUserModelSecurity();
        listModelUserSecurity = UserModelSecurityMapper.mapUserModelSecurity(list);
        return listModelUserSecurity;
    }

    /**
     * Get responsibility details for selected User from getItemData(int userId).
     */
    @Override
    public UserModelSecurityDetailsDTO fetchUserModelSecurity(int userId) throws ServiceException {
        UserModelSecurityDetailsDTO modelUserSecurityDetailsDTO;
        UserModelSecurityEditorSessionServer server = cpContextHolder.getUserModelSecurityEditorSessionServer();
        UserModelSecurityImpl userModelSecurityImpl = getItemData(userId, server);
        modelUserSecurityDetailsDTO = UserModelSecurityMapper.mapUserModelSecurityImpl(userModelSecurityImpl);
        return modelUserSecurityDetailsDTO;
    }

    /**
     * Get responsibility details for selected User from database.
     */
    private UserModelSecurityImpl getItemData(int userId, UserModelSecurityEditorSessionServer server) throws ServiceException {
        try {
            UserPK userPK = new UserPK(userId);
            UserModelSecurityEditorSessionSSO userModelSecurityEditorSessionSSO;
            userModelSecurityEditorSessionSSO = server.getItemData(userPK);
            UserModelSecurityImpl userModelSecurityImpl = userModelSecurityEditorSessionSSO.getEditorData();
            return userModelSecurityImpl;
        } catch (CPException e) {
            logger.error("Error during get Model!", e);
            throw new ServiceException("Error during get Model Security!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during get Model Security!", e);
            throw new ServiceException("Validation error during get Model Security!", e);
        }
    }

    /**
     * Get tree for selected Model.
     */
    @Override
    public NodeStaticWithIdAndDescriptionDTO fetchTree(int modelId) throws ServiceException {
        return modelUserSecurityService.getTree(modelId);
    }

    /**
     * Save responsibility details for selected User at update(UserModelSecurityImpl userModelSecurityImpl, UserModelSecurityEditorSessionServer server).
     * @throws SQLException 
     */
    @Override
    public ResponseMessage saveUserDetails(UserModelSecuritySaveData userModelSecuritySaveData) throws ServiceException, SQLException {
        UserModelSecurityEditorSessionServer server = cpContextHolder.getUserModelSecurityEditorSessionServer();
        UserModelSecurityImpl userModelSecurityImpl = getItemData(userModelSecuritySaveData.getUserId(), server);

        List<UserModelElementAssignment> startListOfModels = userModelSecurityImpl.getUserModelElementAccess();
        userModelSecurityImpl = UserModelSecurityMapper.mapUserModelSecuritySaveData(userModelSecuritySaveData, userModelSecurityImpl);
        List<UserModelElementAssignment> endListOfModels = userModelSecurityImpl.getUserModelElementAccess();
        List<Integer> modelIds = getModelIdsToDeleteProfiles(startListOfModels, endListOfModels);
        String[] parts = userModelSecurityImpl.getPrimaryKey().toString().split("=");
        int userId = Integer.valueOf(parts[1]);
        if (modelIds.size()>0){            
            List<Integer> userIds = new ArrayList<Integer>();
            userIds.add(userId);
            profileService.deleteProfiles(modelIds, userIds); //uncomment to enable automatic delete profiles
        }
        List<Integer> addedModels = getModelIdsToDeleteProfiles(endListOfModels, startListOfModels);
        for (Integer addedModel: addedModels) {
            profileService.createProfiles(addedModel, userId);
        }

        return update(userModelSecurityImpl, server);
    }

    private List<Integer> getModelIdsToDeleteProfiles(List<UserModelElementAssignment> startListOfModels, List<UserModelElementAssignment> endListOfModels) {
        List<Integer> list = new ArrayList<Integer>();
        List<String> modelDetails = new ArrayList<String>();
        for (int i=0;i<startListOfModels.size();i++){
            if (endListOfModels.size() == 0){
                for (int k=0;k<startListOfModels.size();k++){
                    modelDetails.add(startListOfModels.get(k).getModel().getPrimaryKey().toString());
                }
            }
            for (int j=0;j<endListOfModels.size();j++){
                if (startListOfModels.get(i).getModel().getPrimaryKey().toString().equals(endListOfModels.get(j).getModel().getPrimaryKey().toString())){
                    break;
                }
                if (j == endListOfModels.size()-1){
                    modelDetails.add(startListOfModels.get(i).getModel().getPrimaryKey().toString());
                }
            }
        }
        for(int i=0;i<modelDetails.size();i++){
            String[] parts = modelDetails.get(i).split("=");
            list.add(Integer.valueOf(parts[1]));
        }
        return list;
    }
    
    /**
     * Save responsibility details Model's at database.
     */
    private ResponseMessage update(UserModelSecurityImpl userModelSecurityImpl, UserModelSecurityEditorSessionServer server) throws ServiceException {
        try {
            server.update(userModelSecurityImpl);
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (CPException e) {
            logger.error("Error during User Security edit operation!", e);
            throw new ServiceException("Error during Model Security edit operation!", e);
        } catch (ValidationException e) {
            logger.error("Error during User Security edit operation!");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        }
    }


}
