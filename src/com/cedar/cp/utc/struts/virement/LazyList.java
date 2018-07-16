// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import java.util.ArrayList;
import org.apache.commons.collections.Factory;

public class LazyList extends ArrayList {

   private Factory mFactory;


   public LazyList(Factory factory) {
      this.mFactory = factory;
   }

   public Object get(int index) {
      while(index >= this.size()) {
         this.add(this.mFactory.create());
      }

      return super.get(index);
   }
}
