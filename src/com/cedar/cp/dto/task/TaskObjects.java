// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.task;

import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.api.task.TaskRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

public class TaskObjects implements Serializable {

   private boolean mHasException;
   private boolean mHasCheckpoint;
   private TaskRequest mRequest;
   private Exception mException;
   private String mStackTrace;
   private TaskCheckpoint mCheckpoint;
   private transient boolean mDirty;
   private transient byte[] mBytes;


   TaskObjects() {}

   public TaskObjects(TaskRequest req, Exception e, TaskCheckpoint check) {
      this.mRequest = req;
      this.mException = e;
      this.mCheckpoint = check;
      this.serializeObjects();
   }

   public TaskObjects(byte[] bytes) {
      this.mBytes = bytes;
      this.deserializeObjects();
   }

   public TaskRequest getRequest() {
      return this.mRequest;
   }

   public void setRequest(TaskRequest req) {
      this.mRequest = req;
      this.mDirty = true;
   }

   public Exception getException() {
      return this.mException;
   }

   public void setException(Exception e) {
      this.mException = e;
      StringWriter sw = new StringWriter();
      e.printStackTrace(new PrintWriter(sw));
      this.mStackTrace = sw.toString();
      this.mDirty = true;
   }

   public String getStackTrace() {
      return this.mStackTrace;
   }

   public TaskCheckpoint getCheckpoint() {
      return this.mCheckpoint;
   }

   public void setCheckpoint(TaskCheckpoint check) {
      this.mCheckpoint = check;
      this.mDirty = true;
   }

   public byte[] getBytes() {
      if(this.mDirty) {
         this.serializeObjects();
      }

      return this.mBytes;
   }

   public void setBytes(byte[] bytes) {
      this.mBytes = bytes;
      this.deserializeObjects();
   }

   private void serializeObjects() {
      try {
         ByteArrayOutputStream ioe = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(ioe);
         this.mHasException = this.mException != null;
         this.mHasCheckpoint = this.mCheckpoint != null;
         oos.writeBoolean(this.mHasException);
         oos.writeBoolean(this.mHasCheckpoint);
         oos.writeObject(this.mRequest);
         if(this.mHasException) {
            oos.writeObject(this.mException);
            oos.writeObject(this.mStackTrace);
         }

         if(this.mHasCheckpoint) {
            oos.writeObject(this.mCheckpoint);
         }

         this.mBytes = ioe.toByteArray();
         oos.flush();
         oos.close();
         this.mDirty = false;
      } catch (IOException var3) {
         throw new RuntimeException("IO exception writing to stream: " + var3.getMessage());
      }
   }

   private void deserializeObjects() {
      try {
         ByteArrayInputStream cnfe = new ByteArrayInputStream(this.mBytes);
         ObjectInputStream ois = new ObjectInputStream(cnfe);
         this.mHasException = ois.readBoolean();
         this.mHasCheckpoint = ois.readBoolean();
         this.mRequest = (TaskRequest)ois.readObject();
         if(this.mHasException) {
            this.mException = (Exception)ois.readObject();
            this.mStackTrace = (String)ois.readObject();
         }

         if(this.mHasCheckpoint) {
            this.mCheckpoint = (TaskCheckpoint)ois.readObject();
         }

         ois.close();
         this.mDirty = false;
      } catch (IOException var3) {
         throw new RuntimeException("IO exception writing to stream: " + var3.getMessage());
      } catch (ClassNotFoundException var4) {
         throw new RuntimeException("ClassNotFound exception deserializing request: " + var4.getMessage());
      }
   }
}
