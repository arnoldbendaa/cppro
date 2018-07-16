// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.virement;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.model.virement.VirementLocation;
import com.cedar.cp.api.model.virement.VirementLocationsEditor;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.virement.VirementCategoryCK;
import com.cedar.cp.dto.model.virement.VirementCategoryPK;
import com.cedar.cp.dto.model.virement.VirementLocationImpl;
import com.cedar.cp.dto.model.virement.VirementLocationPK;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.base.SwingUtils;
import com.cedar.cp.impl.model.virement.VirementCategoryEditorImpl;
import com.cedar.cp.util.awt.QListModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public class VirementLocationsEditorImpl extends SubBusinessEditorImpl implements VirementLocationsEditor {

   private QListModel mModel = new QListModel(new ArrayList());


   public VirementLocationsEditorImpl(BusinessSession sess, SubBusinessEditorOwner owner) {
      super(sess, owner);
      if(this.getEditor().getVirementCategory().getResponsibilityAreas() != null) {
         this.mModel.addAll(this.getEditor().getVirementCategory().getResponsibilityAreas());
      }

   }

   protected void undoModifications() throws CPException {}

   protected void saveModifications() throws ValidationException {
      if(this.isContentModified()) {
         this.getEditor().setResponsibilityAreas(this.mModel);
         this.getEditor().setContentModified();
      }

   }

   public TreeModel getResponsibilityAreaTree() {
      return this.getEditor().getResponsibilityAreaTree();
   }

   public void addResponsibilityArea(Object structureElementPK, String visId, String description) throws ValidationException {
      VirementLocationPK vlPK = new VirementLocationPK(this.getVirementLocationId(), ((StructureElementPK)structureElementPK).getStructureId(), ((StructureElementPK)structureElementPK).getStructureElementId());
      VirementLocationImpl loc = new VirementLocationImpl(vlPK, visId, description);
      if(this.mModel.contains(loc)) {
         throw new ValidationException("Duplicate entry");
      } else {
         this.mModel.add(0, loc);
         this.setContentModified();
      }
   }

   private int getVirementLocationId() {
      return this.getVirementCategoryPK() == null?-1:this.getVirementCategoryPK().getVirementCategoryId();
   }

   public void removeResponsinbilityArea(VirementLocation loc) throws ValidationException {
      if(this.mModel.remove(loc)) {
         this.setContentModified();
      } else {
         throw new ValidationException("Not found");
      }
   }

   public void removeResponsinbilityArea(Object structureElementPK) throws ValidationException {
      VirementLocation loc = this.getVirementLocation((StructureElementPK)structureElementPK);
      if(loc != null && this.mModel.remove(loc)) {
         this.setContentModified();
      } else {
         throw new ValidationException("Not found");
      }
   }

   private VirementLocation getVirementLocation(StructureElementPK sePK) {
      Iterator i = this.mModel.iterator();

      VirementLocationImpl loc;
      do {
         if(!i.hasNext()) {
            return null;
         }

         loc = (VirementLocationImpl)i.next();
      } while(!loc.getStructureElementKey().equals(sePK));

      return loc;
   }

   public List<TreeNode> searchTree(String visId) {
      EntityList elements = this.getConnection().getListHelper().doElementPickerSearch(this.getBusinessDimensionId(), visId);
      return SwingUtils.locateNodesInTree(this.getResponsibilityAreaTree(), elements);
   }

   public List<TreeNode> queryTreeNodes() {
      ArrayList input = new ArrayList();
      Iterator nodes = this.getLocations().iterator();

      while(nodes.hasNext()) {
         Object o = nodes.next();
         input.add((StructureElementKey)this.coerceElement(o));
      }

      EntityList nodes1 = this.getConnection().getHierarchysProcess().queryPathToRoots(input);
      return SwingUtils.locateNodesInTree(this.getResponsibilityAreaTree(), nodes1);
   }

   private Object coerceElement(Object o) {
      if(o instanceof VirementLocationImpl) {
         o = ((VirementLocationImpl)o).getStructureElementKey();
      }

      return o;
   }

   private int getBusinessDimensionId() {
      ModelRefImpl modelRef = (ModelRefImpl)this.getEditor().getVirementCategory().getModelRef();
      EntityList dims = this.getConnection().getListHelper().getModelDimensions(modelRef.getModelPK().getModelId());
      DimensionRefImpl dimensionRef = (DimensionRefImpl)dims.getValueAt(0, "Dimension");
      return dimensionRef.getDimensionPK().getDimensionId();
   }

   private VirementCategoryPK getVirementCategoryPK() {
      return this.getEditor().getVirementCategory().getPrimaryKey() != null?((VirementCategoryCK)this.getEditor().getVirementCategory().getPrimaryKey()).getVirementCategoryPK():null;
   }

   private VirementCategoryEditorImpl getEditor() {
      return (VirementCategoryEditorImpl)this.getOwner();
   }

   public QListModel getLocations() {
      return this.mModel;
   }
}
