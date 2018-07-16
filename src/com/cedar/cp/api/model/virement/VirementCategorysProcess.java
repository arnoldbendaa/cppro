// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementCategoryEditorSession;

public interface VirementCategorysProcess extends BusinessProcess {

   EntityList getAllVirementCategorys();

   VirementCategoryEditorSession getVirementCategoryEditorSession(Object var1) throws ValidationException;

   void validateVirementPosting(boolean var1, ModelRef var2, FinanceCubeRef var3, String var4, String var5, double var6, double var8) throws ValidationException;
}
