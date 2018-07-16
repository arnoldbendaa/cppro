// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.recharge;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.model.recharge.RechargeCK;
import com.cedar.cp.dto.model.recharge.RechargeGroupEditorSessionCSO;
import com.cedar.cp.dto.model.recharge.RechargeGroupEditorSessionSSO;
import com.cedar.cp.dto.model.recharge.RechargeGroupImpl;
import com.cedar.cp.dto.model.recharge.RechargeGroupPK;
import com.cedar.cp.dto.model.recharge.RechargePK;
import com.cedar.cp.dto.model.recharge.SingleRechargeELO;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.model.recharge.RechargeDAO;
import com.cedar.cp.ejb.impl.model.recharge.RechargeGroupAccessor;
import com.cedar.cp.ejb.impl.model.recharge.RechargeGroupEVO;
import com.cedar.cp.ejb.impl.rechargegroup.RechargeGroupRelEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class RechargeGroupEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0>";
   private static final String DEPENDANTS_FOR_DELETE = "<0>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient RechargeGroupAccessor mRechargeGroupAccessor;
   private RechargeGroupEditorSessionSSO mSSO;
   private RechargeGroupPK mThisTableKey;
   private RechargeGroupEVO mRechargeGroupEVO;


   public RechargeGroupEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (RechargeGroupPK)paramKey;

      RechargeGroupEditorSessionSSO e;
      try {
         this.mRechargeGroupEVO = this.getRechargeGroupAccessor().getDetails(this.mThisTableKey, "<0>");
         this.makeItemData();
         e = this.mSSO;
      } catch (ValidationException var10) {
         throw var10;
      } catch (EJBException var11) {
         if(var11.getCause() instanceof ValidationException) {
            throw (ValidationException)var11.getCause();
         }

         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("getItemData", this.mThisTableKey);
         }

      }

      return e;
   }

   private void makeItemData() throws Exception {
      this.mSSO = new RechargeGroupEditorSessionSSO();
      RechargeGroupImpl editorData = this.buildRechargeGroupEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(RechargeGroupImpl editorData) throws Exception {}

   private RechargeGroupImpl buildRechargeGroupEditData(Object thisKey) throws Exception {
      RechargeGroupImpl editorData = new RechargeGroupImpl(thisKey);
      editorData.setVisId(this.mRechargeGroupEVO.getVisId());
      editorData.setDescription(this.mRechargeGroupEVO.getDescription());
      editorData.setVersionNum(this.mRechargeGroupEVO.getVersionNum());
      this.completeRechargeGroupEditData(editorData);
      return editorData;
   }

   private void completeRechargeGroupEditData(RechargeGroupImpl editorData) throws Exception {
      RechargeGroupRelEVO evo = null;
      ArrayList selected = new ArrayList();
      ArrayList row = null;
      RechargeDAO dao = new RechargeDAO();
      SingleRechargeELO list = null;
      ArrayList grouprel = new ArrayList(this.mRechargeGroupEVO.getRecharges());
      int size = grouprel.size();

      for(int i = 0; i < size; ++i) {
         row = new ArrayList(2);
         evo = (RechargeGroupRelEVO)grouprel.get(i);
         list = dao.getSingleRecharge(evo.getRechargeId());
         if(list.getNumRows() == 0) {
            this.mRechargeGroupEVO.deleteRechargesItem(evo.getPK());
         } else {
            if(i == 0) {
               editorData.setModelId(((Integer)list.getValueAt(0, "ModelId")).intValue());
            }

            row.add(list.getValueAt(0, "Recharge"));
            row.add(list.getValueAt(0, "Description"));
            selected.add(row);
         }
      }

      editorData.setSelectedRecharge(selected);
   }

   public RechargeGroupEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      RechargeGroupEditorSessionSSO var4;
      try {
         this.mSSO = new RechargeGroupEditorSessionSSO();
         RechargeGroupImpl e = new RechargeGroupImpl((Object)null);
         this.completeGetNewItemData(e);
         this.mSSO.setEditorData(e);
         var4 = this.mSSO;
      } catch (EJBException var9) {
         throw var9;
      } catch (Exception var10) {
         var10.printStackTrace();
         throw new EJBException(var10.getMessage(), var10);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("getNewItemData", "");
         }

      }

      return var4;
   }

   private void completeGetNewItemData(RechargeGroupImpl editorData) throws Exception {}

   public RechargeGroupPK insert(RechargeGroupEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      RechargeGroupImpl editorData = cso.getEditorData();

      RechargeGroupPK e;
      try {
         this.mRechargeGroupEVO = new RechargeGroupEVO();
         this.mRechargeGroupEVO.setVisId(editorData.getVisId());
         this.mRechargeGroupEVO.setDescription(editorData.getDescription());
         this.updateRechargeGroupRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mRechargeGroupEVO = this.getRechargeGroupAccessor().create(this.mRechargeGroupEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("RechargeGroup", this.mRechargeGroupEVO.getPK(), 1);
         e = this.mRechargeGroupEVO.getPK();
      } catch (ValidationException var10) {
         throw new EJBException(var10);
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("insert", "");
         }

      }

      return e;
   }

   private void updateRechargeGroupRelationships(RechargeGroupImpl editorData) throws ValidationException {}

   private void completeInsertSetup(RechargeGroupImpl editorData) throws Exception {
      RechargeGroupRelEVO evo = null;
      int order = 0;
      int key = 0;
      Iterator iter = editorData.getSelectedRecharge().iterator();

      while(iter.hasNext()) {
         List row = (List)iter.next();
         EntityRef ref = (EntityRef)row.get(0);
         RechargeCK ck = (RechargeCK)ref.getPrimaryKey();
         RechargePK pk = ck.getRechargePK();
         evo = new RechargeGroupRelEVO();
         evo.setRechargeGroupRelId(key--);
         evo.setRechargeId(pk.getRechargeId());
         evo.setProcessOrder(order++);
         this.mRechargeGroupEVO.addRechargesItem(evo);
      }

   }

   private void insertIntoAdditionalTables(RechargeGroupImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public RechargeGroupPK copy(RechargeGroupEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      RechargeGroupImpl editorData = cso.getEditorData();
      this.mThisTableKey = (RechargeGroupPK)editorData.getPrimaryKey();

      RechargeGroupPK var5;
      try {
         RechargeGroupEVO e = this.getRechargeGroupAccessor().getDetails(this.mThisTableKey, "<0>");
         this.mRechargeGroupEVO = e.deepClone();
         this.mRechargeGroupEVO.setVisId(editorData.getVisId());
         this.mRechargeGroupEVO.setDescription(editorData.getDescription());
         this.mRechargeGroupEVO.setVersionNum(0);
         this.updateRechargeGroupRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mRechargeGroupEVO.prepareForInsert();
         this.mRechargeGroupEVO = this.getRechargeGroupAccessor().create(this.mRechargeGroupEVO);
         this.mThisTableKey = this.mRechargeGroupEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("RechargeGroup", this.mRechargeGroupEVO.getPK(), 1);
         var5 = this.mThisTableKey;
      } catch (ValidationException var11) {
         throw new EJBException(var11);
      } catch (EJBException var12) {
         throw var12;
      } catch (Exception var13) {
         var13.printStackTrace();
         throw new EJBException(var13);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("copy", this.mThisTableKey);
         }

      }

      return var5;
   }

   private void validateCopy() throws ValidationException {}

   private void completeCopySetup(RechargeGroupImpl editorData) throws Exception {}

   public void update(RechargeGroupEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      RechargeGroupImpl editorData = cso.getEditorData();
      this.mThisTableKey = (RechargeGroupPK)editorData.getPrimaryKey();

      try {
         this.mRechargeGroupEVO = this.getRechargeGroupAccessor().getDetails(this.mThisTableKey, "<0>");
         this.preValidateUpdate(editorData);
         this.mRechargeGroupEVO.setVisId(editorData.getVisId());
         this.mRechargeGroupEVO.setDescription(editorData.getDescription());
         if(editorData.getVersionNum() != this.mRechargeGroupEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mRechargeGroupEVO.getVersionNum());
         }

         this.updateRechargeGroupRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getRechargeGroupAccessor().setDetails(this.mRechargeGroupEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("RechargeGroup", this.mRechargeGroupEVO.getPK(), 3);
      } catch (ValidationException var10) {
         throw new EJBException(var10);
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("update", this.mThisTableKey);
         }

      }

   }

   private void preValidateUpdate(RechargeGroupImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(RechargeGroupImpl editorData) throws Exception {
      RechargeGroupRelEVO evo = null;
      Iterator iter = this.mRechargeGroupEVO.getRecharges().iterator();

      while(iter.hasNext()) {
         evo = (RechargeGroupRelEVO)iter.next();
         this.mRechargeGroupEVO.deleteRechargesItem(evo.getPK());
      }

      this.completeInsertSetup(editorData);
   }

   private void updateAdditionalTables(RechargeGroupImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (RechargeGroupPK)paramKey;

      try {
         this.mRechargeGroupEVO = this.getRechargeGroupAccessor().getDetails(this.mThisTableKey, "<0>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mRechargeGroupAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("RechargeGroup", this.mThisTableKey, 2);
      } catch (ValidationException var10) {
         throw var10;
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage(), var12);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("delete", this.mThisTableKey);
         }

      }

   }

   private void deleteDataFromOtherTables() throws Exception {}

   private void validateDelete() throws ValidationException, Exception {}

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private RechargeGroupAccessor getRechargeGroupAccessor() throws Exception {
      if(this.mRechargeGroupAccessor == null) {
         this.mRechargeGroupAccessor = new RechargeGroupAccessor(this.getInitialContext());
      }

      return this.mRechargeGroupAccessor;
   }

   private void sendEntityEventMessage(String tableName, PrimaryKey pk, int changeType) {
      try {
         JmsConnectionImpl e = new JmsConnectionImpl(this.getInitialContext(), 3, "entityEventTopic");
         e.createSession();
         EntityEventMessage em = new EntityEventMessage(tableName, pk, changeType, this.getClass().getName());
         this.mLog.debug("update", "sending event message: " + em.toString());
         e.send(em);
         e.closeSession();
         e.closeConnection();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }
}
