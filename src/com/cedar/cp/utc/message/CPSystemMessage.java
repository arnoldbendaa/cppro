// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.message;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.message.MessageEditor;
import com.cedar.cp.api.message.MessageEditorSession;
import com.cedar.cp.api.message.MessagesProcess;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.message.MessageAttachmentDTO;
import com.cedar.cp.utc.struts.message.MessageDTO;
import java.util.ArrayList;
import java.util.Iterator;

public class CPSystemMessage {

   private CPContext mConn;
   private CPConnection mCPConnection;


   public CPSystemMessage(CPContext conn) {
      this.mConn = conn;
   }

   public CPSystemMessage(CPConnection conn) {
      this.mCPConnection = conn;
   }

   public CPConnection getCPConnection() {
      if(this.mCPConnection == null) {
         this.mCPConnection = this.mConn.getCPConnection();
      }

      return this.mCPConnection;
   }

   public void send(MessageDTO message, boolean outBox) throws Exception {
      if(this.mConn != null || this.mCPConnection != null) {
         if(message.getToUser() != null && message.getToUser().length() > 0 || message.getToDist() != null && message.getToDist().length() > 0 || message.getToUserEmailAddress() != null && message.getToUserEmailAddress().length() > 0) {
            CPConnection conn;
            if(this.mCPConnection != null) {
               conn = this.mCPConnection;
            } else {
               conn = this.mConn.getCPConnection();
            }

            MessagesProcess process = conn.getMessagesProcess();
            MessageEditorSession session = process.getMessageEditorSession((Object)null);
            MessageEditor editor = session.getMessageEditor();
            editor.setToEmailAddress(message.getToUserEmailAddress());
            editor.setFromEmailAddress(message.getFromUserEmailAddress());
            editor.setSubject(message.getSubject());
            editor.setContent(message.getContent());
            if(!message.getAttachments().isEmpty()) {
               Iterator toDist = message.getAttachments().iterator();

               while(toDist.hasNext()) {
                  MessageAttachmentDTO toDistType = (MessageAttachmentDTO)toDist.next();
                  if(toDistType.getAttatch() != null && toDistType.getAttatch().getFileData().length > 0) {
                     editor.addAttachment(toDistType.getAttachAsFile());
                  } else if(toDistType.getAttatchRead() != null && toDistType.getAttatchRead().getSize() > 0L) {
                     editor.addAttachment(toDistType.getAttatchRead());
                  }
               }
            }

            if(message.getOriginalMessageId() > 0L) {
               EntityList var13 = conn.getListHelper().getAttatchmentForMessage(message.getOriginalMessageId());

               for(int var15 = 0; var15 < var13.getNumRows(); ++var15) {
                  editor.addAttachment((String)var13.getValueAt(var15, "AttatchName"), (byte[])((byte[])var13.getValueAt(var15, "Attatch")));
               }
            }

            editor.setMessageType(message.getMessageType());
            String names;
            String[] var14;
            String[] var17;
            if(message.getToUser_VisID() != null && message.getToUser_VisID().length() > 0) {
               var14 = message.getToUser_VisID().split(";");
               var17 = var14;
               int inDistList = var14.length;

               for(int exDistList = 0; exDistList < inDistList; ++exDistList) {
                  names = var17[exDistList];
                  editor.addToUser(names.trim());
               }
            }

            if(message.getToDist() != null && message.getToDist().length() > 0) {
               var14 = message.getToDist().split(";");
               var17 = message.getToDistType().split(";");
               ArrayList var16 = new ArrayList();
               ArrayList var19 = new ArrayList();

               for(int var18 = 0; var18 < var14.length; ++var18) {
                  if(var17[var18].equals("internal")) {
                     var16.add(var14[var18]);
                  } else {
                     var19.add(var14[var18]);
                  }
               }

               names = null;
               int i;
               EntityList var20;
               if(var16.size() > 0) {
                  var20 = this.getCPConnection().getListHelper().getDistinctInternalDestinationUsers((String[])var16.toArray(new String[0]));

                  for(i = 0; i < var20.getNumRows(); ++i) {
                     editor.addToUser(var20.getValueAt(i, "User").toString());
                     editor.addToEmailAddress(var20.getValueAt(i, "EMailAddress"));
                  }
               }

               if(var19.size() > 0) {
                  var20 = this.getCPConnection().getListHelper().getDistinctExternalDestinationUsers((String[])var19.toArray(new String[0]));

                  for(i = 0; i < var20.getNumRows(); ++i) {
                     editor.addToUser(var20.getValueAt(i, "FullName").toString());
                     editor.addToEmailAddress(var20.getValueAt(i, "EMailAddress"));
                  }
               }
            }

            if(outBox) {
               editor.addFromUser(message.getFromUser_VisID());
            }

            process.createNewMessage(editor);
            process.terminateSession(session);
         }

      }
   }
}
