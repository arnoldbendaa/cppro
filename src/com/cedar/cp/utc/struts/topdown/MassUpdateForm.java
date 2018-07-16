// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.topdown;

import com.cedar.cp.utc.common.CPForm;
import com.cedar.cp.utc.struts.topdown.MassUpdateDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

public class MassUpdateForm extends CPForm {

   private int mTaskId;
   private String mPassedAction;
   private int mDeleteId;
   private List mUnitSelection;
   private List mUnitSelectionLabel;
   private List mChangeOptions;
   private MassUpdateDTO mMassDTO;
   private boolean mSelectedCell;


   public int getTaskId() {
      return this.mTaskId;
   }

   public void setTaskId(int taskId) {
      this.mTaskId = taskId;
   }

   public String getPassedAction() {
      return this.mPassedAction;
   }

   public void setPassedAction(String passedAction) {
      this.mPassedAction = passedAction;
   }

   public int getDeleteId() {
      return this.mDeleteId;
   }

   public void setDeleteId(int deleteId) {
      this.mDeleteId = deleteId;
   }

   public List getUnitSelection() {
      return this.mUnitSelection;
   }

   public void setUnitSelection(List unitSelection) {
      this.mUnitSelection = unitSelection;
   }

   public List getUnitSelectionLabel() {
      return this.mUnitSelectionLabel;
   }

   public void setUnitSelectionLabel(List unitSelectionLabel) {
      this.mUnitSelectionLabel = unitSelectionLabel;
   }

   public List getChangeOptions() {
      return this.mChangeOptions;
   }

   public void setChangeOptions(List changeOptions) {
      this.mChangeOptions = changeOptions;
   }

   public MassUpdateDTO getMassDTO() {
      return this.mMassDTO;
   }

   public void setMassDTO(MassUpdateDTO massDTO) {
      this.mMassDTO = massDTO;
   }

   public boolean isSelectedCell() {
      return this.mSelectedCell;
   }

   public void setSelectedCell(boolean selectedCell) {
      this.mSelectedCell = selectedCell;
   }

   public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
      ActionErrors errors = new ActionErrors();
      this.checkForEmpty("modelVisId", "modelVisId", this.getMassDTO().getModelVisId(), errors);
      this.checkForEmpty("financeCubeVisId", "financeCubeVisId", this.getMassDTO().getFinanceCubeVisId(), errors);
      this.checkForEmpty("reason", "reason", this.getMassDTO().getReason(), errors);
      this.checkForEmpty("reference", "reference", this.getMassDTO().getReference(), errors);
      MassUpdateDTO dto = (MassUpdateDTO)httpServletRequest.getSession().getAttribute("massUpdate");
      if(dto != null) {
         this.checkForEmpty("changeCells", "changeCells", dto.getChangeCells(), errors);
      } else {
         this.checkForEmpty("changeCells", "changeCells", dto, errors);
      }

      this.checkForEmpty("dataTypeVisId", "dataTypeVisId", this.getMassDTO().getDataTypeVisId(), errors);
      if(this.getMassDTO().getChangePercent() != null && this.getMassDTO().getChangePercent().length() > 0) {
         this.checkForNumber("changePercent", "changePercent", this.getMassDTO().getChangePercent(), errors, 1);
      }

      if(this.getMassDTO().getChangeTo() != null && this.getMassDTO().getChangeTo().length() > 0) {
         this.checkForNumber("changeTo", "changeTo", this.getMassDTO().getChangeTo(), errors, 1);
      }

      if(this.getMassDTO().getChangeBy() != null && this.getMassDTO().getChangeBy().length() > 0) {
         this.checkForNumber("changeBy", "changeBy", this.getMassDTO().getChangeBy(), errors, 1);
      }

      if(errors.size() > 0 && dto != null) {
         this.getMassDTO().setDimensionHeader(dto.getDimensionHeader());
         this.getMassDTO().setHoldCells(dto.getHoldCells());
         this.getMassDTO().setChangeCells(dto.getChangeCells());
      }

      return errors;
   }

   public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
      this.setMassDTO(new MassUpdateDTO());
      ArrayList units = new ArrayList();
      ArrayList unitsLabel = new ArrayList();

      for(int changeType = -2; changeType < 5; ++changeType) {
         BigDecimal label = BigDecimal.ONE;
         label = label.movePointRight(changeType);
         units.add(Integer.valueOf(changeType));
         unitsLabel.add(label);
      }

      this.setUnitSelection(units);
      this.setUnitSelectionLabel(unitsLabel);
      ArrayList var7 = new ArrayList(2);
      var7.add("Value");
      var7.add("Percentage");
      this.setChangeOptions(var7);
      this.setSelectedCell(false);
      this.setPassedAction("");
      this.setDeleteId(0);
   }
}
