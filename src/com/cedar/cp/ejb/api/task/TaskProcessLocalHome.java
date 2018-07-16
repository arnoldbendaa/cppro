// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.task;

import com.cedar.cp.ejb.api.task.TaskProcessLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface TaskProcessLocalHome extends EJBLocalHome {

   TaskProcessLocal create() throws CreateException;
}
