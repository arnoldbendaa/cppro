package com.cedar.cp.dto.model.globalmapping2.extsys;

import com.cedar.cp.api.model.globalmapping2.extsys.ExternalSystem;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCalendarYear;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.globalmapping2.extsys.ExternalEntityImpl;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class FinanceCompanyImpl extends ExternalEntityImpl implements FinanceCompany, Serializable {

	private boolean mDummy;
	private List<FinanceLedger> mFinanceLedgers;

	private ExternalSystem mExternalSystem;
	private String mSuggestedCPModelVisId;
	private HashMap<String, String> mAccountTypes;
	private HashMap<String, String> mHierarchyTypes;

	public FinanceCompanyImpl(String companyVisId, String descr, HashMap<String, String> accTypesMap, HashMap<String, String> hierTypesMap) {
		this.setEntityRef(new EntityRefImpl(new MappingKeyImpl(companyVisId), descr));
		this.mAccountTypes = accTypesMap;
		this.mHierarchyTypes = hierTypesMap;
	}

	public String getCompanyVisId() {
		return (String) ((MappingKeyImpl) this.getEntityRef().getPrimaryKey()).get(0);
	}

	public boolean isDummy() {
		return this.mDummy;
	}

	public List<FinanceLedger> getFinanceLedgers() {
		return this.mFinanceLedgers;
	}

	public void setDummy(boolean dummy) {
		this.mDummy = dummy;
	}

	public void setFinanceLedgers(List<FinanceLedger> financeLedgers) {
		this.mFinanceLedgers = financeLedgers;
	}

	public ExternalSystem getExternalSystem() {
		return this.mExternalSystem;
	}

	public String getSuggestedCPModelVisId() {
		return this.mSuggestedCPModelVisId;
	}

	public String getSuggestedCPCalVisId() {
		return this.getSuggestedCPModelVisId() + "-Cal";
	}

	public String getSuggestedCPCubeVisId() {
		return this.getSuggestedCPModelVisId() + "-Cube";
	}

	public void setSuggestedCPModelVisId(String s) {
		this.mSuggestedCPModelVisId = s;
	}

	public String getAccountTypeText(String accType) {
		return (String) this.mAccountTypes.get(accType);
	}

	public String getHierarchyTypeText(String hierType) {
		return (String) this.mHierarchyTypes.get(hierType);
	}

	public void setExternalSystem(ExternalSystem externalSystem) {
		this.mExternalSystem = externalSystem;
	}
}
