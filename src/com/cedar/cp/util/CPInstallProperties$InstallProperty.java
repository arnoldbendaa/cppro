// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties;
import java.util.ArrayList;
import java.util.List;

public abstract class CPInstallProperties$InstallProperty {

   protected String mPromptText;
   protected String[] mAllowedValues;
   protected String mValue;
   protected List<String> mFeedback;
   // $FF: synthetic field
   final CPInstallProperties this$0;


   public CPInstallProperties$InstallProperty(CPInstallProperties var1, String promptText, String ... allowedValues) {
      this.this$0 = var1;
      this.mAllowedValues = new String[0];
      this.mValue = "";
      this.mFeedback = new ArrayList();
      this.mPromptText = promptText;
      this.mAllowedValues = allowedValues;
   }

   public String getPromptText() {
      return this.mPromptText;
   }

   public String[] getAllowedValues() {
      return this.mAllowedValues;
   }

   public String getAllowedValuesText() {
      StringBuilder sb = new StringBuilder();
      String[] arr$ = this.mAllowedValues;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String s = arr$[i$];
         if(sb.length() == 0) {
            sb.append(" (");
         } else {
            sb.append(",");
         }

         sb.append(s);
      }

      if(sb.length() > 0) {
         sb.append(")");
      }

      return sb.toString();
   }

   public String getValue() {
      return this.mValue;
   }

   public boolean setValue(String newValue) {
      if(newValue == null) {
         newValue = "";
      }

      if(this.mValue.equals(newValue)) {
         return false;
      } else {
         this.mValue = newValue;
         return true;
      }
   }

   public boolean checkProperty(String newValue) {
      this.mFeedback.clear();
      if(this.mAllowedValues.length > 0) {
         String[] arr$ = this.mAllowedValues;
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String s = arr$[i$];
            if(s.equalsIgnoreCase(newValue)) {
               return true;
            }
         }

         this.addFeedback("<" + newValue + "> is not a valid choice");
         return false;
      } else {
         return true;
      }
   }

   public List<String> getFeedback() {
      return this.mFeedback;
   }

   public void addFeedback(String feedback) {
      this.mFeedback.add(feedback);
   }
}
