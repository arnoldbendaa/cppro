// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.budgetcycle;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.dto.message.MessageImpl;
import com.cedar.cp.dto.model.BudgetStateTaskRequest;
import com.cedar.cp.ejb.api.message.MessageHelperServer;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import java.sql.Connection;
import javax.naming.InitialContext;

public class BudgetStateTask extends AbstractTask {

   public int getReportType() {
      return 0;
   }

   public String getEntityName() {
      return "BudgetStateTask";
   }

   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      BudgetStateTaskRequest request = (BudgetStateTaskRequest)this.getRequest();
      EntityList modelList = this.getCPConnection().getListHelper().getAllModelsWebForUser(getCPConnection().getUserContext().getUserId());

      for(int i = 0; i < modelList.getNumRows(); ++i) {
         int modelId = ((Integer)modelList.getValueAt(i, "ModelId")).intValue();
         String modelVisId = modelList.getValueAt(i, "VisId").toString();
         EntityList bcList = this.getCPConnection().getListHelper().getBudgetCyclesForModelWithState(modelId, 1);

         for(int j = 0; j < bcList.getNumRows(); ++j) {
            int cycleId = ((Integer)bcList.getValueAt(j, "BudgetCycleId")).intValue();
            String cycleVisId = bcList.getValueAt(j, "VisId").toString();
            EntityList contactList = this.getCPConnection().getListHelper().getContactLocations(cycleId, request.getDaysBefore());
            if(contactList.getNumRows() > 0) {
               int[] strucIds = new int[contactList.getNumRows()];

               for(int contactUserList = 0; contactUserList < contactList.getNumRows(); ++contactUserList) {
                  strucIds[contactUserList] = ((Integer)contactList.getValueAt(contactUserList, "SructureElementId")).intValue();
               }

               EntityList var18 = this.getCPConnection().getBudgetUsersProcess().getBudgetUserDetails(cycleId, strucIds);
               int contactSize = var18.getNumRows();
               if(contactSize > 0) {
                  MessageHelperServer server = new MessageHelperServer(new InitialContext(), false);
                  MessageImpl msg = new MessageImpl((Object)null);
                  msg.addFromUser("System");
                  msg.setMessageType(SystemPropertyHelper.queryIntegerSystemProperty((Connection)null, "WEB: Alert message type", 0));
                  msg.setSubject("Your Budget is due");

                  for(int content = 0; content < contactSize; ++content) {
                     msg.addToUser(var18.getValueAt(content, "Name").toString());
                  }

                  StringBuffer var19 = new StringBuffer();
                  var19.append("<pre>");
                  var19.append("Your budget needs submitting for");
                  var19.append("\n");
                  var19.append("\t Model :        ");
                  var19.append(modelVisId);
                  var19.append("\n");
                  var19.append("\t Budget Cycle : ");
                  var19.append(cycleVisId);
                  var19.append("\n");
                  var19.append("\n");
                  var19.append(this.createLinkBack());
                  var19.append("</pre>");
                  msg.setContent(var19.toString());
                  this.log("sending budget state message to :" + msg.getToUsers().toString());
                  server.createNewMessage(msg);
               }
            }
         }
      }

   }

   private String createLinkBack() {
      StringBuffer sb = new StringBuffer(100);
      sb.append("<a target=\"_blank\" href=\"");
      sb.append(SystemPropertyHelper.queryStringSystemProperty((Connection)null, "WEB: Root URL", "about:blank"));
      sb.append("\">Collaborative Planning</a>");
      return sb.toString();
   }
}
