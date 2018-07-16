// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.message;

import com.cedar.cp.utc.common.CPForm;
import com.cedar.cp.utc.struts.message.MessageDTO;

public class MessageOpenForm extends CPForm {

   private MessageDTO mMessage = new MessageDTO();
   private String mSource = "";
   private long mMessageId;
   private int mAttachId;


   public MessageDTO getMessage() {
      return this.mMessage;
   }

   public void setMessage(MessageDTO message) {
      this.mMessage = message;
   }

   public String getSource() {
      return this.mSource;
   }

   public void setSource(String source) {
      this.mSource = source;
   }

   public long getMessageId() {
      return this.mMessageId;
   }

   public void setMessageId(long messageId) {
      this.mMessageId = messageId;
   }

   public int getAttachId() {
      return this.mAttachId;
   }

   public void setAttachId(int attachId) {
      this.mAttachId = attachId;
   }
}
