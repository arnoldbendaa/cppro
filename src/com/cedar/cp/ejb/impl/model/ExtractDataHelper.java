// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.facades.ExtractDataDTO;
import com.cedar.cp.dto.model.HierarchiesForModelELO;
import com.cedar.cp.ejb.base.cube.flatform.DimensionLookup;
import com.cedar.cp.ejb.base.cube.flatform.HierarchyLookup;
import com.cedar.cp.ejb.base.cube.flatform.StructureElementLookup;
import com.cedar.cp.ejb.impl.dataentry.DataEntryDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ExtractDataHelper {
	static DataEntryDAO dataEntryDAO = new DataEntryDAO();
   public static ExtractDataDTO getExtractData(ExtractDataDTO extractDataDTO) {
      
      if(extractDataDTO.getCellKeys() != null && !extractDataDTO.getCellKeys().isEmpty()) {
    	  String company = extractDataDTO.getCompany();
    	  if (company != null) {
    		  extractDataDTO.setCellData(dataEntryDAO.getCellValues(extractDataDTO.getFinanceCubeId(), extractDataDTO.getNumDims(), extractDataDTO.getHierarchyVisIds(), extractDataDTO.getCellKeys(), company));
    	  } else {
    		  extractDataDTO.setCellData(dataEntryDAO.getCellValues(extractDataDTO.getFinanceCubeId(), extractDataDTO.getNumDims(), extractDataDTO.getHierarchyVisIds(), extractDataDTO.getCellKeys()));
    	  }
      }

      if(extractDataDTO.isElementDescriptionLookupRequired()) {
         ModelDAO modelDAO = new ModelDAO();
         StructureElementDAO structureElementDAO = new StructureElementDAO();
         HierarchiesForModelELO hierarchiesForModelELO = modelDAO.getHierarchiesForModel(extractDataDTO.getModelId());
         HashMap dimLookups = new HashMap();

         Iterator i$2;
         for(int i$ = 0; i$ < hierarchiesForModelELO.getNumRows(); ++i$) {
            DimensionRef dimensionLookup = (DimensionRef)hierarchiesForModelELO.getValueAt(i$, "Dimension");
            HierarchyRef i$1 = (HierarchyRef)hierarchiesForModelELO.getValueAt(i$, "Hierarchy");
            DimensionLookup hierLookupEntry = (DimensionLookup)dimLookups.get(dimensionLookup.getNarrative());
            if(hierLookupEntry == null) {
               hierLookupEntry = new DimensionLookup();
               hierLookupEntry.setDimensionVisId(dimensionLookup.getNarrative());
               dimLookups.put(dimensionLookup.getNarrative(), hierLookupEntry);
            }

            HierarchyLookup hierarchyVisId = hierLookupEntry.getHierarchyLookup(i$1.getNarrative(), true);
            Map hierarchyLookup = extractDataDTO.getElementMapForHierarchy(i$1.getNarrative());
            if(hierarchyLookup != null && !hierarchyLookup.isEmpty()) {
               i$2 = hierarchyLookup.keySet().iterator();

               while(i$2.hasNext()) {
                  String seLookupEntry = (String)i$2.next();
                  hierarchyVisId.registerStructureElementLookup(seLookupEntry);
               }
            }
         }

         structureElementDAO.queryVisualIds(dimLookups);
         Iterator var14 = dimLookups.values().iterator();

         while(var14.hasNext()) {
            DimensionLookup var15 = (DimensionLookup)var14.next();
            Iterator var16 = var15.getHierarchies().entrySet().iterator();

            while(var16.hasNext()) {
               Entry var17 = (Entry)var16.next();
               String var19 = (String)var17.getKey();
               HierarchyLookup var18 = (HierarchyLookup)var17.getValue();
               i$2 = var18.getStructureElements().entrySet().iterator();

               while(i$2.hasNext()) {
                  Entry var20 = (Entry)i$2.next();
                  extractDataDTO.setElementDescriptionLookup(var19, (String)var20.getKey(), ((StructureElementLookup)var20.getValue()).getStructureElementDescription());
               }
            }
         }
      }

      return extractDataDTO;
   }
}
