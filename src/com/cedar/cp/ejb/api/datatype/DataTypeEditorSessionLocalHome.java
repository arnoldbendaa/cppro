// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.datatype;

import com.cedar.cp.ejb.api.datatype.DataTypeEditorSessionLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface DataTypeEditorSessionLocalHome extends EJBLocalHome {

   DataTypeEditorSessionLocal create() throws CreateException;
}
