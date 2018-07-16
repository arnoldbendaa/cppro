// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.flatform;

import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.base.cube.flatform.DimensionLookup;
import com.cedar.cp.ejb.base.cube.flatform.HierarchyLookup;
import com.cedar.cp.ejb.base.cube.flatform.Property;
import com.cedar.cp.ejb.base.cube.flatform.WorksheetCellUpdate;
import com.cedar.cp.util.Pair;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WorksheetUpdate {

   private static final String sFINANCE_CUBE_ID_PROP_NAME = WorkbookProperties.FINANCE_CUBE_ID.toString();
   private static final String sMODEL_ID_PROP_NAME = WorkbookProperties.MODEL_ID.toString();
   private String mName;
   private Map<String, String> mProperties = new HashMap();
   private List<WorksheetCellUpdate> mWorksheetUpdateCells = new ArrayList();


   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public void addProperty(Property property) {
      this.mProperties.put(property.getName(), property.getValue());
   }

   public void addWorksheetCellUpdate(WorksheetCellUpdate cell) {
      this.mWorksheetUpdateCells.add(cell);
   }

   public Map<String, String> getProperties() {
      return this.mProperties;
   }

   public List<WorksheetCellUpdate> getWorksheetUpdateCells() {
      return this.mWorksheetUpdateCells;
   }

   public void replaceParameterMacros(Map<String, String> parameters) {
      Iterator i$ = this.mWorksheetUpdateCells.iterator();

      while(i$.hasNext()) {
         WorksheetCellUpdate cell = (WorksheetCellUpdate)i$.next();
         cell.replaceParameterMacros(parameters);
      }

   }

   public void insertContextVariables(String dim0VisId) {
      Pair[] dimHierVisIds = this.queryDimensionAndHierarchyVisIds();
      if(dimHierVisIds != null) {
         Iterator i$ = this.mWorksheetUpdateCells.iterator();

         while(i$.hasNext()) {
            WorksheetCellUpdate cellUpdate = (WorksheetCellUpdate)i$.next();
            String[] cellVisIds = cellUpdate.getAddressElements();
            if(cellVisIds.length == dimHierVisIds.length && dim0VisId != null) {
               String[] newCellAddress = new String[cellVisIds.length + 1];
               newCellAddress[0] = dim0VisId;
               System.arraycopy(cellVisIds, 0, newCellAddress, 1, cellVisIds.length);
               cellUpdate.setAddressElements(newCellAddress);
            }
         }

      }
   }

   public void insertContextVariables(Map parameters) {
      Pair[] dimHierVisIds = this.queryDimensionAndHierarchyVisIds();
      if(dimHierVisIds != null) {
         Iterator i$ = this.mWorksheetUpdateCells.iterator();

         while(i$.hasNext()) {
            WorksheetCellUpdate cellUpdate = (WorksheetCellUpdate)i$.next();
            String[] cellVisIds = cellUpdate.getAddressElements();
            ArrayList dimList = new ArrayList();
            int newContextualEle = 0;
            if(cellUpdate.isPutCell()) {
               int dim0VisId = 0;

               String newCellAddress;
               int z;
               while(dim0VisId < dimHierVisIds.length) {
                  newCellAddress = null;
                  z = 0;

                  while(true) {
                     if(z < cellVisIds.length) {
                        if(cellVisIds[z].indexOf("dim" + dim0VisId) <= -1) {
                           ++z;
                           continue;
                        }

                        newCellAddress = cellVisIds[z];
                     }

                     if(newCellAddress == null) {
                        newCellAddress = "dim" + dim0VisId + "=";
                        newCellAddress = newCellAddress + String.valueOf(parameters.get(WorkbookProperties.DIMENSION_$_VISID.toString().replace("$", String.valueOf(dim0VisId))));
                        ++newContextualEle;
                     }

                     newCellAddress = this.removePrefix(newCellAddress);
                     dimList.add(newCellAddress);
                     ++dim0VisId;
                     break;
                  }
               }

               for(dim0VisId = 0; dim0VisId < cellVisIds.length; ++dim0VisId) {
                  newCellAddress = cellVisIds[dim0VisId];
                  if(newCellAddress.indexOf("dim") == -1) {
                     dimList.add(this.removePrefix(newCellAddress));
                  }
               }

               String[] var11 = new String[dimList.size()];
               int var13 = dimList.size();

               for(z = 0; z < var13; ++z) {
                  var11[z] = (String)dimList.get(z);
               }

               cellUpdate.setAddressElements(var11);
            } else {
               String var12 = String.valueOf(parameters.get(WorkbookProperties.DIMENSION_0_VISID.toString()));
               if(cellVisIds.length == dimHierVisIds.length && var12 != null) {
                  String[] var14 = new String[cellVisIds.length + 1];
                  var14[0] = var12;
                  System.arraycopy(cellVisIds, 0, var14, 1, cellVisIds.length);
                  cellUpdate.setAddressElements(var14);
               }
            }
         }

      }
   }

   private String removePrefix(String visIdWithPrefix) {
      if(visIdWithPrefix != null && visIdWithPrefix.trim().length() > 0) {
         String[] valuePair = visIdWithPrefix.split("=");
         return valuePair[1];
      } else {
         return null;
      }
   }

   public void queryLookupInfo(Map<String, DimensionLookup> dimLookupMap) {
      Pair[] visIds = this.queryDimensionAndHierarchyVisIds();
      if(visIds != null) {
         DimensionLookup[] dimensionLookups = new DimensionLookup[visIds.length];
         HierarchyLookup[] hierarchyLookups = new HierarchyLookup[visIds.length];

         for(int i$ = 0; i$ < visIds.length; ++i$) {
            dimensionLookups[i$] = this.getDimensionLookup(dimLookupMap, (String)visIds[i$].getChild1());
            hierarchyLookups[i$] = dimensionLookups[i$].getHierarchyLookup((String)visIds[i$].getChild2(), true);
         }

         Iterator var9 = this.mWorksheetUpdateCells.iterator();

         while(var9.hasNext()) {
            WorksheetCellUpdate cellUpdate = (WorksheetCellUpdate)var9.next();
            String[] cellVisIds = cellUpdate.getAddressElements();

            for(int dimIdx = 0; dimIdx < visIds.length; ++dimIdx) {
               hierarchyLookups[dimIdx].registerStructureElementLookup(cellVisIds[dimIdx]);
            }
         }

      }
   }

   private DimensionLookup getDimensionLookup(Map<String, DimensionLookup> dimLookupMap, String visId) {
      DimensionLookup dimLookup = (DimensionLookup)dimLookupMap.get(visId);
      if(dimLookup == null) {
         dimLookup = new DimensionLookup();
         dimLookup.setDimensionVisId(visId);
         dimLookupMap.put(visId, dimLookup);
      }

      return dimLookup;
   }

   public Pair<String, String>[] queryDimensionAndHierarchyVisIds() {
      ArrayList dimVisIds = new ArrayList();

      for(int visIdsArray = 0; visIdsArray < 10; ++visIdsArray) {
         String i = (String)this.mProperties.get(WorkbookProperties.DIMENSION_$_VISID.toString().replace("$", String.valueOf(visIdsArray)));
         String hierVisId = (String)this.mProperties.get(WorkbookProperties.DIMENSION_$_HIERARCHY_VISID.toString().replace("$", String.valueOf(visIdsArray)));
         if(i == null || hierVisId == null) {
            break;
         }

         dimVisIds.add(new Pair(i, hierVisId));
      }

      if(dimVisIds.isEmpty()) {
         return null;
      } else {
         Pair[] var5 = (Pair[])(new Pair[dimVisIds.size()]);

         for(int var6 = 0; var6 < var5.length; ++var6) {
            var5[var6] = (Pair)dimVisIds.get(var6);
         }

         return var5;
      }
   }

   public DimensionLookup[] getDimensionLookup(Map<String, DimensionLookup> dimensionMapLookup) {
      Pair[] dimHierLookups = this.queryDimensionAndHierarchyVisIds();
      if(dimHierLookups == null) {
         return null;
      } else {
         DimensionLookup[] dimLookups = new DimensionLookup[dimHierLookups.length];

         for(int i = 0; i < dimLookups.length; ++i) {
            dimLookups[i] = (DimensionLookup)dimensionMapLookup.get(dimHierLookups[i].getChild1());
         }

         return dimLookups;
      }
   }

    public int getFinanceCubeId() {
        String financeCubeIdProp = (String) this.mProperties.get(WorkbookProperties.FINANCE_CUBE_ID.toString());
        if (financeCubeIdProp == null) {
            return -1;
        } else {
            try {
                FinanceCubeCK financeCubeCK = (FinanceCubeCK) FinanceCubeCK.getKeyFromTokens(financeCubeIdProp);
                return financeCubeCK.getFinanceCubePK().getFinanceCubeId();
            } catch (Exception e) {
                int financeCubeId = new Integer(financeCubeIdProp).intValue();
                return financeCubeId;
            }
        }
    }

    public int getModelId() {
        String modelIdProp = (String) this.mProperties.get(WorkbookProperties.MODEL_ID.toString());
        if (modelIdProp == null) {
            return -1;
        } else {
            try {
                ModelPK modelPK = ModelPK.getKeyFromTokens(modelIdProp);
                return modelPK.getModelId();
            } catch (Exception e) {
                int modelId = new Integer(modelIdProp).intValue();
                return modelId;
            }
        }
    }

   public void queryDataTypes(Set dataTypes) {
      Iterator i$ = this.getWorksheetUpdateCells().iterator();

      while(i$.hasNext()) {
         WorksheetCellUpdate worksheetCellUpdate = (WorksheetCellUpdate)i$.next();
         dataTypes.add(worksheetCellUpdate.getDataType());
      }

   }
}
