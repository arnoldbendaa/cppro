// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.message;

import com.cedar.cp.api.message.Message;
import com.cedar.cp.dto.message.MessageImpl;
import com.cedar.cp.dto.message.MessagePK;
import com.cedar.cp.impl.message.MessageEditorSessionImpl;
import java.util.List;

public class MessageAdapter implements Message {

   private MessageImpl mEditorData;
   private MessageEditorSessionImpl mEditorSessionImpl;


   public MessageAdapter(MessageEditorSessionImpl e, MessageImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected MessageEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected MessageImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(MessagePK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getSubject() {
      return this.mEditorData.getSubject();
   }

   public String getContent() {
      return this.mEditorData.getContent();
   }

   public int getMessageType() {
      return this.mEditorData.getMessageType();
   }

   public void setSubject(String p) {
      this.mEditorData.setSubject(p);
   }

   public void setContent(String p) {
      this.mEditorData.setContent(p);
   }

   public void setMessageType(int p) {
      this.mEditorData.setMessageType(p);
   }

   public List getMessageUsers() {
      return this.mEditorData.getMessageUsers();
   }

   public List getFromUsers() {
      return this.mEditorData.getFromUsers();
   }

   public List getToUsers() {
      return this.mEditorData.getToUsers();
   }

   public String getFromEmailAddress() {
      return this.mEditorData.getFromEmailAddress();
   }

   public String getToEmailAddress() {
      return this.mEditorData.getToEmailAddress();
   }

   public List getAttachments() {
      return this.mEditorData.getAttachments();
   }
}
