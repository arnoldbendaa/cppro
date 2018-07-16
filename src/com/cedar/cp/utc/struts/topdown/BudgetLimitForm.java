// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.topdown;

import com.cedar.cp.utc.common.CPForm;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

public class BudgetLimitForm extends CPForm {

   private int mModelId;
   private String mModelVisId;
   private String mModelDescription;
   private int mFinanceCubeId;
   private String mFinanceCubeVisId;
   private String mFinanceCubeDescription;
   private int mSelectedRA;
   private List mRespAreaStructureElement;
   private List mRespAreaDisplay;
   private List mImposedLimits;
   private List mSetlimits;
   private List mHeadings;
   private int mNoOfDims;


   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public String getModelVisId() {
      return this.mModelVisId;
   }

   public void setModelVisId(String modelVisId) {
      this.mModelVisId = modelVisId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.mFinanceCubeId = financeCubeId;
   }

   public String getFinanceCubeVisId() {
      return this.mFinanceCubeVisId;
   }

   public void setFinanceCubeVisId(String financeCubeVisId) {
      this.mFinanceCubeVisId = financeCubeVisId;
   }

   public int getSelectedRA() {
      return this.mSelectedRA;
   }

   public void setSelectedRA(int selectedRA) {
      this.mSelectedRA = selectedRA;
   }

   public List getRespAreaStructureElement() {
      return this.mRespAreaStructureElement;
   }

   public void setRespAreaStructureElement(List respAreaStructureElement) {
      this.mRespAreaStructureElement = respAreaStructureElement;
   }

   public List getRespAreaDisplay() {
      return this.mRespAreaDisplay;
   }

   public void setRespAreaDisplay(List respAreaDisplay) {
      this.mRespAreaDisplay = respAreaDisplay;
   }

   public List getImposedLimits() {
      return this.mImposedLimits;
   }

   public void setImposedLimits(List imposedLimits) {
      this.mImposedLimits = imposedLimits;
   }

   public List getSetlimits() {
      return this.mSetlimits;
   }

   public void setSetlimits(List setlimits) {
      this.mSetlimits = setlimits;
   }

   public String getModelDescription() {
      return this.mModelDescription;
   }

   public void setModelDescription(String modelDescription) {
      this.mModelDescription = modelDescription;
   }

   public String getFinanceCubeDescription() {
      return this.mFinanceCubeDescription;
   }

   public void setFinanceCubeDescription(String financeCubeDescription) {
      this.mFinanceCubeDescription = financeCubeDescription;
   }

   public List getHeadings() {
      return this.mHeadings;
   }

   public void setHeadings(List headings) {
      this.mHeadings = headings;
   }

   public int getNoOfDims() {
      return this.mNoOfDims;
   }

   public void setNoOfDims(int noOfDims) {
      this.mNoOfDims = noOfDims;
   }

   public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
      if(this.getModelId() == 0) {
         this.setRespAreaStructureElement(Collections.EMPTY_LIST);
         this.setRespAreaDisplay(Collections.EMPTY_LIST);
      }

   }

   public String getModelDisplay() {
      if(this.getModelVisId() == null) {
         return null;
      } else {
         StringBuilder sb = new StringBuilder(this.getModelVisId());
         if(sb.length() > 0) {
            sb.append(" - ");
         }

         sb.append(this.getModelDescription());
         return sb.toString();
      }
   }

   public String getFinanceCubeDisplay() {
      if(this.getFinanceCubeVisId() == null) {
         return null;
      } else {
         StringBuilder sb = new StringBuilder(this.getFinanceCubeVisId());
         if(sb.length() > 0) {
            sb.append(" - ");
         }

         sb.append(this.getFinanceCubeDescription());
         return sb.toString();
      }
   }
}
