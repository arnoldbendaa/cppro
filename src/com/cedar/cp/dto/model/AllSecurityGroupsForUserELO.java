// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllSecurityGroupsForUserELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"SecurityGroup", "Model", "SecurityGroupUserRel", "SecurityGroupUserRel"};
   private transient int mSecurityGroupId;
   private transient int mUpdateAccessId;


   public AllSecurityGroupsForUserELO() {
      super(new String[]{"SecurityGroupId", "UpdateAccessId"});
   }

   public void add(int col1, int col2) {
      ArrayList l = new ArrayList();
      l.add(new Integer(col1));
      l.add(new Integer(col2));
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
      this.mSecurityGroupId = ((Integer)l.get(index)).intValue();
      this.mUpdateAccessId = ((Integer)l.get(var4++)).intValue();
   }

   public int getSecurityGroupId() {
      return this.mSecurityGroupId;
   }

   public int getUpdateAccessId() {
      return this.mUpdateAccessId;
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
