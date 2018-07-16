package com.cedar.cp.dto.recalculate;

import com.cedar.cp.api.recalculate.RecalculateBatchTaskRef;
import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.dto.base.AbstractELO;
import com.cedar.cp.dto.model.BudgetCycleRefImpl;
import com.cedar.cp.dto.model.ModelRefImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllRecalculateBatchTasksELO extends AbstractELO implements Serializable {

	private static final String[] mEntity = new String[] { "RecalculateBatchTask", "RecalculateBatchTaskLink" };
	private transient RecalculateBatchTaskRef mRecalculateBatchTaskEntityRef;
	private transient ModelRefImpl mModel;
	private transient BudgetCycleRefImpl mBudgetCycle;
	private transient List<DataEntryProfileRef> mDataEntryProfileRefs;
	private transient List<Integer> mRespArea;
	private transient int mUserId;
	private transient String mIdentifier;
	private transient String mDescription;

	public AllRecalculateBatchTasksELO() {
		super(new String[] { "RecalculateBatchTask", "ModelId", "BudgetCycleId", "XmlForms", "ResponsibilityArea", "Identifier", "Description", "UserId" });
	}
	
	public AllRecalculateBatchTasksELO(boolean listProcessInfo) {
		super(new String[] { "RecalculateBatchTask", "ModelId", "Identifier", "Description", "UserId" });
	}

	public void add(RecalculateBatchTaskRef eRefImportTask, ModelRefImpl model, BudgetCycleRefImpl budgetCycle, List<DataEntryProfileRef> DataEntryProfileRefs, List<Integer> respArea, String identifier, String description, int userId) {
		ArrayList l = new ArrayList();
		l.add(eRefImportTask);
		l.add(model);
		l.add(budgetCycle);
		l.add(DataEntryProfileRefs);
		l.add(respArea);
		l.add(identifier);
		l.add(description);
		l.add(userId);
		this.mCollection.add(l);
	}
	
	public void add(RecalculateBatchTaskRef eRefImportTask, ModelRefImpl model, String identifier, String description, int userId) {
		ArrayList l = new ArrayList();
		l.add(eRefImportTask);
		l.add(model);
		l.add(identifier);
		l.add(description);
		l.add(userId);
		this.mCollection.add(l);
	}
	
	public void next() {
		if (this.mIterator == null) {
			this.reset();
		}

		++this.mCurrRowIndex;
		List l = (List) this.mIterator.next();
		byte index = 0;
		int var4 = index + 1;
		
		if (l.size() == 5) {
			this.mRecalculateBatchTaskEntityRef = (RecalculateBatchTaskRef) l.get(index);
			this.setModel((ModelRefImpl) l.get(var4++));
			this.setIdentifier((String) l.get(var4++));
			this.setDescription((String) l.get(var4++));
			this.setUserId((Integer) l.get(var4++));
		} else {			
			this.mRecalculateBatchTaskEntityRef = (RecalculateBatchTaskRef) l.get(index);
			this.setModel((ModelRefImpl) l.get(var4++));
			this.setBudgetCycle((BudgetCycleRefImpl) l.get(var4++));
			this.setDataEntryProfileRefs((List<DataEntryProfileRef>) l.get(var4++));
			this.setRespArea((List<Integer>) l.get(var4++));
			this.setIdentifier((String) l.get(var4++));
			this.setDescription((String) l.get(var4++));
			this.setUserId((Integer) l.get(var4++));
		}
	}

	public RecalculateBatchTaskRef getRecalculateBatchTaskEntityRef() {
		return this.mRecalculateBatchTaskEntityRef;
	}


	public boolean includesEntity(String name) {
		for (int i = 0; i < mEntity.length; ++i) {
			if (name.equals(mEntity[i])) {
				return true;
			}
		}

		return false;
	}

	public BudgetCycleRefImpl getBudgetCycle() {
		return mBudgetCycle;
	}

	public void setBudgetCycle(BudgetCycleRefImpl mBudgetCycle) {
		this.mBudgetCycle = mBudgetCycle;
	}

	public ModelRefImpl getModel() {
		return mModel;
	}

	public void setModel(ModelRefImpl mModel) {
		this.mModel = mModel;
	}

//	public List<String[]> getXmlForm() {
//		return mXmlForm;
//	}
//
//	public void setXmlForm(List<String[]> mXmlForm) {
//		this.mXmlForm = mXmlForm;
//	}

	public List<DataEntryProfileRef> getDataEntryProfileRefs() {
		return mDataEntryProfileRefs;
	}

	public void setDataEntryProfileRefs(List<DataEntryProfileRef> mDataEntryProfileRefs) {
		this.mDataEntryProfileRefs = mDataEntryProfileRefs;
	}

	public List<Integer> getRespArea() {
		return mRespArea;
	}

	public void setRespArea(List<Integer> mRespArea) {
		this.mRespArea = mRespArea;
	}

	public int getUserId() {
		return mUserId;
	}

	public void setUserId(int mUserId) {
		this.mUserId = mUserId;
	}

	public String getIdentifier() {
		return mIdentifier;
	}

	public void setIdentifier(String mIdentifier) {
		this.mIdentifier = mIdentifier;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}

}
