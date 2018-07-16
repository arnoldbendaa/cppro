package com.cedar.cp.dto.model.globalmapping2.extsys;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.globalmapping2.extsys.ExternalEntityImpl;
import java.io.Serializable;
import java.util.List;

public class FinanceHierarchyImpl extends ExternalEntityImpl implements FinanceHierarchy, Serializable {

   private List<FinanceCompany> mFinanceCompanies;
   private List<FinanceHierarchyElement> mFinanceHierarchyElements;
   private List<FinanceDimensionElement> mFinanceDimensionElements;
   private int mType;
   private FinanceDimension mFinanceDimension;
   private String mSuggestedCPVisId;
   private boolean mIsDummy;
   private boolean mIsMandatory;


   public FinanceHierarchyImpl(String hierarchyName, String hierarchyType, String descr) {
      this.setEntityRef(new EntityRefImpl(new MappingKeyImpl(hierarchyName, hierarchyType), descr));
   }

   public String getHierarchyName() {
      return (String)((MappingKeyImpl)this.getEntityRef().getPrimaryKey()).get(0);
   }

   public String getHierarchyType() {
      return (String)((MappingKeyImpl)this.getEntityRef().getPrimaryKey()).get(1);
   }

   public FinanceHierarchyImpl(EntityRef entityRef, FinanceDimension financeDimension, List<FinanceHierarchyElement> financeHierarchyElements, List<FinanceDimensionElement> financeDimensionElements) {
      super(entityRef);
      this.mFinanceDimension = financeDimension;
      this.mFinanceDimensionElements = financeDimensionElements;
      this.mFinanceHierarchyElements = financeHierarchyElements;
   }

   public List<FinanceHierarchyElement> getFinanceHierarchyElements() {
      return this.mFinanceHierarchyElements;
   }

   public void setFinanceHierarchyElements(List<FinanceHierarchyElement> financeHierarchyElements) {
      this.mFinanceHierarchyElements = financeHierarchyElements;
   }

   public List<FinanceDimensionElement> getFinanceDimensionElements() {
      return this.mFinanceDimensionElements;
   }

   public void setFinanceDimensionElements(List<FinanceDimensionElement> financeDimensionElements) {
      this.mFinanceDimensionElements = financeDimensionElements;
   }

   public FinanceDimension getFinanceDimension() {
      return this.mFinanceDimension;
   }

   public void setFinanceDimension(FinanceDimension financeDimension) {
      this.mFinanceDimension = financeDimension;
   }

   public void setSuggestedCPVisId(String suggestedCPVisId) {
      this.mSuggestedCPVisId = suggestedCPVisId;
   }

   public String getSuggestedCPVisId() {
      return this.mSuggestedCPVisId;
   }

   public void setIsDummy(boolean b) {
      this.mIsDummy = b;
   }

   public boolean isDummy() {
      return this.mIsDummy;
   }

   public void setIsMandatory(boolean b) {
      this.mIsMandatory = b;
   }

   public boolean isMandatory() {
      return this.mIsMandatory;
   }

   public boolean equals(Object obj) {
      if(obj == this) {
         return true;
      } else if(obj instanceof FinanceHierarchy) {
         FinanceHierarchy other = (FinanceHierarchy)obj;
         return this.getEntityRef().equals(other.getEntityRef());
      } else {
         return super.equals(obj);
      }
   }

   public int hashCode() {
      return this.getEntityRef().hashCode();
   }
   
	public void setFinanceCompanies(List<FinanceCompany> financeCompanies) {
		this.mFinanceCompanies = financeCompanies;
	}

	public void addFinanceCompany(FinanceCompany financeCompany) {
		this.mFinanceCompanies.add(financeCompany);
	}

	public void removeFinanceCompany(FinanceCompany financeCompany) {
		this.mFinanceCompanies.remove(financeCompany);
	}

	public List<FinanceCompany> getFinanceCompanies() {
		return this.mFinanceCompanies;
	}
}
