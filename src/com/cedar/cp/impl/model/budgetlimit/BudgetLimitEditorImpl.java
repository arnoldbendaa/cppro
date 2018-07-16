// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.budgetlimit;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.budgetlimit.BudgetLimit;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitEditor;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitEditorSessionSSO;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.model.budgetlimit.BudgetLimitAdapter;
import com.cedar.cp.impl.model.budgetlimit.BudgetLimitEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

public class BudgetLimitEditorImpl extends BusinessEditorImpl implements BudgetLimitEditor {

   private BudgetLimitEditorSessionSSO mServerSessionData;
   private BudgetLimitImpl mEditorData;
   private BudgetLimitAdapter mEditorDataAdapter;


   public BudgetLimitEditorImpl(BudgetLimitEditorSessionImpl session, BudgetLimitEditorSessionSSO serverSessionData, BudgetLimitImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(BudgetLimitEditorSessionSSO serverSessionData, BudgetLimitImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setBudgetLocationElementId(int newBudgetLocationElementId) throws ValidationException {
      this.validateBudgetLocationElementId(newBudgetLocationElementId);
      if(this.mEditorData.getBudgetLocationElementId() != newBudgetLocationElementId) {
         this.setContentModified();
         this.mEditorData.setBudgetLocationElementId(newBudgetLocationElementId);
      }
   }

   public void setDim0(int newDim0) throws ValidationException {
      this.validateDim0(newDim0);
      if(this.mEditorData.getDim0() != newDim0) {
         this.setContentModified();
         this.mEditorData.setDim0(newDim0);
      }
   }

   public void setDim1(int newDim1) throws ValidationException {
      this.validateDim1(newDim1);
      if(this.mEditorData.getDim1() != newDim1) {
         this.setContentModified();
         this.mEditorData.setDim1(newDim1);
      }
   }

   public void setDim2(int newDim2) throws ValidationException {
      this.validateDim2(newDim2);
      if(this.mEditorData.getDim2() != newDim2) {
         this.setContentModified();
         this.mEditorData.setDim2(newDim2);
      }
   }

   public void setDim3(int newDim3) throws ValidationException {
      this.validateDim3(newDim3);
      if(this.mEditorData.getDim3() != newDim3) {
         this.setContentModified();
         this.mEditorData.setDim3(newDim3);
      }
   }

   public void setDim4(int newDim4) throws ValidationException {
      this.validateDim4(newDim4);
      if(this.mEditorData.getDim4() != newDim4) {
         this.setContentModified();
         this.mEditorData.setDim4(newDim4);
      }
   }

   public void setDim5(int newDim5) throws ValidationException {
      this.validateDim5(newDim5);
      if(this.mEditorData.getDim5() != newDim5) {
         this.setContentModified();
         this.mEditorData.setDim5(newDim5);
      }
   }

   public void setDim6(int newDim6) throws ValidationException {
      this.validateDim6(newDim6);
      if(this.mEditorData.getDim6() != newDim6) {
         this.setContentModified();
         this.mEditorData.setDim6(newDim6);
      }
   }

   public void setDim7(int newDim7) throws ValidationException {
      this.validateDim7(newDim7);
      if(this.mEditorData.getDim7() != newDim7) {
         this.setContentModified();
         this.mEditorData.setDim7(newDim7);
      }
   }

   public void setDim8(int newDim8) throws ValidationException {
      this.validateDim8(newDim8);
      if(this.mEditorData.getDim8() != newDim8) {
         this.setContentModified();
         this.mEditorData.setDim8(newDim8);
      }
   }

   public void setDim9(int newDim9) throws ValidationException {
      this.validateDim9(newDim9);
      if(this.mEditorData.getDim9() != newDim9) {
         this.setContentModified();
         this.mEditorData.setDim9(newDim9);
      }
   }

   public void setDataType(String newDataType) throws ValidationException {
      if(newDataType != null) {
         newDataType = StringUtils.rtrim(newDataType);
      }

      this.validateDataType(newDataType);
      if(this.mEditorData.getDataType() == null || !this.mEditorData.getDataType().equals(newDataType)) {
         this.setContentModified();
         this.mEditorData.setDataType(newDataType);
      }
   }

   public void setMinValue(Long newMinValue) throws ValidationException {
      this.validateMinValue(newMinValue);
      if(this.mEditorData.getMinValue() == null || !this.mEditorData.getMinValue().equals(newMinValue)) {
         this.setContentModified();
         this.mEditorData.setMinValue(newMinValue);
      }
   }

   public void setMaxValue(Long newMaxValue) throws ValidationException {
      this.validateMaxValue(newMaxValue);
      if(this.mEditorData.getMaxValue() == null || !this.mEditorData.getMaxValue().equals(newMaxValue)) {
         this.setContentModified();
         this.mEditorData.setMaxValue(newMaxValue);
      }
   }

   public void validateBudgetLocationElementId(int newBudgetLocationElementId) throws ValidationException {}

   public void validateDim0(int newDim0) throws ValidationException {}

   public void validateDim1(int newDim1) throws ValidationException {}

   public void validateDim2(int newDim2) throws ValidationException {}

   public void validateDim3(int newDim3) throws ValidationException {}

   public void validateDim4(int newDim4) throws ValidationException {}

   public void validateDim5(int newDim5) throws ValidationException {}

   public void validateDim6(int newDim6) throws ValidationException {}

   public void validateDim7(int newDim7) throws ValidationException {}

   public void validateDim8(int newDim8) throws ValidationException {}

   public void validateDim9(int newDim9) throws ValidationException {}

   public void validateDataType(String newDataType) throws ValidationException {
      if(newDataType != null && newDataType.length() > 2) {
         throw new ValidationException("length (" + newDataType.length() + ") of DataType must not exceed 2 on a BudgetLimit");
      }
   }

   public void validateMinValue(Long newMinValue) throws ValidationException {}

   public void validateMaxValue(Long newMaxValue) throws ValidationException {}

   public void setFinanceCubeRef(FinanceCubeRef ref) throws ValidationException {
      FinanceCubeRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getFinanceCubeEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getFinanceCubeRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getFinanceCubeRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setFinanceCubeRef(actualRef);
      this.setContentModified();
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
      return ((BudgetLimitEditorSessionImpl)this.getBusinessSession()).getOwnershipRefs();
   }

   public BudgetLimit getBudgetLimit() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new BudgetLimitAdapter((BudgetLimitEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}

   public void setFinanceCubeRef(int id) throws ValidationException {
      FinanceCubePK pk = new FinanceCubePK(id);
      FinanceCubeRefImpl ref = new FinanceCubeRefImpl(pk, "");
      this.setFinanceCubeRef(ref);
   }
}
