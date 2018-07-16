// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.message;

import com.cedar.cp.api.message.MessageRef;
import com.cedar.cp.dto.message.MessageAttatchPK;
import com.cedar.cp.dto.message.MessagePK;
import com.cedar.cp.dto.message.MessageRefImpl;
import com.cedar.cp.dto.message.MessageUserPK;
import com.cedar.cp.ejb.impl.message.MessageAttatchEVO;
import com.cedar.cp.ejb.impl.message.MessageUserEVO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MessageEVO implements Serializable {

   private transient MessagePK mPK;
   private long mMessageId;
   private String mSubject;
   private String mContent;
   private int mMessageType;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<MessageUserPK, MessageUserEVO> mMessageUsers;
   protected boolean mMessageUsersAllItemsLoaded;
   private Map<MessageAttatchPK, MessageAttatchEVO> mMessageAttatchments;
   protected boolean mMessageAttatchmentsAllItemsLoaded;
   private boolean mModified;
   public static final int SYSTEM_MESSAGE = 0;
   public static final int EMAIL = 1;
   public static final int BOTH = 2;


   public MessageEVO() {}

   public MessageEVO(long newMessageId, String newSubject, String newContent, int newMessageType, int newVersionNum, Collection newMessageUsers, Collection newMessageAttatchments) {
      this.mMessageId = newMessageId;
      this.mSubject = newSubject;
      this.mContent = newContent;
      this.mMessageType = newMessageType;
      this.mVersionNum = newVersionNum;
      this.setMessageUsers(newMessageUsers);
      this.setMessageAttatchments(newMessageAttatchments);
   }

   public void setMessageUsers(Collection<MessageUserEVO> items) {
      if(items != null) {
         if(this.mMessageUsers == null) {
            this.mMessageUsers = new HashMap();
         } else {
            this.mMessageUsers.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            MessageUserEVO child = (MessageUserEVO)i$.next();
            this.mMessageUsers.put(child.getPK(), child);
         }
      } else {
         this.mMessageUsers = null;
      }

   }

   public void setMessageAttatchments(Collection<MessageAttatchEVO> items) {
      if(items != null) {
         if(this.mMessageAttatchments == null) {
            this.mMessageAttatchments = new HashMap();
         } else {
            this.mMessageAttatchments.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            MessageAttatchEVO child = (MessageAttatchEVO)i$.next();
            this.mMessageAttatchments.put(child.getPK(), child);
         }
      } else {
         this.mMessageAttatchments = null;
      }

   }

   public MessagePK getPK() {
      if(this.mPK == null) {
         this.mPK = new MessagePK(this.mMessageId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public long getMessageId() {
      return this.mMessageId;
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

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public int getUpdatedByUserId() {
      return this.mUpdatedByUserId;
   }

   public Timestamp getUpdatedTime() {
      return this.mUpdatedTime;
   }

   public Timestamp getCreatedTime() {
      return this.mCreatedTime;
   }

   public void setMessageId(long newMessageId) {
      if(this.mMessageId != newMessageId) {
         this.mModified = true;
         this.mMessageId = newMessageId;
         this.mPK = null;
      }
   }

   public void setMessageType(int newMessageType) {
      if(this.mMessageType != newMessageType) {
         this.mModified = true;
         this.mMessageType = newMessageType;
      }
   }

   public void setVersionNum(int newVersionNum) {
      if(this.mVersionNum != newVersionNum) {
         this.mModified = true;
         this.mVersionNum = newVersionNum;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setSubject(String newSubject) {
      if(this.mSubject != null && newSubject == null || this.mSubject == null && newSubject != null || this.mSubject != null && newSubject != null && !this.mSubject.equals(newSubject)) {
         this.mSubject = newSubject;
         this.mModified = true;
      }

   }

   public void setContent(String newContent) {
      if(this.mContent != null && newContent == null || this.mContent == null && newContent != null || this.mContent != null && newContent != null && !this.mContent.equals(newContent)) {
         this.mContent = newContent;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(MessageEVO newDetails) {
      this.setMessageId(newDetails.getMessageId());
      this.setSubject(newDetails.getSubject());
      this.setContent(newDetails.getContent());
      this.setMessageType(newDetails.getMessageType());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public MessageEVO deepClone() {
      MessageEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (MessageEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mMessageId > 0L) {
         newKey = true;
         this.mMessageId = 0L;
      } else if(this.mMessageId < 1L) {
         newKey = true;
      }

      this.setVersionNum(0);
      Iterator iter;
      MessageUserEVO item;
      if(this.mMessageUsers != null) {
         for(iter = (new ArrayList(this.mMessageUsers.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (MessageUserEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      MessageAttatchEVO item1;
      if(this.mMessageAttatchments != null) {
         for(iter = (new ArrayList(this.mMessageAttatchments.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (MessageAttatchEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mMessageId < 1L) {
         returnCount = startCount + 1;
      }

      Iterator iter;
      MessageUserEVO item;
      if(this.mMessageUsers != null) {
         for(iter = this.mMessageUsers.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (MessageUserEVO)iter.next();
         }
      }

      MessageAttatchEVO item1;
      if(this.mMessageAttatchments != null) {
         for(iter = this.mMessageAttatchments.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (MessageAttatchEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mMessageId < 1L) {
         this.mMessageId = (long)startKey;
         nextKey = startKey + 1;
      }

      Iterator iter;
      MessageUserEVO item;
      if(this.mMessageUsers != null) {
         for(iter = (new ArrayList(this.mMessageUsers.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (MessageUserEVO)iter.next();
            this.changeKey(item, this.mMessageId, item.getMessageUserId());
         }
      }

      MessageAttatchEVO item1;
      if(this.mMessageAttatchments != null) {
         for(iter = (new ArrayList(this.mMessageAttatchments.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (MessageAttatchEVO)iter.next();
            this.changeKey(item1, this.mMessageId, item1.getMessageAttatchId());
         }
      }

      return nextKey;
   }

   public Collection<MessageUserEVO> getMessageUsers() {
      return this.mMessageUsers != null?this.mMessageUsers.values():null;
   }

   public Map<MessageUserPK, MessageUserEVO> getMessageUsersMap() {
      return this.mMessageUsers;
   }

   public void loadMessageUsersItem(MessageUserEVO newItem) {
      if(this.mMessageUsers == null) {
         this.mMessageUsers = new HashMap();
      }

      this.mMessageUsers.put(newItem.getPK(), newItem);
   }

   public void addMessageUsersItem(MessageUserEVO newItem) {
      if(this.mMessageUsers == null) {
         this.mMessageUsers = new HashMap();
      }

      MessageUserPK newPK = newItem.getPK();
      if(this.getMessageUsersItem(newPK) != null) {
         throw new RuntimeException("addMessageUsersItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mMessageUsers.put(newPK, newItem);
      }
   }

   public void changeMessageUsersItem(MessageUserEVO changedItem) {
      if(this.mMessageUsers == null) {
         throw new RuntimeException("changeMessageUsersItem: no items in collection");
      } else {
         MessageUserPK changedPK = changedItem.getPK();
         MessageUserEVO listItem = this.getMessageUsersItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeMessageUsersItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteMessageUsersItem(MessageUserPK removePK) {
      MessageUserEVO listItem = this.getMessageUsersItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeMessageUsersItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public MessageUserEVO getMessageUsersItem(MessageUserPK pk) {
      return (MessageUserEVO)this.mMessageUsers.get(pk);
   }

   public MessageUserEVO getMessageUsersItem() {
      if(this.mMessageUsers.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mMessageUsers.size());
      } else {
         Iterator iter = this.mMessageUsers.values().iterator();
         return (MessageUserEVO)iter.next();
      }
   }

   public Collection<MessageAttatchEVO> getMessageAttatchments() {
      return this.mMessageAttatchments != null?this.mMessageAttatchments.values():null;
   }

   public Map<MessageAttatchPK, MessageAttatchEVO> getMessageAttatchmentsMap() {
      return this.mMessageAttatchments;
   }

   public void loadMessageAttatchmentsItem(MessageAttatchEVO newItem) {
      if(this.mMessageAttatchments == null) {
         this.mMessageAttatchments = new HashMap();
      }

      this.mMessageAttatchments.put(newItem.getPK(), newItem);
   }

   public void addMessageAttatchmentsItem(MessageAttatchEVO newItem) {
      if(this.mMessageAttatchments == null) {
         this.mMessageAttatchments = new HashMap();
      }

      MessageAttatchPK newPK = newItem.getPK();
      if(this.getMessageAttatchmentsItem(newPK) != null) {
         throw new RuntimeException("addMessageAttatchmentsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mMessageAttatchments.put(newPK, newItem);
      }
   }

   public void changeMessageAttatchmentsItem(MessageAttatchEVO changedItem) {
      if(this.mMessageAttatchments == null) {
         throw new RuntimeException("changeMessageAttatchmentsItem: no items in collection");
      } else {
         MessageAttatchPK changedPK = changedItem.getPK();
         MessageAttatchEVO listItem = this.getMessageAttatchmentsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeMessageAttatchmentsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteMessageAttatchmentsItem(MessageAttatchPK removePK) {
      MessageAttatchEVO listItem = this.getMessageAttatchmentsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeMessageAttatchmentsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public MessageAttatchEVO getMessageAttatchmentsItem(MessageAttatchPK pk) {
      return (MessageAttatchEVO)this.mMessageAttatchments.get(pk);
   }

   public MessageAttatchEVO getMessageAttatchmentsItem() {
      if(this.mMessageAttatchments.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mMessageAttatchments.size());
      } else {
         Iterator iter = this.mMessageAttatchments.values().iterator();
         return (MessageAttatchEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public MessageRef getEntityRef(String entityText) {
      return new MessageRefImpl(this.getPK(), entityText);
   }

   public void postCreateInit() {
      this.mMessageUsersAllItemsLoaded = true;
      if(this.mMessageUsers == null) {
         this.mMessageUsers = new HashMap();
      }

      this.mMessageAttatchmentsAllItemsLoaded = true;
      if(this.mMessageAttatchments == null) {
         this.mMessageAttatchments = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("MessageId=");
      sb.append(String.valueOf(this.mMessageId));
      sb.append(' ');
      sb.append("Subject=");
      sb.append(String.valueOf(this.mSubject));
      sb.append(' ');
      sb.append("Content=");
      sb.append(String.valueOf(this.mContent));
      sb.append(' ');
      sb.append("MessageType=");
      sb.append(String.valueOf(this.mMessageType));
      sb.append(' ');
      sb.append("VersionNum=");
      sb.append(String.valueOf(this.mVersionNum));
      sb.append(' ');
      sb.append("UpdatedByUserId=");
      sb.append(String.valueOf(this.mUpdatedByUserId));
      sb.append(' ');
      sb.append("UpdatedTime=");
      sb.append(String.valueOf(this.mUpdatedTime));
      sb.append(' ');
      sb.append("CreatedTime=");
      sb.append(String.valueOf(this.mCreatedTime));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
      }

      return sb.toString();
   }

   public String print() {
      return this.print(0);
   }

   public String print(int indent) {
      StringBuffer sb = new StringBuffer();

      for(int i$ = 0; i$ < indent; ++i$) {
         sb.append(' ');
      }

      sb.append("Message: ");
      sb.append(this.toString());
      if(this.mMessageUsersAllItemsLoaded || this.mMessageUsers != null) {
         sb.delete(indent, sb.length());
         sb.append(" - MessageUsers: allItemsLoaded=");
         sb.append(String.valueOf(this.mMessageUsersAllItemsLoaded));
         sb.append(" items=");
         if(this.mMessageUsers == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mMessageUsers.size()));
         }
      }

      if(this.mMessageAttatchmentsAllItemsLoaded || this.mMessageAttatchments != null) {
         sb.delete(indent, sb.length());
         sb.append(" - MessageAttatchments: allItemsLoaded=");
         sb.append(String.valueOf(this.mMessageAttatchmentsAllItemsLoaded));
         sb.append(" items=");
         if(this.mMessageAttatchments == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mMessageAttatchments.size()));
         }
      }

      Iterator var5;
      if(this.mMessageUsers != null) {
         var5 = this.mMessageUsers.values().iterator();

         while(var5.hasNext()) {
            MessageUserEVO listItem = (MessageUserEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mMessageAttatchments != null) {
         var5 = this.mMessageAttatchments.values().iterator();

         while(var5.hasNext()) {
            MessageAttatchEVO var6 = (MessageAttatchEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(MessageUserEVO child, long newMessageId, long newMessageUserId) {
      if(this.getMessageUsersItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mMessageUsers.remove(child.getPK());
         child.setMessageId(newMessageId);
         child.setMessageUserId(newMessageUserId);
         this.mMessageUsers.put(child.getPK(), child);
      }
   }

   public void changeKey(MessageAttatchEVO child, long newMessageId, long newMessageAttatchId) {
      if(this.getMessageAttatchmentsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mMessageAttatchments.remove(child.getPK());
         child.setMessageId(newMessageId);
         child.setMessageAttatchId(newMessageAttatchId);
         this.mMessageAttatchments.put(child.getPK(), child);
      }
   }

   public void setMessageUsersAllItemsLoaded(boolean allItemsLoaded) {
      this.mMessageUsersAllItemsLoaded = allItemsLoaded;
   }

   public boolean isMessageUsersAllItemsLoaded() {
      return this.mMessageUsersAllItemsLoaded;
   }

   public void setMessageAttatchmentsAllItemsLoaded(boolean allItemsLoaded) {
      this.mMessageAttatchmentsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isMessageAttatchmentsAllItemsLoaded() {
      return this.mMessageAttatchmentsAllItemsLoaded;
   }
}
