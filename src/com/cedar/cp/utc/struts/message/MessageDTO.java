// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.message;

import com.cedar.cp.utc.struts.message.MessageAttachmentDTO;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MessageDTO implements Cloneable, Comparable {

   private long mMessageId;
   private long mMessageUserId;
   private String mToUser;
   private String mToUser_VisID;
   private String mFromUser;
   private String mFromUser_VisID;
   private String mContent;
   private int mMessageType = 0;
   private String mSubject;
   private Date mDate;
   private String mContentType = "text/html";
   private long mOriginalMessageId;
   public static final String sPLAIN = "text/plain";
   public static final String sHTML = "text/html";
   private boolean mHasAttachment;
   private List<MessageAttachmentDTO> mAttachments;
   private boolean mSelected = false;
   private String mToUserEmailAddress;
   private String mFromUserEmailAddress;
   private int mToUserID;
   private boolean mRead = false;
   private String mToDist;
   private String mToDistType;
   public static SimpleDateFormat sFormat = new SimpleDateFormat("dd/MMM/yyyy");


   public long getMessageId() {
      return this.mMessageId;
   }

   public void setMessageId(long messageId) {
      this.mMessageId = messageId;
   }

   public String getToUser() {
      return this.mToUser;
   }

   public void setToUser(String toUser) {
      this.mToUser = toUser;
   }

   public void setToUser(Object object) {
      if(object == null) {
         this.mToUser = "";
      } else {
         this.mToUser = object.toString();
      }

   }

   public String getToUser_VisID() {
      return this.mToUser_VisID;
   }

   public void setToUser_VisID(String toUser_VisID) {
      this.mToUser_VisID = toUser_VisID;
   }

   public void setToUser_VisID(Object object) {
      if(object == null) {
         this.mToUser_VisID = "";
      } else {
         this.mToUser_VisID = object.toString();
      }

   }

   public String getFromUser() {
      return this.mFromUser;
   }

   public void setFromUser(String fromUser) {
      this.mFromUser = fromUser;
   }

   public void setFromUser(Object object) {
      if(object == null) {
         this.mFromUser = "";
      } else {
         this.mFromUser = object.toString();
      }

   }

   public String getFromUser_VisID() {
      return this.mFromUser_VisID;
   }

   public void setFromUser_VisID(String fromUser_VisID) {
      this.mFromUser_VisID = fromUser_VisID;
   }

   public void setFromUser_VisID(Object object) {
      if(object == null) {
         this.mFromUser_VisID = "";
      } else {
         this.mFromUser_VisID = object.toString();
      }

   }

   public String getContent() {
      return this.mContent;
   }

   public void setContent(String content) {
      this.mContent = content;
   }

   public void setContent(Object object) {
      if(object == null) {
         this.mContent = "";
      } else {
         this.mContent = object.toString();
      }

   }

   public int getMessageType() {
      return this.mMessageType;
   }

   public void setMessageType(int messageType) {
      this.mMessageType = messageType;
   }

   public String getSubject() {
      return this.mSubject;
   }

   public void setSubject(String subject) {
      this.mSubject = subject;
   }

   public void setSubject(Object object) {
      if(object == null) {
         this.mSubject = "";
      } else {
         this.mSubject = object.toString();
      }

   }

   public Date getDate() {
      return this.mDate;
   }

   public String getFormattedDate() {
      return sFormat.format(this.getDate());
   }

   public void setDate(Date date) {
      this.mDate = date;
   }

   public void setDate(Object object) {
      if(object != null && object instanceof Timestamp) {
         this.mDate = new Date(((Timestamp)object).getTime());
      }

   }

   public boolean isSelected() {
      return this.mSelected;
   }

   public void setSelected(boolean selected) {
      this.mSelected = selected;
   }

   public String getToUserEmailAddress() {
      return this.mToUserEmailAddress;
   }

   public void setToUserEmailAddress(String toUserEmailAddress) {
      this.mToUserEmailAddress = toUserEmailAddress;
   }

   public String getFromUserEmailAddress() {
      return this.mFromUserEmailAddress;
   }

   public void setFromUserEmailAddress(String fromUserEmailAddress) {
      this.mFromUserEmailAddress = fromUserEmailAddress;
   }

   public int getToUserID() {
      return this.mToUserID;
   }

   public void setToUserID(int toUserID) {
      this.mToUserID = toUserID;
   }

   public boolean isRead() {
      return this.mRead;
   }

   public void setRead(boolean read) {
      this.mRead = read;
   }

   public void setRead(Object read) {
      if(read != null) {
         this.mRead = ((Boolean)read).booleanValue();
      }

   }

   public long getMessageUserId() {
      return this.mMessageUserId;
   }

   public void setMessageUserId(long messageUserId) {
      this.mMessageUserId = messageUserId;
   }

   public String getContentType() {
      return this.mContentType;
   }

   public void setContentType(String contentType) {
      this.mContentType = contentType;
   }

   public boolean isHTML() {
      return "text/html".equals(this.getContentType());
   }

   protected Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public int compareTo(Object o) {
      MessageDTO compareTo = (MessageDTO)o;
      return this.getDate().after(compareTo.getDate())?0:1;
   }

   public boolean isHasAttachment() {
      return this.mHasAttachment;
   }

   public void setHasAttachment(boolean hasAttachment) {
      this.mHasAttachment = hasAttachment;
   }

   public String getToDist() {
      return this.mToDist;
   }

   public void setToDist(String toDist) {
      this.mToDist = toDist;
   }

   public String getToDistType() {
      return this.mToDistType;
   }

   public void setToDistType(String toDistType) {
      this.mToDistType = toDistType;
   }

   public List<MessageAttachmentDTO> getAttachments() {
      return this.mAttachments == null?Collections.EMPTY_LIST:this.mAttachments;
   }

   public void setAttachments(List<MessageAttachmentDTO> attachments) {
      this.mAttachments = attachments;
   }

   public int getAttachmentSize() {
      return this.mAttachments == null?0:this.mAttachments.size();
   }

   public void addAttatchment(MessageAttachmentDTO attatch) {
      if(this.mAttachments == null) {
         this.mAttachments = new ArrayList();
      }

      this.mAttachments.add(attatch);
   }

   public MessageAttachmentDTO getAttachment(int index) {
      return this.mAttachments == null && index == 0?this.getAttachment(index, true):this.getAttachment(index, false);
   }

   public MessageAttachmentDTO getAttachment(int index, boolean newAttatch) {
      if(this.mAttachments == null || newAttatch) {
         this.addAttatchment(new MessageAttachmentDTO());
      }

      return this.mAttachments != null && this.mAttachments.size() >= index?(MessageAttachmentDTO)this.mAttachments.get(index):null;
   }

   public long getOriginalMessageId() {
      return this.mOriginalMessageId;
   }

   public void setOriginalMessageId(long originalMessageId) {
      this.mOriginalMessageId = originalMessageId;
   }

}
