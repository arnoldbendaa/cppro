// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.model.ModelRef;

public interface SecurityAccessDef {

   int READ_ACCESS = 1;
   int WRITE_ACCESS = 2;


   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   int getAccessMode();

   String getExpression();

   ModelRef getModelRef();
}
