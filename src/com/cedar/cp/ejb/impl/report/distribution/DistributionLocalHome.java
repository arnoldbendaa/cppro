// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.distribution;

import com.cedar.cp.dto.report.distribution.DistributionPK;
import com.cedar.cp.ejb.impl.report.distribution.DistributionEVO;
import com.cedar.cp.ejb.impl.report.distribution.DistributionLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface DistributionLocalHome extends EJBLocalHome {

   DistributionLocal create(DistributionEVO var1) throws EJBException, CreateException;

   DistributionLocal findByPrimaryKey(DistributionPK var1) throws FinderException;
}
