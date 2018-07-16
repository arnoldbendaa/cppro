// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.currency.CurrencyRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.Model;
import com.cedar.cp.dto.model.ModelImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.ModelEditorSessionImpl;
import java.util.List;
import java.util.Map;

public class ModelAdapter implements Model {

   private ModelImpl mEditorData;
   private ModelEditorSessionImpl mEditorSessionImpl;


   public ModelAdapter(ModelEditorSessionImpl e, ModelImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected ModelEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected ModelImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(ModelPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public boolean isCurrencyInUse() {
      return this.mEditorData.isCurrencyInUse();
   }

   public boolean isLocked() {
      return this.mEditorData.isLocked();
   }

   public boolean isVirementEntryEnabled() {
      return this.mEditorData.isVirementEntryEnabled();
   }

   public CurrencyRef getCurrencyRef() {
      if(this.mEditorData.getCurrencyRef() != null) {
         try {
            CurrencyRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getCurrencyEntityRef(this.mEditorData.getCurrencyRef());
            this.mEditorData.setCurrencyRef(e);
            return e;
         } catch (Exception var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2.getMessage());
         }
      } else {
         return null;
      }
   }

   public DimensionRef getAccountRef() {
      if(this.mEditorData.getAccountRef() != null) {
         try {
            DimensionRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getDimensionEntityRef(this.mEditorData.getAccountRef());
            this.mEditorData.setAccountRef(e);
            return e;
         } catch (Exception var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2.getMessage());
         }
      } else {
         return null;
      }
   }

   public DimensionRef getCalendarRef() {
      if(this.mEditorData.getCalendarRef() != null) {
         try {
            DimensionRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getDimensionEntityRef(this.mEditorData.getCalendarRef());
            this.mEditorData.setCalendarRef(e);
            return e;
         } catch (Exception var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2.getMessage());
         }
      } else {
         return null;
      }
   }

   public HierarchyRef getBudgetHierarchyRef() {
      if(this.mEditorData.getBudgetHierarchyRef() != null) {
         try {
            HierarchyRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getHierarchyEntityRef(this.mEditorData.getBudgetHierarchyRef());
            this.mEditorData.setBudgetHierarchyRef(e);
            return e;
         } catch (Exception var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2.getMessage());
         }
      } else {
         return null;
      }
   }

   public void setCurrencyRef(CurrencyRef ref) {
      this.mEditorData.setCurrencyRef(ref);
   }

   public void setAccountRef(DimensionRef ref) {
      this.mEditorData.setAccountRef(ref);
   }

   public void setCalendarRef(DimensionRef ref) {
      this.mEditorData.setCalendarRef(ref);
   }

   public void setBudgetHierarchyRef(HierarchyRef ref) {
      this.mEditorData.setBudgetHierarchyRef(ref);
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setCurrencyInUse(boolean p) {
      this.mEditorData.setCurrencyInUse(p);
   }

   public void setLocked(boolean p) {
      this.mEditorData.setLocked(p);
   }

   public void setVirementEntryEnabled(boolean p) {
      this.mEditorData.setVirementEntryEnabled(p);
   }

   public void addSelectedDimensionRef(EntityRef businessDimensionRef) {
      this.mEditorData.addSelectedDimensionRef(businessDimensionRef);
   }

   public void removeSelectedDimensionRef(EntityRef businessDimensionRef) {
      this.mEditorData.removeSelectedDimensionRef(businessDimensionRef);
   }

   public List getSelectedDimensionRefs() {
      return this.mEditorData.getSelectedDimensionRefs();
   }

   public boolean isVirementsInUse() {
      return this.mEditorData.isVirementsInUse();
   }

   public Map<String, String> getProperties() {
      return this.mEditorData.getProperties();
   }
}
