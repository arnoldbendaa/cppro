// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import java.io.File;
import java.io.IOException;

public class CommandRunner {

   public static void main(String[] arg) throws IOException {
      if(arg.length == 0 || arg.length > 2) {
         usage();
      }

      if(arg.length == 1) {
         runcommand(arg);
      }

      if(arg.length == 2) {
         runcommandfrom(arg);
      }

   }

   private static void runcommand(String[] arg) throws IOException {
      String command = arg[0];
      System.out.println(" before runtime");
      Runtime javaRunTime = Runtime.getRuntime();
      Process mProcess = javaRunTime.exec(command, (String[])null);
      System.out.println("after exec : " + mProcess.exitValue());
   }

   private static void runcommandfrom(String[] arg) throws IOException {
      String command = arg[0];
      String orion_dir = arg[1];
      File f = new File(orion_dir);
      if(!f.exists()) {
         System.out.println("Orion Dir does not exist");
      } else {
         System.out.println(" before runtime");
         Runtime javaRunTime = Runtime.getRuntime();
         Process mProcess = javaRunTime.exec(command, (String[])null, f);
         System.out.println("after exec : " + mProcess.exitValue());
      }
   }

   public static void usage() {
      System.out.println("CommandRunner [command, {location to run from} ] ");
   }
}
