// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.message;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.message.MessageImpl;
import com.cedar.cp.dto.systemproperty.SystemPropertyELO;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.ejb.api.message.MessageHelperServer;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import com.cedar.cp.ejb.impl.user.UserDAO;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.naming.InitialContext;

public class ServerSideUserMailer {

   private Log mLog;
   private int mUserId;
   private boolean mAutonomous;


   public ServerSideUserMailer(int userid) {
      this(userid, false);
   }

   public ServerSideUserMailer(int userid, boolean autonomous) {
      this.mLog = new Log(this.getClass());
      this.mUserId = userid;
      this.mAutonomous = autonomous;
   }

   public void sendUserMailMessage(String subject, String content) {
      try {
         MessageHelperServer e = new MessageHelperServer(new InitialContext(), false);
         MessageImpl msg = new MessageImpl((Object)null);
         msg.addFromUser("System");
         msg.addToUser(this.getUserEVO().getName());
         msg.setSubject(subject);
         msg.setContent(content);
         msg.setMessageType(this.getMailType());
         if(this.mAutonomous) {
            e.autonomousInsert(msg);
         } else {
            e.createNewMessage(msg);
         }
      } catch (Exception var5) {
         this.mLog.error("sendUserMailMessage", "Unable to send server side message " + var5.getMessage());
      }

   }

   public void sendUsersMailMessage(Collection users, String subject, String content) {
      try {
         MessageHelperServer e = new MessageHelperServer(new InitialContext(), false);
         MessageImpl msg = new MessageImpl((Object)null);
         msg.addFromUser(this.getUserEVO().getName());
         Iterator uIter = this.convertToUserIds(users).iterator();

         while(uIter.hasNext()) {
            msg.addToUser((String)uIter.next());
         }

         msg.setSubject(subject);
         msg.setContent(content);
         msg.setMessageType(this.getMailType());
         e.autonomousInsert(msg);
      } catch (Exception var7) {
         this.mLog.error("sendUserMailMessage", "Unable to send server side message " + var7.getMessage());
      }

   }

   private Collection convertToUserIds(Collection e) throws ValidationException {
      ArrayList r = new ArrayList();
      UserDAO userDAO = new UserDAO();
      Iterator uIter = e.iterator();

      while(uIter.hasNext()) {
         Object u = uIter.next();
         UserEVO evo;
         if(u instanceof UserRefImpl) {
            evo = userDAO.getDetails(((UserRefImpl)u).getUserPK(), "");
            r.add(evo.getName());
         } else if(u instanceof UserPK) {
            evo = userDAO.getDetails((UserPK)u, "");
            r.add(evo.getName());
         } else {
            if(!(u instanceof String)) {
               throw new IllegalArgumentException("Unexpected user id object:" + u);
            }

            r.add(u);
         }
      }

      return r;
   }

   private UserEVO getUserEVO() throws ValidationException {
      return (new UserDAO()).getDetails(new UserPK(this.mUserId), "");
   }

   private int getMailType() {
      SystemPropertyDAO spDAO = new SystemPropertyDAO();
      SystemPropertyELO spELO = spDAO.getSystemProperty("WEB: Alert message type");
      spELO.next();
      return Integer.parseInt(spELO.getValue());
   }
}
