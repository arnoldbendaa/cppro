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
package com.softproideas.app.reviewbudget.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.softproideas.app.reviewbudget.budget.mapper.ReviewBudgetMapper;
import com.softproideas.app.reviewbudget.dimension.model.ElementDTO;
import com.softproideas.app.reviewbudget.xcellform.model.XCellFormDTO;

@Repository("budgetCycleDAO")
public class BudgetCycleDAOImpl extends JdbcDaoSupport implements BudgetCycleDAO {

    @Autowired
    @Qualifier("cpDataSource")
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

//@formatter:off
    @Override
    public int getFlatFormId(int dataEntryProfileId) {
        String sql = "SELECT XMLFORM_ID FROM DATA_ENTRY_PROFILE WHERE DATA_ENTRY_PROFILE_ID = ?";
        // List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, new Object[] { dataEntryProfileId });
        int flatFormId = -1;
        flatFormId = getJdbcTemplate().queryForObject(sql, Integer.class, dataEntryProfileId);
        return flatFormId;
    }
    
    @Override
    public XCellFormDTO getXCellFormDTO(int dataEntryProfileId) throws InvalidResultSetAccessException, Exception {
        String sql = "select x.JSON_FORM, x.DEFINITION, x.SECURITY_ACCESS, bc.XML_FORM_DATA_TYPE, bc.PERIOD_ID_TO "
                + "from XML_FORM x "
                + "join DATA_ENTRY_PROFILE dep on (x.XML_FORM_ID = dep.XMLFORM_ID) "
                + "join BUDGET_CYCLE bc on (dep.BUDGET_CYCLE_ID = bc.BUDGET_CYCLE_ID)"
                + " where dep.DATA_ENTRY_PROFILE_ID = ?";
        //SqlRowSet sqlRowSet = getJdbcTemplate().queryForRowSet(sql, dataEntryProfileId);
        List<Map<String, Object>> queryForList = getJdbcTemplate().queryForList(sql, dataEntryProfileId);
        XCellFormDTO reviewBudgetDTO = ReviewBudgetMapper.mapToReviewBudgetDTO(queryForList);
        return reviewBudgetDTO;
    }
    
    @Override
    public XCellFormDTO getXCellFormDTOMobile(int dataEntryProfileId) throws InvalidResultSetAccessException, Exception {
        String sql = "select x.JSON_FORM, x.DEFINITION, x.EXCEL_FILE, bc.XML_FORM_DATA_TYPE, bc.PERIOD_ID_TO "
                + "from XML_FORM x "
                + "join DATA_ENTRY_PROFILE dep on (x.XML_FORM_ID = dep.XMLFORM_ID) "
                + "join BUDGET_CYCLE bc on (dep.BUDGET_CYCLE_ID = bc.BUDGET_CYCLE_ID)"
                + " where dep.DATA_ENTRY_PROFILE_ID = ?";
        List<Map<String, Object>> queryForList = getJdbcTemplate().queryForList(sql, dataEntryProfileId);
        XCellFormDTO reviewBudgetDTO = ReviewBudgetMapper.mapToReviewBudgetDTOMobile(queryForList);
        return reviewBudgetDTO;
    }

    @Override
    public Map<String, String> getHierarchyLabels(Map<Integer, Integer> selectionsMap){
        String sql = ""
                + "WITH dim0 AS "
                + "  (SELECT VIS_ID FROM STRUCTURE_ELEMENT WHERE STRUCTURE_ELEMENT_ID = ? "
                + "  ), "
                + "  dim1 AS "
                + "  (SELECT VIS_ID FROM STRUCTURE_ELEMENT WHERE STRUCTURE_ELEMENT_ID = ? "
                + "  ), "
                + "  dim2 AS "
                + "  (SELECT VIS_ID, "
                + "    CAL_VIS_ID_PREFIX "
                + "  FROM STRUCTURE_ELEMENT "
                + "  WHERE STRUCTURE_ELEMENT_ID = ?"
                + "  ) "
                + "SELECT dim0.VIS_ID \"" + WorkbookProperties.DIMENSION_0_VISID + "\", "
                + "dim1.VIS_ID \"" + WorkbookProperties.DIMENSION_1_VISID + "\", "
                + "dim2.CAL_VIS_ID_PREFIX||dim2.VIS_ID \"" + WorkbookProperties.DIMENSION_2_VISID + "\" "
                + "FROM dim0, dim1, dim2";

        Object[] params = new Integer[3];
        for(int i = 0; i < 3; i++){
            params[i] = selectionsMap.get(i);
        }
        Map<String, Object> queryForMap = getJdbcTemplate().queryForMap(sql, params);
        Map<String, String> hierarchyLabels = new HashMap<String, String>();
        for(String s: queryForMap.keySet()){
            hierarchyLabels.put(s, (String)queryForMap.get(s));
        }
        return hierarchyLabels;
    }
    
