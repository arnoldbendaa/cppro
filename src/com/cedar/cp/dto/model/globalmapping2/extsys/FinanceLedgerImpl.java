package com.cedar.cp.dto.model.globalmapping2.extsys;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCalendarYear;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.globalmapping2.extsys.ExternalEntityImpl;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class FinanceLedgerImpl extends ExternalEntityImpl implements FinanceLedger, Serializable {

	private boolean mDummy;
	private List<FinanceCalendarYear> mFinanceCalendarYears;
	private List<FinanceCompany> mFinanceCompanies;
	private List<FinanceDimension> mFinanceDimensions;
	private List<FinanceValueType> mFinanceValueTypes;
	private List<FinanceValueTypeOwner> mFinanceValueTypeOwners;
	private List<FinanceDimension> mSelectedDimensions;
	private int mFirstMappedYear;

	public FinanceLedgerImpl(String ledgerVisId, String descr) {
		this.setEntityRef(new EntityRefImpl(new MappingKeyImpl(ledgerVisId), descr));
	}

	public String getLedgerVisId() {
		return (String) ((MappingKeyImpl) this.getEntityRef().getPrimaryKey()).get(0);
	}

	public boolean isDummy() {
		return this.mDummy;
	}

	public List<FinanceDimension> getFinanceDimensions() {
		return this.mFinanceDimensions;
	}

	public void setDummy(boolean dummy) {
		this.mDummy = dummy;
	}

	public void setFinanceDimensions(List<FinanceDimension> financeDimensions) {
		this.mFinanceDimensions = financeDimensions;
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

	public List<FinanceDimension> getSelectedDimensions() {
		return this.mSelectedDimensions;
	}

	public void setSelectedDimensions(List<FinanceDimension> selectedDimensions) {
		this.mSelectedDimensions = selectedDimensions;
		this.mFinanceValueTypes = null;
	}

	public List<FinanceValueType> getFinanceValueTypes() {
		return this.mFinanceValueTypes;
	}

	public void setFinanceValueTypes(List<FinanceValueType> financeValueTypes) {
		this.mFinanceValueTypes = financeValueTypes;
	}

	public List<FinanceValueTypeOwner> getFinanceValueTypeOwners() {
		return this.mFinanceValueTypeOwners;
	}

	public void setFinanceValueTypeOwners(List<FinanceValueTypeOwner> financeValueTypeOwners) {
		this.mFinanceValueTypeOwners = financeValueTypeOwners;
	}

	public int getFirstMappedYear() {
		return this.mFirstMappedYear;
	}

	public void setFirstMappedYear(int firstMappedYear) {
		if (this.mFirstMappedYear != firstMappedYear) {
			this.mFirstMappedYear = firstMappedYear;
			this.mFinanceValueTypes = null;
			this.mFinanceValueTypeOwners = null;
		}

	}

	public FinanceDimension findFinanceDimension(String name) {
		Iterator i$ = this.mFinanceDimensions.iterator();

		FinanceDimension fd;
		do {
			if (!i$.hasNext()) {
				return null;
			}

			fd = (FinanceDimension) i$.next();
		} while (!fd.getDimensionVisId().equals(name));

		return fd;
	}

	public FinanceDimension findFinanceDimension(MappingKey mk) {
		Iterator i$ = this.mFinanceDimensions.iterator();

		FinanceDimension fd;
		do {
			if (!i$.hasNext()) {
				return null;
			}

			fd = (FinanceDimension) i$.next();
		} while (!fd.getEntityRef().getPrimaryKey().equals(mk));

		return fd;
	}

	public List<FinanceCalendarYear> getFinanceCalendarYears() {
		return this.mFinanceCalendarYears;
	}

	public void setFinanceCalendarYears(List<FinanceCalendarYear> financeCalendarYears) {
		this.mFinanceCalendarYears = financeCalendarYears;
	}
}
