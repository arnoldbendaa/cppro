// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.workbench;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.UserContext;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.approver.CrumbDTO;
import com.cedar.cp.utc.struts.workbench.WorkBenchForm;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class BaseWorkBenchAction extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
      CPContext context = this.getCPContext(httpServletRequest);
      CPConnection cnx = context.getCPConnection();
      UserContext userCnx = cnx.getUserContext();
      WorkBenchForm myForm = (WorkBenchForm)actionForm;
      myForm.setController(userCnx.hasSecurity("WEB_PROCESS.RootNode"));
      this.populateForm(myForm, context, httpServletRequest);
      return actionMapping.findForward("success");
   }

   public abstract void populateForm(WorkBenchForm var1, CPContext var2, HttpServletRequest var3);

   public void doCrumbs(ActionForm form, String locationIdentifier, String description) {
      if(form instanceof WorkBenchForm) {
         WorkBenchForm myForm = (WorkBenchForm)form;
         ArrayList crumbs = new ArrayList();
         StringBuffer sel = new StringBuffer();
         StringBuffer vl = new StringBuffer();
         StringBuffer odl = new StringBuffer();
         StringBuffer oucl = new StringBuffer();
         StringBuffer dl = new StringBuffer();
         String delim = "**,**";
         StringTokenizer seTokens;
         StringTokenizer vlTokens;
         StringTokenizer odTokens;
         StringTokenizer oucTokens;
         StringTokenizer dTokens;
         String vlToken;
         String seToken;
         String oucToken;
         String odToken;
         String dToken;
         if(myForm.getAddId() != null && myForm.getAddId().length() > 0) {
            sel.append(myForm.getStructureElementList());
            vl.append(myForm.getVisIdList());
            odl.append(myForm.getOldDepthList());
            oucl.append(myForm.getOldUserCountList());
            dl.append(myForm.getDescriptionList());
            if(sel.length() > 0) {
               sel.append(delim);
               vl.append(delim);
               odl.append(delim);
               oucl.append(delim);
               dl.append(delim);
            }

            sel.append(myForm.getStructureElementId());
            vl.append(locationIdentifier);
            odl.append(myForm.getOldDepth());
            oucl.append(myForm.getOldUserCount());
            dl.append(description);
         } else if(myForm.getOldId() != null && myForm.getOldId().length() > 0) {
            String dto = myForm.getOldId();
            boolean add = true;
            seTokens = new StringTokenizer(myForm.getStructureElementList(), delim);
            vlTokens = new StringTokenizer(myForm.getVisIdList(), delim);
            odTokens = new StringTokenizer(myForm.getOldDepthList(), delim);
            oucTokens = new StringTokenizer(myForm.getOldUserCountList(), delim);
            dTokens = new StringTokenizer(myForm.getDescriptionList(), delim);

            while(seTokens.hasMoreTokens() && add) {
               seToken = seTokens.nextToken();
               if(seToken.equals(dto)) {
                  add = false;
               }

               vlToken = vlTokens.nextToken();
               odToken = odTokens.nextToken();
               oucToken = oucTokens.nextToken();
               dToken = dTokens.nextToken();
               if(sel.length() > 0) {
                  sel.append(delim);
                  vl.append(delim);
                  odl.append(delim);
                  oucl.append(delim);
                  dl.append(delim);
               }

               sel.append(seToken);
               vl.append(vlToken);
               odl.append(odToken);
               oucl.append(oucToken);
               dl.append(dToken);
            }
         } else {
            sel.append(myForm.getStructureElementList());
            vl.append(myForm.getVisIdList());
            odl.append(myForm.getOldDepthList());
            oucl.append(myForm.getOldUserCountList());
            dl.append(myForm.getDescriptionList());
         }

         myForm.setStructureElementList(sel.toString());
         myForm.setVisIdList(vl.toString());
         myForm.setOldDepthList(odl.toString());
         myForm.setOldUserCountList(oucl.toString());
         myForm.setDescriptionList(dl.toString());
         seTokens = new StringTokenizer(myForm.getStructureElementList(), delim);
         vlTokens = new StringTokenizer(myForm.getVisIdList(), delim);
         odTokens = new StringTokenizer(myForm.getOldDepthList(), delim);
         oucTokens = new StringTokenizer(myForm.getOldUserCountList(), delim);
         dTokens = new StringTokenizer(myForm.getDescriptionList(), delim);

         while(seTokens.hasMoreTokens()) {
            seToken = seTokens.nextToken();
            vlToken = vlTokens.nextToken();
            odToken = oucTokens.nextToken();
            oucToken = odTokens.nextToken();
            dToken = dTokens.nextToken();
            CrumbDTO dto1 = new CrumbDTO(vlToken, seToken, odToken, oucToken, dToken);
            crumbs.add(dto1);
         }

         myForm.setCrumbs(crumbs);
      }

   }
}
