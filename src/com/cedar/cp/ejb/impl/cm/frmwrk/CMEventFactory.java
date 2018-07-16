// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cm.frmwrk;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionElementRef;
import com.cedar.cp.dto.dimension.DimensionElementEvent;
import com.cedar.cp.dto.dimension.calendar.CalendarYearSpecImpl;
import com.cedar.cp.dto.dimension.event.HierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.InsertDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.InsertHierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.InsertHierarchyElementFeedEvent;
import com.cedar.cp.dto.dimension.event.MoveHierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.MoveHierarchyElementFeedEvent;
import com.cedar.cp.dto.dimension.event.RemoveDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.RemoveHierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.RemoveHierarchyElementFeedEvent;
import com.cedar.cp.dto.dimension.event.UpdateDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.UpdateHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.CalendarEvent;
import com.cedar.cp.ejb.impl.cm.event.InsertCalendarYearEvent;
import com.cedar.cp.ejb.impl.cm.event.InsertedDimensionElementEvent;
import com.cedar.cp.ejb.impl.cm.event.InsertedHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.InsertedHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.cm.event.MovedHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.MovedHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.cm.event.PostUpdateDimensionElementEvent;
import com.cedar.cp.ejb.impl.cm.event.PostUpdateHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.PostUpdateHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.cm.event.RemoveCalendarYearEvent;
import com.cedar.cp.ejb.impl.cm.event.RemovedDimensionElementEvent;
import com.cedar.cp.ejb.impl.cm.event.RemovedHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.event.RemovedHierarchyElementFeedEvent;
import com.cedar.cp.ejb.impl.cm.event.UpdateCalendarYearEvent;
import com.cedar.cp.ejb.impl.cm.event.UpdatedDimensionElementEvent;
import com.cedar.cp.ejb.impl.cm.event.UpdatedHierarchyElementEvent;
import com.cedar.cp.ejb.impl.cm.xml.DimensionEvent;
import com.cedar.cp.ejb.impl.cm.xml.HierarchyEvent;
import com.cedar.cp.ejb.impl.cm.xml.NullableBoolean;
import com.cedar.cp.ejb.impl.dimension.CalendarYearSpecDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedDAG;

public class CMEventFactory {

   private static boolean decodeBoolean(NullableBoolean nullableBoolean) {
      return nullableBoolean != null?nullableBoolean.value().equalsIgnoreCase("true"):false;
   }

   private static Boolean decodeNullableBoolean(NullableBoolean nullableBoolean) {
      return nullableBoolean != null?Boolean.valueOf(nullableBoolean.value().equalsIgnoreCase("true")):null;
   }

   public DimensionElementEvent createDimensionElementEvent(DimensionEvent xmlDimEvent, Object pk) throws ValidationException {
      String action = xmlDimEvent.getAction().value();
      Object event;
      if(action.equalsIgnoreCase("insert")) {
         int aug_crdr = decodeBoolean(xmlDimEvent.getCredit())?1:2;
         int crdr = xmlDimEvent.getAugCredit() != null && !xmlDimEvent.getAugCredit().value().equals("NO_OVERRIDE")?(xmlDimEvent.getAugCredit().value().equals("CREDIT")?1:2):0;
         event = new InsertDimensionElementEvent(pk, xmlDimEvent.getVisId(), xmlDimEvent.getDescription(), aug_crdr, crdr, decodeBoolean(xmlDimEvent.getNotPlannable()), decodeBoolean(xmlDimEvent.getDisabled()), decodeBoolean(xmlDimEvent.getNull()));
      } else if(action.equalsIgnoreCase("amend")) {
         Integer aug_crdr1 = xmlDimEvent.getAugCredit() == null?null:Integer.valueOf(xmlDimEvent.getAugCredit().value().equals("NO_OVERRIDE")?0:(xmlDimEvent.getAugCredit().value().equals("CREDIT")?1:2));
         Integer crdr1 = xmlDimEvent.getCredit() == null?null:Integer.valueOf(xmlDimEvent.getCredit().value().equalsIgnoreCase("true")?1:2);
         event = new UpdateDimensionElementEvent(pk, xmlDimEvent.getOrigVisId(), xmlDimEvent.getVisId(), xmlDimEvent.getDescription(), crdr1, aug_crdr1, decodeNullableBoolean(xmlDimEvent.getNotPlannable()), decodeNullableBoolean(xmlDimEvent.getDisabled()), decodeNullableBoolean(xmlDimEvent.getNull()));
      } else {
         if(!action.equalsIgnoreCase("delete")) {
            throw new ValidationException("Unknown action in DimensionEvent[" + action + "]");
         }

         event = new RemoveDimensionElementEvent(pk, xmlDimEvent.getVisId());
      }

      return (DimensionElementEvent)event;
   }

