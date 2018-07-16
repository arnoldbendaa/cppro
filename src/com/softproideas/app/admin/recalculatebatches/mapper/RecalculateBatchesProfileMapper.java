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
package com.softproideas.app.admin.recalculatebatches.mapper;

import java.util.ArrayList;
import java.util.List;

import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.dto.user.DataEntryProfileCK;
import com.cedar.cp.dto.user.DataEntryProfilePK;
import com.cedar.cp.dto.user.DataEntryProfileRefImpl;
import com.cedar.cp.dto.user.UserPK;
import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchProfileDTO;
import com.softproideas.app.core.profile.model.ProfileDTO;

/**
 * <p>Class is responsible for maps different object related to profiles which are related to recalculate batch to data transfer object (and vice-versa)</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public class RecalculateBatchesProfileMapper {

    /**
     * Maps list of profiles {@link RecalculateBatchProfileDTO} to list of transfer data object related to profiles {@link DataEntryProfileRef} in recalculate batch.
     */
    public static List<DataEntryProfileRef> mapProfilesDTOToProfilesRef(List<RecalculateBatchProfileDTO> profiles, int userId) {
        List<DataEntryProfileRef> dataEntryProfileRefs = new ArrayList<DataEntryProfileRef>();
        for (RecalculateBatchProfileDTO profile: profiles) {
            if (profile.isSelected()) {
                DataEntryProfilePK dataEntryProfilePK = new DataEntryProfilePK(profile.getProfileId());
                UserPK userPK = new UserPK(userId);
                DataEntryProfileCK dataEntryProfilecK = new DataEntryProfileCK(userPK, dataEntryProfilePK);
                DataEntryProfileRef dataEntryProfileRef = new DataEntryProfileRefImpl(dataEntryProfilecK, profile.getName());
                dataEntryProfileRefs.add(dataEntryProfileRef);
            }
        }
        return dataEntryProfileRefs;
    }

    /**
     * Maps (rewrites properties) list of profiles {@link ProfileDTO} to list of profiles {@link RecalculateBatchProfileDTO} which 
     * has more details in recalculate batch .
     */
    public static List<RecalculateBatchProfileDTO> mapProfilesDTOToBatchRecalculateProfileDTO(List<ProfileDTO> profilesDTO) {
        List<RecalculateBatchProfileDTO> list = new ArrayList<RecalculateBatchProfileDTO>();
        for (ProfileDTO profileDTO: profilesDTO) {
            RecalculateBatchProfileDTO recalculateBatchProfileDTO = new RecalculateBatchProfileDTO();

            recalculateBatchProfileDTO.setProfileId(profileDTO.getProfileId());
            recalculateBatchProfileDTO.setName(profileDTO.getName());
            recalculateBatchProfileDTO.setDescription(profileDTO.getDescription());
            recalculateBatchProfileDTO.setFormId(profileDTO.getFormId());
            recalculateBatchProfileDTO.setStructureId0(profileDTO.getStructureId0());

            recalculateBatchProfileDTO.setStructureId1(profileDTO.getStructureId1());
            recalculateBatchProfileDTO.setStructureElementId0(profileDTO.getStructureElementId0());
            recalculateBatchProfileDTO.setStructureElementId1(profileDTO.getStructureElementId1());
            recalculateBatchProfileDTO.setElementLabel0(profileDTO.getElementLabel0());
            recalculateBatchProfileDTO.setElementLabel1(profileDTO.getElementLabel1());

            recalculateBatchProfileDTO.setDataType(profileDTO.getDataType());
            recalculateBatchProfileDTO.setFormType(profileDTO.getFormType());
            recalculateBatchProfileDTO.setSelected(false);
            list.add(recalculateBatchProfileDTO);
        }
        return list;
    }
}