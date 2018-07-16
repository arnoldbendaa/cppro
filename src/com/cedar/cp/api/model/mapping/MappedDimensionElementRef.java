// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping;

import com.cedar.cp.api.base.EntityRef;

public interface MappedDimensionElementRef extends EntityRef {

   int MAPPING_TYPE_SINGLE = 0;
   int MAPPING_TYPE_PREFIX = 1;
   int MAPPING_TYPE_RANGE = 2;
   int MAPPING_TYPE_HIERARCHY = 3;

}
