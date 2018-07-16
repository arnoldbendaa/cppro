package com.cedar.cp.ejb.impl.user;

import com.cedar.cp.api.base.DuplicateNameValidationException;
/*      */
import com.cedar.cp.api.base.ValidationException;
/*      */
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.user.DataEntryProfileRef;
/*      */
import com.cedar.cp.dto.user.AllDataEntryProfilesELO;
import com.cedar.cp.dto.user.AllSystemDataEntryProfilesForUserELO;
/*      */
import com.cedar.cp.dto.user.AllDataEntryProfilesForFormELO;
/*      */
import com.cedar.cp.dto.user.AllDataEntryProfilesForUserELO;
/*      */
import com.cedar.cp.dto.user.AllUsersForDataEntryProfilesForModelELO;
/*      */
import com.cedar.cp.dto.user.DataEntryProfileCK;
/*      */
import com.cedar.cp.dto.user.DataEntryProfileHistoryCK;
/*      */
import com.cedar.cp.dto.user.DataEntryProfileHistoryPK;
/*      */
import com.cedar.cp.dto.user.DataEntryProfileHistoryRefImpl;
/*      */
import com.cedar.cp.dto.user.DataEntryProfilePK;
/*      */
import com.cedar.cp.dto.user.DataEntryProfileRefImpl;
/*      */
import com.cedar.cp.dto.user.DefaultDataEntryProfileELO;
/*      */
import com.cedar.cp.dto.user.UserCK;
/*      */
import com.cedar.cp.dto.user.UserPK;
/*      */
import com.cedar.cp.dto.user.UserRefImpl;
/*      */
import com.cedar.cp.ejb.impl.base.AbstractDAO;
/*      */
import com.cedar.cp.util.Log;
/*      */
import com.cedar.cp.util.Timer;

/*      */
import java.sql.Connection;
/*      */
import java.sql.PreparedStatement;
/*      */
import java.sql.ResultSet;
/*      */
import java.sql.SQLException;
/*      */
import java.sql.Timestamp;
/*      */
import java.util.ArrayList;
import java.util.Calendar;
/*      */
import java.util.Collection;
/*      */
import java.util.Collections;
/*      */
import java.util.Date;
/*      */
import java.util.Iterator;
import java.util.List;
/*      */
import java.util.ListIterator;
/*      */
import java.util.Map;
/*      */
import java.util.Set;

import javax.sql.DataSource;

public class DataEntryProfileDAO extends AbstractDAO {

	/* 38 */Log _log = new Log(getClass());
	/*      */private static final String SQL_SELECT_COLUMNS = "select DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID,DATA_ENTRY_PROFILE.VIS_ID,DATA_ENTRY_PROFILE.USER_ID,DATA_ENTRY_PROFILE.MODEL_ID,DATA_ENTRY_PROFILE.AUTO_OPEN_DEPTH,DATA_ENTRY_PROFILE.DESCRIPTION,DATA_ENTRY_PROFILE.XMLFORM_ID,DATA_ENTRY_PROFILE.FILL_DISPLAY_AREA,DATA_ENTRY_PROFILE.SHOW_BOLD_SUMMARIES,DATA_ENTRY_PROFILE.SHOW_HORIZONTAL_LINES,DATA_ENTRY_PROFILE.STRUCTURE_ID0,DATA_ENTRY_PROFILE.STRUCTURE_ID1,DATA_ENTRY_PROFILE.STRUCTURE_ID2,DATA_ENTRY_PROFILE.STRUCTURE_ID3,DATA_ENTRY_PROFILE.STRUCTURE_ID4,DATA_ENTRY_PROFILE.STRUCTURE_ID5,DATA_ENTRY_PROFILE.STRUCTURE_ID6,DATA_ENTRY_PROFILE.STRUCTURE_ID7,DATA_ENTRY_PROFILE.STRUCTURE_ID8,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID0,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID1,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID2,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID3,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID4,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID5,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID6,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID7,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID8,DATA_ENTRY_PROFILE.ELEMENT_LABEL0,DATA_ENTRY_PROFILE.ELEMENT_LABEL1,DATA_ENTRY_PROFILE.ELEMENT_LABEL2,DATA_ENTRY_PROFILE.ELEMENT_LABEL3,DATA_ENTRY_PROFILE.ELEMENT_LABEL4,DATA_ENTRY_PROFILE.ELEMENT_LABEL5,DATA_ENTRY_PROFILE.ELEMENT_LABEL6,DATA_ENTRY_PROFILE.ELEMENT_LABEL7,DATA_ENTRY_PROFILE.ELEMENT_LABEL8,DATA_ENTRY_PROFILE.DATA_TYPE,DATA_ENTRY_PROFILE.VERSION_NUM,DATA_ENTRY_PROFILE.UPDATED_BY_USER_ID,DATA_ENTRY_PROFILE.UPDATED_TIME,DATA_ENTRY_PROFILE.CREATED_TIME";
	/*      */protected static final String SQL_LOAD = " from DATA_ENTRY_PROFILE where    DATA_ENTRY_PROFILE_ID = ? ";
	/*      */protected static final String SQL_CREATE = "insert into DATA_ENTRY_PROFILE ( DATA_ENTRY_PROFILE_ID,VIS_ID,USER_ID,MODEL_ID,AUTO_OPEN_DEPTH,DESCRIPTION,XMLFORM_ID,FILL_DISPLAY_AREA,SHOW_BOLD_SUMMARIES,SHOW_HORIZONTAL_LINES,STRUCTURE_ID0,STRUCTURE_ID1,STRUCTURE_ID2,STRUCTURE_ID3,STRUCTURE_ID4,STRUCTURE_ID5,STRUCTURE_ID6,STRUCTURE_ID7,STRUCTURE_ID8,STRUCTURE_ELEMENT_ID0,STRUCTURE_ELEMENT_ID1,STRUCTURE_ELEMENT_ID2,STRUCTURE_ELEMENT_ID3,STRUCTURE_ELEMENT_ID4,STRUCTURE_ELEMENT_ID5,STRUCTURE_ELEMENT_ID6,STRUCTURE_ELEMENT_ID7,STRUCTURE_ELEMENT_ID8,ELEMENT_LABEL0,ELEMENT_LABEL1,ELEMENT_LABEL2,ELEMENT_LABEL3,ELEMENT_LABEL4,ELEMENT_LABEL5,ELEMENT_LABEL6,ELEMENT_LABEL7,ELEMENT_LABEL8,DATA_TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	/*      */protected static final String SQL_DUPLICATE_VALUE_CHECK_DATAENTRYPROFILENAME = "select count(*) from DATA_ENTRY_PROFILE where    USER_ID = ? AND MODEL_ID = ? AND VIS_ID = ? and not(    DATA_ENTRY_PROFILE_ID = ? )";
	/*      */protected static final String SQL_STORE = "update DATA_ENTRY_PROFILE set VIS_ID = ?,USER_ID = ?,MODEL_ID = ?,AUTO_OPEN_DEPTH = ?,DESCRIPTION = ?,XMLFORM_ID = ?,FILL_DISPLAY_AREA = ?,SHOW_BOLD_SUMMARIES = ?,SHOW_HORIZONTAL_LINES = ?,STRUCTURE_ID0 = ?,STRUCTURE_ID1 = ?,STRUCTURE_ID2 = ?,STRUCTURE_ID3 = ?,STRUCTURE_ID4 = ?,STRUCTURE_ID5 = ?,STRUCTURE_ID6 = ?,STRUCTURE_ID7 = ?,STRUCTURE_ID8 = ?,STRUCTURE_ELEMENT_ID0 = ?,STRUCTURE_ELEMENT_ID1 = ?,STRUCTURE_ELEMENT_ID2 = ?,STRUCTURE_ELEMENT_ID3 = ?,STRUCTURE_ELEMENT_ID4 = ?,STRUCTURE_ELEMENT_ID5 = ?,STRUCTURE_ELEMENT_ID6 = ?,STRUCTURE_ELEMENT_ID7 = ?,STRUCTURE_ELEMENT_ID8 = ?,ELEMENT_LABEL0 = ?,ELEMENT_LABEL1 = ?,ELEMENT_LABEL2 = ?,ELEMENT_LABEL3 = ?,ELEMENT_LABEL4 = ?,ELEMENT_LABEL5 = ?,ELEMENT_LABEL6 = ?,ELEMENT_LABEL7 = ?,ELEMENT_LABEL8 = ?,DATA_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ? where    DATA_ENTRY_PROFILE_ID = ? AND VERSION_NUM = ?";
	/*      */protected static final String SQL_CHECK_VERSION_NUM = "select VERSION_NUM from DATA_ENTRY_PROFILE where DATA_ENTRY_PROFILE_ID = ?";
	/* 676 */protected static String SQL_ALL_DATA_ENTRY_PROFILES = "select 0       ,USR.USER_ID      ,USR.NAME      ,DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID      ,DATA_ENTRY_PROFILE.VIS_ID      ,DATA_ENTRY_PROFILE.DESCRIPTION from DATA_ENTRY_PROFILE    ,USR where 1=1   and DATA_ENTRY_PROFILE.USER_ID = USR.USER_ID  order by DATA_ENTRY_PROFILE.VIS_ID";

    protected static String SQL_ALL_DATA_ENTRY_PROFILES_FOR_USER = "select 0,      USR.USER_ID,      USR.NAME,      DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID      ,DATA_ENTRY_PROFILE.VIS_ID      ,DATA_ENTRY_PROFILE.DESCRIPTION,      DATA_ENTRY_PROFILE.XMLFORM_ID,     DATA_ENTRY_PROFILE.STRUCTURE_ID0,    DATA_ENTRY_PROFILE.STRUCTURE_ID1,    DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID0,    DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID1,    DATA_ENTRY_PROFILE.ELEMENT_LABEL0,    DATA_ENTRY_PROFILE.ELEMENT_LABEL1,    DATA_ENTRY_PROFILE.DATA_TYPE, FORMS.TYPE  from    DATA_ENTRY_PROFILE, USR, (select xml_form.xml_form_id, xml_form.type from xml_form, xml_form_user_link, budget_cycle_link where xml_form.xml_form_id = xml_form_user_link.xml_form_id (+) and (xml_form_user_link.user_id is null or xml_form_user_link.user_id = ?) and budget_cycle_link.budget_cycle_id = ? and budget_cycle_link.xml_form_id=xml_form.xml_form_id) FORMS where 1=1 and DATA_ENTRY_PROFILE.MOBILE <>'Y' AND    DATA_ENTRY_PROFILE.XMLFORM_ID = FORMS.XML_FORM_ID AND    DATA_ENTRY_PROFILE.USER_ID = USR.USER_ID  AND    DATA_ENTRY_PROFILE.USER_ID = ?  AND   (DATA_ENTRY_PROFILE.BUDGET_CYCLE_ID = ? OR DATA_ENTRY_PROFILE.BUDGET_CYCLE_ID=0) AND    DATA_ENTRY_PROFILE.MODEL_ID = ? order by DATA_ENTRY_PROFILE.VIS_ID";

	/* 896 */protected static String SQL_ALL_USERS_FOR_DATA_ENTRY_PROFILES_FOR_MODEL = "select 0       ,USR.USER_ID      ,USR.NAME      ,DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID      ,DATA_ENTRY_PROFILE.VIS_ID from DATA_ENTRY_PROFILE    ,USR where 1=1   and DATA_ENTRY_PROFILE.USER_ID = USR.USER_ID  and  DATA_ENTRY_PROFILE.MODEL_ID = ?";
	/*      */protected static String SQL_ALL_SYSTEM_DATA_ENTRY_PROFILES_FOR_USER = "select 0       ,USR.USER_ID      ,USR.NAME      ,DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID      ,DATA_ENTRY_PROFILE.VIS_ID      ,DATA_ENTRY_PROFILE.DESCRIPTION      ,DATA_ENTRY_PROFILE.XMLFORM_ID from DATA_ENTRY_PROFILE    ,USR where 1=1   and DATA_ENTRY_PROFILE.USER_ID = USR.USER_ID  and  DATA_ENTRY_PROFILE.USER_ID = ? AND DATA_ENTRY_PROFILE.MODEL_ID = ? order by DATA_ENTRY_PROFILE.VIS_ID";
	/* 1003 */protected static String SQL_ALL_DATA_ENTRY_PROFILES_FOR_FORM = "select 0       ,USR.USER_ID      ,USR.NAME      ,DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID      ,DATA_ENTRY_PROFILE.VIS_ID      ,DATA_ENTRY_PROFILE.DESCRIPTION from DATA_ENTRY_PROFILE    ,USR where 1=1   and DATA_ENTRY_PROFILE.USER_ID = USR.USER_ID  and  DATA_ENTRY_PROFILE.XMLFORM_ID = ?";
	/*      */
	
