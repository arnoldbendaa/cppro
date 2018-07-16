// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.virement.VirementAccount;
import javax.swing.tree.TreeModel;

public interface VirementAccountEditor extends SubBusinessEditor {

   TreeModel getAccountTree();

   void setTranLimit(double var1) throws ValidationException;

   void setTotalLimitIn(double var1) throws ValidationException;

   void setTotalLimitOut(double var1) throws ValidationException;

   void setAllowInput(boolean var1);

   void setAllowOutput(boolean var1);

   VirementAccount getVirementAccount();
}
