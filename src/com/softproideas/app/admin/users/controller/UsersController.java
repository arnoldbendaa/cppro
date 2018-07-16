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
package com.softproideas.app.admin.users.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.users.model.UserDetailsDTO;
import com.softproideas.app.admin.users.service.UsersService;
import com.softproideas.app.core.users.model.UserCoreDTO;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.tree.NodeLazyDTO;

@RequestMapping(value = "/users")
@Controller
public class UsersController {

    @Autowired
    UsersService usersService;

    /**
     * Get all Users from database
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<UserCoreDTO> browseUsers() throws Exception {
        return usersService.browseUsers();
    }

    /**
     * Save user details
     */
    @ResponseBody
    @RequestMapping(value = "/{usersId}", method = RequestMethod.PUT)
    public ResponseMessage save(@PathVariable int usersId, @RequestBody UserDetailsDTO user) throws Exception {
        ResponseMessage responseMessage = usersService.save(usersId, user);
        return responseMessage;
    }

    /**
     * Get all user details from database
     */
    @ResponseBody
    @RequestMapping(value = "/{usersId}", method = RequestMethod.GET)
    public UserDetailsDTO fetchUsersDetails(@PathVariable int usersId) throws Exception {
        return usersService.fetchUsersDetails(usersId);
    }

    /**
     * Delete user from database
     */
    @ResponseBody
    @RequestMapping(value = "/{usersId}", method = RequestMethod.DELETE)
    public ResponseMessage delete(@PathVariable int usersId) throws Exception {
        ResponseMessage responseMessage = usersService.delete(usersId);
        return responseMessage;
    }

    /**
     * Get TreeNode for security tab
     */
    @ResponseBody
    @RequestMapping(value = "/{userId}/security", method = RequestMethod.GET)
    public List<NodeLazyDTO> browseUserSecurityNode(@PathVariable int userId, @RequestParam(value = "id", required = true) String id) throws Exception {
        if (id.equals("1")) {// when we ask for root
            return usersService.browseSecurityModelRoot();

        } else if (id.startsWith("modelId")) {
            String[] modelParams = id.split("/");
            Integer modelId = Integer.parseInt(modelParams[1]);
            return usersService.browseSecurityModels(userId, modelId);
        } else if (id.startsWith("structureId")) {
            String[] structureParams = id.split("/");

            Integer structureId = Integer.parseInt(structureParams[1]);

            Integer structureElementId = Integer.parseInt(structureParams[3]);
            return usersService.browseSecurityStructureElement(userId, structureId, structureElementId);
        }
        return browseUserSecurityNode(userId, id);

    }

}
