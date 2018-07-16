// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.LevelDateRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.LevelDateCK;
import com.cedar.cp.dto.model.LevelDatePK;
import java.io.Serializable;

public class LevelDateRefImpl extends EntityRefImpl implements LevelDateRef, Serializable {

   public LevelDateRefImpl(LevelDateCK key, String narrative) {
      super(key, narrative);
   }

   public LevelDateRefImpl(LevelDatePK key, String narrative) {
      super(key, narrative);
   }

   public LevelDatePK getLevelDatePK() {
      return this.mKey instanceof LevelDateCK?((LevelDateCK)this.mKey).getLevelDatePK():(LevelDatePK)this.mKey;
   }
}
