package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.BudgetCycleEditor;
import com.cedar.cp.api.model.BudgetCycleEditorSession;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.BudgetCycleEditorSessionSSO;
import com.cedar.cp.dto.model.BudgetCycleImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.api.model.BudgetCycleEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.model.BudgetCycleEditorImpl;
import com.cedar.cp.impl.model.BudgetCyclesProcessImpl;
import com.cedar.cp.util.Log;

public class BudgetCycleEditorSessionImpl extends BusinessSessionImpl implements BudgetCycleEditorSession {

	protected BudgetCycleEditorSessionSSO mServerSessionData;
	protected BudgetCycleImpl mEditorData;
	protected BudgetCycleEditorImpl mClientEditor;
	private Log mLog = new Log(this.getClass());

	public BudgetCycleEditorSessionImpl(BudgetCyclesProcessImpl process, Object key) throws ValidationException {
		super(process);

		try {
			if (key == null) {
				this.mServerSessionData = this.getSessionServer().getNewItemData();
			} else {
				this.mServerSessionData = this.getSessionServer().getItemData(key);
			}
		} catch (ValidationException var4) {
			throw var4;
		} catch (Exception var5) {
			throw new RuntimeException("Can\'t get BudgetCycle", var5);
		}

		this.mEditorData = this.mServerSessionData.getEditorData();
	}

	public BudgetCycleEditorSessionImpl(BudgetCyclesProcessImpl process, Object key, boolean b) throws ValidationException {
		super(process);

		try {
			if (key != null) {
				this.getSessionServer().clearCache(key);
			}
		} catch (ValidationException var4) {
			throw var4;
		} catch (Exception var5) {
			throw new RuntimeException("Can\'t get BudgetCycle", var5);
		}
	}

	protected BudgetCycleEditorSessionServer getSessionServer() throws CPException {
		return new BudgetCycleEditorSessionServer(this.getConnection());
	}

	public BudgetCycleEditor getBudgetCycleEditor() {
		if (this.mClientEditor == null) {
			this.mClientEditor = new BudgetCycleEditorImpl(this, this.mServerSessionData, this.mEditorData);
			this.mActiveEditors.add(this.mClientEditor);
		}

		return this.mClientEditor;
	}

	public Object getPrimaryKey() {
		return this.mEditorData.getPrimaryKey();
	}

	public EntityList getAvailableModels() {
		try {
			return this.getSessionServer().getAvailableModels();
		} catch (Exception var2) {
			throw new RuntimeException("unexpected exceptio", var2);
		}
	}

	public EntityList getOwnershipRefs() {
		try {
			return this.getSessionServer().getOwnershipRefs(this.mEditorData.getPrimaryKey());
		} catch (Exception var2) {
			throw new RuntimeException("unexpected exceptio", var2);
		}
	}

	public Object persistModifications(boolean cloneOnSave) throws CPException, ValidationException {
		if (this.mClientEditor != null) {
			this.mClientEditor.saveModifications();
		}

		if (this.mEditorData.getPrimaryKey() == null) {
			this.mEditorData.setPrimaryKey(this.getSessionServer().insert(this.mEditorData));
		} else if (cloneOnSave) {
			this.mEditorData.setPrimaryKey(this.getSessionServer().copy(this.mEditorData));
		} else {
			this.getSessionServer().update(this.mEditorData);
		}

		return this.mEditorData.getPrimaryKey();
	}

	public void terminate() {
	}

	public EntityList getAvailableFinanceXMLFormForModelRefs(ModelRef ref) {
		try {
			ModelPK e = (ModelPK) ref.getPrimaryKey();
			EntityList c = this.getConnection().getListHelper().getAllFinanceXmlFormsAndDataTypesForModel(e.getModelId());
			return c;
		} catch (Exception var4) {
			throw new RuntimeException(var4.getMessage());
		}
	}
	
	public EntityList getAvailableXMLFormForModelRefs(ModelRef ref) {
		try {
			ModelPK e = (ModelPK) ref.getPrimaryKey();
			EntityList c = this.getConnection().getListHelper().getAllXmlFormsForModel(e.getModelId());
			return c;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
