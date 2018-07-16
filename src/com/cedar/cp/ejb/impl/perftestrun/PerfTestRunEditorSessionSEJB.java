// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.perftestrun;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.perftestrun.PerfTestRunResult;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.perftestrun.PerfTestRunEditorSessionCSO;
import com.cedar.cp.dto.perftestrun.PerfTestRunEditorSessionSSO;
import com.cedar.cp.dto.perftestrun.PerfTestRunImpl;
import com.cedar.cp.dto.perftestrun.PerfTestRunPK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunAccessor;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunEVO;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunResultEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class PerfTestRunEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0>";
   private static final String DEPENDANTS_FOR_DELETE = "<0>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient PerfTestRunAccessor mPerfTestRunAccessor;
   private PerfTestRunEditorSessionSSO mSSO;
   private PerfTestRunPK mThisTableKey;
   private PerfTestRunEVO mPerfTestRunEVO;


   public PerfTestRunEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (PerfTestRunPK)paramKey;

      PerfTestRunEditorSessionSSO e;
      try {
         this.mPerfTestRunEVO = this.getPerfTestRunAccessor().getDetails(this.mThisTableKey, "<0>");
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
      this.mSSO = new PerfTestRunEditorSessionSSO();
      PerfTestRunImpl editorData = this.buildPerfTestRunEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(PerfTestRunImpl editorData) throws Exception {}

   private PerfTestRunImpl buildPerfTestRunEditData(Object thisKey) throws Exception {
      PerfTestRunImpl editorData = new PerfTestRunImpl(thisKey);
      editorData.setVisId(this.mPerfTestRunEVO.getVisId());
      editorData.setDescription(this.mPerfTestRunEVO.getDescription());
      editorData.setShipped(this.mPerfTestRunEVO.getShipped());
      editorData.setWhenRan(this.mPerfTestRunEVO.getWhenRan());
      this.completePerfTestRunEditData(editorData);
      return editorData;
   }

   private void completePerfTestRunEditData(PerfTestRunImpl editorData) throws Exception {}

   public PerfTestRunEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      PerfTestRunEditorSessionSSO var4;
      try {
         this.mSSO = new PerfTestRunEditorSessionSSO();
         PerfTestRunImpl e = new PerfTestRunImpl((Object)null);
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

   private void completeGetNewItemData(PerfTestRunImpl editorData) throws Exception {}

   public PerfTestRunPK insert(PerfTestRunEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      PerfTestRunImpl editorData = cso.getEditorData();

      PerfTestRunPK e;
      try {
         this.mPerfTestRunEVO = new PerfTestRunEVO();
         this.mPerfTestRunEVO.setVisId(editorData.getVisId());
         this.mPerfTestRunEVO.setDescription(editorData.getDescription());
         this.mPerfTestRunEVO.setShipped(editorData.isShipped());
         this.mPerfTestRunEVO.setWhenRan(editorData.getWhenRan());
         this.updatePerfTestRunRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mPerfTestRunEVO = this.getPerfTestRunAccessor().create(this.mPerfTestRunEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("PerfTestRun", this.mPerfTestRunEVO.getPK(), 1);
         e = this.mPerfTestRunEVO.getPK();
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

   private void updatePerfTestRunRelationships(PerfTestRunImpl editorData) throws ValidationException {}

   private void completeInsertSetup(PerfTestRunImpl editorData) throws Exception {
      this.updateRunResults(editorData.getRunResults());
   }

   private void insertIntoAdditionalTables(PerfTestRunImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public PerfTestRunPK copy(PerfTestRunEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      PerfTestRunImpl editorData = cso.getEditorData();
      this.mThisTableKey = (PerfTestRunPK)editorData.getPrimaryKey();

      PerfTestRunPK var5;
      try {
         PerfTestRunEVO e = this.getPerfTestRunAccessor().getDetails(this.mThisTableKey, "<0>");
         this.mPerfTestRunEVO = e.deepClone();
         this.mPerfTestRunEVO.setVisId(editorData.getVisId());
         this.mPerfTestRunEVO.setDescription(editorData.getDescription());
         this.mPerfTestRunEVO.setShipped(editorData.isShipped());
         this.mPerfTestRunEVO.setWhenRan(editorData.getWhenRan());
         this.updatePerfTestRunRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mPerfTestRunEVO.prepareForInsert();
         this.mPerfTestRunEVO = this.getPerfTestRunAccessor().create(this.mPerfTestRunEVO);
         this.mThisTableKey = this.mPerfTestRunEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("PerfTestRun", this.mPerfTestRunEVO.getPK(), 1);
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

   private void completeCopySetup(PerfTestRunImpl editorData) throws Exception {}

   public void update(PerfTestRunEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      PerfTestRunImpl editorData = cso.getEditorData();
      this.mThisTableKey = (PerfTestRunPK)editorData.getPrimaryKey();

      try {
         this.mPerfTestRunEVO = this.getPerfTestRunAccessor().getDetails(this.mThisTableKey, "<0>");
         this.preValidateUpdate(editorData);
         this.mPerfTestRunEVO.setVisId(editorData.getVisId());
         this.mPerfTestRunEVO.setDescription(editorData.getDescription());
         this.mPerfTestRunEVO.setShipped(editorData.isShipped());
         this.mPerfTestRunEVO.setWhenRan(editorData.getWhenRan());
         this.updatePerfTestRunRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getPerfTestRunAccessor().setDetails(this.mPerfTestRunEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("PerfTestRun", this.mPerfTestRunEVO.getPK(), 3);
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

   private void preValidateUpdate(PerfTestRunImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(PerfTestRunImpl editorData) throws Exception {
      this.updateRunResults(editorData.getRunResults());
   }

   private void updateAdditionalTables(PerfTestRunImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (PerfTestRunPK)paramKey;

      try {
         this.mPerfTestRunEVO = this.getPerfTestRunAccessor().getDetails(this.mThisTableKey, "<0>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mPerfTestRunAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("PerfTestRun", this.mThisTableKey, 2);
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

   private PerfTestRunAccessor getPerfTestRunAccessor() throws Exception {
      if(this.mPerfTestRunAccessor == null) {
         this.mPerfTestRunAccessor = new PerfTestRunAccessor(this.getInitialContext());
      }

      return this.mPerfTestRunAccessor;
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

   private void updateRunResults(List runResults) {
      int numRowsRequired = runResults.size();
      int tempId = 0;

      for(int rowNo = 0; rowNo < numRowsRequired; ++rowNo) {
         PerfTestRunResultEVO evo = new PerfTestRunResultEVO();
         evo.setPerfTestRunResultId(tempId--);
         evo.setPerfTestRunId(this.mPerfTestRunEVO.getPerfTestRunId());
         PerfTestRunResult result = (PerfTestRunResult)runResults.get(rowNo);
         evo.setPerfTestId(result.getPerfTestId());
         evo.setExecutionTime(result.getExecutionTime());
         this.mPerfTestRunEVO.addPerfTestRunResultsItem(evo);
      }

   }
}
