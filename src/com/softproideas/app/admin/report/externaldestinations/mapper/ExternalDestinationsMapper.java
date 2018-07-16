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
package com.softproideas.app.admin.report.externaldestinations.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.report.destination.external.ExternalDestinationRef;
import com.cedar.cp.dto.report.destination.external.AllExternalDestinationsELO;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationImpl;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationPK;
import com.softproideas.app.admin.report.externaldestinations.model.ExternalDestinationDTO;
import com.softproideas.app.admin.report.externaldestinations.model.ExternalDestinationDetailsDTO;
import com.softproideas.app.admin.report.externaldestinations.model.ExternalDestinationUserDTO;

public class ExternalDestinationsMapper {

    @SuppressWarnings("unchecked")
    public static List<ExternalDestinationDTO> mapAllExternalDestinationsELO(AllExternalDestinationsELO list) {
        List<ExternalDestinationDTO> externalDestinationsDTOList = new ArrayList<ExternalDestinationDTO>();
        for (Iterator<AllExternalDestinationsELO> it = list.iterator(); it.hasNext();) {
            AllExternalDestinationsELO row = it.next();
            ExternalDestinationDTO externalDestinationDTO = new ExternalDestinationDTO();

            ExternalDestinationRef ref = row.getExternalDestinationEntityRef();
            externalDestinationDTO.setReportId(((ExternalDestinationPK) ref.getPrimaryKey()).getExternalDestinationId());
            externalDestinationDTO.setReportVisId(ref.getNarrative());
            externalDestinationDTO.setReportDescription(row.getDescription());

            externalDestinationsDTOList.add(externalDestinationDTO);
        }

        return externalDestinationsDTOList;
    }

    public static ExternalDestinationDetailsDTO mapExternalDestination(ExternalDestinationImpl externalDestination) {
        ExternalDestinationDetailsDTO externalDestinationDetailsDTO = new ExternalDestinationDetailsDTO();

        externalDestinationDetailsDTO.setReportId(((ExternalDestinationPK) externalDestination.getPrimaryKey()).getExternalDestinationId());
        externalDestinationDetailsDTO.setReportVisId(externalDestination.getVisId());
        externalDestinationDetailsDTO.setReportDescription(externalDestination.getDescription());
        externalDestinationDetailsDTO.setVersionNum(externalDestination.getVersionNum());

        // users
        @SuppressWarnings("unchecked")
        List<String> users = externalDestination.getUserList();
        List<ExternalDestinationUserDTO> userListDTO = new ArrayList<ExternalDestinationUserDTO>();
        for (int i = 0; i < users.size(); i++) {
            ExternalDestinationUserDTO userDTO = new ExternalDestinationUserDTO(i + 1, users.get(i));
            userListDTO.add(userDTO);
        }
        externalDestinationDetailsDTO.setUsers(userListDTO);

        return externalDestinationDetailsDTO;
    }

    public static ExternalDestinationImpl mapExternalDestinationDetailsDTOToExternalDestinationDetailsImpl(ExternalDestinationImpl impl, ExternalDestinationDetailsDTO report) {
        // override some fields values
        impl.setVisId(report.getReportVisId());
        impl.setDescription(report.getReportDescription());
        impl.setVersionNum(report.getVersionNum());

        // users
        List<ExternalDestinationUserDTO> userListDTO = report.getUsers();
        List<String> usersToSave = new ArrayList<String>();
        for (Iterator<ExternalDestinationUserDTO> it = userListDTO.iterator(); it.hasNext();) {
            ExternalDestinationUserDTO row = it.next();
            usersToSave.add(row.getName());
        }
        impl.setUserList(usersToSave);

        return impl;
    }

}
