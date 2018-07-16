// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension;

import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.SecurityRangeRow;

public interface SecurityRangeRowEditor extends SubBusinessEditor {

   SecurityRangeRow getSecurityRangeRow();

   void setFrom(String var1) throws ValidationException;

   void setTo(String var1) throws ValidationException;
}
