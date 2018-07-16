// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.util.awt.QListModel;
import java.util.List;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public interface VirementLocationsEditor extends SubBusinessEditor {

   TreeModel getResponsibilityAreaTree();

   void addResponsibilityArea(Object var1, String var2, String var3) throws ValidationException;

   void removeResponsinbilityArea(Object var1) throws ValidationException;

   QListModel getLocations();

   List<TreeNode> searchTree(String var1);

   List<TreeNode> queryTreeNodes();
}
