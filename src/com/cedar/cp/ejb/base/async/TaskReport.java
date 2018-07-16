// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.report.ReportPK;
import com.cedar.cp.ejb.base.async.XMLReportUtils;
import com.cedar.cp.ejb.impl.report.ReportDAO;
import com.cedar.cp.ejb.impl.report.ReportEVO;
import java.util.Collection;

public class TaskReport extends XMLReportUtils {

   private int mUserId;
   private int mTaskId;
   private int mReportType;
   private StringBuffer mReportText;
   private ReportDAO mReportDAO;
   public static final int MAX_REPORT_TEXT = 100000;
   private TaskCheckpoint mCheckpoint;
   private boolean mHasUpdates;
   private boolean mUpdatesApplied;
   private Integer mActivityType;
   private Integer mBudgetCycleId;
   private StringBuffer mActivityDetail;
   private int mReportId;


   public TaskReport(int userId, int taskId, int reportType, TaskCheckpoint checkpoint) {
      this(userId, taskId, reportType, checkpoint, false, false);
   }

   public TaskReport(int userId, int taskId, int reportType, TaskCheckpoint checkpoint, boolean hasUpdates, boolean updatesApplied) {
      this.mUserId = userId;
      this.mTaskId = taskId;
      this.mReportType = reportType;
      this.mCheckpoint = checkpoint;
      this.mReportText = new StringBuffer(100000);
      this.mHasUpdates = hasUpdates;
      this.mUpdatesApplied = updatesApplied;
      this.mActivityDetail = new StringBuffer(100000);
   }

   private ReportDAO getReportDAO() {
      if(this.mReportDAO == null) {
         this.mReportDAO = new ReportDAO();
      }

      return this.mReportDAO;
   }

   public void flushText() {
      if(this.mCheckpoint.getReportId() == 0) {
         ReportEVO reportEVO = new ReportEVO(-1, this.mUserId, this.mReportType, this.mTaskId, false, this.mReportText.toString(), this.mHasUpdates, this.mUpdatesApplied, (Integer)null, this.mBudgetCycleId, this.mActivityType, this.mActivityDetail != null?this.mActivityDetail.toString():null, 0, (Collection)null);
         this.getReportDAO().setDetails(reportEVO);
         ReportPK reportPK = null;

         try {
            reportPK = this.getReportDAO().create();
         } catch (Exception var4) {
            throw new CPException(var4.getMessage(), var4);
         }

         int reportId = reportPK.getReportId();
         this.mCheckpoint.setReportId(reportId);
      } else if(this.mReportText.length() > 0) {
         this.getReportDAO().updateReportText(this.mCheckpoint.getReportId(), this.mReportText.toString());
         this.mReportText = new StringBuffer(100000);
      }

   }

   public void setCompleted() {
      if(this.mReportText.length() > 0) {
         this.flushText();
         this.mReportText = null;
      }

      this.mReportId = this.mCheckpoint.getReportId();
      this.getReportDAO().closeReport(this.mReportId);
   }

   public int length() {
      return this.mReportText.length();
   }

   public void add(String s) {
      if(s != null) {
         if(this.mReportText.length() + s.length() > 100000) {
            this.flushText();
         }

         this.mReportText.append(s);
      }
   }

   public String toString() {
      return this.mReportText.toString();
   }

   public int getReportId() {
      return this.mReportId;
   }

   public Integer getActivityType() {
      return this.mActivityType;
   }

   public Integer getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public StringBuffer getActivityDetail() {
      return this.mActivityDetail;
   }

   public void setActivityDetail(Integer budgetCycleId, int activityType, StringBuffer activityDetail) throws ValidationException {
      this.mBudgetCycleId = budgetCycleId;
      this.mActivityType = new Integer(activityType);
      this.mActivityDetail = activityDetail;
      ReportEVO evo;
      if(this.mCheckpoint.getReportId() == 0) {
         evo = new ReportEVO(-1, this.mUserId, this.mReportType, this.mTaskId, false, this.mReportText.toString(), this.mHasUpdates, this.mUpdatesApplied, (Integer)null, this.mBudgetCycleId, this.mActivityType, this.mActivityDetail != null?this.mActivityDetail.toString():null, 0, (Collection)null);
         this.getReportDAO().setDetails(evo);
         ReportPK e = null;

         try {
            e = this.getReportDAO().create();
         } catch (Exception var8) {
            throw new CPException(var8.getMessage(), var8);
         }

         int reportId = e.getReportId();
         this.mCheckpoint.setReportId(reportId);
      } else {
         evo = this.getReportDAO().getDetails(new ReportPK(this.mCheckpoint.getReportId()), "");
         evo.setActivityType(this.getActivityType());
         evo.setActivityDetail(this.getActivityDetail() != null?this.getActivityDetail().toString():null);
         evo.setBudgetCycleId(this.getBudgetCycleId());
         this.getReportDAO().setDetails(evo);

         try {
            this.getReportDAO().store();
         } catch (Exception var7) {
            throw new CPException("Failed to update report header:" + var7.getMessage(), var7);
         }
      }

   }
}
