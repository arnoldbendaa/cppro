// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.message;

import com.cedar.cp.api.base.CPFileWrapper;
import com.cedar.cp.api.message.Message;
import com.cedar.cp.dto.message.MessagePK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageImpl implements Message, Serializable, Cloneable {

   private long mDeleteUserKey;
   private long mUserKey;
   private boolean mState;
   private List mMessageUsers;
   private List mToUser;
   private List mFromUser;
   private String mFromEmailAddress;
   private String mToEmailAddress;
   private List mAttachments;
   private Object mPrimaryKey;
   private String mSubject;
   private String mContent;
   private int mMessageType;
   private int mVersionNum;


   public MessageImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mSubject = "";
      this.mContent = "";
      this.mMessageType = 0;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (MessagePK)paramKey;
   }

   public String getSubject() {
      return this.mSubject;
   }

   public String getContent() {
      return this.mContent;
   }

   public int getMessageType() {
      return this.mMessageType;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setSubject(String paramSubject) {
      this.mSubject = paramSubject;
   }

   public void setContent(String paramContent) {
      this.mContent = paramContent;
   }

   public void setMessageType(int paramMessageType) {
      this.mMessageType = paramMessageType;
   }

   public List getMessageUsers() {
      return this.mMessageUsers == null?Collections.EMPTY_LIST:this.mMessageUsers;
   }

   public void setMessageUsers(List messageUsers) {
      this.mMessageUsers = messageUsers;
   }

   public void addMessageUser(Object evo) {
      if(this.mMessageUsers == null) {
         this.mMessageUsers = new ArrayList();
      }

      this.mMessageUsers.add(evo);
   }

   public List getToUsers() {
      return this.mToUser == null?Collections.EMPTY_LIST:this.mToUser;
   }

   public void setToUser(List toUser) {
      this.mToUser = toUser;
   }

   public void addToUser(String id) {
      if(this.mToUser == null) {
         this.mToUser = new ArrayList();
      }

      this.mToUser.add(id);
   }

   public List getFromUsers() {
      return this.mFromUser == null?Collections.EMPTY_LIST:this.mFromUser;
   }

   public void setFromUser(List fromUser) {
      this.mFromUser = fromUser;
   }

   public void addFromUser(String id) {
      if(this.mFromUser == null) {
         this.mFromUser = new ArrayList();
      }

      this.mFromUser.add(id);
   }

   public long getUserKey() {
      return this.mUserKey;
   }

   public void setUserKey(long userKey) {
      this.mUserKey = userKey;
   }

   public boolean isState() {
      return this.mState;
   }

   public void setState(boolean state) {
      this.mState = state;
   }

   public long getDeleteUserKey() {
      return this.mDeleteUserKey;
   }

   public void setDeleteUserKey(long deleteUserKey) {
      this.mDeleteUserKey = deleteUserKey;
   }

   public String getFromEmailAddress() {
      return this.mFromEmailAddress;
   }

   public void setFromEmailAddress(String fromEmailAddress) {
      this.mFromEmailAddress = fromEmailAddress;
   }

   public String getToEmailAddress() {
      return this.mToEmailAddress;
   }

   public void setToEmailAddress(String toEmailAddress) {
      this.mToEmailAddress = toEmailAddress;
   }

   public List getAttachments() {
      return this.mAttachments == null?Collections.EMPTY_LIST:this.mAttachments;
   }

   public void setAttachments(List attachments) {
      this.mAttachments = attachments;
   }

   public void addAttachment(CPFileWrapper file) {
      if(this.mAttachments == null) {
         this.mAttachments = new ArrayList();
      }

      if(!this.mAttachments.contains(file)) {
         this.mAttachments.add(file);
      }

   }
}
