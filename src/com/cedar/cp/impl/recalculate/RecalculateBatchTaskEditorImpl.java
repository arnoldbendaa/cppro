package com.cedar.cp.impl.recalculate;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.cedar.cp.api.recalculate.RecalculateBatchTask;
import com.cedar.cp.api.recalculate.RecalculateBatchTaskEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.api.recalculate.RecalculateBatchTaskAssignment;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskAssignmentImpl;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskEditorSessionSSO;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskImpl;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;
import com.cedar.cp.dto.dimension.StructureElementNodeImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.recalculate.RecalculateBatchTaskAdapter;
import com.cedar.cp.impl.recalculate.RecalculateBatchTaskEditorSessionImpl;
import com.cedar.cp.util.OnDemandMutableTreeNode;

public class RecalculateBatchTaskEditorImpl extends BusinessEditorImpl implements RecalculateBatchTaskEditor {

	private RecalculateBatchTaskEditorSessionSSO mServerSessionData;
	private RecalculateBatchTaskImpl mEditorData;
	private RecalculateBatchTaskAdapter mEditorDataAdapter;

	public RecalculateBatchTaskEditorImpl(RecalculateBatchTaskEditorSessionImpl session, RecalculateBatchTaskEditorSessionSSO serverSessionData, RecalculateBatchTaskImpl editorData) {
		super(session);
		this.mServerSessionData = serverSessionData;
		this.mEditorData = editorData;
	}

	public void updateEditorData(RecalculateBatchTaskEditorSessionSSO serverSessionData, RecalculateBatchTaskImpl editorData) {
		this.mServerSessionData = serverSessionData;
		this.mEditorData = editorData;
	}

	public RecalculateBatchTask getRecalculateBatchTask() {
		if (this.mEditorDataAdapter == null) {
			this.mEditorDataAdapter = new RecalculateBatchTaskAdapter((RecalculateBatchTaskEditorSessionImpl) this.getBusinessSession(), this.mEditorData);
		}

		return this.mEditorDataAdapter;
	}

	public void saveModifications() throws ValidationException {
		this.saveValidation();
	}

	private void saveValidation() throws ValidationException {
		if (this.mEditorData.getRecalculateBatchTaskAssignments().size() < 1) {
			throw new ValidationException("RecalculateBatchTask requires at least one assignment");
		}
	}

	public List getRecalculateBatchTaskAssignments() {
		return this.mEditorData.getRecalculateBatchTaskAssignments();
	}

	public RecalculateBatchTaskAssignment getRecalculateBatchTaskAssignmentItem(int id) {
		RecalculateBatchTaskPK pk = new RecalculateBatchTaskPK(id);
		return this.mEditorData.getRecalculateBatchTaskAssignmentItem(pk);
	}

	public void setRecalculateBatchTaskAssignmentItems(List l) {
		this.mEditorData.setRecalculateBatchTaskAssignments(l);
	}

	public List<UserModelElementAssignment> getRespAreaAccess() {
		return this.getConnection().getListHelper().getRespAreaAccess();
	}

	public EntityList getImmediateChildren(Object pk) {
		int elementId = ((StructureElementPK) pk).getStructureElementId();
		int structureId = ((StructureElementPK) pk).getStructureId();
		return this.getConnection().getListHelper().getImmediateChildren(structureId, elementId);
	}

	public boolean isModelSelected(EntityRef entityRef) {
		Iterator i = this.mEditorData.getRecalculateBatchTaskAssignments().iterator();

		RecalculateBatchTaskAssignment rbta;
		do {
			if (!i.hasNext()) {
				return false;
			}

			rbta = (RecalculateBatchTaskAssignment) i.next();
		} while (!rbta.getOwningModelRef().equals(entityRef) || rbta.getOwningBudgetCycleRef() != null || rbta.getOwningBudgetLocationRef() != null);

		return false;
	}

