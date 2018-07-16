// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.ParamHelper;
import java.applet.Applet;

public class ParamHelperApplet extends ParamHelper {

   private static Applet sourceApplet;


   public ParamHelperApplet(Applet a) {
      sourceApplet = a;
   }

   public void setParamSpec(String[][] paramSpec) {
      super.setParamSpec(paramSpec);
      String[] args = new String[this.mParamSpec.length];

      for(int w = 0; w < this.mParamSpec.length; ++w) {
         this.setLongAndShortNames(this.mParamSpec[w][0]);
         String paramValue = sourceApplet.getParameter(this.mShortKeyword);
         if(paramValue == null && !this.mShortKeyword.equals(this.mFullKeyword)) {
            paramValue = sourceApplet.getParameter(this.mFullKeyword);
         }

         if(paramValue != null) {
            args[w] = this.mShortKeyword + paramValue;
         }
      }

      this.mArgs = args;
      this.mArgMatched = new boolean[this.mArgs.length];
   }
}
