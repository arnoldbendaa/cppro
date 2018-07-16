// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.api.report.pack.ReportPackOption;
import com.cedar.cp.api.report.pack.ReportPackRef;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.api.task.group.TaskGroupRef;
import com.cedar.cp.api.udeflookup.UdefLookupRef;
import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.dto.extsys.ExternalSystemRefImpl;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.mapping.MappedModelPK;
import com.cedar.cp.dto.report.pack.ReportPackPK;
import com.cedar.cp.dto.task.group.TaskGroupPK;
import com.cedar.cp.dto.task.group.TaskGroupTaskRequest;
import com.cedar.cp.dto.udeflookup.UdefLookupPK;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildCK;
import com.cedar.cp.ejb.api.admin.tidytask.TidyTaskEditorSessionServer;
import com.cedar.cp.ejb.api.extsys.ExternalSystemEditorSessionServer;
import com.cedar.cp.ejb.api.model.BudgetCycleHelperServer;
import com.cedar.cp.ejb.api.model.amm.AmmModelEditorSessionServer;
import com.cedar.cp.ejb.api.model.mapping.MappedModelEditorSessionServer;
import com.cedar.cp.ejb.api.report.pack.ReportPackEditorSessionServer;
import com.cedar.cp.ejb.api.task.group.TaskGroupEditorSessionServer;
import com.cedar.cp.ejb.api.udeflookup.UdefLookupEditorSessionServer;
import com.cedar.cp.ejb.api.xmlform.rebuild.FormRebuildEditorSessionServer;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.TaskGroupTask$1;
import com.cedar.cp.ejb.base.async.TaskGroupTask$MyCheckpoint;
import com.cedar.cp.ejb.impl.report.pack.ReportPackDAO;
import com.cedar.cp.ejb.impl.task.group.TaskGroupDAO;
import com.cedar.cp.ejb.impl.task.group.TaskGroupEVO;
import com.cedar.cp.ejb.impl.task.group.TgRowEVO;
import com.cedar.cp.ejb.impl.task.group.TgRowParamEVO;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupDAO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.naming.InitialContext;

public class TaskGroupTask extends AbstractTask {

   private transient InitialContext mInitialContext;


   public void runUnitOfWork(InitialContext context) throws Exception {
      this.mInitialContext = context;
      if(this.getCheckpoint() == null) {
         this.firstTime();
      }

      if(this.getCheckpoint() != null) {
         this.issueNextTask();
      }

   }

   private void firstTime() throws Exception {
      this.setCheckpoint(new TaskGroupTask$MyCheckpoint());
      TaskGroupDAO tgdao = new TaskGroupDAO();
      TaskGroupPK pk = (TaskGroupPK)((TaskGroupTaskRequest)this.getRequest()).getTaskGroupRef().getPrimaryKey();
      TaskGroupEVO tgEvo = tgdao.getDetails(pk, "<0><1>");
      tgEvo.setLastSubmit(new Timestamp(System.currentTimeMillis()));
      tgEvo.setUpdatedByUserId(this.getUserId());
      tgdao.setDetails(tgEvo);
      tgdao.store();
      this.sendEntityEventMessage(this.mInitialContext, "TaskGroup", pk, 3);
      ArrayList tgRows = new ArrayList();
      tgRows.addAll(tgEvo.getTaskGroupRowsMap().values());
      Collections.sort(tgRows, new TaskGroupTask$1(this));
      this.getCheckpoint().setTaskGroupRows(tgRows);
      if(tgRows.size() == 0) {
         this.log("no task group rows");
         this.setCheckpoint((TaskCheckpoint)null);
      }

   }

