// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.homepage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.BrowserUtil;
import com.cedar.cp.utc.common.CPContext;

public class HomePageTabSetupAction extends BaseHomePageAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	
    	

        CPContext context = getCPContext(request);

        if (BrowserUtil.isUnsupportedBrowser(request)) {
            return mapping.findForward("unsupportedBrowser");
        }

        if ((context != null) && (context.isForceQuestions())) {
            return mapping.findForward("forceQuestions");
        } else if ((context != null) && (context.isForceWord())) {
            return mapping.findForward("forceWord");
        }
        if (((context != null) && (!context.getUserContext().userMustChangePassword()) && (!context.isPortalUser())) || ((context != null) && ((context.isSingleSignon()) || (context.isCosignSignon()) || (context.isPortalSignon()) || (context.isNtlmSignon())))) {
            populateHomeForm(form, request, context);
            return mapping.findForward("loggedOn");
        }

        return mapping.findForward("notLoggedOn");
    }

    protected void populateHomeForm(ActionForm form, HttpServletRequest request, CPContext context) {
        CPConnection cnx = context.getCPConnection();
        HomePageForm myForm = (HomePageForm) form;
        ArrayList modelList = new ArrayList();
        int userId = cnx.getUserContext().getUserId();
        EntityList models = context.getCPConnection().getListHelper().getAllModelsWithActiveCycleForUser(userId);
        int rows = models.getNumRows();

        for (int i = 0; i < rows; ++i) {
            ModelDTO modelDTO = new ModelDTO();
            Object modelref = models.getValueAt(i, "VisId");
            Integer modelId = (Integer) models.getValueAt(i, "ModelId");
            String description = models.getValueAt(i, "Description").toString();
            modelDTO.setName(modelref);
            modelDTO.setModelId(modelId.intValue());
            modelDTO.setDescription(description);
            modelList.add(modelDTO);
        }

        myForm.setModel(modelList);
        this.populateVirementStatus(myForm, context);
    }

}
