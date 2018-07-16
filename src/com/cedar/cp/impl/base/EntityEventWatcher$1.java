// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.base;

import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.impl.base.EntityEventWatcher;

class EntityEventWatcher$1 extends Thread {

   // $FF: synthetic field
   final EntityEventWatcher this$0;


   EntityEventWatcher$1(EntityEventWatcher var1) {
      this.this$0 = var1;
   }

   public void run() {
      synchronized(EntityEventWatcher.accessMethod000(this.this$0)) {
         if(EntityEventWatcher.accessMethod100(this.this$0)) {
            EntityEventWatcher.accessMethod200(this.this$0);
         }
      }

      while(EntityEventWatcher.accessMethod100(this.this$0) && EntityEventWatcher.accessMethod300(this.this$0) != null) {
         EntityEventMessage em = (EntityEventMessage)EntityEventWatcher.accessMethod300(this.this$0).receive();
         if(em != null) {
            EntityEventWatcher.accessMethod400(this.this$0, em);
         }
      }

   }
}
