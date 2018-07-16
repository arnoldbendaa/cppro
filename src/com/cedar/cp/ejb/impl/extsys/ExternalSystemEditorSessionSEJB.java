// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:38
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.extsys.RemoteFileSystemResource;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.extsys.ExtSysE5DB2PushTaskRequest;
import com.cedar.cp.dto.extsys.ExternalSystemEditorSessionCSO;
import com.cedar.cp.dto.extsys.ExternalSystemEditorSessionSSO;
import com.cedar.cp.dto.extsys.ExternalSystemImpl;
import com.cedar.cp.dto.extsys.ExternalSystemImportTaskRequest;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.dto.extsys.RemoteFileSystemResourceImpl;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.base.impexp.ImpExpUtils;
import com.cedar.cp.ejb.impl.extsys.ExtSysPropertyEVO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemAccessor;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemDAO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEVO;
import com.cedar.cp.ejb.impl.task.group.TaskRIChecker;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class ExternalSystemEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<11>";
   private static final String DEPENDANTS_FOR_INSERT = "<11>";
   private static final String DEPENDANTS_FOR_COPY = "<11>";
   private static final String DEPENDANTS_FOR_UPDATE = "<11>";
   private static final String DEPENDANTS_FOR_DELETE = "<0><1><2><3><4><5><6><7><8><9><10><11>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ExternalSystemAccessor mExternalSystemAccessor;
   private ExternalSystemEditorSessionSSO mSSO;
   private ExternalSystemPK mThisTableKey;
   private ExternalSystemEVO mExternalSystemEVO;


   public ExternalSystemEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (ExternalSystemPK)paramKey;

      ExternalSystemEditorSessionSSO e;
      try {
         this.mExternalSystemEVO = this.getExternalSystemAccessor().getDetails(this.mThisTableKey, "<11>");
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
      this.mSSO = new ExternalSystemEditorSessionSSO();
      ExternalSystemImpl editorData = this.buildExternalSystemEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(ExternalSystemImpl editorData) throws Exception {
      this.loadProperties(this.mExternalSystemEVO, editorData);
   }

   private ExternalSystemImpl buildExternalSystemEditData(Object thisKey) throws Exception {
      ExternalSystemImpl editorData = new ExternalSystemImpl(thisKey);
      editorData.setSystemType(this.mExternalSystemEVO.getSystemType());
      editorData.setVisId(this.mExternalSystemEVO.getVisId());
      editorData.setDescription(this.mExternalSystemEVO.getDescription());
      editorData.setLocation(this.mExternalSystemEVO.getLocation());
      editorData.setConnectorClass(this.mExternalSystemEVO.getConnectorClass());
      editorData.setImportSource(this.mExternalSystemEVO.getImportSource());
      editorData.setExportTarget(this.mExternalSystemEVO.getExportTarget());
      editorData.setEnabled(this.mExternalSystemEVO.getEnabled());
      editorData.setVersionNum(this.mExternalSystemEVO.getVersionNum());
      this.completeExternalSystemEditData(editorData);
      return editorData;
   }

   private void completeExternalSystemEditData(ExternalSystemImpl editorData) throws Exception {}

   public ExternalSystemEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      ExternalSystemEditorSessionSSO var4;
      try {
         this.mSSO = new ExternalSystemEditorSessionSSO();
         ExternalSystemImpl e = new ExternalSystemImpl((Object)null);
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

   private void completeGetNewItemData(ExternalSystemImpl editorData) throws Exception {
      editorData.setSystemType(20);
   }

   public ExternalSystemPK insert(ExternalSystemEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ExternalSystemImpl editorData = cso.getEditorData();

      ExternalSystemPK e;
      try {
         this.mExternalSystemEVO = new ExternalSystemEVO();
         this.mExternalSystemEVO.setSystemType(editorData.getSystemType());
         this.mExternalSystemEVO.setVisId(editorData.getVisId());
         this.mExternalSystemEVO.setDescription(editorData.getDescription());
         this.mExternalSystemEVO.setLocation(editorData.getLocation());
         this.mExternalSystemEVO.setConnectorClass(editorData.getConnectorClass());
         this.mExternalSystemEVO.setImportSource(editorData.getImportSource());
         this.mExternalSystemEVO.setExportTarget(editorData.getExportTarget());
         this.mExternalSystemEVO.setEnabled(editorData.isEnabled());
         this.updateExternalSystemRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mExternalSystemEVO = this.getExternalSystemAccessor().create(this.mExternalSystemEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("ExternalSystem", this.mExternalSystemEVO.getPK(), 1);
         e = this.mExternalSystemEVO.getPK();
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

   private void updateExternalSystemRelationships(ExternalSystemImpl editorData) throws ValidationException {}

   private void completeInsertSetup(ExternalSystemImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(ExternalSystemImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public ExternalSystemPK copy(ExternalSystemEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ExternalSystemImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ExternalSystemPK)editorData.getPrimaryKey();

      ExternalSystemPK var5;
      try {
         ExternalSystemEVO e = this.getExternalSystemAccessor().getDetails(this.mThisTableKey, "<11>");
         this.mExternalSystemEVO = e.deepClone();
         this.mExternalSystemEVO.setSystemType(editorData.getSystemType());
         this.mExternalSystemEVO.setVisId(editorData.getVisId());
         this.mExternalSystemEVO.setDescription(editorData.getDescription());
         this.mExternalSystemEVO.setLocation(editorData.getLocation());
         this.mExternalSystemEVO.setConnectorClass(editorData.getConnectorClass());
         this.mExternalSystemEVO.setImportSource(editorData.getImportSource());
         this.mExternalSystemEVO.setExportTarget(editorData.getExportTarget());
         this.mExternalSystemEVO.setEnabled(editorData.isEnabled());
         this.mExternalSystemEVO.setVersionNum(0);
         this.updateExternalSystemRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mExternalSystemEVO.prepareForInsert();
         this.mExternalSystemEVO = this.getExternalSystemAccessor().create(this.mExternalSystemEVO);
         this.mThisTableKey = this.mExternalSystemEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("ExternalSystem", this.mExternalSystemEVO.getPK(), 1);
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

   private void completeCopySetup(ExternalSystemImpl editorData) throws Exception {}

   public void update(ExternalSystemEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      ExternalSystemImpl editorData = cso.getEditorData();
      this.mThisTableKey = (ExternalSystemPK)editorData.getPrimaryKey();

      try {
         this.mExternalSystemEVO = this.getExternalSystemAccessor().getDetails(this.mThisTableKey, "<11>");
         this.preValidateUpdate(editorData);
         this.mExternalSystemEVO.setSystemType(editorData.getSystemType());
         this.mExternalSystemEVO.setVisId(editorData.getVisId());
         this.mExternalSystemEVO.setDescription(editorData.getDescription());
         this.mExternalSystemEVO.setLocation(editorData.getLocation());
         this.mExternalSystemEVO.setConnectorClass(editorData.getConnectorClass());
         this.mExternalSystemEVO.setImportSource(editorData.getImportSource());
         this.mExternalSystemEVO.setExportTarget(editorData.getExportTarget());
         this.mExternalSystemEVO.setEnabled(editorData.isEnabled());
         if(editorData.getVersionNum() != this.mExternalSystemEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mExternalSystemEVO.getVersionNum());
         }

         this.updateExternalSystemRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getExternalSystemAccessor().setDetails(this.mExternalSystemEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("ExternalSystem", this.mExternalSystemEVO.getPK(), 3);
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

   private void preValidateUpdate(ExternalSystemImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(ExternalSystemImpl editorData) throws Exception {
      this.updateExternalSystemEVOs(editorData, this.mExternalSystemEVO);
      this.updateXactColumnSelection(editorData);
   }

   private void updateAdditionalTables(ExternalSystemImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (ExternalSystemPK)paramKey;

      try {
         this.mExternalSystemEVO = this.getExternalSystemAccessor().getDetails(this.mThisTableKey, "<0><1><2><3><4><5><6><7><8><9><10><11>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mExternalSystemAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("ExternalSystem", this.mThisTableKey, 2);
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

   private void validateDelete() throws ValidationException, Exception {
      try {
         TaskRIChecker.isInUseTaskGroup(this.getCPConnection(), this.mExternalSystemEVO.getPK(), 7);
      } catch (ValidationException var2) {
         throw new ValidationException("External System " + var2.getMessage() + " is in use in TaskGroup ");
      }
   }

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private ExternalSystemAccessor getExternalSystemAccessor() throws Exception {
      if(this.mExternalSystemAccessor == null) {
         this.mExternalSystemAccessor = new ExternalSystemAccessor(this.getInitialContext());
      }

      return this.mExternalSystemAccessor;
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

   public EntityList getCompanies(int systemId, int systemType) throws EJBException {
      try {
         return this.getExternalSystemAccessor().getCompanies(systemId, systemType);
      } catch (EJBException var4) {
         throw var4;
      } catch (Exception var5) {
         var5.printStackTrace();
         throw new EJBException(var5.getMessage());
      }
   }

   public EntityList getFinanceLedgers(int systemId, int systemType, String company) throws EJBException {
      try {
         return this.getExternalSystemAccessor().getFinanceLedgers(systemId, systemType, company);
      } catch (EJBException var5) {
         throw var5;
      } catch (Exception var6) {
         var6.printStackTrace();
         throw new EJBException(var6.getMessage());
      }
   }

   public EntityList getFinanceCalendarYears(int systemId, int systemType, String company) throws EJBException {
      try {
         return this.getExternalSystemAccessor().getFinanceCalendarYears(systemId, systemType, company);
      } catch (EJBException var5) {
         throw var5;
      } catch (Exception var6) {
         var6.printStackTrace();
         throw new EJBException(var6.getMessage());
      }
   }
   
	public EntityList getGlobalFinanceCalendarYears(int systemId, int systemType, List<String> companies) throws EJBException {
		try {
			return this.getExternalSystemAccessor().getGlobalFinanceCalendarYears(systemId, systemType, companies);
		} catch (EJBException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

   public EntityList getFinancePeriods(int systemId, int systemType, String company, int year) throws EJBException {
      try {
         return this.getExternalSystemAccessor().getFinancePeriods(systemId, systemType, company, year);
      } catch (EJBException var6) {
         throw var6;
      } catch (Exception var7) {
         var7.printStackTrace();
         throw new EJBException(var7.getMessage());
      }
   }

   public EntityList getFinanceDimensions(int systemId, int systemType, String company, String ledger) throws EJBException {
      try {
         return this.getExternalSystemAccessor().getFinanceDimensions(systemId, systemType, company, ledger);
      } catch (EJBException var6) {
         throw var6;
      } catch (Exception var7) {
         var7.printStackTrace();
         throw new EJBException(var7.getMessage());
      }
   }
   
   public EntityList getGlobalFinanceDimensions(int systemId, int systemType, List<String> companies, String ledger) throws EJBException {
      try {
         return this.getExternalSystemAccessor().getGlobalFinanceDimensions(systemId, systemType, companies, ledger);
      } catch (EJBException e) {
         throw e;
      } catch (Exception e) {
         e.printStackTrace();
         throw new EJBException(e.getMessage());
      }
   }

   public EntityList getFinanceValueTypes(int systemId, int systemType, String company, String ledger, String dimCodes, int startYear, int cursorNum) throws EJBException {
      try {
         return this.getExternalSystemAccessor().getFinanceValueTypes(systemId, systemType, company, ledger, dimCodes, startYear, cursorNum);
      } catch (EJBException var9) {
         throw var9;
      } catch (Exception var10) {
         var10.printStackTrace();
         throw new EJBException(var10.getMessage());
      }
   }

	public EntityList getGlobalFinanceValueTypes(int systemId, int systemType, List<String> companies, String ledger, String dimCodes, int startYear, int cursorNum) throws EJBException {
		try {
			return this.getExternalSystemAccessor().getGlobalFinanceValueTypes(systemId, systemType, companies, ledger, dimCodes, startYear, cursorNum);
		} catch (EJBException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

   public EntityList getFinanceHierarchies(int systemId, int systemType, String company, String ledger, String extSysDimType, String dimCode) {
      try {
         return this.getExternalSystemAccessor().getFinanceHierarchies(systemId, systemType, company, ledger, extSysDimType, dimCode);
      } catch (EJBException var8) {
         throw var8;
      } catch (Exception var9) {
         var9.printStackTrace();
         throw new EJBException(var9.getMessage());
      }
   }
   
   public EntityList getGlobalFinanceHierarchies(int systemId, int systemType, List<String> companies, String ledger, String extSysDimType, String dimCode) {
      try {
         return this.getExternalSystemAccessor().getGlobalFinanceHierarchies(systemId, systemType, companies, ledger, extSysDimType, dimCode);
      } catch (EJBException e) {
         throw e;
      } catch (Exception e) {
         e.printStackTrace();
         throw new EJBException(e.getMessage());
      }
   }

   public EntityList getFinanceDimElementGroups(int systemId, int systemType, String company, String ledger, String extSysDimType, String dimCode, int parentType, String parent, String accType) {
      try {
         return this.getExternalSystemAccessor().getFinanceDimElementGroups(systemId, systemType, company, ledger, extSysDimType, dimCode, parentType, parent, accType);
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage());
      }
   }

	public EntityList getGlobalFinanceDimElementGroups(int systemId, int systemType, List<String> companies, String ledger, String extSysDimType, String dimCode, int parentType, String parent, String accType) {
		try {
			return this.getExternalSystemAccessor().getGlobalFinanceDimElementGroups(systemId, systemType, companies, ledger, extSysDimType, dimCode, parentType, parent, accType);
		} catch (EJBException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}
   
   public EntityList getFinanceHierarchyElems(int systemId, int systemType, String company, String ledger, String extSysDimType, String dimCode, String hierName, String hierType, String parent) {
      try {
         return this.getExternalSystemAccessor().getFinanceHierarchyElems(systemId, systemType, company, ledger, extSysDimType, dimCode, hierName, hierType, parent);
      } catch (EJBException var11) {
         throw var11;
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new EJBException(var12.getMessage());
      }
   }
   
	public EntityList getGlobalFinanceHierarchyElems(int systemId, int systemType, List<String> companies, String ledger, String extSysDimType, String dimCode, String hierName, String hierType, String parent) {
		try {
			return this.getExternalSystemAccessor().getGlobalFinanceHierarchyElems(systemId, systemType, companies, ledger, extSysDimType, dimCode, hierName, hierType, parent);
		} catch (EJBException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
	}

   public String getSuggestedModelVisId(int systemId, int systemType, String company) {
      try {
         return this.getExternalSystemAccessor().getSuggestedModelVisId(systemId, systemType, company);
      } catch (EJBException var5) {
         throw var5;
      } catch (Exception var6) {
         var6.printStackTrace();
         throw new EJBException(var6.getMessage());
      }
   }

   public int issueExternalSystemImportTask(int userId, ExternalSystemRef externalSystemRef, String importSource, int issuingTaskId) throws ValidationException, EJBException {
      try {
         ExternalSystemImportTaskRequest e = new ExternalSystemImportTaskRequest(externalSystemRef, importSource);
         return TaskMessageFactory.issueNewTask(this.getInitialContext(), false, e, userId, issuingTaskId);
      } catch (EJBException var6) {
         throw var6;
      } catch (Exception var7) {
         var7.printStackTrace();
         throw new EJBException(var7.getMessage());
      }
   }

   public URL initiateTransfer(URL clientURL, ExternalSystemRef externalSystemRef, byte[] data) throws ValidationException, EJBException {
      ExternalSystemEVO extSysEVO = (new ExternalSystemDAO()).getDetails((ExternalSystemPK)externalSystemRef.getPrimaryKey(), "");
      if(extSysEVO == null) {
         throw new ValidationException("Unable to locate external system for ref:" + externalSystemRef);
      } else {
         try {
            String e = ImpExpUtils.extractLastPathName(clientURL);
            String dir = extSysEVO.getImportSource() != null?ImpExpUtils.extractDirectory(new URL(extSysEVO.getImportSource())):null;
            if(dir == null) {
               dir = System.getProperty("user.home");
            }

            File f = new File(dir + File.separator + e);
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(data);
            fos.close();
            return f.toURL();
         } catch (MalformedURLException var9) {
            throw new ValidationException("Failed to create server file:" + var9.getMessage());
         } catch (FileNotFoundException var10) {
            throw new ValidationException("Failed to create server file:" + var10.getMessage());
         } catch (IOException var11) {
            throw new ValidationException("Failed to write to server file:" + var11.getMessage());
         }
      }
   }

   public void appendToFile(URL url, byte[] data) throws ValidationException, EJBException {
      RandomAccessFile raf = null;

      try {
         File e = new File(url.getPath());
         raf = new RandomAccessFile(e, "rw");
         raf.seek(raf.length());
         raf.write(data);
      } catch (IOException var12) {
         throw new ValidationException("Failed to append to server file:" + url + " " + var12.getMessage());
      } finally {
         if(raf != null) {
            try {
               raf.close();
            } catch (IOException var11) {
               throw new EJBException("Failed to close random access file:" + var11.getMessage(), var11);
            }
         }

      }

   }

   public List<RemoteFileSystemResource> queryRemoteFileSystem(String dir) throws ValidationException, EJBException {
      ArrayList res = new ArrayList();
      File[] files;
      int len$;
      if(dir == null) {
         File[] fd = File.listRoots();
         files = fd;
         int arr$ = fd.length;

         for(len$ = 0; len$ < arr$; ++len$) {
            File i$ = files[len$];
            RemoteFileSystemResourceImpl f = new RemoteFileSystemResourceImpl(i$.getPath(), i$.isDirectory()?2:1, (RemoteFileSystemResource)null, (List)null);
            res.add(f);
         }
      } else {
         File var10 = new File(dir);
         if(var10.exists() && var10.isDirectory()) {
            files = var10.listFiles();
            File[] var11 = files;
            len$ = files.length;

            for(int var12 = 0; var12 < len$; ++var12) {
               File var13 = var11[var12];
               RemoteFileSystemResourceImpl rfsr = new RemoteFileSystemResourceImpl(var13.getName().trim().length() > 0?var13.getName():var13.getPath(), var13.isDirectory()?2:1, (RemoteFileSystemResource)null, (List)null);
               res.add(rfsr);
            }
         }
      }

      return res;
   }

   public EntityList queryDataForPushSubmission() throws EJBException {
      return (new ExternalSystemDAO()).queryDataForPushSubmission();
   }

   public EntityList queryAllXactSubsystems(Object externalSystemPK) throws EJBException {
      return (new ExternalSystemDAO()).queryAllXactSubsystems(externalSystemPK);
   }

   public EntityList queryAllXactAvailableColumns(int subsystemId) throws EJBException {
      return (new ExternalSystemDAO()).queryAllXactAvailableColumns(subsystemId);
   }

   public EntityList queryXactColumnSelection(int subsystemId) throws EJBException {
      return (new ExternalSystemDAO()).queryXactColumnSelection(subsystemId);
   }

   private void updateXactColumnSelection(ExternalSystemImpl externalSystemImpl) {
      ExternalSystemDAO dao = new ExternalSystemDAO();
      Map allSelection = externalSystemImpl.getAllSelectedColumns();
      Set allKey = allSelection.keySet();
      Iterator i$ = allKey.iterator();

      while(i$.hasNext()) {
         Object key = i$.next();
         dao.updateXactColumnSelection(((Integer)key).intValue(), (List)allSelection.get(key));
      }

   }

   public int issueExtSysE5DB2PushTask(int userId, List<FinanceCubeRef> financeCubes) throws ValidationException, EJBException {
      try {
         ExtSysE5DB2PushTaskRequest e = new ExtSysE5DB2PushTaskRequest(financeCubes);
         return TaskMessageFactory.issueNewTask(this.getInitialContext(), false, e, userId);
      } catch (EJBException var4) {
         throw var4;
      } catch (Exception var5) {
         var5.printStackTrace();
         throw new EJBException(var5.getMessage());
      }
   }

   private void loadProperties(ExternalSystemEVO externalSystemEVO, ExternalSystemImpl externalSystemImpl) {
      if(externalSystemEVO.getExtSysProperties() != null) {
         Iterator i$ = externalSystemEVO.getExtSysProperties().iterator();

         while(i$.hasNext()) {
            ExtSysPropertyEVO extSysPropertyEVO = (ExtSysPropertyEVO)i$.next();
            externalSystemImpl.getProperties().put(extSysPropertyEVO.getPropertyName(), extSysPropertyEVO.getPropertyValue());
         }
      }

   }

   private void updateExternalSystemEVOs(ExternalSystemImpl externalSystemImpl, ExternalSystemEVO externalSystemEVO) {
      Map newProperties = externalSystemImpl.getProperties();
      HashMap existingEVOs = new HashMap();
      Iterator i$;
      if(externalSystemEVO.getExtSysProperties() != null) {
         i$ = externalSystemEVO.getExtSysProperties().iterator();

         ExtSysPropertyEVO entry;
         while(i$.hasNext()) {
            entry = (ExtSysPropertyEVO)i$.next();
            existingEVOs.put(entry.getPropertyName(), entry);
         }

         i$ = externalSystemEVO.getExtSysProperties().iterator();

         while(i$.hasNext()) {
            entry = (ExtSysPropertyEVO)i$.next();
            String extSysPropertyEVO = entry.getPropertyName();
            String value = (String)newProperties.get(extSysPropertyEVO);
            if(value != null) {
               entry.setPropertyValue(value);
               newProperties.remove(extSysPropertyEVO);
            } else {
               externalSystemEVO.deleteExtSysPropertiesItem(entry.getPK());
            }
         }
      }

      i$ = newProperties.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry1 = (Entry)i$.next();
         ExtSysPropertyEVO extSysPropertyEVO1 = new ExtSysPropertyEVO(externalSystemEVO.getExternalSystemId(), (String)entry1.getKey(), (String)entry1.getValue());
         externalSystemEVO.addExtSysPropertiesItem(extSysPropertyEVO1);
      }

   }
}
