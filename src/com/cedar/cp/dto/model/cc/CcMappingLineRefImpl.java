// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.cc;

import com.cedar.cp.api.model.cc.CcMappingLineRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.cc.CcMappingLineCK;
import com.cedar.cp.dto.model.cc.CcMappingLinePK;
import java.io.Serializable;

public class CcMappingLineRefImpl extends EntityRefImpl implements CcMappingLineRef, Serializable {

   public CcMappingLineRefImpl(CcMappingLineCK key, String narrative) {
      super(key, narrative);
   }

   public CcMappingLineRefImpl(CcMappingLinePK key, String narrative) {
      super(key, narrative);
   }

   public CcMappingLinePK getCcMappingLinePK() {
      return this.mKey instanceof CcMappingLineCK?((CcMappingLineCK)this.mKey).getCcMappingLinePK():(CcMappingLinePK)this.mKey;
   }
}
