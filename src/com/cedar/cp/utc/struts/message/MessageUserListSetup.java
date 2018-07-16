// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.message;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.message.MessageUserListForm;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MessageUserListSetup extends CPAction {

   public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
	   CPContext ctxt = getCPContext(httpServletRequest);

	    EntityList entity = ctxt.getCPConnection().getUsersProcess().getAllNonDisabledUsers();
	    List users = entity.getDataAsList();

	    if ((actionForm instanceof MessageUserListForm))
	    {
	      MessageUserListForm myForm = (MessageUserListForm)actionForm;
	      String searchId = myForm.getSearchUserId().toUpperCase();
	      String searchUser = myForm.getSearchUserName().toUpperCase();

	      if ((searchId.length() == 0) && (searchUser.length() == 0))
	      {
	        myForm.setUsers(users);
	        myForm.setSize(users.size());
	      }
	      else
	      {
	        List userList = new ArrayList();
	        Iterator iter = users.iterator();
	        while (iter.hasNext())
	        {
	          List item = (List)iter.next();
	          Object key = item.get(0);
	          String id = key.toString().toUpperCase();
	          String name = item.get(1).toString().toUpperCase();

	          if ((searchId.length() > 0) && (searchUser.length() > 0))
	          {
	            if ((id.indexOf(searchId) >= 0) && (name.indexOf(searchUser) >= 0))
	            {
	              userList.add(item);
	            }
	          }
	          else if (searchId.length() > 0)
	          {
	            if (id.indexOf(searchId) >= 0)
	            {
	              userList.add(item);
	            }
	          }
	          else if (searchUser.length() > 0)
	          {
	            if (name.indexOf(searchUser) >= 0)
	            {
	              userList.add(item);
	            }
	          }
	        }

	        myForm.setUsers(userList);
	        myForm.setSize(userList.size());
	      }
	    }

	    return actionMapping.findForward("success");
   }
}
