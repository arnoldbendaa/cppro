package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.model.BudgetCycleLinkCK;
import com.cedar.cp.dto.model.BudgetCycleLinkPK;
import com.cedar.cp.dto.model.BudgetCycleLinkRefImpl;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.xmlform.AllXMLFormUserLinkELO;
import com.cedar.cp.dto.xmlform.CheckXMLFormUserLinkELO;
import com.cedar.cp.dto.xmlform.XmlFormCK;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.dto.xmlform.XmlFormRefImpl;
import com.cedar.cp.dto.xmlform.XmlFormUserLinkCK;
import com.cedar.cp.dto.xmlform.XmlFormUserLinkPK;
import com.cedar.cp.dto.xmlform.XmlFormUserLinkRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import javax.sql.DataSource;

public class BudgetCycleLinkDAO extends AbstractDAO {

	Log _log = new Log(getClass());
	protected BudgetCycleLinkEVO mDetails;

	public BudgetCycleLinkDAO(Connection connection) {
		super(connection);
	}

	public BudgetCycleLinkDAO() {
	}

	public BudgetCycleLinkDAO(DataSource ds) {
		super(ds);
	}

	protected BudgetCycleLinkPK getPK() {
		return mDetails.getPK();
	}

	public void setDetails(BudgetCycleLinkEVO details) {
		mDetails = details.deepClone();
	}

	private BudgetCycleLinkEVO getEvoFromJdbc(ResultSet resultSet_) throws SQLException {
		int col = 1;
		BudgetCycleLinkEVO evo = new BudgetCycleLinkEVO(resultSet_.getInt(col++), resultSet_.getInt(col++), resultSet_.getString(col++));
		return evo;
	}

	private int putEvoKeysToJdbc(BudgetCycleLinkEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
		int col = startCol_;
		stmt_.setInt(col++, evo_.getBudgetCycleId());
		stmt_.setInt(col++, evo_.getXmlFormId());
		return col;
	}

	private int putEvoDataToJdbc(BudgetCycleLinkEVO evo_, PreparedStatement stmt_, int startCol_) throws SQLException {
		int col = startCol_;
		stmt_.setString(col++, evo_.getXmlFormDataType());
		return col;
	}

	protected void doLoad(BudgetCycleLinkPK pk) throws ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = getConnection().prepareStatement("select BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID, BUDGET_CYCLE_LINK.XML_FORM_ID, BUDGET_CYCLE_LINK.XML_FORM_DATA_TYPE from BUDGET_CYCLE_LINK where BUDGET_CYCLE_ID = ? AND XML_FORM_ID = ? ");

			int col = 1;
			stmt.setInt(col++, pk.getBudgetCycleId());
			stmt.setInt(col++, pk.getXmlFormId());

			resultSet = stmt.executeQuery();

			if (!resultSet.next()) {
				throw new ValidationException(getEntityName() + " select of " + pk + " not found");
			}

