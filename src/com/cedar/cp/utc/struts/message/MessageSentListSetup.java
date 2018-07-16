// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:57
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.message;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.message.MessageListSetup;

public class MessageSentListSetup extends MessageListSetup {

   public EntityList getMessageList(CPContext cntx) {
      CPConnection conn = cntx.getCPConnection();
      return conn.getListHelper().getSentItemsForUser(cntx.getUserId());
   }
}
