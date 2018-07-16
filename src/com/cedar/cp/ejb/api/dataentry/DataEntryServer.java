// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.dataentry;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dataEntry.CellCalculationDetails;
import com.cedar.cp.api.dataEntry.CellContents;
import com.cedar.cp.api.dataEntry.DataExtract;
import com.cedar.cp.api.dataEntry.FinanceSystemCellData;
import com.cedar.cp.api.dataEntry.MassUpdateParameters;
import com.cedar.cp.api.facades.ExtractDataDTO;
import com.cedar.cp.dto.extsys.ExtSysTransactionQueryParams;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.dataentry.DataEntryHome;
import com.cedar.cp.ejb.api.dataentry.DataEntryLocal;
import com.cedar.cp.ejb.api.dataentry.DataEntryLocalHome;
import com.cedar.cp.ejb.api.dataentry.DataEntryRemote;
import com.cedar.cp.ejb.impl.dataentry.DataEntrySEJB;
import com.cedar.cp.impl.dataEntry.DataEntryProcessImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.inputs.FormDataInputModel;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.naming.Context;

public class DataEntryServer extends AbstractSession {

	private static final String REMOTE_JNDI_NAME = "ejb/DataEntryRemoteHome";
	private static final String LOCAL_JNDI_NAME = "ejb/DataEntryLocalHome";
	protected DataEntryRemote mRemote;
	protected DataEntryLocal mLocal;
	private Log mLog = new Log(this.getClass());

	public DataEntryServer(CPConnection conn_) {
		super(conn_);
	}

	public DataEntryServer(Context context_, boolean remote) {
		super(context_, remote);
	}

	private DataEntrySEJB getRemote() throws CreateException, RemoteException, CPException {
//		if (this.mRemote == null) {
//			String jndiName = this.getRemoteJNDIName();
//
//			try {
//				DataEntryHome e = (DataEntryHome) this.getHome(jndiName, DataEntryHome.class);
//				this.mRemote = e.create();
//			} catch (CreateException var3) {
//				this.removeFromCache(jndiName);
//				var3.printStackTrace();
//				throw new CPException("getRemote " + jndiName + " CreateException", var3);
//			} catch (RemoteException var4) {
//				this.removeFromCache(jndiName);
//				var4.printStackTrace();
//				throw new CPException("getRemote " + jndiName + " RemoteException", var4);
//			}
//		}
//
//		return this.mRemote;
		return DataEntryProcessImpl.server;
	}

	public int getFinanceCubeIdFromVisId(String modelVisId, String cubeVisId) throws ValidationException {
		try {
			if (isRemoteConnection()) {
				return getRemote().getFinanceCubeIdFromVisId(modelVisId, cubeVisId);
			}
			return getLocal().getFinanceCubeIdFromVisId(modelVisId, cubeVisId);
		} catch (Exception e) {
			throw unravelException(e);
		}
	}

	private DataEntrySEJB getLocal() throws CPException {
//		if (this.mLocal == null) {
//			try {
//				DataEntryLocalHome e = (DataEntryLocalHome) this.getLocalHome(this.getLocalJNDIName());
//				this.mLocal = e.create();
//			} catch (CreateException var2) {
//				throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
//			}
//		}
//
//		return this.mLocal;
		return DataEntryProcessImpl.server;

	}

	public void removeSession() throws CPException {
	}

	public boolean doesCellMatchChartOfAccounts(int modelId, int financeCubeId, int[] cellReference, String[] cellVisIds, int userId, int budgetCycleId) throws ValidationException {
		boolean matches = false;

		try {
			if (this.isRemoteConnection()) {
				matches = this.getRemote().doesCellMatchChartOfAccounts(modelId, financeCubeId, cellReference, cellVisIds, userId, budgetCycleId);
			} else {
				matches = this.getLocal().doesCellMatchChartOfAccounts(modelId, financeCubeId, cellReference, cellVisIds, userId, budgetCycleId);
			}

			return matches;
		} catch (Exception var9) {
			throw this.unravelException(var9);
		}
	}

