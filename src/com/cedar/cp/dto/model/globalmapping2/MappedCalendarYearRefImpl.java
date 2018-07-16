package com.cedar.cp.dto.model.globalmapping2;

import com.cedar.cp.api.model.globalmapping2.MappedCalendarYearRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedCalendarYearCK;
import com.cedar.cp.dto.model.globalmapping2.MappedCalendarYearPK;
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
