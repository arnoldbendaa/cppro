package com.cedar.cp.utc.struts.message;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ListHelper;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MessageListSetup extends CPAction {
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		if ((actionForm instanceof MessageListForm)) {
			MessageListForm myForm = (MessageListForm) actionForm;
			CPContext cont = getCPContext(httpServletRequest);

			EntityList list = getMessageList(cont, myForm.getFrom(), myForm.getDisplayMessages());
			int size = list.getNumRows();

			List messages = new ArrayList(size);

			for (int i = 0; i < size; i++) {
				MessageDTO dto = new MessageDTO();
				dto.setMessageId(((Long) list.getValueAt(i, "MessageId")).longValue());
				dto.setMessageUserId(((Long) list.getValueAt(i, "MessageUserId")).longValue());
				dto.setSubject(list.getValueAt(i, "Subject"));
				dto.setRead(list.getValueAt(i, "Read"));
				dto.setDate(list.getValueAt(i, "CreatedTime"));
				dto.setHasAttachment(((Boolean) list.getValueAt(i, "Attach")).booleanValue());

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
					dto.setFromUser_VisID(name.toString());
					dto.setFromUser(fullname.toString());
				} else {
					dto.setFromUser_VisID("System");
					dto.setFromUser("System");
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

				dto.setToUser_VisID(name.toString());
				dto.setToUser(fullname.toString());

				messages.add(dto);
			}

			myForm.setMessages(messages);
			myForm.setSize(messages.size());
		}

		return actionMapping.findForward("success");
	}

	public EntityList getMessageList(CPContext cntx, int from, int size) {
		CPConnection conn = cntx.getCPConnection();
		int to;
		if (from == 0)
			to = size;
		else
			to = from + size - 1;
		return conn.getListHelper().getMailDetailForUser(cntx.getUserId(), 0, from, to);
	}
}