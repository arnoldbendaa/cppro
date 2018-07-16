// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.cc;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeNode;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.StructureElementNode;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.cc.CcDeploymentEditorSession;
import com.cedar.cp.api.model.cc.CcDeploymentLine;
import com.cedar.cp.api.model.cc.CcDeploymentLineEditor;
import com.cedar.cp.api.model.cc.CcMappingLine;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.StructureElementKeyImpl;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.cc.CcDeploymentLineImpl;
import com.cedar.cp.dto.model.cc.CcMappingLineImpl;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.base.SwingUtils;
import com.cedar.cp.impl.model.cc.CcDeploymentEditorImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public class CcDeploymentLineEditorImpl extends SubBusinessEditorImpl implements CcDeploymentLineEditor {

   private CcDeploymentLineImpl mDeploymentLine;


   public CcDeploymentLineEditorImpl(CcDeploymentEditorSession session, CcDeploymentEditorImpl owner, CcDeploymentLineImpl line) {
      super(session, owner);

      try {
         this.mDeploymentLine = (CcDeploymentLineImpl)line.clone();
      } catch (CloneNotSupportedException var5) {
         throw new RuntimeException("Failed to clone CcDeploymentLineImpl:", var5);
      }
   }

   protected void undoModifications() throws CPException {}

   protected void saveModifications() throws ValidationException {
      DimensionRef[] dimensionRefs = this.mDeploymentLine.getDimensionRefs();
      boolean[] selected = new boolean[dimensionRefs.length];
      int i = 0;

      while(i < dimensionRefs.length) {
         DimensionRef dimensionRef = dimensionRefs[i];
         Map entries = (Map)this.mDeploymentLine.getDeploymentEntries().get(dimensionRef);
         Iterator i$ = entries.entrySet().iterator();

         while(true) {
            if(i$.hasNext()) {
               Entry entry = (Entry)i$.next();
               if(!((Boolean)entry.getValue()).booleanValue()) {
                  continue;
               }

               selected[i] = true;
            }

            ++i;
            break;
         }
      }

      for(i = 0; i < selected.length; ++i) {
         if(!selected[i]) {
            throw new ValidationException("A postive selection (a tick) must be made in dimension " + dimensionRefs[i]);
         }
      }

      if(this.mDeploymentLine.getDeploymentDataTypes().isEmpty()) {
         throw new ValidationException("One or more data types must be selected");
      } else {
         this.deleteInvalidMappingLines();
         ((CcDeploymentEditorImpl)this.getOwner()).saveDeploymentLine(this.mDeploymentLine);
      }
   }

   public CcDeploymentLine getDeploymentLine() {
      return this.mDeploymentLine;
   }

   public void setCalendarLevel(int level) throws ValidationException {
      if(level != this.mDeploymentLine.getCalendarLevel()) {
         this.mDeploymentLine.setCalendarLevel(level);
         this.setContentModified();
      }

   }

   public boolean setDeploymentEntry(DimensionRef dimensionRef, Object node, Boolean value) throws ValidationException {
      Map seMap = (Map)this.mDeploymentLine.getDeploymentEntries().get(dimensionRef);
      if(seMap == null) {
         throw new ValidationException("Unable to locate structure element reference set for dimension:" + dimensionRef);
      } else {
         StructureElementRef seRef = (StructureElementRef)this.coerceElement(node);
         Boolean existingValue = (Boolean)seMap.get(seRef);
         if(existingValue != null && existingValue.booleanValue() == value.booleanValue()) {
            return false;
         } else {
            seMap.put(seRef, value);
            this.setContentModified();
            return true;
         }
      }
   }

   public boolean removeDeploymentEntry(DimensionRef dimensionRef, Object node) throws ValidationException {
      Map seMap = (Map)this.mDeploymentLine.getDeploymentEntries().get(dimensionRef);
      if(seMap == null) {
         throw new ValidationException("Unable to locate structure element reference set for dimension:" + dimensionRef);
      } else {
         StructureElementRef seRef = (StructureElementRef)this.coerceElement(node);
         if(seMap.remove(seRef) != null) {
            this.setContentModified();
            return true;
         } else {
            return false;
         }
      }
   }

   public int querySelectionStatus(DimensionRef dimensionRef, Object node) {
      int result = 0;
      Map sekMap = (Map)this.mDeploymentLine.getDeploymentEntries().get(dimensionRef);
      if(sekMap == null) {
         throw new IllegalStateException("Unable to locate structure element reference set for dimension:" + dimensionRef);
      } else {
         DefaultMutableTreeNode dmtn;
         for(Object startNode = node; node != null && node instanceof DefaultMutableTreeNode; node = dmtn.getParent()) {
            dmtn = (DefaultMutableTreeNode)node;
            if(!(dmtn.getUserObject() instanceof StructureElementNode)) {
               break;
            }

            StructureElementNode sen = (StructureElementNode)dmtn.getUserObject();
            Boolean selected = (Boolean)sekMap.get(this.coerceElement(sen));
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
   }

   public List<TreeNode> querySelectedTreeNodes(DimensionRef dimensionRef, TreeModel tm) {
      Map seMap = (Map)this.mDeploymentLine.getDeploymentEntries().get(dimensionRef);
      ArrayList input = new ArrayList();
      Iterator nodes = seMap.entrySet().iterator();

      while(nodes.hasNext()) {
         Entry entry = (Entry)nodes.next();
         StructureElementRefImpl seRef = (StructureElementRefImpl)entry.getKey();
         input.add(new StructureElementKeyImpl(seRef.getStructureElementPK().getStructureId(), seRef.getStructureElementPK().getStructureElementId()));
      }

      EntityList nodes1 = this.getConnection().getHierarchysProcess().queryPathToRoots(input);
      return SwingUtils.locateNodesInTree(tm, nodes1);
   }

   public List<TreeNode> searchDimension(DimensionRef dimensionRef, TreeModel treeModel, String searchText) {
      int dimensionId = ((DimensionRefImpl)dimensionRef).getDimensionPK().getDimensionId();
      EntityList elements = this.getConnection().getListHelper().doElementPickerSearch(dimensionId, searchText);
      return SwingUtils.locateNodesInTree(treeModel, elements);
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
         element = sen.getStructureElementRef();
      }

      return element;
   }

   public void addDataType(DataTypeRef dataTypeRef) {
      if(!this.mDeploymentLine.getDeploymentDataTypes().contains(dataTypeRef)) {
         this.mDeploymentLine.getDeploymentDataTypes().add(dataTypeRef);
         this.setContentModified();
      }

   }

   public void removeDataType(DataTypeRef dataTypeRef) throws ValidationException {
      if(!this.mDeploymentLine.getDeploymentDataTypes().contains(dataTypeRef)) {
         throw new ValidationException("Data type " + dataTypeRef + " not found in deployment line");
      } else {
         this.mDeploymentLine.getDeploymentDataTypes().remove(dataTypeRef);
         this.setContentModified();
      }
   }

   public List<DataTypeRef> getAvailableDataTypes() throws ValidationException {
      return ((CcDeploymentEditorImpl)this.getOwner()).getDataTypes();
   }

   private void deleteInvalidMappingLines() {
      DimensionRef[] explicitDimensionRefs = this.mDeploymentLine.getDeployment().getExplicitMappingDimensionRefs();
      if(explicitDimensionRefs.length > 0) {
         Map deploymentEntries = this.mDeploymentLine.getDeploymentEntries();
         Map treeModelMap = this.getConnection().getHierarchysProcess().getFilteredTreeModels(deploymentEntries);
         Iterator i$ = (new ArrayList(this.mDeploymentLine.getMappingLines())).iterator();

         while(i$.hasNext()) {
            CcMappingLine mappingLine = (CcMappingLine)i$.next();
            CcMappingLineImpl lineImpl = (CcMappingLineImpl)mappingLine;
            boolean mappingLineRemoved = false;
            DimensionRef[] arr$ = explicitDimensionRefs;
            int len$ = explicitDimensionRefs.length;

            for(int i$1 = 0; i$1 < len$; ++i$1) {
               DimensionRef dimensionRef = arr$[i$1];
               TreeModel treeModel = (TreeModel)treeModelMap.get(dimensionRef);
               ArrayList selections = new ArrayList(lineImpl.getEntries());
               boolean isChildOfDeploymentLine = false;
               Iterator i$2 = selections.iterator();

               while(i$2.hasNext()) {
                  StructureElementRef seRef = (StructureElementRef)i$2.next();
                  if(this.mappingSelectionChildOfDeployment(treeModel, seRef)) {
                     isChildOfDeploymentLine = true;
                     break;
                  }
               }

               if(!isChildOfDeploymentLine) {
                  this.mDeploymentLine.getMappingLines().remove(lineImpl);
                  mappingLineRemoved = true;
                  this.setContentModified();
                  break;
               }
            }

            if(!this.mDeploymentLine.getDeploymentDataTypes().contains(lineImpl.getDataType())) {
               this.mDeploymentLine.getMappingLines().remove(lineImpl);
               mappingLineRemoved = true;
               this.setContentModified();
               break;
            }
         }
      }

   }

   private boolean mappingSelectionChildOfDeployment(TreeModel treeModel, StructureElementRef seRef) {
      ArrayList input = new ArrayList();
      StructureElementRefImpl seRefImpl = (StructureElementRefImpl)seRef;
      input.add(new StructureElementKeyImpl(seRefImpl.getStructureElementPK().getStructureId(), seRefImpl.getStructureElementPK().getStructureElementId()));
      EntityList nodes = this.getConnection().getHierarchysProcess().queryPathToRoots(input);
      List treeNodes = SwingUtils.locateNodesInTree(treeModel, nodes);
      Iterator i$ = treeNodes.iterator();

      while(i$.hasNext()) {
         TreeNode treeNode = (TreeNode)i$.next();
         DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)treeNode;
         if(dmtn.getUserObject() instanceof StructureElementNode) {
            StructureElementNode node = (StructureElementNode)dmtn.getUserObject();
            if(node.getStructureId() == seRefImpl.getStructureId() && node.getStructureElementId() == seRefImpl.getStructureElementPK().getStructureElementId()) {
               return true;
            }
         }
      }

      return false;
   }
}
