// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.mapping.MappedCalendarYear;
import com.cedar.cp.api.model.mapping.MappedDataType;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubeImpl;
import com.cedar.cp.util.XmlUtils;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.List;

public class MappedDataTypeImpl implements MappedDataType, Serializable, Cloneable {

   private MappedFinanceCubeImpl mMappedFinanceCube;
   private Object mKey;
   private DataTypeRef mDataTypeRef;
   private String mExtSysValueType;
   private String mExtSysCurrency;
   private String mExtSysBalType;
   private int mImpExpStatus;
   private Integer mImpStartYearOffset;
   private Integer mImpEndYearOffset;
   private Integer mExpStartYearOffset;
   private Integer mExpEndYearOffset;


   public MappedDataTypeImpl(MappedFinanceCubeImpl mappedFinanceCube) {
      this.mMappedFinanceCube = mappedFinanceCube;
   }

   public MappedDataTypeImpl(MappedFinanceCubeImpl mappedFinanceCube, Object key, DataTypeRefImpl dataTypeRef, String extSysValueType, String extSysCurrency, String extSysBalType, int impExpStatus, Integer impStartYearOffset, Integer impEndYearOffset, Integer expStartYearOffset, Integer expEndYearOffset) {
      this(mappedFinanceCube);
      this.mKey = key;
      this.mDataTypeRef = dataTypeRef;
      this.mExtSysValueType = extSysValueType;
      this.mExtSysCurrency = extSysCurrency;
      this.mExtSysBalType = extSysBalType;
      this.mImpExpStatus = impExpStatus;
      this.mImpStartYearOffset = impStartYearOffset;
      this.mImpEndYearOffset = impEndYearOffset;
      this.mExpStartYearOffset = expStartYearOffset;
      this.mExpEndYearOffset = expEndYearOffset;
   }

   public String getExtSysBalType() {
      return this.mExtSysBalType;
   }

   public void setExtSysBalType(String extSysBalType) {
      this.mExtSysBalType = extSysBalType;
   }

   public String getExtSysCurrency() {
      return this.mExtSysCurrency;
   }

   public void setExtSysCurrency(String extSysCurrency) {
      this.mExtSysCurrency = extSysCurrency;
   }

   public String getExtSysValueType() {
      return this.mExtSysValueType;
   }

   public void setExtSysValueType(String extSysValueType) {
      this.mExtSysValueType = extSysValueType;
   }

   public DataTypeRef getDataTypeRef() {
      return this.mDataTypeRef;
   }

   public void setDataTypeRef(DataTypeRef dataTypeRef) {
      this.mDataTypeRef = dataTypeRef;
   }

   public Object getKey() {
      return this.mKey;
   }

   public void setKey(Object key) {
      this.mKey = key;
   }

   public int getImpExpStatus() {
      return this.mImpExpStatus;
   }

   public void setImpExpStatus(int impExpStatus) {
      this.mImpExpStatus = impExpStatus;
   }

   public Integer getImpStartYearOffset() {
      return this.mImpStartYearOffset;
   }

   public void setImpStartYearOffset(Integer impStartYearOffset) {
      this.mImpStartYearOffset = impStartYearOffset;
   }

   public Integer getImpEndYearOffset() {
      return this.mImpEndYearOffset;
   }

   public void setImpEndYearOffset(Integer impEndYearOffset) {
      this.mImpEndYearOffset = impEndYearOffset;
   }

   public Integer getExpStartYearOffset() {
      return this.mExpStartYearOffset;
   }

   public void setExpStartYearOffset(Integer expStartYearOffset) {
      this.mExpStartYearOffset = expStartYearOffset;
   }

   public Integer getExpEndYearOffset() {
      return this.mExpEndYearOffset;
   }

   public void setExpEndYearOffset(Integer expEndYearOffset) {
      this.mExpEndYearOffset = expEndYearOffset;
   }

   public MappedCalendarYear getImpStartYear() {
      return this.getImpStartYearOffset() != null?this.getYearForOffset(this.getImpStartYearOffset().intValue()):null;
   }

   public MappedCalendarYear getImpEndYear() {
      return this.getImpEndYearOffset() != null?this.getYearForOffset(this.getImpEndYearOffset().intValue()):null;
   }

