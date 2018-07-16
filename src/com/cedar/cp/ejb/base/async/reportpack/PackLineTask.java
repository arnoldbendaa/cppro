// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.reportpack;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPFileWrapper;
import com.cedar.cp.api.base.CPZipFileWrapper;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.report.distribution.DistributionDetail;
import com.cedar.cp.api.report.distribution.DistributionDetailUser;
import com.cedar.cp.api.report.distribution.DistributionDetails;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.dimension.BudgetLocationElementForModelELO;
import com.cedar.cp.dto.dimension.ReportLeavesForParentELO;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.calendar.CalendarInfoImpl;
import com.cedar.cp.dto.message.MessageImpl;
import com.cedar.cp.dto.model.BudgetUsersForNodeELO;
import com.cedar.cp.dto.model.cc.CcDeploymentXMLFormTypeELO;
import com.cedar.cp.dto.report.destination.external.AllUsersForExternalDestinationIdELO;
import com.cedar.cp.dto.report.destination.internal.AllUsersForInternalDestinationIdELO;
import com.cedar.cp.dto.report.distribution.DistributionDetailImpl;
import com.cedar.cp.dto.report.distribution.DistributionDetailsForVisIdELO;
import com.cedar.cp.dto.report.distribution.DistributionDetailsImpl;
import com.cedar.cp.dto.report.pack.PackLineTaskRequest;
import com.cedar.cp.dto.report.task.ReportGroupingImpl;
import com.cedar.cp.dto.systemproperty.SystemPropertyELO;
import com.cedar.cp.dto.user.UserMessageAttributesForIdELO;
import com.cedar.cp.ejb.api.message.MessageHelperServer;
import com.cedar.cp.ejb.api.report.task.ReportGroupingEditorSessionServer;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.reportpack.CellCalcSummaryReport;
import com.cedar.cp.ejb.base.async.reportpack.PackLineTask$PackLineTaskCheckpoint;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.BudgetUserDAO;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcUtils;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationDAO;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationDAO;
import com.cedar.cp.ejb.impl.report.distribution.DistributionDAO;
import com.cedar.cp.ejb.impl.report.distribution.DistributionLinkEVO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import com.cedar.cp.ejb.impl.user.UserDAO;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.naming.InitialContext;

public class PackLineTask extends AbstractTask {

   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      PackLineTaskRequest request = (PackLineTaskRequest)this.getRequest();
      if(this.getCheckpoint() == null) {
         this.setCheckpoint(new PackLineTask$PackLineTaskCheckpoint());
      }

