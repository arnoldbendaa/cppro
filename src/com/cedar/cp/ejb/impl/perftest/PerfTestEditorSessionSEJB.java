// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.perftest;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.perftest.PerfTestEditorSessionCSO;
import com.cedar.cp.dto.perftest.PerfTestEditorSessionSSO;
import com.cedar.cp.dto.perftest.PerfTestImpl;
import com.cedar.cp.dto.perftest.PerfTestPK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.perftest.PerfTestAccessor;
import com.cedar.cp.ejb.impl.perftest.PerfTestEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class PerfTestEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "";
   private static final String DEPENDANTS_FOR_UPDATE = "";
   private static final String DEPENDANTS_FOR_DELETE = "";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient PerfTestAccessor mPerfTestAccessor;
   private PerfTestEditorSessionSSO mSSO;
   private PerfTestPK mThisTableKey;
   private PerfTestEVO mPerfTestEVO;


   public PerfTestEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (PerfTestPK)paramKey;

      PerfTestEditorSessionSSO e;
      try {
         this.mPerfTestEVO = this.getPerfTestAccessor().getDetails(this.mThisTableKey, "");
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
      this.mSSO = new PerfTestEditorSessionSSO();
      PerfTestImpl editorData = this.buildPerfTestEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(PerfTestImpl editorData) throws Exception {}

   private PerfTestImpl buildPerfTestEditData(Object thisKey) throws Exception {
      PerfTestImpl editorData = new PerfTestImpl(thisKey);
      editorData.setVisId(this.mPerfTestEVO.getVisId());
      editorData.setDescription(this.mPerfTestEVO.getDescription());
      editorData.setClassName(this.mPerfTestEVO.getClassName());
      this.completePerfTestEditData(editorData);
      return editorData;
   }

   private void completePerfTestEditData(PerfTestImpl editorData) throws Exception {}

   public PerfTestEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      PerfTestEditorSessionSSO var4;
      try {
         this.mSSO = new PerfTestEditorSessionSSO();
         PerfTestImpl e = new PerfTestImpl((Object)null);
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

   private void completeGetNewItemData(PerfTestImpl editorData) throws Exception {}

   public PerfTestPK insert(PerfTestEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      PerfTestImpl editorData = cso.getEditorData();

      PerfTestPK e;
      try {
         this.mPerfTestEVO = new PerfTestEVO();
         this.mPerfTestEVO.setVisId(editorData.getVisId());
         this.mPerfTestEVO.setDescription(editorData.getDescription());
         this.mPerfTestEVO.setClassName(editorData.getClassName());
         this.updatePerfTestRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mPerfTestEVO = this.getPerfTestAccessor().create(this.mPerfTestEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("PerfTest", this.mPerfTestEVO.getPK(), 1);
         e = this.mPerfTestEVO.getPK();
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

   private void updatePerfTestRelationships(PerfTestImpl editorData) throws ValidationException {}

   private void completeInsertSetup(PerfTestImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(PerfTestImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public PerfTestPK copy(PerfTestEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      PerfTestImpl editorData = cso.getEditorData();
      this.mThisTableKey = (PerfTestPK)editorData.getPrimaryKey();

      PerfTestPK var5;
      try {
         PerfTestEVO e = this.getPerfTestAccessor().getDetails(this.mThisTableKey, "");
         this.mPerfTestEVO = e.deepClone();
         this.mPerfTestEVO.setVisId(editorData.getVisId());
         this.mPerfTestEVO.setDescription(editorData.getDescription());
         this.mPerfTestEVO.setClassName(editorData.getClassName());
         this.updatePerfTestRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mPerfTestEVO.prepareForInsert();
         this.mPerfTestEVO = this.getPerfTestAccessor().create(this.mPerfTestEVO);
         this.mThisTableKey = this.mPerfTestEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("PerfTest", this.mPerfTestEVO.getPK(), 1);
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

   private void completeCopySetup(PerfTestImpl editorData) throws Exception {}

   public void update(PerfTestEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      PerfTestImpl editorData = cso.getEditorData();
      this.mThisTableKey = (PerfTestPK)editorData.getPrimaryKey();

      try {
         this.mPerfTestEVO = this.getPerfTestAccessor().getDetails(this.mThisTableKey, "");
         this.preValidateUpdate(editorData);
         this.mPerfTestEVO.setVisId(editorData.getVisId());
         this.mPerfTestEVO.setDescription(editorData.getDescription());
         this.mPerfTestEVO.setClassName(editorData.getClassName());
         this.updatePerfTestRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getPerfTestAccessor().setDetails(this.mPerfTestEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("PerfTest", this.mPerfTestEVO.getPK(), 3);
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

   private void preValidateUpdate(PerfTestImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(PerfTestImpl editorData) throws Exception {}

   private void updateAdditionalTables(PerfTestImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (PerfTestPK)paramKey;

      try {
         this.mPerfTestEVO = this.getPerfTestAccessor().getDetails(this.mThisTableKey, "");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mPerfTestAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("PerfTest", this.mThisTableKey, 2);
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

   private PerfTestAccessor getPerfTestAccessor() throws Exception {
      if(this.mPerfTestAccessor == null) {
         this.mPerfTestAccessor = new PerfTestAccessor(this.getInitialContext());
      }

      return this.mPerfTestAccessor;
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
