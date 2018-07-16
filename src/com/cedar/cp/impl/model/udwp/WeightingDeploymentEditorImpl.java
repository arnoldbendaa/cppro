// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.udwp;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeNode;
import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.dimension.StructureElementNode;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.udwp.WeightingDeployment;
import com.cedar.cp.api.model.udwp.WeightingDeploymentEditor;
import com.cedar.cp.api.model.udwp.WeightingProfileEditorSession;
import com.cedar.cp.api.model.udwp.WeightingProfileRef;
import com.cedar.cp.api.model.udwp.WeightingProfilesProcess;
import com.cedar.cp.dto.datatype.DataTypeNodeImpl;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.StructureElementKeyImpl;
import com.cedar.cp.dto.dimension.StructureElementNodeImpl;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentEditorSessionSSO;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.SwingUtils;
import com.cedar.cp.impl.model.udwp.WeightingDeploymentAdapter;
import com.cedar.cp.impl.model.udwp.WeightingDeploymentEditorImpl$1;
import com.cedar.cp.impl.model.udwp.WeightingDeploymentEditorImpl$DataTypeCategory;
import com.cedar.cp.impl.model.udwp.WeightingDeploymentEditorImpl$MyRootNode;
import com.cedar.cp.impl.model.udwp.WeightingDeploymentEditorSessionImpl;
import com.cedar.cp.util.OnDemandMutableTreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public class WeightingDeploymentEditorImpl extends BusinessEditorImpl implements WeightingDeploymentEditor {

   private TreeModel mAccountModel;
   private int mAccountDimensionId;
   private TreeModel mBusinessModel;
   private int mBusinessDimensionId;
   private TreeModel mDataTypeModel;
   private WeightingDeploymentEditorSessionSSO mServerSessionData;
   private WeightingDeploymentImpl mEditorData;
   private WeightingDeploymentAdapter mEditorDataAdapter;


   public WeightingDeploymentEditorImpl(WeightingDeploymentEditorSessionImpl session, WeightingDeploymentEditorSessionSSO serverSessionData, WeightingDeploymentImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(WeightingDeploymentEditorSessionSSO serverSessionData, WeightingDeploymentImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setProfileId(int newProfileId) throws ValidationException {
      this.validateProfileId(newProfileId);
      if(this.mEditorData.getProfileId() != newProfileId) {
         this.setContentModified();
         this.mEditorData.setProfileId(newProfileId);
      }
   }

   public void setAnyAccount(boolean newAnyAccount) throws ValidationException {
      this.validateAnyAccount(newAnyAccount);
      if(this.mEditorData.isAnyAccount() != newAnyAccount) {
         this.setContentModified();
         this.mEditorData.setAnyAccount(newAnyAccount);
      }
   }

   public void setAnyBusiness(boolean newAnyBusiness) throws ValidationException {
      this.validateAnyBusiness(newAnyBusiness);
      if(this.mEditorData.isAnyBusiness() != newAnyBusiness) {
         this.setContentModified();
         this.mEditorData.setAnyBusiness(newAnyBusiness);
      }
   }

   public void setAnyDataType(boolean newAnyDataType) throws ValidationException {
      this.validateAnyDataType(newAnyDataType);
      if(this.mEditorData.isAnyDataType() != newAnyDataType) {
         this.setContentModified();
         this.mEditorData.setAnyDataType(newAnyDataType);
      }
   }

   public void setWeighting(int newWeighting) throws ValidationException {
      this.validateWeighting(newWeighting);
      if(this.mEditorData.getWeighting() != newWeighting) {
         this.setContentModified();
         this.mEditorData.setWeighting(newWeighting);
      }
   }

   public void validateProfileId(int newProfileId) throws ValidationException {}

   public void validateAnyAccount(boolean newAnyAccount) throws ValidationException {}

   public void validateAnyBusiness(boolean newAnyBusiness) throws ValidationException {}

   public void validateAnyDataType(boolean newAnyDataType) throws ValidationException {}

   public void validateWeighting(int newWeighting) throws ValidationException {}

   public void setWeightingProfileRef(WeightingProfileRef ref) throws ValidationException {
      WeightingProfileRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getWeightingProfileEntityRef(ref);
         } catch (Exception var6) {
            throw new ValidationException(var6.getMessage());
         }
      }

      if(this.mEditorData.getWeightingProfileRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getWeightingProfileRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setWeightingProfileRef(actualRef);
      this.setContentModified();
      if(this.mEditorData.getWeightingProfileRef() != null) {
         WeightingProfilesProcess process = this.getConnection().getWeightingProfilesProcess();
         WeightingProfileEditorSession session = process.getWeightingProfileEditorSession(this.mEditorData.getWeightingProfileRef().getPrimaryKey());
         String descr = session.getWeightingProfileEditor().getWeightingProfile().getDescription();
         process.terminateSession(session);
         this.mEditorData.setProfileDescription(descr);
      }

   }

   public void setModelRef(ModelRef ref) throws ValidationException {
      ModelRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getModelEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getModelRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getModelRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setModelRef(actualRef);
      this.setContentModified();
   }

   public EntityList getOwnershipRefs() {
      return ((WeightingDeploymentEditorSessionImpl)this.getBusinessSession()).getOwnershipRefs();
   }

   public WeightingDeployment getWeightingDeployment() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new WeightingDeploymentAdapter((WeightingDeploymentEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}

   public TreeModel getAccountTreeModel() {
      if(this.mAccountModel == null) {
         DefaultMutableTreeNode root = new DefaultMutableTreeNode(new WeightingDeploymentEditorImpl$MyRootNode("All Accounts"));
         int modelId = ((ModelRefImpl)this.mEditorData.getModelRef()).getModelPK().getModelId();
         EntityList roots = this.getConnection().getListHelper().getAllRootsForModel(modelId);

         for(int i = 0; i < roots.getNumRows(); ++i) {
            int dimType = ((Integer)roots.getValueAt(i, "Type")).intValue();
            if(dimType == 1) {
               this.mAccountDimensionId = ((Integer)roots.getValueAt(i, "DimensionId")).intValue();
               StructureElementNodeImpl se = new StructureElementNodeImpl(this.getConnection(), roots.getRowData(i));
               root.add(new OnDemandMutableTreeNode(se, "com.cedar.cp.impl.dimension.StructureElementProxyNode"));
            }
         }

         this.mAccountModel = new DefaultTreeModel(root);
      }

      return this.mAccountModel;
   }

   public TreeModel getBusinessTreeModel() {
      if(this.mBusinessModel == null) {
         DefaultMutableTreeNode root = new DefaultMutableTreeNode(new WeightingDeploymentEditorImpl$MyRootNode("All Business"));
         int modelId = ((ModelRefImpl)this.mEditorData.getModelRef()).getModelPK().getModelId();
         EntityList roots = this.getConnection().getListHelper().getAllRootsForModel(modelId);
         this.mBusinessDimensionId = -1;

         for(int i = 0; i < roots.getNumRows(); ++i) {
            int dimType = ((Integer)roots.getValueAt(i, "Type")).intValue();
            int id = ((Integer)roots.getValueAt(i, "DimensionId")).intValue();
            if(dimType != 2 || this.mBusinessDimensionId != -1 && this.mBusinessDimensionId != id) {
               break;
            }

            this.mBusinessDimensionId = id;
            StructureElementNodeImpl se = new StructureElementNodeImpl(this.getConnection(), roots.getRowData(i));
            new OnDemandMutableTreeNode(se, "com.cedar.cp.impl.dimension.StructureElementProxyNode");
            root.add(new OnDemandMutableTreeNode(se, "com.cedar.cp.impl.dimension.StructureElementProxyNode"));
         }

         this.mBusinessModel = new DefaultTreeModel(root);
      }

      return this.mBusinessModel;
   }

   public TreeModel getDataTypeTreeModel() {
      if(this.mDataTypeModel == null) {
         EntityList dataTypes = this.getConnection().getListHelper().getAllDataTypes();
         int size = dataTypes.getNumRows();
         DefaultMutableTreeNode root = new DefaultMutableTreeNode(new WeightingDeploymentEditorImpl$MyRootNode("All Data Types"));
         HashMap subTypes = new HashMap();

         for(int i = 0; i < size; ++i) {
            int subType = ((Integer)dataTypes.getValueAt(i, "SubType")).intValue();
            DataTypeNodeImpl node = new DataTypeNodeImpl(dataTypes.getRowData(i));
            if(((Integer)dataTypes.getValueAt(i, "SubType")).intValue() == 4) {
               Integer child = (Integer)dataTypes.getValueAt(i, "MeasureClass");
               if(child != null && child.intValue() != 1) {
                  continue;
               }
            }

            DefaultMutableTreeNode var9;
            if(subTypes.get(Integer.valueOf(node.getSubType())) == null) {
               var9 = this.getSubTypeNode(subType);
               subTypes.put(Integer.valueOf(subType), var9);
               root.add(var9);
            }

            var9 = new DefaultMutableTreeNode(node);
            ((DefaultMutableTreeNode)subTypes.get(Integer.valueOf(subType))).add(var9);
         }

         this.mDataTypeModel = new DefaultTreeModel(root);
      }

      return this.mDataTypeModel;
   }

   private DefaultMutableTreeNode getSubTypeNode(int subType) {
      switch(subType) {
      case 1:
         return new DefaultMutableTreeNode(new WeightingDeploymentEditorImpl$DataTypeCategory("Temp Virement"));
      case 2:
         return new DefaultMutableTreeNode(new WeightingDeploymentEditorImpl$DataTypeCategory("Perm Virement"));
      case 3:
         return new DefaultMutableTreeNode(new WeightingDeploymentEditorImpl$DataTypeCategory("Virtual"));
      case 4:
         return new DefaultMutableTreeNode(new WeightingDeploymentEditorImpl$DataTypeCategory("Measure"));
      default:
         return new DefaultMutableTreeNode(new WeightingDeploymentEditorImpl$DataTypeCategory("Financial Value"));
      }
   }

   public int queryAccountSelectionStatus(Object node) {
      int result = 0;
      if(this.mEditorData.isAnyAccount()) {
         result |= 4;
      }

      DefaultMutableTreeNode dmtn;
      for(Object startNode = node; node != null && node instanceof DefaultMutableTreeNode; node = dmtn.getParent()) {
         dmtn = (DefaultMutableTreeNode)node;
         if(!(dmtn.getUserObject() instanceof StructureElementNode)) {
            break;
         }

         StructureElementNode sen = (StructureElementNode)dmtn.getUserObject();
         Boolean selected = (Boolean)this.mEditorData.getAccountElements().get(this.coerceElement(sen));
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

   public int queryBusinessSelectionStatus(Object node) {
      int result = 0;
      if(this.mEditorData.isAnyBusiness()) {
         result |= 4;
      }

      DefaultMutableTreeNode dmtn;
      for(Object startNode = node; node != null && node instanceof DefaultMutableTreeNode; node = dmtn.getParent()) {
         dmtn = (DefaultMutableTreeNode)node;
         if(!(dmtn.getUserObject() instanceof StructureElementNode)) {
            break;
         }

         StructureElementNode sen = (StructureElementNode)dmtn.getUserObject();
         Boolean selected = (Boolean)this.mEditorData.getBusinessElements().get(this.coerceElement(sen));
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

   public void addAccountElement(Object element, boolean selected) {
      element = this.coerceElement(element);
      if(element instanceof WeightingDeploymentEditorImpl$MyRootNode) {
         this.mEditorData.setAnyAccount(selected);
      } else {
         this.mEditorData.addAccountElement(element, Boolean.valueOf(selected));
      }

      this.setContentModified();
   }

   public Object removeAccountElement(Object element) {
      element = this.coerceElement(element);
      Object removed;
      if(element instanceof WeightingDeploymentEditorImpl$MyRootNode) {
         this.mEditorData.setAnyAccount(false);
         removed = Boolean.valueOf(true);
      } else {
         removed = this.mEditorData.removeAccountElement(element);
      }

      if(removed != null) {
         this.setContentModified();
      }

      return removed;
   }

   public Object removeBusinessElement(Object element) {
      element = this.coerceElement(element);
      Object removed;
      if(element instanceof WeightingDeploymentEditorImpl$MyRootNode) {
         this.mEditorData.setAnyBusiness(false);
         removed = Boolean.valueOf(true);
      } else {
         removed = this.mEditorData.removeBusinessElement(element);
      }

      if(removed != null) {
         this.setContentModified();
      }

      return removed;
   }

   public void addBusinessElement(Object element, boolean selected) {
      element = this.coerceElement(element);
      if(element instanceof WeightingDeploymentEditorImpl$MyRootNode) {
         this.mEditorData.setAnyBusiness(selected);
      } else {
         this.mEditorData.addBusinessElement(element, Boolean.valueOf(selected));
      }

      this.setContentModified();
   }

   public void addDataType(Object dataType) {
      if(this.isDataTypeCategoryNode(dataType)) {
         DefaultMutableTreeNode node = (DefaultMutableTreeNode)dataType;

         for(int i = 0; i < node.getChildCount(); ++i) {
            TreeNode child = node.getChildAt(i);
            if(!this.isDataTypeSelected(child)) {
               this.addDataType(child);
            }
         }
      } else if(!this.isDataTypeSelected(dataType)) {
         dataType = this.coerceElement(dataType);
         if(dataType instanceof WeightingDeploymentEditorImpl$MyRootNode) {
            this.mEditorData.setAnyDataType(true);
         } else {
            this.mEditorData.addDataType(dataType);
         }

         this.setContentModified();
      }

   }

   public boolean removeDataType(Object dataType) {
      boolean removed = false;
      if(this.isDataTypeCategoryNode(dataType)) {
         DefaultMutableTreeNode node = (DefaultMutableTreeNode)dataType;

         for(int i = 0; i < node.getChildCount(); ++i) {
            TreeNode child = node.getChildAt(i);
            if(this.isDataTypeSelected(child)) {
               this.removeDataType(child);
               removed = true;
            }
         }

         return removed;
      } else {
         if(this.isDataTypeSelected(dataType)) {
            dataType = this.coerceElement(dataType);
            if(dataType instanceof WeightingDeploymentEditorImpl$MyRootNode) {
               this.mEditorData.setAnyDataType(false);
               removed = true;
            } else {
               removed = this.mEditorData.removeDataType(dataType);
            }

            if(removed) {
               this.setContentModified();
            }
         }

         return removed;
      }
   }

   private boolean isDataTypeCategoryNode(Object node) {
      return node instanceof DefaultMutableTreeNode && ((DefaultMutableTreeNode)node).getUserObject() instanceof WeightingDeploymentEditorImpl$DataTypeCategory;
   }

   public boolean isDataTypeSelected(Object dataType) {
      if(this.isDataTypeCategoryNode(dataType)) {
         DefaultMutableTreeNode node = (DefaultMutableTreeNode)dataType;

         for(int i = 0; i < node.getChildCount(); ++i) {
            if(!this.isDataTypeSelected(node.getChildAt(i))) {
               return false;
            }
         }

         return true;
      } else if(this.mEditorData.isAnyDataType()) {
         return true;
      } else {
         dataType = this.coerceElement(dataType);
         return this.mEditorData.getDataTypes().contains(dataType);
      }
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

   public void commit() throws ValidationException {
      if(!this.mEditorData.hasAccountEntry()) {
         throw new ValidationException("No account element selection defined");
      } else if(!this.mEditorData.hasBusinessEntry()) {
         throw new ValidationException("No business element selection defined");
      } else if(this.mEditorData.getDataTypes().isEmpty() && !this.mEditorData.isAnyDataType()) {
         throw new ValidationException("No data type selection defined");
      } else {
         super.commit();
      }
   }

   public List<TreeNode> querySelectedBusinessNodes() {
      ArrayList input = new ArrayList();
      Iterator nodes = this.mEditorData.getBusinessElements().entrySet().iterator();

      while(nodes.hasNext()) {
         Entry entry = (Entry)nodes.next();
         input.add((StructureElementKey)this.coerceElement(entry.getKey()));
      }

      EntityList nodes1 = this.getConnection().getHierarchysProcess().queryPathToRoots(input);
      return SwingUtils.locateNodesInTree(this.getBusinessTreeModel(), nodes1);
   }

   public List<TreeNode> querySelectedAccountNodes() {
      ArrayList input = new ArrayList();
      Iterator nodes = this.mEditorData.getAccountElements().entrySet().iterator();

      while(nodes.hasNext()) {
         Entry entry = (Entry)nodes.next();
         input.add((StructureElementKey)this.coerceElement(entry.getKey()));
      }

      EntityList nodes1 = this.getConnection().getHierarchysProcess().queryPathToRoots(input);
      return SwingUtils.locateNodesInTree(this.getAccountTreeModel(), nodes1);
   }

   public List<TreeNode> querySelectedDataTypeNodes() {
      Set dataTypes = this.mEditorData.getDataTypes();
      ArrayList selectedNodes = new ArrayList();
      SwingUtils.locateNodesInTree((DefaultMutableTreeNode)this.getDataTypeTreeModel().getRoot(), dataTypes, selectedNodes, new WeightingDeploymentEditorImpl$1(this));
      return selectedNodes;
   }

   public List<TreeNode> searchBusinessTree(String visId) {
      TreeModel tm = this.getBusinessTreeModel();
      EntityList elements = this.getConnection().getListHelper().doElementPickerSearch(this.mBusinessDimensionId, visId);
      return SwingUtils.locateNodesInTree(tm, elements);
   }

   public List<TreeNode> searchAccountTree(String visId) {
      TreeModel tm = this.getAccountTreeModel();
      EntityList elements = this.getConnection().getListHelper().doElementPickerSearch(this.mAccountDimensionId, visId);
      return SwingUtils.locateNodesInTree(tm, elements);
   }

   // $FF: synthetic method
   static Object accessMethod000(WeightingDeploymentEditorImpl x0, Object x1) {
      return x0.coerceElement(x1);
   }
}
