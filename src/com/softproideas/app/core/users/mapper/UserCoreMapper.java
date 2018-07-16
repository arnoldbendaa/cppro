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
package com.softproideas.app.core.users.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.softproideas.app.core.users.model.UserCoreDTO;

public class UserCoreMapper {

    /**
     * Get set of users and all internal system users. Return two DTOs: one with current users, other one with rest of users.
     */
    @SuppressWarnings("unchecked")
    public static HashMap<String, List<UserCoreDTO>> prepareCurrentAndAvailableUsers(List<UserRefImpl> users, AllUsersELO allUsers) {
        HashMap<String, List<UserCoreDTO>> objectToReturn = new HashMap<String, List<UserCoreDTO>>();
        // prepare array with used users IDs
        List<Integer> usersInUseIds = new ArrayList<Integer>();
        for (int i = 0; i < users.size(); i++) {
            UserRefImpl userRefImpl = users.get(i);
            usersInUseIds.add(userRefImpl.getUserPK().getUserId());
        }
        // create empty list: for users and for availableUsers
        List<UserCoreDTO> usersDTOList = new ArrayList<UserCoreDTO>();
        List<UserCoreDTO> availableUsersDTOList = new ArrayList<UserCoreDTO>();
        for (Iterator<AllUsersELO> it = allUsers.iterator(); it.hasNext();) {
            AllUsersELO row = it.next();
            UserRef ref = row.getUserEntityRef();
            int userId = ((UserPK) ref.getPrimaryKey()).getUserId();
            // create userDTO object
            UserCoreDTO userDTO = new UserCoreDTO();
            userDTO.setUserId(((UserPK) ref.getPrimaryKey()).getUserId());
            userDTO.setUserName(ref.getNarrative());
            userDTO.setUserFullName(row.getFullName());
            userDTO.setUserDisabled(row.getUserDisabled());
            // add userDTO to users in use list or to available users list
            if (usersInUseIds.contains(userId)) {
                usersDTOList.add(userDTO);
            } else {
                availableUsersDTOList.add(userDTO);
            }
        }
        objectToReturn.put("usersDTOList", usersDTOList);
        objectToReturn.put("availableUsersDTOList", availableUsersDTOList);
        return objectToReturn;
    }

    @SuppressWarnings("unchecked")
    public static HashMap<String, List<UserCoreDTO>> prepareTemplateUsers(List<Integer> users, AllUsersELO allUsers) {

        HashMap<String, List<UserCoreDTO>> objectToReturn = new HashMap<String, List<UserCoreDTO>>();
        // create empty list: for users and for availableUsers
        List<UserCoreDTO> usersDTOList = new ArrayList<UserCoreDTO>();
        List<UserCoreDTO> availableUsersDTOList = new ArrayList<UserCoreDTO>();
        if (allUsers != null) {
            for (Iterator<AllUsersELO> it = allUsers.iterator(); it.hasNext();) {
                AllUsersELO row = it.next();
                UserRef ref = row.getUserEntityRef();
                int userId = ((UserPK) ref.getPrimaryKey()).getUserId();
                // create userDTO object
                UserCoreDTO userDTO = new UserCoreDTO();
                userDTO.setUserId(((UserPK) ref.getPrimaryKey()).getUserId());
                userDTO.setUserName(ref.getNarrative());
                userDTO.setUserFullName(row.getFullName());
                userDTO.setUserDisabled(row.getUserDisabled());
                // add userDTO to users in use list or to available users list
                if (users.contains(userId)) {
                    usersDTOList.add(userDTO);
                } else {
                    availableUsersDTOList.add(userDTO);
                }
            }
        }
        objectToReturn.put("usersDTOList", usersDTOList);
        objectToReturn.put("availableUsersDTOList", availableUsersDTOList);
        return objectToReturn;
    }

    public static List<UserRefImpl> mapUserCoreDTO(List<UserCoreDTO> usersDTO) {
        List<UserRefImpl> users = new ArrayList<UserRefImpl>();
        for (Iterator<UserCoreDTO> it = usersDTO.iterator(); it.hasNext();) {
            UserCoreDTO userDTO = it.next();
            UserPK userPK = new UserPK(userDTO.getUserId());
            UserRefImpl userRef = new UserRefImpl(userPK, userDTO.getUserName());
            users.add(userRef);
        }
        return users;
    }

}
