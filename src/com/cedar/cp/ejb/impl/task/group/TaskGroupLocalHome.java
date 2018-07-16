// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task.group;

import com.cedar.cp.dto.task.group.TaskGroupPK;
import com.cedar.cp.ejb.impl.task.group.TaskGroupEVO;
import com.cedar.cp.ejb.impl.task.group.TaskGroupLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface TaskGroupLocalHome extends EJBLocalHome {

   TaskGroupLocal create(TaskGroupEVO var1) throws EJBException, CreateException;

   TaskGroupLocal findByPrimaryKey(TaskGroupPK var1) throws FinderException;
}
