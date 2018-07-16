// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.virement.ResponsibilityAreaNode;
import com.cedar.cp.api.model.virement.ResponsibilityAreaNodeEditor;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public interface ResponsibilityAreasEditor extends BusinessEditor {

   ResponsibilityAreaNodeEditor getEditor(ResponsibilityAreaNode var1) throws ValidationException;

   int queryNodeStatus(DefaultMutableTreeNode var1);

   int queryNodeSettings(DefaultMutableTreeNode var1);

   List<TreeNode> searchTree(String var1);

   List<TreeNode> queryTreeNodes();

   TreeModel getTree();
}
