// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.cc;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.cc.CcMappingLine;
import com.cedar.cp.api.model.cc.CcMappingLineEditor;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.StructureElementKeyImpl;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.cc.CcMappingLineImpl;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.base.SwingUtils;
import com.cedar.cp.impl.model.cc.CcDeploymentEditorImpl;
import com.cedar.cp.impl.model.cc.CcMappingLineEditorImpl$1;
import com.cedar.cp.util.GeneralUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public class CcMappingLineEditorImpl extends SubBusinessEditorImpl implements CcMappingLineEditor {

   private CcMappingLineImpl mMappingLine;


   public CcMappingLineEditorImpl(BusinessSession sess, SubBusinessEditorOwner owner, CcMappingLineImpl mappingLine) {
      super(sess, owner);

      try {
         this.mMappingLine = (CcMappingLineImpl)mappingLine.clone();
      } catch (CloneNotSupportedException var5) {
         throw new RuntimeException("Failed to clone CcMappingLineImpl:" + var5.getMessage());
      }
   }

   protected void undoModifications() throws CPException {}

   protected void saveModifications() throws ValidationException {
      if(this.mMappingLine.getDataType() == null) {
         throw new ValidationException("A target data type must be selected.");
      } else if(this.mMappingLine.getFormField() == null) {
         throw new ValidationException("A source form field must be selected.");
      } else {
         DimensionRef[] mappingDimensionRefs = this.getCcDeploymentEditorImpl().getCcDeployment().getExplicitMappingDimensionRefs();

         for(int i = 0; i < mappingDimensionRefs.length; ++i) {
            if(this.getMappingEntry(mappingDimensionRefs[i]) == null) {
               throw new ValidationException("A selection in the " + mappingDimensionRefs[i] + " dimension must be made.");
            }
         }

         this.getCcDeploymentEditorImpl().saveMappingLine(this.mMappingLine);
      }
   }

   public CcMappingLine getCcMappingLine() {
      return this.mMappingLine;
   }

   public List<String> getFormFields() throws ValidationException {
      return this.getCcDeploymentEditorImpl().getXMLFormFields();
   }

   public Map<DimensionRef, TreeModel> getTreeModels() {
      DimensionRef[] dimensionRefs = this.getCcDeploymentEditorImpl().getExplicitMappingDimensionRefs();
      Map deploymentEntries = this.mMappingLine.getDeploymentLine().getDeploymentEntries();
      return this.getConnection().getHierarchysProcess().getFilteredTreeModels(deploymentEntries);
   }

   public List<DataTypeRef> getDataTypes() throws ValidationException {
      Set deploymentDataTypes = this.mMappingLine.getDeploymentLine().getDeploymentDataTypes();
      ArrayList dataTypes = new ArrayList(deploymentDataTypes);
      Collections.sort(dataTypes, new CcMappingLineEditorImpl$1(this));
      return dataTypes;
   }

   public void setDataType(DataTypeRef dataTypeRef) {
      if(GeneralUtils.isDifferent(dataTypeRef, this.mMappingLine.getDataType())) {
         this.mMappingLine.setDataTypeRef(dataTypeRef);
         this.setContentModified();
      }

   }

   public int queryDimensionIndex(DimensionRef dimensionRef) {
      DimensionRef[] mappingDimensions = this.getCcDeploymentEditorImpl().getCcDeployment().getExplicitMappingDimensionRefs();
      int index = -1;

      for(int i = 0; i < mappingDimensions.length; ++i) {
         if(dimensionRef.equals(mappingDimensions[i])) {
            index = i;
            break;
         }
      }

      return index;
   }

   public void setMappingEntry(DimensionRef dimensionRef, StructureElementRef structureElementRef) throws ValidationException {
      int mappingDimensionIndex = this.queryDimensionIndex(dimensionRef);
      if(mappingDimensionIndex == -1) {
         throw new ValidationException("Dimension:" + dimensionRef + " is not defined as \'Explicit\' in the deployment context.");
      } else {
         if(GeneralUtils.isDifferent(structureElementRef, this.mMappingLine.getEntries().get(mappingDimensionIndex))) {
            this.mMappingLine.getEntries().set(mappingDimensionIndex, structureElementRef);
            this.setContentModified();
         }

      }
   }

   public StructureElementRef getMappingEntry(DimensionRef dimensionRef) {
      int mappingDimensionIndex = this.queryDimensionIndex(dimensionRef);
      return mappingDimensionIndex == -1?null:(StructureElementRef)this.mMappingLine.getEntries().get(mappingDimensionIndex);
   }

   public void setFormField(String formField) throws ValidationException {
      if(GeneralUtils.isDifferent(formField, this.mMappingLine.getFormField())) {
         this.mMappingLine.setFormField(formField);
         this.setContentModified();
      }

   }

   public List<TreeNode> querySelectedTreeNode(DimensionRef dimensionRef, TreeModel tm) {
      ArrayList input = new ArrayList();
      int dimensionIndex = this.getCcDeploymentEditorImpl().getExplicitDimensionIndex(dimensionRef);
      StructureElementRefImpl seRef = (StructureElementRefImpl)this.mMappingLine.getEntries().get(dimensionIndex);
      if(seRef != null) {
         input.add(new StructureElementKeyImpl(seRef.getStructureElementPK().getStructureId(), seRef.getStructureElementPK().getStructureElementId()));
         EntityList nodes = this.getConnection().getHierarchysProcess().queryPathToRoots(input);
         return SwingUtils.locateNodesInTree(tm, nodes);
      } else {
         return new ArrayList();
      }
   }

   public List<TreeNode> searchDimension(DimensionRef dimensionRef, TreeModel treeModel, String text) {
      int dimensionId = ((DimensionRefImpl)dimensionRef).getDimensionPK().getDimensionId();
      EntityList elements = this.getConnection().getListHelper().doElementPickerSearch(dimensionId, text);
      return SwingUtils.locateNodesInTree(treeModel, elements);
   }

   public DimensionRef[] getExplicitMappingDimensionRefs() {
      return this.getCcDeploymentEditorImpl().getExplicitMappingDimensionRefs();
   }

   public CcDeploymentEditorImpl getCcDeploymentEditorImpl() {
      return (CcDeploymentEditorImpl)this.getOwner();
   }
}
