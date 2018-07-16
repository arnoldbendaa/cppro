package com.cedar.cp.api.budgetlocation;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.dimension.Hierarchy;
import java.util.List;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public interface BudgetLocationEditor extends BusinessEditor {
	
  Hierarchy getHierarchy();

  EntityRef getHierarchyRef();

  EntityRef getModelRef();

  EntityRef getDimensionRef();

  Object getRootElementPK();

  Object getRootElementEntityRef();

  EntityList getImmediateChildren(Object paramObject);

  TreeModel getHierarchyTreeModel();

  List<TreeNode> searchHierTree(TreeModel paramTreeModel, String paramString);

  void setDeployForms(boolean paramBoolean);

  void setReadOnly(EntityRef paramEntityRef, Object paramObject, Boolean paramBoolean);

  List getModelUserElementAccess();

  EntityList getAllUsers();

  UserModelElementAssignment addElementAccess(EntityRef paramEntityRef1, EntityRef paramEntityRef2, String paramString, Boolean paramBoolean);

  void removeElementAccess(EntityRef paramEntityRef1, EntityRef paramEntityRef2);

  List getExportRows();
}
