package com.cedar.cp.dto.model.globalmapping2;

import com.cedar.cp.api.model.globalmapping2.MappedFinanceCubeRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedFinanceCubeCK;
import com.cedar.cp.dto.model.globalmapping2.MappedFinanceCubePK;
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
