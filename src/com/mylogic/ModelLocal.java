// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.mylogic;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelEVO;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.Remote;
import javax.ejb.RemoveException;

public interface ModelLocal {

   ModelEVO getDetails(String var1) throws ValidationException;

   ModelEVO getDetails(ModelCK var1, String var2) throws ValidationException;

   ModelPK generateKeys();

   void setDetails(ModelEVO var1);

   ModelEVO setAndGetDetails(ModelEVO var1, String var2);

   void flush();
   
   //by arnold
   public ModelPK ejbCreate(ModelEVO details) throws CreateException, EJBException;
   
   public void ejbPostCreate(ModelEVO details) throws CreateException, EJBException;
   
   public void ejbRemove() throws RemoveException;
   
   public ModelPK ejbFindByPrimaryKey(ModelPK pk) throws FinderException;
   public void ejbLoad() ;
   public void ejbStore() throws EJBException;
   public ModelPK getPK();
   public void ejbActivate();
   public void ejbPassivate();
   public void setEntityContext(EntityContext entityContext);
   public void unsetEntityContext();
   ModelDAO getDAO();
   public ModelEVO getDetails(ModelCK paramCK) throws ValidationException;
}
