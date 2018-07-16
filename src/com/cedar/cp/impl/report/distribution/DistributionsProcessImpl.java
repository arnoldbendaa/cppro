// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.distribution;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.distribution.DistributionDetails;
import com.cedar.cp.api.report.distribution.DistributionEditorSession;
import com.cedar.cp.api.report.distribution.DistributionsProcess;
import com.cedar.cp.ejb.api.report.distribution.DistributionEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.report.distribution.DistributionEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class DistributionsProcessImpl extends BusinessProcessImpl implements DistributionsProcess {

   private Log mLog = new Log(this.getClass());


   public DistributionsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      DistributionEditorSessionServer es = new DistributionEditorSessionServer(this.getConnection());

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

   public DistributionEditorSession getDistributionEditorSession(Object key) throws ValidationException {
      DistributionEditorSessionImpl sess = new DistributionEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllDistributions() {
      try {
         return this.getConnection().getListHelper().getAllDistributions();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllDistributions", var2);
      }
   }

   public EntityList getDistributionForVisId(String param1) {
      try {
         return this.getConnection().getListHelper().getDistributionForVisId(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get DistributionForVisId", var3);
      }
   }

   public EntityList getDistributionDetailsForVisId(String param1) {
      try {
         return this.getConnection().getListHelper().getDistributionDetailsForVisId(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get DistributionDetailsForVisId", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing Distribution";
      return ret;
   }

   protected int getProcessID() {
      return 79;
   }

   public DistributionDetails getDistributionDetailList(String visId, int modelId, int structure_element_id, EntityRef ref) throws ValidationException {
      EntityList eList = this.getConnection().getListHelper().getDistributionForVisId(visId);
      EntityRef eRef = (EntityRef)eList.getValueAt(0, "Distribution");
      DistributionEditorSession session = this.getDistributionEditorSession(eRef.getPrimaryKey());
      return session.getDistributionDetailList(modelId, structure_element_id, ref);
   }
}
