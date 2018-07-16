// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import com.cedar.cp.api.model.cc.RuntimeCcDeployment;
import com.cedar.cp.api.model.cc.RuntimeCcDeploymentMapping;
import com.cedar.cp.api.model.cc.RuntimeCcDeploymentTarget;
import com.cedar.cp.api.model.cc.RuntimeCubeDeploymentEntry;
import com.cedar.cp.api.model.cc.RuntimeCubeDeploymentLine;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RuntimeCcDeploymentLine extends RuntimeCubeDeploymentLine implements Serializable {

   private List<Integer> mCalendarElementIds = new ArrayList();
   private List<RuntimeCcDeploymentMapping> mCcDeploymentMappings = new ArrayList();
   private transient RuntimeCcDeploymentTarget mCcDeploymentTarget;


   public RuntimeCcDeploymentLine(RuntimeCcDeployment deployment, int ccDeploymentLineId, List<RuntimeCubeDeploymentEntry> lines, String[] dataTypes) {
      super(deployment, ccDeploymentLineId, lines, dataTypes);
   }

   public List<Integer> getCalendarElementIds() {
      return this.mCalendarElementIds;
   }

   public void setCalendarElementIds(List<Integer> calendarElementIds) {
      this.mCalendarElementIds = calendarElementIds;
   }

   public List<RuntimeCcDeploymentMapping> getCcDeploymentMappings() {
      return this.mCcDeploymentMappings;
   }

   public void setCcDeploymentMappings(List<RuntimeCcDeploymentMapping> ccDeploymentMappings) {
      this.mCcDeploymentMappings = ccDeploymentMappings;
   }

   public boolean targetsDataType(int[] explicitDimensionIndexes, int[] cellAddress, String dataType) {
      Iterator i$ = this.mCcDeploymentMappings.iterator();

      while(i$.hasNext()) {
         RuntimeCcDeploymentMapping mapping = (RuntimeCcDeploymentMapping)i$.next();
         if(mapping.getDataType().equals(dataType)) {
            int[] mappingIds = mapping.getExplicitStructureElementIds();
            int matchCount = 0;
            int i = 0;

            while(true) {
               if(i < mappingIds.length) {
                  int dimIndex = explicitDimensionIndexes[i];
                  if(mappingIds[i] == cellAddress[dimIndex]) {
                     ++matchCount;
                     ++i;
                     continue;
                  }
               }

               if(matchCount == mappingIds.length) {
                  return true;
               }
               break;
            }
         }
      }

      return false;
   }

   public int getCalendarRelevance(CalendarInfo calendarInfo, CalendarElementNode periodNode) {
      Iterator i$ = this.mCalendarElementIds.iterator();

      CalendarElementNode targetNode;
      do {
         if(!i$.hasNext()) {
            return 0;
         }

         Integer calendarElementId = (Integer)i$.next();
         if(periodNode.getStructureElementId() == calendarElementId.intValue()) {
            return 1;
         }

         targetNode = calendarInfo.getById(calendarElementId);
      } while(!periodNode.isNodeAncestor(targetNode));

      return -1;
   }

   public RuntimeCcDeploymentTarget getRuntimeCcDeploymentTarget() {
      if(this.mCcDeploymentTarget == null) {
         this.mCcDeploymentTarget = new RuntimeCcDeploymentTarget(this.getRuntimeCubeDeployment().getOwnerId(), this.getOwnerLineId());
      }

      return this.mCcDeploymentTarget;
   }
}
