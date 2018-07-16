package com.cedar.cp.ejb.impl.extsys;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.dto.base.EntityListImpl;
import com.cedar.cp.dto.dataEntry.FinanceSystemCellDataImpl;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.common.JdbcUtils;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

public class OaAccessorDAO extends AbstractDAO {
	Log _log = new Log(getClass());
	private ExternalSystemDAO mOwningDAO;

	public OaAccessorDAO(DataSource ds, ExternalSystemDAO owner) {
		super(ds);
		mOwningDAO = owner;
	}

	public EntityList getCompanies(String sql) throws SQLException {
		if (_log.isDebugEnabled())
			_log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = getConnection().prepareStatement(sql);
			stmt.setFetchSize(500);
			resultSet = stmt.executeQuery();
			return JdbcUtils.extractToEntityListImpl(ExternalSystemDAO.COMPANIES_COL_INFO, resultSet);
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
		return null;
	}

	public EntityList getFinanceCalendarYears(String sql, String company) throws SQLException {
		if (_log.isDebugEnabled())
			_log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = getConnection().prepareStatement(sql);
			stmt.setInt(1, new Integer(company).intValue());
			resultSet = stmt.executeQuery();
			return JdbcUtils.extractToEntityListImpl(ExternalSystemDAO.CALENDARS_COL_INFO, resultSet);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
	}

	public EntityList getGlobalFinanceCalendarYears(String sql, List<String> companies) throws SQLException {
		if (this._log.isDebugEnabled())
			this._log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;

		sql = sql.replace("select distinct", "select distinct company as CMPY,");
		sql = sql.replace("order by pyear", "order by company, pyear");
		String sql1 = "in (" + companies.get(0);
		for (int i = 1; i < companies.size(); i++) {
			sql1 = sql1 + ", " + companies.get(i);
		}
		sql1 = sql1 + ")";
		sql = sql.replace("= ?", sql1);

		try {
			stmt = getConnection().prepareStatement(sql);
			resultSet = stmt.executeQuery();
			EntityListImpl localEntityListImpl = JdbcUtils.extractToEntityListImpl(ExternalSystemDAO.GLOBAL_CALENDARS_COL_INFO, resultSet);
			return localEntityListImpl;
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		} // throw localObject;
	}

	public EntityList getFinancePeriods(String sql, String company, int year) throws SQLException {
		if (_log.isDebugEnabled())
			_log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = getConnection().prepareStatement(sql);
			stmt.setInt(1, new Integer(company).intValue());
			stmt.setInt(2, year);
			resultSet = stmt.executeQuery();
			return JdbcUtils.extractToEntityListImpl(ExternalSystemDAO.PERIODS_COL_INFO, resultSet);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
	}

	public EntityList getFinanceLedgers(String sql, String company) throws SQLException {
		if (_log.isDebugEnabled())
			_log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = getConnection().prepareStatement(sql);
			stmt.setInt(1, new Integer(company).intValue());
			stmt.setInt(2, new Integer(company).intValue());
			resultSet = stmt.executeQuery();
			return JdbcUtils.extractToEntityListImpl(ExternalSystemDAO.LEDGERS_COL_INFO, resultSet);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
	}

	public EntityList getFinanceDimensions(String sql, String company, String ledger) throws SQLException {
		if (_log.isDebugEnabled())
			_log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = getConnection().prepareStatement(sql);
			stmt.setInt(1, new Integer(company).intValue());
			stmt.setString(2, ledger);
			stmt.setString(3, ledger);
			resultSet = stmt.executeQuery();
			return JdbcUtils.extractToEntityListImpl(ExternalSystemDAO.DIMENSIONS_COL_INFO, resultSet);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
	}

