// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.ra;

import com.cedar.cp.api.model.ra.ResponsibilityAreaRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaCK;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaPK;
import java.io.Serializable;

public class ResponsibilityAreaRefImpl extends EntityRefImpl implements ResponsibilityAreaRef, Serializable {

   public ResponsibilityAreaRefImpl(ResponsibilityAreaCK key, String narrative) {
      super(key, narrative);
   }

   public ResponsibilityAreaRefImpl(ResponsibilityAreaPK key, String narrative) {
      super(key, narrative);
   }

   public ResponsibilityAreaPK getResponsibilityAreaPK() {
      return this.mKey instanceof ResponsibilityAreaCK?((ResponsibilityAreaCK)this.mKey).getResponsibilityAreaPK():(ResponsibilityAreaPK)this.mKey;
   }
}
