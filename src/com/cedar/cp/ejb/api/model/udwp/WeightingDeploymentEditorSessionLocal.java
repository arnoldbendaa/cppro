// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.udwp;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentCK;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentEditorSessionCSO;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentEditorSessionSSO;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface WeightingDeploymentEditorSessionLocal extends EJBLocalObject {

   WeightingDeploymentEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   WeightingDeploymentEditorSessionSSO getNewItemData(int var1) throws EJBException;

   WeightingDeploymentCK insert(WeightingDeploymentEditorSessionCSO var1) throws ValidationException, EJBException;

   EntityList getOwnershipData(int var1, Object var2) throws EJBException;

   WeightingDeploymentCK copy(WeightingDeploymentEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(WeightingDeploymentEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   EntityList queryDeployments() throws EJBException;
}
