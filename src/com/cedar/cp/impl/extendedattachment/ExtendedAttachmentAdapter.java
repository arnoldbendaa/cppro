// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.extendedattachment;

import com.cedar.cp.api.extendedattachment.ExtendedAttachment;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentImpl;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentPK;
import com.cedar.cp.impl.extendedattachment.ExtendedAttachmentEditorSessionImpl;

public class ExtendedAttachmentAdapter implements ExtendedAttachment {

   private ExtendedAttachmentImpl mEditorData;
   private ExtendedAttachmentEditorSessionImpl mEditorSessionImpl;


   public ExtendedAttachmentAdapter(ExtendedAttachmentEditorSessionImpl e, ExtendedAttachmentImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected ExtendedAttachmentEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected ExtendedAttachmentImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(ExtendedAttachmentPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getFileName() {
      return this.mEditorData.getFileName();
   }

   public byte[] getAttatch() {
      return this.mEditorData.getAttatch();
   }

   public void setFileName(String p) {
      this.mEditorData.setFileName(p);
   }

   public void setAttatch(byte[] p) {
      this.mEditorData.setAttatch(p);
   }
}
