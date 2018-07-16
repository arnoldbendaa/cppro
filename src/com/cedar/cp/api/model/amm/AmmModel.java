// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.amm;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.amm.AmmDimensionMapping;
import com.cedar.cp.api.model.amm.AmmFinanceCubeMapping;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.util.List;

public interface AmmModel {

   Object getPrimaryKey();

   int getModelId();

   int getSrcModelId();

   Integer getInvalidatedByTaskId();

   ModelRef getTargetModelRef();

   ModelRef getSourceModelRef();

   boolean isNew();

   boolean isModelLocked();

   boolean isDimsLocked();

   int getMappingIndex();

   AmmDimensionMapping getMapping(int var1);

   AmmDimensionMapping getNextMapping();

   List<AmmDimensionMapping> getDimMappings();

   List<AmmFinanceCubeMapping> getFinanceCubeMappings();

   CalendarInfo getSourceInfo();
}
