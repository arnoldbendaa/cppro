// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.extendedattachment;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extendedattachment.ExtendedAttachmentEditor;
import com.cedar.cp.api.extendedattachment.ExtendedAttachmentEditorSession;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentEditorSessionSSO;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentImpl;
import com.cedar.cp.ejb.api.extendedattachment.ExtendedAttachmentEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.extendedattachment.ExtendedAttachmentEditorImpl;
import com.cedar.cp.impl.extendedattachment.ExtendedAttachmentsProcessImpl;
import com.cedar.cp.util.Log;

public class ExtendedAttachmentEditorSessionImpl extends BusinessSessionImpl implements ExtendedAttachmentEditorSession {

   protected ExtendedAttachmentEditorSessionSSO mServerSessionData;
   protected ExtendedAttachmentImpl mEditorData;
   protected ExtendedAttachmentEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public ExtendedAttachmentEditorSessionImpl(ExtendedAttachmentsProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get ExtendedAttachment", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected ExtendedAttachmentEditorSessionServer getSessionServer() throws CPException {
      return new ExtendedAttachmentEditorSessionServer(this.getConnection());
   }

   public ExtendedAttachmentEditor getExtendedAttachmentEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new ExtendedAttachmentEditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
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