	protected static String SQL_DEFAULT_DATA_ENTRY_PROFILE = "select 0       ,USR.USER_ID      ,USR.NAME      ,DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID      ,DATA_ENTRY_PROFILE.VIS_ID      ,DATA_ENTRY_PROFILE_HISTORY.USER_ID      ,DATA_ENTRY_PROFILE_HISTORY.MODEL_ID      ,DATA_ENTRY_PROFILE_HISTORY.BUDGET_LOCATION_ELEMENT_ID      ,DATA_ENTRY_PROFILE_HISTORY.BUDGET_CYCLE_ID      ,DATA_ENTRY_PROFILE.DESCRIPTION from DATA_ENTRY_PROFILE    ,USR    ,DATA_ENTRY_PROFILE_HISTORY where 1=1   and DATA_ENTRY_PROFILE.USER_ID = USR.USER_ID  and  DATA_ENTRY_PROFILE.USER_ID = ? AND DATA_ENTRY_PROFILE.MODEL_ID = ? AND DATA_ENTRY_PROFILE.BUDGET_CYCLE_ID = ? AND DATA_ENTRY_PROFILE_HISTORY.BUDGET_LOCATION_ELEMENT_ID = ? AND DATA_ENTRY_PROFILE.USER_ID = DATA_ENTRY_PROFILE_HISTORY.USER_ID AND DATA_ENTRY_PROFILE.MODEL_ID = DATA_ENTRY_PROFILE_HISTORY.MODEL_ID AND DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID = DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID AND DATA_ENTRY_PROFILE.XMLFORM_ID IN (select budget_cycle_link.xml_form_id from budget_cycle_link where data_entry_profile.budget_cycle_id=?)";
		
	protected static final String SQL_DELETE_BATCH = "delete from DATA_ENTRY_PROFILE where    DATA_ENTRY_PROFILE_ID = ? ";
	
	/* 1386 */private static String[][] SQL_DELETE_CHILDREN = { { "DATA_ENTRY_PROFILE_HISTORY", "delete from DATA_ENTRY_PROFILE_HISTORY where     DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID = ? " } };
	/*      */
	/* 1395 */private static String[][] SQL_DELETE_CHILDRENS_DEPENDANTS = new String[0][];
	/*      */
	/* 1399 */private static String SQL_DELETE_DEPENDANT_CRITERIA = "and DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID = ?)";
	/*      */public static final String SQL_BULK_GET_ALL = " from DATA_ENTRY_PROFILE where 1=1 and DATA_ENTRY_PROFILE.USER_ID = ? order by  DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID";
	/*      */protected static final String SQL_GET_ALL = " from DATA_ENTRY_PROFILE where    USER_ID = ? ";
	/* 1873 */protected static String SQL_CHECK_HISTORY = "select DATA_ENTRY_PROFILE_ID  from DATA_ENTRY_PROFILE_HISTORY where USER_ID = ?   and MODEL_ID = ?   and BUDGET_LOCATION_ELEMENT_ID = ?   and BUDGET_CYCLE_ID = ?";
	/*      */
	/* 1880 */protected static String SQL_INSERT_HISTORY = "insert into DATA_ENTRY_PROFILE_HISTORY       (USER_ID, MODEL_ID, BUDGET_LOCATION_ELEMENT_ID, BUDGET_CYCLE_ID, DATA_ENTRY_PROFILE_ID) values (?,?,?,?,?)";
	/*      */protected static String SQL_DEFAULT_INSERT_HISTORY = "insert into DATA_ENTRY_PROFILE_HISTORY       (DATA_ENTRY_PROFILE_HISTORY.USER_ID, DATA_ENTRY_PROFILE_HISTORY.MODEL_ID, DATA_ENTRY_PROFILE_HISTORY.BUDGET_LOCATION_ELEMENT_ID, DATA_ENTRY_PROFILE_HISTORY.BUDGET_CYCLE_ID, DATA_ENTRY_PROFILE_HISTORY.DATA_ENTRY_PROFILE_ID) values (?,?,?,?,(select DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID from DATA_ENTRY_PROFILE where DATA_ENTRY_PROFILE.USER_ID = ? AND DATA_ENTRY_PROFILE.MODEL_ID = ? AND DATA_ENTRY_PROFILE.BUDGET_CYCLE_ID = ?))";

	/* 1885 */protected static String SQL_UPDATE_HISTORY = "update DATA_ENTRY_PROFILE_HISTORY   set DATA_ENTRY_PROFILE_ID = ? where USER_ID = ?   and MODEL_ID = ?   and BUDGET_LOCATION_ELEMENT_ID = ?   and BUDGET_CYCLE_ID = ?";
	/*      */private static final String DELETE_ALL_PROFILES_FOR_FORM = "delete from DATA_ENTRY_PROFILE where XMLFORM_ID = ?";
	private static final String DELETE_ALL_PROFILE_HISTORY_FOR_MODEL = "delete from DATA_ENTRY_PROFILE_HISTORY where MODEL_ID = ?";
	/*      */protected DataEntryProfileHistoryDAO mDataEntryProfileHistoryDAO;
	/*      */protected DataEntryProfileEVO mDetails;

	/*      */
	/*      */public DataEntryProfileDAO(Connection connection)
	/*      */{
		/* 45 */super(connection);
		/*      */}

	/*      */
	/*      */public DataEntryProfileDAO()
	/*      */{
		/*      */}

	/*      */
	/*      */public DataEntryProfileDAO(DataSource ds)
	/*      */{
		/* 61 */super(ds);
		/*      */}

	/*      */
	/*      */protected DataEntryProfilePK getPK()
	/*      */{
		/* 69 */return this.mDetails.getPK();
		/*      */}

	/*      */
	/*      */public void setDetails(DataEntryProfileEVO details)
	/*      */{
		/* 78 */this.mDetails = details.deepClone();
		/*      */}

