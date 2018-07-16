// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.datatype;

import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import com.cedar.cp.ejb.impl.datatype.DataTypeLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface DataTypeLocalHome extends EJBLocalHome {

   DataTypeLocal create(DataTypeEVO var1) throws EJBException, CreateException;

   DataTypeLocal findByPrimaryKey(DataTypePK var1) throws FinderException;
}
