// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.amm;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.amm.AmmModelCK;
import com.cedar.cp.dto.model.amm.AmmModelPK;
import com.cedar.cp.ejb.impl.model.amm.AmmModelEVO;
import javax.ejb.EJBLocalObject;

public interface AmmModelLocal extends EJBLocalObject {

   AmmModelEVO getDetails(String var1) throws ValidationException;

   AmmModelEVO getDetails(AmmModelCK var1, String var2) throws ValidationException;

   AmmModelPK generateKeys();

   void setDetails(AmmModelEVO var1);

   AmmModelEVO setAndGetDetails(AmmModelEVO var1, String var2);
}
