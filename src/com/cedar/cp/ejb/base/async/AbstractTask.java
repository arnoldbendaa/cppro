// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.DriverManager;
import com.cedar.cp.api.base.JmsConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.CPConnection.ConnectionContext;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.systemproperty.SystemPropertyELO;
import com.cedar.cp.dto.task.Task;
import com.cedar.cp.dto.task.TaskMessageLogger;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.base.TaskReportWriter;
import com.cedar.cp.ejb.base.async.TaskReport;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import com.cedar.cp.ejb.impl.task.TaskDAO;
import com.cedar.cp.ejb.impl.user.UserDAO;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.util.Cryptography;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import javax.naming.InitialContext;

public abstract class AbstractTask extends AbstractDAO implements Task, TaskMessageLogger, TaskReportWriter {

   private int mUserId;
   private int mTaskId;
   private int mOriginalTaskId;
   private TaskRequest mRequest;
   private transient TaskCheckpoint mCheckpoint;
   private JmsConnection mDespatcherJmsConnection;
   private TaskReport mTaskReport;
   protected transient Log mLog = new Log(this.getClass());
   private UserEVO mUserEVO;
   private CPConnection mCPConnection;
   private String mCompletionExceptionMessage;


   public void log(String text) {
      if(this.mDespatcherJmsConnection != null) {
         (new TaskDAO()).logEvent(this.mTaskId, 1, text);
      }
   }

   public void logInfo(String text) {
      if(this.mDespatcherJmsConnection != null) {
         (new TaskDAO()).logEvent(this.mTaskId, 4, text);
      }
   }

   public void log(List messages) {
      Iterator i = messages.iterator();

      while(i.hasNext()) {
         this.log((String)i.next());
      }

   }

   public void setDespatcherJmsConnection(JmsConnection jmsconn) {
      this.mDespatcherJmsConnection = jmsconn;
   }

   public TaskReport createTaskReport() {
      return new TaskReport(this.mUserId, this.mTaskId, this.getReportType(), this.getCheckpoint());
   }

   public TaskReport getTaskReport() {
      if(this.mTaskReport == null) {
         this.mTaskReport = this.createTaskReport();
      }

      return this.mTaskReport;
   }

   public abstract int getReportType();

   public void sendEntityEventMessage(InitialContext context, String tableName, PrimaryKey pk, int changeType) {
      try {
         JmsConnectionImpl e = new JmsConnectionImpl(context, 3, "entityEventTopic");
         e.createSession();
         EntityEventMessage em = new EntityEventMessage(tableName, pk, changeType, this.getClass().getName());
         this.mLog.debug("update", "sending event message: " + em.toString());
         e.send(em);
         e.closeSession();
         e.closeConnection();
      } catch (CPException var7) {
         var7.printStackTrace();
      }

   }

   public CPConnection getCPConnection() {
      if(this.mCPConnection == null) {
         try {
            UserEVO e = this.getUserEVO();
            String connectionURL = SystemPropertyHelper.queryStringSystemProperty((Connection)null, "WEB: Connection URL", (String)null);
            String password = e.getPasswordBytes() != null && e.getPasswordBytes().length() != 0?Cryptography.decrypt(e.getPasswordBytes(), "fc30"):null;
            this.mCPConnection = DriverManager.getConnection(connectionURL, e.getName(), password, true, false, ConnectionContext.SERVER_TASK);
         } catch (Exception var4) {
            throw new CPException(var4.getMessage(), var4);
         }
      }

      return this.mCPConnection;
   }

   private UserEVO getUserEVO() throws ValidationException {
      if(this.mUserEVO == null) {
         this.mUserEVO = (new UserDAO()).getDetails(new UserPK(this.getUserId()), "");
      }

      return this.mUserEVO;
   }

   public void closeCPConnection() {
      if(this.mCPConnection != null) {
         this.mCPConnection.close();
         this.mCPConnection = null;
      }

   }

   public int getUserId() {
      return this.mUserId;
   }

   public int getTaskId() {
      return this.mTaskId;
   }

   public int getOriginalTaskId() {
      return this.mOriginalTaskId;
   }

   public TaskRequest getRequest() {
      return this.mRequest;
   }

   public TaskCheckpoint getCheckpoint() {
      return this.mCheckpoint;
   }

   public int getCheckpointNumber() {
      return this.mCheckpoint.getCheckpointNumber();
   }

   public void setUserId(int userId) {
      this.mUserId = userId;
   }

   public void setTaskId(int taskId) {
      this.mTaskId = taskId;
   }

   public void setOriginalTaskId(int originalTaskId) {
      this.mOriginalTaskId = originalTaskId;
   }

   public void setRequest(TaskRequest request) {
      this.mRequest = request;
   }

   public void setCheckpoint(TaskCheckpoint check) {
      this.mCheckpoint = check;
   }

   public void setCompletionExceptionMessage(String message) {
      this.log("");
      this.logInfo(message);
      this.mCompletionExceptionMessage = message;
   }

   public String getCompletionExceptionMessage() {
      return this.mCompletionExceptionMessage;
   }

   public String getCompletionMessage() {
      return "Task complete" + (this.mCompletionExceptionMessage == null?"":" - " + this.mCompletionExceptionMessage);
   }

   public boolean mustComplete() {
      return true;
   }

   public boolean autoDeleteWhenComplete() {
      return false;
   }

   public void tidyActions() {
      if(this.getCheckpoint() != null && this.mTaskReport != null) {
         this._log.debug("tidyActions", "deleting reports for task " + this.getTaskId());
         String sql = "delete from REPORT where TASK_ID = ?";
         PreparedStatement stmt = null;

         try {
            stmt = this.getConnection().prepareStatement(sql);
            stmt.setInt(1, this.getTaskId());
            int sqle = stmt.executeUpdate();
            if(sqle > 0) {
               this._log.info("tidyActions", "deleted reports=" + sqle);
            }
         } catch (SQLException var7) {
            throw new RuntimeException("unable to delete reports for task", var7);
         } finally {
            this.closeStatement(stmt);
            this.closeConnection();
         }
      }

   }

   public String getCompletionFooter() {
      StringBuffer footer = new StringBuffer();
      footer.append(" - ");
      footer.append("<a target=\"_blank\" href=\"");
      footer.append(this.getRootURL());
      footer.append("taskDetails.do?");
      footer.append("taskId=");
      footer.append(this.getTaskId());
      footer.append("\">Task Events</a>");
      if(this.getTaskReport().getReportId() > 0) {
         footer.append(" - ");
         footer.append("<a target=\"_blank\" href=\"");
         footer.append(this.getRootURL());
         footer.append("reportDetails.do?");
         footer.append("reportId=");
         footer.append(this.getTaskReport().getReportId());
         footer.append("\">Task Report</a>");
      }

      return footer.toString();
   }

   private String getRootURL() {
      String value = "";
      SystemPropertyDAO spDao = new SystemPropertyDAO();
      SystemPropertyELO spELO = spDao.getSystemProperty("WEB: Root URL");
      if(spELO.hasNext()) {
         spELO.next();
         value = spELO.getValue();
      }

      if(value.lastIndexOf("/") != value.length()) {
         value = value + "/";
      }

      return value;
   }
}