			mDetails = getEvoFromJdbc(resultSet);
			if (mDetails.isModified())
				_log.info("doLoad", mDetails);
		} catch (SQLException sqle) {
			throw handleSQLException(pk, "select BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID, BUDGET_CYCLE_LINK.XML_FORM_ID from BUDGET_CYCLE_LINK where BUDGET_CYCLE_ID = ? AND XML_FORM_ID = ? ", sqle);
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
		PreparedStatement stmt = null;
		try {
			stmt = getConnection().prepareStatement("insert into BUDGET_CYCLE_LINK (BUDGET_CYCLE_ID,XML_FORM_ID,XML_FORM_DATA_TYPE) values (?,?,?)");

			int col = 1;
			col = putEvoKeysToJdbc(mDetails, stmt, col);
			col = putEvoDataToJdbc(mDetails, stmt, col);

			int resultCount = stmt.executeUpdate();
			if (resultCount != 1)
			{
				throw new RuntimeException(getEntityName() + " insert failed (" + mDetails.getPK() + "): resultCount=" + resultCount);
			}

			mDetails.reset();
		} catch (SQLException sqle) {
			throw handleSQLException(mDetails.getPK(), "insert into BUDGET_CYCLE_LINK (BUDGET_CYCLE_ID,XML_FORM_ID,XML_FORM_DATA_TYPE) values (?,?,?)", sqle);
		} finally {
			closeStatement(stmt);
			closeConnection();

			if (timer != null)
				timer.logDebug("doCreate", mDetails.toString());
		}
	}

	protected void doStore() throws DuplicateNameValidationException, VersionValidationException, ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		PreparedStatement stmt = null;

		boolean mainChanged = mDetails.isModified();
		boolean dependantChanged = false;
		try {
			if ((mainChanged) || (dependantChanged)) {
				stmt = getConnection().prepareStatement("update BUDGET_CYCLE_LINK set XML_FORM_DATA_TYPE = ? where BUDGET_CYCLE_ID = ? AND XML_FORM_ID = ?");

				int col = 1;
				col = putEvoDataToJdbc(mDetails, stmt, col);
				col = putEvoKeysToJdbc(mDetails, stmt, col);

				int resultCount = stmt.executeUpdate();

				if (resultCount != 1) {
					throw new RuntimeException(getEntityName() + " update failed (" + getPK() + "): resultCount=" + resultCount);
				}

				mDetails.reset();
			}

		} catch (SQLException sqle) {
			throw handleSQLException(getPK(), "update BUDGET_CYCLE_LINK set XML_FORM_DATA_TYPE = ? where BUDGET_CYCLE_ID = ? AND XML_FORM_ID = ?", sqle);
		} finally {
			closeStatement(stmt);
			closeConnection();

			if ((timer != null) && (
					(mainChanged) || (dependantChanged)))
				timer.logDebug("store", mDetails.getPK() + "(" + mainChanged + "," + dependantChanged + ")");
		}
	}

	public boolean update(Map items) throws DuplicateNameValidationException, VersionValidationException, ValidationException {
		if (items == null) {
			return false;
		}
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		PreparedStatement deleteStmt = null;

		boolean somethingChanged = false;
		try {
			Iterator iter2 = new ArrayList(items.values()).iterator();
			while (iter2.hasNext()) {
				mDetails = ((BudgetCycleLinkEVO) iter2.next());

				if (mDetails.deletePending()) {
					somethingChanged = true;

					if (deleteStmt == null) {
						deleteStmt = getConnection().prepareStatement("delete from BUDGET_CYCLE_LINK where BUDGET_CYCLE_ID = ? AND XML_FORM_ID = ?");
					}

					int col = 1;
					deleteStmt.setInt(col++, mDetails.getBudgetCycleId());
					deleteStmt.setInt(col++, mDetails.getXmlFormId());

					if (_log.isDebugEnabled()) {
						_log.debug("update", "BudgetCycleLink deleting BudgetCycleId=" + mDetails.getBudgetCycleId() + ",XmlFormId=" + mDetails.getXmlFormId());
					}

					deleteStmt.addBatch();

					items.remove(mDetails.getPK());
				}

			}

			if (deleteStmt != null) {
				Timer timer2 = _log.isDebugEnabled() ? new Timer(_log) : null;

				deleteStmt.executeBatch();

				if (timer2 != null) {
					timer2.logDebug("update", "delete batch");
				}
			}

			Iterator iter1 = items.values().iterator();
			while (iter1.hasNext()) {
				mDetails = ((BudgetCycleLinkEVO) iter1.next());

				if (mDetails.insertPending()) {
					somethingChanged = true;
					doCreate();
				} else if (mDetails.isModified()) {
					somethingChanged = true;
					doStore();
				}

			}

			return somethingChanged;
		} catch (SQLException sqle) {
			throw handleSQLException("delete from BUDGET_CYCLE_LINK where BUDGET_CYCLE_ID = ? AND XML_FORM_ID = ?", sqle);
		} finally {
			if (deleteStmt != null) {
				closeStatement(deleteStmt);
				closeConnection();
			}

			mDetails = null;

			if ((somethingChanged) &&
					(timer != null))
				timer.logDebug("update", "collection");
		}
	}

	public void bulkGetAll(ModelPK entityPK, Collection owners, String dependants) {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		PreparedStatement stmt = null;
		ResultSet resultSet = null;

		int itemCount = 0;

		BudgetCycleEVO owningEVO = null;
		Iterator ownersIter = owners.iterator();
		while (ownersIter.hasNext()) {
			owningEVO = (BudgetCycleEVO) ownersIter.next();
			owningEVO.setBudgetCycleLinksAllItemsLoaded(true);
		}
		ownersIter = owners.iterator();
		owningEVO = (BudgetCycleEVO) ownersIter.next();
		Collection theseItems = null;
		try {
			stmt = getConnection().prepareStatement("select BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID, BUDGET_CYCLE_LINK.XML_FORM_ID, BUDGET_CYCLE_LINK.XML_FORM_DATA_TYPE from BUDGET_CYCLE_LINK, BUDGET_CYCLE where 1=1 and BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BUDGET_CYCLE.MODEL_ID = ? order by BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID, BUDGET_CYCLE_LINK.XML_FORN_ID");

			int col = 1;
			stmt.setInt(col++, entityPK.getModelId());

			resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				itemCount++;
				mDetails = getEvoFromJdbc(resultSet);

				while (mDetails.getBudgetCycleId() != owningEVO.getBudgetCycleId()) {
					if (!ownersIter.hasNext()) {
						_log.debug("bulkGetAll", "can't find owning [BudgetCycleId=" + mDetails.getBudgetCycleId() + "] for " + mDetails.getPK());

						_log.debug("bulkGetAll", "loaded owners are:");
						ownersIter = owners.iterator();
						while (ownersIter.hasNext()) {
							owningEVO = (BudgetCycleEVO) ownersIter.next();
							_log.debug("bulkGetAll", "    " + owningEVO.toString());
						}
						throw new IllegalStateException("can't find owner");
					}
					owningEVO = (BudgetCycleEVO) ownersIter.next();
				}

				if (owningEVO.getBudgetCycleLinks() == null) {
					theseItems = new ArrayList();
					owningEVO.setBudgetCycleLinks(theseItems);
					owningEVO.setBudgetCycleLinksAllItemsLoaded(true);
				}
				theseItems.add(mDetails);
			}

			if (timer != null) {
				timer.logDebug("bulkGetAll", "items=" + itemCount);
			}
		} catch (SQLException sqle) {
			throw handleSQLException("select BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID, BUDGET_CYCLE_LINK.XML_FORM_ID, BUDGET_CYCLE_LINK.XML_FORM_DATA_TYPE from BUDGET_CYCLE_LINK, BUDGET_CYCLE where 1=1 and BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID and BUDGET_CYCLE.MODEL_ID = ? order by BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID, BUDGET_CYCLE_LINK.XML_FORN_ID", sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();

			mDetails = null;
		}
	}

	public Collection getAll(int selectBudgetCycleId, String dependants, Collection currentList) {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;

		ArrayList items = new ArrayList();
		try {
			stmt = getConnection().prepareStatement("select BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID, BUDGET_CYCLE_LINK.XML_FORM_ID, BUDGET_CYCLE_LINK.XML_FORM_DATA_TYPE from BUDGET_CYCLE_LINK where BUDGET_CYCLE_ID = ?");

			int col = 1;
			stmt.setInt(col++, selectBudgetCycleId);

			resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				mDetails = getEvoFromJdbc(resultSet);
				items.add(mDetails);
			}

			if (currentList != null) {
				ListIterator iter = items.listIterator();
				BudgetCycleLinkEVO currentEVO = null;
				BudgetCycleLinkEVO newEVO = null;
				while (iter.hasNext()) {
					newEVO = (BudgetCycleLinkEVO) iter.next();
					Iterator iter2 = currentList.iterator();
					while (iter2.hasNext()) {
						currentEVO = (BudgetCycleLinkEVO) iter2.next();
						if (currentEVO.getPK().equals(newEVO.getPK())) {
							iter.set(currentEVO);
						}
					}
				}

				Iterator iter2 = currentList.iterator();
				while (iter2.hasNext()) {
					currentEVO = (BudgetCycleLinkEVO) iter2.next();
					if (currentEVO.insertPending()) {
						items.add(currentEVO);
					}
				}
			}
			mDetails = null;
		} catch (SQLException sqle) {
			throw handleSQLException("select BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID, BUDGET_CYCLE_LINK.XML_FORM_ID, BUDGET_CYCLE_LINK.XML_FORM_DATA_TYPE from BUDGET_CYCLE_LINK where BUDGET_CYCLE_ID = ?", sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();

			if (timer != null) {
				timer.logDebug("getAll", " XmlFormId=" + selectBudgetCycleId + " items=" + items.size());
			}

		}

		return items;
	}

	public BudgetCycleLinkEVO getDetails(ModelCK paramCK, String dependants) throws ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		if (mDetails == null) {
			doLoad(((BudgetCycleLinkCK) paramCK).getBudgetCycleLinkPK());
		}
		else if (!mDetails.getPK().equals(((BudgetCycleLinkCK) paramCK).getBudgetCycleLinkPK())) {
			doLoad(((BudgetCycleLinkCK) paramCK).getBudgetCycleLinkPK());
		}

		BudgetCycleLinkEVO details = new BudgetCycleLinkEVO();
		details = mDetails.deepClone();

		if (timer != null) {
			timer.logDebug("getDetails", paramCK + " " + dependants);
		}
		return details;
	}

	public BudgetCycleLinkEVO getDetails(ModelCK paramCK, BudgetCycleLinkEVO paramEVO, String dependants) throws ValidationException {
		BudgetCycleLinkEVO savedEVO = mDetails;
		mDetails = paramEVO;
		BudgetCycleLinkEVO newEVO = getDetails(paramCK, dependants);
		mDetails = savedEVO;
		return newEVO;
	}

	public BudgetCycleLinkEVO getDetails(String dependants) throws ValidationException {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;

		BudgetCycleLinkEVO details = mDetails.deepClone();

		if (timer != null) {
			timer.logDebug("getDetails", mDetails.getPK() + " " + dependants);
		}
		return details;
	}

	public String getEntityName() {
		return "BudgetCycleLink";
	}

	public BudgetCycleLinkRefImpl getRef(BudgetCycleLinkPK paramBudgetCycleLinkPK) {
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = getConnection().prepareStatement("select 0,MODEL.MODEL_ID, BUDGET_CYCLE.BUDGET_CYCLE_ID from BUDGET_CYCLE_LINK\njoin BUDGET_CYCLE on (1=1\nand BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID\n)\njoin MODEL on (1=1\nand BUDGET_CYCLE.MODEL_ID = MODEL.MODEL_ID\n) where 1=1 and BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID = ? and BUDGET_CYCLE_LINK.XML_FORM_ID = ?");
			int col = 1;
			stmt.setInt(col++, paramBudgetCycleLinkPK.getBudgetCycleId());
			stmt.setInt(col++, paramBudgetCycleLinkPK.getXmlFormId());

			resultSet = stmt.executeQuery();

			if (!resultSet.next()) {
				throw new RuntimeException(getEntityName() + " getRef " + paramBudgetCycleLinkPK + " not found");
			}
			col = 2;
			ModelPK newModelPK = new ModelPK(resultSet.getInt(col++));

			BudgetCyclePK newBudgetCyclePK = new BudgetCyclePK(resultSet.getInt(col++));

			String textBudgetCycleLink = "";
			BudgetCycleLinkCK ckBudgetCycleLink = new BudgetCycleLinkCK(newModelPK, newBudgetCyclePK, paramBudgetCycleLinkPK);

			return new BudgetCycleLinkRefImpl(ckBudgetCycleLink, textBudgetCycleLink);
		} catch (SQLException sqle) {
			throw handleSQLException(paramBudgetCycleLinkPK, "select 0,MODEL.MODEL_ID, BUDGET_CYCLE.BUDGET_CYCLE_ID from BUDGET_CYCLE_LINK\njoin BUDGET_CYCLE on (1=1\nand BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID = BUDGET_CYCLE.BUDGET_CYCLE_ID\n)\njoin MODEL on (1=1\nand BUDGET_CYCLE.MODEL_ID = MODEL.MODEL_ID\n) where 1=1 and BUDGET_CYCLE_LINK.BUDGET_CYCLE_ID = ? and BUDGET_CYCLE_LINK.XML_FORM_ID = ?", sqle);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();

			if (timer != null)
				timer.logDebug("getRef", paramBudgetCycleLinkPK);
		}
	}

	public void insertLink(BudgetCycleLinkEVO activityLinkEVO) {
		mDetails = activityLinkEVO;
		Timer timer = _log.isDebugEnabled() ? new Timer(_log) : null;
		PreparedStatement stmt = null;
		try {
			stmt = getConnection().prepareStatement("insert into BUDGET_CYCLE_LINK (BUDGET_CYCLE_ID, XML_FORM_ID, XML_FORM_DATA_TYPE) values (?, ?, ?)");

			int col = 1;
			col = putEvoKeysToJdbc(mDetails, stmt, col);
			col = putEvoDataToJdbc(mDetails, stmt, col);

			int resultCount = stmt.executeUpdate();
			if (resultCount != 1)
			{
				throw new RuntimeException(getEntityName() + " insert failed (" + mDetails.getPK() + "): resultCount=" + resultCount);
			}
		} catch (SQLException sqle) {
			if (sqle.getErrorCode() != 1)
				throw handleSQLException(mDetails.getPK(), "insert into BUDGET_CYCLE_LINK (BUDGET_CYCLE_ID, XML_FORM_ID, XML_FORM_DATA_TYPE) values (?, ?, ?)", sqle);
		} finally {
			closeStatement(stmt);
			closeConnection();

			if (timer != null)
				timer.logDebug("insertLink", mDetails.toString());
		}
	}
}