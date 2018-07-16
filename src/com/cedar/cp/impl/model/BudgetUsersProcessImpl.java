package com.cedar.cp.impl.model;

import java.util.List;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.BudgetUsersProcess;
import com.cedar.cp.ejb.api.budgetlocation.BudgetLocationEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.util.Log;

public class BudgetUsersProcessImpl extends BusinessProcessImpl implements BudgetUsersProcess {

	private Log mLog = new Log(this.getClass());

	public BudgetUsersProcessImpl(CPConnection connection) {
		super(connection);
	}

	public EntityList getAllBudgetUsers() {
		try {
			return this.getConnection().getListHelper().getAllBudgetUsers();
		} catch (Exception var2) {
			var2.printStackTrace();
			throw new RuntimeException("can\'t get AllBudgetUsers", var2);
		}
	}

	public EntityList getCheckUserAccessToModel(int param1, int param2) {
		try {
			return this.getConnection().getListHelper().getCheckUserAccessToModel(param1, param2);
		} catch (Exception var4) {
			var4.printStackTrace();
			throw new RuntimeException("can\'t get CheckUserAccessToModel", var4);
		}
	}

	public EntityList getCheckUserAccess(int param1, int param2) {
		try {
			return this.getConnection().getListHelper().getCheckUserAccess(param1, param2);
		} catch (Exception var4) {
			var4.printStackTrace();
			throw new RuntimeException("can\'t get CheckUserAccess", var4);
		}
	}

	public EntityList getCheckUser(int param1) {
		try {
			return this.getConnection().getListHelper().getCheckUser(param1);
		} catch (Exception var3) {
			var3.printStackTrace();
			throw new RuntimeException("can\'t get CheckUser", var3);
		}
	}

	public EntityList getBudgetUsersForNode(int param1, int param2) {
		try {
			return this.getConnection().getListHelper().getBudgetUsersForNode(param1, param2);
		} catch (Exception var4) {
			var4.printStackTrace();
			throw new RuntimeException("can\'t get BudgetUsersForNode", var4);
		}
	}

	public EntityList getNodesForUserAndCycle(int param1, int param2) {
		try {
			return this.getConnection().getListHelper().getNodesForUserAndCycle(param1, param2);
		} catch (Exception var4) {
			var4.printStackTrace();
			throw new RuntimeException("can\'t get NodesForUserAndCycle", var4);
		}
	}

	public EntityList getNodesForUserAndModel(int param1, int param2) {
		try {
			return this.getConnection().getListHelper().getNodesForUserAndModel(param1, param2);
		} catch (Exception var4) {
			var4.printStackTrace();
			throw new RuntimeException("can\'t get NodesForUserAndModel", var4);
		}
	}

	public EntityList getUsersForModelAndElement(int param1, int param2) {
		try {
			return this.getConnection().getListHelper().getUsersForModelAndElement(param1, param2);
		} catch (Exception var4) {
			var4.printStackTrace();
			throw new RuntimeException("can\'t get UsersForModelAndElement", var4);
		}
	}

	public String getProcessName() {
		String ret = "Processing BudgetUser";
		return ret;
	}

	protected int getProcessID() {
		return 17;
	}

	public EntityList getBudgetDetailsForUser(int userId) {
		try {
			return this.getConnection().getListHelper().getBudgetDetailsForUser(userId);
		} catch (Exception var3) {
			var3.printStackTrace();
			throw new RuntimeException("can\'t get BudgetDetailsForUser", var3);
		}
	}

	public EntityList getBudgetDetailsForUser(int userId, int modelid) {
		return this.getConnection().getListHelper().getBudgetDetailsForUser(userId, modelid);
	}

	public EntityList getBudgetDetailsForUser(int userId, boolean detailedSelection, int locationId, int cycleId) {
		return this.getConnection().getListHelper().getBudgetDetailsForUser(userId, detailedSelection, locationId, cycleId);
	}

	public EntityList getBudgetUserDetails(int bcId, int[] structureElementId) {
		return this.getConnection().getListHelper().getBudgetUserDetails(bcId, structureElementId);
	}

	public EntityList getBudgetUserDetailsNodeDown(int bcId, int structureElementId, int structureId) {
		return this.getConnection().getListHelper().getBudgetUserDetailsNodeDown(bcId, structureElementId, structureId);
	}

	public EntityList getBudgetUserAuthDetailsNodeUp(int bcId, int structureElementId, int structureId) {
		return this.getConnection().getListHelper().getBudgetUserAuthDetailsNodeUp(bcId, structureElementId, structureId);
	}

	public EntityList getPickerStartUpDetails(int modelId, int[] structureElementId, int userId) {
		return this.getConnection().getListHelper().getPickerStartUpDetails(modelId, structureElementId, userId);
	}

	public void copyAssignments(int userId, Object fromUserKey, List<Object> toUserKeys) throws ValidationException {
		BudgetLocationEditorSessionServer es = new BudgetLocationEditorSessionServer(getConnection());
		try {
			es.copyAssignments(userId, fromUserKey, toUserKeys);
		} catch (ValidationException e) {
			throw e;
		} catch (CPException e) {
			throw new RuntimeException("can't copy assignments ");
		}
	}

	public EntityList getUserModelSecurity() {
		return getConnection().getListHelper().getUserModelSecurity();
	}
}
