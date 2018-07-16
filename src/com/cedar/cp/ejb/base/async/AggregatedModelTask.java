// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.amm.AmmCubeMap;
import com.cedar.cp.api.model.amm.AmmDataTypeMap;
import com.cedar.cp.api.model.amm.AmmMap;
import com.cedar.cp.api.model.amm.AmmModelMap;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.model.AggregatedModelTaskRequest;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.AggregatedModelTask$AggregatedModelCheckpoint;
import com.cedar.cp.ejb.base.async.AggregatedModelTask$SourceCube;
import com.cedar.cp.ejb.base.async.AggregatedModelTask$TargetCube;
import com.cedar.cp.ejb.impl.model.amm.AmmModelDAO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.InitialContext;

public class AggregatedModelTask extends AbstractTask {

   public int getReportType() {
      return 0;
   }

   public String getEntityName() {
      return "AggregatedModelTask";
   }

   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      if(this.getCheckpoint() == null) {
         this.firstTime();
      } else {
         this.nextStep();
      }

   }

   private void firstTime() {
      this.setCheckpoint(new AggregatedModelTask$AggregatedModelCheckpoint());
      AmmMap ammMap = (new AmmModelDAO()).getAmmMap();
      this.log(" ");
      Object refreshList;
      Iterator i$;
      AmmModelMap m;
      if(this.getMyRequest().getRequestList() == null) {
         this.log("No specific cube/dataType mappings have been supplied.");
         this.log("In this mode, the task will only refresh those mappings");
         this.log("that are out of date.");
         this.log("The list below shows all the existing mappings.");
         this.log("- Items marked with a * are out of date.");
         refreshList = new ArrayList();
         i$ = ammMap.getModelMaps().iterator();

         while(i$.hasNext()) {
            m = (AmmModelMap)i$.next();
            ((List)refreshList).addAll(this.createOutOfDateRequests(m));
         }
      } else {
         this.log("Specific cube/dataType mappings have been specified.");
         this.log("In this mode, the task will refresh only the requested");
         this.log("mappings regardless of whether they are out of date.");
         this.log(" ");
         this.log("The following list shows all the existing mappings.");
         this.log("- Items marked with a * are out of date.");
         this.log("- Items marked with <req> have been specifically requested.");
         refreshList = this.getMyRequest().getRequestList();
      }

      this.log(" ");
      this.log("Dependency Tree:");
      this.log(" ");
      i$ = ammMap.getModelMaps().iterator();

      while(i$.hasNext()) {
         m = (AmmModelMap)i$.next();
         this.printModel(m, 1);
      }

      this.log(" ");
      this.buildWorkList(ammMap, (List)refreshList);
      if(this.getMyCheckpoint().noTargetCubesRemaining()) {
         this.setCheckpoint((TaskCheckpoint)null);
      }

   }

   private List<AmmDataTypeMap> createOutOfDateRequests(AmmModelMap m) {
      ArrayList outOfDateRequests = new ArrayList();
      Iterator i$;
      if(m.getParentId() != null) {
         i$ = m.getCubeMaps().iterator();

         while(i$.hasNext()) {
            AmmCubeMap x = (AmmCubeMap)i$.next();
            Iterator i$1 = x.getDataTypeMaps().iterator();

            while(i$1.hasNext()) {
               AmmDataTypeMap y = (AmmDataTypeMap)i$1.next();
               if(y.isTargetRefreshNeeded()) {
                  outOfDateRequests.add(y);
               }
            }
         }
      }

      i$ = m.getModelMaps().iterator();

      while(i$.hasNext()) {
         AmmModelMap x1 = (AmmModelMap)i$.next();
         outOfDateRequests.addAll(this.createOutOfDateRequests(x1));
      }

      return outOfDateRequests;
   }

   private void printModel(AmmModelMap m, int level) {
      this.log(this.fill(' ', level * 4) + " model " + m.getVisId() + (m.isTargetRefreshNeeded()?" *":""));
      Iterator i$;
      if(m.getParentId() != null) {
         i$ = m.getCubeMaps().iterator();

         while(i$.hasNext()) {
            AmmCubeMap x = (AmmCubeMap)i$.next();
            this.log(this.fill(' ', (level + 2) * 4) + "cube " + x.getTargetVisId() + " <-- " + x.getSourceVisId() + (x.isTargetRefreshNeeded()?" *":""));
            Iterator i$1 = x.getDataTypeMaps().iterator();

            while(i$1.hasNext()) {
               AmmDataTypeMap y = (AmmDataTypeMap)i$1.next();
               this.log(this.fill(' ', (level + 3) * 4) + "dataType " + y.getTargetVisId() + " <-- " + y.getSourceVisId() + (this.getMyRequest().isRefreshRequested(y)?" <req>":"") + (y.isTargetRefreshNeeded()?" *" + y.getTimeDifferenceText():""));
            }
         }
      }

      i$ = m.getModelMaps().iterator();

      while(i$.hasNext()) {
         AmmModelMap x1 = (AmmModelMap)i$.next();
         this.printModel(x1, level + 1);
      }

   }

   private void buildWorkList(AmmMap ammMap, List<AmmDataTypeMap> refreshList) {
      ArrayList cubes = new ArrayList();
      Iterator targetCube = ammMap.getModelMaps().iterator();

      while(targetCube.hasNext()) {
         AmmModelMap i$ = (AmmModelMap)targetCube.next();
         this.buildCubeList(i$, cubes);
      }

      AggregatedModelTask$TargetCube targetCube1 = null;
      Iterator i$3 = cubes.iterator();

      while(i$3.hasNext()) {
         AmmCubeMap cube = (AmmCubeMap)i$3.next();
         if(targetCube1 == null || !targetCube1.getTargetCubeId().equals(cube.getTargetId())) {
            if(targetCube1 != null && targetCube1.getSourceCubes().size() > 0) {
               this.getMyCheckpoint().addTargetCube(targetCube1);
            }

            targetCube1 = new AggregatedModelTask$TargetCube(cube.getTargetId(), cube.getTargetVisId());
         }

         AggregatedModelTask$SourceCube sc = new AggregatedModelTask$SourceCube(cube.getSourceId(), cube.getSourceVisId(), cube.getAmmModelId());
         Iterator i$1 = cube.getDataTypeMaps().iterator();

         while(i$1.hasNext()) {
            AmmDataTypeMap dtMap = (AmmDataTypeMap)i$1.next();
            Iterator i$2 = refreshList.iterator();

            while(i$2.hasNext()) {
               AmmDataTypeMap req = (AmmDataTypeMap)i$2.next();
               if(dtMap.getAmmDataTypeId().equals(req.getAmmDataTypeId())) {
                  sc.addDataTypeMap(dtMap);
               }
            }
         }

         if(sc.getDataTypeMaps().size() > 0) {
            targetCube1.addSourceCube(sc);
         }
      }

      if(targetCube1 != null && targetCube1.getSourceCubes().size() > 0) {
         this.getMyCheckpoint().addTargetCube(targetCube1);
      }

   }

   private void buildCubeList(AmmModelMap modelMap, List<AmmCubeMap> cubes) {
      cubes.addAll(modelMap.getCubeMaps());
      Iterator i$ = modelMap.getModelMaps().iterator();

      while(i$.hasNext()) {
         AmmModelMap m = (AmmModelMap)i$.next();
         this.buildCubeList(m, cubes);
      }

   }

   private void nextStep() {
      AggregatedModelTask$TargetCube targetCube = this.getMyCheckpoint().getTargetCube();
      Integer sourceCubeId = null;
      String sourceDataTypeIds = null;
      if(this.getMyCheckpoint().getStoredProcedureStep() == 2) {
         while(!targetCube.noSourcesRemaining()) {
            AggregatedModelTask$SourceCube sourceCube = targetCube.getNextSourceCube();
            sourceCubeId = sourceCube.getCubeId();
            sourceDataTypeIds = sourceCube.getSourceDataTypeIds();
            this.log("obtaining transactions from " + sourceCube.getCubeVisId());
            (new AmmModelDAO()).performStep(Integer.valueOf(this.getMyCheckpoint().getStoredProcedureStep()), Integer.valueOf(this.getTaskId()), targetCube.getTargetCubeId(), sourceCubeId, sourceDataTypeIds);
            targetCube.increaseSourceCubeIndex();
         }

         this.log(" ");
         this.log("updating " + targetCube.getTargetCubeVisId());
         this.getMyCheckpoint().increaseStoredProcedureStep();
         (new AmmModelDAO()).performStep(Integer.valueOf(this.getMyCheckpoint().getStoredProcedureStep()), Integer.valueOf(this.getTaskId()), targetCube.getTargetCubeId(), (Integer)null, (String)null);
      } else {
         (new AmmModelDAO()).performStep(Integer.valueOf(this.getMyCheckpoint().getStoredProcedureStep()), Integer.valueOf(this.getTaskId()), targetCube.getTargetCubeId(), (Integer)null, (String)null);
      }

      this.getMyCheckpoint().increaseStoredProcedureStep();
      if(this.getMyCheckpoint().noStepsRemaining()) {
         this.getMyCheckpoint().increaseTargetCubeIndex();
         if(this.getMyCheckpoint().noTargetCubesRemaining()) {
            this.setCheckpoint((TaskCheckpoint)null);
         }
      }

   }

   private void displayMapping(Integer ammModelId) {
      this.log(" ");
      EntityList modelRS = (new AmmModelDAO()).getMappingDisplay(ammModelId.intValue());

      for(int modIdx = 0; modIdx < modelRS.getNumRows(); ++modIdx) {
         this.log("model " + modelRS.getValueAt(modIdx, "MODEL_VIS_ID") + " <-- " + modelRS.getValueAt(modIdx, "SRC_MODEL_VIS_ID"));
         EntityList dimRS = (EntityList)modelRS.getValueAt(modIdx, "DIMS");

         for(int cubeRS = 0; cubeRS < dimRS.getNumRows(); ++cubeRS) {
            String cubeIdx = (String)dimRS.getValueAt(cubeRS, "DIM_VIS_ID");
            BigDecimal dataTypeRS = (BigDecimal)dimRS.getValueAt(cubeRS, "DIMENSION_ID");
            String dtIdx = (String)dimRS.getValueAt(cubeRS, "SRC_DIM_VIS_ID");
            BigDecimal srcDimId = (BigDecimal)dimRS.getValueAt(cubeRS, "SRC_DIMENSION_ID");
            String srcHierVisId = (String)dimRS.getValueAt(cubeRS, "SRC_HIERARCHY_VIS_ID");
            BigDecimal srcHierId = (BigDecimal)dimRS.getValueAt(cubeRS, "SRC_HIERARCHY_ID");
            EntityList dimElemRS = (EntityList)dimRS.getValueAt(cubeRS, "DIM_ELEMS");
            if(cubeIdx != null && dtIdx != null) {
               this.log(this.fill(' ', 4) + "dimension " + cubeIdx + " <-- " + dtIdx + " (hier=" + srcHierVisId + ")");
               String var21 = "";

               for(int var22 = 0; var22 < dimElemRS.getNumRows(); ++var22) {
                  var21 = var21 + "<" + dimElemRS.getValueAt(var22, "DIM_ELEM_VIS_ID");
                  EntityList var23 = (EntityList)dimElemRS.getValueAt(var22, "SRC_ELEMS");

                  for(int seIdx1 = 0; seIdx1 < var23.getNumRows(); ++seIdx1) {
                     var21 = var21 + "{" + var23.getValueAt(seIdx1, "SRC_SE_VIS_ID") + "}";
                     if(seIdx1 == var23.getNumRows() - 1) {
                        var21 = var21 + ">";
                     }

                     if(var21.length() > 50) {
                        this.log(this.fill(' ', 8) + var21);
                        var21 = "";
                     }
                  }
               }

               if(var21.length() > 0) {
                  this.log(this.fill(' ', 8) + var21);
               }
            } else if(cubeIdx != null) {
               this.log(this.fill(' ', 4) + "unmapped dimension " + cubeIdx);
               this.log(this.fill(' ', 8) + "dimElem=" + dimElemRS.getValueAt(0, "DIM_ELEM_VIS_ID"));
            } else {
               this.log(this.fill(' ', 4) + "unmapped src dimension " + dtIdx + " (hier=" + srcHierVisId + ")");
               EntityList srcElemRS = (EntityList)dimElemRS.getValueAt(0, "SRC_ELEMS");
               String line = "";

               for(int seIdx = 0; seIdx < srcElemRS.getNumRows(); ++seIdx) {
                  line = line + "{" + srcElemRS.getValueAt(seIdx, "SRC_SE_VIS_ID") + "}";
                  if(line.length() > 50) {
                     this.log(this.fill(' ', 8) + line);
                     line = "";
                  }
               }

               if(line.length() > 0) {
                  this.log(this.fill(' ', 8) + line);
               }
            }
         }

         EntityList var17 = (EntityList)modelRS.getValueAt(modIdx, "CUBES");

         for(int var18 = 0; var18 < var17.getNumRows(); ++var18) {
            this.log(this.fill(' ', 4) + "cube " + var17.getValueAt(var18, "CUBE_VIS_ID") + " <-- " + var17.getValueAt(var18, "SRC_CUBE_VIS_ID"));
            EntityList var19 = (EntityList)var17.getValueAt(var18, "DATA_TYPES");

            for(int var20 = 0; var20 < var19.getNumRows(); ++var20) {
               this.log(this.fill(' ', 8) + "    dataType " + var19.getValueAt(var20, "DATA_TYPE") + " <-- " + var19.getValueAt(var20, "SRC_DATA_TYPE") + " (startOffset=" + var19.getValueAt(var20, "SRC_START_YEAR_OFFSET") + " endOffset=" + var19.getValueAt(var20, "SRC_END_YEAR_OFFSET") + ")");
            }
         }
      }

      this.log(" ");
   }

   private String fill(char fillChar, int len) {
      StringBuilder sb = new StringBuilder(len);

      for(int i = 0; i < len; ++i) {
         sb.append(fillChar);
      }

      return sb.toString();
   }

   private AggregatedModelTask$AggregatedModelCheckpoint getMyCheckpoint() {
      return (AggregatedModelTask$AggregatedModelCheckpoint)this.getCheckpoint();
   }

   private AggregatedModelTaskRequest getMyRequest() {
      return (AggregatedModelTaskRequest)this.getRequest();
   }
}
