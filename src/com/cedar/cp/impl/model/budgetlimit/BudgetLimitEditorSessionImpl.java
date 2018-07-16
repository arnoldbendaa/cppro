// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.budgetlimit;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitEditor;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitEditorSession;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitEditorSessionSSO;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitImpl;
import com.cedar.cp.ejb.api.model.budgetlimit.BudgetLimitEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.model.budgetlimit.BudgetLimitEditorImpl;
import com.cedar.cp.impl.model.budgetlimit.BudgetLimitsProcessImpl;
import com.cedar.cp.util.Log;

public class BudgetLimitEditorSessionImpl extends BusinessSessionImpl implements BudgetLimitEditorSession {

   protected BudgetLimitEditorSessionSSO mServerSessionData;
   protected BudgetLimitImpl mEditorData;
   protected BudgetLimitEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public BudgetLimitEditorSessionImpl(BudgetLimitsProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get BudgetLimit", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected BudgetLimitEditorSessionServer getSessionServer() throws CPException {
      return new BudgetLimitEditorSessionServer(this.getConnection());
   }

   public BudgetLimitEditor getBudgetLimitEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new BudgetLimitEditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public EntityList getAvailableFinanceCubes() {
      try {
         return this.getSessionServer().getAvailableFinanceCubes();
      } catch (Exception var2) {
         throw new RuntimeException("unexpected exceptio", var2);
      }
   }

   public EntityList getOwnershipRefs() {
      try {
         return this.getSessionServer().getOwnershipRefs(this.mEditorData.getPrimaryKey());
      } catch (Exception var2) {
         throw new RuntimeException("unexpected exceptio", var2);
      }
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
