// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.virement;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.model.virement.VirementAccountsEditor;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.virement.VirementAccountImpl;
import com.cedar.cp.dto.model.virement.VirementAccountPK;
import com.cedar.cp.dto.model.virement.VirementCategoryCK;
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

public class VirementAccountsEditorImpl extends SubBusinessEditorImpl implements VirementAccountsEditor {

   private QListModel mModel = new QListModel(new ArrayList());


   public VirementAccountsEditorImpl(BusinessSession sess, SubBusinessEditorOwner owner) {
      super(sess, owner);
      if(this.getEditor().getVirementCategory().getAccounts() != null) {
         this.mModel.addAll(this.getEditor().getVirementCategory().getAccounts());
      }

   }

   public void removeAccount(Object structureElementPK) throws ValidationException {
      VirementAccountImpl acct = this.findAccount((StructureElementPK)structureElementPK);
      if(acct == null) {
         throw new ValidationException("Account element " + structureElementPK + " not found.");
      } else {
         this.mModel.remove(acct);
         this.setContentModified();
      }
   }

   private VirementAccountImpl findAccount(StructureElementPK sePK) {
      Iterator i = this.mModel.iterator();

      VirementAccountImpl acct;
      do {
         if(!i.hasNext()) {
            return null;
         }

         acct = (VirementAccountImpl)i.next();
      } while(acct.getKey().getStructureElementId() != sePK.getStructureElementId());

      return acct;
   }

   public TreeModel getAccountTree() {
      return this.getEditor().getAccountTree();
   }

   public QListModel getAccounts() {
      return this.mModel;
   }

   public void addAccount(Object structureElementPK, String visId, String description, double tranLimit, double totalLimitIn, double totalLimitOut, boolean inAllowed, boolean outAllowed) throws ValidationException {
      StructureElementPK sePK = (StructureElementPK)structureElementPK;
      if(this.findAccount(sePK) != null) {
         throw new ValidationException("Already exists");
      } else {
         VirementAccountPK acctPK = new VirementAccountPK(this.getVirementCategoryId(), sePK.getStructureId(), sePK.getStructureElementId());
         VirementAccountImpl acct = new VirementAccountImpl(acctPK, visId, description, tranLimit, totalLimitIn, totalLimitOut, outAllowed, inAllowed);
         this.mModel.add(acct);
         this.setContentModified();
      }
   }

   public List<TreeNode> searchTree(String visId) {
      EntityList elements = this.getConnection().getListHelper().doElementPickerSearch(this.getAccountDimensionId(), visId);
      return SwingUtils.locateNodesInTree(this.getAccountTree(), elements);
   }

   public List<TreeNode> queryAccountTreeNodes() {
      ArrayList input = new ArrayList();
      Iterator nodes = this.getAccounts().iterator();

      while(nodes.hasNext()) {
         Object o = nodes.next();
         input.add((StructureElementKey)this.coerceElement(o));
      }

      EntityList nodes1 = this.getConnection().getHierarchysProcess().queryPathToRoots(input);
      return SwingUtils.locateNodesInTree(this.getAccountTree(), nodes1);
   }

   private Object coerceElement(Object o) {
      if(o instanceof VirementAccountImpl) {
         o = ((VirementAccountImpl)o).getStructureElementKey();
      }

      return o;
   }

   private int getAccountDimensionId() {
      ModelRefImpl modelRef = (ModelRefImpl)this.getEditor().getVirementCategory().getModelRef();
      EntityList dims = this.getConnection().getListHelper().getModelDimensions(modelRef.getModelPK().getModelId());
      DimensionRefImpl dimensionRef = (DimensionRefImpl)dims.getValueAt(dims.getNumRows() - 2, "Dimension");
      return dimensionRef.getDimensionPK().getDimensionId();
   }

   private int getVirementCategoryId() {
      VirementCategoryCK ck = (VirementCategoryCK)this.getEditor().getVirementCategory().getPrimaryKey();
      return ck == null?-1:ck.getVirementCategoryPK().getVirementCategoryId();
   }

   protected void undoModifications() throws CPException {}

   protected void saveModifications() throws ValidationException {
      this.getEditor().setAccounts(this.mModel);
   }

   private VirementCategoryEditorImpl getEditor() {
      return (VirementCategoryEditorImpl)this.getOwner();
   }
}
