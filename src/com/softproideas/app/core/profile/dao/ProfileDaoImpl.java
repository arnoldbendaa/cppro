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
/**
 * 
 */
package com.softproideas.app.core.profile.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDetailsDTO;
//import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDetailsDTO;//arnold
import com.softproideas.app.core.profile.mapper.ProfileMapper;
import com.softproideas.app.core.profile.model.UserIdXmlForm;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.commons.util.MapperUtil;

import cppro.conn.ConnectionUtils;
import cppro.utils.DBUtils;

/**
 * <p>
 * TODO: Comment me
 * </p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 *        <p>
 *        2015 All rights reserved to IT Services Jacek Kurasiewicz
 *        </p>
 */
@Repository("profileDao")
public class ProfileDaoImpl extends JdbcDaoSupport implements ProfileDao {

    @Autowired
    @Qualifier("cpDataSource")
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public void deleteProfilesForForm(final int flatFormId, final List<Integer> userIdList, boolean isInUserTable) {
        String userIdsForQuery = userIdList.toString();
        String str;
        if (isInUserTable) {
            str = "IN";
        } else {
            str = "NOT IN";
        }
        userIdsForQuery = userIdsForQuery.substring(1, userIdsForQuery.length() - 1);
        String query = "delete from \"DATA_ENTRY_PROFILE\" where (XMLFORM_ID = ? AND \"USER_ID\" " + str + "(" + userIdsForQuery + "))";
        DBUtils.getJDBCTempate().update(query, flatFormId);
    }

