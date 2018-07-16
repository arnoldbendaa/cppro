// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.FinanceCube;
import com.cedar.cp.api.model.RollUpRule;
import java.util.List;

public interface RollUpRuleLine {

   FinanceCube getFinanceCube();

   DataTypeRef getDataType();

   List<RollUpRule> getRollUpRules();

   boolean rollsUp(DimensionRef var1);
}
