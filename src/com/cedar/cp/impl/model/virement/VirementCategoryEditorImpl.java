// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.virement;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementAccount;
import com.cedar.cp.api.model.virement.VirementAccountEditor;
import com.cedar.cp.api.model.virement.VirementAccountsEditor;
import com.cedar.cp.api.model.virement.VirementCategory;
import com.cedar.cp.api.model.virement.VirementCategoryEditor;
import com.cedar.cp.api.model.virement.VirementLocation;
import com.cedar.cp.api.model.virement.VirementLocationsEditor;
import com.cedar.cp.dto.dimension.HierarchyRefImpl;
import com.cedar.cp.dto.dimension.StructureElementNodeImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.ModelDimensionRelRefImpl;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.virement.VirementAccountImpl;
import com.cedar.cp.dto.model.virement.VirementAccountPK;
import com.cedar.cp.dto.model.virement.VirementCategoryEditorSessionSSO;
import com.cedar.cp.dto.model.virement.VirementCategoryImpl;
import com.cedar.cp.dto.model.virement.VirementCategoryPK;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.model.virement.VirementAccountEditorImpl;
import com.cedar.cp.impl.model.virement.VirementAccountsEditorImpl;
import com.cedar.cp.impl.model.virement.VirementCategoryAdapter;
import com.cedar.cp.impl.model.virement.VirementCategoryEditorSessionImpl;
import com.cedar.cp.impl.model.virement.VirementLocationsEditorImpl;
import com.cedar.cp.util.OnDemandMutableTreeNode;
import com.cedar.cp.util.StringUtils;
import com.cedar.cp.util.awt.QListModel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class VirementCategoryEditorImpl extends BusinessEditorImpl implements VirementCategoryEditor, SubBusinessEditorOwner {

   private transient TreeModel mAccountTreeModel;
   private transient TreeModel mResponsibilityAreaTreeModel;
   private VirementAccountsEditorImpl mAccountsEditor;
   private VirementLocationsEditor mLocationsEditor;
   private Map mAccountEditors;
   private VirementCategoryEditorSessionSSO mServerSessionData;
   private VirementCategoryImpl mEditorData;
   private VirementCategoryAdapter mEditorDataAdapter;


   public VirementCategoryEditorImpl(VirementCategoryEditorSessionImpl session, VirementCategoryEditorSessionSSO serverSessionData, VirementCategoryImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
      this.mAccountEditors = new HashMap();
   }

   public void updateEditorData(VirementCategoryEditorSessionSSO serverSessionData, VirementCategoryImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setTranLimit(long newTranLimit) throws ValidationException {
      this.validateTranLimit(newTranLimit);
      if(this.mEditorData.getTranLimit() != newTranLimit) {
         this.setContentModified();
         this.mEditorData.setTranLimit(newTranLimit);
      }
   }

   public void setTotalLimitIn(long newTotalLimitIn) throws ValidationException {
      this.validateTotalLimitIn(newTotalLimitIn);
      if(this.mEditorData.getTotalLimitIn() != newTotalLimitIn) {
         this.setContentModified();
         this.mEditorData.setTotalLimitIn(newTotalLimitIn);
      }
   }

   public void setTotalLimitOut(long newTotalLimitOut) throws ValidationException {
      this.validateTotalLimitOut(newTotalLimitOut);
      if(this.mEditorData.getTotalLimitOut() != newTotalLimitOut) {
         this.setContentModified();
         this.mEditorData.setTotalLimitOut(newTotalLimitOut);
      }
   }

   public void setVisId(String newVisId) throws ValidationException {
      if(newVisId != null) {
         newVisId = StringUtils.rtrim(newVisId);
      }

      this.validateVisId(newVisId);
      if(this.mEditorData.getVisId() == null || !this.mEditorData.getVisId().equals(newVisId)) {
         this.setContentModified();
         this.mEditorData.setVisId(newVisId);
      }
   }

   public void setDescription(String newDescription) throws ValidationException {
      if(newDescription != null) {
         newDescription = StringUtils.rtrim(newDescription);
      }

      this.validateDescription(newDescription);
      if(this.mEditorData.getDescription() == null || !this.mEditorData.getDescription().equals(newDescription)) {
         this.setContentModified();
         this.mEditorData.setDescription(newDescription);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a VirementCategory");
      } else if(newVisId == null || newVisId.trim().length() == 0) {
         throw new ValidationException("An identifier must be supplied");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a VirementCategory");
      } else if(newDescription == null || newDescription.trim().length() == 0) {
         throw new ValidationException("An description must be supplied");
      }
   }

   public void validateTranLimit(long newTranLimit) throws ValidationException {
      if(newTranLimit < 0L) {
         throw new ValidationException("Transaction limit must be greater than or equal to zero.");
      }
   }

   public void validateTotalLimitIn(long newTotalLimitIn) throws ValidationException {
      if(newTotalLimitIn < 0L) {
         throw new ValidationException("Total limit in must be greater than or equal to zero.");
      }
   }

   public void validateTotalLimitOut(long newTotalLimitOut) throws ValidationException {
      if(newTotalLimitOut < 0L) {
         throw new ValidationException("Total limit out must be greater than or equal to zero.");
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
      return ((VirementCategoryEditorSessionImpl)this.getBusinessSession()).getOwnershipRefs();
   }

   public VirementCategory getVirementCategory() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new VirementCategoryAdapter((VirementCategoryEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {
      this.validateVisId(this.getVirementCategory().getVisId());
      this.validateDescription(this.getVirementCategory().getDescription());
   }

   public TreeModel getAccountTree() {
      if(this.mAccountTreeModel == null) {
         this.loadTreeRoots();
      }

      return this.mAccountTreeModel;
   }

   public TreeModel getResponsibilityAreaTree() {
      if(this.mResponsibilityAreaTreeModel == null) {
         this.loadTreeRoots();
      }

      return this.mResponsibilityAreaTreeModel;
   }

   private void loadTreeRoots() {
      if(this.mEditorData != null && this.mEditorData.getModelRef() != null) {
         ModelRefImpl modelRef = (ModelRefImpl)this.mEditorData.getModelRef();
         int modelId = modelRef.getModelPK().getModelId();
         EntityList roots = this.getConnection().getListHelper().getAllRootsForModel(modelRef.getModelPK().getModelId());
         int busDimId = -1;

         for(int i = 0; i < roots.getNumRows(); ++i) {
            HierarchyRefImpl hierRef = (HierarchyRefImpl)roots.getValueAt(i, "Hierarchy");
            ModelDimensionRelRefImpl mdrRef = (ModelDimensionRelRefImpl)roots.getValueAt(i, "ModelDimensionRel");
            int hierarchyId = ((Integer)roots.getValueAt(i, "BudgetHierarchyId")).intValue();
            int type = ((Integer)roots.getValueAt(i, "Type")).intValue();
            if(this.mResponsibilityAreaTreeModel == null && type == 2 && hierRef.getHierarchyPK().getHierarchyId() == hierarchyId) {
               this.mResponsibilityAreaTreeModel = new DefaultTreeModel(new OnDemandMutableTreeNode(new StructureElementNodeImpl(this.getConnection(), roots.getRowData(i)), "com.cedar.cp.impl.dimension.StructureElementProxyNode"));
            }

            if(busDimId == -1) {
               busDimId = mdrRef.getModelDimensionRelPK().getDimensionId();
            } else if(this.mAccountTreeModel == null && type == 1 && busDimId != mdrRef.getModelDimensionRelPK().getDimensionId()) {
               this.mAccountTreeModel = new DefaultTreeModel(new OnDemandMutableTreeNode(new StructureElementNodeImpl(this.getConnection(), roots.getRowData(i)), "com.cedar.cp.impl.dimension.StructureElementProxyNode"));
               break;
            }
         }

      }
   }

   public void addResponsibilityArea(Object structureElementPK, String visId, String description) throws ValidationException {}

   public void removeResponsinbilityArea(VirementLocation location) throws ValidationException {
      this.mEditorData.removeResponsibilityArea(location);
      this.setContentModified();
   }

   public void removeAccount(VirementAccount account) throws ValidationException {
      this.mEditorData.removeVirementAccount(account);
      this.setContentModified();
   }

   public VirementLocationsEditor getLocationEditor() {
      if(this.mLocationsEditor != null) {
         throw new IllegalStateException("Only one location editor allowed");
      } else {
         this.mLocationsEditor = new VirementLocationsEditorImpl(this.getBusinessSession(), this);
         return this.mLocationsEditor;
      }
   }

   private int getVirementCategoryId() {
      return this.mEditorData.getPrimaryKey() != null?((VirementCategoryPK)this.mEditorData.getPrimaryKey()).getVirementCategoryId():-1;
   }

   public VirementAccountsEditor getAccountsEditor() {
      if(this.mAccountsEditor == null) {
         this.mAccountsEditor = new VirementAccountsEditorImpl(this.getBusinessSession(), this);
      }

      return this.mAccountsEditor;
   }

   public VirementAccountEditor getAccountEditor(Object structureElementKey, String visId, String description) throws ValidationException {
      if(structureElementKey == null) {
         throw new ValidationException("A structure element key must be supplied");
      } else {
         VirementAccountEditorImpl editor = this.findAccountEditor(structureElementKey);
         if(editor == null) {
            VirementCategoryImpl vc = this.mEditorData;
            StructureElementPK sePK = (StructureElementPK)structureElementKey;
            VirementAccountImpl va = vc.findVirementAccount(sePK.getStructureElementId());
            if(va == null) {
               VirementAccountPK pk = new VirementAccountPK(this.getVirementCategoryId(), sePK.getStructureId(), sePK.getStructureElementId());
               va = new VirementAccountImpl(pk, visId, description, 0L, 0L, 0L, true, true);
            }

            editor = new VirementAccountEditorImpl(this.getBusinessSession(), this, va);
            this.mAccountEditors.put(structureElementKey, editor);
         }

         return editor;
      }
   }

   public void removeSubBusinessEditor(SubBusinessEditor editor) throws CPException {
      if(editor instanceof VirementLocationsEditor && this.mLocationsEditor == editor) {
         this.mLocationsEditor = null;
      } else if(editor instanceof VirementAccountsEditor && editor == this.mAccountsEditor) {
         this.mAccountsEditor = null;
      } else {
         if(!(editor instanceof VirementAccountEditor)) {
            throw new CPException("Unknown editor:" + editor);
         }

         if(this.removeAccountEditor((VirementAccountEditorImpl)editor) == null) {
            throw new CPException("Unknown editor:" + editor);
         }
      }

   }

   public void setResponsibilityAreas(QListModel respAreas) {
      this.mEditorData.setResponisbilityAreaList(respAreas.getList());
   }

   void update(VirementAccountImpl virementAccount) {
      Iterator i = this.mEditorData.getAccounts().iterator();

      VirementAccountImpl vaImpl;
      do {
         if(!i.hasNext()) {
            this.mEditorData.getAccounts().add(virementAccount);
            return;
         }

         vaImpl = (VirementAccountImpl)i.next();
      } while(!vaImpl.getKey().equals(virementAccount.getKey()));

      vaImpl.setAll(virementAccount);
   }

   void setAccounts(QListModel accounts) {
      this.mEditorData.setAccountAreaList(accounts.getList());
      this.setContentModified();
   }

   private VirementAccountEditorImpl findAccountEditor(Object structureElementKey) {
      return (VirementAccountEditorImpl)this.mAccountEditors.get(structureElementKey);
   }

   private VirementAccountEditorImpl removeAccountEditor(VirementAccountEditorImpl editor) {
      if(this.mAccountEditors.containsValue(editor)) {
         this.mAccountEditors.remove(editor);
         return editor;
      } else {
         return null;
      }
   }

   public void setTranLimit(double newTranLimit) throws ValidationException {
      if(this.mEditorData.getTranLimitAsDouble() != newTranLimit) {
         this.setContentModified();
         this.mEditorData.setTranLimit(newTranLimit);
      }
   }

   public void setTotalLimitIn(double newTotalLimit) throws ValidationException {
      if(this.mEditorData.getTotalLimitInAsDouble() != newTotalLimit) {
         this.setContentModified();
         this.mEditorData.setTotalLimitIn(newTotalLimit);
      }
   }

   public void setTotalLimitOut(double newTotalLimit) throws ValidationException {
      if(this.mEditorData.getTotalLimitOutAsDouble() != newTotalLimit) {
         this.setContentModified();
         this.mEditorData.setTotalLimitOut(newTotalLimit);
      }
   }
}
