// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.cc.CcDeploymentLine;
import java.io.Serializable;
import java.util.List;

public interface CcMappingLine extends Serializable {

   Object getKey();

   DataTypeRef getDataType();

   CcDeploymentLine getDeploymentLine();

   String getFormField();

   List<StructureElementRef> getEntries();
}
