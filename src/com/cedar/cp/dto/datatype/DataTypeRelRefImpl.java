// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.datatype;

import com.cedar.cp.api.datatype.DataTypeRelRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.datatype.DataTypeRelCK;
import com.cedar.cp.dto.datatype.DataTypeRelPK;
import java.io.Serializable;

public class DataTypeRelRefImpl extends EntityRefImpl implements DataTypeRelRef, Serializable {

   public DataTypeRelRefImpl(DataTypeRelCK key, String narrative) {
      super(key, narrative);
   }

   public DataTypeRelRefImpl(DataTypeRelPK key, String narrative) {
      super(key, narrative);
   }

   public DataTypeRelPK getDataTypeRelPK() {
      return this.mKey instanceof DataTypeRelCK?((DataTypeRelCK)this.mKey).getDataTypeRelPK():(DataTypeRelPK)this.mKey;
   }
}
