// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin;

import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPSystemProperties;
import java.net.URL;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

public class AdministrationSetupAction extends CPAction {

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      CPContext context = this.getCPContext(request);
      CPSystemProperties props = this.getCPSystemProperties(request);
      MessageResources msgs = this.getResources(request);
      String userId = context.getUserId();
      String password = context.getPassword();
      String connectionURL = context.getConnectionURL();
      String originalRequest = request.getRequestURL().toString();
      URL originalURL = new URL(originalRequest);
      String clientHostReference = originalURL.getHost();
      String[] components = connectionURL.split(":");
      StringBuilder tmp = new StringBuilder();

      for(int rmiTunnel = 0; rmiTunnel < components.length; ++rmiTunnel) {
         if(rmiTunnel > 0) {
            tmp.append(':');
         }

         tmp.append(rmiTunnel == 2?clientHostReference:components[rmiTunnel]);
      }

      connectionURL = tmp.toString();
      String var25 = context.getUseRMITunnel();
      String cosign = context.getCosignSignon();
      String portal = context.getPortalSignon();
      String ntlm = context.getNtlmSignon();
      String sso = context.getSSOSignon();
      String name = props.getSystemName();
      String logLevel = String.valueOf(props.getLogLevel());
      String[] args = new String[]{userId, password, connectionURL, var25, cosign, portal, ntlm, sso, URLEncoder.encode(name, "UTF-8"), logLevel};
      String path = msgs.getMessage("cp.tc.jnlp", args);
      return new ActionForward("tc", path, false);
   }
}
