// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.task.group;

import com.cedar.cp.api.task.group.TaskGroupRef;
import com.cedar.cp.api.task.group.TgRowParamRef;
import com.cedar.cp.api.task.group.TgRowRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskGroupRICheckELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"TaskGroup", "TgRow", "TgRowParam", "TgRow", "TgRowParam"};
   private transient TaskGroupRef mTaskGroupEntityRef;
   private transient TgRowRef mTgRowEntityRef;
   private transient TgRowParamRef mTgRowParamEntityRef;
   private transient String mDescription;
   private transient int mRowType;
   private transient String mKey;
   private transient String mParam;


   public TaskGroupRICheckELO() {
      super(new String[]{"TaskGroup", "TgRow", "TgRowParam", "Description", "RowType", "Key", "Param"});
   }

   public void add(TaskGroupRef eRefTaskGroup, TgRowRef eRefTgRow, TgRowParamRef eRefTgRowParam, String col1, int col2, String col3, String col4) {
      ArrayList l = new ArrayList();
      l.add(eRefTaskGroup);
      l.add(eRefTgRow);
      l.add(eRefTgRowParam);
      l.add(col1);
      l.add(new Integer(col2));
      l.add(col3);
      l.add(col4);
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
      this.mTaskGroupEntityRef = (TaskGroupRef)l.get(index);
      this.mTgRowEntityRef = (TgRowRef)l.get(var4++);
      this.mTgRowParamEntityRef = (TgRowParamRef)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mRowType = ((Integer)l.get(var4++)).intValue();
      this.mKey = (String)l.get(var4++);
      this.mParam = (String)l.get(var4++);
   }

   public TaskGroupRef getTaskGroupEntityRef() {
      return this.mTaskGroupEntityRef;
   }

   public TgRowRef getTgRowEntityRef() {
      return this.mTgRowEntityRef;
   }

   public TgRowParamRef getTgRowParamEntityRef() {
      return this.mTgRowParamEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getRowType() {
      return this.mRowType;
   }

   public String getKey() {
      return this.mKey;
   }

   public String getParam() {
      return this.mParam;
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
