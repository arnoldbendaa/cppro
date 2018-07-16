// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.amm;

import com.cedar.cp.dto.model.amm.AmmModelPK;
import com.cedar.cp.ejb.impl.model.amm.AmmModelEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmModelLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface AmmModelLocalHome extends EJBLocalHome {

   AmmModelLocal create(AmmModelEVO var1) throws EJBException, CreateException;

   AmmModelLocal findByPrimaryKey(AmmModelPK var1) throws FinderException;
}
