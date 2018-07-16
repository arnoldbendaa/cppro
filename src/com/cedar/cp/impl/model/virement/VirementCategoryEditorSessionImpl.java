// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.virement;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.virement.VirementCategoryEditor;
import com.cedar.cp.api.model.virement.VirementCategoryEditorSession;
import com.cedar.cp.dto.model.virement.VirementCategoryEditorSessionSSO;
import com.cedar.cp.dto.model.virement.VirementCategoryImpl;
import com.cedar.cp.ejb.api.model.virement.VirementCategoryEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.model.virement.VirementCategoryEditorImpl;
import com.cedar.cp.impl.model.virement.VirementCategorysProcessImpl;
import com.cedar.cp.util.Log;

public class VirementCategoryEditorSessionImpl extends BusinessSessionImpl implements VirementCategoryEditorSession {

   protected VirementCategoryEditorSessionSSO mServerSessionData;
   protected VirementCategoryImpl mEditorData;
   protected VirementCategoryEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public VirementCategoryEditorSessionImpl(VirementCategorysProcessImpl process, Object key) throws ValidationException {
      super(process);

      try {
         if(key == null) {
            this.mServerSessionData = this.getSessionServer().getNewItemData();
         } else {
            this.mServerSessionData = this.getSessionServer().getItemData(key);
         }
      } catch (ValidationException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new RuntimeException("Can\'t get VirementCategory", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected VirementCategoryEditorSessionServer getSessionServer() throws CPException {
      return new VirementCategoryEditorSessionServer(this.getConnection());
   }

   public VirementCategoryEditor getVirementCategoryEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new VirementCategoryEditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public EntityList getAvailableModels() {
      try {
         return this.getSessionServer().getAvailableModels();
      } catch (Exception var2) {
         throw new RuntimeException("unexpected exceptio", var2);
      }
   }

   public EntityList getOwnershipRefs() {
      try {
         return this.getSessionServer().getOwnershipRefs(this.mEditorData.getPrimaryKey());
      } catch (Exception var2) {
         throw new RuntimeException("unexpected exceptio", var2);
      }
   }

   public Object persistModifications(boolean cloneOnSave) throws CPException, ValidationException {
      if(this.mClientEditor != null) {
         this.mClientEditor.saveModifications();
      }

      if(this.mEditorData.getPrimaryKey() == null) {
         this.mEditorData.setPrimaryKey(this.getSessionServer().insert(this.mEditorData));
      } else if(cloneOnSave) {
         this.mEditorData.setPrimaryKey(this.getSessionServer().copy(this.mEditorData));
      } else {
         this.getSessionServer().update(this.mEditorData);
      }

      return this.mEditorData.getPrimaryKey();
   }

   public void terminate() {}
}
