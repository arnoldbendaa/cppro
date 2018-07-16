// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.amm;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionElementRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.amm.AmmDataTypeMap;
import com.cedar.cp.api.model.amm.AmmDimensionMapping;
import com.cedar.cp.api.model.amm.AmmFinDataTypeMap;
import com.cedar.cp.api.model.amm.AmmFinanceCubeMapping;
import com.cedar.cp.api.model.amm.AmmMap;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.dimension.AllStructureElementsELO;
import com.cedar.cp.dto.dimension.DimensionElementCK;
import com.cedar.cp.dto.dimension.DimensionElementPK;
import com.cedar.cp.dto.dimension.DimensionElementRefImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.calendar.CalendarInfoImpl;
import com.cedar.cp.dto.model.AggregatedModelTaskRequest;
import com.cedar.cp.dto.model.FinanceCubeDetailsELO;
import com.cedar.cp.dto.model.FinanceCubesForModelELO;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.amm.AmmModelEditorSessionCSO;
import com.cedar.cp.dto.model.amm.AmmModelEditorSessionSSO;
import com.cedar.cp.dto.model.amm.AmmModelImpl;
import com.cedar.cp.dto.model.amm.AmmModelPK;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmDataTypeEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmDimensionEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmDimensionElementEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmFinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmModelAccessor;
import com.cedar.cp.ejb.impl.model.amm.AmmModelDAO;
import com.cedar.cp.ejb.impl.model.amm.AmmModelEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmSrcStructureElementEVO;
import com.cedar.cp.ejb.impl.task.TaskDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;

public class AmmModelEditorSessionSEJB extends AbstractSession {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0><1><2><3><4>";
   private static final String DEPENDANTS_FOR_INSERT = "";
   private static final String DEPENDANTS_FOR_COPY = "<0><1><2><3><4>";
   private static final String DEPENDANTS_FOR_UPDATE = "<0><1><2><3><4>";
   private static final String DEPENDANTS_FOR_DELETE = "<0><1><2><3><4>";
   private transient Log mLog = new Log(this.getClass());
   private transient SessionContext mSessionContext;
   private transient AmmModelAccessor mAmmModelAccessor;
   private transient ModelAccessor mModelAccessor;
   private AmmModelEditorSessionSSO mSSO;
   private AmmModelPK mThisTableKey;
   private AmmModelEVO mAmmModelEVO;


