// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.services;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.FinanceCube;
import com.cedar.cp.api.model.Model;
import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpApplicationState;
import com.cedar.cp.util.DefaultValueMapping;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class FinanceCubeService {

   MetaDataImpExpApplicationState applicationState = MetaDataImpExpApplicationState.getInstance();


   public FinanceCube getFinanceCubeByVisId(String visId) throws ValidationException {
      Object key = this.applicationState.getFinanceCubePK(visId);
      return this.applicationState.getFinanceCube(key);
   }

   public DefaultValueMapping buildFinanceCubeMapping() {
      EntityList fcubes = this.applicationState.getConnection().getListHelper().getAllSimpleFinanceCubes();
      int size = fcubes.getNumRows();
      String[] lits = new String[size];
      Object[] values = new Object[size];

      for(int i = 0; i < size; ++i) {
         lits[i] = fcubes.getValueAt(i, "FinanceCube").toString();
         values[i] = fcubes.getValueAt(i, "FinanceCubeId");
      }

      return new DefaultValueMapping(lits, values);
   }

   public List<String> filterFinaceCubeVisIdListByDim(int numberOfDim) throws ValidationException {
      if(numberOfDim > 0) {
         EntityList fcubes = this.applicationState.getConnection().getListHelper().getAllSimpleFinanceCubes();
         ArrayList filteredCubeVisIdList = new ArrayList();
         if(fcubes != null) {
            int size = fcubes.getNumRows();

            for(int i = 0; i < size; ++i) {
               String visId = fcubes.getValueAt(i, "FinanceCube").toString();
               Model model = this.applicationState.getFinanceCubeModel(visId);
               List selectedDim = model.getSelectedDimensionRefs();
               if(selectedDim != null && selectedDim.size() >= numberOfDim) {
                  filteredCubeVisIdList.add(visId);
               }
            }

            return filteredCubeVisIdList;
         }
      }

      return null;
   }

   public List<EntityRef> getDimensionsByFinanceCubeVisId(String cubeVisId) throws ValidationException {
      if(cubeVisId != null && cubeVisId.trim().length() > 0) {
         Model model = this.applicationState.getFinanceCubeModel(cubeVisId);
         if(model != null) {
            return model.getSelectedDimensionRefs();
         }
      }

      return null;
   }

   public EntityList getHierarchiesByDimensionId(int dimensionId) throws ValidationException {
      return dimensionId > 0?this.applicationState.getConnection().getListHelper().getHierarcyDetailsFromDimId(dimensionId):null;
   }

   public Hashtable<String, List<String>> getDimensionHashtableByCubeVisId(String cubeVisId) throws ValidationException {
      Hashtable hash = new Hashtable();
      List dimensionList = this.getDimensionsByFinanceCubeVisId(cubeVisId);
      String visId;
      ArrayList var16;
      if(dimensionList != null) {
         for(Iterator i$ = dimensionList.iterator(); i$.hasNext(); hash.put(visId, var16)) {
            EntityRef dimensionRef = (EntityRef)i$.next();
            visId = dimensionRef.getNarrative();
            Object pk = dimensionRef.getPrimaryKey();
            int dimId = 0;
            String propName = "DimensionId=";
            int dimIdIndex = pk.toString().indexOf(propName);
            if(pk != null && dimIdIndex >= 0) {
               String hierList = pk.toString().replace(propName, "");
               dimId = Integer.valueOf(hierList).intValue();
            }

            var16 = new ArrayList();
            EntityList entityList = this.getHierarchiesByDimensionId(dimId);
            if(entityList != null) {
               Object[] s = entityList.getValues("Hierarchy");

               for(int i = 0; i < s.length; ++i) {
                  HierarchyRef hier = (HierarchyRef)s[i];
                  var16.add(hier.getNarrative());
               }
            }
         }
      }

      return hash;
   }
}
