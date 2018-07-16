// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.extendedattachment;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extendedattachment.ExtendedAttachment;
import com.cedar.cp.api.extendedattachment.ExtendedAttachmentEditor;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentEditorSessionSSO;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.extendedattachment.ExtendedAttachmentAdapter;
import com.cedar.cp.impl.extendedattachment.ExtendedAttachmentEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

public class ExtendedAttachmentEditorImpl extends BusinessEditorImpl implements ExtendedAttachmentEditor {

   private ExtendedAttachmentEditorSessionSSO mServerSessionData;
   private ExtendedAttachmentImpl mEditorData;
   private ExtendedAttachmentAdapter mEditorDataAdapter;


   public ExtendedAttachmentEditorImpl(ExtendedAttachmentEditorSessionImpl session, ExtendedAttachmentEditorSessionSSO serverSessionData, ExtendedAttachmentImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(ExtendedAttachmentEditorSessionSSO serverSessionData, ExtendedAttachmentImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setFileName(String newFileName) throws ValidationException {
      if(newFileName != null) {
         newFileName = StringUtils.rtrim(newFileName);
      }

      this.validateFileName(newFileName);
      if(this.mEditorData.getFileName() == null || !this.mEditorData.getFileName().equals(newFileName)) {
         this.setContentModified();
         this.mEditorData.setFileName(newFileName);
      }
   }

   public void setAttatch(byte[] newAttatch) throws ValidationException {
      this.validateAttatch(newAttatch);
      if(this.mEditorData.getAttatch() == null || !this.mEditorData.getAttatch().equals(newAttatch)) {
         this.setContentModified();
         this.mEditorData.setAttatch(newAttatch);
      }
   }

   public void validateFileName(String newFileName) throws ValidationException {
      if(newFileName != null && newFileName.length() > 256) {
         throw new ValidationException("length (" + newFileName.length() + ") of FileName must not exceed 256 on a ExtendedAttachment");
      }
   }

   public void validateAttatch(byte[] newAttatch) throws ValidationException {}

   public ExtendedAttachment getExtendedAttachment() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new ExtendedAttachmentAdapter((ExtendedAttachmentEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
