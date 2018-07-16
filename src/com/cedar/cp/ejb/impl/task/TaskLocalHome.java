// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task;

import com.cedar.cp.dto.task.TaskPK;
import com.cedar.cp.ejb.impl.task.TaskEVO;
import com.cedar.cp.ejb.impl.task.TaskLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface TaskLocalHome extends EJBLocalHome {

   TaskLocal create(TaskEVO var1) throws EJBException, CreateException;

   TaskLocal findByPrimaryKey(TaskPK var1) throws FinderException;
}
