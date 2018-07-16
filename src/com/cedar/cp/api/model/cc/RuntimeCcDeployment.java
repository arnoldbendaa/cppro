// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import com.cedar.cp.api.model.cc.RuntimeCcDeployment$1;
import com.cedar.cp.api.model.cc.RuntimeCcDeploymentLine;
import com.cedar.cp.api.model.cc.RuntimeCcDeploymentMapping;
import com.cedar.cp.api.model.cc.RuntimeCcSummaryMapping;
import com.cedar.cp.api.model.cc.RuntimeCubeDeployment;
import com.cedar.cp.api.model.cc.RuntimeCubeDeploymentLine;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class RuntimeCcDeployment extends RuntimeCubeDeployment implements Serializable {

   private int mXMLFormId;
   private int[] mExplicitDimensions;
   private int[] mContextualDimensions;
   public static final int CALENDAR_IRRELEVANT = 0;
   public static final int CALENDAR_TARGETTED = 1;
   public static final int CALENDAR_PROTECTED = -1;


   public RuntimeCcDeployment(int modelId, int financeCubeId, int ccDeploymentId, int[] contextDefs, int xmlFormId) {
      super(modelId, financeCubeId, ccDeploymentId, 0);
      this.mXMLFormId = xmlFormId;
      int count = 0;
      int[] i = contextDefs;
      int j = contextDefs.length;

      int i$;
      int context;
      for(i$ = 0; i$ < j; ++i$) {
         context = i[i$];
         if(context == 1) {
            ++count;
         }
      }

      this.mExplicitDimensions = new int[count];
      int var11 = 0;

      for(j = 0; var11 < contextDefs.length; ++var11) {
         if(contextDefs[var11] == 1) {
            this.mExplicitDimensions[j++] = var11;
         }
      }

      count = 0;
      i = contextDefs;
      j = contextDefs.length;

      for(i$ = 0; i$ < j; ++i$) {
         context = i[i$];
         if(context == 0) {
            ++count;
         }
      }

      this.mContextualDimensions = new int[count];
      var11 = 0;

      for(j = 0; var11 < contextDefs.length; ++var11) {
         if(contextDefs[var11] == 0) {
            this.mContextualDimensions[j++] = var11;
         }
      }

   }

   public int getXMLFormId() {
      return this.mXMLFormId;
   }

   public int getCcDeploymentId() {
      return this.getOwnerId();
   }

   public int getCalendarElementIdForCell(int[] cellAddress, String dataType, CalendarInfo calendarInfo) {
      Iterator i$ = this.getDeploymentLines().values().iterator();

      while(i$.hasNext()) {
         RuntimeCubeDeploymentLine cubeDeploymentLine = (RuntimeCubeDeploymentLine)i$.next();
         RuntimeCcDeploymentLine deploymentLine = (RuntimeCcDeploymentLine)cubeDeploymentLine;
         if(deploymentLine.targetsDataType(this.mExplicitDimensions, cellAddress, dataType)) {
            CalendarElementNode periodNode = calendarInfo.getById(cellAddress[cellAddress.length - 1]);
            int relevance = deploymentLine.getCalendarRelevance(calendarInfo, periodNode);
            if(relevance != 0) {
               while(relevance == -1) {
                  periodNode = (CalendarElementNode)periodNode.getParent();
                  relevance = deploymentLine.getCalendarRelevance(calendarInfo, periodNode);
               }

               return periodNode.getStructureElementId();
            }
         }
      }

      return -1;
   }

   public boolean doesDeploymentTargetCell(int[] cellAddress, String dataType, CalendarInfo calendarInfo, CalendarElementNode periodNode) {
      Iterator i$ = this.getDeploymentLines().values().iterator();

      while(i$.hasNext()) {
         RuntimeCubeDeploymentLine cubeDeploymentLine = (RuntimeCubeDeploymentLine)i$.next();
         RuntimeCcDeploymentLine deploymentLine = (RuntimeCcDeploymentLine)cubeDeploymentLine;
         if(deploymentLine.targetsDataType(this.mExplicitDimensions, cellAddress, dataType)) {
            int relevance = deploymentLine.getCalendarRelevance(calendarInfo, periodNode);
            if(relevance != 0) {
               return true;
            }
         }
      }

      return false;
   }

   public RuntimeCcDeploymentLine queryDeploymentLine(int[] cellAddress, String dataType, CalendarInfo calendarInfo, CalendarElementNode periodNode) {
      Iterator i$ = this.getDeploymentLines().values().iterator();

      while(i$.hasNext()) {
         RuntimeCubeDeploymentLine cubeDeploymentLine = (RuntimeCubeDeploymentLine)i$.next();
         RuntimeCcDeploymentLine deploymentLine = (RuntimeCcDeploymentLine)cubeDeploymentLine;
         if(deploymentLine.targetsDataType(this.mExplicitDimensions, cellAddress, dataType)) {
            int relevance = deploymentLine.getCalendarRelevance(calendarInfo, periodNode);
            if(relevance != 0) {
               return deploymentLine;
            }
         }
      }

      return null;
   }

   public RuntimeCcDeploymentLine getCcDeploymentLine(int id) {
      return (RuntimeCcDeploymentLine)this.getDeploymentLines().get(Integer.valueOf(id));
   }

   public boolean doesDeploymentLineTargetDataType(int deploymentLineId, int[] cellAddress, String dataType) {
      RuntimeCcDeploymentLine deploymentLine = this.getCcDeploymentLine(deploymentLineId);
      return deploymentLine != null?deploymentLine.targetsDataType(this.mExplicitDimensions, cellAddress, dataType):false;
   }

   public int getCalendarRelevance(int lineId, int[] cellAddress, String dataType, CalendarInfo calendarInfo, CalendarElementNode periodNode) {
      RuntimeCcDeploymentLine deploymentLine = this.getCcDeploymentLine(lineId);
      return deploymentLine != null && deploymentLine.targetsDataType(this.mExplicitDimensions, cellAddress, dataType)?deploymentLine.getCalendarRelevance(calendarInfo, periodNode):0;
   }

   public List<RuntimeCcSummaryMapping> expandCellAddressMappings(int[] cellAddressTemplate, int[] cellPositionsTemplate, int lineId) {
      ArrayList results = new ArrayList();
      if(lineId == -1) {
         Iterator deploymentLine = this.getDeploymentLines().values().iterator();

         while(deploymentLine.hasNext()) {
            RuntimeCubeDeploymentLine cubeDeploymentLine = (RuntimeCubeDeploymentLine)deploymentLine.next();
            RuntimeCcDeploymentLine deploymentLine1 = (RuntimeCcDeploymentLine)cubeDeploymentLine;
            this.expandCellAddressMappings(cellAddressTemplate, cellPositionsTemplate, deploymentLine1, results);
         }
      } else {
         RuntimeCcDeploymentLine deploymentLine2 = this.getCcDeploymentLine(lineId);
         if(deploymentLine2 == null) {
            throw new IllegalStateException("Failed to locate cell calc deployment line with line id:" + lineId);
         }

         this.expandCellAddressMappings(cellAddressTemplate, cellPositionsTemplate, deploymentLine2, results);
      }

      return results;
   }

   private void expandCellAddressMappings(int[] cellAddressTemplate, int[] cellPositionsTemplate, RuntimeCcDeploymentLine deploymentLine, List<RuntimeCcSummaryMapping> mappings) {
      List deploymentMappings = deploymentLine.getCcDeploymentMappings();
      Iterator i$ = deploymentMappings.iterator();

      while(i$.hasNext()) {
         RuntimeCcDeploymentMapping deploymentMapping = (RuntimeCcDeploymentMapping)i$.next();
         int[] explicitStructureElementIds = deploymentMapping.getExplicitStructureElementIds();
         int[] explicitStructureElementPositions = deploymentMapping.getExplicitStructureElementPositions();
         int[] newAddress = new int[cellAddressTemplate.length];
         System.arraycopy(cellAddressTemplate, 0, newAddress, 0, cellAddressTemplate.length);
         int[] newPositions = new int[cellAddressTemplate.length];
         System.arraycopy(cellPositionsTemplate, 0, newPositions, 0, cellPositionsTemplate.length);

         for(int mapping = 0; mapping < this.mExplicitDimensions.length; ++mapping) {
            int index = this.mExplicitDimensions[mapping];
            newAddress[index] = explicitStructureElementIds[mapping];
            newPositions[index] = explicitStructureElementPositions[mapping];
         }

         RuntimeCcSummaryMapping var14 = new RuntimeCcSummaryMapping(deploymentLine, newAddress, newPositions, deploymentMapping.getDataType(), deploymentMapping.getSummaryFieldName());
         mappings.add(var14);
      }

   }

   public int getDeploymentLineId(RuntimeCcDeploymentLine deploymentLine) {
      Iterator i$ = this.getDeploymentLines().entrySet().iterator();

      Entry entry;
      do {
         if(!i$.hasNext()) {
            return -1;
         }

         entry = (Entry)i$.next();
      } while(entry.getValue() != deploymentLine);

      return ((Integer)entry.getKey()).intValue();
   }

   public List<CalendarElementNode> queryAdjacentCalendarDeploymentsNodes(int[] cellAddress, String dataType, CalendarInfo calendarInfo, CalendarElementNode calendarNode) {
      ArrayList result = new ArrayList();
      Iterator i$ = this.getDeploymentLines().values().iterator();

      while(i$.hasNext()) {
         RuntimeCubeDeploymentLine cubeDeploymentLine = (RuntimeCubeDeploymentLine)i$.next();
         RuntimeCcDeploymentLine deploymentLine = (RuntimeCcDeploymentLine)cubeDeploymentLine;
         if(deploymentLine.targetsDataType(this.mExplicitDimensions, cellAddress, dataType)) {
            Iterator i$1 = deploymentLine.getCalendarElementIds().iterator();

            while(i$1.hasNext()) {
               Integer depLineCalNodeId = (Integer)i$1.next();
               CalendarElementNode depLineCalNode = calendarInfo.getById(depLineCalNodeId);
               if(depLineCalNode != null && depLineCalNode.getElemType() == calendarNode.getElemType()) {
                  result.add(depLineCalNode);
               }
            }
         }
      }

      Collections.sort(result, new RuntimeCcDeployment$1(this));
      return result;
   }
}
