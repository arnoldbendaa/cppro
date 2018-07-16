// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.RollUpRuleLine;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface FinanceCube {

   int SCALE_FACTOR = 10000;


   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   Integer getLockedByTaskId();

   boolean isHasData();

   boolean isAudited();

   boolean isCubeFormulaEnabled();

   ModelRef getModelRef();

   Map<DataTypeRef, Timestamp> getSelectedDataTypeRefs();

   List<RollUpRuleLine> getRollUpRuleLines();

   RollUpRuleLine getRollUpRuleLine(DataTypeRef var1);

   DimensionRef[] getDimensions();

   boolean isNew();

   boolean isChangeManagementOutstanding();

   Timestamp getUpdatedTime();
}
