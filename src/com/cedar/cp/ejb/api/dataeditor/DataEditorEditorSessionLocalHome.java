package com.cedar.cp.ejb.api.dataeditor;

import com.cedar.cp.ejb.api.dataeditor.DataEditorEditorSessionLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface DataEditorEditorSessionLocalHome extends EJBLocalHome {

   DataEditorEditorSessionLocal create() throws CreateException;
}
