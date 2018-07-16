// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.topdown;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.picker.ElementDTO;
import com.cedar.cp.utc.struts.topdown.DimensionForm;
import com.cedar.cp.utc.struts.topdown.MassUpdateDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DimensionSelect extends CPAction {

   public static final String sSPLIT_TOKEN = "_\\*_";
   public static final String sJSP_TOKEN = "_*_";


   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      CPContext cntx = this.getCPContext(httpServletRequest);
      EntityList eList = null;
      boolean size = false;
      String forward = "success";
      if(actionForm instanceof DimensionForm) {
         DimensionForm myForm = (DimensionForm)actionForm;
         myForm.setCPContextId(httpServletRequest);
         eList = cntx.getCPConnection().getListHelper().getAllDimensionsForModel(myForm.getMassDTO().getModelId());
         int size1 = eList.getNumRows();
         myForm.setNoOfDims(size1);
         String action = myForm.getAction();
         if(action == null) {
            action = "";
         }

         Object o;
         if(action.equals("")) {
            o = httpServletRequest.getSession().getAttribute("massUpdate");
            if(o instanceof MassUpdateDTO) {
               MassUpdateDTO dto = (MassUpdateDTO)o;
               List l = null;
               l = dto.getChangeCells();
               if(l != null && l.size() > 0) {
                  myForm.getMassDTO().setChangeCells(l);
               }

               l = dto.getHoldCells();
               if(l != null && l.size() > 0) {
                  myForm.getMassDTO().setHoldCells(l);
               }

               l = dto.getDimensionHeader();
               if(l != null && l.size() > 0) {
                  myForm.getMassDTO().setDimensionHeader(l);
               }
            }

            forward = "success";
         } else if(action.equals("Click")) {
            o = httpServletRequest.getSession().getAttribute("massUpdate");
            if(o instanceof MassUpdateDTO) {
               myForm.setMassDTO((MassUpdateDTO)o);
            }

            if(myForm.isRequireDataType()) {
               myForm.getMassDTO().setLastAction("dsChangeCell");
            } else {
               myForm.getMassDTO().setLastAction("dsHoldCells");
            }

            this.setHeaders(myForm);
            this.setSelected(myForm);
            forward = "selected";
         } else if(action.equals("cancel")) {
            o = httpServletRequest.getSession().getAttribute("massUpdate");
            if(o instanceof MassUpdateDTO) {
               myForm.setMassDTO((MassUpdateDTO)o);
            }

            myForm.getMassDTO().setLastAction("dsCancel");
            forward = "selected";
         }

         httpServletRequest.getSession().setAttribute("massUpdate", myForm.getMassDTO());
      }

      return actionMapping.findForward(forward);
   }

   private void setHeaders(DimensionForm form) {
      ArrayList headerContents = new ArrayList();
      StringTokenizer token = new StringTokenizer(form.getHeaders(), "_*_");
      String s = "";

      while(token.hasMoreTokens()) {
         s = token.nextToken();
         headerContents.add(s);
      }

      form.getMassDTO().setDimensionHeader(headerContents);
   }

   private void setSelected(DimensionForm form) {
      ElementDTO dto = null;
      ArrayList contents = new ArrayList();
      String[] idtoken = form.getSelectedIds().split("_\\*_");
      String[] identifiertoken = form.getSelectedIdentifiers().split("_\\*_");
      String[] structureidtoken = form.getSelectedStructureIds().split("_\\*_");
      String[] descriptiontoken = form.getSelectedDescriptions().split("_\\*_");
      int maxStructureSize = structureidtoken.length;
      boolean size = false;
      int var15;
      if(form.isRequireDataType()) {
         var15 = maxStructureSize + 1;
      } else {
         var15 = maxStructureSize;
      }

      String s = "";

      int i;
      for(i = 0; i < var15; ++i) {
         dto = new ElementDTO();
         s = idtoken[i];
         dto.setId(this.parseValue(s));
         if(i < maxStructureSize) {
            s = structureidtoken[i];
            dto.setStructureId(this.parseValue(s));
         }

         s = identifiertoken[i];
         dto.setIdentifier(s);
         s = descriptiontoken[i];
         dto.setDescription(s);
         contents.add(dto);
      }

      if(form.isRequireDataType()) {
         form.getMassDTO().addChangedCells(contents);
      } else {
         form.getMassDTO().addHoldCells(contents);
      }

      int diff = identifiertoken.length - var15;
      ArrayList duplicate = null;
      if(diff > 0) {
         for(int j = 0; j < diff; ++j) {
            duplicate = new ArrayList(contents);
            dto = new ElementDTO();
            s = idtoken[i];
            dto.setId(this.parseValue(s));
            s = identifiertoken[i];
            dto.setIdentifier(s);
            s = descriptiontoken[i];
            dto.setDescription(s);
            duplicate.remove(var15 - 1);
            duplicate.add(dto);
            ++i;
            if(form.isRequireDataType()) {
               form.getMassDTO().addChangedCells(duplicate);
            } else {
               form.getMassDTO().addHoldCells(duplicate);
            }
         }
      }

   }
}
