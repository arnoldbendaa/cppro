// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.AllStructureElementsELO;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementParentsELO;
import com.cedar.cp.dto.model.BudgetCyclesToFixStateELO;
import com.cedar.cp.dto.model.CycleStateDetailsELO;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMMetaDataController;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMUpdateAdapter;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.BudgetStateDAO;
import com.cedar.cp.ejb.impl.model.BudgetStateEVO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.util.HashMap;
import java.util.Map;

public class BudgetStateChanges extends CMUpdateAdapter {

   private Map<Integer, BudgetStateEVO> mInsertMap;
   private Map<Integer, BudgetStateEVO> mUpdateMap;
   private Map<Integer, EntityList> mCycleMap;
   private StructureElementDAO strucDao = new StructureElementDAO();
   private Integer mStructureId;
   private Integer mBudgetCycleId;
   private CMMetaDataController mController;
   protected transient Log mLog = new Log(this.getClass());


   public BudgetStateChanges() {
      this.mController = null;
   }

   public BudgetStateChanges(CMMetaDataController controller) {
      this.mController = controller;
   }

   public void terminateProcessing(ModelRef modelRef) {
      this.tidyBudgetState(((ModelRefImpl)modelRef).getModelPK().getModelId());
   }

   public void tidyBudgetState(int modelId) {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ModelDAO modelDAO = new ModelDAO();
      BudgetCyclesToFixStateELO cycleList = modelDAO.getBudgetCyclesToFixState(modelId);
      int size = cycleList.getNumRows();

      for(int i = 0; i < size; ++i) {
         this.mStructureId = (Integer)cycleList.getValueAt(i, "BudgetHierarchyId");
         this.mBudgetCycleId = (Integer)cycleList.getValueAt(i, "BudgetCycleId");
         this.processCycle();
      }

      if(timer != null) {
         timer.logDebug("tidyBudgetState", "complete");
      }

   }

   public void tidyBudgetState(int modelId, int budgetCycleId) {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ModelDAO modelDAO = new ModelDAO();
      BudgetCyclesToFixStateELO cycleList = modelDAO.getBudgetCyclesToFixState(modelId);
      int size = cycleList.getNumRows();

      for(int i = 0; i < size; ++i) {
         this.mStructureId = (Integer)cycleList.getValueAt(i, "BudgetHierarchyId");
         this.mBudgetCycleId = (Integer)cycleList.getValueAt(i, "BudgetCycleId");
         if(budgetCycleId == this.mBudgetCycleId.intValue()) {
            this.processCycle();
         }
      }

      if(timer != null) {
         timer.logDebug("tidyBudgetState", "complete");
      }

   }

