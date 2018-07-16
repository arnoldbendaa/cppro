// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.currency.CurrencyRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.Model;
import com.cedar.cp.api.model.ModelEditor;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.model.ModelEditorSessionSSO;
import com.cedar.cp.dto.model.ModelImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.model.ModelAdapter;
import com.cedar.cp.impl.model.ModelEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.util.Iterator;
import java.util.List;

public class ModelEditorImpl extends BusinessEditorImpl implements ModelEditor {

   private ModelEditorSessionSSO mServerSessionData;
   private ModelImpl mEditorData;
   private ModelAdapter mEditorDataAdapter;


   public ModelEditorImpl(ModelEditorSessionImpl session, ModelEditorSessionSSO serverSessionData, ModelImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(ModelEditorSessionSSO serverSessionData, ModelImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setCurrencyInUse(boolean newCurrencyInUse) throws ValidationException {
      this.validateCurrencyInUse(newCurrencyInUse);
      if(this.mEditorData.isCurrencyInUse() != newCurrencyInUse) {
         this.setContentModified();
         this.mEditorData.setCurrencyInUse(newCurrencyInUse);
      }
   }

   public void setLocked(boolean newLocked) throws ValidationException {
      this.validateLocked(newLocked);
      if(this.mEditorData.isLocked() != newLocked) {
         this.setContentModified();
         this.mEditorData.setLocked(newLocked);
      }
   }

   public void setVirementEntryEnabled(boolean newVirementEntryEnabled) throws ValidationException {
      this.validateVirementEntryEnabled(newVirementEntryEnabled);
      if(this.mEditorData.isVirementEntryEnabled() != newVirementEntryEnabled) {
         this.setContentModified();
         this.mEditorData.setVirementEntryEnabled(newVirementEntryEnabled);
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
      if(newVisId != null && newVisId.length() > 50) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 50 on a Model");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a Model");
      }
   }

   public void validateCurrencyInUse(boolean newCurrencyInUse) throws ValidationException {}

   public void validateLocked(boolean newLocked) throws ValidationException {}

   public void validateVirementEntryEnabled(boolean newVirementEntryEnabled) throws ValidationException {}

   public void setCurrencyRef(CurrencyRef ref) throws ValidationException {
      CurrencyRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getCurrencyEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getCurrencyRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getCurrencyRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setCurrencyRef(actualRef);
      this.setContentModified();
   }

   public void setAccountRef(DimensionRef ref) throws ValidationException {
      DimensionRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getDimensionEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getAccountRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getAccountRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setAccountRef(actualRef);
      this.setContentModified();
   }

   public void setCalendarRef(DimensionRef ref) throws ValidationException {
      DimensionRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getDimensionEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getCalendarRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getCalendarRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setCalendarRef(actualRef);
      this.setContentModified();
   }

   public void setBudgetHierarchyRef(HierarchyRef ref) throws ValidationException {
      HierarchyRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getHierarchyEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getBudgetHierarchyRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getBudgetHierarchyRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setBudgetHierarchyRef(actualRef);
      this.setContentModified();
   }

   public Model getModel() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new ModelAdapter((ModelEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
      if(this.mEditorData.getSelectedDimensionRefs().size() > 0) {
         HierarchyRef hRef = this.mEditorData.getBudgetHierarchyRef();
         HierarchyCK hCK = (HierarchyCK)hRef.getPrimaryKey();
         DimensionRef dRef = null;
         List dims = this.mEditorData.getSelectedDimensionRefs();
         Iterator dIter = dims.iterator();

         while(dIter.hasNext()) {
            DimensionRef dimensionRef = (DimensionRef)dIter.next();
            DimensionPK dPK = (DimensionPK)dimensionRef.getPrimaryKey();
            if(hCK.getDimensionPK().equals(dPK)) {
               dRef = dimensionRef;
               break;
            }
         }

         if(dRef == null) {
            throw new CPException("Failed to locate owning dimension for " + hRef);
         }

         this.mEditorData.getSelectedDimensionRefs().remove(dRef);
         this.mEditorData.getSelectedDimensionRefs().add(0, dRef);
      }

   }

   private void saveValidation() throws ValidationException {
      if(this.mEditorData.getAccountRef() == null || this.mEditorData.getCalendarRef() == null || this.mEditorData.getSelectedDimensionRefs().size() == 0) {
         throw new ValidationException("An account dimension, calendar dimension and one or more business dimensions must be selected");
      }
   }

   public void addSelectedDimensionRef(EntityRef businessDimensionRef) {
      this.mEditorData.addSelectedDimensionRef(businessDimensionRef);
      this.setContentModified();
   }

   public boolean isDimensionRefRemovable(EntityRef businessDimensionRef) {
      return this.mServerSessionData.isBusinessDimensionAmendable(businessDimensionRef);
   }

   public void removeSelectedDimensionRef(EntityRef businessDimensionRef) {
      this.mEditorData.removeSelectedDimensionRef(businessDimensionRef);
      this.setContentModified();
   }

   public boolean isAccountRefEditable() {
      return this.mServerSessionData.isAccountAmendable();
   }

   public boolean isCalendarRefEditable() {
      return this.mServerSessionData.isCalendarAmendable();
   }

   public boolean isBudgetHierarchyRefEditable() {
      return this.mServerSessionData.isBudgetHierarchyAmendable();
   }

   public void setProperty(String name, String value) {
      if(value == null) {
         if(this.mEditorData.getProperties().remove(name) != null) {
            this.setContentModified();
         }
      } else {
         String oldValue = (String)this.mEditorData.getProperties().get(name);
         this.mEditorData.getProperties().put(name, value);
         if(oldValue == null || !oldValue.equals(value)) {
            this.setContentModified();
         }
      }

   }
}
