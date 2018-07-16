// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.message;

import com.cedar.cp.utc.common.CPForm;
import com.cedar.cp.utc.struts.message.MessageDTO;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

public class MessageNewForm extends CPForm {

   private MessageDTO message = new MessageDTO();
   private List messageType = new ArrayList();
   private List messageTypeLabel = new ArrayList();
   private List messageSendErrors;
   private int budgetCycleId;
   private int structureElementId;
   private boolean cascade = false;
   private boolean mContactApprover = false;
   private int structureId;
   private int mModelId;
   private int mUserId;
   private String mPageSource;
   private int[] mContactId;


   public MessageDTO getMessage() {
      return this.message;
   }

   public void setMessage(MessageDTO message) {
      this.message = message;
   }

   public List getMessageType() {
      return this.messageType;
   }

   public void setMessageType(List messageType) {
      this.messageType = messageType;
   }

   public List getMessageTypeLabel() {
      return this.messageTypeLabel;
   }

   public void setMessageTypeLabel(List messageTypeLabel) {
      this.messageTypeLabel = messageTypeLabel;
   }

   public List getMessageSendErrors() {
      return this.messageSendErrors;
   }

   public void setMessageSendErrors(List messageSendErrors) {
      this.messageSendErrors = messageSendErrors;
   }

   public int getBudgetCycleId() {
      return this.budgetCycleId;
   }

   public void setBudgetCycleId(int budgetCycleId) {
      this.budgetCycleId = budgetCycleId;
   }

   public int getStructureElementId() {
      return this.structureElementId;
   }

   public void setStructureElementId(int structureElementId) {
      this.structureElementId = structureElementId;
   }

   public boolean isCascade() {
      return this.cascade;
   }

   public void setCascade(boolean cascade) {
      this.cascade = cascade;
   }

   public int getStructureId() {
      return this.structureId;
   }

   public void setStructureId(int structureId) {
      this.structureId = structureId;
   }

   public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
      ArrayList list = new ArrayList();
      list.add("0");
      list.add("1");
      list.add("2");
      this.setMessageType(list);
      list = new ArrayList();
      list.add("System Message");
      list.add("eMail");
      list.add("eMail and System Message");
      this.setMessageTypeLabel(list);
   }

   public String getPageSource() {
      return this.mPageSource;
   }

   public void setPageSource(String pageSource) {
      this.mPageSource = pageSource;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public int[] getContactId() {
      return this.mContactId;
   }

   public void setContactId(int[] contactId) {
      this.mContactId = contactId;
   }

   public boolean isContactApprover() {
      return this.mContactApprover;
   }

   public void setContactApprover(boolean contactApprover) {
      this.mContactApprover = contactApprover;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public void setUserId(int userId) {
      this.mUserId = userId;
   }

   public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
      ActionErrors errors = new ActionErrors();
      if((this.getMessage().getToUser() == null || this.getMessage().getToUser().length() == 0) && (this.getMessage().getToDist() == null || this.getMessage().getToDist().length() == 0)) {
         this.checkForEmpty("User", "message.toUser", this.getMessage().getToUser(), errors);
      }

      if(this.getMessage() != null) {
         FormFile attatch = this.getMessage().getAttachment(0).getAttatch();
         if(attatch != null && attatch.getFileName().length() > 0) {
            long size = (long)attatch.getFileSize();
            if(size > (long)this.getCPSystemProperties(request).getMaxUploadFileSize()) {
               ActionMessage error = new ActionMessage("cp.commumication.message.error.file.size");
               errors.add("message.file.size.error", error);
            }
         }
      }

      return errors;
   }
}
