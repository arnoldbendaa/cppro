package com.cedar.cp.utc.struts.message;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ListHelper;
import com.cedar.cp.utc.common.CPBaseAJAXAction;
import com.cedar.cp.utc.common.CPContext;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.json.JSONArray;
import org.json.JSONObject;

public class MessageData extends CPBaseAJAXAction {
	private static SimpleDateFormat sDate = new SimpleDateFormat("dd/MMM/yy");
	private static SimpleDateFormat sTime = new SimpleDateFormat("HH:mm");

	public Object processRequest(ActionForm actionForm, HttpServletRequest httpServletRequest, CPContext context, CPConnection conn) throws Exception {
		String folder = httpServletRequest.getParameter("folder");
		String maxFetch = httpServletRequest.getParameter("maxFetch");

		int iFolder = 0;
		int iMaxFextch = 999;

		if (folder != null) {
			try {
				iFolder = Integer.parseInt(folder);
			} catch (NumberFormatException nfe) {
				iFolder = 0;
			}
		}

		if (maxFetch != null) {
			try {
				iMaxFextch = Integer.parseInt(maxFetch);
			} catch (NumberFormatException nfe) {
				iMaxFextch = 999;
			}

		}

		EntityList list = getMessageList(context, iFolder, 0, iMaxFextch);
		int size = list.getNumRows();

		JSONObject wrapper = new JSONObject();
		JSONArray data = new JSONArray();
		for (int i = 0; i < size; i++) {
			JSONObject message = new JSONObject();
			message.put("id", i + 1);
			message.put("MessageId", list.getValueAt(i, "MessageId"));
			message.put("MessageUserId", list.getValueAt(i, "MessageUserId"));
			message.put("Subject", list.getValueAt(i, "Subject"));
			message.put("Read", list.getValueAt(i, "Read"));
			message.put("Attach", list.getValueAt(i, "Attach"));

			Timestamp ts = (Timestamp) list.getValueAt(i, "CreatedTime");

			message.put("Date", sDate.format(Long.valueOf(ts.getTime())));
			message.put("Time", sTime.format(Long.valueOf(ts.getTime())));

			EntityList userList = (EntityList) list.getValueAt(i, "FromDetail");

			StringBuffer name = new StringBuffer();
			StringBuffer fullname = new StringBuffer();
			for (int j = 0; j < userList.getNumRows(); j++) {
				if (j != 0) {
					name.append(";");
					fullname.append(";");
				}

				name.append(userList.getValueAt(j, "Name"));
				fullname.append(userList.getValueAt(j, "FullName"));
			}

			if (name.length() > 0) {
				message.put("From_VIS_ID", name.toString());
				message.put("From", fullname.toString());
			} else {
				message.put("From_VIS_ID", "System");
				message.put("From", "System");
			}

			userList = (EntityList) list.getValueAt(i, "ToDetail");

			name = new StringBuffer();
			fullname = new StringBuffer();
			for (int j = 0; j < userList.getNumRows(); j++) {
				if (j != 0) {
					name.append(";");
					fullname.append(";");
				}

				name.append(userList.getValueAt(j, "Name"));
				fullname.append(userList.getValueAt(j, "FullName"));
			}

			message.put("To_VIS_ID", name.toString());
			message.put("To", fullname.toString());

			data.put(message);
		}

		wrapper.put("identifier", "id");
		wrapper.put("items", data);

		return wrapper;
	}

	public EntityList getMessageList(CPContext cntx, int folder, int from, int size) {
		CPConnection conn = cntx.getCPConnection();
		int to;
		if (from == 0) {
			to = size;
		} else {
			to = from + size - 1;
		}
		return conn.getListHelper().getMailDetailForUser(cntx.getUserId(), folder, from, to);
	}
}