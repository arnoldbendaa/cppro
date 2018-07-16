package com.cedar.cp.dto.model.globalmapping2;

import com.cedar.cp.api.model.globalmapping2.MappedHierarchyRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedHierarchyCK;
import com.cedar.cp.dto.model.globalmapping2.MappedHierarchyPK;
import java.io.Serializable;

public class MappedHierarchyRefImpl extends EntityRefImpl implements MappedHierarchyRef, Serializable {

   public MappedHierarchyRefImpl(MappedHierarchyCK key, String narrative) {
      super(key, narrative);
   }

   public MappedHierarchyRefImpl(MappedHierarchyPK key, String narrative) {
      super(key, narrative);
   }

   public MappedHierarchyPK getMappedHierarchyPK() {
      return this.mKey instanceof MappedHierarchyCK?((MappedHierarchyCK)this.mKey).getMappedHierarchyPK():(MappedHierarchyPK)this.mKey;
   }
}