	public boolean isResponsibilityAreaSelected(EntityRef owningModelRef, EntityRef entityRef) {
		Iterator i = this.mEditorData.getRecalculateBatchTaskAssignments().iterator();

		RecalculateBatchTaskAssignment rbta;
		do {
			if (!i.hasNext()) {
				return false;
			}

			rbta = (RecalculateBatchTaskAssignment) i.next();
		} while (rbta.getOwningBudgetCycleRef() != null || rbta.getOwningBudgetLocationRef() == null || !rbta.getOwningBudgetLocationRef().equals(entityRef));

		return true;
	}

	public boolean isResponsibilityAreaAndChildrenSelected(EntityRef owningModelRef, EntityRef entityRef) {
		Iterator i = this.mEditorData.getRecalculateBatchTaskAssignments().iterator();

		RecalculateBatchTaskAssignment rbta;
		do {
			if (!i.hasNext()) {
				return false;
			}

			rbta = (RecalculateBatchTaskAssignment) i.next();
		} while (!rbta.getOwningModelRef().equals(owningModelRef) || rbta.getOwningBudgetCycleRef() != null || rbta.getOwningBudgetLocationRef() == null || !rbta.getOwningBudgetLocationRef().equals(entityRef));

		return true;
	}

	public void addResponsibilityArea(EntityRef modelRef, EntityRef respRef, List pathToRoot) {
		int id = 0 - (this.mEditorData.getRecalculateBatchTaskAssignments().size() + 1);
		RecalculateBatchTaskAssignmentImpl rbta = new RecalculateBatchTaskAssignmentImpl(new RecalculateBatchTaskPK(id));
		rbta.setOwningModelRef((ModelRef) modelRef);
		rbta.setOwningBudgetLocationRef((StructureElementRef) respRef);
		rbta.setParents(pathToRoot);
		this.mEditorData.addRecalculateBatchTaskAssignmentItem(rbta);
		this.setContentModified();
	}

	public void removeModel(EntityRef modelRef) {
		ListIterator i = this.mEditorData.getRecalculateBatchTaskAssignments().listIterator();

		RecalculateBatchTaskAssignment rbta;
		do {
			if (!i.hasNext()) {
				throw new IllegalStateException(modelRef.getPrimaryKey() + " not found");
			}

			rbta = (RecalculateBatchTaskAssignment) i.next();
		} while (!rbta.getOwningModelRef().equals(modelRef) || rbta.getOwningBudgetCycleRef() != null || rbta.getOwningBudgetLocationRef() != null);

		i.remove();
		this.setContentModified();
	}

	public void removeResponsibilityArea(EntityRef modelRef, EntityRef respRef) {
		ListIterator i = this.mEditorData.getRecalculateBatchTaskAssignments().listIterator();

		RecalculateBatchTaskAssignment rbta;
		StructureElementPK structPk;
		StructureElementPK newPk;
		do {
			if (!i.hasNext()) {
				throw new IllegalStateException(modelRef.getPrimaryKey() + " " + respRef.getPrimaryKey() + " not found");
			}

			rbta = (RecalculateBatchTaskAssignment) i.next();
			structPk = (StructureElementPK)(rbta.getOwningBudgetLocationRef().getPrimaryKey());
			newPk = (StructureElementPK)(respRef.getPrimaryKey());

		} while (rbta.getOwningBudgetCycleRef() != null || rbta.getOwningBudgetLocationRef() == null || newPk.getStructureElementId() != structPk.getStructureElementId());

		i.remove();
		this.setContentModified();
	}

	public boolean isChildSelected(EntityRef modelRef) {
		ListIterator i = this.mEditorData.getRecalculateBatchTaskAssignments().listIterator();

		RecalculateBatchTaskAssignment rbta;
		do {
			if (!i.hasNext()) {
				return false;
			}

			rbta = (RecalculateBatchTaskAssignment) i.next();
		} while (rbta.getOwningBudgetCycleRef() == null && rbta.getOwningBudgetLocationRef() == null);

		return true;
	}