   public AmmModelEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
      this.setUserId(userId);
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("getItemData", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.mThisTableKey = (AmmModelPK)paramKey;

      AmmModelEditorSessionSSO e;
      try {
         this.mAmmModelEVO = this.getAmmModelAccessor().getDetails(this.mThisTableKey, "<0><1><2><3><4>");
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
      this.mSSO = new AmmModelEditorSessionSSO();
      AmmModelImpl editorData = this.buildAmmModelEditData(this.mThisTableKey);
      this.completeGetItemData(editorData);
      this.mSSO.setEditorData(editorData);
   }

   private void completeGetItemData(AmmModelImpl editorData) throws Exception {
      DimensionElementDAO dimElementDAO = new DimensionElementDAO();
      StructureElementDAO strucDAO = new StructureElementDAO();
      Iterator hierarchyList = this.mAmmModelEVO.getAmmDimensions().iterator();

      AmmDimensionElementEVO detailELO;
      ArrayList var55;
      label135:
      while(hierarchyList.hasNext()) {
         AmmDimensionEVO hierarchyID = (AmmDimensionEVO)hierarchyList.next();
         AmmDimensionMapping eList = null;
         DimensionPK sourceInfo;
         DimensionRef finDAO;
         if(hierarchyID.getDimensionId() != null && hierarchyID.getSrcDimensionId() != null && hierarchyID.getSrcHierarchyId() != null) {
            sourceInfo = new DimensionPK(hierarchyID.getDimensionId().intValue());
            finDAO = this.getCPConnection().getListHelper().getDimensionEntityRef(sourceInfo);
            sourceInfo = new DimensionPK(hierarchyID.getSrcDimensionId().intValue());
            DimensionRef var36 = this.getCPConnection().getListHelper().getDimensionEntityRef(sourceInfo);
            HierarchyPK var50 = new HierarchyPK(hierarchyID.getSrcHierarchyId().intValue());
            HierarchyRef finMapping = this.getCPConnection().getListHelper().getHierarchyEntityRef(var50);
            if(finDAO.getType() == 3) {
               eList = editorData.addCalDimMapping(finDAO, var36, finMapping);
            } else {
               eList = editorData.addMappedDimMapping(finDAO, var36, finMapping);
            }
         } else if(hierarchyID.getDimensionId() != null && hierarchyID.getSrcDimensionId() == null && hierarchyID.getSrcHierarchyId() == null) {
            sourceInfo = new DimensionPK(hierarchyID.getDimensionId().intValue());
            finDAO = this.getCPConnection().getListHelper().getDimensionEntityRef(sourceInfo);
            eList = editorData.addUnmappedDimMapping(finDAO);
         } else {
            sourceInfo = new DimensionPK(hierarchyID.getSrcDimensionId().intValue());
            finDAO = this.getCPConnection().getListHelper().getDimensionEntityRef(sourceInfo);
            HierarchyPK dtDAO = new HierarchyPK(hierarchyID.getSrcHierarchyId().intValue());
            HierarchyRef elo = this.getCPConnection().getListHelper().getHierarchyEntityRef(dtDAO);
            eList = editorData.addUnmappedSourceDimMapping(finDAO, elo);
         }

         Iterator var38;
         HashMap var37;
         AmmDimensionElementEVO var41;
         switch(eList.getType().intValue()) {
         case 2:
            Iterator var39 = hierarchyID.getAmmDimElements().iterator();

            while(true) {
               if(!var39.hasNext()) {
                  continue label135;
               }

               AmmDimensionElementEVO var42 = (AmmDimensionElementEVO)var39.next();
               DimensionElementPK var49 = new DimensionElementPK(var42.getDimensionElementId().intValue());
               DimensionElementRefImpl var47 = dimElementDAO.getRef(var49);
               eList.setUnmappedDimensionRef(var47);
            }
         case 3:
            ArrayList var34 = new ArrayList();
            var38 = hierarchyID.getAmmDimElements().iterator();

            while(var38.hasNext()) {
               var41 = (AmmDimensionElementEVO)var38.next();
               Iterator var45 = var41.getAmmSourceElements().iterator();

               while(var45.hasNext()) {
                  AmmSrcStructureElementEVO var56 = (AmmSrcStructureElementEVO)var45.next();
                  EntityList var53 = this.getCPConnection().getListHelper().getStructureElement(var56.getSrcStructureElementId());
                  var34.add((StructureElementRef)var53.getValueAt(0, "StructureElement"));
               }
            }

            if(!var34.isEmpty()) {
               eList.setSourceElementData(var34);
            }
            continue;
         case 4:
            EntityList var35 = this.getCPConnection().getListHelper().getCalendarForModel(this.mAmmModelEVO.getModelId());
            Integer var40 = (Integer)var35.getValueAt(0, "HierarchyId");
            AllStructureElementsELO var44 = strucDAO.getAllStructureElements(var40.intValue());
            CalendarInfoImpl var52 = CalendarInfoImpl.getCalendarInfo(var40, var44);
            HashMap var48 = new HashMap();
            Iterator finDTMappping = hierarchyID.getAmmDimElements().iterator();

            while(finDTMappping.hasNext()) {
               detailELO = (AmmDimensionElementEVO)finDTMappping.next();
               CalendarElementNode finMap = var52.getById(detailELO.getDimensionElementId());
               ArrayList i$ = new ArrayList();
               Iterator finEVO = detailELO.getAmmSourceElements().iterator();

               while(finEVO.hasNext()) {
                  AmmSrcStructureElementEVO dtFinEL = (AmmSrcStructureElementEVO)finEVO.next();
                  StructureElementPK dtSize = new StructureElementPK(eList.getSourcehierarchyId().intValue(), dtFinEL.getSrcStructureElementId());
                  i$.add(strucDAO.getRef(dtSize));
               }

               if(finMap != null) {
                  var48.put(finMap, i$);
               }
            }

            eList.setSelectedCalanderElementData(var48);
            continue;
         default:
            var37 = new HashMap();
            var38 = hierarchyID.getAmmDimElements().iterator();
         }

         while(var38.hasNext()) {
            var41 = (AmmDimensionElementEVO)var38.next();
            DimensionElementPK var57 = new DimensionElementPK(var41.getDimensionElementId().intValue());
            DimensionElementRefImpl var58 = dimElementDAO.getRef(var57);
            var55 = new ArrayList();
            Iterator var61 = var41.getAmmSourceElements().iterator();

            while(var61.hasNext()) {
               AmmSrcStructureElementEVO var63 = (AmmSrcStructureElementEVO)var61.next();
               StructureElementPK var62 = new StructureElementPK(eList.getSourcehierarchyId().intValue(), var63.getSrcStructureElementId());
               var55.add(strucDAO.getRef(var62));
            }

            var37.put(var58, var55);
         }

         eList.setSelectedElementData(var37);
      }

      EntityList var31 = this.getCPConnection().getListHelper().getCalendarForModel(this.mAmmModelEVO.getSrcModelId());
      Integer var32 = (Integer)var31.getValueAt(0, "HierarchyId");
      AllStructureElementsELO var33 = strucDAO.getAllStructureElements(var32.intValue());
      CalendarInfoImpl var43 = CalendarInfoImpl.getCalendarInfo(var32, var33);
      editorData.setSourceInfo(var43);
      FinanceCubeDAO var51 = new FinanceCubeDAO();
      DataTypeDAO var46 = new DataTypeDAO();
      FinanceCubesForModelELO var59 = var51.getFinanceCubesForModel(this.mAmmModelEVO.getSrcModelId());
      ArrayList var54 = new ArrayList(var59.getNumRows());
      detailELO = null;

      while(var59.hasNext()) {
         var59.next();
         AmmFinanceCubeMapping var64 = new AmmFinanceCubeMapping();
         var64.setSourceFinanceCubeRef(var59.getFinanceCubeEntityRef());
         var64.setSourceFinanceCubeId(var59.getFinanceCubeId());
         var64.setSourceDescription(var59.getDescription());
         Iterator var66 = this.mAmmModelEVO.getAmmFinanceCubes().iterator();

         while(var66.hasNext()) {
            AmmFinanceCubeEVO var65 = (AmmFinanceCubeEVO)var66.next();
            if(var59.getFinanceCubeId() == var65.getSrcFinanceCubeId()) {
               FinanceCubeDetailsELO var60 = var51.getFinanceCubeDetails(var65.getFinanceCubeId());
               var60.next();
               var64.setFinanceCubeId(var60.getFinanceCubeId());
               var64.setFinanceCubeRef(var60.getFinanceCubeEntityRef());
               var64.setDescription(var60.getDescription());
               EntityList var67 = this.getCPConnection().getListHelper().getPickerDataTypesWeb(var59.getFinanceCubeId(), new int[]{0, 1, 2, 3}, false);
               int var68 = var67.getNumRows();
               var55 = new ArrayList(var68);

               for(int m = 0; m < var68; ++m) {
                  AmmFinDataTypeMap finDTMap = new AmmFinDataTypeMap();
                  Iterator i$1 = var65.getAmmDataTypes().iterator();

                  while(i$1.hasNext()) {
                     AmmDataTypeEVO dtEVO = (AmmDataTypeEVO)i$1.next();
                     DataTypeRef dtREF = (DataTypeRef)var67.getValueAt(m, "DataType");
                     String dtDescription = (String)var67.getValueAt(m, "Description");
                     Short dtId = (Short)var67.getValueAt(m, "DataTypeId");
                     finDTMap.setSourceRef(dtREF);
                     finDTMap.setSourceDescription(dtDescription);
                     if(dtEVO.getSrcDataTypeId() == dtId.intValue()) {
                        DataTypePK dtPK = new DataTypePK((short)dtEVO.getDataTypeId());
                        dtREF = var46.getRef(dtPK);
                        finDTMap.setTargetRef(dtREF);
                        CalendarElementNode root = var43.getRoot();
                        int numYears = root.getChildren().size();
                        int startYearID = numYears + dtEVO.getSrcStartYearOffset();
                        int endYearID = numYears + dtEVO.getSrcEndYearOffset();
                        finDTMap.setStartYear((CalendarElementNode)root.getChildren().get(startYearID - 1));
                        finDTMap.setStartOffset(dtEVO.getSrcStartYearOffset());
                        finDTMap.setEndYear((CalendarElementNode)root.getChildren().get(endYearID - 1));
                        finDTMap.setEndOffset(dtEVO.getSrcEndYearOffset());
                     }
                  }

                  var55.add(finDTMap);
               }

               HashMap var69 = new HashMap();
               var69.put(var60.getFinanceCubeEntityRef(), var55);
               var64.setDataTypeMap(var69);
            }
         }

         var54.add(var64);
      }

      editorData.setFinanceCubeMappings(var54);
   }

   private AmmModelImpl buildAmmModelEditData(Object thisKey) throws Exception {
      AmmModelImpl editorData = new AmmModelImpl(thisKey);
      editorData.setModelId(this.mAmmModelEVO.getModelId());
      editorData.setSrcModelId(this.mAmmModelEVO.getSrcModelId());
      editorData.setInvalidatedByTaskId(this.mAmmModelEVO.getInvalidatedByTaskId());
      editorData.setVersionNum(this.mAmmModelEVO.getVersionNum());
      ModelPK key = null;
      if(this.mAmmModelEVO.getModelId() != 0) {
         key = new ModelPK(this.mAmmModelEVO.getModelId());
      }

      ModelEVO evoModel;
      if(key != null) {
         evoModel = this.getModelAccessor().getDetails(key, "");
         editorData.setTargetModelRef(new ModelRefImpl(evoModel.getPK(), evoModel.getVisId()));
      }

      key = null;
      if(this.mAmmModelEVO.getSrcModelId() != 0) {
         key = new ModelPK(this.mAmmModelEVO.getSrcModelId());
      }

      if(key != null) {
         evoModel = this.getModelAccessor().getDetails(key, "");
         editorData.setSourceModelRef(new ModelRefImpl(evoModel.getPK(), evoModel.getVisId()));
      }

      this.completeAmmModelEditData(editorData);
      return editorData;
   }

   private void completeAmmModelEditData(AmmModelImpl editorData) throws Exception {}

   public AmmModelEditorSessionSSO getNewItemData(int userId) throws EJBException {
      this.mLog.debug("getNewItemData");
      this.setUserId(userId);
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;

      AmmModelEditorSessionSSO var4;
      try {
         this.mSSO = new AmmModelEditorSessionSSO();
         AmmModelImpl e = new AmmModelImpl((Object)null);
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

   private void completeGetNewItemData(AmmModelImpl editorData) throws Exception {}

   public AmmModelPK insert(AmmModelEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("insert");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      AmmModelImpl editorData = cso.getEditorData();

      AmmModelPK e;
      try {
         this.mAmmModelEVO = new AmmModelEVO();
         this.mAmmModelEVO.setModelId(editorData.getModelId());
         this.mAmmModelEVO.setSrcModelId(editorData.getSrcModelId());
         this.mAmmModelEVO.setInvalidatedByTaskId(editorData.getInvalidatedByTaskId());
         this.updateAmmModelRelationships(editorData);
         this.completeInsertSetup(editorData);
         this.validateInsert();
         this.mAmmModelEVO = this.getAmmModelAccessor().create(this.mAmmModelEVO);
         this.insertIntoAdditionalTables(editorData, true);
         this.sendEntityEventMessage("AmmModel", this.mAmmModelEVO.getPK(), 1);
         e = this.mAmmModelEVO.getPK();
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

   private void updateAmmModelRelationships(AmmModelImpl editorData) throws ValidationException {
      Object key;
      if(editorData.getTargetModelRef() != null) {
         key = editorData.getTargetModelRef().getPrimaryKey();
         if(key instanceof ModelPK) {
            this.mAmmModelEVO.setModelId(((ModelPK)key).getModelId());
         } else {
            this.mAmmModelEVO.setModelId(((ModelCK)key).getModelPK().getModelId());
         }

         try {
            this.getModelAccessor().getDetails(key, "");
         } catch (Exception var5) {
            var5.printStackTrace();
            throw new ValidationException(editorData.getTargetModelRef() + " no longer exists");
         }
      } else {
         this.mAmmModelEVO.setModelId(0);
      }

      if(editorData.getSourceModelRef() != null) {
         key = editorData.getSourceModelRef().getPrimaryKey();
         if(key instanceof ModelPK) {
            this.mAmmModelEVO.setSrcModelId(((ModelPK)key).getModelId());
         } else {
            this.mAmmModelEVO.setSrcModelId(((ModelCK)key).getModelPK().getModelId());
         }

         try {
            this.getModelAccessor().getDetails(key, "");
         } catch (Exception var4) {
            var4.printStackTrace();
            throw new ValidationException(editorData.getSourceModelRef() + " no longer exists");
         }
      } else {
         this.mAmmModelEVO.setSrcModelId(0);
      }

   }

   private void completeInsertSetup(AmmModelImpl editorData) throws Exception {
      int dimEvoId = 0;
      int dimElementEvoId = 0;
      int dimSrcStrucElementEvoId = 0;
      Iterator finEvoId = editorData.getDimMappings().iterator();

      while(finEvoId.hasNext()) {
         AmmDimensionMapping finDTEvoId;
         AmmDimensionEVO i$;
         finDTEvoId = (AmmDimensionMapping)finEvoId.next();
         i$ = null;
         Map fcMapping;
         Iterator finEvo;
         AmmDimensionElementEVO dtMap;
         AmmDimensionElementEVO var23;
         label124:
         switch(finDTEvoId.getType().intValue()) {
         case 2:
            i$ = new AmmDimensionEVO();
            if(finDTEvoId.getUnmappedDimensionRef() != null) {
               var23 = new AmmDimensionElementEVO();
               --dimElementEvoId;
               var23.setAmmDimensionElementId(dimElementEvoId);
               DimensionElementRef var26 = finDTEvoId.getUnmappedDimensionRef();
               DimensionElementCK var32 = (DimensionElementCK)var26.getPrimaryKey();
               DimensionElementPK var36 = var32.getDimensionElementPK();
               var23.setDimensionElementId(Integer.valueOf(var36.getDimensionElementId()));
               i$.addAmmDimElementsItem(var23);
            }
            break;
         case 3:
            i$ = new AmmDimensionEVO();
            if(finDTEvoId.getSourceElementData() != null && !finDTEvoId.getSourceElementData().isEmpty()) {
               var23 = new AmmDimensionElementEVO();
               --dimElementEvoId;
               var23.setAmmDimensionElementId(dimElementEvoId);
               var23.setDimensionElementId((Integer)null);
               finEvo = finDTEvoId.getSourceElementData().iterator();

               while(finEvo.hasNext()) {
                  StructureElementRef var31 = (StructureElementRef)finEvo.next();
                  AmmSrcStructureElementEVO var29 = new AmmSrcStructureElementEVO();
                  --dimSrcStrucElementEvoId;
                  var29.setAmmSrcStructureElementId(dimSrcStrucElementEvoId);
                  StructureElementPK var33 = (StructureElementPK)var31.getPrimaryKey();
                  var29.setSrcStructureElementId(var33.getStructureElementId());
                  var23.addAmmSourceElementsItem(var29);
               }

               i$.addAmmDimElementsItem(var23);
            }
            break;
         case 4:
            i$ = new AmmDimensionEVO();
            fcMapping = finDTEvoId.getSelectedCalanderElementData();
            finEvo = fcMapping.keySet().iterator();

            while(true) {
               if(!finEvo.hasNext()) {
                  break label124;
               }

               CalendarElementNode i$1 = (CalendarElementNode)finEvo.next();
               if(!((List)fcMapping.get(i$1)).isEmpty()) {
                  dtMap = new AmmDimensionElementEVO();
                  --dimElementEvoId;
                  dtMap.setAmmDimensionElementId(dimElementEvoId);
                  dtMap.setDimensionElementId(Integer.valueOf(i$1.getStructureElementId()));
                  Iterator dtEVO = ((List)fcMapping.get(i$1)).iterator();

                  while(dtEVO.hasNext()) {
                     StructureElementRef dtref = (StructureElementRef)dtEVO.next();
                     AmmSrcStructureElementEVO dtPK = new AmmSrcStructureElementEVO();
                     --dimSrcStrucElementEvoId;
                     dtPK.setAmmSrcStructureElementId(dimSrcStrucElementEvoId);
                     StructureElementPK ceNode = (StructureElementPK)dtref.getPrimaryKey();
                     dtPK.setSrcStructureElementId(ceNode.getStructureElementId());
                     dtMap.addAmmSourceElementsItem(dtPK);
                  }

                  i$.addAmmDimElementsItem(dtMap);
               }
            }
         default:
            i$ = new AmmDimensionEVO();
            fcMapping = finDTEvoId.getSelectedElementData();
            finEvo = fcMapping.keySet().iterator();

            while(finEvo.hasNext()) {
               DimensionElementRef var27 = (DimensionElementRef)finEvo.next();
               if(!((List)fcMapping.get(var27)).isEmpty()) {
                  dtMap = new AmmDimensionElementEVO();
                  --dimElementEvoId;
                  dtMap.setAmmDimensionElementId(dimElementEvoId);
                  DimensionElementCK var35 = (DimensionElementCK)var27.getPrimaryKey();
                  DimensionElementPK var39 = var35.getDimensionElementPK();
                  dtMap.setDimensionElementId(Integer.valueOf(var39.getDimensionElementId()));
                  Iterator var41 = ((List)fcMapping.get(var27)).iterator();

                  while(var41.hasNext()) {
                     StructureElementRef var40 = (StructureElementRef)var41.next();
                     AmmSrcStructureElementEVO years = new AmmSrcStructureElementEVO();
                     --dimSrcStrucElementEvoId;
                     years.setAmmSrcStructureElementId(dimSrcStrucElementEvoId);
                     StructureElementPK noOfYears = (StructureElementPK)var40.getPrimaryKey();
                     years.setSrcStructureElementId(noOfYears.getStructureElementId());
                     dtMap.addAmmSourceElementsItem(years);
                  }

                  i$.addAmmDimElementsItem(dtMap);
               }
            }
         }

         --dimEvoId;
         i$.setAmmDimensionId(dimEvoId);
         i$.setDimensionId(finDTEvoId.getDimensionId());
         i$.setSrcDimensionId(finDTEvoId.getSourceDimensionId());
         i$.setSrcHierarchyId(finDTEvoId.getSourcehierarchyId());
         this.mAmmModelEVO.addAmmDimensionsItem(i$);
      }

      int var21 = 0;
      int var22 = 0;
      Iterator var25 = editorData.getFinanceCubeMappings().iterator();

      while(var25.hasNext()) {
         AmmFinanceCubeMapping var24 = (AmmFinanceCubeMapping)var25.next();
         if(var24.getDataTypeMap() != null && !var24.getDataTypeMap().isEmpty()) {
            AmmFinanceCubeEVO var30 = new AmmFinanceCubeEVO();
            --var21;
            var30.setAmmFinanceCubeId(var21);
            var30.setFinanceCubeId(var24.getFinanceCubeId());
            var30.setSrcFinanceCubeId(var24.getSourceFinanceCubeId());
            Iterator var28 = ((List)var24.getDataTypeMap().get(var24.getFinanceCubeRef())).iterator();

            while(var28.hasNext()) {
               AmmFinDataTypeMap var34 = (AmmFinDataTypeMap)var28.next();
               if(var34.getSourceRef() != null && var34.getTargetRef() != null && var34.getStartYear() != null && var34.getEndYear() != null) {
                  AmmDataTypeEVO var38 = new AmmDataTypeEVO();
                  --var22;
                  var38.setAmmDataTypeId(var22);
                  DataTypeRef var37 = var34.getTargetRef();
                  DataTypePK var42 = (DataTypePK)var37.getPrimaryKey();
                  var38.setDataTypeId(var42.getDataTypeId());
                  var37 = var34.getSourceRef();
                  var42 = (DataTypePK)var37.getPrimaryKey();
                  var38.setSrcDataTypeId(var42.getDataTypeId());
                  CalendarElementNode var44 = editorData.getSourceInfo().getRoot();
                  List var43 = var44.getChildren();
                  int var45 = var43.size();

                  for(int i = 0; i < var45; ++i) {
                     Object o = var43.get(i);
                     int offSetValue;
                     if(var34.getStartYear().equals(o)) {
                        offSetValue = var45 - 1 - i;
                        if(offSetValue == 0) {
                           var38.setSrcStartYearOffset(0);
                        } else {
                           var38.setSrcStartYearOffset(-offSetValue);
                        }
                     }

                     if(var34.getEndYear().equals(o)) {
                        offSetValue = var45 - 1 - i;
                        if(offSetValue == 0) {
                           var38.setSrcEndYearOffset(0);
                        } else {
                           var38.setSrcEndYearOffset(-offSetValue);
                        }
                     }
                  }

                  var30.addAmmDataTypesItem(var38);
               }
            }

            if(var30.getAmmDataTypes() != null && var30.getAmmDataTypes().size() > 0) {
               this.mAmmModelEVO.addAmmFinanceCubesItem(var30);
            }
         }
      }

   }

   private void insertIntoAdditionalTables(AmmModelImpl editorData, boolean isInsert) throws Exception {}

   private void validateInsert() throws ValidationException {}

   public AmmModelPK copy(AmmModelEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("copy");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      AmmModelImpl editorData = cso.getEditorData();
      this.mThisTableKey = (AmmModelPK)editorData.getPrimaryKey();

      AmmModelPK var5;
      try {
         AmmModelEVO e = this.getAmmModelAccessor().getDetails(this.mThisTableKey, "<0><1><2><3><4>");
         this.mAmmModelEVO = e.deepClone();
         this.mAmmModelEVO.setModelId(editorData.getModelId());
         this.mAmmModelEVO.setSrcModelId(editorData.getSrcModelId());
         this.mAmmModelEVO.setInvalidatedByTaskId(editorData.getInvalidatedByTaskId());
         this.mAmmModelEVO.setVersionNum(0);
         this.updateAmmModelRelationships(editorData);
         this.completeCopySetup(editorData);
         this.validateCopy();
         this.mAmmModelEVO.prepareForInsert();
         this.mAmmModelEVO = this.getAmmModelAccessor().create(this.mAmmModelEVO);
         this.mThisTableKey = this.mAmmModelEVO.getPK();
         this.insertIntoAdditionalTables(editorData, false);
         this.sendEntityEventMessage("AmmModel", this.mAmmModelEVO.getPK(), 1);
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

   private void completeCopySetup(AmmModelImpl editorData) throws Exception {}

   public void update(AmmModelEditorSessionCSO cso) throws ValidationException, EJBException {
      this.mLog.debug("update");
      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(cso.getUserId());
      AmmModelImpl editorData = cso.getEditorData();
      this.mThisTableKey = (AmmModelPK)editorData.getPrimaryKey();

      try {
         this.mAmmModelEVO = this.getAmmModelAccessor().getDetails(this.mThisTableKey, "<0><1><2><3><4>");
         this.preValidateUpdate(editorData);
         this.mAmmModelEVO.setModelId(editorData.getModelId());
         this.mAmmModelEVO.setSrcModelId(editorData.getSrcModelId());
         this.mAmmModelEVO.setInvalidatedByTaskId(editorData.getInvalidatedByTaskId());
         if(editorData.getVersionNum() != this.mAmmModelEVO.getVersionNum()) {
            throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mAmmModelEVO.getVersionNum());
         }

         this.updateAmmModelRelationships(editorData);
         this.completeUpdateSetup(editorData);
         this.postValidateUpdate();
         this.getAmmModelAccessor().setDetails(this.mAmmModelEVO);
         this.updateAdditionalTables(editorData);
         this.sendEntityEventMessage("AmmModel", this.mAmmModelEVO.getPK(), 3);
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

   private void preValidateUpdate(AmmModelImpl editorData) throws ValidationException {}

   private void postValidateUpdate() throws ValidationException {}

   private void completeUpdateSetup(AmmModelImpl editorData) throws Exception {
      this.mAmmModelEVO.setInvalidatedByTaskId((Integer)null);
      int dimElementEvoId = 0;
      int dimSrcStrucElementEvoId = 0;
      Iterator finEvoId = editorData.getDimMappings().iterator();

      Iterator i$;
      Iterator var25;
      label230:
      while(finEvoId.hasNext()) {
         AmmDimensionMapping finDTEvoId = (AmmDimensionMapping)finEvoId.next();
         AmmDimensionEVO fcMapping;
         Iterator finEvo;
         AmmDimensionElementEVO i$1;
         AmmDimensionElementEVO dtEVO;
         Map var23;
         AmmDimensionElementEVO var26;
         switch(finDTEvoId.getType().intValue()) {
         case 2:
            i$ = this.mAmmModelEVO.getAmmDimensions().iterator();

            while(true) {
               if(!i$.hasNext()) {
                  continue label230;
               }

               fcMapping = (AmmDimensionEVO)i$.next();
               if(fcMapping.getDimensionId() != null && fcMapping.getSrcDimensionId() == null && fcMapping.getSrcHierarchyId() == null && finDTEvoId.getDimensionId().equals(fcMapping.getDimensionId())) {
                  finEvo = fcMapping.getAmmDimElements().iterator();

                  while(finEvo.hasNext()) {
                     i$1 = (AmmDimensionElementEVO)finEvo.next();
                     fcMapping.deleteAmmDimElementsItem(i$1.getPK());
                  }

                  if(finDTEvoId.getUnmappedDimensionRef() != null) {
                     var26 = new AmmDimensionElementEVO();
                     --dimElementEvoId;
                     var26.setAmmDimensionElementId(dimElementEvoId);
                     DimensionElementRef var32 = finDTEvoId.getUnmappedDimensionRef();
                     DimensionElementCK var31 = (DimensionElementCK)var32.getPrimaryKey();
                     DimensionElementPK var37 = var31.getDimensionElementPK();
                     var26.setDimensionElementId(Integer.valueOf(var37.getDimensionElementId()));
                     fcMapping.addAmmDimElementsItem(var26);
                  }
               }
            }
         case 3:
            i$ = this.mAmmModelEVO.getAmmDimensions().iterator();

            while(true) {
               if(!i$.hasNext()) {
                  continue label230;
               }

               fcMapping = (AmmDimensionEVO)i$.next();
               if(fcMapping.getDimensionId() == null && fcMapping.getSrcDimensionId() != null && fcMapping.getSrcHierarchyId() != null && finDTEvoId.getSourceDimensionId().equals(fcMapping.getSrcDimensionId())) {
                  finEvo = fcMapping.getAmmDimElements().iterator();

                  while(finEvo.hasNext()) {
                     i$1 = (AmmDimensionElementEVO)finEvo.next();
                     fcMapping.deleteAmmDimElementsItem(i$1.getPK());
                  }

                  if(finDTEvoId.getSourceElementData() != null && !finDTEvoId.getSourceElementData().isEmpty()) {
                     var26 = new AmmDimensionElementEVO();
                     --dimElementEvoId;
                     var26.setAmmDimensionElementId(dimElementEvoId);
                     var26.setDimensionElementId((Integer)null);
                     var25 = finDTEvoId.getSourceElementData().iterator();

                     while(var25.hasNext()) {
                        StructureElementRef var30 = (StructureElementRef)var25.next();
                        AmmSrcStructureElementEVO var28 = new AmmSrcStructureElementEVO();
                        --dimSrcStrucElementEvoId;
                        var28.setAmmSrcStructureElementId(dimSrcStrucElementEvoId);
                        StructureElementPK var34 = (StructureElementPK)var30.getPrimaryKey();
                        var28.setSrcStructureElementId(var34.getStructureElementId());
                        var26.addAmmSourceElementsItem(var28);
                     }

                     fcMapping.addAmmDimElementsItem(var26);
                  }
               }
            }
         case 4:
            i$ = this.mAmmModelEVO.getAmmDimensions().iterator();

            while(true) {
               if(!i$.hasNext()) {
                  continue label230;
               }

               fcMapping = (AmmDimensionEVO)i$.next();
               if(fcMapping.getDimensionId() != null && fcMapping.getSrcDimensionId() != null && fcMapping.getSrcHierarchyId() != null && finDTEvoId.getDimensionId().equals(fcMapping.getDimensionId())) {
                  finEvo = fcMapping.getAmmDimElements().iterator();

                  while(finEvo.hasNext()) {
                     i$1 = (AmmDimensionElementEVO)finEvo.next();
                     fcMapping.deleteAmmDimElementsItem(i$1.getPK());
                  }

                  var23 = finDTEvoId.getSelectedCalanderElementData();
                  var25 = var23.keySet().iterator();

                  while(var25.hasNext()) {
                     CalendarElementNode dtMap = (CalendarElementNode)var25.next();
                     if(!((List)var23.get(dtMap)).isEmpty()) {
                        dtEVO = new AmmDimensionElementEVO();
                        --dimElementEvoId;
                        dtEVO.setAmmDimensionElementId(dimElementEvoId);
                        dtEVO.setDimensionElementId(Integer.valueOf(dtMap.getStructureElementId()));
                        Iterator dtref = ((List)var23.get(dtMap)).iterator();

                        while(dtref.hasNext()) {
                           StructureElementRef dtPK = (StructureElementRef)dtref.next();
                           AmmSrcStructureElementEVO ceNode = new AmmSrcStructureElementEVO();
                           --dimSrcStrucElementEvoId;
                           ceNode.setAmmSrcStructureElementId(dimSrcStrucElementEvoId);
                           StructureElementPK years = (StructureElementPK)dtPK.getPrimaryKey();
                           ceNode.setSrcStructureElementId(years.getStructureElementId());
                           dtEVO.addAmmSourceElementsItem(ceNode);
                        }

                        fcMapping.addAmmDimElementsItem(dtEVO);
                     }
                  }
               }
            }
         default:
            i$ = this.mAmmModelEVO.getAmmDimensions().iterator();
         }

         while(i$.hasNext()) {
            fcMapping = (AmmDimensionEVO)i$.next();
            if(fcMapping.getDimensionId() != null && fcMapping.getSrcDimensionId() != null && fcMapping.getSrcHierarchyId() != null && finDTEvoId.getDimensionId().equals(fcMapping.getDimensionId())) {
               finEvo = fcMapping.getAmmDimElements().iterator();

               while(finEvo.hasNext()) {
                  i$1 = (AmmDimensionElementEVO)finEvo.next();
                  fcMapping.deleteAmmDimElementsItem(i$1.getPK());
               }

               var23 = finDTEvoId.getSelectedElementData();
               var25 = var23.keySet().iterator();

               while(var25.hasNext()) {
                  DimensionElementRef var27 = (DimensionElementRef)var25.next();
                  if(!((List)var23.get(var27)).isEmpty()) {
                     dtEVO = new AmmDimensionElementEVO();
                     --dimElementEvoId;
                     dtEVO.setAmmDimensionElementId(dimElementEvoId);
                     DimensionElementCK var36 = (DimensionElementCK)var27.getPrimaryKey();
                     DimensionElementPK var40 = var36.getDimensionElementPK();
                     dtEVO.setDimensionElementId(Integer.valueOf(var40.getDimensionElementId()));
                     Iterator var42 = ((List)var23.get(var27)).iterator();

                     while(var42.hasNext()) {
                        StructureElementRef var41 = (StructureElementRef)var42.next();
                        AmmSrcStructureElementEVO noOfYears = new AmmSrcStructureElementEVO();
                        --dimSrcStrucElementEvoId;
                        noOfYears.setAmmSrcStructureElementId(dimSrcStrucElementEvoId);
                        StructureElementPK i = (StructureElementPK)var41.getPrimaryKey();
                        noOfYears.setSrcStructureElementId(i.getStructureElementId());
                        dtEVO.addAmmSourceElementsItem(noOfYears);
                     }

                     fcMapping.addAmmDimElementsItem(dtEVO);
                  }
               }
            }
         }
      }

      finEvoId = this.mAmmModelEVO.getAmmFinanceCubes().iterator();

      while(finEvoId.hasNext()) {
         AmmFinanceCubeEVO var21 = (AmmFinanceCubeEVO)finEvoId.next();
         this.mAmmModelEVO.deleteAmmFinanceCubesItem(var21.getPK());
      }

      int var20 = 0;
      int var22 = 0;
      i$ = editorData.getFinanceCubeMappings().iterator();

      while(i$.hasNext()) {
         AmmFinanceCubeMapping var24 = (AmmFinanceCubeMapping)i$.next();
         if(var24.getDataTypeMap() != null && !var24.getDataTypeMap().isEmpty()) {
            AmmFinanceCubeEVO var33 = new AmmFinanceCubeEVO();
            --var20;
            var33.setAmmFinanceCubeId(var20);
            var33.setFinanceCubeId(var24.getFinanceCubeId());
            var33.setSrcFinanceCubeId(var24.getSourceFinanceCubeId());
            var25 = ((List)var24.getDataTypeMap().get(var24.getFinanceCubeRef())).iterator();

            while(var25.hasNext()) {
               AmmFinDataTypeMap var29 = (AmmFinDataTypeMap)var25.next();
               if(var29.getSourceRef() != null && var29.getTargetRef() != null && var29.getStartYear() != null && var29.getEndYear() != null) {
                  AmmDataTypeEVO var35 = new AmmDataTypeEVO();
                  --var22;
                  var35.setAmmDataTypeId(var22);
                  DataTypeRef var39 = var29.getTargetRef();
                  DataTypePK var38 = (DataTypePK)var39.getPrimaryKey();
                  var35.setDataTypeId(var38.getDataTypeId());
                  var39 = var29.getSourceRef();
                  var38 = (DataTypePK)var39.getPrimaryKey();
                  var35.setSrcDataTypeId(var38.getDataTypeId());
                  CalendarElementNode var43 = editorData.getSourceInfo().getRoot();
                  List var45 = var43.getChildren();
                  int var44 = var45.size();

                  for(int var46 = 0; var46 < var44; ++var46) {
                     Object o = var45.get(var46);
                     int offSetValue;
                     if(var29.getStartYear().equals(o)) {
                        offSetValue = var44 - 1 - var46;
                        if(offSetValue == 0) {
                           var35.setSrcStartYearOffset(0);
                        } else {
                           var35.setSrcStartYearOffset(-offSetValue);
                        }
                     }

                     if(var29.getEndYear().equals(o)) {
                        offSetValue = var44 - 1 - var46;
                        if(offSetValue == 0) {
                           var35.setSrcEndYearOffset(0);
                        } else {
                           var35.setSrcEndYearOffset(-offSetValue);
                        }
                     }
                  }

                  var33.addAmmDataTypesItem(var35);
               }
            }

            if(var33.getAmmDataTypes().size() > 0) {
               this.mAmmModelEVO.addAmmFinanceCubesItem(var33);
            }
         }
      }

   }

   private void updateAdditionalTables(AmmModelImpl editorData) throws Exception {}

   public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
      if(this.mLog.isDebugEnabled()) {
         this.mLog.debug("delete", paramKey);
      }

      Timer timer = this.mLog.isInfoEnabled()?new Timer(this.mLog):null;
      this.setUserId(userId);
      this.mThisTableKey = (AmmModelPK)paramKey;

      try {
         this.mAmmModelEVO = this.getAmmModelAccessor().getDetails(this.mThisTableKey, "<0><1><2><3><4>");
         this.validateDelete();
         this.deleteDataFromOtherTables();
         this.mAmmModelAccessor.remove(this.mThisTableKey);
         this.sendEntityEventMessage("AmmModel", this.mThisTableKey, 2);
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

   private AmmModelAccessor getAmmModelAccessor() throws Exception {
      if(this.mAmmModelAccessor == null) {
         this.mAmmModelAccessor = new AmmModelAccessor(this.getInitialContext());
      }

      return this.mAmmModelAccessor;
   }

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

   public int issueAggregatedModelTask(int userId, List<AmmDataTypeMap> refreshList) throws ValidationException, EJBException {
      AggregatedModelTaskRequest request = new AggregatedModelTaskRequest(refreshList);
      EntityList taskEL = (new TaskDAO()).getTasks();

      int e;
      for(e = 0; e < taskEL.getNumRows(); ++e) {
         int taskId = ((Integer)taskEL.getValueAt(e, "TaskId")).intValue();
         String taskName = (String)taskEL.getValueAt(e, "TaskName");
         int status = ((Integer)taskEL.getValueAt(e, "Status")).intValue();
         if(status != 5 && status != 10 && status != 9 && taskName.equals(request.getIdentifier())) {
            throw new ValidationException(request.getIdentifier() + " has already been issued - task " + taskId);
         }

         if(status != 5 && status != 10 && status != 9 && taskName.equals("ChangeManagementTask")) {
            throw new ValidationException("ChangeManagement task is outstanding - task " + taskId);
         }
      }

      try {
         e = TaskMessageFactory.issueNewTask(new InitialContext(), false, request, userId);
         this.mLog.debug("issueAggregatedModelTask", "taskId=" + e);
         return e;
      } catch (Exception var9) {
         var9.printStackTrace();
         throw new EJBException(var9);
      }
   }

   public AmmMap getAmmMap() throws ValidationException, EJBException {
      try {
         return (new AmmModelDAO()).getAmmMap();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new EJBException(var2);
      }
   }
}
