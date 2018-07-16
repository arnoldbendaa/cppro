package com.cedar.cp.ejb.impl.dataentry;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.dto.model.SecurityAccessExpressionLexer;
import com.cedar.cp.ejb.impl.base.SqlExecutor;
import com.cedar.cp.ejb.impl.dataentry.ContextInputFactory$MyColumn;
import com.cedar.cp.ejb.impl.dataentry.ContextInputFactory$MyFormInputControls;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.SqlBuilder;
import com.cedar.cp.util.SqlResultSet;
import com.cedar.cp.util.SqlUtils;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.performance.PerformanceDatumImpl;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.DataTypeColumnValue;
import com.cedar.cp.util.xmlform.FinanceCubeInput;
import com.cedar.cp.util.xmlform.FormContext;
import com.cedar.cp.util.xmlform.LookupDateSpec;
import com.cedar.cp.util.xmlform.LookupInput;
import com.cedar.cp.util.xmlform.RowInput;
import com.cedar.cp.util.xmlform.StructureColumnValue;
import com.cedar.cp.util.xmlform.Subset;
import com.cedar.cp.util.xmlform.inputs.FormInputModel;
import com.cedar.cp.util.xmlform.inputs.FormResultSetInputModel;
import com.cedar.cp.util.xmlform.inputs.LookupData;
import com.cedar.cp.util.xmlform.inputs.LookupTarget;
import com.cedar.cp.util.xmlform.inputs.RowInputModel;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class ContextInputFactory {

	private FormContext mFormContext;
	private Log mLog = new Log(this.getClass());
	private static final Map sFinanceCubeCache = new HashMap();
	private ContextInputFactory$MyFormInputControls mMyControls;

	public ContextInputFactory(FormContext cnx) {
		this.mFormContext = cnx;
	}

	public FormInputModel getFormInputModel(PerformanceDatumImpl perf, RowInput rowInput) {
		return new RowInputModel(rowInput);
	}

	public Map<String, Object> getLookupInputs(PerformanceDatumImpl perf, List inputs, Map contextVariables) {
		Timer t = new Timer(this.mLog);
		int lookupCount = 0;
		int sqlCount = 0;
		int alreadyCachedCount = 0;
		HashMap returnMap = new HashMap();
		HashMap tsMap = new HashMap();
		Iterator ss = inputs.iterator();

		while (ss.hasNext()) {
			Object i$ = ss.next();
			if (i$ instanceof LookupInput) {
				LookupInput keyStr = (LookupInput) i$;
				++lookupCount;
				StringBuilder list = new StringBuilder();
				list.append(keyStr.getLookupName()).append(",");
				list.append(keyStr.getPartitionColumn()).append(",");
				list.append(keyStr.getKey()).append(",");
				list.append(keyStr.getScenario() == null ? "" : keyStr.getScenario()).append(",");
				list.append(keyStr.getDate() == null ? "" : keyStr.getDate()).append(",");
				list.append(keyStr.getEndDate() == null ? "" : keyStr.getEndDate()).append(",");
				Iterator firstLu = keyStr.getSubsets().iterator();

				while (firstLu.hasNext()) {
					Subset globalLookupTableKey = (Subset) firstLu.next();
					list.append(globalLookupTableKey.getColumn()).append(",");
					list.append(globalLookupTableKey.getValue()).append(",");
				}

				Object var28 = (List) tsMap.get(list.toString());
				if (var28 == null) {
					var28 = new ArrayList();
				}

				((List) var28).add(keyStr);
				tsMap.put(list.toString(), var28);
			}
		}

		TreeSet var23 = new TreeSet();
		var23.addAll(tsMap.keySet());
		Iterator var22 = var23.iterator();

		while (var22.hasNext()) {
			String var24 = (String) var22.next();
			List var25 = (List) tsMap.get(var24);
			LookupInput var27 = (LookupInput) var25.get(0);
			String var26 = null;
			LookupData lud = null;
			boolean dataAlreadyCached = false;
			if (var27.getDate() == null && var27.getSubsets().isEmpty()) {
				var26 = var27.getLookupTableName() + "\t" + var27.getKey() + (var27.getPartitionColumn() == null ? "" : "\t" + var27.getPartitionColumn());
				lud = (LookupData) contextVariables.get(var26);
				dataAlreadyCached = lud != null;
				if (dataAlreadyCached) {
					++alreadyCachedCount;
				}
			}

			if (!dataAlreadyCached && contextVariables.get(var27.getId()) == null) {
				SqlResultSet i$1 = this.getLookupData(var27, contextVariables);
				++sqlCount;
				StringBuilder lui = new StringBuilder();
				lui.append(var27.getLookupName()).append(" rows=").append(String.valueOf(i$1.getNumRows()));
				if (var25.size() > 1) {
					lui.append(" lookups=").append(String.valueOf(var25.size()));
				}

				if (var27.getScenario() != null) {
					lui.append(" s=").append(var27.getScenario());
				}

				Iterator lut = var27.getSubsets().iterator();

				while (lut.hasNext()) {
					Subset s = (Subset) lut.next();
					lui.append(" ").append(s.getColumn()).append("=").append(s.getValue()).append("=").append(contextVariables.get(s.getValue()));
				}

				t.logInfo("getLookupInputs", lui.toString());
				lud = new LookupData(var27.getKey(), var27.getPartitionColumn(), i$1);
			}

			LookupInput var29;
			LookupTarget var31;
			for (Iterator var30 = var25.iterator(); var30.hasNext(); returnMap.put(var29.getId(), var31)) {
				var29 = (LookupInput) var30.next();
				if (var26 == null) {
					var31 = new LookupTarget(var29, lud);
				} else {
					if (!dataAlreadyCached) {
						returnMap.put(var26, lud);
					}

					var31 = new LookupTarget(var29, lud);
				}
			}
		}

		if (sqlCount > 1) {
			t.logInfo("getLookupInputs", "lookups=" + lookupCount + ", sqlCount=" + sqlCount + ", alreadyCached=" + alreadyCachedCount);
		}

		return returnMap;
	}

	private SqlResultSet getLookupData(LookupInput lui, Map contextVariables) {
		Timer t = new Timer(this.mLog);
		boolean timeLvl = false;
		boolean timeRange = false;
		SqlBuilder sqlb = new SqlBuilder(new String[] { "select  TIME_LVL, TIME_RANGE", "from    UDEF_LOOKUP", "where   VIS_ID = <visId>" });
		SqlExecutor sqle = new SqlExecutor("getLookupData", this.mFormContext.getSqlConnection(), sqlb, this.mLog);
		sqle.setLogSql(false);
		sqle.addBindVariable("<visId>", SqlUtils.generateTableName(lui.getLookupName(), 27));

		int timeLvl1;
		try {
			ResultSet subsetCriteria = sqle.getResultSet();
			subsetCriteria.next();
			timeLvl1 = subsetCriteria.getInt("TIME_LVL");
			timeRange = subsetCriteria.getString("TIME_RANGE").equalsIgnoreCase("Y");
		} catch (SQLException var16) {
			throw new RuntimeException("UDEF_LOOKUP visId=" + SqlUtils.generateTableName(lui.getLookupName(), 27), var16);
		} finally {
			sqle.close();
			this.mFormContext.closeSqlConnection();
			t.logDebug("getLookupData", "udefLookup=" + lui.getLookupName());
		}

		sqlb = new SqlBuilder(new String[] { "select  lu.*${derivedEndDate} from ${tableName} lu", "${criteria}", "order   by ${partitionColumn}${keyColumn}${dateColumn}${endDateColumn}" });
		SqlBuilder subsetCriteria1 = new SqlBuilder();
		if (lui.getScenario() != null || lui.getDate() != null || !lui.getSubsets().isEmpty()) {
			subsetCriteria1.addLine("where   1 = 1");
		}

		if (lui.getScenario() != null) {
			subsetCriteria1.addLine("and     X_SCENARIO = <scenario>");
		}

		if (timeLvl1 != 0 && lui.getDate() != null) {
			subsetCriteria1.addLine("and     X_TA_DATE between <startDate> and <endDate>");
			if (timeRange) {
				subsetCriteria1.addLine("and     X_TA_END_DATE between <startDate> and <endDate>");
			}
		}

		String rs;
		if (timeLvl1 == 0) {
			rs = "to_date(\'01-01-0001\',\'DD-MM-YYYY\') X_TA_DATE,to_date(\'31-12-9999\',\'DD-MM-YYYY\') X_TA_END_DATE";
			sqlb.substitute(new String[] { "${derivedEndDate}", "," + rs });
		} else if (!timeRange) {
			rs = null;
			if (timeLvl1 == 1) {
				rs = "to_char(add_months(X_TA_DATE,12) - 1,\'YYYY-MM-DD\')||\' 23:59:59\'";
			} else if (timeLvl1 == 2) {
				rs = "to_char(add_months(to_date(to_char(X_TA_DATE,\'YYYY-MM\')||\'-01\',\'YYYY-MM-DD\'),1) - 1,\'YYYY-MM-DD\')||\' 23:59:59\'";
			} else {
				rs = "to_char(X_TA_DATE,\'YYYY-MM-DD\')||\' 23:59:59\'";
			}

			sqlb.substitute(new String[] { "${derivedEndDate}", ",to_date(" + rs + ",\'YYYY-MM-DD HH24:MI:SS\') as X_TA_END_DATE" });
		}

		Iterator rs1 = lui.getSubsets().iterator();

		Subset s;
		String colName;
		while (rs1.hasNext()) {
			s = (Subset) rs1.next();
			colName = s.getColumn().toUpperCase();
			subsetCriteria1.addLine("and     X_" + colName + " = <" + colName + ">");
		}

		sqlb.substitute(new String[] { "${tableName}", lui.getLookupTableName(), "${partitionColumn}", lui.getPartitionColumn() != null ? "X_" + lui.getKey() + "," : null, "${keyColumn}", "CP_USER_SEQ", "${dateColumn}", timeLvl1 > 0 ? ",X_TA_DATE" : null, "${endDateColumn}", timeRange ? ",X_TA_END_DATE" : null, "${derivedEndDate}", null });
		sqlb.substituteLines("${criteria}", subsetCriteria1);
		sqle = new SqlExecutor("getLookupInputs(" + lui.getLookupName() + ")", this.mFormContext.getSqlConnection(), sqlb, this.mLog);
		sqle.setLogSql(true);
		sqle.setFetchSize(Integer.valueOf(512));
		if (lui.getScenario() != null) {
			sqle.addBindVariable("<scenario>", lui.getScenario());
		}

		if (timeLvl1 != 0 && lui.getDate() != null) {
			Integer rs2 = (Integer) contextVariables.get(WorkbookProperties.MODEL_ID.toString());
			CalendarInfo s1 = (CalendarInfo) contextVariables.get(WorkbookProperties.CALENDAR_INFO.toString() + rs2);
			Integer colName1 = (Integer) contextVariables.get(WorkbookProperties.BUDGET_CYCLE_PERIOD_ID.toString());
			sqle.addBindVariable("<startDate>", (new LookupDateSpec(lui.getDate())).getStartDate(colName1, s1));
			if (lui.getEndDate() != null) {
				sqle.addBindVariable("<endDate>", (new LookupDateSpec(lui.getEndDate())).getEndDate(colName1, s1));
			} else {
				sqle.addBindVariable("<endDate>", (new LookupDateSpec(lui.getDate())).getEndDate(colName1, s1));
			}
		}

		rs1 = lui.getSubsets().iterator();

		while (rs1.hasNext()) {
			s = (Subset) rs1.next();
			colName = s.getColumn().toUpperCase();
			String colValue = s.getValue();
			Object contextVariable = contextVariables.get(colValue);
			sqle.addBindVariable("<" + colName + ">", contextVariable != null ? contextVariable : colValue);
		}

		SqlResultSet rs3 = sqle.getSqlResultSet();
		sqle.close();
		this.mFormContext.closeSqlConnection();
		return rs3;
	}

	private int lookupHierarchyFromVisId(int modelId, int dimSeq, String visId, int structureElementId) throws SQLException {
		Timer t = new Timer(this.mLog);
		ResultSet rs = null;
		PreparedStatement stmt = null;

		int result;
		try {
			stmt = this.mFormContext.getSqlConnection().prepareStatement("select  HIERARCHY_ID\nfrom    HIERARCHY_ELEMENT\nwhere   HIERARCHY_ELEMENT_ID = ?");
			stmt.setInt(1, structureElementId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = (new Integer(rs.getInt(1))).intValue();
			} else {
				rs.close();
				stmt = this.mFormContext.getSqlConnection().prepareStatement("select h.hierarchy_id from model_dimension_rel mr, hierarchy h where mr.model_id = ?   and h.dimension_id = mr.dimension_id   and mr.dimension_seq_num = ?   and h.vis_id = ?");
				stmt.setInt(1, modelId);
				stmt.setInt(2, dimSeq);
				stmt.setString(3, visId);
				rs = stmt.executeQuery();
				if (!rs.next()) {
					throw new IllegalStateException("No hierarchy visId for " + visId);
				}

				result = (new Integer(rs.getInt(1))).intValue();
			}
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException var15) {
					;
				}
			}

			this.mFormContext.closeSqlConnection();
			t.logDebug("lookupHierarchyFromVisId");
		}

		return result;
	}

	private int lookupFinanceCubeFromVisId(String visId) throws SQLException {
		Integer result = (Integer) sFinanceCubeCache.get(visId);
		if (result == null) {
			ResultSet rs = null;
			PreparedStatement stmt = null;

			try {
				stmt = this.mFormContext.getSqlConnection().prepareStatement("select finance_cube_id from finance_cube where vis_id = ?");
				stmt.setString(1, visId);
				rs = stmt.executeQuery();
				if (rs.next()) {
					result = new Integer(rs.getInt(1));
				} else {
					this.mLog.warn("lookupFinanceCubeFromVisId", "FinanceCube visId \'" + visId + "\' not found");
				}

				if (rs.next()) {
					throw new IllegalStateException("Finance cube has non-unique vis id " + visId);
				}

				if (result != null) {
					sFinanceCubeCache.put(visId, result);
				}
			} finally {
				if (rs != null) {
					rs.close();
				}

				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException var11) {
						;
					}
				}

				this.mFormContext.closeSqlConnection();
			}
		}

		return result != null ? result.intValue() : 0;
	}

	private int lookupFinanceCubeDimensionCount(int cubeId) throws SQLException {
		int result = 0;
		ResultSet rs = null;
		PreparedStatement stmt = null;

		try {
			stmt = this.mFormContext.getSqlConnection().prepareStatement("select count(*)   from finance_cube fc,        model_dimension_rel mr  where fc.finance_cube_id = ?    and fc.model_id = mr.model_id");
			stmt.setInt(1, cubeId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			} else {
				this.mLog.warn("lookupFinanceCubeDimensionCount", "FinanceCube \'" + cubeId + "\' was not found");
			}
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException var11) {
					;
				}
			}

			this.mFormContext.closeSqlConnection();
		}

		return result;
	}

	private String getRetrievalMethodProperty() {
		String result = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;

		try {
			stmt = this.mFormContext.getSqlConnection().prepareStatement("select VALUE from SYSTEM_PROPERTY where PRPRTY = \'SYS: Data Entry retrieval version\'");
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getString(1);
			} else {
				this.mLog.warn("getNewRetrievalMethodProperty", "property \'SYS: Data Entry retrieval version\' not found");
			}
		} catch (SQLException var12) {
			throw new RuntimeException(var12);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException var11) {
				;
			}

			this.mFormContext.closeSqlConnection();
		}

		return result;
	}

	private DataType lookupDataType(String dataTypeVisId) {
		DataType row = (DataType) this.mMyControls.getDataTypes().get(dataTypeVisId);
		if (row == null) {
			throw new IllegalStateException("can\'t find dataType " + dataTypeVisId);
		} else {
			return row;
		}
	}

	public String getRtFormatCol(DataType dt) {
		return dt.isFinanceValue() ? "1" : (dt.isMeasureNumeric() ? "1" : "0");
	}

	public String getRtDecodeCol(DataType dt) {
		return dt.isFinanceValue() ? "F" : (dt.isMeasureNumeric() ? "N" : (!dt.isMeasureString() && !dt.isMeasureBoolean() ? (!dt.isMeasureDate() && !dt.isMeasureDateTime() && !dt.isMeasureTime() ? null : "D") : "S"));
	}

	public String getDecodeString(DataType dt) {
		return dt.isFinanceValue() ? "decode(coalesce(P,N),null,null,(nvl(N,0)+nvl(P,0))/${scale})" : (dt.isMeasureNumeric() ? "decode(coalesce(P,N),null,null,(nvl(N,0)+nvl(P,0))/${scale})" : (dt.isMeasureString() ? "S" : (dt.isMeasureBoolean() ? "S" : (!dt.isMeasureDate() && !dt.isMeasureDateTime() && !dt.isMeasureTime() ? null : "D"))));
	}

	public int getScale(DataType dt) {
		return dt.isFinanceValue() ? 10000 : (dt.getMeasureScale() != null ? (int) Math.pow(10.0D, (double) dt.getMeasureScale().intValue()) : 0);
	}

	public FormInputModel getFinanceFormDataRows3(int userId, FinanceCubeInput config, int budgetCycleId, String contextDataType, int xAxisIndex, int[] structureElementIds, int childDepth, boolean secondaryStructure, CalendarInfo calInfo, Map<String, DataType> dataTypes, Map<Integer, EntityList> securityAccessDetails) throws Exception {
		return this.getFormInputModel(userId, config, budgetCycleId, contextDataType, xAxisIndex, structureElementIds, childDepth, secondaryStructure, calInfo, dataTypes, securityAccessDetails);
	}

	private void getDimInfo() throws SQLException {
		this.mLog.debug("getDimInfo", "");
		this.mMyControls.setFinanceCubeId(this.mMyControls.getFinanceCubeInput().getCubeId());
		if (this.mMyControls.getFinanceCubeId() == 0) {
			this.mMyControls.setFinanceCubeId(this.lookupFinanceCubeFromVisId(this.mMyControls.getFinanceCubeInput().getCubeVisId()));
		}

		ContextInputFactory$MyFormInputControls.accessMethod002(this.mMyControls, this.lookupFinanceCubeDimensionCount(this.mMyControls.getFinanceCubeId()));
		List cols = this.mMyControls.getFinanceCubeInput().getColumnValues();
		this.mMyControls.mTreeStructureIds = new int[this.mMyControls.getNumDims()];

		int traceCols;
		Object traceIndex;
		for (traceCols = 0; traceCols < cols.size(); ++traceCols) {
			traceIndex = cols.get(traceCols);
			if (traceIndex instanceof StructureColumnValue) {
				StructureColumnValue i = (StructureColumnValue) traceIndex;
				this.mMyControls.mTreeStructureIds[i.getDim()] = this.lookupHierarchyFromVisId(this.mMyControls.getFinanceCubeInput().getModelId(), i.getDim(), i.getHier(), ContextInputFactory$MyFormInputControls.accessMethod100(this.mMyControls)[i.getDim()]);
			}
		}

		if (this.mMyControls.getStructureElementId(this.mMyControls.getXAxisIndex()) == 0) {
			this.mMyControls.mChildDepth = 1;
		}

		if (this.mLog.isDebugEnabled()) {
			this.mLog.debug("financeCubeId=" + this.mMyControls.getFinanceCubeId() + " visId=\'" + this.mMyControls.getFinanceCubeInput().getCubeVisId() + "\'" + " dims=" + this.mMyControls.getNumDims() + " xAxisIndex=" + this.mMyControls.getXAxisIndex());
		}

		for (traceCols = 0; traceCols < this.mMyControls.getNumDims() - 1; ++traceCols) {
			if (this.mLog.isDebugEnabled()) {
				this.mLog.debug("dim" + String.valueOf(traceCols) + (traceCols == this.mMyControls.getXAxisIndex() ? "*" : " ") + (this.mMyControls.getStructureId(traceCols) > 0 ? " structureId=" + this.mMyControls.getStructureId(traceCols) : "") + " elemId=" + this.mMyControls.getStructureElementId(traceCols));
			}
		}

		if (this.mLog.isDebugEnabled()) {
			this.mLog.debug("dim" + (this.mMyControls.getNumDims() - 1) + "  structureId=" + this.mMyControls.getCalendarInfo().getCalendarId() + " elemId=" + this.mMyControls.getStructureElementId(this.mMyControls.getNumDims() - 1));
		}

		this.mMyControls.mMyColumns = new ArrayList();
		Iterator var7 = cols.iterator();

		while (var7.hasNext()) {
			traceIndex = var7.next();
			if (traceIndex instanceof DataTypeColumnValue) {
				ContextInputFactory$MyColumn var10 = new ContextInputFactory$MyColumn(this, (DataTypeColumnValue) traceIndex);
				this.mMyControls.mMyColumns.add(var10);
			}
		}

		this.mMyControls.mCalendarElemNode = this.mMyControls.getCalendarInfo().getById(this.mMyControls.getStructureElementId(this.mMyControls.getNumDims() - 1));
		StringBuffer var9 = new StringBuffer();
		int var8 = 0;

		for (int var11 = 0; var11 < this.mMyControls.mMyColumns.size(); ++var11) {
			ContextInputFactory$MyColumn column = (ContextInputFactory$MyColumn) this.mMyControls.mMyColumns.get(var11);
			if (column.isFixedPeriod()) {
				CalendarElementNode calElem = this.lookupPeriodVisId(column.getPeriodVisId());
				if (calElem != null && (column.getYearOffset() != 0 || column.getPeriodOffset() != 0)) {
					calElem = this.mMyControls.getCalendarInfo().getElementOffsetByYearAndPeriod(calElem.getStructureElementId(), column.getYearOffset(), column.getPeriodOffset());
				}

				column.setCalendarElement(calElem);
			}

			if (var8 == 3) {
				if (this.mLog.isDebugEnabled()) {
					this.mLog.debug(var9.toString());
				}

				var9 = new StringBuffer();
				var8 = 0;
			}

			++var8;
			var9.append("{" + String.valueOf(var11 + 1) + (column.isFixedPeriod() ? ",pvis=" + column.getPeriodVisId() : "") + (column.getYearOffset() != 0 ? ",y=" + column.getYearOffset() : "") + (column.getPeriodOffset() != 0 ? ",p=" + column.getPeriodOffset() : "") + (column.isYtd() ? ",ytd=y" : "")
					+ (column.isFixedDataType() ? ",dt=" + column.getDataType() : "") + "}");
		}

		if (this.mLog.isDebugEnabled()) {
			this.mLog.debug(var9.toString());
		}

		this.getFixedDimInfo();
		this.mMyControls.mIsDftNeeded = this.isDftNeeded();
	}

	private void getFixedDimInfo() throws SQLException {
		Timer t = new Timer(mLog);

		SqlBuilder sqlb = new SqlBuilder(new String[] { "with params as", "(", "select  MODEL_ID", "        ,BUDGET_HIERARCHY_ID", "        ,<userId> as USER_ID", "        ,<cycleId> as BUDGET_CYCLE_ID", "        ,<contextDims> as CONTEXT_DIMS", "        ,<contextDim.ra> as CONTEXT_DIM0", "from    MODEL", "where   MODEL_ID = <modelId>", ")",
				"/*--- get budget state for context cost centre */", ",getRABudgetState as", "(", "${getRABudgetState}", ")", ",getContextElemInfo as", "(", "${getContextElemInfo}", ")", "select   decode(CONTEXT_DIMS,CONTEXT_LEAF_ELEM_COUNT,0,1) as CONTEXT_NON_LEAF", "        ,nvl(CONTEXT_DISABLED,0) as CONTEXT_DISABLED",
				"        ,nvl(CONTEXT_NON_PLANNABLE,0) as CONTEXT_NON_PLANNABLE", "        ,RA_BUDGET_STATE as CONTEXT_RA_BUDGET_STATE", "from    params, getContextElemInfo", "left    join getRaBudgetState on (1=1)" });

		SqlExecutor sqle = new SqlExecutor("getFixedDimInfo", mFormContext.getSqlConnection(), sqlb, mLog);

		boolean checkPossible = false;
		List contextDims = new ArrayList();
		boolean ccAtSheetLevel = false;
		for (int dimIndex = 0; dimIndex < mMyControls.getNumDims() - 1; dimIndex++) {
			if (!mMyControls.isDimAnAxis(dimIndex)) {
				contextDims.add(new StringBuilder().append("${separator}<contextDim.").append(dimIndex).append(">").toString());
				sqle.addBindVariable(new StringBuilder().append("<contextDim.").append(String.valueOf(dimIndex)).append(">").toString(), Integer.valueOf(mMyControls.getStructureElementId(dimIndex)));

				if (dimIndex == 0) {
					ccAtSheetLevel = true;
				}

				checkPossible = true;
			}
		}
		if (checkPossible) {
			sqlb.substituteLines("${getContextElemInfo}", new String[] { "select  count(*)                            as CONTEXT_LEAF_ELEM_COUNT", "        ,max(decode(DISABLED,'Y',1,0))      as CONTEXT_DISABLED", "        ,max(decode(NOT_PLANNABLE,'Y',1,0)) as CONTEXT_NON_PLANNABLE", "from    DIMENSION_ELEMENT", "where   DIMENSION_ELEMENT_ID",
					"        in  (", "            ${contextDims}", "            )" });

			sqlb.substituteLines("${contextDims}", ",", contextDims);
		} else {
			sqlb.substituteLines("${getContextElemInfo}", new String[] { "select   0 as CONTEXT_LEAF_ELEM_COUNT", "        ,0 as CONTEXT_DISABLED", "        ,0 as CONTEXT_NON_PLANNABLE", "from    params" });
		}

		if (ccAtSheetLevel) {
			sqlb.substituteLines("${getRABudgetState}", new String[] { "select  nvl(STATE,2) as RA_BUDGET_STATE", "from    params p", "left", "join    BUDGET_STATE bs", "        on (    bs.BUDGET_CYCLE_ID = p.BUDGET_CYCLE_ID", "            and bs.STRUCTURE_ELEMENT_ID = p.CONTEXT_DIM0)" });

			sqle.addBindVariable("<cycleId>", Integer.valueOf(mMyControls.getBudgetCycleId()));
		} else {
			sqlb.substituteLines("${getRABudgetState}", new String[] { "select  nullif(1,1) as RA_BUDGET_STATE", "from    dual" });
		}

		sqle.addBindVariable("<contextDim.ra>", Integer.valueOf(mMyControls.getStructureElementId(0)));
		sqle.addBindVariable("<cycleId>", Integer.valueOf(mMyControls.getBudgetCycleId()));
		sqle.addBindVariable("<userId>", Integer.valueOf(mMyControls.getUserId()));
		sqle.addBindVariable("<modelId>", Integer.valueOf(mMyControls.getFinanceCubeInput().getModelId()));
		sqle.addBindVariable("<contextDims>", Integer.valueOf(contextDims.size()));

		ResultSet rs = sqle.getResultSet();
		rs.next();
		mMyControls.mIsContextNonLeaf = (rs.getInt("CONTEXT_NON_LEAF") == 1);
		mMyControls.mIsContextDisabled = (rs.getInt("CONTEXT_DISABLED") == 1);
		mMyControls.mIsContextNonPlannable = (rs.getInt("CONTEXT_NON_PLANNABLE") == 1);
		mMyControls.mContextRABudgetState = Integer.valueOf(rs.getInt("CONTEXT_RA_BUDGET_STATE"));
		if (rs.wasNull())
			mMyControls.mContextRABudgetState = null;
		sqle.close();
		t.logInfo("getFixedDimInfo", new StringBuilder().append("nonLeaf=").append(mMyControls.mIsContextNonLeaf).append(",disabled=").append(mMyControls.mIsContextDisabled).append(",nonPlannable=").append(mMyControls.mIsContextNonPlannable).append(",raBudgetState=").append(mMyControls.mContextRABudgetState).toString());
	}

	private boolean isDftNeeded() throws SQLException {
		if (this.mMyControls.isRuntimeDataTypeUsed()) {
			DataType i$ = this.lookupDataType(this.mMyControls.getRuntimeDataType());
			if (i$.isMeasure()) {
				return true;
			}
		}

		Iterator i$1 = this.mMyControls.getColumns().iterator();

		while (i$1.hasNext()) {
			ContextInputFactory$MyColumn col = (ContextInputFactory$MyColumn) i$1.next();
			if (col.isFixedDataType()) {
				DataType dt = this.lookupDataType(col.getDataType());
				if (dt.isMeasure()) {
					return true;
				}
			}
		}

		if (this.mMyControls.isGetAllMode() && this.mMyControls.getAxisDimIndexes().length == this.mMyControls.getNumDims() - 1) {
			return true;
		} else if (this.mMyControls.mIsContextNonLeaf) {
			return false;
		} else {
			return true;
		}
	}

	private CalendarElementNode lookupPeriodVisId(String periodVisId) {
		return this.mMyControls.getCalendarInfo().findRelativeElement(this.mMyControls.mCalendarElemNode, periodVisId);
	}

	private SqlBuilder getRuntimeDataType() {
		if (this.mMyControls.isRuntimeDataTypeUsed()) {
			SqlBuilder sql = new SqlBuilder(new String[] { "${subqueryPrefix}rtDataType as", "(", "select  <rtDTvisId> as DATA_TYPE", "        ,<rtDTnum> as DT_NUM", "        ,<rtDTsrc> as DT_SRC", "        ,<rtDTscale> as DT_SCALE", "from    dual", ")" });
			sql.substitute(new String[] { "${subqueryPrefix}", this.mMyControls.getSubqueryPrefix() });
			return sql;
		} else {
			return new SqlBuilder();
		}
	}

	private String getPeriodQueryName(int yearOffset, String suffix) {
		String queryName = "rtCal" + suffix;
		if (yearOffset != 0) {
			if (yearOffset < 0) {
				queryName = queryName + "_Ym" + (0 - yearOffset);
			} else {
				queryName = queryName + "_Yp" + yearOffset;
			}
		}

		return queryName;
	}

	private SqlBuilder getRuntimePeriod(String queryName, ContextInputFactory$MyColumn column) {
		if (this.mMyControls.getQueryNames().contains(queryName)) {
			return null;
		} else {
			this.mMyControls.addQueryName(queryName);
			SqlBuilder sql = new SqlBuilder(new String[] { "${subqueryPrefix}${queryName} as", "(", "select   <${queryName}_calDim> as DIM${calDimIndex}", "        ,<${queryName}_pos> as POSITION", "        ,<${queryName}_end_position> as END_POSITION", "        ,<${queryName}_elemType> as CAL_ELEM_TYPE",
					"        ,<${queryName}_sibStart> as SIBLING_START_POSITION", "        ,<${queryName}_sibEnd> as SIBLING_END_POSITION", "from    dual", ")" });
			sql.substitute(new String[] { "${subqueryPrefix}", this.mMyControls.getSubqueryPrefix() });
			sql.substitute(new String[] { "${queryName}", queryName });
			CalendarElementNode calElem = this.mMyControls.getCalendarElementNode();
			int runtimeCalDim = calElem.getStructureElementId();
			int runtimePosition = calElem.getPosition();
			int runtimeEndPosition = calElem.getEndPosition();
			int runtimeElemType = calElem.getElemType();
			int runtimeSiblingStartPosition = 0;
			int runtimeSiblingEndPosition = 0;
			if (calElem.getParent() != null) {
				runtimePosition = calElem.getPosition();
				runtimeSiblingStartPosition = ((CalendarElementNode) calElem.getParent()).getPosition() + 1;
				runtimeSiblingEndPosition = ((CalendarElementNode) calElem.getParent()).getEndPosition();
				if (column.getYearOffset() != 0) {
					calElem = this.mMyControls.getCalendarInfo().getElementOffsetByYear(calElem.getStructureElementId(), column.getYearOffset());
					if (calElem != null) {
						runtimeCalDim = calElem.getStructureElementId();
						runtimePosition = calElem.getPosition();
						runtimeEndPosition = calElem.getEndPosition();
						runtimeSiblingStartPosition = ((CalendarElementNode) calElem.getParent()).getPosition() + 1;
						runtimeSiblingEndPosition = ((CalendarElementNode) calElem.getParent()).getEndPosition();
					} else {
						runtimeCalDim = -1;
					}
				}
			}

			if (runtimeElemType == 0) {
				runtimeSiblingStartPosition = runtimePosition;
				runtimeSiblingEndPosition = runtimePosition;
			}

			this.mMyControls.mSqlExecutor.addBindVariable("<" + queryName + "_calDim>", Integer.valueOf(runtimeCalDim));
			this.mMyControls.mSqlExecutor.addBindVariable("<" + queryName + "_pos>", Integer.valueOf(runtimePosition));
			this.mMyControls.mSqlExecutor.addBindVariable("<" + queryName + "_end_position>", Integer.valueOf(runtimeEndPosition));
			this.mMyControls.mSqlExecutor.addBindVariable("<" + queryName + "_elemType>", Integer.valueOf(runtimeElemType));
			this.mMyControls.mSqlExecutor.addBindVariable("<" + queryName + "_sibStart>", Integer.valueOf(runtimeSiblingStartPosition));
			this.mMyControls.mSqlExecutor.addBindVariable("<" + queryName + "_sibEnd>", Integer.valueOf(runtimeSiblingEndPosition));
			return sql;
		}
	}

	private SqlBuilder getRuntimeSiblings(String periodQueryName, String thisQueryName, ContextInputFactory$MyColumn column) {
		if (this.mMyControls.getQueryNames().contains(thisQueryName)) {
			return null;
		} else {
			this.mMyControls.addQueryName(thisQueryName);
			SqlBuilder sql = new SqlBuilder();
			sql.addLines(this.getRuntimePeriod(periodQueryName, column));
			sql.addLines(new String[] { ",${queryName}_init as", "(", "select   se.STRUCTURE_ELEMENT_ID as DIM${calDimIndex}", "        ,se.POSITION", "        ,se.END_POSITION", "        ,ROWNUM as SEQNUM", "from    ${periodQueryName}", "        ,STRUCTURE_ELEMENT se", "where   se.STRUCTURE_ID = ${calStructureId}",
					"and     se.POSITION between ${periodQueryName}.SIBLING_START_POSITION", "                        and ${periodQueryName}.SIBLING_END_POSITION", "and     decode(se.CAL_ELEM_TYPE,6,3,7,3,8,3,9,3,se.CAL_ELEM_TYPE)", "        = decode(${periodQueryName}.CAL_ELEM_TYPE,6,3,7,3,8,3,9,3,${periodQueryName}.CAL_ELEM_TYPE)",
					"order   by POSITION", ")", ",${queryName} as", "(", "select   DIM${calDimIndex}", "        ,POSITION", "        ,END_POSITION", "        , ${queryName}_init.SEQNUM - rt.SEQNUM as OFFSET", "from    (", "        select  SEQNUM", "        from    ${periodQueryName}", "        join    ${queryName}_init using (DIM${calDimIndex})",
					"        ) rt", "        ,${queryName}_init", ")" });
			sql.substitute(new String[] { "${queryName}", thisQueryName, "${periodQueryName}", periodQueryName, "${calStructureId}", String.valueOf(this.mMyControls.getCalendarInfo().getCalendarId()) });
			return sql;
		}
	}

	private FormInputModel getFormInputModel(int userId, FinanceCubeInput config, int budgetCycleId, String contextDataType, int xAxisIndex, int[] structureElementIds, int childrenDepth, boolean secondaryStructure, CalendarInfo calInfo, Map<String, DataType> dataTypes, Map<Integer, EntityList> securityAccessDetails) throws Exception {
		Timer timer = new Timer(mLog);

		mMyControls = new ContextInputFactory$MyFormInputControls(this, userId, config, budgetCycleId, contextDataType, xAxisIndex, structureElementIds, childrenDepth, secondaryStructure, calInfo, dataTypes, securityAccessDetails);

		getDimInfo();

		SqlBuilder sqlb = new SqlBuilder(new String[] { "${rtDataType}", "${getFormColumns}", "${getFactData}", "${getFactRows}", "${getFinalResultSet}" });

		SqlExecutor sqle = new SqlExecutor("getFormInputModel", mFormContext.getSqlConnection(), sqlb, mLog);
		if (!mMyControls.isGetSpecificMode())
			sqle.setFetchSize(Integer.valueOf(100));
		mMyControls.mSqlExecutor = sqle;

		FormResultSetInputModel formInputModel = new FormResultSetInputModel();
		try {
			commonSubstitutionsAndBinds(sqlb, sqle);

			formInputModel.storeSqlRows(mMyControls.getColumns().size(), sqle.getResultSet());

			if (mMyControls.mIsContextNonLeaf)
				formInputModel.setSheetProtectionLevel(1);
			else if (mMyControls.mIsContextDisabled)
				formInputModel.setSheetProtectionLevel(2);
			else if (mMyControls.mIsContextNonPlannable) {
				formInputModel.setSheetProtectionLevel(3);
			} else if (!mMyControls.isDim0AnAxis()) {
				if ((mMyControls.mContextRABudgetState == null) || (mMyControls.mContextRABudgetState.intValue() != 2))
					formInputModel.setSheetProtectionLevel(4);
			}
		} catch (Exception e) {
			String info;
			int i;
			StructureColumnValue scv;
			mLog.error("getFormInputModel", e);
			throw e;
		} finally {
			sqle.close();
			mFormContext.closeSqlConnection();

			String info = new StringBuilder().append("[timer=").append(timer.getElapsed()).append("] sqlRows=").append(formInputModel != null ? Integer.valueOf(formInputModel.getSqlRowCount()) : "").append(" rows=").append(formInputModel != null ? Integer.valueOf(formInputModel.getRowCount()) : "").append(" cols=")
					.append(formInputModel != null ? Integer.valueOf(formInputModel.getColumnCount()) : "").append(" dims=").append(mMyControls.getNumDims()).append(" axes=").toString();

			for (int i = 0; i < mMyControls.getStructureColumns().size(); i++) {
				StructureColumnValue scv = (StructureColumnValue) mMyControls.getStructureColumns().get(i);
				if (i > 0)
					info = new StringBuilder().append(info).append(",").toString();
				info = new StringBuilder().append(info).append(scv.getDim()).toString();
				if (scv.getDim() == mMyControls.getXAxisIndex())
					info = new StringBuilder().append(info).append("*").toString();
			}
			info = new StringBuilder().append(info).append(" mode=").toString();
			info = new StringBuilder().append(info).append(mMyControls.isGetNextLevelMode() ? "nextLevel" : mMyControls.isGetSpecificMode() ? "specific" : mMyControls.isGetSpecificModeNoFilter() ? "specificNoFilter" : new StringBuilder().append("depth=").append(mMyControls.mChildDepth).toString()).toString();

			info = new StringBuilder().append(info).append(mMyControls.mIsDftNeeded ? " dft=y" : " dft=n").toString();
			info = new StringBuilder().append(info).append(mMyControls.isNewRetrieveMethod() ? " (new)" : "").toString();
			info = new StringBuilder().append(info).append(" userId=").append(userId).toString();
			mLog.info("getFormInputModel", info);
		}
		return formInputModel;
	}

	private void commonSubstitutionsAndBinds(SqlBuilder sqlb, SqlExecutor sqle) {
		sqlb.substituteLines("${rtDataType}", this.getRuntimeDataType());
		sqlb.substituteLines("${getFormColumns}", this.getFormColumns());
		if (this.mMyControls.isNewRetrieveMethod()) {
			sqlb.substituteLines("${getFactData}", this.getFactData1());
		} else {
			sqlb.substituteLines("${getFactData}", this.getFactData2());
		}

		sqlb.substituteLines("${getFactRows}", this.getFactRows());
		sqlb.substituteLines("${getFinalResultSet}", this.getFinalResultSet());
		sqlb.substitute(new String[] { "${allDims}", SqlBuilder.repeatString("${separator}DIM${index}", ",", this.mMyControls.getNumDims()) });
		sqlb.substitute(new String[] { "${allAxisDims}", SqlBuilder.repeatString("${separator}DIM${sub}", ",", this.mMyControls.getAxisDimIndexes()) });
		sqlb.substitute(new String[] { "${financeCubeId}", String.valueOf(this.mMyControls.getFinanceCubeId()), "${modelId}", String.valueOf(this.mMyControls.getFinanceCubeInput().getModelId()), "${XaxisIndex}", String.valueOf(this.mMyControls.getXAxisIndex()), "${calDimIndex}", String.valueOf(this.mMyControls.getCalendarDimIndex()),
				"${accDimIndex}", String.valueOf(this.mMyControls.getCalendarDimIndex() - 1), "${mainAxisDimIndex}", String.valueOf(((StructureColumnValue) this.mMyControls.getStructureColumns().get(0)).getDim()) });

		int dt;
		for (dt = 0; dt < this.mMyControls.getAxisDimIndexes().length; ++dt) {
			int axisDimIndex = this.mMyControls.getAxisDimIndexes()[dt].intValue();
			sqlb.substitute(new String[] { "${axisStructureId." + String.valueOf(axisDimIndex) + "}", String.valueOf(this.mMyControls.getStructureId(axisDimIndex)) });
		}

		sqlb.substitute(new String[] { "${axisStructureId.ra}", String.valueOf(this.mMyControls.getStructureId(0)) });
		sqlb.substitute(new String[] { "${calDimStructureId}", String.valueOf(this.mMyControls.getCalendarInfo().getCalendarId()) });
		sqle.addBindVariable("<depth>", Integer.valueOf(this.mMyControls.mChildDepth));

		for (dt = 0; dt < this.mMyControls.getNumDims(); ++dt) {
			sqle.addBindVariable("<contextDim." + dt + ">", Integer.valueOf(this.mMyControls.getStructureElementId(dt)));
		}

		sqle.addBindVariable("<userId>", Integer.valueOf(this.mMyControls.getUserId()));
		sqle.addBindVariable("<cycleId>", Integer.valueOf(this.mMyControls.getBudgetCycleId()));
		sqle.addBindVariable("<sheetCostCentreState>", this.mMyControls.mContextRABudgetState);
		if (this.mMyControls.isRuntimeDataTypeUsed()) {
			DataType var5 = this.lookupDataType(this.mMyControls.getRuntimeDataType());
			sqle.addBindVariable("<rtDTvisId>", this.mMyControls.getRuntimeDataType());
			sqle.addBindVariable("<rtDTnum>", this.getRtFormatCol(var5));
			sqle.addBindVariable("<rtDTsrc>", this.getRtDecodeCol(var5));
			sqle.addBindVariable("<rtDTscale>", Integer.valueOf(this.getScale(var5)));
		}

	}

	private SqlBuilder getFactData1() {
		SqlBuilder getFactData = new SqlBuilder();
		getFactData.addLines(new String[] { "${getFactSource}", ",sft as", "(", "select   /*+ ordered */ ${DIMs.f},f.DATA_TYPE, gfc.COL, AXIS_DIM_INDEX", "        ,${getNoteValue} as T, CREATED", "        ,<userId> as USER_ID", "        ,case when USER_ID = <userId> then 1 else 0 end as CREATED_BY_THIS_USER", "from    SFT${financeCubeId} f",
				"cross", "join    (", "        select  distinct", "                ${DIMs}, COL, AXIS_DIM_INDEX", "        from    (", "                select  ${DIMs}, COL, AXIS_DIM_INDEX", "                from    ${finalFactSource}", "                )", "        ) src", "cross", "join    getFormColumns gfc", "where   ${matchSftDims}",
				"and     src.COL = gfc.COL and f.DIM${calDimIndex} = gfc.DIM${calDimIndex} and f.DATA_TYPE = gfc.DATA_TYPE", "and     IS_YTD = 0", ")", ",uft as", "(", "select   ${DIMs},DATA_TYPE,COL,AXIS_DIM_INDEX", "        ,T", "        ,case when CREATED_BY_THIS_USER = 1", "              then 0",
				"              when CREATED > nvl(LAST_VIEWED,to_date(\'2000-01-01\',\'YYYY-MM-DD\'))", "              then 1", "              else 0", "         end as I", "from    (", "        select  ${DIMs},DATA_TYPE", "                ,AXIS_DIM_INDEX,max(CREATED) as CREATED", "        from    sft",
				"        group   by ${DIMs},DATA_TYPE,AXIS_DIM_INDEX", "        )", "join    sft using (${DIMs},DATA_TYPE,AXIS_DIM_INDEX,CREATED)", "left", "join    UFT${financeCubeId} f using (${DIMs},DATA_TYPE,USER_ID)", ")", ",ft as", "(", "select   ${nonCalDIMs},DATA_TYPE,COL,AXIS_DIM_INDEX",
				"        ,sum(P)P, sum(N)N, max(S)S, max(D)D, max(C)C", "        ,nullif(\'1\',\'1\') as T, nullif(1,1) as I", "from    (select distinct * from ${finalFactSource})", "group   by ${nonCalDIMs},DATA_TYPE,COL,AXIS_DIM_INDEX", "union all", "select   ${nonCalDIMs},DATA_TYPE,COL,AXIS_DIM_INDEX",
				"        ,nullif(1,1) P, nullif(1,1) N, nullif(\'1\',\'1\') S, nullif(sysdate,sysdate) D, nullif(1,1) C", "        ,T, I", "from   uft", ")", ",getFactColumns as", "(", "select  ${nonCalDIMs},DATA_TYPE,COL,AXIS_DIM_INDEX", "        ,P, N, S, D, C, T, I", "        from   ft", ")" });
		if (this.mMyControls.isGetAllMode()) {
			this.getFactDataExpandAllMode(getFactData);
		} else {
			this.getFactDataSpecificOrNextMode(getFactData);
		}

		getFactData.substituteLines("${dft}", new String[] { "select  ${DIMs},DATA_TYPE", "        ,nullif(1,1) as P", "        ,NUMBER_VALUE as N, ${stringValue} as S, ${dateValue} as D", "        ,nullif(1,1) as C", "from    DFT${financeCubeId}" });
		getFactData.substituteLines("${cft}", new String[] { "select  ${DIMs},DATA_TYPE", "       ,nullif(1,1) as P", "       ,nullif(1,1) as N, nullif(\'1\',\'1\') as S, nullif(sysdate,sysdate) as D", "       ,SHORT_ID as C", "from    CFT${financeCubeId}" });
		getFactData.substituteLines("${sft}", new String[] { "select  ${DIMs},DATA_TYPE", "       ,nullif(1,1) as P", "       ,nullif(1,1) as N, nullif(\'1\',\'1\') as S, nullif(sysdate,sysdate) as D", "       ,nullif(1,1) as C", "from    SFT${financeCubeId}" });
		getFactData.substituteLines("${pft}", new String[] { "select  /*+ INDEX_SS(f PFT${financeCubeId}_PK) */ ${DIMs},DATA_TYPE", "        ,${getParentAmount}", "         as P, nullif(1,1) as N", "        ,nullif(\'1\',\'1\') as S, nullif(sysdate,sysdate) as D", "        ,nullif(1,1) as C", "from    PFT${financeCubeId} f" });
		getFactData.substitute(new String[] { "${DIMs}", SqlBuilder.repeatString("${separator}DIM${index}", ",", this.mMyControls.getNumDims()) });
		getFactData.substitute(new String[] { "${DIMs.f}", SqlBuilder.repeatString("${separator}f.DIM${index}", ",", this.mMyControls.getNumDims()) });
		getFactData.substitute(new String[] { "${nonCalDIMs}", this.getNonCalDimCols("DIM", "") });
		getFactData.substitute(new String[] { "${nonCalDIMs.f}", this.getNonCalDimCols("DIM", "f.") });
		getFactData.substitute(new String[] { "${matchSftDims}", SqlBuilder.repeatString("${separator}f.DIM${index} = src.DIM${index}", " and ", this.mMyControls.getNumDims()) });
		String getNoteValue = "STRING_VALUE";

		try {
			if (this.mFormContext.getSqlConnection().getMetaData().getDatabaseMajorVersion() == 9) {
				getNoteValue = "substr(STRING_VALUE,1,40)";
			}
		} catch (SQLException var9) {
			getNoteValue = "substr(STRING_VALUE,1,40)";
		}

		getFactData.substitute(new String[] { "${getNoteValue}", getNoteValue });
		ArrayList nonRollupDataTypes = new ArrayList();
		Iterator maxStringLen = this.mMyControls.getDataTypes().values().iterator();

		while (maxStringLen.hasNext()) {
			DataType dateColumnNeeded = (DataType) maxStringLen.next();
			if (!dateColumnNeeded.propagatesInDimension(this.mMyControls.getXAxisIndex())) {
				nonRollupDataTypes.add(dateColumnNeeded.getVisId());
			}
		}

		if (nonRollupDataTypes.size() == 0) {
			getFactData.substitute(new String[] { "${getParentAmount}", "NUMBER_VALUE" });
		} else {
			getFactData.substituteLines("${getParentAmount}", new String[] { "case DATA_TYPE", "     ${nonRollupDataTypes}", "     else NUMBER_VALUE", "end" });
			SqlBuilder maxStringLen1 = new SqlBuilder();
			Iterator dateColumnNeeded1 = nonRollupDataTypes.iterator();

			while (dateColumnNeeded1.hasNext()) {
				String i$ = (String) dateColumnNeeded1.next();
				maxStringLen1.addLine("when \'" + i$ + "\' then null");
			}

			getFactData.substituteLines("${nonRollupDataTypes}", maxStringLen1);
		}

		int maxStringLen2 = 0;
		boolean dateColumnNeeded2 = false;
		Iterator i$1 = this.mMyControls.getColumns().iterator();

		while (i$1.hasNext()) {
			ContextInputFactory$MyColumn dt = (ContextInputFactory$MyColumn) i$1.next();
			if (dt.isFixedDataType()) {
				DataType dt1 = this.lookupDataType(dt.getDataType());
				if (dt1.isMeasure()) {
					if (dt1.isMeasureString()) {
						if (dt1.getMeasureLength().intValue() > maxStringLen2) {
							maxStringLen2 = dt1.getMeasureLength().intValue();
						}
					} else if (dt1.isMeasureBoolean()) {
						if (1 > maxStringLen2) {
							maxStringLen2 = 1;
						}
					} else if (dt1.isMeasureDate() || dt1.isMeasureDateTime() || dt1.isMeasureTime()) {
						dateColumnNeeded2 = true;
					}
				}
			}
		}

		if (this.mMyControls.isRuntimeDataTypeUsed()) {
			i$1 = this.mMyControls.getDataTypes().values().iterator();

			while (i$1.hasNext()) {
				DataType dt2 = (DataType) i$1.next();
				if (dt2.getMeasureLength() != null && dt2.getMeasureLength().intValue() > maxStringLen2) {
					maxStringLen2 = dt2.getMeasureLength().intValue();
				} else if (dt2.isMeasureBoolean()) {
					if (1 > maxStringLen2) {
						maxStringLen2 = 1;
					}
				} else if (dt2.isMeasureDate() || dt2.isMeasureDateTime() || dt2.isMeasureTime()) {
					dateColumnNeeded2 = true;
				}
			}
		}

		getFactData.substitute(new String[] { "${stringValue}", maxStringLen2 > 0 ? "substr(STRING_VALUE,1,${maxStringLen})" : "null", "${maxStringLen}", String.valueOf(maxStringLen2), "${dateValue}", dateColumnNeeded2 ? "DATE_VALUE" : "null" });
		return getFactData;
	}

	private String getNonCalDimCols(String columnBaseName, String prefix) {
		return SqlBuilder.repeatString("${separator}" + prefix + columnBaseName + "${index}", ", ", this.mMyControls.getNumDims() - 1);
	}

	private void getFactDataExpandAllMode(SqlBuilder getFactData) {
		if (!this.mMyControls.isSecondaryStructure()) {
			this.expandAllPrimary(getFactData);
		} else {
			this.expandAllSecondary(getFactData);
		}

	}

	private void expandAllPrimary(SqlBuilder getFactData) {
		SqlBuilder sql = new SqlBuilder();
		String finalFactSource = "factParents";
		this.factParents(sql);
		if (this.mMyControls.getAxisDimIndexes().length > 1) {
			this.factLeafs(sql);
			this.factMiddle(sql);
			this.factFinal(sql);
			finalFactSource = "factFinal";
		}

		sql.substitute(new String[] { "${lastAxisIndex}", String.valueOf(this.mMyControls.getAxisDimIndexes()[this.mMyControls.getAxisDimIndexes().length - 1]) });
		getFactData.substituteLines("${getFactSource}", sql);
		getFactData.substitute(new String[] { "${finalFactSource}", finalFactSource });
	}

	private void factFinal(SqlBuilder sql) {
		sql.addLines(new String[] { ",factFinal as", "(" });
		sql.addLines(new String[] { "select ${DIMs},DATA_TYPE,COL,AXIS_DIM_INDEX,P,N,S,D,C from factParents" });
		sql.addLines(new String[] { "union all", "select ${DIMs},DATA_TYPE,COL,AXIS_DIM_INDEX,P,N,S,D,C from factLeafs" });
		sql.addLines(new String[] { "union all", "select ${DIMs},DATA_TYPE,COL,AXIS_DIM_INDEX,P,N,S,D,C from factMiddleParents" });
		sql.addLines(new String[] { ")" });
	}

	private void expandAllSecondary(SqlBuilder getFactData) {
		this.expandAllPrimary(getFactData);
	}

	private void getFactDataSpecificOrNextMode(SqlBuilder getFactData) {
		getFactData.substituteLines("${getFactSource}", new String[] { ",factSelection as", "(", "select  ${DIMs.f},f.DATA_TYPE,gfc.COL", "        ,${XaxisIndex} as AXIS_DIM_INDEX", "        ,P, N, S, D, C", "from    (", "        ${pft}", "        union all", "        ${cft}", "        union all", "        ${sft}", "        union all",
				"        ${dft}", "        ) f", "        ,getFormColumns gfc", "where   1 = 1", "${matchDims}", "        /* form column calendar dimension and data type */", "and     gfc.DATA_TYPE = f.DATA_TYPE and gfc.DIM${calDimIndex} = f.DIM${calDimIndex}", ")" });
		SqlBuilder matchDims = new SqlBuilder();
		int i;
		if (this.mMyControls.isGetSpecificMode()) {
			matchDims.addLine("        /* specific dimensions */");

			for (i = 0; i < this.mMyControls.getNumDims() - 1; ++i) {
				matchDims.addLine("and     DIM${index} = <contextDim.${index}>");
				matchDims.substitute(new String[] { "${index}", String.valueOf(i) });
			}
		} else {
			for (i = 0; i < this.mMyControls.getNumDims() - 1; ++i) {
				if (!this.mMyControls.isDimAnAxis(i)) {
					matchDims.addLines(new String[] { "        /* non-form dimension */", "and     DIM${index} = <contextDim.${index}>" });
					matchDims.substitute(new String[] { "${index}", String.valueOf(i) });
				} else {
					if (this.mMyControls.getXAxisIndex() != i) {
						matchDims.addLines(new String[] { "        /* not X axis */", "and     DIM${index} = <contextDim.${index}>" });
					} else if (!this.mMyControls.isSecondaryStructure()) {
						matchDims.addLines(new String[] { "        /* primary X axis - match immediate children */", "        and exists", "        (", "        select  1", "        from    STRUCTURE_ELEMENT p, STRUCTURE_ELEMENT c", "        where   p.STRUCTURE_ID = ${axisStructureId.${index}}",
								"        and     p.STRUCTURE_ELEMENT_ID = <contextDim.${index}>", "        and     c.STRUCTURE_ID = p.STRUCTURE_ID", "        and     c.PARENT_ID = p.STRUCTURE_ELEMENT_ID", "        and     c.STRUCTURE_ELEMENT_ID = f.DIM${index}", "        )" });
					} else {
						matchDims.addLines(new String[] { "        /* secondary X axis - match all chilren */", "        and exists", "        (", "        select  1", "        from    STRUCTURE_ELEMENT p, STRUCTURE_ELEMENT c", "        where   p.STRUCTURE_ID = ${axisStructureId.${index}}",
								"        and     p.STRUCTURE_ELEMENT_ID = <contextDim.${index}>", "        and     c.STRUCTURE_ID = p.STRUCTURE_ID", "        and     c.POSITION between p.POSITION and p.END_POSITION", "        and     c.LEAF = \'Y\'", "        and     c.STRUCTURE_ELEMENT_ID = f.DIM${index}", "        )" });
					}

					matchDims.substitute(new String[] { "${index}", String.valueOf(i) });
				}
			}
		}

		getFactData.substituteLines("${matchDims}", matchDims);
		getFactData.substitute(new String[] { "${finalFactSource}", "factSelection" });
	}

	private void factParents(SqlBuilder sql) {
		sql.addLines(new String[] { ",factParents as", "(", "select  ${DIMs.f},f.DATA_TYPE,gfc.COL", "        ,${XaxisIndex} as AXIS_DIM_INDEX", "        ${seInfo}", "        ,P, N, S, D, C", "from    (", "        ${pft}", "        union all", "        ${cft}", "        union all", "        ${sft}", "        union all", "        ${dft}",
				"        ) f", "        ${seTables}", "        ,getFormColumns gfc", "where   1 = 1", "${matchParentDims}", "        /* form column calendar dimension and data type */", "and     gfc.DATA_TYPE = f.DATA_TYPE and gfc.DIM${calDimIndex} = f.DIM${calDimIndex}", ")" });
		SqlBuilder seInfo = new SqlBuilder();

		for (int seTables = 0; seTables < this.mMyControls.getNumDims() - 1; ++seTables) {
			if (this.mMyControls.isDimAnAxis(seTables) && !this.mMyControls.isPreXaxis(seTables)) {
				if (seTables == this.mMyControls.getXAxisIndex()) {
					if (!this.mMyControls.isSecondaryStructure()) {
						seInfo.addLines(new String[] { ",se${index}.LEAF                    as LEAF${index}", ",se${index}.DEPTH - p${index}.DEPTH as DEPTH${index}" });
					}
				} else if (this.mMyControls.isDimAnAxis(seTables)) {
					seInfo.addLines(new String[] { ",p${index}.LEAF         as LEAF${index}", ",p${index}.POSITION     as POSITION${index}", ",p${index}.END_POSITION as END_POSITION${index}" });
				}

				seInfo.substitute(new String[] { "${index}", String.valueOf(seTables) });
			}
		}

		SqlBuilder var6 = new SqlBuilder();
		SqlBuilder matchParentDims = new SqlBuilder();

		for (int i = 0; i < this.mMyControls.getNumDims() - 1; ++i) {
			if (!this.mMyControls.isDimAnAxis(i)) {
				matchParentDims.addLine("and     f.DIM${index} = <contextDim.${index}> /* dim not in form */");
			} else if (this.mMyControls.isPreXaxis(i)) {
				matchParentDims.addLine("and     f.DIM${index} = <contextDim.${index}> /* pre X axis */");
			} else if (i == this.mMyControls.getXAxisIndex()) {
				if (!this.mMyControls.isSecondaryStructure()) {
					var6.addLines(new String[] { ",(select * from STRUCTURE_ELEMENT where STRUCTURE_ID = ${axisStructureId.${index}} and STRUCTURE_ELEMENT_ID = <contextDim.${index}>) p${index}" });
					var6.addLines(new String[] { ",STRUCTURE_ELEMENT se${index}" });
					matchParentDims.addLines(new String[] { "        /* X axis */", "and     se${index}.STRUCTURE_ID = p${index}.STRUCTURE_ID", "and     se${index}.POSITION between p${index}.POSITION + 1 and p${index}.END_POSITION", "and     se${index}.STRUCTURE_ELEMENT_ID = f.DIM${index}" });
				} else if (this.mMyControls.isSecondaryStructure()) {
					matchParentDims.addLines(new String[] { "and     exists ", "        (", "        select 1 from STRUCTURE_ELEMENT p, STRUCTURE_ELEMENT c", "        where   p.STRUCTURE_ID = ${axisStructureId.${index}}", "        and     p.STRUCTURE_ELEMENT_ID = <contextDim.${index}>", "        and     c.STRUCTURE_ID = p.STRUCTURE_ID",
							"        and     c.POSITION between p.POSITION and p.END_POSITION", "        and     c.LEAF = \'Y\'", "        and     c.STRUCTURE_ELEMENT_ID = f.DIM${index}", "        )" });
				}
			} else {
				var6.addLines(new String[] { ",(select * from STRUCTURE_ELEMENT where STRUCTURE_ID = ${axisStructureId.${index}} and STRUCTURE_ELEMENT_ID = <contextDim.${index}>) p${index}" });
				matchParentDims.addLine("and     f.DIM${index} = <contextDim.${index}>");
				matchParentDims.addLines(new String[] { "and     p${index}.STRUCTURE_ELEMENT_ID = f.DIM${index}" });
			}

			var6.substitute(new String[] { "${index}", String.valueOf(i) });
			matchParentDims.substitute(new String[] { "${index}", String.valueOf(i) });
		}

		sql.substituteLines("${seInfo}", seInfo);
		sql.substituteLines("${seTables}", var6);
		sql.substituteLines("${matchParentDims}", matchParentDims);
	}

	private void factLeafs(SqlBuilder sql) {
		if (this.mMyControls.getAxisDimIndexes().length != 1) {
			sql.addLines(new String[] { ",factLeafs as", "(", "select  ${DIMs.f},f.DATA_TYPE,gfc.COL", "        ,${lastAxisIndex} as AXIS_DIM_INDEX", "        ,f.P, f.N, f.S, f.D, f.C", "from    (", "        ${cft}", "        union all", "        ${sft}", "        union all", "        ${dft}", "        ) f", "       ,factParents fp",
					"        ,getFormColumns gfc", "where   1=1", "${primarySelection}", "${matchLeafDims}", "and     gfc.COL = fp.COL", "and     gfc.DATA_TYPE = f.DATA_TYPE and gfc.DIM${calDimIndex} = f.DIM${calDimIndex}", ")" });
			SqlBuilder matchLeafDims = new SqlBuilder();

			for (int i = 0; i < this.mMyControls.getNumDims() - 1; ++i) {
				if (!this.mMyControls.isDimAnAxis(i)) {
					matchLeafDims.addLines(new String[] { "and     f.DIM${index} = fp.DIM${index} /* not an axis */" });
				} else if (this.mMyControls.isPreXaxis(i)) {
					matchLeafDims.addLines(new String[] { "and     f.DIM${index} = fp.DIM${index} /* pre X axis */" });
				} else if (i == this.mMyControls.getXAxisIndex()) {
					if (!this.mMyControls.isSecondaryStructure()) {
						sql.substituteLines("${primarySelection}", new String[] { "and     fp.LEAF${XaxisIndex} = \'Y\'", "and     fp.DEPTH${XaxisIndex} < <depth>" });
					}

					matchLeafDims.addLines(new String[] { "and     f.DIM${index} = fp.DIM${index}" });
				} else {
					matchLeafDims.addLines(new String[] { "and     exists", "        (", "        select  1 from STRUCTURE_ELEMENT c", "        where   c.STRUCTURE_ID = ${axisStructureId.${index}}", "        and     c.POSITION between fp.POSITION${index} and fp.END_POSITION${index}", "        and     c.LEAF = \'Y\'",
							"        and     c.STRUCTURE_ELEMENT_ID = f.DIM${index}", "        )" });
				}

				matchLeafDims.substitute(new String[] { "${index}", String.valueOf(i) });
			}

			sql.substituteLines("${matchLeafDims}", matchLeafDims);
			sql.substitute(new String[] { "${primarySelection}", null });
		}
	}

	private void factMiddle(SqlBuilder sql) {
		if (this.mMyControls.getAxisDimIndexes().length != 1) {
			sql.addLines(new String[] { ",factMiddleKeys as", "(", "select  distinct ${nonCalDIMs.f},gfc.COL, IS_CAL_ORIGINAL", "from    (", "        ${cft}", "        union all", "        ${sft}", "        union all", "        ${dft}", "        ) f", "       ,factParents fp", "       ,(",
					"       select  se.STRUCTURE_ELEMENT_ID as DIM${calDimIndex},DATA_TYPE,COL", "               ,decode(se.STRUCTURE_ELEMENT_ID,DIM${calDimIndex},\'Y\',\' \') as IS_CAL_ORIGINAL", "       from    getFormColumns gfc, STRUCTURE_ELEMENT se",
					"       where   se.STRUCTURE_ID = ${calDimStructureId} and se.POSITION between gfc.POSITION and gfc.END_POSITION", "       order   by se.STRUCTURE_ELEMENT_ID, DATA_TYPE", "       ) gfc", "where   1=1", "${primarySelection}", "${matchLeafDims}", "and     gfc.COL = fp.COL",
					"and     gfc.DATA_TYPE = f.DATA_TYPE and gfc.DIM${calDimIndex} = f.DIM${calDimIndex}", ")" });
			SqlBuilder matchLeafDims = new SqlBuilder();

			for (int unionMiddleDims = 0; unionMiddleDims < this.mMyControls.getNumDims() - 1; ++unionMiddleDims) {
				if (!this.mMyControls.isDimAnAxis(unionMiddleDims)) {
					matchLeafDims.addLines(new String[] { "and     f.DIM${index} = fp.DIM${index} /* not an axis */" });
				} else if (this.mMyControls.isPreXaxis(unionMiddleDims)) {
					matchLeafDims.addLines(new String[] { "and     f.DIM${index} = fp.DIM${index} /* pre X axis */" });
				} else if (unionMiddleDims == this.mMyControls.getXAxisIndex()) {
					if (!this.mMyControls.isSecondaryStructure()) {
						sql.substituteLines("${primarySelection}", new String[] { "and     fp.LEAF${XaxisIndex} = \'Y\'", "and     fp.DEPTH${XaxisIndex} < <depth>" });
					}

					matchLeafDims.addLines(new String[] { "and     f.DIM${index} = fp.DIM${index}" });
				} else {
					matchLeafDims.addLines(new String[] { "and     exists", "        (", "        select  1 from STRUCTURE_ELEMENT c", "        where   c.STRUCTURE_ID = ${axisStructureId.${index}}", "        and     c.POSITION between fp.POSITION${index} and fp.END_POSITION${index}", "        and     c.LEAF = \'Y\'",
							"        and     c.STRUCTURE_ELEMENT_ID = f.DIM${index}", "        )" });
				}

				matchLeafDims.substitute(new String[] { "${index}", String.valueOf(unionMiddleDims) });
			}

			sql.substituteLines("${matchLeafDims}", matchLeafDims);
			sql.substitute(new String[] { "${primarySelection}", null });
			sql.addLines(new String[] { ",factMiddleParents as", "(", "select  ${DIMs},DATA_TYPE,COL,AXIS_DIM_INDEX,P,N,S,D,C", "from    (", "        ${pft}", "        union all", "        ${sft}", "        union all", "        ${dft}", "        ) f", "join   (", "       select  ${DIMs},DATA_TYPE,fk.COL,${lastAxisIndex} as AXIS_DIM_INDEX",
					"       from    factMiddleKeys fk, getFormColumns gfc", "       where   gfc.COL = fk.COL", "       and     IS_CAL_ORIGINAL = \' \'", "       ${unionMiddleDims}", "       )", "       using (${DIMs},DATA_TYPE)", ")" });
			SqlBuilder var10 = new SqlBuilder();
			String selectDims = SqlBuilder.repeatString("${separator}DIM${index}", ",", this.mMyControls.getNumDims());

			for (int i = 1; i < this.mMyControls.getAxisDimIndexes().length - 1; ++i) {
				int axisIndex = this.mMyControls.getAxisDimIndexes()[i].intValue();
				if (axisIndex != this.mMyControls.getXAxisIndex() || !this.mMyControls.isSecondaryStructure()) {
					var10.addLines(new String[] { "union all", "select  distinct ${DIMs.repl},DATA_TYPE,fk.COL,${index} as AXIS_DIM_INDEX", "       from    factMiddleKeys fk, getFormColumns gfc", "       where   gfc.COL = fk.COL" });
					String dimsRepl = selectDims;

					for (int j = i + 1; j < this.mMyControls.getAxisDimIndexes().length; ++j) {
						int nextAxisIndex = this.mMyControls.getAxisDimIndexes()[j].intValue();
						dimsRepl = dimsRepl.replace("DIM" + nextAxisIndex, "<contextDim." + nextAxisIndex + "> DIM" + nextAxisIndex);
						var10.substitute(new String[] { "${nextAxisIndex}", String.valueOf(nextAxisIndex) });
					}

					var10.substitute(new String[] { "${DIMs.repl}", dimsRepl });
					var10.substitute(new String[] { "${index}", String.valueOf(axisIndex) });
				}
			}

			sql.substituteLines("${unionMiddleDims}", var10);
		}
	}

	private SqlBuilder getFactData2() {
		SqlBuilder getFactData = new SqlBuilder();
		getFactData.addLines(new String[] { "${axisQueries}", ",pft as", "(", "select  ${axisDimsPrefixed},f.DATA_TYPE,COL", "        ${setAxisDimIndex}", "        ,P,N,S,D,C", "from    (", "        select  /*+ INDEX_SS(f PFT${financeCubeId}_PK) */ ${allDimsUnprefixed}, DATA_TYPE", "                ,${getParentAmount}", "                 as P",
				"                ,nullif(1,1) as N, nullif(\'1\',\'1\') as S, nullif(sysdate,sysdate) as D", "                ,nullif(1,1) as C", "        from    PFT${financeCubeId} f", "        ${dft}", "        union all", "        select  ${allDimsUnprefixed}, DATA_TYPE", "                ,nullif(1,1) as P",
				"                ,nullif(1,1) as N, nullif(\'1\',\'1\') as S, nullif(sysdate,sysdate) as D", "                ,SHORT_ID as C", "        from    CFT${financeCubeId}", "        ) f", "        ${axisQueriesAsTables}", "        /*--- form columns:   */ ,getFormColumns", "where   1=1", "${matchDims}", "${matchFormColumns}",
				"${omitUnwanted}", ")", ",sft as", "(", "select  /*+ ordered */ distinct", "        ${allDimsPrefixed},f.DATA_TYPE,COL", "        ${setAxisDimIndex}", "        ,${getNoteValue} as T, CREATED", "        ,<userId> as USER_ID", "        ,case when USER_ID = <userId> then 1 else 0 end as CREATED_BY_THIS_USER",
				"from    /*--- fact table:     */  SFT${financeCubeId} f", "        ${axisQueriesAsTables}", "        /*--- form columns:   */ ,getFormColumns", "where   1=1", "${matchDims}", "${matchFormColumns}", "and     IS_YTD = 0", "${omitUnwanted}", ")", ",uft as", "(", "select  ${allDimsUnprefixed},DATA_TYPE,COL,AXIS_DIM_INDEX",
				"        ,T", "        ,case when CREATED_BY_THIS_USER = 1", "              then 0", "              when CREATED > nvl(LAST_VIEWED,to_date(\'2000-01-01\',\'YYYY-MM-DD\'))", "              then 1", "              else 0", "         end as I", "from    (", "        select  ${allDims},DATA_TYPE",
				"                ,AXIS_DIM_INDEX,max(CREATED) as CREATED", "        from    sft", "        group   by ${allDims},DATA_TYPE,AXIS_DIM_INDEX", "        )", "join    sft using (${allDims},DATA_TYPE,AXIS_DIM_INDEX,CREATED)", "left", "join    UFT${financeCubeId} f using (${allDims},DATA_TYPE,USER_ID)", ")", ",ft as", "(",
				"select  ${axisDimsUnprefixed},DATA_TYPE,COL,AXIS_DIM_INDEX", "        ,sum(P)P, sum(N)N, max(S)S, max(D)D, max(C)C", "        ,nullif(\'1\',\'1\') as T, nullif(1,1) as I", "from   pft", "group   by ${axisDimsUnprefixed},DATA_TYPE,COL,AXIS_DIM_INDEX", "union all", "select  ${axisDimsUnprefixed},DATA_TYPE,COL,AXIS_DIM_INDEX",
				"        ,null P, null N, null S, null D, null C", "        ,T, I", "from   uft", ")", ",getFactColumns as", "(", "select   ${axisDimsUnprefixed},DATA_TYPE,COL,AXIS_DIM_INDEX", "        ,C,S,D,N,T,I,P", "from    ft", ")" });
		if (this.mMyControls.mIsDftNeeded) {
			getFactData.substituteLines("${dft}", new String[] { "union all", "select  ${allDimsUnprefixed}, DATA_TYPE", "        ,nullif(1,1) as P", "        ,NUMBER_VALUE as N, ${stringValue} as S, ${dateValue} as D", "        ,nullif(1,1) as C", "from    DFT${financeCubeId} f" });
		} else {
			getFactData.substitute(new String[] { "${dft}", null });
			getFactData.substitute(new String[] { "${unionDft}", null });
		}

		getFactData.substituteLines("${matchFormColumns}", new String[] { "        /*--- column calendar element and dataType: */", "and     f.DIM${calDimIndex} = getFormColumns.DIM${calDimIndex}", "and     f.DATA_TYPE = getFormColumns.DATA_TYPE" });
		String axisDimsPrefixed = "";
		String axisDimsUnprefixed = "";

		for (int getNoteValue = 0; getNoteValue < this.mMyControls.getAxisDimIndexes().length; ++getNoteValue) {
			int nonRollupDataTypes = this.mMyControls.getAxisDimIndexes()[getNoteValue].intValue();
			if (axisDimsPrefixed.length() > 0) {
				axisDimsPrefixed = axisDimsPrefixed + ",";
				axisDimsUnprefixed = axisDimsUnprefixed + ",";
			}

			axisDimsPrefixed = axisDimsPrefixed + "f.DIM" + nonRollupDataTypes;
			axisDimsUnprefixed = axisDimsUnprefixed + "DIM" + nonRollupDataTypes;
		}

		getFactData.substitute(new String[] { "${axisDimsPrefixed}", axisDimsPrefixed, "${axisDimsUnprefixed}", axisDimsUnprefixed });
		String var12 = "STRING_VALUE";

		try {
			if (this.mFormContext.getSqlConnection().getMetaData().getDatabaseMajorVersion() == 9) {
				var12 = "substr(STRING_VALUE,1,40)";
			}
		} catch (SQLException var11) {
			var12 = "substr(STRING_VALUE,1,40)";
		}

		getFactData.substitute(new String[] { "${getNoteValue}", var12 });
		ArrayList var13 = new ArrayList();
		Iterator maxStringLen = this.mMyControls.getDataTypes().values().iterator();

		while (maxStringLen.hasNext()) {
			DataType dateColumnNeeded = (DataType) maxStringLen.next();
			if (!dateColumnNeeded.propagatesInDimension(this.mMyControls.getXAxisIndex())) {
				var13.add(dateColumnNeeded.getVisId());
			}
		}

		if (var13.size() == 0) {
			getFactData.substitute(new String[] { "${getParentAmount}", "NUMBER_VALUE" });
		} else {
			getFactData.substituteLines("${getParentAmount}", new String[] { "case DATA_TYPE", "     ${nonRollupDataTypes}", "     else NUMBER_VALUE", "end" });
			SqlBuilder var15 = new SqlBuilder();
			Iterator var16 = var13.iterator();

			while (var16.hasNext()) {
				String i$ = (String) var16.next();
				var15.addLine("when \'" + i$ + "\' then null");
			}

			getFactData.substituteLines("${nonRollupDataTypes}", var15);
		}

		int var14 = 0;
		boolean var17 = false;
		Iterator var18 = this.mMyControls.getColumns().iterator();

		while (var18.hasNext()) {
			ContextInputFactory$MyColumn dt = (ContextInputFactory$MyColumn) var18.next();
			if (dt.isFixedDataType()) {
				DataType dt1 = this.lookupDataType(dt.getDataType());
				if (dt1.isMeasure()) {
					if (dt1.isMeasureString()) {
						if (dt1.getMeasureLength().intValue() > var14) {
							var14 = dt1.getMeasureLength().intValue();
						}
					} else if (dt1.isMeasureBoolean()) {
						if (1 > var14) {
							var14 = 1;
						}
					} else if (dt1.isMeasureDate() || dt1.isMeasureDateTime() || dt1.isMeasureTime()) {
						var17 = true;
					}
				}
			}
		}

		if (this.mMyControls.isRuntimeDataTypeUsed()) {
			var18 = this.mMyControls.getDataTypes().values().iterator();

			while (var18.hasNext()) {
				DataType var19 = (DataType) var18.next();
				if (var19.getMeasureLength() != null && var19.getMeasureLength().intValue() > var14) {
					var14 = var19.getMeasureLength().intValue();
				} else if (var19.isMeasureBoolean()) {
					if (1 > var14) {
						var14 = 1;
					}
				} else if (var19.isMeasureDate() || var19.isMeasureDateTime() || var19.isMeasureTime()) {
					var17 = true;
				}
			}
		}

		getFactData.substitute(new String[] { "${stringValue}", var14 > 0 ? "substr(STRING_VALUE,1,${maxStringLen})" : "null", "${maxStringLen}", String.valueOf(var14), "${dateValue}", var17 ? "DATE_VALUE" : "null" });
		if (this.mMyControls.isGetSpecificMode()) {
			this.getMatchForSpecific(getFactData);
		} else if (this.mMyControls.isGetNextLevelMode()) {
			this.getMatchForNextLevel(getFactData);
		} else {
			this.getMatchForExpandAll(getFactData);
		}

		getFactData.substitute(new String[] { "${mainAxisDimIndex}", String.valueOf(((StructureColumnValue) this.mMyControls.getStructureColumns().get(0)).getDim()) });
		getFactData.substitute(new String[] { "${allDimsPrefixed}", SqlBuilder.repeatString("${separator}f.DIM${index}", ",", this.mMyControls.getNumDims()) });
		getFactData.substitute(new String[] { "${allDimsUnprefixed}", SqlBuilder.repeatString("${separator}DIM${index}", ",", this.mMyControls.getNumDims()) });
		getFactData.substituteLines("${setAxisDimIndex}", this.setAxisDimIndex());
		return getFactData;
	}

	private void getMatchForSpecific(SqlBuilder sqlb) {
		sqlb.substitute(new String[] { "${axisQueries}", null });
		sqlb.substitute(new String[] { "${axisQueriesAsTables}", null });
		sqlb.substitute(new String[] { "${omitUnwanted}", null });
		sqlb.substituteRepeatingLines("${matchDims}", this.mMyControls.getNumDims() - 1, (String) null, new String[] { "and     f.DIM${index} = <contextDim.${index}>" });
	}

	private void getMatchForNextLevel(SqlBuilder sqlb) {
		SqlBuilder matchDims = new SqlBuilder();
		if (!this.mMyControls.isSecondaryStructure()) {
			sqlb.substituteLines("${axisQueries}", new String[] { ",c${XaxisIndex} as", "(", "/*--- primary axis: get all immediate children */", "select  /*+ materialize */", "        se.STRUCTURE_ELEMENT_ID as DIM", "        ,se.DEPTH, se.POSITION", "from    STRUCTURE_ELEMENT se", "where   se.STRUCTURE_ID = ${axisStructureId.${XaxisIndex}}",
					"and     se.PARENT_ID = <contextDim.${XaxisIndex}>", "order   by 1", ")" });
		} else {
			sqlb.substituteLines("${axisQueries}", new String[] { ",c${XaxisIndex} as", "(", "/*--- secondary axis: get all leaf descendants */", "select  /*+ materialize */", "        c.STRUCTURE_ELEMENT_ID as DIM", "        ,c.DEPTH, c.POSITION", "from    STRUCTURE_ELEMENT p", "        ,STRUCTURE_ELEMENT c",
					"where   p.STRUCTURE_ID = ${axisStructureId.${XaxisIndex}}", "and     p.STRUCTURE_ELEMENT_ID = <contextDim.${XaxisIndex}>", "and     c.STRUCTURE_ID = p.STRUCTURE_ID", "and     c.POSITION between p.POSITION", "                       and p.END_POSITION", "and     c.LEAF = \'Y\'", "order   by 1", ")" });
		}

		sqlb.substitute(new String[] { "${axisQueriesAsTables}", "/*--- X axis:         */ ,c${XaxisIndex}" });
		sqlb.substitute(new String[] { "${omitUnwanted}", null });

		for (int dimIndex = 0; dimIndex < this.mMyControls.getNumDims() - 1; ++dimIndex) {
			if (dimIndex != this.mMyControls.getXAxisIndex()) {
				matchDims.addLine("and     f.DIM${index} = <contextDim.${index}> /*--- context */");
			} else {
				matchDims.addLine("and     f.DIM${index} = c${index}.DIM /*--- X axis */");
			}

			matchDims.substitute(new String[] { "${index}", String.valueOf(dimIndex) });
		}

		sqlb.substituteLines("${matchDims}", matchDims);
	}

	private SqlBuilder setAxisDimIndex() {
		SqlBuilder sqlb = new SqlBuilder();
		if (this.mMyControls.isGetSpecificMode()) {
			sqlb.addLine(",${XaxisIndex} as AXIS_DIM_INDEX");
		} else if (this.mMyControls.isGetNextLevelMode()) {
			sqlb.addLine(",${XaxisIndex} as AXIS_DIM_INDEX");
		} else if (this.mMyControls.getStructureColumns().size() == 1) {
			sqlb.addLine(",${XaxisIndex} as AXIS_DIM_INDEX");
		} else {
			sqlb.addLines(new String[] { ",decode(\'Y\'", " ${checkIfLeaf}", " ,${XaxisIndex}", " ) as AXIS_DIM_INDEX" });

			for (int i = this.mMyControls.getStructureColumns().size() - 1; i > 0; --i) {
				StructureColumnValue scv = (StructureColumnValue) this.mMyControls.getStructureColumns().get(i);
				int axisIndex = scv.getDim();
				if (!this.isFixedDim(axisIndex)) {
					sqlb.substituteLines("${checkIfLeaf}", new String[] { ",decode(f.DIM${axisIndex},c${axisIndex}.DIM,LEAF${axisIndex},\'Y\'),${axisIndex}", "${checkIfLeaf}" });
					sqlb.substitute(new String[] { "${axisIndex}", String.valueOf(axisIndex) });
				}
			}

			sqlb.substitute(new String[] { "${checkIfLeaf}", null });
		}

		return sqlb;
	}

	private void getMatchForExpandAll(SqlBuilder sqlb) {
		SqlBuilder matchDims = new SqlBuilder();
		SqlBuilder axisQueries = new SqlBuilder();
		SqlBuilder axisQueriesAsTables = new SqlBuilder();

		Integer prevAxisDimIndex;
		int i;
		int axisIndex;
		for (int omitUnwanted = 0; omitUnwanted < this.mMyControls.getNumDims(); ++omitUnwanted) {
			prevAxisDimIndex = null;

			for (i = 0; i < this.mMyControls.getAxisDimIndexes().length; ++i) {
				if (this.mMyControls.getAxisDimIndexes()[i].intValue() == omitUnwanted) {
					prevAxisDimIndex = Integer.valueOf(i);
				}
			}

			if (prevAxisDimIndex == null) {
				if (omitUnwanted < this.mMyControls.getNumDims() - 1) {
					matchDims.addLine("and     f.DIM${index} = <contextDim.${index}>");
				}
			} else {
				Integer var10 = null;

				for (axisIndex = 0; axisIndex < this.mMyControls.getAxisDimIndexes().length; ++axisIndex) {
					if (this.mMyControls.getAxisDimIndexes()[axisIndex].intValue() == this.mMyControls.getXAxisIndex()) {
						var10 = Integer.valueOf(axisIndex);
					}
				}

				if (var10 == null) {
					throw new IllegalStateException("X axis dim index not found");
				}

				if (prevAxisDimIndex.intValue() < var10.intValue()) {
					axisQueries.addLines(new String[] { "/*--- pre-X axis */", ",c${index} as", "(", "select  /*+ materialize */", "        STRUCTURE_ELEMENT_ID as DIM", "        ,LEAF                as LEAF${index}", "        ,DEPTH, POSITION", "from    STRUCTURE_ELEMENT", "where   STRUCTURE_ID = ${axisStructureId.${index}}",
							"and     STRUCTURE_ELEMENT_ID = <contextDim.${index}>", ")" });
					axisQueriesAsTables.addLines(new String[] { "/*--- pre-X axis:     */ ,c${index}" });
					matchDims.addLines(new String[] { "        /*--- pre-X axis: */", "and     f.DIM${index} = <contextDim.${index}>", "and     f.DIM${index} = c${index}.DIM" });
				} else if (prevAxisDimIndex == var10) {
					if (prevAxisDimIndex.intValue() == 0) {
						axisQueries.addLines(new String[] { "/*--- X axis (primary): get all branch and leaf dependants: */", ",c${index} as", "(", "select  /*+ materialize */", "         cse.STRUCTURE_ELEMENT_ID as DIM", "        ,cse.LEAF                 as LEAF${index}", "        ,cse.DEPTH, cse.POSITION", "from    STRUCTURE_ELEMENT pse",
								"join    STRUCTURE_ELEMENT cse", "        on", "        (", "            cse.STRUCTURE_ID = pse.STRUCTURE_ID", "        and cse.POSITION between pse.POSITION + 1 and pse.END_POSITION", "        and cse.DEPTH <= pse.DEPTH + <depth>", "        )", "where   pse.STRUCTURE_ID         = ${axisStructureId.${index}}",
								"and     pse.STRUCTURE_ELEMENT_ID = <contextDim.${index}>", "order   by 1", ")" });
						axisQueriesAsTables.addLines(new String[] { "/*--- X axis:         */ ,c${index}" });
						matchDims.addLines(new String[] { "        /*--- X axis: */", "and     f.DIM${index} = c${index}.DIM" });
					} else {
						axisQueries.addLines(new String[] { "/*--- X axis (secondary): get all leaf descendants */", ",c${index} as", "(", "select  /*+ materialize */", "        c.STRUCTURE_ELEMENT_ID as DIM", "        ,c.LEAF                as LEAF${index}", "        ,c.DEPTH, c.POSITION", "from    STRUCTURE_ELEMENT p",
								"        ,STRUCTURE_ELEMENT c", "where   p.STRUCTURE_ID = ${axisStructureId.${index}}", "and     p.STRUCTURE_ELEMENT_ID = <contextDim.${index}>", "and     c.STRUCTURE_ID = p.STRUCTURE_ID", "and     c.POSITION between p.POSITION", "                       and p.END_POSITION", "and     c.LEAF = \'Y\'", "order   by 1",
								")" });
						axisQueriesAsTables.addLines(new String[] { "/*--- X axis:         */ ,c${index}" });
						matchDims.addLines(new String[] { "        /*--- X axis: */", "and     f.DIM${index} = c${index}.DIM" });
					}
				} else {
					axisQueries.addLines(new String[] { "/*--- post X axis: */", ",c${index} as", "(", "/*--- (1) select context element */", "select  /*+ materialize */", "        STRUCTURE_ELEMENT_ID as DIM", "        ,LEAF as LEAF${index}", "        ,POSITION, END_POSITION, STRUCTURE_ID", "from    STRUCTURE_ELEMENT",
							"where   STRUCTURE_ID = ${axisStructureId.${index}}", "and     STRUCTURE_ELEMENT_ID = <contextDim.${index}>", ")", ",c${index}_match as", "(", "/*--- (2) used to match with fact tables */", "select  DIM, LEAF${index}         from c${index}", "union   all", "select  DIM, \' \'  as LEAF${index} from c${index}",
							"where   LEAF${index} = \'Y\'", ")", ",c${index}_dep as", "(", "/*--- (3) get all leaf descendants */", "select  /*+ materialize */", "        se.STRUCTURE_ELEMENT_ID as DIM", "        ,se.DEPTH, se.POSITION", "from    c${index}", "join    STRUCTURE_ELEMENT se", "        on",
							"        (   se.STRUCTURE_ID = c${index}.STRUCTURE_ID", "        and se.POSITION between c${index}.POSITION + 1 and c${index}.END_POSITION", "        and se.LEAF = \'Y\'", "        )", "order   by 1", ")" });
					axisQueriesAsTables.addLines(new String[] { "/*--- secondary axis: */ ,c${index}_match c${index}" });
					matchDims.addLines(new String[] { "        /*--- secondary axis: */", "and     (", "             (f.DIM${index} = c${index}.DIM)", "        or   (LEAF${index} = \' \' and f.DIM${index} in (select DIM from c${index}_dep))", "        )" });
				}
			}

			axisQueries.substitute(new String[] { "${index}", String.valueOf(omitUnwanted) });
			axisQueriesAsTables.substitute(new String[] { "${index}", String.valueOf(omitUnwanted) });
			matchDims.substitute(new String[] { "${index}", String.valueOf(omitUnwanted) });
		}

		SqlBuilder var9 = new SqlBuilder();
		prevAxisDimIndex = Integer.valueOf(this.mMyControls.getXAxisIndex());

		for (i = 0; i < this.mMyControls.getAxisDimIndexes().length; ++i) {
			axisIndex = this.mMyControls.getAxisDimIndexes()[i].intValue();
			if (axisIndex != this.mMyControls.getXAxisIndex() && axisIndex != this.mMyControls.getNumDims() - 1) {
				if (prevAxisDimIndex.intValue() == this.mMyControls.getXAxisIndex()) {
					var9.addLines(new String[] { "and     not", "        (", "            (   LEAF${XaxisIndex} = \' \'", "            and decode(f.DIM${axisIndex},c${axisIndex}.DIM,LEAF${axisIndex},\'Y\') = \'Y\'", "            )" });
				} else {
					var9.addLines(new String[] { "            or", "            (   decode(f.DIM${prevAxisIndex},c${prevAxisIndex}.DIM,LEAF${prevAxisIndex},\'Y\') = \' \'", "            and decode(f.DIM${axisIndex},c${axisIndex}.DIM,LEAF${axisIndex},\'Y\') = \'Y\'", "            )" });
				}

				var9.substitute(new String[] { "${prevAxisIndex}", String.valueOf(prevAxisDimIndex) });
				var9.substitute(new String[] { "${axisIndex}", String.valueOf(axisIndex) });
				prevAxisDimIndex = Integer.valueOf(axisIndex);
			}
		}

		if (var9.getLines().size() > 0) {
			var9.addLine("        )");
		}

		sqlb.substituteLines("${omitUnwanted}", var9);
		sqlb.substituteLines("${axisQueries}", axisQueries);
		sqlb.substituteLines("${axisQueriesAsTables}", axisQueriesAsTables);
		sqlb.substituteLines("${matchDims}", matchDims);
	}

	private boolean isFixedDim(int dim) {
		boolean isFixed = true;
		Integer axisDimIndexIndex = null;

		for (int xAxisDimIndexIndex = 0; xAxisDimIndexIndex < this.mMyControls.getAxisDimIndexes().length; ++xAxisDimIndexIndex) {
			if (this.mMyControls.getAxisDimIndexes()[xAxisDimIndexIndex].intValue() == dim) {
				axisDimIndexIndex = Integer.valueOf(xAxisDimIndexIndex);
			}
		}

		if (axisDimIndexIndex == null) {
			isFixed = true;
		} else {
			Integer var6 = null;

			for (int j = 0; j < this.mMyControls.getAxisDimIndexes().length; ++j) {
				if (this.mMyControls.getAxisDimIndexes()[j].intValue() == this.mMyControls.getXAxisIndex()) {
					var6 = Integer.valueOf(j);
				}
			}

			if (var6 == null) {
				throw new IllegalStateException("X axis dim index not found");
			}

			if (axisDimIndexIndex.intValue() >= var6.intValue()) {
				isFixed = false;
			}
		}

		return isFixed;
	}

	private SqlBuilder getFormColumns() {
		SqlBuilder sql = new SqlBuilder(new String[] { "${subqueryPrefix}getFormColumns as", "(", "select  /*+ materialize */", "        0 COL,0 IS_YTD,0 DIM${calDimIndex},0 POSITION, 0 END_POSITION,\'  \' DATA_TYPE from dual where 1 = 0" });
		SqlBuilder periodQuerySql = new SqlBuilder();
		boolean unionAll = false;

		for (int colNum = 0; colNum < this.mMyControls.mMyColumns.size(); ++colNum) {
			ContextInputFactory$MyColumn column = (ContextInputFactory$MyColumn) this.mMyControls.mMyColumns.get(colNum);
			String runtimePeriodQuery = this.getPeriodQueryName(column.getYearOffset(), "");
			String runtimeSiblingQuery = this.getPeriodQueryName(column.getYearOffset(), "_Siblings");
			String colCalSeId = null;
			String colCalPos = "-1";
			String colCalEndPos = "-1";
			String colDataType = null;
			String colSourceName = null;
			String colSourceCriteria = null;
			SqlBuilder colSelect = new SqlBuilder(new String[] { "union all select ${col}, ${isYtd}, ${calSeId}, ${calPos}, ${calEndPos}, ${dataType} from ${colSourceName}", "          ${colSourceCriteria}" });
			if (column.isFixedPeriod() && column.isYtd()) {
				int var21 = column.getPeriodElemId();
				List ytdElems = this.mMyControls.getCalendarInfo().getYtdElements(var21);
				Iterator i$ = ytdElems.iterator();

				while (i$.hasNext()) {
					CalendarElementNode ytdElem = (CalendarElementNode) i$.next();
					int elemId = ytdElem.getStructureElementId();
					colCalSeId = String.valueOf(elemId);
					colCalPos = String.valueOf(ytdElem.getPosition());
					colCalEndPos = String.valueOf(ytdElem.getEndPosition());
					if (column.isFixedDataType()) {
						colDataType = "\'" + column.getDataType() + "\'";
					} else {
						colDataType = "DATA_TYPE";
						colSourceName = "rtDataType";
					}

					SqlBuilder ytdColumn = new SqlBuilder();
					ytdColumn.addLines(colSelect);
					ytdColumn.substitute(new String[] { "${col}", String.valueOf(colNum + 1), "${isYtd}", column.isYtd() ? "1" : "0", "${calSeId}", colCalSeId, "${calPos}", colCalPos, "${calEndPos}", colCalEndPos, "${dataType}", colDataType, "${unionAll}", unionAll ? "union all" : null, "${colSourceName}",
							colSourceName != null ? colSourceName : "dual", "${colSourceCriteria}", colSourceCriteria != null ? colSourceCriteria : "" });
					unionAll = true;
					sql.addLines(ytdColumn);
				}
			} else {
				if (column.isYtd()) {
					periodQuerySql.addLines(this.getRuntimeSiblings(runtimePeriodQuery, runtimeSiblingQuery, column));
					colCalSeId = runtimeSiblingQuery + ".DIM${calDimIndex}";
					colCalPos = runtimeSiblingQuery + ".POSITION";
					colCalEndPos = runtimeSiblingQuery + ".END_POSITION";
					colSourceName = runtimePeriodQuery + "," + runtimeSiblingQuery;
					colSourceCriteria = "where   " + runtimePeriodQuery + ".POSITION " + (column.getPeriodOffset() == 0 ? "" : (column.getPeriodOffset() < 0 ? " - " + String.valueOf(0 - column.getPeriodOffset()) : " + " + column.getPeriodOffset())) + " >= " + runtimeSiblingQuery + ".POSITION";
				} else if (column.isFixedPeriod()) {
					colCalSeId = String.valueOf(column.getPeriodElemId());
					CalendarElementNode cen = this.mMyControls.getCalendarInfo().getById(column.getPeriodElemId());
					if (cen != null) {
						colCalPos = String.valueOf(cen.getPosition());
						colCalEndPos = String.valueOf(cen.getEndPosition());
					}
				} else if (column.getPeriodOffset() == 0) {
					periodQuerySql.addLines(this.getRuntimePeriod(runtimePeriodQuery, column));
					colCalSeId = "DIM${calDimIndex}";
					colCalPos = "POSITION";
					colCalEndPos = "END_POSITION";
					colSourceName = runtimePeriodQuery;
				} else {
					periodQuerySql.addLines(this.getRuntimeSiblings(runtimePeriodQuery, runtimeSiblingQuery, column));
					colCalSeId = "DIM${calDimIndex}";
					colCalPos = "POSITION";
					colCalEndPos = "END_POSITION";
					colSourceName = runtimeSiblingQuery;
					colSourceCriteria = "where   " + runtimeSiblingQuery + ".OFFSET = " + column.getPeriodOffset();
				}

				if (column.isFixedDataType()) {
					colDataType = "\'" + column.getDataType() + "\'";
				} else {
					colDataType = "DATA_TYPE";
					if (colSourceName == null) {
						colSourceName = "rtDataType";
					} else {
						colSourceName = colSourceName + ",rtDataType";
					}
				}

				colSelect.substitute(new String[] { "${col}", String.valueOf(colNum + 1), "${isYtd}", column.isYtd() ? "1" : "0", "${calSeId}", colCalSeId, "${calPos}", colCalPos, "${calEndPos}", colCalEndPos, "${dataType}", colDataType, "${runtimeSiblingQuery}", runtimeSiblingQuery, "${colSourceName}",
						colSourceName != null ? colSourceName : "dual", "${colSourceCriteria}", colSourceCriteria != null ? colSourceCriteria : "", "${unionAll}", unionAll ? "union all" : "" });
				unionAll = true;
				sql.addLines(colSelect);
			}
		}

		sql.addLine(")");
		sql.substitute(new String[] { "${subqueryPrefix}", this.mMyControls.getSubqueryPrefix() });
		periodQuerySql.addLines(sql);
		return periodQuerySql;
	}

	private SqlBuilder getFactRows() {
		SqlBuilder sql = new SqlBuilder(new String[] { ",getFactRows as", "(", "select  ${allAxisDims},AXIS_DIM_INDEX,COL${stateColumn}", "        ,${getNumber} as N", "        ,C,S,D,T,I", "from    ${rtDataType}getFactColumns", "${budgetStateJoin}", ")" });
		boolean isCostCentreAnAxis = false;

		for (int cubeHasNumericMeasure = 0; cubeHasNumericMeasure < this.mMyControls.getAxisDimIndexes().length; ++cubeHasNumericMeasure) {
			if (this.mMyControls.getAxisDimIndexes()[cubeHasNumericMeasure].intValue() == 0) {
				isCostCentreAnAxis = true;
			}
		}

		if (!isCostCentreAnAxis) {
			sql.substitute(new String[] { "${stateColumn}", null, "${stateGroupColumn}", null, "${budgetStateJoin}", null });
		} else {
			sql.substitute(new String[] { "${stateColumn}", ",nvl(STATE,-1) as STATE" });
			sql.substitute(new String[] { "${stateGroupColumn}", ",nvl(STATE,-1)" });
			sql.substituteLines("${budgetStateJoin}", new String[] { "left", "join    BUDGET_STATE bs", "        on", "        (", "            BUDGET_CYCLE_ID = <cycleId>", "        and bs.STRUCTURE_ELEMENT_ID = DIM0", "        )" });
		}

		boolean var10 = false;
		Iterator allDefaultScale = this.mMyControls.getDataTypes().keySet().iterator();

		while (allDefaultScale.hasNext()) {
			String decodeNumber = (String) allDefaultScale.next();
			DataType i = this.lookupDataType(decodeNumber);
			if (i.isMeasureNumeric()) {
				var10 = true;
				break;
			}
		}

		sql.substitute(new String[] { "${scaleColumn}", this.mMyControls.isRuntimeDataTypeUsed() ? ", DT_SCALE" : null });
		sql.substitute(new String[] { "${rtDataType}", this.mMyControls.isRuntimeDataTypeUsed() ? "rtDataType, " : null });
		boolean var11 = true;
		if (this.mMyControls.isRuntimeDataTypeUsed()) {
			var11 = false;
		} else {
			for (int var12 = 0; var12 < this.mMyControls.getColumns().size(); ++var12) {
				ContextInputFactory$MyColumn var15 = (ContextInputFactory$MyColumn) this.mMyControls.getColumns().get(var12);
				if (var15.isFixedDataType() && this.getScale(this.lookupDataType(var15.getDataType())) != 10000) {
					var11 = false;
				}
			}
		}

		if (!var11 && var10) {
			sql.substituteLines("${getNumber}", new String[] { "decode", "  (COL", "   ${decodeNumber}", "   ,decode(coalesce(P,N),null,null,nvl(P,0)+nvl(N,0))/10000", "  ) as N" });
			SqlBuilder var13 = new SqlBuilder();

			for (int var14 = 0; var14 < this.mMyControls.getColumns().size(); ++var14) {
				ContextInputFactory$MyColumn col = (ContextInputFactory$MyColumn) this.mMyControls.getColumns().get(var14);
				DataType dt = col.isFixedDataType() ? this.lookupDataType(col.getDataType()) : null;
				int scale = dt != null ? this.getScale(dt) : -1;
				if (col.isFixedDataType()) {
					if (!dt.isFinanceValue() && !dt.isMeasureNumeric()) {
						var13.addLines(new String[] { ",${num}, nullif(1,1)" });
					} else {
						if (scale == 10000) {
							continue;
						}

						var13.addLines(new String[] { ",${num},decode(coalesce(P,N),null,null,nvl(P,0)+nvl(N,0))/${scale}" });
						var13.substitute(new String[] { "${scale}", String.valueOf(scale) });
					}
				} else {
					var13.addLines(new String[] { ",${num}", ",decode", "  (DT_SRC,\'S\',nullif(1,1),\'D\',nullif(1,1)", "  ,decode(coalesce(P,N),null,null,nvl(P,0)+nvl(N,0))/DT_SCALE", "  )" });
				}

				var13.substitute(new String[] { "${num}", String.valueOf(var14 + 1) });
			}

			sql.substituteLines("${decodeNumber}", var13);
		} else {
			sql.substitute(new String[] { "${getNumber}", "decode(coalesce(P,N),null,null,nvl(P,0)+nvl(N,0))/10000" });
		}

		return sql;
	}

	private SqlBuilder getFinalResultSet() {
		SqlBuilder sql = new SqlBuilder(new String[] { "${rangeSubqueries}", "${raAccessSubquery}", ",getFinal as", "(", "select  ${hint}", "         ${visId} as VIS_ID", "        ,${description} as DESCRIPTION", "        ,${budgetStateValue} as STATE", "        ,${depth} as DEPTH", "        ,${leaf} as LEAF", "        ,${is_credit} as IS_CREDIT",
				"        ,${disabled} as DISABLED", "        ,${not_plannable} as NOT_PLANNABLE", "        ,case", "         when <sheetNonLeaf> = 1", "         then ${PROTECTION_SHEET_NON_LEAF} /*--- fixed elem non-leaf */", "         when <sheetDisabled> = 1", "         then ${PROTECTION_SHEET_DISABLED} /*--- fixed elem disabled */",
				"         when <sheetNonPlannable> = 1", "         then ${PROTECTION_SHEET_NON_PLANNABLE} /*--- fixed elem non-plannable */", "         when ${anyNonLeaf}", "         then ${PROTECTION_NON_LEAF} /*--- elem non-leaf */", "         when ${anyDisabled}", "         then ${PROTECTION_DISABLED_ELEMENT} /*--- elem disabled */",
				"         when ${anyNonPlannable}", "         then ${PROTECTION_NON_PLANNABLE_ELEMENT} /*--- elem non-plannable */", "         ${budgetStateCheck}", "         ${checkRangeAccess}", "         when ${raAccessMode} = 1", "         then ${PROTECTION_SHEET_RA_READONLY} /*--- responsibility area protection */",
				"         when ${raAccessMode} = 2", "         then ${PROTECTION_OFF} /*--- no protection */", "         ${checkMissingRangeAccess}", "         else ${PROTECTION_OFF} /*--- no protection */", "         end as PROTECTION_STATE", "        ,${dim} as DIM", "        ,${dimPos} as DIM_POS", "        ,AXIS_DIM_INDEX",
				"        ,COL,N,C,S,D,T,I", "        ,case when", "            max(N) over", "            (", "            partition by ${axisPositions}", "            order     by ${depth}", "            rows      between unbounded preceding and unbounded following", "            ) is null then null else \'Y\'", "         end as IS_NEEDED",
				"from    getFactRows", "${joinStructureElement}", "${rangeQuery}", "${raAccessQuery}", "order   by", "        ${axisPositions}", "        ,${depth},COL", ")", "select  VIS_ID,DESCRIPTION,STATE,DEPTH,LEAF", "        ,IS_CREDIT,DISABLED,NOT_PLANNABLE,PROTECTION_STATE", "        ,DIM,DIM_POS,AXIS_DIM_INDEX",
				"        ,COL,N,C,S,D,T,I", "from    getFinal", "where   IS_NEEDED = \'Y\'" });
		if (this.mMyControls.isGetAllMode()) {
			if (this.mMyControls.getAxisDimIndexes()[0].intValue() == 0) {
				sql.substitute(new String[] { "${hint}", null });
			}
		} else if (this.mMyControls.isGetNextLevelMode()) {
			sql.substitute(new String[] { "${hint}", null });
		}

		sql.substitute(new String[] { "${hint}", "/* no_query_transformation */ " });
		String axisPositions = "";
		if (this.mMyControls.isGetSpecificMode()) {
			sql.substituteLines("${joinStructureElement}", new String[] { "join    STRUCTURE_ELEMENT d${XaxisIndex}", "        on  (", "                 d${XaxisIndex}.STRUCTURE_ID = ${axisStructureId.${XaxisIndex}}", "            and  d${XaxisIndex}.STRUCTURE_ELEMENT_ID = DIM${XaxisIndex}", "            )" });
			axisPositions = axisPositions + "d${XaxisIndex}.POSITION";
		} else {
			for (int isCostCentreAnAxis = 0; isCostCentreAnAxis < this.mMyControls.getStructureColumns().size(); ++isCostCentreAnAxis) {
				StructureColumnValue getColumn = (StructureColumnValue) this.mMyControls.getStructureColumns().get(isCostCentreAnAxis);
				int getDepth = getColumn.getDim();
				if (isCostCentreAnAxis > 0) {
					axisPositions = axisPositions + ",";
				}

				axisPositions = axisPositions + "d" + getDepth + ".POSITION";
				sql.substituteLines("${joinStructureElement}", new String[] { "join    STRUCTURE_ELEMENT d${axisIndex}", "        on (d${axisIndex}.STRUCTURE_ID = ${axisStructureId.${axisIndex}} and d${axisIndex}.STRUCTURE_ELEMENT_ID = DIM${axisIndex})", "${joinStructureElement}" });
				sql.substitute(new String[] { "${axisStructureId}", String.valueOf(this.mMyControls.getStructureId(getColumn.getDim())) });
				sql.substitute(new String[] { "${axisIndex}", String.valueOf(getDepth) });
			}

			sql.substitute(new String[] { "${joinStructureElement}", null });
		}

		sql.substitute(new String[] { "${axisPositions}", axisPositions });
		boolean var18 = false;

		for (int var19 = 0; var19 < this.mMyControls.getAxisDimIndexes().length; ++var19) {
			if (this.mMyControls.getAxisDimIndexes()[var19].intValue() == 0) {
				var18 = true;
			}
		}

		if (!var18) {
			sql.substitute(new String[] { "${budgetStateValue}", "<sheetCostCentreState>" });
		} else {
			sql.substitute(new String[] { "${budgetStateValue}", "STATE" });
		}

		String var21 = "";
		String var20 = "";
		String getDim = "";
		String getDimPos = "";
		if (!this.mMyControls.isGetSpecificMode() && this.mMyControls.getStructureColumns().size() != 1) {
			var21 = "decode(AXIS_DIM_INDEX,";
			var20 = "decode(AXIS_DIM_INDEX,";
			getDim = "decode(AXIS_DIM_INDEX,";
			getDimPos = "decode(AXIS_DIM_INDEX,";

			for (int factColumns = this.mMyControls.getStructureColumns().size() - 1; factColumns > 0; --factColumns) {
				StructureColumnValue rangeSubqueries = (StructureColumnValue) this.mMyControls.getStructureColumns().get(factColumns);
				int rangeQuery = rangeSubqueries.getDim();
				var21 = var21 + rangeQuery + ",d" + rangeQuery + ".${columnName},";
				var20 = var20 + rangeQuery + ",d${mainAxisDimIndex}.DEPTH + " + factColumns + ",";
				getDim = getDim + rangeQuery + ",DIM" + rangeQuery + ",";
				getDimPos = getDimPos + rangeQuery + ",d" + rangeQuery + ".POSITION" + ",";
			}

			var21 = var21 + "d${mainAxisDimIndex}.${columnName})";
			var20 = var20 + "d${mainAxisDimIndex}.DEPTH)";
			getDim = getDim + "DIM${mainAxisDimIndex})";
			getDimPos = getDimPos + "d${mainAxisDimIndex}.POSITION)";
		} else {
			var21 = "d${XaxisIndex}.${columnName}";
			var20 = "DEPTH";
			getDim = "DIM${XaxisIndex}";
			getDimPos = "d${XaxisIndex}.POSITION";
		}

		sql.substitute(new String[] { "${visId}", var21 });
		sql.substitute(new String[] { "${columnName}", "VIS_ID" });
		sql.substitute(new String[] { "${description}", var21 });
		sql.substitute(new String[] { "${columnName}", "DESCRIPTION" });
		sql.substitute(new String[] { "${depth}", var20 });
		sql.substitute(new String[] { "${leaf}", var21 });
		sql.substitute(new String[] { "${columnName}", "LEAF" });
		sql.substitute(new String[] { "${is_credit}", var21 });
		sql.substitute(new String[] { "${columnName}", "IS_CREDIT" });
		sql.substitute(new String[] { "${disabled}", var21 });
		sql.substitute(new String[] { "${columnName}", "DISABLED" });
		sql.substitute(new String[] { "${not_plannable}", var21 });
		sql.substitute(new String[] { "${columnName}", "NOT_PLANNABLE" });
		sql.substitute(new String[] { "${dim}", getDim });
		sql.substitute(new String[] { "${dimPos}", getDimPos });
		SqlBuilder var22 = new SqlBuilder();
		var22.addRepeatingLines(this.mMyControls.getColumns().size(), (String) null, new String[] { ",V${num},I${num},T${num},C${num},X${num},Y${num}" });
		sql.substituteLines("${factColumns}", var22);
		if (!var18) {
			sql.substituteLines("${budgetStateCheck}", new String[] { "when <sheetCostCentreState> <> 2", "then ${PROTECTION_SHEET_NON_PREPARING} /*--- fixed costcentre state not preparing */" });
		} else {
			sql.substituteLines("${budgetStateCheck}", new String[] { "when STATE not in (-1,1,2)", "then ${PROTECTION_ELEMENT_NON_PREPARING} /*--- costcentre element state not preparing */" });
		}

		SqlBuilder var23 = new SqlBuilder();
		SqlBuilder var24 = new SqlBuilder();
		this.getRangeSecurityChecks(var23, var24);
		sql.substituteLines("${rangeSubqueries}", var23);
		sql.substituteLines("${rangeQuery}", var24);
		SqlBuilder raAccessSubquery = new SqlBuilder();
		SqlBuilder raAccessQuery = new SqlBuilder();
		this.getRAAccessMode(raAccessSubquery, raAccessQuery);
		sql.substituteLines("${raAccessSubquery}", raAccessSubquery);
		sql.substituteLines("${raAccessQuery}", raAccessQuery);
		if (var24.getLines().size() == 0) {
			sql.substitute(new String[] { "${checkRangeAccess}", null });
			sql.substitute(new String[] { "${checkMissingRangeAccess}", null });
		} else {
			sql.substituteLines("${checkRangeAccess}", new String[] { "when RANGE_ACCESS_MODE = 1", "then ${PROTECTION_SHEET_BUSINESS_RANGECODE} /*--- elem range code protection */", "when RANGE_ACCESS_MODE = 2", "then ${PROTECTION_OFF} /*--- no protection */" });
			sql.substituteLines("${checkMissingRangeAccess}", new String[] { "when RANGE_ACCESS_MODE is null and exists(select 1 from getRanges)", "then ${PROTECTION_SHEET_BUSINESS_RANGECODE} /*--- elem range code protection */" });
		}

		if (raAccessQuery.getLines().size() == 0) {
			sql.substitute(new String[] { "${raAccessMode}", "<sheetRaAccess>" });
		} else {
			sql.substitute(new String[] { "${raAccessMode}", "RA_ACCESS_MODE" });
		}

		String anyNonLeaf = "";
		String anyDisabled = "";
		String anyNonPlannable = "";
		if (this.mMyControls.isGetSpecificMode()) {
			anyNonLeaf = "d${XaxisIndex}.LEAF <> \'Y\'";
			anyDisabled = "d${XaxisIndex}.DISABLED = \'Y\'";
			anyNonPlannable = "d${XaxisIndex}.NOT_PLANNABLE = \'Y\'";
		} else if (this.mMyControls.getStructureColumns().size() == 1) {
			anyNonLeaf = "d${XaxisIndex}.LEAF <> \'Y\'";
			anyDisabled = "d${XaxisIndex}.DISABLED = \'Y\'";
			anyNonPlannable = "d${XaxisIndex}.NOT_PLANNABLE = \'Y\'";
		} else {
			for (int i = 0; i < this.mMyControls.getAxisDimIndexes().length; ++i) {
				int axisIndex = this.mMyControls.getAxisDimIndexes()[i].intValue();
				var21 = var21 + axisIndex + ",d" + axisIndex + ".${columnName},";
				var20 = var20 + axisIndex + ",d${mainAxisDimIndex}.DEPTH + " + i + ",";
				getDim = getDim + axisIndex + ",DIM" + axisIndex + ",";
				anyNonLeaf = anyNonLeaf + (anyNonLeaf.length() > 0 ? " or " : "") + "d" + axisIndex + ".LEAF <> \'Y\'";
				anyDisabled = anyDisabled + (anyDisabled.length() > 0 ? "," : "") + "d" + axisIndex + ".DISABLED";
				anyNonPlannable = anyNonPlannable + (anyNonPlannable.length() > 0 ? "," : "") + "d" + axisIndex + ".NOT_PLANNABLE";
			}

			anyDisabled = "\'Y\' in (" + anyDisabled + ")";
			anyNonPlannable = "\'Y\' in (" + anyNonPlannable + ")";
		}

		sql.substitute(new String[] { "${anyNonLeaf}", anyNonLeaf, "${anyDisabled}", anyDisabled, "${anyNonPlannable}", anyNonPlannable });
		sql.substitute(new String[] { "${PROTECTION_SHEET_NON_LEAF}", String.valueOf(1), "${PROTECTION_SHEET_DISABLED}", String.valueOf(2), "${PROTECTION_SHEET_NON_PLANNABLE}", String.valueOf(3), "${PROTECTION_SHEET_NON_PREPARING}", String.valueOf(4), "${PROTECTION_ELEMENT_NON_PREPARING}", String.valueOf(21), "${PROTECTION_NON_LEAF}",
				String.valueOf(11), "${PROTECTION_DISABLED_ELEMENT}", String.valueOf(12), "${PROTECTION_NON_PLANNABLE_ELEMENT}", String.valueOf(13), "${PROTECTION_NON_PLANNABLE_ELEMENT}", String.valueOf(13), "${PROTECTION_SHEET_BUSINESS_RANGECODE}", String.valueOf(19), "${PROTECTION_SHEET_RA_READONLY}", String.valueOf(5), "${PROTECTION_OFF}",
				String.valueOf(0) });
		this.mMyControls.mSqlExecutor.addBindVariable("<sheetNonLeaf>", Integer.valueOf(this.mMyControls.mIsContextNonLeaf ? 1 : 0));
		this.mMyControls.mSqlExecutor.addBindVariable("<sheetDisabled>", Integer.valueOf(this.mMyControls.mIsContextDisabled ? 1 : 0));
		this.mMyControls.mSqlExecutor.addBindVariable("<sheetNonPlannable>", Integer.valueOf(this.mMyControls.mIsContextNonPlannable ? 1 : 0));
		this.mMyControls.mSqlExecutor.addBindVariable("<sheetRaAccess>", this.mMyControls.mContextRAAccessMode);
		return sql;
	}

	private void getRangeSecurityChecks(SqlBuilder subqueries, SqlBuilder mainQuery) {
		if (!this.mMyControls.mIsContextNonLeaf) {
			EntityList el = this.mMyControls.getSecurityAccessDetails(this.mMyControls.getFinanceCubeInput().getModelId());
			if (el.getNumRows() != 0) {
				subqueries.addLines(new String[] { ",getUserAccess as", "(", "select  SECURITY_ACCESS_DEF_ID, ACCESS_MODE, sa.VIS_ID as ACCESS_NAME", "from    SECURITY_GROUP_USER_REL", "join    SECURITY_GROUP sg        using (SECURITY_GROUP_ID)", "join    SECURITY_ACCESS_DEF sa   on    (SECURITY_ACCESS_DEF_ID = UPDATE_ACCESS_ID)",
						"where   USER_ID=<userId>", ")", ",getRanges as", "(", "select SECURITY_ACCESS_DEF_ID, DIMENSION_ID, FROM_ID, TO_ID, sr.VIS_ID as RANGE_NAME", "from   getUserAccess", "join   SECURITY_ACC_RNG_REL sar  using (SECURITY_ACCESS_DEF_ID)", "join   SECURITY_RANGE sr         using (SECURITY_RANGE_ID)",
						"join   SECURITY_RANGE_ROW        using (SECURITY_RANGE_ID)", "order  by 1", ")", ",getRangeSec as", "(", "select  ${outputDims}", "        ${rangeAccessCode}", "from    (", "        select  distinct", "                ${inputDims}", "        from    ${dimInputTable}", "        )", "join    getUserAccess sa", "        on",
						"        ${expression}", "${groupBy}", ")" });
				SqlBuilder expressionSql = new SqlBuilder();

				for (int isDimNeededForRangeCheck = 0; isDimNeededForRangeCheck < el.getNumRows(); ++isDimNeededForRangeCheck) {
					String isDimNeededInFinalResult = (String) el.getValueAt(isDimNeededForRangeCheck, "ACCESS_NAME");
					String outputDims = (String) el.getValueAt(isDimNeededForRangeCheck, "EXPRESSION");
					EntityList inputDims = (EntityList) el.getValueAt(isDimNeededForRangeCheck, "RANGES");
					expressionSql.addLines(new String[] { "${or}", "(", "    ACCESS_NAME = \'${accessName}\' /*--- ${accessExpression} */", "and (", "    ${accessSqlExpression}", "    )", ")" });
					expressionSql.substitute(new String[] { "${or}", isDimNeededForRangeCheck == 0 ? null : "or", "${accessName}", isDimNeededInFinalResult, "${accessExpression}", outputDims });
					SqlBuilder dimInputTable = new SqlBuilder();
					SecurityAccessExpressionLexer i = new SecurityAccessExpressionLexer(outputDims);
					String axisDim = null;
					int indent = 0;

					while ((axisDim = i.next()) != null) {
						int tokenType = i.getTokenType();
						if (tokenType != 4) {
							if (tokenType == 5) {
								--indent;
								dimInputTable.addLine(this.fill(indent * 4) + ")");
							} else if (tokenType == 2) {
								dimInputTable.addLine(this.fill(indent * 4) + "and");
							} else if (tokenType == 3) {
								dimInputTable.addLine(this.fill(indent * 4) + "or");
							} else if (tokenType == 1) {
								dimInputTable.addLine(this.fill(indent * 4) + "not");
							} else {
								dimInputTable.addLines(new String[] { this.fill(indent * 4) + "/*--- range " + axisDim + " */", this.fill(indent * 4) + "${checkRange}" });

								for (int j = 0; j < inputDims.getNumRows(); ++j) {
									String rangeName = (String) inputDims.getValueAt(j, "RANGE_NAME");
									int dimSeqNum = ((BigDecimal) inputDims.getValueAt(j, "DIMENSION_SEQ_NUM")).intValue();
									if (axisDim.equals(rangeName)) {
										dimInputTable.substituteLines("${checkRange}", new String[] { "${dimValue} in", "    (", "    select  DIMENSION_ELEMENT_ID", "    from    getRanges sr", "    join    DIMENSION_ELEMENT de", "            on (de.DIMENSION_ID = sr.DIMENSION_ID", "                and de.VIS_ID between FROM_ID and TO_ID)",
												"    where   sr.SECURITY_ACCESS_DEF_ID = sa.SECURITY_ACCESS_DEF_ID", "    and     RANGE_NAME = \'${rangeName}\'", "    )" });
										dimInputTable.substitute(new String[] { "${rangeName}", rangeName });
										if (!this.mMyControls.isGetSpecificMode() && (!this.mMyControls.isGetNextLevelMode() || this.mMyControls.getXAxisIndex() == dimSeqNum) && (!this.mMyControls.isGetAllMode() || !this.isFixedDim(dimSeqNum))) {
											dimInputTable.substitute(new String[] { "${dimValue}", "DIM" + dimSeqNum });
										} else {
											dimInputTable.substitute(new String[] { "${dimValue}", "<contextDim." + dimSeqNum + ">" });
										}
									}
								}
							}
						} else {
							dimInputTable.addLine(this.fill(indent * 4) + "(");
							++indent;
						}
					}

					expressionSql.substituteLines("${accessSqlExpression}", dimInputTable);
				}

				subqueries.substituteLines("${expression}", expressionSql);
				boolean[] var17 = new boolean[this.mMyControls.getNumDims() - 1];

				int var25;
				for (int var18 = 0; var18 < el.getNumRows(); ++var18) {
					EntityList var19 = (EntityList) el.getValueAt(var18, "RANGES");

					for (int var22 = 0; var22 < var19.getNumRows(); ++var22) {
						var25 = ((BigDecimal) var19.getValueAt(var22, "DIMENSION_SEQ_NUM")).intValue();
						var17[var25] = true;
					}
				}

				boolean[] var20 = new boolean[this.mMyControls.getNumDims() - 1];
				int var28;
				if (!this.mMyControls.isGetSpecificMode() && !this.mMyControls.isGetNextLevelMode()) {
					for (int var21 = 0; var21 < this.mMyControls.getNumDims() - 1; ++var21) {
						Integer[] var26 = this.mMyControls.getAxisDimIndexes();
						var25 = var26.length;

						for (var28 = 0; var28 < var25; ++var28) {
							int var29 = var26[var28].intValue();
							if (var21 == var29) {
								var20[var21] = true;
							}
						}
					}
				} else {
					var20[this.mMyControls.getXAxisIndex()] = true;
				}

				ArrayList var23 = new ArrayList();
				ArrayList var24 = new ArrayList();
				String var27 = "dual";

				for (var28 = 0; var28 < this.mMyControls.getNumDims() - 1; ++var28) {
					if (var20[var28]) {
						var24.add("${separator}DIM" + String.valueOf(var28));
						var27 = "getFactRows";
						var23.add("${separator}DIM" + String.valueOf(var28));
					} else if (var17[var28]) {
						var24.add("${separator}<contextDim.${index}> as DIM" + String.valueOf(var28));
					}
				}

				subqueries.substituteLines("${inputDims}", ",", var24);
				subqueries.substitute(new String[] { "${dimInputTable}", var27 });
				if (var23.size() >= var24.size()) {
					subqueries.substitute(new String[] { "${rangeAccessCode}", ",ACCESS_MODE as RANGE_ACCESS_MODE" });
					subqueries.substitute(new String[] { "${groupBy}", null });
				} else {
					subqueries.substitute(new String[] { "${rangeAccessCode}", ",min(ACCESS_MODE) as RANGE_ACCESS_MODE" });
					subqueries.substitute(new String[] { "${groupBy}", "group  by ${outputDims}" });
				}

				subqueries.substituteLines("${outputDims}", ",", var23);
				mainQuery.addLines(new String[] { "left", "join    getRangeSec ${rangeJoinDims}" });
				if (var23.size() == 0) {
					mainQuery.substitute(new String[] { "${rangeJoinDims}", "on (1=1)" });
				} else {
					mainQuery.substituteLines("${rangeJoinDims}", new String[] { "using (", "      ${outputDims}", "      )" });
					mainQuery.substituteLines("${outputDims}", ",", var23);
				}

				mainQuery.substituteLines("${expression}", expressionSql);
			}
		}
	}

	private String fill(int len) {
		StringBuilder sb = new StringBuilder(len);

		for (int i = 0; i < len; ++i) {
			sb.append(' ');
		}

		return sb.toString();
	}

	private void getRAAccessMode(SqlBuilder subqueries, SqlBuilder mainQuery) {
		if (this.mMyControls.isDim0AnAxis()) {
			if (!this.mMyControls.isGetSpecificMode()) {
				if (!this.mMyControls.isGetNextLevelMode() || this.mMyControls.getXAxisIndex() == 0) {
					subqueries.addLines(new String[] { ",getRaAccess as", "(", "select  DIM as DIM0, RA_ACCESS_MODE", "from    (", "        select   STRUCTURE_ELEMENT_ID as DIM", "                ,RA_ACCESS_MODE", "                ,rank()", "                 over (order by ra.DEPTH desc, ra.POSITION)", "                 as RANK",
							"        from    STRUCTURE_ELEMENT c0", "        join    (", "                select  DEPTH,POSITION,END_POSITION", "                        ,decode(READ_ONLY,\'Y\',1,2) as RA_ACCESS_MODE", "                from    BUDGET_USER bu", "                        ,(",
							"                        select  DEPTH, POSITION, END_POSITION", "                                ,STRUCTURE_ELEMENT_ID as DIM", "                        from    STRUCTURE_ELEMENT", "                        where   STRUCTURE_ID = ${axisStructureId.ra}",
							"                        start   with STRUCTURE_ID = ${axisStructureId.ra}", "                                 and PARENT_ID = 0", "                        connect by  STRUCTURE_ID = prior STRUCTURE_ID", "                                and PARENT_ID    = prior STRUCTURE_ELEMENT_ID", "                        )",
							"                where   bu.MODEL_ID = ${modelId}", "                and     bu.STRUCTURE_ELEMENT_ID = DIM", "                and     bu.USER_ID = <userId>", "                order   by DEPTH,POSITION", "                ) ra", "                on (c0.POSITION between ra.POSITION and ra.END_POSITION)",
							"                where c0.STRUCTURE_ID = ${axisStructureId.ra}", "        )", "where   RANK = 1", "order   by 1", ")" });
					mainQuery.addLines(new String[] { "left", "join    getRaAccess using (DIM0)" });
				}
			}
		}
	}

	// $FF: synthetic method
	static FormContext accessMethod200(ContextInputFactory x0) {
		return x0.mFormContext;
	}

	// $FF: synthetic method
	static String accessMethod300(ContextInputFactory x0) {
		return x0.getRetrievalMethodProperty();
	}

	// $FF: synthetic method
	public static ContextInputFactory$MyFormInputControls accessMethod400(ContextInputFactory x0) {
		return x0.mMyControls;
	}

}
