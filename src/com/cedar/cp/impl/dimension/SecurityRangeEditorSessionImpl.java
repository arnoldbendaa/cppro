// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.SecurityRangeEditor;
import com.cedar.cp.api.dimension.SecurityRangeEditorSession;
import com.cedar.cp.dto.dimension.SecurityRangeEditorSessionSSO;
import com.cedar.cp.dto.dimension.SecurityRangeImpl;
import com.cedar.cp.ejb.api.dimension.SecurityRangeEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.dimension.SecurityRangeEditorImpl;
import com.cedar.cp.impl.dimension.SecurityRangesProcessImpl;
import com.cedar.cp.util.Log;

public class SecurityRangeEditorSessionImpl extends BusinessSessionImpl implements SecurityRangeEditorSession {

   protected SecurityRangeEditorSessionSSO mServerSessionData;
   protected SecurityRangeImpl mEditorData;
   protected SecurityRangeEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public SecurityRangeEditorSessionImpl(SecurityRangesProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get SecurityRange", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected SecurityRangeEditorSessionServer getSessionServer() throws CPException {
      return new SecurityRangeEditorSessionServer(this.getConnection());
   }

   public SecurityRangeEditor getSecurityRangeEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new SecurityRangeEditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public EntityList getAvailableDimensions() {
      try {
         return this.getSessionServer().getAvailableDimensions();
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
