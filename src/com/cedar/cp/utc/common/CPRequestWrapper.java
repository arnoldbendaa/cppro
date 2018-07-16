// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CPRequestWrapper extends HttpServletRequestWrapper {

   private String mSingleEntryKey;


   public CPRequestWrapper(HttpServletRequest httpServletRequest) {
      super(httpServletRequest);
   }

   public CPRequestWrapper(HttpServletRequest httpServletRequest, String singleEntryKey) {
      super(httpServletRequest);
      this.mSingleEntryKey = singleEntryKey;
   }

   public String getQueryString() {
      String s = super.getQueryString();
      if(this.mSingleEntryKey != null && this.mSingleEntryKey.length() > 0 && s != null && s.indexOf(this.mSingleEntryKey) > -1) {
         s = "";
      }

      return s;
   }
}
