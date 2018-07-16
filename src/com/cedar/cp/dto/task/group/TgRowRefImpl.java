// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.task.group;

import com.cedar.cp.api.task.group.TgRowRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.task.group.TgRowCK;
import com.cedar.cp.dto.task.group.TgRowPK;
import java.io.Serializable;

public class TgRowRefImpl extends EntityRefImpl implements TgRowRef, Serializable {

   public TgRowRefImpl(TgRowCK key, String narrative) {
      super(key, narrative);
   }

   public TgRowRefImpl(TgRowPK key, String narrative) {
      super(key, narrative);
   }

   public TgRowPK getTgRowPK() {
      return this.mKey instanceof TgRowCK?((TgRowCK)this.mKey).getTgRowPK():(TgRowPK)this.mKey;
   }
}
