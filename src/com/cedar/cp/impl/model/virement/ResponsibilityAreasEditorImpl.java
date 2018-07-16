// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.virement;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.virement.ResponsibilityAreaNode;
import com.cedar.cp.api.model.virement.ResponsibilityAreaNodeEditor;
import com.cedar.cp.api.model.virement.ResponsibilityAreasEditor;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaCK;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.base.SwingUtils;
import com.cedar.cp.impl.model.ra.ResponsibilityAreaEditorImpl;
import com.cedar.cp.impl.model.ra.ResponsibilityAreaEditorSessionImpl;
import com.cedar.cp.impl.model.virement.ResponsibilityAreaNodeEditorImpl;
import com.cedar.cp.impl.model.virement.ResponsibilityAreaNodeImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public class ResponsibilityAreasEditorImpl extends BusinessEditorImpl implements ResponsibilityAreasEditor, SubBusinessEditorOwner {

   private TreeModel mTreeModel;
   private Map mChangedNodes = new HashMap();
   private ModelPK mModelPK;


   public ResponsibilityAreasEditorImpl(BusinessSessionImpl session, ModelPK modelPK) {
      super(session);
      this.mModelPK = modelPK;
   }

   public void saveModifications() throws ValidationException {
      boolean first = true;
      ResponsibilityAreaEditorImpl editor = (ResponsibilityAreaEditorImpl)((ResponsibilityAreaEditorSessionImpl)this.getBusinessSession()).getResponsibilityAreaEditor();
      ResponsibilityAreaImpl ra = editor.getRAImpl();
      Map target = ra.getUpdatedNodes();
      Iterator raIter = this.mChangedNodes.values().iterator();

      while(raIter.hasNext()) {
         ResponsibilityAreaNodeImpl node = (ResponsibilityAreaNodeImpl)raIter.next();
         if(first) {
            ra.setPrimaryKey((ResponsibilityAreaCK)node.getRespAreaKey());
            ra.setOwningStructureElementRef(node.getStructureElementRef());
            ra.setStructureElementId(node.getStructureElementId());
            ra.setStructureId(node.getStructureId());
            ra.setVirementAuthStatus(node.queryAuthStatus());
            ra.setModelId(this.mModelPK.getModelId());
            ra.setModelRef(new ModelRefImpl(this.mModelPK, ""));
            ra.setVersionNum(node.getVersionNum());
            first = false;
         } else {
            target.put(new Integer(node.getStructureElementId()), node.makeResponsibilityAreaImpl());
         }
      }

   }

   public void removeSubBusinessEditor(SubBusinessEditor editor) throws CPException {}

   public void updateNode(ResponsibilityAreaNode node) {
      this.putNode(node);
   }

   private void putNode(ResponsibilityAreaNode node) {
      this.mChangedNodes.put(new Integer(node.getStructureElementId()), node);
   }

   private ResponsibilityAreaNode getNode(int seId) {
      return (ResponsibilityAreaNode)this.mChangedNodes.get(new Integer(seId));
   }

   public ResponsibilityAreaNodeEditor getEditor(ResponsibilityAreaNode node) throws ValidationException {
      return new ResponsibilityAreaNodeEditorImpl(this.getBusinessSession(), this, (ResponsibilityAreaNodeImpl)node);
   }

   public int queryNodeStatus(DefaultMutableTreeNode dmtn) {
      ResponsibilityAreaNode ran = (ResponsibilityAreaNode)dmtn.getUserObject();
      if(this.getNode(ran.getStructureElementId()) != null) {
         ran = this.getNode(ran.getStructureElementId());
      }

      return ran.queryAuthStatus() == 0?(dmtn.getParent() != null?this.queryNodeStatus((DefaultMutableTreeNode)dmtn.getParent()):1):ran.queryAuthStatus();
   }

   public int queryNodeSettings(DefaultMutableTreeNode dmtn) {
      ResponsibilityAreaNodeImpl ran = (ResponsibilityAreaNodeImpl)dmtn.getUserObject();
      if(this.getNode(ran.getStructureElementId()) != null) {
         ran = (ResponsibilityAreaNodeImpl)this.getNode(ran.getStructureElementId());
      }

      return ran.queryAuthStatus();
   }

   public List<TreeNode> searchTree(String visId) {
      EntityList elements = this.getConnection().getListHelper().doElementPickerSearch(this.getBusinessDimensionId(), visId);
      return SwingUtils.locateNodesInTree(this.getTree(), elements);
   }

   private int getBusinessDimensionId() {
      EntityList dims = this.getConnection().getListHelper().getModelDimensions(this.mModelPK.getModelId());
      DimensionRefImpl dimensionRef = (DimensionRefImpl)dims.getValueAt(0, "Dimension");
      return dimensionRef.getDimensionPK().getDimensionId();
   }

   public List<TreeNode> queryTreeNodes() {
      return new ArrayList();
   }

   public TreeModel getTree() {
      if(this.mTreeModel == null) {
         try {
            this.mTreeModel = this.getConnection().getResponsibilityAreasProcess().getResponsibilityAreaHierarchy(this.mModelPK);
         } catch (ValidationException var2) {
            throw new CPException("Failed to load hierarchy", var2);
         }
      }

      return this.mTreeModel;
   }
}
