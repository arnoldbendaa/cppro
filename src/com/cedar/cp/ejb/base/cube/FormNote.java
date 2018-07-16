// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import java.io.Serializable;

public class FormNote implements Serializable {

   String mNote;
   int mFormId;
   int mStructureElementId;
   private int mAttachmentId;


   public String getNote() {
      return this.mNote;
   }

   public void setNote(String note) {
      this.mNote = note;
   }

   public int getFormId() {
      return this.mFormId;
   }

   public void setFormId(int formId) {
      this.mFormId = formId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public void setStructureElementId(int structureElementId) {
      this.mStructureElementId = structureElementId;
   }

   public int getAttachmentId() {
      return this.mAttachmentId;
   }

   public void setAttachmentId(int attachmentId) {
      this.mAttachmentId = attachmentId;
   }
}
