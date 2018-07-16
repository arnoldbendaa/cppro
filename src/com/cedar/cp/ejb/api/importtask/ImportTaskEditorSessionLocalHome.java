package com.cedar.cp.ejb.api.importtask;

import com.cedar.cp.ejb.api.importtask.ImportTaskEditorSessionLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ImportTaskEditorSessionLocalHome extends EJBLocalHome {

	ImportTaskEditorSessionLocal create() throws CreateException;
}