    public void insertProfileForForm(int flatFormId, List<Integer> userIdList, boolean isInUserTable) {
        if (userIdList.size() > 0) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("userIdList", userIdList);
            parameters.addValue("flatFormId", flatFormId);
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(DBUtils.getJDBCTempate().getDataSource());
            String str;
            if (isInUserTable) {
                str = "IN";
            } else {
                str = "NOT IN";
            }
            // @formatter:off
			String getDataForProfiles = ""
					+ "SELECT DISTINCT XML_FORM.VIS_ID     AS visId, "
					+ "  XML_FORM.DESCRIPTION              AS description, "
					+ "  FINANCE_CUBE.MODEL_ID             AS modelId, "
					+ "  BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID AS budgetCycleId, "
					+ "  BUDGET_USER.USER_ID               AS userId "
					+ "FROM XML_FORM "
					+ "JOIN FINANCE_CUBE "
					+ "ON XML_FORM.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID "
					+ "JOIN BUDGET_CYCLE_LINK "
					+ "ON XML_FORM.XML_FORM_ID = BUDGET_CYCLE_LINK.XML_FORM_ID "
					+ "JOIN BUDGET_USER "
					+ "ON BUDGET_USER.MODEL_ID    = FINANCE_CUBE.MODEL_ID "
					+ "AND BUDGET_USER.USER_ID " + str + "(:userIdList) "
					+ "WHERE XML_FORM.XML_FORM_ID = :flatFormId";
			// @formatter:on

            Map<String, Object> idStm = DBUtils.getJDBCTempate().queryForMap("SELECT MAX(DATA_ENTRY_PROFILE_ID) AS maxId FROM DATA_ENTRY_PROFILE");
            List<Map<String, Object>> dataStm = namedParameterJdbcTemplate.queryForList(getDataForProfiles, parameters.getValues());

            int pk = MapperUtil.mapBigDecimal(idStm.get("maxId"));
            final int npk = pk + 1;

            String dataEntryProfileVisId = "";
            String dataEntryProfileDescription = "";
            int modelId = 0;
            int budgetCycleId = 0;
            List<Integer> userIdListWithAccess = new ArrayList<Integer>();

            if (dataStm.size() > 0) {
                dataEntryProfileVisId = (String) dataStm.get(0).get("visId");
                dataEntryProfileVisId = ProfileMapper.mapFormNameToProfileName(dataEntryProfileVisId);
                dataEntryProfileDescription = (String) dataStm.get(0).get("description");
                modelId = MapperUtil.mapBigDecimal(dataStm.get(0).get("modelId"));
                budgetCycleId = MapperUtil.mapBigDecimal(dataStm.get(0).get("budgetCycleId"));
                for (int i = 0; i < dataStm.size(); i++) {
                    if (MapperUtil.mapBigDecimal(dataStm.get(i).get("budgetCycleId")) == budgetCycleId) {
                        userIdListWithAccess.add(MapperUtil.mapBigDecimal(dataStm.get(i).get("userId")));
                    }
                }
                insertBatchProfile(npk, dataEntryProfileVisId, userIdListWithAccess, modelId, dataEntryProfileDescription, flatFormId, budgetCycleId);
            }
        }
    }

    public void insertBatchProfile(final int npk, final String dataEntryProfileVisId, final List<Integer> userIdList, final int modelId, final String dataEntryProfileDescription, final int flatFormId, final int budgetCycleId) {
        String query = "insert into DATA_ENTRY_PROFILE (DATA_ENTRY_PROFILE_ID, VIS_ID, USER_ID, MODEL_ID, DESCRIPTION, XMLFORM_ID, UPDATED_TIME, CREATED_TIME, BUDGET_CYCLE_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DBUtils.getJDBCTempate().batchUpdate(query, new BatchPreparedStatementSetter() {
            Calendar calendar = Calendar.getInstance();
            final Date now = calendar.getTime();
            java.sql.Timestamp nowTime = new java.sql.Timestamp(now.getTime());

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Integer userId = userIdList.get(i);
                ps.setInt(1, npk + i);
                ps.setString(2, dataEntryProfileVisId);
                ps.setInt(3, userId);
                ps.setInt(4, modelId);
                ps.setString(5, dataEntryProfileDescription);
                ps.setInt(6, flatFormId);
                ps.setTimestamp(7, nowTime);
                ps.setTimestamp(8, nowTime);
                ps.setInt(9, budgetCycleId);
            }

            @Override
            public int getBatchSize() {
                return userIdList.size();
            }

        });
    }

    @Override
    public void deleteProfiles(List<Integer> modelId, List<Integer> userIds) throws DaoException {

        String userIdsForQuery = userIds.toString();
        String userModelsForQuery = modelId.toString();
        userIdsForQuery = userIdsForQuery.substring(1, userIdsForQuery.length() - 1);
        userModelsForQuery = userModelsForQuery.substring(1, userModelsForQuery.length() - 1);
        if (userIdsForQuery.length() == 0) {
            userIdsForQuery.concat("-1");
        }
        String query = "delete from \"DATA_ENTRY_PROFILE\" where (\"MODEL_ID\" IN (" + userModelsForQuery + ") and \"USER_ID\" IN (" + userIdsForQuery + "))";
        try {
            DBUtils.getJDBCTempate().update(query);
        } catch (DataAccessException e) {
            String errorMsg = "Error while executing delete query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    // returning list of ids of xml forms for users from BUDGET_USER but not in
    // DATA_ENTRY_PROFILE
    @Override
    public List<UserIdXmlForm> getXmlFormsForCreateMissingProfiles(Integer modelId, Integer userId) throws DaoException {
        // @formatter:off
		String query = ""
				+ "WITH xmlformsids AS "
				+ "  ( SELECT DISTINCT xful.XML_FORM_ID "
				+ "  FROM XML_FORM_USER_LINK xful "
				+ "  LEFT JOIN XML_FORM xmlf "
				+ "  ON (xful.XML_FORM_ID = xmlf.XML_FORM_ID) "
				+ "  LEFT JOIN FINANCE_CUBE fc "
				+ "  ON (xmlf.FINANCE_CUBE_ID = fc.FINANCE_CUBE_ID) "
				+ "  WHERE fc.MODEL_ID        = ? "
				+ "  AND XFUL.USER_ID         = ? "
				+ "  UNION "
				+ "    (SELECT xml_form_id "
				+ "    FROM XML_FORM "
				+ "    WHERE xml_form_id NOT IN "
				+ "      ( SELECT DISTINCT xml_form_id FROM XML_FORM_USER_LINK "
				+ "      ) " + "    ) " + "  MINUS "
				+ "    (SELECT xmlform_id AS xml_form_id "
				+ "    FROM data_entry_profile " + "    WHERE user_id = ? "
				+ "    AND model_id  = ? " + "    ) " + "  ), "
				+ "  unique_bcl AS " + "  (SELECT XML_FORM_ID, "
				+ "    MAX(BUDGET_CYCLE_ID) AS BUDGET_CYCLE_ID "
				+ "  FROM BUDGET_CYCLE_LINK " + "  GROUP BY XML_FORM_ID "
				+ "  ) " + "SELECT xmlformsids.xml_form_id, "
				+ "  XML_FORM.vis_id, " + "  XML_FORM.DESCRIPTION, "
				+ "  unique_bcl.BUDGET_CYCLE_ID " + "FROM xmlformsids "
				+ "LEFT JOIN XML_FORM "
				+ "ON (xml_form.XML_FORM_ID = xmlformsids.XML_FORM_ID) "
				+ "LEFT JOIN unique_bcl "
				+ "ON (unique_bcl.XML_FORM_ID = xml_form.XML_FORM_ID) "
				+ "WHERE unique_bcl.BUDGET_CYCLE_ID IS NOT NULL";
		// @formatter:on

        try {
            List<Map<String, Object>> rows = DBUtils.getJDBCTempate().queryForList(query, modelId, userId, userId, modelId);
            List<UserIdXmlForm> usersXmlForms = ProfileMapper.mapXmlFormsIds(rows, userId);
            return usersXmlForms;
        } catch (DataAccessException e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public void createProfilesByModel(final List<UserIdXmlForm> userIdXmlForms, final Integer modelId) throws SQLException {
        // @formatter:off
		String query = ""
				+ "insert into DATA_ENTRY_PROFILE "
				+ "(DATA_ENTRY_PROFILE_ID, VIS_ID, USER_ID, MODEL_ID, DESCRIPTION, XMLFORM_ID, UPDATED_TIME, CREATED_TIME, MOBILE, BUDGET_CYCLE_ID)"
				+ " values (?,?,?,?,?,?,?,?,?,?)";
		// @formatter:on
        PreparedStatement pkStm = null;
        Connection conn = null;
		try {
			conn = ConnectionUtils.getConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        Exception exception = null;

        int pk = 0;
        try {
            pkStm = conn.prepareStatement("SELECT MAX(DATA_ENTRY_PROFILE_ID) FROM DATA_ENTRY_PROFILE");
            ResultSet resultSet = pkStm.executeQuery();

            while (resultSet.next()) {
                pk = resultSet.getInt(1);
            }

            pkStm.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            exception = e;
        } finally {
            try {
                if (exception != null) {
                    exception.printStackTrace();
                }
                conn.close();
            } catch (Exception e) {
                if (exception != null) {
                    exception.printStackTrace();
                }
                e.printStackTrace();
            }
        }
        conn.close();
        final int npk = pk + 1;

        DBUtils.getJDBCTempate().batchUpdate(query, new BatchPreparedStatementSetter() {
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            java.sql.Timestamp nowTime = new java.sql.Timestamp(now.getTime());

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UserIdXmlForm userIdXmlForm = userIdXmlForms.get(i);
                ps.setInt(1, npk + i);
                ps.setString(2, userIdXmlForm.getVisId());
                ps.setInt(3, userIdXmlForm.getUserId());
                ps.setInt(4, modelId);
                ps.setString(5, userIdXmlForm.getDescription());
                ps.setInt(6, userIdXmlForm.getXmlFormId());
                ps.setTimestamp(7, nowTime);
                ps.setTimestamp(8, nowTime);
                ps.setString(9, "N");
                ps.setInt(10, userIdXmlForm.getBudgetCycleId());
            }

            @Override
            public int getBatchSize() {
                return userIdXmlForms.size();
            }
        });

    }

    /* (non-Javadoc)
     * 
     * @see
     * com.softproideas.app.core.profile.dao.ProfileDao#deleteProfilesForUser
     * (int, java.util.List) */
    @Override
    public void deleteProfilesForUser(final List<Integer> flatFormIdList, final int userId) throws DaoException {
        String query = "delete from DATA_ENTRY_PROFILE where XMLFORM_ID = ? AND USER_ID = ?";

        DBUtils.getJDBCTempate().batchUpdate(query, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Integer flatFormId = flatFormIdList.get(i);
                ps.setInt(1, flatFormId);
                ps.setInt(2, userId);
            }

            @Override
            public int getBatchSize() {
                return flatFormIdList.size();
            }

        });
    }

    /* (non-Javadoc)
     * 
     * @see
     * com.softproideas.app.core.profile.dao.ProfileDao#insertProfileForUser
     * (int, java.util.List) */
    @Override
    public void insertProfileForUser(List<Integer> flatFormIdList, int userId, List<Integer> modelsIdsFlatFormsInsertedBefore) throws DaoException {
        if (flatFormIdList.size() > 0) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            
            int listLength = flatFormIdList.size();
            int paramSize = (int) Math.ceil(listLength/999.0);
            for(int i = 0 ; i < paramSize;i++){
            	parameters.addValue("flatFormIdList"+String.valueOf(i+1), flatFormIdList.subList(i*999, Math.min(listLength,(i+1)*999-1)));
            }
            
            
//            parameters.addValue("flatFormIdList1", flatFormIdList.subList(0, Math.min(flatFormIdList.size(), 998)));
            parameters.addValue("userId", userId);
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(DBUtils.getJDBCTempate().getDataSource());

            // @formatter:off
			String getDataForProfiles = ""
					+ "SELECT DISTINCT XML_FORM.VIS_ID AS visId, "
					+ "  XML_FORM.DESCRIPTION          AS description, "
					+ "  FINANCE_CUBE.MODEL_ID         AS modelId, "
					+ "  BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID, "
					+ "  XML_FORM.XML_FORM_ID "
					+ "FROM XML_FORM "
					+ "JOIN FINANCE_CUBE "
					+ "ON XML_FORM.FINANCE_CUBE_ID = FINANCE_CUBE.FINANCE_CUBE_ID "
					+ "JOIN BUDGET_CYCLE_LINK "
					+ "ON BUDGET_CYCLE_LINK.XML_FORM_ID                                        = XML_FORM.XML_FORM_ID "
					+ "AND (BUDGET_CYCLE_LINK.XML_FORM_ID, BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID) IN "
					+ "  (SELECT BUDGET_CYCLE_LINK.XML_FORM_ID, "
					+ "    MAX(BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID) "
					+ "  FROM BUDGET_CYCLE_LINK "
					+ "  GROUP BY BUDGET_CYCLE_LINK.XML_FORM_ID " + "  ) "
					+ "JOIN BUDGET_USER " + "ON BUDGET_USER.USER_ID = :userId "
					+ "AND BUDGET_USER.MODEL_ID = FINANCE_CUBE.MODEL_ID "
					+ "WHERE XML_FORM.XML_FORM_ID IN (:flatFormIdList1) ";
			// @formatter:on
			String sql = " ";
			if(paramSize==2)
				sql = "or XML_FORM.XML_FORM_ID IN  (:flatFormIdList2)";
			else if(paramSize==3)
				sql = "or XML_FORM.XML_FORM_ID IN  (:flatFormIdList2) or XML_FORM.XML_FORM_ID IN  (:flatFormIdList3)";
			else if(paramSize==4)
				sql = "or XML_FORM.XML_FORM_ID IN  (:flatFormIdList2) or XML_FORM.XML_FORM_ID IN  (:flatFormIdList3) or XML_FORM.XML_FORM_ID IN  (:flatFormIdList4)";
			else if(paramSize>4){
	            for(int i = 4 ; i < paramSize;i++){
	            	sql = sql +" or XML_FORM.XML_FORM_ID IN (:flatformIdList"+String.valueOf(i+1)+")";
	            }
			}
            getDataForProfiles = getDataForProfiles + sql;
            Map<String, Object> idStm = DBUtils.getJDBCTempate().queryForMap("SELECT MAX(DATA_ENTRY_PROFILE_ID) AS maxId FROM DATA_ENTRY_PROFILE");
            List<Map<String, Object>> dataStm = namedParameterJdbcTemplate.queryForList(getDataForProfiles, parameters.getValues());

            int pk = MapperUtil.mapBigDecimal(idStm.get("maxId"));
            final int npk = pk + 1;

            List<Integer> flatFormIdFlatFormsHavingBudgetCycleList = new ArrayList<Integer>();
            String dataEntryProfileVisId;
            List<String> dataEntryProfileVisIdList = new ArrayList<String>();
            List<String> dataEntryProfileDescriptionList = new ArrayList<String>();
            Integer modelId;
            List<Integer> modelIdList = new ArrayList<Integer>();
            List<Integer> budgetCycleIdList = new ArrayList<Integer>();
            boolean isNotInserted = true;

            for (int i = 0; i < dataStm.size(); i++) {
                modelId = MapperUtil.mapBigDecimal(dataStm.get(i).get("modelId"));
                for (int j = 0; j < modelsIdsFlatFormsInsertedBefore.size(); j++) {
                    if (modelId == modelsIdsFlatFormsInsertedBefore.get(j).intValue()) {
                        isNotInserted = false;
                        j = modelsIdsFlatFormsInsertedBefore.size();
                    }
                }
                if (isNotInserted == true) {
                    flatFormIdFlatFormsHavingBudgetCycleList.add(MapperUtil.mapBigDecimal(dataStm.get(i).get("xml_form_id")));
                    dataEntryProfileVisId = (String) dataStm.get(i).get("visId");
                    dataEntryProfileVisIdList.add(ProfileMapper.mapFormNameToProfileName(dataEntryProfileVisId));
                    dataEntryProfileDescriptionList.add((String) dataStm.get(i).get("description"));
                    modelIdList.add(modelId);
                    budgetCycleIdList.add(MapperUtil.mapBigDecimal(dataStm.get(i).get("budget_cycle_id")));
                } else {
                    isNotInserted = true;
                }
            }
            if (dataStm.size() > 0) {
                insertBatchProfileForUser(npk, dataEntryProfileVisIdList, userId, modelIdList, dataEntryProfileDescriptionList, flatFormIdFlatFormsHavingBudgetCycleList, budgetCycleIdList);
            }
        }
    }

    public void insertBatchProfileForUser(final int npk, final List<String> dataEntryProfileVisIdList, final int userId, final List<Integer> modelIdList, final List<String> dataEntryProfileDescriptionList, final List<Integer> flatFormIdFlatFormsHavingBudgetCycleList, final List<Integer> budgetCycleIdList) {
        String query = "insert into DATA_ENTRY_PROFILE (DATA_ENTRY_PROFILE_ID, VIS_ID, USER_ID, MODEL_ID, DESCRIPTION, XMLFORM_ID, UPDATED_TIME, CREATED_TIME, BUDGET_CYCLE_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DBUtils.getJDBCTempate().batchUpdate(query, new BatchPreparedStatementSetter() {
            Calendar calendar = Calendar.getInstance();
            final Date now = calendar.getTime();
            java.sql.Timestamp nowTime = new java.sql.Timestamp(now.getTime());

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Integer flatFormId = flatFormIdFlatFormsHavingBudgetCycleList.get(i);
                ps.setInt(1, npk + i);
                ps.setString(2, dataEntryProfileVisIdList.get(i));
                ps.setInt(3, userId);
                ps.setInt(4, modelIdList.get(i));
                ps.setString(5, dataEntryProfileDescriptionList.get(i));
                ps.setInt(6, flatFormId);
                ps.setTimestamp(7, nowTime);
                ps.setTimestamp(8, nowTime);
                ps.setInt(9, budgetCycleIdList.get(i));
            }

            @Override
            public int getBatchSize() {
                return flatFormIdFlatFormsHavingBudgetCycleList.size();
            }

        });
    }

    /* (non-Javadoc)
     * 
     * @see
     * com.softproideas.app.core.profile.dao.ProfileDao#deleteProfilesForBudgetCycle
     * (java.util.List, int) */
    @Override
    public void deleteProfilesForBudgetCycle(final List<Integer> flatFormIdList, int budgetCycleId) throws DaoException {
        if (flatFormIdList.size() > 0) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("flatFormIdList", flatFormIdList);
            parameters.addValue("budgetCycleId", budgetCycleId);
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(DBUtils.getJDBCTempate().getDataSource());

            // @formatter:off
			String getDataForProfiles = ""
					+ "DELETE FROM DATA_ENTRY_PROFILE WHERE BUDGET_CYCLE_ID = :budgetCycleId AND XMLFORM_ID IN (:flatFormIdList)";
			// @formatter:on

            namedParameterJdbcTemplate.update(getDataForProfiles, parameters.getValues());
        }
    }

    /* (non-Javadoc)
     * 
     * @see
     * com.softproideas.app.core.profile.dao.ProfileDao#insertProfileForBudgetCycle
     * (java.util.List, int, java.util.List) */
    @Override
    public void insertProfileForBudgetCycle(List<Integer> flatFormIdList, BudgetCycleDetailsDTO budgetCycle) throws DaoException {
        if (flatFormIdList.size() > 0) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("flatFormIdList", flatFormIdList);
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(DBUtils.getJDBCTempate().getDataSource());

            // @formatter:off
			String getDataUsersForProfiles = ""
					+ "SELECT DISTINCT BUDGET_USER.USER_ID AS userId, "
					+ "  XML_FORM.XML_FORM_ID AS xmlFormId "
					+ "FROM BUDGET_USER "
					+ "INNER JOIN FINANCE_CUBE "
					+ "ON BUDGET_USER.MODEL_ID = FINANCE_CUBE.MODEL_ID "
					+ "INNER JOIN XML_FORM "
					+ "ON FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID "
					+ "AND XML_FORM.XML_FORM_ID       IN (:flatFormIdList) "
					+ "INNER JOIN XML_FORM_USER_LINK "
					+ "ON XML_FORM_USER_LINK.XML_FORM_ID = XML_FORM.XML_FORM_ID "
					+ "AND XML_FORM_USER_LINK.USER_ID    = BUDGET_USER.USER_ID";

			String getDataFlatFormsForProfiles = ""
					+ "SELECT VIS_ID AS visId, " + "  DESCRIPTION "
					+ "FROM XML_FORM "
					+ "WHERE XML_FORM_ID IN (:flatFormIdList)";
			// @formatter:on

            Map<String, Object> idStm = DBUtils.getJDBCTempate().queryForMap("SELECT MAX(DATA_ENTRY_PROFILE_ID) AS maxId FROM DATA_ENTRY_PROFILE");
            List<Map<String, Object>> dataUsersStm = namedParameterJdbcTemplate.queryForList(getDataUsersForProfiles, parameters.getValues());
            List<Map<String, Object>> dataFlatFormsStm = namedParameterJdbcTemplate.queryForList(getDataFlatFormsForProfiles, parameters.getValues());

            int pk = MapperUtil.mapBigDecimal(idStm.get("maxId"));
            final int npk = pk + 1;

            int flatFormId;
            String flatFormVisId;
            String dataEntryProfileVisId;
            String dataEntryProfileDescription;
            List<UserFlatFormLink> userFlatFormLinkList = new ArrayList<UserFlatFormLink>();
            List<UserFlatFormDataToRecordProfile> userFlatFormDataToRecordProfileList = new ArrayList<UserFlatFormDataToRecordProfile>();

            for (int i = 0; i < dataUsersStm.size(); i++) {
                UserFlatFormLink userFlatFormLink = new UserFlatFormLink();
                userFlatFormLink.setUserId(MapperUtil.mapBigDecimal(dataUsersStm.get(i).get("userId")));
                userFlatFormLink.setFlatFormId(MapperUtil.mapBigDecimal(dataUsersStm.get(i).get("xmlFormId")));
                userFlatFormLinkList.add(userFlatFormLink);
            }
            for (int i = 0; i < dataFlatFormsStm.size(); i++) {
                flatFormId = MapperUtil.mapBigDecimal(dataUsersStm.get(i).get("xmlFormId"));
                flatFormVisId = (String) dataFlatFormsStm.get(i).get("visId");
                dataEntryProfileVisId = ProfileMapper.mapFormNameToProfileName(flatFormVisId);
                dataEntryProfileDescription = (String) dataFlatFormsStm.get(i).get("description");
                for (int j = 0; j < userFlatFormLinkList.size(); j++) {
                    if (userFlatFormLinkList.get(j).getFlatFormId() == flatFormId) {
                        UserFlatFormDataToRecordProfile userFlatFormDataToRecordProfile = new UserFlatFormDataToRecordProfile();
                        userFlatFormDataToRecordProfile.setUserId(userFlatFormLinkList.get(j).getUserId());
                        userFlatFormDataToRecordProfile.setFlatFormId(flatFormId);
                        userFlatFormDataToRecordProfile.setFlatFormVisId(dataEntryProfileVisId);
                        userFlatFormDataToRecordProfile.setFlatFormDescription(dataEntryProfileDescription);
                        userFlatFormDataToRecordProfileList.add(userFlatFormDataToRecordProfile);
                    }
                }
            }
            if (dataUsersStm.size() > 0 && dataFlatFormsStm.size() > 0) {
                insertBatchProfileForBudgetCycle(npk, userFlatFormDataToRecordProfileList, budgetCycle.getModel().getModelId(), budgetCycle.getBudgetCycleId());
            }
        }
    }

    public void insertBatchProfileForBudgetCycle(final int npk, final List<UserFlatFormDataToRecordProfile> userFlatFormDataToRecordProfileList, final int modelId, final int budgetCycleId) {
        String query = "insert into DATA_ENTRY_PROFILE (DATA_ENTRY_PROFILE_ID, VIS_ID, USER_ID, MODEL_ID, DESCRIPTION, XMLFORM_ID, UPDATED_TIME, CREATED_TIME, BUDGET_CYCLE_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DBUtils.getJDBCTempate().batchUpdate(query, new BatchPreparedStatementSetter() {
            Calendar calendar = Calendar.getInstance();
            final Date now = calendar.getTime();
            java.sql.Timestamp nowTime = new java.sql.Timestamp(now.getTime());

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, npk + i);
                ps.setString(2, userFlatFormDataToRecordProfileList.get(i).getFlatFormVisId());
                ps.setInt(3, userFlatFormDataToRecordProfileList.get(i).getUserId());
                ps.setInt(4, modelId);
                ps.setString(5, userFlatFormDataToRecordProfileList.get(i).getFlatFormDescription());
                ps.setInt(6, userFlatFormDataToRecordProfileList.get(i).getFlatFormId());
                ps.setTimestamp(7, nowTime);
                ps.setTimestamp(8, nowTime);
                ps.setInt(9, budgetCycleId);
            }

            @Override
            public int getBatchSize() {
                return userFlatFormDataToRecordProfileList.size();
            }

        });
    }

    @Override
    public void deleteAllProfilesForUser(int userId) throws DaoException {
        String query = "delete from DATA_ENTRY_PROFILE where USER_ID = ?";
        DBUtils.getJDBCTempate().update(query, userId);
    }

    @Override
    public void deleteAllProfilesForBudgetCycle(int budgetCycleId) throws DaoException {
        String query = "delete from DATA_ENTRY_PROFILE where BUDGET_CYCLE_ID = ?";
        DBUtils.getJDBCTempate().update(query, budgetCycleId);
    }

}
