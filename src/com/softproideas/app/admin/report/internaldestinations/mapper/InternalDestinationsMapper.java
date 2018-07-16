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
package com.softproideas.app.admin.report.internaldestinations.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.report.destination.internal.InternalDestinationRef;
import com.cedar.cp.dto.report.destination.internal.AllInternalDestinationsELO;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationImpl;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationPK;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.softproideas.app.admin.report.internaldestinations.model.InternalDestinationDTO;
import com.softproideas.app.admin.report.internaldestinations.model.InternalDestinationDetailsDTO;
import com.softproideas.app.admin.report.internaldestinations.model.ReportMessageTypeDTO;
import com.softproideas.app.admin.report.internaldestinations.util.InternalDestinationUtil;
import com.softproideas.app.core.users.mapper.UserCoreMapper;
import com.softproideas.app.core.users.model.UserCoreDTO;

public class InternalDestinationsMapper {

    @SuppressWarnings("unchecked")
    public static List<InternalDestinationDTO> mapAllInternalDestinationsELO(AllInternalDestinationsELO list) {
        List<InternalDestinationDTO> internalDestinationsDTOList = new ArrayList<InternalDestinationDTO>();
        for (Iterator<AllInternalDestinationsELO> it = list.iterator(); it.hasNext();) {
            AllInternalDestinationsELO row = it.next();
            InternalDestinationDTO internalDestinationDTO = new InternalDestinationDTO();

            InternalDestinationRef ref = row.getInternalDestinationEntityRef();
            internalDestinationDTO.setReportId(((InternalDestinationPK) ref.getPrimaryKey()).getInternalDestinationId());
            internalDestinationDTO.setReportVisId(ref.getNarrative());
            internalDestinationDTO.setReportDescription(row.getDescription());

            internalDestinationsDTOList.add(internalDestinationDTO);
        }

        return internalDestinationsDTOList;
    }

    @SuppressWarnings("unchecked")
    public static InternalDestinationDetailsDTO mapInternalDestination(InternalDestinationImpl internalDestination, AllUsersELO allUsers) {
        InternalDestinationDetailsDTO internalDestinationDetailsDTO = new InternalDestinationDetailsDTO();

        internalDestinationDetailsDTO.setReportId(((InternalDestinationPK) internalDestination.getPrimaryKey()).getInternalDestinationId());
        internalDestinationDetailsDTO.setReportVisId(internalDestination.getVisId());
        internalDestinationDetailsDTO.setReportDescription(internalDestination.getDescription());
        internalDestinationDetailsDTO.setVersionNum(internalDestination.getVersionNum());

        // messageType field
        Integer messageTypeNumber = internalDestination.getMessageType();
        String messageTypeName = InternalDestinationUtil.getMessageTypeName(messageTypeNumber);
        ReportMessageTypeDTO messageTypeDTO = new ReportMessageTypeDTO(messageTypeNumber, messageTypeName);
        internalDestinationDetailsDTO.setMessageType(messageTypeDTO);

        // users and availableUsers
        HashMap<String, List<UserCoreDTO>> preparedUsers = UserCoreMapper.prepareCurrentAndAvailableUsers(internalDestination.getUserList(), allUsers);
        internalDestinationDetailsDTO.setUsers(preparedUsers.get("usersDTOList"));//internalDestinationDetailsDTO.setUsers(usersDTOList);
        internalDestinationDetailsDTO.setAvailableUsers(preparedUsers.get("availableUsersDTOList"));//internalDestinationDetailsDTO.setAvailableUsers(availableUsersDTOList);;

        return internalDestinationDetailsDTO;
    }

    public static InternalDestinationImpl mapInternalDestinationDetailsDTOToInternalDestinationDetailsImpl(InternalDestinationImpl impl, InternalDestinationDetailsDTO report) {
        // override some fields values
        impl.setVisId(report.getReportVisId());
        impl.setDescription(report.getReportDescription());
        impl.setMessageType(report.getMessageType().getIndex());
        impl.setVersionNum(report.getVersionNum());
        // users
        List<UserRefImpl> userList = new ArrayList<UserRefImpl>();
        for (Iterator<UserCoreDTO> it = report.getUsers().iterator(); it.hasNext();) {
            UserCoreDTO row = it.next();
            UserPK userPK = new UserPK(row.getUserId());
            UserRefImpl u = new UserRefImpl(userPK, row.getUserName());
            userList.add(u);
        }
        impl.setUserList(userList);
        return impl;
    }

}
