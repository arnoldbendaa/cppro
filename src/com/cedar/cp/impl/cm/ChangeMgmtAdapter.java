// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.cm;

import com.cedar.cp.api.cm.ChangeMgmt;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.cm.ChangeMgmtImpl;
import com.cedar.cp.dto.cm.ChangeMgmtPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.cm.ChangeMgmtEditorSessionImpl;
import java.sql.Timestamp;

public class ChangeMgmtAdapter implements ChangeMgmt {

   private ChangeMgmtImpl mEditorData;
   private ChangeMgmtEditorSessionImpl mEditorSessionImpl;


   public ChangeMgmtAdapter(ChangeMgmtEditorSessionImpl e, ChangeMgmtImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected ChangeMgmtEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected ChangeMgmtImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(ChangeMgmtPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public int getModelId() {
      return this.mEditorData.getModelId();
   }

   public Timestamp getCreatedTime() {
      return this.mEditorData.getCreatedTime();
   }

   public int getTaskId() {
      return this.mEditorData.getTaskId();
   }

   public String getSourceSystem() {
      return this.mEditorData.getSourceSystem();
   }

   public String getXmlText() {
      return this.mEditorData.getXmlText();
   }

   public ModelRef getRelatedModelRef() {
      if(this.mEditorData.getRelatedModelRef() != null) {
         try {
            ModelRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getModelEntityRef(this.mEditorData.getRelatedModelRef());
            this.mEditorData.setRelatedModelRef(e);
            return e;
         } catch (Exception var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2.getMessage());
         }
      } else {
         return null;
      }
   }

   public void setRelatedModelRef(ModelRef ref) {
      this.mEditorData.setRelatedModelRef(ref);
   }

   public void setModelId(int p) {
      this.mEditorData.setModelId(p);
   }

   public void setCreatedTime(Timestamp p) {
      this.mEditorData.setCreatedTime(p);
   }

   public void setTaskId(int p) {
      this.mEditorData.setTaskId(p);
   }

   public void setSourceSystem(String p) {
      this.mEditorData.setSourceSystem(p);
   }

   public void setXmlText(String p) {
      this.mEditorData.setXmlText(p);
   }
}
