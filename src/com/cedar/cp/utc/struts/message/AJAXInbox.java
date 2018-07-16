// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.message;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPBaseAJAXAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.dwr.RecentMessages;
import com.cedar.cp.utc.struts.message.MessageDTO;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.json.JSONArray;
import org.json.JSONObject;

public class AJAXInbox extends CPBaseAJAXAction {

   public Object processRequest(ActionForm actionForm, HttpServletRequest httpServletRequest, CPContext context, CPConnection conn) throws Exception {
      String action = httpServletRequest.getParameter("action");
      String data = httpServletRequest.getParameter("data");
      JSONObject jsonObj = new JSONObject(data);
      JSONArray o = null;
      if(action.equals("getChildren")) {
         JSONObject node = jsonObj.getJSONObject("node");
         String widgetId = node.getString("widgetId");
         if("Root".equals(widgetId)) {
            o = this.getCommsStatus(conn, context.getUserId());
         } else if("InBox".equals(widgetId)) {
            o = this.getInboxMessages(httpServletRequest, context.getUserId());
         }
      }

      return o;
   }

   private JSONArray getCommsStatus(CPConnection conn, String userId) throws Exception {
      JSONArray list = new JSONArray();
      EntityList eList = conn.getMessagesProcess().getInBoxForUser(userId);
      int inBoxSize = eList.getNumRows();
      eList = conn.getMessagesProcess().getUnreadInBoxForUser(userId);
      int inBoxUnReadSize = eList.getNumRows();
      eList = conn.getMessagesProcess().getSentItemsForUser(userId);
      int outBoxSize = eList.getNumRows();
      JSONObject object = new JSONObject();
      StringBuilder title = new StringBuilder();
      title.append("Inbox :").append("&nbsp;");
      title.append(inBoxSize).append("&nbsp;").append("(").append(inBoxUnReadSize).append(")");
      object.put("title", title.toString());
      object.put("widgetId", "InBox");
      object.put("isFolder", true);
      object.put("rankId", 0);
      object.put("actionsDisabled", "none");
      list.put(object);
      object = new JSONObject();
      title = new StringBuilder();
      title.append("Sent Items :").append("&nbsp;").append(outBoxSize);
      object.put("title", title.toString());
      object.put("widgetId", "SentItems");
      object.put("isFolder", false);
      object.put("rankId", 1);
      object.put("actionsDisabled", "none");
      list.put(object);
      return list;
   }

   private JSONArray getInboxMessages(HttpServletRequest request, String userId) throws Exception {
      JSONArray list = new JSONArray();
      RecentMessages messageListHelper = new RecentMessages();
      List messageList = messageListHelper.getLastMessages(request);
      int size = messageList.size();
      if(size > 0) {
         int object = 0;
         Iterator title = messageList.iterator();

         while(title.hasNext()) {
            MessageDTO dto = (MessageDTO)title.next();
            JSONObject object1 = new JSONObject();
            StringBuilder title1 = new StringBuilder();
            title1.append("From :").append("&nbsp;");
            title1.append(dto.getFromUser()).append("&nbsp;");
            title1.append("Subject :").append("&nbsp;");
            title1.append(dto.getSubject()).append("&nbsp;");
            object1.put("title", title1.toString());
            StringBuilder widget = new StringBuilder();
            widget.append("Message_").append(dto.getMessageId());
            object1.put("widgetId", widget.toString());
            object1.put("isFolder", false);
            object1.put("rankId", object);
            object1.put("messageId", (double)dto.getMessageId());
            object1.put("actionsDisabled", "none");
            ++object;
            list.put(object1);
         }
      } else {
         JSONObject var13 = new JSONObject();
         StringBuilder var14 = new StringBuilder();
         var14.append("No messages available for User ").append(userId);
         var13.put("title", var14.toString());
         var13.put("widgetId", "No_Messages");
         var13.put("isFolder", false);
         var13.put("rankId", 0);
         var13.put("actionsDisabled", "none");
         list.put(var13);
      }

      return list;
   }
}
