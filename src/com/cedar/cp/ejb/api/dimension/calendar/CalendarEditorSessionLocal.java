// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.dimension.calendar;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.calendar.CalendarEditorSessionCSO;
import com.cedar.cp.dto.dimension.calendar.CalendarEditorSessionSSO;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface CalendarEditorSessionLocal extends EJBLocalObject {

   CalendarEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   CalendarEditorSessionSSO getNewItemData(int var1) throws EJBException;

   HierarchyCK insert(CalendarEditorSessionCSO var1) throws ValidationException, EJBException;

   EntityList getOwnershipData(int var1, Object var2) throws EJBException;

   HierarchyCK copy(CalendarEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(CalendarEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
