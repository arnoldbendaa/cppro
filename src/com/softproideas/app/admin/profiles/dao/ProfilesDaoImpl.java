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
package com.softproideas.app.admin.profiles.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.softproideas.app.admin.profiles.mapper.ProfilesMapper;
import com.softproideas.app.admin.profiles.model.MobileProfileDTO;
import com.softproideas.app.admin.profiles.model.ProfileDTO;
import com.softproideas.app.admin.profiles.model.ProfileDetailsDTO;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.models.FormDeploymentDataDTO;
import com.softproideas.common.models.StructureElementCoreDTO;
import com.softproideas.commons.model.ResponseMessage;

@Repository("profilesDao")
public class ProfilesDaoImpl extends JdbcDaoSupport implements ProfilesDao {

    @Autowired
    @Qualifier("cpDataSource")
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private static Logger logger = LoggerFactory.getLogger(ProfilesDaoImpl.class);
    private static final String BROWSE_PROFILES = "select dep.DATA_ENTRY_PROFILE_ID, dep.VIS_ID "
            + "as DATA_ENTRY_VIS_ID, dep.XMLFORM_ID, dep.UPDATED_TIME, dep.DESCRIPTION, m.MODEL_ID, "
            + "m.VIS_ID, u.NAME, u.USER_ID from DATA_ENTRY_PROFILE dep LEFT JOIN MODEL m ON (dep.MODEL_ID=m.MODEL_ID) "
            + "LEFT JOIN USR u ON (dep.USER_ID=u.USER_ID)";
    private static final String BROWSE_PAGE_WEB_PROFILES = "SELECT * FROM (SELECT ROW_NUMBER() OVER(order by dep.UPDATED_TIME DESC) AS rn, dep.DATA_ENTRY_PROFILE_ID, dep.VIS_ID as DATA_ENTRY_VIS_ID, dep.XMLFORM_ID, dep.UPDATED_TIME, dep.DESCRIPTION, m.MODEL_ID, m.VIS_ID, u.NAME, u.USER_ID from DATA_ENTRY_PROFILE dep LEFT JOIN MODEL m ON (dep.MODEL_ID=m.MODEL_ID) LEFT JOIN USR u ON (dep.USER_ID=u.USER_ID) WHERE dep.MOBILE='N') WHERE rn > ? * ? AND rn <= ? * (? + 1) and model_id>0 and user_id>0";
    private static final String BROWSE_PAGE_MOBILE_PROFILES = "SELECT * FROM (SELECT ROW_NUMBER() OVER(order by dep.UPDATED_TIME DESC) AS rn, dep.DATA_ENTRY_PROFILE_ID, dep.VIS_ID as DATA_ENTRY_VIS_ID, dep.XMLFORM_ID, dep.UPDATED_TIME, dep.DESCRIPTION, m.MODEL_ID, m.VIS_ID, u.NAME, u.USER_ID from DATA_ENTRY_PROFILE dep LEFT JOIN MODEL m ON (dep.MODEL_ID=m.MODEL_ID) LEFT JOIN USR u ON (dep.USER_ID=u.USER_ID) WHERE dep.MOBILE='Y') WHERE rn > ? * ? AND rn <= ? * (? + 1) and model_id>0 and user_id>0";
    private static final String BROWSE_PROFILE_DETAILS = "SELECT * FROM DATA_ENTRY_PROFILE where DATA_ENTRY_PROFILE_ID = ?";

    @Override
    public List<ProfileDTO> browseMobileProfiles() throws DaoException {
        String query = BROWSE_PROFILES + " where dep.MOBILE='Y'";

        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
            return ProfilesMapper.mapProfiles(rows);
        } 
        catch(EmptyResultDataAccessException e){
            return ProfilesMapper.mapProfiles(new ArrayList<Map<String, Object>>());
        }
        catch(DataAccessException e){
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }
    
    @Override
    public List<ProfileDTO> browseWebProfiles() throws DaoException {
        String query = BROWSE_PROFILES + " where dep.MOBILE='N'";

        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
            return ProfilesMapper.mapProfiles(rows);
        }
        catch(EmptyResultDataAccessException e){
            return ProfilesMapper.mapProfiles(new ArrayList<Map<String, Object>>());
        }
        catch(DataAccessException e){
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public List<ProfileDTO> browsePageMobileProfiles(int page, int offset) throws DaoException {
        String query = BROWSE_PAGE_MOBILE_PROFILES;

        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, offset, page, offset, page);
            return ProfilesMapper.mapProfiles(rows);
        }
        catch(EmptyResultDataAccessException e){
            return ProfilesMapper.mapProfiles(new ArrayList<Map<String, Object>>());
        }
        catch(DataAccessException e){
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }
    
