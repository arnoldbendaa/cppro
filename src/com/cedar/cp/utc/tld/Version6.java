// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.tld;

import javax.servlet.jsp.JspException;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.bean.MessageTag;

public class Version6 extends MessageTag {

   private String mMode;


   public String getMode() {
      return this.mMode;
   }

   public void setMode(String mode) {
      this.mMode = mode;
   }

   public String getCPValue(String s) {
      StringBuilder value = new StringBuilder();
      if(this.mMode != null && this.mMode.equalsIgnoreCase("dev")) {
         value.append(s.substring(0, s.lastIndexOf(".")));
         value.append(".");
         value.append(System.currentTimeMillis());
      } else {
         value.append(s);
      }

      return value.toString();
   }

   public int doStartTag() throws JspException {
      String key = this.key;
      if(key == null) {
         Object args = TagUtils.getInstance().lookup(this.pageContext, this.name, this.property, this.scope);
         if(args != null && !(args instanceof String)) {
            JspException message1 = new JspException(messages.getMessage("message.property", key));
            TagUtils.getInstance().saveException(this.pageContext, message1);
            throw message1;
         }

         key = (String)args;
      }

      Object[] args1 = new Object[]{this.arg0, this.arg1, this.arg2, this.arg3, this.arg4};
      String message = TagUtils.getInstance().message(this.pageContext, this.bundle, this.localeKey, key, args1);
      if(message == null) {
         JspException e = new JspException(messages.getMessage("message.message", "\"" + key + "\""));
         TagUtils.getInstance().saveException(this.pageContext, e);
         throw e;
      } else {
         TagUtils.getInstance().write(this.pageContext, this.getCPValue(message));
         return 0;
      }
   }
}
