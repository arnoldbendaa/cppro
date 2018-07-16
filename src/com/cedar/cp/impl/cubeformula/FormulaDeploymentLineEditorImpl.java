// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.cubeformula;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.PickerSelectionStates;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.cubeformula.FormulaDeploymentLine;
import com.cedar.cp.api.cubeformula.FormulaDeploymentLineEditor;
import com.cedar.cp.api.datatype.DataTypeNode;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.StructureElementNode;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentLineImpl;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.StructureElementKeyImpl;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.base.SwingUtils;
import com.cedar.cp.impl.cubeformula.CubeFormulaEditorImpl;
import com.cedar.cp.util.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public class FormulaDeploymentLineEditorImpl extends SubBusinessEditorImpl implements FormulaDeploymentLineEditor, PickerSelectionStates {

   private FormulaDeploymentLineImpl mDeploymentLine;


   public FormulaDeploymentLineEditorImpl(BusinessSession sess, SubBusinessEditorOwner owner, FormulaDeploymentLineImpl line) {
      super(sess, owner);

      try {
         this.mDeploymentLine = (FormulaDeploymentLineImpl)line.clone();
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
         this.getCubeEditor().saveDeploymentLine(this.mDeploymentLine);
      }
   }

   CubeFormulaEditorImpl getCubeEditor() {
      return (CubeFormulaEditorImpl)this.getOwner();
   }

   public List<DataTypeRef> getAvailableDataTypes() {
      return this.getCubeEditor().getAvailableDataTypes();
   }

   public void addDataType(DataTypeRef dataTypeRef) throws ValidationException {
      if(dataTypeRef == null) {
         throw new ValidationException("A valid data type must be supplied");
      } else {
         this.mDeploymentLine.getDeploymentDataTypes().add(dataTypeRef);
         this.setContentModified();
      }
   }

   public void removeDataType(DataTypeRef dataTypeRef) throws ValidationException {
      if(this.mDeploymentLine.getDeploymentDataTypes().remove(dataTypeRef)) {
         this.setContentModified();
      }

   }

   public boolean setDeploymentEntry(DimensionRef dimensionRef, Object node, Boolean value) throws ValidationException {
      Map seMap = (Map)this.mDeploymentLine.getDeploymentEntries().get(dimensionRef);
      if(seMap == null) {
         throw new ValidationException("Unable to locate structure element reference set for dimension:" + dimensionRef);
      } else {
         StructureElementRef seRef = (StructureElementRef)this.coerceElement(node);
         Boolean existingValue = (Boolean)seMap.get(new Pair(seRef, (Object)null));
         if(existingValue != null && existingValue.booleanValue() == value.booleanValue()) {
            return false;
         } else {
            seMap.put(new Pair(seRef, (Object)null), value);
            this.setContentModified();
            return true;
         }
      }
   }

   public boolean setDeploymentEntry(DimensionRef dimensionRef, Object fromNode, Object toNode, Boolean value) throws ValidationException {
      Map seMap = (Map)this.mDeploymentLine.getDeploymentEntries().get(dimensionRef);
      if(seMap == null) {
         throw new ValidationException("Unable to locate structure element reference set for dimension:" + dimensionRef);
      } else {
         StructureElementRef startSeRef = (StructureElementRef)this.coerceElement(fromNode);
         StructureElementRef endSeRef = (StructureElementRef)this.coerceElement(toNode);
         Boolean existingValue = (Boolean)seMap.get(new Pair(startSeRef, endSeRef));
         if(existingValue != null && existingValue.booleanValue() == value.booleanValue()) {
            return false;
         } else {
            seMap.put(new Pair(startSeRef, endSeRef), value);
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
         if(seMap.remove(new Pair(seRef, (Object)null)) != null) {
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
            StructureElementRef seRef = (StructureElementRef)this.coerceElement(sen);
            Boolean selected = (Boolean)sekMap.get(new Pair(seRef, (Object)null));
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

   public List<TreeNode> querySelectedTreeNodes(DimensionRef dimensionRef, TreeModel treeModel) {
      Map seMap = (Map)this.mDeploymentLine.getDeploymentEntries().get(dimensionRef);
      ArrayList input = new ArrayList();
      Iterator nodes = seMap.entrySet().iterator();

      while(nodes.hasNext()) {
         Entry entry = (Entry)nodes.next();
         StructureElementRefImpl startSeRef = (StructureElementRefImpl)((Pair)entry.getKey()).getChild1();
         input.add(new StructureElementKeyImpl(startSeRef.getStructureElementPK().getStructureId(), startSeRef.getStructureElementPK().getStructureElementId()));
         StructureElementRefImpl endSeRef = (StructureElementRefImpl)((Pair)entry.getKey()).getChild2();
         if(endSeRef != null) {
            input.add(new StructureElementKeyImpl(endSeRef.getStructureElementPK().getStructureId(), endSeRef.getStructureElementPK().getStructureElementId()));
         }
      }

      EntityList nodes1 = this.getConnection().getHierarchysProcess().queryPathToRoots(input);
      return SwingUtils.locateNodesInTree(treeModel, nodes1);
   }

   public List<TreeNode> searchDimension(DimensionRef dimensionRef, TreeModel treeModel, String searchText) {
      int dimensionId = ((DimensionRefImpl)dimensionRef).getDimensionPK().getDimensionId();
      EntityList elements = this.getConnection().getListHelper().doElementPickerSearch(dimensionId, searchText);
      return SwingUtils.locateNodesInTree(treeModel, elements);
   }

   public FormulaDeploymentLine getFormulaDeploymentLine() {
      return this.mDeploymentLine;
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
}
