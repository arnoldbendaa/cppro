// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.cubeformula;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.util.Pair;
import java.util.Map;
import java.util.Set;

public interface FormulaDeploymentLine {

   Object getKey();

   DimensionRef[] getDimensionRefs();

   int getLineIndex();

   String getContext();

   Map<DimensionRef, Map<Pair<StructureElementRef, StructureElementRef>, Boolean>> getDeploymentEntries();

   Set<DataTypeRef> getDeploymentDataTypes();
}