	public DataExtract dataImport(DataExtract dataExtract) throws ValidationException, CPException {
		try {
			DataExtract result;
			if (this.isRemoteConnection()) {
				result = this.getRemote().dataImport(dataExtract);
			} else {
				result = this.getLocal().dataImport(dataExtract);
			}

			return result;
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public DataExtract dataImportPrep(Object key) throws ValidationException, CPException {
		try {
			DataExtract result;
			if (this.isRemoteConnection()) {
				result = this.getRemote().dataImportPrep(key);
			} else {
				result = this.getLocal().dataImportPrep(key);
			}

			return result;
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public DataExtract getDataExtract(EntityRef financeCubeRef, List businessDims, List accountIds, List periodIds, List dataTypes, boolean fullIntersection, boolean count) throws ValidationException {
		DataExtract result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getDataExtract(financeCubeRef, businessDims, accountIds, periodIds, dataTypes, fullIntersection, count);
			} else {
				result = this.getLocal().getDataExtract(financeCubeRef, businessDims, accountIds, periodIds, dataTypes, fullIntersection, count);
			}

			return result;
		} catch (Exception var10) {
			throw this.unravelException(var10);
		}
	}

	public List getFinanceCubeDataSlice(String modelVisId, String fcVisId, String aggregationRule, int[] rowHeadings, int[] columnHeadings, int[] filterHeadings, List rows, List columns, List filters) throws ValidationException {
		List result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getFinanceCubeDataSlice(modelVisId, fcVisId, aggregationRule, rowHeadings, columnHeadings, filterHeadings, rows, columns, filters);
			} else {
				result = this.getLocal().getFinanceCubeDataSlice(modelVisId, fcVisId, aggregationRule, rowHeadings, columnHeadings, filterHeadings, rows, columns, filters);
			}

			return result;
		} catch (Exception var12) {
			throw this.unravelException(var12);
		}
	}

	public List getAllStructureElementsForFinanceCube(int userId, String modelVisId, String fcVisId) throws ValidationException {
		List result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getAllStructureElementsForFinanceCube(userId, modelVisId, fcVisId);
			} else {
				result = this.getLocal().getAllStructureElementsForFinanceCube(userId, modelVisId, fcVisId);
			}

			return result;
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public Map getFinanceCubeDataForExtract(int fcId, int[] structureElementIds, String dataType) throws ValidationException {
		Map result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getFinanceCubeDataForExtract(fcId, structureElementIds, dataType);
			} else {
				result = this.getLocal().getFinanceCubeDataForExtract(fcId, structureElementIds, dataType);
			}

			return result;
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public Object getSingleCellValue(int fcId, int[] structureElementIds, String dataType) throws ValidationException {
		Object result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getSingleCellValue(fcId, structureElementIds, dataType);
			} else {
				result = this.getLocal().getSingleCellValue(fcId, structureElementIds, dataType);
			}

			return result;
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public CellContents getFinanceCubeCell(String modelVisId, String financeCubeVisId, boolean ytd, String[] structureVisIds, String[] elementVisIds, String dataType) throws ValidationException {
		CellContents result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getFinanceCubeCell(modelVisId, financeCubeVisId, ytd, structureVisIds, elementVisIds, dataType);
			} else {
				result = this.getLocal().getFinanceCubeCell(modelVisId, financeCubeVisId, ytd, structureVisIds, elementVisIds, dataType);
			}

