// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.extsys;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extsys.ExtSysE5DB2PushProcess;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.ejb.api.extsys.ExternalSystemEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.util.List;

public class ExtSysE5DB2PushProcessImpl extends BusinessProcessImpl implements ExtSysE5DB2PushProcess {

   private Log mLog = new Log(this.getClass());


   public ExtSysE5DB2PushProcessImpl(CPConnection connection) {
      super(connection);
   }

   public boolean isAvailable() {
      EntityList list = this.getConnection().getSystemPropertysProcess().getSystemProperty("E5: DATABASE_TYPE");
      return String.valueOf(list.getValueAt(0, "Value")).equalsIgnoreCase("DB2");
   }

   public EntityList queryDataForPushSubmission() throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      EntityList var3;
      try {
         ExternalSystemEditorSessionServer e = new ExternalSystemEditorSessionServer(this.getConnection());
         var3 = e.queryDataForPushSubmission();
      } catch (CPException var7) {
         throw new RuntimeException("can\'t queryDataForPushSubmission()", var7);
      } finally {
         if(timer != null) {
            timer.logDebug("queryDataForPushSubmission");
         }

      }

      return var3;
   }

   public int issuePushTask(List<FinanceCubeRef> financeCubes) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      int var4;
      try {
         ExternalSystemEditorSessionServer e = new ExternalSystemEditorSessionServer(this.getConnection());
         var4 = e.issuePushTask(financeCubes);
      } catch (CPException var8) {
         throw new RuntimeException("can\'t issuePushTask()", var8);
      } finally {
         if(timer != null) {
            timer.logDebug("issuePushTask");
         }

      }

      return var4;
   }

   protected int getProcessID() {
      return 88;
   }
}