	public EntityList getGlobalFinanceDimensions(String sql, List<String> companies, String ledger) throws SQLException {
		if (this._log.isDebugEnabled())
			this._log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		sql = "select CMPY," + sql.substring(6);
		String sql1 = sql;
		for (int i = 0; i < companies.size(); i++) {
			if (i > 0) {
				sql = sql + " union all " + sql1;
			}
		}
		try {
			stmt = getConnection().prepareStatement(sql);
			int j = 1;
			for (String company : companies) {
				stmt.setInt(j, new Integer(company).intValue());
				j++;
				stmt.setString(j, ledger);
				j++;
				stmt.setString(j, ledger);
				j++;
			}
			resultSet = stmt.executeQuery();
			EntityListImpl localEntityListImpl = JdbcUtils.extractToEntityListImpl(ExternalSystemDAO.GLOBAL_DIMENSIONS_COL_INFO, resultSet);
			return localEntityListImpl;
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		} // throw localObject;
	}

	public EntityList getFinanceValueTypes(String sql, String company, String ledger, String dimCodes) throws SQLException {
		if (_log.isDebugEnabled())
			_log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			_log.debug(sql);
			stmt = getConnection().prepareStatement(sql);
			resultSet = stmt.executeQuery();
			return JdbcUtils.extractToEntityListImpl(ExternalSystemDAO.VALUE_TYPES_COL_INFO, resultSet);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
	}

	public EntityList getGlobalFinanceValueTypes(String sql, List<String> companies, String ledger, String dimCodes) throws SQLException {
		if (this._log.isDebugEnabled())
			this._log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String sql1 = "in (" + companies.get(0);
		for (int i = 1; i < companies.size(); i++) {
			sql1 = sql1 + ", " + companies.get(i);
		}
		sql1 = sql1 + ")";
		sql = sql.replace("= ?", sql1);
		sql = sql.replace("union all", "union");
		try {
			this._log.debug(sql);
			stmt = getConnection().prepareStatement(sql);
			resultSet = stmt.executeQuery();
			EntityListImpl localEntityListImpl = JdbcUtils.extractToEntityListImpl(ExternalSystemDAO.VALUE_TYPES_COL_INFO, resultSet);
			return localEntityListImpl;
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		} // throw localObject;
	}

	public EntityList getFinanceHierarchies(String sql, String company, String ledger, String extSysDimType, String dimCode) throws SQLException {
		if (_log.isDebugEnabled())
			_log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = getConnection().prepareStatement(sql);
			resultSet = stmt.executeQuery();
			return JdbcUtils.extractToEntityListImpl(ExternalSystemDAO.HIERARCHIES_COL_INFO, resultSet);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
	}

	public EntityList getGlobalFinanceHierarchies(String sql, List<String> companies, String ledger, String extSysDimType, String dimCode) throws SQLException {
		if (this._log.isDebugEnabled())
			this._log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String sql1 = sql.substring(6, sql.length() - 10);
		sql = "select " + companies.get(0) + " as CMPY," + sql1.replaceAll("\\?", companies.get(0));
		for (int i = 1; i < companies.size(); i++) {
			sql = sql + " union all " + "select " + companies.get(i) + " as CMPY," + sql1.replaceAll("\\?", companies.get(i));
			;
		}
		try {
			stmt = getConnection().prepareStatement(sql);
			resultSet = stmt.executeQuery();
			EntityListImpl localEntityListImpl = JdbcUtils.extractToEntityListImpl(ExternalSystemDAO.GLOBAL_HIERARCHIES_COL_INFO, resultSet);
			return localEntityListImpl;
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		} // throw localObject;
	}

	public EntityList getFinanceDimElementGroups(String sql, String company, String ledger, String extSysDimType, String dimCode, int parentType, String parent, String accType) throws SQLException {
		if (_log.isDebugEnabled())
			_log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = getConnection().prepareStatement(sql);
			resultSet = stmt.executeQuery();
			return JdbcUtils.extractToEntityListImpl(ExternalSystemDAO.GROUPS_COL_INFO, resultSet);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
	}

