// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dataEntry;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dataEntry.CellCalculationDetails;
import com.cedar.cp.api.dataEntry.CellContents;
import com.cedar.cp.api.dataEntry.DataEntryProcess;
import com.cedar.cp.api.dataEntry.DataExportParameters;
import com.cedar.cp.api.dataEntry.DataExtract;
import com.cedar.cp.api.dataEntry.FinanceSystemCellData;
import com.cedar.cp.api.dataEntry.MassUpdateParameters;
import com.cedar.cp.api.facades.ExtractDataDTO;
import com.cedar.cp.dto.dataEntry.DataExportParametersImpl;
import com.cedar.cp.dto.datatype.DataTypeNodeImpl;
import com.cedar.cp.dto.dimension.StructureElementNodeImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.extsys.ExtSysTransactionQueryParams;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.ejb.api.dataentry.DataEntryServer;
import com.cedar.cp.ejb.api.model.CubeUpdateServer;
import com.cedar.cp.ejb.impl.dataentry.DataEntrySEJB;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.extsys.ExtSysConnectorManagerImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.OnDemandMutableTreeNode;
import com.cedar.cp.util.performance.GenericPerformanceType;
import com.cedar.cp.util.performance.PerformanceDatumImpl;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.inputs.FormDataInputModel;

import cppro.utils.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;

public class DataEntryProcessImpl extends BusinessProcessImpl implements DataEntryProcess {

	private Log mLog = new Log(this.getClass());
	public static DataEntrySEJB server = new DataEntrySEJB();
	public DataEntryProcessImpl(CPConnection connection) {
		super(connection);
	}

	protected int getProcessID() {
		return 53;
	}

	public void executeForegroundCubeUpdate(String xmlUpdate) throws ValidationException, CPException {
		ArrayList ids = new ArrayList();
		String cubeUpdate = "Cube Update";
		ids.add(cubeUpdate);
		PerformanceDatumImpl perf = GenericPerformanceType.getInstance("ForegroundCubeUpdate", ids, "Foreground cube update").createPerformanceDatum("");

		try {
			CubeUpdateServer server = new CubeUpdateServer(this.getConnection());
			if (this.mLog.isDebugEnabled()) {
				this.mLog.debug("executeForegroundCubeUpdate", xmlUpdate);
			}

			server.executeCubeUpdate(xmlUpdate);
		} finally {
			perf.setDatumPoint(cubeUpdate);
			perf.completed();
		}

	}

	public void executeForegroundFlatFormUpdate(String xmlUpdate) throws ValidationException, CPException {
		ArrayList ids = new ArrayList();
		String cubeUpdate = "Flat Form Cube Update";
		ids.add(cubeUpdate);
		PerformanceDatumImpl perf = GenericPerformanceType.getInstance("ForegroundFlatFormUpdate", ids, "Foreground flat form cube update").createPerformanceDatum("");

		try {
			CubeUpdateServer server = new CubeUpdateServer(this.getConnection());
			if (this.mLog.isDebugEnabled()) {
				this.mLog.debug("executeForegroundCubeUpdate", xmlUpdate);
			}

			server.executeFlatFormUpdate(xmlUpdate);
		} finally {
			perf.setDatumPoint(cubeUpdate);
			perf.completed();
		}

	}

	public DataExportParameters getDataExportParameters(EntityRef financeCubeRef) throws CPException {
		FinanceCubeCK fck = (FinanceCubeCK) financeCubeRef.getPrimaryKey();
		FinanceCubePK fpk = fck.getFinanceCubePK();
		int financeCubeId = fpk.getFinanceCubeId();
		EntityList list = this.getConnection().getListHelper().getFinanceCubeDetails(financeCubeId);
		int modelId = ((Integer) list.getValueAt(0, "ModelId")).intValue();
		DataExportParametersImpl result = new DataExportParametersImpl(fpk.getFinanceCubeId());
		result.setModelId(modelId);
		result.setModels(this.getCellPickerModel(modelId, financeCubeId));
		return result;
	}

