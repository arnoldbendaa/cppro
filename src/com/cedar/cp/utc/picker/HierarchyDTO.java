// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.picker;

import com.cedar.cp.utc.picker.ElementDTO;
import java.util.ArrayList;
import java.util.List;

public class HierarchyDTO extends ElementDTO {

   private List mElements;


   public List getElements() {
      return this.mElements;
   }

   public void setElements(List elements) {
      this.mElements = elements;
   }

   public void addElement(ElementDTO dto) {
      if(this.mElements == null) {
         this.mElements = new ArrayList();
      }

      this.mElements.add(dto);
   }
}
