package com.cedar.cp.dto.model.globalmapping2.extsys;

import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCalendarYear;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.api.model.globalmapping2.extsys.FinancePeriod;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.globalmapping2.extsys.ExternalEntityImpl;
import java.io.Serializable;
import java.util.List;

public class FinanceCalendarYearImpl extends ExternalEntityImpl implements FinanceCalendarYear, Serializable {

	private int mYear;
	private List<FinanceCompany> mFinanceCompanies;
	private List<FinancePeriod> mFinancePeriods;

	public FinanceCalendarYearImpl(String yearVisId, String descr) {
		this.setEntityRef(new EntityRefImpl(new MappingKeyImpl(yearVisId), descr));
	}

	public String getYearVisId() {
		return (String) ((MappingKeyImpl) this.getEntityRef().getPrimaryKey()).get(0);
	}

	public int getYear() {
		return this.mYear;
	}

	public List<FinancePeriod> getFinancePeriods() {
		return this.mFinancePeriods;
	}

	public void setYear(int year) {
		this.mYear = year;
	}

	public void setFinancePeriods(List<FinancePeriod> financePeriods) {
		this.mFinancePeriods = financePeriods;
	}

	public String toString() {
		return "Year: " + this.mYear;
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
