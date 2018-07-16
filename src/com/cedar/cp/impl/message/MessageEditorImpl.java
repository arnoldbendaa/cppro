// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.message;

import com.cedar.cp.api.base.CPFileWrapper;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.message.Message;
import com.cedar.cp.api.message.MessageEditor;
import com.cedar.cp.dto.message.MessageEditorSessionSSO;
import com.cedar.cp.dto.message.MessageImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.message.MessageAdapter;
import com.cedar.cp.impl.message.MessageEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.util.List;

public class MessageEditorImpl extends BusinessEditorImpl implements MessageEditor {

   private MessageEditorSessionSSO mServerSessionData;
   private MessageImpl mEditorData;
   private MessageAdapter mEditorDataAdapter;


   public MessageEditorImpl(MessageEditorSessionImpl session, MessageEditorSessionSSO serverSessionData, MessageImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(MessageEditorSessionSSO serverSessionData, MessageImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setMessageType(int newMessageType) throws ValidationException {
      this.validateMessageType(newMessageType);
      if(this.mEditorData.getMessageType() != newMessageType) {
         this.setContentModified();
         this.mEditorData.setMessageType(newMessageType);
      }
   }

   public void setSubject(String newSubject) throws ValidationException {
      if(newSubject != null) {
         newSubject = StringUtils.rtrim(newSubject);
      }

      this.validateSubject(newSubject);
      if(this.mEditorData.getSubject() == null || !this.mEditorData.getSubject().equals(newSubject)) {
         this.setContentModified();
         this.mEditorData.setSubject(newSubject);
      }
   }

   public void setContent(String newContent) throws ValidationException {
      if(newContent != null) {
         newContent = StringUtils.rtrim(newContent);
      }

      this.validateContent(newContent);
      if(this.mEditorData.getContent() == null || !this.mEditorData.getContent().equals(newContent)) {
         this.setContentModified();
         this.mEditorData.setContent(newContent);
      }
   }

   public void validateSubject(String newSubject) throws ValidationException {
      if(newSubject != null && newSubject.length() > 128) {
         throw new ValidationException("length (" + newSubject.length() + ") of Subject must not exceed 128 on a Message");
      }
   }

   public void validateContent(String newContent) throws ValidationException {}

   public void validateMessageType(int newMessageType) throws ValidationException {}

   public Message getMessage() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new MessageAdapter((MessageEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}

   public List getMessageUsers() {
      return this.mEditorData.getMessageUsers();
   }

   public void setMessageUsers(List messageUsers) {
      this.mEditorData.setMessageUsers(messageUsers);
      this.setContentModified();
   }

   public void addMessageUser(Object evo) {
      this.mEditorData.addMessageUser(evo);
      this.setContentModified();
   }

   public void addFromUser(String id) {
      this.mEditorData.addFromUser(id);
      this.setContentModified();
   }

   public void addToUser(String id) {
      this.mEditorData.addToUser(id);
      this.setContentModified();
   }

   public void setRead(long userKey, boolean state) {
      this.mEditorData.setUserKey(userKey);
      this.mEditorData.setState(state);
      this.setContentModified();
   }

   public void setDeleteId(long userId) {
      this.mEditorData.setDeleteUserKey(userId);
      this.setContentModified();
   }

   public void setFromEmailAddress(String address) {
      this.mEditorData.setFromEmailAddress(address);
      this.setContentModified();
   }

   public void setFromEmailAddress(Object address) {
      if(address != null) {
         this.setFromEmailAddress(address.toString());
      }

   }

   public void setToEmailAddress(String address) {
      this.mEditorData.setToEmailAddress(address);
      this.setContentModified();
   }

   public void setToEmailAddress(Object address) {
      if(address != null) {
         this.setToEmailAddress(address.toString());
      }

   }

   public void addToEmailAddress(String address) {
      String emailRegEx = "(\\S+)@(\\S+)";
      if(address != null && address.matches(emailRegEx)) {
         String[] values = this.mEditorData.getToEmailAddress().split(";");
         boolean doAdd = true;
         String[] sb = values;
         int arr$ = values.length;

         int len$;
         for(len$ = 0; len$ < arr$; ++len$) {
            String i$ = sb[len$];
            if(i$.equals(address)) {
               doAdd = false;
               break;
            }
         }

         if(doAdd) {
            StringBuilder var10 = new StringBuilder();
            String[] var11 = values;
            len$ = values.length;

            for(int var12 = 0; var12 < len$; ++var12) {
               String s = var11[var12];
               if(s.matches(emailRegEx)) {
                  if(var10.length() > 0) {
                     var10.append(";");
                  }

                  var10.append(s);
               }
            }

            if(var10.length() > 0) {
               var10.append(";");
            }

            var10.append(address);
            this.mEditorData.setToEmailAddress(var10.toString());
         }

      }
   }

   public void addToEmailAddress(Object address) {
      if(address != null) {
         this.addToEmailAddress(address.toString());
      }

   }

   public void addAttachment(CPFileWrapper file) {
      this.mEditorData.addAttachment(file);
   }

   public void addAttachment(String name, byte[] data) {
      CPFileWrapper file = new CPFileWrapper(data, name);
      this.addAttachment(file);
   }

   public MessageImpl getMessageImpl() {
      return this.mEditorData;
   }
}
