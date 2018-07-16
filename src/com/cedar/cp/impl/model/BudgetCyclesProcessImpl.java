// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.api.model.BudgetCycleEditorSession;
import com.cedar.cp.api.model.BudgetCyclesProcess;
import com.cedar.cp.api.model.ChangeBudgetStateResult;
import com.cedar.cp.api.model.OnDemandFinanceFormData;
import com.cedar.cp.api.model.ReviewBudgetDetails;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitCheck;
import com.cedar.cp.dto.dimension.StructureElementELO;
import com.cedar.cp.ejb.api.model.BudgetCycleEditorSessionServer;
import com.cedar.cp.ejb.api.model.BudgetCycleHelperServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.BudgetCycleEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.FinanceCubeInput;
import java.util.Map;

public class BudgetCyclesProcessImpl extends BusinessProcessImpl implements BudgetCyclesProcess {

	private Log mLog = new Log(this.getClass());

	public BudgetCyclesProcessImpl(CPConnection connection) {
		super(connection);
	}

	public void deleteObject(Object primaryKey) throws ValidationException {
		Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
		BudgetCycleEditorSessionServer es = new BudgetCycleEditorSessionServer(this.getConnection());

		try {
			es.delete(primaryKey);
		} catch (ValidationException var5) {
			throw var5;
		} catch (CPException var6) {
			throw new RuntimeException("can\'t delete " + primaryKey, var6);
		}

		if (timer != null) {
			timer.logDebug("deleteObject", primaryKey);
		}

	}

	public BudgetCycleEditorSession getBudgetCycleEditorSession(Object key) throws ValidationException {
		BudgetCycleEditorSessionImpl sess = new BudgetCycleEditorSessionImpl(this, key);
		this.mActiveSessions.add(sess);
		return sess;
	}

	public EntityList getAllBudgetCycles() {
		try {
			return this.getConnection().getListHelper().getAllBudgetCycles();
		} catch (Exception var2) {
			var2.printStackTrace();
			throw new RuntimeException("can\'t get AllBudgetCycles", var2);
		}
	}

	public EntityList getAllBudgetCyclesWeb() {
		try {
			return this.getConnection().getListHelper().getAllBudgetCyclesWeb();
		} catch (Exception var2) {
			var2.printStackTrace();
			throw new RuntimeException("can\'t get AllBudgetCyclesWeb", var2);
		}
	}

	public EntityList getAllBudgetCyclesWebForLoggedUser() {
		try {
			return this.getConnection().getListHelper().getAllBudgetCyclesForLoggedUser();
		} catch (Exception var2) {
			var2.printStackTrace();
			throw new RuntimeException("can\'t get AllBudgetCyclesForLoggedUser", var2);
		}
	}

	public EntityList getAllBudgetCyclesWebDetailed() {
		try {
			return this.getConnection().getListHelper().getAllBudgetCyclesWebDetailed();
		} catch (Exception var2) {
			var2.printStackTrace();
			throw new RuntimeException("can\'t get AllBudgetCyclesWebDetailed", var2);
		}
	}

	public EntityList getBudgetCyclesForModel(int param1) {
		try {
			return this.getConnection().getListHelper().getBudgetCyclesForModel(param1);
		} catch (Exception var3) {
			var3.printStackTrace();
			throw new RuntimeException("can\'t get BudgetCyclesForModel", var3);
		}
	}

	public EntityList getBudgetCyclesForModelWithState(int param1, int param2) {
		try {
			return this.getConnection().getListHelper().getBudgetCyclesForModelWithState(param1, param2);
		} catch (Exception var4) {
			var4.printStackTrace();
			throw new RuntimeException("can\'t get BudgetCyclesForModelWithState", var4);
		}
	}

	public EntityList getBudgetCycleIntegrity() {
		try {
			return this.getConnection().getListHelper().getBudgetCycleIntegrity();
		} catch (Exception var2) {
			var2.printStackTrace();
			throw new RuntimeException("can\'t get BudgetCycleIntegrity", var2);
		}
	}

	public EntityList getBudgetCycleDetailedForId(int param1) {
		try {
			return this.getConnection().getListHelper().getBudgetCycleDetailedForId(param1);
		} catch (Exception var3) {
			var3.printStackTrace();
			throw new RuntimeException("can\'t get BudgetCycleDetailedForId", var3);
		}
	}

	public EntityList getBudgetCycleXmlFormsForId(int param1, int userId) {
		try {
			return this.getConnection().getListHelper().getBudgetCycleXmlFormsForId(param1,userId);
		} catch (Exception var3) {
			var3.printStackTrace();
			throw new RuntimeException("can\'t get getBudgetCycleXmlFormsForId", var3);
		}
	}

	public EntityList getBudgetTransferBudgetCycles() {
		try {
			return this.getConnection().getListHelper().getBudgetTransferBudgetCycles();
		} catch (Exception var2) {
			var2.printStackTrace();
			throw new RuntimeException("can\'t get BudgetTransferBudgetCycles", var2);
		}
	}

