// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task.group;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.task.group.TaskGroupRICheckELO;
import com.cedar.cp.ejb.impl.task.group.TaskGroupDAO;

public class TaskRIChecker {

   public static void isInUseTaskGroup(CPConnection cpConnection, PrimaryKey entityPK, int itemIndex) throws ValidationException {
      TaskGroupDAO tgDao = new TaskGroupDAO();
      TaskGroupRICheckELO tgELO = tgDao.getTaskGroupRICheck(itemIndex);

      Object eKey;
      String listName;
      do {
         if(!tgELO.hasNext()) {
            return;
         }

         tgELO.next();
         String sKey = tgELO.getParam();
         eKey = cpConnection.getEntityKeyFactory().getKeyFromTokens(sKey);
         String[] extValues = sKey.split("[|]");
         listName = extValues[0].substring(0, extValues[0].length() - 2);
      } while(!entityPK.equals(cpConnection.getListHelper().getEntityRef(eKey, listName).getPrimaryKey()));

      throw new ValidationException(tgELO.getTaskGroupEntityRef().getNarrative());
   }
}
