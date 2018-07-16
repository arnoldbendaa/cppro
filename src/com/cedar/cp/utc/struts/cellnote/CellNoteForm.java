// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.cellnote;

import com.cedar.cp.utc.common.CPForm;

public class CellNoteForm extends CPForm {

   private int modelId;
   private int financeCubeId;
   private int cellNoteId;
   private String newNote = "";
   private String oldNotes = "";


   public String getNewNote() {
      return this.newNote;
   }

   public void setNewNote(String newNote) {
      this.newNote = newNote;
   }

   public String getOldNotes() {
      return this.oldNotes;
   }

   public void setOldNotes(String oldNotes) {
      this.oldNotes = oldNotes;
   }

   public int getFinanceCubeId() {
      return this.financeCubeId;
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.financeCubeId = financeCubeId;
   }

   public int getCellNoteId() {
      return this.cellNoteId;
   }

   public void setCellNoteId(int cellNoteId) {
      this.cellNoteId = cellNoteId;
   }

   public int getModelId() {
      return this.modelId;
   }

   public void setModelId(int modelId) {
      this.modelId = modelId;
   }
}
