// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extendedattachment;

import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentImpl;
import java.io.Serializable;

public class ExtendedAttachmentEditorSessionSSO implements Serializable {

   private ExtendedAttachmentImpl mEditorData;


   public ExtendedAttachmentEditorSessionSSO() {}

   public ExtendedAttachmentEditorSessionSSO(ExtendedAttachmentImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(ExtendedAttachmentImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public ExtendedAttachmentImpl getEditorData() {
      return this.mEditorData;
   }
}
