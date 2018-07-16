// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyEditorSessionCSO;
import com.cedar.cp.dto.dimension.HierarchyEditorSessionSSO;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface HierarchyEditorSessionLocal extends EJBLocalObject {

   HierarchyEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   HierarchyEditorSessionSSO getNewItemData(int var1) throws EJBException;

   HierarchyCK insert(HierarchyEditorSessionCSO var1) throws ValidationException, EJBException;

   EntityList getOwnershipData(int var1, Object var2) throws EJBException;

   HierarchyCK copy(HierarchyEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(HierarchyEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   List processEvents(List var1) throws ValidationException, CPException, EJBException;

   EntityList getAvailableDimensionsForInsert(int var1) throws CPException, EJBException;

   EntityList queryPathToRoots(List<StructureElementKey> var1) throws EJBException;

   Map<DimensionRef, EntityList> getFilteredTreeRoots(Map<DimensionRef, Map<StructureElementRef, Boolean>> var1) throws EJBException;

   EntityList getImmediateChildrenWithFilter(StructureElementRef var1, Map<StructureElementRef, Boolean> var2) throws EJBException;
}
