// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.distribution;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.distribution.DistributionCK;
import com.cedar.cp.dto.report.distribution.DistributionPK;
import com.cedar.cp.ejb.impl.report.distribution.DistributionEVO;
import javax.ejb.EJBLocalObject;

public interface DistributionLocal extends EJBLocalObject {

   DistributionEVO getDetails(String var1) throws ValidationException;

   DistributionEVO getDetails(DistributionCK var1, String var2) throws ValidationException;

   DistributionPK generateKeys();

   void setDetails(DistributionEVO var1);

   DistributionEVO setAndGetDetails(DistributionEVO var1, String var2);
}
