package com.cedar.cp.utc.struts.message;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.message.MessagesProcess;
import com.cedar.cp.utc.common.CPBaseAJAXAction;
import com.cedar.cp.utc.common.CPContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.json.JSONArray;
import org.json.JSONObject;

public class MessageList extends CPBaseAJAXAction {
	public Object processRequest(ActionForm actionForm, HttpServletRequest httpServletRequest, CPContext context, CPConnection conn) throws Exception {
		String messageId = httpServletRequest.getParameter("messageId");
		String messageUserId = httpServletRequest.getParameter("messageUserId");

		String messageIds = httpServletRequest.getParameter("messageIds");

		if (messageId != null) {
			long lMessageId = Long.parseLong(messageId);
			long lMessageUserId = Long.parseLong(messageUserId);

			conn.getMessagesProcess().deleteObject(lMessageId, lMessageUserId);
		} else if (messageIds != null) {
			JSONArray js = new JSONArray(messageIds);
			for (int i = 0; i < js.length(); i++) {
				JSONObject mo = js.getJSONObject(i);
				long lMessageId = mo.getLong("messageId");
				long lMessageUserId = mo.getLong("messageUserId");
				conn.getMessagesProcess().deleteObject(lMessageId, lMessageUserId);
			}
		} else {
			String folder = httpServletRequest.getParameter("folder");

			if ("inBox".equals(folder))
				conn.getMessagesProcess().emptyFolder(0, context.getUserId());
			else {
				conn.getMessagesProcess().emptyFolder(1, context.getUserId());
			}
		}
		getCPContext(httpServletRequest).clearCache();
		return null;
	}
}