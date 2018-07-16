// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extendedattachment;

import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentImpl;
import java.io.Serializable;

public class ExtendedAttachmentEditorSessionCSO implements Serializable {

   private int mUserId;
   private ExtendedAttachmentImpl mEditorData;


   public ExtendedAttachmentEditorSessionCSO(int userId, ExtendedAttachmentImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public ExtendedAttachmentImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
