// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:29
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.dimension.AllSecurityRangesForModelELO;
import com.cedar.cp.dto.dimension.SecurityRangeRefImpl;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.AllSecurityGroupsUsingAccessDefELO;
import com.cedar.cp.dto.model.CellCalcIntegrityELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.SecurityAccRngRelPK;
import com.cedar.cp.dto.model.SecurityAccessDefCK;
import com.cedar.cp.dto.model.SecurityAccessDefEditorSessionCSO;
import com.cedar.cp.dto.model.SecurityAccessDefEditorSessionSSO;
import com.cedar.cp.dto.model.SecurityAccessDefImpl;
import com.cedar.cp.dto.model.SecurityAccessExpressionParser;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.model.CellCalcDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.SecurityAccRngRelEVO;
import com.cedar.cp.ejb.impl.model.SecurityAccessDefEVO;
import com.cedar.cp.ejb.impl.model.SecurityAccessDefEditorSessionSEJB$1;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class SecurityAccessDefEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<19>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<19>";
   private static final String DEPENDANTS_FOR_UPDATE = "<19>";
   private static final String DEPENDANTS_FOR_DELETE = "<19>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient ModelAccessor mModelAccessor;
   private SecurityAccessDefEditorSessionSSO mSSO;
   private SecurityAccessDefCK mThisTableKey;
   private ModelEVO mModelEVO;
   private SecurityAccessDefEVO mSecurityAccessDefEVO;


   public SecurityAccessDefEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (SecurityAccessDefCK)paramKey;

      SecurityAccessDefEditorSessionSSO e;
      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<19>");
         this.mSecurityAccessDefEVO = this.mModelEVO.getSecurityAccessDefsItem(this.mThisTableKey.getSecurityAccessDefPK());
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
      this.mSSO = new SecurityAccessDefEditorSessionSSO();
      SecurityAccessDefImpl editorData = this.buildSecurityAccessDefEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(SecurityAccessDefImpl editorData) throws Exception {}

   private SecurityAccessDefImpl buildSecurityAccessDefEditData(Object thisKey) throws Exception {
      SecurityAccessDefImpl editorData = new SecurityAccessDefImpl(thisKey);
      editorData.setVisId(this.mSecurityAccessDefEVO.getVisId());
      editorData.setDescription(this.mSecurityAccessDefEVO.getDescription());
      editorData.setAccessMode(this.mSecurityAccessDefEVO.getAccessMode());
      editorData.setExpression(this.mSecurityAccessDefEVO.getExpression());
      editorData.setVersionNum(this.mSecurityAccessDefEVO.getVersionNum());
      editorData.setModelRef(new ModelRefImpl(this.mModelEVO.getPK(), this.mModelEVO.getVisId()));
      this.completeSecurityAccessDefEditData(editorData);
      return editorData;
   }

   private void completeSecurityAccessDefEditData(SecurityAccessDefImpl editorData) throws Exception {}

   public SecurityAccessDefEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      SecurityAccessDefEditorSessionSSO var4;
      try {
         this.mSSO = new SecurityAccessDefEditorSessionSSO();
         SecurityAccessDefImpl e = new SecurityAccessDefImpl((Object)null);
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

   private void completeGetNewItemData(SecurityAccessDefImpl editorData) throws Exception {}

   public SecurityAccessDefCK insert(SecurityAccessDefEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      SecurityAccessDefImpl editorData = cso.getEditorData();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(editorData.getModelRef(), "");
         this.mSecurityAccessDefEVO = new SecurityAccessDefEVO();
         this.mSecurityAccessDefEVO.setVisId(editorData.getVisId());
         this.mSecurityAccessDefEVO.setDescription(editorData.getDescription());
         this.mSecurityAccessDefEVO.setAccessMode(editorData.getAccessMode());
         this.mSecurityAccessDefEVO.setExpression(editorData.getExpression());
         this.updateSecurityAccessDefRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mModelEVO.addSecurityAccessDefsItem(this.mSecurityAccessDefEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<18>");
         Iterator e = this.mModelEVO.getSecurityAccessDefs().iterator();

         while(true) {
            if(e.hasNext()) {
               this.mSecurityAccessDefEVO = (SecurityAccessDefEVO)e.next();
               if(!this.mSecurityAccessDefEVO.insertPending()) {
                  continue;
               }
            }

            this.insertIntoAdditionalTables(editorData, true);
            this.sendEntityEventMessage("SecurityAccessDef", this.mSecurityAccessDefEVO.getPK(), 1);
            SecurityAccessDefCK var5 = new SecurityAccessDefCK(this.mModelEVO.getPK(), this.mSecurityAccessDefEVO.getPK());
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

   private void updateSecurityAccessDefRelationships(SecurityAccessDefImpl editorData) throws ValidationException {}

   private void completeInsertSetup(SecurityAccessDefImpl editorData) throws Exception {
      int modelId = ((ModelRefImpl)editorData.getModelRef()).getModelPK().getModelId();
      this.updateRangeReferences(editorData.getExpression(), modelId);
   }

   private void insertIntoAdditionalTables(SecurityAccessDefImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public SecurityAccessDefCK copy(SecurityAccessDefEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      SecurityAccessDefImpl editorData = cso.getEditorData();
      this.mThisTableKey = (SecurityAccessDefCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<19>");
         SecurityAccessDefEVO e = this.mModelEVO.getSecurityAccessDefsItem(this.mThisTableKey.getSecurityAccessDefPK());
         this.mSecurityAccessDefEVO = e.deepClone();
         this.mSecurityAccessDefEVO.setVisId(editorData.getVisId());
         this.mSecurityAccessDefEVO.setDescription(editorData.getDescription());
         this.mSecurityAccessDefEVO.setAccessMode(editorData.getAccessMode());
         this.mSecurityAccessDefEVO.setExpression(editorData.getExpression());
         this.mSecurityAccessDefEVO.setVersionNum(0);
         this.updateSecurityAccessDefRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         ModelPK parentKey = (ModelPK)editorData.getModelRef().getPrimaryKey();
         if(!parentKey.equals(this.mModelEVO.getPK())) {
            this.mModelEVO = this.getModelAccessor().getDetails(parentKey, "");
         }

         this.mSecurityAccessDefEVO.prepareForInsert((ModelEVO)null);
         this.mModelEVO.addSecurityAccessDefsItem(this.mSecurityAccessDefEVO);
         this.mModelEVO = this.getModelAccessor().setAndGetDetails(this.mModelEVO, "<18><19>");
         Iterator iter = this.mModelEVO.getSecurityAccessDefs().iterator();

         while(true) {
            if(iter.hasNext()) {
               this.mSecurityAccessDefEVO = (SecurityAccessDefEVO)iter.next();
               if(!this.mSecurityAccessDefEVO.insertPending()) {
                  continue;
               }
            }

            this.mThisTableKey = new SecurityAccessDefCK(this.mModelEVO.getPK(), this.mSecurityAccessDefEVO.getPK());
            this.insertIntoAdditionalTables(editorData, false);
            this.sendEntityEventMessage("SecurityAccessDef", this.mSecurityAccessDefEVO.getPK(), 1);
            SecurityAccessDefCK var7 = this.mThisTableKey;
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

   private void completeCopySetup(SecurityAccessDefImpl editorData) throws Exception {}

   public void update(SecurityAccessDefEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      SecurityAccessDefImpl editorData = cso.getEditorData();
      this.mThisTableKey = (SecurityAccessDefCK)editorData.getPrimaryKey();

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<19>");
         this.mSecurityAccessDefEVO = this.mModelEVO.getSecurityAccessDefsItem(this.mThisTableKey.getSecurityAccessDefPK());
         this.preValidateUpdate(editorData);
         this.mSecurityAccessDefEVO.setVisId(editorData.getVisId());
         this.mSecurityAccessDefEVO.setDescription(editorData.getDescription());
         this.mSecurityAccessDefEVO.setAccessMode(editorData.getAccessMode());
         this.mSecurityAccessDefEVO.setExpression(editorData.getExpression());
         if(editorData.getVersionNum() != this.mSecurityAccessDefEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mSecurityAccessDefEVO.getVersionNum());
         }

         this.updateSecurityAccessDefRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("SecurityAccessDef", this.mSecurityAccessDefEVO.getPK(), 3);
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

   private void preValidateUpdate(SecurityAccessDefImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(SecurityAccessDefImpl editorData) throws Exception {
      int modelId = ((ModelRefImpl)editorData.getModelRef()).getModelPK().getModelId();
      this.updateRangeReferences(editorData.getExpression(), modelId);
   }

   public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
      this.mLog.debug("getOwnershipData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (SecurityAccessDefCK)paramKey;

      AllModelsELO e;
      try {
         e = this.getModelAccessor().getAllModels();
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

   private void updateAdditionalTables(SecurityAccessDefImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (SecurityAccessDefCK)paramKey;

      try {
         this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<19>");
         this.mSecurityAccessDefEVO = this.mModelEVO.getSecurityAccessDefsItem(this.mThisTableKey.getSecurityAccessDefPK());
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mModelEVO.deleteSecurityAccessDefsItem(this.mThisTableKey.getSecurityAccessDefPK());
         this.getModelAccessor().setDetails(this.mModelEVO);
         this.sendEntityEventMessage("SecurityAccessDef", this.mThisTableKey.getSecurityAccessDefPK(), 2);
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
      AllSecurityGroupsUsingAccessDefELO list = this.getModelAccessor().getAllSecurityGroupsUsingAccessDef(this.mSecurityAccessDefEVO.getSecurityAccessDefId());
      if(list.getNumRows() != 0) {
         throw new ValidationException("This access defintion is referenced in one or more security groups");
      } else {
         CellCalcDAO ccdao = new CellCalcDAO();
         CellCalcIntegrityELO var6 = ccdao.getCellCalcIntegrity();
         int numRows = var6.getNumRows();

         for(int i = 0; i < numRows; ++i) {
            Integer id = (Integer)var6.getValueAt(i, "AccessDefinitionId");
            if(this.mSecurityAccessDefEVO.getPK().getSecurityAccessDefId() == id.intValue()) {
               throw new ValidationException("This access defintion is referenced by Cell Calculation");
            }
         }

      }
   }

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

   private void updateRangeReferences(String expression, int modelId) throws Exception {
      Set usrRangeSet = this.queryRangesReferenced(expression);
      Map dbRangesMap = this.queryRangesForModel(modelId);
      Map curRangeMap = this.queryCurrentRangeMap();
      Iterator usrRangeIter = usrRangeSet.iterator();

      while(usrRangeIter.hasNext()) {
         String curRangeIter = (String)usrRangeIter.next();
         SecurityRangeRefImpl evo = (SecurityRangeRefImpl)dbRangesMap.get(curRangeIter);
         if(evo == null) {
            throw new ValidationException("Unknown range referenced [" + curRangeIter + "] in access expression");
         }

         SecurityAccRngRelPK sarrPKk = new SecurityAccRngRelPK(this.mSecurityAccessDefEVO.getPK().getSecurityAccessDefId(), evo.getSecurityRangePK().getSecurityRangeId());
         if(curRangeMap.get(sarrPKk) != null) {
            curRangeMap.remove(sarrPKk);
         } else {
            SecurityAccRngRelEVO evo1 = new SecurityAccRngRelEVO(this.mSecurityAccessDefEVO.getSecurityAccessDefId(), evo.getSecurityRangePK().getSecurityRangeId(), 0);
            this.mSecurityAccessDefEVO.addSecurityRangesForAccessDefItem(evo1);
         }
      }

      Iterator curRangeIter1 = curRangeMap.values().iterator();

      while(curRangeIter1.hasNext()) {
         SecurityAccRngRelEVO evo2 = (SecurityAccRngRelEVO)curRangeIter1.next();
         this.mSecurityAccessDefEVO.deleteSecurityRangesForAccessDefItem(evo2.getPK());
      }

   }

   private Map queryCurrentRangeMap() throws Exception {
      HashMap currRangeMap = new HashMap();
      if(this.mSecurityAccessDefEVO.getSecurityRangesForAccessDef() != null) {
         Iterator iter = this.mSecurityAccessDefEVO.getSecurityRangesForAccessDef().iterator();

         while(iter.hasNext()) {
            SecurityAccRngRelEVO evo = (SecurityAccRngRelEVO)iter.next();
            currRangeMap.put(evo.getPK(), evo);
         }
      }

      return currRangeMap;
   }

   private Set queryRangesReferenced(String expression) throws Exception {
      HashSet usrRangeSet = new HashSet();
      SecurityAccessExpressionParser parser = new SecurityAccessExpressionParser(expression);
      parser.addListener(new SecurityAccessDefEditorSessionSEJB$1(this, usrRangeSet));
      parser.parse();
      return usrRangeSet;
   }

   private Map queryRangesForModel(int modelId) throws Exception {
      DimensionAccessor accessor = new DimensionAccessor(this.getInitialContext());
      AllSecurityRangesForModelELO dbRanges = accessor.getAllSecurityRangesForModel(modelId);
      HashMap dbRangeMap = new HashMap();

      for(int i = 0; i < dbRanges.size(); ++i) {
         SecurityRangeRefImpl ref = (SecurityRangeRefImpl)dbRanges.getValueAt(i, "SecurityRange");
         dbRangeMap.put(ref.getNarrative(), ref);
      }

      return dbRangeMap;
   }
}
