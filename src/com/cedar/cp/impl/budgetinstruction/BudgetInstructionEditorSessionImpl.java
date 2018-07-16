// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.budgetinstruction;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionEditor;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionEditorSession;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionEditorSessionSSO;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionImpl;
import com.cedar.cp.ejb.api.budgetinstruction.BudgetInstructionEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.budgetinstruction.BudgetInstructionEditorImpl;
import com.cedar.cp.impl.budgetinstruction.BudgetInstructionsProcessImpl;
import com.cedar.cp.util.Log;

public class BudgetInstructionEditorSessionImpl extends BusinessSessionImpl implements BudgetInstructionEditorSession {

   protected BudgetInstructionEditorSessionSSO mServerSessionData;
   protected BudgetInstructionImpl mEditorData;
   protected BudgetInstructionEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public BudgetInstructionEditorSessionImpl(BudgetInstructionsProcessImpl process, Object key) throws ValidationException {
      super(process);

      try {
         if(key == null) {
            this.mServerSessionData = this.getSessionServer().getNewItemData();
         } else {
            this.mServerSessionData = this.getSessionServer().getItemData(key);
         }
      } catch (ValidationException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new RuntimeException("Can\'t get BudgetInstruction", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected BudgetInstructionEditorSessionServer getSessionServer() throws CPException {
      return new BudgetInstructionEditorSessionServer(this.getConnection());
   }

   public BudgetInstructionEditor getBudgetInstructionEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new BudgetInstructionEditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public Object persistModifications(boolean cloneOnSave) throws CPException, ValidationException {
      if(this.mClientEditor != null) {
         this.mClientEditor.saveModifications();
      }

      if(this.mEditorData.getPrimaryKey() == null) {
         this.mEditorData.setPrimaryKey(this.getSessionServer().insert(this.mEditorData));
      } else if(cloneOnSave) {
         this.mEditorData.setPrimaryKey(this.getSessionServer().copy(this.mEditorData));
      } else {
         this.getSessionServer().update(this.mEditorData);
      }

      return this.mEditorData.getPrimaryKey();
   }

   public void terminate() {}
}
