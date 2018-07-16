// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.admin.tidytask;

import com.cedar.cp.api.admin.tidytask.TidyTask;
import com.cedar.cp.dto.admin.tidytask.TidyTaskImpl;
import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
import com.cedar.cp.impl.admin.tidytask.TidyTaskEditorSessionImpl;
import java.util.List;

public class TidyTaskAdapter implements TidyTask {

   private TidyTaskImpl mEditorData;
   private TidyTaskEditorSessionImpl mEditorSessionImpl;


   public TidyTaskAdapter(TidyTaskEditorSessionImpl e, TidyTaskImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected TidyTaskEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected TidyTaskImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(TidyTaskPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public List getTaskList() {
      return this.mEditorData.getTaskList();
   }
}
