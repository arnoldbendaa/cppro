// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.admin.tidy;

import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.admin.tidytask.TidyTaskRequest;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.admin.tidy.TidyTask$TidyCheckpoint;
import com.cedar.cp.ejb.base.async.admin.tidy.TidyTask$TidyDBDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import javax.naming.InitialContext;

public class TidyTask extends AbstractTask {

   private transient InitialContext mInitialContext;
   private transient Log mLog = new Log(this.getClass());


   public int getReportType() {
      return 8;
   }

   public TidyTask$TidyCheckpoint getCheckpoint() {
      return (TidyTask$TidyCheckpoint)super.getCheckpoint();
   }

   public String getEntityName() {
      return "TidyTask";
   }

   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      this.mInitialContext = initialContext;
      if(this.getRequest() instanceof TidyTaskRequest) {
         this.mLog.debug("runUnitOfWork", "Starting task");
         this.setCheckpoint(new TidyTask$TidyCheckpoint());
         TidyTask$TidyDBDAO dao = new TidyTask$TidyDBDAO(this);
         boolean firstReport = true;

         List row;
         while((row = ((TidyTaskRequest)this.getRequest()).getNextCommand()) != null) {
            String type = row.get(0).toString();
            String cmd = row.get(1).toString();
            this.log(type + " : " + cmd);
            Timer t = new Timer(this.mLog);
            Integer rowsAffected = null;
            if(type.equalsIgnoreCase("Update")) {
               rowsAffected = dao.issueUpdate(cmd);
            } else if(type.equalsIgnoreCase("Query")) {
               dao.issueQuery(cmd);
            } else if(type.equalsIgnoreCase("Package (report)")) {
               if(firstReport) {
                  this.getTaskReport().flushText();
                  this.getTaskReport().addReport();
                  firstReport = false;
               }

               dao.issueReportPackage(cmd);
            } else if(type.equalsIgnoreCase("Package (update)")) {
               dao.issueUpdatePackage(cmd);
            } else {
               if(!type.equalsIgnoreCase("Java Class")) {
                  throw new UnsupportedOperationException("Unsupported Type : " + type);
               }

               this.runClass(cmd);
            }

            this.log("elapsed=" + t.getElapsed() + (rowsAffected == null?"":", rows=" + rowsAffected));
            ((TidyTaskRequest)this.getRequest()).pushList();
         }

         if(!firstReport) {
            this.getTaskReport().addEndReport();
            this.getTaskReport().setCompleted();
         }

         this.mLog.debug("runUnitOfWork", "Ending  task");
         this.setCheckpoint((TaskCheckpoint)null);
      }
   }

   public void runClass(String className) {
      this.mLog.info("runClass", className);
      Class cls = null;

      try {
         cls = Class.forName(className);
      } catch (ClassNotFoundException var11) {
         this.mLog.info("runClass", className);
         this.log("CLASS NOT FOUND EXCEPTION: " + var11.getMessage());
         return;
      }

      Constructor constructor = null;
      Constructor[] setTaskIdMethod = cls.getDeclaredConstructors();
      int runMethod = setTaskIdMethod.length;

      Constructor instance;
      for(int getMessagesMethod = 0; getMessagesMethod < runMethod; ++getMessagesMethod) {
         instance = setTaskIdMethod[getMessagesMethod];
         if(instance.getParameterTypes().length == 0) {
            constructor = instance;
            break;
         }
      }

      Method var13 = null;
      Method var14 = null;
      Method var15 = null;
      Method[] var16 = cls.getDeclaredMethods();
      int e = var16.length;

      for(int messages = 0; messages < e; ++messages) {
         Method m = var16[messages];
         if(m.getName().equals("setTaskId") && m.getParameterTypes().length == 1 && m.getParameterTypes()[0].getName().equals("java.lang.Integer")) {
            var13 = m;
         } else if(m.getName().equals("run") && m.getParameterTypes().length == 0) {
            var14 = m;
         } else if(m.getName().equals("getMessages") && m.getParameterTypes().length == 0 && m.getReturnType().getName().equals("java.util.List")) {
            var15 = m;
         }
      }

      if(var14 == null) {
         this.mLog.info("runClass", "run method not found");
      } else {
         if(var13 == null) {
            this.mLog.info("runClass", "setTaskId method not found");
         }

         if(var15 == null) {
            this.mLog.info("runClass", "getMessages method not found");
         }

         if(constructor == null) {
            this.mLog.info("runClass", "couldn\'t find appropriate constructor");
            this.log("couldn\'t find appropriate constructor in " + className);
         } else {
            instance = null;

            try {
               Object var17 = cls.newInstance();
               Object[] var18 = new Object[]{new Integer(this.getTaskId())};
               if(var13 != null) {
                  var13.invoke(var17, var18);
               }

               var14.invoke(var17, new Object[0]);
               if(var15 != null) {
                  List var19 = (List)var15.invoke(var17, new Object[0]);
                  this.log(var19);
               }
            } catch (Exception var12) {
               this.log(var12.getClass().getName() + " " + var12.getMessage());
               if(var12.getCause() != null) {
                  var12.getCause().printStackTrace();
               } else {
                  var12.printStackTrace();
               }
            }

         }
      }
   }

   public InitialContext getInitialContext() {
      return this.mInitialContext;
   }

   public boolean mustComplete() {
      return false;
   }
}
