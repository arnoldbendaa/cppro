// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.ParamHelper;

public class ParamHelperAppl extends ParamHelper {

   public ParamHelperAppl(String[] args) {
      this.mArgs = args;
      if(this.mArgs.length > 0) {
         this.mArgMatched = new boolean[this.mArgs.length];
      }

   }
}
