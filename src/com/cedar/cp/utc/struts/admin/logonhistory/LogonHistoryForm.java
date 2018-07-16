// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin.logonhistory;

import com.cedar.cp.utc.common.CPSearchForm;
import com.cedar.cp.utc.struts.admin.logonhistory.LogonHistoryDTO;
import com.cedar.cp.util.DefaultValueMapping;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LogonHistoryForm extends CPSearchForm {

   private static final long serialVersionUID = 1L;
   private String mUsername = null;
   private Timestamp mEventDate = null;
   private String mEventDateStr = "";
   private int mEventType = 0;
   private String mEventTypeName = null;
   private List<LogonHistoryDTO> mDtoList = null;
   private DefaultValueMapping mEventTypeMappingObj;
   public static String[] STATE_NAMES = new String[]{"TIMED_OUT", "LOGOFF", "SUCCESSFUL", "FAILED"};
   public static Integer[] STATE_VALUES = new Integer[]{Integer.valueOf(-2), Integer.valueOf(-1), Integer.valueOf(1), Integer.valueOf(2)};


   public LogonHistoryForm() {
      this.mEventTypeMappingObj = new DefaultValueMapping(STATE_NAMES, STATE_VALUES);
   }

   public List<LogonHistoryDTO> getDtoList() {
      return this.mDtoList;
   }

   public void setDtoList(List<LogonHistoryDTO> dtoList) {
      this.mDtoList = dtoList;
   }

   public DefaultValueMapping getEventTypeMappingObj() {
      return this.mEventTypeMappingObj;
   }

   public void setEventTypeMappingObj(DefaultValueMapping eventTypeMappingObj) {
      this.mEventTypeMappingObj = eventTypeMappingObj;
   }

   public String getUsername() {
      return this.mUsername;
   }

   public String getUsernameSearch() {
      return this.mUsername != null && this.mUsername.trim().length() != 0?this.mUsername:"%";
   }

   public void setUsername(String username) {
      this.mUsername = username;
   }

   public Timestamp getEventDate() {
      if(this.mEventDateStr != null && this.mEventDateStr.trim().length() > 0) {
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

         try {
            Date e = formatter.parse(this.mEventDateStr);
            return new Timestamp(e.getTime());
         } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
         }
      } else {
         return this.mEventDate;
      }
   }

   public void setEventDate(Timestamp eventDate) {
      this.mEventDate = eventDate;
   }

   public int getEventType() {
      return this.mEventTypeName != null && this.mEventTypeName.trim().length() > 0?((Integer)this.mEventTypeMappingObj.getValue(this.mEventTypeName)).intValue():this.mEventType;
   }

   public void setEventType(int eventType) {
      this.mEventType = eventType;
   }

   public String getEventTypeName() {
      return this.mEventTypeName;
   }

   public void setEventTypeName(String eventTypeName) {
      this.mEventTypeName = eventTypeName;
   }

   public String getEventDateStr() {
      return this.mEventDateStr;
   }

   public void setEventDateStr(String eventDateStr) {
      this.mEventDateStr = eventDateStr;
   }

   public String getDojoDate() {
      if(this.getEventDateStr() != null && this.getEventDateStr().length() > 0) {
         StringBuilder sb = new StringBuilder();
         sb.append(this.getEventDateStr().substring(6, 10));
         sb.append(",");
         sb.append(this.getEventDateStr().substring(3, 5));
         sb.append(",");
         sb.append(this.getEventDateStr().substring(0, 2));
         return sb.toString();
      } else {
         return "";
      }
   }

}
