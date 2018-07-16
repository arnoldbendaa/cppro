// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.CellCalcCK;
import com.cedar.cp.dto.model.CellCalcEditorSessionCSO;
import com.cedar.cp.dto.model.CellCalcEditorSessionSSO;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface CellCalcEditorSessionLocal extends EJBLocalObject {

   CellCalcEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   CellCalcEditorSessionSSO getNewItemData(int var1) throws EJBException;

   CellCalcCK insert(CellCalcEditorSessionCSO var1) throws ValidationException, EJBException;

   EntityList getOwnershipData(int var1, Object var2) throws EJBException;

   CellCalcCK copy(CellCalcEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(CellCalcEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
