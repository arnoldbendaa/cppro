// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.ModelRef;
import java.util.List;

public interface SecurityRange {

   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   DimensionRef getDimensionRef();

   ModelRef getModelRef();

   List getRanges();
}
