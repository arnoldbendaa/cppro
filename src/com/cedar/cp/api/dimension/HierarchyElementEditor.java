// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension;

import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyElement;

public interface HierarchyElementEditor extends SubBusinessEditor {

   void setCreditDebit(int var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   HierarchyElement getHierarchyElement();

   void setAugCreditDebit(int var1) throws ValidationException;
}
