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
package com.softproideas.app.admin.usermodelsecurity.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.usermodelsecurity.model.UserModelSecurityDTO;
import com.softproideas.app.admin.usermodelsecurity.model.UserModelSecurityDetailsDTO;
import com.softproideas.app.admin.usermodelsecurity.model.UserModelSecuritySaveData;
import com.softproideas.app.admin.usermodelsecurity.service.UserModelSecurityService;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.tree.NodeStaticWithIdAndDescriptionDTO;

@RequestMapping(value = "/userModel")
@Controller
public class UserModelSecurityController {

    @Autowired
    UserModelSecurityService userModelSecurityService;

    /**
     * Get responsibility list of all Users.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<UserModelSecurityDTO> browseModels() throws Exception {
        return userModelSecurityService.browseUsersModelSecurity();
    }

    /**
     * Update responsibility details for selected User from database.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMessage updateUsersDetails(@Valid @RequestBody UserModelSecuritySaveData userModelSecuritySaveData) throws Exception {
        ResponseMessage responseMessage = userModelSecurityService.saveUserDetails(userModelSecuritySaveData);
        return responseMessage;
    }

    /**
     * Get responsibility details for selected User.
     */
    @ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public UserModelSecurityDetailsDTO fetchUser(@PathVariable int userId) throws Exception {
        return userModelSecurityService.fetchUserModelSecurity(userId);
    }

    /**
     * Get tree for selected Model.
     */
    @ResponseBody
    @RequestMapping(value = "/tree/{modelId}", method = RequestMethod.GET)
    public NodeStaticWithIdAndDescriptionDTO fetchTree(@PathVariable int modelId) throws Exception {
        return userModelSecurityService.fetchTree(modelId);
    }

}
