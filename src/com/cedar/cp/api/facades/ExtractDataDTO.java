// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.facades;

import com.cedar.cp.api.base.EntityList;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface ExtractDataDTO extends Serializable {

   int getModelId();

   void setModelId(int var1);

   int getFinanceCubeId();
   
   String getCompany();

   void setFinanceCubeId(int var1);

   int getNumDims();

   void setNumDims(int var1);

   int[] getHierarchyIds();

   void setHierarchyIds(int[] var1);

   String[] getHierarchyVisIds();

   void setHierarchyVisIds(String[] var1);

   List<String[]> getCellKeys();

   void setCellKeys(List<String[]> var1);

   EntityList getCellData();

   void setCellData(EntityList var1);

   void addElementDescriptionLookup(String var1, String var2);

   void setElementDescriptionLookup(String var1, String var2, String var3);

   String getElementDescriptionLookup(String var1, String var2);

   boolean isElementDescriptionLookupRequired();

   Map<String, String> getElementMapForHierarchy(String var1);

   Map<String, Map<String, String>> getHierarchyElementMaps();
}
