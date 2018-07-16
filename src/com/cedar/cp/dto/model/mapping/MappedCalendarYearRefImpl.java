// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.api.model.mapping.MappedCalendarYearRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.mapping.MappedCalendarYearCK;
import com.cedar.cp.dto.model.mapping.MappedCalendarYearPK;
import java.io.Serializable;

public class MappedCalendarYearRefImpl extends EntityRefImpl implements MappedCalendarYearRef, Serializable {

   public MappedCalendarYearRefImpl(MappedCalendarYearCK key, String narrative) {
      super(key, narrative);
   }

   public MappedCalendarYearRefImpl(MappedCalendarYearPK key, String narrative) {
      super(key, narrative);
   }

   public MappedCalendarYearPK getMappedCalendarYearPK() {
      return this.mKey instanceof MappedCalendarYearCK?((MappedCalendarYearCK)this.mKey).getMappedCalendarYearPK():(MappedCalendarYearPK)this.mKey;
   }
}
