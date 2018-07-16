// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.mapping;

import com.cedar.cp.dto.model.mapping.MappedModelPK;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelEVO;
import com.cedar.cp.ejb.impl.model.mapping.MappedModelLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface MappedModelLocalHome extends EJBLocalHome {

   MappedModelLocal create(MappedModelEVO var1) throws EJBException, CreateException;

   MappedModelLocal findByPrimaryKey(MappedModelPK var1) throws FinderException;
}
