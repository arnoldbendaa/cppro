// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.CPInstallProperties;
import com.cedar.cp.util.CPInstallProperties$InstallProperty;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class CPInstallProperties$1 extends Thread {

   // $FF: synthetic field
   final String[] val$threadCmd;
   // $FF: synthetic field
   final CPInstallProperties$InstallProperty val$ip;
   // $FF: synthetic field
   final CPInstallProperties this$0;


   CPInstallProperties$1(CPInstallProperties var1, String[] var2, CPInstallProperties$InstallProperty var3) {
      this.this$0 = var1;
      this.val$threadCmd = var2;
      this.val$ip = var3;
   }

   public void run() {
      ProcessBuilder pb = new ProcessBuilder(this.val$threadCmd);
      pb.redirectErrorStream(true);
      Process process = null;
      boolean var23 = false;

      InputStream is;
      InputStreamReader isr;
      BufferedReader br;
      StringBuffer sb;
      String line;
      label207: {
         label208: {
            try {
               var23 = true;
               process = pb.start();
               process.waitFor();
               var23 = false;
               break label207;
            } catch (IOException var28) {
               if(this.val$ip != null) {
                  this.val$ip.addFeedback("couldn\'t execute " + this.val$threadCmd[0]);
                  var23 = false;
                  break label208;
               }

               var23 = false;
               break label208;
            } catch (InterruptedException var29) {
               var23 = false;
            } finally {
               if(var23) {
                  InputStream is1 = process.getInputStream();
                  InputStreamReader isr1 = new InputStreamReader(is1);
                  BufferedReader br1 = new BufferedReader(isr1);
                  StringBuffer sb1 = new StringBuffer();

                  String line1;
                  try {
                     while((line1 = br1.readLine()) != null) {
                        sb1.append(line1).append(' ');
                     }
                  } catch (IOException var24) {
                     if(this.val$ip != null) {
                        this.val$ip.addFeedback("couldn\'t execute " + this.val$threadCmd[0]);
                     }
                  }

                  CPInstallProperties.accessMethod1002(this.this$0, sb1.toString());
                  process.destroy();
               }
            }

            is = process.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            sb = new StringBuffer();

            try {
               while((line = br.readLine()) != null) {
                  sb.append(line).append(' ');
               }
            } catch (IOException var25) {
               if(this.val$ip != null) {
                  this.val$ip.addFeedback("couldn\'t execute " + this.val$threadCmd[0]);
               }
            }

            CPInstallProperties.accessMethod1002(this.this$0, sb.toString());
            process.destroy();
            return;
         }

         is = process.getInputStream();
         isr = new InputStreamReader(is);
         br = new BufferedReader(isr);
         sb = new StringBuffer();

         try {
            while((line = br.readLine()) != null) {
               sb.append(line).append(' ');
            }
         } catch (IOException var26) {
            if(this.val$ip != null) {
               this.val$ip.addFeedback("couldn\'t execute " + this.val$threadCmd[0]);
            }
         }

         CPInstallProperties.accessMethod1002(this.this$0, sb.toString());
         process.destroy();
         return;
      }

      is = process.getInputStream();
      isr = new InputStreamReader(is);
      br = new BufferedReader(isr);
      sb = new StringBuffer();

      try {
         while((line = br.readLine()) != null) {
            sb.append(line).append(' ');
         }
      } catch (IOException var27) {
         if(this.val$ip != null) {
            this.val$ip.addFeedback("couldn\'t execute " + this.val$threadCmd[0]);
         }
      }

      CPInstallProperties.accessMethod1002(this.this$0, sb.toString());
      process.destroy();
   }
}
