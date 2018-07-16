// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.cc.CcDeployment;
import com.cedar.cp.api.model.cc.CcMappingLine;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CcDeploymentLine extends Serializable {

   Object getKey();

   DimensionRef[] getDimensionRefs();

   int getIndex();

   int getCalendarLevel();

   Map<DimensionRef, Map<StructureElementRef, Boolean>> getDeploymentEntries();

   Set<DataTypeRef> getDeploymentDataTypes();

   List<CcMappingLine> getMappingLines();

   CcDeployment getDeployment();
}
