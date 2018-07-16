// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPSystemProperties;
import com.cedar.cp.utc.common.SingleEntryPointMismatchException;
import edu.umich.auth.cosign.CosignPrincipal;
import edu.umich.auth.cosign.CosignServletCallbackHandler;
import java.security.Principal;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

public class CPCosignServletCallbackHandler extends CosignServletCallbackHandler {

   public void handleSuccessfulLogin() throws ServletException {
      super.handleSuccessfulLogin();
      HttpSession session = this.getRequest().getSession();

      try {
         CPContext t = (CPContext)session.getAttribute("cpContext");
         if(t == null) {
            Principal principal = this.getRequest().getUserPrincipal();
            CosignPrincipal cosignUser = (CosignPrincipal)principal;
            CPSystemProperties sysProps = (CPSystemProperties)this.getRequest().getSession().getServletContext().getAttribute("cpSystemProperties");
            CPContext context = CPContext.logon(sysProps, this.getRequest(), cosignUser);
            context.setCosignSignon(true);
            session.setAttribute("cpContext", context);
            session.setMaxInactiveInterval('\ua8c0');
         }
      } catch (SingleEntryPointMismatchException var7) {
         throw new IllegalStateException("Single entry exception");
      } catch (Exception var8) {
         throw new IllegalStateException("CPCosignServletCallbackHandler can\'t process user", var8);
      } catch (Throwable var9) {
         var9.printStackTrace();
      }

   }
}