      this.getCheckpoint().setMaxBatchSize(SystemPropertyHelper.queryIntegerSystemProperty((Connection)null, "SYS: Report Pack batch size", 100));
      this.doWork(request);
      if(this.getCheckpoint().getListIndex() >= request.getRespArea().size() - 1) {
         Map filesToSend;
         if(request.isGroup() && this.getOriginalTaskId() > 0) {
            filesToSend = this.getCheckpoint().getGroup();
            if(filesToSend != null && !filesToSend.isEmpty()) {
               ReportGroupingEditorSessionServer server1 = new ReportGroupingEditorSessionServer(new InitialContext(), false);
               Iterator messageText1 = this.getCheckpoint().getGroup().keySet().iterator();

               while(messageText1.hasNext()) {
                  DistributionDetailUser i$2 = (DistributionDetailUser)messageText1.next();
                  ReportGroupingImpl ddu1 = new ReportGroupingImpl((Object)null);
                  ddu1.setParentTaskId(this.getOriginalTaskId());
                  ddu1.setTaskId(this.getTaskId());
                  ddu1.setDistributionType(i$2.getDistributionType());
                  ddu1.setMessageType(i$2.getMessageType());
                  ddu1.setMessageId(i$2.getId());
                  Iterator msg1 = ((List)this.getCheckpoint().getGroup().get(i$2)).iterator();

                  while(msg1.hasNext()) {
                     CPFileWrapper i$3 = (CPFileWrapper)msg1.next();
                     ddu1.addFile(i$3);
                  }

                  server1.insert(ddu1);
               }
            }
         } else {
            filesToSend = this.getCheckpoint().getGroup();
            if(filesToSend != null && !filesToSend.isEmpty()) {
               MessageHelperServer server = new MessageHelperServer(new InitialContext(), false);
               StringBuilder messageText = new StringBuilder("Please find your reports attached \n");
               if(request.getOption() != null && request.getOption().getMessageText() != null) {
                  messageText.append("\n").append(request.getOption().getMessageText());
               }

               Iterator i$ = this.getCheckpoint().getGroup().keySet().iterator();

               while(i$.hasNext()) {
                  DistributionDetailUser ddu = (DistributionDetailUser)i$.next();
                  MessageImpl msg = new MessageImpl((Object)null);
                  msg.addFromUser("System");
                  msg.setSubject("CP Reports");
                  msg.setContent(messageText.toString());
                  msg.setMessageType(ddu.getMessageType());
                  msg.addToUser(ddu.getId());
                  Iterator i$1 = ((List)this.getCheckpoint().getGroup().get(ddu)).iterator();

                  while(i$1.hasNext()) {
                     CPFileWrapper file = (CPFileWrapper)i$1.next();
                     msg.addAttachment(new CPFileWrapper(file.getData(), file.getName()));
                  }

                  server.createNewMessage(msg);
               }
            }
         }

         this.setCheckpoint((TaskCheckpoint)null);
      }

   }

   public void doWork(PackLineTaskRequest request) throws Exception {
      switch(request.getReportType()) {
      case 1:
         this.doFormProcessing(request);
         break;
      case 2:
         this.doMappedProcessing(request);
         break;
      case 3:
         this.doCalculationReportType(request);
         break;
      case 4:
         this.doSummaryCalculationReportType(request);
         break;
      default:
         throw new UnsupportedOperationException("can not process report of type " + request.getReportType());
      }

   }

   private void doFormProcessing(PackLineTaskRequest request) throws Exception {
      Class wrapper = Class.forName("com.cedar.cp.utc.task.FormReportRunner");
      Constructor wrapperConst = wrapper.getConstructor(new Class[]{CPConnection.class});
      Method m1 = wrapper.getMethod("doWork", new Class[]{Map.class, String.class, String.class, Integer.TYPE, Integer.TYPE, byte[].class});
      Method m2 = wrapper.getMethod("getFile", new Class[0]);
      Method m3 = wrapper.getMethod("getHeader", new Class[0]);
      Map selection = request.getSelectionCriteria();
      List respArea = request.getRespArea();

      int i;
      while((i = this.getCheckpoint().getNextIndex()) < respArea.size()) {
         EntityList eList = (EntityList)respArea.get(i);
         EntityRef locationRef = (EntityRef)eList.getValueAt(0, "StructureElement");
         StructureElementPK pk = (StructureElementPK)locationRef.getPrimaryKey();
         this.log("processing form  : " + request.getFormId() + " for : " + locationRef.getNarrative());
         selection.put(Integer.valueOf(0), Integer.valueOf(pk.getStructureElementId()));
         Object wrapperInst = wrapperConst.newInstance(new Object[]{this.getCPConnection()});
         m1.invoke(wrapperInst, new Object[]{selection, request.getModelId(), request.getDataType(), Integer.valueOf(request.getFormId()), Integer.valueOf(request.getDepth()), request.getTemplate()});
         CPFileWrapper resultFile = (CPFileWrapper)m2.invoke(wrapperInst, new Object[0]);
         String resultHeaderText = (String)m3.invoke(wrapperInst, new Object[0]);
         if(resultFile == null) {
            this.log("empty file for :" + locationRef.getNarrative());
         } else {
            this.processResult(pk.getStructureElementId(), resultFile, resultHeaderText, request);
         }

         if(this.getCheckpoint().shouldCheckpoint()) {
            break;
         }
      }

   }

   private void doMappedProcessing(PackLineTaskRequest request) throws Exception {
      Class wrapper = Class.forName("com.cedar.cp.utc.task.MappedReportRunner");
      Constructor wrapperConst = wrapper.getConstructor(new Class[]{CPConnection.class});
      Method m1 = wrapper.getMethod("doWork", new Class[]{EntityList.class, Map.class, byte[].class});
      Method m2 = wrapper.getMethod("getFile", new Class[0]);
      List respArea = request.getRespArea();

      int i;
      while((i = this.getCheckpoint().getNextIndex()) < respArea.size()) {
         EntityList eList = (EntityList)respArea.get(i);
         EntityRef locationRef = (EntityRef)eList.getValueAt(0, "StructureElement");
         StructureElementPK pk = (StructureElementPK)locationRef.getPrimaryKey();
         this.log("processing mapped report for " + locationRef.getNarrative());
         Object wrapperInst = wrapperConst.newInstance(new Object[]{this.getCPConnection()});
         if(request.getOption() == null) {
            m1.invoke(wrapperInst, new Object[]{eList, null, request.getTemplate()});
         } else {
            m1.invoke(wrapperInst, new Object[]{eList, request.getOption().getParamMap(), request.getTemplate()});
         }

         CPFileWrapper resultFile = (CPFileWrapper)m2.invoke(wrapperInst, new Object[0]);
         if(resultFile == null) {
            this.log("empty file");
         } else {
            this.processResult(pk.getStructureElementId(), resultFile, "Mapping Report", request);
         }

         if(this.getCheckpoint().shouldCheckpoint()) {
            break;
         }
      }

   }

   private void doCalculationReportType(PackLineTaskRequest request) throws Exception {
      Class wrapper = Class.forName("com.cedar.cp.utc.task.CalculationReportRunner");
      Constructor wrapperConst = wrapper.getConstructor(new Class[]{CPConnection.class});
      Method m1 = wrapper.getMethod("doWork", new Class[]{String.class, CalendarInfo.class, Integer.TYPE, Integer.TYPE, List.class, Integer.TYPE, byte[].class, Map.class});
      Method m2 = wrapper.getMethod("getFile", new Class[0]);
      CalendarInfoImpl calInfo = (new StructureElementDAO()).getCalendarInfoForModel((new Integer(request.getModelId())).intValue());
      List respArea = request.getRespArea();

      int i;
      while((i = this.getCheckpoint().getNextIndex()) < respArea.size()) {
         EntityList eList = (EntityList)respArea.get(i);
         EntityRef locationRef = (EntityRef)eList.getValueAt(0, "StructureElement");
         StructureElementPK pk = (StructureElementPK)locationRef.getPrimaryKey();
         this.log("processing cellcalc form  : " + request.getFormId() + " for : " + locationRef.getNarrative());
         Object wrapperInst = wrapperConst.newInstance(new Object[]{this.getCPConnection()});
         m1.invoke(wrapperInst, new Object[]{request.getModelId(), calInfo, Integer.valueOf(request.getFormId()), Integer.valueOf(request.getDeploymentId()), request.getContextDims(), Integer.valueOf(pk.getStructureElementId()), request.getTemplate(), request.getOption().getParamMap()});
         CPZipFileWrapper resultFile = (CPZipFileWrapper)m2.invoke(wrapperInst, new Object[0]);
         if(resultFile == null) {
            this.log("empty file");
         } else {
            this.log("created file " + resultFile.getName());
            this.processResult(pk.getStructureElementId(), resultFile, "Cell Calculation Header", request);
         }

         if(this.getCheckpoint().shouldCheckpoint()) {
            break;
         }
      }

   }

   private void doSummaryCalculationReportType(PackLineTaskRequest request) throws Exception {
      Class wrapper = Class.forName("com.cedar.cp.utc.task.CalculationReportRunner");
      Constructor wrapperConst = wrapper.getConstructor(new Class[]{CPConnection.class});
      Method m1 = wrapper.getMethod("doWork", new Class[]{String.class, CalendarInfo.class, Integer.TYPE, Integer.TYPE, List.class, Integer.TYPE, byte[].class, Map.class});
      Method m2 = wrapper.getMethod("getFile", new Class[0]);
      CalendarInfoImpl calInfo = (new StructureElementDAO()).getCalendarInfoForModel((new Integer(request.getModelId())).intValue());
      CellCalcUtils util = new CellCalcUtils();
      CcDeploymentXMLFormTypeELO el = util.getCcDeploymentDAO().getCcDeploymentXMLFormType(request.getDeploymentId());
      el.next();
      int calcType = el.getType();
      List respArea = request.getRespArea();
      StructureElementDAO strucDAO = new StructureElementDAO();

      int i;
      while((i = this.getCheckpoint().getNextIndex()) < respArea.size()) {
         EntityList eList = (EntityList)respArea.get(i);
         EntityRef locationRef = (EntityRef)eList.getValueAt(0, "StructureElement");
         StructureElementPK pk = (StructureElementPK)locationRef.getPrimaryKey();
         this.log("processing sumary cellcalc for  : " + locationRef.getNarrative());
         ReportLeavesForParentELO leavesForParent = strucDAO.getReportLeavesForParent(pk.getStructureId(), pk.getStructureId(), pk.getStructureElementId(), pk.getStructureId(), pk.getStructureElementId());
         if(leavesForParent.getNumRows() == 0) {
            this.log("no children to summerize");
         } else {
            ArrayList resultFiles = new ArrayList();

            for(int report = 0; report < leavesForParent.getNumRows(); ++report) {
               int costCentreId = ((Integer)leavesForParent.getValueAt(report, "StructureElementId")).intValue();
               Object mergedResult = wrapperConst.newInstance(new Object[]{this.getCPConnection()});
               m1.invoke(mergedResult, new Object[]{request.getModelId(), calInfo, Integer.valueOf(request.getFormId()), Integer.valueOf(request.getDeploymentId()), request.getContextDims(), Integer.valueOf(costCentreId), null, request.getOption().getParamMap()});
               CPZipFileWrapper resultFile = (CPZipFileWrapper)m2.invoke(mergedResult, new Object[0]);
               if(resultFile != null) {
                  resultFiles.add(resultFile);
               }
            }

            this.log("result files to summarise :" + resultFiles.size());
            CellCalcSummaryReport var27 = new CellCalcSummaryReport();
            CPFileWrapper var26 = var27.createSummary(request.getTemplate());
            Iterator i$ = resultFiles.iterator();

            while(i$.hasNext()) {
               CPZipFileWrapper file = (CPZipFileWrapper)i$.next();

               CPFileWrapper contents;
               for(Iterator i$1 = file.getFiles().iterator(); i$1.hasNext(); var26 = var27.mergeFiles(var26, contents)) {
                  contents = (CPFileWrapper)i$1.next();
                  this.log("processing file : " + contents.getName());
               }
            }

            if(calcType == 1) {
               var26 = var27.produceSummary("CellCalcSummary_" + locationRef.getNarrative() + ".xls", var26, request.getColumnSettings());
            } else {
               var26 = var27.produceDynamicSummary("CellCalcSummary_" + locationRef.getNarrative() + ".xls", var26, request.getColumnSettings());
            }

            this.processResult(pk.getStructureElementId(), var26, "Cell Calc Summary", request);
            if(this.getCheckpoint().shouldCheckpoint()) {
               break;
            }
         }
      }

   }

   private void processResult(int budgetLocation, Object file, String header, PackLineTaskRequest request) throws Exception {
      if(file instanceof CPFileWrapper) {
         if(((CPFileWrapper)file).getData().length == 0) {
            return;
         }
      } else if(file instanceof CPZipFileWrapper && ((CPZipFileWrapper)file).getFiles().isEmpty()) {
         return;
      }

      DistributionDetails dd;
      if(request.getDistribution() != null) {
         dd = this.getDistributionDetailList(Integer.parseInt(request.getModelId()), budgetLocation, request.getDistribution(), (Boolean)null);
      } else {
         dd = this.getDistributionDetailList(Integer.parseInt(request.getModelId()), budgetLocation, (EntityRef)null, Boolean.valueOf(request.isSelf()));
      }

      if(dd.getServerRoot() != null) {
         this.mLog.info("processResult", "writing to file on server");
         File splitData = new File(dd.getServerRoot());
         if(!splitData.exists()) {
            throw new Exception("Server root does not exist");
         }

         File subject = new File(dd.getServerRoot() + File.separator + this.getOriginalTaskId());
         if(!subject.exists()) {
            this.log("create task dir under server root");
            subject.mkdirs();
         }

         if(file instanceof CPFileWrapper) {
            File content = ((CPFileWrapper)file).writeToDisk(subject);
            this.mLog.info("processResult", "written file to server root" + content);
            this.log("written file to server root " + content);
         } else if(file instanceof CPZipFileWrapper) {
            Iterator content2 = ((CPZipFileWrapper)file).getFiles().iterator();

            while(content2.hasNext()) {
               CPFileWrapper server = (CPFileWrapper)content2.next();
               File i$ = server.writeToDisk(subject);
               this.mLog.info("processResult", "written file to server root" + i$);
               this.log("written file to server root " + i$);
            }
         }
      }

      if(request.isGroup()) {
         this.mLog.info("processResult", "put it somewhere to send out later");
         if(file instanceof CPFileWrapper) {
            this.getCheckpoint().addToGroup(dd, (CPFileWrapper)file);
         } else if(file instanceof CPZipFileWrapper) {
            Iterator splitData2 = ((CPZipFileWrapper)file).getFiles().iterator();

            while(splitData2.hasNext()) {
               CPFileWrapper subject1 = (CPFileWrapper)splitData2.next();
               this.getCheckpoint().addToGroup(dd, subject1);
            }
         }
      } else {
         this.mLog.info("processResult", "send file out");
         String[] splitData1 = header.split("\n\t");
         StringBuffer subject2 = new StringBuffer();
         StringBuffer content1 = new StringBuffer();
         subject2.append("Report : ");
         subject2.append(" for ");
         subject2.append(splitData1[0]);
         content1.append("<pre>");
         content1.append("Your report is attached for :");
         content1.append("\n\t");
         content1.append(header);
         content1.append("\n");
         content1.append("</pre>");
         if(request.getOption() != null && request.getOption().getMessageText() != null) {
            content1.append("\n").append(request.getOption().getMessageText());
         }

         MessageHelperServer server1 = new MessageHelperServer(new InitialContext(), false);

         MessageImpl msg;
         for(Iterator i$2 = dd.getDistributionList().iterator(); i$2.hasNext(); server1.createNewMessage(msg)) {
            Object o = i$2.next();
            DistributionDetail messageDetail = (DistributionDetail)o;
            msg = new MessageImpl((Object)null);
            msg.addFromUser("System");
            msg.setSubject(subject2.toString());
            msg.setContent(content1.toString());
            Iterator i$1;
            if(file instanceof CPFileWrapper) {
               msg.addAttachment((CPFileWrapper)file);
            } else if(file instanceof CPZipFileWrapper) {
               i$1 = ((CPZipFileWrapper)file).getFiles().iterator();

               while(i$1.hasNext()) {
                  CPFileWrapper userId = (CPFileWrapper)i$1.next();
                  msg.addAttachment(userId);
               }
            }

            msg.setMessageType(messageDetail.getMessageType());
            if(messageDetail.getDistributionType() == 1) {
               msg.setToEmailAddress(messageDetail.getListAsString());
            } else {
               Object userId1;
               if(messageDetail.getDistributionType() == 0) {
                  i$1 = messageDetail.getUsers().iterator();

                  while(i$1.hasNext()) {
                     userId1 = i$1.next();
                     msg.addToUser(userId1.toString());
                  }
               } else if(messageDetail.getDistributionType() == 2) {
                  i$1 = messageDetail.getUsers().iterator();

                  while(i$1.hasNext()) {
                     userId1 = i$1.next();
                     msg.addToUser(userId1.toString());
                  }
               }
            }
         }
      }

   }

   public DistributionDetails getDistributionDetailList(int modelId, int budgetLocation, EntityRef ref, Boolean self) {
      DistributionDetailsImpl details = new DistributionDetailsImpl();
      StructureElementDAO strucDAO = new StructureElementDAO();
      BudgetLocationElementForModelELO eList = strucDAO.getBudgetLocationElementForModel(modelId, budgetLocation);
      StringBuilder blString = new StringBuilder();
      blString.append(eList.getValueAt(0, "VisId"));
      if(blString.length() > 0 && eList.getValueAt(0, "Description") != null) {
         blString.append(" - ");
      }

      blString.append(eList.getValueAt(0, "Description"));
      details.setBudgetLocation(blString.toString());
      boolean ra = false;
      DistributionDetailImpl detail;
      SystemPropertyELO var18;
      SystemPropertyDAO var22;
      if(ref != null) {
         DistributionDAO spDAO = new DistributionDAO();
         DistributionDetailsForVisIdELO dao = spDAO.getDistributionDetailsForVisId(ref.getNarrative());

         while(dao.hasNext()) {
            dao.next();
            ra = dao.getRaDistribution();
            String i = dao.getDirRoot();
            if(i != null && i.length() > 0) {
               details.setServerRoot(i);
            }

            detail = new DistributionDetailImpl();
            boolean doAdd = true;
            if(dao.getDestinationType() == null) {
               doAdd = false;
            } else {
               int i1;
               if(DistributionLinkEVO.INTERNAL.equals(dao.getDestinationType())) {
                  detail.setDistributionType(0);
                  InternalDestinationDAO dao1 = new InternalDestinationDAO();
                  AllUsersForInternalDestinationIdELO var17 = dao1.getAllUsersForInternalDestinationId(dao.getDestinationId());

                  for(i1 = 0; i1 < var17.getNumRows(); ++i1) {
                     if(i1 == 0) {
                        detail.setMessageType(((Integer)var17.getValueAt(i1, "MessageType")).intValue());
                     }

                     detail.addUser(var17.getValueAt(i1, "User").toString());
                  }
               } else if(DistributionLinkEVO.EXTERNAL.equals(dao.getDestinationType())) {
                  detail.setDistributionType(1);
                  ExternalDestinationDAO var26 = new ExternalDestinationDAO();
                  AllUsersForExternalDestinationIdELO var19 = var26.getAllUsersForExternalDestinationId(dao.getDestinationId());

                  for(i1 = 0; i1 < var19.getNumRows(); ++i1) {
                     detail.addUser(var19.getValueAt(i1, "EmailAddress").toString());
                  }
               }
            }

            if(doAdd) {
               details.addDistributionDetail(detail);
            }
         }
      } else {
         ra = !self.booleanValue();
         if(!ra) {
            detail = new DistributionDetailImpl();
            detail.setDistributionType(0);
            var22 = new SystemPropertyDAO();
            var18 = var22.getSystemProperty("WEB: Alert message type");
            detail.setMessageType(Integer.parseInt(var18.getValueAt(0, "Value").toString()));
            UserDAO var23 = new UserDAO();
            UserMessageAttributesForIdELO var20 = var23.getUserMessageAttributesForId(this.getUserId());
            detail.addUser(var20.getValueAt(0, "User").toString());
            details.addDistributionDetail(detail);
         }
      }

      if(ra) {
         detail = new DistributionDetailImpl();
         detail.setDistributionType(2);
         var22 = new SystemPropertyDAO();
         var18 = var22.getSystemProperty("WEB: Alert message type");
         detail.setMessageType(Integer.parseInt(var18.getValueAt(0, "Value").toString()));
         BudgetUserDAO var24 = new BudgetUserDAO();
         BudgetUsersForNodeELO var21 = var24.getBudgetUsersForNode(modelId, budgetLocation);

         for(int var25 = 0; var25 < var21.getNumRows(); ++var25) {
            detail.addUser(var21.getValueAt(var25, "User").toString());
         }

         details.addDistributionDetail(detail);
      }

      return details;
   }

   public int getReportType() {
      return 0;
   }

   public String getEntityName() {
      return "PackLineTask";
   }

   public PackLineTask$PackLineTaskCheckpoint getCheckpoint() {
      return (PackLineTask$PackLineTaskCheckpoint)super.getCheckpoint();
   }
}
