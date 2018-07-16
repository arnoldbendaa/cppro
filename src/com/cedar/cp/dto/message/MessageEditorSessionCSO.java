// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.message;

import com.cedar.cp.dto.message.MessageImpl;
import java.io.Serializable;

public class MessageEditorSessionCSO implements Serializable {

   private int mUserId;
   private MessageImpl mEditorData;


   public MessageEditorSessionCSO(int userId, MessageImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public MessageImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
