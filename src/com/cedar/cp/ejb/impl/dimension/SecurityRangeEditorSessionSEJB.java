// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.dimension.SecurityRangeRow;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.dimension.AllDimensionsELO;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.SecurityRangeCK;
import com.cedar.cp.dto.dimension.SecurityRangeEditorSessionCSO;
import com.cedar.cp.dto.dimension.SecurityRangeEditorSessionSSO;
import com.cedar.cp.dto.dimension.SecurityRangeImpl;
import com.cedar.cp.dto.dimension.SecurityRangeRowImpl;
import com.cedar.cp.dto.model.AllAccessDefsUsingRangeELO;
import com.cedar.cp.dto.model.ModelForDimensionELO;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.SecurityRangeEVO;
import com.cedar.cp.ejb.impl.dimension.SecurityRangeRowEVO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class SecurityRangeEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<8>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<8>";
   private static final String DEPENDANTS_FOR_UPDATE = "<8>";
   private static final String DEPENDANTS_FOR_DELETE = "<8>";
   private transient ModelAccessor mModelAccessor;
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient DimensionAccessor mDimensionAccessor;
   private SecurityRangeEditorSessionSSO mSSO;
   private SecurityRangeCK mThisTableKey;
   private DimensionEVO mDimensionEVO;
   private SecurityRangeEVO mSecurityRangeEVO;


   public SecurityRangeEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (SecurityRangeCK)paramKey;

      SecurityRangeEditorSessionSSO e;
      try {
         this.mDimensionEVO = this.getDimensionAccessor().getDetails(this.mThisTableKey, "<8>");
         this.mSecurityRangeEVO = this.mDimensionEVO.getSecurityRangesItem(this.mThisTableKey.getSecurityRangePK());
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
      this.mSSO = new SecurityRangeEditorSessionSSO();
      SecurityRangeImpl editorData = this.buildSecurityRangeEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(SecurityRangeImpl editorData) throws Exception {}

   private SecurityRangeImpl buildSecurityRangeEditData(Object thisKey) throws Exception {
      SecurityRangeImpl editorData = new SecurityRangeImpl(thisKey);
      editorData.setVisId(this.mSecurityRangeEVO.getVisId());
      editorData.setDescription(this.mSecurityRangeEVO.getDescription());
      editorData.setVersionNum(this.mSecurityRangeEVO.getVersionNum());
      editorData.setDimensionRef(new DimensionRefImpl(this.mDimensionEVO.getPK(), this.mDimensionEVO.getVisId(), this.mDimensionEVO.getType()));
      this.completeSecurityRangeEditData(editorData);
      return editorData;
   }

   private void completeSecurityRangeEditData(SecurityRangeImpl editorData) throws Exception {
      editorData.setRanges(this.queryRangeDetails());
      ModelForDimensionELO mlist = this.getModelAccessor().getModelForDimension(this.mSecurityRangeEVO.getDimensionId());
      if(mlist.getNumRows() > 0) {
         editorData.setModelRef((ModelRefImpl)mlist.getValueAt(0, "Model"));
      }

   }

   public SecurityRangeEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      SecurityRangeEditorSessionSSO var4;
      try {
         this.mSSO = new SecurityRangeEditorSessionSSO();
         SecurityRangeImpl e = new SecurityRangeImpl((Object)null);
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

   private void completeGetNewItemData(SecurityRangeImpl editorData) throws Exception {}

   public SecurityRangeCK insert(SecurityRangeEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      SecurityRangeImpl editorData = cso.getEditorData();

      try {
         this.mDimensionEVO = this.getDimensionAccessor().getDetails(editorData.getDimensionRef(), "");
         this.mSecurityRangeEVO = new SecurityRangeEVO();
         this.mSecurityRangeEVO.setVisId(editorData.getVisId());
         this.mSecurityRangeEVO.setDescription(editorData.getDescription());
         this.updateSecurityRangeRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mDimensionEVO.addSecurityRangesItem(this.mSecurityRangeEVO);
         this.mDimensionEVO = this.getDimensionAccessor().setAndGetDetails(this.mDimensionEVO, "<7>");
         Iterator e = this.mDimensionEVO.getSecurityRanges().iterator();

         while(true) {
            if(e.hasNext()) {
               this.mSecurityRangeEVO = (SecurityRangeEVO)e.next();
               if(!this.mSecurityRangeEVO.insertPending()) {
                  continue;
               }
            }

            this.insertIntoAdditionalTables(editorData, true);
            this.sendEntityEventMessage("SecurityRange", this.mSecurityRangeEVO.getPK(), 1);
            SecurityRangeCK var5 = new SecurityRangeCK(this.mDimensionEVO.getPK(), this.mSecurityRangeEVO.getPK());
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

   private void updateSecurityRangeRelationships(SecurityRangeImpl editorData) throws ValidationException {}

   private void completeInsertSetup(SecurityRangeImpl editorData) throws Exception {
      this.updateRangeDetails(editorData.getRanges());
   }

   private void insertIntoAdditionalTables(SecurityRangeImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public SecurityRangeCK copy(SecurityRangeEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      SecurityRangeImpl editorData = cso.getEditorData();
      this.mThisTableKey = (SecurityRangeCK)editorData.getPrimaryKey();

      try {
         this.mDimensionEVO = this.getDimensionAccessor().getDetails(this.mThisTableKey, "<8>");
         SecurityRangeEVO e = this.mDimensionEVO.getSecurityRangesItem(this.mThisTableKey.getSecurityRangePK());
         this.mSecurityRangeEVO = e.deepClone();
         this.mSecurityRangeEVO.setVisId(editorData.getVisId());
         this.mSecurityRangeEVO.setDescription(editorData.getDescription());
         this.mSecurityRangeEVO.setVersionNum(0);
         this.updateSecurityRangeRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         DimensionPK parentKey = (DimensionPK)editorData.getDimensionRef().getPrimaryKey();
         if(!parentKey.equals(this.mDimensionEVO.getPK())) {
            this.mDimensionEVO = this.getDimensionAccessor().getDetails(parentKey, "");
         }

         this.mSecurityRangeEVO.prepareForInsert((DimensionEVO)null);
         this.mDimensionEVO.addSecurityRangesItem(this.mSecurityRangeEVO);
         this.mDimensionEVO = this.getDimensionAccessor().setAndGetDetails(this.mDimensionEVO, "<7><8>");
         Iterator iter = this.mDimensionEVO.getSecurityRanges().iterator();

         while(true) {
            if(iter.hasNext()) {
               this.mSecurityRangeEVO = (SecurityRangeEVO)iter.next();
               if(!this.mSecurityRangeEVO.insertPending()) {
                  continue;
               }
            }

            this.mThisTableKey = new SecurityRangeCK(this.mDimensionEVO.getPK(), this.mSecurityRangeEVO.getPK());
            this.insertIntoAdditionalTables(editorData, false);
            this.sendEntityEventMessage("SecurityRange", this.mSecurityRangeEVO.getPK(), 1);
            SecurityRangeCK var7 = this.mThisTableKey;
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

   private void completeCopySetup(SecurityRangeImpl editorData) throws Exception {}

   public void update(SecurityRangeEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      SecurityRangeImpl editorData = cso.getEditorData();
      this.mThisTableKey = (SecurityRangeCK)editorData.getPrimaryKey();

      try {
         this.mDimensionEVO = this.getDimensionAccessor().getDetails(this.mThisTableKey, "<8>");
         this.mSecurityRangeEVO = this.mDimensionEVO.getSecurityRangesItem(this.mThisTableKey.getSecurityRangePK());
         this.preValidateUpdate(editorData);
         this.mSecurityRangeEVO.setVisId(editorData.getVisId());
         this.mSecurityRangeEVO.setDescription(editorData.getDescription());
         if(editorData.getVersionNum() != this.mSecurityRangeEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mSecurityRangeEVO.getVersionNum());
         }

         this.updateSecurityRangeRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getDimensionAccessor().setDetails(this.mDimensionEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("SecurityRange", this.mSecurityRangeEVO.getPK(), 3);
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

   private void preValidateUpdate(SecurityRangeImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(SecurityRangeImpl editorData) throws Exception {
      this.updateRangeDetails(editorData.getRanges());
   }

   public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
      this.mLog.debug("getOwnershipData");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (SecurityRangeCK)paramKey;

      AllDimensionsELO e;
      try {
         e = this.getDimensionAccessor().getAllDimensions();
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

   private void updateAdditionalTables(SecurityRangeImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (SecurityRangeCK)paramKey;

      try {
         this.mDimensionEVO = this.getDimensionAccessor().getDetails(this.mThisTableKey, "<8>");
         this.mSecurityRangeEVO = this.mDimensionEVO.getSecurityRangesItem(this.mThisTableKey.getSecurityRangePK());
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mDimensionEVO.deleteSecurityRangesItem(this.mThisTableKey.getSecurityRangePK());
         this.getDimensionAccessor().setDetails(this.mDimensionEVO);
         this.sendEntityEventMessage("SecurityRange", this.mThisTableKey.getSecurityRangePK(), 2);
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
      AllAccessDefsUsingRangeELO list = this.getModelAccessor().getAllAccessDefsUsingRange(this.mSecurityRangeEVO.getSecurityRangeId());
      if(list.getNumRows() != 0) {
         throw new ValidationException("This range is referenced in one or more security access definitions");
      }
   }

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private DimensionAccessor getDimensionAccessor() throws Exception {
      if(this.mDimensionAccessor == null) {
         this.mDimensionAccessor = new DimensionAccessor(this.getInitialContext());
      }

      return this.mDimensionAccessor;
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

   private List queryRangeDetails() {
      ArrayList ranges = new ArrayList();
      List evos = this.queryExistingRows();

      for(int rowNo = 0; rowNo < evos.size(); ++rowNo) {
         SecurityRangeRowEVO securityRangeRowEVO = (SecurityRangeRowEVO)evos.get(rowNo);
         SecurityRangeRowImpl row = new SecurityRangeRowImpl();
         row.setFrom(securityRangeRowEVO.getFromId());
         row.setTo(securityRangeRowEVO.getToId());
         ranges.add(row);
      }

      Collections.sort(ranges);
      return ranges;
   }

   private void updateRangeDetails(List rangeRows) {
      int numRowsRequired = rangeRows.size();
      List existingRows = this.queryExistingRows();

      int rowNo;
      for(rowNo = 0; rowNo < existingRows.size(); ++rowNo) {
         SecurityRangeRowEVO tempId = (SecurityRangeRowEVO)existingRows.get(rowNo);
         if(rowNo < numRowsRequired) {
            SecurityRangeRow evo = (SecurityRangeRow)rangeRows.get(rowNo);
            tempId.setFromId(evo.getFrom());
            tempId.setToId(evo.getTo());
         } else {
            tempId.setDeletePending();
         }
      }

      for(int var8 = 0; rowNo < numRowsRequired; ++rowNo) {
         SecurityRangeRowEVO var9 = new SecurityRangeRowEVO();
         var9.setSecurityRangeRowId(var8--);
         var9.setSecurityRangeId(this.mSecurityRangeEVO.getSecurityRangeId());
         var9.setSequence(rowNo);
         SecurityRangeRow row = (SecurityRangeRow)rangeRows.get(rowNo);
         var9.setFromId(row.getFrom());
         var9.setToId(row.getTo());
         this.mSecurityRangeEVO.addRangeRowsItem(var9);
      }

   }

   private List queryExistingRows() {
      return (List)(this.mSecurityRangeEVO.getRangeRows() != null?new ArrayList(this.mSecurityRangeEVO.getRangeRows()):Collections.EMPTY_LIST);
   }

   private ModelAccessor getModelAccessor() throws Exception {
      if(this.mModelAccessor == null) {
         this.mModelAccessor = new ModelAccessor(this.getInitialContext());
      }

      return this.mModelAccessor;
   }
}
