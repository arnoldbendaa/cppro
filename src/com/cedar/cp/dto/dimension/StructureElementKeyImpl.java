// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.dimension.StructureElementNode;
import java.io.Serializable;

public class StructureElementKeyImpl implements StructureElementKey, Serializable {

   private int mStructureId;
   private int mStructureElementId;


   public StructureElementKeyImpl(int structureId, int structureElementId) {
      this.mStructureId = structureId;
      this.mStructureElementId = structureElementId;
   }

   public int getStructureId() {
      return this.mStructureId;
   }

   public int getId() {
      return this.mStructureElementId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public boolean equals(Object obj) {
      if(obj instanceof StructureElementKeyImpl) {
         StructureElementKeyImpl sen1 = (StructureElementKeyImpl)obj;
         return this.mStructureId == sen1.mStructureId && this.mStructureElementId == sen1.mStructureElementId;
      } else if(!(obj instanceof StructureElementNode)) {
         return super.equals(obj);
      } else {
         StructureElementNode sen = (StructureElementNode)obj;
         return this.mStructureId == sen.getStructureId() && this.mStructureElementId == sen.getStructureElementId();
      }
   }

   public int hashCode() {
      return this.mStructureId + this.mStructureElementId;
   }
}
