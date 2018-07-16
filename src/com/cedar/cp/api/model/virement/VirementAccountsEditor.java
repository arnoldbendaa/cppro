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

public interface VirementAccountsEditor extends SubBusinessEditor {

   TreeModel getAccountTree();

   void addAccount(Object var1, String var2, String var3, double var4, double var6, double var8, boolean var10, boolean var11) throws ValidationException;

   void removeAccount(Object var1) throws ValidationException;

   QListModel getAccounts();

   List<TreeNode> searchTree(String var1);

   List<TreeNode> queryAccountTreeNodes();
}