   public PostUpdateDimensionElementEvent createPostUpdateDimensionElementEvent(DimensionElementEvent srcEvent, DimensionElementDAG preElement, DimensionElementDAG postElement) throws ValidationException {
      if(srcEvent instanceof InsertDimensionElementEvent) {
         return new InsertedDimensionElementEvent(postElement, (InsertDimensionElementEvent)srcEvent);
      } else if(srcEvent instanceof RemoveDimensionElementEvent) {
         return new RemovedDimensionElementEvent(preElement, (RemoveDimensionElementEvent)srcEvent);
      } else if(srcEvent instanceof UpdateDimensionElementEvent) {
         return new UpdatedDimensionElementEvent(preElement, (UpdateDimensionElementEvent)srcEvent);
      } else {
         throw new ValidationException("Unkown subclass of DimensionElementEvent:" + srcEvent.getClass());
      }
   }

   public HierarchyElementEvent createHierarchyElementEvent(HierarchyEvent xmlEvent, Object pk, Object parentPK) throws ValidationException {
      String type = xmlEvent.getType().value();
      String action = xmlEvent.getAction().value();
      int index = xmlEvent.getIndex() != null?xmlEvent.getIndex().intValue():0;
      if(!type.equalsIgnoreCase("hierarchy-element")) {
         throw new IllegalArgumentException("Unexpected type in createHierarchyElementEvent:" + type);
      } else if(action.equalsIgnoreCase("insert")) {
         int crdr1 = decodeBoolean(xmlEvent.getCredit())?1:2;
         int aug_crdr1 = xmlEvent.getAugCredit() != null && !xmlEvent.getAugCredit().value().equals("NO_OVERRIDE")?(xmlEvent.getAugCredit().value().equals("CREDIT")?1:2):0;
         return new InsertHierarchyElementEvent(parentPK, xmlEvent.getParent(), xmlEvent.getVisId(), xmlEvent.getDescription(), index, crdr1, aug_crdr1, (Object)null);
      } else if(action.equalsIgnoreCase("amend")) {
         Integer crdr = xmlEvent.getCredit() == null?null:Integer.valueOf(decodeBoolean(xmlEvent.getCredit())?1:2);
         Integer aug_crdr = xmlEvent.getAugCredit() == null?null:Integer.valueOf(xmlEvent.getAugCredit().value().equals("NO_OVERRIDE")?0:(xmlEvent.getAugCredit().value().equals("CREDIT")?1:2));
         return new UpdateHierarchyElementEvent(pk, xmlEvent.getOrigVisId(), xmlEvent.getVisId(), xmlEvent.getDescription(), crdr, aug_crdr);
      } else if(action.equalsIgnoreCase("delete")) {
         return new RemoveHierarchyElementEvent(pk, xmlEvent.getVisId());
      } else if(action.equalsIgnoreCase("move")) {
         return new MoveHierarchyElementEvent(parentPK, xmlEvent.getParent(), index, pk, xmlEvent.getVisId());
      } else {
         throw new ValidationException("Unknown action type in hierarchy element:" + action);
      }
   }

