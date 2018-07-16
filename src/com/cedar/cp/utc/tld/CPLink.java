// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.tld;

import com.cedar.cp.utc.common.CPContext;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.LinkTag;

public class CPLink extends LinkTag {

   protected String mSecurityString;
   private HttpSession mSession = null;


   public String getSecurityString() {
      return this.mSecurityString;
   }

   public void setSecurityString(String securityString) {
      this.mSecurityString = securityString;
   }

   public int doStartTag() throws JspException {
      if(this.checkSecurity()) {
         return super.doStartTag();
      } else {
         StringBuffer results = new StringBuffer();
         results.append("");
         TagUtils.getInstance().write(this.pageContext, results.toString());
         return 6;
      }
   }

   public int doEndTag() throws JspException {
      if(this.checkSecurity()) {
         return super.doEndTag();
      } else {
         StringBuffer results = new StringBuffer();
         results.append("");
         TagUtils.getInstance().write(this.pageContext, results.toString());
         return 6;
      }
   }

   public void setPageContext(PageContext pageContext) {
      super.setPageContext(pageContext);
      this.setSession(pageContext.getSession());
   }

   private HttpSession getSession() {
      return this.mSession;
   }

   private void setSession(HttpSession session) {
      this.mSession = session;
   }

   private boolean checkSecurity() {
      boolean result = true;
      if(this.getSecurityString() != null && this.getSession() != null) {
         Object o = this.getSession().getAttribute("cpContext");
         if(o != null && o instanceof CPContext) {
            CPContext conn = (CPContext)o;
            result = conn.getUserContext().hasSecurity(this.getSecurityString());
         }
      }

      return result;
   }
}
