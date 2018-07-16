// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.recharge;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.recharge.Recharge;
import com.cedar.cp.api.model.recharge.RechargeCellDataVO;
import com.cedar.cp.dto.datatype.DataTypeNodeImpl;
import com.cedar.cp.dto.dimension.StructureElementNodeImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.recharge.RechargeImpl;
import com.cedar.cp.dto.model.recharge.RechargePK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.dimension.DimensionNodeImpl;
import com.cedar.cp.impl.model.recharge.RechargeEditorSessionImpl;
import com.cedar.cp.util.OnDemandMutableTreeNode;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class RechargeAdapter implements Recharge {

   private RechargeImpl mEditorData;
   private RechargeEditorSessionImpl mEditorSessionImpl;


   public RechargeAdapter(RechargeEditorSessionImpl e, RechargeImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected RechargeEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected RechargeImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(RechargePK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public String getReason() {
      return this.mEditorData.getReason();
   }

   public String getReference() {
      return this.mEditorData.getReference();
   }

   public BigDecimal getAllocationPercentage() {
      return this.mEditorData.getAllocationPercentage();
   }

   public boolean isManualRatios() {
      return this.mEditorData.isManualRatios();
   }

   public int getAllocationDataTypeId() {
      return this.mEditorData.getAllocationDataTypeId();
   }

   public boolean isDiffAccount() {
      return this.mEditorData.isDiffAccount();
   }

   public int getAccountStructureId() {
      return this.mEditorData.getAccountStructureId();
   }

   public int getAccountStructureElementId() {
      return this.mEditorData.getAccountStructureElementId();
   }

   public int getRatioType() {
      return this.mEditorData.getRatioType();
   }

   public ModelRef getModelRef() {
      if(this.mEditorData.getModelRef() != null) {
         if(this.mEditorData.getModelRef().getNarrative() != null && this.mEditorData.getModelRef().getNarrative().length() > 0) {
            return this.mEditorData.getModelRef();
         } else {
            try {
               ModelRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getModelEntityRef(this.mEditorData.getModelRef());
               this.mEditorData.setModelRef(e);
               return e;
            } catch (Exception var2) {
               throw new RuntimeException(var2.getMessage());
            }
         }
      } else {
         return null;
      }
   }

   public void setModelRef(ModelRef ref) {
      this.mEditorData.setModelRef(ref);
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setReason(String p) {
      this.mEditorData.setReason(p);
   }

   public void setReference(String p) {
      this.mEditorData.setReference(p);
   }

   public void setAllocationPercentage(BigDecimal p) {
      this.mEditorData.setAllocationPercentage(p);
   }

   public void setManualRatios(boolean p) {
      this.mEditorData.setManualRatios(p);
   }

   public void setAllocationDataTypeId(int p) {
      this.mEditorData.setAllocationDataTypeId(p);
   }

   public void setDiffAccount(boolean p) {
      this.mEditorData.setDiffAccount(p);
   }

   public void setAccountStructureId(int p) {
      this.mEditorData.setAccountStructureId(p);
   }

   public void setAccountStructureElementId(int p) {
      this.mEditorData.setAccountStructureElementId(p);
   }

   public void setRatioType(int p) {
      this.mEditorData.setRatioType(p);
   }

   public TreeModel getAccountTree() {
      if(this.mEditorData.getAccountTree() == null) {
         CPConnection conn = this.mEditorSessionImpl.getConnection();
         EntityList list = conn.getModelsProcess().getTreeInfoForDimTypeInModel(((ModelPK)this.getModelRef().getPrimaryKey()).getModelId(), 1);
         List models = this.processTreeModels(list);
         Object model = null;
         if(list.getNumRows() == 1) {
            model = (TreeModel)models.get(0);
         } else {
            model = new DefaultTreeModel(new DefaultMutableTreeNode("Empty List"));
         }

         this.mEditorData.setAccountModel((TreeModel)model);
      }

      return this.mEditorData.getAccountTree();
   }

   public Object getAccountStructureElementRef() {
      if(this.mEditorData.getAccountStructureElementRef() == null && this.getAccountStructureElementId() != 0) {
         CPConnection conn = this.mEditorSessionImpl.getConnection();
         EntityList list = conn.getListHelper().getStructureElement(this.getAccountStructureElementId());
         this.mEditorData.setAccountStructureElementRef(list.getValueAt(0, "StructureElement"));
      }

      return this.mEditorData.getAccountStructureElementRef();
   }

   public EntityList getTableModelHeadings() {
      if(this.mEditorData.getTableModelHeadings() == null) {
         CPConnection conn = this.mEditorSessionImpl.getConnection();
         EntityList list = conn.getListHelper().getModelDimensionseExcludeCall(((ModelPK)this.getModelRef().getPrimaryKey()).getModelId());
         this.mEditorData.setTableModelHeadings(list);
      }

      return this.mEditorData.getTableModelHeadings();
   }

   public List<RechargeCellDataVO> getSelectedSourceCells() {
      return this.mEditorData.getSelectedSourceCells();
   }

   public List<RechargeCellDataVO> getSelectedTargetCells() {
      return this.mEditorData.getSelectedTargetCells();
   }

   public List<RechargeCellDataVO> getSelectedOffsetCells() {
      return this.mEditorData.getSelectedOffsetCells();
   }

   public TreeModel[] getCellPickerModel(boolean incDataType) {
      return this.getCellPickerModel(incDataType, false);
   }

   public TreeModel[] getCellPickerModel(boolean incDataType, boolean writeableDataTypes) {
      TreeModel[] models = new TreeModel[0];
      ArrayList values = new ArrayList();
      values.addAll(this.getStructureElementModel());
      if(incDataType) {
         values.addAll(this.getDataTypeModel(writeableDataTypes));
      }

      models = (TreeModel[])((TreeModel[])values.toArray(models));
      return models;
   }

   public List getStructureElementModel() {
      if(this.mEditorData.getStructureElementModel() == null) {
         CPConnection conn = this.mEditorSessionImpl.getConnection();
         int[] types = new int[]{1, 2};
         EntityList list = conn.getListHelper().getTreeInfoForModelDimTypes(((ModelPK)this.getModelRef().getPrimaryKey()).getModelId(), types);
         List values = this.processTreeModels(list);
         this.mEditorData.setStructureElementModel(values);
      }

      return this.mEditorData.getStructureElementModel();
   }

   public List getDataTypeModel(boolean writeableDataTypes) {
      ArrayList values = new ArrayList();
      CPConnection conn = this.mEditorSessionImpl.getConnection();
      EntityList list;
      if(writeableDataTypes) {
         list = conn.getListHelper().getDataTypesByTypeWriteable(0);
      } else {
         list = conn.getListHelper().getDataTypesByType(0);
      }

      int size = list.getNumRows();
      DefaultMutableTreeNode root = new DefaultMutableTreeNode("All Data Types");

      for(int i = 0; i < size; ++i) {
         DataTypeNodeImpl node = new DataTypeNodeImpl(list.getRowData(i));
         root.add(new DefaultMutableTreeNode(node));
      }

      values.add(new DefaultTreeModel(root));
      return values;
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
               StructureElementNodeImpl node = new StructureElementNodeImpl(this.getEditorSessionImpl().getConnection(), elementList, "RechargeAdapter");
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
}
