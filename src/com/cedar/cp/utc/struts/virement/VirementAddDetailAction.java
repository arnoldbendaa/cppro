// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.picker.ElementDTO;
import com.cedar.cp.utc.struts.virement.VirementAddressForm;
import com.cedar.cp.utc.struts.virement.VirementBaseAction;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO$SessionMgr;
import com.cedar.cp.utc.struts.virement.VirementGroupDTO;
import com.cedar.cp.utc.struts.virement.VirementLineDTO;
import com.cedar.cp.util.StringLexer;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VirementAddDetailAction extends VirementBaseAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      HttpSession session = httpServletRequest.getSession();
      CPContext cntx = this.getCPContext(httpServletRequest);
      EntityList eList = null;
      boolean size = false;
      VirementAddressForm form = (VirementAddressForm)actionForm;
      form.setCPContextId(httpServletRequest);
      if(form.getUserAction() != null && form.getUserAction().equals("CellSelected")) {
         form.setData(VirementDataEntryDTO$SessionMgr.load(session, form.getData().getKey()));
         httpServletRequest.setAttribute("requestId", form.getKey());
         VirementDataEntryDTO businessDimensionId1 = form.getData();
         VirementGroupDTO rootForDimension1;
         if(form.isAddGroup()) {
            rootForDimension1 = new VirementGroupDTO();
            businessDimensionId1.addGroup(rootForDimension1);
         } else {
            List structureElementId1 = (List)businessDimensionId1.getGroups();
            rootForDimension1 = (VirementGroupDTO)structureElementId1.get(form.getCurrentGroup());
         }

         VirementLineDTO structureElementId2 = new VirementLineDTO();
         rootForDimension1.addRow(structureElementId2);
         structureElementId2.setCells(this.getSelected(cntx.getCPConnection(), form));
         structureElementId2.setTo(form.getTransferType().equalsIgnoreCase("T"));
         structureElementId2.setAllocationThreshold(String.valueOf(this.queryAllocationThreshold(httpServletRequest)));
         int systemProps1 = structureElementId2.getCells().size() - 1;
         ElementDTO calElement = (ElementDTO)((List)structureElementId2.getCells()).get(systemProps1);
         if(calElement.getKey() != null) {
            CalendarInfo calInfo = cntx.getCPConnection().getHierarchysProcess().getCalendarInfoForModel(Integer.valueOf(businessDimensionId1.getModelId()));
            CalendarElementNode cen = calInfo.getById(calElement.getKey());
            calElement.setIdentifier(cen.getFullPathVisId());
         }

         form.setdTO(businessDimensionId1);
         form.setUserAction("");
         return actionMapping.findForward("selected");
      } else if(form.getUserAction() != null && form.getUserAction().equalsIgnoreCase("cancel")) {
         return actionMapping.findForward("cancel");
      } else {
         eList = cntx.getCPConnection().getListHelper().getAllDimensionsForModel(form.getData().getModelId());
         int businessDimensionId = ((Integer)eList.getValueAt(0, "DimensionId")).intValue();
         EntityList rootForDimension = cntx.getCPConnection().getListHelper().getHierarcyDetailsIncRootNodeFromDimId(businessDimensionId);
         int structureElementId = ((Integer)rootForDimension.getValueAt(0, "StructureElementId")).intValue();
         form.setRootRa(String.valueOf(structureElementId));
         form.setCPContextId(httpServletRequest);
         EntityList systemProps = cntx.getCPConnection().getSystemPropertysProcess().getSystemProperty("VIRE: RESTRICT_BY_RA");
         form.setDisableRASecurity(systemProps.getNumRows() != 1 || !Boolean.parseBoolean(systemProps.getValueAt(0, "Value").toString()));
         int size1 = eList.getNumRows();
         form.setNoOfDims(size1);
         VirementDataEntryDTO$SessionMgr.save(session, form.getData());
         form.setKey(form.getData().getKey());
         if(form.getUserAction() != null && form.getUserAction().equalsIgnoreCase("addGroup")) {
            form.setAddGroup(true);
         }

         return actionMapping.findForward("success");
      }
   }

   protected List getSelected(CPConnection connection, VirementAddressForm form) {
      ElementDTO dto = null;
      ArrayList contents = new ArrayList(1);
      StringLexer idtoken = new StringLexer(form.getSelectedIds(), "_*_");
      StringLexer identifiertoken = new StringLexer(form.getSelectedIdentifiers(), "_*_");
      StringLexer descriptiontoken = new StringLexer(form.getSelectedDescriptions(), "_*_");
      StringLexer structuretoken = new StringLexer(form.getSelectedStructureIds(), "_*_");
      String s = "";

      while(idtoken.hasMoreTokens()) {
         dto = new ElementDTO();
         s = idtoken.nextToken();
         dto.setId(this.parseValue(s));
         s = structuretoken.nextToken();
         dto.setStructureId(this.parseValue(s));
         s = identifiertoken.nextToken();
         dto.setIdentifier(s);
         s = descriptiontoken.nextToken();
         dto.setDescription(s);
         dto.setKey(connection.getEntityKeyFactory().getKeyFromTokens("StructureElementPK|" + dto.getStructureId() + "," + dto.getId()));
         contents.add(dto);
      }

      return contents;
   }
}