	public EntityList getGlobalFinanceDimElementGroups(String sql, List<String> companies, String ledger, String extSysDimType, String dimCode, int parentType, String parent, String accType) throws SQLException {
		if (this._log.isDebugEnabled())
			this._log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;

		String sql1 = sql;
		sql1 = sql1.replaceAll("select  distinct", "selectdistinct");
		sql = sql1.replaceAll("select ", "select " + companies.get(0) + " as CMPY, ").replaceAll("selectdistinct", "select distinct " + companies.get(0) + " as CMPY, ").replaceAll("\\?", companies.get(0));
		for (int i = 1; i < companies.size(); i++) {
			sql = sql + " \r\nunion all \r\n" + sql1.replaceAll("select ", "select " + companies.get(i) + " as CMPY, ").replaceAll("selectdistinct", "select distinct " + companies.get(i) + " as CMPY, ").replaceAll("\\?", companies.get(i));
			;
		}

		try {
			stmt = getConnection().prepareStatement(sql);
			resultSet = stmt.executeQuery();
			EntityListImpl localEntityListImpl = JdbcUtils.extractToEntityListImpl(ExternalSystemDAO.GLOBAL_GROUPS_COL_INFO, resultSet);
			return localEntityListImpl;
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		} // throw localObject;
	}

