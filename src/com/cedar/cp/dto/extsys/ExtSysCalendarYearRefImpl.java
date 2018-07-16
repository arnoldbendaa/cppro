// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.ExtSysCalendarYearRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.extsys.ExtSysCalendarYearCK;
import com.cedar.cp.dto.extsys.ExtSysCalendarYearPK;
import java.io.Serializable;

public class ExtSysCalendarYearRefImpl extends EntityRefImpl implements ExtSysCalendarYearRef, Serializable {

   public ExtSysCalendarYearRefImpl(ExtSysCalendarYearCK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysCalendarYearRefImpl(ExtSysCalendarYearPK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysCalendarYearPK getExtSysCalendarYearPK() {
      return this.mKey instanceof ExtSysCalendarYearCK?((ExtSysCalendarYearCK)this.mKey).getExtSysCalendarYearPK():(ExtSysCalendarYearPK)this.mKey;
   }
}
