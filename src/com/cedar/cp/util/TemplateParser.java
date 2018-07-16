// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import java.util.StringTokenizer;

public abstract class TemplateParser {

   private String mStartDelim;
   private String mEndDelim;
   private String mTemplate;
   private String mDelims;


   public TemplateParser(String template) {
      this.mTemplate = template;
      this.mStartDelim = "{";
      this.mEndDelim = "}";
      this.mDelims = this.mStartDelim + this.mEndDelim + " \t\n\r";
   }

   public TemplateParser(String startDelim, String endDelim, String template) {
      this.mTemplate = template;
      this.mStartDelim = startDelim;
      this.mEndDelim = endDelim;
      this.mDelims = this.mStartDelim + this.mEndDelim + " \t\n\r";
   }

   public abstract String parseToken(String var1);

   public String parse() {
      StringBuffer sb = new StringBuffer();
      StringTokenizer st = new StringTokenizer(this.mTemplate, this.mDelims, true);

      while(st.hasMoreTokens()) {
         String token = st.nextToken();
         if(token.equals(this.mStartDelim)) {
            token = st.nextToken();
            sb.append(this.parseToken(token));
            token = st.nextToken();
            if(!token.equals(this.mEndDelim)) {
               throw new IllegalStateException("Unmatched " + this.mStartDelim + this.mEndDelim + " pair");
            }
         } else {
            sb.append(token);
         }
      }

      return sb.toString();
   }
}
