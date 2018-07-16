package com.cedar.cp.dto.model.globalmapping2.extsys;

import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.globalmapping2.extsys.ExternalEntityImpl;
import java.io.Serializable;
import java.util.List;

public class FinanceDimensionElementImpl extends ExternalEntityImpl implements FinanceDimensionElement, Serializable {

	private FinanceHierarchyElement mFinanceHierarchyElement;
	private FinanceDimension mFinanceDimension;
	private FinanceDimensionElementGroup mFinanceDimensionElementGroup;
	private List<FinanceCompany> mFinanceCompanies;
	
	public FinanceDimensionElementImpl(String elem, String accType, String descr) {
		this.setEntityRef(new EntityRefImpl(new MappingKeyImpl(elem, (Object) null, accType), descr));
	}

	public FinanceDimensionElementGroup getFinanceDimensionElementGroup() {
		return this.mFinanceDimensionElementGroup;
	}

	public void setFinanceDimensionElementGroup(FinanceDimensionElementGroup financeDimensionElementGroup) {
		this.mFinanceDimensionElementGroup = financeDimensionElementGroup;
	}

	public FinanceDimension getFinanceDimension() {
		return this.mFinanceDimension == null ? (this.mFinanceDimensionElementGroup != null ? this.mFinanceDimensionElementGroup.getFinanceDimension() : this.mFinanceHierarchyElement.getFinanceHierarchy().getFinanceDimension()) : this.mFinanceDimension;
	}

	public void setFinanceDimension(FinanceDimension financeDimension) {
		this.mFinanceDimension = financeDimension;
	}

	public FinanceHierarchyElement getFinanceHierarchyElement() {
		return this.mFinanceHierarchyElement;
	}

	public void setFinanceHierarchyElement(FinanceHierarchyElement financeHierarchyElement) {
		this.mFinanceHierarchyElement = financeHierarchyElement;
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
