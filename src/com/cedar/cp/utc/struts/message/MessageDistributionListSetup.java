// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.message;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.message.MessageDistributionListForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MessageDistributionListSetup extends CPAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		CPContext ctxt = this.getCPContext(httpServletRequest);

		EntityList entity = ctxt.getCPConnection().getInternalDestinationsProcess().getAllInternalDestinationDetails();
		List internalDistribution = entity.getDataAsList();
		entity = ctxt.getCPConnection().getExternalDestinationsProcess().getAllExternalDestinationDetails();
		List externalDistribution = entity.getDataAsList();

		MessageDistributionListForm myForm = (MessageDistributionListForm) actionForm;
		myForm.setInternalDistributions(internalDistribution);
		myForm.setExternalDistributions(externalDistribution);

		return actionMapping.findForward("success");
	}
}
