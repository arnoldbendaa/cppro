// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.homepage;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPBaseAJAXAction;
import com.cedar.cp.utc.common.CPContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.json.JSONArray;
import org.json.JSONObject;

public class WorkbenchAJAXAction extends CPBaseAJAXAction {

   public Object processRequest(ActionForm actionForm, HttpServletRequest httpServletRequest, CPContext context, CPConnection conn) throws Exception {
      String action = httpServletRequest.getParameter("action");
      String data = httpServletRequest.getParameter("data");
      JSONObject jsonObj = new JSONObject(data);
      JSONArray o = null;
      if(action.equals("getChildren")) {
         JSONObject node = jsonObj.getJSONObject("node");
         String widgetId = node.getString("widgetId");
         String[] params = widgetId.split("::");
         String objectType = "RootNode";
         if(params != null) {
            objectType = params[0].trim();
         }

         if("workBenchRootNode".equals(objectType)) {
            o = this.getModels(conn, context.getIntUserId());
         } else {
            int structureid;
            if("Model".equals(objectType)) {
               structureid = Integer.parseInt(params[1].trim());
               o = this.getBudgetCycles(conn, structureid, widgetId);
            } else {
               int structureElement;
               if("BudgetCycle".equals(objectType)) {
                  structureid = Integer.parseInt(params[1].trim());
                  structureElement = Integer.parseInt(params[4].trim());
                  o = this.getStructureResp(conn, structureid, context.getIntUserId(), widgetId, structureElement);
               } else if("StructureElement".equals(objectType)) {
                  structureid = Integer.parseInt(params[1].trim());
                  structureElement = Integer.parseInt(params[2].trim());
                  int modelId = Integer.parseInt(params[params.length - 2].trim());
                  int budgetCycleId = Integer.parseInt(params[params.length - 5].trim());
                  o = this.getStructureElement(conn, structureid, structureElement, widgetId, modelId, budgetCycleId);
               }
            }
         }
      }

      return o;
   }

   private JSONArray getModels(CPConnection conn, int userId) throws Exception {
      JSONArray list = new JSONArray();
      EntityList modelList = conn.getModelsProcess().getAllModelsWebForUser(userId);
      int size = modelList.getNumRows();

      for(int i = 0; i < size; ++i) {
         JSONObject object = new JSONObject();
         int modelId = ((Integer)modelList.getValueAt(i, "ModelId")).intValue();
         String visId = (String)modelList.getValueAt(i, "VisId");
         String description = (String)modelList.getValueAt(i, "Description");
         StringBuilder widget = new StringBuilder();
         widget.append("Model::").append(modelId).append("::").append(visId);
         StringBuilder title = new StringBuilder();
         title.append("<span class=\'treeNodeTypeModel\'>").append(visId).append("</span>");
         object.put("title", title.toString());
         object.put("widgetId", widget.toString());
         object.put("modelId", modelId);
         object.put("isFolder", "true");
         object.put("rankId", i);
         object.put("actionsDisabled", "none");
         list.put(object);
      }

      return list;
   }

   private JSONArray getBudgetCycles(CPConnection conn, int modelId, String parent) throws Exception {
      JSONArray list = new JSONArray();
      EntityList bcList = conn.getBudgetCyclesProcess().getBudgetCyclesForModelWithState(modelId, 1);
      int size = bcList.getNumRows();
      if(size > 0) {
         for(int object = 0; object < size; ++object) {
            JSONObject object1 = new JSONObject();
            int bcId = ((Integer)bcList.getValueAt(object, "BudgetCycleId")).intValue();
            String visId = (String)bcList.getValueAt(object, "VisId");
            String description = (String)bcList.getValueAt(object, "Description");
            StringBuilder widget = new StringBuilder();
            widget.append("BudgetCycle::").append(bcId).append("::").append(visId);
            widget.append("::").append(parent);
            StringBuilder title = new StringBuilder();
            title.append("<span class=\'treeNodeTypeBC\'>").append(description).append("</span>");
            object1.put("modelId", modelId);
            object1.put("budgetCycleId", bcId);
            object1.put("title", title.toString());
            object1.put("widgetId", widget.toString());
            object1.put("isFolder", "true");
            object1.put("rankId", object);
            object1.put("actionsDisabled", "none");
            list.put(object1);
         }
      } else {
         JSONObject var14 = new JSONObject();
         var14.put("title", "No Budget Cycles Available");
         var14.put("widgetId", "No_BudgetCycle");
         var14.put("isFolder", false);
         var14.put("rankId", 0);
         var14.put("actionsDisabled", "none");
         list.put(var14);
      }

      return list;
   }

   private JSONArray getStructureResp(CPConnection conn, int budgetCycleId, int userId, String parent, int modelId) throws Exception {
      JSONArray list = new JSONArray();
      EntityList eList = conn.getListHelper().getNodesForUserAndCycle(budgetCycleId, userId);
      int size = eList.getNumRows();

      for(int i = 0; i < size; ++i) {
         JSONObject object = new JSONObject();
         int structureId = ((Integer)eList.getValueAt(i, "StructureId")).intValue();
         int structureElementId = ((Integer)eList.getValueAt(i, "StructureElementId")).intValue();
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
         String leaf = eList.getValueAt(i, "Leaf").toString();
         if("false".equals(leaf)) {
            object.put("isFolder", true);
         } else {
            object.put("isFolder", false);
         }

         object.put("rankId", i);
         object.put("actionsDisabled", "none");
         list.put(object);
      }

      return list;
   }

   private JSONArray getStructureElement(CPConnection conn, int strucId, int strucElementId, String parent, int modelId, int budgetCycleId) throws Exception {
      JSONArray list = new JSONArray();
      EntityList eList = conn.getListHelper().getImmediateChildren(strucId, strucElementId);
      int size = eList.getNumRows();

      for(int i = 0; i < size; ++i) {
         JSONObject object = new JSONObject();
         int structureId = ((Integer)eList.getValueAt(i, "StructureId")).intValue();
         int structureElementId = ((Integer)eList.getValueAt(i, "StructureElementId")).intValue();
         String visId = (String)eList.getValueAt(i, "VisId");
         String description = (String)eList.getValueAt(i, "Description");
         StringBuilder widget = new StringBuilder();
         widget.append("StructureElement::").append(structureId).append("::").append(structureElementId).append("::").append(visId);
         widget.append("::").append(parent);
         StringBuilder title = new StringBuilder();
         title.append(visId).append(" - ").append(description);
         object.put("modelId", modelId);
         object.put("budgetCycleId", budgetCycleId);
         object.put("structureId", structureId);
         object.put("structureElementId", structureElementId);
         object.put("title", title.toString());
         object.put("widgetId", widget.toString());
         String leaf = eList.getValueAt(i, "Leaf").toString();
         if("false".equals(leaf)) {
            object.put("isFolder", true);
         } else {
            object.put("isFolder", false);
         }

         object.put("rankId", i);
         object.put("actionsDisabled", "none");
         list.put(object);
      }

      return list;
   }
}