	public boolean doesCellMatchChartOfAccounts(int modelId, int financeCubeId, int[] cellReference, String[] cellVisIds, int userId, int budgetCycleId) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.doesCellMatchChartOfAccounts(modelId, financeCubeId, cellReference, cellVisIds, userId, budgetCycleId);
	}

	public TreeModel[] getCellPickerModel(int modelId, int financeCubeId) {
		TreeModel[] models = new TreeModel[0];
		ArrayList values = new ArrayList();
		values.addAll(this.getStructureElementModel(modelId));
		values.addAll(this.getDataTypeModel(financeCubeId));
		models = (TreeModel[]) ((TreeModel[]) values.toArray(models));
		return models;
	}

	public List getStructureElementModel(int modelId) {
		new ArrayList();
		CPConnection conn = this.getConnection();
		int[] types = new int[] { 1, 2, 3 };
		EntityList list = conn.getListHelper().getTreeInfoForModelDimTypes(modelId, types);
		List values = this.processTreeModels(list);
		return values;
	}

	public List getDataTypeModel(int financCubeId) {
		ArrayList values = new ArrayList();
		CPConnection conn = this.getConnection();
		EntityList list = conn.getListHelper().getPickerDataTypesWeb(financCubeId, new int[] { 0, 4 }, false);
		int size = list.getNumRows();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("All Data Types");
		HashMap subTypes = new HashMap();
		int subType = 0;

		for (int i = 0; i < size; ++i) {
			DataTypeNodeImpl node = new DataTypeNodeImpl(list.getRowData(i));
			if (i == 0 || subType != node.getSubType()) {
				subType = node.getSubType();
				subTypes.put(Integer.valueOf(subType), this.getSubTypeNode(subType));
				root.add((MutableTreeNode) subTypes.get(Integer.valueOf(subType)));
			}

			DefaultMutableTreeNode child = new DefaultMutableTreeNode(node);
			((DefaultMutableTreeNode) subTypes.get(Integer.valueOf(subType))).add(child);
		}

		values.add(new DefaultTreeModel(root));
		return values;
	}

	private DefaultMutableTreeNode getSubTypeNode(int subType) {
		switch (subType) {
		case 1:
			return new DefaultMutableTreeNode("Temp Virement");
		case 2:
			return new DefaultMutableTreeNode("Perm Virement");
		case 3:
			return new DefaultMutableTreeNode("Virtual");
		case 4:
			return new DefaultMutableTreeNode("Measure");
		default:
			return new DefaultMutableTreeNode("Financial Value");
		}
	}

	private List processTreeModels(EntityList list) {
		int size = list.getNumRows();
		ArrayList values = new ArrayList(size);
		DefaultMutableTreeNode root = null;

		for (int i = 0; i < size; ++i) {
			root = new DefaultMutableTreeNode(list.getValueAt(i, "VisId"));
			EntityList hierList = (EntityList) list.getValueAt(i, "Hierarchy");
			int hierSize = hierList.getNumRows();

			for (int model = 0; model < hierSize; ++model) {
				DefaultMutableTreeNode hier = new DefaultMutableTreeNode(hierList.getValueAt(model, "HierarchyVisId"));
				EntityList elementList = (EntityList) hierList.getValueAt(model, "StructureElement");
				int elementsize = elementList.getNumRows();

				for (int k = 0; k < elementsize; ++k) {
					StructureElementNodeImpl node = new StructureElementNodeImpl(this.getConnection(), elementList, "RechargeAdapter");
					OnDemandMutableTreeNode elem = new OnDemandMutableTreeNode(node, "com.cedar.cp.impl.dimension.StructureElementProxyNode");
					hier.add(elem);
				}

				root.add(hier);
			}

			DefaultTreeModel var15 = new DefaultTreeModel(root);
			values.add(var15);
		}

		return values;
	}

	public DataExtract dataImport(DataExtract dataExtract) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.dataImport(dataExtract);
	}

	public DataExtract dataImportPrep(Object key) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.dataImportPrep(key);
	}

	public DataExtract getDataExtract(EntityRef financeCubeRef, List buisnessDims, List accountIds, List periodIds, List dataTypes, boolean fullIntersection, boolean count) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getDataExtract(financeCubeRef, buisnessDims, accountIds, periodIds, dataTypes, fullIntersection, count);
	}

	public List getFinanceCubeDataSlice(String modelVisId, String fcVisId, String aggregationRule, int[] rowHeadings, int[] columnHeadings, int[] filterHeadings, List rows, List columns, List filters) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getFinanceCubeDataSlice(modelVisId, fcVisId, aggregationRule, rowHeadings, columnHeadings, filterHeadings, rows, columns, filters);
	}

	public Map getFinanceCubeDataForExtract(int fcId, int[] structureElementIds, String dataType) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getFinanceCubeDataForExtract(fcId, structureElementIds, dataType);
	}

	public Object getSingleCellValue(int fcId, int[] structureElementIds, String dataType) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getSingleCellValue(fcId, structureElementIds, dataType);
	}

	public CellContents getFinanceCubeCell(String modelVisId, String financeCubeVisId, boolean ytd, String[] structureVisIds, String[] elementVisIds, String dataType) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getFinanceCubeCell(modelVisId, financeCubeVisId, ytd, structureVisIds, elementVisIds, dataType);
	}

	public CellContents getFinanceCubeCell(String modelVisId, int financeCubeId, boolean ytd, int[] structureIds, String[] elementVisIds, String dataType) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getFinanceCubeCell(modelVisId, financeCubeId, ytd, structureIds, elementVisIds, dataType);
	}

	public EntityList getCellValues(int fcId, int numDims, String[] structureVisIds, List<String[]> cellKeys, String company) throws ValidationException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getCellValues(fcId, numDims, structureVisIds, cellKeys, company);
	}

	public EntityList getCellValues(int fcId, int numDims, int[] hierIds, List<String[]> cellKeys, String company) throws ValidationException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getCellValues(fcId, numDims, hierIds, cellKeys, company);
	}

	public EntityList getCellValues(int fcId, int numDims, String[] structureVisIds, List<String[]> cellKeys) throws ValidationException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getCellValues(fcId, numDims, structureVisIds, cellKeys);
	}

	public EntityList getCellValues(int fcId, int numDims, int[] hierIds, List<String[]> cellKeys) throws ValidationException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getCellValues(fcId, numDims, hierIds, cellKeys);
	}

	public EntityList getOACellValues(int company, List<String[]> cellKeys) throws ValidationException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getOACellValues(company, cellKeys);
	}

    public EntityList getCurrencyCellValues(List<String[]> cellKeys) throws ValidationException {
        //DataEntryServer server = new DataEntryServer(this.getConnection());
        return server.getCurrencyCellValues(cellKeys);
    }
    
    public EntityList getParameterCellValues(List<String[]> cellKeys) throws ValidationException {
//        DataEntryServer server = new DataEntryServer(this.getConnection());
//    	DataEntrySEJB server = new DataEntrySEJB();
        return server.getParameterCellValues(cellKeys);
    }
    
    public EntityList getAuctionCellValues(List<String[]> cellKeys) throws ValidationException {
        //DataEntryServer server = new DataEntryServer(this.getConnection());
        return server.getAuctionCellValues(cellKeys);
    }

	public ExtractDataDTO getExtractData(ExtractDataDTO extractDataDTO) throws ValidationException {
//		DataEntryServer server = new DataEntryServer(this.getConnection());
//		DataEntrySEJB server = new DataEntrySEJB();
		return server.getExtractData(extractDataDTO);
	}

	public List getAllStructureElementsForFinanceCube(int userId, String modelVisId, String fcVisId) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getAllStructureElementsForFinanceCube(userId, modelVisId, fcVisId);
	}

	public EntityList getCellNote(int financeCubeId, String cellPk, int userId) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getCellNote(financeCubeId, cellPk, userId);
	}

	public EntityList getAllNotes(int financeCubeId, int userId) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getAllNotes(financeCubeId, userId);
	}
	
	public EntityList getLastNotes(int financeCubeId) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getLastNotes(financeCubeId);
	}

	public EntityList getCellNote(int financeCubeId, String cellPk, int userId, String company) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getCellNote(financeCubeId, cellPk, userId, company);
	}

	public EntityList getCellAudit(int financeCubeId, String cellPk, int userId) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getCellAudit(financeCubeId, cellPk, userId);
	}

	public FinanceSystemCellData getFinanceSystemCellData(Integer cubeId, String modelVisId, String cubeVisId, String cellPk, String ytd, int userId, int cmpy) throws ValidationException, CPException {
		DataEntryServer server = new DataEntryServer(getConnection());
		int financeCubeId;
		if (cubeId == null)
			financeCubeId = server.getFinanceCubeIdFromVisId(modelVisId, cubeVisId);
		else {
			financeCubeId = cubeId.intValue();
		}

		EntityList extSysInfo = server.queryExtSysInfoForCube(financeCubeId);

		int externalSystemType = ((Integer) extSysInfo.getValueAt(0, "systemType")).intValue();

		if (externalSystemType == 20) {
			ExtSysTransactionQueryParams params = server.queryExtSysParams(financeCubeId, cellPk, ytd);
			String connectorClass = (String) extSysInfo.getValueAt(0, "connectorClass");
			int externalSystemId = ((Integer) extSysInfo.getValueAt(0, "externalSystemId")).intValue();

			return ExtSysConnectorManagerImpl.getInstance().getFinanceSystemCellData(externalSystemId, params, connectorClass);
		}

		return server.getFinanceSystemCellData(financeCubeId, cellPk, ytd, userId, cmpy);
	}
	
	public String[] getPropertiesForInvoice() {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getPropertiesForInvoice();
	}
	
	public EntityList getSheetNote(int formId, int sheetNoteId) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getSheetNote(formId, sheetNoteId);
	}

	public boolean getXMLFormSecurityAccessFlag(int formId) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getXMLFormSecurityAccessFlag(formId);
	}

	public int getXMLFormIdFromCellCalcId(int financeCubeId, int cellCalcShortId) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getXMLFormIdFromCellCalcId(financeCubeId, cellCalcShortId);
	}

	public CellCalculationDetails getCellCalculationDetails(int financeCubeId, int formDefinitionId, Map contextVariables, int cellCalcShortId) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getCellCalculationDetails(financeCubeId, formDefinitionId, contextVariables, cellCalcShortId);
	}

	public List<Object> getCellCalculationContext(int financeCubeId, int cellCalcShortId) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getCellCalculationContext(financeCubeId, cellCalcShortId);
	}

	public List<Integer> getCellCalcShortIdsForRA(int financeCubeId, int cellCalcDeploymentId, int budgetLocation) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getCellCalcShortIdsForRA(financeCubeId, cellCalcDeploymentId, budgetLocation);
	}

	public List<Integer> getCellCalcShortIdsForRA(int financeCubeId, int cellCalcDeploymentId, int budgetLocation, CalendarElementNode rootCalNode) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getCellCalcShortIdsForRA(financeCubeId, cellCalcDeploymentId, budgetLocation, rootCalNode);
	}

	public FormConfig getXMLFormConfig(Map contextVariables, int formId) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getXMLFormConfig(contextVariables, formId);
	}

	public FormDataInputModel getCalculationPreviousData(FormConfig formConfig, int financeCubeId, int cellCalcShortId) throws ValidationException, CPException {
		FormDataInputModel result = null;
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		result = server.getCalculationPreviousData(formConfig, financeCubeId, cellCalcShortId);
		return result;
	}

	public Map getCalculationSummaryData(FormConfig formConfig, int financeCubeId, int cellCalcShortId) throws ValidationException, CPException {
		Map result = null;
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		result = server.getCalculationSummaryData(formConfig, financeCubeId, cellCalcShortId);
		return result;
	}

	public EntityList getImmediateChildren(int userId, Object primaryKey) throws ValidationException, CPException {
		EntityList result = null;
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		result = server.getImmediateChildren(userId, primaryKey);
		return result;
	}
	
    public EntityList getImmediateChildrenLimitedByPermission(int dimensionType, int userId, int budgetCycleId, Object primaryKey) throws ValidationException, CPException {
        EntityList result = null;
        //DataEntryServer server = new DataEntryServer(this.getConnection());
        result = server.getImmediateChildrenLimitedByPermission(dimensionType, userId, budgetCycleId, primaryKey);
        return result;
    }

	public EntityList getElementsForConstraints(boolean raDimension, boolean calDimension, int userId, String financeCubeVisId, int hierarchyId, List constraints) throws ValidationException, CPException {
		EntityList result = null;
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		result = server.getElementsForConstraints(raDimension, calDimension, userId, financeCubeVisId, hierarchyId, constraints);
		return result;
	}

	public int issueMassUpdate(int userId, MassUpdateParameters mup) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		int task = server.issueMassUpdate(userId, mup);
		return task;
	}

	public String validateMassUpdate(int financeCubeId, int srcCalElem, int targetCalElem) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		String result = server.validateMassUpdate(financeCubeId, srcCalElem, targetCalElem);
		return result;
	}

	public int issueRecharge(int intUserId, int financeCubeId, int calanderStructureElementId, boolean report, boolean source, boolean target, int[] rechargeIds) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		int task = server.issueRecharge(intUserId, financeCubeId, calanderStructureElementId, report, source, target, rechargeIds);
		return task;
	}

	public int issueRecharge(int intUserId, int financeCubeId, EntityRef calanderStructureElementId, boolean report, boolean source, boolean target, int[] rechargeIds) throws ValidationException, CPException {
		StructureElementPK pk = (StructureElementPK) calanderStructureElementId.getPrimaryKey();
		int calId = pk.getStructureElementId();
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		int task = server.issueRecharge(intUserId, financeCubeId, calId, report, source, target, rechargeIds);
		return task;
	}

	public CalendarInfo getCalendarInfoForModel(String modelVisId) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getCalendarInfoForModel(modelVisId);
	}

	public int getCalYearMonthId(String visId, String calVisIdPrefix, int dimId) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getCalYearMonthId(visId, calVisIdPrefix, dimId);
	}

	public List<Map<String, String>> getStructureElements(List<Integer> dimsList) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getStructureElements(dimsList);
	}

	public Map<String, String> getGetCalendarRangeDetails(int id) throws ValidationException, CPException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getCalendarRangeDetails(id);
	}

	public boolean getUserReadOnlyAccess(int modelId, int structureElementId) throws ValidationException {
		//DataEntryServer server = new DataEntryServer(this.getConnection());
		return server.getUserReadOnlyAccess(modelId, structureElementId,DBUtils.userId);
	}
}
