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
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.api.budgetlocation.UserModelSecurity;
import com.cedar.cp.api.budgetlocation.UserModelSecurityEditor;
import com.cedar.cp.api.budgetlocation.UserModelSecurityEditorSession;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.budgetlocation.UserModelElementAssignmentImpl;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityEditorSessionSSO;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.StructureElementNodeImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.base.SwingUtils;
import com.cedar.cp.util.OnDemandMutableTreeNode;

public class UserModelSecurityEditorImpl extends BusinessEditorImpl implements UserModelSecurityEditor, SubBusinessEditorOwner {

	private UserModelSecurityEditorSessionSSO mServerSessionData;
	private UserModelSecurityImpl mEditorData;
	private UserModelSecurityAdapter mEditorDataAdapter;
	private Integer mCurrentModelIndex;
	private EntityList mAllUsers;

	public UserModelSecurityEditorImpl(UserModelSecurityEditorSessionImpl session, UserModelSecurityEditorSessionSSO serverSessionData, UserModelSecurityImpl editorData) {
		super(session);

		mServerSessionData = serverSessionData;

		mEditorData = editorData;
	}

	public void updateEditorData(UserModelSecurityEditorSessionSSO serverSessionData, UserModelSecurityImpl editorData) {
		mServerSessionData = serverSessionData;
		mEditorData = editorData;
	}

	public UserModelSecurity getUserModelSecurity() {
		if (mEditorDataAdapter == null) {
			mEditorDataAdapter = new UserModelSecurityAdapter((UserModelSecurityEditorSessionImpl) getBusinessSession(), mEditorData);
		}

		return mEditorDataAdapter;
	}

	public void saveModifications() throws ValidationException {
		saveValidation();
	}

	private void saveValidation() throws ValidationException {
	}

	public String getUserDescription() {
		return mServerSessionData.getEditorData().getUserRef() + " - " + mServerSessionData.getEditorData().getUserDescription();
	}

	public List<UserModelElementAssignment> getUserModelElementAccess() {
		return mServerSessionData.getEditorData().getUserModelElementAccess();
	}

	public void setUserModelElementAccess(List<UserModelElementAssignment> l) {
		mServerSessionData.getEditorData().setUserModelElementAccess(l);
	}

	public EntityList getModelsAndRAHierarchies() {
		return mServerSessionData.getEditorData().getModelsAndRAHierarchies();
	}

	public void setReadOnly(Object pk, Boolean b) {
		List l = mEditorData.getUserModelElementAccess();
		for (int i = 0; i < l.size(); i++) {
			UserModelElementAssignment ea = (UserModelElementAssignment) l.get(i);
			if ((ea.getUser().equals(mEditorData.getUserRef())) && (ea.getStructureElementPK().equals(pk))) {
				ea.setReadOnly(b);
				setContentModified();
				return;
			}
		}
		throw new IllegalStateException(pk + " not found");
	}

	public UserModelElementAssignment addElementAccess(EntityRef model, EntityRef se, String sedesc, Boolean readOnly) {
		UserModelElementAssignment ea = new UserModelElementAssignmentImpl(getModelRef(), mEditorData.getUserRef(), se, sedesc, readOnly);
		List l = mEditorData.getUserModelElementAccess();
		l.add(ea);
		setContentModified();
		return ea;
	}

	public void removeElementAccess(EntityRef model, EntityRef se) {
		List l = mEditorData.getUserModelElementAccess();
		for (int i = 0; i < l.size(); i++) {
			UserModelElementAssignment ea = (UserModelElementAssignment) l.get(i);
			if ((ea.getUser().equals(mEditorData.getUserRef())) && (ea.getStructureElementRef().equals(se))) {
				l.remove(i);
				setContentModified();
				return;
			}
		}
		throw new IllegalStateException();
	}

	public void setCurrentModelIndex(int i) {
		mCurrentModelIndex = Integer.valueOf(i);
	}

	public Integer getCurrentModelIndex() {
		return mCurrentModelIndex;
	}

	public EntityRef getModelRef() {
		return (EntityRef) mServerSessionData.getEditorData().getModelsAndRAHierarchies().getValueAt(mCurrentModelIndex.intValue(), "Model");
	}

	public String getModelDescription() {
		return (String) mServerSessionData.getEditorData().getModelsAndRAHierarchies().getValueAt(mCurrentModelIndex.intValue(), "ModelDescription");
	}

	public EntityRef getDimensionRef() {
		return (EntityRef) mServerSessionData.getEditorData().getModelsAndRAHierarchies().getValueAt(mCurrentModelIndex.intValue(), "Dimension");
	}

