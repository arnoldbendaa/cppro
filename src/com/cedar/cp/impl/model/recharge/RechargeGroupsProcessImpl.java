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
import com.cedar.cp.api.model.recharge.RechargeGroupEditorSession;
import com.cedar.cp.api.model.recharge.RechargeGroupsProcess;
import com.cedar.cp.dto.model.recharge.RechargeCK;
import com.cedar.cp.ejb.api.model.recharge.RechargeGroupEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.recharge.RechargeGroupEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.util.List;
import javax.swing.tree.TreeModel;

public class RechargeGroupsProcessImpl extends BusinessProcessImpl implements RechargeGroupsProcess {

   private Log mLog = new Log(this.getClass());


   public RechargeGroupsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      RechargeGroupEditorSessionServer es = new RechargeGroupEditorSessionServer(this.getConnection());

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

   public RechargeGroupEditorSession getRechargeGroupEditorSession(Object key) throws ValidationException {
      RechargeGroupEditorSessionImpl sess = new RechargeGroupEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllRechargeGroups() {
      try {
         return this.getConnection().getListHelper().getAllRechargeGroups();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllRechargeGroups", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing RechargeGroup";
      return ret;
   }

   protected int getProcessID() {
      return 70;
   }

   public EntityList getAvailableFinanceCubes(Object key) throws ValidationException {
      Object pk = ((EntityRef)key).getPrimaryKey();
      int modelid = this.getRechargeGroupEditorSession(pk).getRechargeGroupEditor().getRechargeGroup().getModelId();
      return this.getConnection().getListHelper().getFinanceCubesForModel(modelid);
   }

   public TreeModel[] getCalModel(Object key) throws ValidationException {
      Object pk = ((EntityRef)key).getPrimaryKey();
      int modelid = this.getRechargeGroupEditorSession(pk).getRechargeGroupEditor().getRechargeGroup().getModelId();
      return this.getConnection().getModelsProcess().getTreeInfoForModelDimTypes(modelid, new int[]{3});
   }

   public int[] getRechargeIds(Object key) throws ValidationException {
      Object pk = ((EntityRef)key).getPrimaryKey();
      List items = this.getRechargeGroupEditorSession(pk).getRechargeGroupEditor().getRechargeGroup().getSelectedRecharge();
      int size = items.size();
      int[] values = new int[size];

      for(int i = 0; i < size; ++i) {
         List item = (List)items.get(i);
         Object refKey = ((EntityRef)item.get(0)).getPrimaryKey();
         RechargeCK ck = (RechargeCK)refKey;
         values[i] = ck.getRechargePK().getRechargeId();
      }

      return values;
   }
}
