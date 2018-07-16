// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.type;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.report.type.ReportTypeEditorSessionCSO;
import com.cedar.cp.dto.report.type.ReportTypeEditorSessionSSO;
import com.cedar.cp.dto.report.type.ReportTypeImpl;
import com.cedar.cp.dto.report.type.ReportTypePK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.report.type.ReportTypeAccessor;
import com.cedar.cp.ejb.impl.report.type.ReportTypeEVO;
import com.cedar.cp.ejb.impl.report.type.param.ReportTypeParamEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.ArrayList;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class ReportTypeEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0>";
   private static final String DEPENDANTS_FOR_DELETE = "<0>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ReportTypeAccessor mReportTypeAccessor;
   private ReportTypeEditorSessionSSO mSSO;
   private ReportTypePK mThisTableKey;
   private ReportTypeEVO mReportTypeEVO;


   public ReportTypeEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (ReportTypePK)paramKey;

      ReportTypeEditorSessionSSO e;
      try {
         this.mReportTypeEVO = this.getReportTypeAccessor().getDetails(this.mThisTableKey, "<0>");
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
      this.mSSO = new ReportTypeEditorSessionSSO();
      ReportTypeImpl editorData = this.buildReportTypeEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(ReportTypeImpl editorData) throws Exception {
      ArrayList row = new ArrayList();
      String[] data = null;
      Iterator iter = this.mReportTypeEVO.getReportTypeParams().iterator();

      while(iter.hasNext()) {
         ReportTypeParamEVO evo = (ReportTypeParamEVO)iter.next();
         data = new String[]{evo.getDescription(), evo.getParamEntity(), evo.getDependentEntity(), evo.getValidationExp(), evo.getValidationTxt()};
         row.add(data);
      }

      editorData.setReportParams(row);
   }

   private ReportTypeImpl buildReportTypeEditData(Object thisKey) throws Exception {
      ReportTypeImpl editorData = new ReportTypeImpl(thisKey);
      editorData.setVisId(this.mReportTypeEVO.getVisId());
      editorData.setDescription(this.mReportTypeEVO.getDescription());
      editorData.setType(this.mReportTypeEVO.getType());
      editorData.setVersionNum(this.mReportTypeEVO.getVersionNum());
      this.completeReportTypeEditData(editorData);
      return editorData;
   }

   private void completeReportTypeEditData(ReportTypeImpl editorData) throws Exception {}

   public ReportTypeEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      ReportTypeEditorSessionSSO var4;
      try {
         this.mSSO = new ReportTypeEditorSessionSSO();
         ReportTypeImpl e = new ReportTypeImpl((Object)null);
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

   private void completeGetNewItemData(ReportTypeImpl editorData) throws Exception {}

   public ReportTypePK insert(ReportTypeEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportTypeImpl editorData = cso.getEditorData();

      ReportTypePK e;
      try {
         this.mReportTypeEVO = new ReportTypeEVO();
         this.mReportTypeEVO.setVisId(editorData.getVisId());
         this.mReportTypeEVO.setDescription(editorData.getDescription());
         this.mReportTypeEVO.setType(editorData.getType());
         this.updateReportTypeRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mReportTypeEVO = this.getReportTypeAccessor().create(this.mReportTypeEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("ReportType", this.mReportTypeEVO.getPK(), 1);
         e = this.mReportTypeEVO.getPK();
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

   private void updateReportTypeRelationships(ReportTypeImpl editorData) throws ValidationException {}

   private void completeInsertSetup(ReportTypeImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(ReportTypeImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public ReportTypePK copy(ReportTypeEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportTypeImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ReportTypePK)editorData.getPrimaryKey();

      ReportTypePK var5;
      try {
         ReportTypeEVO e = this.getReportTypeAccessor().getDetails(this.mThisTableKey, "<0>");
         this.mReportTypeEVO = e.deepClone();
         this.mReportTypeEVO.setVisId(editorData.getVisId());
         this.mReportTypeEVO.setDescription(editorData.getDescription());
         this.mReportTypeEVO.setType(editorData.getType());
         this.mReportTypeEVO.setVersionNum(0);
         this.updateReportTypeRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mReportTypeEVO.prepareForInsert();
         this.mReportTypeEVO = this.getReportTypeAccessor().create(this.mReportTypeEVO);
         this.mThisTableKey = this.mReportTypeEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("ReportType", this.mReportTypeEVO.getPK(), 1);
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

   private void completeCopySetup(ReportTypeImpl editorData) throws Exception {}

   public void update(ReportTypeEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ReportTypeImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ReportTypePK)editorData.getPrimaryKey();

      try {
         this.mReportTypeEVO = this.getReportTypeAccessor().getDetails(this.mThisTableKey, "<0>");
         this.preValidateUpdate(editorData);
         this.mReportTypeEVO.setVisId(editorData.getVisId());
         this.mReportTypeEVO.setDescription(editorData.getDescription());
         this.mReportTypeEVO.setType(editorData.getType());
         if(editorData.getVersionNum() != this.mReportTypeEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mReportTypeEVO.getVersionNum());
         }

         this.updateReportTypeRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getReportTypeAccessor().setDetails(this.mReportTypeEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("ReportType", this.mReportTypeEVO.getPK(), 3);
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

   private void preValidateUpdate(ReportTypeImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(ReportTypeImpl editorData) throws Exception {}

   private void updateAdditionalTables(ReportTypeImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (ReportTypePK)paramKey;

      try {
         this.mReportTypeEVO = this.getReportTypeAccessor().getDetails(this.mThisTableKey, "<0>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mReportTypeAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("ReportType", this.mThisTableKey, 2);
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

   private ReportTypeAccessor getReportTypeAccessor() throws Exception {
      if(this.mReportTypeAccessor == null) {
         this.mReportTypeAccessor = new ReportTypeAccessor(this.getInitialContext());
      }

      return this.mReportTypeAccessor;
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
