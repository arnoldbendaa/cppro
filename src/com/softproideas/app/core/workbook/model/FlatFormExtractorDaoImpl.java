package com.softproideas.app.core.workbook.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("flatFormExtractorDao")
public class FlatFormExtractorDaoImpl extends JdbcDaoSupport implements FlatFormExtractorDao {

    @Autowired
    @Qualifier("cpDataSource")
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public Map<String, String> getPropertiesForModelId(String modelId){
      //@formatter:off
    	String sql = ""
    			+ "SELECT 0 \"DIM_ID\", DIM.VIS_ID \"DIM_VIS_ID\", HIER.VIS_ID \"HIER_VIS_ID\", FC.FINANCE_CUBE_ID, FC.VIS_ID \"FC_VIS_ID\", MO.MODEL_ID, MO.VIS_ID \"MODEL_VIS_ID\" "
    			+ "FROM DIMENSION DIM "
    			+ "JOIN HIERARCHY HIER ON (HIER.DIMENSION_ID = DIM.DIMENSION_ID) "
    			+ "JOIN MODEL MO ON (MO.BUDGET_HIERARCHY_ID = HIER.HIERARCHY_ID) "
    			+ "JOIN FINANCE_CUBE FC ON (FC.MODEL_ID = MO.MODEL_ID) "
    			+ "WHERE MO.MODEL_ID = ? "
    			+ "UNION ALL "
    			+ "SELECT 1 \"DIM_ID\", DIM.VIS_ID \"DIM_VIS_ID\", HIER.VIS_ID \"HIER_VIS_ID\", FC.FINANCE_CUBE_ID, FC.VIS_ID \"FC_VIS_ID\", MO.MODEL_ID, MO.VIS_ID \"MODEL_VIS_ID\" "
    			+ "FROM DIMENSION DIM "
    			+ "JOIN HIERARCHY HIER ON (HIER.DIMENSION_ID = DIM.DIMENSION_ID) "
    			+ "JOIN MODEL MO ON (MO.ACCOUNT_ID = DIM.DIMENSION_ID) "
    			+ "JOIN FINANCE_CUBE FC ON (FC.MODEL_ID = MO.MODEL_ID) "
    			+ "WHERE MO.MODEL_ID = ? "
    			+ "UNION ALL "
    			+ "SELECT 2 \"DIM_ID\",  DIM.VIS_ID \"DIM_VIS_ID\", HIER.VIS_ID \"HIER_VIS_ID\", FC.FINANCE_CUBE_ID, FC.VIS_ID \"FC_VIS_ID\", MO.MODEL_ID, MO.VIS_ID \"MODEL_VIS_ID\" "
    			+ "FROM DIMENSION DIM "
    			+ "JOIN HIERARCHY HIER ON (HIER.DIMENSION_ID = DIM.DIMENSION_ID) "
    			+ "JOIN MODEL MO ON (MO.CALENDAR_ID = DIM.DIMENSION_ID) "
    			+ "JOIN FINANCE_CUBE FC ON (FC.MODEL_ID = MO.MODEL_ID) "
    			+ "WHERE MO.MODEL_ID = ?";
    	//@formatter:on
    	List<Map<String, Object>> queryForList = getJdbcTemplate().queryForList(sql, modelId);
    	Map<String, String> map = FlatFormExtractorMapper.map(queryForList);
    	return map;
    }

