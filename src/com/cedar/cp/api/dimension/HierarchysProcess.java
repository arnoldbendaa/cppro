// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyEditorSession;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.dimension.calendar.CalendarEditorSession;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.util.List;
import java.util.Map;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public interface HierarchysProcess extends BusinessProcess {

   EntityList getAllHierarchys();
   
   EntityList getHierarchiesForLoggedUser();
   
   EntityList getSelectedHierarchys();

   EntityList getImportableHierarchies(int var1);

   EntityList getHierarcyDetailsFromDimId(int var1);

   EntityList getHierarcyDetailsIncRootNodeFromDimId(int var1);

   EntityList getCalendarForModel(int var1);

   EntityList getCalendarForModelVisId(String var1);

   EntityList getCalendarForFinanceCube(int var1);

   HierarchyEditorSession getHierarchyEditorSession(Object var1) throws ValidationException;

   TreeModel getRuntimeStructure(HierarchyRef var1) throws ValidationException;

   EntityList queryPathToRoots(List<StructureElementKey> var1);

   CalendarEditorSession getCalendarEditorSession(Object var1);

   void deleteCalendarObject(Object var1) throws ValidationException;

   CalendarInfo getCalendarInfo(Object var1);

   CalendarInfo getCalendarInfoForModel(Object var1);

   CalendarInfo getCalendarInfoForFinanceCube(Object var1);

   Map<DimensionRef, TreeModel> getTreeModels(ModelRef var1);

   Map<DimensionRef, TreeModel> getFilteredTreeModels(Map<DimensionRef, Map<StructureElementRef, Boolean>> var1);

   EntityList getImmediateChildrenWithFilter(StructureElementRef var1, Map<StructureElementRef, Boolean> var2);

   List<TreeNode> searchTree(Object var1, List<String> var2, TreeModel var3);

   List<TreeNode> queryTreeNodes(Object var1, List<Object> var2, TreeModel var3);
}