   public MappedCalendarYear getExpStartYear() {
      return this.getExpStartYearOffset() != null?this.getYearForOffset(this.getExpStartYearOffset().intValue()):null;
   }

   public MappedCalendarYear getExpEndYear() {
      return this.getExpEndYearOffset() != null?this.getYearForOffset(this.getExpEndYearOffset().intValue()):null;
   }

   private List<MappedCalendarYear> getMappedCalendarYears() {
      return this.getMappedFinanceCube() != null && this.getMappedFinanceCube().getMappedModel() != null && this.getMappedFinanceCube().getMappedModel().getMappedCalendar() != null?this.getMappedFinanceCube().getMappedModel().getMappedCalendar().getMappedCalendarYears():null;
   }

   private MappedCalendarYear getYearForOffset(int offset) {
      List years = this.getMappedCalendarYears();
      if(years != null && !years.isEmpty()) {
         if(offset > 0) {
            return null;
         } else {
            int index = years.size() - 1 + offset;
            return (MappedCalendarYear)years.get(index);
         }
      } else {
         return null;
      }
   }

   public Object clone() throws CloneNotSupportedException {
      MappedDataTypeImpl clone = (MappedDataTypeImpl)super.clone();
      clone.mMappedFinanceCube = this.mMappedFinanceCube;
      clone.mKey = this.mKey;
      clone.mDataTypeRef = this.mDataTypeRef;
      clone.mExtSysValueType = this.mExtSysValueType;
      clone.mExtSysCurrency = this.mExtSysCurrency;
      clone.mExtSysBalType = this.mExtSysBalType;
      clone.mImpExpStatus = this.mImpExpStatus;
      clone.mImpStartYearOffset = this.mImpStartYearOffset;
      clone.mImpEndYearOffset = this.mImpEndYearOffset;
      clone.mExpStartYearOffset = this.mExpStartYearOffset;
      clone.mExpEndYearOffset = this.mExpEndYearOffset;
      return clone;
   }

   public void writeXml(Writer out) throws IOException {
      out.write(" <mappedDataType ");
      XmlUtils.outputAttribute(out, "dataType", this.mDataTypeRef);
      XmlUtils.outputAttribute(out, "extSysValueType", this.mExtSysValueType);
      XmlUtils.outputAttribute(out, "extSysBalType", this.mExtSysBalType);
      XmlUtils.outputAttribute(out, "extSysCurrency", this.mExtSysCurrency);
      XmlUtils.outputAttribute(out, "impExpStatus", Integer.valueOf(this.mImpExpStatus));
      XmlUtils.outputAttribute(out, "impStartYearOffset", this.mImpStartYearOffset);
      XmlUtils.outputAttribute(out, "impEndYearOffset", this.mImpEndYearOffset);
      XmlUtils.outputAttribute(out, "expStartYearOffset", this.mExpStartYearOffset);
      XmlUtils.outputAttribute(out, "expEndYearOffset", this.mExpEndYearOffset);
      XmlUtils.outputAttribute(out, "impStartYear", this.mImpStartYearOffset != null?Integer.valueOf(this.getYearForOffset(this.mImpStartYearOffset.intValue()).getYear()):"");
      XmlUtils.outputAttribute(out, "impEndYear", this.mImpEndYearOffset != null?Integer.valueOf(this.getYearForOffset(this.mImpEndYearOffset.intValue()).getYear()):"");
      XmlUtils.outputAttribute(out, "expStartYear", this.mExpStartYearOffset != null?Integer.valueOf(this.getYearForOffset(this.mExpStartYearOffset.intValue()).getYear()):"");
      XmlUtils.outputAttribute(out, "expEndYear", this.mExpEndYearOffset != null?Integer.valueOf(this.getYearForOffset(this.mExpEndYearOffset.intValue()).getYear()):"");
      out.write(" />\n ");
   }

   public void setMappedFinanceCube(MappedFinanceCubeImpl mappedFinanceCube) {
      this.mMappedFinanceCube = mappedFinanceCube;
   }

   public MappedFinanceCubeImpl getMappedFinanceCube() {
      return this.mMappedFinanceCube;
   }
}
