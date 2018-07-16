package com.cedar.cp.api.model.globalmapping2;

import com.cedar.cp.api.dimension.calendar.Calendar;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarYear;
import com.cedar.cp.util.xmlform.XMLWritable;

import java.io.Serializable;
import java.util.List;

public interface MappedCalendar extends Serializable, XMLWritable {

   Calendar getCalendar();

   List<MappedCalendarYear> getMappedCalendarYears();

   MappedCalendarYear findMappedCalendarYear(int var1);
   
   void setCompanies(String companies);
}