	public boolean isChildSelected(EntityRef modelRef, EntityRef respRef) {
		ListIterator i = this.mEditorData.getRecalculateBatchTaskAssignments().listIterator();

		while (i.hasNext()) {
			RecalculateBatchTaskAssignment rbta = (RecalculateBatchTaskAssignment) i.next();
			if (rbta.getOwningModelRef().equals(modelRef) && rbta.getOwningBudgetCycleRef() == null && rbta.getOwningBudgetLocationRef() != null) {
				if (rbta.getParents() == null) {
					return false;
				}

				if (rbta.getParents().contains(respRef.getNarrative())) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isBudgetCycleSelected(EntityRef modelRef) {
		ListIterator i = this.mEditorData.getRecalculateBatchTaskAssignments().listIterator();

		RecalculateBatchTaskAssignment rbta;
		do {
			if (!i.hasNext()) {
				return false;
			}

			rbta = (RecalculateBatchTaskAssignment) i.next();
		} while (!rbta.getOwningModelRef().equals(modelRef) || rbta.getOwningBudgetCycleRef() == null);

		return true;
	}

	public void removeRecalculateBatchTaskAssignmentItem(Object key) {
		ListIterator iter = this.mEditorData.getRecalculateBatchTaskAssignments().listIterator();

		RecalculateBatchTaskAssignment item;
		do {
			if (!iter.hasNext()) {
				throw new RuntimeException("can\'t find " + key + " to remove");
			}

			item = (RecalculateBatchTaskAssignment) iter.next();
		} while (!key.equals(item.getPrimaryKey()));

		iter.remove();
	}

	public OnDemandMutableTreeNode getNodes(int hierId) {
		EntityList rootElem = this.getConnection().getListHelper().getRespAreaImmediateChildren(hierId, 0);
		StructureElementNodeImpl raNode = new StructureElementNodeImpl(this.getConnection(), rootElem.getRowData(0));
		return new OnDemandMutableTreeNode(raNode, "com.cedar.cp.impl.dimension.StructureElementProxyNode");
	}

	public void addXmlForm(DataEntryProfileRef dataEntryRef) {
		mEditorData.addRecalculateBatchTaskFormItem(dataEntryRef);
		this.setContentModified();
	}

	public void removeXmlForm(DataEntryProfileRef dataEntryRef) {
		mEditorData.removeRecalculateBatchTaskFormItem(dataEntryRef);
		this.setContentModified();
	}

	public void clearData() {
		mEditorData.clearRecalculateBatchTaskForm();
		this.setContentModified();
//		mEditorData.clearRecalculateBatchTaskAssignments();
	}

	public boolean containsRecalculateBatchTaskFormItem(DataEntryProfileRef deRef) {
		return mEditorData.containsRecalculateBatchTaskFormItem(deRef);
	}

	public void setBudgetCycleId(int budgetCycleId) {
		mEditorData.setBudgetCycleId(budgetCycleId);
	}

	public int getBudgetCycleId() {
		return mEditorData.getBudgetCycleId();
	}

	public List<DataEntryProfileRef> getRecalculateBatchTaskForms() {
		return mEditorData.getRecalculateBatchTaskForms();
	}
	
	public List<DataEntryProfileRef> getNewRecalculateBatchTaskForms() {
		return mEditorData.getSelectedRecalculateBatchTaskForms();
	}

	public List<Integer> getRecalculateBatchTaskRespArea() {
		return mEditorData.getRecalculateBatchTaskRespArea();
	}
	
	public String getIdentifier() {
		return mEditorData.getIdentifier();
	}
	
	public String getDescription() {
		return mEditorData.getDescription();
	}
	
	public void setIdentifier(String identifier) {
		mEditorData.setIdentifier(identifier);
	}
	
	public void setDescription(String description) {
		mEditorData.setDescription(description);
	}
}
