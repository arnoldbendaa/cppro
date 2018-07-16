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
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;

public interface HierarchyEditorSessionRemote extends EJBObject {

   HierarchyEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   HierarchyEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   HierarchyCK insert(HierarchyEditorSessionCSO var1) throws ValidationException, RemoteException;

   EntityList getOwnershipData(int var1, Object var2) throws RemoteException;

   HierarchyCK copy(HierarchyEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(HierarchyEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   List processEvents(List var1) throws ValidationException, CPException, RemoteException;

   EntityList getAvailableDimensionsForInsert(int var1) throws CPException, RemoteException;

   EntityList queryPathToRoots(List<StructureElementKey> var1) throws RemoteException;

   Map<DimensionRef, EntityList> getFilteredTreeRoots(Map<DimensionRef, Map<StructureElementRef, Boolean>> var1) throws RemoteException;

   EntityList getImmediateChildrenWithFilter(StructureElementRef var1, Map<StructureElementRef, Boolean> var2) throws RemoteException;
}
