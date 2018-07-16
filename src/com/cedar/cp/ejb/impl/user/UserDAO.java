package com.cedar.cp.ejb.impl.user;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.reset.ChallengeQuestionCK;
import com.cedar.cp.dto.reset.ChallengeQuestionPK;
import com.cedar.cp.dto.reset.UserResetLinkCK;
import com.cedar.cp.dto.reset.UserResetLinkPK;
import com.cedar.cp.dto.user.AllDashboardsForUserELO;
import com.cedar.cp.dto.user.AllNonDisabledUsersELO;
import com.cedar.cp.dto.user.AllRevisionsELO;
import com.cedar.cp.dto.user.AllUserAttributesELO;
import com.cedar.cp.dto.user.AllUserDetailsReportELO;
import com.cedar.cp.dto.user.AllUsersAssignmentsELO;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.dto.user.AllUsersExportELO;
import com.cedar.cp.dto.user.DataEntryProfileCK;
import com.cedar.cp.dto.user.DataEntryProfilePK;
import com.cedar.cp.dto.user.FinanceSystemUserNameELO;
import com.cedar.cp.dto.user.SecurityStringsForUserELO;
import com.cedar.cp.dto.user.UserCK;
import com.cedar.cp.dto.user.UserMessageAttributesELO;
import com.cedar.cp.dto.user.UserMessageAttributesForIdELO;
import com.cedar.cp.dto.user.UserMessageAttributesForNameELO;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserPreferenceCK;
import com.cedar.cp.dto.user.UserPreferencePK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.dto.user.UserRoleCK;
import com.cedar.cp.dto.user.UserRolePK;
import com.cedar.cp.dto.user.UserStrikeCountELO;
import com.cedar.cp.dto.user.UsersWithSecurityStringELO;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.reset.ChallengeQuestionDAO;
import com.cedar.cp.ejb.impl.reset.ChallengeQuestionEVO;
import com.cedar.cp.ejb.impl.reset.UserResetLinkDAO;
import com.cedar.cp.ejb.impl.reset.UserResetLinkEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

public class UserDAO extends AbstractDAO {
    Log _log = new Log(getClass());
    protected static final String SQL_FIND_BY_PRIMARY_KEY = "select USER_ID from USR where    USER_ID = ? ";
    private static final String SQL_SELECT_COLUMNS = "select USR.USER_ID,USR.NAME,USR.FULL_NAME,USR.E_MAIL_ADDRESS,USR.PASSWORD_BYTES,USR.PASSWORD_DATE,USR.CHANGE_PASSWORD,USR.RESET_STRIKES,USR.USER_DISABLED,USR.PASSWORD_NEVER_EXPIRES,USR.EXTERNAL_SYSTEM_USER_NAME,USR.LOGON_ALIAS,USR.VERSION_NUM,USR.UPDATED_BY_USER_ID,USR.UPDATED_TIME,USR.CREATED_TIME";
    protected static final String SQL_LOAD = " from USR where    USER_ID = ? ";
    protected static final String SQL_CREATE = "insert into USR ( USER_ID,NAME,FULL_NAME,E_MAIL_ADDRESS,PASSWORD_BYTES,PASSWORD_DATE,CHANGE_PASSWORD,RESET_STRIKES,USER_DISABLED,PASSWORD_NEVER_EXPIRES,EXTERNAL_SYSTEM_USER_NAME,LOGON_ALIAS,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    protected static final String SQL_UPDATE_SEQ_NUM = "update USR_SEQ set SEQ_NUM = SEQ_NUM + ?";
    protected static final String SQL_SELECT_SEQ_NUM = "select SEQ_NUM from USR_SEQ";
    protected static final String SQL_DUPLICATE_VALUE_CHECK_NAME = "select count(*) from USR where    NAME = ? and not(    USER_ID = ? )";
    protected static final String SQL_DUPLICATE_VALUE_CHECK_LOGON_ALIAS = "select count(*) from USR where    LOGON_ALIAS = ? and not(    USER_ID = ? )";
    protected static final String SQL_STORE = "update USR set NAME = ?,FULL_NAME = ?,E_MAIL_ADDRESS = ?,PASSWORD_BYTES = ?,PASSWORD_DATE = ?,CHANGE_PASSWORD = ?,RESET_STRIKES = ?,USER_DISABLED = ?,PASSWORD_NEVER_EXPIRES = ?,EXTERNAL_SYSTEM_USER_NAME = ?,LOGON_ALIAS = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    USER_ID = ? AND VERSION_NUM = ?";
    protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from USR where USER_ID = ?";
    protected static final String SQL_REMOVE = "delete from USR where    USER_ID = ? ";
    protected static String SQL_ALL_USERS = "select 0       ,USR.USER_ID      ,USR.NAME      ,USR.FULL_NAME      ,USR.USER_DISABLED from USR where 1=1  order by NAME";
    
    protected static String SQL_SELECT_ALL_REVISIONS = "SELECT * FROM ROADMAP";
    
    protected static String SQL_SECURITY_STRINGS_FOR_USER = "select distinct 0       ,ROLE_SECURITY.SECURITY_STRING from USR    ,ROLE_SECURITY    ,USER_ROLE    ,ROLE_SECURITY_REL where 1=1  and  USR.USER_ID = ? and USER_ROLE.USER_ID = USR.USER_ID and ROLE_SECURITY_REL.ROLE_ID = USER_ROLE.ROLE_ID and ROLE_SECURITY_REL.ROLE_SECURITY_ID = ROLE_SECURITY.ROLE_SECURITY_ID";

    protected static String SQL_ALL_USERS_EXPORT = "select 0       ,USR.USER_ID      ,USR.NAME      ,USR.FULL_NAME      ,USR.E_MAIL_ADDRESS      ,USR.USER_DISABLED      ,USR.PASSWORD_NEVER_EXPIRES      ,USR.EXTERNAL_SYSTEM_USER_NAME      ,ROLE.VIS_ID      ,USR.LOGON_ALIAS from USR    ,ROLE    ,USER_ROLE where 1=1  and  USR.USER_ID = USER_ROLE.USER_ID and USER_ROLE.ROLE_ID = ROLE.ROLE_ID order by USR.NAME";

    protected static String SQL_ALL_USER_ATTRIBUTES = "select 0       ,USR.USER_ID      ,USR.NAME      ,USR.FULL_NAME      ,USR.E_MAIL_ADDRESS      ,USR.PASSWORD_BYTES      ,USR.PASSWORD_DATE      ,USR.CHANGE_PASSWORD      ,USR.USER_DISABLED      ,USR.PASSWORD_NEVER_EXPIRES from USR where 1=1  order by USER_ID";

    protected static String SQL_ALL_NON_DISABLED_USERS = "select 0       ,USR.USER_ID      ,USR.NAME      ,USR.FULL_NAME      ,USR.E_MAIL_ADDRESS from USR where 1=1  and  USER_DISABLED = ' ' order by NAME";

    protected static String SQL_USER_MESSAGE_ATTRIBUTES = "select 0       ,USR.USER_ID      ,USR.NAME      ,USR.NAME      ,USR.FULL_NAME      ,USR.USER_DISABLED      ,USR.E_MAIL_ADDRESS from USR where 1=1  order by USR.NAME";

    protected static String SQL_USER_MESSAGE_ATTRIBUTES_FOR_ID = "select 0       ,USR.USER_ID      ,USR.NAME      ,USR.NAME      ,USR.FULL_NAME      ,USR.USER_DISABLED      ,USR.E_MAIL_ADDRESS from USR where 1=1  and  USER_ID = ?";

    protected static String SQL_USER_MESSAGE_ATTRIBUTES_FOR_NAME = "select 0       ,USR.USER_ID      ,USR.NAME      ,USR.NAME      ,USR.FULL_NAME      ,USR.USER_DISABLED      ,USR.E_MAIL_ADDRESS from USR where 1=1  and  NAME = ?";

    protected static String SQL_FINANCE_SYSTEM_USER_NAME = "select 0       ,USR.USER_ID      ,USR.NAME      ,USR.EXTERNAL_SYSTEM_USER_NAME from USR where 1=1  and  USR.USER_ID = ?";

