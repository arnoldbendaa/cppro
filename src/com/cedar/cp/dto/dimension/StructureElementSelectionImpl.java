// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.StructureElementKey;
import java.io.Serializable;

public class StructureElementSelectionImpl implements Serializable {

   private StructureElementKey mStructureElementKey;
   private boolean mSelection;


   public StructureElementSelectionImpl(StructureElementKey structureElementKey, boolean selection) {
      this.mStructureElementKey = structureElementKey;
      this.mSelection = selection;
   }

   public boolean isSelection() {
      return this.mSelection;
   }

   public void setSelection(boolean selection) {
      this.mSelection = selection;
   }

   public boolean equals(Object obj) {
      if(obj == this) {
         return true;
      } else if(obj instanceof StructureElementKey) {
         return this.mStructureElementKey.equals(obj);
      } else if(!(obj instanceof StructureElementSelectionImpl)) {
         return false;
      } else {
         StructureElementSelectionImpl other = (StructureElementSelectionImpl)obj;
         return this.mStructureElementKey.equals(other.mStructureElementKey) && this.mSelection == other.mSelection;
      }
   }

   public int hashCode() {
      return this.mStructureElementKey.hashCode();
   }

   public StructureElementKey getStructureElementKey() {
      return this.mStructureElementKey;
   }
}
