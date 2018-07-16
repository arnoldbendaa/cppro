// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.dwr;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.dwr.CPBaseDWRRequest;
import com.cedar.cp.utc.struts.message.MessageDTO;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class RecentMessages extends CPBaseDWRRequest {

   public List getLastMessages(HttpServletRequest request) {
      CPContext cont = this.getCPContextDWR(request);
      CPConnection conn = cont.getCPConnection();
      EntityList list = conn.getListHelper().getSummaryUnreadMessagesForUser(cont.getUserId());
      int size = list.getNumRows();
      ArrayList messages = new ArrayList(size);

      for(int i = 0; i < size; ++i) {
         MessageDTO dto = new MessageDTO();
         dto.setMessageId(((Long)list.getValueAt(i, "MessageId")).longValue());
         dto.setMessageUserId(((Long)list.getValueAt(i, "MessageUserId")).longValue());
         dto.setSubject(list.getValueAt(i, "Subject"));
         dto.setRead(list.getValueAt(i, "Read"));
         dto.setDate(list.getValueAt(i, "CreatedTime"));
         dto.setHasAttachment(((Boolean)list.getValueAt(i, "col6")).booleanValue());
         EntityList fromList = conn.getListHelper().getMessageFromUser(dto.getMessageId());
         StringBuffer name = new StringBuffer();
         StringBuffer fullname = new StringBuffer();

         for(int j = 0; j < fromList.getNumRows(); ++j) {
            if(j != 0) {
               name.append(";");
               fullname.append(";");
            }

            name.append(fromList.getValueAt(j, "Name"));
            fullname.append(fromList.getValueAt(j, "FullName"));
         }

         if(name.length() > 0) {
            dto.setFromUser_VisID(name.toString());
            dto.setFromUser(fullname.toString());
         } else {
            dto.setFromUser_VisID("System");
            dto.setFromUser("System");
         }

         messages.add(dto);
      }

      return messages;
   }
}
