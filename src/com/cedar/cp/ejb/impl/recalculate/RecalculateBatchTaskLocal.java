package com.cedar.cp.ejb.impl.recalculate;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskCK;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskEVO;

import javax.ejb.EJBLocalObject;

public interface RecalculateBatchTaskLocal extends EJBLocalObject {

	RecalculateBatchTaskEVO getDetails(String var1) throws ValidationException;

	RecalculateBatchTaskEVO getDetails(RecalculateBatchTaskCK var1, String var2) throws ValidationException;

   void setDetails(RecalculateBatchTaskEVO var1);

   RecalculateBatchTaskEVO setAndGetDetails(RecalculateBatchTaskEVO var1, String var2);
}
