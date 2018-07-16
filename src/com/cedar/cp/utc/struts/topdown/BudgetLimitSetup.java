// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.topdown;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.picker.DimensionDTO;
import com.cedar.cp.utc.struts.topdown.BudgetLimitForm;
import com.cedar.cp.utc.struts.topdown.LimitsDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BudgetLimitSetup extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      String action = "success";
      CPContext cnx = this.getCPContext(httpServletRequest);
      CPConnection conn = null;
      EntityList list = null;
      boolean size = false;
      if(actionForm instanceof BudgetLimitForm) {
         BudgetLimitForm myForm = (BudgetLimitForm)actionForm;
         if(myForm.getFinanceCubeId() != 0) {
            conn = cnx.getCPConnection();
            int var28;
            if(myForm.getModelId() == 0) {
               list = conn.getListHelper().getFinanceCubeDetails(myForm.getFinanceCubeId());
               var28 = list.getNumRows();

               int elements;
               for(elements = 0; elements < var28; ++elements) {
                  myForm.setModelId(((Integer)list.getValueAt(elements, "ModelId")).intValue());
                  myForm.setFinanceCubeDescription((String)list.getValueAt(elements, "Description"));
               }

               list = conn.getListHelper().getModelDetailsWeb(myForm.getModelId());
               var28 = list.getNumRows();

               for(elements = 0; elements < var28; ++elements) {
                  myForm.setModelVisId((String)list.getValueAt(elements, "VisId"));
                  myForm.setModelDescription((String)list.getValueAt(elements, "Description"));
               }
            }

            ArrayList var29 = new ArrayList();
            ArrayList labels = new ArrayList();
            list = conn.getListHelper().getNodesForUserAndModel(myForm.getModelId(), cnx.getIntUserId());
            var28 = list.getNumRows();
            StringBuffer sb = null;
            String s = "";
            Object element = null;

            for(int headers = 0; headers < var28; ++headers) {
               sb = new StringBuffer();
               element = list.getValueAt(headers, "StructureElementId");
               if(myForm.getSelectedRA() == 0) {
                  myForm.setSelectedRA(((Integer)element).intValue());
               }

               if(!var29.contains(element)) {
                  var29.add(element);
                  sb.append(list.getValueAt(headers, "VisId"));
                  s = (String)list.getValueAt(headers, "Description");
                  if(s != null && s.length() > 0) {
                     sb.append(" - ");
                     sb.append(s);
                  }

                  labels.add(sb.toString());
               }
            }

            myForm.setRespAreaStructureElement(var29);
            myForm.setRespAreaDisplay(labels);
            ArrayList var30 = new ArrayList();
            list = conn.getListHelper().getAllDimensionsForModel(myForm.getModelId());
            var28 = list.getNumRows();
            DimensionDTO dimDTO = null;

            int noOfDims;
            for(noOfDims = 1; noOfDims < var28; ++noOfDims) {
               dimDTO = new DimensionDTO();
               dimDTO.setId(((Integer)list.getValueAt(noOfDims, "DimensionId")).intValue());
               dimDTO.setIdentifier((String)list.getValueAt(noOfDims, "VisId"));
               dimDTO.setDescription((String)list.getValueAt(noOfDims, "Description"));
               var30.add(dimDTO);
            }

            myForm.setHeadings(var30);
            noOfDims = var28;
            myForm.setNoOfDims(var28);
            if(myForm.getFinanceCubeId() != 0) {
               List l = conn.getBudgetLimitsProcess().getImposedLimits(var28, myForm.getFinanceCubeId(), myForm.getSelectedRA());
               var28 = l.size();
               ArrayList imposed = new ArrayList(var28);
               LimitsDTO imp = null;
               List innerList = null;
               boolean innserSize = false;
               BigDecimal bd = null;

               int i;
               int var31;
               for(int set = 0; set < var28; ++set) {
                  imp = new LimitsDTO();
                  innerList = (List)l.get(set);
                  var31 = innerList.size();
                  imp.setRespAreaId((Integer)innerList.get(1));

                  for(i = 2; i < var31; ++i) {
                     if(i <= var31 - 4) {
                        imp.addStructureElement((String)innerList.get(i));
                     } else if(i == var31 - 3) {
                        imp.setMinValue((BigDecimal)innerList.get(i));
                     } else if(i == var31 - 2) {
                        imp.setMaxValue((BigDecimal)innerList.get(i));
                     } else if(i == var31 - 1) {
                        bd = (BigDecimal)innerList.get(i);
                        if(bd != null) {
                           imp.setCurrentValue(bd.doubleValue());
                        }
                     }
                  }

                  imposed.add(imp);
               }

               myForm.setImposedLimits(imposed);
               l = conn.getBudgetLimitsProcess().getBudgetLimitsSetByBudgetLocation(noOfDims, myForm.getFinanceCubeId(), myForm.getSelectedRA());
               var28 = l.size();
               ArrayList var32 = new ArrayList(var28);

               for(i = 0; i < var28; ++i) {
                  imp = new LimitsDTO();
                  innerList = (List)l.get(i);
                  var31 = innerList.size();
                  imp.setLimitId((Integer)innerList.get(0));

                  for(int j = 1; j < var31; ++j) {
                     if(j <= var31 - 3) {
                        imp.addStructureElement((String)innerList.get(j));
                     } else if(j == var31 - 2) {
                        imp.setMinValue((BigDecimal)innerList.get(j));
                     } else if(j == var31 - 1) {
                        imp.setMaxValue((BigDecimal)innerList.get(j));
                     }
                  }

                  var32.add(imp);
               }

               myForm.setSetlimits(var32);
            } else {
               myForm.setImposedLimits(Collections.EMPTY_LIST);
               myForm.setSetlimits(Collections.EMPTY_LIST);
            }
         }
      }

      return actionMapping.findForward(action);
   }
}
