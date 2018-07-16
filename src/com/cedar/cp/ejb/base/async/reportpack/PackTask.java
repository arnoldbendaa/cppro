// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.reportpack;

import com.cedar.cp.api.base.CPFileWrapper;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.report.definition.WebReportOption;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.message.MessageImpl;
import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
import com.cedar.cp.dto.report.distribution.DistributionPK;
import com.cedar.cp.dto.report.pack.PackTaskRequest;
import com.cedar.cp.dto.report.pack.ReportPackPK;
import com.cedar.cp.ejb.api.message.MessageHelperServer;
import com.cedar.cp.ejb.api.report.definition.ReportDefinitionEditorSessionServer;
import com.cedar.cp.ejb.api.report.pack.ReportPackEditorSessionServer;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.reportpack.PackTask$PackTaskCheckpoint;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionDAO;
import com.cedar.cp.ejb.impl.report.distribution.DistributionDAO;
import com.cedar.cp.ejb.impl.report.pack.ReportPackDAO;
import com.cedar.cp.ejb.impl.report.pack.ReportPackEVO;
import com.cedar.cp.ejb.impl.report.pack.ReportPackLinkEVO;
import com.cedar.cp.ejb.impl.report.task.ReportGroupingDAO;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import javax.naming.InitialContext;

public class PackTask extends AbstractTask {

   private transient InitialContext mInitialContext;


   public int getReportType() {
      return 0;
   }

   public String getEntityName() {
      return "PackTask";
   }

   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      this.mInitialContext = initialContext;
      PackTaskRequest request = (PackTaskRequest)this.getRequest();
      if(this.getCheckpoint() == null) {
         this.issuePackLines(request);
      } else {
         this.processResults(request);
      }

   }

   private void issuePackLines(PackTaskRequest request) throws Exception {
      PackTask$PackTaskCheckpoint cp = new PackTask$PackTaskCheckpoint();
      if(request.getWebOptions() == null) {
         ReportPackDAO wro = new ReportPackDAO();
         ReportPackPK taskIds = (ReportPackPK)request.getPackRef().getPrimaryKey();
         ReportPackEVO evo = wro.getDetails(taskIds, "<0>");
         ReportDefinitionDAO rdDAO = new ReportDefinitionDAO();
         DistributionDAO dDAO = new DistributionDAO();
         cp.setGroup(evo.getGroupAttachment());
         Iterator i$ = evo.getReportPackDefinitionDistributionList().iterator();

         while(i$.hasNext()) {
            ReportPackLinkEVO linkEVO = (ReportPackLinkEVO)i$.next();
            ReportDefinitionPK defPK = new ReportDefinitionPK(linkEVO.getReportDefId());
            DistributionPK disPk = new DistributionPK(linkEVO.getDistributionId());
            List taskIds1 = (new ReportPackEditorSessionServer(this.mInitialContext, false)).issueReportLine(this.getUserId(), rdDAO.getRef(defPK), dDAO.getRef(disPk), request.getOption(), cp.isGroup(), this.getTaskId());
            this.log("issued Report Pack line task - id=" + taskIds1);
         }
      } else {
         WebReportOption wro1 = request.getWebOptions();
         cp.setGroup(wro1.isGroup());
         List taskIds2 = (new ReportDefinitionEditorSessionServer(this.mInitialContext, false)).issueWebReport(this.getUserId(), wro1, this.getTaskId());
         this.log("issued Report Pack line task - id=" + taskIds2);
      }

      this.setCheckpoint(cp);
   }

   private void processResults(PackTaskRequest request) throws Exception {
      if(this.getCheckpoint().isGroup()) {
         this.log("sending messages");
         MessageHelperServer server = new MessageHelperServer(new InitialContext(), false);
         StringBuilder messageText = new StringBuilder("Please find your reports attached \n");
         if(request.getOption() != null && request.getOption().getMessageText() != null) {
            messageText.append("\n").append(request.getOption().getMessageText());
         }

         ReportGroupingDAO rgDAO = new ReportGroupingDAO();
         EntityList data = rgDAO.getGroupingData(this.getTaskId());
         int noOfMessages = data.getNumRows();

         for(int i = 0; i < noOfMessages; ++i) {
            int dType = ((BigDecimal)data.getValueAt(i, "DISTRIBUTION_TYPE")).intValue();
            int mType = ((BigDecimal)data.getValueAt(i, "MESSAGE_TYPE")).intValue();
            String messageID = data.getValueAt(i, "MESSAGE_ID").toString();
            EntityList reportFiles = (EntityList)data.getValueAt(i, "DATA");
            int noOfFiles = reportFiles.getNumRows();
            this.log("we have  attachments for " + messageID);
            if(noOfFiles > 0) {
               MessageImpl msg = new MessageImpl((Object)null);
               msg.addFromUser("System");
               msg.setSubject("CP Reports");
               msg.setContent(messageText.toString());
               if(dType == 1) {
                  msg.setMessageType(1);
                  msg.setToEmailAddress(messageID);
               } else if(dType == 0 || dType == 2) {
                  msg.setMessageType(mType);
                  msg.addToUser(messageID);
               }

               for(int j = 0; j < noOfFiles; ++j) {
                  String fileName = reportFiles.getValueAt(j, "FILE_NAME").toString();
                  byte[] messageData = (byte[])((byte[])reportFiles.getValueAt(j, "FILE_DATA"));
                  msg.addAttachment(new CPFileWrapper(messageData, fileName));
               }

               server.createNewMessage(msg);
            }
         }

         this.log("tidy up grouping table");
         rgDAO.deleteGroupings(this.getTaskId());
      }

      this.setCheckpoint((TaskCheckpoint)null);
   }

   public PackTask$PackTaskCheckpoint getCheckpoint() {
      return (PackTask$PackTaskCheckpoint)super.getCheckpoint();
   }
}