   private void issueNextTask() throws Exception {
      while(this.getCheckpointNumber() != this.getCheckpoint().getTaskGroupRows().size()) {
         TgRowEVO tgrEvo = (TgRowEVO)this.getCheckpoint().getTaskGroupRows().get(this.getCheckpointNumber());
         boolean taskIssued = false;
         switch(tgrEvo.getRowType()) {
         case 1:
            taskIssued = this.issueBudgetStateTask(tgrEvo);
            break;
         case 2:
            taskIssued = this.issueTidyTask(tgrEvo);
            break;
         case 3:
            taskIssued = this.issueAggregatedModelTask(tgrEvo);
            break;
         case 4:
            taskIssued = this.issueImportMappedModelTask(tgrEvo);
            break;
         case 5:
            taskIssued = this.issueReportPackTask(tgrEvo);
            break;
         case 6:
            taskIssued = this.issueLookupRebuildTask(tgrEvo);
            break;
         case 7:
            taskIssued = this.issueExternalSystemImportTask(tgrEvo);
            break;
         case 8:
            taskIssued = this.issueFormRebuildTask(tgrEvo);
            break;
         case 100:
            taskIssued = this.issueTaskGroupTask(tgrEvo);
            break;
         default:
            throw new IllegalStateException("unexpected task group row type:" + tgrEvo.getRowType());
         }

         if(taskIssued) {
            return;
         }

         this.getCheckpoint().setCheckpointNumberUp();
      }

      this.log("Completed");
      this.setCheckpoint((TaskCheckpoint)null);
   }

   private boolean issueBudgetStateTask(TgRowEVO tgrEvo) throws Exception {
      int daysBefore = Integer.parseInt(this.getMandatoryParamValue(tgrEvo, "DaysBefore"));
      int taskId = (new BudgetCycleHelperServer(this.mInitialContext, false)).issueBudgetStateTask(daysBefore, this.getUserId(), this.getTaskId());
      this.log("issued Budget State task - id=" + taskId);
      return true;
   }

   private boolean issueTaskGroupTask(TgRowEVO tgrEvo) throws Exception {
      TaskGroupRef er = (new TaskGroupDAO()).getDetails(TaskGroupPK.getKeyFromTokens(this.getMandatoryParamValue(tgrEvo, "ID")), "").getEntityRef();
      int taskId = (new TaskGroupEditorSessionServer(this.mInitialContext, false)).submitGroup(er, this.getUserId(), this.getTaskId());
      this.log("issued Task Group task - id=" + taskId);
      return true;
   }

   private boolean issueTidyTask(TgRowEVO tgrEvo) throws Exception {
      EntityRefImpl er = new EntityRefImpl(TidyTaskPK.getKeyFromTokens(this.getMandatoryParamValue(tgrEvo, "ID")), "");
      int taskId = (new TidyTaskEditorSessionServer(this.mInitialContext, false)).issueTidyTask(er, this.getUserId(), this.getTaskId());
      this.log("issued Tidy Task - id=" + taskId);
      return true;
   }

   private boolean issueAggregatedModelTask(TgRowEVO tgrEvo) throws Exception {
      int taskId = (new AmmModelEditorSessionServer(this.mInitialContext, false)).issueAggregatedModelTask(this.getUserId(), (List)null);
      this.log("issued Aggregated Model task - id=" + taskId);
      return true;
   }

   private boolean issueImportMappedModelTask(TgRowEVO tgrEvo) throws Exception {
      int taskId = (new MappedModelEditorSessionServer(this.mInitialContext, false)).issueModelImportTask(this.getUserId(), new int[]{MappedModelPK.getKeyFromTokens(this.getMandatoryParamValue(tgrEvo, "ID")).getMappedModelId()}, this.getTaskId());
      this.log("issued Import Mapped Model Task - taskId=" + taskId);
      return true;
   }

   private boolean issueExternalSystemImportTask(TgRowEVO tgrEvo) throws Exception {
      ExternalSystemRefImpl er = new ExternalSystemRefImpl(ExternalSystemPK.getKeyFromTokens(this.getMandatoryParamValue(tgrEvo, "ID")), "");
      int taskId = (new ExternalSystemEditorSessionServer(this.mInitialContext, false)).issueExternalSystemImportTask(this.getUserId(), er, (String)null, this.getTaskId());
      this.log("issued External System Import task - id=" + taskId);
      return true;
   }

