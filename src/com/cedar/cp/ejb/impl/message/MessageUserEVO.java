// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.message;

import com.cedar.cp.api.message.MessageUserRef;
import com.cedar.cp.dto.message.MessageUserCK;
import com.cedar.cp.dto.message.MessageUserPK;
import com.cedar.cp.dto.message.MessageUserRefImpl;
import com.cedar.cp.ejb.impl.message.MessageEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class MessageUserEVO implements Serializable {

   private transient MessageUserPK mPK;
   private long mMessageId;
   private long mMessageUserId;
   private String mUserId;
   private boolean mRead;
   private boolean mDeleted;
   private int mType;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int INBOX = 0;
   public static final int SENT_ITEMS = 1;


   public MessageUserEVO() {}

   public MessageUserEVO(long newMessageId, long newMessageUserId, String newUserId, boolean newRead, boolean newDeleted, int newType) {
      this.mMessageId = newMessageId;
      this.mMessageUserId = newMessageUserId;
      this.mUserId = newUserId;
      this.mRead = newRead;
      this.mDeleted = newDeleted;
      this.mType = newType;
   }

   public MessageUserPK getPK() {
      if(this.mPK == null) {
         this.mPK = new MessageUserPK(this.mMessageId, this.mMessageUserId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public long getMessageId() {
      return this.mMessageId;
   }

   public long getMessageUserId() {
      return this.mMessageUserId;
   }

   public String getUserId() {
      return this.mUserId;
   }

   public boolean getRead() {
      return this.mRead;
   }

   public boolean getDeleted() {
      return this.mDeleted;
   }

   public int getType() {
      return this.mType;
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

   public void setMessageUserId(long newMessageUserId) {
      if(this.mMessageUserId != newMessageUserId) {
         this.mModified = true;
         this.mMessageUserId = newMessageUserId;
         this.mPK = null;
      }
   }

   public void setRead(boolean newRead) {
      if(this.mRead != newRead) {
         this.mModified = true;
         this.mRead = newRead;
      }
   }

   public void setDeleted(boolean newDeleted) {
      if(this.mDeleted != newDeleted) {
         this.mModified = true;
         this.mDeleted = newDeleted;
      }
   }

   public void setType(int newType) {
      if(this.mType != newType) {
         this.mModified = true;
         this.mType = newType;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setUserId(String newUserId) {
      if(this.mUserId != null && newUserId == null || this.mUserId == null && newUserId != null || this.mUserId != null && newUserId != null && !this.mUserId.equals(newUserId)) {
         this.mUserId = newUserId;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(MessageUserEVO newDetails) {
      this.setMessageId(newDetails.getMessageId());
      this.setMessageUserId(newDetails.getMessageUserId());
      this.setUserId(newDetails.getUserId());
      this.setRead(newDetails.getRead());
      this.setDeleted(newDetails.getDeleted());
      this.setType(newDetails.getType());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public MessageUserEVO deepClone() {
      MessageUserEVO cloned = new MessageUserEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mMessageId = this.mMessageId;
      cloned.mMessageUserId = this.mMessageUserId;
      cloned.mRead = this.mRead;
      cloned.mDeleted = this.mDeleted;
      cloned.mType = this.mType;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mUserId != null) {
         cloned.mUserId = this.mUserId;
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(MessageEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mMessageId > 0L) {
         newKey = true;
         if(parent == null) {
            this.setMessageId((long)((int)(-this.mMessageId)));
         } else {
            parent.changeKey(this, this.mMessageId, -this.mMessageUserId);
         }
      } else if(this.mMessageId < 1L) {
         newKey = true;
      }

      if(this.mMessageUserId > 0L) {
         newKey = true;
         if(parent == null) {
            this.setMessageUserId((long)((int)(-this.mMessageUserId)));
         } else {
            parent.changeKey(this, this.mMessageId, -this.mMessageUserId);
         }
      } else if(this.mMessageUserId < 1L) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mMessageId < 1L) {
         returnCount = startCount + 1;
      }

      if(this.mMessageUserId < 1L) {
         ++returnCount;
      }

      return returnCount;
   }

   public int assignNextKey(MessageEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mMessageId < 1L) {
         parent.changeKey(this, this.mMessageId, (long)startKey);
         nextKey = startKey + 1;
      }

      if(this.mMessageUserId < 1L) {
         parent.changeKey(this, this.mMessageId, (long)nextKey);
         ++nextKey;
      }

      return nextKey;
   }

   public void setInsertPending() {
      this.mInsertPending = true;
   }

   public boolean insertPending() {
      return this.mInsertPending;
   }

   public void setDeletePending() {
      this.mDeletePending = true;
   }

   public boolean deletePending() {
      return this.mDeletePending;
   }

   protected void reset() {
      this.mModified = false;
      this.mInsertPending = false;
   }

   public MessageUserRef getEntityRef(MessageEVO evoMessage, String entityText) {
      return new MessageUserRefImpl(new MessageUserCK(evoMessage.getPK(), this.getPK()), entityText);
   }

   public MessageUserCK getCK(MessageEVO evoMessage) {
      return new MessageUserCK(evoMessage.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("MessageId=");
      sb.append(String.valueOf(this.mMessageId));
      sb.append(' ');
      sb.append("MessageUserId=");
      sb.append(String.valueOf(this.mMessageUserId));
      sb.append(' ');
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
      sb.append(' ');
      sb.append("Read=");
      sb.append(String.valueOf(this.mRead));
      sb.append(' ');
      sb.append("Deleted=");
      sb.append(String.valueOf(this.mDeleted));
      sb.append(' ');
      sb.append("Type=");
      sb.append(String.valueOf(this.mType));
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

      if(this.mInsertPending) {
         sb.append("insertPending ");
      }

      if(this.mDeletePending) {
         sb.append("deletePending ");
      }

      return sb.toString();
   }

   public String print() {
      return this.print(0);
   }

   public String print(int indent) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < indent; ++i) {
         sb.append(' ');
      }

      sb.append("MessageUser: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
