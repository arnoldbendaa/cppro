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
package com.softproideas.app.dashboard.form.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.cedar.cp.api.base.ValidationException;
import com.softproideas.app.dashboard.form.TabType;
import com.softproideas.app.dashboard.form.model.CellRange;
import com.softproideas.app.dashboard.form.model.ContextData;
import com.softproideas.app.dashboard.form.model.DashboardDTO;
import com.softproideas.app.dashboard.form.model.DashboardTab;
import com.softproideas.app.dashboard.form.model.HierarchyElement;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

@Repository("formDao")
public class FormDAOImpl extends JdbcDaoSupport implements FormDAO {

    @Autowired
    @Qualifier("cpDataSource")
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }
    
    @Override
    public ResponseMessage insertOrUpdateFreeForm(DashboardDTO form) throws DaoException, ValidationException {
        if (form.getDashboardTitle()== null || form.getDashboardTitle().isEmpty()){
            return new ResponseMessage(false, "Dashboard has no title");
        }
        ArrayList<DashboardTab> formTabs = form.getTabs();
        DashboardTab currentTab;
        for(int i=0;i<formTabs.size();i++){
            currentTab = formTabs.get(i);
            if (currentTab.getTabName()== null || currentTab.getTabName().isEmpty()){
                return new ResponseMessage(false, "Tab has no title");
            }
            if (TabType.PIE.equalsName(currentTab.getChartType())) {
                if (!validateCellRange(currentTab.getRowsRange()) || !validateCellRange(currentTab.getValuesRange())){
                    return new ResponseMessage(false, "Dashboard has no required data");
                }
            } else{
                if (!validateCellRange(currentTab.getRowsRange()) || !validateCellRange(currentTab.getValuesRange()) || !validateCellRange(currentTab.getColsRange())){
                    return new ResponseMessage(false, "Dashboard has no required data");
                }
            }
        }
        String query = "";
        PreparedStatementSetter pss = null;
        String uuid = null;
        if (form.getDashboardUUID()!=null){
            query = "update DASHBOARD set DASHBOARD_TITLE = ?, CHARTS = ?, FORM_ID = ?, UPDATED_TIME = LOCALTIMESTAMP, SELECTED_DIMENSIONS = ?, DATA_TYPE = ?, DASHBOARD_TYPE = ?,  MODEL_ID = ? where DASHBOARD_UUID = ?";
            pss = prepareUpdateStatement(form);  
        } else{
            query = "insert into DASHBOARD (DASHBOARD_UUID, DASHBOARD_TITLE, CHARTS, FORM_ID, SELECTED_DIMENSIONS, DATA_TYPE, UPDATED_TIME, DASHBOARD_TYPE, MODEL_ID) values (?, ?, ?, ?, ?, ?, LOCALTIMESTAMP, ?, ?)";
            uuid = UUID.randomUUID().toString();
            pss = prepareInsertStatement(form, uuid);
        }
        try {
            getJdbcTemplate().update(query, pss);
            ResponseMessage response = new ResponseMessage(true, "Dashboard has been saved.");
            if(form.getDashboardUUID() == null && uuid != null){
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("uuid", uuid);
                response.setData(data);
            }
            return response;
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            ValidationError error = new ValidationError("Dashboard with that name already exists");
            return error;
        } catch (Exception e) {
            e.printStackTrace();
            ValidationError error = new ValidationError("Error while executing insert query");
            return error;
        }
    }
    
    
    @Override
    public boolean deleteFreeForm(UUID id) throws DaoException {
        String query = "delete from DASHBOARD where DASHBOARD_UUID = ?";
        try {
            int result = getJdbcTemplate().update(query, id.toString());
            return result >= 1;
        }
          catch (Exception e) {
            String errorMsg = "Error while executing delete query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }
   
    @Override
    public DashboardDTO getFreeFormByUUID(UUID id) throws DaoException, IOException, ClassNotFoundException {
        String query = "select * from DASHBOARD where DASHBOARD_UUID = ?";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, new Object[] { id.toString() });
        DashboardDTO freeForm = new DashboardDTO();
        freeForm.setDashboardUUID(id);
        if ((rows != null) && (rows.size() > 0)) {
                BigDecimal formId = (BigDecimal)(rows.get(0).get("FORM_ID"));
                freeForm.setFormId(formId.intValue());
                
                String dashboardName = (String)(rows.get(0).get("DASHBOARD_TITLE"));
                freeForm.setDashboardTitle(dashboardName);
                
                byte[] tempMemberCharts = (byte[])(rows.get(0).get("CHARTS"));    
                ByteArrayInputStream bos = new ByteArrayInputStream(tempMemberCharts) ;
                ObjectInputStream out = new ObjectInputStream(bos);
                ArrayList<DashboardTab> charts = (ArrayList<DashboardTab>)out.readObject();
                freeForm.setTabs(charts);
                
                byte[] tempContextData = (byte[])(rows.get(0).get("SELECTED_DIMENSIONS"));    
                bos = new ByteArrayInputStream(tempContextData) ;
                out = new ObjectInputStream(bos);
                ContextData contextData = (ContextData)out.readObject();
                freeForm.setContextData(contextData);
                  
                String type = (String)(rows.get(0).get("DASHBOARD_TYPE"));
                freeForm.setType(type);
                
                BigDecimal modelId = (BigDecimal)(rows.get(0).get("MODEL_ID"));
                freeForm.setModelId(modelId.intValue());
                
        }
        return freeForm;
    }
    
    @Override
    public HierarchyElement getContextData(String structureElementVisId, int modelId) {
        String query = "SELECT * FROM structure_element se JOIN hierarchy h ON (se.structure_id = h.hierarchy_id) JOIN model m ON (h.dimension_id = m.account_id) WHERE se.vis_id = ? AND m.model_id = ?";      
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, new Object[] { structureElementVisId, modelId });
        BigDecimal structureId = (BigDecimal)(rows.get(0).get("STRUCTURE_ID"));
        BigDecimal structureElementId = (BigDecimal)(rows.get(0).get("STRUCTURE_ELEMENT_ID"));
        HierarchyElement hierarchyElement = new HierarchyElement(structureId.intValue(), structureElementId.intValue(), null, null);
        return hierarchyElement;
    }
    
    private PreparedStatementSetter prepareInsertStatement(final DashboardDTO freeForm, final String uuid) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                byte[] charts = serializeObjectToBytes(freeForm.getTabs());
                byte[] contextData = serializeObjectToBytes(freeForm.getContextData());
                //byte[] selectedDimensions = serializeObjectToBytes(freeForm.getSelectedDimensions());
                
                ps.setString(1, uuid);
                ps.setString(2, freeForm.getDashboardTitle());
                ps.setBytes(3, charts);
                ps.setInt(4, freeForm.getFormId());
                ps.setBytes(5, contextData);
                ps.setString(6, null);
                ps.setString(7, freeForm.getType().toString());
                ps.setInt(8, freeForm.getModelId());
            }
        };
        return pss;
    }
    
    private PreparedStatementSetter prepareUpdateStatement(final DashboardDTO freeForm) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                byte[] charts = serializeObjectToBytes(freeForm.getTabs());
                byte[] contextData = serializeObjectToBytes(freeForm.getContextData());
                //byte[] selectedDimensions = serializeObjectToBytes(freeForm.getSelectedDimensions());

                ps.setString(1, freeForm.getDashboardTitle());
                ps.setBytes(2, charts);
                ps.setInt(3, freeForm.getFormId());
                ps.setBytes(4, contextData);
                ps.setString(5, null);
                ps.setString(6, freeForm.getType().toString());
                ps.setInt(7, freeForm.getModelId());
                ps.setString(8, freeForm.getDashboardUUID().toString());
                
            }
        };
        return pss;
    }

    private byte[] serializeObjectToBytes(Object obj){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] bytesFormat = null;
        try {
            out = new ObjectOutputStream(bos);   
            out.writeObject(obj);
            bytesFormat = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytesFormat;
    }

	@Override
	public Integer exchangeForModelId(Integer financeCubeId) {
		String query = "SELECT MODEL_ID FROM FINANCE_CUBE WHERE FINANCE_CUBE_ID = ?";      
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, new Object[] { financeCubeId });
        BigDecimal modelId = (BigDecimal)(rows.get(0).get("MODEL_ID"));
		return modelId.intValue();
	}
    
    private boolean validateCellRange(CellRange cellRange){
        if (cellRange == null){
            return false;
        }
        String sheetName = cellRange.getSheetName();
        String firstCell = cellRange.getFirstCell();
        String lastCell = cellRange.getLastCell();
        if (sheetName == null || sheetName.isEmpty()){
            return false;
        }
        if (firstCell == null || firstCell.isEmpty()){
            return false;
        }
        if (lastCell == null || lastCell.isEmpty()){
            return false;
        }
        
        return true;
    }
}
