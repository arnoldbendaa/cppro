package com.cedar.cp.dto.model.globalmapping2.extsys;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import java.io.Serializable;
import java.util.List;

public class FinanceDimensionElementGroupImpl implements FinanceDimensionElementGroup, Serializable {

	private EntityRef mEntityRef;
	private List<FinanceDimensionElementGroup> mFinanceDimensionElementGroups;
	private List<FinanceDimensionElement> mFinanceDimensionElements;
	private FinanceDimensionElementGroup mFinanceDimensionElementGroup;
	private FinanceDimension mFinanceDimension;
	private String mExtSysAccountType;
	private int mGroupType;
	private Integer mMappingType;
	private List<FinanceCompany> mFinanceCompanies;

	public FinanceDimensionElementGroupImpl(String elem, String accType, String descr) {
		this.setEntityRef(new EntityRefImpl(new MappingKeyImpl(elem, (Object) null, accType), descr));
	}

	public String getElem() {
		return (String) ((MappingKeyImpl) this.getEntityRef().getPrimaryKey()).get(0);
	}

	public String getExtSysAccountType() {
		return (String) ((MappingKeyImpl) this.getEntityRef().getPrimaryKey()).get(2);
	}

	public List<FinanceDimensionElementGroup> getFinanceDimensionElementGroups() {
		return this.mFinanceDimensionElementGroups;
	}

	public List<FinanceDimensionElement> getFinanceDimensionElements() {
		return this.mFinanceDimensionElements;
	}

	public EntityRef getEntityRef() {
		return this.mEntityRef;
	}

	public void setEntityRef(EntityRef entityRef) {
		this.mEntityRef = entityRef;
	}

	public void setFinanceDimensionElements(List<FinanceDimensionElement> financeDimensionElements) {
		this.mFinanceDimensionElements = financeDimensionElements;
	}

	public void setFinanceDimensionElementGroups(List<FinanceDimensionElementGroup> financeDimensionElementGroups) {
		this.mFinanceDimensionElementGroups = financeDimensionElementGroups;
	}

	public FinanceDimension getFinanceDimension() {
		return this.mFinanceDimension;
	}

	public FinanceDimensionElementGroup getFinanceDimensionElementGroup() {
		return this.mFinanceDimensionElementGroup;
	}

	public void setFinanceDimensionElementGroup(FinanceDimensionElementGroup group) {
		this.mFinanceDimensionElementGroup = group;
	}

	public void setFinanceDimension(FinanceDimension financeDimension) {
		this.mFinanceDimension = financeDimension;
	}

	public int getGroupType() {
		return this.mGroupType;
	}

	public void setGroupType(int groupType) {
		this.mGroupType = groupType;
	}

	public Integer getMappingType() {
		return this.mMappingType;
	}

	public void setMappingType(Integer mappingType) {
		this.mMappingType = mappingType;
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
