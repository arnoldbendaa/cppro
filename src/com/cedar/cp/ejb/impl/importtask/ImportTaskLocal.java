package com.cedar.cp.ejb.impl.importtask;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.importtask.ImportTaskCK;
import com.cedar.cp.dto.importtask.ImportTaskPK;
import com.cedar.cp.ejb.impl.importtask.ImportTaskEVO;
import javax.ejb.EJBLocalObject;

public interface ImportTaskLocal extends EJBLocalObject {

   ImportTaskEVO getDetails(String var1) throws ValidationException;

   ImportTaskEVO getDetails(ImportTaskCK var1, String var2) throws ValidationException;

   ImportTaskPK generateKeys();

   void setDetails(ImportTaskEVO var1);

   ImportTaskEVO setAndGetDetails(ImportTaskEVO var1, String var2);
}
