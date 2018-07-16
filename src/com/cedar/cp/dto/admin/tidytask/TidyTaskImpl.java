// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.admin.tidytask;

import com.cedar.cp.api.admin.tidytask.TidyTask;
import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TidyTaskImpl implements TidyTask, Serializable, Cloneable {

   private List mTaskList;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private int mVersionNum;


   public TidyTaskImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (TidyTaskPK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public List getTaskList() {
      if(this.mTaskList == null) {
         this.mTaskList = new ArrayList();
      }

      return this.mTaskList;
   }

   public void setTidyList(List tidyList) {
      this.mTaskList = tidyList;
   }
}
