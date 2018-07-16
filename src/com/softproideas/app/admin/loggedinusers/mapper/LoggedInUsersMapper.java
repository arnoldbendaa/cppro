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
package com.softproideas.app.admin.loggedinusers.mapper;

import java.util.ArrayList;
import java.util.List;

import com.cedar.cp.api.base.EntityList;
import com.softproideas.app.admin.loggedinusers.model.LoggedInUserDTO;

/**
 * Maps list of all available logged in users to list , transfer data object related to logged in users.
 */
public class LoggedInUsersMapper {

    public static List<LoggedInUserDTO> mapToLoggedUsersDTO(EntityList entityList) {
        List<LoggedInUserDTO> list = new ArrayList<LoggedInUserDTO>();
        for (int i = 0; i < entityList.getNumRows(); i++) {
            LoggedInUserDTO loggedInUserDTO = new LoggedInUserDTO();
            loggedInUserDTO.setLoggedInUsersId((Integer) entityList.getValueAt(i, "IntUserId"));
            loggedInUserDTO.setName((String) entityList.getValueAt(i, "UserId"));
            loggedInUserDTO.setFullName((String) entityList.getValueAt(i, "UserName"));
            loggedInUserDTO.setClientIp((String) entityList.getValueAt(i, "ClientIP"));
            loggedInUserDTO.setClientHost((String) entityList.getValueAt(i, "ClientHost"));
            loggedInUserDTO.setCreationTime((String) entityList.getValueAt(i, "CreationTime"));
            loggedInUserDTO.setAdmin((Boolean) entityList.getValueAt(i, "isAdmin"));
            loggedInUserDTO.setLastAccessedTime((String) entityList.getValueAt(i, "LastAccessedTime"));
            loggedInUserDTO.setContextId((String) entityList.getValueAt(i, "ContextId"));

            list.add(loggedInUserDTO);
        }
        return list;

    }

}
