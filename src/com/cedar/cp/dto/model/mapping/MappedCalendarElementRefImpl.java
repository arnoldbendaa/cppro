// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.api.model.mapping.MappedCalendarElementRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.mapping.MappedCalendarElementCK;
import com.cedar.cp.dto.model.mapping.MappedCalendarElementPK;
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
