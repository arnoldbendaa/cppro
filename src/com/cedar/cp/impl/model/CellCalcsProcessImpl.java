// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.CellCalcEditorSession;
import com.cedar.cp.api.model.CellCalcsProcess;
import com.cedar.cp.ejb.api.model.CellCalcEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.CellCalcEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class CellCalcsProcessImpl extends BusinessProcessImpl implements CellCalcsProcess {

   private Log mLog = new Log(this.getClass());


   public CellCalcsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      CellCalcEditorSessionServer es = new CellCalcEditorSessionServer(this.getConnection());

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

   public CellCalcEditorSession getCellCalcEditorSession(Object key) throws ValidationException {
      CellCalcEditorSessionImpl sess = new CellCalcEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllCellCalcs() {
      try {
         return this.getConnection().getListHelper().getAllCellCalcs();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllCellCalcs", var2);
      }
   }

   public EntityList getCellCalcIntegrity() {
      try {
         return this.getConnection().getListHelper().getCellCalcIntegrity();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get CellCalcIntegrity", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing CellCalc";
      return ret;
   }

   protected int getProcessID() {
      return 6;
   }
}
