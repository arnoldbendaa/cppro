package com.cedar.cp.api.budgetlocation;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import java.util.List;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public interface UserModelSecurityEditor extends BusinessEditor {

  void setCurrentModelIndex(int paramInt);

  Integer getCurrentModelIndex();

  EntityRef getModelRef();

  String getModelDescription();

  int getHierarchyId();

  EntityRef getDimensionRef();

  String getDimensionDescription();

  Object getRootElementPK();

  EntityList getImmediateChildren(Object paramObject);

  TreeModel getHierarchyTreeModel();

  List<TreeNode> searchHierTree(TreeModel paramTreeModel, String paramString);

  List getExportRows();

  int imp(List<String[]> paramList, boolean paramBoolean, StringBuffer paramStringBuffer);

  void setUserModelElementAccess(List<UserModelElementAssignment> paramList);

  String getUserDescription();

  List<UserModelElementAssignment> getUserModelElementAccess();

  EntityList getModelsAndRAHierarchies();

  void setReadOnly(Object paramObject, Boolean paramBoolean);

  UserModelElementAssignment addElementAccess(EntityRef paramEntityRef1, EntityRef paramEntityRef2, String paramString, Boolean paramBoolean);

  void removeElementAccess(EntityRef paramEntityRef1, EntityRef paramEntityRef2);

  void setDeployForms(boolean paramBoolean);
}