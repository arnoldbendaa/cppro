// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.ra;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;

public interface ResponsibilityArea {

   int AUTH_INHERIT_PARENT = 0;
   int AUTH_REQUIRED = 1;
   int AUTH_NOT_REQUIRED = 2;


   Object getPrimaryKey();

   int getModelId();

   int getStructureId();

   int getStructureElementId();

   int getVirementAuthStatus();

   ModelRef getModelRef();

   StructureElementRef getOwningStructureElementRef();
}
