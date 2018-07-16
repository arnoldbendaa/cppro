package com.cedar.cp.ejb.impl.recalculate;

import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskEVO;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskLocal;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface RecalculateBatchTaskLocalHome extends EJBLocalHome {

	RecalculateBatchTaskLocal create(RecalculateBatchTaskEVO var1) throws EJBException, CreateException;

	RecalculateBatchTaskLocal findByPrimaryKey(RecalculateBatchTaskPK var1) throws FinderException;
}
