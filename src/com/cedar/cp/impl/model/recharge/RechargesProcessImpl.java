// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.recharge;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.recharge.RechargeEditorSession;
import com.cedar.cp.api.model.recharge.RechargesProcess;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.recharge.RechargeCK;
import com.cedar.cp.ejb.api.model.recharge.RechargeEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.recharge.RechargeEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import javax.swing.tree.TreeModel;

public class RechargesProcessImpl extends BusinessProcessImpl implements RechargesProcess {

   private Log mLog = new Log(this.getClass());


   public RechargesProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      RechargeEditorSessionServer es = new RechargeEditorSessionServer(this.getConnection());

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

   public RechargeEditorSession getRechargeEditorSession(Object key) throws ValidationException {
      RechargeEditorSessionImpl sess = new RechargeEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllRecharges() {
      try {
         return this.getConnection().getListHelper().getAllRecharges();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllRecharges", var2);
      }
   }

   public EntityList getAllRechargesWithModel(int param1) {
      try {
         return this.getConnection().getListHelper().getAllRechargesWithModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllRechargesWithModel", var3);
      }
   }

   public EntityList getSingleRecharge(int param1) {
      try {
         return this.getConnection().getListHelper().getSingleRecharge(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get SingleRecharge", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing Recharge";
      return ret;
   }

   protected int getProcessID() {
      return 69;
   }

   public EntityList getAvailableFinanceCubes(Object key) throws ValidationException {
      Object pk = ((EntityRef)key).getPrimaryKey();
      ModelRef ref = this.getRechargeEditorSession(pk).getRechargeEditor().getRecharge().getModelRef();
      return this.getConnection().getListHelper().getFinanceCubesForModel(((ModelPK)ref.getPrimaryKey()).getModelId());
   }

   public TreeModel[] getCalModel(Object key) throws ValidationException {
      Object pk = ((EntityRef)key).getPrimaryKey();
      ModelRef ref = this.getRechargeEditorSession(pk).getRechargeEditor().getRecharge().getModelRef();
      int modelid = ((ModelPK)ref.getPrimaryKey()).getModelId();
      return this.getConnection().getModelsProcess().getTreeInfoForModelDimTypes(modelid, new int[]{3});
   }

   public int[] getRechargeIds(Object key) {
      Object pk = ((EntityRef)key).getPrimaryKey();
      RechargeCK ck = (RechargeCK)pk;
      int[] value = new int[]{ck.getRechargePK().getRechargeId()};
      return value;
   }
}
