// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPContextCache;
import com.cedar.cp.utc.common.CPSystemProperties;
import com.cedar.cp.util.Log;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

public class CPForm extends ActionForm {

   protected Log mLog = new Log(this.getClass());
   private String mSilentLogonDetails;
   private String mCPContextId;
   private String mInitialPage;
   private String mInitialPageParams;


   protected CPContext getCPContext(HttpServletRequest request) {
      HttpSession session = request.getSession();
      CPContext context = (CPContext)session.getAttribute("cpContext");
      return context;
   }

   protected CPSystemProperties getCPSystemProperties(HttpServletRequest request) {
//      HttpSession session = request.getSession();
//      ServletContext servletContext = session.getServletContext();
//      CPSystemProperties context = (CPSystemProperties)servletContext.getAttribute("cpSystemProperties");
//      return context;
      
      
		HttpSession session = request.getSession();
		ServletContext servletContext = session.getServletContext();

		CPSystemProperties context = (CPSystemProperties) servletContext.getAttribute("cpSystemProperties");
		
		if(context==null){
			CPSystemProperties props = new CPSystemProperties();
			servletContext.setAttribute("cpSystemProperties", props);
//			session.setAttribute("cpSystemProperties", props);
			
			context = (CPSystemProperties) servletContext.getAttribute("cpSystemProperties");
		}
		return context;

   }

   protected Log getLogger() {
      return this.mLog;
   }

   protected void checkForEmpty(String fieldName, String fieldKey, String value, ActionErrors errors) {
      if(value == null || value.trim().length() == 0) {
         ActionMessage message = new ActionMessage("cp.errors.field.null", fieldName);
         errors.add(fieldKey, message);
      }

   }

   protected void checkForEmpty(String fieldName, String fieldKey, List value, ActionErrors errors) {
      if(value == null || value.size() == 0) {
         ActionMessage message = new ActionMessage("cp.errors.field.null", fieldName);
         errors.add(fieldKey, message);
      }

   }

   protected void checkForEmpty(String fieldName, String fieldKey, Object value, ActionErrors errors) {
      if(value == null) {
         ActionMessage message = new ActionMessage("cp.errors.field.null", fieldName);
         errors.add(fieldKey, message);
      }

   }

   protected void checkForLength(String fieldName, String fieldKey, String value, int maxLength, ActionErrors errors) {
      if(value != null && value.trim().length() > maxLength) {
         ActionMessage message = new ActionMessage("cp.errors.field.length", fieldName, new Integer(maxLength));
         errors.add(fieldKey, message);
      }

   }

   protected void checkForNumber(String fieldName, String fieldKey, String value, ActionErrors errors, int type) {
      try {
         if(type == 0) {
            Integer.parseInt(value);
         } else if(type == 1) {
            Double.parseDouble(value);
         } else if(type == 2) {
            Long.parseLong(value);
         }
      } catch (NumberFormatException var8) {
         ActionMessage message = new ActionMessage("cp.errors.field.number", fieldName);
         errors.add(fieldKey, message);
      }

   }

   public String getSilentLogonDetails() {
      return this.mSilentLogonDetails;
   }

   public void setSilentLogonDetails(String silentLogonDetails) {
      this.mSilentLogonDetails = silentLogonDetails;
   }

   public void setCPContextId(HttpServletRequest request) {
      this.mCPContextId = CPContextCache.getCPContextId(this.getCPContext(request));
   }

   public String getCPContextId() {
      return this.mCPContextId;
   }

   public String getContextId() {
      return this.mCPContextId;
   }

   protected Double scaleValueForDisplay(Long l) {
      Double value = new Double((double)l.longValue() * 1.0D / 10000.0D);
      return value;
   }

   public String getInitialPage() {
      return this.mInitialPage == null?"":this.mInitialPage;
   }

   public void setInitialPage(String initialPage) {
      this.mInitialPage = initialPage;
   }

   public String getInitialPageParams() {
      return this.mInitialPageParams;
   }

   public void setInitialPageParams(String initialPageParams) {
      this.mInitialPageParams = initialPageParams;
   }
}
