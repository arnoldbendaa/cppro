// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import java.io.Serializable;

public class StructureElementRefImpl extends EntityRefImpl implements StructureElementRef, Serializable, StructureElementKey {

   public StructureElementRefImpl(StructureElementPK key, String narrative) {
      super(key, narrative);
   }

   public StructureElementPK getStructureElementPK() {
      return (StructureElementPK)this.mKey;
   }

   public int getStructureId() {
      return ((StructureElementKey)this.mKey).getStructureId();
   }

   public int getId() {
      return ((StructureElementKey)this.mKey).getId();
   }
}
