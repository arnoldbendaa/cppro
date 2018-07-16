// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.api.model.ChangeBudgetStateResult;
import com.cedar.cp.api.model.OnDemandFinanceFormData;
import com.cedar.cp.api.model.ReviewBudgetDetails;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitCheck;
import com.cedar.cp.dto.dimension.StructureElementELO;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.model.BudgetCycleHelperHome;
import com.cedar.cp.ejb.api.model.BudgetCycleHelperLocal;
import com.cedar.cp.ejb.api.model.BudgetCycleHelperLocalHome;
import com.cedar.cp.ejb.api.model.BudgetCycleHelperRemote;
import com.cedar.cp.ejb.impl.model.BudgetCycleHelperSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.FinanceCubeInput;
import java.rmi.RemoteException;
import java.util.Map;
import javax.ejb.CreateException;
import javax.naming.Context;

public class BudgetCycleHelperServer extends AbstractSession {

	private static final String REMOTE_JNDI_NAME = "ejb/BudgetCycleHelperRemoteHome";
	private static final String LOCAL_JNDI_NAME = "ejb/BudgetCycleHelperLocalHome";
	protected BudgetCycleHelperRemote mRemote;
	protected BudgetCycleHelperLocal mLocal;
	BudgetCycleHelperSEJB server = new BudgetCycleHelperSEJB();
	private Log mLog = new Log(this.getClass());

	public BudgetCycleHelperServer(CPConnection conn_) {
		super(conn_);
	}

	public BudgetCycleHelperServer(Context context_, boolean remote) {
		super(context_, remote);
	}

	private BudgetCycleHelperRemote getRemote() throws CreateException, RemoteException, CPException {
		if (this.mRemote == null) {
			String jndiName = this.getRemoteJNDIName();

			try {
				BudgetCycleHelperHome e = (BudgetCycleHelperHome) this.getHome(jndiName, BudgetCycleHelperHome.class);
				this.mRemote = e.create();
			} catch (CreateException var3) {
				this.removeFromCache(jndiName);
				var3.printStackTrace();
				throw new CPException("getRemote " + jndiName + " CreateException", var3);
			} catch (RemoteException var4) {
				this.removeFromCache(jndiName);
				var4.printStackTrace();
				throw new CPException("getRemote " + jndiName + " RemoteException", var4);
			}
		}

		return this.mRemote;
	}

	private BudgetCycleHelperLocal getLocal() throws CPException {
		if (this.mLocal == null) {
			try {
				BudgetCycleHelperLocalHome e = (BudgetCycleHelperLocalHome) this.getLocalHome(this.getLocalJNDIName());
				this.mLocal = e.create();
			} catch (CreateException var2) {
				throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
			}
		}

		return this.mLocal;
	}

	public void removeSession() throws CPException {
	}

	public ReviewBudgetDetails getReviewBudgetDetails(int userId, String levelPrefix, int modelId, int budgetCycleId, Map userSelections, String dataType, int formId, Map contextVariables) throws ValidationException {
		ReviewBudgetDetails ret = null;

		try {
			if (this.isRemoteConnection()) {
				ret = this.getRemote().getReviewBudgetDetails(userId, levelPrefix, modelId, budgetCycleId, userSelections, dataType, formId, contextVariables);
			} else {
				ret = this.getLocal().getReviewBudgetDetails(userId, levelPrefix, modelId, budgetCycleId, userSelections, dataType, formId, contextVariables);
			}

			return ret;
		} catch (Exception var11) {
			throw this.unravelException(var11);
		}
	}

	public ReviewBudgetDetails getReviewBudgetDetails(int userId, String levelPrefix, int topNodeId, int modelId, int budgetCycleId, Object dataEntryProfilePrimaryKey, boolean hasDesignModeSecurity, Map selections, String dataType, Map contextVariables) throws ValidationException {
		ReviewBudgetDetails ret = null;

		try {
			if (this.isRemoteConnection()) {
				ret = this.getRemote().getReviewBudgetDetails(userId, levelPrefix, topNodeId, modelId, budgetCycleId, dataEntryProfilePrimaryKey, hasDesignModeSecurity, selections, dataType, contextVariables);
			} else {
				ret = this.getLocal().getReviewBudgetDetails(userId, levelPrefix, topNodeId, modelId, budgetCycleId, dataEntryProfilePrimaryKey, hasDesignModeSecurity, selections, dataType, contextVariables);
			}

			return ret;
		} catch (Exception var13) {
			throw this.unravelException(var13);
		}
	}

	public ReviewBudgetDetails getReviewBudgetDetails(int userId, String levelPrefix, int topNodeId, int modelId, int budgetCycleId, Object dataEntryProfilePrimaryKey, boolean hasDesignModeSecurity, Map selections, String dataType, Map contextVariables, CPConnection conn) throws ValidationException {
		ReviewBudgetDetails ret = null;

		try {
			if (this.isRemoteConnection()) {
				ret = this.getRemote().getReviewBudgetDetails(userId, levelPrefix, topNodeId, modelId, budgetCycleId, dataEntryProfilePrimaryKey, hasDesignModeSecurity, selections, dataType, contextVariables, conn);
			} else {
				ret = this.getLocal().getReviewBudgetDetails(userId, levelPrefix, topNodeId, modelId, budgetCycleId, dataEntryProfilePrimaryKey, hasDesignModeSecurity, selections, dataType, contextVariables);
			}

			return ret;
		} catch (Exception var13) {
			throw this.unravelException(var13);
		}
	}

