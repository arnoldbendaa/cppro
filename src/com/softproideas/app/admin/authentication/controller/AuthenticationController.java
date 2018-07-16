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
package com.softproideas.app.admin.authentication.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.authentication.model.AdminUserDTO;
import com.softproideas.app.admin.authentication.model.AuthenticationDTO;
import com.softproideas.app.admin.authentication.model.AuthenticationDetailsDTO;
import com.softproideas.app.admin.authentication.model.AuthenticationTechniqueDTO;
import com.softproideas.app.admin.authentication.model.SecurityLogDTO;
import com.softproideas.app.admin.authentication.service.AuthenticationService;
import com.softproideas.app.admin.authentication.util.AuthenticationUtil;
import com.softproideas.commons.model.ResponseMessage;

@RequestMapping(value = "/authentications")
@Controller
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    /**
     * Get all Authentications from database.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<AuthenticationDTO> browseAuthentications() throws Exception {
        return authenticationService.browseAuthentications();
    }

    /**
     * Update Authentication
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMessage updateAuthentication(@RequestBody AuthenticationDetailsDTO authentication) throws Exception {
        ResponseMessage responseMessage = authenticationService.save(authentication);
        return responseMessage;
    }

    /**
     * Insert Authentication.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage createAuthentication(@RequestBody AuthenticationDetailsDTO authentication) throws Exception {
        ResponseMessage responseMessage = authenticationService.save(authentication);
        return responseMessage;
    }

    /**
     * Get one Authentication details.
     */
    @ResponseBody
    @RequestMapping(value = "/{authenticationId}", method = RequestMethod.GET)
    public AuthenticationDetailsDTO fetchAuthenticationDetails(@PathVariable int authenticationId) throws Exception {
        return authenticationService.fetchAuthenticationDetails(authenticationId);
    }

    /**
     * Delete Authentication
     */
    @ResponseBody
    @RequestMapping(value = "/{authenticationId}", method = RequestMethod.DELETE)
    public ResponseMessage deleteAuthentication(@PathVariable int authenticationId) throws Exception {
        ResponseMessage responseMessage = authenticationService.delete(authenticationId);
        return responseMessage;
    }

    @ResponseBody
    @RequestMapping(value = "/authenticationTechniques/number/{index}", method = RequestMethod.GET)
    public String getAuthenticationTechniqueName(@PathVariable int index) throws Exception {
        return AuthenticationUtil.getAuthenticationTechniqueName(index);
    }

    @ResponseBody
    @RequestMapping(value = "/authenticationTechniques/name/{name}", method = RequestMethod.GET)
    public int getAuthenticationTechniqueNumber(@PathVariable String name) throws Exception {
        return AuthenticationUtil.getAuthenticationTechniqueNumber(name);
    }

    @ResponseBody
    @RequestMapping(value = "/securityLogs/number/{index}", method = RequestMethod.GET)
    public String getSecurityLogName(@PathVariable int index) throws Exception {
        return AuthenticationUtil.getSecurityLogName(index);
    }

    @ResponseBody
    @RequestMapping(value = "/securityLogs/name/{name}", method = RequestMethod.GET)
    public int getSecurityLogNumber(@PathVariable String name) throws Exception {
        return AuthenticationUtil.getSecurityLogNumber(name);
    }

    /**
     * Set Authentication as active
     */
    @ResponseBody
    @RequestMapping(value = "/activate", method = RequestMethod.PUT)
    public ResponseMessage activateAuthentication(@RequestBody List<Integer> activation) throws Exception {
        ResponseMessage responseMessage = authenticationService.activate(activation);
        return responseMessage;
    }

    @ResponseBody
    @RequestMapping(value = "/adminUsers", method = RequestMethod.GET)
    public ArrayList<AdminUserDTO> getAdminUsers() throws Exception {
        return authenticationService.getAdminUsers();
    }

    @ResponseBody
    @RequestMapping(value = "/authenticationTechniques", method = RequestMethod.GET)
    public ArrayList<AuthenticationTechniqueDTO> getAuthenticationTechniques() throws Exception {
        return AuthenticationUtil.getAuthenticationTechniques();
    }

    @ResponseBody
    @RequestMapping(value = "/securityLogs", method = RequestMethod.GET)
    public ArrayList<SecurityLogDTO> getSecurityLogs() throws Exception {
        return AuthenticationUtil.getSecurityLogs();
    }

}
