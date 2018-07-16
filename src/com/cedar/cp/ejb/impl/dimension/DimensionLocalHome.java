// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionLocal;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface DimensionLocalHome extends EJBLocalHome {

   DimensionLocal create(DimensionEVO var1) throws EJBException, CreateException;

   DimensionLocal findByPrimaryKey(DimensionPK var1) throws FinderException;

   Collection findAll() throws FinderException, EJBException;
}
