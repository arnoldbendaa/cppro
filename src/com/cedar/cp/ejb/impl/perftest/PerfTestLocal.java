// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.perftest;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.perftest.PerfTestPK;
import com.cedar.cp.ejb.impl.perftest.PerfTestEVO;
import javax.ejb.EJBLocalObject;

public interface PerfTestLocal extends EJBLocalObject {

   PerfTestEVO getDetails(String var1) throws ValidationException;

   PerfTestPK generateKeys();

   void setDetails(PerfTestEVO var1);

   PerfTestEVO setAndGetDetails(PerfTestEVO var1, String var2);
}
