package com.cedar.cp.ejb.impl.model.globalmapping2;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2CK;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2PK;
import com.cedar.cp.ejb.impl.model.globalmapping2.GlobalMappedModel2EVO;
import javax.ejb.EJBLocalObject;

public interface GlobalMappedModel2Local extends EJBLocalObject {

   GlobalMappedModel2EVO getDetails(String var1) throws ValidationException;

   GlobalMappedModel2EVO getDetails(GlobalMappedModel2CK var1, String var2) throws ValidationException;

   GlobalMappedModel2PK generateKeys();

   void setDetails(GlobalMappedModel2EVO var1);

   GlobalMappedModel2EVO setAndGetDetails(GlobalMappedModel2EVO var1, String var2);
}
