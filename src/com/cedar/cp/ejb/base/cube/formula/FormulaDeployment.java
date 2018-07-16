// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.formula;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.ejb.base.cube.formula.FormulaDeployment$ElementReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FormulaDeployment {

   private DimensionRef[] mDimensions;
   private Map<DimensionRef, Set<FormulaDeployment$ElementReference>> mElements;
   private Set<String> mDataTypes;


   public FormulaDeployment(DimensionRef[] dimensions) {
      this.mDimensions = dimensions;
      this.mElements = new HashMap();
      DimensionRef[] arr$ = dimensions;
      int len$ = dimensions.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         DimensionRef dimensionRef = arr$[i$];
         this.mElements.put(dimensionRef, new HashSet());
      }

      this.mDataTypes = new HashSet();
   }

   public Map<DimensionRef, Set<FormulaDeployment$ElementReference>> getElements() {
      return this.mElements;
   }

   public boolean addElement(DimensionRef dimensionRef, FormulaDeployment$ElementReference element) {
      return ((Set)this.mElements.get(dimensionRef)).add(element);
   }

   public DimensionRef[] getDimensions() {
      return this.mDimensions;
   }
}
