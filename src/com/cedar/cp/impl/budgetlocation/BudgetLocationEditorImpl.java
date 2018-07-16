package com.cedar.cp.impl.budgetlocation;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.budgetlocation.BudgetLocation;
import com.cedar.cp.api.budgetlocation.BudgetLocationEditor;
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.api.dimension.Hierarchy;
import com.cedar.cp.dto.budgetlocation.BudgetLocationEditorSessionSSO;
import com.cedar.cp.dto.budgetlocation.BudgetLocationImpl;
import com.cedar.cp.dto.budgetlocation.UserModelElementAssignmentImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.StructureElementNodeImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.base.SwingUtils;
import com.cedar.cp.util.OnDemandMutableTreeNode;

public class BudgetLocationEditorImpl extends BusinessEditorImpl implements BudgetLocationEditor, SubBusinessEditorOwner {

	private BudgetLocationEditorSessionSSO mServerSessionData;
	private BudgetLocationImpl mEditorData;
	private BudgetLocationAdapter mEditorDataAdapter;
	protected BudgetUserEditorImpl mUserEditor;
	private int mCurrentStructureElementId = -1;

	public BudgetLocationEditorImpl(BudgetLocationEditorSessionImpl session, BudgetLocationEditorSessionSSO serverSessionData, BudgetLocationImpl editorData) {
		super(session);

		mServerSessionData = serverSessionData;

		mEditorData = editorData;
	}

	public void updateEditorData(BudgetLocationEditorSessionSSO serverSessionData, BudgetLocationImpl editorData) {
		mServerSessionData = serverSessionData;
		mEditorData = editorData;
	}

	public BudgetLocation getBudgetLocation() {
		if (mEditorDataAdapter == null) {
			mEditorDataAdapter = new BudgetLocationAdapter((BudgetLocationEditorSessionImpl) getBusinessSession(), mEditorData);
		}

		return mEditorDataAdapter;
	}

	public void saveModifications() throws ValidationException {
		saveValidation();
	}

	private void saveValidation() throws ValidationException {
	}

	public Hierarchy getHierarchy() {
		return mServerSessionData.getHierarchy();
	}

	public EntityRef getHierarchyRef() {
		return mServerSessionData.getEditorData().getHierarchyRef();
	}

	public Object getRootElementPK() {
		return ((EntityRef) mEditorData.getRootElementEntityRef()).getPrimaryKey();
	}

	public Object getRootElementEntityRef() {
		return mEditorData.getRootElementEntityRef();
	}

	public EntityList getImmediateChildren(Object pk) {
		int structureId = ((StructureElementPK) pk).getStructureId();
		int elementId = ((StructureElementPK) pk).getStructureElementId();
		if (pk.equals(getRootElementPK())) {
			return mEditorData.getRootChildren();
		}
		return getConnection().getListHelper().getImmediateChildren(structureId, elementId);
	}

	public EntityRef getModelRef() {
		return mEditorData.getModelRef();
	}

	public EntityRef getDimensionRef() {
		return mEditorData.getDimensionRef();
	}

	public void removeSubBusinessEditor(SubBusinessEditor editor) throws CPException {
		if ((editor instanceof BudgetUserEditorImpl)) {
			if (editor != mUserEditor) {
				throw new CPException("Attempt to remove unknown sub editor : " + editor);
			}
			mUserEditor = null;
		} else {
			throw new CPException("Attempt to remove unknown sub editor : " + editor);
		}
	}

	public TreeModel getHierarchyTreeModel() {
		HierarchyPK hierPk = (HierarchyPK) getHierarchyRef().getPrimaryKey();
		EntityList rootElem = getConnection().getListHelper().getRespAreaImmediateChildren(hierPk.getHierarchyId(), 0);
		StructureElementNodeImpl raNode = new StructureElementNodeImpl(getConnection(), rootElem.getRowData(0));
		return new DefaultTreeModel(new OnDemandMutableTreeNode(raNode, "com.cedar.cp.impl.dimension.StructureElementProxyNode"));
	}

	public List<TreeNode> searchHierTree(TreeModel model, String search) {
		int dimensionId = ((DimensionPK) getDimensionRef().getPrimaryKey()).getDimensionId();
		EntityList elements = getConnection().getListHelper().doElementPickerSearch(dimensionId, search);
		return SwingUtils.locateNodesInTree(model, elements);
	}

	public List getExportRows() {
		List l = new ArrayList();
		EntityRef prevModel = null;
		EntityRef prevUser = null;
		for (UserModelElementAssignment ea : getModelUserElementAccess()) {
			if ((prevModel == null) || (!prevModel.equals(ea.getModel()))) {
				if (prevModel != null)
					l.add(new Object[0]);
				l.add(new Object[]{"Model", ea.getModel().getNarrative(), "Replace"});
				l.add(new Object[0]);
				l.add(new Object[]{"", "User", "Responsibility Area", "Read Only"});
				prevModel = ea.getModel();
				prevUser = null;
			}
			if ((prevUser == null) || (!prevUser.equals(ea.getUser()))) {
				if (prevUser != null)
					l.add(new Object[0]);
				prevUser = ea.getUser();
			}
			Object[] o = {"", ea.getUser(), ea.getStructureElementRef(), ea.getReadOnly().booleanValue() ? "Y" : "N"};
			l.add(o);
		}
		return l;
	}

	public void setDeployForms(boolean b) {
		mEditorData.setDeployForms(b);
	}

	public List<UserModelElementAssignment> getModelUserElementAccess() {
		return mServerSessionData.getEditorData().getModelUserElementAccess();
	}

	public EntityList getAllUsers() {
		return mServerSessionData.getEditorData().getAllUsers();
	}

	public void setReadOnly(EntityRef user, Object pk, Boolean b) {
		List l = mEditorData.getModelUserElementAccess();
		for (int i = 0; i < l.size(); i++) {
			UserModelElementAssignment ea = (UserModelElementAssignment) l.get(i);
			if ((ea.getUser().equals(user)) && (ea.getStructureElementPK().equals(pk))) {
				ea.setReadOnly(b);
				setContentModified();
				return;
			}
		}
		throw new IllegalStateException(pk + " not found");
	}

	public UserModelElementAssignment addElementAccess(EntityRef user, EntityRef se, String sedesc, Boolean readOnly) {
		UserModelElementAssignment ea = new UserModelElementAssignmentImpl(getModelRef(), user, se, sedesc, readOnly);
		List l = mEditorData.getModelUserElementAccess();
		l.add(ea);
		setContentModified();
		return ea;
	}

	public void removeElementAccess(EntityRef user, EntityRef se) {
		List l = mEditorData.getModelUserElementAccess();
		for (int i = 0; i < l.size(); i++) {
			UserModelElementAssignment ea = (UserModelElementAssignment) l.get(i);
			if ((ea.getUser().equals(user)) && (ea.getStructureElementRef().equals(se))) {
				l.remove(i);
				setContentModified();
				return;
			}
		}
		throw new IllegalStateException();
	}
}