// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.cm;

import com.cedar.cp.api.cm.ChangeMgmtRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AllChangeMgmtsForModelELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ChangeMgmt", "Model", "Model"};
   private transient ChangeMgmtRef mChangeMgmtEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient Timestamp mCreatedTime;
   private transient String mSourceSystem;
   private transient int mTaskId;


   public AllChangeMgmtsForModelELO() {
      super(new String[]{"ChangeMgmt", "Model", "CreatedTime", "SourceSystem", "TaskId"});
   }

   public void add(ChangeMgmtRef eRefChangeMgmt, ModelRef eRefModel, Timestamp col1, String col2, int col3) {
      ArrayList l = new ArrayList();
      l.add(eRefChangeMgmt);
      l.add(eRefModel);
      l.add(col1);
      l.add(col2);
      l.add(new Integer(col3));
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
      this.mChangeMgmtEntityRef = (ChangeMgmtRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mCreatedTime = (Timestamp)l.get(var4++);
      this.mSourceSystem = (String)l.get(var4++);
      this.mTaskId = ((Integer)l.get(var4++)).intValue();
   }

   public ChangeMgmtRef getChangeMgmtEntityRef() {
      return this.mChangeMgmtEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public Timestamp getCreatedTime() {
      return this.mCreatedTime;
   }

   public String getSourceSystem() {
      return this.mSourceSystem;
   }

   public int getTaskId() {
      return this.mTaskId;
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
