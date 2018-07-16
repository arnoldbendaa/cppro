package com.cedar.cp.ejb.api.recalculate;

import com.cedar.cp.ejb.api.recalculate.RecalculateBatchTaskEditorSessionLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface RecalculateBatchTaskEditorSessionLocalHome extends EJBLocalHome {

	RecalculateBatchTaskEditorSessionLocal create() throws CreateException;
}
