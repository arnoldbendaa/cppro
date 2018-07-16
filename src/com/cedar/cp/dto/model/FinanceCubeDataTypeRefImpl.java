// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.FinanceCubeDataTypeRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.FinanceCubeDataTypeCK;
import com.cedar.cp.dto.model.FinanceCubeDataTypePK;
import java.io.Serializable;

public class FinanceCubeDataTypeRefImpl extends EntityRefImpl implements FinanceCubeDataTypeRef, Serializable {

   public FinanceCubeDataTypeRefImpl(FinanceCubeDataTypeCK key, String narrative) {
      super(key, narrative);
   }

   public FinanceCubeDataTypeRefImpl(FinanceCubeDataTypePK key, String narrative) {
      super(key, narrative);
   }

   public FinanceCubeDataTypePK getFinanceCubeDataTypePK() {
      return this.mKey instanceof FinanceCubeDataTypeCK?((FinanceCubeDataTypeCK)this.mKey).getFinanceCubeDataTypePK():(FinanceCubeDataTypePK)this.mKey;
   }
}
