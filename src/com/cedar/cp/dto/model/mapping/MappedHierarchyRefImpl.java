// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.api.model.mapping.MappedHierarchyRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.model.mapping.MappedHierarchyCK;
import com.cedar.cp.dto.model.mapping.MappedHierarchyPK;
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