	public OnDemandFinanceFormData getFinanceFormDataRows(int userId, int modelId, int budgetCycleId, FinanceCubeInput config, int sheetProtectionLevel, int[] treeStructureIndexes, String contextDataType, int xAxisIndex, int[] structureElementIds, int childrenDepth, boolean secondaryStructure, boolean noData, int[] relevantCellCalcs,
			Map<String, DataType> dataTypes, Map<Integer, EntityList> securityAccessDetails, CalendarInfo calInfo) throws ValidationException {
		OnDemandFinanceFormData model = null;

		try {
			if (this.isRemoteConnection()) {
				model = this.getRemote().getFinanceFormDataRows(userId, modelId, budgetCycleId, config, sheetProtectionLevel, treeStructureIndexes, contextDataType, xAxisIndex, structureElementIds, childrenDepth, secondaryStructure, noData, relevantCellCalcs, dataTypes, securityAccessDetails, calInfo);
			} else {
				model = this.getLocal().getFinanceFormDataRows(userId, modelId, budgetCycleId, config, sheetProtectionLevel, treeStructureIndexes, contextDataType, xAxisIndex, structureElementIds, childrenDepth, secondaryStructure, noData, relevantCellCalcs, dataTypes, securityAccessDetails, calInfo);
			}

			return model;
		} catch (Exception var19) {
			throw this.unravelException(var19);
		}
	}

	public ChangeBudgetStateResult setBudgetState(int modelId, int userId, int budgetCycleId, int structureElementId, int state, BudgetLimitCheck check, Integer childStateFrom, Integer childStateTo) throws ValidationException {
		try {
//			return this.isRemoteConnection() ? this.getRemote().setBudgetState(modelId, userId, budgetCycleId, structureElementId, state, check, childStateFrom, childStateTo) : this.getLocal().setBudgetState(modelId, userId, budgetCycleId, structureElementId, state, check, childStateFrom, childStateTo);
			return server.setBudgetState(modelId, userId, budgetCycleId, structureElementId, state, check, childStateFrom, childStateTo);
		} catch (Exception var10) {
			throw this.unravelException(var10);
		}
	}

	public void updateDataEntryProfileHistory(int userId, int modelId, int budgetCycleId, int structureElementId) throws ValidationException {
		try {
			if (this.isRemoteConnection()) {
				this.getRemote().updateDataEntryProfileHistory(userId, modelId, budgetCycleId, structureElementId);
			} else {
				this.getLocal().updateDataEntryProfileHistory(userId, modelId, budgetCycleId, structureElementId);
			}
		} catch (Exception var10) {
			throw this.unravelException(var10);
		}
	}

	public EntityList getCycleStateHistory(int bcId, int structure_id, int structure_elementid) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().getCycleStateHistory(bcId, structure_id, structure_elementid) : this.getLocal().getCycleStateHistory(bcId, structure_id, structure_elementid);
		} catch (Exception var5) {
			throw this.unravelException(var5);
		}
	}

	public int issueBudgetStateTask(int daysBefore, int userId) throws ValidationException {
		return this.issueBudgetStateTask(daysBefore, userId, 0);
	}

	public int issueBudgetStateTask(int daysBefore, int userId, int issuingTaskId) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().issueBudgetStateTask(daysBefore, userId, issuingTaskId) : this.getLocal().issueBudgetStateTask(daysBefore, userId, issuingTaskId);
		} catch (Exception var5) {
			throw this.unravelException(var5);
		}
	}

	public int issueBudgetStateRebuildTask(EntityRef ref, int userId) throws ValidationException {
		try {
			return this.isRemoteConnection() ? this.getRemote().issueBudgetStateRebuildTask(ref, userId) : this.getLocal().issueBudgetStateRebuildTask(ref, userId);
		} catch (Exception var4) {
			throw this.unravelException(var4);
		}
	}

	public StructureElementELO[] getCalendarDetailsLabels(int[] structureElementIds) throws ValidationException {
		StructureElementELO[] model = null;

		try {
			if (this.isRemoteConnection()) {
				model = this.getRemote().getCalendarDetailsLabels(structureElementIds);
			} else {
				model = this.getLocal().getCalendarDetailsLabels(structureElementIds);
			}

			return model;
		} catch (Exception var19) {
			throw this.unravelException(var19);
		}
	}
	
	public void updatePeriods(int bcId, int periodId, int fromTo) throws ValidationException {
		try {
			if (this.isRemoteConnection()) {
				this.getRemote().updatePeriods(bcId, periodId, fromTo);
			} else {
				this.getLocal().updatePeriods(bcId, periodId, fromTo);
			}
		} catch (Exception var) {
			throw this.unravelException(var);
		}
	}

	public String getRemoteJNDIName() {
		return "ejb/BudgetCycleHelperRemoteHome";
	}

	public String getLocalJNDIName() {
		return "ejb/BudgetCycleHelperLocalHome";
	}
}
