package com.cedar.cp.ejb.impl.model.globalmapping2;

import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2PK;
import com.cedar.cp.ejb.impl.model.globalmapping2.GlobalMappedModel2EVO;
import com.cedar.cp.ejb.impl.model.globalmapping2.GlobalMappedModel2Local;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface GlobalMappedModel2LocalHome extends EJBLocalHome {

   GlobalMappedModel2Local create(GlobalMappedModel2EVO var1) throws EJBException, CreateException;

   GlobalMappedModel2Local findByPrimaryKey(GlobalMappedModel2PK var1) throws FinderException;
}
