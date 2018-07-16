// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:31:44
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.idm.taglibs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class IsUserInRoleTag extends TagSupport {

   private String mRole;
   private boolean mValue = true;


   public final int doStartTag() throws JspException {
      HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
      if(this.mRole != null) {
         String[] roles = this.mRole.split(",");
         String[] arr$ = roles;
         int len$ = roles.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String role = arr$[i$];
            boolean result = request.isUserInRole(role);
            if(role == null || role.trim().length() == 0 || this.mValue == result) {
               return 1;
            }
         }
      }

      return 0;
   }

   public void setValue(boolean value) {
      this.mValue = value;
   }

   public void setRole(String role) {
      this.mRole = role;
   }
}