	public EntityList getFinanceHierarchyElems(String sql, String company, String ledger, String extSysDimType, String dimCode, String hierName, String hierType, String parent) throws SQLException {
		if (_log.isDebugEnabled())
			_log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		// brzydkie obejscie bledu z brakiem hierarchii
		sql = sql.replace("(oa_hierarchy.hlev = 1 and replace(pro_element(hilevs,2,2) ,':',' ') = ' ')", "1=1");
		int col = 1;
		try {
			stmt = getConnection().prepareStatement(sql);
			resultSet = stmt.executeQuery();
			return JdbcUtils.extractToEntityListImpl(ExternalSystemDAO.HIER_ELEMS_COL_INFO, resultSet);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
	}

	public EntityList getGlobalFinanceHierarchyElems(String sql, List<String> companies, String ledger, String extSysDimType, String dimCode, String hierName, String hierType, String parent) throws SQLException {
		if (this._log.isDebugEnabled())
			this._log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;

		String sql1 = sql.substring(0, sql.length() - 10);
		// brzydkie obejscie bledu z brakiem hierarchii
		sql1 = sql1.replace("(oa_hierarchy.hlev = 1 and replace(pro_element(hilevs,2,2) ,':',' ') = ' ')", "1=1");
		sql = sql1.replaceAll("select\r\n", "select " + companies.get(0) + " as CMPY, \r\n").replaceAll("\\?", companies.get(0));
		for (int i = 1; i < companies.size(); i++) {
			sql = sql + " \r\nunion all \r\n" + sql1.replaceAll("select\r\n", "select " + companies.get(i) + " as CMPY, \r\n").replaceAll("\\?", companies.get(i));
			;
		}
		sql = sql + "\r\n\r\n order by 1,2";

		int col = 1;
		try {
			stmt = getConnection().prepareStatement(sql);
			resultSet = stmt.executeQuery();
			EntityListImpl localEntityListImpl = JdbcUtils.extractToEntityListImpl(ExternalSystemDAO.GLOBAL_HIER_ELEMS_COL_INFO, resultSet);
			return localEntityListImpl;
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		} // throw localObject;
	}

	public void getUnfilteredDimension(String deSql, String heSql) throws SQLException {
		int CHUNK_SIZE = 500;

		if (deSql == null) {
			return;
		}
		if (_log.isDebugEnabled()) {
			_log.debug(deSql);
		}
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String sql = null;
		List rowList = new ArrayList();
		int col = 1;
		String tempVisId = "";
		try {
			sql = deSql;
			stmt = getConnection().prepareStatement(sql);
			stmt.setFetchSize(500);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				if (rowList.size() == 500) {
					getOwningDAO().insertOaDimElemBatch(rowList);
					rowList.clear();
				}
				String visId = resultSet.getString("VIS_ID");
				String descr = resultSet.getString("DESCR");
				String accountType = resultSet.getString("ACCOUNT_TYPE");
				String disabled = resultSet.getString("DISABLED").substring(0, 1);
				if (!tempVisId.equals(visId)) {
				    rowList.add(new Object[] { visId, descr, accountType, disabled });
				    tempVisId = visId;
				}
			}
			if (rowList.size() > 0) {
				getOwningDAO().insertOaDimElemBatch(rowList);
				rowList.clear();
			}
			
			tempVisId = "";

			if (heSql != null) {
				if (_log.isDebugEnabled())
					_log.debug(heSql);
				Map thisLevelKeys = new HashMap();
				Map prevLevelKeys = new HashMap();
				int currLevel = -1;
				sql = heSql;
				stmt = getConnection().prepareStatement(sql);
				stmt.setFetchSize(500);
				resultSet = stmt.executeQuery();
				while (resultSet.next()) {
					if (rowList.size() == 500) {
						getOwningDAO().insertOaHierElemBatch(rowList);
						rowList.clear();
					}
					String hierName = resultSet.getString("HIER");
					int level = resultSet.getInt("LEV");
					String visId = resultSet.getString("VIS_ID");
					String owningVisId = resultSet.getString("OWNING_VIS_ID").trim();
					String descr = resultSet.getString("DESCR");
					String isLeaf = resultSet.getString("IS_LEAF");

					if (level != currLevel) {
						prevLevelKeys = thisLevelKeys;
						thisLevelKeys = new HashMap();
						currLevel = level;
					}

					String[] owningKey = { hierName, owningVisId };
					if (prevLevelKeys.get(owningKey) != null) {
						owningVisId = (String) prevLevelKeys.get(owningKey);
					}

					String actualVisId = visId;
					if (!isLeaf.equalsIgnoreCase("Y")) {
						while (true) {
							String[] thisKey = { hierName, actualVisId };
							if (thisLevelKeys.get(thisKey) == null) {
								thisLevelKeys.put(new String[] { hierName, visId }, actualVisId);
								break;
							}
							actualVisId = "=" + actualVisId;
						}
					}
					if (!tempVisId.equals(actualVisId)) {
					    rowList.add(new Object[] { hierName, actualVisId, owningVisId, descr, isLeaf });
					    tempVisId = actualVisId;
					}
				}
				if (rowList.size() > 0)
					getOwningDAO().insertOaHierElemBatch(rowList);
			}
		} catch (SQLException e) {
			_log.debug(sql);
			throw e;
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
	}

    public int importPctrans(String sql, int company) throws SQLException {
        int added = 0;
        if (sql == null)
            return 0;
        if (this._log.isDebugEnabled())
            this._log.debug(sql);
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = getConnection().prepareStatement(sql);
            stmt.setFetchSize(1);
            stmt.setInt(1, company);
            resultSet = stmt.executeQuery();

            // last added row
            String lastCostCentre = "";
            String lastExpenseCode = "";
            String lastProject = "";
            int lastYearNo = 0;
            int lastPeriod = 0;
            BigDecimal emptyBigDecimal = new BigDecimal(0);

            while (resultSet.next()) {
                String costCentre = resultSet.getString("costcentre");
                String expenseCode = resultSet.getString("expensecode");
                String project = resultSet.getString("project");
                int yearNo = resultSet.getInt("yearno");
                int period = resultSet.getInt("period");
                BigDecimal baseVal = resultSet.getBigDecimal("baseval");
                BigDecimal qty = resultSet.getBigDecimal("qty");

                String lastKey = company+"|"+lastCostCentre+"|"+lastExpenseCode+"|"+lastProject+"|"+lastYearNo;
                String newKey = company+"|"+costCentre+"|"+expenseCode+"|"+project+"|"+yearNo;

                if (newKey.equals(lastKey)) { // the same year
                    if (lastPeriod < period -1) {
                        while (lastPeriod < period - 1) { // fill to actual period
                            lastPeriod++;
                            getOwningDAO().insertOaPctransElem(new Object[] { company, lastCostCentre, lastProject, lastExpenseCode, lastYearNo, lastPeriod, emptyBigDecimal, emptyBigDecimal });
                            added = added + 1;
                        }
                    }
                } else { // new year
                    if (lastPeriod > 0 && lastPeriod < 12) { // fill last year
                        while (lastPeriod < 12) { // fill to 12
                            lastPeriod++;
                            getOwningDAO().insertOaPctransElem(new Object[] { company, lastCostCentre, lastProject, lastExpenseCode, lastYearNo, lastPeriod, emptyBigDecimal, emptyBigDecimal });
                            added = added + 1;
                        }
                    }

                    if (period > 1) { // fill actual year
                        lastPeriod = 0;
                        while (lastPeriod < period - 1) { // fill to actual period
                            lastPeriod++;
                            getOwningDAO().insertOaPctransElem(new Object[] { company, costCentre, project, expenseCode, yearNo, lastPeriod, emptyBigDecimal, emptyBigDecimal });
                            added = added + 1;
                        }
                    }
                }

                getOwningDAO().insertOaPctransElem(new Object[] { company, costCentre, project, expenseCode, yearNo, period, baseVal, qty });
                added = added + 1;

                // update last added row
                lastCostCentre = costCentre;
                lastExpenseCode = expenseCode;
                lastProject = project;
                lastYearNo = yearNo;
                lastPeriod = period;
            }
            // fill after last record
            while (lastPeriod > 0 && lastPeriod < 12) { // fill to 12
                lastPeriod++;
                getOwningDAO().insertOaPctransElem(new Object[] { company, lastCostCentre, lastProject, lastExpenseCode, lastYearNo, lastPeriod, emptyBigDecimal, emptyBigDecimal });
                added = added + 1;
            }

        } catch (SQLException e) {
            _log.debug(sql);
            throw e;
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }
        return added;
    }

