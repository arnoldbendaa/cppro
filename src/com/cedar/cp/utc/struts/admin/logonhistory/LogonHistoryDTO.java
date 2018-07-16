// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin.logonhistory;

import com.cedar.cp.utc.struts.admin.logonhistory.LogonHistoryForm;
import com.cedar.cp.util.DefaultValueMapping;
import java.util.Date;

public class LogonHistoryDTO {

   private String mUsername = null;
   private Date mEventDate = null;
   private int mEventType = 0;


   public String getEventTypeName() {
      DefaultValueMapping stateMapping = (new LogonHistoryForm()).getEventTypeMappingObj();
      return String.valueOf(stateMapping.getLiteral(new Integer(this.mEventType)));
   }

   public String getUsername() {
      return this.mUsername;
   }

   public void setUsername(String username) {
      this.mUsername = username;
   }

   public Date getEventDate() {
      return this.mEventDate;
   }

   public void setEventDate(Date eventDate) {
      this.mEventDate = eventDate;
   }

   public int getEventType() {
      return this.mEventType;
   }

   public void setEventType(int eventType) {
      this.mEventType = eventType;
   }
}
