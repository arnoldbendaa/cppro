// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.cc;

import com.cedar.cp.api.model.cc.CcMappingEntryRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.cc.CcMappingEntryCK;
import com.cedar.cp.dto.model.cc.CcMappingEntryPK;
import java.io.Serializable;

public class CcMappingEntryRefImpl extends EntityRefImpl implements CcMappingEntryRef, Serializable {

   public CcMappingEntryRefImpl(CcMappingEntryCK key, String narrative) {
      super(key, narrative);
   }

   public CcMappingEntryRefImpl(CcMappingEntryPK key, String narrative) {
      super(key, narrative);
   }

   public CcMappingEntryPK getCcMappingEntryPK() {
      return this.mKey instanceof CcMappingEntryCK?((CcMappingEntryCK)this.mKey).getCcMappingEntryPK():(CcMappingEntryPK)this.mKey;
   }
}
