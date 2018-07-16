// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.budgetinstruction;

import com.cedar.cp.api.budgetinstruction.BudgetInstruction;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionAssignment;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentImpl;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BudgetInstructionImpl implements BudgetInstruction, Serializable, Cloneable {

   private List mBudgetInstructionAssignments = new ArrayList();
   private Object mPrimaryKey;
   private String mVisId;
   private String mDocumentRef;
   private byte[] mDocument;
   private int mVersionNum;


   public BudgetInstructionImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDocumentRef = "";
      this.mDocument = null;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (BudgetInstructionPK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDocumentRef() {
      return this.mDocumentRef;
   }

   public byte[] getDocument() {
      return this.mDocument;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDocumentRef(String paramDocumentRef) {
      this.mDocumentRef = paramDocumentRef;
   }

   public void setDocument(byte[] paramDocument) {
      this.mDocument = paramDocument;
   }

   public void setBudgetInstructionAssignments(List l) {
      this.mBudgetInstructionAssignments = l;
   }

   public List getBudgetInstructionAssignments() {
      return this.mBudgetInstructionAssignments;
   }

   public void addBudgetInstructionAssignmentItem(BudgetInstructionAssignment bia) {
      this.mBudgetInstructionAssignments.add(bia);
   }

   public BudgetInstructionAssignment getBudgetInstructionAssignmentItem(Object key) {
      if(key != null) {
         Object editorData = null;
         Iterator iter = this.mBudgetInstructionAssignments.iterator();

         while(iter.hasNext()) {
            BudgetInstructionAssignment item = (BudgetInstructionAssignment)iter.next();
            if(key.equals(item.getPrimaryKey())) {
               return item;
            }
         }
      }

      return new BudgetInstructionAssignmentImpl((Object)null);
   }
}
