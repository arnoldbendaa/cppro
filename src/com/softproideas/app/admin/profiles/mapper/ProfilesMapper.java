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
package com.softproideas.app.admin.profiles.mapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.softproideas.app.admin.profiles.model.MobileProfileDTO;
import com.softproideas.app.admin.profiles.model.ProfileDTO;
import com.softproideas.app.admin.profiles.model.ProfileDetailsDTO;
import com.softproideas.app.admin.profiles.model.StructureElementDTO;
import com.softproideas.common.models.StructureElementCoreDTO;

public class ProfilesMapper {

    public static List<ProfileDTO> mapProfiles(List<Map<String, Object>> rows) {
        List<ProfileDTO> list = new ArrayList<ProfileDTO>();
        for (Map<String, Object> row: rows) {
            ProfileDTO profile = mapProfile(row);
            list.add(profile);
        }
        return list;
    }

    public static ProfileDTO mapProfile(Map<String, Object> row) {
        ProfileDTO profile = new ProfileDTO();

        // data entry profile id (pk)
        BigDecimal dataEntryProfileId = (BigDecimal) row.get("DATA_ENTRY_PROFILE_ID");
        profile.setProfileId(dataEntryProfileId.intValue());

        // data entry vis id
        String dataEntryVisId = (String) row.get("DATA_ENTRY_VIS_ID");
        profile.setProfileVisId(dataEntryVisId);

        // description
        String description = (String) row.get("DESCRIPTION");
        profile.setProfileDescription(description);

        // date and hour
        Timestamp time = (Timestamp) row.get("UPDATED_TIME");
        Date dateTime = new Date(time.getTime());
        String date = new SimpleDateFormat("yyyy-MM-dd").format(dateTime);
        profile.setUpdatedTime(date);

        // model vis id
        String modelVisId = (String) row.get("VIS_ID");
        profile.setModelVisId(modelVisId);

        // model id
        BigDecimal d = (BigDecimal) row.get("MODEL_ID");
        int modelId = d.intValue();
        profile.setModelId(modelId);

        // xml form id
        BigDecimal p = (BigDecimal) row.get("XMLFORM_ID");
        int xmlFormId = p.intValue();
        profile.setXmlFormId(xmlFormId);

        // user name
        String userName = (String) row.get("NAME");
        profile.setUserName(userName);

        // user id
        BigDecimal u = (BigDecimal) row.get("USER_ID");
        int userId = u.intValue();
        profile.setUserId(userId);

        return profile;
    }

    public static ProfileDetailsDTO mapProfileDetails(Map<String, Object> row) {
        ProfileDetailsDTO profile = new ProfileDetailsDTO();

        // Business Dimension + Account Dimension + Calendar Dimension
        List<StructureElementCoreDTO> selectedElements = new ArrayList<StructureElementCoreDTO>();
        for (int i = 0; i < 3; i++) {
            StructureElementCoreDTO dto = new StructureElementCoreDTO();
            dto.setStructureElementVisId((String) row.get("ELEMENT_LABEL" + i));
            dto.setStructureElementId(((BigDecimal) row.get("STRUCTURE_ELEMENT_ID" + i)).intValue());
            dto.setStructureId(((BigDecimal) row.get("STRUCTURE_ID" + i)).intValue());
            selectedElements.add(dto);
        }
        profile.setSelectedElements(selectedElements);
        profile.setFormId(((BigDecimal) row.get("XMLFORM_ID")).intValue());
        profile.setUserId(((BigDecimal) row.get("USER_ID")).intValue());
        profile.setProfileId(((BigDecimal) row.get("DATA_ENTRY_PROFILE_ID")).intValue());
        profile.setProfileVisId((String) row.get("VIS_ID"));
        profile.setProfileDescription((String) row.get("DESCRIPTION"));
        profile.setBudgetCycleId(((BigDecimal) row.get("BUDGET_CYCLE_ID")).intValue());
        profile.setModelId(((BigDecimal) row.get("MODEL_ID")).intValue());
        profile.setDataType((String) row.get("DATA_TYPE"));
        String mobile = (String) row.get("MOBILE");
        profile.setMobile(mobile.charAt(0));

        return profile;
    }
    
    public static MobileProfileDTO mapMobileProfileForUser(Map<String, Object> row) {
        MobileProfileDTO profile = new MobileProfileDTO();

        // Business Dimension + Account Dimension + Calendar Dimension
        List<StructureElementDTO> selectedElements = new ArrayList<StructureElementDTO>();
        for (int i = 0; i < 3; i++) {
            StructureElementDTO dto = new StructureElementDTO();
            dto.setStructureElementVisId((String) row.get("ELEMENT_LABEL" + i));
            dto.setStructureElementId(((BigDecimal) row.get("STRUCTURE_ELEMENT_ID" + i)).intValue());
            dto.setStructureId(((BigDecimal) row.get("STRUCTURE_ID" + i)).intValue());
            selectedElements.add(dto);
        }
        profile.setSelectedElements(selectedElements);

        profile.setDataType((String) row.get("DATA_TYPE"));
        profile.setProfileId(((BigDecimal) row.get("DATA_ENTRY_PROFILE_ID")).intValue());
        profile.setProfileVisId((String) row.get("VIS_ID"));
        profile.setProfileDescription((String) row.get("DESCRIPTION"));
        profile.setModelVisId((String) row.get("MODEL_VIS_ID"));

        Timestamp time = (Timestamp) row.get("UPDATED_TIME");
        Date dateTime = new Date(time.getTime());
        String date = new SimpleDateFormat("yyyy-MM-dd").format(dateTime);
        profile.setUpdatedTime(date);

        return profile;
    }

    public static List<MobileProfileDTO> mapProfilesForUser(List<Map<String, Object>> rows) {

        List<MobileProfileDTO> profiles = new ArrayList<MobileProfileDTO>();
        for (Map<String, Object> row: rows) {
            profiles.add(mapMobileProfileForUser(row));
        }
        return profiles;
    }

}
