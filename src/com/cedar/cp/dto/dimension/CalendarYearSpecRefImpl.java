// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.CalendarYearSpecRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.dimension.CalendarYearSpecCK;
import com.cedar.cp.dto.dimension.CalendarYearSpecPK;
import java.io.Serializable;

public class CalendarYearSpecRefImpl extends EntityRefImpl implements CalendarYearSpecRef, Serializable {

   public CalendarYearSpecRefImpl(CalendarYearSpecCK key, String narrative) {
      super(key, narrative);
   }

   public CalendarYearSpecRefImpl(CalendarYearSpecPK key, String narrative) {
      super(key, narrative);
   }

   public CalendarYearSpecPK getCalendarYearSpecPK() {
      return this.mKey instanceof CalendarYearSpecCK?((CalendarYearSpecCK)this.mKey).getCalendarYearSpecPK():(CalendarYearSpecPK)this.mKey;
   }
}
