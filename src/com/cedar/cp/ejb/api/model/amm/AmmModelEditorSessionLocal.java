// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.amm;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.amm.AmmMap;
import com.cedar.cp.dto.model.amm.AmmModelEditorSessionCSO;
import com.cedar.cp.dto.model.amm.AmmModelEditorSessionSSO;
import com.cedar.cp.dto.model.amm.AmmModelPK;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface AmmModelEditorSessionLocal extends EJBLocalObject {

   AmmModelEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   AmmModelEditorSessionSSO getNewItemData(int var1) throws EJBException;

   AmmModelPK insert(AmmModelEditorSessionCSO var1) throws ValidationException, EJBException;

   AmmModelPK copy(AmmModelEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(AmmModelEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   int issueAggregatedModelTask(int var1, List var2) throws ValidationException, EJBException;

   AmmMap getAmmMap() throws ValidationException, EJBException;
}
