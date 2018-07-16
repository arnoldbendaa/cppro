// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.destination.external;

import com.cedar.cp.dto.report.destination.external.ExternalDestinationPK;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationEVO;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface ExternalDestinationLocalHome extends EJBLocalHome {

   ExternalDestinationLocal create(ExternalDestinationEVO var1) throws EJBException, CreateException;

   ExternalDestinationLocal findByPrimaryKey(ExternalDestinationPK var1) throws FinderException;
}
