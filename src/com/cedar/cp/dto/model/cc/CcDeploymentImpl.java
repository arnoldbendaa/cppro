// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.cc;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.cc.CcDeployment;
import com.cedar.cp.api.model.cc.CcDeploymentLine;
import com.cedar.cp.api.model.cc.CcMappingLine;
import com.cedar.cp.dto.model.cc.CcDeploymentCK;
import com.cedar.cp.dto.model.cc.CcDeploymentPK;
import com.cedar.cp.util.ValueMapping;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CcDeploymentImpl implements CcDeployment, Serializable, Cloneable {

   private ValueMapping mFormMapping;
   private List<CcDeploymentLine> mDeploymentLines;
   private DimensionRef[] mDimensionRefs;
   private DimensionRef[] mExplicitMappingDimensionRefs;
   private Object mPrimaryKey;
   private int mModelId;
   private String mVisId;
   private String mDescription;
   private int mXmlformId;
   private Integer mDimContext0;
   private Integer mDimContext1;
   private Integer mDimContext2;
   private Integer mDimContext3;
   private Integer mDimContext4;
   private Integer mDimContext5;
   private Integer mDimContext6;
   private Integer mDimContext7;
   private Integer mDimContext8;
   private Integer mDimContext9;
   private int mVersionNum;
   private ModelRef mModelRef;


   public CcDeploymentImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mModelId = 0;
      this.mVisId = "";
      this.mDescription = "";
      this.mXmlformId = 0;
      this.mDimContext0 = null;
      this.mDimContext1 = null;
      this.mDimContext2 = null;
      this.mDimContext3 = null;
      this.mDimContext4 = null;
      this.mDimContext5 = null;
      this.mDimContext6 = null;
      this.mDimContext7 = null;
      this.mDimContext8 = null;
      this.mDimContext9 = null;
      this.mDeploymentLines = new ArrayList();
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (CcDeploymentPK)paramKey;
   }

   public void setPrimaryKey(CcDeploymentCK paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getXmlformId() {
      return this.mXmlformId;
   }

   public Integer getDimContext0() {
      return this.mDimContext0;
   }

   public Integer getDimContext1() {
      return this.mDimContext1;
   }

   public Integer getDimContext2() {
      return this.mDimContext2;
   }

   public Integer getDimContext3() {
      return this.mDimContext3;
   }

   public Integer getDimContext4() {
      return this.mDimContext4;
   }

   public Integer getDimContext5() {
      return this.mDimContext5;
   }

   public Integer getDimContext6() {
      return this.mDimContext6;
   }

   public Integer getDimContext7() {
      return this.mDimContext7;
   }

   public Integer getDimContext8() {
      return this.mDimContext8;
   }

   public Integer getDimContext9() {
      return this.mDimContext9;
   }

   public ModelRef getModelRef() {
      return this.mModelRef;
   }

   public void setModelRef(ModelRef ref) {
      this.mModelRef = ref;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setModelId(int paramModelId) {
      this.mModelId = paramModelId;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setXmlformId(int paramXmlformId) {
      this.mXmlformId = paramXmlformId;
   }

   public Integer[] getDimContextArray() {
      return new Integer[]{this.getDimContext0(), this.getDimContext1(), this.getDimContext2(), this.getDimContext3(), this.getDimContext4(), this.getDimContext5(), this.getDimContext6(), this.getDimContext7(), this.getDimContext8(), this.getDimContext9()};
   }

   public void setDimContextArray(Integer[] p) {
      this.setDimContext0(p[0]);
      this.setDimContext1(p[1]);
      this.setDimContext2(p[2]);
      this.setDimContext3(p[3]);
      this.setDimContext4(p[4]);
      this.setDimContext5(p[5]);
      this.setDimContext6(p[6]);
      this.setDimContext7(p[7]);
      this.setDimContext8(p[8]);
      this.setDimContext9(p[9]);
   }

   public void setDimContext0(Integer paramDimContext0) {
      this.mDimContext0 = paramDimContext0;
   }

   public void setDimContext1(Integer paramDimContext1) {
      this.mDimContext1 = paramDimContext1;
   }

   public void setDimContext2(Integer paramDimContext2) {
      this.mDimContext2 = paramDimContext2;
   }

   public void setDimContext3(Integer paramDimContext3) {
      this.mDimContext3 = paramDimContext3;
   }

   public void setDimContext4(Integer paramDimContext4) {
      this.mDimContext4 = paramDimContext4;
   }

   public void setDimContext5(Integer paramDimContext5) {
      this.mDimContext5 = paramDimContext5;
   }

   public void setDimContext6(Integer paramDimContext6) {
      this.mDimContext6 = paramDimContext6;
   }

   public void setDimContext7(Integer paramDimContext7) {
      this.mDimContext7 = paramDimContext7;
   }

   public void setDimContext8(Integer paramDimContext8) {
      this.mDimContext8 = paramDimContext8;
   }

   public void setDimContext9(Integer paramDimContext9) {
      this.mDimContext9 = paramDimContext9;
   }

   public ValueMapping getFormMapping() {
      return this.mFormMapping;
   }

   public void setFormMapping(ValueMapping formMapping) {
      this.mFormMapping = formMapping;
   }

   public List<CcDeploymentLine> getDeploymentLines() {
      return this.mDeploymentLines;
   }

   public void setDeploymentLines(List<CcDeploymentLine> deploymentLines) {
      this.mDeploymentLines = deploymentLines;
   }

   public DimensionRef[] getDimensionRefs() {
      return this.mDimensionRefs;
   }

   public void setDimensionRefs(DimensionRef[] dimensionRefs) {
      this.mDimensionRefs = dimensionRefs;
   }

   public CcDeploymentLine getDeploymentLine(Object key) {
      Iterator i$ = this.mDeploymentLines.iterator();

      CcDeploymentLine line;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         line = (CcDeploymentLine)i$.next();
      } while(!line.getKey().equals(key));

      return line;
   }

   public CcMappingLine getMappingLine(Object key) {
      Iterator i$ = this.mDeploymentLines.iterator();

      while(i$.hasNext()) {
         CcDeploymentLine deploymentLine = (CcDeploymentLine)i$.next();
         Iterator i$1 = deploymentLine.getMappingLines().iterator();

         while(i$1.hasNext()) {
            CcMappingLine mappingLine = (CcMappingLine)i$1.next();
            if(mappingLine.getKey().equals(key)) {
               return mappingLine;
            }
         }
      }

      return null;
   }

   public CcMappingLine findMappingLine(Object key) {
      Iterator i$ = this.getAllMappingLines().iterator();

      CcMappingLine line;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         line = (CcMappingLine)i$.next();
      } while(!line.getKey().equals(key));

      return line;
   }

   public List<CcMappingLine> getAllMappingLines() {
      ArrayList allMappingLines = new ArrayList();
      Iterator i$ = this.mDeploymentLines.iterator();

      while(i$.hasNext()) {
         CcDeploymentLine deploymentLine = (CcDeploymentLine)i$.next();
         allMappingLines.addAll(deploymentLine.getMappingLines());
      }

      return allMappingLines;
   }

   public void setExplicitMappingDimensionRefs(DimensionRef[] explicitMappingDimensionRefs) {
      this.mExplicitMappingDimensionRefs = explicitMappingDimensionRefs;
   }

   public void resetExplictMappingDimensionRefs() {
      this.mExplicitMappingDimensionRefs = null;
   }

   public DimensionRef[] getExplicitMappingDimensionRefs() {
      if(this.mExplicitMappingDimensionRefs == null) {
         DimensionRef[] allDimensionRefs = this.getDimensionRefs();
         Integer[] dimContext = this.getDimContextArray();
         ArrayList mappingDimensions = new ArrayList();

         for(int i = 0; i < dimContext.length; ++i) {
            if(dimContext[i] != null && dimContext[i].intValue() == 1) {
               mappingDimensions.add(allDimensionRefs[i]);
            }
         }

         this.mExplicitMappingDimensionRefs = (DimensionRef[])mappingDimensions.toArray(new DimensionRef[0]);
      }

      return this.mExplicitMappingDimensionRefs;
   }
}
