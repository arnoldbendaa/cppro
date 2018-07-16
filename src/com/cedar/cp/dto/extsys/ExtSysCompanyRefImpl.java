// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.ExtSysCompanyRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.extsys.ExtSysCompanyCK;
import com.cedar.cp.dto.extsys.ExtSysCompanyPK;
import java.io.Serializable;

public class ExtSysCompanyRefImpl extends EntityRefImpl implements ExtSysCompanyRef, Serializable {

   public ExtSysCompanyRefImpl(ExtSysCompanyCK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysCompanyRefImpl(ExtSysCompanyPK key, String narrative) {
      super(key, narrative);
   }

   public ExtSysCompanyPK getExtSysCompanyPK() {
      return this.mKey instanceof ExtSysCompanyCK?((ExtSysCompanyCK)this.mKey).getExtSysCompanyPK():(ExtSysCompanyPK)this.mKey;
   }
}
