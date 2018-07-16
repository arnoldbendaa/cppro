// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.mapping.MappedDimension;
import com.cedar.cp.api.model.mapping.MappedDimensionElementEditor;
import com.cedar.cp.api.model.mapping.MappedHierarchyEditor;

public interface MappedDimensionEditor extends SubBusinessEditor {

   void setFinanceDimensionKey(MappingKey var1) throws ValidationException;

   void setDimensionVisId(String var1) throws ValidationException;

   void setDimensionDescription(String var1) throws ValidationException;

   void setNullElementVisId(String var1) throws ValidationException;

   void setNullElementDescription(String var1) throws ValidationException;

   void setNullElementCrDrFlag(Integer var1) throws ValidationException;

   void setDimensionType(int var1) throws ValidationException;

   void setDisabledLeafNodesExcluded(boolean var1);

   MappedDimension getDimensionMapping();

   void removeMappedHierarchy(Object var1) throws ValidationException;

   MappedHierarchyEditor getMappedHierarchyEditor(Object var1) throws ValidationException;

   MappedDimensionElementEditor getMappedDimensionElementEditor(Object var1) throws ValidationException;

   void removeMappedDimensionElement(Object var1) throws ValidationException;
}