	public String getProcessName() {
		String ret = "Processing BudgetCycle";
		return ret;
	}

	protected int getProcessID() {
		return 16;
	}

	public ReviewBudgetDetails getReviewBudgetDetails(int userId, String levelPrefix, int modelId, int budgetCycleId, Map userSelections, String dataType, int formId, Map contextVariables) throws ValidationException {
		ReviewBudgetDetails details = (new BudgetCycleHelperServer(this.getConnection())).getReviewBudgetDetails(userId, levelPrefix, modelId, budgetCycleId, userSelections, dataType, formId, contextVariables);
		return details;
	}

	public ReviewBudgetDetails getReviewBudgetDetails(int userId, String levelPrefix, int topNodeId, int modelId, int budgetCycleId, Object dataEntryProfilePrimaryKey, boolean hasDesignModeSecurity, Map selections, String dataType, Map contextVariables) throws ValidationException {
		ReviewBudgetDetails details = (new BudgetCycleHelperServer(this.getConnection())).getReviewBudgetDetails(userId, levelPrefix, topNodeId, modelId, budgetCycleId, dataEntryProfilePrimaryKey, hasDesignModeSecurity, selections, dataType, contextVariables);
		return details;
	}

	public String[] getCalendarDetailsLabels(int[] ids) throws ValidationException {
		StructureElementELO[] details = (new BudgetCycleHelperServer(this.getConnection())).getCalendarDetailsLabels(ids);
		String[] result = new String[details.length];
		for (int i = 0; i < details.length; i++) {
			StructureElementELO elo = details[i];
			if (elo.getNumRows() > 0)
				result[i] = (String) elo.getValueAt(0, "VisId");
			else
				result[i] = "-";
		}
		return result;
	}

	public OnDemandFinanceFormData getFinanceFormDataRows(int userId, int modelId, int budgetCycleId, FinanceCubeInput config, int sheetProtectionLevel, int[] treeStructureIndexes, String contextDataType, int xAxisIndex, int[] structureElementIds, int childrenDepth, boolean secondaryStructure, boolean noData, int[] relevantCellCalcs,
			Map<String, DataType> dataTypes, Map<Integer, EntityList> securityAccessDetails, CalendarInfo calInfo) throws ValidationException {
		return (new BudgetCycleHelperServer(this.getConnection())).getFinanceFormDataRows(userId, modelId, budgetCycleId, config, sheetProtectionLevel, treeStructureIndexes, contextDataType, xAxisIndex, structureElementIds, childrenDepth, secondaryStructure, noData, relevantCellCalcs, dataTypes, securityAccessDetails, calInfo);
	}

	public ChangeBudgetStateResult setBudgetState(int modelId, int userId, int budgetCycleId, int structureElementId, int state, BudgetLimitCheck check, Integer childStateFrom, Integer childStateTo) throws ValidationException {
		return (new BudgetCycleHelperServer(this.getConnection())).setBudgetState(modelId, userId, budgetCycleId, structureElementId, state, check, childStateFrom, childStateTo);
	}

	public void updateDataEntryProfileHistory(int userId, int modelId, int budgetCycleId, int structureElementId) throws ValidationException {
		(new BudgetCycleHelperServer(this.getConnection())).updateDataEntryProfileHistory(userId, modelId, budgetCycleId, structureElementId);
	}

	public EntityList getCycleStateHistory(int bcId, int structure_id, int structure_elementid) throws ValidationException {
		return (new BudgetCycleHelperServer(this.getConnection())).getCycleStateHistory(bcId, structure_id, structure_elementid);
	}

	public int issueBudgetStateTask(int daysBefore, int userId) throws ValidationException {
		return (new BudgetCycleHelperServer(this.getConnection())).issueBudgetStateTask(daysBefore, userId);
	}

	public int issueBudgetStateRebuildTask(EntityRef ref, int userId) throws ValidationException {
		return (new BudgetCycleHelperServer(this.getConnection())).issueBudgetStateRebuildTask(ref, userId);
	}

	public ReviewBudgetDetails getReviewBudgetDetails(int userId, String levelPrefix, int topNodeId, int modelId, int budgetCycleId, Object dataEntryProfilePrimaryKey, boolean hasDesignModeSecurity, Map selections, String dataType, Map contextVariables, CPConnection conn) throws ValidationException {
		ReviewBudgetDetails details = (new BudgetCycleHelperServer(this.getConnection())).getReviewBudgetDetails(userId, levelPrefix, topNodeId, modelId, budgetCycleId, dataEntryProfilePrimaryKey, hasDesignModeSecurity, selections, dataType, contextVariables, this.getConnection());
		return details;
	}
	
	public void updatePeriods(int bcId, int periodId, int fromTo) throws ValidationException {
		new BudgetCycleHelperServer(this.getConnection()).updatePeriods(bcId, periodId, fromTo);
	}
	
	public void clearBudgetCycleServerCache(Object key) throws ValidationException {
		BudgetCycleEditorSessionImpl sess = new BudgetCycleEditorSessionImpl(this, key, true);
	}

}