   private boolean issueReportPackTask(TgRowEVO tgrEvo) throws Exception {
      String id = this.getMandatoryParamValue(tgrEvo, "ID");
      String param = this.getOptionalParamValue(tgrEvo, "Param");
      String message = this.getOptionalParamValue(tgrEvo, "Message");
      ReportPackRef er = (new ReportPackDAO()).getDetails(ReportPackPK.getKeyFromTokens(id), "").getEntityRef();
      ReportPackOption rpo = new ReportPackOption();
      rpo.setParamText(param);
      rpo.setMessageText(message);
      int taskId = (new ReportPackEditorSessionServer(this.mInitialContext, false)).issueReport(this.getUserId(), er, (ReportPackOption)null);
      this.log("issued Report Pack task - id=" + taskId);
      return true;
   }

   private boolean issueLookupRebuildTask(TgRowEVO tgrEvo) throws Exception {
      UdefLookupPK pk = UdefLookupPK.getKeyFromTokens(this.getMandatoryParamValue(tgrEvo, "ID"));
      UdefLookupRef er = (new UdefLookupDAO()).getDetails(pk, "").getEntityRef();
      int[] taskIds = (new UdefLookupEditorSessionServer(this.mInitialContext, false)).issueRebuild(this.getUserId(), er.getNarrative(), pk, this.getTaskId());
      if(taskIds.length == 0) {
         this.log("no Lookup Cell Calc Rebuild tasks issued");
         return false;
      } else {
         StringBuilder sb = new StringBuilder();

         for(int i = 0; i < taskIds.length; ++i) {
            if(i > 0) {
               sb.append(",");
            }

            sb.append(String.valueOf(taskIds[i]));
         }

         this.log("issued Lookup Cell Calc Rebuild tasks - ids=" + sb.toString());
         return true;
      }
   }

   private boolean issueFormRebuildTask(TgRowEVO tgrEvo) throws Exception {
      ModelCK ck = FormRebuildCK.getKeyFromTokens(this.getMandatoryParamValue(tgrEvo, "ID"));
      EntityRefImpl er = new EntityRefImpl(ck, "");
      List taskIds = (new FormRebuildEditorSessionServer(this.mInitialContext, false)).submit(er, this.getUserId(), this.getTaskId());
      if(taskIds.size() == 0) {
         this.log("no Form Rebuild tasks issued");
         return false;
      } else {
         StringBuilder sb = new StringBuilder();

         for(int i = 0; i < taskIds.size(); ++i) {
            if(i > 0) {
               sb.append(",");
            }

            sb.append(String.valueOf(taskIds.get(i)));
         }

         this.log("issued Form Rebuild tasks - ids=" + sb.toString());
         return true;
      }
   }

   private String getMandatoryParamValue(TgRowEVO tgrEvo, String name) {
      String val = this.getOptionalParamValue(tgrEvo, name);
      if(val == null) {
         throw new IllegalStateException("parameter <" + name + "> not found");
      } else {
         return val;
      }
   }

   private String getOptionalParamValue(TgRowEVO tgrEvo, String name) {
      Iterator i$ = tgrEvo.getTGRowsParams().iterator();

      TgRowParamEVO tgrpEvo;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         tgrpEvo = (TgRowParamEVO)i$.next();
      } while(!tgrpEvo.getKey().equals(name));

      return tgrpEvo.getParam();
   }

   public int getReportType() {
      return 0;
   }

   public String getEntityName() {
      return "TaskGroupTask";
   }

   public TaskGroupTask$MyCheckpoint getCheckpoint() {
      return (TaskGroupTask$MyCheckpoint)super.getCheckpoint();
   }
}
