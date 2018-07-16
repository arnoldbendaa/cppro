package com.cedar.cp.ejb.impl.importtask;

import com.cedar.cp.dto.importtask.ImportTaskPK;
import com.cedar.cp.ejb.impl.importtask.ImportTaskEVO;
import com.cedar.cp.ejb.impl.importtask.ImportTaskLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface ImportTaskLocalHome extends EJBLocalHome {

   ImportTaskLocal create(ImportTaskEVO var1) throws EJBException, CreateException;

   ImportTaskLocal findByPrimaryKey(ImportTaskPK var1) throws FinderException;
}
