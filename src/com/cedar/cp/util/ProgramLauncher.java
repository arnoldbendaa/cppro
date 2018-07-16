// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import java.io.IOException;

public class ProgramLauncher {

   private static final String WIN_ID = "Windows";


   public static Process runProcess(String program, String arg0) throws IOException {
      if(isWindowsPlatform()) {
         String[] cmdArray = new String[]{"cmd", "/C", "start", program, arg0};
         return Runtime.getRuntime().exec(cmdArray);
      } else {
         return null;
      }
   }

   public static boolean isWindowsPlatform() {
      String os = System.getProperty("os.name");
      return os != null && os.startsWith("Windows");
   }
}
