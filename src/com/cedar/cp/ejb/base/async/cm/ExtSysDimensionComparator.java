// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.cm;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.dto.task.TaskMessageLogger;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementActionType;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementEvent;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementEventType;
import com.cedar.cp.ejb.impl.cm.xml.DimensionActionType;
import com.cedar.cp.ejb.impl.cm.xml.DimensionEvent;
import com.cedar.cp.ejb.impl.cm.xml.DimensionEventType;
import com.cedar.cp.ejb.impl.cm.xml.HierarchyActionType;
import com.cedar.cp.ejb.impl.cm.xml.HierarchyEvent;
import com.cedar.cp.ejb.impl.cm.xml.HierarchyEventType;
import com.cedar.cp.ejb.impl.cm.xml.NullableBoolean;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.math.BigInteger;

public class ExtSysDimensionComparator {

   public Log mLog;
   public int mTaskId;
   private TaskMessageLogger mTaskLog;


   public ExtSysDimensionComparator(int taskId, Log log, TaskMessageLogger taskLog) {
      this.mTaskId = taskId;
      this.mLog = log;
      this.mTaskLog = taskLog;
   }

   public int getTaskId() {
      return this.mTaskId;
   }

   public ChangeManagementEvent run(int dimensionId, int extSysType, int companyId) throws Exception {
      Timer t = new Timer();
      ExternalSystemDAO xsdao = new ExternalSystemDAO();
      EntityList diffs = xsdao.importDimension(this.getTaskId(), extSysType, dimensionId, companyId);
      boolean changesFound = diffs.getNumRows() != 0;
      int hierarchyAmends = 0;
      int deInserts = 0;
      int deAmends = 0;
      int deDeletes = 0;
      int heInserts = 0;
      int heMoves = 0;
      int heAmends = 0;
      int heDeletes = 0;
      int hefInserts = 0;
      int hefDeletes = 0;
      int hefMoves = 0;
      ChangeManagementEvent dimEvent = null;
      DimensionEvent hierEvent = null;

      for(int i = 0; i < diffs.getNumRows(); ++i) {
         String type = (String)diffs.getValueAt(i, "TYPE");
         String action = (String)diffs.getValueAt(i, "ACTION");
         String visId = (String)diffs.getValueAt(i, "VIS_ID");
         String descr = (String)diffs.getValueAt(i, "DESCR");
         String notPlannableStr = (String)diffs.getValueAt(i, "NOT_PLANNABLE");
         String disabledStr = (String)diffs.getValueAt(i, "DISABLED");
         String isCreditStr = (String)diffs.getValueAt(i, "IS_CREDIT");
         String childIndexStr = (String)diffs.getValueAt(i, "CHILD_INDEX");
         BigInteger childIndex = childIndexStr == null?null:new BigInteger(childIndexStr);
         String owningVisId = (String)diffs.getValueAt(i, "OWNING_VIS_ID");
         String key1 = (String)diffs.getValueAt(i, "KEY1");
         String key2 = (String)diffs.getValueAt(i, "KEY2");
         String key3 = (String)diffs.getValueAt(i, "KEY3");
         String key4 = (String)diffs.getValueAt(i, "KEY4");
         String key5 = (String)diffs.getValueAt(i, "KEY5");
         String key6 = (String)diffs.getValueAt(i, "KEY6");
         String diagSequence = key1 + "," + key2 + "," + key3 + "," + key4 + "," + key5 + "," + key6;
         this.mLog.debug("importDimension", type + " " + action + " " + visId + (descr != null?" descr=" + descr:"") + (notPlannableStr != null?" notPlannable=" + notPlannableStr:"") + (disabledStr != null?" disabled=" + disabledStr:"") + (isCreditStr != null?" isCredit=" + isCreditStr:"") + (owningVisId != null?" owningVisId=" + owningVisId:"") + (childIndexStr != null?" childIndex=" + childIndexStr:"") + " (seq=" + diagSequence + ")");
         if(type.equals("dimension")) {
            dimEvent = new ChangeManagementEvent();
            dimEvent.setAction(ChangeManagementActionType.fromValue(action));
            dimEvent.setType(ChangeManagementEventType.fromValue(type));
            dimEvent.setVisId(visId);
         } else if(type.equals("dimension-element")) {
            DimensionEvent hefEvent = new DimensionEvent();
            hefEvent.setAction(DimensionActionType.fromValue(action));
            hefEvent.setType(DimensionEventType.fromValue(type));
            hefEvent.setVisId(visId);
            if(action.equals("insert")) {
               ++deInserts;
               hefEvent.setDescription(descr);
               hefEvent.setNotPlannable(this.decodeNullableYNValue(notPlannableStr));
               hefEvent.setDisabled(this.decodeNullableYNValue(disabledStr));
               hefEvent.setCredit(this.decodeNullableYNValue(isCreditStr));
            } else if(action.equals("amend")) {
               ++deAmends;
               if(descr != null) {
                  hefEvent.setDescription(descr);
               }

               hefEvent.setNotPlannable(this.decodeNullableYNValue(notPlannableStr));
               hefEvent.setDisabled(this.decodeNullableYNValue(disabledStr));
               hefEvent.setCredit(this.decodeNullableYNValue(isCreditStr));
            } else if(action.equals("delete")) {
               ++deDeletes;
            }

            dimEvent.getEvent().add(hefEvent);
         } else if(type.equals("hierarchy")) {
            ++hierarchyAmends;
            hierEvent = new DimensionEvent();
            hierEvent.setAction(DimensionActionType.fromValue(action));
            hierEvent.setType(DimensionEventType.fromValue(type));
            hierEvent.setVisId(visId);
            dimEvent.getEvent().add(hierEvent);
         } else {
            HierarchyEvent var39;
            if(type.equals("hierarchy-element")) {
               var39 = new HierarchyEvent();
               var39.setAction(HierarchyActionType.fromValue(action));
               var39.setType(HierarchyEventType.fromValue(type));
               var39.setVisId(visId);
               if(action.equals("insert")) {
                  ++heInserts;
                  var39.setDescription(descr);
                  var39.setCredit(this.decodeNullableYNValue(isCreditStr));
                  var39.setParent(owningVisId);
                  var39.setIndex(childIndex);
               } else if(action.equals("amend")) {
                  ++heAmends;
                  if(descr != null) {
                     var39.setDescription(descr);
                  }

                  var39.setCredit(this.decodeNullableYNValue(isCreditStr));
               } else if(action.equals("move")) {
                  ++heMoves;
                  var39.setParent(owningVisId);
                  var39.setIndex(childIndex);
               } else if(action.equals("delete")) {
                  ++heDeletes;
               }

               hierEvent.getEvent().add(var39);
            } else {
               if(!type.equals("hierarchy-element-feed")) {
                  throw new IllegalStateException("unexpected type:" + type);
               }

               var39 = new HierarchyEvent();
               var39.setAction(HierarchyActionType.fromValue(action));
               var39.setType(HierarchyEventType.fromValue(type));
               var39.setVisId(visId);
               if(action.equals("insert")) {
                  ++hefInserts;
                  var39.setParent(owningVisId);
                  var39.setIndex(childIndex);
               } else if(action.equals("move")) {
                  ++hefMoves;
                  var39.setParent(owningVisId);
                  var39.setIndex(childIndex);
               } else if(action.equals("delete")) {
                  ++hefDeletes;
               }

               hierEvent.getEvent().add(var39);
            }
         }
      }

      if(!changesFound) {
         this.mTaskLog.log("[" + t.getElapsed() + "] no changes");
         return null;
      } else {
         this.mTaskLog.log("[" + t.getElapsed() + "] changes found:");
         if(deInserts + deAmends + deDeletes > 0) {
            this.mTaskLog.log("  - dimensionElements:" + (deInserts > 0?" inserts=" + deInserts:"") + (deAmends > 0?" updates=" + deAmends:"") + (deDeletes > 0?" deletes=" + deDeletes:""));
         }

         if(heInserts + heAmends + heMoves + heDeletes > 0) {
            this.mTaskLog.log("  - hierarchyElements:" + (heInserts > 0?" inserts=" + heInserts:"") + (heAmends > 0?" updates=" + heAmends:"") + (heMoves > 0?" moves=" + heMoves:"") + (heDeletes > 0?" deletes=" + heDeletes:""));
         }

         if(hefInserts + hefMoves + hefDeletes > 0) {
            this.mTaskLog.log("  - hierarchyFeeds:" + (hefInserts > 0?" inserts=" + hefInserts:"") + (hefMoves > 0?" moves=" + hefMoves:"") + (hefDeletes > 0?" deletes=" + hefDeletes:""));
         }

         return dimEvent;
      }
   }

   private NullableBoolean decodeNullableYNValue(String value) {
      return value != null?(value.equalsIgnoreCase("Y")?NullableBoolean.fromValue("true"):NullableBoolean.fromValue("false")):null;
   }
}