    public int importPctransBatch(String sql, int company) throws SQLException {
        int added = 0;
        if (sql == null)
            return 0;
        if (this._log.isDebugEnabled())
            this._log.debug(sql);
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        List rowList = new ArrayList();
        try {
            stmt = getConnection().prepareStatement(sql);
            stmt.setFetchSize(5000);
            stmt.setInt(1, company);
            resultSet = stmt.executeQuery();

            // last added row
            String lastCostCentre = "";
            String lastExpenseCode = "";
            String lastProject = "";
            int lastYearNo = 0;
            int lastPeriod = 0;
            BigDecimal emptyBigDecimal = new BigDecimal(0);

            while (resultSet.next()) {
                if (rowList.size() >= 5000) {
                    getOwningDAO().insertOaPctransElemBatch(rowList);
                    rowList.clear();
                }
                String costCentre = resultSet.getString("costcentre");
                String expenseCode = resultSet.getString("expensecode");
                String project = resultSet.getString("project");
                int yearNo = resultSet.getInt("yearno");
                int period = resultSet.getInt("period");
                BigDecimal baseVal = resultSet.getBigDecimal("baseval");
                BigDecimal qty = resultSet.getBigDecimal("qty");

                String lastKey = company + "|" + lastCostCentre + "|" + lastExpenseCode + "|" + lastProject + "|" + lastYearNo;
                String newKey = company + "|" + costCentre + "|" + expenseCode + "|" + project + "|" + yearNo;

                if (newKey.equals(lastKey)) { // the same year
                    if (lastPeriod < period - 1) {
                        while (lastPeriod < period - 1) { // fill to actual period
                            lastPeriod++;
                            rowList.add(new Object[] { company, lastCostCentre, lastProject, lastExpenseCode, lastYearNo, lastPeriod, emptyBigDecimal, emptyBigDecimal });
                            added = added + 1;
                        }
                    }
                } else { // new year
                    if (lastPeriod > 0 && lastPeriod < 12) { // fill last year
                        while (lastPeriod < 12) { // fill to 12
                            lastPeriod++;
                            rowList.add(new Object[] { company, lastCostCentre, lastProject, lastExpenseCode, lastYearNo, lastPeriod, emptyBigDecimal, emptyBigDecimal });
                            added = added + 1;
                        }
                    }

                    if (period > 1) { // fill actual year
                        lastPeriod = 0;
                        while (lastPeriod < period - 1) { // fill to actual period
                            lastPeriod++;
                            rowList.add(new Object[] { company, costCentre, project, expenseCode, yearNo, lastPeriod, emptyBigDecimal, emptyBigDecimal });
                            added = added + 1;
                        }
                    }
                }

                rowList.add(new Object[] { company, costCentre, project, expenseCode, yearNo, period, baseVal, qty });
                added = added + 1;

                // update last added row
                lastCostCentre = costCentre;
                lastExpenseCode = expenseCode;
                lastProject = project;
                lastYearNo = yearNo;
                lastPeriod = period;
            }
            // fill after last record
            while (lastPeriod > 0 && lastPeriod < 12) { // fill to 12
                lastPeriod++;
                rowList.add(new Object[] { company, lastCostCentre, lastProject, lastExpenseCode, lastYearNo, lastPeriod, emptyBigDecimal, emptyBigDecimal });
                added = added + 1;
            }
            if (rowList.size() > 0) {
                getOwningDAO().insertOaPctransElemBatch(rowList);
                rowList.clear();
            }

        } catch (SQLException e) {
            _log.debug(sql);
            throw e;
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }
        return added;
    }

