// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.login;

//import com.adv.stats.perf.J2EEPerformanceLogger;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPSystemProperties;
import com.cedar.cp.util.Timer;
import edu.umich.auth.cosign.CosignConfig;
import edu.umich.auth.cosign.util.ServiceConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class Logout extends Action {

   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
      Timer timer = new Timer();
      timer.start();
      HttpSession session = request.getSession();
      CPContext context = (CPContext)session.getAttribute("cpContext");
      if(context != null) {
         context.logoff();
      }

      session.removeAttribute("cpContext");
      session.invalidate();
      CPSystemProperties props = (CPSystemProperties)session.getServletContext().getAttribute("cpSystemProperties");
      String logoffURL;
      if(props.isCosignSignonFilterEnabled()) {
         String e = request.getRequestURI();
         logoffURL = e.substring(request.getContextPath().length());
         String resource = logoffURL.substring(logoffURL.lastIndexOf(47) + 1);
         if(logoffURL.charAt(logoffURL.length() - 1) != 47) {
            logoffURL = logoffURL.substring(0, logoffURL.lastIndexOf(47) + 1);
         }

         ServiceConfig serviceConfig = CosignConfig.INSTANCE.hasServiceOveride(logoffURL, resource, request.getQueryString());
         String serviceName = serviceConfig.getName();
         if(serviceName == null) {
            serviceName = "cosign-eucsCosigntest-edutest.cedar.com";
         }

         Cookie c = new Cookie(serviceName.toString(), (String)null);
         c.setPath("/");
         response.addCookie(c);
      }

      logoffURL = props.getLogOffPage();
      if(logoffURL != null && logoffURL.length() > 0) {
         try {
            response.sendRedirect(logoffURL);
            return null;
         } catch (Exception var15) {
            var15.printStackTrace();
         }
      }
      if (context != null ) {
//          (new J2EEPerformanceLogger()).log(context.getUserId(), "CP", "Logout", 0, timer.stop(), System.currentTimeMillis());
      }
      return mapping.findForward("success");
   }
}
