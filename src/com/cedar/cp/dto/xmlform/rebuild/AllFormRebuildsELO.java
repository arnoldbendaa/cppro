// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform.rebuild;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.xmlform.rebuild.FormRebuildRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AllFormRebuildsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"FormRebuild", "Model"};
   private transient FormRebuildRef mFormRebuildEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient String mDescription;
   private transient Timestamp mLastSubmit;


   public AllFormRebuildsELO() {
      super(new String[]{"FormRebuild", "Model", "Description", "LastSubmit"});
   }

   public void add(FormRebuildRef eRefFormRebuild, ModelRef eRefModel, String col1, Timestamp col2) {
      ArrayList l = new ArrayList();
      l.add(eRefFormRebuild);
      l.add(eRefModel);
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
      this.mFormRebuildEntityRef = (FormRebuildRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mLastSubmit = (Timestamp)l.get(var4++);
   }

   public FormRebuildRef getFormRebuildEntityRef() {
      return this.mFormRebuildEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public Timestamp getLastSubmit() {
      return this.mLastSubmit;
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
