// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.mapping.MappedCalendar;
import com.cedar.cp.api.model.mapping.MappedDimension;
import com.cedar.cp.api.model.mapping.MappedFinanceCube;
import com.cedar.cp.api.model.mapping.MappedHierarchy;
import com.cedar.cp.api.model.mapping.MappedModel;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.mapping.MappedCalendarImpl;
import com.cedar.cp.dto.model.mapping.MappedDimensionImpl;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubeImpl;
import com.cedar.cp.dto.model.mapping.MappedHierarchyImpl;
import com.cedar.cp.dto.model.mapping.MappedModelPK;
import com.cedar.cp.util.XmlUtils;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MappedModelImpl implements MappedModel, Serializable, Cloneable {

   private String mModelVisId;
   private String mModelDescription;
   private List<MappedDimension> mDimensionMappings = new ArrayList();
   private List<MappedFinanceCube> mMappedFinanceCubes = new ArrayList();
   private MappedCalendarImpl mMappedCalendar = new MappedCalendarImpl();
   private int mNextKeyId = -1;
   private Object mPrimaryKey;
   private int mModelId;
   private int mExternalSystemId;
   private String mCompanyVisId;
   private String mLedgerVisId;
   private int mVersionNum;
   private ModelRef mOwningModelRef;
   private ExternalSystemRef mExternalSystemRef;


   public MappedModelImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mModelId = 0;
      this.mExternalSystemId = 0;
      this.mCompanyVisId = "";
      this.mLedgerVisId = "";
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (MappedModelPK)paramKey;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getExternalSystemId() {
      return this.mExternalSystemId;
   }

   public String getCompanyVisId() {
      return this.mCompanyVisId;
   }

   public String getLedgerVisId() {
      return this.mLedgerVisId;
   }

   public ModelRef getOwningModelRef() {
      return this.mOwningModelRef;
   }

   public ExternalSystemRef getExternalSystemRef() {
      return this.mExternalSystemRef;
   }

   public void setOwningModelRef(ModelRef ref) {
      this.mOwningModelRef = ref;
   }

   public void setExternalSystemRef(ExternalSystemRef ref) {
      this.mExternalSystemRef = ref;
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

   public void setExternalSystemId(int paramExternalSystemId) {
      this.mExternalSystemId = paramExternalSystemId;
   }

   public void setCompanyVisId(String paramCompanyVisId) {
      this.mCompanyVisId = paramCompanyVisId;
   }

   public void setLedgerVisId(String paramLedgerVisId) {
      this.mLedgerVisId = paramLedgerVisId;
   }

   public boolean isNew() {
      return this.mPrimaryKey == null;
   }

   public String getModelVisId() {
      return this.mModelVisId;
   }

   public void setModelVisId(String modelVisId) {
      this.mModelVisId = modelVisId;
   }

   public String getModelDescription() {
      return this.mModelDescription;
   }

   public void setModelDescription(String modelDescription) {
      this.mModelDescription = modelDescription;
   }

   public List<MappedDimension> getDimensionMappings() {
      return this.mDimensionMappings;
   }

   public MappedDimension findMappedDimension(MappingKey financeDimensionKey) {
      Iterator i$ = this.mDimensionMappings.iterator();

      MappedDimension md;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         md = (MappedDimension)i$.next();
      } while(!md.getFinanceDimensionKey().equals(financeDimensionKey));

      return md;
   }

   public MappedDimensionImpl findMappedDimension(Object key) {
      Iterator i$ = this.mDimensionMappings.iterator();

      MappedDimension md;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         md = (MappedDimension)i$.next();
      } while(!md.getKey().equals(key));

      return (MappedDimensionImpl)md;
   }

   public MappedFinanceCube findMappedFinanceCube(Object key) {
      Iterator i$ = this.mMappedFinanceCubes.iterator();

      MappedFinanceCube mfc;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         mfc = (MappedFinanceCube)i$.next();
      } while(!mfc.getKey().equals(key));

      return mfc;
   }

   public boolean isRespAreaHierarchySelected() {
      return this.getResponisbilityAreaHierarchy() != null;
   }

   public MappedHierarchyImpl getResponisbilityAreaHierarchy() {
      Iterator i$ = this.mDimensionMappings.iterator();

      while(i$.hasNext()) {
         MappedDimension md = (MappedDimension)i$.next();
         Iterator i$1 = md.getMappedHierarchies().iterator();

         while(i$1.hasNext()) {
            MappedHierarchy mh = (MappedHierarchy)i$1.next();
            if(mh.isResponsibilityAreaHierarchy()) {
               return (MappedHierarchyImpl)mh;
            }
         }
      }

      return null;
   }

   public int getNextKey() {
      return --this.mNextKeyId;
   }

   public void setMappedCalendar(MappedCalendarImpl mappedCalendar) {
      this.mMappedCalendar = mappedCalendar;
   }

   public MappedCalendar getMappedCalendar() {
      return this.mMappedCalendar;
   }

   public List<MappedFinanceCube> getMappedFinanceCubes() {
      return this.mMappedFinanceCubes;
   }

   public void setMappedFinanceCubes(List<MappedFinanceCube> mappedFinanceCubes) {
      this.mMappedFinanceCubes = mappedFinanceCubes;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<mappedModel ");
      XmlUtils.outputAttribute(out, "modelVisId", this.mModelVisId);
      XmlUtils.outputAttribute(out, "companyVisId", this.mCompanyVisId);
      XmlUtils.outputAttribute(out, "ledgerVisId", this.mLedgerVisId);
      XmlUtils.outputAttribute(out, "modelDescription", this.mModelDescription);
      XmlUtils.outputAttribute(out, "modelId", Integer.valueOf(this.mModelId));
      XmlUtils.outputAttribute(out, "externalSystemRef", this.mExternalSystemRef);
      out.write(">\n");
      XmlUtils.outputElement(out, "dimensionMappings", (List)this.mDimensionMappings);
      XmlUtils.outputElement(out, "mappedFinanceCubes", (List)this.mMappedFinanceCubes);
      this.mMappedCalendar.writeXml(out);
      out.write("</mappedModel>\n");
   }

   public MappingKey getFinanceCompanyKey() {
      return this.mCompanyVisId == null?null:new MappingKeyImpl(this.mCompanyVisId);
   }

   public MappingKey getFinanceLedgerKey() {
      return this.mLedgerVisId == null?null:new MappingKeyImpl(this.mLedgerVisId);
   }

   public void checkMappedDataTypeYearOffsets() {
      if(this.mMappedFinanceCubes != null) {
         Iterator i$ = this.mMappedFinanceCubes.iterator();

         while(i$.hasNext()) {
            MappedFinanceCube mfc = (MappedFinanceCube)i$.next();
            MappedFinanceCubeImpl mfcImpl = (MappedFinanceCubeImpl)mfc;
            mfcImpl.validateCalendarYearIndexes();
         }
      }

   }
}
