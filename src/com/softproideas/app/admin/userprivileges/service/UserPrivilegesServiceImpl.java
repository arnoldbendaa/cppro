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
/**
 * 
 */
package com.softproideas.app.admin.userprivileges.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.UserContext;
import com.softproideas.commons.context.CPContextHolder;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Service("userPrivilegesService")
public class UserPrivilegesServiceImpl implements UserPrivilegesService {

    @Autowired
    CPContextHolder cpContextHolder;

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.userprivileges.service.UserPrivilegesService#fetchUserPrivilages() */
    @Override
    public int[] fetchUserPrivileges() {
        int[] tabPrivilages = cpContextHolder.getUserContext().getAllowedBusinessProcesses();
        int[] tabPrivilagesDTO = new int[110];

        for (int i = 0; i < UserContext.MAX_PROCESS_ID; i++) {
            if (tabPrivilages[i] == -1) {
                break;
            }
            tabPrivilagesDTO[tabPrivilages[i]] = 1;
        }
        return tabPrivilagesDTO;
    }
    
    @Override
    public Set<String> fetchUserRoles() {
        Set<String> userRoles = cpContextHolder.getUserContext().getUserRoles();
        return userRoles;
    }

}
