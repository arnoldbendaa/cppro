// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.datatype;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.datatype.DataTypeCK;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import javax.ejb.EJBLocalObject;

public interface DataTypeLocal extends EJBLocalObject {

   DataTypeEVO getDetails(String var1) throws ValidationException;

   DataTypeEVO getDetails(DataTypeCK var1, String var2) throws ValidationException;

   DataTypePK generateKeys();

   void setDetails(DataTypeEVO var1);

   DataTypeEVO setAndGetDetails(DataTypeEVO var1, String var2);
}
