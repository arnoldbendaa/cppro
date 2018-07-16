package com.cedar.cp.ejb.api.xmlform.convert;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ExcelIOEditorSessionLocalHome extends EJBLocalHome {

	ExcelIOEditorSessionLocal create() throws CreateException;
}
