// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.virement;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.virement.VirementCategoryCK;
import com.cedar.cp.dto.model.virement.VirementCategoryEditorSessionCSO;
import com.cedar.cp.dto.model.virement.VirementCategoryEditorSessionSSO;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface VirementCategoryEditorSessionLocal extends EJBLocalObject {

   VirementCategoryEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   VirementCategoryEditorSessionSSO getNewItemData(int var1) throws EJBException;

   VirementCategoryCK insert(VirementCategoryEditorSessionCSO var1) throws ValidationException, EJBException;

   EntityList getOwnershipData(int var1, Object var2) throws EJBException;

   VirementCategoryCK copy(VirementCategoryEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(VirementCategoryEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   void validateVirementPosting(boolean var1, ModelRef var2, FinanceCubeRef var3, String var4, String var5, double var6, double var8) throws ValidationException, EJBException;
}
