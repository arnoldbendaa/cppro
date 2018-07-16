// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:36
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.budgetlimit;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.model.AllFinanceCubesELO;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitCK;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitEditorSessionCSO;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitEditorSessionSSO;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitImpl;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.budgetlimit.BudgetLimitDAO;
import com.cedar.cp.ejb.impl.model.budgetlimit.BudgetLimitEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class BudgetLimitEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "";
   private static final String DEPENDANTS_FOR_UPDATE = "";
   private static final String DEPENDANTS_FOR_DELETE = "";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ModelAccessor mModelAccessor;
   private BudgetLimitEditorSessionSSO mSSO;
   private BudgetLimitCK mThisTableKey;
   private ModelEVO mModelEVO;
   private FinanceCubeEVO mFinanceCubeEVO;
   private BudgetLimitEVO mBudgetLimitEVO;


   public BudgetLimitEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (BudgetLimitCK)paramKey;

      BudgetLimitEditorSessionSSO e;
      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "");
         this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(this.mThisTableKey.getFinanceCubePK());
         this.mBudgetLimitEVO = this.mFinanceCubeEVO.getBudgetLimitsItem(this.mThisTableKey.getBudgetLimitPK());
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
      this.mSSO = new BudgetLimitEditorSessionSSO();
      BudgetLimitImpl editorData = this.buildBudgetLimitEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(BudgetLimitImpl editorData) throws Exception {}

   private BudgetLimitImpl buildBudgetLimitEditData(Object thisKey) throws Exception {
      BudgetLimitImpl editorData = new BudgetLimitImpl(thisKey);
      editorData.setBudgetLocationElementId(this.mBudgetLimitEVO.getBudgetLocationElementId());
      editorData.setDim0(this.mBudgetLimitEVO.getDim0());
      editorData.setDim1(this.mBudgetLimitEVO.getDim1());
      editorData.setDim2(this.mBudgetLimitEVO.getDim2());
      editorData.setDim3(this.mBudgetLimitEVO.getDim3());
      editorData.setDim4(this.mBudgetLimitEVO.getDim4());
      editorData.setDim5(this.mBudgetLimitEVO.getDim5());
      editorData.setDim6(this.mBudgetLimitEVO.getDim6());
      editorData.setDim7(this.mBudgetLimitEVO.getDim7());
      editorData.setDim8(this.mBudgetLimitEVO.getDim8());
      editorData.setDim9(this.mBudgetLimitEVO.getDim9());
      editorData.setDataType(this.mBudgetLimitEVO.getDataType());
      editorData.setMinValue(this.mBudgetLimitEVO.getMinValue());
      editorData.setMaxValue(this.mBudgetLimitEVO.getMaxValue());
      editorData.setVersionNum(this.mBudgetLimitEVO.getVersionNum());
      editorData.setFinanceCubeRef(new FinanceCubeRefImpl(this.mFinanceCubeEVO.getPK(), this.mFinanceCubeEVO.getVisId()));
      editorData.setModelRef(new ModelRefImpl(this.mModelEVO.getPK(), this.mModelEVO.getVisId()));
      this.completeBudgetLimitEditData(editorData);
      return editorData;
   }

   private void completeBudgetLimitEditData(BudgetLimitImpl editorData) throws Exception {}

   public BudgetLimitEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      BudgetLimitEditorSessionSSO var4;
      try {
         this.mSSO = new BudgetLimitEditorSessionSSO();
         BudgetLimitImpl e = new BudgetLimitImpl((Object)null);
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

   private void completeGetNewItemData(BudgetLimitImpl editorData) throws Exception {}

   public BudgetLimitCK insert(BudgetLimitEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      BudgetLimitImpl editorData = cso.getEditorData();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(editorData.getFinanceCubeRef(), "");
         this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(((FinanceCubeCK)editorData.getFinanceCubeRef().getPrimaryKey()).getFinanceCubePK());
         this.mBudgetLimitEVO = new BudgetLimitEVO();
         this.mBudgetLimitEVO.setBudgetLocationElementId(editorData.getBudgetLocationElementId());
         this.mBudgetLimitEVO.setDim0(editorData.getDim0());
         this.mBudgetLimitEVO.setDim1(editorData.getDim1());
         this.mBudgetLimitEVO.setDim2(editorData.getDim2());
         this.mBudgetLimitEVO.setDim3(editorData.getDim3());
         this.mBudgetLimitEVO.setDim4(editorData.getDim4());
         this.mBudgetLimitEVO.setDim5(editorData.getDim5());
         this.mBudgetLimitEVO.setDim6(editorData.getDim6());
         this.mBudgetLimitEVO.setDim7(editorData.getDim7());
         this.mBudgetLimitEVO.setDim8(editorData.getDim8());
         this.mBudgetLimitEVO.setDim9(editorData.getDim9());
         this.mBudgetLimitEVO.setDataType(editorData.getDataType());
         this.mBudgetLimitEVO.setMinValue(editorData.getMinValue());
         this.mBudgetLimitEVO.setMaxValue(editorData.getMaxValue());
         this.updateBudgetLimitRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mFinanceCubeEVO.addBudgetLimitsItem(this.mBudgetLimitEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<0><2>");
         this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(((FinanceCubeCK)editorData.getFinanceCubeRef().getPrimaryKey()).getFinanceCubePK());
         Iterator e = this.mFinanceCubeEVO.getBudgetLimits().iterator();

         while(true) {
            if(e.hasNext()) {
               this.mBudgetLimitEVO = (BudgetLimitEVO)e.next();
               if(!this.mBudgetLimitEVO.insertPending()) {
                  continue;
               }
            }

            this.insertIntoAdditionalTables(editorData, true);
            this.sendEntityEventMessage("BudgetLimit", this.mBudgetLimitEVO.getPK(), 1);
            BudgetLimitCK var5 = new BudgetLimitCK(this.mModelEVO.getPK(), this.mFinanceCubeEVO.getPK(), this.mBudgetLimitEVO.getPK());
            return var5;
         }
      } catch (ValidationException var11) {
         throw new EJBException(var11);
      } catch (EJBException var12) {
         throw var12;
      } catch (Exception var13) {
         var13.printStackTrace();
         throw new EJBException(var13.getMessage(), var13);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("insert", "");
         }

      }
   }

   private void updateBudgetLimitRelationships(BudgetLimitImpl editorData) throws ValidationException {}

   private void completeInsertSetup(BudgetLimitImpl editorData) throws Exception {}

   private void insertIntoAdditionalTables(BudgetLimitImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public BudgetLimitCK copy(BudgetLimitEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      BudgetLimitImpl editorData = cso.getEditorData();
      this.mThisTableKey = (BudgetLimitCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "");
         this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(this.mThisTableKey.getFinanceCubePK());
         BudgetLimitEVO e = this.mFinanceCubeEVO.getBudgetLimitsItem(this.mThisTableKey.getBudgetLimitPK());
         this.mBudgetLimitEVO = e.deepClone();
         this.mBudgetLimitEVO.setBudgetLocationElementId(editorData.getBudgetLocationElementId());
         this.mBudgetLimitEVO.setDim0(editorData.getDim0());
         this.mBudgetLimitEVO.setDim1(editorData.getDim1());
         this.mBudgetLimitEVO.setDim2(editorData.getDim2());
         this.mBudgetLimitEVO.setDim3(editorData.getDim3());
         this.mBudgetLimitEVO.setDim4(editorData.getDim4());
         this.mBudgetLimitEVO.setDim5(editorData.getDim5());
         this.mBudgetLimitEVO.setDim6(editorData.getDim6());
         this.mBudgetLimitEVO.setDim7(editorData.getDim7());
         this.mBudgetLimitEVO.setDim8(editorData.getDim8());
         this.mBudgetLimitEVO.setDim9(editorData.getDim9());
         this.mBudgetLimitEVO.setDataType(editorData.getDataType());
         this.mBudgetLimitEVO.setMinValue(editorData.getMinValue());
         this.mBudgetLimitEVO.setMaxValue(editorData.getMaxValue());
         this.mBudgetLimitEVO.setVersionNum(0);
         this.updateBudgetLimitRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         FinanceCubeCK parentKey = (FinanceCubeCK)editorData.getFinanceCubeRef().getPrimaryKey();
         if(!parentKey.getFinanceCubePK().equals(this.mFinanceCubeEVO.getPK())) {
            this.mModelEVO = this.getModelAccessor().getDetails(parentKey, "<0>");
            this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(parentKey.getFinanceCubePK());
         }

         this.mBudgetLimitEVO.prepareForInsert((FinanceCubeEVO)null);
         this.mFinanceCubeEVO.addBudgetLimitsItem(this.mBudgetLimitEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<0><2>");
         this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(parentKey.getFinanceCubePK());
         Iterator iter = this.mFinanceCubeEVO.getBudgetLimits().iterator();

         while(true) {
            if(iter.hasNext()) {
               this.mBudgetLimitEVO = (BudgetLimitEVO)iter.next();
               if(!this.mBudgetLimitEVO.insertPending()) {
                  continue;
               }
            }

            this.mThisTableKey = new BudgetLimitCK(this.mModelEVO.getPK(), this.mFinanceCubeEVO.getPK(), this.mBudgetLimitEVO.getPK());
            this.insertIntoAdditionalTables(editorData, false);
            this.sendEntityEventMessage("BudgetLimit", this.mBudgetLimitEVO.getPK(), 1);
            BudgetLimitCK var7 = this.mThisTableKey;
            return var7;
         }
      } catch (ValidationException var13) {
         throw new EJBException(var13);
      } catch (EJBException var14) {
         throw var14;
      } catch (Exception var15) {
         var15.printStackTrace();
         throw new EJBException(var15);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("copy", this.mThisTableKey);
         }

      }
   }

   private void validateCopy() throws ValidationException {}

   private void completeCopySetup(BudgetLimitImpl editorData) throws Exception {}

   public void update(BudgetLimitEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      BudgetLimitImpl editorData = cso.getEditorData();
      this.mThisTableKey = (BudgetLimitCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "");
         this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(this.mThisTableKey.getFinanceCubePK());
         this.mBudgetLimitEVO = this.mFinanceCubeEVO.getBudgetLimitsItem(this.mThisTableKey.getBudgetLimitPK());
         this.preValidateUpdate(editorData);
         this.mBudgetLimitEVO.setBudgetLocationElementId(editorData.getBudgetLocationElementId());
         this.mBudgetLimitEVO.setDim0(editorData.getDim0());
         this.mBudgetLimitEVO.setDim1(editorData.getDim1());
         this.mBudgetLimitEVO.setDim2(editorData.getDim2());
         this.mBudgetLimitEVO.setDim3(editorData.getDim3());
         this.mBudgetLimitEVO.setDim4(editorData.getDim4());
         this.mBudgetLimitEVO.setDim5(editorData.getDim5());
         this.mBudgetLimitEVO.setDim6(editorData.getDim6());
         this.mBudgetLimitEVO.setDim7(editorData.getDim7());
         this.mBudgetLimitEVO.setDim8(editorData.getDim8());
         this.mBudgetLimitEVO.setDim9(editorData.getDim9());
         this.mBudgetLimitEVO.setDataType(editorData.getDataType());
         this.mBudgetLimitEVO.setMinValue(editorData.getMinValue());
         this.mBudgetLimitEVO.setMaxValue(editorData.getMaxValue());
         if(editorData.getVersionNum() != this.mBudgetLimitEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mBudgetLimitEVO.getVersionNum());
         }

         this.updateBudgetLimitRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("BudgetLimit", this.mBudgetLimitEVO.getPK(), 3);
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

   private void preValidateUpdate(BudgetLimitImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(BudgetLimitImpl editorData) throws Exception {}

   public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
      this.mLog.debug("getOwnershipData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (BudgetLimitCK)paramKey;

      AllFinanceCubesELO e;
      try {
         e = this.getModelAccessor().getAllFinanceCubes();
      } catch (Exception var8) {
         var8.printStackTrace();
         throw new EJBException(var8.getMessage(), var8);
      } finally {
         this.setUserId(0);
         if(timer != null) {
            timer.logInfo("getOwnershipData", "");
         }

      }

      return e;
   }

   private void updateAdditionalTables(BudgetLimitImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (BudgetLimitCK)paramKey;

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "");
         this.mFinanceCubeEVO = this.mModelEVO.getFinanceCubesItem(this.mThisTableKey.getFinanceCubePK());
         this.mBudgetLimitEVO = this.mFinanceCubeEVO.getBudgetLimitsItem(this.mThisTableKey.getBudgetLimitPK());
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mFinanceCubeEVO.deleteBudgetLimitsItem(this.mThisTableKey.getBudgetLimitPK());
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.sendEntityEventMessage("BudgetLimit", this.mThisTableKey.getBudgetLimitPK(), 2);
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

   private ModelAccessor getModelAccessor() throws Exception {
      if(this.mModelAccessor == null) {
         this.mModelAccessor = new ModelAccessor(this.getInitialContext());
      }

      return this.mModelAccessor;
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

   public List getImposedLimits(int numDims, int fcId, int budgetLocation) {
      BudgetLimitDAO dao = new BudgetLimitDAO();
      return dao.getBudgetLimitsForBudgetLocation(numDims, fcId, budgetLocation, false);
   }

   public List getBudgetLimitsSetByBudgetLocation(int numDims, int fcId, int budgetLocation) {
      BudgetLimitDAO dao = new BudgetLimitDAO();
      return dao.getBudgetLimitsSetByBudgetLocation(numDims, fcId, budgetLocation);
   }
}
