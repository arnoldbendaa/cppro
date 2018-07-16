// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPRoleSecurityException;
import com.cedar.cp.utc.common.CPSystemProperties;
import com.cedar.cp.utc.common.WebBussinessProcces;
import com.cedar.cp.util.Log;
import java.io.File;
import java.util.Arrays;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;

public class CPAction extends Action {

   protected Log mLog = new Log(this.getClass());
   protected transient File mTempDir = null;
   protected transient File mTempFile = null;


   protected CPContext getCPContext(HttpServletRequest request) throws CPRoleSecurityException {
      return this.getCPContext(request, (String)null);
   }

   protected CPContext getCPContext(HttpServletRequest request, String securityString) throws CPRoleSecurityException {
      HttpSession session = request.getSession();
      CPContext context = (CPContext)session.getAttribute("cpContext");
      if(securityString != null && !context.getUserContext().hasSecurity(securityString)) {
         throw new CPRoleSecurityException("No security for " + securityString);
      } else {
         return context;
      }
   }

   protected CPSystemProperties getCPSystemProperties(HttpServletRequest request) {
      HttpSession session = request.getSession();
      ServletContext servletContext = session.getServletContext();
      return (CPSystemProperties)servletContext.getAttribute("cpSystemProperties");
//	   return CPSystemProperties.getSystemProperties(request);
   }

   protected Log getLogger() {
      return this.mLog;
   }

   protected void dumpParams(HttpServletRequest request) {
      this.mLog.debug("*********** Params **************");
      Enumeration e = request.getParameterNames();

      String key;
      while(e.hasMoreElements()) {
         key = (String)e.nextElement();
         this.mLog.debug(key + " = " + Arrays.asList(request.getParameterValues(key)));
      }

      this.mLog.debug("*********** Attributes **************");
      e = request.getAttributeNames();

      while(e.hasMoreElements()) {
         key = (String)e.nextElement();
         this.mLog.debug(key + " = " + request.getAttribute(key).toString());
      }

   }

   protected void setHeaderInfo(HttpServletResponse response) {
      response.setHeader("Pragma", "no-cache");
      response.setHeader("Cache-Control", "no-cache");
      response.setIntHeader("Expires", -1);
   }

   protected void setWebProccess(HttpServletRequest request, WebBussinessProcces process) {
      HttpSession session = request.getSession();
      session.setAttribute("cpWebProcess", process);
   }

   protected WebBussinessProcces getWebProcess(HttpServletRequest request) {
      HttpSession session = request.getSession();
      return (WebBussinessProcces)session.getAttribute("cpWebProcess");
   }

   protected void removeWebProcess(HttpServletRequest request) {
      WebBussinessProcces proc = this.getWebProcess(request);
      if(proc != null) {
         BusinessProcess session = proc.getProcess();
         if(session != null) {
            BusinessSession session1 = proc.getSession();
            if(session1 != null) {
               session.terminateSession(session1);
            }
         }

         proc.setProcess((BusinessProcess)null);
         proc.setSession((BusinessSession)null);
         proc.setEditor((BusinessEditor)null);
      }

      HttpSession session2 = request.getSession();
      session2.setAttribute("cpWebProcess", proc);
      session2.removeAttribute("cpWebProcess");
   }

   protected String getTempDir() {
      String dir = System.getProperty("java.io.tmpdir");
      return dir;
   }

   protected String getTempFileUrl() {
      return "file://" + this.mTempFile.getAbsolutePath();
   }

   protected int parseValue(Object o) {
      return this.parseValue((String)o);
   }

   protected int parseValue(String s) {
      int i = 0;

      try {
         i = Integer.parseInt(s);
      } catch (Exception var4) {
         this.mLog.warn("parseValue", var4);
      }

      return i;
   }

   protected Long scaleValue(double d) {
      Double dub = Double.valueOf(d * 10000.0D);
      Long value = Long.valueOf(dub.longValue());
      return value;
   }

   protected CPConnection getCPConnection(HttpServletRequest request, String id, String password) throws Exception {
      CPContext conx = this.getCPContext(request);
      if(conx == null) {
         CPSystemProperties sysProps = this.getCPSystemProperties(request);
         conx = CPContext.logon(sysProps, request, id, password);
      }

      return conx.getCPConnection();
   }
}
