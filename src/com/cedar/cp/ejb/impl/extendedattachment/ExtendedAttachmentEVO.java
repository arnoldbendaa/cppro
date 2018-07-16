// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extendedattachment;

import com.cedar.cp.api.extendedattachment.ExtendedAttachmentRef;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentPK;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentRefImpl;
import java.io.Serializable;

public class ExtendedAttachmentEVO implements Serializable {

   private transient ExtendedAttachmentPK mPK;
   private int mExtendedAttachmentId;
   private String mFileName;
   private byte[] mAttatch;
   private boolean mModified;


   public ExtendedAttachmentEVO() {}

   public ExtendedAttachmentEVO(int newExtendedAttachmentId, String newFileName, byte[] newAttatch) {
      this.mExtendedAttachmentId = newExtendedAttachmentId;
      this.mFileName = newFileName;
      this.mAttatch = newAttatch;
   }

   public ExtendedAttachmentPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ExtendedAttachmentPK(this.mExtendedAttachmentId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getExtendedAttachmentId() {
      return this.mExtendedAttachmentId;
   }

   public String getFileName() {
      return this.mFileName;
   }

   public byte[] getAttatch() {
      return this.mAttatch;
   }

   public void setExtendedAttachmentId(int newExtendedAttachmentId) {
      if(this.mExtendedAttachmentId != newExtendedAttachmentId) {
         this.mModified = true;
         this.mExtendedAttachmentId = newExtendedAttachmentId;
         this.mPK = null;
      }
   }

   public void setFileName(String newFileName) {
      if(this.mFileName != null && newFileName == null || this.mFileName == null && newFileName != null || this.mFileName != null && newFileName != null && !this.mFileName.equals(newFileName)) {
         this.mFileName = newFileName;
         this.mModified = true;
      }

   }

   public void setAttatch(byte[] newAttatch) {
      if(this.mAttatch != null && newAttatch == null || this.mAttatch == null && newAttatch != null || this.mAttatch != null && newAttatch != null && !this.mAttatch.equals(newAttatch)) {
         this.mAttatch = newAttatch;
         this.mModified = true;
      }

   }

   public void setDetails(ExtendedAttachmentEVO newDetails) {
      this.setExtendedAttachmentId(newDetails.getExtendedAttachmentId());
      this.setFileName(newDetails.getFileName());
      this.setAttatch(newDetails.getAttatch());
   }

   public ExtendedAttachmentEVO deepClone() {
      ExtendedAttachmentEVO cloned = new ExtendedAttachmentEVO();
      cloned.mModified = this.mModified;
      cloned.mExtendedAttachmentId = this.mExtendedAttachmentId;
      if(this.mFileName != null) {
         cloned.mFileName = this.mFileName;
      }

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

      return cloned;
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mExtendedAttachmentId > 0) {
         newKey = true;
         this.mExtendedAttachmentId = 0;
      } else if(this.mExtendedAttachmentId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mExtendedAttachmentId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mExtendedAttachmentId < 1) {
         this.mExtendedAttachmentId = startKey;
         nextKey = startKey + 1;
      }

      return nextKey;
   }

   protected void reset() {
      this.mModified = false;
   }

   public ExtendedAttachmentRef getEntityRef() {
      return new ExtendedAttachmentRefImpl(this.getPK(), this.mFileName);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ExtendedAttachmentId=");
      sb.append(String.valueOf(this.mExtendedAttachmentId));
      sb.append(' ');
      sb.append("FileName=");
      sb.append(String.valueOf(this.mFileName));
      sb.append(' ');
      sb.append("Attatch=");
      sb.append(String.valueOf(this.mAttatch));
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

      for(int i = 0; i < indent; ++i) {
         sb.append(' ');
      }

      sb.append("ExtendedAttachment: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
