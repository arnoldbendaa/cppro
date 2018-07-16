// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.xmlform.CellPickerInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CellPickerInfoImpl implements CellPickerInfo {

   private ModelRef mModelRef;
   private List<DimensionRef> mDimensions = new ArrayList();
   private Map<DimensionRef, List<HierarchyRef>> mHierarchyMap = new HashMap();
   private Map<DimensionRef, String> mDimensionDescrMap = new HashMap();
   private Map<HierarchyRef, String> mHierarchyDescrMap = new HashMap();
   private List<DataTypeRef> mDataTypes = new ArrayList();


   public List<DimensionRef> getDimensions() {
      return this.mDimensions;
   }

   public List<HierarchyRef> getHierarchies(DimensionRef dimensionRef) {
      return (List)this.mHierarchyMap.get(dimensionRef);
   }

   public List<DataTypeRef> getDataTypes() {
      return this.mDataTypes;
   }

   public String getDescription(DimensionRef dimensionRef) {
      return (String)this.mDimensionDescrMap.get(dimensionRef);
   }

   public String getDescription(HierarchyRef hierarchyRef) {
      return (String)this.mHierarchyDescrMap.get(hierarchyRef);
   }

   public Map<DimensionRef, List<HierarchyRef>> getHierarchyMap() {
      return this.mHierarchyMap;
   }

   public Map<DimensionRef, String> getDimensionDescrMap() {
      return this.mDimensionDescrMap;
   }

   public Map<HierarchyRef, String> getHierarchyDescrMap() {
      return this.mHierarchyDescrMap;
   }

   public ModelRef getModelRef() {
      return this.mModelRef;
   }

   public void setModelRef(ModelRef modelRef) {
      this.mModelRef = modelRef;
   }
}
