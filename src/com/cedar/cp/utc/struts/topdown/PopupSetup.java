// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.topdown;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.topdown.PopupDTO;
import com.cedar.cp.utc.struts.topdown.PopupForm;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PopupSetup extends CPAction {

   private static final String[] sModelCols = new String[]{"ModelId", "VisId", "Description"};
   private static final String[] sFinanceCubeCols = new String[]{"FinanceCubeId", "VisId", "Description"};
   private static final String[] sDataTypeCols = new String[]{"DataTypeId", "VisId", "Description"};
   private static final String[] sBudgetCycleCols = new String[]{"BudgetCycleId", "VisId", "Description"};


   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      if(actionForm instanceof PopupForm) {
         PopupForm myForm = (PopupForm)actionForm;
         CPContext cntx = this.getCPContext(httpServletRequest);
         EntityList list;
         if(myForm.isModel()) {
            list = cntx.getCPConnection().getListHelper().getAllModelsWebForUser(cntx.getIntUserId());
            myForm.setSelection(this.buildDTOList(list, sModelCols));
            return actionMapping.findForward("model");
         }

         if(myForm.isFinanceCube()) {
            if(myForm.getModelId() == 0) {
               list = cntx.getCPConnection().getListHelper().getAllFinanceCubesWeb();
            } else {
               list = cntx.getCPConnection().getListHelper().getAllFinanceCubesWebForModel(myForm.getModelId());
            }

            myForm.setSelection(this.buildDTOList(list, sFinanceCubeCols));
            return actionMapping.findForward("financecube");
         }

         if(myForm.isFinanceCube1()) {
            list = cntx.getCPConnection().getListHelper().getAllFinanceCubesWebForUser(cntx.getIntUserId());
            myForm.setSelection(this.buildDTOList(list, sFinanceCubeCols));
            return actionMapping.findForward("financecube");
         }

         if(myForm.isDataType()) {
            list = cntx.getCPConnection().getListHelper().getPickerDataTypesWeb(myForm.getFinancecubeId(), new int[]{0, 1, 2, 3, 4}, false);
            myForm.setSelection(this.buildDTOList(list, sDataTypeCols));
            return actionMapping.findForward("datatype");
         }

         if(myForm.isDataType1()) {
            list = cntx.getCPConnection().getListHelper().getPickerDataTypesWeb(myForm.getFinancecubeId(), new int[]{0}, true);
            myForm.setSelection(this.buildDTOList(list, sDataTypeCols));
            return actionMapping.findForward("datatype");
         }

         if(myForm.isBudgetCycleType()) {
            list = cntx.getCPConnection().getListHelper().getBudgetCyclesForModelWithState(myForm.getModelId(), 1);
            myForm.setSelection(this.buildDTOList(list, sBudgetCycleCols));
            return actionMapping.findForward("budgetcycle");
         }
      }

      return null;
   }

   private List buildDTOList(EntityList eList, String[] cols) {
      ArrayList returnValue = new ArrayList();
      int listSize = eList.getNumRows();

      for(int i = 0; i < listSize; ++i) {
         PopupDTO dto = new PopupDTO();

         for(int j = 0; j < cols.length; ++j) {
            if(j == 0) {
               Object id = eList.getValueAt(i, cols[j]);
               if(id instanceof Short) {
                  dto.setId(((Short)id).intValue());
               } else {
                  dto.setId(((Integer)id).intValue());
               }
            } else if(j == 1) {
               dto.setVisId((String)eList.getValueAt(i, cols[j]));
            } else if(j == 2) {
               dto.setDescription((String)eList.getValueAt(i, cols[j]));
            }
         }

         returnValue.add(dto);
      }

      return returnValue;
   }

}
