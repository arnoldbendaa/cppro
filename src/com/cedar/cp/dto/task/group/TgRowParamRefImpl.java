// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.task.group;

import com.cedar.cp.api.task.group.TgRowParamRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.task.group.TgRowParamCK;
import com.cedar.cp.dto.task.group.TgRowParamPK;
import java.io.Serializable;

public class TgRowParamRefImpl extends EntityRefImpl implements TgRowParamRef, Serializable {

   public TgRowParamRefImpl(TgRowParamCK key, String narrative) {
      super(key, narrative);
   }

   public TgRowParamRefImpl(TgRowParamPK key, String narrative) {
      super(key, narrative);
   }

   public TgRowParamPK getTgRowParamPK() {
      return this.mKey instanceof TgRowParamCK?((TgRowParamCK)this.mKey).getTgRowParamPK():(TgRowParamPK)this.mKey;
   }
}