    protected static String SQL_USERS_WITH_SECURITY_STRING = "select distinct 0       ,USR.USER_ID      ,USR.NAME from USR    ,ROLE_SECURITY    ,ROLE_SECURITY_REL    ,ROLE    ,USER_ROLE where 1=1  and  ROLE_SECURITY.SECURITY_STRING = ? AND ROLE_SECURITY.ROLE_SECURITY_ID = ROLE_SECURITY_REL.ROLE_SECURITY_ID AND ROLE_SECURITY_REL.ROLE_ID = ROLE.ROLE_ID AND ROLE.ROLE_ID = USER_ROLE.ROLE_ID AND USR.USER_ID = USER_ROLE.USER_ID";

    protected static String SQL_USER_STRIKE_COUNT = "select 0       ,USR.USER_ID      ,USR.NAME      ,USR.RESET_STRIKES from USR where 1=1  and  USR.USER_ID = ?";

    private static String[][] SQL_DELETE_CHILDREN = { { "USER_ROLE", "delete from USER_ROLE where     USER_ROLE.USER_ID = ? " }, { "USER_PREFERENCE", "delete from USER_PREFERENCE where     USER_PREFERENCE.USER_ID = ? " }, { "DATA_ENTRY_PROFILE", "delete from DATA_ENTRY_PROFILE where     DATA_ENTRY_PROFILE.USER_ID = ? " }, { "CHALLENGE_QUESTION", "delete from CHALLENGE_QUESTION where     CHALLENGE_QUESTION.USER_ID = ? " },
            { "USER_RESET_LINK", "delete from USER_RESET_LINK where     USER_RESET_LINK.USER_ID = ? " } };

    private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = { { "DATA_ENTRY_PROFILE_HISTORY", "delete from DATA_ENTRY_PROFILE_HISTORY DataEntryProfileHistory where exists (select * from DATA_ENTRY_PROFILE_HISTORY,DATA_ENTRY_PROFILE,USR where     DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID = DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID and DATA_ENTRY_PROFILE.USER_ID = USR.USER_ID and DataEntryProfileHistory.DATA_ENTRY_PROFILE_ID = DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID " } };

    private static String SQL_DELETE_DEPENDANT_CRITERIA = "and USR.USER_ID = ?)";
    private static final String SQL_CHECK_IF_VALID = "select VERSION_NUM from USR where   USER_ID = ?";
    public static final String SQL_GET_USER_ROLE_REF = "select 0,USR.USER_ID from USER_ROLE\njoin USR on (1=1\nand USER_ROLE.USER_ID = USR.USER_ID\n) where 1=1 and USER_ROLE.USER_ID = ? and USER_ROLE.ROLE_ID = ?";
    public static final String SQL_GET_USER_PREFERENCE_REF = "select 0,USR.USER_ID from USER_PREFERENCE\njoin USR on (1=1\nand USER_PREFERENCE.USER_ID = USR.USER_ID\n) where 1=1 and USER_PREFERENCE.USER_PREF_ID = ?";
    public static final String SQL_GET_DATA_ENTRY_PROFILE_REF = "select 0,USR.USER_ID,DATA_ENTRY_PROFILE.VIS_ID from DATA_ENTRY_PROFILE\njoin USR on (1=1\nand DATA_ENTRY_PROFILE.USER_ID = USR.USER_ID\n) where 1=1 and DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID = ?";
    public static final String SQL_GET_DATA_ENTRY_PROFILE_HISTORY_REF = "select 0,USR.USER_ID,DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID from DATA_ENTRY_PROFILE_HISTORY\njoin DATA_ENTRY_PROFILE on (1=1\nand DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID = DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID\n)\njoin USR on (1=1\nand DATA_ENTRY_PROFILE.USER_ID = USR.USER_ID\n) where 1=1 and DATA_ENTRY_PROFILE_HISTORY.USER_ID = ? and DATA_ENTRY_PROFILE_HISTORY.MODEL_ID = ? and DATA_ENTRY_PROFILE_HISTORY.BUDGET_LOCATION_ELEMENT_ID = ?";
    public static final String SQL_GET_CHALLENGE_QUESTION_REF = "select 0,USR.USER_ID,CHALLENGE_QUESTION.QUESTION_TEXT from CHALLENGE_QUESTION\njoin USR on (1=1\nand CHALLENGE_QUESTION.USER_ID = USR.USER_ID\n) where 1=1 and CHALLENGE_QUESTION.USER_ID = ? and CHALLENGE_QUESTION.QUESTION_TEXT = ?";
    public static final String SQL_GET_USER_RESET_LINK_REF = "select 0,USR.USER_ID,USER_RESET_LINK.PWD_LINK from USER_RESET_LINK\njoin USR on (1=1\nand USER_RESET_LINK.USER_ID = USR.USER_ID\n) where 1=1 and USER_RESET_LINK.USER_ID = ? and USER_RESET_LINK.PWD_LINK = ?";
    protected static final String SQL_FIND_BY_USER_ID = "select USER_ID from USR where    NAME = ? ";
    protected static final String SQL_FIND_BY_LOGON_ALIAS = "select USER_ID from USR where    lower(LOGON_ALIAS) = ? ";
    protected static final String SQL_RESPAREA_ASSIGNMENTS = "SELECT    a.name,   a.full_name,   d.vis_id,   c.vis_id,   c.description,    CASE b.READ_ONLY        WHEN NULL THEN 'W'        WHEN 'N' THEN 'W'        WHEN ' ' THEN 'W'        ELSE 'R'    END FROM    usr a,   budget_user b,   structure_element c,   model d WHERE    a.user_id = b.user_id    AND b.structure_element_id = c.structure_element_id    AND b.model_id = d.model_id    AND d.budget_hierarchy_id = c.structure_id    AND a.name LIKE ?    AND a.full_name LIKE ?    AND d.vis_id LIKE ?    AND c.vis_id LIKE ? ORDER BY    upper(a.full_name), d.vis_id, c.vis_id";
    private static String SQL_RESET_USER_STRIKE = "update USR set RESET_STRIKES = 0 where user_id = ?";
    protected UserRoleDAO mUserRoleDAO;
    protected UserPreferenceDAO mUserPreferenceDAO;
    protected DataEntryProfileDAO mDataEntryProfileDAO;
    protected ChallengeQuestionDAO mChallengeQuestionDAO;
    protected UserResetLinkDAO mUserResetLinkDAO;
    protected UserEVO mDetails;

    public UserDAO(Connection connection) {
        super(connection);
    }

    public UserDAO() {
    }

    public UserDAO(DataSource ds) {
        super(ds);
    }

    protected UserPK getPK() {
        return mDetails.getPK();
    }

    public void setDetails(UserEVO details) {
        mDetails = details.deepClone();
    }

    public UserEVO setAndGetDetails(UserEVO details, String dependants) {
        setDetails(details);
        generateKeys();
        getDependants(mDetails, dependants);
        return mDetails.deepClone();
    }

    public UserPK create() throws DuplicateNameValidationException, ValidationException {
        doCreate();

        return mDetails.getPK();
    }

    public void load(UserPK pk) throws ValidationException {
        doLoad(pk);
    }

    public void store() throws DuplicateNameValidationException, VersionValidationException, ValidationException {
        doStore();
    }

    public void remove() {
        doRemove();
    }

    public UserPK findByPrimaryKey(UserPK pk_) throws ValidationException {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        if (exists(pk_)) {
            if (timer != null) {
                timer.logDebug("findByPrimaryKey", pk_);
            }
            return pk_;
        }

        throw new ValidationException(new StringBuilder().append(pk_).append(" not found").toString());
    }

