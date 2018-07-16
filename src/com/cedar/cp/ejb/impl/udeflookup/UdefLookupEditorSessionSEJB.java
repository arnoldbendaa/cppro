// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.udeflookup;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.cc.CcDeploymentRef;
import com.cedar.cp.api.udeflookup.UdefColumn;
import com.cedar.cp.api.udeflookup.UdefLookup;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.model.CellCalcRebuildTaskRequest;
import com.cedar.cp.dto.model.cc.CcDeploymentCK;
import com.cedar.cp.dto.model.cc.CcDeploymentsForLookupTableELO;
import com.cedar.cp.dto.udeflookup.LookupAdminTaskRequest;
import com.cedar.cp.dto.udeflookup.UdefColumnImpl;
import com.cedar.cp.dto.udeflookup.UdefLookupColumnDefPK;
import com.cedar.cp.dto.udeflookup.UdefLookupEditorSessionCSO;
import com.cedar.cp.dto.udeflookup.UdefLookupEditorSessionSSO;
import com.cedar.cp.dto.udeflookup.UdefLookupImpl;
import com.cedar.cp.dto.udeflookup.UdefLookupPK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentDAO;
import com.cedar.cp.ejb.impl.task.group.TaskRIChecker;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupAccessor;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupColumnDefEVO;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupDAO;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupEVO;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupEditorSessionSEJB$1;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;

