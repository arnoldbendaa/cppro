// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.message;

import com.cedar.cp.api.base.CPFileWrapper;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.message.Message;
import com.cedar.cp.ejb.impl.message.MessageAttatchEVO;
import com.cedar.cp.util.Log;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class NewBackOfficeEmailSender {

   private String mUser;
   private String mPassword;
   private Session mSession;
   private transient Log mLog = new Log(this.getClass());
   private static MimetypesFileTypeMap sMimeTypes = new MimetypesFileTypeMap();


   public NewBackOfficeEmailSender(EntityList eList) {
      String host = "";

      for(int mailprops = 0; mailprops < eList.getNumRows(); ++mailprops) {
         String prop = eList.getValueAt(mailprops, "SystemProperty").toString();
         if(prop.endsWith("HOST")) {
            host = eList.getValueAt(mailprops, "Value").toString().trim();
         } else if(prop.endsWith("USER")) {
            this.mUser = eList.getValueAt(mailprops, "Value").toString().trim();
         } else if(prop.endsWith("PASSWORD")) {
            this.mPassword = eList.getValueAt(mailprops, "Value").toString().trim();
         }
      }

      Properties props = new Properties();
      props.put("mail.host", host);
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.auth", "true");
      if(this.mUser != null && this.mUser.length() > 0) {
          props.put("mail.user", this.mUser);
       }
      if(this.mPassword != null && this.mPassword.length() > 0) {
          props.put("mail.password", this.mPassword);
       }

      props.put("mail.debug", System.getProperty("mail.debug", "false"));
      this.mSession = Session.getInstance(props, new Authenticator(){
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication(mUser, mPassword);
          }
      });
   }

   public void send(Message message) throws Exception {
      MimeMessage msg = new MimeMessage(this.mSession);

      try {
         String content = message.getFromEmailAddress();
         InternetAddress from_addr = new InternetAddress(content);
         msg.setFrom(from_addr);
      } catch (Exception var13) {
         this.mLog.warn("send", "error setting from address");
      }

      try {
         String[] var15 = message.getToEmailAddress().split(";");
         String[] messagePart = var15;
         int i$ = var15.length;

         for(int o = 0; o < i$; ++o) {
            String wrapper = messagePart[o];
            if(wrapper != null && wrapper.trim().length() > 0 && !wrapper.equals("null")) {
               try {
                  InternetAddress addr = new InternetAddress(wrapper);
                  msg.addRecipient(RecipientType.TO, addr);
               } catch (Exception var12) {
                  this.mLog.warn("send", "error adding to address for user " + wrapper);
               }
            }
         }
      } catch (Exception var14) {
         this.mLog.warn("send", "error adding to address\'s");
      }

      msg.setSubject(message.getSubject());
      msg.setSentDate(Calendar.getInstance().getTime());
      MimeMultipart var16 = new MimeMultipart();
      MimeBodyPart var17 = new MimeBodyPart();
      var17.setDataHandler(new DataHandler(new ByteArrayDataSource(message.getContent().replaceAll("\n", "<br/>"), "text/html")));
      var16.addBodyPart(var17);
      if(!message.getAttachments().isEmpty()) {
         Iterator var18 = message.getAttachments().iterator();

         while(var18.hasNext()) {
            Object var19 = var18.next();
            CPFileWrapper var20 = null;
            if(var19 instanceof MessageAttatchEVO) {
               MessageAttatchEVO currAttachment = (MessageAttatchEVO)var19;
               if(currAttachment.getAttatch() != null && currAttachment.getAttatch().length > 0) {
                  var20 = new CPFileWrapper(currAttachment.getAttatch(), currAttachment.getAttatchName());
               }
            } else if(var19 instanceof CPFileWrapper) {
               var20 = (CPFileWrapper)var19;
            } else {
               this.mLog.debug("What can we do now : " + var19);
            }

            if(var20 != null) {
               MimeBodyPart var21 = new MimeBodyPart();
               DataHandler handle = new DataHandler(new ByteArrayDataSource(var20.getData(), sMimeTypes.getContentType(var20.getName())));
               var21.setFileName(var20.getName());
               var21.setDataHandler(handle);
               var16.addBodyPart(var21);
            }
         }
      }

      msg.setContent(var16);
      if(msg.getAllRecipients() != null && msg.getAllRecipients().length > 0) {
         Transport.send(msg);
      }

   }

}
