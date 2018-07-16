// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.destination.internal;

import com.cedar.cp.dto.report.destination.internal.InternalDestinationPK;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationEVO;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface InternalDestinationLocalHome extends EJBLocalHome {

   InternalDestinationLocal create(InternalDestinationEVO var1) throws EJBException, CreateException;

   InternalDestinationLocal findByPrimaryKey(InternalDestinationPK var1) throws FinderException;
}