    @Override
    public List<ProfileDTO> browsePageWebProfiles(int page, int offset) throws DaoException {
        String query = BROWSE_PAGE_WEB_PROFILES;

        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, offset, page, offset, page);
            return ProfilesMapper.mapProfiles(rows);
        } 
        catch(EmptyResultDataAccessException e){
            return ProfilesMapper.mapProfiles(new ArrayList<Map<String, Object>>());
        }
        catch(DataAccessException e){
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public List<ProfileDTO> browseMobileProfiles(int userId) throws DaoException {

        String query = BROWSE_PROFILES + " where dep.MOBILE='Y' and u.USER_ID=?";
        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, userId);
            return ProfilesMapper.mapProfiles(rows);
        }
        catch(EmptyResultDataAccessException e){
            return ProfilesMapper.mapProfiles(new ArrayList<Map<String, Object>>());
        }
        catch(DataAccessException e){
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }

    }
    @Override
    public ProfileDetailsDTO getProfile(int profileId) throws DaoException {

        String query = "SELECT "
                + "dep.DATA_ENTRY_PROFILE_ID, dep.VIS_ID, dep.DESCRIPTION, "
                + "dep.XMLFORM_ID, dep.USER_ID, dep.MOBILE, "
                + "dep.ELEMENT_LABEL0, dep.ELEMENT_LABEL1, dep.ELEMENT_LABEL2, "
                + "dep.STRUCTURE_ELEMENT_ID0, dep.STRUCTURE_ELEMENT_ID1, dep.STRUCTURE_ELEMENT_ID2, "
                + "dep.STRUCTURE_ID0, dep.STRUCTURE_ID1, dep.STRUCTURE_ID2, "
                + "dep.BUDGET_CYCLE_ID, dep.MODEL_ID, dep.DATA_TYPE "
                + "FROM "
                + "DATA_ENTRY_PROFILE dep "
                + "WHERE "
                + "DATA_ENTRY_PROFILE_ID=?";
        try {
            Map<String, Object> result = getJdbcTemplate().queryForMap(query, profileId);
            ProfileDetailsDTO profileDetailsDTO = ProfilesMapper.mapProfileDetails(result);
            return profileDetailsDTO;
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
        catch(DataAccessException e){
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }
    
    @Override
    public List<MobileProfileDTO> getProfilesForUser(int userId) throws DaoException {

        String query =  ""
                + "SELECT dep.DATA_ENTRY_PROFILE_ID, "
                + "  dep.VIS_ID, "
                + "  dep.DESCRIPTION, "
                + "  dep.ELEMENT_LABEL0, "
                + "  dep.ELEMENT_LABEL1, "
                + "  dep.ELEMENT_LABEL2, "
                + "  dep.STRUCTURE_ELEMENT_ID0, "
                + "  dep.STRUCTURE_ELEMENT_ID1, "
                + "  dep.STRUCTURE_ELEMENT_ID2, "
                + "  dep.STRUCTURE_ID0, "
                + "  dep.STRUCTURE_ID1, "
                + "  dep.STRUCTURE_ID2, "
                + "  dep.DATA_TYPE, "
                + "  dep.UPDATED_TIME, "
                + "  m.VIS_ID AS MODEL_VIS_ID "
                + "FROM DATA_ENTRY_PROFILE dep "
                + "LEFT JOIN MODEL m "
                + "ON (dep.MODEL_ID=m.MODEL_ID) "
                + "LEFT JOIN XML_FORM_USER_LINK xful "
                + "ON (dep.XMLFORM_ID=xful.XML_FORM_ID) "
                + "WHERE dep.USER_ID = ? "
                + "AND dep.MOBILE    ='Y' "
                + "AND dep.USER_ID   = xful.USER_ID "
                + "ORDER BY TO_NUMBER(decode ((REGEXP_SUBSTR(M.VIS_ID, '[^\\/\"]+', 1, 1)), 'global', '1000000',REGEXP_SUBSTR(M.VIS_ID, '[^\\/\"]+', 1, 1))), "
                + "  dep.VIS_ID, "
                + "  dep.ELEMENT_LABEL0";
        try {
            List<Map<String,Object>> result = getJdbcTemplate().queryForList(query, userId);
            return ProfilesMapper.mapProfilesForUser(result);
        }
        catch(EmptyResultDataAccessException e){
            return ProfilesMapper.mapProfilesForUser(new ArrayList<Map<String,Object>>());
        }
        catch(DataAccessException e){
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }

    }

    private PreparedStatementSetter prepareStatementForUpdateSeq(final int insertNumber) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, insertNumber);
            }
        };
        return pss;
    }
    
    @Override
    public int updateProfileSeq() throws DaoException {
        int rowCount = 0;
        try {
            String query = "update USR_SEQ set SEQ_NUM = SEQ_NUM + ?";
            PreparedStatementSetter pss = prepareStatementForUpdateSeq(1);
                getJdbcTemplate().update(query, pss);
                query = "select SEQ_NUM from USR_SEQ";
                List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
                if (rows.size() > 0) {
                    rowCount = ((BigDecimal)rows.get(0).get("SEQ_NUM")).intValue();
                }
                return rowCount - 1;
            } catch (DuplicateKeyException e) {
                String errorMsg = "Error while executing insert query - DUPLICATE ENTRY";
                logger.error(errorMsg, e);
                throw new DaoException(errorMsg, e);
            } catch (Exception e) {
                String errorMsg = "Error while executing insert query";
                logger.error(errorMsg, e);
                throw new DaoException(errorMsg, e);
            }
    };
    
    private PreparedStatementSetter prepareStatementForMobileProfileForUser(final int userId, final FormDeploymentDataDTO profile) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                int col = 1;
                java.util.Date date= new java.util.Date();
                List<StructureElementCoreDTO> structureElements = profile.getStructureElements();
                try {
                    ps.setInt(col++, updateProfileSeq());
                } catch (DaoException e) {
                    e.printStackTrace();
                }
                ps.setString(col++, profile.getIdentifier());
                ps.setInt(col++, userId);
                ps.setInt(col++, profile.getModelId());
                ps.setInt(col++, 0);
                ps.setString(col++, profile.getDescription());
                ps.setInt(col++, profile.getFlatFormId());
                ps.setString(col++, " ");
                ps.setString(col++, " ");
                ps.setString(col++, " ");
                ps.setInt(col++, getStructureId(structureElements.get(0)));
                ps.setInt(col++, getStructureId(structureElements.get(1)));
                ps.setInt(col++, getStructureId(structureElements.get(2)));
                ps.setInt(col++, 0);
                ps.setInt(col++, 0);
                ps.setInt(col++, 0);
                ps.setInt(col++, 0);
                ps.setInt(col++, 0);
                ps.setInt(col++, 0);
                ps.setInt(col++, getStructureElementId(structureElements.get(0)));
                ps.setInt(col++, getStructureElementId(structureElements.get(1)));
                ps.setInt(col++, getStructureElementId(structureElements.get(2)));
                ps.setInt(col++, 0);
                ps.setInt(col++, 0);
                ps.setInt(col++, 0);
                ps.setInt(col++, 0);
                ps.setInt(col++, 0);
                ps.setInt(col++, 0);
                ps.setString(col++, getStructureElementVisId(structureElements.get(0)));
                ps.setString(col++, getStructureElementVisId(structureElements.get(1)));
                ps.setString(col++, getStructureElementVisId(structureElements.get(2)));
                ps.setString(col++, null);
                ps.setString(col++, null);
                ps.setString(col++, null);
                ps.setString(col++, null);
                ps.setString(col++, null);
                ps.setString(col++, null);
                ps.setString(col++, profile.getDataType());
                ps.setInt(col++, 0);
                ps.setInt(col++, 0);
                ps.setTimestamp(col++, new Timestamp(date.getTime()));
                ps.setTimestamp(col++, new Timestamp(date.getTime()));
                ps.setInt(col++, profile.getBudgetCycleId());
                ps.setString(col++, "Y");
            }
        };
        return pss;
    };
    
    private int getStructureId(StructureElementCoreDTO element){
        int value;
        if(element != null){
            value = element.getStructureId();
        } else{
            return 0;
        }
        return value;
    }
    
    
    private int getStructureElementId(StructureElementCoreDTO element){
        int value;
        if(element != null){
            value = element.getStructureElementId();
        } else{
            return 0;
        }
        return value;
    }
    private String getStructureElementVisId(StructureElementCoreDTO element){
        String value;
        if(element != null){
            value = element.getStructureElementVisId();
        } else{
            return null;
        }
        return value;
    }
    @Override
    public boolean insertMobileProfilesForUser(int userId, FormDeploymentDataDTO profile) throws DaoException {
        String query = "insert into DATA_ENTRY_PROFILE (DATA_ENTRY_PROFILE_ID, VIS_ID, USER_ID, MODEL_ID, AUTO_OPEN_DEPTH,DESCRIPTION,XMLFORM_ID,FILL_DISPLAY_AREA,SHOW_BOLD_SUMMARIES,SHOW_HORIZONTAL_LINES,STRUCTURE_ID0,STRUCTURE_ID1,STRUCTURE_ID2,STRUCTURE_ID3,STRUCTURE_ID4,STRUCTURE_ID5,STRUCTURE_ID6,STRUCTURE_ID7,STRUCTURE_ID8,STRUCTURE_ELEMENT_ID0,STRUCTURE_ELEMENT_ID1,STRUCTURE_ELEMENT_ID2,STRUCTURE_ELEMENT_ID3,STRUCTURE_ELEMENT_ID4,STRUCTURE_ELEMENT_ID5,STRUCTURE_ELEMENT_ID6,STRUCTURE_ELEMENT_ID7,STRUCTURE_ELEMENT_ID8,ELEMENT_LABEL0,ELEMENT_LABEL1,ELEMENT_LABEL2,ELEMENT_LABEL3,ELEMENT_LABEL4,ELEMENT_LABEL5,ELEMENT_LABEL6,ELEMENT_LABEL7,ELEMENT_LABEL8,DATA_TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME, BUDGET_CYCLE_ID, MOBILE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatementSetter pss = prepareStatementForMobileProfileForUser(userId, profile);
        try {
            int result = getJdbcTemplate().update(query, pss);
            return result >= 1;
        } catch (DuplicateKeyException e) {
            String errorMsg = "Error while executing insert query - DUPLICATE ENTRY";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        } catch (Exception e) {
            String errorMsg = "Error while executing insert query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public ProfileDetailsDTO getProfileDetails(int profileId) throws DaoException {
        try {
            Map<String,Object> result = getJdbcTemplate().queryForMap(BROWSE_PROFILE_DETAILS, profileId);
            return ProfilesMapper.mapProfileDetails(result);
        }
        catch(EmptyResultDataAccessException e){
            //return ProfilesMapper.mapProfilesForUser(new ArrayList<Map<String,Object>>());
        }
        catch(DataAccessException e){
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
        return null;
    }
    
    private PreparedStatementSetter prepareUpdateStatement(final FormDeploymentDataDTO profile) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                List<StructureElementCoreDTO> structureElements = profile.getStructureElements();
                ps.setInt(1, profile.getBudgetCycleId());
                ps.setString(2, profile.getIdentifier());
                ps.setString(3, profile.getDescription());
                ps.setString(4, profile.getDataType());
                ps.setInt(5, getStructureId(structureElements.get(0)));
                ps.setInt(6, getStructureId(structureElements.get(1)));
                ps.setInt(7, getStructureId(structureElements.get(2)));
                ps.setInt(8, getStructureElementId(structureElements.get(0)));
                ps.setInt(9, getStructureElementId(structureElements.get(1)));
                ps.setInt(10, getStructureElementId(structureElements.get(2)));
                ps.setString(11, getStructureElementVisId(structureElements.get(0)));
                ps.setString(12, getStructureElementVisId(structureElements.get(1)));
                ps.setString(13, getStructureElementVisId(structureElements.get(2)));
                ps.setInt(14, profile.getProfileId());
            }
        };
        return pss;
    }
    
    @Override
    public ResponseMessage updateProfile(FormDeploymentDataDTO profile) throws DaoException {
        try {
            String query = "update DATA_ENTRY_PROFILE set BUDGET_CYCLE_ID = ?, VIS_ID = ?, DESCRIPTION = ?, DATA_TYPE = ?, STRUCTURE_ID0 = ?,  STRUCTURE_ID1 = ?, STRUCTURE_ID2 = ?, STRUCTURE_ELEMENT_ID0 = ?, STRUCTURE_ELEMENT_ID1 = ?, STRUCTURE_ELEMENT_ID2 = ?, ELEMENT_LABEL0 = ?, ELEMENT_LABEL1 = ?, ELEMENT_LABEL2 = ?, UPDATED_TIME = LOCALTIMESTAMP where DATA_ENTRY_PROFILE_ID = ?";
            PreparedStatementSetter pss = prepareUpdateStatement(profile);
            getJdbcTemplate().update(query, pss);
            return new ResponseMessage(true, "Profile has been updated");
        }
        catch(EmptyResultDataAccessException e){
        }
        catch(DataAccessException e){
            String errorMsg = "Error while executing update query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
        return null;
    }
}
