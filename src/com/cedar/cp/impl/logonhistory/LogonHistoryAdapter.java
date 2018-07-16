// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.logonhistory;

import com.cedar.cp.api.logonhistory.LogonHistory;
import com.cedar.cp.dto.logonhistory.LogonHistoryImpl;
import com.cedar.cp.dto.logonhistory.LogonHistoryPK;
import com.cedar.cp.impl.logonhistory.LogonHistoryEditorSessionImpl;
import java.sql.Timestamp;

public class LogonHistoryAdapter implements LogonHistory {

   private LogonHistoryImpl mEditorData;
   private LogonHistoryEditorSessionImpl mEditorSessionImpl;


   public LogonHistoryAdapter(LogonHistoryEditorSessionImpl e, LogonHistoryImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected LogonHistoryEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected LogonHistoryImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(LogonHistoryPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getUserName() {
      return this.mEditorData.getUserName();
   }

   public Timestamp getEventDate() {
      return this.mEditorData.getEventDate();
   }

   public int getEventType() {
      return this.mEditorData.getEventType();
   }

   public void setUserName(String p) {
      this.mEditorData.setUserName(p);
   }

   public void setEventDate(Timestamp p) {
      this.mEditorData.setEventDate(p);
   }

   public void setEventType(int p) {
      this.mEditorData.setEventType(p);
   }
}
