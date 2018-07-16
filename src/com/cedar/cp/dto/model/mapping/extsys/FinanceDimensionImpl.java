// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping.extsys;

import com.cedar.cp.api.model.mapping.extsys.FinanceDimension;
import com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup;
import com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy;
import com.cedar.cp.api.model.mapping.extsys.FinanceLedger;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.mapping.extsys.ExternalEntityImpl;
import java.io.Serializable;
import java.util.List;

public class FinanceDimensionImpl extends ExternalEntityImpl implements FinanceDimension, Serializable {

   private int mDimensionType;
   private String mExtSysDimensionType;
   private String mDimensionMask;
   private boolean mDummy;
   private FinanceLedger mFinanceLedger;
   private List<FinanceDimensionElementGroup> mFinanceDimensionElementsGroups;
   private List<FinanceHierarchy> mFinanceHierarchies;
   private String mSuggestedCPVisId;
   private boolean mIsMandatory;


   public FinanceDimensionImpl(String dimVisId, String descr) {
      this.setEntityRef(new EntityRefImpl(new MappingKeyImpl(dimVisId), descr));
   }

   public String getDimensionVisId() {
      return (String)((MappingKeyImpl)this.getEntityRef().getPrimaryKey()).get(0);
   }

   public int getDimensionType() {
      return this.mDimensionType;
   }

   public String getExtSysDimensionType() {
      return this.mExtSysDimensionType;
   }

   public String getDimensionMask() {
      return this.mDimensionMask;
   }

   public boolean isDummy() {
      return this.mDummy;
   }

   public List<FinanceDimensionElementGroup> getFinanceDimensionElementGroups() {
      return this.mFinanceDimensionElementsGroups;
   }

   public List<FinanceHierarchy> getFinanceHierarchies() {
      return this.mFinanceHierarchies;
   }

   public void setDimensionType(int dimensionType) {
      this.mDimensionType = dimensionType;
   }

   public void setExtSysDimensionType(String dimensionType) {
      this.mExtSysDimensionType = dimensionType;
   }

   public void setDimensionMask(String dimMask) {
      this.mDimensionMask = dimMask;
   }

   public void setDummy(boolean dummy) {
      this.mDummy = dummy;
   }

   public void setFinanceDimensionElementsGroups(List<FinanceDimensionElementGroup> financeDimensionElementsGroups) {
      this.mFinanceDimensionElementsGroups = financeDimensionElementsGroups;
   }

   public void setFinanceHierarchies(List<FinanceHierarchy> financeHierarchies) {
      this.mFinanceHierarchies = financeHierarchies;
   }

   public void setFinanceLedger(FinanceLedger financeLedger) {
      this.mFinanceLedger = financeLedger;
   }

   public FinanceLedger getFinanceLedger() {
      return this.mFinanceLedger;
   }

   public void setSuggestedCPVisId(String suggestedCPVisId) {
      this.mSuggestedCPVisId = suggestedCPVisId;
   }

   public String getSuggestedCPVisId() {
      return this.getFinanceLedger().getFinanceCompany().getSuggestedCPModelVisId() + this.mSuggestedCPVisId;
   }

   public void setIsMandatory(boolean mandatory) {
      this.mIsMandatory = mandatory;
   }

   public boolean isMandatory() {
      return this.mIsMandatory;
   }

   public boolean equals(Object obj) {
      if(this == obj) {
         return true;
      } else if(!(obj instanceof FinanceDimension)) {
         return false;
      } else {
         FinanceDimension other = (FinanceDimension)obj;
         return this.getEntityRef().equals(other.getEntityRef()) && this.mExtSysDimensionType.equals(other.getExtSysDimensionType()) && this.mDimensionType == other.getDimensionType() && this.mDimensionMask == other.getDimensionMask() && this.mDummy == other.isDummy() && this.mFinanceLedger == other.getFinanceLedger();
      }
   }
}
