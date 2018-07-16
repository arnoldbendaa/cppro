// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm.event;

import com.cedar.cp.ejb.impl.dimension.DimensionElementDAG;
import java.io.Serializable;

public class PostUpdateDimensionElementEvent implements Serializable {

   private DimensionElementDAG mElement;


   public PostUpdateDimensionElementEvent(DimensionElementDAG element) {
      this.mElement = element;
   }

   public DimensionElementDAG getElement() {
      return this.mElement;
   }
}
