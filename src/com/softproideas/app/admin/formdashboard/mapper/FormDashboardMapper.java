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
package com.softproideas.app.admin.formdashboard.mapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.softproideas.common.models.FormDashboardDTO;

public class FormDashboardMapper {

    public static List<FormDashboardDTO> mapForms(List<Map<String, Object>> rows, List<Integer> filterXmlForms, List<Integer> filterModelsIds, boolean isSystemAdmininistrator) {
        List<FormDashboardDTO> list = new ArrayList<FormDashboardDTO>();
        for (Map<String, Object> row: rows) {
            FormDashboardDTO form = mapForm(row);
            if (isSystemAdmininistrator) {
                list.add(form);
            } else {
                if (filterModelsIds.contains(form.getModelId())) {
                    if (filterXmlForms.contains(form.getFormId())) {
                        list.add(form);
                    }
                }
            }
        }
        return list;
    }

    private static FormDashboardDTO mapForm(Map<String, Object> row) {
        FormDashboardDTO form = new FormDashboardDTO();

        String uuid = (String) row.get("DASHBOARD_UUID");
        form.setDashboardUUID(UUID.fromString(uuid));

        String dashboardName = (String) row.get("DASHBOARD_TITLE");
        form.setDashboardName(dashboardName);

        BigDecimal formId = (BigDecimal) row.get("FORM_ID");
        form.setFormId(formId.intValue());

        BigDecimal modelId = (BigDecimal) row.get("MODEL_ID");
        form.setModelId(modelId.intValue());

        String formName = (String) row.get("VIS_ID");
        form.setFormName(formName);

        Timestamp time = (Timestamp) row.get("UPDATED_TIME");
        Date dateTime = new Date(time.getTime());

        String date = new SimpleDateFormat("yyyy-MM-dd").format(dateTime);
        form.setLastUpdate(date);

        return form;
    }

    public static List<Integer> mapXmlFormIdsForUser(List<Map<String, Object>> rows) {
        List<Integer> list = new ArrayList<Integer>();
        for (Map<String, Object> row: rows) {
            Integer value = mapXmlFormIdForUser(row);
            if (value != null) {
                list.add(value);
            }
        }
        return list;
    }

    private static Integer mapXmlFormIdForUser(Map<String, Object> row) {
        BigDecimal formId = (BigDecimal) row.get("XML_FORM_ID");
        return formId == null ? null : formId.intValue();
    }

    public static List<Integer> mapModelIdsForUser(List<Map<String, Object>> rows) {
        List<Integer> list = new ArrayList<Integer>();
        for (Map<String, Object> row: rows) {
            Integer value = mapModelIdUser(row);
            if (value != null) {
                list.add(value);
            }
        }
        return list;
    }

    private static Integer mapModelIdUser(Map<String, Object> row) {
        BigDecimal modelId = (BigDecimal) row.get("MODEL_ID");
        return modelId == null ? null : modelId.intValue();
    }

}
