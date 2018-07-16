// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.mapping.MappedDimensionElement;
import com.cedar.cp.api.model.mapping.MappedHierarchy;
import com.cedar.cp.api.model.mapping.MappedModel;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.util.List;
import java.util.SortedSet;

public interface MappedDimension extends XMLWritable {

   Object getKey();

   MappingKey getFinanceDimensionKey();

   DimensionRef getDimension();

   String getDimensionVisId();

   String getDimensionDescription();

   int getDimensionType();

   MappedModel getModelMapping();

   String getPathVisId();

   List<MappedHierarchy> getMappedHierarchies();

   MappedHierarchy findMappedHierarchy(MappingKey var1);

   SortedSet<MappedDimensionElement> getMappedDimensionElements();

   String getNullDimensionElementVisId();

   String getNullDimensionElementDescription();

   Integer getNullDimensionElementCreditDebit();

   boolean isDisabledLeafNodesExcluded();
}
