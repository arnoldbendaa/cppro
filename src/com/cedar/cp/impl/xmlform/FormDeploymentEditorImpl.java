// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.xmlform;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeNode;
import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.dimension.StructureElementNode;
import com.cedar.cp.api.xmlform.FormDeploymentData;
import com.cedar.cp.api.xmlform.FormDeploymentEditor;
import com.cedar.cp.dto.datatype.DataTypeNodeImpl;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.StructureElementKeyImpl;
import com.cedar.cp.dto.dimension.StructureElementNodeImpl;
import com.cedar.cp.dto.xmlform.FormDeploymentDataImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.base.SwingUtils;
import com.cedar.cp.impl.dimension.DimensionNodeImpl;
import com.cedar.cp.util.OnDemandMutableTreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public class FormDeploymentEditorImpl extends BusinessEditorImpl implements FormDeploymentEditor {

   private transient TreeModel mRaModel;
   private transient Integer mBusinessDimensionId;
   public static final int SELECTION_STATUS_SELECTED = 1;
   public static final int SELECTION_STATUS_UNSELECTED = 2;
   public static final int SELECTION_STATUS_PARENTAGE_SELECTED = 4;
   public static final int SELECTION_STATUS_PARENTAGE_UNSELECTED = 8;
   private FormDeploymentDataImpl mEditorData = new FormDeploymentDataImpl();


   public FormDeploymentEditorImpl(BusinessSessionImpl session, Object key, int financeCubeId) {
      super(session);
      this.mEditorData.setKey(key);
      this.mEditorData.setFinanceCubeId(Integer.valueOf(financeCubeId));
   }

   public String getIdentifier() {
      return this.mEditorData.getIdentifier();
   }

   public void setIdentifier(String identifier) {
      this.mEditorData.setIdentifier(identifier);
   }
   
	public Integer getBudgetCycleId() {
		return this.mEditorData.getBudgetCycleId();
	}
	
	public void setBudgetCycle(Integer budgetCycleId) {
		this.mEditorData.setBudgetCycled(budgetCycleId);
	}
	
	public List getBudgetCycleModel() {
        if(this.mEditorData.getModelId() == null) {
        	EntityList roots = this.getConnection().getFinanceCubesProcess().getFinanceCubeDetails(this.mEditorData.getFinanceCubeId().intValue());
            this.mEditorData.setModelId((Integer)roots.getValueAt(0, "ModelId"));
        }
		ArrayList values = new ArrayList();
		EntityList bcList = this.getConnection().getBudgetCyclesProcess().getBudgetCyclesForModelWithState(this.mEditorData.getModelId().intValue(), 1);;
		
        for(int j = 0; j < bcList.getNumRows(); ++j) {
           int cycleId = ((Integer)bcList.getValueAt(j, "BudgetCycleId")).intValue();
           String cycleVisId = bcList.getValueAt(j, "VisId").toString();
           values.add(new Object[]{cycleId, cycleVisId});
        }
		return values;
	}

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public void setDescription(String description) {
      this.mEditorData.setDescription(description);
   }

   public Map getBusinessElements() {
      return this.mEditorData.getBusinessElements();
   }

   public void setAutoExpandDepth(int depth) {
      this.mEditorData.setAutoExpandDepth(depth);
   }

   public int getAutoExpandDepth() {
      return this.mEditorData.getAutoExpandDepth();
   }

   public void setFill(boolean fill) {
      this.mEditorData.setFill(fill);
   }

   public boolean getFill() {
      return this.mEditorData.isFill();
   }

   public void setBold(boolean bold) {
      this.mEditorData.setBold(bold);
   }

   public boolean getBold() {
      return this.mEditorData.isBold();
   }

   public void setHorz(boolean horz) {
      this.mEditorData.setHorz(horz);
   }

   public boolean getHorz() {
      return this.mEditorData.isHorz();
   }

   public int queryBusinessSelectionStatus(Object node) {
      int result = 0;

      DefaultMutableTreeNode dmtn;
      for(Object startNode = node; node != null && node instanceof DefaultMutableTreeNode; node = dmtn.getParent()) {
         dmtn = (DefaultMutableTreeNode)node;
         if(!(dmtn.getUserObject() instanceof StructureElementNode)) {
            break;
         }

         StructureElementNode sen = (StructureElementNode)dmtn.getUserObject();
         Boolean selected = (Boolean)this.getBusinessElements().get(this.coerceElement(sen));
         if(selected != null) {
            if(startNode == node) {
               result |= selected.booleanValue()?1:2;
            } else {
               result |= selected.booleanValue()?4:8;
            }
            break;
         }
      }

      return result;
   }

   public Object removeBusinessElement(Object element) {
      element = this.coerceElement(element);
      Object removed = this.getBusinessElements().remove(element);
      return removed;
   }

   public void addBusinessElement(Object element, boolean selection) {
      element = this.coerceElement(element);
      this.getBusinessElements().put(element, Boolean.valueOf(selection));
   }

   public List<TreeNode> searchBusinessTree(String visId) {
      TreeModel tm = this.getBusinessTreeModel();
      EntityList elements = this.getConnection().getListHelper().doElementPickerSearch(this.mBusinessDimensionId.intValue(), visId);
      return SwingUtils.locateNodesInTree(tm, elements);
   }

   public List<TreeNode> querySelectedBusinessNodes() {
      ArrayList input = new ArrayList();
      Iterator nodes = this.getBusinessElements().entrySet().iterator();

      while(nodes.hasNext()) {
         Entry entry = (Entry)nodes.next();
         input.add((StructureElementKey)this.coerceElement(entry.getKey()));
      }

      EntityList nodes1 = this.getConnection().getHierarchysProcess().queryPathToRoots(input);
      return SwingUtils.locateNodesInTree(this.getBusinessTreeModel(), nodes1);
   }

   public TreeModel getBusinessTreeModel() {
      if(this.mRaModel == null) {
         DefaultMutableTreeNode root = new DefaultMutableTreeNode("All Business");
         EntityList roots;
         if(this.mEditorData.getModelId() == null) {
            roots = this.getConnection().getFinanceCubesProcess().getFinanceCubeDetails(this.mEditorData.getFinanceCubeId().intValue());
            this.mEditorData.setModelId((Integer)roots.getValueAt(0, "ModelId"));
         }

         roots = this.getConnection().getListHelper().getAllRootsForModel(this.mEditorData.getModelId().intValue());
         this.mBusinessDimensionId = Integer.valueOf(-1);

         for(int i = 0; i < roots.getNumRows(); ++i) {
            int dimType = ((Integer)roots.getValueAt(i, "Type")).intValue();
            int id = ((Integer)roots.getValueAt(i, "DimensionId")).intValue();
            if(dimType != 2 || this.mBusinessDimensionId.intValue() != -1 && this.mBusinessDimensionId.intValue() != id) {
               break;
            }

            this.mBusinessDimensionId = Integer.valueOf(id);
            StructureElementNodeImpl se = new StructureElementNodeImpl(this.getConnection(), roots.getRowData(i));
            new OnDemandMutableTreeNode(se, "com.cedar.cp.impl.dimension.StructureElementProxyNode");
            root.add(new OnDemandMutableTreeNode(se, "com.cedar.cp.impl.dimension.StructureElementProxyNode"));
         }

         this.mRaModel = new DefaultTreeModel(root);
      }

      return this.mRaModel;
   }

   private Object coerceElement(Object element) {
      if(element instanceof DefaultMutableTreeNode) {
         element = ((DefaultMutableTreeNode)element).getUserObject();
      }

      if(element instanceof DataTypeNode) {
         element = ((DataTypeNode)element).getDataTypeRef();
      }

      if(element instanceof DataTypeRefImpl) {
         element = ((DataTypeRefImpl)element).getDataTypePK();
      }

      if(element instanceof StructureElementNode) {
         StructureElementNode sen = (StructureElementNode)element;
         element = new StructureElementKeyImpl(sen.getStructureId(), sen.getStructureElementId());
      }

      return element;
   }

   public TreeModel[] getCellPickerModel() {
      TreeModel[] models = new TreeModel[0];
      ArrayList values = new ArrayList();
      values.addAll(this.getStructureElementModel());
      values.addAll(this.getDataTypeModel());
      models = (TreeModel[])((TreeModel[])values.toArray(models));
      return models;
   }

   public List getStructureElementModel() {
      EntityList list = this.getConnection().getListHelper().getModelDimensions(this.mEditorData.getModelId().intValue());
      int[] seq = new int[list.getNumRows() - 1];

      for(int i = 1; i < list.getNumRows(); ++i) {
         seq[i - 1] = ((Integer)list.getValueAt(i, "DimensionSeqNum")).intValue();
      }

      list = this.getConnection().getListHelper().getTreeInfoForModelDimSeq(this.mEditorData.getModelId().intValue(), seq);
      List values = this.processTreeModels(list);
      return values;
   }

   public List getDataTypeModel() {
      ArrayList values = new ArrayList();
      CPConnection conn = this.getConnection();
      EntityList list = conn.getListHelper().getAllDataTypesForFinanceCube(this.mEditorData.getFinanceCubeId().intValue());
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
            hierNode.setDescription(hierList.getValueAt(model, "HierarchyDescription").toString());
            DefaultMutableTreeNode hier = new DefaultMutableTreeNode(hierNode);
            EntityList elementList = (EntityList)hierList.getValueAt(model, "StructureElement");
            int elementsize = elementList.getNumRows();

            for(int k = 0; k < elementsize; ++k) {
               StructureElementNodeImpl node = new StructureElementNodeImpl(this.getConnection(), elementList, "FormDeployment");
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

   public Map getSelection() {
      return this.mEditorData.getSelection();
   }

   public void setSelection(Map selection) {
      this.mEditorData.setSelection(selection);
   }

   public void setMailType(int type) {
      this.mEditorData.setMailType(type);
   }

   public int getMailType() {
      return this.mEditorData.getMailType();
   }

   public String getMailContent() {
      return this.mEditorData.getMailContent();
   }

   public void setMailContent(String mailContent) {
      this.mEditorData.setMailContent(mailContent);
   }

   public void setAttchmentName(String attchmentName) {
      this.mEditorData.setAttachmentName(attchmentName);
   }

   public String getAttchmentName() {
      return this.mEditorData.getAttachmentName();
   }

   public void setAttchment(byte[] data) {
      this.mEditorData.setAttachment(data);
   }

   public byte[] getAttatchment() {
      return this.mEditorData.getAttachment();
   }

   public void saveModifications() throws ValidationException {}

   public FormDeploymentData getFormDeploymentData() {
      return this.mEditorData;
   }
}
