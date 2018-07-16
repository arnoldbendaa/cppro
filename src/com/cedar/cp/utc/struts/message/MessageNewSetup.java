// Decompiled by: Fernflower v0.8.6
// Date: 12.08.2012 13:28:56
// Copyright: 2008-2012, Stiver
// Home page: http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.message;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.common.CPContextCache;
import com.cedar.cp.utc.struts.message.MessageDTO;
import com.cedar.cp.utc.struts.message.MessageNewForm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MessageNewSetup extends CPAction {
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws Exception
	{
		String type = httpServletRequest.getParameter("MessageType");
		String messageID = httpServletRequest.getParameter("messageId");
		String values = httpServletRequest.getParameter("list");
		String[] params = null;

		if (values != null)
		{
			String[] items = values.split(",");
			params = new String[items.length];
			for (int i = 0; i < items.length; i++)
			{
				params[i] = items[i];
			}
		}

		long lMessageId = 0L;
		if (messageID != null)
		{
			lMessageId = Long.parseLong(messageID);
		}

		MessageDTO dto = new MessageDTO();
		StringBuffer mailBody = new StringBuffer();
		boolean doContent = false;

		MessageNewForm myform = (MessageNewForm) actionForm;

		if ("approvers".equals(httpServletRequest.getParameter("pageSource")))
		{
			CPContext cntx = getCPContext(httpServletRequest);
			EntityList list = cntx.getCPConnection().getListHelper().getUserMessageAttributesForMultipleIds(params);
			populateNames(dto, list);
		}
		else if (type != null)
		{
			if (type.equals("new"))
			{
				String source = myform.getPageSource();
				if (source == null)
				{
					source = "";
				}

				CPContext cntx = getCPContext(httpServletRequest);
				if ((source.equals("budgetState")) || (source.equals("budgetHistory")))
				{
					EntityList list;
					if (myform.isCascade())
					{
						list = cntx.getCPConnection().getBudgetUsersProcess().getBudgetUserDetailsNodeDown(myform.getBudgetCycleId(), myform.getStructureElementId(), myform.getStructureId());
					}
					else
					{
						if (myform.isContactApprover())
						{
							list = cntx.getCPConnection().getBudgetUsersProcess().getBudgetUserAuthDetailsNodeUp(myform.getBudgetCycleId(), myform.getStructureElementId(), myform.getStructureId());
						}
						else
						{
							list = cntx.getCPConnection().getBudgetUsersProcess().getBudgetUserDetails(myform.getBudgetCycleId(), new int[] { myform.getStructureElementId() });
						}
					}
					populateNames(dto, list);
				}
				else if (source.equals("budgetHistoryAll"))
				{
					int[] ids = myform.getContactId();

					EntityList list = cntx.getCPConnection().getBudgetUsersProcess().getBudgetUserDetails(myform.getBudgetCycleId(), ids);
					populateNames(dto, list);
				}
				else if (source.equals("budgetLimit"))
				{
					EntityList list = cntx.getCPConnection().getListHelper().getUsersForModelAndElement(myform.getModelId(), myform.getStructureElementId());
					populateNames(dto, list);
				}
				else if (source.equals("activeUsers"))
				{
					EntityList list = cntx.getCPConnection().getListHelper().getUserMessageAttributesForId(myform.getUserId());
					populateNames(dto, list);
				}
				else if (source.equals("allActiveUsers"))
				{
					Iterator iter = CPContextCache.getContextSnapShot().values().iterator();

					ArrayList userList = new ArrayList();
					while (iter.hasNext())
					{
						CPContext cacheContext = (CPContext) iter.next();
						if (cacheContext.getSession() != null)
						{
							userList.add(cacheContext.getUserId());
						}
					}
					String[] users = (String[]) userList.toArray(new String[0]);
					EntityList list = cntx.getCPConnection().getListHelper().getUserMessageAttributesForMultipleIds(users);
					populateNames(dto, list);
				}
			}
			else if (type.equals("reply"))
			{
				dto = getOriginalMessage(getCPContext(httpServletRequest), lMessageId);

				dto.setToUser(dto.getFromUser());
				dto.setToUser_VisID(dto.getFromUser_VisID());
				dto.setSubject("Re: " + dto.getSubject());
				dto.setMessageType(0);
				doContent = true;
			}
			else if (type.equals("forward"))
			{
				dto = getOriginalMessage(getCPContext(httpServletRequest), lMessageId, true);
				dto.setToUser("");
				dto.setToUser_VisID("");
				dto.setSubject("Fw: " + dto.getSubject());
				dto.setMessageType(0);
				doContent = true;
			}

			if (doContent)
			{
				mailBody.append("\n");
				mailBody.append("\n");
				mailBody.append("\n");

				StringTokenizer st = new StringTokenizer(dto.getContent(), "\n", true);

				while (st.hasMoreTokens())
				{
					String value = st.nextToken();

					if (value.indexOf("\n") < 0)
					{
						mailBody.append(">> ");
					}
					mailBody.append(value);
				}
			}
			dto.setContent(mailBody.toString());
		}

		myform.setMessage(dto);

		return actionMapping.findForward("success");
	}

	private MessageDTO populateNames(MessageDTO dto, EntityList list)
	{
		String names = "";
		String ids = "";
		String emails = "";

		int rows = list.getNumRows();

		for (int i = 0; i < rows; i++)
		{
			if (i == 0)
			{
				ids = ids + list.getValueAt(i, "Name");
				names = names + list.getValueAt(i, "FullName");
				emails = emails + list.getValueAt(i, "EMailAddress");
			}
			else
			{
				ids = ids + "; " + list.getValueAt(i, "Name");
				names = names + "; " + list.getValueAt(i, "FullName");
				emails = emails + ";" + list.getValueAt(i, "EMailAddress");
			}
		}

		dto.setToUser(names);
		dto.setToUser_VisID(ids);
		dto.setToUserEmailAddress(emails);

		return dto;
	}

	private MessageDTO getOriginalMessage(CPContext cnt, long messageId)
	{
		return getOriginalMessage(cnt, messageId, false);
	}

	private MessageDTO getOriginalMessage(CPContext cnt, long messageId, boolean includeAttatch)
	{
		CPConnection conn = cnt.getCPConnection();
		EntityList list = conn.getListHelper().getMessageForId(messageId, cnt.getUserId());
		int size = list.getNumRows();
		if (size == 0)
		{
			list = conn.getListHelper().getMessageForIdSentItem(messageId, cnt.getUserId());
			size = list.getNumRows();
		}

		MessageDTO dto = new MessageDTO();
		for (int i = 0; i < size; i++)
		{
			dto.setMessageId(((Long) list.getValueAt(i, "MessageId")).longValue());
			dto.setSubject(list.getValueAt(i, "Subject"));
			dto.setContent(list.getValueAt(i, "Content"));
			dto.setRead(list.getValueAt(i, "Read"));
			dto.setDate(list.getValueAt(i, "CreatedTime"));

			if (includeAttatch)
			{
				dto.setOriginalMessageId(messageId);
			}

			EntityList fromList = conn.getListHelper().getMessageFromUser(dto.getMessageId());
			StringBuffer name = new StringBuffer();
			StringBuffer fullname = new StringBuffer();
			StringBuffer mail = new StringBuffer();
			String mailaddress = "";
			for (int j = 0; j < fromList.getNumRows(); j++)
			{
				if (j != 0)
				{
					name.append(";");
					fullname.append(";");
					if ((mail.length() > 0) && (mail.lastIndexOf(";") != mail.length()))
					{
						mail.append(";");
					}
				}

				name.append(fromList.getValueAt(j, "Name"));
				fullname.append(fromList.getValueAt(j, "FullName"));
				if (fromList.getValueAt(j, "EMailAddress") != null)
				{
					mailaddress = fromList.getValueAt(j, "EMailAddress").toString();
				}
				if (mailaddress.length() > 0)
				{
					mail.append(mailaddress);
				}
			}

			if (name.length() > 0)
			{
				dto.setFromUser_VisID(name.toString());
				dto.setFromUser(fullname.toString());
			}
			else
			{
				dto.setFromUser_VisID("System");
				dto.setFromUser("System");
			}
		}
		return dto;
	}
}
