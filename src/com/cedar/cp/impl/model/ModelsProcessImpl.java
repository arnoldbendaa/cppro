// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelEditorSession;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.ModelsProcess;
import com.cedar.cp.dto.dimension.StructureElementNodeImpl;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.api.model.ModelEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.dimension.DimensionNodeImpl;
import com.cedar.cp.impl.model.ModelEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.OnDemandMutableTreeNode;
import com.cedar.cp.util.Timer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class ModelsProcessImpl extends BusinessProcessImpl implements ModelsProcess {

   private Log mLog = new Log(this.getClass());


   public ModelsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ModelEditorSessionServer es = new ModelEditorSessionServer(this.getConnection());

      try {
    	 int modelId = ((ModelPK) primaryKey).getModelId();
    	 Object[][] forms = es.checkForms(modelId);
    	 if (forms.length > 0) {
 			String warning = "The following forms will be deleted:\n\n";
 			int noOfForms = 0;
 			for (Object[] array : forms) {
 				if (noOfForms < 6) {
 					warning += "-" + array[0] + " - " + array[2] + "\n";
 				} else {
 					warning += "and " + (forms.length - 5) + " more.";
 					break;
 				}
 				noOfForms++;
 			}
 			warning += "\n\nAre you sure you want to continue?";
 			JOptionPane optionPane = new JOptionPane(warning, 3, JOptionPane.YES_NO_OPTION);			
			JFrame myFrame = new JFrame("XML Forms Check");
		    myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
			JDialog dialog = optionPane.createDialog(myFrame, "Confirm Delete");
			dialog.setVisible(true);
			Integer clicked = (Integer) optionPane.getValue();
			if (clicked == 0) {
				es.delete(primaryKey);
			}
    	 } else {
    		es.delete(primaryKey);
    	 }
      } catch (ValidationException var5) {
         throw var5;
      } catch (RemoteException e) {
      } catch (NullPointerException e) {
    	  // closed dialog without answer
    	  es.delete(primaryKey);
      } catch (CPException var6) {
         throw new RuntimeException("can\'t delete " + primaryKey, var6);
      }

      if(timer != null) {
         timer.logDebug("deleteObject", primaryKey);
      }

   }

   public ModelEditorSession getModelEditorSession(Object key) throws ValidationException {
      ModelEditorSessionImpl sess = new ModelEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllModels() {
      try {
         return this.getConnection().getListHelper().getAllModels();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllModels", var2);
      }
   }

   public EntityList getAllModelsForLoggedUser() {
      try {
         return this.getConnection().getListHelper().getAllModelsForLoggedUser();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get ModelsForLoggedUser", var2);
      }
   }
   
   public Map<String, String> getPropertiesForModelVisId(String modelVisId){
	   try {
		   return this.getConnection().getListHelper().getPropertiesForModelVisId(modelVisId);
	   } catch (Exception e) {
		   e.printStackTrace();
		   throw new RuntimeException("can\'t get getPropertiesForModelVisId", e);
	   }
   }
   
   public Map<String, String> getPropertiesForModelId(int modelId){
	   try {
		   return this.getConnection().getListHelper().getPropertiesForModelId(modelId);
	   } catch (Exception e) {
		   e.printStackTrace();
		   throw new RuntimeException("can\'t get getPropertiesForModelId", e);
	   }
   }
   
   public EntityList getAllModelsForGlobalMappedModel(int modelId) {
       try {
          return this.getConnection().getListHelper().getAllModelsForGlobalMappedModel(modelId);
       } catch (Exception e) {
          e.printStackTrace();
          throw new RuntimeException("can\'t get getAllModelsForGlobalMappedModel", e);
       }
    }
   
   public EntityList getAllModelsWeb() {
      try {
         return this.getConnection().getListHelper().getAllModelsWeb();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllModelsWeb", var2);
      }
   }

   public EntityList getAllModelsWebForUser(int param1) {
      try {
         return this.getConnection().getListHelper().getAllModelsWebForUser(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllModelsWebForUser", var3);
      }
   }

   public EntityList getAllModelsWithActiveCycleForUser(int param1) {
      try {
         return this.getConnection().getListHelper().getAllModelsWithActiveCycleForUser(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllModelsWithActiveCycleForUser", var3);
      }
   }

   public EntityList getAllBudgetHierarchies() {
      try {
         return this.getConnection().getListHelper().getAllBudgetHierarchies();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllBudgetHierarchies", var2);
      }
   }

   public EntityList getModelForDimension(int param1) {
      try {
         return this.getConnection().getListHelper().getModelForDimension(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get ModelForDimension", var3);
      }
   }

   public EntityList getModelDimensions(int param1) {
      try {
         return this.getConnection().getListHelper().getModelDimensions(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get ModelDimensions", var3);
      }
   }

   public EntityList getModelDimensionseExcludeCall(int param1) {
      try {
         return this.getConnection().getListHelper().getModelDimensionseExcludeCall(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get ModelDimensionseExcludeCall", var3);
      }
   }

   public EntityList getModelDetailsWeb(int param1) {
      try {
         return this.getConnection().getListHelper().getModelDetailsWeb(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get ModelDetailsWeb", var3);
      }
   }

   public EntityList getAllRootsForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getAllRootsForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllRootsForModel", var3);
      }
   }

   public EntityList getBudgetHierarchyRootNodeForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getBudgetHierarchyRootNodeForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get BudgetHierarchyRootNodeForModel", var3);
      }
   }

   public EntityList getBudgetCyclesToFixState(int param1) {
      try {
         return this.getConnection().getListHelper().getBudgetCyclesToFixState(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get BudgetCyclesToFixState", var3);
      }
   }

   public EntityList getMaxDepthForBudgetHierarchy(int param1) {
      try {
         return this.getConnection().getListHelper().getMaxDepthForBudgetHierarchy(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get MaxDepthForBudgetHierarchy", var3);
      }
   }

   public EntityList getCalendarSpecForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getCalendarSpecForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get CalendarSpecForModel", var3);
      }
   }

   public EntityList getHierarchiesForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getHierarchiesForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get HierarchiesForModel", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing Model";
      return ret;
   }

   protected int getProcessID() {
      return 20;
   }

   public EntityList getTreeInfoForDimTypeInModel(int modelId, int dimType) {
      try {
         return this.getConnection().getListHelper().getTreeInfoForDimTypeInModel(modelId, dimType);
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new RuntimeException("can\'t get AllRootsForModel", var4);
      }
   }

   public TreeModel[] getTreeInfoForModelDimTypes(int modelid, int[] dimTypes) {
      EntityList list = this.getConnection().getListHelper().getTreeInfoForModelDimTypes(modelid, dimTypes);
      int size = list.getNumRows();
      List values = this.processTreeModels(list);
      TreeModel[] type = new TreeModel[size];
      return (TreeModel[])((TreeModel[])values.toArray(type));
   }

   public TreeModel[] getTreeInfoForModelDimSeq(int modelid, int[] seq) {
      EntityList list = this.getConnection().getListHelper().getTreeInfoForModelDimSeq(modelid, seq);
      int size = list.getNumRows();
      List values = this.processTreeModels(list);
      TreeModel[] type = new TreeModel[size];
      return (TreeModel[])((TreeModel[])values.toArray(type));
   }

   public TreeModel[] getTreeInfoForModelRA(int modelid) {
      EntityList list = this.getConnection().getListHelper().getTreeInfoForModelRA(modelid);
      int size = list.getNumRows();
      List values = this.processTreeModels(list);
      TreeModel[] type = new TreeModel[size];
      return (TreeModel[])((TreeModel[])values.toArray(type));
   }

   private List processTreeModels(EntityList list) {
      int size = list.getNumRows();
      ArrayList values = new ArrayList(size);
      DefaultMutableTreeNode root = null;

      for(int i = 0; i < size; ++i) {
         DimensionNodeImpl dim = new DimensionNodeImpl();
         dim.setVisId(list.getValueAt(i, "VisId").toString());
         dim.setDescription(list.getValueAt(i, "Description").toString());
         dim.setDimensionId(((Integer)list.getValueAt(i, "DimensionId")).intValue());
         root = new DefaultMutableTreeNode(dim);
         EntityList hierList = (EntityList)list.getValueAt(i, "Hierarchy");
         int hierSize = hierList.getNumRows();

         for(int model = 0; model < hierSize; ++model) {
            StructureElementNodeImpl hierNode = new StructureElementNodeImpl();
            hierNode.setVisId(hierList.getValueAt(model, "HierarchyVisId").toString());
            hierNode.setStructureId(((Integer)hierList.getValueAt(model, "HierarchyId")).intValue());
            DefaultMutableTreeNode hier = new DefaultMutableTreeNode(hierNode);
            EntityList elementList = (EntityList)hierList.getValueAt(model, "StructureElement");
            int elementsize = elementList.getNumRows();

            for(int k = 0; k < elementsize; ++k) {
               StructureElementNodeImpl node = new StructureElementNodeImpl(this.getConnection(), elementList, "RechargeAdapter");
               OnDemandMutableTreeNode elem = new OnDemandMutableTreeNode(node, "com.cedar.cp.impl.dimension.StructureElementProxyNode");
               hier.add(elem);
            }

            root.add(hier);
         }

         DefaultTreeModel var17 = new DefaultTreeModel(root);
         values.add(var17);
      }

      return values;
   }

   public List doElementPickerSearch(int dimId, String visId) {
      EntityList list = this.getConnection().getListHelper().doElementPickerSearch(dimId, visId);
      int size = list.getNumRows();
      ArrayList foundPathToRoot = new ArrayList();
      ArrayList contents = new ArrayList();
      String visID = "";

      for(int i = 0; i < size; ++i) {
         StructureElementNodeImpl dto = new StructureElementNodeImpl();
         dto.setDescription((String)list.getValueAt(i, "Description"));
         dto.setLeaf(((Boolean)list.getValueAt(i, "Leaf")).booleanValue());
         dto.setStructureElementId(((Integer)list.getValueAt(i, "StructureElementId")).intValue());
         visID = (String)list.getValueAt(i, "VisId");
         dto.setVisId(visID);
         dto.setStructureId(((Integer)list.getValueAt(i, "StructureId")).intValue());
         if(visID.equals(visId) && i < size - 1 && i != 0) {
            foundPathToRoot.add(contents);
            contents = new ArrayList();
         }

         contents.add(dto);
      }

      foundPathToRoot.add(contents);
      return foundPathToRoot;
   }

   public TreeModel getRespAreaHierarchy(Object modelKey) throws ValidationException {
      int modelId = this.coerceModelId(modelKey);
      EntityList rootDetails = this.getBudgetHierarchyRootNodeForModel(modelId);
      if(rootDetails.getNumRows() == 1) {
         HierarchyRef hierarchyRef = (HierarchyRef)rootDetails.getValueAt(0, "Hierarchy");
         return this.getConnection().getHierarchysProcess().getRuntimeStructure(hierarchyRef);
      } else {
         return null;
      }
   }

   public EntityList getAllHierarchiesForModel(Object modelKey) {
      int modelId = this.coerceModelId(modelKey);
      return this.getConnection().getModelsProcess().getHierarchiesForModel(modelId);
   }

   public DimensionRef getRaDimensionRef(Object modelKey) {
      int modelId = this.coerceModelId(modelKey);
      EntityList dimensionDetails = this.getConnection().getListHelper().getAllDimensionsForModel(modelId);
      return dimensionDetails.getNumRows() >= 3?(DimensionRef)dimensionDetails.getValueAt(0, "Dimension"):null;
   }

   public EntityList getAllDimensionsForModel(Object modelKey) {
      int modelId = this.coerceModelId(modelKey);
      EntityList dimensionDetails = this.getConnection().getListHelper().getAllDimensionsForModel(modelId);
      return dimensionDetails;
   }

   public EntityList getModelDimensions(Object modelKey) {
      return this.getModelDimensions(this.coerceModelId(modelKey));
   }

   public int getModelId(Object modelKey) {
      return this.coerceModelId(modelKey);
   }

   private int coerceModelId(Object key) {
      if(key instanceof FinanceCubeRef) {
         key = ((FinanceCubeRef)key).getPrimaryKey();
      }

      if(key instanceof FinanceCubeCK) {
         key = ((FinanceCubeCK)key).getModelPK();
      }

      if(key instanceof ModelRef) {
         key = ((ModelRefImpl)key).getModelPK();
      }

      if(key instanceof ModelCK) {
         key = ((ModelCK)key).getModelPK();
      }

      if(key instanceof ModelPK) {
         return ((ModelPK)key).getModelId();
      } else {
         throw new IllegalArgumentException("Unexepected model key type:" + key);
      }
   }

   public List<Integer> getReadOnlyRaAccessPositions(int modelId, int userId) {
      return this.getConnection().getListHelper().getReadOnlyRaAccessPositions(modelId, userId);
   }

   @Override
   public int issueImportDataModelTask(List<ModelRef> models, List<DataTypeRef> dataTypes, int globalModelId, int userId) throws ValidationException {
       ModelEditorSessionServer server = new ModelEditorSessionServer(this.getConnection());
       return server.issueImportDataModelTask(models, dataTypes, globalModelId, userId);
   }
}
