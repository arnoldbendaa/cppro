package com.cedar.cp.dto.model.globalmapping2.extsys;

import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.globalmapping2.extsys.ExternalEntityImpl;
import java.io.Serializable;
import java.util.List;

public class FinanceHierarchyElementImpl extends ExternalEntityImpl implements FinanceHierarchyElement, Serializable {

   private List<FinanceHierarchyElement> mFinanceHierarchyElements;
   private List<FinanceDimensionElement> mFinanceDimensionElements;
   private FinanceHierarchy mFinanceHierarchy;
   private List<FinanceCompany> mFinanceCompanies;

   public FinanceHierarchyElementImpl(String elem, String hierName, String hierType, String descr) {
      this.setEntityRef(new EntityRefImpl(new MappingKeyImpl(elem, hierName, hierType), descr));
   }

   public String getHierarchyElem() {
      return (String)((MappingKeyImpl)this.getEntityRef().getPrimaryKey()).get(0);
   }

   public List<FinanceHierarchyElement> getFinanceHierarchyElements() {
      return this.mFinanceHierarchyElements;
   }

   public List<FinanceDimensionElement> getFinanceDimensionElements() {
      return this.mFinanceDimensionElements;
   }

   public void setFinanceHierarchyElements(List<FinanceHierarchyElement> financeHierarchyElements) {
      this.mFinanceHierarchyElements = financeHierarchyElements;
   }

   public void setFinanceDimensionElements(List<FinanceDimensionElement> financeDimensionElements) {
      this.mFinanceDimensionElements = financeDimensionElements;
   }

   public FinanceHierarchy getFinanceHierarchy() {
      return this.mFinanceHierarchy;
   }

   public void setFinanceHierarchy(FinanceHierarchy financeHierarchy) {
      this.mFinanceHierarchy = financeHierarchy;
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
