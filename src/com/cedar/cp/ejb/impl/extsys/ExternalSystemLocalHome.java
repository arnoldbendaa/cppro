// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:38
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys;

import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEVO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface ExternalSystemLocalHome extends EJBLocalHome {

   ExternalSystemLocal create(ExternalSystemEVO var1) throws EJBException, CreateException;

   ExternalSystemLocal findByPrimaryKey(ExternalSystemPK var1) throws FinderException;
}
