// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.api.model.mapping.MappedFinanceCubeRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubeCK;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubePK;
import java.io.Serializable;

public class MappedFinanceCubeRefImpl extends EntityRefImpl implements MappedFinanceCubeRef, Serializable {

   public MappedFinanceCubeRefImpl(MappedFinanceCubeCK key, String narrative) {
      super(key, narrative);
   }

   public MappedFinanceCubeRefImpl(MappedFinanceCubePK key, String narrative) {
      super(key, narrative);
   }

   public MappedFinanceCubePK getMappedFinanceCubePK() {
      return this.mKey instanceof MappedFinanceCubeCK?((MappedFinanceCubeCK)this.mKey).getMappedFinanceCubePK():(MappedFinanceCubePK)this.mKey;
   }
}
