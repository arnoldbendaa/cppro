// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.formnotes;

import com.cedar.cp.api.formnotes.FormNotesRef;
import com.cedar.cp.dto.formnotes.FormNotesPK;
import com.cedar.cp.dto.formnotes.FormNotesRefImpl;
import java.io.Serializable;
import java.sql.Timestamp;

public class FormNotesEVO implements Serializable {

   private transient FormNotesPK mPK;
   private int mFormNoteId;
   private int mFormId;
   private int mStructureElementId;
   private String mNote;
   private int mAttachmentId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mModified;


   public FormNotesEVO() {}

   public FormNotesEVO(int newFormNoteId, int newFormId, int newStructureElementId, String newNote, int newAttachmentId) {
      this.mFormNoteId = newFormNoteId;
      this.mFormId = newFormId;
      this.mStructureElementId = newStructureElementId;
      this.mNote = newNote;
      this.mAttachmentId = newAttachmentId;
   }

   public FormNotesPK getPK() {
      if(this.mPK == null) {
         this.mPK = new FormNotesPK(this.mFormNoteId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getFormNoteId() {
      return this.mFormNoteId;
   }

   public int getFormId() {
      return this.mFormId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public String getNote() {
      return this.mNote;
   }

   public int getAttachmentId() {
      return this.mAttachmentId;
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

   public void setFormNoteId(int newFormNoteId) {
      if(this.mFormNoteId != newFormNoteId) {
         this.mModified = true;
         this.mFormNoteId = newFormNoteId;
         this.mPK = null;
      }
   }

   public void setFormId(int newFormId) {
      if(this.mFormId != newFormId) {
         this.mModified = true;
         this.mFormId = newFormId;
      }
   }

   public void setStructureElementId(int newStructureElementId) {
      if(this.mStructureElementId != newStructureElementId) {
         this.mModified = true;
         this.mStructureElementId = newStructureElementId;
      }
   }

   public void setAttachmentId(int newAttachmentId) {
      if(this.mAttachmentId != newAttachmentId) {
         this.mModified = true;
         this.mAttachmentId = newAttachmentId;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setNote(String newNote) {
      if(this.mNote != null && newNote == null || this.mNote == null && newNote != null || this.mNote != null && newNote != null && !this.mNote.equals(newNote)) {
         this.mNote = newNote;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(FormNotesEVO newDetails) {
      this.setFormNoteId(newDetails.getFormNoteId());
      this.setFormId(newDetails.getFormId());
      this.setStructureElementId(newDetails.getStructureElementId());
      this.setNote(newDetails.getNote());
      this.setAttachmentId(newDetails.getAttachmentId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public FormNotesEVO deepClone() {
      FormNotesEVO cloned = new FormNotesEVO();
      cloned.mModified = this.mModified;
      cloned.mFormNoteId = this.mFormNoteId;
      cloned.mFormId = this.mFormId;
      cloned.mStructureElementId = this.mStructureElementId;
      cloned.mAttachmentId = this.mAttachmentId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mNote != null) {
         cloned.mNote = this.mNote;
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mFormNoteId > 0) {
         newKey = true;
         this.mFormNoteId = 0;
      } else if(this.mFormNoteId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mFormNoteId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mFormNoteId < 1) {
         this.mFormNoteId = startKey;
         nextKey = startKey + 1;
      }

      return nextKey;
   }

   protected void reset() {
      this.mModified = false;
   }

   public FormNotesRef getEntityRef(String entityText) {
      return new FormNotesRefImpl(this.getPK(), entityText);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("FormNoteId=");
      sb.append(String.valueOf(this.mFormNoteId));
      sb.append(' ');
      sb.append("FormId=");
      sb.append(String.valueOf(this.mFormId));
      sb.append(' ');
      sb.append("StructureElementId=");
      sb.append(String.valueOf(this.mStructureElementId));
      sb.append(' ');
      sb.append("Note=");
      sb.append(String.valueOf(this.mNote));
      sb.append(' ');
      sb.append("AttachmentId=");
      sb.append(String.valueOf(this.mAttachmentId));
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

      for(int i = 0; i < indent; ++i) {
         sb.append(' ');
      }

      sb.append("FormNotes: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
