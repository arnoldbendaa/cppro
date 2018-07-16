// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import javax.ejb.EJBLocalObject;

public interface ModelLocal extends EJBLocalObject {

   ModelEVO getDetails(String var1) throws ValidationException;

   ModelEVO getDetails(ModelCK var1, String var2) throws ValidationException;

   ModelPK generateKeys();

   void setDetails(ModelEVO var1);

   ModelEVO setAndGetDetails(ModelEVO var1, String var2);

   void flush();
}
