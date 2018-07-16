// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.admin.tidytask;

import com.cedar.cp.api.admin.tidytask.TidyTaskLinkRef;
import com.cedar.cp.api.admin.tidytask.TidyTaskRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderedChildrenELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"TidyTaskLink", "TidyTask"};
   private transient TidyTaskLinkRef mTidyTaskLinkEntityRef;
   private transient TidyTaskRef mTidyTaskEntityRef;
   private transient int mType;
   private transient String mCmd;


   public OrderedChildrenELO() {
      super(new String[]{"TidyTaskLink", "TidyTask", "Type", "Cmd"});
   }

   public void add(TidyTaskLinkRef eRefTidyTaskLink, TidyTaskRef eRefTidyTask, int col1, String col2) {
      ArrayList l = new ArrayList();
      l.add(eRefTidyTaskLink);
      l.add(eRefTidyTask);
      l.add(new Integer(col1));
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
      this.mTidyTaskLinkEntityRef = (TidyTaskLinkRef)l.get(index);
      this.mTidyTaskEntityRef = (TidyTaskRef)l.get(var4++);
      this.mType = ((Integer)l.get(var4++)).intValue();
      this.mCmd = (String)l.get(var4++);
   }

   public TidyTaskLinkRef getTidyTaskLinkEntityRef() {
      return this.mTidyTaskLinkEntityRef;
   }

   public TidyTaskRef getTidyTaskEntityRef() {
      return this.mTidyTaskEntityRef;
   }

   public int getType() {
      return this.mType;
   }

   public String getCmd() {
      return this.mCmd;
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
