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
package com.softproideas.app.core.profile.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.user.DataEntryProfile;
import com.cedar.cp.api.user.DataEntryProfileEditor;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.user.DataEntryProfileRefImpl;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.utc.de.profiles.DataEntryProfileVO;
import com.softproideas.app.core.form.mapper.FormMapper;
import com.softproideas.app.core.profile.model.ProfileDTO;
import com.softproideas.app.core.profile.model.UserIdXmlForm;

/**
 * <p>Mapps objects</p>
 * 
 * @author Szymon Walczak
 * @email szymon.walczak@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public class ProfileMapper {

    /**
     * Sets properties in <code>ProfileDTO</code> from <code>EntityList</code>
     */
    public static List<ProfileDTO> mapToDTO(EntityList profileEntityList) {
        List<ProfileDTO> profiles = new ArrayList<ProfileDTO>();
        @SuppressWarnings("unchecked")
        List<ArrayList<Object>> dataList = profileEntityList.getDataAsList();

        for (List<Object> oneEntry: dataList) {
            int i = 0;
            ProfileDTO profile = new ProfileDTO();
            DataEntryProfileRefImpl dataEntry = (DataEntryProfileRefImpl) oneEntry.get(i);
            profile.setProfileId(dataEntry.getDataEntryProfilePK().getDataEntryProfileId());
            profile.setName(dataEntry.getNarrative());
            i = 2;
            profile.setDescription((String) oneEntry.get(i++));
            profile.setFormId((Integer) oneEntry.get(i++));
            profile.setStructureId0((Integer) oneEntry.get(i++));
            profile.setStructureId1((Integer) oneEntry.get(i++));
            profile.setStructureElementId0((Integer) oneEntry.get(i++));
            profile.setStructureElementId1((Integer) oneEntry.get(i++));
            profile.setElementLabel0((String) oneEntry.get(i++));
            profile.setElementLabel1((String) oneEntry.get(i++));
            profile.setDataType((String) oneEntry.get(i++));
            profile.setFormType(FormMapper.mapFormType((Integer) oneEntry.get(i++)));

            profiles.add(profile);
        }
        return profiles;
    }

    /**
     * Sets properties in <code>DataEntryEditor</code> from <code>DataEntryProfileVO</code>
     */
    public static void mapToEditor(DataEntryProfileEditor editor, DataEntryProfileVO profile) throws ValidationException {
        editor.setDescription(profile.getDescription());
        editor.setFillDisplayArea(profile.isFillDisplayArea());
        editor.setShowBoldSummaries(profile.isShowBoldSummaries());
        editor.setShowHorizontalLines(profile.isShowHorizontalLines());
        editor.setUserId(profile.getUserId());
        editor.setAutoOpenDepth(profile.getAutoOpenDepth());
        editor.setUserRef(profile.getUserRef());
        editor.setVisId(profile.getVisId());
        editor.setXmlformId(profile.getXmlformId());
        editor.setXmlFormRef(profile.getXmlFormRef());
        editor.setModelId(profile.getModelId());
        editor.setBudgetCycleId(profile.getBudgetCycleId());
        editor.setDataType(profile.getDataType());
        editor.setStructureIdArray(profile.getStructureIdArray());
        editor.setStructureElementIdArray(profile.getStructureElementIdArray());
        editor.setElementLabelArray(profile.getElementLabelArray());
        editor.setBudgetCycleId(profile.getBudgetCycleId());
    }

    /**
     * Sets properties in <code>DataEntryEditor</code>
     */
    public static void mapToEditor(DataEntryProfileEditor editor, ProfileDTO profile, XmlFormRef formRef) throws ValidationException {

        editor.setDescription(profile.getDescription());
        editor.setXmlformId(profile.getFormId());
        editor.setXmlFormRef(formRef);
        editor.setStructureId0(profile.getStructureId0());
        editor.setStructureId1(profile.getStructureId1());
        editor.setStructureElementId0(profile.getStructureElementId0());
        editor.setStructureElementId1(profile.getStructureElementId1());
        editor.setElementLabel0(profile.getElementLabel0());
        editor.setElementLabel1(profile.getElementLabel1());
        editor.setDataType(profile.getDataType());
    }

    /**
     * Sets properties related to xmlForm in <code>DataEntryProfileVO</code>
     */
    public static void mapFormPropertiesToVO(DataEntryProfileVO profileVO, Object[] xmlForm) {
        XmlFormRef xmlFormRef = (XmlFormRef) xmlForm[0];

        if (profileVO == null) {
            profileVO = new DataEntryProfileVO();
        }

        profileVO.setXmlFormRef(xmlFormRef);
        profileVO.setXmlformId(((XmlFormPK) xmlFormRef.getPrimaryKey()).getXmlFormId());
        profileVO.setDescription((String) xmlForm[3]);
        String visId = mapFormNameToProfileName(xmlFormRef.getNarrative());
        profileVO.setVisId(visId);
    }

    public static String mapFormNameToProfileName(String narrative) {
        Pattern p = Pattern.compile(".*-\\s*(.*)");
        Matcher m = p.matcher(narrative);

        if (m.find()) {
            return m.group(1).toString();
        } else {
            return narrative;
        }
    }

    /**
     * Sets properties in <code>DataEntryProfileVO</code> from <code>ProfileDTO</code>
     */
    public static void mapToVO(DataEntryProfileVO profileVO, ProfileDTO profile) {
        if (profileVO == null) {
            profileVO = new DataEntryProfileVO();
        }
        profileVO.setDescription(profile.getDescription());
        profileVO.setVisId(profile.getName());
        profileVO.setStructureId0(profile.getStructureId0());
        profileVO.setStructureId1(profile.getStructureId1());
        profileVO.setStructureElementId0(profile.getStructureElementId0());
        profileVO.setStructureElementId1(profile.getStructureElementId1());
        profileVO.setElementLabel0(profile.getElementLabel0());
        profileVO.setElementLabel1(profile.getElementLabel1());
        profileVO.setDataType(profile.getDataType());
    }

    /**
     * Sets properties in <code>DataEntryProfileVO</code> from <code>dataEntryProfile</code>
     */
    public static void mapEditorDataToVO(DataEntryProfileVO profileVO, DataEntryProfile profile) {
        if (profileVO == null) {
            profileVO = new DataEntryProfileVO();
        }
        profileVO.setDescription(profile.getDescription());
        profileVO.setFillDisplayArea(profile.isFillDisplayArea());
        profileVO.setPrimaryKey(profile.getPrimaryKey());
        profileVO.setShowBoldSummaries(profile.isShowBoldSummaries());
        profileVO.setShowHorizontalLines(profile.isShowHorizontalLines());
        profileVO.setUserId(profile.getUserId());
        profileVO.setAutoOpenDepth(profile.getAutoOpenDepth());
        profileVO.setUserRef(profile.getUserRef());
        profileVO.setVisId(profile.getVisId());
        profileVO.setModelId(profile.getModelId());
        profileVO.setBudgetCycleId(profile.getBudgetCycleId());
        profileVO.setDataType(profile.getDataType());
        profileVO.setStructureIdArray(profile.getStructureIdArray());
        profileVO.setStructureElementIdArray(profile.getStructureElementIdArray());
        profileVO.setElementLabelArray(profile.getElementLabelArray());
    }

    public static List<UserIdXmlForm> mapXmlFormsIds(List<Map<String, Object>> rows, Integer userId) {
        List<UserIdXmlForm> list = new ArrayList<UserIdXmlForm>();
        for (Map<String, Object> row: rows) {
            UserIdXmlForm XmlForm = mapXmlFormsId(row, userId);
            if (userId != null) {
                list.add(XmlForm);
            }
        }
        return list;
    }

    private static UserIdXmlForm mapXmlFormsId(Map<String, Object> row, Integer userId) {
        UserIdXmlForm result = new UserIdXmlForm();

        result.setUserId(userId);

        BigDecimal xmlFormId = (BigDecimal) row.get("XML_FORM_ID");
        result.setXmlFormId(xmlFormId.intValue());

        String visId = mapFormNameToProfileName((String) row.get("VIS_ID"));
        result.setVisId(visId);

        String description = (String) row.get("DESCRIPTION");
        result.setDescription(description);

        BigDecimal budgetCycleId = (BigDecimal) row.get("BUDGET_CYCLE_ID");
        result.setBudgetCycleId((budgetCycleId != null) ? budgetCycleId.intValue() : 0);

        return result;
    }
}
