// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:42
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.message;

import com.cedar.cp.api.message.MessageAttatchRef;
import com.cedar.cp.dto.message.MessageAttatchCK;
import com.cedar.cp.dto.message.MessageAttatchPK;
import com.cedar.cp.dto.message.MessageAttatchRefImpl;
import com.cedar.cp.ejb.impl.message.MessageEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class MessageAttatchEVO implements Serializable {

   private transient MessageAttatchPK mPK;
   private long mMessageId;
   private long mMessageAttatchId;
   private byte[] mAttatch;
   private String mAttatchName;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public MessageAttatchEVO() {}

   public MessageAttatchEVO(long newMessageId, long newMessageAttatchId, byte[] newAttatch, String newAttatchName) {
      this.mMessageId = newMessageId;
      this.mMessageAttatchId = newMessageAttatchId;
      this.mAttatch = newAttatch;
      this.mAttatchName = newAttatchName;
   }

   public MessageAttatchPK getPK() {
      if(this.mPK == null) {
         this.mPK = new MessageAttatchPK(this.mMessageId, this.mMessageAttatchId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public long getMessageId() {
      return this.mMessageId;
   }

   public long getMessageAttatchId() {
      return this.mMessageAttatchId;
   }

   public byte[] getAttatch() {
      return this.mAttatch;
   }

   public String getAttatchName() {
      return this.mAttatchName;
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

   public void setMessageAttatchId(long newMessageAttatchId) {
      if(this.mMessageAttatchId != newMessageAttatchId) {
         this.mModified = true;
         this.mMessageAttatchId = newMessageAttatchId;
         this.mPK = null;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setAttatch(byte[] newAttatch) {
      if(this.mAttatch != null && newAttatch == null || this.mAttatch == null && newAttatch != null || this.mAttatch != null && newAttatch != null && !this.mAttatch.equals(newAttatch)) {
         this.mAttatch = newAttatch;
         this.mModified = true;
      }

   }

   public void setAttatchName(String newAttatchName) {
      if(this.mAttatchName != null && newAttatchName == null || this.mAttatchName == null && newAttatchName != null || this.mAttatchName != null && newAttatchName != null && !this.mAttatchName.equals(newAttatchName)) {
         this.mAttatchName = newAttatchName;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(MessageAttatchEVO newDetails) {
      this.setMessageId(newDetails.getMessageId());
      this.setMessageAttatchId(newDetails.getMessageAttatchId());
      this.setAttatch(newDetails.getAttatch());
      this.setAttatchName(newDetails.getAttatchName());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public MessageAttatchEVO deepClone() {
      MessageAttatchEVO cloned = new MessageAttatchEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mMessageId = this.mMessageId;
      cloned.mMessageAttatchId = this.mMessageAttatchId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mAttatch != null) {
         cloned.mAttatch = new byte[this.mAttatch.length];
         int i = -1;

         try {
            while(true) {
               ++i;
               cloned.mAttatch[i] = this.mAttatch[i];
            }
         } catch (ArrayIndexOutOfBoundsException var4) {
            ;
         }
      }

      if(this.mAttatchName != null) {
         cloned.mAttatchName = this.mAttatchName;
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
            parent.changeKey(this, this.mMessageId, -this.mMessageAttatchId);
         }
      } else if(this.mMessageId < 1L) {
         newKey = true;
      }

      if(this.mMessageAttatchId > 0L) {
         newKey = true;
         if(parent == null) {
            this.setMessageAttatchId((long)((int)(-this.mMessageAttatchId)));
         } else {
            parent.changeKey(this, this.mMessageId, -this.mMessageAttatchId);
         }
      } else if(this.mMessageAttatchId < 1L) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mMessageId < 1L) {
         returnCount = startCount + 1;
      }

      if(this.mMessageAttatchId < 1L) {
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

      if(this.mMessageAttatchId < 1L) {
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

   public MessageAttatchRef getEntityRef(MessageEVO evoMessage, String entityText) {
      return new MessageAttatchRefImpl(new MessageAttatchCK(evoMessage.getPK(), this.getPK()), entityText);
   }

   public MessageAttatchCK getCK(MessageEVO evoMessage) {
      return new MessageAttatchCK(evoMessage.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("MessageId=");
      sb.append(String.valueOf(this.mMessageId));
      sb.append(' ');
      sb.append("MessageAttatchId=");
      sb.append(String.valueOf(this.mMessageAttatchId));
      sb.append(' ');
      sb.append("Attatch=");
      sb.append(String.valueOf(this.mAttatch));
      sb.append(' ');
      sb.append("AttatchName=");
      sb.append(String.valueOf(this.mAttatchName));
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

      sb.append("MessageAttatch: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
