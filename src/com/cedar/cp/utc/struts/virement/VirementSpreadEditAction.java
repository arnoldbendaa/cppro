// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.model.udwp.Profile;
import com.cedar.cp.utc.struts.virement.DataTypeDTO;
import com.cedar.cp.utc.struts.virement.VirementBaseAction;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO$SessionMgr;
import com.cedar.cp.utc.struts.virement.VirementGroupDTO;
import com.cedar.cp.utc.struts.virement.VirementLineDTO;
import com.cedar.cp.utc.struts.virement.VirementLineSpreadDTO;
import com.cedar.cp.utc.struts.virement.VirementSpreadForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VirementSpreadEditAction extends VirementBaseAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      HttpSession session = httpServletRequest.getSession();
      VirementSpreadForm form = (VirementSpreadForm)actionForm;
      VirementLineDTO updatedLine = form.getLine();
      form.setData(VirementDataEntryDTO$SessionMgr.load(session, form.getData().getKey()));
      if(form.getUserAction() != null && form.getUserAction().equalsIgnoreCase("ok")) {
         List var18 = (List)form.getData().getGroups();
         VirementGroupDTO var19 = (VirementGroupDTO)var18.get(form.getCurrentGroup());
         List var21 = (List)var19.getLines();
         VirementLineDTO var20 = (VirementLineDTO)var21.get(form.getCurrentLine());
         var20.setTransferValue(updatedLine.getTransferValue());
         DataTypeDTO var22 = form.getData().getDataType(updatedLine.getDataTypeId());
         var20.setDataTypeVisId(var22 != null?var22.getVisId():null);
         var20.setDataTypeId(updatedLine.getDataTypeId());
         var20.setSpreadProfileId(updatedLine.getSpreadProfileId());
         CPConnection var23 = this.getCPContext(httpServletRequest).getCPConnection();
         String profileId1 = updatedLine.getSpreadProfileId();
         if(profileId1 != null && profileId1.trim().length() != 0 && !profileId1.equals(var23.getWeightingProfilesProcess().getCustomWeightingProfile().getTokenizedKey())) {
            Profile i1 = this.getWeightingProfile(var23, form.getData(), var20);
            if(i1 != null) {
               var20.setSpreadProfileVisId(i1.getRef().getNarrative());
            } else {
               var20.setSpreadProfileVisId((String)null);
            }
         } else {
            var20.setSpreadProfileVisId((String)null);
         }

         for(int var24 = 0; var24 < var20.getSpreadProfile().size(); ++var24) {
            VirementLineSpreadDTO oldSpread = (VirementLineSpreadDTO)((List)var20.getSpreadProfile()).get(var24);
            VirementLineSpreadDTO newSpread = (VirementLineSpreadDTO)((List)updatedLine.getSpreadProfile()).get(var24);
            oldSpread.update(newSpread);
         }

         return actionMapping.findForward("success");
      } else if(form.getUserAction() != null && form.getUserAction().equalsIgnoreCase("profileSelected")) {
         CPConnection connection = this.getCPContext(httpServletRequest).getCPConnection();
         form.setProfiles(this.queryWeightingProfiles(connection, form.getData(), updatedLine, true));
         String profileId = updatedLine.getSpreadProfileId();
         if(profileId.trim().length() != 0 && !profileId.equals(connection.getWeightingProfilesProcess().getCustomWeightingProfile().getTokenizedKey())) {
            Profile p = this.getWeightingProfile(connection, form.getData(), updatedLine);
            if(p != null) {
               int[] profile = p.getWeightings();

               for(int i = 0; i < updatedLine.getSpreadProfile().size() && i < profile.length; ++i) {
                  VirementLineSpreadDTO dto = (VirementLineSpreadDTO)((List)updatedLine.getSpreadProfile()).get(i);
                  dto.setWeighting(String.valueOf(profile[i]));
               }
            }
         }

         return actionMapping.findForward("iterate");
      } else {
         if(form.getUserAction() != null && form.getUserAction().equalsIgnoreCase("quit")) {
            httpServletRequest.setAttribute("requestId", form.getData().getKey());
            if(form.getData().isReadOnly()) {
               if(form.getParentPage() != null && form.getParentPage().equalsIgnoreCase("auth")) {
                  return actionMapping.findForward("authVirement");
               }

               return actionMapping.findForward("viewVirement");
            }
         }

         return actionMapping.findForward("success");
      }
   }
}
