// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm.frmwrk;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.cubeformula.CubeFormulaRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.DimensionElementPK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.task.TaskMessageLogger;
import com.cedar.cp.ejb.api.model.mapping.MappedModelEditorSessionServer;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMUpdateListener;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyNodeDAG;
import com.cedar.cp.ejb.impl.model.ModelEVO;

public interface CMMetaDataController {

   void registerModuleListener(CMUpdateListener var1);

   DimensionDAG getDimension();

   DimensionElementDAG getDimensionElementDAG(DimensionElementPK var1);

   HierarchyDAG getHierarchy();

   HierarchyNodeDAG getHierarchyNode(Object var1);

   ModelRef getModelRef();

   ModelEVO getModelEVO();

   EntityList getFinanceCubes();

   void registerFinanceCubeForRebuild(FinanceCubePK var1);

   boolean isFinanceCubeRegisteredForRebuild(FinanceCubePK var1);

   void registerAllFinanceCubesForRebuild();

   boolean cubeHasBalancesForElement(FinanceCubeRef var1, DimensionRef var2, int var3);

   int getDimensionIndex(int var1);

   int getDimensionCount();

   MappedModelEditorSessionServer getMappedModelEditorSessionServer() throws Exception;

   void registerFormulaForUndeploy(FinanceCubeRef var1, CubeFormulaRef var2);

   int getUserId();

   TaskMessageLogger getTaskMessageLogger();

   Integer getTaskId();
}
