package com.cedar.cp.dto.model.globalmapping2;

import com.cedar.cp.api.dimension.calendar.Calendar;
import com.cedar.cp.api.model.globalmapping2.MappedCalendar;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarYear;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.util.XmlUtils;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MappedCalendarImpl implements MappedCalendar, Serializable {

    private CalendarImpl mCalendar;
    private List<MappedCalendarYear> mMappedCalendarYears;

    public MappedCalendarImpl() {
        this.mCalendar = new CalendarImpl();
        this.mMappedCalendarYears = new ArrayList();
    }

    public MappedCalendarImpl(CalendarImpl calendar, List<MappedCalendarYear> mappedCalendarYear) {
        this.mCalendar = calendar;
        this.mMappedCalendarYears = mappedCalendarYear;
    }

    public List<MappedCalendarYear> getMappedCalendarYears() {
        return this.mMappedCalendarYears;
    }

    public Calendar getCalendar() {
        return this.mCalendar;
    }

    public MappedCalendarYear findMappedCalendarYear(int year) {
        Iterator i$ = this.mMappedCalendarYears.iterator();

        MappedCalendarYear mcy;
        do {
            if (!i$.hasNext()) {
                return null;
            }

            mcy = (MappedCalendarYear) i$.next();
        } while (mcy.getYear() != year);

        return mcy;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void writeXml(Writer out) throws IOException {
        out.write("<mappedCalendar>\n");
        this.mCalendar.writeXml(out);
        XmlUtils.outputElement(out, "mappedCalendarYears", (List) this.mMappedCalendarYears);
        out.write("</mappedCalendar>\n");
    }
    
    public void setCompanies(String companies) {
        if (this.mMappedCalendarYears != null && this.mMappedCalendarYears.size() > 0) {
            for (MappedCalendarYear cmappedCalendarYear : this.mMappedCalendarYears) {
                cmappedCalendarYear.setCompanies(companies);
            }
        }
    }
}
