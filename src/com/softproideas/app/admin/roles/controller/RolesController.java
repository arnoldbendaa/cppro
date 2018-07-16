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
package com.softproideas.app.admin.roles.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.roles.model.RoleDetailsDTO;
import com.softproideas.app.admin.roles.service.RolesService;
import com.softproideas.app.core.roles.model.RoleCoreDTO;
import com.softproideas.commons.model.ResponseMessage;

@RequestMapping(value = "/roles")
@Controller
public class RolesController {

    @Autowired
    RolesService rolesService;

    /**
     * Get all Roles from database.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<RoleCoreDTO> browseRoles() throws Exception {
        return rolesService.browseRoles();
    }

    /**
     * Get all Role Details.
     */
    @ResponseBody
    @RequestMapping(value = "/{rolesId}", method = RequestMethod.GET)
    public RoleCoreDTO fetchRoleDetails(@PathVariable int rolesId) throws Exception {
        return rolesService.fetchRoleDetails(rolesId);
    }

    /**
     * Delete Role from database.
     */
    @ResponseBody
    @RequestMapping(value = "/{rolesId}", method = RequestMethod.DELETE)
    public ResponseMessage delete(@PathVariable int rolesId) throws Exception {
        ResponseMessage responseMessage = rolesService.delete(rolesId);
        return responseMessage;
    }

    /**
     * Save Role details.
     */
    @ResponseBody
    @RequestMapping(value = "/{rolesId}", method = RequestMethod.PUT)
    public ResponseMessage save(@PathVariable int rolesId, @RequestBody RoleDetailsDTO role) throws Exception {
        ResponseMessage responseMessage = rolesService.save(rolesId, role);
        return responseMessage;
    }

}
