// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.xmlform.rebuild;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.api.xmlform.rebuild.FormRebuild;
import com.cedar.cp.dto.datatype.DataTypeNodeImpl;
import com.cedar.cp.dto.dimension.StructureElementNodeImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildImpl;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.dimension.DimensionNodeImpl;
import com.cedar.cp.impl.xmlform.rebuild.FormRebuildEditorSessionImpl;
import com.cedar.cp.util.OnDemandMutableTreeNode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;

public class FormRebuildAdapter implements FormRebuild {

   private FormRebuildImpl mEditorData;
   private FormRebuildEditorSessionImpl mEditorSessionImpl;


   public FormRebuildAdapter(FormRebuildEditorSessionImpl e, FormRebuildImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected FormRebuildEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected FormRebuildImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(FormRebuildPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public Timestamp getLastSubmit() {
      return this.mEditorData.getLastSubmit();
   }

   public int getXmlformId() {
      return this.mEditorData.getXmlformId();
   }

   public int getBudgetCycleId() {
      return this.mEditorData.getBudgetCycleId();
   }

   public int[] getStructureIdArray() {
      return this.mEditorData.getStructureIdArray();
   }

   public void setStructureIdArray(int[] p) {
      this.mEditorData.setStructureIdArray(p);
   }

   public int getStructureId0() {
      return this.mEditorData.getStructureId0();
   }

   public int getStructureId1() {
      return this.mEditorData.getStructureId1();
   }

   public int getStructureId2() {
      return this.mEditorData.getStructureId2();
   }

   public int getStructureId3() {
      return this.mEditorData.getStructureId3();
   }

   public int getStructureId4() {
      return this.mEditorData.getStructureId4();
   }

   public int getStructureId5() {
      return this.mEditorData.getStructureId5();
   }

   public int getStructureId6() {
      return this.mEditorData.getStructureId6();
   }

   public int getStructureId7() {
      return this.mEditorData.getStructureId7();
   }

   public int getStructureId8() {
      return this.mEditorData.getStructureId8();
   }

   public int[] getStructureElementIdArray() {
      return this.mEditorData.getStructureElementIdArray();
   }

   public void setStructureElementIdArray(int[] p) {
      this.mEditorData.setStructureElementIdArray(p);
   }

   public int getStructureElementId0() {
      return this.mEditorData.getStructureElementId0();
   }

   public int getStructureElementId1() {
      return this.mEditorData.getStructureElementId1();
   }

   public int getStructureElementId2() {
      return this.mEditorData.getStructureElementId2();
   }

   public int getStructureElementId3() {
      return this.mEditorData.getStructureElementId3();
   }

   public int getStructureElementId4() {
      return this.mEditorData.getStructureElementId4();
   }

   public int getStructureElementId5() {
      return this.mEditorData.getStructureElementId5();
   }

   public int getStructureElementId6() {
      return this.mEditorData.getStructureElementId6();
   }

   public int getStructureElementId7() {
      return this.mEditorData.getStructureElementId7();
   }

   public int getStructureElementId8() {
      return this.mEditorData.getStructureElementId8();
   }

   public String getDataType() {
      return this.mEditorData.getDataType();
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

   public void setLastSubmit(Timestamp p) {
      this.mEditorData.setLastSubmit(p);
   }

   public void setXmlformId(int p) {
      this.mEditorData.setXmlformId(p);
   }

   public void setBudgetCycleId(int p) {
      this.mEditorData.setBudgetCycleId(p);
   }

   public void setStructureId0(int p) {
      this.mEditorData.setStructureId0(p);
   }

   public void setStructureId1(int p) {
      this.mEditorData.setStructureId1(p);
   }

   public void setStructureId2(int p) {
      this.mEditorData.setStructureId2(p);
   }

   public void setStructureId3(int p) {
      this.mEditorData.setStructureId3(p);
   }

   public void setStructureId4(int p) {
      this.mEditorData.setStructureId4(p);
   }

   public void setStructureId5(int p) {
      this.mEditorData.setStructureId5(p);
   }

   public void setStructureId6(int p) {
      this.mEditorData.setStructureId6(p);
   }

   public void setStructureId7(int p) {
      this.mEditorData.setStructureId7(p);
   }

   public void setStructureId8(int p) {
      this.mEditorData.setStructureId8(p);
   }

   public void setStructureElementId0(int p) {
      this.mEditorData.setStructureElementId0(p);
   }

   public void setStructureElementId1(int p) {
      this.mEditorData.setStructureElementId1(p);
   }

   public void setStructureElementId2(int p) {
      this.mEditorData.setStructureElementId2(p);
   }

   public void setStructureElementId3(int p) {
      this.mEditorData.setStructureElementId3(p);
   }

   public void setStructureElementId4(int p) {
      this.mEditorData.setStructureElementId4(p);
   }

   public void setStructureElementId5(int p) {
      this.mEditorData.setStructureElementId5(p);
   }

   public void setStructureElementId6(int p) {
      this.mEditorData.setStructureElementId6(p);
   }

   public void setStructureElementId7(int p) {
      this.mEditorData.setStructureElementId7(p);
   }

   public void setStructureElementId8(int p) {
      this.mEditorData.setStructureElementId8(p);
   }

   public void setDataType(String p) {
      this.mEditorData.setDataType(p);
   }

   public int getModelId() {
      ModelPK pk = (ModelPK)this.getModelRef().getPrimaryKey();
      return pk.getModelId();
   }

   public BudgetCycleRef getBudgetCycleRef() {
      return this.mEditorData.getBudgetCycleRef();
   }

   public XmlFormRef getXmlFormRef() {
      return this.mEditorData.getXmlFormRef();
   }

   public String getSelectionAsText() {
      return this.mEditorData.getSelectionAsText();
   }

   public TreeModel[] getCellPickerModel() {
      TreeModel[] models = new TreeModel[0];
      ArrayList values = new ArrayList();
      values.addAll(this.getStructureElementModel());
      values.addAll(this.getDataTypeModel(true));
      models = (TreeModel[])((TreeModel[])values.toArray(models));
      return models;
   }

   public List getStructureElementModel() {
      CPConnection conn = this.mEditorSessionImpl.getConnection();
      int[] types = new int[]{1, 2, 3};
      EntityList list = conn.getListHelper().getTreeInfoForModelDimTypes(((ModelPK)this.getModelRef().getPrimaryKey()).getModelId(), types);
      List values = this.processTreeModels(list);
      return values;
   }

   public List getDataTypeModel(boolean writeableDataTypes) {
      ArrayList values = new ArrayList();
      CPConnection conn = this.mEditorSessionImpl.getConnection();
      int[] dataTypeSubTypes = new int[]{0, 4};
      EntityList list = conn.getListHelper().getPickerDataTypesWeb(dataTypeSubTypes, writeableDataTypes);
      int size = list.getNumRows();
      DefaultMutableTreeNode root = new DefaultMutableTreeNode("All Data Types");
      HashMap subTypes = new HashMap();
      int subType = 0;

      for(int i = 0; i < size; ++i) {
         DataTypeNodeImpl node = new DataTypeNodeImpl(list.getRowData(i));
         if(i == 0 || subType != node.getSubType()) {
            subType = node.getSubType();
            subTypes.put(Integer.valueOf(subType), this.getSubTypeNode(subType));
            root.add((MutableTreeNode)subTypes.get(Integer.valueOf(subType)));
         }

         DefaultMutableTreeNode child = new DefaultMutableTreeNode(node);
         ((DefaultMutableTreeNode)subTypes.get(Integer.valueOf(subType))).add(child);
      }

      values.add(new DefaultTreeModel(root));
      return values;
   }

   private DefaultMutableTreeNode getSubTypeNode(int subType) {
      switch(subType) {
      case 1:
         return new DefaultMutableTreeNode("Temp Virement");
      case 2:
         return new DefaultMutableTreeNode("Perm Virement");
      case 3:
         return new DefaultMutableTreeNode("Virtual");
      case 4:
         return new DefaultMutableTreeNode("Measure");
      default:
         return new DefaultMutableTreeNode("Financial Value");
      }
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
