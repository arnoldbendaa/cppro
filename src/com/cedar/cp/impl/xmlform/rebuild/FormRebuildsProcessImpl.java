// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.xmlform.rebuild;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlform.rebuild.FormRebuildEditorSession;
import com.cedar.cp.api.xmlform.rebuild.FormRebuildsProcess;
import com.cedar.cp.ejb.api.xmlform.rebuild.FormRebuildEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.xmlform.rebuild.FormRebuildEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.util.List;

public class FormRebuildsProcessImpl extends BusinessProcessImpl implements FormRebuildsProcess {

   private Log mLog = new Log(this.getClass());


   public FormRebuildsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      FormRebuildEditorSessionServer es = new FormRebuildEditorSessionServer(this.getConnection());

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

   public FormRebuildEditorSession getFormRebuildEditorSession(Object key) throws ValidationException {
      FormRebuildEditorSessionImpl sess = new FormRebuildEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllFormRebuilds() {
      try {
         return this.getConnection().getListHelper().getAllFormRebuilds();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllFormRebuilds", var2);
      }
   }

   public EntityList getAllFormRebuildsForLoggedUser() {
      try {
         return this.getConnection().getListHelper().getAllFormRebuildsForLoggedUser();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllFormRebuildsForLoggedUser", var2);
      }
   }

   public EntityList getAllBudgetCyclesInRebuilds() {
      try {
         return this.getConnection().getListHelper().getAllBudgetCyclesInRebuilds();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllBudgetCyclesInRebuilds", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing FormRebuild";
      return ret;
   }

   protected int getProcessID() {
      return 96;
   }

   public List submit(EntityRef ref, int userId) throws ValidationException {
      FormRebuildEditorSessionServer server = new FormRebuildEditorSessionServer(this.getConnection());
      return server.submit(ref, userId);
   }
}
