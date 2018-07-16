// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.budgetinstruction;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionEditorSession;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionsProcess;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.api.budgetinstruction.BudgetInstructionEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.budgetinstruction.BudgetInstructionEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class BudgetInstructionsProcessImpl extends BusinessProcessImpl implements BudgetInstructionsProcess {

   private Log mLog = new Log(this.getClass());


   public BudgetInstructionsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      BudgetInstructionEditorSessionServer es = new BudgetInstructionEditorSessionServer(this.getConnection());

      try {
         es.delete(primaryKey);
      } catch (ValidationException var5) {
         throw var5;
      } catch (CPException var6) {
         throw new RuntimeException("can\'t delete " + primaryKey, var6);
      }

      if(timer != null) {
         timer.logDebug("deleteObject", primaryKey);
      }

   }

   public BudgetInstructionEditorSession getBudgetInstructionEditorSession(Object key) throws ValidationException {
      BudgetInstructionEditorSessionImpl sess = new BudgetInstructionEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllBudgetInstructions() {
      try {
         return this.getConnection().getListHelper().getAllBudgetInstructions();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllBudgetInstructions", var2);
      }
   }

   public EntityList getAllBudgetInstructionsWeb() {
      try {
         return this.getConnection().getListHelper().getAllBudgetInstructionsWeb();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllBudgetInstructionsWeb", var2);
      }
   }

   public EntityList getAllBudgetInstructionsForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getAllBudgetInstructionsForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllBudgetInstructionsForModel", var3);
      }
   }

   public EntityList getAllBudgetInstructionsForCycle(int param1) {
      try {
         return this.getConnection().getListHelper().getAllBudgetInstructionsForCycle(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllBudgetInstructionsForCycle", var3);
      }
   }

   public EntityList getAllBudgetInstructionsForLocation(int param1) {
      try {
         return this.getConnection().getListHelper().getAllBudgetInstructionsForLocation(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllBudgetInstructionsForLocation", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing BudgetInstruction";
      return ret;
   }

   protected int getProcessID() {
      return 3;
   }

   public BudgetInstructionEditorSession getBudgetInstructionEditorSession(int id) throws ValidationException {
      BudgetInstructionPK key = new BudgetInstructionPK(id);
      BudgetInstructionEditorSessionImpl sess = new BudgetInstructionEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllBudgetInstructionsForModel(Object param1) {
      EntityList list = null;
      if(param1 instanceof ModelRef) {
         ModelRef ref = (ModelRef)param1;
         ModelPK pk = (ModelPK)ref.getPrimaryKey();
         int modelid = pk.getModelId();
         list = this.getAllBudgetInstructionsForModel(modelid);
      }

      return list;
   }

   public void deleteObject(int primaryKey) throws ValidationException {
      BudgetInstructionPK pk = new BudgetInstructionPK(primaryKey);
      this.deleteObject(pk);
   }
}
