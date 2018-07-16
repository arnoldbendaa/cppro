// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.util.xmlform.XMLWritable;

public interface MappedDimensionElement extends XMLWritable, Comparable {

   int MAP_TYPE_SINGLE = 0;
   int MAP_TYPE_PREFIX = 1;
   int MAP_TYPE_RANGE = 2;
   int MAP_TYPE_HIERARCHY = 3;


   Object getKey();

   MappingKey getFinanceDimensionElementKey();

   int getMappingType();

   String getVisId1();

   String getVisId2();

   String getVisId3();
}