    protected boolean exists(UserPK pk) {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;
        try {
            stmt = getConnection().prepareStatement("select USER_ID from USR where    USER_ID = ? ");

            int col = 1;
            stmt.setInt(col++, pk.getUserId());

            resultSet = stmt.executeQuery();

            if (!resultSet.next())
                returnValue = false;
            else
                returnValue = true;
        } catch (SQLException sqle) {
            throw handleSQLException(pk, "select USER_ID from USR where    USER_ID = ? ", sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }
        return returnValue;
    }

    private UserEVO getEvoFromJdbc(ResultSet resultSet_) throws SQLException {
        int col = 1;
        int userId = resultSet_.getInt(1);
        UserEVO evo = new UserEVO(userId, 
        		resultSet_.getString(2), resultSet_.getString(3), resultSet_.getString(4), 
        		resultSet_.getString(5), resultSet_.getTimestamp(6), resultSet_.getString(7).equals("Y"), 
        		resultSet_.getInt(8), isUserReadOnly(userId), resultSet_.getString(9).equals("Y"), 
        		resultSet_.getString(10).equals("Y"), resultSet_.getString(11), resultSet_.getString(12), 
        		resultSet_.getInt(13), null, null, null, null, null, resultSet_.getString(14).equals("Y"),
                resultSet_.getString(15).equals("Y"), resultSet_.getString(16).equals("Y"),
                resultSet_.getString(17).equals("Y"), resultSet_.getString(18).equals("Y"), 
                resultSet_.getString(22), resultSet_.getString(23));

        evo.setUpdatedByUserId(resultSet_.getInt(19));
        evo.setUpdatedTime(resultSet_.getTimestamp(20));
        evo.setCreatedTime(resultSet_.getTimestamp(21));
        return evo;
    }

    private int putEvoKeysToJdbc(UserEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
        int col = startCol_;
        stmt_.setInt(col++, evo_.getUserId());
        return col;
    }

    private int putEvoDataToJdbc(UserEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
        int col = startCol_;
        stmt_.setString(col++, evo_.getName());
        
        stmt_.setString(col++, evo_.getFullName());
        
        stmt_.setString(col++, evo_.getEMailAddress());
        
        stmt_.setString(col++, evo_.getPasswordBytes());
        
        stmt_.setTimestamp(col++, evo_.getPasswordDate());
        
        if (evo_.getChangePassword())
            stmt_.setString(col++, "Y");
        else
            stmt_.setString(col++, " ");
        
        stmt_.setInt(col++, evo_.getResetStrikes());
        
        if (evo_.getUserDisabled())
            stmt_.setString(col++, "Y");
        else
            stmt_.setString(col++, " ");
        
        if (evo_.getPasswordNeverExpires())
            stmt_.setString(col++, "Y");
        else
            stmt_.setString(col++, " ");
        
        stmt_.setString(col++, evo_.getExternalSystemUserName());
        
        stmt_.setString(col++, evo_.getLogonAlias());
        
        stmt_.setInt(col++, evo_.getVersionNum());
        
        if (evo_.isNewFeaturesEnabled())
            stmt_.setString(col++, "Y");
        else
            stmt_.setString(col++, "N");
        
        if (evo_.areButtonsVisible())
            stmt_.setString(col++, "Y");
        else
            stmt_.setString(col++, "N");
        
        if (evo_.isShowBudgetActivity())
            stmt_.setString(col++, "Y");
        else
            stmt_.setString(col++, "N");
        
        if (evo_.isNewView())
            stmt_.setString(col++, "Y");
        else
            stmt_.setString(col++, "N");
        
        if (evo_.getRoadMapAvailable())
            stmt_.setString(col++, "Y");
        else
            stmt_.setString(col++, "N");
        
        stmt_.setInt(col++, evo_.getUpdatedByUserId());
        
        stmt_.setTimestamp(col++, evo_.getUpdatedTime());
        
        stmt_.setTimestamp(col++, evo_.getCreatedTime());
        
        stmt_.setString(col++, evo_.getMobilePIN());
        
        stmt_.setString(col++, evo_.getSalt());
        return col;
    }

    protected void doLoad(UserPK pk) throws ValidationException {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement(
            		"select USR.USER_ID,USR.NAME,USR.FULL_NAME,USR.E_MAIL_ADDRESS,USR.PASSWORD_BYTES,"
            		+ "USR.PASSWORD_DATE,USR.CHANGE_PASSWORD,USR.RESET_STRIKES,USR.USER_DISABLED,"
            		+ "USR.PASSWORD_NEVER_EXPIRES,USR.EXTERNAL_SYSTEM_USER_NAME,USR.LOGON_ALIAS,"
            		+ "USR.VERSION_NUM,USR.NEW_FEATURES, USR.BUTTONS_VISIBLE, USR.SHOW_BUDGET_ACTIVITY,"
            		+ "USR.NEW_VIEW,ROADMAP_AVAILABLE, USR.UPDATED_BY_USER_ID,USR.UPDATED_TIME,USR.CREATED_TIME,"
            		+ "USR.MOBILE_PIN,USR.SALT "
            		+ "from USR where USER_ID = ? ");

            stmt.setInt(1, pk.getUserId());
            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new ValidationException(new StringBuilder().append(getEntityName()).append(" select of ").append(pk).append(" not found").toString());
            }

            mDetails = getEvoFromJdbc(resultSet);
            if (mDetails.isModified())
                _log.info("doLoad", mDetails);
        } catch (SQLException sqle) {
            throw handleSQLException(pk, "select USR.USER_ID,USR.NAME,USR.FULL_NAME,USR.E_MAIL_ADDRESS,USR.PASSWORD_BYTES,USR.PASSWORD_DATE,USR.CHANGE_PASSWORD,USR.RESET_STRIKES,USR.USER_DISABLED,USR.PASSWORD_NEVER_EXPIRES,USR.EXTERNAL_SYSTEM_USER_NAME,USR.LOGON_ALIAS,USR.VERSION_NUM, USR.NEW_FEATURES, USR.BUTTONS_VISIBLE, USR.SHOW_BUDGET_ACTIVITY, USR.NEW_VIEW,ROADMAP_AVAILABLE, USR.UPDATED_BY_USER_ID,USR.UPDATED_TIME,USR.CREATED_TIME, USR.MOBILE_PIN,USR.SALT from USR where USER_ID = ? ", sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            if (timer != null)
                timer.logDebug("doLoad", pk);
        }
    }

    protected void doCreate() throws DuplicateNameValidationException, ValidationException {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        generateKeys();

        mDetails.postCreateInit();

        PreparedStatement stmt = null;
        try {
            duplicateValueCheckName();

            duplicateValueCheckLogon_Alias();

            mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
            mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
            stmt = getConnection().prepareStatement("insert into USR ( "
            		+ "USER_ID,NAME,FULL_NAME,E_MAIL_ADDRESS,PASSWORD_BYTES,PASSWORD_DATE,CHANGE_PASSWORD,"
            		+ "RESET_STRIKES,USER_DISABLED,PASSWORD_NEVER_EXPIRES,EXTERNAL_SYSTEM_USER_NAME,LOGON_ALIAS,"
            		+ "VERSION_NUM, USR.NEW_FEATURES, USR.BUTTONS_VISIBLE, USR.SHOW_BUDGET_ACTIVITY, USR.NEW_VIEW, "
            		+ "ROADMAP_AVAILABLE, UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME,MOBILE_PIN,SALT) "
            		+ "values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            int col = 1;
            col = putEvoKeysToJdbc(mDetails, stmt, col);
            col = putEvoDataToJdbc(mDetails, stmt, col);

            int resultCount = stmt.executeUpdate();
            if (resultCount != 1) {
                throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" insert failed (").append(mDetails.getPK()).append("): resultCount=").append(resultCount).toString());
            }

            mDetails.reset();
        } catch (SQLException sqle) {
            throw handleSQLException(mDetails.getPK(), "insert into USR ( "
            		+ "USER_ID,NAME,FULL_NAME,E_MAIL_ADDRESS,PASSWORD_BYTES,PASSWORD_DATE,CHANGE_PASSWORD,"
            		+ "RESET_STRIKES,USER_DISABLED,PASSWORD_NEVER_EXPIRES,EXTERNAL_SYSTEM_USER_NAME,LOGON_ALIAS,"
            		+ "VERSION_NUM, USR.NEW_FEATURES, USR.BUTTONS_VISIBLE, USR.SHOW_BUDGET_ACTIVITY, USR.NEW_VIEW, "
            		+ "ROADMAP_AVAILABLE, UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME,MOBILE_PIN,SALT) "
            		+ "values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();

            if (timer != null) {
                timer.logDebug("doCreate", mDetails.toString());
            }
        }

        try {
            setUserReadOnly(mDetails.getUserId(), mDetails.getUserReadOnly());

            getUserRoleDAO().update(mDetails.getUserRolesMap());

            getUserPreferenceDAO().update(mDetails.getUserPreferencesMap());

            getDataEntryProfileDAO().update(mDetails.getDataEntryProfilesMap());

            getChallengeQuestionDAO().update(mDetails.getChallengeQuestionsMap());

            getUserResetLinkDAO().update(mDetails.getResetLinkMap());
        } catch (Exception e) {
            throw new RuntimeException("unexpected exception", e);
        }
    }

