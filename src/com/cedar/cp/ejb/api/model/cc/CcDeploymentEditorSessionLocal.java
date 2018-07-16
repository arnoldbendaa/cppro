// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.cc;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.model.cc.CcDeploymentCK;
import com.cedar.cp.dto.model.cc.CcDeploymentEditorSessionCSO;
import com.cedar.cp.dto.model.cc.CcDeploymentEditorSessionSSO;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface CcDeploymentEditorSessionLocal extends EJBLocalObject {

   CcDeploymentEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   CcDeploymentEditorSessionSSO getNewItemData(int var1) throws EJBException;

   CcDeploymentCK insert(CcDeploymentEditorSessionCSO var1) throws ValidationException, EJBException;

   EntityList getOwnershipData(int var1, Object var2) throws EJBException;

   CcDeploymentCK copy(CcDeploymentEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(CcDeploymentEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   String testDeployment(int var1, StructureElementRef[] var2, boolean[] var3) throws ValidationException, EJBException;

   int[] issueCellCalcRebuildTask(int var1, List<Object[]> var2) throws ValidationException, EJBException;
}
