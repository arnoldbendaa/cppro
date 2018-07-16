// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.topdown;

import com.cedar.cp.utc.struts.topdown.BudgetLimitForm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class BudgetLimitEditForm extends BudgetLimitForm {

   private String mSelectedAction;
   private int mDeleteId;
   private int mEditId;
   private String mRequiredDataType = "0";
   private boolean mWriteableDataTypes = true;
   private String mSelectedIds;
   private String mSelectedIdentifiers;
   private String mSelectedDescriptions;
   private String mMinValue;
   private String mMaxValue;
   private List mSelectedElements;


   public String getSelectedAction() {
      return this.mSelectedAction;
   }

   public void setSelectedAction(String selectedAction) {
      this.mSelectedAction = selectedAction;
   }

   public int getDeleteId() {
      return this.mDeleteId;
   }

   public void setDeleteId(int deleteId) {
      this.mDeleteId = deleteId;
   }

   public int getEditId() {
      return this.mEditId;
   }

   public void setEditId(int editId) {
      this.mEditId = editId;
   }

   public boolean isRequireDataType() {
      return this.mRequiredDataType != null && this.mRequiredDataType.trim().length() > 0;
   }

   public String getRequiredDataType() {
      return this.mRequiredDataType;
   }

   public void setRequiredDataType(String requiredDataType) {
      this.mRequiredDataType = requiredDataType;
   }

   public String getSelectedIds() {
      return this.mSelectedIds;
   }

   public void setSelectedIds(String selectedIds) {
      this.mSelectedIds = selectedIds;
   }

   public String getSelectedIdentifiers() {
      return this.mSelectedIdentifiers;
   }

   public void setSelectedIdentifiers(String selectedIdentifiers) {
      this.mSelectedIdentifiers = selectedIdentifiers;
   }

   public String getSelectedDescriptions() {
      return this.mSelectedDescriptions;
   }

   public void setSelectedDescriptions(String selectedDescriptions) {
      this.mSelectedDescriptions = selectedDescriptions;
   }

   public String getMinValue() {
      return this.mMinValue;
   }

   public double getMinDoubleValue() throws NumberFormatException {
      String s = this.getMinValue();
      if(s != null && s.length() > 0) {
         try {
            return Double.parseDouble(s);
         } catch (NumberFormatException var3) {
            throw var3;
         }
      } else {
         return 0.0D;
      }
   }

   public void setMinValue(Long minValue) {
      if(minValue != null) {
         Double d = this.scaleValueForDisplay(minValue);
         this.mMinValue = d.toString();
      } else {
         this.mMinValue = "";
      }

   }

   public void setMinValue(String minValue) {
      this.mMinValue = minValue;
   }

   public String getMaxValue() {
      return this.mMaxValue;
   }

   public double getMaxDoubleValue() throws NumberFormatException {
      String s = this.getMaxValue();
      if(s != null && s.length() > 0) {
         try {
            return Double.parseDouble(s);
         } catch (NumberFormatException var3) {
            throw var3;
         }
      } else {
         return 0.0D;
      }
   }

   public void setMaxValue(Long maxValue) {
      if(maxValue != null) {
         Double d = this.scaleValueForDisplay(maxValue);
         this.mMaxValue = d.toString();
      } else {
         this.mMaxValue = "";
      }

   }

   public void setMaxValue(String maxValue) {
      this.mMaxValue = maxValue;
   }

   public List getSelectedElements() {
      if(this.mSelectedElements == null) {
         this.mSelectedElements = Collections.EMPTY_LIST;
      }

      return this.mSelectedElements;
   }

   public void setSelectedElements(List selectedElements) {
      this.mSelectedElements = selectedElements;
   }

   public void addElement(String s) {
      if(this.mSelectedElements == null) {
         this.mSelectedElements = new ArrayList();
      }

      this.mSelectedElements.add(s);
   }

   public boolean isWriteableDataTypes() {
      return this.mWriteableDataTypes;
   }

   public void setWriteableDataTypes(boolean writeableDataTypes) {
      this.mWriteableDataTypes = writeableDataTypes;
   }

   public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
      ActionErrors errors = new ActionErrors();
      if(this.getSelectedIds() != null && this.getSelectedIds().length() > 0) {
         errors = this.validate();
      }

      if(this.getEditId() != 0 && !this.getSelectedAction().equals("editSetup")) {
         errors = this.validate();
      }

      if(errors.size() > 0) {
         this.setCPContextId(httpServletRequest);
      }

      return errors;
   }

   private ActionErrors validate() {
      ActionErrors errors = new ActionErrors();
      if((this.getMinValue() == null || this.getMinValue().equals("")) && (this.getMaxValue() == null || this.getMaxValue().equals(""))) {
         ActionMessage doCheck = new ActionMessage("cp.budgetlimit.edit.error1");
         errors.add("", doCheck);
      }

      boolean doCheck1 = true;
      ActionMessage error1;
      if(this.getMinValue() != null && this.getMinValue().length() > 0) {
         try {
            this.getMinDoubleValue();
         } catch (NumberFormatException var6) {
            doCheck1 = false;
            error1 = new ActionMessage("cp.budgetlimit.edit.error2");
            errors.add("", error1);
         }
      }

      if(this.getMaxValue() != null && this.getMaxValue().length() > 0) {
         try {
            this.getMaxDoubleValue();
         } catch (NumberFormatException var5) {
            doCheck1 = false;
            error1 = new ActionMessage("cp.budgetlimit.edit.error2");
            errors.add("", error1);
         }
      }

      if(doCheck1 && this.getMaxValue() != null && this.getMaxValue().length() > 0 && this.getMinValue() != null && this.getMinValue().length() > 0 && this.getMinDoubleValue() > this.getMaxDoubleValue()) {
         ActionMessage error = new ActionMessage("cp.budgetlimit.edit.min.error");
         errors.add("", error);
      }

      return errors;
   }
}
