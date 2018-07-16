// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report;

import com.cedar.cp.util.SpreadsheetUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReportColumnMapDTO implements Serializable {

   private int mColumnId;
   private int mFunctionId;
   public static String[] FUNCTIONS_LIST = new String[]{"", "hidden", "sum", "average", "min", "max", "display"};


   public ReportColumnMapDTO() {
      this.setColumnId(0);
      this.setFunctionId(0);
   }

   public ReportColumnMapDTO(int columnId, int functionId) {
      this.mColumnId = columnId;
      this.mFunctionId = functionId;
   }

   public ReportColumnMapDTO(JSONObject obj) throws JSONException {
      this.setColumnId(obj.getInt("col"));
      this.setFunctionId(obj.getInt("mode"));
   }

   public int getColumnId() {
      return this.mColumnId;
   }

   public void setColumnId(int columnId) {
      this.mColumnId = columnId;
   }

   public String getColumnName() {
      return SpreadsheetUtils.getColumnLetter(this.getColumnId());
   }

   public int getFunctionId() {
      return this.mFunctionId;
   }

   public void setFunctionId(int functionId) {
      this.mFunctionId = functionId;
   }

   public String getFunctionName() {
      switch(this.getFunctionId()) {
      case 1:
         return FUNCTIONS_LIST[1];
      case 2:
         return FUNCTIONS_LIST[2];
      case 3:
         return FUNCTIONS_LIST[3];
      case 4:
         return FUNCTIONS_LIST[4];
      case 5:
         return FUNCTIONS_LIST[5];
      case 6:
         return FUNCTIONS_LIST[6];
      default:
         return FUNCTIONS_LIST[0];
      }
   }

   public static Map<Integer, ReportColumnMapDTO> buildMap(String s) throws JSONException {
      JSONArray obj = null;

      try {
         obj = new JSONArray(s);
      } catch (NullPointerException var3) {
         ;
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return buildMap(obj);
   }

   public static Map<Integer, ReportColumnMapDTO> buildMap(JSONArray obj) throws JSONException {
      HashMap returnList = new HashMap();
      if(obj == null) {
         return returnList;
      } else {
         for(int i = 0; i < obj.length(); ++i) {
            ReportColumnMapDTO dto = new ReportColumnMapDTO(obj.getJSONObject(i));
            returnList.put(Integer.valueOf(dto.getColumnId()), dto);
         }

         return returnList;
      }
   }

}
