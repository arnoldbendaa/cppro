// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dataEntry;

import com.cedar.cp.api.dataEntry.AvailableStructureElement;
import com.cedar.cp.util.xmlform.inputs.StructureElementReference;
import java.io.Serializable;

public class AvailableStructureElementImpl implements AvailableStructureElement, Serializable {

   private Integer mLevel;
   private StructureElementReference mStructureElement;


   public AvailableStructureElementImpl(Integer level, StructureElementReference seRef) {
      this.mLevel = level;
      this.mStructureElement = seRef;
   }

   public int getLevel() {
      return this.mLevel.intValue();
   }

   public StructureElementReference getStructureElement() {
      return this.mStructureElement;
   }
}
