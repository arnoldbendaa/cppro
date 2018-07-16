package com.cedar.cp.api.model.globalmapping2.extsys;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType;
import java.util.List;

public interface FinanceValueTypeOwner {

   EntityRef getEntityRef();

   List<FinanceValueType> getFinanceValueTypes();
}