   public PostUpdateHierarchyElementEvent createPostUpdateHierarchyElementEvent(HierarchyElementEvent srcEvent, HierarchyElementDAG preElement, HierarchyElementDAG postElement, HierarchyElementDAG origParent, int origIndex) throws ValidationException {
      if(srcEvent instanceof InsertHierarchyElementEvent) {
         return new InsertedHierarchyElementEvent(postElement, (InsertHierarchyElementEvent)srcEvent);
      } else if(srcEvent instanceof UpdateHierarchyElementEvent) {
         return new UpdatedHierarchyElementEvent(preElement, (UpdateHierarchyElementEvent)srcEvent);
      } else if(srcEvent instanceof MoveHierarchyElementEvent) {
         return new MovedHierarchyElementEvent(preElement, (MoveHierarchyElementEvent)srcEvent, origParent, origIndex);
      } else if(srcEvent instanceof RemoveHierarchyElementEvent) {
         return new RemovedHierarchyElementEvent(preElement, (RemoveHierarchyElementEvent)srcEvent);
      } else {
         throw new ValidationException("Unkown subclass of HierarchyElementEvent:" + srcEvent.getClass());
      }
   }

   public HierarchyElementEvent createHierarchyElementFeedEvent(HierarchyEvent xmlEvent, Object pk, Object parentPK, DimensionElementRef dimensionElementRef) throws ValidationException {
      String type = xmlEvent.getType().value();
      String action = xmlEvent.getAction().value();
      if(type.equalsIgnoreCase("hierarchy-element-feed")) {
         if(action.equalsIgnoreCase("insert")) {
            return new InsertHierarchyElementFeedEvent(parentPK, xmlEvent.getVisId(), xmlEvent.getIndex().intValue(), dimensionElementRef);
         } else if(action.equalsIgnoreCase("move")) {
            if(xmlEvent.getIndex() == null) {
               throw new IllegalArgumentException("Index not specified on hef-move for:" + pk);
            } else {
               return new MoveHierarchyElementFeedEvent(parentPK, xmlEvent.getParent(), xmlEvent.getIndex().intValue(), pk, xmlEvent.getVisId());
            }
         } else if(action.equalsIgnoreCase("delete")) {
            return new RemoveHierarchyElementFeedEvent(pk, (String)null);
         } else {
            throw new ValidationException("Unknown action for hierarchy-element-feed event:" + action);
         }
      } else {
         throw new ValidationException("Unknown hierarchy element event type:" + type);
      }
   }

   public PostUpdateHierarchyElementFeedEvent createPostUpdateHierarchyElementFeedEvent(HierarchyElementEvent srcEvent, HierarchyElementFeedDAG preElement, HierarchyElementFeedDAG postElement, HierarchyElementDAG origParent, int origIndex) throws ValidationException {
      if(srcEvent instanceof InsertHierarchyElementFeedEvent) {
         return new InsertedHierarchyElementFeedEvent(postElement, (InsertHierarchyElementFeedEvent)srcEvent);
      } else if(srcEvent instanceof MoveHierarchyElementFeedEvent) {
         return new MovedHierarchyElementFeedEvent(preElement, (MoveHierarchyElementFeedEvent)srcEvent, origParent, origIndex);
      } else if(srcEvent instanceof RemoveHierarchyElementFeedEvent) {
         return new RemovedHierarchyElementFeedEvent(preElement, (RemoveHierarchyElementFeedEvent)srcEvent);
      } else {
         throw new ValidationException("Unkown subclass of HierarchyElementEvent:" + srcEvent.getClass());
      }
   }

   public CalendarEvent createCalendarEvent(DimensionEvent xmlEvent, CalendarYearSpecDAG originalYearSpec, CalendarYearSpecImpl newYearSpec) throws ValidationException {
      String action = xmlEvent.getAction().value();
      Object cEvent;
      if(action.equalsIgnoreCase("insert")) {
         cEvent = new InsertCalendarYearEvent(newYearSpec);
      } else if(action.equalsIgnoreCase("amend")) {
         cEvent = new UpdateCalendarYearEvent(originalYearSpec, newYearSpec);
      } else {
         if(!action.equalsIgnoreCase("delete")) {
            throw new ValidationException("Invalid action type for a calnedar event:" + action);
         }

         cEvent = new RemoveCalendarYearEvent(originalYearSpec);
      }

      return (CalendarEvent)cEvent;
   }
}
