// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementAccount;
import com.cedar.cp.api.model.virement.VirementAccountEditor;
import com.cedar.cp.api.model.virement.VirementAccountsEditor;
import com.cedar.cp.api.model.virement.VirementCategory;
import com.cedar.cp.api.model.virement.VirementLocation;
import com.cedar.cp.api.model.virement.VirementLocationsEditor;
import javax.swing.tree.TreeModel;

public interface VirementCategoryEditor extends BusinessEditor {

   void setTranLimit(long var1) throws ValidationException;

   void setTotalLimitIn(long var1) throws ValidationException;

   void setTotalLimitOut(long var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setModelRef(ModelRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   VirementCategory getVirementCategory();

   TreeModel getResponsibilityAreaTree();

   void addResponsibilityArea(Object var1, String var2, String var3) throws ValidationException;

   void removeResponsinbilityArea(VirementLocation var1) throws ValidationException;

   TreeModel getAccountTree();

   VirementLocationsEditor getLocationEditor();

   VirementAccountEditor getAccountEditor(Object var1, String var2, String var3) throws ValidationException;

   void removeAccount(VirementAccount var1) throws ValidationException;

   VirementAccountsEditor getAccountsEditor();

   void setTranLimit(double var1) throws ValidationException;

   void setTotalLimitIn(double var1) throws ValidationException;

   void setTotalLimitOut(double var1) throws ValidationException;
}