	/*      */
	/*      */private DataEntryProfileEVO getEvoFromJdbc(ResultSet resultSet_)
	/*      */throws SQLException
	/*      */{
		/* 131 */int col = 1;
		/* 132 */DataEntryProfileEVO evo = new DataEntryProfileEVO(resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getInt(col++), resultSet_.getString(col++).equals("Y"), resultSet_.getString(col++)
				.equals("Y"), resultSet_.getString(col++).equals("Y"), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++),
				resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++),
				resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getString(col++), resultSet_.getInt(col++), null);
		/*      */
		/* 175 */evo.setUpdatedByUserId(resultSet_.getInt(col++));
		/* 176 */evo.setUpdatedTime(resultSet_.getTimestamp(col++));
		/* 177 */evo.setCreatedTime(resultSet_.getTimestamp(col++));
		         evo.setMobile(resultSet_.getString(col++).charAt(0));
		/* 178 */return evo;
		/*      */}

	/*      */
	/*      */private int putEvoKeysToJdbc(DataEntryProfileEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
	/*      */{
		/* 183 */int col = startCol_;
		/* 184 */stmt_.setInt(col++, evo_.getDataEntryProfileId());
		/* 185 */return col;
		/*      */}

	/*      */
	/*      */private int putEvoDataToJdbc(DataEntryProfileEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException
	/*      */{
		/* 190 */int col = startCol_;
		/* 191 */stmt_.setString(col++, evo_.getVisId());
		/* 192 */stmt_.setInt(col++, evo_.getUserId());
		/* 193 */stmt_.setInt(col++, evo_.getModelId());
		stmt_.setInt(col++, evo_.getBudgetCycleId());
		/* 194 */stmt_.setInt(col++, evo_.getAutoOpenDepth());
		/* 195 */stmt_.setString(col++, evo_.getDescription());
		/* 196 */stmt_.setInt(col++, evo_.getXmlformId());
		/* 197 */if (evo_.getFillDisplayArea())
			/* 198 */stmt_.setString(col++, "Y");
		/*      */else
			/* 200 */stmt_.setString(col++, " ");
		/* 201 */if (evo_.getShowBoldSummaries())
			/* 202 */stmt_.setString(col++, "Y");
		/*      */else
			/* 204 */stmt_.setString(col++, " ");
		/* 205 */if (evo_.getShowHorizontalLines())
			/* 206 */stmt_.setString(col++, "Y");
		/*      */else
			/* 208 */stmt_.setString(col++, " ");
		/* 209 */stmt_.setInt(col++, evo_.getStructureId0());
		/* 210 */stmt_.setInt(col++, evo_.getStructureId1());
		/* 211 */stmt_.setInt(col++, evo_.getStructureId2());
		/* 212 */stmt_.setInt(col++, evo_.getStructureId3());
		/* 213 */stmt_.setInt(col++, evo_.getStructureId4());
		/* 214 */stmt_.setInt(col++, evo_.getStructureId5());
		/* 215 */stmt_.setInt(col++, evo_.getStructureId6());
		/* 216 */stmt_.setInt(col++, evo_.getStructureId7());
		/* 217 */stmt_.setInt(col++, evo_.getStructureId8());
		/* 218 */stmt_.setInt(col++, evo_.getStructureElementId0());
		/* 219 */stmt_.setInt(col++, evo_.getStructureElementId1());
		/* 220 */stmt_.setInt(col++, evo_.getStructureElementId2());
		/* 221 */stmt_.setInt(col++, evo_.getStructureElementId3());
		/* 222 */stmt_.setInt(col++, evo_.getStructureElementId4());
		/* 223 */stmt_.setInt(col++, evo_.getStructureElementId5());
		/* 224 */stmt_.setInt(col++, evo_.getStructureElementId6());
		/* 225 */stmt_.setInt(col++, evo_.getStructureElementId7());
		/* 226 */stmt_.setInt(col++, evo_.getStructureElementId8());
		/* 227 */stmt_.setString(col++, evo_.getElementLabel0());
		/* 228 */stmt_.setString(col++, evo_.getElementLabel1());
		/* 229 */stmt_.setString(col++, evo_.getElementLabel2());
		/* 230 */stmt_.setString(col++, evo_.getElementLabel3());
		/* 231 */stmt_.setString(col++, evo_.getElementLabel4());
		/* 232 */stmt_.setString(col++, evo_.getElementLabel5());
		/* 233 */stmt_.setString(col++, evo_.getElementLabel6());
		/* 234 */stmt_.setString(col++, evo_.getElementLabel7());
		/* 235 */stmt_.setString(col++, evo_.getElementLabel8());
		/* 236 */stmt_.setString(col++, evo_.getDataType());
		/* 237 */stmt_.setInt(col++, evo_.getVersionNum());
		/* 238 */stmt_.setInt(col++, evo_.getUpdatedByUserId());
		/* 239 */stmt_.setTimestamp(col++, evo_.getUpdatedTime());
		/* 240 */stmt_.setTimestamp(col++, evo_.getCreatedTime());
		         stmt_.setString(col++, Character.toString(evo_.getMobile()));
		/* 241 */return col;
		/*      */}

	/*      */
	/*      */protected void doLoad(DataEntryProfilePK pk)
	/*      */throws ValidationException
	/*      */{
		/* 257 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		/*      */
		/* 259 */PreparedStatement stmt = null;
		/* 260 */ResultSet resultSet = null;
		/*      */try
		/*      */{
			/* 264 */stmt = getConnection()
					.prepareStatement(
							"select DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID,DATA_ENTRY_PROFILE.VIS_ID,DATA_ENTRY_PROFILE.USER_ID,DATA_ENTRY_PROFILE.MODEL_ID,DATA_ENTRY_PROFILE.BUDGET_CYCLE_ID,DATA_ENTRY_PROFILE.AUTO_OPEN_DEPTH,DATA_ENTRY_PROFILE.DESCRIPTION,DATA_ENTRY_PROFILE.XMLFORM_ID,DATA_ENTRY_PROFILE.FILL_DISPLAY_AREA,DATA_ENTRY_PROFILE.SHOW_BOLD_SUMMARIES,DATA_ENTRY_PROFILE.SHOW_HORIZONTAL_LINES,DATA_ENTRY_PROFILE.STRUCTURE_ID0,DATA_ENTRY_PROFILE.STRUCTURE_ID1,DATA_ENTRY_PROFILE.STRUCTURE_ID2,DATA_ENTRY_PROFILE.STRUCTURE_ID3,DATA_ENTRY_PROFILE.STRUCTURE_ID4,DATA_ENTRY_PROFILE.STRUCTURE_ID5,DATA_ENTRY_PROFILE.STRUCTURE_ID6,DATA_ENTRY_PROFILE.STRUCTURE_ID7,DATA_ENTRY_PROFILE.STRUCTURE_ID8,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID0,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID1,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID2,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID3,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID4,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID5,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID6,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID7,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID8,DATA_ENTRY_PROFILE.ELEMENT_LABEL0,DATA_ENTRY_PROFILE.ELEMENT_LABEL1,DATA_ENTRY_PROFILE.ELEMENT_LABEL2,DATA_ENTRY_PROFILE.ELEMENT_LABEL3,DATA_ENTRY_PROFILE.ELEMENT_LABEL4,DATA_ENTRY_PROFILE.ELEMENT_LABEL5,DATA_ENTRY_PROFILE.ELEMENT_LABEL6,DATA_ENTRY_PROFILE.ELEMENT_LABEL7,DATA_ENTRY_PROFILE.ELEMENT_LABEL8,DATA_ENTRY_PROFILE.DATA_TYPE,DATA_ENTRY_PROFILE.VERSION_NUM,DATA_ENTRY_PROFILE.UPDATED_BY_USER_ID,DATA_ENTRY_PROFILE.UPDATED_TIME,DATA_ENTRY_PROFILE.CREATED_TIME, DATA_ENTRY_PROFILE.MOBILE from DATA_ENTRY_PROFILE where    DATA_ENTRY_PROFILE_ID = ? ");
			/*      */
			/* 267 */int col = 1;
			/* 268 */stmt.setInt(col++, pk.getDataEntryProfileId());
			/*      */
			/* 270 */resultSet = stmt.executeQuery();
			/*      */
			/* 272 */if (!resultSet.next()) {
				/* 273 */throw new ValidationException(getEntityName() + " select of " + pk + " not found");
				/*      */}
			/*      */
			/* 276 */this.mDetails = getEvoFromJdbc(resultSet);
			/* 277 */if (this.mDetails.isModified())
				/* 278 */this._log.info("doLoad", this.mDetails);
			/*      */}
		/*      */catch (SQLException sqle)
		/*      */{
			/* 282 */throw handleSQLException(
					pk,
					"select DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID,DATA_ENTRY_PROFILE.VIS_ID,DATA_ENTRY_PROFILE.USER_ID,DATA_ENTRY_PROFILE.MODEL_ID,DATA_ENTRY_PROFILE.BUDGET_CYCLE_ID,DATA_ENTRY_PROFILE.AUTO_OPEN_DEPTH,DATA_ENTRY_PROFILE.DESCRIPTION,DATA_ENTRY_PROFILE.XMLFORM_ID,DATA_ENTRY_PROFILE.FILL_DISPLAY_AREA,DATA_ENTRY_PROFILE.SHOW_BOLD_SUMMARIES,DATA_ENTRY_PROFILE.SHOW_HORIZONTAL_LINES,DATA_ENTRY_PROFILE.STRUCTURE_ID0,DATA_ENTRY_PROFILE.STRUCTURE_ID1,DATA_ENTRY_PROFILE.STRUCTURE_ID2,DATA_ENTRY_PROFILE.STRUCTURE_ID3,DATA_ENTRY_PROFILE.STRUCTURE_ID4,DATA_ENTRY_PROFILE.STRUCTURE_ID5,DATA_ENTRY_PROFILE.STRUCTURE_ID6,DATA_ENTRY_PROFILE.STRUCTURE_ID7,DATA_ENTRY_PROFILE.STRUCTURE_ID8,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID0,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID1,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID2,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID3,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID4,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID5,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID6,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID7,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID8,DATA_ENTRY_PROFILE.ELEMENT_LABEL0,DATA_ENTRY_PROFILE.ELEMENT_LABEL1,DATA_ENTRY_PROFILE.ELEMENT_LABEL2,DATA_ENTRY_PROFILE.ELEMENT_LABEL3,DATA_ENTRY_PROFILE.ELEMENT_LABEL4,DATA_ENTRY_PROFILE.ELEMENT_LABEL5,DATA_ENTRY_PROFILE.ELEMENT_LABEL6,DATA_ENTRY_PROFILE.ELEMENT_LABEL7,DATA_ENTRY_PROFILE.ELEMENT_LABEL8,DATA_ENTRY_PROFILE.DATA_TYPE,DATA_ENTRY_PROFILE.VERSION_NUM,DATA_ENTRY_PROFILE.UPDATED_BY_USER_ID,DATA_ENTRY_PROFILE.UPDATED_TIME,DATA_ENTRY_PROFILE.CREATED_TIME, DATA_ENTRY_PROFILE.MOBILE from DATA_ENTRY_PROFILE where    DATA_ENTRY_PROFILE_ID = ? ",
					sqle);
			/*      */}
		/*      */finally
		/*      */{
			/* 286 */closeResultSet(resultSet);
			/* 287 */closeStatement(stmt);
			/* 288 */closeConnection();
			/*      */
			/* 290 */if (timer != null)
				/* 291 */timer.logDebug("doLoad", pk);
			/*      */}
		/*      */}

	/*      */
	/*      */protected void doCreate()
	/*      */throws DuplicateNameValidationException, ValidationException
	/*      */{
	    
	            int newDataEntryProfileId = getNewDataEntryProfileId(); 
	            this.mDetails.setDataEntryProfileId(newDataEntryProfileId);
	            
	    /* 392 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		/* 393 */this.mDetails.postCreateInit();
		/*      */
		/* 395 */PreparedStatement stmt = null;
		/*      */try
		/*      */{
			/* 400 */duplicateValueCheckDataEntryProfileName();
			/*      */
			/* 402 */this.mDetails.setCreatedTime(new Timestamp(new Date().getTime()));
			/* 403 */this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
			/* 404 */stmt = getConnection()
					.prepareStatement(
							"insert into DATA_ENTRY_PROFILE ( DATA_ENTRY_PROFILE_ID, VIS_ID, USER_ID, MODEL_ID, BUDGET_CYCLE_ID, AUTO_OPEN_DEPTH,DESCRIPTION,XMLFORM_ID,FILL_DISPLAY_AREA,SHOW_BOLD_SUMMARIES,SHOW_HORIZONTAL_LINES,STRUCTURE_ID0,STRUCTURE_ID1,STRUCTURE_ID2,STRUCTURE_ID3,STRUCTURE_ID4,STRUCTURE_ID5,STRUCTURE_ID6,STRUCTURE_ID7,STRUCTURE_ID8,STRUCTURE_ELEMENT_ID0,STRUCTURE_ELEMENT_ID1,STRUCTURE_ELEMENT_ID2,STRUCTURE_ELEMENT_ID3,STRUCTURE_ELEMENT_ID4,STRUCTURE_ELEMENT_ID5,STRUCTURE_ELEMENT_ID6,STRUCTURE_ELEMENT_ID7,STRUCTURE_ELEMENT_ID8,ELEMENT_LABEL0,ELEMENT_LABEL1,ELEMENT_LABEL2,ELEMENT_LABEL3,ELEMENT_LABEL4,ELEMENT_LABEL5,ELEMENT_LABEL6,ELEMENT_LABEL7,ELEMENT_LABEL8,DATA_TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME,MOBILE) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			/*      */
			/* 407 */int col = 1;
			/* 408 */col = putEvoKeysToJdbc(this.mDetails, stmt, col);
			/* 409 */col = putEvoDataToJdbc(this.mDetails, stmt, col);
			/*      */
			/* 412 */int resultCount = stmt.executeUpdate();
			/* 413 */if (resultCount != 1)
			/*      */{
				/* 415 */throw new RuntimeException(getEntityName() + " insert failed (" + this.mDetails.getPK() + "): resultCount=" + resultCount);
				/*      */}
			/*      */
			/* 418 */this.mDetails.reset();
			/*      */}
		/*      */catch (SQLException sqle)
		/*      */{
			/* 422 */throw handleSQLException(
					this.mDetails.getPK(),
					"insert into DATA_ENTRY_PROFILE ( DATA_ENTRY_PROFILE_ID,VIS_ID,USER_ID,MODEL_ID, BUDGET_CYCLE_ID, AUTO_OPEN_DEPTH,DESCRIPTION,XMLFORM_ID,FILL_DISPLAY_AREA,SHOW_BOLD_SUMMARIES,SHOW_HORIZONTAL_LINES,STRUCTURE_ID0,STRUCTURE_ID1,STRUCTURE_ID2,STRUCTURE_ID3,STRUCTURE_ID4,STRUCTURE_ID5,STRUCTURE_ID6,STRUCTURE_ID7,STRUCTURE_ID8,STRUCTURE_ELEMENT_ID0,STRUCTURE_ELEMENT_ID1,STRUCTURE_ELEMENT_ID2,STRUCTURE_ELEMENT_ID3,STRUCTURE_ELEMENT_ID4,STRUCTURE_ELEMENT_ID5,STRUCTURE_ELEMENT_ID6,STRUCTURE_ELEMENT_ID7,STRUCTURE_ELEMENT_ID8,ELEMENT_LABEL0,ELEMENT_LABEL1,ELEMENT_LABEL2,ELEMENT_LABEL3,ELEMENT_LABEL4,ELEMENT_LABEL5,ELEMENT_LABEL6,ELEMENT_LABEL7,ELEMENT_LABEL8,DATA_TYPE,VERSION_NUM,UPDATED_BY_USER_ID,UPDATED_TIME,CREATED_TIME,MOBILE) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
					sqle);
			/*      */}
		/*      */finally
		/*      */{
			/* 426 */closeStatement(stmt);
			/* 427 */closeConnection();
			/*      */
			/* 429 */if (timer != null) {
				/* 430 */timer.logDebug("doCreate", this.mDetails.toString());
				/*      */}
			/*      */}
		/*      */
		/*      */try
		/*      */{
			/* 436 */getDataEntryProfileHistoryDAO().update(this.mDetails.getDataEntryProfilesHistoryMap());
			/*      */}
		/*      */catch (Exception e)
		/*      */{
			/* 442 */throw new RuntimeException("unexpected exception", e);
			/*      */}
		/*      */}

	/*      */
	/*      */protected void duplicateValueCheckDataEntryProfileName()
	/*      */throws DuplicateNameValidationException
	/*      */{
		/* 459 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		/* 460 */PreparedStatement stmt = null;
		/* 461 */ResultSet resultSet = null;
		/*      */try
		/*      */{
			/* 465 */stmt = getConnection().prepareStatement("select count(*) from DATA_ENTRY_PROFILE where    USER_ID = ? AND MODEL_ID = ? AND BUDGET_CYCLE_ID = ? AND XMLFORM_ID = ? and not(    DATA_ENTRY_PROFILE_ID = ? )");
			/*      */
			/* 468 */int col = 1;
			/* 469 */stmt.setInt(col++, this.mDetails.getUserId());
			/* 470 */stmt.setInt(col++, this.mDetails.getModelId());
			stmt.setInt(col++, this.mDetails.getBudgetCycleId());
			/* 471 */stmt.setInt(col++, this.mDetails.getXmlformId());
			/* 472 */col = putEvoKeysToJdbc(this.mDetails, stmt, col);
			/*      */
			/* 475 */resultSet = stmt.executeQuery();
			/*      */
			/* 477 */if (!resultSet.next()) {
				/* 478 */throw new RuntimeException(getEntityName() + " select of " + getPK() + " not found");
				/*      */}
			/*      */
			/* 482 */col = 1;
			/* 483 */int count = resultSet.getInt(col++);
			/* 484 */if (count > 0) {
				/* 485 */throw new DuplicateNameValidationException(getEntityName() + " " + getPK() + " DataEntryProfileName");
				/*      */}
			/*      */
			/*      */}
		/*      */catch (SQLException sqle)
		/*      */{
			/* 491 */throw handleSQLException(getPK(), "select count(*) from DATA_ENTRY_PROFILE where    USER_ID = ? AND MODEL_ID = ? AND VIS_ID = ? and not(    DATA_ENTRY_PROFILE_ID = ? )", sqle);
			/*      */}
		/*      */finally
		/*      */{
			/* 495 */closeResultSet(resultSet);
			/* 496 */closeStatement(stmt);
			/* 497 */closeConnection();
			/*      */
			/* 499 */if (timer != null)
				/* 500 */timer.logDebug("duplicateValueCheckDataEntryProfileName", "");
			/*      */}
		/*      */}

	/*      */
	/*      */protected void doStore()
	/*      */throws DuplicateNameValidationException, VersionValidationException, ValidationException
	/*      */{
		/* 560 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		/*      */
		/* 564 */PreparedStatement stmt = null;
		/*      */
		/* 566 */boolean mainChanged = this.mDetails.isModified();
		/* 567 */boolean dependantChanged = false;
		/*      */try
		/*      */{
			/* 571 */if (mainChanged) {
				/* 572 */duplicateValueCheckDataEntryProfileName();
				/*      */}
			/* 574 */if (getDataEntryProfileHistoryDAO().update(this.mDetails.getDataEntryProfilesHistoryMap())) {
				/* 575 */dependantChanged = true;
				/*      */}
			/* 577 */if ((mainChanged) || (dependantChanged))
			/*      */{
				/* 580 */this.mDetails.setVersionNum(this.mDetails.getVersionNum() + 1);
				/*      */
				/* 583 */this.mDetails.setUpdatedTime(new Timestamp(new Date().getTime()));
				         this.mDetails.setMobile('N');
				/* 584 */stmt = getConnection()
						.prepareStatement(
								"update DATA_ENTRY_PROFILE set VIS_ID = ?,USER_ID = ?,MODEL_ID = ?, BUDGET_CYCLE_ID = ?, AUTO_OPEN_DEPTH = ?,DESCRIPTION = ?,XMLFORM_ID = ?,FILL_DISPLAY_AREA = ?,SHOW_BOLD_SUMMARIES = ?,SHOW_HORIZONTAL_LINES = ?,STRUCTURE_ID0 = ?,STRUCTURE_ID1 = ?,STRUCTURE_ID2 = ?,STRUCTURE_ID3 = ?,STRUCTURE_ID4 = ?,STRUCTURE_ID5 = ?,STRUCTURE_ID6 = ?,STRUCTURE_ID7 = ?,STRUCTURE_ID8 = ?,STRUCTURE_ELEMENT_ID0 = ?,STRUCTURE_ELEMENT_ID1 = ?,STRUCTURE_ELEMENT_ID2 = ?,STRUCTURE_ELEMENT_ID3 = ?,STRUCTURE_ELEMENT_ID4 = ?,STRUCTURE_ELEMENT_ID5 = ?,STRUCTURE_ELEMENT_ID6 = ?,STRUCTURE_ELEMENT_ID7 = ?,STRUCTURE_ELEMENT_ID8 = ?,ELEMENT_LABEL0 = ?,ELEMENT_LABEL1 = ?,ELEMENT_LABEL2 = ?,ELEMENT_LABEL3 = ?,ELEMENT_LABEL4 = ?,ELEMENT_LABEL5 = ?,ELEMENT_LABEL6 = ?,ELEMENT_LABEL7 = ?,ELEMENT_LABEL8 = ?,DATA_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ?, MOBILE = ? where    DATA_ENTRY_PROFILE_ID = ? AND VERSION_NUM = ?");
				/*      */
				/* 587 */int col = 1;
				/* 588 */col = putEvoDataToJdbc(this.mDetails, stmt, col);
				/* 589 */col = putEvoKeysToJdbc(this.mDetails, stmt, col);
				/*      */
				/* 591 */stmt.setInt(col++, this.mDetails.getVersionNum() - 1);
				/*      */
				/* 594 */int resultCount = stmt.executeUpdate();
				/*      */
				/* 596 */if (resultCount == 0) {
					/* 597 */checkVersionNum();
					/*      */}
				/* 599 */if (resultCount != 1) {
					/* 600 */throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
					/*      */}
				/*      */
				/* 603 */this.mDetails.reset();
				/*      */}
			/*      */
			/*      */}
		/*      */catch (SQLException sqle)
		/*      */{
			/* 612 */throw handleSQLException(
					getPK(),
					"update DATA_ENTRY_PROFILE set VIS_ID = ?,USER_ID = ?,MODEL_ID = ?, BUDGET_CYCLE_ID = ?, AUTO_OPEN_DEPTH = ?,DESCRIPTION = ?,XMLFORM_ID = ?,FILL_DISPLAY_AREA = ?,SHOW_BOLD_SUMMARIES = ?,SHOW_HORIZONTAL_LINES = ?,STRUCTURE_ID0 = ?,STRUCTURE_ID1 = ?,STRUCTURE_ID2 = ?,STRUCTURE_ID3 = ?,STRUCTURE_ID4 = ?,STRUCTURE_ID5 = ?,STRUCTURE_ID6 = ?,STRUCTURE_ID7 = ?,STRUCTURE_ID8 = ?,STRUCTURE_ELEMENT_ID0 = ?,STRUCTURE_ELEMENT_ID1 = ?,STRUCTURE_ELEMENT_ID2 = ?,STRUCTURE_ELEMENT_ID3 = ?,STRUCTURE_ELEMENT_ID4 = ?,STRUCTURE_ELEMENT_ID5 = ?,STRUCTURE_ELEMENT_ID6 = ?,STRUCTURE_ELEMENT_ID7 = ?,STRUCTURE_ELEMENT_ID8 = ?,ELEMENT_LABEL0 = ?,ELEMENT_LABEL1 = ?,ELEMENT_LABEL2 = ?,ELEMENT_LABEL3 = ?,ELEMENT_LABEL4 = ?,ELEMENT_LABEL5 = ?,ELEMENT_LABEL6 = ?,ELEMENT_LABEL7 = ?,ELEMENT_LABEL8 = ?,DATA_TYPE = ?,VERSION_NUM = ?,UPDATED_BY_USER_ID = ?,UPDATED_TIME = ?,CREATED_TIME = ?, MOBILE = ? where    DATA_ENTRY_PROFILE_ID = ? AND VERSION_NUM = ?",
					sqle);
			/*      */}
		/*      */finally
		/*      */{
			/* 616 */closeStatement(stmt);
			/* 617 */closeConnection();
			/*      */
			/* 619 */if ((timer != null) && (
			/* 620 */(mainChanged) || (dependantChanged)))
				/* 621 */timer.logDebug("store", this.mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
			/*      */}
		/*      */}

	/*      */
	/*      */private void checkVersionNum()
	/*      */throws VersionValidationException
	/*      */{
		/* 633 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		/* 634 */PreparedStatement stmt = null;
		/* 635 */ResultSet resultSet = null;
		/*      */try
		/*      */{
			/* 639 */stmt = getConnection().prepareStatement("select VERSION_NUM from DATA_ENTRY_PROFILE where DATA_ENTRY_PROFILE_ID = ?");
			/*      */
			/* 642 */int col = 1;
			/* 643 */stmt.setInt(col++, this.mDetails.getDataEntryProfileId());
			/*      */
			/* 646 */resultSet = stmt.executeQuery();
			/*      */
			/* 648 */if (!resultSet.next()) {
				/* 649 */throw new RuntimeException(getEntityName() + " checkVersionNum: select of " + getPK() + " not found");
				/*      */}
			/*      */
			/* 652 */col = 1;
			/* 653 */int dbVersionNumber = resultSet.getInt(col++);
			/* 654 */if (this.mDetails.getVersionNum() - 1 != dbVersionNumber) {
				/* 655 */throw new VersionValidationException(getEntityName() + " " + getPK() + " expected:" + (this.mDetails.getVersionNum() - 1) + " found:" + dbVersionNumber);
				/*      */}
			/*      */
			/*      */}
		/*      */catch (SQLException sqle)
		/*      */{
			/* 661 */throw handleSQLException(getPK(), "select VERSION_NUM from DATA_ENTRY_PROFILE where DATA_ENTRY_PROFILE_ID = ?", sqle);
			/*      */}
		/*      */finally
		/*      */{
			/* 665 */closeStatement(stmt);
			/* 666 */closeResultSet(resultSet);
			/*      */
			/* 668 */if (timer != null)
				/* 669 */timer.logDebug("checkVersionNum", this.mDetails.getPK());
			/*      */}
		/*      */}

	/*      */
	/*      */public AllDataEntryProfilesELO getAllDataEntryProfiles()
	/*      */{
		/* 705 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		/* 706 */PreparedStatement stmt = null;
		/* 707 */ResultSet resultSet = null;
		/* 708 */AllDataEntryProfilesELO results = new AllDataEntryProfilesELO();
		/*      */try
		/*      */{
			/* 711 */stmt = getConnection().prepareStatement(SQL_ALL_DATA_ENTRY_PROFILES);
			/* 712 */int col = 1;
			/* 713 */resultSet = stmt.executeQuery();
			/* 714 */while (resultSet.next())
			/*      */{
				/* 716 */col = 2;
				/*      */
				/* 719 */UserPK pkUser = new UserPK(resultSet.getInt(col++));
				/*      */
				/* 722 */String textUser = resultSet.getString(col++);
				/*      */
				/* 725 */DataEntryProfilePK pkDataEntryProfile = new DataEntryProfilePK(resultSet.getInt(col++));
				/*      */
				/* 728 */String textDataEntryProfile = resultSet.getString(col++);
				/*      */
				/* 733 */DataEntryProfileCK ckDataEntryProfile = new DataEntryProfileCK(pkUser, pkDataEntryProfile);
				/*      */
				/* 739 */UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
				/*      */
				/* 745 */DataEntryProfileRefImpl erDataEntryProfile = new DataEntryProfileRefImpl(ckDataEntryProfile, textDataEntryProfile);
				/*      */
				/* 750 */String col1 = resultSet.getString(col++);
				/*      */
				/* 753 */results.add(erDataEntryProfile, erUser, col1);
				/*      */}
			/*      */
			/*      */}
		/*      */catch (SQLException sqle)
		/*      */{
			/* 762 */throw handleSQLException(SQL_ALL_DATA_ENTRY_PROFILES, sqle);
			/*      */}
		/*      */finally
		/*      */{
			/* 766 */closeResultSet(resultSet);
			/* 767 */closeStatement(stmt);
			/* 768 */closeConnection();
			/*      */}
		/*      */
		/* 771 */if (timer != null) {
			/* 772 */timer.logDebug("getAllDataEntryProfiles", " items=" + results.size());
			/*      */}
		/*      */
		/* 776 */return results;
		/*      */}

	public AllDataEntryProfilesForUserELO getAllDataEntryProfilesForUser(int userId, int modelId, int budgetCycleId) {
		Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		AllDataEntryProfilesForUserELO results = new AllDataEntryProfilesForUserELO();
		try {
			stmt = getConnection().prepareStatement(SQL_ALL_DATA_ENTRY_PROFILES_FOR_USER);
			int col = 1;
			stmt.setInt(col++, userId);
			stmt.setInt(col++, budgetCycleId);
			stmt.setInt(col++, userId);
			stmt.setInt(col++, budgetCycleId);
			stmt.setInt(col++, modelId);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				col = 2;
				UserPK pkUser = new UserPK(resultSet.getInt(col++));
				String textUser = resultSet.getString(col++);
				DataEntryProfilePK pkDataEntryProfile = new DataEntryProfilePK(resultSet.getInt(col++));
				String textDataEntryProfile = resultSet.getString(col++);
				DataEntryProfileCK ckDataEntryProfile = new DataEntryProfileCK(pkUser, pkDataEntryProfile);
				UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
				DataEntryProfileRefImpl erDataEntryProfile = new DataEntryProfileRefImpl(ckDataEntryProfile, textDataEntryProfile);
				String col1 = resultSet.getString(col++);
				int xmlFormId = resultSet.getInt(col++);
				int structureId0 = resultSet.getInt(col++);
				int structureId1 = resultSet.getInt(col++);
				int structureElementId0 = resultSet.getInt(col++);
				int structureElementId1 = resultSet.getInt(col++);
				String elementLabel0 = resultSet.getString(col++);
				String elementLabel1 = resultSet.getString(col++);
				String dataType = resultSet.getString(col++);
				Integer formType = resultSet.getInt(col++);
				
				results.add(erDataEntryProfile, erUser, col1, xmlFormId, structureId0, structureId1, structureElementId0, structureElementId1, elementLabel0, elementLabel1, dataType, formType);
			}
		} catch (SQLException sqle) {
			throw handleSQLException(SQL_ALL_DATA_ENTRY_PROFILES_FOR_USER, sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}

		if (timer != null) {
			timer.logDebug("getAllDataEntryProfilesForUser", " UserId=" + userId + ",ModelId=" + modelId + " items=" + results.size());
		}

		return results;
	}

	/*      */public AllUsersForDataEntryProfilesForModelELO getAllUsersForDataEntryProfilesForModel(int param1)
	/*      */{
		/* 926 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		/* 927 */PreparedStatement stmt = null;
		/* 928 */ResultSet resultSet = null;
		/* 929 */AllUsersForDataEntryProfilesForModelELO results = new AllUsersForDataEntryProfilesForModelELO();
		/*      */try
		/*      */{
			/* 932 */stmt = getConnection().prepareStatement(SQL_ALL_USERS_FOR_DATA_ENTRY_PROFILES_FOR_MODEL);
			/* 933 */int col = 1;
			/* 934 */stmt.setInt(col++, param1);
			/* 935 */resultSet = stmt.executeQuery();
			/* 936 */while (resultSet.next())
			/*      */{
				/* 938 */col = 2;
				/*      */
				/* 941 */UserPK pkUser = new UserPK(resultSet.getInt(col++));
				/*      */
				/* 944 */String textUser = resultSet.getString(col++);
				/*      */
				/* 947 */DataEntryProfilePK pkDataEntryProfile = new DataEntryProfilePK(resultSet.getInt(col++));
				/*      */
				/* 950 */String textDataEntryProfile = resultSet.getString(col++);
				/*      */
				/* 955 */DataEntryProfileCK ckDataEntryProfile = new DataEntryProfileCK(pkUser, pkDataEntryProfile);
				/*      */
				/* 961 */UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
				/*      */
				/* 967 */DataEntryProfileRefImpl erDataEntryProfile = new DataEntryProfileRefImpl(ckDataEntryProfile, textDataEntryProfile);
				/*      */
				/* 974 */results.add(erDataEntryProfile, erUser);
				/*      */}
			/*      */
			/*      */}
		/*      */catch (SQLException sqle)
		/*      */{
			/* 982 */throw handleSQLException(SQL_ALL_USERS_FOR_DATA_ENTRY_PROFILES_FOR_MODEL, sqle);
			/*      */}
		/*      */finally
		/*      */{
			/* 986 */closeResultSet(resultSet);
			/* 987 */closeStatement(stmt);
			/* 988 */closeConnection();
			/*      */}
		/*      */
		/* 991 */if (timer != null) {
			/* 992 */timer.logDebug("getAllUsersForDataEntryProfilesForModel", " ModelId=" + param1 + " items=" + results.size());
			/*      */}
		/*      */
		/* 997 */return results;
		/*      */}

	/*      */
	/*      */public AllDataEntryProfilesForFormELO getAllDataEntryProfilesForForm(int param1)
	/*      */{
		/* 1034 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		/* 1035 */PreparedStatement stmt = null;
		/* 1036 */ResultSet resultSet = null;
		/* 1037 */AllDataEntryProfilesForFormELO results = new AllDataEntryProfilesForFormELO();
		/*      */try
		/*      */{
			/* 1040 */stmt = getConnection().prepareStatement(SQL_ALL_DATA_ENTRY_PROFILES_FOR_FORM);
			/* 1041 */int col = 1;
			/* 1042 */stmt.setInt(col++, param1);
			/* 1043 */resultSet = stmt.executeQuery();
			/* 1044 */while (resultSet.next())
			/*      */{
				/* 1046 */col = 2;
				/*      */
				/* 1049 */UserPK pkUser = new UserPK(resultSet.getInt(col++));
				/*      */
				/* 1052 */String textUser = resultSet.getString(col++);
				/*      */
				/* 1055 */DataEntryProfilePK pkDataEntryProfile = new DataEntryProfilePK(resultSet.getInt(col++));
				/*      */
				/* 1058 */String textDataEntryProfile = resultSet.getString(col++);
				/*      */
				/* 1063 */DataEntryProfileCK ckDataEntryProfile = new DataEntryProfileCK(pkUser, pkDataEntryProfile);
				/*      */
				/* 1069 */UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
				/*      */
				/* 1075 */DataEntryProfileRefImpl erDataEntryProfile = new DataEntryProfileRefImpl(ckDataEntryProfile, textDataEntryProfile);
				/*      */
				/* 1080 */String col1 = resultSet.getString(col++);
				/*      */
				/* 1083 */results.add(erDataEntryProfile, erUser, col1);
				/*      */}
			/*      */
			/*      */}
		/*      */catch (SQLException sqle)
		/*      */{
			/* 1092 */throw handleSQLException(SQL_ALL_DATA_ENTRY_PROFILES_FOR_FORM, sqle);
			/*      */}
		/*      */finally
		/*      */{
			/* 1096 */closeResultSet(resultSet);
			/* 1097 */closeStatement(stmt);
			/* 1098 */closeConnection();
			/*      */}
		/*      */
		/* 1101 */if (timer != null) {
			/* 1102 */timer.logDebug("getAllDataEntryProfilesForForm", " XmlformId=" + param1 + " items=" + results.size());
			/*      */}
		/*      */
		/* 1107 */return results;
		/*      */}

	/*      */
	/*      */public DefaultDataEntryProfileELO getDefaultDataEntryProfile(int userId, int modelId, int budgetCycleId, int budgetLocationElementId)
	/*      */{
		/* 1153 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		/* 1154 */PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		/* 1155 */ResultSet resultSet = null;
		/* 1156 */DefaultDataEntryProfileELO results = new DefaultDataEntryProfileELO();
		/*      */try
		/*      */{
			/* 1159 */stmt = getConnection().prepareStatement(SQL_DEFAULT_DATA_ENTRY_PROFILE);
			/* 1160 */int col = 1;
			/* 1161 */stmt.setInt(col++, userId);
			/* 1162 */stmt.setInt(col++, modelId);
			/* 1163 */stmt.setInt(col++, budgetCycleId);
					  stmt.setInt(col++, budgetLocationElementId);
					  stmt.setInt(col++, budgetCycleId);
			/* 1164 */resultSet = stmt.executeQuery();

			/* 1165 */if (resultSet.next())
			/*      */{
				/* 1167 */col = 2;
				/*      */
				/* 1170 */UserPK pkUser = new UserPK(resultSet.getInt(col++));
				/*      */
				/* 1173 */String textUser = resultSet.getString(col++);
				/*      */
				/* 1176 */DataEntryProfilePK pkDataEntryProfile = new DataEntryProfilePK(resultSet.getInt(col++));
				/*      */
				/* 1179 */String textDataEntryProfile = resultSet.getString(col++);
				/*      */
				/* 1182 */DataEntryProfileHistoryPK pkDataEntryProfileHistory = new DataEntryProfileHistoryPK(resultSet.getInt(col++), resultSet.getInt(col++), resultSet.getInt(col++), resultSet.getInt(col++));
				/*      */
				/* 1187 */String textDataEntryProfileHistory = "";
				/*      */
				/* 1191 */DataEntryProfileCK ckDataEntryProfile = new DataEntryProfileCK(pkUser, pkDataEntryProfile);
				/*      */
				/* 1197 */UserRefImpl erUser = new UserRefImpl(pkUser, textUser);
				/*      */
				/* 1203 */DataEntryProfileRefImpl erDataEntryProfile = new DataEntryProfileRefImpl(ckDataEntryProfile, textDataEntryProfile);
				/*      */
				/* 1209 */DataEntryProfileHistoryRefImpl erDataEntryProfileHistory = new DataEntryProfileHistoryRefImpl(pkDataEntryProfileHistory, textDataEntryProfileHistory);
				/*      */
				/* 1214 */String col1 = resultSet.getString(col++);
				/*      */
				/* 1217 */results.add(erDataEntryProfile, erUser, erDataEntryProfileHistory, col1);
				/*      */}
			/*      */
			/*      */}
		/*      */catch (SQLException sqle)
		/*      */{
			/* 1227 */throw handleSQLException(SQL_DEFAULT_DATA_ENTRY_PROFILE, sqle);
			/*      */}
		/*      */finally
		/*      */{
			/* 1231 */closeResultSet(resultSet);
			/* 1232 */closeStatement(stmt);
			/* 1233 */closeConnection();
			/*      */}
		/*      */
		/* 1236 */if (timer != null) {
			/* 1237 */timer.logDebug("getDefaultDataEntryProfile", " UserId=" + userId + ",ModelId=" + modelId + ",BudgetLocationElementId=" + budgetCycleId + " items=" + results.size());
			/*      */}
		/*      */
		/* 1244 */return results;
		/*      */}

	/*      */
	/*      */public boolean update(Map items)
	/*      */throws DuplicateNameValidationException, VersionValidationException, ValidationException
	/*      */{
		/* 1261 */if (items == null) {
			/* 1262 */return false;
			/*      */}
		/* 1264 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		/* 1265 */PreparedStatement deleteStmt = null;
		/*      */
		/* 1267 */boolean somethingChanged = false;
		/*      */try
		/*      */{
			/* 1271 */Iterator iter3 = new ArrayList(items.values()).iterator();
			/* 1272 */while (iter3.hasNext())
			/*      */{
				/* 1274 */this.mDetails = ((DataEntryProfileEVO) iter3.next());
				/* 1275 */if (!this.mDetails.deletePending())
					/*      */continue;
				/* 1277 */somethingChanged = true;
				/*      */
				/* 1280 */deleteDependants(this.mDetails.getPK());
				/*      */}
			/*      */
			/* 1284 */Iterator iter2 = new ArrayList(items.values()).iterator();
			/* 1285 */while (iter2.hasNext())
			/*      */{
				/* 1287 */this.mDetails = ((DataEntryProfileEVO) iter2.next());
				/*      */
				/* 1290 */if (!this.mDetails.deletePending())
					/*      */continue;
				/* 1292 */somethingChanged = true;
				/*      */
				/* 1295 */if (deleteStmt == null) {
					/* 1296 */deleteStmt = getConnection().prepareStatement("delete from DATA_ENTRY_PROFILE where    DATA_ENTRY_PROFILE_ID = ? ");
					/*      */}
				/*      */
				/* 1299 */int col = 1;
				/* 1300 */deleteStmt.setInt(col++, this.mDetails.getDataEntryProfileId());
				/*      */
				/* 1302 */if (this._log.isDebugEnabled()) {
					/* 1303 */this._log.debug("update", "DataEntryProfile deleting DataEntryProfileId=" + this.mDetails.getDataEntryProfileId());
					/*      */}
				/*      */
				/* 1308 */deleteStmt.addBatch();
				/*      */
				/* 1311 */items.remove(this.mDetails.getPK());
				/*      */}
			/*      */
			/* 1316 */if (deleteStmt != null)
			/*      */{
				/* 1318 */Timer timer2 = this._log.isDebugEnabled() ? new Timer(this._log) : null;
				/*      */
				/* 1320 */deleteStmt.executeBatch();
				/*      */
				/* 1322 */if (timer2 != null) {
					/* 1323 */timer2.logDebug("update", "delete batch");
					/*      */}
				/*      */}
			/*      */
			/* 1327 */Iterator iter1 = items.values().iterator();
			/* 1328 */while (iter1.hasNext())
			/*      */{
				/* 1330 */this.mDetails = ((DataEntryProfileEVO) iter1.next());
				/*      */
				/* 1332 */if (this.mDetails.insertPending())
				/*      */{
					/* 1334 */somethingChanged = true;
					/* 1335 */doCreate();
					continue;
					/*      */}
				/*      */
				/* 1338 */if (this.mDetails.isModified())
				/*      */{
					/* 1340 */somethingChanged = true;
					/* 1341 */doStore();
					continue;
					/*      */}
				/*      */
				/* 1345 */if ((this.mDetails.deletePending()) ||
				/* 1351 */(!getDataEntryProfileHistoryDAO().update(this.mDetails.getDataEntryProfilesHistoryMap())))
					continue;
				/* 1352 */somethingChanged = true;
				/*      */}
			/*      */
			/* 1364 */boolean bool1 = somethingChanged;
			/*      */return bool1;
			/*      */}
		/*      */catch (SQLException sqle)
		/*      */{
			/* 1368 */throw handleSQLException("delete from DATA_ENTRY_PROFILE where    DATA_ENTRY_PROFILE_ID = ? ", sqle);
			/*      */}
		/*      */finally
		/*      */{
			/* 1372 */if (deleteStmt != null)
			/*      */{
				/* 1374 */closeStatement(deleteStmt);
				/* 1375 */closeConnection();
				/*      */}
			/*      */
			/* 1378 */this.mDetails = null;
			/*      */
			/* 1380 */if ((somethingChanged) &&
			/* 1381 */(timer != null))
				/* 1382 */timer.logDebug("update", "collection");
			/* 1382 */}
		/*      */}

	/*      */
	/*      */private void deleteDependants(DataEntryProfilePK pk)
	/*      */{
		/* 1408 */Set emptyStrings = Collections.emptySet();
		/* 1409 */deleteDependants(pk, emptyStrings);
		/*      */}

	/*      */
	/*      */private void deleteDependants(DataEntryProfilePK pk, Set<String> exclusionTables)
	/*      */{
		/* 1415 */for (int i = SQL_DELETE_CHILDRENS_DEPENDANTS.length - 1; i > -1; i--)
		/*      */{
			/* 1417 */if (exclusionTables.contains(SQL_DELETE_CHILDRENS_DEPENDANTS[i][0]))
				/*      */continue;
			/* 1419 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
			/*      */
			/* 1421 */PreparedStatement stmt = null;
			/*      */
			/* 1423 */int resultCount = 0;
			/* 1424 */String s = null;
			/*      */try
			/*      */{
				/* 1427 */s = SQL_DELETE_CHILDRENS_DEPENDANTS[i][1] + SQL_DELETE_DEPENDANT_CRITERIA;
				/*      */
				/* 1429 */if (this._log.isDebugEnabled()) {
					/* 1430 */this._log.debug("deleteDependants", s);
					/*      */}
				/* 1432 */stmt = getConnection().prepareStatement(s);
				/*      */
				/* 1435 */int col = 1;
				/* 1436 */stmt.setInt(col++, pk.getDataEntryProfileId());
				/*      */
				/* 1439 */resultCount = stmt.executeUpdate();
				/*      */}
			/*      */catch (SQLException sqle)
			/*      */{
				/* 1443 */throw handleSQLException(pk, s, sqle);
				/*      */}
			/*      */finally
			/*      */{
				/* 1447 */closeStatement(stmt);
				/* 1448 */closeConnection();
				/*      */
				/* 1450 */if (timer != null) {
					/* 1451 */timer.logDebug("deleteDependants", "A[" + i + "] count=" + resultCount);
					/*      */}
				/*      */}
			/*      */}
		/* 1455 */for (int i = SQL_DELETE_CHILDREN.length - 1; i > -1; i--)
		/*      */{
			/* 1457 */if (exclusionTables.contains(SQL_DELETE_CHILDREN[i][0]))
				/*      */continue;
			/* 1459 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
			/*      */
			/* 1461 */PreparedStatement stmt = null;
			/*      */
			/* 1463 */int resultCount = 0;
			/* 1464 */String s = null;
			/*      */try
			/*      */{
				/* 1467 */s = SQL_DELETE_CHILDREN[i][1];
				/*      */
				/* 1469 */if (this._log.isDebugEnabled()) {
					/* 1470 */this._log.debug("deleteDependants", s);
					/*      */}
				/* 1472 */stmt = getConnection().prepareStatement(s);
				/*      */
				/* 1475 */int col = 1;
				/* 1476 */stmt.setInt(col++, pk.getDataEntryProfileId());
				/*      */
				/* 1479 */resultCount = stmt.executeUpdate();
				/*      */}
			/*      */catch (SQLException sqle)
			/*      */{
				/* 1483 */throw handleSQLException(pk, s, sqle);
				/*      */}
			/*      */finally
			/*      */{
				/* 1487 */closeStatement(stmt);
				/* 1488 */closeConnection();
				/*      */
				/* 1490 */if (timer != null)
					/* 1491 */timer.logDebug("deleteDependants", "B[" + i + "] count=" + resultCount);
				/*      */}
			/*      */}
		/*      */}

	/*      */
	/*      */public void bulkGetAll(UserPK entityPK, UserEVO owningEVO, String dependants)
	/*      */{
		/* 1511 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		/*      */
		/* 1513 */PreparedStatement stmt = null;
		/* 1514 */ResultSet resultSet = null;
		/*      */
		/* 1516 */int itemCount = 0;
		/*      */
		/* 1518 */Collection theseItems = new ArrayList();
		/* 1519 */owningEVO.setDataEntryProfiles(theseItems);
		/* 1520 */owningEVO.setDataEntryProfilesAllItemsLoaded(true);
		/*      */try
		/*      */{
			/* 1524 */stmt = getConnection()
					.prepareStatement(
							"select DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID,DATA_ENTRY_PROFILE.VIS_ID,DATA_ENTRY_PROFILE.USER_ID,DATA_ENTRY_PROFILE.MODEL_ID,DATA_ENTRY_PROFILE.BUDGET_CYCLE_ID,DATA_ENTRY_PROFILE.AUTO_OPEN_DEPTH,DATA_ENTRY_PROFILE.DESCRIPTION,DATA_ENTRY_PROFILE.XMLFORM_ID,DATA_ENTRY_PROFILE.FILL_DISPLAY_AREA,DATA_ENTRY_PROFILE.SHOW_BOLD_SUMMARIES,DATA_ENTRY_PROFILE.SHOW_HORIZONTAL_LINES,DATA_ENTRY_PROFILE.STRUCTURE_ID0,DATA_ENTRY_PROFILE.STRUCTURE_ID1,DATA_ENTRY_PROFILE.STRUCTURE_ID2,DATA_ENTRY_PROFILE.STRUCTURE_ID3,DATA_ENTRY_PROFILE.STRUCTURE_ID4,DATA_ENTRY_PROFILE.STRUCTURE_ID5,DATA_ENTRY_PROFILE.STRUCTURE_ID6,DATA_ENTRY_PROFILE.STRUCTURE_ID7,DATA_ENTRY_PROFILE.STRUCTURE_ID8,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID0,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID1,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID2,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID3,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID4,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID5,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID6,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID7,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID8,DATA_ENTRY_PROFILE.ELEMENT_LABEL0,DATA_ENTRY_PROFILE.ELEMENT_LABEL1,DATA_ENTRY_PROFILE.ELEMENT_LABEL2,DATA_ENTRY_PROFILE.ELEMENT_LABEL3,DATA_ENTRY_PROFILE.ELEMENT_LABEL4,DATA_ENTRY_PROFILE.ELEMENT_LABEL5,DATA_ENTRY_PROFILE.ELEMENT_LABEL6,DATA_ENTRY_PROFILE.ELEMENT_LABEL7,DATA_ENTRY_PROFILE.ELEMENT_LABEL8,DATA_ENTRY_PROFILE.DATA_TYPE,DATA_ENTRY_PROFILE.VERSION_NUM,DATA_ENTRY_PROFILE.UPDATED_BY_USER_ID,DATA_ENTRY_PROFILE.UPDATED_TIME,DATA_ENTRY_PROFILE.CREATED_TIME, DATA_ENTRY_PROFILE.MOBILE from DATA_ENTRY_PROFILE where 1=1 and DATA_ENTRY_PROFILE.USER_ID = ? order by  DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID");
			/*      */
			/* 1526 */int col = 1;
			/* 1527 */stmt.setInt(col++, entityPK.getUserId());
			/*      */
			/* 1529 */resultSet = stmt.executeQuery();
			/*      */
			/* 1532 */while (resultSet.next())
			/*      */{
				/* 1534 */itemCount++;
				/* 1535 */this.mDetails = getEvoFromJdbc(resultSet);
				/*      */
				/* 1537 */theseItems.add(this.mDetails);
				/*      */}
			/*      */
			/* 1540 */if (timer != null) {
				/* 1541 */timer.logDebug("bulkGetAll", "items=" + itemCount);
				/*      */}
			/*      */
			/* 1544 */if ((itemCount > 0) && (dependants.indexOf("<3>") > -1))
			/*      */{
				/* 1546 */getDataEntryProfileHistoryDAO().bulkGetAll(entityPK, theseItems, dependants);
				/*      */}
			/*      */}
		/*      */catch (SQLException sqle) {
			/* 1550 */throw handleSQLException(
					"select DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID,DATA_ENTRY_PROFILE.VIS_ID,DATA_ENTRY_PROFILE.USER_ID,DATA_ENTRY_PROFILE.MODEL_ID,DATA_ENTRY_PROFILE.BUDGET_CYCLE_ID,DATA_ENTRY_PROFILE.AUTO_OPEN_DEPTH,DATA_ENTRY_PROFILE.DESCRIPTION,DATA_ENTRY_PROFILE.XMLFORM_ID,DATA_ENTRY_PROFILE.FILL_DISPLAY_AREA,DATA_ENTRY_PROFILE.SHOW_BOLD_SUMMARIES,DATA_ENTRY_PROFILE.SHOW_HORIZONTAL_LINES,DATA_ENTRY_PROFILE.STRUCTURE_ID0,DATA_ENTRY_PROFILE.STRUCTURE_ID1,DATA_ENTRY_PROFILE.STRUCTURE_ID2,DATA_ENTRY_PROFILE.STRUCTURE_ID3,DATA_ENTRY_PROFILE.STRUCTURE_ID4,DATA_ENTRY_PROFILE.STRUCTURE_ID5,DATA_ENTRY_PROFILE.STRUCTURE_ID6,DATA_ENTRY_PROFILE.STRUCTURE_ID7,DATA_ENTRY_PROFILE.STRUCTURE_ID8,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID0,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID1,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID2,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID3,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID4,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID5,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID6,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID7,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID8,DATA_ENTRY_PROFILE.ELEMENT_LABEL0,DATA_ENTRY_PROFILE.ELEMENT_LABEL1,DATA_ENTRY_PROFILE.ELEMENT_LABEL2,DATA_ENTRY_PROFILE.ELEMENT_LABEL3,DATA_ENTRY_PROFILE.ELEMENT_LABEL4,DATA_ENTRY_PROFILE.ELEMENT_LABEL5,DATA_ENTRY_PROFILE.ELEMENT_LABEL6,DATA_ENTRY_PROFILE.ELEMENT_LABEL7,DATA_ENTRY_PROFILE.ELEMENT_LABEL8,DATA_ENTRY_PROFILE.DATA_TYPE,DATA_ENTRY_PROFILE.VERSION_NUM,DATA_ENTRY_PROFILE.UPDATED_BY_USER_ID,DATA_ENTRY_PROFILE.UPDATED_TIME,DATA_ENTRY_PROFILE.CREATED_TIME, DATA_ENTRY_PROFILE.MOBILE from DATA_ENTRY_PROFILE where 1=1 and DATA_ENTRY_PROFILE.USER_ID = ? order by  DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID",
					sqle);
			/*      */}
		/*      */finally
		/*      */{
			/* 1554 */closeResultSet(resultSet);
			/* 1555 */closeStatement(stmt);
			/* 1556 */closeConnection();
			/*      */
			/* 1558 */this.mDetails = null;
			/*      */}
		/*      */}

	/*      */
	/*      */public Collection getAll(int selectUserId, String dependants, Collection currentList)
	/*      */{
		/* 1583 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		/* 1584 */PreparedStatement stmt = null;
		/* 1585 */ResultSet resultSet = null;
		/*      */
		/* 1587 */ArrayList items = new ArrayList();
		/*      */try
		/*      */{
			/* 1591 */stmt = getConnection()
					.prepareStatement(
							"select DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID,DATA_ENTRY_PROFILE.VIS_ID,DATA_ENTRY_PROFILE.USER_ID,DATA_ENTRY_PROFILE.MODEL_ID,DATA_ENTRY_PROFILE.BUDGET_CYCLE_ID,DATA_ENTRY_PROFILE.AUTO_OPEN_DEPTH,DATA_ENTRY_PROFILE.DESCRIPTION,DATA_ENTRY_PROFILE.XMLFORM_ID,DATA_ENTRY_PROFILE.FILL_DISPLAY_AREA,DATA_ENTRY_PROFILE.SHOW_BOLD_SUMMARIES,DATA_ENTRY_PROFILE.SHOW_HORIZONTAL_LINES,DATA_ENTRY_PROFILE.STRUCTURE_ID0,DATA_ENTRY_PROFILE.STRUCTURE_ID1,DATA_ENTRY_PROFILE.STRUCTURE_ID2,DATA_ENTRY_PROFILE.STRUCTURE_ID3,DATA_ENTRY_PROFILE.STRUCTURE_ID4,DATA_ENTRY_PROFILE.STRUCTURE_ID5,DATA_ENTRY_PROFILE.STRUCTURE_ID6,DATA_ENTRY_PROFILE.STRUCTURE_ID7,DATA_ENTRY_PROFILE.STRUCTURE_ID8,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID0,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID1,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID2,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID3,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID4,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID5,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID6,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID7,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID8,DATA_ENTRY_PROFILE.ELEMENT_LABEL0,DATA_ENTRY_PROFILE.ELEMENT_LABEL1,DATA_ENTRY_PROFILE.ELEMENT_LABEL2,DATA_ENTRY_PROFILE.ELEMENT_LABEL3,DATA_ENTRY_PROFILE.ELEMENT_LABEL4,DATA_ENTRY_PROFILE.ELEMENT_LABEL5,DATA_ENTRY_PROFILE.ELEMENT_LABEL6,DATA_ENTRY_PROFILE.ELEMENT_LABEL7,DATA_ENTRY_PROFILE.ELEMENT_LABEL8,DATA_ENTRY_PROFILE.DATA_TYPE,DATA_ENTRY_PROFILE.VERSION_NUM,DATA_ENTRY_PROFILE.UPDATED_BY_USER_ID,DATA_ENTRY_PROFILE.UPDATED_TIME,DATA_ENTRY_PROFILE.CREATED_TIME, DATA_ENTRY_PROFILE.MOBILE from DATA_ENTRY_PROFILE where    USER_ID = ? AND ROWNUM<50");
			/*      */
			/* 1593 */int col = 1;
			/* 1594 */stmt.setInt(col++, selectUserId);
			/*      */
			/* 1596 */resultSet = stmt.executeQuery();
			/*      */
			/* 1598 */while (resultSet.next())
			/*      */{
				/* 1600 */this.mDetails = getEvoFromJdbc(resultSet);
				/*      */
				/* 1603 */getDependants(this.mDetails, dependants);
				/*      */
				/* 1606 */items.add(this.mDetails);
				/*      */}
			/*      */
			/* 1609 */if (currentList != null)
			/*      */{
				/* 1612 */ListIterator iter = items.listIterator();
				/* 1613 */DataEntryProfileEVO currentEVO = null;
				/* 1614 */DataEntryProfileEVO newEVO = null;
				/* 1615 */while (iter.hasNext())
				/*      */{
					/* 1617 */newEVO = (DataEntryProfileEVO) iter.next();
					/* 1618 */Iterator iter2 = currentList.iterator();
					/* 1619 */while (iter2.hasNext())
					/*      */{
						/* 1621 */currentEVO = (DataEntryProfileEVO) iter2.next();
						/* 1622 */if (!currentEVO.getPK().equals(newEVO.getPK()))
							/*      */continue;
						/* 1624 */iter.set(currentEVO);
						/*      */}
					/*      */
					/*      */}
				/*      */
				/* 1630 */Iterator iter2 = currentList.iterator();
				/* 1631 */while (iter2.hasNext())
				/*      */{
					/* 1633 */currentEVO = (DataEntryProfileEVO) iter2.next();
					/* 1634 */if (currentEVO.insertPending()) {
						/* 1635 */items.add(currentEVO);
						/*      */}
					/*      */}
				/*      */}
			/* 1639 */this.mDetails = null;
			/*      */}
		/*      */catch (SQLException sqle)
		/*      */{
			/* 1643 */throw handleSQLException(
					"select DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID,DATA_ENTRY_PROFILE.VIS_ID,DATA_ENTRY_PROFILE.USER_ID,DATA_ENTRY_PROFILE.MODEL_ID,DATA_ENTRY_PROFILE.BUDGET_CYCLE_ID,DATA_ENTRY_PROFILE.AUTO_OPEN_DEPTH,DATA_ENTRY_PROFILE.DESCRIPTION,DATA_ENTRY_PROFILE.XMLFORM_ID,DATA_ENTRY_PROFILE.FILL_DISPLAY_AREA,DATA_ENTRY_PROFILE.SHOW_BOLD_SUMMARIES,DATA_ENTRY_PROFILE.SHOW_HORIZONTAL_LINES,DATA_ENTRY_PROFILE.STRUCTURE_ID0,DATA_ENTRY_PROFILE.STRUCTURE_ID1,DATA_ENTRY_PROFILE.STRUCTURE_ID2,DATA_ENTRY_PROFILE.STRUCTURE_ID3,DATA_ENTRY_PROFILE.STRUCTURE_ID4,DATA_ENTRY_PROFILE.STRUCTURE_ID5,DATA_ENTRY_PROFILE.STRUCTURE_ID6,DATA_ENTRY_PROFILE.STRUCTURE_ID7,DATA_ENTRY_PROFILE.STRUCTURE_ID8,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID0,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID1,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID2,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID3,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID4,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID5,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID6,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID7,DATA_ENTRY_PROFILE.STRUCTURE_ELEMENT_ID8,DATA_ENTRY_PROFILE.ELEMENT_LABEL0,DATA_ENTRY_PROFILE.ELEMENT_LABEL1,DATA_ENTRY_PROFILE.ELEMENT_LABEL2,DATA_ENTRY_PROFILE.ELEMENT_LABEL3,DATA_ENTRY_PROFILE.ELEMENT_LABEL4,DATA_ENTRY_PROFILE.ELEMENT_LABEL5,DATA_ENTRY_PROFILE.ELEMENT_LABEL6,DATA_ENTRY_PROFILE.ELEMENT_LABEL7,DATA_ENTRY_PROFILE.ELEMENT_LABEL8,DATA_ENTRY_PROFILE.DATA_TYPE,DATA_ENTRY_PROFILE.VERSION_NUM,DATA_ENTRY_PROFILE.UPDATED_BY_USER_ID,DATA_ENTRY_PROFILE.UPDATED_TIME,DATA_ENTRY_PROFILE.CREATED_TIME, DATA_ENTRY_PROFILE.MOBILE from DATA_ENTRY_PROFILE where    USER_ID = ? ",
					sqle);
			/*      */}
		/*      */finally
		/*      */{
			/* 1647 */closeResultSet(resultSet);
			/* 1648 */closeStatement(stmt);
			/* 1649 */closeConnection();
			/*      */
			/* 1651 */if (timer != null) {
				/* 1652 */timer.logDebug("getAll", " UserId=" + selectUserId + " items=" + items.size());
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 1657 */return items;
		/*      */}

	/*      */
	/*      */public DataEntryProfileEVO getDetails(UserCK paramCK, String dependants)
	/*      */throws ValidationException
	/*      */{
		/* 1674 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		/*      */
		/* 1677 */if (this.mDetails == null) {
			/* 1678 */doLoad(((DataEntryProfileCK) paramCK).getDataEntryProfilePK());
			/*      */}
		/* 1680 */else if (!this.mDetails.getPK().equals(((DataEntryProfileCK) paramCK).getDataEntryProfilePK())) {
			/* 1681 */doLoad(((DataEntryProfileCK) paramCK).getDataEntryProfilePK());
			/*      */}
		/*      */
		/* 1684 */if ((dependants.indexOf("<3>") > -1) && (!this.mDetails.isDataEntryProfilesHistoryAllItemsLoaded()))
		/*      */{
			/* 1689 */this.mDetails.setDataEntryProfilesHistory(getDataEntryProfileHistoryDAO().getAll(this.mDetails.getDataEntryProfileId(), dependants, this.mDetails.getDataEntryProfilesHistory()));
			/*      */
			/* 1696 */this.mDetails.setDataEntryProfilesHistoryAllItemsLoaded(true);
			/*      */}
		/*      */
		/* 1699 */if ((paramCK instanceof DataEntryProfileHistoryCK))
		/*      */{
			/* 1701 */if (this.mDetails.getDataEntryProfilesHistory() == null) {
				/* 1702 */this.mDetails.loadDataEntryProfilesHistoryItem(getDataEntryProfileHistoryDAO().getDetails(paramCK, dependants));
				/*      */}
			/*      */else {
				/* 1705 */DataEntryProfileHistoryPK pk = ((DataEntryProfileHistoryCK) paramCK).getDataEntryProfileHistoryPK();
				/* 1706 */DataEntryProfileHistoryEVO evo = this.mDetails.getDataEntryProfilesHistoryItem(pk);
				/* 1707 */if (evo == null) {
					/* 1708 */this.mDetails.loadDataEntryProfilesHistoryItem(getDataEntryProfileHistoryDAO().getDetails(paramCK, dependants));
					/*      */}
				/*      */}
			/*      */}
		/*      */
		/* 1713 */DataEntryProfileEVO details = new DataEntryProfileEVO();
		/* 1714 */details = this.mDetails.deepClone();
		/*      */
		/* 1716 */if (timer != null) {
			/* 1717 */timer.logDebug("getDetails", paramCK + " " + dependants);
			/*      */}
		/* 1719 */return details;
		/*      */}

	/*      */
	/*      */public DataEntryProfileEVO getDetails(UserCK paramCK, DataEntryProfileEVO paramEVO, String dependants)
	/*      */throws ValidationException
	/*      */{
		/* 1725 */DataEntryProfileEVO savedEVO = this.mDetails;
		/* 1726 */this.mDetails = paramEVO;
		/* 1727 */DataEntryProfileEVO newEVO = getDetails(paramCK, dependants);
		/* 1728 */this.mDetails = savedEVO;
		/* 1729 */return newEVO;
		/*      */}

	/*      */
	/*      */public DataEntryProfileEVO getDetails(String dependants)
	/*      */throws ValidationException
	/*      */{
		/* 1735 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		/*      */
		/* 1739 */getDependants(this.mDetails, dependants);
		/*      */
		/* 1742 */DataEntryProfileEVO details = this.mDetails.deepClone();
		/*      */
		/* 1744 */if (timer != null) {
			/* 1745 */timer.logDebug("getDetails", this.mDetails.getPK() + " " + dependants);
			/*      */}
		/* 1747 */return details;
		/*      */}

	/*      */
	/*      */protected DataEntryProfileHistoryDAO getDataEntryProfileHistoryDAO()
	/*      */{
		/* 1756 */if (this.mDataEntryProfileHistoryDAO == null)
		/*      */{
			/* 1758 */if (this.mDataSource != null)
				/* 1759 */this.mDataEntryProfileHistoryDAO = new DataEntryProfileHistoryDAO(this.mDataSource);
			/*      */else {
				/* 1761 */this.mDataEntryProfileHistoryDAO = new DataEntryProfileHistoryDAO(getConnection());
				/*      */}
			/*      */}
		/* 1764 */return this.mDataEntryProfileHistoryDAO;
		/*      */}

	/*      */
	/*      */public String getEntityName()
	/*      */{
		/* 1769 */return "DataEntryProfile";
		/*      */}

	/*      */
	/*      */public DataEntryProfileRefImpl getRef(DataEntryProfilePK paramDataEntryProfilePK)
	/*      */{
		/* 1774 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		/* 1775 */PreparedStatement stmt = null;
		/* 1776 */ResultSet resultSet = null;
		/*      */try
		/*      */{
			/* 1779 */stmt = getConnection().prepareStatement("select 0,USR.USER_ID,DATA_ENTRY_PROFILE.VIS_ID from DATA_ENTRY_PROFILE,USR where 1=1 and DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID = ? and DATA_ENTRY_PROFILE.USER_ID = USR.USER_ID");
			/* 1780 */int col = 1;
			/* 1781 */stmt.setInt(col++, paramDataEntryProfilePK.getDataEntryProfileId());
			/*      */
			/* 1783 */resultSet = stmt.executeQuery();
			/*      */
			/* 1785 */if (!resultSet.next()) {
				/* 1786 */throw new RuntimeException(getEntityName() + " getRef " + paramDataEntryProfilePK + " not found");
				/*      */}
			/* 1788 */col = 2;
			/* 1789 */UserPK newUserPK = new UserPK(resultSet.getInt(col++));
			/*      */
			/* 1793 */String textDataEntryProfile = resultSet.getString(col++);
			/* 1794 */DataEntryProfileCK ckDataEntryProfile = new DataEntryProfileCK(newUserPK, paramDataEntryProfilePK);
			/*      */
			/* 1799 */DataEntryProfileRefImpl localDataEntryProfileRefImpl = new DataEntryProfileRefImpl(ckDataEntryProfile, textDataEntryProfile);
			/*      */return localDataEntryProfileRefImpl;
			/*      */}
		/*      */catch (SQLException sqle)
		/*      */{
			/* 1804 */throw handleSQLException(paramDataEntryProfilePK, "select 0,USR.USER_ID,DATA_ENTRY_PROFILE.VIS_ID from DATA_ENTRY_PROFILE,USR where 1=1 and DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID = ? and DATA_ENTRY_PROFILE.USER_ID = USR.USER_ID", sqle);
			/*      */}
		/*      */finally
		/*      */{
			/* 1808 */closeResultSet(resultSet);
			/* 1809 */closeStatement(stmt);
			/* 1810 */closeConnection();
			/*      */
			/* 1812 */if (timer != null)
				/* 1813 */timer.logDebug("getRef", paramDataEntryProfilePK);
			/* 1813 */}
		/*      */}

	/*      */
	/*      */public void getDependants(Collection c, String dependants)
	/*      */{
		/* 1825 */if (c == null)
			/* 1826 */return;
		/* 1827 */Iterator iter = c.iterator();
		/* 1828 */while (iter.hasNext())
		/*      */{
			/* 1830 */DataEntryProfileEVO evo = (DataEntryProfileEVO) iter.next();
			/* 1831 */getDependants(evo, dependants);
			/*      */}
		/*      */}

	/*      */
	/*      */public void getDependants(DataEntryProfileEVO evo, String dependants)
	/*      */{
		/* 1845 */if (evo.insertPending()) {
			/* 1846 */return;
			/*      */}
		/* 1848 */if (evo.getDataEntryProfileId() < 1) {
			/* 1849 */return;
			/*      */}
		/*      */
		/* 1853 */if (dependants.indexOf("<3>") > -1)
		/*      */{
			/* 1856 */if (!evo.isDataEntryProfilesHistoryAllItemsLoaded())
			/*      */{
				/* 1858 */evo.setDataEntryProfilesHistory(getDataEntryProfileHistoryDAO().getAll(evo.getDataEntryProfileId(), dependants, evo.getDataEntryProfilesHistory()));
				/*      */
				/* 1865 */evo.setDataEntryProfilesHistoryAllItemsLoaded(true);
				/*      */}
			/*      */}
		/*      */}


    public void updateDataEntryProfileHistory(int userId, int modelId, int budgetCycleId, int budgetLocationElementId) {
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        ResultSet resultSet = null;
        try {
            stmt1 = getConnection().prepareStatement(SQL_CHECK_HISTORY);

            int col = 1;
            stmt1.setInt(col++, userId);
            stmt1.setInt(col++, modelId);
            stmt1.setInt(col++, budgetLocationElementId);
            stmt1.setInt(col++, budgetCycleId);

            resultSet = stmt1.executeQuery();

            int profileId = 0;
            if (resultSet.next()) {
                profileId = resultSet.getInt(1);
            }

            if (profileId != this.mDetails.getPK().getDataEntryProfileId()) {
                if (profileId == 0) {
                    stmt2 = getConnection().prepareStatement(SQL_INSERT_HISTORY);
                    col = 1;
                    stmt2.setInt(col++, userId);
                    stmt2.setInt(col++, modelId);
                    stmt2.setInt(col++, budgetLocationElementId);
                    stmt2.setInt(col++, budgetCycleId);
                    stmt2.setInt(col++, this.mDetails.getPK().getDataEntryProfileId());
                    stmt2.execute();
                } else {
                    stmt3 = getConnection().prepareStatement(SQL_UPDATE_HISTORY);
                    col = 1;
                    stmt3.setInt(col++, this.mDetails.getPK().getDataEntryProfileId());
                    stmt3.setInt(col++, userId);
                    stmt3.setInt(col++, modelId);
                    stmt3.setInt(col++, budgetLocationElementId);
                    stmt3.setInt(col++, budgetCycleId);
                    stmt3.execute();
                }
            }
        } catch (SQLException sqle) {
            throw handleSQLException("Can't update the data entry profile history", sqle);
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt1);
            closeStatement(stmt2);
            closeStatement(stmt3);
            closeConnection();
        }
    }

    public void deleteFormsforXMLFormId(int formId) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        try {
            stmt = getConnection().prepareStatement("delete from DATA_ENTRY_PROFILE where XMLFORM_ID = ?");
            stmt.setInt(1, formId);

            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw handleSQLException("delete from DATA_ENTRY_PROFILE where XMLFORM_ID = ?", sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null)
            timer.logDebug("deleteFormsforXMLFormId");
    }
    
    public void deleteFormsforXMLFormId(int formId, Boolean mobile) {
        Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
        PreparedStatement stmt = null;
        try {
            if (mobile != null && mobile) {
                stmt = getConnection().prepareStatement("delete from DATA_ENTRY_PROFILE where XMLFORM_ID = ? and MOBILE = 'Y'");
            } else {
                stmt = getConnection().prepareStatement("delete from DATA_ENTRY_PROFILE where XMLFORM_ID = ? and MOBILE <> 'Y'");
            }
            stmt.setInt(1, formId);           
            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw handleSQLException("delete from DATA_ENTRY_PROFILE where XMLFORM_ID = ?", sqle);
        } finally {
            closeStatement(stmt);
            closeConnection();
        }

        if (timer != null)
            timer.logDebug("deleteFormsforXMLFormId");
    }
    
	public List<Integer> selectProfileUserIdforXMLFormId(int formId) {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		List userIds = new ArrayList();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement("select distinct user_id from DATA_ENTRY_PROFILE where XMLFORM_ID = ?");
			stmt.setInt(1, formId);

			rs = stmt.executeQuery();
			while (rs.next()) {
				userIds.add(Integer.valueOf(rs.getInt(1)));
			}
		} catch (SQLException sqle) {
			throw handleSQLException("delete from DATA_ENTRY_PROFILE where XMLFORM_ID = ?", sqle);
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			closeConnection();
		}

		if (timer != null) {
			timer.logDebug("deleteFormsforXMLFormId");
		}
		return userIds;
	}

	public void deleteDataEntryProfiles(Object[][] forms, int modelId) {
		// Data entry profiles
		for (Object[] form : forms) {
			/* 2316 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
			/*      */
			/* 2318 */PreparedStatement stmt = null;
			/*      */
			/* 2320 */int resultCount = 0;
			/* 2321 */String s = null;
			/*      */try
			/*      */{
				/* 2324 */s = DELETE_ALL_PROFILES_FOR_FORM;
				/*      */
				/* 2326 */if (this._log.isDebugEnabled()) {
					/* 2327 */this._log.debug("delete profiles", s);
					/*      */}
				/* 2329 */stmt = getConnection().prepareStatement(s);
				/*      */
				/* 2332 */int col = 1;
				/* 2333 */stmt.setInt(col++, (Integer) form[4]);
				/*      */
				/* 2336 */resultCount = stmt.executeUpdate();
				/*      */}
			/*      */catch (SQLException sqle)
			/*      */{
				/*      */}
			/*      */finally
			/*      */{
				/* 2344 */closeStatement(stmt);
				/* 2345 */closeConnection();
				/*      */
				/* 2347 */if (timer != null)
					/* 2348 */timer.logDebug("delete profiles", new StringBuilder().append(" count=").append(resultCount).toString());
				/*      */}
			/*      */}

		// HISTORY
		/* 2316 */Timer timer = this._log.isDebugEnabled() ? new Timer(this._log) : null;
		/*      */
		/* 2318 */PreparedStatement stmt = null;
		/*      */
		/* 2320 */int resultCount = 0;
		/* 2321 */String s = null;
		/*      */try
		/*      */{
			/* 2324 */s = DELETE_ALL_PROFILE_HISTORY_FOR_MODEL;
			/*      */
			/* 2326 */if (this._log.isDebugEnabled()) {
				/* 2327 */this._log.debug("delete data entry history", s);
				/*      */}
			/* 2329 */stmt = getConnection().prepareStatement(s);
			/*      */
			/* 2332 */int col = 1;
			/* 2333 */stmt.setInt(col++, (Integer) modelId);
			/*      */
			/* 2336 */resultCount = stmt.executeUpdate();
			/*      */}
		/*      */catch (SQLException sqle)
		/*      */{
			/*      */}
		/*      */finally
		/*      */{
			/* 2344 */closeStatement(stmt);
			/* 2345 */closeConnection();
			/*      */
			/* 2347 */if (timer != null)
				/* 2348 */timer.logDebug("delete data entry history", new StringBuilder().append(" count=").append(resultCount).toString());
			/*      */}
		/*      */}

	public List<Object[]> getDataProfileForBudgetCycleAndUser(int userId, int budgetCycleId) {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		List result = new ArrayList();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement("select DATA_ENTRY_PROFILE.DATA_ENTRY_PROFILE_ID, DATA_ENTRY_PROFILE.VIS_ID, DATA_ENTRY_PROFILE.DESCRIPTION from DATA_ENTRY_PROFILE where DATA_ENTRY_PROFILE.USER_ID = ?  and  DATA_ENTRY_PROFILE.BUDGET_CYCLE_ID = ?");
			stmt.setInt(1, userId);
			stmt.setInt(2, budgetCycleId);

			rs = stmt.executeQuery();
			while (rs.next()) {
				Object[] data = new Object[3];
				data[0] = rs.getInt(1);
				data[1] = rs.getString(2);
				data[2] = rs.getString(3);
				result.add(data);
			}
		} catch (SQLException sqle) {
			throw handleSQLException("get DP For BC and User", sqle);
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			closeConnection();
		}

		if (timer != null) {
			timer.logDebug("get DP For BC and User");
		}
		return result;
	}
	
	public AllSystemDataEntryProfilesForUserELO getAllSystemDataEntryProfilesForUser(int param1, int param2) {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		AllSystemDataEntryProfilesForUserELO results = new AllSystemDataEntryProfilesForUserELO();
		try {
			stmt = getConnection().prepareStatement(SQL_ALL_SYSTEM_DATA_ENTRY_PROFILES_FOR_USER);
			int col = 1;
			stmt.setInt(col++, param1);
			stmt.setInt(col++, param2);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				col = 2;

				UserPK pkUser = new UserPK(resultSet.getInt(col++));

				String textUser = resultSet.getString(col++);

				DataEntryProfilePK pkDataEntryProfile = new DataEntryProfilePK(resultSet.getInt(col++));

				String textDataEntryProfile = resultSet.getString(col++);

				DataEntryProfileCK ckDataEntryProfile = new DataEntryProfileCK(pkUser, pkDataEntryProfile);

				UserRefImpl erUser = new UserRefImpl(pkUser, textUser);

				DataEntryProfileRefImpl erDataEntryProfile = new DataEntryProfileRefImpl(ckDataEntryProfile, textDataEntryProfile);

				String col1 = resultSet.getString(col++);
				int col2 = resultSet.getInt(col++);

				results.add(erDataEntryProfile, erUser, col1, col2);
			}

		} catch (SQLException sqle) {
			throw handleSQLException(SQL_ALL_SYSTEM_DATA_ENTRY_PROFILES_FOR_USER, sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}

		if (timer != null) {
			timer.logDebug("getAllSystemGeneratedDataEntryProfilesForUser", " UserId=" + param1 + ",ModelId=" + param2 + " items=" + results.size());
		}

		return results;
	}

	public DataEntryProfileEVO getDetails(DataEntryProfileRef ref) throws ValidationException {
		Object key = ref.getPrimaryKey();
		if ((key instanceof DataEntryProfilePK)) {
			DataEntryProfilePK pk = (DataEntryProfilePK) ref.getPrimaryKey();
			doLoad(pk);
		}
		if ((key instanceof DataEntryProfileCK)) {
			DataEntryProfileCK ck = (DataEntryProfileCK) ref.getPrimaryKey();
			doLoad(ck.getDataEntryProfilePK());
		}

		return mDetails;
	}
	
	 private int getNewDataEntryProfileId() {

	        PreparedStatement pkStm = null;
	        int pk = 0;
	        try {
	            Connection conn = getConnection();

	            pkStm = conn.prepareStatement("SELECT MAX(DATA_ENTRY_PROFILE_ID) FROM DATA_ENTRY_PROFILE");
	            ResultSet resultSet = pkStm.executeQuery();
	            
	            while (resultSet.next()) {
	                pk = resultSet.getInt(1);
	            }
	        } catch (SQLException sqle) {
	            throw handleSQLException("", sqle);
	        } finally {
	            closeStatement(pkStm);
	            closeConnection();
	        }
	        return ++pk;
	    }
}

/*
 * Location: /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar Qualified Name: com.cedar.cp.ejb.impl.user.DataEntryProfileDAO JD-Core Version: 0.6.0
 */