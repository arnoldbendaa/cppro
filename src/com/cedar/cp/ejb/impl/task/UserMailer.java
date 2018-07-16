// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.ejb.impl.message.ServerSideUserMailer;
import com.cedar.cp.ejb.impl.task.UserMailerOwner;
import com.cedar.cp.ejb.impl.user.UserEVO;
import java.util.Collection;

public class UserMailer {

   private UserEVO mUserEVO;
   private CPConnection mCPConnection;
   private UserMailerOwner mOwner;


   public UserMailer(UserMailerOwner owner) {
      this.mOwner = owner;
   }

   public void sendMessage(Collection users, String subject, String content) throws Exception {
      ServerSideUserMailer mailer = new ServerSideUserMailer(this.getUserId());
      mailer.sendUsersMailMessage(users, subject, content);
   }

   public void sendUserMailMessage(String subject, String content) throws Exception {
      ServerSideUserMailer mailer = new ServerSideUserMailer(this.getUserId());
      mailer.sendUserMailMessage(subject, content);
   }

   public void closeDown() {
      if(this.mCPConnection != null) {
         this.mCPConnection.close();
         this.mCPConnection = null;
      }

      this.mUserEVO = null;
   }

   private int getUserId() {
      return this.mOwner.getUserId();
   }
}