public class UdefLookupEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0>";
   private static final String DEPENDANTS_FOR_DELETE = "<0>";
   private static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
   private static SimpleDateFormat yearMonthFormat = new SimpleDateFormat("yyyy/MM");
   private transient boolean mAdminTaskNeeded;
   private transient int mPrevTimeLvl;
   private transient int mPrevYearStartMonth;
   private transient boolean mPrevTimeRange;
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient UdefLookupAccessor mUdefLookupAccessor;
   private UdefLookupEditorSessionSSO mSSO;
   private UdefLookupPK mThisTableKey;
   private UdefLookupEVO mUdefLookupEVO;


   public UdefLookupEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (UdefLookupPK)paramKey;

      UdefLookupEditorSessionSSO e;
      try {
         this.mUdefLookupEVO = this.getUdefLookupAccessor().getDetails(this.mThisTableKey, "<0>");
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
      this.mSSO = new UdefLookupEditorSessionSSO();
      UdefLookupImpl editorData = this.buildUdefLookupEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(UdefLookupImpl editorData) throws Exception {}

   private UdefLookupImpl buildUdefLookupEditData(Object thisKey) throws Exception {
      UdefLookupImpl editorData = new UdefLookupImpl(thisKey);
      editorData.setVisId(this.mUdefLookupEVO.getVisId());
      editorData.setDescription(this.mUdefLookupEVO.getDescription());
      editorData.setGenTableName(this.mUdefLookupEVO.getGenTableName());
      editorData.setAutoSubmit(this.mUdefLookupEVO.getAutoSubmit());
      editorData.setScenario(this.mUdefLookupEVO.getScenario());
      editorData.setTimeLvl(this.mUdefLookupEVO.getTimeLvl());
      editorData.setYearStartMonth(this.mUdefLookupEVO.getYearStartMonth());
      editorData.setTimeRange(this.mUdefLookupEVO.getTimeRange());
      editorData.setLastSubmit(this.mUdefLookupEVO.getLastSubmit());
      editorData.setDataUpdated(this.mUdefLookupEVO.getDataUpdated());
      this.completeUdefLookupEditData(editorData);
      return editorData;
   }

   private void completeUdefLookupEditData(UdefLookupImpl editorData) throws Exception {
      ArrayList colRows = new ArrayList();
      colRows.addAll(this.mUdefLookupEVO.getColumnDef());
      Collections.sort(colRows, new UdefLookupEditorSessionSEJB$1(this));
      ArrayList columnDef = new ArrayList();
      Iterator iter = colRows.iterator();

      while(iter.hasNext()) {
         UdefLookupColumnDefEVO evo = (UdefLookupColumnDefEVO)iter.next();
         UdefColumnImpl row = new UdefColumnImpl();
         row.setKey(evo.getPK());
         row.setIndex(evo.getColumnDefIndex());
         row.setName(evo.getName());
         row.setTitle(evo.getTitle());
         row.setType(evo.getType());
         if(row.isTimeAwareColumn()) {
            if(this.mUdefLookupEVO.getTimeLvl() == 1) {
               row.setType(4);
            } else if(this.mUdefLookupEVO.getTimeLvl() == 2) {
               row.setType(5);
            }
         }

         row.setSize(evo.getColumnSize());
         row.setDP(evo.getColumnDp());
         row.setOptional(evo.getOptional());
         columnDef.add(row);
      }

      editorData.setColumnDef(columnDef);
   }

   public UdefLookupEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      UdefLookupEditorSessionSSO var4;
      try {
         this.mSSO = new UdefLookupEditorSessionSSO();
         UdefLookupImpl e = new UdefLookupImpl((Object)null);
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

   private void completeGetNewItemData(UdefLookupImpl editorData) throws Exception {}

   public UdefLookupPK insert(UdefLookupEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      UdefLookupImpl editorData = cso.getEditorData();

      UdefLookupPK e;
      try {
         this.mUdefLookupEVO = new UdefLookupEVO();
         this.mUdefLookupEVO.setVisId(editorData.getVisId());
         this.mUdefLookupEVO.setDescription(editorData.getDescription());
         this.mUdefLookupEVO.setGenTableName(editorData.getGenTableName());
         this.mUdefLookupEVO.setAutoSubmit(editorData.isAutoSubmit());
         this.mUdefLookupEVO.setScenario(editorData.isScenario());
         this.mUdefLookupEVO.setTimeLvl(editorData.getTimeLvl());
         this.mUdefLookupEVO.setYearStartMonth(editorData.getYearStartMonth());
         this.mUdefLookupEVO.setTimeRange(editorData.isTimeRange());
         this.mUdefLookupEVO.setLastSubmit(editorData.getLastSubmit());
         this.mUdefLookupEVO.setDataUpdated(editorData.getDataUpdated());
         this.updateUdefLookupRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mUdefLookupEVO = this.getUdefLookupAccessor().create(this.mUdefLookupEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("UdefLookup", this.mUdefLookupEVO.getPK(), 1);
         e = this.mUdefLookupEVO.getPK();
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

   private void updateUdefLookupRelationships(UdefLookupImpl editorData) throws ValidationException {}

   private void completeInsertSetup(UdefLookupImpl editorData) throws Exception {
      byte counter = -1;
      int index = 0;
      boolean var10001 = this.mUdefLookupEVO.getScenario();
      int var8 = counter - 1;
      this.addOrReplaceGeneratedColumn(var10001, "SCENARIO", counter);
      this.addOrReplaceGeneratedColumn(this.mUdefLookupEVO.getTimeLvl() > 0, "TA_DATE", var8--);
      this.addOrReplaceGeneratedColumn(this.mUdefLookupEVO.getTimeLvl() > 0 && this.mUdefLookupEVO.getTimeRange(), "TA_END_DATE", var8--);
      Iterator iter = editorData.getColumnDef().iterator();

      while(iter.hasNext()) {
         UdefLookupColumnDefEVO colEvo = null;
         UdefColumn col = (UdefColumn)iter.next();
         if(!col.getName().equals("SCENARIO") && !col.isTimeAwareColumn()) {
            colEvo = new UdefLookupColumnDefEVO();
            colEvo.setColumnDefId(var8--);
            colEvo.setColumnDefIndex(index++);
            colEvo.setName(col.getName());
            colEvo.setTitle(col.getTitle());
            colEvo.setType(col.getType());
            colEvo.setColumnSize(col.getSize());
            colEvo.setColumnDp(col.getDP());
            colEvo.setOptional(col.isOptional());
            this.mUdefLookupEVO.addColumnDefItem(colEvo);
         }
      }

   }

   private void insertIntoAdditionalTables(UdefLookupImpl editorData, boolean isInsert) throws Exception {
      int lookupId = this.mUdefLookupEVO.getUdefLookupId();
      this.mUdefLookupEVO.setGenTableName("UD_" + this.mUdefLookupEVO.getVisId().toUpperCase());
      this.mUdefLookupAccessor.setDetails(this.mUdefLookupEVO);
      this.issueLookupAdminTask(lookupId, this.mUdefLookupEVO.getGenTableName(), LookupAdminTaskRequest.CREATE_LOOKUP_TABLES);
   }

   private void validateInsert() throws ValidationException {}

   public UdefLookupPK copy(UdefLookupEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      UdefLookupImpl editorData = cso.getEditorData();
      this.mThisTableKey = (UdefLookupPK)editorData.getPrimaryKey();

      UdefLookupPK var5;
      try {
         UdefLookupEVO e = this.getUdefLookupAccessor().getDetails(this.mThisTableKey, "<0>");
         this.mUdefLookupEVO = e.deepClone();
         this.mUdefLookupEVO.setVisId(editorData.getVisId());
         this.mUdefLookupEVO.setDescription(editorData.getDescription());
         this.mUdefLookupEVO.setGenTableName(editorData.getGenTableName());
         this.mUdefLookupEVO.setAutoSubmit(editorData.isAutoSubmit());
         this.mUdefLookupEVO.setScenario(editorData.isScenario());
         this.mUdefLookupEVO.setTimeLvl(editorData.getTimeLvl());
         this.mUdefLookupEVO.setYearStartMonth(editorData.getYearStartMonth());
         this.mUdefLookupEVO.setTimeRange(editorData.isTimeRange());
         this.mUdefLookupEVO.setLastSubmit(editorData.getLastSubmit());
         this.mUdefLookupEVO.setDataUpdated(editorData.getDataUpdated());
         this.updateUdefLookupRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mUdefLookupEVO.prepareForInsert();
         this.mUdefLookupEVO = this.getUdefLookupAccessor().create(this.mUdefLookupEVO);
         this.mThisTableKey = this.mUdefLookupEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("UdefLookup", this.mUdefLookupEVO.getPK(), 1);
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

   private void completeCopySetup(UdefLookupImpl editorData) throws Exception {}

   public void update(UdefLookupEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      UdefLookupImpl editorData = cso.getEditorData();
      this.mThisTableKey = (UdefLookupPK)editorData.getPrimaryKey();

      try {
         this.mUdefLookupEVO = this.getUdefLookupAccessor().getDetails(this.mThisTableKey, "<0>");
         this.preValidateUpdate(editorData);
         this.mUdefLookupEVO.setVisId(editorData.getVisId());
         this.mUdefLookupEVO.setDescription(editorData.getDescription());
         this.mUdefLookupEVO.setGenTableName(editorData.getGenTableName());
         this.mUdefLookupEVO.setAutoSubmit(editorData.isAutoSubmit());
         this.mUdefLookupEVO.setScenario(editorData.isScenario());
         this.mUdefLookupEVO.setTimeLvl(editorData.getTimeLvl());
         this.mUdefLookupEVO.setYearStartMonth(editorData.getYearStartMonth());
         this.mUdefLookupEVO.setTimeRange(editorData.isTimeRange());
         this.mUdefLookupEVO.setLastSubmit(editorData.getLastSubmit());
         this.mUdefLookupEVO.setDataUpdated(editorData.getDataUpdated());
         this.updateUdefLookupRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getUdefLookupAccessor().setDetails(this.mUdefLookupEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("UdefLookup", this.mUdefLookupEVO.getPK(), 3);
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

   private void preValidateUpdate(UdefLookupImpl editorData) throws ValidationException {
      this.mPrevTimeLvl = this.mUdefLookupEVO.getTimeLvl();
      this.mPrevYearStartMonth = this.mUdefLookupEVO.getYearStartMonth();
      this.mPrevTimeRange = this.mUdefLookupEVO.getTimeRange();
   }

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(UdefLookupImpl editorData) throws Exception {
      this.mAdminTaskNeeded = false;
      byte counter = -1;
      boolean var10002 = this.mUdefLookupEVO.getScenario();
      int var8 = counter - 1;
      this.mAdminTaskNeeded = this.addOrReplaceGeneratedColumn(var10002, "SCENARIO", counter) || this.mAdminTaskNeeded;
      this.mAdminTaskNeeded = this.addOrReplaceGeneratedColumn(this.mUdefLookupEVO.getTimeLvl() > 0, "TA_DATE", var8--) || this.mAdminTaskNeeded;
      this.mAdminTaskNeeded = this.addOrReplaceGeneratedColumn(this.mUdefLookupEVO.getTimeLvl() > 0 && this.mUdefLookupEVO.getTimeRange(), "TA_END_DATE", var8--) || this.mAdminTaskNeeded;
      int index = editorData.getNextIndex();
      Iterator iter = editorData.getColumnDef().iterator();

      UdefColumn col;
      while(iter.hasNext()) {
         col = (UdefColumn)iter.next();
         UdefLookupColumnDefEVO colEvo;
         switch(col.getState()) {
         case 1:
            colEvo = new UdefLookupColumnDefEVO();
            colEvo.setColumnDefId(var8--);
            colEvo.setColumnDefIndex(index++);
            colEvo.setName(col.getName());
            colEvo.setTitle(col.getTitle());
            colEvo.setType(col.getType());
            colEvo.setColumnSize(col.getSize());
            colEvo.setColumnDp(col.getDP());
            colEvo.setOptional(col.isOptional());
            this.mUdefLookupEVO.addColumnDefItem(colEvo);
            this.mAdminTaskNeeded = true;
            break;
         case 2:
            colEvo = this.mUdefLookupEVO.getColumnDefItem((UdefLookupColumnDefPK)col.getKey());
            colEvo.setName(col.getName());
            colEvo.setTitle(col.getTitle());
            colEvo.setType(col.getType());
            colEvo.setColumnSize(col.getSize());
            colEvo.setColumnDp(col.getDP());
            colEvo.setOptional(col.isOptional());
            this.mUdefLookupEVO.changeColumnDefItem(colEvo);
            this.mAdminTaskNeeded = true;
         }
      }

      for(iter = editorData.getRemoveKeys().iterator(); iter.hasNext(); this.mAdminTaskNeeded = true) {
         col = (UdefColumn)iter.next();
         this.mUdefLookupEVO.deleteColumnDefItem((UdefLookupColumnDefPK)col.getKey());
      }

   }

   private void updateAdditionalTables(UdefLookupImpl editorData) throws Exception {
      if(this.mAdminTaskNeeded || this.mUdefLookupEVO.getTimeLvl() != this.mPrevTimeLvl || this.mUdefLookupEVO.getYearStartMonth() != this.mPrevYearStartMonth) {
         int lookupId = this.mUdefLookupEVO.getUdefLookupId();
         this.issueLookupAdminTask(lookupId, this.mUdefLookupEVO.getGenTableName(), LookupAdminTaskRequest.ALTER_LOOKUP_TABLES);
      }

   }

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (UdefLookupPK)paramKey;

      try {
         this.mUdefLookupEVO = this.getUdefLookupAccessor().getDetails(this.mThisTableKey, "<0>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mUdefLookupAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("UdefLookup", this.mThisTableKey, 2);
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

   private void deleteDataFromOtherTables() throws Exception {
      this.issueLookupAdminTask(this.mUdefLookupEVO.getUdefLookupId(), this.mUdefLookupEVO.getGenTableName(), LookupAdminTaskRequest.DROP_LOOKUP_TABLES);
   }

   private void validateDelete() throws ValidationException, Exception {
      try {
         TaskRIChecker.isInUseTaskGroup(this.getCPConnection(), this.mUdefLookupEVO.getPK(), 6);
      } catch (ValidationException var2) {
         throw new ValidationException("Lookup table " + var2.getMessage() + " is in use in TaskGroup ");
      }
   }

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private UdefLookupAccessor getUdefLookupAccessor() throws Exception {
      if(this.mUdefLookupAccessor == null) {
         this.mUdefLookupAccessor = new UdefLookupAccessor(this.getInitialContext());
      }

      return this.mUdefLookupAccessor;
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

   private int issueLookupAdminTask(int lookupId, String genTableName, int action) throws Exception {
      try {
         LookupAdminTaskRequest e = new LookupAdminTaskRequest(lookupId, genTableName, action, this.mPrevTimeLvl, this.mPrevYearStartMonth, this.mPrevTimeRange);
         return TaskMessageFactory.issueNewTask(this.getInitialContext(), true, e, this.getUserId());
      } catch (Exception var5) {
         throw new EJBException(var5);
      }
   }

   public void saveTableData(UdefLookupEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("saveTableData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      UdefLookupImpl editorData = cso.getEditorData();

      try {
         if(editorData.getTableData().size() == 0) {
            throw new ValidationException("No data to persist");
         }

         for(int dao = 0; dao < editorData.getTableData().size(); ++dao) {
            Object[] rowData = (Object[])((Object[])editorData.getTableData().get(dao));

            for(int col = 0; col < rowData.length; ++col) {
               UdefColumn e = (UdefColumn)editorData.getColumnDef().get(col);
               Object data = rowData[col];
               BigDecimal bd;
               Boolean bol;
               Date date;
               String s;
               if(!e.isOptional()) {
                  if(data == null && e.getType() != 2) {
                     throw new ValidationException("Data can not be empty at row " + (dao + 1) + " for column " + e.getTitle());
                  }

                  switch(e.getType()) {
                  case 1:
                     bd = (BigDecimal)data;
                     break;
                  case 2:
                     bol = (Boolean)data;
                     break;
                  case 3:
                     date = (Date)data;
                     break;
                  case 4:
                  case 5:
                     try {
                        rowData[col] = this.adjustTimeAwareDate(cso, e, data);
                     } catch (ValidationException var21) {
                        throw new ValidationException("row " + dao + 1 + " column " + e.getTitle() + ": " + var21.getMessage());
                     }

                     editorData.getTableData().set(dao, rowData);
                     break;
                  default:
                     s = (String)data;
                     if(s.length() > e.getSize().intValue()) {
                        throw new ValidationException("String contents at row " + (dao + 1) + " for column " + e.getTitle() + " is too long (max = " + e.getSize() + ")");
                     }

                     if(s.length() == 0) {
                        throw new ValidationException("String contents at row " + (dao + 1) + " for column " + e.getTitle() + " can not be zero length");
                     }
                  }
               } else if(data != null) {
                  switch(e.getType()) {
                  case 1:
                     bd = (BigDecimal)data;
                     bd.setScale(e.getDP().intValue(), 0);
                     break;
                  case 2:
                     bol = (Boolean)data;
                     break;
                  case 3:
                     date = (Date)data;
                     break;
                  case 4:
                  case 5:
                     try {
                        rowData[col] = this.adjustTimeAwareDate(cso, e, (Date)data);
                     } catch (ValidationException var20) {
                        throw new ValidationException("row " + dao + 1 + " column " + e.getTitle() + ": " + var20.getMessage());
                     }

                     editorData.getTableData().set(dao, rowData);
                     break;
                  default:
                     s = (String)data;
                     if(s.length() > e.getSize().intValue()) {
                        throw new ValidationException("String contents at row " + (dao + 1) + " for column " + e.getTitle() + " is too long (max = " + e.getSize() + ")");
                     }
                  }
               }
            }
         }

         UdefLookupDAO var26 = new UdefLookupDAO();
         var26.saveTableData(editorData.getGenTableName(), editorData.getColumnDef(), editorData.getTableData());
         var26.setDataChanged((UdefLookupPK)editorData.getPrimaryKey());
         if(editorData.isAutoSubmit()) {
            this.issueRebuild(this.getUserId(), editorData.getVisId(), (UdefLookupPK)editorData.getPrimaryKey(), 0);
         }

         this.sendEntityEventMessage("UdefLookup", (UdefLookupPK)editorData.getPrimaryKey(), 3);
      } catch (ValidationException var22) {
         throw new EJBException(var22);
      } catch (EJBException var23) {
         throw var23;
      } catch (Exception var24) {
         var24.printStackTrace();
         throw new EJBException(var24.getMessage(), var24);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("saveTableData", "");
         }

      }

   }

   private Date adjustTimeAwareDate(UdefLookupEditorSessionCSO cso, UdefColumn column, Object data) throws ValidationException {
      if(data == null) {
         return null;
      } else {
         Date date = null;
         if(data instanceof String) {
            String editorData = (String)data;
            if(column.getType() == 5) {
               try {
                  if(editorData.length() != 7 || !editorData.matches("\\d\\d\\d\\d/(0[1-9]|1[012])")) {
                     throw new ValidationException("invalid YYYY/MM");
                  }

                  date = yearMonthFormat.parse(editorData + "/01");
               } catch (ParseException var9) {
                  throw new ValidationException("invalid YYYY/MM");
               }
            } else if(column.getType() == 4) {
               try {
                  if(editorData.length() != 4 || !editorData.matches("\\d\\d\\d\\d")) {
                     throw new ValidationException("invalid YYYY");
                  }

                  if(column.isRangeStart()) {
                     date = yearMonthFormat.parse(editorData + "/01");
                  } else {
                     date = yearMonthFormat.parse(editorData + "/12");
                  }
               } catch (ParseException var8) {
                  throw new ValidationException("invalid YYYY");
               }
            }
         } else {
            date = (Date)data;
         }

         UdefLookupImpl editorData1 = cso.getEditorData();
         Calendar cal = Calendar.getInstance();
         cal.setTime(date);
         if(editorData1.getTimeLvl() == 1) {
            if(column.isRangeStart()) {
               cal.set(2, editorData1.getYearStartMonth());
               cal.set(5, 1);
               return cal.getTime();
            } else {
               int endMonthNum = editorData1.getYearStartMonth() - 1;
               if(endMonthNum == -1) {
                  endMonthNum = 11;
               }

               cal.set(2, endMonthNum);
               cal.set(5, 1);
               cal.add(2, 1);
               cal.add(5, -1);
               return cal.getTime();
            }
         } else if(editorData1.getTimeLvl() == 2) {
            if(column.isRangeStart()) {
               cal.set(5, 1);
               return cal.getTime();
            } else {
               cal.add(2, 1);
               cal.add(5, -1);
               return cal.getTime();
            }
         } else {
            return date;
         }
      }
   }

   public void issueRebuild(int userId) throws ValidationException, EJBException {
      this.issueRebuild(userId, this.mUdefLookupEVO.getVisId(), this.mUdefLookupEVO.getPK(), 0);
   }

   public int[] issueRebuild(int userId, String visId, UdefLookupPK key, int issuingTaskId) throws ValidationException, EJBException {
      ArrayList issuedTasks = new ArrayList();

      try {
         CcDeploymentDAO e = new CcDeploymentDAO();
         String likeClause = "%lookupName=\"" + visId + "\"%";
         CcDeploymentsForLookupTableELO cellCalcs = e.getCcDeploymentsForLookupTable(likeClause);

         for(int dao = 0; dao < cellCalcs.getNumRows(); ++dao) {
            CcDeploymentRef issuedTaskArray = (CcDeploymentRef)cellCalcs.getValueAt(dao, "CcDeployment");
            String i = (String)cellCalcs.getValueAt(dao, "Description");
            CellCalcRebuildTaskRequest request = new CellCalcRebuildTaskRequest(((CcDeploymentCK)issuedTaskArray.getPrimaryKey()).getModelPK().getModelId(), (CcDeploymentCK)issuedTaskArray.getPrimaryKey(), issuedTaskArray.getNarrative(), i);
            request.setLookupChanged(true);
            int taskId = TaskMessageFactory.issueNewTask(new InitialContext(), false, request, userId, issuingTaskId);
            issuedTasks.add(Integer.valueOf(taskId));
            this.mLog.debug("issueCellCalcRebuild", "taskId=" + taskId);
         }

         UdefLookupDAO var15 = new UdefLookupDAO();
         var15.setSubmitData(key);
         this.sendEntityEventMessage("UdefLookup", key, 3);
         int[] var17 = new int[issuedTasks.size()];

         for(int var16 = 0; var16 < issuedTasks.size(); ++var16) {
            var17[var16] = ((Integer)issuedTasks.get(var16)).intValue();
         }

         return var17;
      } catch (Exception var14) {
         throw new EJBException(var14);
      }
   }

   private UdefLookupColumnDefEVO findColumn(String name) {
      if(this.mUdefLookupEVO.getColumnDef() == null) {
         return null;
      } else {
         Iterator i$ = this.mUdefLookupEVO.getColumnDef().iterator();

         UdefLookupColumnDefEVO col;
         do {
            if(!i$.hasNext()) {
               return null;
            }

            col = (UdefLookupColumnDefEVO)i$.next();
         } while(!name.equals(col.getName()));

         return col;
      }
   }

   public boolean addOrReplaceGeneratedColumn(boolean isNeeded, String name, int tempId) {
      UdefLookupColumnDefEVO colEvo = this.findColumn(name);
      if(isNeeded) {
         if(colEvo == null) {
            colEvo = new UdefLookupColumnDefEVO();
            if(name.equals("SCENARIO")) {
               colEvo.setColumnDefIndex(-3);
               colEvo.setTitle("Scenario");
               colEvo.setType(0);
               colEvo.setColumnSize(Integer.valueOf(20));
            } else if(name.equals("TA_DATE")) {
               colEvo.setColumnDefIndex(-2);
               colEvo.setTitle(this.mUdefLookupEVO.getTimeRange()?"Start Date":"Date");
               colEvo.setType(3);
            } else {
               if(!name.equals("TA_END_DATE")) {
                  throw new IllegalArgumentException(name + " not valid");
               }

               colEvo.setColumnDefIndex(-1);
               colEvo.setTitle("End Date");
               colEvo.setType(3);
            }

            colEvo.setColumnDefId(tempId);
            colEvo.setName(name);
            colEvo.setColumnDp(Integer.valueOf(0));
            colEvo.setOptional(false);
            this.mUdefLookupEVO.addColumnDefItem(colEvo);
            return true;
         }
      } else if(colEvo != null) {
         this.mUdefLookupEVO.deleteColumnDefItem(colEvo.getPK());
         return true;
      }

      return false;
   }

   public EntityList getUdefForms(Object key) throws ValidationException, EJBException {
      return (new UdefLookupDAO()).getUdefForms(key);
   }

   public UdefLookup getUdefLookup(String visId) throws ValidationException, EJBException {
      UdefLookupDAO dao = new UdefLookupDAO();
      this.mUdefLookupEVO = dao.getUdefLookup(visId);

      try {
         return this.buildUdefLookupEditData(this.mUdefLookupEVO.getPK());
      } catch (Exception var4) {
         throw new EJBException(var4);
      }
   }

}
