package com.cedar.cp.dto.model.globalmapping2;

import com.cedar.cp.api.model.globalmapping2.MappedCalendarElementRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedCalendarElementCK;
import com.cedar.cp.dto.model.globalmapping2.MappedCalendarElementPK;
import java.io.Serializable;

public class MappedCalendarElementRefImpl extends EntityRefImpl implements MappedCalendarElementRef, Serializable {

   public MappedCalendarElementRefImpl(MappedCalendarElementCK key, String narrative) {
      super(key, narrative);
   }

   public MappedCalendarElementRefImpl(MappedCalendarElementPK key, String narrative) {
      super(key, narrative);
   }

   public MappedCalendarElementPK getMappedCalendarElementPK() {
      return this.mKey instanceof MappedCalendarElementCK?((MappedCalendarElementCK)this.mKey).getMappedCalendarElementPK():(MappedCalendarElementPK)this.mKey;
   }
}
