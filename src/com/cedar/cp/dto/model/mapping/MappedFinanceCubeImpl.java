// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.api.model.mapping.MappedDataType;
import com.cedar.cp.api.model.mapping.MappedFinanceCube;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.mapping.MappedCalendarImpl;
import com.cedar.cp.dto.model.mapping.MappedDataTypeImpl;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubePK;
import com.cedar.cp.dto.model.mapping.MappedModelImpl;
import com.cedar.cp.util.XmlUtils;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MappedFinanceCubeImpl implements MappedFinanceCube, Serializable, Cloneable {

   private MappedModelImpl mMappedModel;
   private FinanceCubePK mFinanceCubePK;
   private Object mKey;
   private String mName;
   private String mDescription;
   private List<MappedDataType> mMappedDataTypes;


   public MappedFinanceCubeImpl(MappedModelImpl mappedModel) {
      this.mMappedModel = mappedModel;
      this.mMappedDataTypes = new ArrayList();
   }

   public MappedFinanceCubeImpl(MappedModelImpl mappedModel, Object key, String name, String description, List<MappedDataType> mappedDataTypes, FinanceCubePK financeCubePK) {
      this(mappedModel);
      this.mKey = key;
      this.mName = name;
      this.mDescription = description;
      this.mMappedDataTypes = mappedDataTypes;
      this.mFinanceCubePK = financeCubePK;
      Iterator i$ = this.mMappedDataTypes.iterator();

      while(i$.hasNext()) {
         MappedDataType mdt = (MappedDataType)i$.next();
         ((MappedDataTypeImpl)mdt).setMappedFinanceCube(this);
      }

   }

   public Object getKey() {
      return this.mKey;
   }

   public void setKey(Object key) {
      this.mKey = key;
   }

   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public List<MappedDataType> getMappedDataTypes() {
      return this.mMappedDataTypes;
   }

   public void setMappedDataTypes(List<MappedDataType> mappedDataTypes) {
      this.mMappedDataTypes = mappedDataTypes;
   }

   public MappedDataType findMappedDataType(Object key) {
      Iterator i$ = this.mMappedDataTypes.iterator();

      MappedDataType mdt;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         mdt = (MappedDataType)i$.next();
      } while(!mdt.getKey().equals(key));

      return mdt;
   }

   public Object clone() throws CloneNotSupportedException {
      MappedFinanceCubeImpl clone = (MappedFinanceCubeImpl)super.clone();
      clone.mMappedModel = this.mMappedModel;
      clone.mKey = this.mKey;
      clone.mName = this.mName;
      clone.mDescription = this.mDescription;
      clone.mMappedDataTypes = new ArrayList();
      if(this.mMappedDataTypes != null) {
         Iterator i$ = this.mMappedDataTypes.iterator();

         while(i$.hasNext()) {
            MappedDataType mdt = (MappedDataType)i$.next();
            clone.mMappedDataTypes.add((MappedDataType)((MappedDataTypeImpl)mdt).clone());
         }
      }

      return clone;
   }

   public boolean isNew() {
      return this.mKey == null || this.mKey instanceof MappedFinanceCubePK && ((MappedFinanceCubePK)this.mKey).getMappedFinanceCubeId() < 0;
   }

   public MappedModelImpl getMappedModel() {
      return this.mMappedModel;
   }

   public FinanceCubePK getFinanceCubePK() {
      return this.mFinanceCubePK;
   }

   public void writeXml(Writer out) throws IOException {
      out.write(" <mappedFinanceCube ");
      XmlUtils.outputAttribute(out, "name", this.mName);
      XmlUtils.outputAttribute(out, "description", this.mDescription);
      out.write(" >\n");
      XmlUtils.outputElement(out, "mappedDataTypes", (List)this.mMappedDataTypes);
      out.write(" </mappedFinanceCube>\n");
   }

   public void validateCalendarYearIndexes() {
      MappedCalendarImpl mci = (MappedCalendarImpl)this.mMappedModel.getMappedCalendar();
      int size = mci.getMappedCalendarYears().size();
      Iterator dtIter = this.mMappedDataTypes.iterator();

      while(dtIter.hasNext()) {
         MappedDataTypeImpl mdt = (MappedDataTypeImpl)dtIter.next();
         if(mdt.getImpStartYearOffset() != null && Math.abs(mdt.getImpStartYearOffset().intValue()) >= size) {
            mdt.setImpStartYearOffset(Integer.valueOf(-(size - 1)));
         }

         if(mdt.getImpEndYearOffset() != null && Math.abs(mdt.getImpEndYearOffset().intValue()) >= size) {
            mdt.setImpEndYearOffset(Integer.valueOf(-(size - 1)));
         }

         if(mdt.getExpStartYearOffset() != null && Math.abs(mdt.getExpStartYearOffset().intValue()) >= size) {
            mdt.setExpStartYearOffset(Integer.valueOf(-(size - 1)));
         }

         if(mdt.getExpEndYearOffset() != null && Math.abs(mdt.getExpEndYearOffset().intValue()) >= size) {
            mdt.setExpEndYearOffset(Integer.valueOf(-(size - 1)));
         }
      }

   }
}
