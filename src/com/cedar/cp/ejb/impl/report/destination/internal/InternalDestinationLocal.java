// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.destination.internal;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationCK;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationPK;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationEVO;
import javax.ejb.EJBLocalObject;

public interface InternalDestinationLocal extends EJBLocalObject {

   InternalDestinationEVO getDetails(String var1) throws ValidationException;

   InternalDestinationEVO getDetails(InternalDestinationCK var1, String var2) throws ValidationException;

   InternalDestinationPK generateKeys();

   void setDetails(InternalDestinationEVO var1);

   InternalDestinationEVO setAndGetDetails(InternalDestinationEVO var1, String var2);
}