    @Override
    public List<ElementDTO> getSelectedDimensions(Map<Integer, Integer> selectionsMap, int modelId){
        if(selectionsMap == null){
            return null;
        }
        String getDim0 = ""
                + "dim0 AS (SELECT 0 DIM_TYPE, se.STRUCTURE_ID, se.STRUCTURE_ELEMENT_ID, se.VIS_ID, se.DESCRIPTION, se.LEAF "
                + "  FROM STRUCTURE_ELEMENT se ";
        String getDim1 = ""
                + "dim1 AS (SELECT 1 DIM_TYPE, se.STRUCTURE_ID, se.STRUCTURE_ELEMENT_ID, se.VIS_ID, se.DESCRIPTION, se.LEAF "
                + "  FROM STRUCTURE_ELEMENT se ";
                
        String getDim = "  WHERE se.STRUCTURE_ELEMENT_ID = ? ), ";
                
        String getDim0FromModelId = ""
                + "JOIN MODEL mo on (se.STRUCTURE_ID = mo.BUDGET_HIERARCHY_ID) "
                + "WHERE mo.MODEL_ID = ? AND se.PARENT_ID = 0), ";
        String getDim1FromModelId = ""
                + "JOIN HIERARCHY hier on (se.STRUCTURE_ID = hier.HIERARCHY_ID) "
                + "JOIN MODEL mo on (hier.DIMENSION_ID = mo.ACCOUNT_ID) "
                + "WHERE mo.MODEL_ID = ? AND se.PARENT_ID = 0),";
        String varname1 = ""
                + "dim2 AS   (SELECT 2 DIM_TYPE, STRUCTURE_ID, STRUCTURE_ELEMENT_ID, VIS_ID, DESCRIPTION, LEAF, CAL_VIS_ID_PREFIX "
                + "FROM STRUCTURE_ELEMENT "
                + "WHERE STRUCTURE_ELEMENT_ID = ?) "
                + "SELECT  * FROM dim0 "
                + "UNION ALL "
                + "SELECT  * FROM dim1 "
                + "UNION ALL "
                + "SELECT  DIM_TYPE, STRUCTURE_ID, STRUCTURE_ELEMENT_ID, CAL_VIS_ID_PREFIX||VIS_ID \"VIS_ID\", DESCRIPTION, LEAF FROM dim2";
        
        Object[] params = new Integer[3];

        StringBuilder sb = new StringBuilder();
        sb.append("WITH ");
        sb.append(getDim0);
        if(selectionsMap.containsKey(0)){
            sb.append(getDim);
            params[0] = selectionsMap.get(0);
        }
        else{
            sb.append(getDim0FromModelId);
            params[0] = modelId;
        }
        sb.append(getDim1);
        if(selectionsMap.containsKey(1)){
            sb.append(getDim);
            params[1] = selectionsMap.get(1);
        }
        else{
            sb.append(getDim1FromModelId);
            params[1] = modelId;
        }
        sb.append(varname1);
        params[2] = selectionsMap.get(2);        

        List<Map<String, Object>> queryForList = getJdbcTemplate().queryForList(sb.toString(), params);
        List<ElementDTO> mapToSelectedDimensions = ReviewBudgetMapper.mapToSelectedDimensions(queryForList);
        return mapToSelectedDimensions;
    }
    //@formatter:on
}
