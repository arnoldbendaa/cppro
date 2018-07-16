// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.cc;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.cc.CcDeployment;
import com.cedar.cp.api.model.cc.CcDeploymentLine;
import com.cedar.cp.api.model.cc.CcMappingLine;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.model.cc.CcDeploymentLinePK;
import com.cedar.cp.dto.model.cc.CcMappingLineImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class CcDeploymentLineImpl implements CcDeploymentLine, Cloneable {

   private CcDeployment mDeployment;
   private CcDeploymentLinePK mKey;
   private int mIndex;
   private int mCalendarLevel;
   private Map<DimensionRef, Map<StructureElementRef, Boolean>> mDeploymentEntries;
   private Set<DataTypeRef> mDeploymentDataTypes;
   private List<CcMappingLine> mMappingLines;


   public CcDeploymentLineImpl(CcDeploymentLinePK key) {
      this.mKey = key;
      this.mDeploymentEntries = new HashMap();
      this.mMappingLines = new ArrayList();
   }

   public CcDeploymentLineImpl(CcDeployment deployment, CcDeploymentLinePK key, int index, int calendarLevel, Map<DimensionRef, Map<StructureElementRef, Boolean>> deploymentEntries, Set<DataTypeRef> deploymentDataTypes, List<CcMappingLine> mappingLines) {
      this.mDeployment = deployment;
      this.mKey = key;
      this.mIndex = index;
      this.mCalendarLevel = calendarLevel;
      this.mDeploymentEntries = deploymentEntries;
      this.mDeploymentDataTypes = deploymentDataTypes;
      this.mMappingLines = mappingLines;
   }

   public Object getKey() {
      return this.mKey;
   }

   public int getIndex() {
      return this.mIndex;
   }

   public void setIndex(int index) {
      this.mIndex = index;
   }

   public DimensionRef[] getDimensionRefs() {
      return this.mDeployment.getDimensionRefs();
   }

   public int getCalendarLevel() {
      return this.mCalendarLevel;
   }

   public void setCalendarLevel(int level) {
      this.mCalendarLevel = level;
   }

   public void setDeployment(CcDeployment deployment) {
      this.mDeployment = deployment;
   }

   public void setDeploymentEntries(Map<DimensionRef, Map<StructureElementRef, Boolean>> deploymentEntries) {
      this.mDeploymentEntries = deploymentEntries;
   }

   public void setMappingLines(List<CcMappingLine> mappingLines) {
      this.mMappingLines = mappingLines;
   }

   public Map<DimensionRef, Map<StructureElementRef, Boolean>> getDeploymentEntries() {
      return this.mDeploymentEntries;
   }

   public CcMappingLine getMappingLine(Object key) {
      Iterator i$ = this.mMappingLines.iterator();

      CcMappingLine line;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         line = (CcMappingLine)i$.next();
      } while(!line.getKey().equals(key));

      return line;
   }

   public List<CcMappingLine> getMappingLines() {
      return this.mMappingLines;
   }

   public CcDeployment getDeployment() {
      return this.mDeployment;
   }

   public Object clone() throws CloneNotSupportedException {
      CcDeploymentLineImpl copy = (CcDeploymentLineImpl)super.clone();
      copy.mDeployment = this.mDeployment;
      copy.mKey = this.mKey;
      copy.mIndex = this.mIndex;
      copy.mCalendarLevel = this.mCalendarLevel;
      copy.mDeploymentEntries = new HashMap();
      Iterator i$ = this.mDeploymentEntries.entrySet().iterator();

      while(i$.hasNext()) {
         Entry mappingLine = (Entry)i$.next();
         DimensionRef mappingLineCopy = (DimensionRef)mappingLine.getKey();
         copy.mDeploymentEntries.put(mappingLineCopy, new HashMap((Map)mappingLine.getValue()));
      }

      copy.mDeploymentDataTypes = new HashSet();
      copy.mDeploymentDataTypes.addAll(this.mDeploymentDataTypes);
      copy.mMappingLines = new ArrayList();
      i$ = this.mMappingLines.iterator();

      while(i$.hasNext()) {
         CcMappingLine mappingLine1 = (CcMappingLine)i$.next();
         CcMappingLineImpl mappingLineCopy1 = (CcMappingLineImpl)((CcMappingLineImpl)mappingLine1).clone();
         mappingLineCopy1.setDeploymentLine(copy);
         copy.mMappingLines.add(mappingLineCopy1);
      }

      return copy;
   }

   public Set<DataTypeRef> getDeploymentDataTypes() {
      return this.mDeploymentDataTypes;
   }

   public void setDeploymentDataTypes(Set<DataTypeRef> deploymentDataTypes) {
      this.mDeploymentDataTypes = deploymentDataTypes;
   }

   public DataTypeRef findDataTypeDeployment(int dataTypeId) {
      Iterator i$ = this.mDeploymentDataTypes.iterator();

      DataTypeRef dt;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         dt = (DataTypeRef)i$.next();
      } while(((DataTypeRefImpl)dt).getDataTypePK().getDataTypeId() != dataTypeId);

      return dt;
   }
}
