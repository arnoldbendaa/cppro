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
import org.apache.struts.taglib.html.ButtonTag;

public class CPButton extends ButtonTag {

   protected String buttonType = null;
   protected String mSecurityString;
   private HttpSession mSession = null;


   public String getButtonType() {
      return this.buttonType;
   }

   public void setButtonType(String buttonType) {
      this.buttonType = buttonType;
   }

   public String getSecurityString() {
      return this.mSecurityString;
   }

   public void setSecurityString(String securityString) {
      this.mSecurityString = securityString;
   }

   public int doEndTag() throws JspException {
      this.buildButton();
      String label = this.value;
      if(label == null && this.text != null) {
         label = this.text;
      }

      if(label == null || label.trim().length() < 1) {
         label = "Click";
      }

      StringBuffer results = new StringBuffer();
      results.append("<input ");
      results.append(" type=\"");
      if(this.getButtonType() == null) {
         results.append("button");
      } else {
         results.append(this.getButtonType());
      }

      results.append("\"");
      if(this.property != null) {
         results.append(" name=\"");
         results.append(this.property);
         if(this.indexed) {
            this.prepareIndex(results, (String)null);
         }

         results.append("\"");
      }

      if(this.accesskey != null) {
         results.append(" accesskey=\"");
         results.append(this.accesskey);
         results.append("\"");
      }

      if(this.tabindex != null) {
         results.append(" tabindex=\"");
         results.append(this.tabindex);
         results.append("\"");
      }

      results.append(" value=\"");
      results.append(label);
      results.append("\"");
      results.append(this.prepareEventHandlers());
      results.append(this.prepareStyles());
      if(!this.checkSecurity()) {
         results.append(" title=\"");
         results.append("This function has been disabled by your administrator");
         results.append("\"");
      }

      results.append(">");
      TagUtils.getInstance().write(this.pageContext, results.toString());
      this.setStyleClass((String)null);
      return 6;
   }

   protected void buildButton() {
      this.setStyleId(this.property);
      if(this.getStyleClass() == null) {
         if(this.checkSecurity()) {
            this.setStyleClass("coaButton");
         } else {
            this.setStyleClass("coaButtonDis");
         }
      }

      if(this.getOnmouseover() == null) {
         this.setOnmouseover("mouseOver(this)");
      }

      if(this.getOnmouseout() == null) {
         this.setOnmouseout("mouseOut(this)");
      }

      this.setDisabled(!this.checkSecurity());
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

   public void release() {
      super.release();
      this.mSecurityString = null;
   }
}
