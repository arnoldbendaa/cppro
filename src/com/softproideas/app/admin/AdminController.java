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
package com.softproideas.app.admin;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.softproideas.app.admin.userprivileges.service.UserPrivilegesService;
import com.softproideas.commons.context.CPContextHolder;

@Controller
public class AdminController {

    @Autowired
    CPContextHolder cpContextHolder;

    @Autowired
    private UserPrivilegesService userPrivilegesService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main() throws Exception {
        //int[] privileges = userPrivilegesService.fetchUserPrivileges();
        Set<String> userRoles = userPrivilegesService.fetchUserRoles();
        ModelAndView mainView = new ModelAndView("main");
        mainView.addObject("roles", userRoles);
        
        String userRolesString = "";
        for (String role: userRoles) {
            userRolesString = userRolesString + "/" + role + "/";
        }
        mainView.addObject("rolesString", userRolesString);
        
        mainView.addObject("userId", cpContextHolder.getUserId());
        mainView.addObject("roadMapAvailable", cpContextHolder.getRoadMapAvailable());
        return mainView;
    }

}