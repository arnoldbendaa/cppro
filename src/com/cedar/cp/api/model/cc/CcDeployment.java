// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.cc.CcDeploymentLine;
import com.cedar.cp.api.model.cc.CcMappingLine;
import com.cedar.cp.util.ValueMapping;
import java.util.List;

public interface CcDeployment {

   int CONTEXTUAL = 0;
   int EXPLICIT = 1;
   int EMBEDDED = 2;


   Object getPrimaryKey();

   int getModelId();

   String getVisId();

   String getDescription();

   int getXmlformId();

   Integer getDimContext0();

   Integer[] getDimContextArray();

   Integer getDimContext1();

   Integer getDimContext2();

   Integer getDimContext3();

   Integer getDimContext4();

   Integer getDimContext5();

   Integer getDimContext6();

   Integer getDimContext7();

   Integer getDimContext8();

   Integer getDimContext9();

   ModelRef getModelRef();

   ValueMapping getFormMapping();

   List<CcDeploymentLine> getDeploymentLines();

   DimensionRef[] getDimensionRefs();

   DimensionRef[] getExplicitMappingDimensionRefs();

   List<CcMappingLine> getAllMappingLines();
}
