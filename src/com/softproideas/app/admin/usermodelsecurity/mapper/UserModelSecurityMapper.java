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
package com.softproideas.app.admin.usermodelsecurity.mapper;

import java.util.ArrayList;
import java.util.List;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityImpl;
import com.cedar.cp.dto.user.UserRefImpl;
import com.softproideas.app.admin.modelusersecurity.mapper.ModelUserSecurityMapper;
import com.softproideas.app.admin.usermodelsecurity.model.UserModelSecurityDTO;
import com.softproideas.app.admin.usermodelsecurity.model.UserModelSecurityDetailsDTO;
import com.softproideas.app.admin.usermodelsecurity.model.UserModelSecuritySaveData;

public class UserModelSecurityMapper {

    public static List<UserModelSecurityDTO> mapUserModelSecurity(EntityList list) {
        List<UserModelSecurityDTO> listDTO = new ArrayList<UserModelSecurityDTO>();

        for (int i = 0; i < list.getNumRows(); i++) {
            UserModelSecurityDTO userModelSecurityDTO = new UserModelSecurityDTO();
            EntityList row = list.getRowData(i);

            userModelSecurityDTO.setUserId(((UserRefImpl) row.getValueAt(0, "User")).getUserPK().getUserId());
            userModelSecurityDTO.setUserFullName((String) row.getValueAt(0, "UserDisplay"));
            userModelSecurityDTO.setNumModels((Integer) row.getValueAt(0, "NumModels"));
            userModelSecurityDTO.setModelSummary((String) row.getValueAt(0, "ModelSummary"));

            listDTO.add(userModelSecurityDTO);
        }
        return listDTO;
    }

    public static UserModelSecurityDetailsDTO mapUserModelSecurityImpl(UserModelSecurityImpl userModelSecurityImpl) {
        UserModelSecurityDetailsDTO userModelSecurityDetailsDTO = new UserModelSecurityDetailsDTO();
        userModelSecurityDetailsDTO.setUserName(userModelSecurityImpl.getUserRef().getNarrative());
        userModelSecurityDetailsDTO.setModelUserElementAccess(ModelUserSecurityMapper.mapModelUserElementAccess((ArrayList<UserModelElementAssignment>) userModelSecurityImpl.getUserModelElementAccess()));
        return userModelSecurityDetailsDTO;
    }

    public static UserModelSecurityImpl mapUserModelSecuritySaveData(UserModelSecuritySaveData userModelSecuritySaveData, UserModelSecurityImpl userModelSecurityImpl) {
        userModelSecurityImpl.setUserModelElementAccess(ModelUserSecurityMapper.mapUserModelElementAssignmentDTO(userModelSecuritySaveData.getUserModelElementAccess()));
        return userModelSecurityImpl;
    }

}