   private void processCycle() {
      Timer timer = new Timer(this.mLog);
      this.getExistingStates();
      AllStructureElementsELO allElems = this.strucDao.getAllStructureElements(this.mStructureId.intValue());

      int i;
      EntityRef mainRef;
      StructureElementPK mainPk;
      Integer mainElementId;
      boolean submitable;
      int j;
      int childPosition;
      BudgetStateEVO var33;
      for(i = allElems.getNumRows() - 1; i > -1; --i) {
         mainRef = (EntityRef)allElems.getValueAt(i, "StructureElement");
         mainPk = (StructureElementPK)mainRef.getPrimaryKey();
         mainElementId = Integer.valueOf(mainPk.getStructureElementId());
         String mainPosition = allElems.getValueAt(i, "Disabled").toString();
         String mainEndPosition = allElems.getValueAt(i, "NotPlannable").toString();
         int isLeaf = ((Integer)allElems.getValueAt(i, "Position")).intValue();
         int evo = ((Integer)allElems.getValueAt(i, "EndPosition")).intValue();
         int changeMade = ((Integer)allElems.getValueAt(i, "Depth")).intValue();
         if(this.mCycleMap.containsKey(mainElementId) && !this.mInsertMap.containsKey(mainElementId) && "false".equals(mainPosition) && "false".equals(mainEndPosition)) {
            submitable = false;

            for(j = i - 1; j > -1; --j) {
               int childRef = ((Integer)allElems.getValueAt(j, "Position")).intValue() + 1;
               childPosition = ((Integer)allElems.getValueAt(j, "EndPosition")).intValue();
               if(isLeaf >= childRef && isLeaf <= childPosition) {
                  EntityRef childPk = (EntityRef)allElems.getValueAt(j, "StructureElement");
                  StructureElementPK childId = (StructureElementPK)childPk.getPrimaryKey();
                  Integer childEVO = Integer.valueOf(childId.getStructureElementId());
                  if(submitable || !this.mCycleMap.containsKey(childEVO)) {
                     submitable = true;
                  }

                  if(submitable) {
                     this.mLog.debug("we have a missing element ");
                     this.getStateEVO(childEVO, Integer.valueOf(2), Boolean.valueOf(submitable));
                  }
               }
            }
         }

         if("true".equals(mainEndPosition) || this.checkExistingState(6, mainElementId.intValue())) {
            if("true".equals(mainEndPosition)) {
               this.getStateEVO(mainElementId, Integer.valueOf(6), Boolean.valueOf(true));
            } else {
               this.getStateEVO(mainElementId, Integer.valueOf(2), Boolean.valueOf(true));
            }
         }

         if("true".equals(mainPosition) || this.checkExistingState(5, mainElementId.intValue())) {
            if("true".equals(mainPosition)) {
               this.getStateEVO(mainElementId, Integer.valueOf(5), Boolean.valueOf(true));
            } else {
               this.getStateEVO(mainElementId, Integer.valueOf(2), Boolean.valueOf(true));
            }
         }

         for(int var25 = i + 1; var25 < allElems.getNumRows(); ++var25) {
            EntityRef var24 = (EntityRef)allElems.getValueAt(var25, "StructureElement");
            StructureElementPK var27 = (StructureElementPK)var24.getPrimaryKey();
            Integer var26 = Integer.valueOf(var27.getStructureElementId());
            int var30 = ((Integer)allElems.getValueAt(var25, "Position")).intValue();
            int var32 = ((Integer)allElems.getValueAt(var25, "Depth")).intValue();
            if(var30 > evo) {
               break;
            }

            if(var32 == changeMade + 1) {
               var33 = this.getStateEVO(var26);
               if(var33 == null || var33.getState() == 2 || var33.getState() == 3) {
                  break;
               }

               if(var30 == evo) {
                  this.getStateEVO(mainElementId, Boolean.valueOf(true));
               }
            }
         }
      }

      for(i = allElems.getNumRows() - 1; i > -1; --i) {
         mainRef = (EntityRef)allElems.getValueAt(i, "StructureElement");
         mainPk = (StructureElementPK)mainRef.getPrimaryKey();
         mainElementId = Integer.valueOf(mainPk.getStructureElementId());
         int var19 = ((Integer)allElems.getValueAt(i, "Position")).intValue();
         int var20 = ((Integer)allElems.getValueAt(i, "EndPosition")).intValue();
         boolean var21 = var19 == var20;
         BudgetStateEVO var23 = this.getStateEVO(mainElementId);
         if(var23 != null && var23.getState() == 2) {
            boolean var22 = false;
            if(var21 && !var23.getSubmitable()) {
               var23.setSubmitable(true);
               var22 = true;
            } else if(!var23.getSubmitable()) {
               submitable = true;

               for(j = i + 1; j < allElems.getNumRows(); ++j) {
                  EntityRef var28 = (EntityRef)allElems.getValueAt(j, "StructureElement");
                  childPosition = ((Integer)allElems.getValueAt(j, "Position")).intValue();
                  if(childPosition > var19 && childPosition <= var20) {
                     StructureElementPK var29 = (StructureElementPK)var28.getPrimaryKey();
                     Integer var31 = Integer.valueOf(var29.getStructureElementId());
                     var33 = this.getStateEVO(var31);
                     if(var33 == null) {
                        submitable = false;
                     } else if(var33.getState() < 3) {
                        submitable = false;
                     }
                  }

                  if(!submitable) {
                     break;
                  }
               }

               if(submitable) {
                  var23.setSubmitable(true);
                  var22 = true;
               }
            }

            if(this.mCycleMap.containsKey(mainElementId) && !this.mUpdateMap.containsKey(mainElementId) && var22) {
               this.mUpdateMap.put(mainElementId, var23);
            }
         }
      }

      if(this.mInsertMap.size() > 0) {
         this.mLog.info("processCycle", "we have the following inserts to do " + this.mInsertMap.size());
      }

      this.writeChanges();
      this.log("[" + timer.getElapsed() + "] budget cycle state inserts=" + this.mInsertMap.size());
      timer.logInfo("processCycle", "cycle work complete");
   }

   private void log(String msg) {
      if(this.mController != null) {
         try {
            this.mController.getTaskMessageLogger().log(msg);
         } catch (Exception var3) {
            System.out.println("unable to log task message");
            throw new RuntimeException(var3);
         }
      }
   }