    @Override
    public Map<String, String> getPropertiesForModelVisId(String modelVisId) {
        //@formatter:off
    	String sql = ""
    	        + "SELECT 0 \"DIM_ID\", DIM.VIS_ID \"DIM_VIS_ID\", HIER.VIS_ID \"HIER_VIS_ID\", FC.FINANCE_CUBE_ID, FC.VIS_ID \"FC_VIS_ID\", MO.MODEL_ID, MO.VIS_ID \"MODEL_VIS_ID\" "
    			+ "FROM DIMENSION DIM "
    			+ "JOIN HIERARCHY HIER ON (HIER.DIMENSION_ID = DIM.DIMENSION_ID) "
    			+ "JOIN MODEL MO ON (MO.BUDGET_HIERARCHY_ID = HIER.HIERARCHY_ID) "
    			+ "JOIN FINANCE_CUBE FC ON (FC.MODEL_ID = MO.MODEL_ID) "
    			+ "WHERE MO.VIS_ID = ? "
    			+ "UNION ALL "
    			+ "SELECT 1 \"DIM_ID\", DIM.VIS_ID \"DIM_VIS_ID\", HIER.VIS_ID \"HIER_VIS_ID\", FC.FINANCE_CUBE_ID, FC.VIS_ID \"FC_VIS_ID\", MO.MODEL_ID, MO.VIS_ID \"MODEL_VIS_ID\" "
    			+ "FROM DIMENSION DIM "
    			+ "JOIN HIERARCHY HIER ON (HIER.DIMENSION_ID = DIM.DIMENSION_ID) "
    			+ "JOIN MODEL MO ON (MO.ACCOUNT_ID = DIM.DIMENSION_ID) "
    			+ "JOIN FINANCE_CUBE FC ON (FC.MODEL_ID = MO.MODEL_ID) "
    			+ "WHERE MO.VIS_ID = ? "
    			+ "UNION ALL "
    			+ "SELECT 2 \"DIM_ID\", DIM.VIS_ID \"DIM_VIS_ID\", HIER.VIS_ID \"HIER_VIS_ID\", FC.FINANCE_CUBE_ID, FC.VIS_ID \"FC_VIS_ID\", MO.MODEL_ID, MO.VIS_ID \"MODEL_VIS_ID\" "
    			+ "FROM DIMENSION DIM "
    			+ "JOIN HIERARCHY HIER ON (HIER.DIMENSION_ID = DIM.DIMENSION_ID) "
    			+ "JOIN MODEL MO ON (MO.CALENDAR_ID = DIM.DIMENSION_ID) "
    			+ "JOIN FINANCE_CUBE FC ON (FC.MODEL_ID = MO.MODEL_ID) "
    			+ "WHERE MO.VIS_ID = ?";
    	//@formatter:on
        List<Map<String, Object>> queryForList = getJdbcTemplate().queryForList(sql, modelVisId, modelVisId, modelVisId);
        Map<String, String> map = FlatFormExtractorMapper.map(queryForList);
        return map;
    }
    
    public void toggleLockFlag(int userId,int xmlFormId, boolean flag){
		char cFlag = flag==true?'Y':'N';

    	String sql = "select count(*) from lock_state where XML_FORM_ID="+xmlFormId;
    	Number number = getJdbcTemplate().queryForObject(sql, Integer.class);
    	int result =  (number != null ? number.intValue() : 0);
    	if(result==0){
    		sql = "insert into lock_state (USER_ID,XML_FORM_ID,LOCKED,LOCK_ENABLE,LOCK_TIME) values("+userId+","+xmlFormId+",'N','"+cFlag+"',CURRENT_TIMESTAMP)";
    		getJdbcTemplate().update(sql);
    	}else if(result>0){
    		//sql = " insert into lock_state (USER_ID,XML_FORM_ID,LOCKED,LOCK_ENABLE) values(?,?,?,?)";
    		sql = "update lock_state set LOCK_ENABLE='"+cFlag+"' where XML_FORM_ID="+xmlFormId;
    		getJdbcTemplate().update(sql);
    	}
    }
    
    public char getLockFlag(int xmlFormId){
    	char cFlag ='a';
    	String sql = "select lock_enable from lock_state where xml_form_id = ?";
    	List<Map<String, Object>> queryForList = getJdbcTemplate().queryForList(sql,xmlFormId);
    	Map<String, String> properties = new HashMap<String, String>();
        for (Map<String, Object> record: queryForList) {
        	String temp = (String) record.get("LOCK_ENABLE");
        	cFlag = temp.charAt(0);
        }
//    	char result = getJdbcTemplate().queryForObject(sql, char.class);
    	return cFlag;
    }
}
