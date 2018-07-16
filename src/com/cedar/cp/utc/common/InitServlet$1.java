// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.utc.common.InitServlet;

class InitServlet$1 extends Thread {

   // $FF: synthetic field
   final int val$userId;
   // $FF: synthetic field
   final int val$taskId;
   // $FF: synthetic field
   final int val$waitTime;
   // $FF: synthetic field
   final long val$originalSendTime;
   // $FF: synthetic field
   final String val$queueJNDIName;
   // $FF: synthetic field
   final String val$appServer;
   // $FF: synthetic field
   final String val$server;
   // $FF: synthetic field
   final int val$port;
   // $FF: synthetic field
   final InitServlet this$0;


   InitServlet$1(InitServlet var1, int var2, int var3, int var4, long var5, String var7, String var8, String var9, int var10) {
      this.this$0 = var1;
      this.val$userId = var2;
      this.val$taskId = var3;
      this.val$waitTime = var4;
      this.val$originalSendTime = var5;
      this.val$queueJNDIName = var7;
      this.val$appServer = var8;
      this.val$server = var9;
      this.val$port = var10;
   }

   public void run() {
      InitServlet.access$000(this.this$0, this.val$userId, this.val$taskId, this.val$waitTime, this.val$originalSendTime, this.val$queueJNDIName, this.val$appServer, this.val$server, this.val$port);
   }
}