   private BudgetStateEVO getStateEVO(Integer structureElementId, Integer state, Boolean force) {
      BudgetStateEVO evo;
      if(this.mInsertMap.containsKey(structureElementId)) {
         evo = (BudgetStateEVO)this.mInsertMap.get(structureElementId);
         evo.setState(state.intValue());
      } else if(this.mUpdateMap.containsKey(structureElementId)) {
         evo = (BudgetStateEVO)this.mUpdateMap.get(structureElementId);
         evo.setState(state.intValue());
      } else {
         evo = new BudgetStateEVO();
         if(this.mCycleMap.containsKey(structureElementId)) {
            EntityList list = (EntityList)this.mCycleMap.get(structureElementId);
            evo.setBudgetCycleId(this.mBudgetCycleId.intValue());
            evo.setStructureElementId(((Integer)list.getValueAt(0, "StructureElementId")).intValue());
            evo.setState(((Integer)list.getValueAt(0, "State")).intValue());
            evo.setSubmitable(((Boolean)list.getValueAt(0, "Submitable")).booleanValue());
            evo.setRejectable(((Boolean)list.getValueAt(0, "Rejectable")).booleanValue());
            if(force.booleanValue() && evo.getState() != state.intValue() || state.intValue() != 2 && evo.getState() != state.intValue()) {
               evo.setState(state.intValue());
               this.mUpdateMap.put(structureElementId, evo);
            }
         } else {
            evo.setBudgetCycleId(this.mBudgetCycleId.intValue());
            evo.setStructureElementId(structureElementId.intValue());
            evo.setState(state.intValue());
            this.mInsertMap.put(structureElementId, evo);
         }
      }

      return evo;
   }

   private BudgetStateEVO getStateEVO(Integer structureElementId) {
      return this.getStateEVO(structureElementId, (Boolean)null);
   }

   private BudgetStateEVO getStateEVO(Integer structureElementId, Boolean alter) {
      BudgetStateEVO evo = null;
      if(this.mInsertMap.containsKey(structureElementId)) {
         evo = (BudgetStateEVO)this.mInsertMap.get(structureElementId);
      } else if(this.mUpdateMap.containsKey(structureElementId)) {
         evo = (BudgetStateEVO)this.mUpdateMap.get(structureElementId);
      } else if(this.mCycleMap.containsKey(structureElementId)) {
         evo = new BudgetStateEVO();
         EntityList list = (EntityList)this.mCycleMap.get(structureElementId);
         evo.setBudgetCycleId(this.mBudgetCycleId.intValue());
         evo.setStructureElementId(((Integer)list.getValueAt(0, "StructureElementId")).intValue());
         evo.setState(((Integer)list.getValueAt(0, "State")).intValue());
      }

      if(alter != null && evo != null) {
         evo.setSubmitable(evo.getState() == 2);
         evo.setRejectable(evo.getState() == 3 && this.getStateEVO(this.getParentId(structureElementId)) != null && this.getStateEVO(this.getParentId(structureElementId)).getState() == 2);
      }

      return evo;
   }

   private Integer getParentId(Integer id) {
      Integer value = Integer.valueOf(0);
      StructureElementParentsELO parents = this.strucDao.getStructureElementParents(this.mStructureId.intValue(), id.intValue());
      if(parents.getNumRows() > 1) {
         value = (Integer)parents.getValueAt(1, "StructureElementId");
      }

      return value;
   }

   private void getExistingStates() {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      BudgetStateDAO stateDAO = new BudgetStateDAO();
      CycleStateDetailsELO stateList = stateDAO.getCycleStateDetails(this.mBudgetCycleId.intValue());
      int currentStateSize = stateList.getNumRows();
      this.mCycleMap = new HashMap(currentStateSize);
      this.mInsertMap = new HashMap();
      this.mUpdateMap = new HashMap();

      for(int i = 0; i < currentStateSize; ++i) {
         this.mCycleMap.put((Integer)stateList.getValueAt(i, "StructureElementId"), stateList.getRowData(i));
      }

      this.mLog.debug("cycle : " + this.mBudgetCycleId);
      this.mLog.debug("state count : " + this.mCycleMap.size());
      if(timer != null) {
         timer.logDebug("getExistingStates", "populated existing state");
      }

   }

   private void writeChanges() {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      BudgetStateDAO stateDao = new BudgetStateDAO();
      stateDao.batchInsert(this.mInsertMap);
      stateDao.batchUpdate(this.mUpdateMap);
      stateDao.batchInsertHistory(this.mInsertMap, this.mUpdateMap, this.mCycleMap);
      if(timer != null) {
         timer.logDebug("writeChanges", "done inserts and updates");
      }

   }

   private boolean checkExistingState(int state, int structureElementId) {
      boolean result = false;
      if(this.mCycleMap.containsKey(Integer.valueOf(structureElementId))) {
         EntityList values = (EntityList)this.mCycleMap.get(Integer.valueOf(structureElementId));
         int existingState = ((Integer)values.getValueAt(0, "State")).intValue();
         if(existingState == state) {
            result = true;
         }
      }

      return result;
   }
}