			return result;
		} catch (Exception var9) {
			throw this.unravelException(var9);
		}
	}

	public EntityList getCellValues(int fcId, int numDims, String[] structureVisIds, List<String[]> cellKeys, String company) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getCellValues(fcId, numDims, structureVisIds, cellKeys, company) : this.getLocal().getCellValues(fcId, numDims, structureVisIds, cellKeys);
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public EntityList getCellValues(int fcId, int numDims, int[] hierIds, List<String[]> cellKeys, String company) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getCellValues(fcId, numDims, hierIds, cellKeys, company) : this.getLocal().getCellValues(fcId, numDims, hierIds, cellKeys);
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public EntityList getCellValues(int fcId, int numDims, String[] structureVisIds, List<String[]> cellKeys) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getCellValues(fcId, numDims, structureVisIds, cellKeys) : this.getLocal().getCellValues(fcId, numDims, structureVisIds, cellKeys);
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public EntityList getCellValues(int fcId, int numDims, int[] hierIds, List<String[]> cellKeys) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getCellValues(fcId, numDims, hierIds, cellKeys) : this.getLocal().getCellValues(fcId, numDims, hierIds, cellKeys);
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public EntityList getOACellValues(int company, List<String[]> cellKeys) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getOACellValues(company, cellKeys) : this.getLocal().getOACellValues(company, cellKeys);
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

    public EntityList getCurrencyCellValues(List<String[]> cellKeys) throws ValidationException {
        try {
            return this.isRemoteConnection() ? this.getRemote().getCurrencyCellValues(cellKeys) : this.getLocal().getCurrencyCellValues(cellKeys);
        } catch (Exception var6) {
            throw this.unravelException(var6);
        }
    }
    
    public EntityList getParameterCellValues(List<String[]> cellKeys) throws ValidationException {
        try {
            return this.isRemoteConnection() ? this.getRemote().getParameterCellValues(cellKeys) : this.getLocal().getParameterCellValues(cellKeys);
        } catch (Exception var6) {
            throw this.unravelException(var6);
        }
    }
    
    public EntityList getAuctionCellValues(List<String[]> cellKeys) throws ValidationException {
        try {
            return this.isRemoteConnection() ? this.getRemote().getAuctionCellValues(cellKeys) : this.getLocal().getAuctionCellValues(cellKeys);
        } catch (Exception var6) {
            throw this.unravelException(var6);
        }
    }


	public ExtractDataDTO getExtractData(ExtractDataDTO extractDataDTO) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getExtractData(extractDataDTO) : this.getLocal().getExtractData(extractDataDTO);
		} catch (Exception var3) {
			throw this.unravelException(var3);
		}
	}

	public CellContents getFinanceCubeCell(String modelVisId, int financeCubeId, boolean ytd, int[] structureIds, String[] elementVisIds, String dataType) throws ValidationException {
		CellContents result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getFinanceCubeCell(modelVisId, financeCubeId, ytd, structureIds, elementVisIds, dataType);
			} else {
				result = this.getLocal().getFinanceCubeCell(modelVisId, financeCubeId, ytd, structureIds, elementVisIds, dataType);
			}

			return result;
		} catch (Exception var9) {
			throw this.unravelException(var9);
		}
	}

	public EntityList getCellNote(int financeCubeId, String cellPk, int userId) throws ValidationException {
		EntityList result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getCellNote(financeCubeId, cellPk, userId);
			} else {
				result = this.getLocal().getCellNote(financeCubeId, cellPk, userId);
			}

			return result;
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public EntityList getAllNotes(int financeCubeId, int userId) throws ValidationException {
		EntityList result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getAllNotes(financeCubeId, userId);
			} else {
				result = this.getLocal().getAllNotes(financeCubeId, userId);
			}

			return result;
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public EntityList getCellNote(int financeCubeId, String cellPk, int userId, String company) throws ValidationException {
		EntityList result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getCellNote(financeCubeId, cellPk, userId, company);
			} else {
				result = this.getLocal().getCellNote(financeCubeId, cellPk, userId, company);
			}

			return result;
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public EntityList getCellAudit(int financeCubeId, String cellPk, int userId) throws ValidationException {
		EntityList result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getCellAudit(financeCubeId, cellPk, userId);
			} else {
				result = this.getLocal().getCellAudit(financeCubeId, cellPk, userId);
			}

			return result;
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public EntityList queryExtSysInfoForCube(int financeCubeId) throws ValidationException {
		Object result = null;

		try {
			return this.isRemoteConnection() ? this.getRemote().queryExtSysInfoForCube(financeCubeId) : this.getLocal().queryExtSysInfoForCube(financeCubeId);
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public ExtSysTransactionQueryParams queryExtSysParams(int financeCubeId, String cellPk, String ytd) {
		try {
			return this.isRemoteConnection() ? this.getRemote().queryExtSysParams(financeCubeId, cellPk, ytd) : this.getLocal().queryExtSysParams(financeCubeId, cellPk, ytd);
		} catch (Exception var5) {
			throw this.unravelFatalException(var5);
		}
	}

	public FinanceSystemCellData getFinanceSystemCellData(int financeCubeId, String cellPk, String ytd, int userId, int cmpy) throws ValidationException {
		Object result = null;

		try {
			return this.isRemoteConnection() ? this.getRemote().getFinanceSystemCellData(financeCubeId, cellPk, ytd, userId, cmpy) : this.getLocal().getFinanceSystemCellData(financeCubeId, cellPk, ytd, userId, cmpy);
		} catch (Exception var7) {
			throw this.unravelException(var7);
		}
	}

	public EntityList getSheetNote(int formId, int elemId) throws ValidationException {
		EntityList result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getSheetNote(formId, elemId);
			} else {
				result = this.getLocal().getSheetNote(formId, elemId);
			}

			return result;
		} catch (Exception var5) {
			throw this.unravelException(var5);
		}
	}

	public boolean getXMLFormSecurityAccessFlag(int formId) throws ValidationException {
		boolean result = false;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getXMLFormSecurityAccessFlag(formId);
			} else {
				result = this.getLocal().getXMLFormSecurityAccessFlag(formId);
			}

			return result;
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public int getXMLFormIdFromCellCalcId(int financeCubeId, int cellCalcId) throws ValidationException {
		boolean result = false;

		try {
			int result1;
			if (this.isRemoteConnection()) {
				result1 = this.getRemote().getXMLFormIdFromCellCalcId(financeCubeId, cellCalcId);
			} else {
				result1 = this.getLocal().getXMLFormIdFromCellCalcId(financeCubeId, cellCalcId);
			}

			return result1;
		} catch (Exception var5) {
			throw this.unravelException(var5);
		}
	}

	public CellCalculationDetails getCellCalculationDetails(int financeCubeId, int formDefinitionId, Map contextVariables, int cellCalcShortId) throws ValidationException {
		CellCalculationDetails result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getCellCalculationDetails(financeCubeId, formDefinitionId, contextVariables, cellCalcShortId);
			} else {
				result = this.getLocal().getCellCalculationDetails(financeCubeId, formDefinitionId, contextVariables, cellCalcShortId);
			}

			return result;
		} catch (Exception var7) {
			throw this.unravelException(var7);
		}
	}

	public List<Object> getCellCalculationContext(int financeCubeId, int cellCalcShortId) throws ValidationException {
		List result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getCellCalculationContext(financeCubeId, cellCalcShortId);
			} else {
				result = this.getLocal().getCellCalculationContext(financeCubeId, cellCalcShortId);
			}

			return result;
		} catch (Exception var5) {
			throw this.unravelException(var5);
		}
	}

	public List<Integer> getCellCalcShortIdsForRA(int financeCubeId, int cellCalcDeploymentId, int budgetLocation) throws ValidationException {
		List result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getCellCalcShortIdsForRA(financeCubeId, cellCalcDeploymentId, budgetLocation);
			} else {
				result = this.getLocal().getCellCalcShortIdsForRA(financeCubeId, cellCalcDeploymentId, budgetLocation);
			}

			return result;
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public List<Integer> getCellCalcShortIdsForRA(int financeCubeId, int cellCalcDeploymentId, int budgetLocation, CalendarElementNode rootCalNode) throws ValidationException {
		List result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getCellCalcShortIdsForRA(financeCubeId, cellCalcDeploymentId, budgetLocation, rootCalNode);
			} else {
				result = this.getLocal().getCellCalcShortIdsForRA(financeCubeId, cellCalcDeploymentId, budgetLocation, rootCalNode);
			}

			return result;
		} catch (Exception var7) {
			throw this.unravelException(var7);
		}
	}

	public FormConfig getXMLFormConfig(Map contextVariables, int formId) throws ValidationException {
		FormConfig result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getXMLFormConfig(contextVariables, formId);
			} else {
				result = this.getLocal().getXMLFormConfig(contextVariables, formId);
			}

			return result;
		} catch (Exception var5) {
			throw this.unravelException(var5);
		}
	}

	public FormDataInputModel getCalculationPreviousData(FormConfig formConfig, int financeCubeId, int cellCalcShortId) throws ValidationException {
		FormDataInputModel result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getCalculationPreviousData(formConfig, financeCubeId, cellCalcShortId);
			} else {
				result = this.getLocal().getCalculationPreviousData(formConfig, financeCubeId, cellCalcShortId);
			}

			return result;
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public Map getCalculationSummaryData(FormConfig formConfig, int financeCubeId, int cellCalcShortId) throws ValidationException {
		Map result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getCalculationSummaryData(formConfig, financeCubeId, cellCalcShortId);
			} else {
				result = this.getLocal().getCalculationSummaryData(formConfig, financeCubeId, cellCalcShortId);
			}

			return result;
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public EntityList getImmediateChildren(int userId, Object primaryKey) throws ValidationException {
		EntityList result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getImmediateChildren(userId, primaryKey);
			} else {
				result = this.getLocal().getImmediateChildren(userId, primaryKey);
			}

			return result;
		} catch (Exception var5) {
			throw this.unravelException(var5);
		}
	}

    public EntityList getImmediateChildrenLimitedByPermission(int dimensionType, int userId, int budgetCycleId, Object primaryKey) throws ValidationException {
        EntityList result = null;

        try {
            if (this.isRemoteConnection()) {
                result = this.getRemote().getImmediateChildrenLimitedByPermission(dimensionType, userId, budgetCycleId, primaryKey);
            } else {
                result = this.getLocal().getImmediateChildrenLimitedByPermission(dimensionType, userId, budgetCycleId, primaryKey);
            }

            return result;
        } catch (Exception e) {
            throw this.unravelException(e);
        }
    }

	public EntityList getElementsForConstraints(boolean raDimension, boolean calDimension, int userId, String financeCubeVisId, int hierarchyId, List constraints) throws ValidationException {
		EntityList result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getElementsForConstraints(raDimension, calDimension, userId, financeCubeVisId, hierarchyId, constraints);
			} else {
				result = this.getLocal().getElementsForConstraints(raDimension, calDimension, userId, financeCubeVisId, hierarchyId, constraints);
			}

			return result;
		} catch (Exception var9) {
			throw this.unravelException(var9);
		}
	}

	public int issueMassUpdate(int userId, MassUpdateParameters mup) throws ValidationException {
		boolean taskId = false;

		try {
			int taskId1;
			if (this.isRemoteConnection()) {
				taskId1 = this.getRemote().issueMassUpdate(userId, mup);
			} else {
				taskId1 = this.getLocal().issueMassUpdate(userId, mup);
			}

			return taskId1;
		} catch (Exception var5) {
			throw this.unravelException(var5);
		}
	}

	public String validateMassUpdate(int financeCubeId, int srcCalElem, int targetCalElem) throws ValidationException {
		try {
			String result;
			if (this.isRemoteConnection()) {
				result = this.getRemote().validateMassUpdate(financeCubeId, srcCalElem, targetCalElem);
			} else {
				result = this.getLocal().validateMassUpdate(financeCubeId, srcCalElem, targetCalElem);
			}

			return result;
		} catch (Exception var6) {
			throw this.unravelException(var6);
		}
	}

	public int issueRecharge(int intUserId, int financeCubeId, int calanderStructureElementId, boolean report, boolean source, boolean target, int[] rechargeIds) throws ValidationException {
		boolean taskId = false;

		try {
			int taskId1;
			if (this.isRemoteConnection()) {
				taskId1 = this.getRemote().issueRecharge(intUserId, financeCubeId, calanderStructureElementId, report, source, target, rechargeIds);
			} else {
				taskId1 = this.getLocal().issueRecharge(intUserId, financeCubeId, calanderStructureElementId, report, source, target, rechargeIds);
			}

			return taskId1;
		} catch (Exception var10) {
			throw this.unravelException(var10);
		}
	}

	public CalendarInfo getCalendarInfoForModel(String modelVisId) throws ValidationException {
		CalendarInfo calInfo = null;

		try {
			if (this.isRemoteConnection()) {
				calInfo = this.getRemote().getCalendarInfoForModel(modelVisId);
			} else {
				calInfo = this.getLocal().getCalendarInfoForModel(modelVisId);
			}

			return calInfo;
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public int getCalYearMonthId(String visId, String calVisIdPrefix, int dimId) throws ValidationException {
		int result = 0;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getCalYearMonthId(visId, calVisIdPrefix, dimId);
			} else {
				result = this.getLocal().getCalYearMonthId(visId, calVisIdPrefix, dimId);
			}

			return result;
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public List<Map<String, String>> getStructureElements(List<Integer> dimsList) throws ValidationException {
		List<Map<String, String>> result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getStructureElements(dimsList);
			} else {
				result = this.getLocal().getStructureElements(dimsList);
			}

			return result;
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public Map<String, String> getCalendarRangeDetails(int id) throws ValidationException {
		Map<String, String> result = null;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getCalendarRangeDetails(id);
			} else {
				result = this.getLocal().getCalendarRangeDetails(id);
			}

			return result;
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public String getRemoteJNDIName() {
		return "ejb/DataEntryRemoteHome";
	}

	public String getLocalJNDIName() {
		return "ejb/DataEntryLocalHome";
	}

	public String[] getPropertiesForInvoice() {
		String[] properties = null;

		try {
			if (this.isRemoteConnection()) {
				properties = this.getRemote().getPropertiesForInvoice();
			} else {
				properties = this.getLocal().getPropertiesForInvoice();
			}
		} catch (Exception var4) {
		}
		return properties;
	}
	
	public boolean getUserReadOnlyAccess(int modelId, int structureElementId) throws ValidationException {
		boolean result;

		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getUserReadOnlyAccess(modelId, structureElementId, getConnection().getUserContext().getUserId());
			} else {
				result = this.getLocal().getUserReadOnlyAccess(modelId, structureElementId, getConnection().getUserContext().getUserId());
			}

			return result;
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public EntityList getLastNotes(int financeCubeId) throws ValidationException {
		EntityList result = null;
		try {
			if (this.isRemoteConnection()) {
				result = this.getRemote().getLastNotes(financeCubeId);
			} else {
				result = this.getLocal().getLastNotes(financeCubeId);
			}

			return result;
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}

	public void saveCellNoteChange(Object cellNote, int financeCubeId, int budgetCycleId, String methodType) throws ValidationException {
		try {
			if (this.isRemoteConnection()) {
				this.getRemote().saveCellNoteChange(cellNote, financeCubeId, getConnection().getUserContext().getUserId(), budgetCycleId, methodType);
			} else {
				this.getLocal().saveCellNoteChange(cellNote, financeCubeId, getConnection().getUserContext().getUserId(), budgetCycleId, methodType);
			}
		} catch (Exception e) {
			throw this.unravelException(e);
		}
	}
}
