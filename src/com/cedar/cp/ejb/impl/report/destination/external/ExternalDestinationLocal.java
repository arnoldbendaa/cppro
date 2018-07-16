// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.destination.external;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationCK;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationPK;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationEVO;
import javax.ejb.EJBLocalObject;

public interface ExternalDestinationLocal extends EJBLocalObject {

   ExternalDestinationEVO getDetails(String var1) throws ValidationException;

   ExternalDestinationEVO getDetails(ExternalDestinationCK var1, String var2) throws ValidationException;

   ExternalDestinationPK generateKeys();

   void setDetails(ExternalDestinationEVO var1);

   ExternalDestinationEVO setAndGetDetails(ExternalDestinationEVO var1, String var2);
}
