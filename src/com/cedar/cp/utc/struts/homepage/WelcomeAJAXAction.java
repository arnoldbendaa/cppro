// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.homepage;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPBaseAJAXAction;
import com.cedar.cp.utc.common.CPContext;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.json.JSONArray;
import org.json.JSONObject;

public class WelcomeAJAXAction extends CPBaseAJAXAction {

   public Object processRequest(ActionForm actionForm, HttpServletRequest httpServletRequest, CPContext context, CPConnection conn) throws Exception {
      String action = httpServletRequest.getParameter("action");
      String data = httpServletRequest.getParameter("data");
      JSONObject jsonObj = new JSONObject(data);
      int daysBefore = this.getCPSystemProperties(httpServletRequest).getStatusWarningDays();
      JSONArray o = null;
      if(action.equals("getChildren")) {
         JSONObject node = jsonObj.getJSONObject("node");
         String widgetId = node.getString("widgetId");
         String[] params = widgetId.split("::");
         String objectType = "RootNode";
         if(params != null) {
            objectType = params[0].trim();
         }

         if("RootNode".equals(objectType)) {
            o = this.getModels(conn, context.getIntUserId(), params[1], daysBefore);
         } else {
            int id;
            if("Model".equals(objectType)) {
               id = Integer.parseInt(params[1].trim());
               o = this.getBudgetCycles(conn, id, widgetId, params[params.length - 1], context.getIntUserId(), daysBefore);
            } else if("BudgetCycle".equals(objectType)) {
               id = Integer.parseInt(params[1].trim());
               int modelId = Integer.parseInt(params[4].trim());
               o = this.getStructureElem(conn, id, widgetId, modelId, params[params.length - 1], context.getIntUserId(), daysBefore);
            }
         }
      }

      return o;
   }

   private JSONArray getModels(CPConnection conn, int userId, String type, int dayBefore) throws Exception {
      JSONArray list = new JSONArray();
      int oldModelId = 0;
      EntityList modelList = conn.getListHelper().getModernWelcomeDetails(userId, dayBefore);
      int size = modelList.getNumRows();

      for(int i = 0; i < size; ++i) {
         int modelId = ((Integer)modelList.getValueAt(i, "ModelId")).intValue();
         if(oldModelId != modelId) {
            JSONObject object = new JSONObject();
            String visId = (String)modelList.getValueAt(i, "ModelVisId");
            String description = (String)modelList.getValueAt(i, "ModelDescription");
            StringBuilder widget = new StringBuilder();
            widget.append("Model::").append(modelId).append("::").append(visId).append("::").append(type);
            StringBuilder title = new StringBuilder();
            title.append(visId).append("-").append(description);
            object.put("title", title.toString());
            object.put("widgetId", widget.toString());
            object.put("modelId", modelId);
            object.put("isFolder", "true");
            object.put("rankId", i);
            object.put("actionsDisabled", "none");
            oldModelId = modelId;
            list.put(object);
         }
      }

      return list;
   }

   private JSONArray getBudgetCycles(CPConnection conn, int modelId, String parent, String type, int userId, int dayBefore) throws Exception {
      JSONArray list = new JSONArray();
      int oldBudgetCycleId = 0;
      EntityList bcList = conn.getListHelper().getModernWelcomeDetails(userId, dayBefore);
      int size = bcList.getNumRows();
      if(size > 0) {
         for(int object = 0; object < size; ++object) {
            int checkModelId = ((Integer)bcList.getValueAt(object, "ModelId")).intValue();
            if(checkModelId == modelId) {
               int bcId = ((Integer)bcList.getValueAt(object, "BudgetCycleId")).intValue();
               if(oldBudgetCycleId != bcId) {
                  JSONObject object1 = new JSONObject();
                  String visId = (String)bcList.getValueAt(object, "BudgetCycleVisId");
                  String description = (String)bcList.getValueAt(object, "BudgetCycleDescription");
                  StringBuilder widget = new StringBuilder();
                  widget.append("BudgetCycle::").append(bcId).append("::").append(visId);
                  widget.append("::").append(parent);
                  widget.append("::").append(type);
                  StringBuilder title = new StringBuilder();
                  title.append(visId).append("-").append(description);
                  object1.put("modelId", modelId);
                  object1.put("budgetCycleId", bcId);
                  object1.put("title", title.toString());
                  object1.put("widgetId", widget.toString());
                  object1.put("isFolder", "true");
                  object1.put("rankId", object);
                  object1.put("actionsDisabled", "none");
                  oldBudgetCycleId = bcId;
                  list.put(object1);
               }
            }
         }
      } else {
         JSONObject var19 = new JSONObject();
         var19.put("title", "No Budget Cycles Available");
         var19.put("widgetId", "No_BudgetCycle");
         var19.put("isFolder", false);
         var19.put("rankId", 0);
         var19.put("actionsDisabled", "none");
         list.put(var19);
      }

      return list;
   }

   private JSONArray getStructureElem(CPConnection conn, int budgetCycleId, String parent, int modelId, String type, int userId, int dayBefore) throws Exception {
      Calendar cal = Calendar.getInstance();
      JSONArray list = new JSONArray();
      EntityList eList = conn.getListHelper().getModernWelcomeDetails(userId, dayBefore);
      int size = eList.getNumRows();

      for(int i = 0; i < size; ++i) {
         int bcId = ((Integer)eList.getValueAt(i, "BudgetCycleId")).intValue();
         if(bcId == budgetCycleId) {
            Timestamp plannedEndDate = (Timestamp)eList.getValueAt(i, "PlannedEndDate");
            if("overdue".equals(type) && plannedEndDate.before(cal.getTime()) || "due".equals(type) && !plannedEndDate.before(cal.getTime())) {
               JSONObject object = new JSONObject();
               int structureId = ((Integer)eList.getValueAt(i, "StructureId")).intValue();
               int structureElementId = ((Integer)eList.getValueAt(i, "SructureElementId")).intValue();
               String visId = (String)eList.getValueAt(i, "VisId");
               String description = (String)eList.getValueAt(i, "Description");
               StringBuilder widget = new StringBuilder();
               widget.append("StructureElement::").append(structureId).append("::").append(structureElementId).append("::").append(visId);
               widget.append("::").append(parent);
               StringBuilder title = new StringBuilder();
               title.append(visId).append("-").append(description);
               object.put("modelId", modelId);
               object.put("budgetCycleId", budgetCycleId);
               object.put("structureId", structureId);
               object.put("structureElementId", structureElementId);
               object.put("title", title.toString());
               object.put("widgetId", widget.toString());
               object.put("isFolder", false);
               object.put("rankId", i);
               object.put("actionsDisabled", "none");
               list.put(object);
            }
         }
      }

      return list;
   }
}
