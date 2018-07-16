// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.CalendarSpecRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.dimension.CalendarSpecCK;
import com.cedar.cp.dto.dimension.CalendarSpecPK;
import java.io.Serializable;

public class CalendarSpecRefImpl extends EntityRefImpl implements CalendarSpecRef, Serializable {

   public CalendarSpecRefImpl(CalendarSpecCK key, String narrative) {
      super(key, narrative);
   }

   public CalendarSpecRefImpl(CalendarSpecPK key, String narrative) {
      super(key, narrative);
   }

   public CalendarSpecPK getCalendarSpecPK() {
      return this.mKey instanceof CalendarSpecCK?((CalendarSpecCK)this.mKey).getCalendarSpecPK():(CalendarSpecPK)this.mKey;
   }
}
