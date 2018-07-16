// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPContextCache;
import com.cedar.cp.utc.common.CPSystemProperties;
import com.cedar.cp.util.Log;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

   protected Log mLog = new Log(this.getClass());


   public void sessionCreated(HttpSessionEvent event) {
      this.mLog.debug("sessionCreated", "Session created");
      CPSystemProperties systemProperties = (CPSystemProperties)event.getSession().getServletContext().getAttribute("cpSystemProperties");
      int timeout = systemProperties.getSessionTimeout();
      event.getSession().setMaxInactiveInterval(timeout * 60);
   }

   public void sessionDestroyed(HttpSessionEvent event) {
      CPContext cntx = (CPContext)event.getSession().getAttribute("cpContext");
      if(cntx != null) {
         CPConnection cnx = cntx.getCPConnection();
         if(cnx != null) {
            cnx.close();
         }

         CPContextCache.remove(cntx);
      }

      this.mLog.debug("sessionDestroyed", "Session destroyed");
   }
}
