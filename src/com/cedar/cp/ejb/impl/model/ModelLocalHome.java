// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.impl.model.ModelEVO;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

public interface ModelLocalHome extends EJBLocalHome {

	ModelLocal create(ModelEVO var1) throws EJBException, CreateException;

	ModelLocal findByPrimaryKey(ModelPK var1) throws FinderException;
}
