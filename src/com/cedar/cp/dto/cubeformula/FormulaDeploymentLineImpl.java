// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.cubeformula;

import com.cedar.cp.api.cubeformula.FormulaDeploymentLine;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.util.Pair;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class FormulaDeploymentLineImpl implements FormulaDeploymentLine, Serializable, Cloneable {

   private DimensionRef[] mDimensionRefs;
   private Object mKey;
   private String mContext;
   private int mLineIndex;
   private Map<DimensionRef, Map<Pair<StructureElementRef, StructureElementRef>, Boolean>> mDeploymentEntries;
   private Set<DataTypeRef> mDeploymentDataTypes;


   public FormulaDeploymentLineImpl(DimensionRef[] dimensionRefs) {
      this.mDeploymentEntries = new HashMap();
      this.mDeploymentDataTypes = new HashSet();
      this.mDimensionRefs = dimensionRefs;
      DimensionRef[] arr$ = dimensionRefs;
      int len$ = dimensionRefs.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         DimensionRef dimensionRef = arr$[i$];
         this.mDeploymentEntries.put(dimensionRef, new HashMap());
      }

      this.mLineIndex = -1;
   }

   public FormulaDeploymentLineImpl(DimensionRef[] dimensionRefs, Object key, String context, int lineIndex, Map<DimensionRef, Map<Pair<StructureElementRef, StructureElementRef>, Boolean>> deploymentEntries, Set<DataTypeRef> deploymentDataTypes) {
      this(dimensionRefs);
      this.mKey = key;
      this.mContext = context;
      this.mLineIndex = lineIndex;
      this.mDeploymentEntries = deploymentEntries;
      this.mDeploymentDataTypes = deploymentDataTypes;
   }

   public Object getKey() {
      return this.mKey;
   }

   public DimensionRef[] getDimensionRefs() {
      return this.mDimensionRefs;
   }

   public int getLineIndex() {
      return this.mLineIndex;
   }

   public Map<DimensionRef, Map<Pair<StructureElementRef, StructureElementRef>, Boolean>> getDeploymentEntries() {
      return this.mDeploymentEntries;
   }

   public Set<DataTypeRef> getDeploymentDataTypes() {
      return this.mDeploymentDataTypes;
   }

   public void setKey(Object key) {
      this.mKey = key;
   }

   public void setLineIndex(int index) {
      this.mLineIndex = index;
   }

   public void setDeploymentEntries(Map<DimensionRef, Map<Pair<StructureElementRef, StructureElementRef>, Boolean>> deploymentEntries) {
      this.mDeploymentEntries = deploymentEntries;
   }

   public void setDeploymentDataTypes(Set<DataTypeRef> deploymentDataTypes) {
      this.mDeploymentDataTypes = deploymentDataTypes;
   }

   public String getContext() {
      return this.mContext;
   }

   public void setContext(String context) {
      this.mContext = context;
   }

   public Object clone() throws CloneNotSupportedException {
      FormulaDeploymentLineImpl clone = new FormulaDeploymentLineImpl(this.mDimensionRefs);
      clone.setKey(this.mKey);
      clone.setContext(this.mContext);
      clone.setLineIndex(this.mLineIndex);
      Iterator i$ = this.mDeploymentEntries.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         clone.getDeploymentEntries().put((DimensionRef) entry.getKey(), new HashMap((Map)entry.getValue()));
      }

      clone.getDeploymentDataTypes().addAll(this.mDeploymentDataTypes);
      return clone;
   }

   public DataTypeRef findDataTypeDeployment(int dataTypeId) {
      Iterator i$ = this.mDeploymentDataTypes.iterator();

      DataTypeRef dataTypeRef;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         dataTypeRef = (DataTypeRef)i$.next();
      } while(((DataTypeRefImpl)dataTypeRef).getDataTypePK().getDataTypeId() != dataTypeId);

      return dataTypeRef;
   }
}
