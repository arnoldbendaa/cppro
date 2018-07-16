// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;


public class InputStatus {

   private static boolean mInsertMode = true;


   public static boolean isInsertModel() {
      return mInsertMode;
   }

   public static synchronized void toggleInsertMode() {
      mInsertMode = !mInsertMode;
   }

}
