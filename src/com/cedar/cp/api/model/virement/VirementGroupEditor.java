// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.virement.VirementGroup;
import com.cedar.cp.api.model.virement.VirementLineEditor;
import com.cedar.cp.api.model.virement.VirementRequest;

public interface VirementGroupEditor extends SubBusinessEditor {

   void setNotes(String var1);

   void remove(VirementRequest var1, Object var2) throws ValidationException;

   VirementLineEditor getEditor(Object var1);

   VirementGroup getVirementGroup();

   Object decodeLineKey(String var1);
}
