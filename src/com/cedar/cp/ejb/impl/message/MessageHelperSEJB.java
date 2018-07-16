// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.message;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.message.MessageEditorSessionCSO;
import com.cedar.cp.dto.message.MessageImpl;
import com.cedar.cp.dto.systemproperty.AllMailPropsELO;
import com.cedar.cp.dto.systemproperty.SystemPropertyELO;
import com.cedar.cp.dto.user.UserMessageAttributesForNameELO;
import com.cedar.cp.ejb.api.message.MessageEditorSessionServer;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.message.NewBackOfficeEmailSender;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import com.cedar.cp.ejb.impl.user.UserDAO;
import com.cedar.cp.util.Log;
import java.rmi.RemoteException;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class MessageHelperSEJB extends AbstractSession implements SessionBean {

   private transient Log mLog = new Log(this.getClass());


   public Object createNewMessage(MessageImpl data) {
      try {
         if(data.getMessageType() != 0) {
            UserDAO dao = new UserDAO();
            StringBuffer mailAddress = null;
            Iterator e;
            if(data.getToEmailAddress() == null || data.getToEmailAddress().length() == 0) {
               if(data.getToUsers() != null) {
                  e = data.getToUsers().iterator();
                  mailAddress = new StringBuffer();

                  while(e.hasNext()) {
                     UserMessageAttributesForNameELO doSystemFrom = dao.getUserMessageAttributesForName((String)e.next());
                     if(doSystemFrom.hasNext()) {
                        doSystemFrom.next();
                        if(doSystemFrom.getEMailAddress() != null && doSystemFrom.getEMailAddress().length() > 0) {
                           if(mailAddress.length() > 0 && mailAddress.lastIndexOf(";") != mailAddress.length()) {
                              mailAddress.append(";");
                           }

                           mailAddress.append(doSystemFrom.getEMailAddress());
                        }
                     }
                  }
               }

               data.setToEmailAddress(mailAddress.toString());
            }

            boolean doSystemFrom1 = false;
            if(data.getFromEmailAddress() == null || data.getFromEmailAddress().length() == 0) {
               if(data.getFromUsers() != null && data.getFromUsers().size() > 0) {
                  e = data.getFromUsers().iterator();
                  mailAddress = new StringBuffer();

                  while(e.hasNext()) {
                     UserMessageAttributesForNameELO spDao = dao.getUserMessageAttributesForName((String)e.next());
                     if(spDao.hasNext()) {
                        spDao.next();
                        if(spDao.getEMailAddress() != null && spDao.getEMailAddress().length() > 0) {
                           if(mailAddress.length() > 0 && mailAddress.lastIndexOf(";") != mailAddress.length()) {
                              mailAddress.append(";");
                           }

                           mailAddress.append(spDao.getEMailAddress());
                        }
                     }
                  }

                  if(mailAddress.length() > 0) {
                     data.setFromEmailAddress(mailAddress.toString());
                  } else {
                     doSystemFrom1 = true;
                  }
               } else {
                  doSystemFrom1 = true;
               }
            }

            SystemPropertyDAO spDao1 = null;
            if(doSystemFrom1) {
               spDao1 = new SystemPropertyDAO();
               SystemPropertyELO eList = spDao1.getSystemProperty("WEB: Alert from mail address");
               eList.next();
               if(eList.getValue().length() > 0) {
                  data.setFromEmailAddress(eList.getValue());
               } else {
                  data.setFromEmailAddress("cp@coasolutions.com");
               }
            }

            if(spDao1 == null) {
               spDao1 = new SystemPropertyDAO();
            }

            AllMailPropsELO eList1 = spDao1.getAllMailProps();
            NewBackOfficeEmailSender sender = new NewBackOfficeEmailSender(eList1);
            sender.send(data);
         }
      } catch (Exception var10) {
         this.mLog.warn("completeInsertSetup", "error tring to send email " + var10.getMessage());
      } catch (Throwable var11) {
         var11.printStackTrace();
      }

      try {
         if(data.getMessageType() != 1) {
            MessageEditorSessionServer e1 = new MessageEditorSessionServer(this.getInitialContext(), false);
            return e1.insertBackDoor(data);
         }
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      return null;
   }

   public Object autonomousInsert(MessageEditorSessionCSO cso) throws ValidationException, EJBException {
      return this.createNewMessage(cso.getEditorData());
   }
   
   public void emptyFolder(int folderType, String userId) {
     MessageDAO dao = new MessageDAO();
     dao.emptyFolder(folderType, userId);
   }
   
   public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException {}

   public void ejbRemove() throws EJBException, RemoteException {}

   public void ejbActivate() throws EJBException, RemoteException {}

   public void ejbPassivate() throws EJBException, RemoteException {}

   public void ejbCreate() throws EJBException {}
}
