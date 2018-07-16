// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.topdown;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.dataEntry.DataEntryProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.picker.ElementDTO;
import com.cedar.cp.utc.struts.topdown.MassUpdateDTO;
import com.cedar.cp.utc.struts.topdown.MassUpdateForm;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MassUpdateSetup extends CPAction {

   public static final String sFormSessionKey = "massUpdate";


   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      if(actionForm instanceof MassUpdateForm) {
         MassUpdateForm myForm = (MassUpdateForm)actionForm;
         String action = myForm.getPassedAction();
         if(action == null) {
            action = "";
         }

         if(!action.equals("new") && myForm.getTaskId() == 0) {
            MassUpdateDTO dto;
            if(action.equals("deleteChange")) {
               dto = (MassUpdateDTO)httpServletRequest.getSession().getAttribute("massUpdate");
               dto.getChangeCells().remove(myForm.getDeleteId());
               myForm.getMassDTO().setChangeCells(dto.getChangeCells());
               this.getCurrentValue(myForm, httpServletRequest, dto);
            } else if(action.equals("deleteHold")) {
               dto = (MassUpdateDTO)httpServletRequest.getSession().getAttribute("massUpdate");
               dto.getHoldCells().remove(myForm.getDeleteId());
               myForm.getMassDTO().setHoldCells(dto.getHoldCells());
               myForm.setMassDTO(dto);
               httpServletRequest.getSession().setAttribute("massUpdate", myForm.getMassDTO());
            } else {
               dto = (MassUpdateDTO)httpServletRequest.getSession().getAttribute("massUpdate");
               if(dto.getLastAction().equals("dsChangeCell")) {
                  this.getCurrentValue(myForm, httpServletRequest, dto, true);
               } else {
                  myForm.setMassDTO(dto);
                  httpServletRequest.getSession().setAttribute("massUpdate", myForm.getMassDTO());
               }
            }
         } else {
            httpServletRequest.getSession().removeAttribute("massUpdate");
            myForm.reset(actionMapping, httpServletRequest);
         }
      }

      return actionMapping.findForward("success");
   }

   private void getCurrentValue(MassUpdateForm myForm, HttpServletRequest httpServletRequest, MassUpdateDTO dto) throws Exception {
      this.getCurrentValue(myForm, httpServletRequest, dto, false);
   }

   private void getCurrentValue(MassUpdateForm myForm, HttpServletRequest httpServletRequest, MassUpdateDTO dto, boolean setDefaultDataType) throws Exception {
      CPConnection con = this.getCPContext(httpServletRequest).getCPConnection();
      DataEntryProcess process = con.getDataEntryProcess();
      List col = dto.getChangeCells();
      ArrayList cellKeys = new ArrayList();
      if(col != null && col.size() > 0) {
         int row_size = 0;
         int col_size = col.size();
         int[] structure_ids = null;

         List row;
         ElementDTO elem;
         int idx;
         for(int celValues = 0; celValues < col_size; ++celValues) {
            row = (List)col.get(celValues);
            row_size = row.size();
            String[] totalVal = new String[row_size + 1];
            structure_ids = new int[row_size - 1];
            totalVal[0] = "M";

            for(idx = 0; idx < row_size - 1; ++idx) {
               elem = (ElementDTO)row.get(idx);
               structure_ids[idx] = elem.getStructureId();
               totalVal[idx + 1] = String.valueOf(elem.getId());
               if(setDefaultDataType && idx == row_size - 2) {
                  dto.setCalSeqId(idx);
                  dto.setCalId(elem.getId());
                  dto.setSrcCalId(elem.getId());
                  dto.setCalVisId(elem.getIdentifier());
               }
            }

            elem = (ElementDTO)row.get(row_size - 1);
            totalVal[row_size] = elem.getIdentifier();
            cellKeys.add(totalVal);
         }

         if(setDefaultDataType && dto.getDataTypeId() == 0) {
            row = (List)dto.getChangeCells().get(0);
            elem = (ElementDTO)row.get(row_size - 1);
            Boolean var19 = Boolean.valueOf(false);
            EntityList var21 = con.getListHelper().getPickerDataTypesWeb(dto.getFinanceCubeId(), new int[]{0}, true);

            for(int rowValue = 0; rowValue < var21.getNumRows(); ++rowValue) {
               Short var20 = (Short)var21.getValueAt(rowValue, "DataTypeId");
               if(var20.intValue() == elem.getId()) {
                  var19 = Boolean.valueOf(true);
                  break;
               }
            }

            if(var19.booleanValue()) {
               dto.setDataTypeId(elem.getId());
               dto.setDataTypeVisId(elem.getIdentifier());
            }
         }

         EntityList var18 = process.getCellValues(dto.getFinanceCubeId(), structure_ids.length, structure_ids, cellKeys);
         if(var18.getNumRows() > 0) {
            BigDecimal var22 = new BigDecimal(0);
            var22 = var22.setScale(2);

            for(idx = 0; idx < var18.getNumRows(); ++idx) {
               BigDecimal var23 = new BigDecimal(var18.getValueAt(idx, "VAL").toString());
               var22 = var22.add(var23);
            }

            dto.setCurrentValue(var22.toString());
            dto.setCurrentValueForDisplay(var22.toString());
         } else {
            dto.setCurrentValue("0");
         }

         myForm.setSelectedCell(true);
      } else {
         myForm.setSelectedCell(false);
      }

      myForm.setMassDTO(dto);
      httpServletRequest.getSession().setAttribute("massUpdate", myForm.getMassDTO());
   }
}
