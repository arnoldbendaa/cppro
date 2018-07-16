// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.budgetlimit;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitEditorSession;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitsProcess;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitCK;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitPK;
import com.cedar.cp.ejb.api.model.budgetlimit.BudgetLimitEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.budgetlimit.BudgetLimitEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.util.List;

public class BudgetLimitsProcessImpl extends BusinessProcessImpl implements BudgetLimitsProcess {

   private Log mLog = new Log(this.getClass());


   public BudgetLimitsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      BudgetLimitEditorSessionServer es = new BudgetLimitEditorSessionServer(this.getConnection());

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

   public BudgetLimitEditorSession getBudgetLimitEditorSession(Object key) throws ValidationException {
      BudgetLimitEditorSessionImpl sess = new BudgetLimitEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllBudgetLimits() {
      try {
         return this.getConnection().getListHelper().getAllBudgetLimits();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllBudgetLimits", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing BudgetLimit";
      return ret;
   }

   protected int getProcessID() {
      return 65;
   }

   public List getImposedLimits(int numDims, int fcId, int budgetLocation) throws ValidationException {
      BudgetLimitEditorSessionServer es = new BudgetLimitEditorSessionServer(this.getConnection());
      return es.getImposedLimits(numDims, fcId, budgetLocation);
   }

   public List getBudgetLimitsSetByBudgetLocation(int numDims, int fcId, int budgetLocation) throws ValidationException {
      BudgetLimitEditorSessionServer es = new BudgetLimitEditorSessionServer(this.getConnection());
      return es.getBudgetLimitsSetByBudgetLocation(numDims, fcId, budgetLocation);
   }

   public void deleteObjectWithId(int modelId, int fcId, int limitId) throws ValidationException {
      Object key = this.getKey(modelId, fcId, limitId);
      this.deleteObject(key);
   }

   public BudgetLimitEditorSession getBudgetLimitEditorSession(int modelId, int fcId, int limitId) throws ValidationException {
      Object key = this.getKey(modelId, fcId, limitId);
      return this.getBudgetLimitEditorSession(key);
   }

   private Object getKey(int modelId, int fcId, int limitId) {
      ModelPK modelPK = new ModelPK(modelId);
      FinanceCubePK fcPK = new FinanceCubePK(fcId);
      BudgetLimitPK blPK = new BudgetLimitPK(limitId);
      BudgetLimitCK blCK = new BudgetLimitCK(modelPK, fcPK, blPK);
      return blCK;
   }
}
