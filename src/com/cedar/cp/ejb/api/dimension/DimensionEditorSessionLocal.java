// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.dimension;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.DimensionEditorSessionCSO;
import com.cedar.cp.dto.dimension.DimensionEditorSessionSSO;
import com.cedar.cp.dto.dimension.DimensionPK;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface DimensionEditorSessionLocal extends EJBLocalObject {

   DimensionEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   DimensionEditorSessionSSO getNewItemData(int var1) throws EJBException;

   DimensionPK insert(DimensionEditorSessionCSO var1) throws ValidationException, EJBException;

   DimensionPK copy(DimensionEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(DimensionEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   List processEvents(List var1) throws ValidationException, EJBException;
}
