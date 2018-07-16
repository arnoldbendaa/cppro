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
package com.softproideas.app.reviewbudget.budget.mapper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.InvalidResultSetAccessException;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.ReviewBudgetDetails;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.flatform.model.workbook.editor.WorkbookMapper;
import com.cedar.cp.util.flatform.model.workbook.util.CellType;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softproideas.app.reviewbudget.budget.model.ReviewBudgetDTO;
import com.softproideas.app.reviewbudget.dimension.model.ElementDTO;
import com.softproideas.app.reviewbudget.xcellform.model.XCellFormDTO;

/**
 * ReviewBudgetMapper is used for mapping data to transfer objects
 * associated with Budget Service.
 * 
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
public class ReviewBudgetMapper {

    /**
     * Map <code>ReviewBudgetDetails</code> to <code>ReviewBudgetDTO</code>.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static ReviewBudgetDTO mapToReviewBudgetDTO(ReviewBudgetDetails reviewBudgetDetails, ReviewBudgetDTO reviewBudgetDTO) {
        if (reviewBudgetDTO == null)
            reviewBudgetDTO = new ReviewBudgetDTO();

        List<ElementDTO> selectedDimensions = mapToSelectedDimensions(reviewBudgetDetails.getSelectionLabels());
        reviewBudgetDTO.setSelectedDimension(selectedDimensions);

        reviewBudgetDTO.setDataType(reviewBudgetDetails.getDataType());

        Map contextVariables = reviewBudgetDetails.getContextVariables();
        int modelId = (Integer) contextVariables.get(WorkbookProperties.MODEL_ID.toString());
        contextVariables.put(WorkbookProperties.CALENDAR_INFO.toString() + modelId, null);
        if (contextVariables.containsKey("cedar.financeCubeInput.key"))
            contextVariables.put("cedar.financeCubeInput.key", null);
        reviewBudgetDTO.setContextVariables(contextVariables);

        return reviewBudgetDTO;
    }

    /**
     * Map <code>File</code> which contains a valid JSON to <code>ReviewBudgetDTO</code>.
     */
    public static ReviewBudgetDTO mapToReviewBudgetDTO(File jsonFile) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonFile, ReviewBudgetDTO.class);
    }

    /**
     * Map <code>EntityList</code> selectionLabels which contains <code>StructureElementELO</code> 
     * to a list of <code>ElementDTO</code>.
     */
    public static List<ElementDTO> mapToSelectedDimensions(EntityList[] selectionLabels) {
        List<ElementDTO> result = new ArrayList<ElementDTO>();

        for (int i = 0; i < selectionLabels.length; ++i) {
            ElementDTO selectedDimension = new ElementDTO();
            if (selectionLabels[i] != null) {
                EntityList el = selectionLabels[i];
                selectedDimension.setId((Integer) el.getValueAt(0, "StructureElementId"));
                selectedDimension.setStructureId((Integer) el.getValueAt(0, "StructureId"));
                selectedDimension.setName((String) el.getValueAt(0, "VisId"));
                selectedDimension.setDescription((String) el.getValueAt(0, "Description"));
                selectedDimension.setLeaf((Boolean) el.getValueAt(0, "Leaf"));
            } else {
                selectedDimension.setName("?");
                selectedDimension.setDescription("");
                selectedDimension.setLeaf(false);
            }
            result.add(selectedDimension);
        }
        return result;
    }

    public static List<ElementDTO> mapToSelectedDimensions(List<Map<String, Object>> queryForList) {
        ElementDTO[] result = new ElementDTO[3];
        for (int i = 0; i < queryForList.size(); ++i) {
            ElementDTO selectedDimension = new ElementDTO();
            Map<String, Object> el = queryForList.get(i);

            BigDecimal id = (BigDecimal) el.get("STRUCTURE_ELEMENT_ID");
            selectedDimension.setId(id.intValue());

            BigDecimal structureId = (BigDecimal) el.get("STRUCTURE_ID");
            selectedDimension.setStructureId(structureId.intValue());

            selectedDimension.setName((String) el.get("VIS_ID"));
            selectedDimension.setDescription((String) el.get("DESCRIPTION"));

            String leaf = (String) el.get("LEAF");
            Boolean l = "Y".equalsIgnoreCase(leaf) ? new Boolean(true) : new Boolean(false);
            selectedDimension.setLeaf(Boolean.valueOf(l));

            BigDecimal dimId = (BigDecimal) el.get("DIM_TYPE");
            result[dimId.intValue()] = selectedDimension;
        }
        for (int i = 0; i < result.length; ++i) {
            if (result[i] == null) {
                ElementDTO selectedDimension = new ElementDTO();
                selectedDimension.setName("?");
                selectedDimension.setDescription("");
                selectedDimension.setLeaf(false);
                result[i] = selectedDimension;
            }
        }
        return Arrays.asList(result);
    }

    public static XCellFormDTO mapToReviewBudgetDTO(List<Map<String, Object>> queryForList) throws InvalidResultSetAccessException, Exception {
        if (queryForList != null && queryForList.size() == 1) {
            Map<String, Object> map = queryForList.get(0);
            XCellFormDTO form = mapToReviewBudget(map);
            String security = (String) map.get("SECURITY_ACCESS");
            form.setUserReadOnlyAccess(Boolean.getBoolean(security));
            return form;
        } else {

        }
        return null;
    }

    public static XCellFormDTO mapToReviewBudgetDTOMobile(List<Map<String, Object>> queryForList) throws InvalidResultSetAccessException, Exception {
        if (queryForList != null && queryForList.size() == 1) {
            Map<String, Object> map = queryForList.get(0);
            XCellFormDTO form = mapToReviewBudget(map);
            form.setExcelFile((byte[]) map.get("EXCEL_FILE"));
            return form;
        } else {

        }
        return null;
    }

    private static XCellFormDTO mapToReviewBudget(Map<String, Object> rowSet) throws InvalidResultSetAccessException, Exception {
        XCellFormDTO form = new XCellFormDTO();

        String json = (String) rowSet.get("JSON_FORM");
        form.setJsonForm(json);
        form.setDataType((String) rowSet.get("XML_FORM_DATA_TYPE"));

        Map<Integer, Integer> context = new HashMap<Integer, Integer>();
        BigDecimal period = (BigDecimal) rowSet.get("PERIOD_ID_TO");
        context.put(2, period.intValue());
        form.setContextVariables(context);

        String xmlForm = (String) rowSet.get("DEFINITION");
        
        WorkbookDTO workbook = WorkbookMapper.mapDefinitionXML(xmlForm, CellType.REVIEW_BUDGET);
        form.setWorkbook(workbook);

        return form;
    }
}
