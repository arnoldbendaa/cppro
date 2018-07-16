// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.budgetinstruction;

import com.cedar.cp.api.budgetinstruction.BudgetInstruction;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionAssignment;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionImpl;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
import com.cedar.cp.impl.budgetinstruction.BudgetInstructionEditorSessionImpl;
import java.util.List;

public class BudgetInstructionAdapter implements BudgetInstruction {

   private BudgetInstructionImpl mEditorData;
   private BudgetInstructionEditorSessionImpl mEditorSessionImpl;


   public BudgetInstructionAdapter(BudgetInstructionEditorSessionImpl e, BudgetInstructionImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected BudgetInstructionEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected BudgetInstructionImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(BudgetInstructionPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDocumentRef() {
      return this.mEditorData.getDocumentRef();
   }

   public byte[] getDocument() {
      return this.mEditorData.getDocument();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDocumentRef(String p) {
      this.mEditorData.setDocumentRef(p);
   }

   public void setDocument(byte[] p) {
      this.mEditorData.setDocument(p);
   }

   public List getBudgetInstructionAssignments() {
      return this.mEditorData.getBudgetInstructionAssignments();
   }

   public BudgetInstructionAssignment getBudgetInstructionAssignmentItem(Object key) {
      return this.mEditorData.getBudgetInstructionAssignmentItem(key);
   }
}
