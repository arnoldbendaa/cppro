// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.virement.VirementLine;
import java.util.List;

public interface VirementLineEditor extends SubBusinessEditor {

   void setTransferValue(double var1);

   void setDataTypeRef(DataTypeRef var1);

   void setTo(boolean var1);

   void setAddress(List var1) throws ValidationException;

   VirementLine getVirementLine();

   void setRepeatValue(Double var1);

   void setSpreadProfile(StructureElementRef var1, boolean var2, int var3) throws ValidationException;

   void setSpreadProfile(String var1, boolean var2, int var3) throws ValidationException;

   void setSpreadProfileKey(Object var1) throws ValidationException;
}
