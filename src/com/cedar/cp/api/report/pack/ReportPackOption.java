// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.pack;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ReportPackOption implements Serializable {

   private String mMessageText;
   private String mParamText;
   private String mDelim = ":";


   public ReportPackOption() {}

   public ReportPackOption(String messageText, String paramText) {
      this.mMessageText = messageText;
      this.mParamText = paramText;
   }

   public String getMessageText() {
      return this.mMessageText;
   }

   public void setMessageText(String messageText) {
      this.mMessageText = messageText;
   }

   public String getParamText() {
      return this.mParamText;
   }

   public void setParamText(String paramText) {
      this.mParamText = paramText;
   }

   public String getDelim() {
      return this.mDelim;
   }

   public void setDelim(String delim) {
      this.mDelim = delim;
   }

   public Map<String, String> getParamMap() {
      if(this.mParamText != null && this.mParamText.length() != 0) {
         String[] params = this.mParamText.split(this.getDelim());
         if(params.length == 0) {
            return Collections.EMPTY_MAP;
         } else {
            HashMap paramMap = new HashMap();
            String[] arr$ = params;
            int len$ = params.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               String s = arr$[i$];
               String[] values = s.split("=");
               if(values.length == 2) {
                  paramMap.put(values[0].trim(), values[1].trim());
               }
            }

            return paramMap;
         }
      } else {
         return Collections.EMPTY_MAP;
      }
   }
}