    public int reserveIds(int insertCount) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        String sqlString = null;
        try {
            sqlString = "update USR_SEQ set SEQ_NUM = SEQ_NUM + ?";
            stmt = getConnection().prepareStatement("update USR_SEQ set SEQ_NUM = SEQ_NUM + ?");
            stmt.setInt(1, insertCount);

            int resultCount = stmt.executeUpdate();
            if (resultCount != 1) {
                throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" reserveIds: update failed: resultCount=").append(resultCount).toString());
            }
            closeStatement(stmt);

            sqlString = "select SEQ_NUM from USR_SEQ";
            stmt = getConnection().prepareStatement("select SEQ_NUM from USR_SEQ");
            resultSet = stmt.executeQuery();
            if (!resultSet.next())
                throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" reserveIds: select failed").toString());
            int latestKey = resultSet.getInt(1);

            return latestKey - insertCount;
        } catch (SQLException sqle) {
            throw handleSQLException(sqlString, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            if (timer != null)
                timer.logDebug("reserveIds", new StringBuilder().append("keys=").append(insertCount).toString());
        }
    }

    public UserPK generateKeys() {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

        int insertCount = mDetails.getInsertCount(0);

        if (insertCount == 0) {
            return mDetails.getPK();
        }
        mDetails.assignNextKey(reserveIds(insertCount));

        return mDetails.getPK();
    }

    protected void duplicateValueCheckName() throws DuplicateNameValidationException {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement("select count(*) from USR where    NAME = ? and not(    USER_ID = ? )");

            int col = 1;
            stmt.setString(col++, mDetails.getName());
            col = putEvoKeysToJdbc(mDetails, stmt, col);

            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" select of ").append(getPK()).append(" not found").toString());
            }

            col = 1;
            int count = resultSet.getInt(col++);
            if (count > 0) {
                throw new DuplicateNameValidationException(new StringBuilder().append(getEntityName()).append(" ").append(getPK()).append(" Name").toString());
            }

        } catch (SQLException sqle) {
            throw handleSQLException(getPK(), "select count(*) from USR where    NAME = ? and not(    USER_ID = ? )", sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            if (timer != null)
                timer.logDebug("duplicateValueCheckName", "");
        }
    }

    protected void duplicateValueCheckLogon_Alias() throws DuplicateNameValidationException {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement("select count(*) from USR where    LOGON_ALIAS = ? and not(    USER_ID = ? )");

            int col = 1;
            stmt.setString(col++, mDetails.getLogonAlias());
            col = putEvoKeysToJdbc(mDetails, stmt, col);

            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" select of ").append(getPK()).append(" not found").toString());
            }

            col = 1;
            int count = resultSet.getInt(col++);
            if (count > 0) {
                throw new DuplicateNameValidationException(new StringBuilder().append(getEntityName()).append(" ").append(getPK()).append(" Logon_Alias").toString());
            }

        } catch (SQLException sqle) {
            throw handleSQLException(getPK(), "select count(*) from USR where    LOGON_ALIAS = ? and not(    USER_ID = ? )", sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            if (timer != null)
                timer.logDebug("duplicateValueCheckLogon_Alias", "");
        }
    }

    protected void doStore() throws DuplicateNameValidationException, VersionValidationException, ValidationException {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

        generateKeys();

        PreparedStatement stmt = null;

        boolean mainChanged = mDetails.isModified();
        boolean dependantChanged = false;
        try {
            if (mainChanged) {
                duplicateValueCheckName();
            }
            if (mainChanged) {
                duplicateValueCheckLogon_Alias();
            }
            if (getUserRoleDAO().update(mDetails.getUserRolesMap())) {
                dependantChanged = true;
            }

            if (getUserPreferenceDAO().update(mDetails.getUserPreferencesMap())) {
                dependantChanged = true;
            }

            if (getDataEntryProfileDAO().update(mDetails.getDataEntryProfilesMap())) {
                dependantChanged = true;
            }

            if (getChallengeQuestionDAO().update(mDetails.getChallengeQuestionsMap())) {
                dependantChanged = true;
            }

            if (getUserResetLinkDAO().update(mDetails.getResetLinkMap())) {
                dependantChanged = true;
            }
            if ((mainChanged) || (dependantChanged)) {
                mDetails.setVersionNum(mDetails.getVersionNum() + 1);

                mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
                stmt = getConnection().prepareStatement(
                		"update USR set "
                		+ "NAME = ?,FULL_NAME = ?,E_MAIL_ADDRESS = ?,PASSWORD_BYTES = ?,PASSWORD_DATE = ?,"
                		+ "CHANGE_PASSWORD = ?,RESET_STRIKES = ?,USER_DISABLED = ?,PASSWORD_NEVER_EXPIRES = ?,"
                		+ "EXTERNAL_SYSTEM_USER_NAME = ?,LOGON_ALIAS = ?,VERSION_NUM = ?, NEW_FEATURES = ?, "
                		+ "BUTTONS_VISIBLE = ?, SHOW_BUDGET_ACTIVITY = ?,NEW_VIEW = ?, ROADMAP_AVAILABLE = ?, "
                		+ "UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ?, "
                		+ "MOBILE_PIN = ?, SALT = ? "
                		+ "where USER_ID = ? AND VERSION_NUM = ?");

                int col = 1;
                col = putEvoDataToJdbc(mDetails, stmt, col);
                col = putEvoKeysToJdbc(mDetails, stmt, col);

                stmt.setInt(col++, mDetails.getVersionNum() - 1);

                int resultCount = stmt.executeUpdate();

                if (resultCount == 0) {
                    checkVersionNum();
                }
                if (resultCount != 1) {
                    throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" update failed (").append(getPK()).append("): resultCount=").append(resultCount).toString());
                }
                setUserReadOnly(mDetails.getUserId(), mDetails.getUserReadOnly());
                mDetails.reset();
            }

        } catch (SQLException sqle) {
            throw handleSQLException(getPK(), "update USR set "
            		+ "NAME = ?,FULL_NAME = ?,E_MAIL_ADDRESS = ?,PASSWORD_BYTES = ?,PASSWORD_DATE = ?,"
            		+ "CHANGE_PASSWORD = ?,RESET_STRIKES = ?,USER_DISABLED = ?,PASSWORD_NEVER_EXPIRES = ?,"
            		+ "EXTERNAL_SYSTEM_USER_NAME = ?,LOGON_ALIAS = ?,VERSION_NUM = ?, NEW_FEATURES = ?, "
            		+ "BUTTONS_VISIBLE = ?, SHOW_BUDGET_ACTIVITY = ?, NEW_VIEW = ?, ROADMAP_AVAILABLE = ?, "
            		+ "UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? "
            		+ "where    USER_ID = ? AND VERSION_NUM = ?", sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();

            if ((timer != null) && ((mainChanged) || (dependantChanged)))
                timer.logDebug("store", new StringBuilder().append(mDetails.getPK()).append("(").append(mainChanged).append(",").append(dependantChanged).append(")").toString());
        }
    }

    private void checkVersionNum() throws VersionValidationException {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement("select VERSION_NUM from USR where USER_ID = ?");

            int col = 1;
            stmt.setInt(col++, mDetails.getUserId());

            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" checkVersionNum: select of ").append(getPK()).append(" not found").toString());
            }

            col = 1;
            int dbVersionNumber = resultSet.getInt(col++);
            if (mDetails.getVersionNum() - 1 != dbVersionNumber) {
                throw new VersionValidationException(new StringBuilder().append(getEntityName()).append(" ").append(getPK()).append(" expected:").append(mDetails.getVersionNum() - 1).append(" found:").append(dbVersionNumber).toString());
            }

        } catch (SQLException sqle) {
            throw handleSQLException(getPK(), "select VERSION_NUM from USR where USER_ID = ?", sqle);
        } finally {
            closeStatement(stmt);
            closeResultSet(resultSet);

            if (timer != null)
                timer.logDebug("checkVersionNum", mDetails.getPK());
        }
    }

    protected void doRemove() {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        deleteDependants(mDetails.getPK());

        PreparedStatement stmt = null;
        try {
            stmt = getConnection().prepareStatement("delete from USR where    USER_ID = ? ");

            int col = 1;
            stmt.setInt(col++, mDetails.getUserId());

            int resultCount = stmt.executeUpdate();

            if (resultCount != 1) {
                throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" delete failed (").append(getPK()).append("): resultCount=").append(resultCount).toString());
            }

        } catch (SQLException sqle) {
            throw handleSQLException(getPK(), "delete from USR where    USER_ID = ? ", sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();

            if (timer != null)
                timer.logDebug("remove", mDetails.getPK());
        }
    }

    public AllUsersELO getAllUsers() {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllUsersELO results = new AllUsersELO();
        try {
            stmt = getConnection().prepareStatement(SQL_ALL_USERS);
            int col = 1;
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                UserPK pkUser = new UserPK(resultSet.getInt(col++));

                String textUser = resultSet.getString(col++);

                UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

                String col1 = resultSet.getString(col++);
                String col2 = resultSet.getString(col++);
                if (resultSet.wasNull()) {
                    col2 = "";
                }

                results.add(erUser, col1, col2.equals("Y"));
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_ALL_USERS, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllUsers", new StringBuilder().append(" items=").append(results.size()).toString());
        }

        return results;
    }
    
    public AllRevisionsELO getAllRevisions() {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllRevisionsELO results = new AllRevisionsELO();
        try {
            stmt = getConnection().prepareStatement(SQL_SELECT_ALL_REVISIONS);
            int col = 1;
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 1;

                Integer id = resultSet.getInt(col++);

                Integer revision = resultSet.getInt(col++);

                java.sql.Date vDate = resultSet.getDate(col++);
                String description = resultSet.getString(col++);
                

                results.add(id, revision, vDate, description);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_SELECT_ALL_REVISIONS, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllRevisions", new StringBuilder().append(" items=").append(results.size()).toString());
        }

        return results;
     }
    
    public AllDashboardsForUserELO getDashboardForms(Integer userId, boolean isAdmin) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        String sql;
        
        if (!isAdmin){
        // @formatter:off
        sql = ""
                + "WITH allDashboards AS "
                + "  (SELECT d.DASHBOARD_UUID, "
                + "    d.DASHBOARD_TITLE, "
                + "    d.UPDATED_TIME, "
                + "    d.DASHBOARD_TYPE, "
                + "    d.FORM_ID, "
                + "    d.MODEL_ID, "
                + "    f.VIS_ID "
                + "  FROM DASHBOARD d "
                + "  JOIN XML_FORM f "
                + "  ON (d.FORM_ID =f.XML_FORM_ID) "
                + "  ), "
                + "  xml_forms AS "
                + "  ( SELECT XML_FORM_ID FROM XML_FORM "
                + "  MINUS "
                + "    (SELECT DISTINCT XML_FORM_ID FROM XML_FORM_USER_LINK "
                + "    MINUS "
                + "    SELECT DISTINCT XML_FORM_ID FROM XML_FORM_USER_LINK WHERE USER_ID = ? "
                + "    ) "
                + "  ), "
                + "  models AS "
                + "  (SELECT DISTINCT m.MODEL_ID "
                + "  FROM usr u "
                + "  LEFT JOIN budget_user bu "
                + "  ON ( u.USER_ID = bu.USER_ID ) "
                + "  LEFT JOIN model m "
                + "  ON ( bu.MODEL_ID = m.MODEL_ID ) "
                + "  WHERE u.USER_ID  = ? "
                + "  ) "
                + "SELECT ad.DASHBOARD_UUID, "
                + "  ad.DASHBOARD_TITLE, "
                + "  ad.DASHBOARD_TYPE "
                + "FROM allDashboards ad "
                + "WHERE ad.FORM_ID IN "
                + "  ( SELECT XML_FORM_ID AS FORM_ID FROM xml_forms "
                + "  ) "
                + "AND ad.MODEL_ID IN "
                + "  (SELECT MODEL_ID FROM models "
                + "  )";
        // @formatter:on
        }
        else{
          // @formatter:off
          sql = ""
                    + "SELECT DASHBOARD_UUID, DASHBOARD_TITLE, DASHBOARD_TYPE FROM dashboard";
          // @formatter:on
        }
        
        AllDashboardsForUserELO results = new AllDashboardsForUserELO();
        try {
            stmt = getConnection().prepareStatement(sql);
            
            int col = 1;
            if (!isAdmin){
                stmt.setInt(col++, userId);
                stmt.setInt(col++, userId);
            }
            
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 1;
                
                String dashboardUuid = resultSet.getString(col++);
                String dashboardTitle = resultSet.getString(col++);
                String dashboardType = resultSet.getString(col++);
               

                results.add(dashboardUuid, dashboardTitle,dashboardType);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(sql, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getDashboardForms", new StringBuilder().append(" items=").append(results.size()).toString());
        }

        return results;
     }
    

    public SecurityStringsForUserELO getSecurityStringsForUser(int param1) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        SecurityStringsForUserELO results = new SecurityStringsForUserELO();
        try {
            stmt = getConnection().prepareStatement(SQL_SECURITY_STRINGS_FOR_USER);
            int col = 1;
            stmt.setInt(col++, param1);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                results.add(resultSet.getString(col++));
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_SECURITY_STRINGS_FOR_USER, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getSecurityStringsForUser", new StringBuilder().append(" UserId=").append(param1).append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public AllUsersExportELO getAllUsersExport() {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllUsersExportELO results = new AllUsersExportELO();
        try {
            stmt = getConnection().prepareStatement(SQL_ALL_USERS_EXPORT);
            int col = 1;
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                results.add(resultSet.getInt(col++), resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++).equals("Y"), resultSet.getString(col++).equals("Y"), resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++));
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_ALL_USERS_EXPORT, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllUsersExport", new StringBuilder().append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public AllUserAttributesELO getAllUserAttributes() {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllUserAttributesELO results = new AllUserAttributesELO();
        try {
            stmt = getConnection().prepareStatement(SQL_ALL_USER_ATTRIBUTES);
            int col = 1;
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                UserPK pkUser = new UserPK(resultSet.getInt(col++));

                String textUser = resultSet.getString(col++);

                UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

                String col1 = resultSet.getString(col++);
                String col2 = resultSet.getString(col++);
                String col3 = resultSet.getString(col++);
                Timestamp col4 = resultSet.getTimestamp(col++);
                String col5 = resultSet.getString(col++);
                if (resultSet.wasNull())
                    col5 = "";
                String col6 = resultSet.getString(col++);
                if (resultSet.wasNull())
                    col6 = "";
                String col7 = resultSet.getString(col++);
                if (resultSet.wasNull()) {
                    col7 = "";
                }

                results.add(erUser, col1, col2, col3, col4, col5.equals("Y"), col6.equals("Y"), col7.equals("Y"));
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_ALL_USER_ATTRIBUTES, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllUserAttributes", new StringBuilder().append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public AllNonDisabledUsersELO getAllNonDisabledUsers() {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllNonDisabledUsersELO results = new AllNonDisabledUsersELO();
        try {
            stmt = getConnection().prepareStatement(SQL_ALL_NON_DISABLED_USERS);
            int col = 1;
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                UserPK pkUser = new UserPK(resultSet.getInt(col++));

                String textUser = resultSet.getString(col++);

                UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

                String col1 = resultSet.getString(col++);
                String col2 = resultSet.getString(col++);

                results.add(erUser, col1, col2);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_ALL_NON_DISABLED_USERS, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllNonDisabledUsers", new StringBuilder().append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public UserMessageAttributesELO getUserMessageAttributes() {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        UserMessageAttributesELO results = new UserMessageAttributesELO();
        try {
            stmt = getConnection().prepareStatement(SQL_USER_MESSAGE_ATTRIBUTES);
            int col = 1;
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                UserPK pkUser = new UserPK(resultSet.getInt(col++));

                String textUser = resultSet.getString(col++);

                UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

                String col1 = resultSet.getString(col++);
                String col2 = resultSet.getString(col++);
                String col3 = resultSet.getString(col++);
                if (resultSet.wasNull())
                    col3 = "";
                String col4 = resultSet.getString(col++);

                results.add(erUser, col1, col2, col3.equals("Y"), col4);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_USER_MESSAGE_ATTRIBUTES, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getUserMessageAttributes", new StringBuilder().append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public UserMessageAttributesForIdELO getUserMessageAttributesForId(int param1) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        UserMessageAttributesForIdELO results = new UserMessageAttributesForIdELO();
        try {
            stmt = getConnection().prepareStatement(SQL_USER_MESSAGE_ATTRIBUTES_FOR_ID);
            int col = 1;
            stmt.setInt(col++, param1);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                UserPK pkUser = new UserPK(resultSet.getInt(col++));

                String textUser = resultSet.getString(col++);

                UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

                String col1 = resultSet.getString(col++);
                String col2 = resultSet.getString(col++);
                String col3 = resultSet.getString(col++);
                if (resultSet.wasNull())
                    col3 = "";
                String col4 = resultSet.getString(col++);

                results.add(erUser, col1, col2, col3.equals("Y"), col4);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_USER_MESSAGE_ATTRIBUTES_FOR_ID, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getUserMessageAttributesForId", new StringBuilder().append(" UserId=").append(param1).append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public UserMessageAttributesForNameELO getUserMessageAttributesForName(String param1) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        UserMessageAttributesForNameELO results = new UserMessageAttributesForNameELO();
        try {
            stmt = getConnection().prepareStatement(SQL_USER_MESSAGE_ATTRIBUTES_FOR_NAME);
            int col = 1;
            stmt.setString(col++, param1);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                UserPK pkUser = new UserPK(resultSet.getInt(col++));

                String textUser = resultSet.getString(col++);

                UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

                String col1 = resultSet.getString(col++);
                String col2 = resultSet.getString(col++);
                String col3 = resultSet.getString(col++);
                if (resultSet.wasNull())
                    col3 = "";
                String col4 = resultSet.getString(col++);

                results.add(erUser, col1, col2, col3.equals("Y"), col4);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_USER_MESSAGE_ATTRIBUTES_FOR_NAME, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getUserMessageAttributesForName", new StringBuilder().append(" Name=").append(param1).append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public FinanceSystemUserNameELO getFinanceSystemUserName(int param1) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        FinanceSystemUserNameELO results = new FinanceSystemUserNameELO();
        try {
            stmt = getConnection().prepareStatement(SQL_FINANCE_SYSTEM_USER_NAME);
            int col = 1;
            stmt.setInt(col++, param1);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                UserPK pkUser = new UserPK(resultSet.getInt(col++));

                String textUser = resultSet.getString(col++);

                UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

                String col1 = resultSet.getString(col++);

                results.add(erUser, col1);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_FINANCE_SYSTEM_USER_NAME, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getFinanceSystemUserName", new StringBuilder().append(" UserId=").append(param1).append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public UsersWithSecurityStringELO getUsersWithSecurityString(String param1) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        UsersWithSecurityStringELO results = new UsersWithSecurityStringELO();
        try {
            stmt = getConnection().prepareStatement(SQL_USERS_WITH_SECURITY_STRING);
            int col = 1;
            stmt.setString(col++, param1);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                results.add(resultSet.getInt(col++), resultSet.getString(col++));
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_USERS_WITH_SECURITY_STRING, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getUsersWithSecurityString", new StringBuilder().append(" SecurityString=").append(param1).append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public UserStrikeCountELO getUserStrikeCount(int param1) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        UserStrikeCountELO results = new UserStrikeCountELO();
        try {
            stmt = getConnection().prepareStatement(SQL_USER_STRIKE_COUNT);
            int col = 1;
            stmt.setInt(col++, param1);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                UserPK pkUser = new UserPK(resultSet.getInt(col++));

                String textUser = resultSet.getString(col++);

                UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

                int col1 = resultSet.getInt(col++);

                results.add(erUser, col1);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_USER_STRIKE_COUNT, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getUserStrikeCount", new StringBuilder().append(" UserId=").append(param1).append(" items=").append(results.size()).toString());
        }

        return results;
    }

    private void deleteDependants(UserPK pk) {
        Set emptyStrings = Collections.emptySet();
        deleteDependants(pk, emptyStrings);
    }

    private void deleteDependants(UserPK pk, Set<String> exclusionTables) {
        for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--) {
            if (!exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0])) {
                Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

                PreparedStatement stmt = null;

                int resultCount = 0;
                String s = null;
                try {
                    s = new StringBuilder().append(SQL_DELETE_CHILDRENS_DEPENDANTS[i][1]).append(SQL_DELETE_DEPENDANT_CRITERIA).toString();

                    if (_log.isDebugEnabled()) {
                        _log.debug("deleteDependants", s);
                    }
                    stmt = getConnection().prepareStatement(s);

                    int col = 1;
                    stmt.setInt(col++, pk.getUserId());

                    resultCount = stmt.executeUpdate();
                } catch (SQLException sqle) {
                    throw handleSQLException(pk, s, sqle);
                } finally {
                    closeStatement(stmt);
                    closeConnection();

                    if (timer != null)
                        timer.logDebug("deleteDependants", new StringBuilder().append("A[").append(i).append("] count=").append(resultCount).toString());
                }
            }
        }
        for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--) {
            if (!exclusionTables.contains(SQL_DELETE_CHILDREN[i][0])) {
                Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

                PreparedStatement stmt = null;

                int resultCount = 0;
                String s = null;
                try {
                    s = SQL_DELETE_CHILDREN[i][1];

                    if (_log.isDebugEnabled()) {
                        _log.debug("deleteDependants", s);
                    }
                    stmt = getConnection().prepareStatement(s);

                    int col = 1;
                    stmt.setInt(col++, pk.getUserId());

                    resultCount = stmt.executeUpdate();
                } catch (SQLException sqle) {
                    throw handleSQLException(pk, s, sqle);
                } finally {
                    closeStatement(stmt);
                    closeConnection();

                    if (timer != null)
                        timer.logDebug("deleteDependants", new StringBuilder().append("B[").append(i).append("] count=").append(resultCount).toString());
                }
            }
        }
    }

    public UserEVO getDetails(UserPK pk, String dependants) throws ValidationException {
        return getDetails(new UserCK(pk), dependants);
    }

    public UserEVO getDetails(UserCK paramCK, String dependants) throws ValidationException {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

        if (mDetails == null) {
            doLoad(paramCK.getUserPK());
        } else if (!mDetails.getPK().equals(paramCK.getUserPK())) {
            doLoad(paramCK.getUserPK());
        } else if (!checkIfValid()) {
            _log.info("getDetails", new StringBuilder().append("[ALERT] UserEVO ").append(mDetails.getPK()).append(" no longer valid - reloading").toString());

            doLoad(paramCK.getUserPK());
        }

        if ((dependants.indexOf("<0>") > -1) && (!mDetails.isUserRolesAllItemsLoaded())) {
            mDetails.setUserRoles(getUserRoleDAO().getAll(mDetails.getUserId(), dependants, mDetails.getUserRoles()));

            mDetails.setUserRolesAllItemsLoaded(true);
        }

        if ((dependants.indexOf("<1>") > -1) && (!mDetails.isUserPreferencesAllItemsLoaded())) {
            mDetails.setUserPreferences(getUserPreferenceDAO().getAll(mDetails.getUserId(), dependants, mDetails.getUserPreferences()));

            mDetails.setUserPreferencesAllItemsLoaded(true);
        }

        if ((dependants.indexOf("<2>") > -1) && (!mDetails.isDataEntryProfilesAllItemsLoaded())) {
            mDetails.setDataEntryProfiles(getDataEntryProfileDAO().getAll(mDetails.getUserId(), dependants, mDetails.getDataEntryProfiles()));

            mDetails.setDataEntryProfilesAllItemsLoaded(true);
        }

        if ((dependants.indexOf("<4>") > -1) && (!mDetails.isChallengeQuestionsAllItemsLoaded())) {
            mDetails.setChallengeQuestions(getChallengeQuestionDAO().getAll(mDetails.getUserId(), dependants, mDetails.getChallengeQuestions()));

            mDetails.setChallengeQuestionsAllItemsLoaded(true);
        }

        if ((dependants.indexOf("<5>") > -1) && (!mDetails.isResetLinkAllItemsLoaded())) {
            mDetails.setResetLink(getUserResetLinkDAO().getAll(mDetails.getUserId(), dependants, mDetails.getResetLink()));

            mDetails.setResetLinkAllItemsLoaded(true);
        }

        if ((paramCK instanceof UserRoleCK)) {
            if (mDetails.getUserRoles() == null) {
                mDetails.loadUserRolesItem(getUserRoleDAO().getDetails(paramCK, dependants));
            } else {
                UserRolePK pk = ((UserRoleCK) paramCK).getUserRolePK();
                UserRoleEVO evo = mDetails.getUserRolesItem(pk);
                if (evo == null) {
                    mDetails.loadUserRolesItem(getUserRoleDAO().getDetails(paramCK, dependants));
                }
            }
        } else if ((paramCK instanceof UserPreferenceCK)) {
            if (mDetails.getUserPreferences() == null) {
                mDetails.loadUserPreferencesItem(getUserPreferenceDAO().getDetails(paramCK, dependants));
            } else {
                UserPreferencePK pk = ((UserPreferenceCK) paramCK).getUserPreferencePK();
                UserPreferenceEVO evo = mDetails.getUserPreferencesItem(pk);
                if (evo == null) {
                    mDetails.loadUserPreferencesItem(getUserPreferenceDAO().getDetails(paramCK, dependants));
                }
            }
        } else if ((paramCK instanceof DataEntryProfileCK)) {
            if (mDetails.getDataEntryProfiles() == null) {
                mDetails.loadDataEntryProfilesItem(getDataEntryProfileDAO().getDetails(paramCK, dependants));
            } else {
                DataEntryProfilePK pk = ((DataEntryProfileCK) paramCK).getDataEntryProfilePK();
                DataEntryProfileEVO evo = mDetails.getDataEntryProfilesItem(pk);
                if (evo == null)
                    mDetails.loadDataEntryProfilesItem(getDataEntryProfileDAO().getDetails(paramCK, dependants));
                else {
                    getDataEntryProfileDAO().getDetails(paramCK, evo, dependants);
                }
            }
        } else if ((paramCK instanceof ChallengeQuestionCK)) {
            if (mDetails.getChallengeQuestions() == null) {
                mDetails.loadChallengeQuestionsItem(getChallengeQuestionDAO().getDetails(paramCK, dependants));
            } else {
                ChallengeQuestionPK pk = ((ChallengeQuestionCK) paramCK).getChallengeQuestionPK();
                ChallengeQuestionEVO evo = mDetails.getChallengeQuestionsItem(pk);
                if (evo == null) {
                    mDetails.loadChallengeQuestionsItem(getChallengeQuestionDAO().getDetails(paramCK, dependants));
                }
            }
        } else if ((paramCK instanceof UserResetLinkCK)) {
            if (mDetails.getResetLink() == null) {
                mDetails.loadResetLinkItem(getUserResetLinkDAO().getDetails(paramCK, dependants));
            } else {
                UserResetLinkPK pk = ((UserResetLinkCK) paramCK).getUserResetLinkPK();
                UserResetLinkEVO evo = mDetails.getResetLinkItem(pk);
                if (evo == null) {
                    mDetails.loadResetLinkItem(getUserResetLinkDAO().getDetails(paramCK, dependants));
                }
            }
        }

        UserEVO details = new UserEVO();
        details = mDetails.deepClone();

        if (timer != null) {
            timer.logDebug("getDetails", new StringBuilder().append(paramCK).append(" ").append(dependants).toString());
        }
        return details;
    }

    private boolean checkIfValid() {
        boolean stillValid = false;
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement("select VERSION_NUM from USR where   USER_ID = ?");
            int col = 1;
            stmt.setInt(col++, mDetails.getUserId());

            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                throw new RuntimeException(new StringBuilder().append(getEntityName()).append(" checkIfValid ").append(mDetails.getPK()).append(" not found").toString());
            }
            col = 1;
            int dbVersionNum = resultSet.getInt(col++);

            if (dbVersionNum == mDetails.getVersionNum())
                stillValid = true;
        } catch (SQLException sqle) {
            throw handleSQLException(mDetails.getPK(), "select VERSION_NUM from USR where   USER_ID = ?", sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();

            if (timer != null) {
                timer.logDebug("checkIfValid", mDetails.getPK());
            }
        }
        return stillValid;
    }

    public UserEVO getDetails(String dependants) throws ValidationException {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

        if (!checkIfValid()) {
            _log.info("getDetails", new StringBuilder().append("User ").append(mDetails.getPK()).append(" no longer valid - reloading").toString());
            doLoad(mDetails.getPK());
        }

        getDependants(mDetails, dependants);

        UserEVO details = mDetails.deepClone();

        if (timer != null) {
            timer.logDebug("getDetails", new StringBuilder().append(mDetails.getPK()).append(" ").append(dependants).toString());
        }
        return details;
    }

    protected UserRoleDAO getUserRoleDAO() {
        if (mUserRoleDAO == null) {
            if (mDataSource != null)
                mUserRoleDAO = new UserRoleDAO(mDataSource);
            else {
                mUserRoleDAO = new UserRoleDAO(getConnection());
            }
        }
        return mUserRoleDAO;
    }

    protected UserPreferenceDAO getUserPreferenceDAO() {
        if (mUserPreferenceDAO == null) {
            if (mDataSource != null)
                mUserPreferenceDAO = new UserPreferenceDAO(mDataSource);
            else {
                mUserPreferenceDAO = new UserPreferenceDAO(getConnection());
            }
        }
        return mUserPreferenceDAO;
    }

    protected DataEntryProfileDAO getDataEntryProfileDAO() {
        if (mDataEntryProfileDAO == null) {
            if (mDataSource != null)
                mDataEntryProfileDAO = new DataEntryProfileDAO(mDataSource);
            else {
                mDataEntryProfileDAO = new DataEntryProfileDAO(getConnection());
            }
        }
        return mDataEntryProfileDAO;
    }

    protected ChallengeQuestionDAO getChallengeQuestionDAO() {
        if (mChallengeQuestionDAO == null) {
            if (mDataSource != null)
                mChallengeQuestionDAO = new ChallengeQuestionDAO(mDataSource);
            else {
                mChallengeQuestionDAO = new ChallengeQuestionDAO(getConnection());
            }
        }
        return mChallengeQuestionDAO;
    }

    protected UserResetLinkDAO getUserResetLinkDAO() {
        if (mUserResetLinkDAO == null) {
            if (mDataSource != null)
                mUserResetLinkDAO = new UserResetLinkDAO(mDataSource);
            else {
                mUserResetLinkDAO = new UserResetLinkDAO(getConnection());
            }
        }
        return mUserResetLinkDAO;
    }

    public String getEntityName() {
        return "User";
    }

    public UserRef getRef(UserPK paramUserPK) throws ValidationException {
        UserEVO evo = getDetails(paramUserPK, "");
        return evo.getEntityRef();
    }

    public void getDependants(Collection c, String dependants) {
        if (c == null)
            return;
        Iterator iter = c.iterator();
        while (iter.hasNext()) {
            UserEVO evo = (UserEVO) iter.next();
            getDependants(evo, dependants);
        }
    }

    public void getDependants(UserEVO evo, String dependants) {
        if (evo.getUserId() < 1) {
            return;
        }

        if (dependants.indexOf("<0>") > -1) {
            if (!evo.isUserRolesAllItemsLoaded()) {
                evo.setUserRoles(getUserRoleDAO().getAll(evo.getUserId(), dependants, evo.getUserRoles()));

                evo.setUserRolesAllItemsLoaded(true);
            }

        }

        if (dependants.indexOf("<1>") > -1) {
            if (!evo.isUserPreferencesAllItemsLoaded()) {
                evo.setUserPreferences(getUserPreferenceDAO().getAll(evo.getUserId(), dependants, evo.getUserPreferences()));

                evo.setUserPreferencesAllItemsLoaded(true);
            }

        }

        if ((dependants.indexOf("<2>") > -1) || (dependants.indexOf("<3>") > -1)) {
            if (!evo.isDataEntryProfilesAllItemsLoaded()) {
                evo.setDataEntryProfiles(getDataEntryProfileDAO().getAll(evo.getUserId(), dependants, evo.getDataEntryProfiles()));

                evo.setDataEntryProfilesAllItemsLoaded(true);
            }
            getDataEntryProfileDAO().getDependants(evo.getDataEntryProfiles(), dependants);
        }

        if (dependants.indexOf("<4>") > -1) {
            if (!evo.isChallengeQuestionsAllItemsLoaded()) {
                evo.setChallengeQuestions(getChallengeQuestionDAO().getAll(evo.getUserId(), dependants, evo.getChallengeQuestions()));

                evo.setChallengeQuestionsAllItemsLoaded(true);
            }

        }

        if (dependants.indexOf("<5>") > -1) {
            if (!evo.isResetLinkAllItemsLoaded()) {
                evo.setResetLink(getUserResetLinkDAO().getAll(evo.getUserId(), dependants, evo.getResetLink()));

                evo.setResetLinkAllItemsLoaded(true);
            }
        }
    }

    public UserPK findByUserID(String id) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

        getConnection();

        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        UserPK theUserPK = null;
        try {
            stmt = mConnection.prepareStatement("select USER_ID from USR where    NAME = ? ");

            int col = 1;
            stmt.setString(col, id);

            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                col = 1;
                theUserPK = new UserPK(resultSet.getInt(col++));
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new RuntimeException(getEntityName(), sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("findByUserID");
        }
        return theUserPK;
    }

    public UserPK findByLogonAlias(String alias) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

        getConnection();

        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        UserPK theUserPK = null;
        try {
            stmt = mConnection.prepareStatement("select USER_ID from USR where    lower(LOGON_ALIAS) = ? ");

            int col = 1;
            stmt.setString(col, alias.toLowerCase());

            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                col = 1;
                theUserPK = new UserPK(resultSet.getInt(col++));
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new RuntimeException(getEntityName(), sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("findByLogonAlias");
        }
        return theUserPK;
    }

    public EntityList getAllRespAreaAssignments(String pName, String pFullName, String pModel, String pLocation) {
        getConnection();

        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        AllUsersAssignmentsELO elo = new AllUsersAssignmentsELO();
        try {
            stmt = mConnection
                    .prepareStatement("SELECT    a.name,   a.full_name,   d.vis_id,   c.vis_id,   c.description,    CASE b.READ_ONLY        WHEN NULL THEN 'W'        WHEN 'N' THEN 'W'        WHEN ' ' THEN 'W'        ELSE 'R'    END FROM    usr a,   budget_user b,   structure_element c,   model d WHERE    a.user_id = b.user_id    AND b.structure_element_id = c.structure_element_id    AND b.model_id = d.model_id    AND d.budget_hierarchy_id = c.structure_id    AND a.name LIKE ?    AND a.full_name LIKE ?    AND d.vis_id LIKE ?    AND c.vis_id LIKE ? ORDER BY    upper(a.full_name), d.vis_id, c.vis_id");

            int col = 1;
            stmt.setString(col++, pName);
            stmt.setString(col++, pFullName);
            stmt.setString(col++, pModel);
            stmt.setString(col++, pLocation);
            resultSet = stmt.executeQuery();

            String read = "";

            while (resultSet.next()) {
                col = 1;

                String name = resultSet.getString(col++);
                String fullname = resultSet.getString(col++);
                String modelid = resultSet.getString(col++);
                String elementid = resultSet.getString(col++);
                String elementdesc = resultSet.getString(col++);
                read = resultSet.getString(col++);

                elo.add(name, fullname, modelid, elementid, elementdesc, read);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new RuntimeException(getEntityName(), sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        return elo;
    }

    public Map<UserPK, AllUsersELO> getMapOfAllUsers() {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        Map resultMap = new HashMap();
        AllUsersELO row = null;
        try {
            stmt = getConnection().prepareStatement(SQL_ALL_USERS);
            int col = 1;
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 2;

                row = new AllUsersELO();

                UserPK pkUser = new UserPK(resultSet.getInt(col++));

                String textUser = resultSet.getString(col++);

                UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

                String col1 = resultSet.getString(col++);
                String col2 = resultSet.getString(col++);
                if (resultSet.wasNull()) {
                    col2 = "";
                }

                row.add(erUser, col1, col2.equals("Y"));

                resultMap.put(pkUser, row);
            }
        } catch (SQLException sqle) {
            throw handleSQLException(SQL_ALL_USERS, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllUsers", new StringBuilder().append(" items=").append(resultMap.size()).toString());
        }

        return resultMap;
    }

    public AllUserDetailsReportELO getAllUserDetailsReport(String name, String fullname, String email, boolean disabled) {
        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

        String sqlStatement = "";
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        AllUserDetailsReportELO results = new AllUserDetailsReportELO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select USR.NAME, USR.FULL_NAME ,USR.E_MAIL_ADDRESS ,USR.USER_DISABLED ,ROLE.VIS_ID ");
            sb.append("from USR ,ROLE ,USER_ROLE ");
            sb.append("where USR.NAME like ? and USR.FULL_NAME like ? and ");
            if (email.equals("%"))
                sb.append("( USR.E_MAIL_ADDRESS like ? or USR.E_MAIL_ADDRESS is null ) ");
            else {
                sb.append("USR.E_MAIL_ADDRESS like ? ");
            }
            sb.append("and USR.USER_DISABLED like ? and USR.USER_ID = USER_ROLE.USER_ID and USER_ROLE.ROLE_ID = ROLE.ROLE_ID");

            sqlStatement = sb.toString();
            stmt = getConnection().prepareStatement(sqlStatement);
            int col = 1;
            stmt.setString(col++, name);
            stmt.setString(col++, fullname);
            stmt.setString(col++, email);
            if (disabled)
                stmt.setString(col++, "Y");
            else {
                stmt.setString(col++, " ");
            }
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                col = 1;

                results.add(resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++), resultSet.getString(col++).equals("Y"), resultSet.getString(col++));
            }

        } catch (SQLException sqle) {
            throw handleSQLException(sqlStatement, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getAllUserDetailsReport");
        }
        return results;
    }

    public UserMessageAttributesForIdELO getUserMessageAttributesForMultipleIds(String[] params) {
        StringBuilder sbSQL = new StringBuilder("select 0       ,USR.USER_ID      ,USR.NAME      ,USR.NAME      ,USR.FULL_NAME      ,USR.USER_DISABLED      ,USR.E_MAIL_ADDRESS from USR where 1=1 and  USR.NAME in('");

        for (int i = 0; i < params.length; i++) {
            sbSQL.append(params[i]);
            if (i < params.length - 1)
                sbSQL.append("','");
            else if (i == params.length - 1) {
                sbSQL.append("')");
            }
        }

        Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        UserMessageAttributesForIdELO results = new UserMessageAttributesForIdELO();
        try {
            stmt = getConnection().prepareStatement(sbSQL.toString());
            resultSet = stmt.executeQuery(sbSQL.toString());
            while (resultSet.next()) {
                int col = 2;

                UserPK pkUser = new UserPK(resultSet.getInt(col++));

                String textUser = resultSet.getString(col++);

                UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

                String col1 = resultSet.getString(col++);
                String col2 = resultSet.getString(col++);
                String col3 = resultSet.getString(col++);
                if (resultSet.wasNull())
                    col3 = "";
                String col4 = resultSet.getString(col++);

                results.add(erUser, col1, col2, col3.equals("Y"), col4);
            }

        } catch (SQLException sqle) {
            throw handleSQLException(SQL_USER_MESSAGE_ATTRIBUTES_FOR_ID, sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null) {
            timer.logDebug("getUserMessageAttributesForId", new StringBuilder().append(" UserId=").append(sbSQL.substring(0, sbSQL.length())).append(" items=").append(results.size()).toString());
        }

        return results;
    }

    public void resetStrikes(UserEVO evo) {
        PreparedStatement stmt = null;
        try {
            stmt = getConnection().prepareStatement(SQL_RESET_USER_STRIKE);
            stmt.setInt(1, evo.getUserId());
            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw handleSQLException(SQL_RESET_USER_STRIKE, sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();
        }
    }

    /**
     * Return true if user can only read
     * 
     * @param userId
     * @return true if none of structure elements is read only
     */
    public boolean isUserReadOnly(int userId) {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement("SELECT READ_ONLY FROM BUDGET_USER WHERE USER_ID = ?");
            stmt.setInt(1, userId);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                if (!resultSet.getString(1).equals("Y")) {
                    return false;
                }
            }
        } catch (SQLException sqle) {
            throw handleSQLException("isUserReadOnly", sqle);
        }
        // don't close connection
        return true;
    }

    /**
     * Sets readOnly access to documents
     * 
     * @param userId
     * @param readOnly
     */
    public void setUserReadOnly(int userId, boolean readOnly) {
        PreparedStatement stmt = null;
        try {
            stmt = getConnection().prepareStatement("UPDATE BUDGET_USER SET READ_ONLY = ? WHERE USER_ID = ?");
            stmt.setString(1, readOnly == true ? "Y" : " ");
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw handleSQLException("setUserReadOnly", sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();
        }
    }
    
    /**
     * Add users logged details to database
     * 
     * @param usrId
     */
    public void addUserLogHistory(String userName) {
        PreparedStatement stmt = null;
        try {
            stmt = getConnection().prepareStatement("insert into USR_LOGGED_HISTORY ( NAME, LOGGED_TIME) values ( ?, ?)");
            int col = 1;
            stmt.setString(col++, userName );
            Timestamp g = new Timestamp(new Date().getTime());
            stmt.setTimestamp(col++, g);
            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw handleSQLException("setUserReadOnly", sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();
        }
    }
}