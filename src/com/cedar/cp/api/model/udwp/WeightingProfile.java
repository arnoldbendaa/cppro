// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.udwp;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.ModelRef;

public interface WeightingProfile {

   int WP_STATIC = 0;
   int WP_DYNAMIC = 1;
   int WP_FORCED = 2;
   int WP_REPEAT = 3;


   Object getPrimaryKey();

   int getModelId();

   String getVisId();

   String getDescription();

   int getStartLevel();

   int getLeafLevel();

   int getProfileType();

   int getDynamicOffset();

   Integer getDynamicDataTypeId();

   Boolean getDynamicEsIfWfbtoz();

   ModelRef getModelRef();

   int getNumWeightingRows();

   String getStructureElementVisId(int var1);

   String getStructureElementDescription(int var1);

   int getWeighting(int var1);

   DataTypeRef getDynamicDataType();
}