	public int getNumPeriodColumns(String sql) throws SQLException {
		if (_log.isDebugEnabled())
			_log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		int col = 1;
		try {
			stmt = getConnection().prepareStatement(sql);
			resultSet = stmt.executeQuery();
			resultSet.next();
			return resultSet.getInt(1);
		} catch (SQLException e) {
			_log.debug(sql);
			throw e;
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
	}

	public String validateMappedDimElement(String sql) throws SQLException {
		if (sql == null) {
			return null;
		}
		if (_log.isDebugEnabled())
			_log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		int col = 1;
		try {
			stmt = getConnection().prepareStatement(sql);
			resultSet = stmt.executeQuery();
			String str;
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
			return null;
		} catch (SQLException e) {
			_log.debug(sql);
			throw e;
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
	}

	public String getValues(int taskId, int financeCubeId, String sql) throws SQLException {
		if (_log.isDebugEnabled())
			_log.debug(sql);
		int CHUNK_SIZE = 500;
		int numRows = 0;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		List rowList = new ArrayList();
		try {
			stmt = getConnection().prepareStatement(sql);
			stmt.setFetchSize(500);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				if (rowList.size() == 500) {
					getOwningDAO().insertOaValueBatch(taskId, financeCubeId, rowList);
					rowList.clear();
				}
				int numCols = resultSet.getMetaData().getColumnCount();
				Object[] cols = new Object[numCols];
				for (int i = 0; i < numCols; i++) {
					if (i == numCols - 1) {
						BigDecimal val = resultSet.getBigDecimal(i + 1);
						cols[i] = val;
					} else {
						cols[i] = resultSet.getObject(i + 1);
					}
				}
				rowList.add(cols);
				numRows++;
			}
			if (numRows > 0)
				getOwningDAO().insertOaValueBatch(taskId, financeCubeId, rowList);
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
		return "unfilteredRows=" + numRows;
	}

	public void getFinanceTransactions(FinanceSystemCellDataImpl cellData, String sql) throws SQLException {
		if (_log.isDebugEnabled())
			_log.debug(sql);
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		EntityListImpl results = null;
		Integer totalColumnNum = null;
		try {
			stmt = getConnection().prepareStatement(sql);
			resultSet = stmt.executeQuery();
			ResultSetMetaData meta = resultSet.getMetaData();
			String[] columnNames = new String[meta.getColumnCount()];
			for (int i = 0; i < meta.getColumnCount(); i++) {
				String columnName = meta.getColumnName(i + 1);
				int columnType = meta.getColumnType(i + 1);
				if (columnName.startsWith("!")) {
					if (totalColumnNum == null) {
						totalColumnNum = Integer.valueOf(i);
						cellData.setTotalName(columnName.substring(1));
					}
				}

				if ((columnName.startsWith("*")) || (columnName.startsWith("!")))
					columnName = columnName.substring(1);
				else
					columnType = 12;
				columnNames[i] = columnName;

				cellData.addColumnNameAndType(columnName, columnType);
			}

			while (resultSet.next()) {
				cellData.newRow();
				int col = 1;
				List l = new ArrayList();
				for (int i = 0; i < columnNames.length; i++) {
					if (((meta.getColumnType(col) == 2) || (meta.getColumnType(col) == 3)) && ((meta.getColumnName(i + 1).startsWith("*")) || (meta.getColumnName(i + 1).startsWith("!")))) {
						BigDecimal columnValue = resultSet.getBigDecimal(col++);

						if (columnValue.scale() > cellData.getColumnMaxScale(i)) {
							cellData.setColumnMaxScale(i, columnValue.scale());
						}
						if ((totalColumnNum != null) && (i == totalColumnNum.intValue())) {
							cellData.addToTotal(columnValue);
						}

						cellData.addColumnValue(columnValue);
					} else {
						cellData.addColumnValue(resultSet.getObject(col++));
					}
				}
				cellData.storeRow();
			}
		} catch (SQLException e) {
			_log.debug(sql);
			throw e;
		} finally {
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
	}

	public int runCOASql1(String sql1) throws SQLException {
		if (this._log.isDebugEnabled()) {
			this._log.debug(sql1);
		}

		PreparedStatement stmt = null;
		ResultSet resultSet = null;

		byte var6;
		try {
			stmt = this.getConnection().prepareStatement(sql1);
			resultSet = stmt.executeQuery();
			if (!resultSet.next()) {
				byte e1 = 0;
				return e1;
			}

			boolean e = resultSet.getInt("IS_VALID_COMBINATION") == 1;
			boolean additionalSecurity = resultSet.getInt("HAS_ADDITIONAL_SECURITY") == 1;
			byte var13;
			if (e && !additionalSecurity) {
				var13 = 1;
				return var13;
			}

			if (!additionalSecurity) {
				var13 = 0;
				return var13;
			}

			var6 = -1;
		} catch (SQLException var10) {
			this._log.debug(sql1);
			throw var10;
		} finally {
			this.closeResultSet(resultSet);
			this.closeStatement(stmt);
			this.closeConnection();
		}

		return var6;
	}

	public String runCOASql2(String sql2) throws SQLException {
		if (_log.isDebugEnabled()) {
			_log.debug(sql2);
		}
		PreparedStatement stmt = null;
		ResultSet resultSet = null;

		StringBuffer rangeSpecList = new StringBuffer();
		try {
			stmt = getConnection().prepareStatement(sql2);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				String rangeSpec = resultSet.getString("RANGE_SPEC");
				if (rangeSpecList.length() > 0)
					rangeSpecList.append(',');
				rangeSpecList.append(rangeSpec);
			}
		} catch (SQLException e) {
			_log.debug(sql2);
			throw e;
		} finally {
			_log.debug(sql2);
			closeResultSet(resultSet);
			closeStatement(stmt);
			closeConnection();
		}
		return rangeSpecList.toString();
	}

	public int runCOASql3(String sql3) throws SQLException {
		if (this._log.isDebugEnabled()) {
			this._log.debug(sql3);
		}

		PreparedStatement stmt = null;
		ResultSet resultSet = null;

		byte e;
		try {
			stmt = this.getConnection().prepareStatement(sql3);
			resultSet = stmt.executeQuery();
			if (resultSet.next()) {
				int e1 = resultSet.getInt("IS_VALID");
				return e1;
			}

			e = 0;
		} catch (SQLException var8) {
			this._log.debug(sql3);
			throw var8;
		} finally {
			this.closeResultSet(resultSet);
			this.closeStatement(stmt);
			this.closeConnection();
		}

		return e;
	}

	public String getEntityName() {
		return "OaAccessorDAO";
	}

	public int getDBType() {
		return getOwningDAO().getDBType();
	}

	private ExternalSystemDAO getOwningDAO() {
		return mOwningDAO;
	}
}