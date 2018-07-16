// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.amm;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionElementRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.amm.AmmFinanceCubeMapping;
import com.cedar.cp.api.model.amm.AmmModel;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.util.List;
import java.util.Map;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public interface AmmModelEditor extends BusinessEditor {

   void setModelId(int var1) throws ValidationException;

   void setSrcModelId(int var1) throws ValidationException;

   void setInvalidatedByTaskId(Integer var1) throws ValidationException;

   void setTargetModelRef(ModelRef var1) throws ValidationException;

   void setSourceModelRef(ModelRef var1) throws ValidationException;

   AmmModel getAmmModel();

   void setModelLocked(boolean var1);

   void setDimsLocked(boolean var1);

   void addMappedDimMapping(DimensionRef var1, DimensionRef var2, HierarchyRef var3) throws ValidationException;

   void addUnmappedDimMapping(DimensionRef var1) throws ValidationException;

   void addUnmappedSourceDimMapping(DimensionRef var1, HierarchyRef var2) throws ValidationException;

   void addCalDimMapping(DimensionRef var1, DimensionRef var2, HierarchyRef var3) throws ValidationException;

   void setFinanceCubeMappings(List<AmmFinanceCubeMapping> var1);

   void setSourceCalInfo(CalendarInfo var1);

   List<TreeNode> searchTree(int var1, String var2, TreeModel var3);

   Map<DimensionElementRef, List<StructureElementRef>> autoMap(List var1, int var2);

   Map<DimensionElementRef, List<StructureElementRef>> copyFrom(EntityList var1, List var2, int var3);

   Map<CalendarElementNode, List<StructureElementRef>> autoMapCal(List var1, CalendarInfo var2);

   Map<CalendarElementNode, List<StructureElementRef>> copyFromCal(EntityList var1, List var2, CalendarInfo var3);
}