	public String getDimensionDescription() {
		return (String) mServerSessionData.getEditorData().getModelsAndRAHierarchies().getValueAt(mCurrentModelIndex.intValue(), "DimensionDescription");
	}

	public int getHierarchyId() {
		return ((Integer) mServerSessionData.getEditorData().getModelsAndRAHierarchies().getValueAt(mCurrentModelIndex.intValue(), "HierarchyId")).intValue();
	}

	public Object getRootElementPK() {
		return ((EntityRef) mServerSessionData.getEditorData().getModelsAndRAHierarchies().getValueAt(mCurrentModelIndex.intValue(), "StructureElement")).getPrimaryKey();
	}

	public EntityList getImmediateChildren(Object pk) {
		int structureId = ((StructureElementPK) pk).getStructureId();
		int elementId = ((StructureElementPK) pk).getStructureElementId();
		if (pk.equals(getRootElementPK())) {
			return null;
		}
		return getConnection().getListHelper().getImmediateChildren(structureId, elementId);
	}

	public void removeSubBusinessEditor(SubBusinessEditor editor) throws CPException {
		if ((editor instanceof UserModelSecurityEditorImpl)) {
			throw new CPException("Attempt to remove unknown sub editor : " + editor);
		}

		throw new CPException("Attempt to remove unknown sub editor : " + editor);
	}

	public TreeModel getHierarchyTreeModel() {
		EntityList rootElem = getConnection().getListHelper().getRespAreaImmediateChildren(getHierarchyId(), 0);
		StructureElementNodeImpl raNode = new StructureElementNodeImpl(getConnection(), rootElem.getRowData(0));
		return new DefaultTreeModel(new OnDemandMutableTreeNode(raNode, "com.cedar.cp.impl.dimension.StructureElementProxyNode"));
	}

	public List<TreeNode> searchHierTree(TreeModel model, String search, Object[] currentPath) {
		int dimensionId = ((DimensionPK) getDimensionRef().getPrimaryKey()).getDimensionId();
		EntityList elements = getConnection().getListHelper().doElementPickerSearch(dimensionId, search);
		return SwingUtils.locateNodesInTree(model, elements);
	}

	public EntityList getAllUsers() {
		if (mAllUsers == null)
			mAllUsers = getConnection().getListHelper().getAllUsers();
		return mAllUsers;
	}

	public int imp(List<String[]> list, boolean replace, StringBuffer message) {
		try {
			((UserModelSecurityEditorSession) getBusinessSession()).doImport(list);
		} catch (ValidationException e) {
			message.append(e.getMessage());
		}
		return 0;
	}

	private ModelRef findModel(String visId) {
		for (int i = 0; i < getModelsAndRAHierarchies().getNumRows(); i++) {
			ModelRef modelRef = (ModelRef) getModelsAndRAHierarchies().getValueAt(i, "Model");
			if (visId.equals(modelRef.getNarrative())) {
				setCurrentModelIndex(i);
				return modelRef;
			}
		}
		return null;
	}

	private UserRef findUser(String visId) {
		for (int i = 0; i < getAllUsers().getNumRows(); i++) {
			UserRef userRef = (UserRef) getAllUsers().getValueAt(i, "User");
			if (visId.equals(userRef.getNarrative())) {
				return userRef;
			}
		}
		return null;
	}

	public List getExportRows() {
		List l = new ArrayList();
		EntityRef prevModel = null;
		EntityRef prevUser = null;
		for (UserModelElementAssignment ea : getUserModelElementAccess()) {
			if ((prevUser == null) || (!prevUser.equals(ea.getUser()))) {
				if (prevUser != null)
					l.add(new Object[0]);
				l.add(new Object[]{"User", ea.getUser().getNarrative(), "Replace"});
				l.add(new Object[0]);
				l.add(new Object[]{"", "Model", "Responsibility Area", "Read Only"});
				prevUser = ea.getUser();
				prevModel = null;
			}
			if ((prevModel == null) || (!prevModel.equals(ea.getModel()))) {
				if (prevModel != null)
					l.add(new Object[0]);
				prevModel = ea.getModel();
			}
			Object[] o = {"", ea.getModel(), ea.getStructureElementRef(), ea.getReadOnly().booleanValue() ? "Y" : "N"};
			l.add(o);
		}
		return l;
	}

	public void setDeployForms(boolean b) {
		mEditorData.setDeployForms(b);
	}

	@Override
	public List<TreeNode> searchHierTree(TreeModel paramTreeModel, String paramString) {
		// TODO Auto-generated method stub
		return null;
	}
}