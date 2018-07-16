// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.xmlform;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.ModelRef;
import java.util.List;

public interface CellPickerInfo {

   ModelRef getModelRef();

   List<DimensionRef> getDimensions();

   List<HierarchyRef> getHierarchies(DimensionRef var1);

   List<DataTypeRef> getDataTypes();

   String getDescription(DimensionRef var1);

   String getDescription(HierarchyRef var1);
}
