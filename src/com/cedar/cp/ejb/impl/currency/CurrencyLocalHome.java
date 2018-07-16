// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.currency;

import com.cedar.cp.dto.currency.CurrencyPK;
import com.cedar.cp.ejb.impl.currency.CurrencyEVO;
import com.cedar.cp.ejb.impl.currency.CurrencyLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface CurrencyLocalHome extends EJBLocalHome {

   CurrencyLocal create(CurrencyEVO var1) throws EJBException, CreateException;

   CurrencyLocal findByPrimaryKey(CurrencyPK var1) throws FinderException;
}
