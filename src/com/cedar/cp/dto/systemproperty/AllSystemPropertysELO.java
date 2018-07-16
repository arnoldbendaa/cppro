// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.systemproperty;

import com.cedar.cp.api.systemproperty.SystemPropertyRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllSystemPropertysELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"SystemProperty"};
   private transient SystemPropertyRef mSystemPropertyEntityRef;
   private transient String mDescription;
   private transient String mValue;


   public AllSystemPropertysELO() {
      super(new String[]{"SystemProperty", "Description", "Value"});
   }

   public void add(SystemPropertyRef eRefSystemProperty, String col1, String col2) {
      ArrayList l = new ArrayList();
      l.add(eRefSystemProperty);
      l.add(col1);
      l.add(col2);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mSystemPropertyEntityRef = (SystemPropertyRef)l.get(index);
      this.mDescription = (String)l.get(var4++);
      this.mValue = (String)l.get(var4++);
   }

   public SystemPropertyRef getSystemPropertyEntityRef() {
      return this.mSystemPropertyEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getValue() {
      return this.mValue;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
