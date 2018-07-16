// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.budgetinstruction;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.budgetinstruction.BudgetInstructionAssignment;
import com.cedar.cp.api.dimension.Hierarchy;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentImpl;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentPK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionEditorSessionCSO;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionEditorSessionSSO;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionImpl;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyImpl;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.StructureElementCK;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementParentsReversedELO;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.BudgetCycleRefImpl;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionAccessor;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionAssignmentEVO;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.StructureElementAccessor;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementEVO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.util.FileUtils;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class BudgetInstructionEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0>";
   private static final String DEPENDANTS_FOR_DELETE = "<0>";
   private transient ModelAccessor mModelAccessor;
   private transient StructureElementAccessor mStructureElementAccessor;
   private transient DimensionAccessor mDimensionAccessor;
   private static String sHOME_DIR = System.getProperty("user.home");
   private static String sBIStore = "CP_budgetinstruction_store";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient BudgetInstructionAccessor mBudgetInstructionAccessor;
   private BudgetInstructionEditorSessionSSO mSSO;
   private BudgetInstructionPK mThisTableKey;
   private BudgetInstructionEVO mBudgetInstructionEVO;


   public BudgetInstructionEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (BudgetInstructionPK)paramKey;

      BudgetInstructionEditorSessionSSO e;
      try {
         this.mBudgetInstructionEVO = this.getBudgetInstructionAccessor().getDetails(this.mThisTableKey, "<0>");
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
      this.mSSO = new BudgetInstructionEditorSessionSSO();
      BudgetInstructionImpl editorData = this.buildBudgetInstructionEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(BudgetInstructionImpl editorData) throws Exception {
      StructureElementDAO seDao = new StructureElementDAO();
      Iterator iter = editorData.getBudgetInstructionAssignments().iterator();

      while(iter.hasNext()) {
         BudgetInstructionAssignmentImpl bia = (BudgetInstructionAssignmentImpl)iter.next();
         if(bia.getOwningBudgetLocationRef() != null) {
            StructureElementRef se = bia.getOwningBudgetLocationRef();
            StructureElementPK pk = (StructureElementPK)se.getPrimaryKey();
            ArrayList parents = new ArrayList();
            StructureElementParentsReversedELO el = seDao.getStructureElementParentsReversed(pk.getStructureId(), pk.getStructureElementId());

            for(int i = 0; i < el.getNumRows() - 1; ++i) {
               parents.add(el.getValueAt(i, "StructureElement").toString());
            }

            bia.setParents(parents);
         }
      }

   }

   private BudgetInstructionImpl buildBudgetInstructionEditData(Object thisKey) throws Exception {
      BudgetInstructionImpl editorData = new BudgetInstructionImpl(thisKey);
      editorData.setVisId(this.mBudgetInstructionEVO.getVisId());
      editorData.setDocumentRef(this.mBudgetInstructionEVO.getDocumentRef());
      editorData.setDocument(this.mBudgetInstructionEVO.getDocument());
      editorData.setVersionNum(this.mBudgetInstructionEVO.getVersionNum());
      this.completeBudgetInstructionEditData(editorData);
      return editorData;
   }

   private void completeBudgetInstructionEditData(BudgetInstructionImpl editorData) throws Exception {
      Iterator i = this.mBudgetInstructionEVO.getBudgetInstructionAssignments().iterator();

      while(i.hasNext()) {
         BudgetInstructionAssignmentEVO biaEvo = (BudgetInstructionAssignmentEVO)i.next();
         BudgetInstructionAssignmentImpl bia = new BudgetInstructionAssignmentImpl(biaEvo.getPK());
         if(biaEvo.getModelId() != 0) {
            ModelPK sePk = new ModelPK(biaEvo.getModelId());
            bia.setOwningModelRef(new ModelRefImpl(sePk, ""));
         }

         if(biaEvo.getBudgetCycleId() != 0) {
            BudgetCyclePK sePk1 = new BudgetCyclePK(biaEvo.getBudgetCycleId());
            bia.setOwningBudgetCycleRef(new BudgetCycleRefImpl(sePk1, ""));
         }

         if(biaEvo.getBudgetLocationHierId() != 0) {
            StructureElementPK sePk2 = new StructureElementPK(biaEvo.getBudgetLocationHierId(), biaEvo.getBudgetLocationElementId());
            StructureElementEVO seEvo = this.getStructureElementAccessor().getDetails(sePk2, "");
            bia.setOwningBudgetLocationRef(seEvo.getEntityRef());
            bia.setSelectChildren(biaEvo.getSelectChildren());
         }

         bia.setSelectChildren(biaEvo.getSelectChildren());
         editorData.addBudgetInstructionAssignmentItem(bia);
      }

   }

   public BudgetInstructionEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      BudgetInstructionEditorSessionSSO var4;
      try {
         this.mSSO = new BudgetInstructionEditorSessionSSO();
         BudgetInstructionImpl e = new BudgetInstructionImpl((Object)null);
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

   private void completeGetNewItemData(BudgetInstructionImpl editorData) throws Exception {
      editorData.setBudgetInstructionAssignments(new ArrayList());
   }

   public BudgetInstructionPK insert(BudgetInstructionEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      BudgetInstructionImpl editorData = cso.getEditorData();

      BudgetInstructionPK e;
      try {
         this.mBudgetInstructionEVO = new BudgetInstructionEVO();
         this.mBudgetInstructionEVO.setVisId(editorData.getVisId());
         this.mBudgetInstructionEVO.setDocumentRef(editorData.getDocumentRef());
         this.mBudgetInstructionEVO.setDocument(editorData.getDocument());
         this.updateBudgetInstructionRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mBudgetInstructionEVO = this.getBudgetInstructionAccessor().create(this.mBudgetInstructionEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("BudgetInstruction", this.mBudgetInstructionEVO.getPK(), 1);
         e = this.mBudgetInstructionEVO.getPK();
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

   private void updateBudgetInstructionRelationships(BudgetInstructionImpl editorData) throws ValidationException {}

   private void completeInsertSetup(BudgetInstructionImpl editorData) throws Exception {
      this.completeUpdateSetup(editorData);
   }

   private void insertIntoAdditionalTables(BudgetInstructionImpl editorData, boolean isInsert) throws Exception {
      if(editorData.getDocument() == null && isInsert) {
         throw new ValidationException("a file is needed as part of insert");
      }
   }

   private void validateInsert() throws ValidationException {}

   public BudgetInstructionPK copy(BudgetInstructionEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      BudgetInstructionImpl editorData = cso.getEditorData();
      this.mThisTableKey = (BudgetInstructionPK)editorData.getPrimaryKey();

      BudgetInstructionPK var5;
      try {
         BudgetInstructionEVO e = this.getBudgetInstructionAccessor().getDetails(this.mThisTableKey, "<0>");
         this.mBudgetInstructionEVO = e.deepClone();
         this.mBudgetInstructionEVO.setVisId(editorData.getVisId());
         this.mBudgetInstructionEVO.setDocumentRef(editorData.getDocumentRef());
         this.mBudgetInstructionEVO.setDocument(editorData.getDocument());
         this.mBudgetInstructionEVO.setVersionNum(0);
         this.updateBudgetInstructionRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mBudgetInstructionEVO.prepareForInsert();
         this.mBudgetInstructionEVO = this.getBudgetInstructionAccessor().create(this.mBudgetInstructionEVO);
         this.mThisTableKey = this.mBudgetInstructionEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("BudgetInstruction", this.mBudgetInstructionEVO.getPK(), 1);
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

   private void completeCopySetup(BudgetInstructionImpl editorData) throws Exception {}

   public void update(BudgetInstructionEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      BudgetInstructionImpl editorData = cso.getEditorData();
      this.mThisTableKey = (BudgetInstructionPK)editorData.getPrimaryKey();

      try {
         this.mBudgetInstructionEVO = this.getBudgetInstructionAccessor().getDetails(this.mThisTableKey, "<0>");
         this.preValidateUpdate(editorData);
         this.mBudgetInstructionEVO.setVisId(editorData.getVisId());
         this.mBudgetInstructionEVO.setDocumentRef(editorData.getDocumentRef());
         this.mBudgetInstructionEVO.setDocument(editorData.getDocument());
         if(editorData.getVersionNum() != this.mBudgetInstructionEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mBudgetInstructionEVO.getVersionNum());
         }

         this.updateBudgetInstructionRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getBudgetInstructionAccessor().setDetails(this.mBudgetInstructionEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("BudgetInstruction", this.mBudgetInstructionEVO.getPK(), 3);
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

   private void preValidateUpdate(BudgetInstructionImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   /*     */   private void completeUpdateSetup(BudgetInstructionImpl editorData)
		   /*     */     throws Exception
		   /*     */   {
		   /* 600 */     setDocumentRef(editorData);
		   /*     */ 
		   /* 603 */     if (this.mBudgetInstructionEVO.getBudgetInstructionAssignments() == null) {
		   /* 604 */       this.mBudgetInstructionEVO.setBudgetInstructionAssignments(new ArrayList());
		   /*     */     }
		   /*     */ 
		   /* 607 */     Iterator i1 = this.mBudgetInstructionEVO.getBudgetInstructionAssignments().iterator();
		   /* 608 */     while (i1.hasNext())
		   /*     */     {
		   /* 610 */       BudgetInstructionAssignmentEVO biaEvo = (BudgetInstructionAssignmentEVO)i1.next();
		   /*     */ 
		   /* 612 */       boolean found = false;
		   /* 613 */       Iterator i2 = editorData.getBudgetInstructionAssignments().iterator();
		   /* 614 */       while (i2.hasNext())
		   /*     */       {
		   /* 616 */         BudgetInstructionAssignment bia = (BudgetInstructionAssignment)i2.next();
		   /* 617 */         if (bia.getPrimaryKey().equals(biaEvo.getPK()))
		   /*     */         {
		   /* 619 */           found = true;
		   /* 620 */           break;
		   /*     */         }
		   /*     */       }
		   /* 623 */       if (!found)
		   /*     */       {
		   /* 625 */         this.mBudgetInstructionEVO.deleteBudgetInstructionAssignmentsItem(biaEvo.getPK());
		   /* 626 */         this.mLog.debug("completeUpdateSetup", "removed " + biaEvo.getPK());
		   /*     */       }
		   /*     */     }
		   /*     */ 
		   /* 630 */     Iterator i = editorData.getBudgetInstructionAssignments().iterator();
		   /* 631 */     while (i.hasNext())
		   /*     */     {
		   /* 633 */       BudgetInstructionAssignment bia = (BudgetInstructionAssignment)i.next();
		   /* 634 */       BudgetInstructionAssignmentPK biaPk = (BudgetInstructionAssignmentPK)bia.getPrimaryKey();
		   /* 635 */       BudgetInstructionAssignmentEVO biaEvo = null;
		   /* 636 */       if (biaPk.getAssignmentId() > 0) {
		   /* 637 */         biaEvo = this.mBudgetInstructionEVO.getBudgetInstructionAssignmentsItem(biaPk);
		   /*     */       }
		   /*     */       else {
		   /* 640 */         biaEvo = new BudgetInstructionAssignmentEVO();
		   /* 641 */         biaEvo.setAssignmentId(biaPk.getAssignmentId());
		   /* 642 */         this.mBudgetInstructionEVO.addBudgetInstructionAssignmentsItem(biaEvo);
		   /*     */       }
		   /*     */ 
		   /* 646 */       biaEvo.setModelId(0);
		   /* 647 */       if (bia.getOwningModelRef() != null)
		   /*     */       {
		   /* 649 */         Object key = bia.getOwningModelRef().getPrimaryKey();
		   /* 650 */         if ((key instanceof ModelPK))
		   /* 651 */           biaEvo.setModelId(((ModelPK)key).getModelId());
		   /*     */         else {
		   /* 653 */           biaEvo.setModelId(((ModelCK)key).getModelPK().getModelId());
		   /*     */         }
		   /*     */ 
		   /*     */         try
		   /*     */         {
		   /* 658 */           getModelAccessor().getDetails(key, "");
		   /*     */         }
		   /*     */         catch (Exception ve)
		   /*     */         {
		   /* 662 */           ve.printStackTrace();
		   /* 663 */           throw new ValidationException(bia.getOwningModelRef() + " no longer exists");
		   /*     */         }
		   /*     */ 
		   /*     */       }
		   /*     */ 
		   /* 668 */       biaEvo.setBudgetCycleId(0);
		   /* 669 */       if (bia.getOwningBudgetCycleRef() != null)
		   /*     */       {
		   /* 671 */         Object key = bia.getOwningBudgetCycleRef().getPrimaryKey();
		   /* 672 */         if ((key instanceof BudgetCyclePK))
		   /* 673 */           biaEvo.setBudgetCycleId(((BudgetCyclePK)key).getBudgetCycleId());
		   /*     */         else {
		   /* 675 */           biaEvo.setBudgetCycleId(((BudgetCycleCK)key).getBudgetCyclePK().getBudgetCycleId());
		   /*     */         }
		   /*     */ 
		   /*     */         try
		   /*     */         {
		   /* 680 */           getModelAccessor().getDetails(key, "");
		   /*     */         }
		   /*     */         catch (Exception ve)
		   /*     */         {
		   /* 684 */           ve.printStackTrace();
		   /* 685 */           throw new ValidationException(bia.getOwningBudgetCycleRef() + " no longer exists");
		   /*     */         }
		   /*     */ 
		   /*     */       }
		   /*     */ 
		   /* 690 */       biaEvo.setBudgetLocationHierId(0);
		   /* 691 */       biaEvo.setBudgetLocationElementId(0);
		   /* 692 */       if (bia.getOwningBudgetLocationRef() != null)
		   /*     */       {
		   /* 694 */         biaEvo.setSelectChildren(bia.getSelectChildren());
		   /* 695 */         Object key = bia.getOwningBudgetLocationRef().getPrimaryKey();
		   /* 696 */         if ((key instanceof StructureElementPK))
		   /*     */         {
		   /* 698 */           biaEvo.setBudgetLocationHierId(((StructureElementPK)key).getStructureId());
		   /* 699 */           biaEvo.setBudgetLocationElementId(((StructureElementPK)key).getStructureElementId());
		   /*     */         }
		   /*     */         else
		   /*     */         {
		   /* 703 */           biaEvo.setBudgetLocationHierId(((StructureElementCK)key).getStructureElementPK().getStructureId());
		   /* 704 */           biaEvo.setBudgetLocationElementId(((StructureElementCK)key).getStructureElementPK().getStructureElementId());
		   /*     */         }
		   /*     */ 
		   /*     */         try
		   /*     */         {
		   /* 710 */           getStructureElementAccessor().getDetails(key, "");
		   /*     */         }
		   /*     */         catch (Exception ve)
		   /*     */         {
		   /* 714 */           ve.printStackTrace();
		   /* 715 */           throw new ValidationException(bia.getOwningBudgetLocationRef() + " no longer exists");
		   /*     */         }
		   /*     */       }
		   /*     */     }
		   /*     */   }

   private void updateAdditionalTables(BudgetInstructionImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (BudgetInstructionPK)paramKey;

      try {
         this.mBudgetInstructionEVO = this.getBudgetInstructionAccessor().getDetails(this.mThisTableKey, "<0>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mBudgetInstructionAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("BudgetInstruction", this.mThisTableKey, 2);
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
      String localFileName = this.mBudgetInstructionEVO.getDocumentRef();
      File localFile = new File(localFileName);
      if(localFile.exists()) {
         File dir = localFile.getParentFile();
         FileUtils.deleteFile(dir);
      }
   }

   private void validateDelete() throws ValidationException, Exception {}

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private BudgetInstructionAccessor getBudgetInstructionAccessor() throws Exception {
      if(this.mBudgetInstructionAccessor == null) {
         this.mBudgetInstructionAccessor = new BudgetInstructionAccessor(this.getInitialContext());
      }

      return this.mBudgetInstructionAccessor;
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

   public EntityList getAllModels() {
      try {
         return this.getModelAccessor().getAllModels();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException(var2.getMessage());
      }
   }

   public EntityList getAllFinanceCubes() {
      try {
         return this.getModelAccessor().getAllFinanceCubes();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException(var2.getMessage());
      }
   }

   public Hierarchy getBudgetLocationHierarchy(Object key) throws EJBException {
      try {
         ModelEVO ex = this.getModelAccessor().getDetails(key, "");
         HierarchyCK hierarchyCK = (HierarchyCK)this.getDimensionAccessor().getCKForDependantPK(new HierarchyPK(ex.getBudgetHierarchyId()));
         DimensionEVO dimensionEVO = this.getDimensionAccessor().getDetails(hierarchyCK, "<4><5><0>");
         HierarchyEVO hierarchyEVO = dimensionEVO.getHierarchiesItem(hierarchyCK.getHierarchyPK());
         DAGContext dagContext = new DAGContext(this.getInitialContext());
         DimensionDAG dimensionDAG = new DimensionDAG(dagContext, dimensionEVO);
         HierarchyDAG hierarchyDAG = new HierarchyDAG(dagContext, dimensionDAG, hierarchyEVO);
         HierarchyImpl hierarchy = new HierarchyImpl(hierarchyCK);
         hierarchyDAG.createLightweightDAG(hierarchy);
         return hierarchy;
      } catch (Exception var10) {
         var10.printStackTrace();
         throw new RuntimeException(var10.getMessage(), var10);
      }
   }

   private ModelAccessor getModelAccessor() throws Exception {
      if(this.mModelAccessor == null) {
         this.mModelAccessor = new ModelAccessor(this.getInitialContext());
      }

      return this.mModelAccessor;
   }

   private StructureElementAccessor getStructureElementAccessor() throws Exception {
      if(this.mStructureElementAccessor == null) {
         this.mStructureElementAccessor = new StructureElementAccessor(this.getInitialContext());
      }

      return this.mStructureElementAccessor;
   }

   private DimensionAccessor getDimensionAccessor() throws Exception {
      if(this.mDimensionAccessor == null) {
         this.mDimensionAccessor = new DimensionAccessor(this.getInitialContext());
      }

      return this.mDimensionAccessor;
   }

   private void setDocumentRef(BudgetInstructionImpl editorData) {
      String remote_filename = editorData.getDocumentRef();
      String name = "";
      int index = remote_filename.lastIndexOf("/");
      if(index < 0) {
         index = remote_filename.lastIndexOf("\\");
      }

      name = remote_filename.substring(index + 1, remote_filename.length());
      this.mBudgetInstructionEVO.setDocumentRef(name);
   }

}
