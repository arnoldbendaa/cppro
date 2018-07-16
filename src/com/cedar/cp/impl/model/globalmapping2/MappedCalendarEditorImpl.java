package com.cedar.cp.impl.model.globalmapping2;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.calendar.CalendarYearSpec;
import com.cedar.cp.api.model.globalmapping2.MappedCalendar;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarEditor;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarYear;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarYearEditor;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCalendarYear;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedCalendarImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedCalendarYearImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedCalendarYearPK;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.dimension.calendar.CalendarEditorImpl;
import com.cedar.cp.impl.model.globalmapping2.MappedCalendarEditorImpl$1;
import com.cedar.cp.impl.model.globalmapping2.MappedCalendarYearEditorImpl;
import com.cedar.cp.impl.model.globalmapping2.GlobalMappedModel2EditorImpl;
import com.cedar.cp.impl.model.globalmapping2.GlobalMappedModel2EditorSessionImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MappedCalendarEditorImpl extends CalendarEditorImpl implements MappedCalendarEditor {

	private MappedCalendarImpl mMappedCalendar;
	private FinanceLedger mFinanceLedger;

	public MappedCalendarEditorImpl(BusinessSessionImpl sess, MappedCalendarImpl mappedCalendar, FinanceLedger financeLedger) {
		super(sess, (CalendarImpl) mappedCalendar.getCalendar());
		this.mMappedCalendar = mappedCalendar;
		this.mFinanceLedger = financeLedger;
	}

	public MappedCalendar getMappedCalendar() {
		return this.mMappedCalendar;
	}

	public MappedCalendarYearEditor getMappedCalendarYearEditor(int year) throws ValidationException {
		FinanceCalendarYear fcy = this.findFinanceCalendarYear(year);
		String companies = "";
		for (int i=0; i < fcy.getFinanceCompanies().size(); i++) {
			if (i > 0) {
				companies = companies+",\""+fcy.getFinanceCompanies().get(i).getCompanyVisId()+"\"";
			} else {
				companies = "\""+fcy.getFinanceCompanies().get(i).getCompanyVisId()+"\"";
			}
		}
		MappedCalendarYearImpl mcy = (MappedCalendarYearImpl) this.mMappedCalendar.findMappedCalendarYear(year);
		boolean newMapping = false;
		if (mcy == null) {
			mcy = new MappedCalendarYearImpl(new MappedCalendarYearPK(-year), year, new ArrayList(), companies);
			newMapping = true;
		}

		return new MappedCalendarYearEditorImpl(this.getBusinessSession(), this, mcy, fcy, newMapping);
	}

	public FinanceCalendarYear findFinanceCalendarYear(int year) {
		Iterator i$ = mFinanceLedger.getFinanceCalendarYears().iterator();
		FinanceCalendarYear fcy;
		do {
			if (!i$.hasNext()) {
				return null;
			}
			fcy = (FinanceCalendarYear) i$.next();
		} while (fcy.getYear() != year);

		return fcy;
	}

	public void removeYear(boolean end) throws ValidationException {
		int yearRemoved = end ? this.getCalendar().getEndYear() : this.getCalendar().getStartYear();
		super.removeYear(end);
		MappedCalendarYearImpl mappedCalendarYear = (MappedCalendarYearImpl) this.mMappedCalendar.findMappedCalendarYear(yearRemoved);
		this.mMappedCalendar.getMappedCalendarYears().remove(mappedCalendarYear);
		this.setContentModified();
	}

	public void setDetail(int year, int calendarType, boolean required) throws ValidationException {
		List oldLeafElements = this.getLeafElementRefs(year);
		super.setDetail(year, calendarType, required);
		List newLeafElements = this.getLeafElementRefs(year);
		if (oldLeafElements.size() != newLeafElements.size()) {
			MappedCalendarYearImpl mappedCalendarYear = (MappedCalendarYearImpl) this.mMappedCalendar.findMappedCalendarYear(year);
			if (mappedCalendarYear != null) {
				mappedCalendarYear.setMappedCalendarElements((List) null);
				this.setContentModified();
			}
		}

	}

	public void update(MappedCalendarYearImpl updatedMappedCalendarYear) {
		MappedCalendarYearImpl origMappedCalendarYear = (MappedCalendarYearImpl) this.mMappedCalendar.findMappedCalendarYear(updatedMappedCalendarYear.getYear());
		int index;
		if (origMappedCalendarYear == null) {
			index = Collections.binarySearch(this.mMappedCalendar.getMappedCalendarYears(), updatedMappedCalendarYear, new MappedCalendarEditorImpl$1(this));
			if (index < 0) {
				index = -(index + 1);
				if (index == this.mMappedCalendar.getMappedCalendarYears().size()) {
					this.mMappedCalendar.getMappedCalendarYears().add(updatedMappedCalendarYear);
				} else {
					this.mMappedCalendar.getMappedCalendarYears().add(index, updatedMappedCalendarYear);
				}
			}
		} else {
			index = this.mMappedCalendar.getMappedCalendarYears().indexOf(origMappedCalendarYear);
			this.mMappedCalendar.getMappedCalendarYears().set(index, updatedMappedCalendarYear);
		}

		this.setContentModified();
	}

	public void commit() throws ValidationException {
		if (this.mMappedCalendar.getCalendar().getVisId() != null && this.mMappedCalendar.getCalendar().getVisId().trim().length() != 0) {
			if (this.mMappedCalendar.getCalendar().getDescription() != null && this.mMappedCalendar.getCalendar().getDescription().trim().length() != 0) {
				Iterator editor = this.mFinanceLedger.getFinanceCalendarYears().iterator();

				while (editor.hasNext()) {
					FinanceCalendarYear fcy = (FinanceCalendarYear) editor.next();
					CalendarYearSpec cys = this.getCalendar().getYearSpec(fcy.getYear());
					if (cys != null) {
						MappedCalendarYear mcy = this.getMappedCalendar().findMappedCalendarYear(cys.getYear());
						if (mcy == null) {
							throw new ValidationException("No period mapping for year:" + cys.getYear());
						}

						if (!mcy.hasMappedElements()) {
							throw new ValidationException("No mapped elements for year:" + cys.getYear());
						}

						if (mcy.hasUnmappedElements()) {
							throw new ValidationException("Unmapped elements for year:" + cys.getYear());
						}
					}
				}

				super.commit();
				GlobalMappedModel2EditorImpl editor1 = (GlobalMappedModel2EditorImpl) ((GlobalMappedModel2EditorSessionImpl) this.getBusinessSession()).getMappedModelEditor();
				editor1.getMappedModelImpl().checkMappedDataTypeYearOffsets();
			} else {
				throw new ValidationException("A calendar description must be supplied.");
			}
		} else {
			throw new ValidationException("A calendar visual id must be supplied.");
		}
	}

	public void rollback() {
	}

	@Override
	public FinanceCompany getFinanceCompany() {
		return this.mFinanceLedger.getFinanceCompanies().get(0);
	}
	
	public void setCompanies(String companies) {
	    this.mMappedCalendar.setCompanies(companies);
	    this.setContentModified();
	}
}
