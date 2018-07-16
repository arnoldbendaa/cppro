// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.dto.dimension.calendar.CalendarYearSpecImpl;
import com.cedar.cp.dto.dimension.calendar.event.InsertYearEvent;
import com.cedar.cp.dto.dimension.calendar.event.RemoveYearEvent;
import com.cedar.cp.dto.dimension.calendar.event.UpdateYearEvent;
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
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementActionType;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementEvent;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementEventType;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementType;
import com.cedar.cp.ejb.impl.cm.xml.CrDrOverrideType;
import com.cedar.cp.ejb.impl.cm.xml.CubeEvent;
import com.cedar.cp.ejb.impl.cm.xml.CubeEventAction;
import com.cedar.cp.ejb.impl.cm.xml.CubeEventTypeType;
import com.cedar.cp.ejb.impl.cm.xml.DimensionActionType;
import com.cedar.cp.ejb.impl.cm.xml.DimensionEvent;
import com.cedar.cp.ejb.impl.cm.xml.DimensionEventType;
import com.cedar.cp.ejb.impl.cm.xml.HierarchyActionType;
import com.cedar.cp.ejb.impl.cm.xml.HierarchyEvent;
import com.cedar.cp.ejb.impl.cm.xml.HierarchyEventType;
import com.cedar.cp.ejb.impl.cm.xml.NullableBoolean;
import com.cedar.cp.ejb.impl.cm.xml.ObjectFactory;
import com.cedar.cp.ejb.impl.cm.xml.YearSpecType;
import com.cedar.cp.ejb.impl.dimension.DimensionEventConvertor;
import com.cedar.cp.ejb.impl.dimension.EventFactory$1;
import com.cedar.cp.ejb.impl.dimension.EventFactory$10;
import com.cedar.cp.ejb.impl.dimension.EventFactory$11;
import com.cedar.cp.ejb.impl.dimension.EventFactory$12;
import com.cedar.cp.ejb.impl.dimension.EventFactory$13;
import com.cedar.cp.ejb.impl.dimension.EventFactory$2;
import com.cedar.cp.ejb.impl.dimension.EventFactory$3;
import com.cedar.cp.ejb.impl.dimension.EventFactory$4;
import com.cedar.cp.ejb.impl.dimension.EventFactory$5;
import com.cedar.cp.ejb.impl.dimension.EventFactory$6;
import com.cedar.cp.ejb.impl.dimension.EventFactory$7;
import com.cedar.cp.ejb.impl.dimension.EventFactory$8;
import com.cedar.cp.ejb.impl.dimension.EventFactory$9;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;

public class EventFactory {

   private List mListeners = new ArrayList();
   private ObjectFactory mFactory;
   private DatatypeFactory mDTFactory;


   public EventFactory() {
      this.addListener(InsertDimensionElementEvent.class, new EventFactory$1(this));
      this.addListener(UpdateDimensionElementEvent.class, new EventFactory$2(this));
      this.addListener(RemoveDimensionElementEvent.class, new EventFactory$3(this));
      this.addListener(InsertHierarchyElementEvent.class, new EventFactory$4(this));
      this.addListener(UpdateHierarchyElementEvent.class, new EventFactory$5(this));
      this.addListener(MoveHierarchyElementEvent.class, new EventFactory$6(this));
      this.addListener(RemoveHierarchyElementEvent.class, new EventFactory$7(this));
      this.addListener(InsertHierarchyElementFeedEvent.class, new EventFactory$8(this));
      this.addListener(MoveHierarchyElementFeedEvent.class, new EventFactory$9(this));
      this.addListener(RemoveHierarchyElementFeedEvent.class, new EventFactory$10(this));
      this.addListener(InsertYearEvent.class, new EventFactory$11(this));
      this.addListener(UpdateYearEvent.class, new EventFactory$12(this));
      this.addListener(RemoveYearEvent.class, new EventFactory$13(this));
   }

   private Object doConvert(InsertHierarchyElementEvent src) throws Exception {
      HierarchyEvent heEvent = this.getObjectFactory().createHierarchyEvent();
      heEvent.setAction(HierarchyActionType.fromValue("insert"));
      heEvent.setVisId(src.getVisId());
      heEvent.setDescription(src.getDescription());
      heEvent.setCredit(NullableBoolean.fromValue(src.getCreditDebit() == 1?"true":"false"));
      heEvent.setAugCredit(CrDrOverrideType.fromValue(src.getAugCreditDebit() == 0?"NO_OVERRIDE":(src.getAugCreditDebit() == 1?"CREDIT":"DEBIT")));
      heEvent.setIndex(BigInteger.valueOf((long)src.getIndex()));
      heEvent.setType(HierarchyEventType.fromValue("hierarchy-element"));
      heEvent.setParent(src.getParentVisId());
      return heEvent;
   }

   private Object doConvert(UpdateHierarchyElementEvent src) throws Exception {
      HierarchyEvent heEvent = this.getObjectFactory().createHierarchyEvent();
      heEvent.setAction(HierarchyActionType.fromValue("amend"));
      heEvent.setType(HierarchyEventType.fromValue("hierarchy-element"));
      heEvent.setVisId(src.getVisId());
      heEvent.setOrigVisId(src.getOrigVisId());
      heEvent.setDescription(src.getDescription());
      heEvent.setCredit(NullableBoolean.fromValue(src.getCreditDebit().intValue() == 1?"true":"false"));
      heEvent.setAugCredit(CrDrOverrideType.fromValue(src.getAugCreditDebit().intValue() == 0?"NO_OVERRIDE":(src.getAugCreditDebit().intValue() == 1?"CREDIT":"DEBIT")));
      return heEvent;
   }

   private Object doConvert(MoveHierarchyElementEvent src) throws Exception {
      HierarchyEvent heEvent = this.getObjectFactory().createHierarchyEvent();
      heEvent.setAction(HierarchyActionType.fromValue("move"));
      heEvent.setType(HierarchyEventType.fromValue("hierarchy-element"));
      heEvent.setVisId(src.getVisId());
      heEvent.setParent(src.getParentVisId());
      heEvent.setIndex(BigInteger.valueOf((long)src.getIndex()));
      return heEvent;
   }

   private Object doConvert(RemoveHierarchyElementEvent src) throws Exception {
      HierarchyEvent heEvent = this.getObjectFactory().createHierarchyEvent();
      heEvent.setAction(HierarchyActionType.fromValue("delete"));
      heEvent.setType(HierarchyEventType.fromValue("hierarchy-element"));
      heEvent.setVisId(src.getVisId());
      return heEvent;
   }

   private Object doConvert(MoveHierarchyElementFeedEvent src) throws Exception {
      HierarchyEvent heEvent = this.getObjectFactory().createHierarchyEvent();
      heEvent.setAction(HierarchyActionType.fromValue("move"));
      heEvent.setType(HierarchyEventType.fromValue("hierarchy-element-feed"));
      heEvent.setVisId(src.getVisId());
      heEvent.setParent(src.getParentVisId());
      heEvent.setIndex(BigInteger.valueOf((long)src.getIndex()));
      return heEvent;
   }

   private Object doConvert(InsertHierarchyElementFeedEvent src) throws Exception {
      HierarchyEvent heEvent = this.getObjectFactory().createHierarchyEvent();
      heEvent.setAction(HierarchyActionType.fromValue("insert"));
      heEvent.setType(HierarchyEventType.fromValue("hierarchy-element-feed"));
      heEvent.setVisId(src.getDimensionElementRef().getNarrative());
      heEvent.setParent(src.getVisId());
      heEvent.setIndex(BigInteger.valueOf((long)src.getIndex()));
      return heEvent;
   }

   private Object doConvert(RemoveHierarchyElementFeedEvent src) throws Exception {
      HierarchyEvent heEvent = this.getObjectFactory().createHierarchyEvent();
      heEvent.setVisId(src.getVisId());
      heEvent.setAction(HierarchyActionType.fromValue("delete"));
      heEvent.setType(HierarchyEventType.fromValue("hierarchy-element-feed"));
      return heEvent;
   }

   private Object doConvert(InsertDimensionElementEvent src) throws Exception {
      DimensionEvent event = this.getObjectFactory().createDimensionEvent();
      event.setAction(DimensionActionType.fromValue("insert"));
      event.setType(DimensionEventType.fromValue("dimension-element"));
      event.setCredit(NullableBoolean.fromValue(src.getCreditDebit() == 1?"true":"false"));
      event.setAugCredit(CrDrOverrideType.fromValue(src.getAugCreditDebit() == 0?"NO_OVERRIDE":(src.getAugCreditDebit() == 1?"CREDIT":"DEBIT")));
      event.setNotPlannable(NullableBoolean.fromValue(src.isNotPlannable()?"true":"false"));
      event.setDisabled(NullableBoolean.fromValue(src.isDisabled()?"true":"false"));
      event.setDescription(src.getDescription());
      event.setVisId(src.getVisId());
      event.setNull(NullableBoolean.fromValue(src.isIsNullElement()?"true":"false"));
      return event;
   }

   private Object doConvert(UpdateDimensionElementEvent src) throws Exception {
      DimensionEvent event = this.getObjectFactory().createDimensionEvent();
      event.setAction(DimensionActionType.fromValue("amend"));
      event.setType(DimensionEventType.fromValue("dimension-element"));
      event.setCredit(NullableBoolean.fromValue(src.getCreditDebit().intValue() == 1?"true":"false"));
      event.setAugCredit(CrDrOverrideType.fromValue(src.getAugCreditDebit().intValue() == 0?"NO_OVERRIDE":(src.getAugCreditDebit().intValue() == 1?"CREDIT":"DEBIT")));
      event.setNotPlannable(NullableBoolean.fromValue(src.isNotPlannable().booleanValue()?"true":"false"));
      event.setDisabled(NullableBoolean.fromValue(src.isDisabled().booleanValue()?"true":"false"));
      event.setDescription(src.getDescription());
      event.setVisId(src.getVisId());
      event.setOrigVisId(src.getOrigVisId());
      event.setNull(NullableBoolean.fromValue(src.isNullElement().booleanValue()?"true":"false"));
      return event;
   }

   private Object doConvert(RemoveDimensionElementEvent src) throws Exception {
      DimensionEvent event = this.getObjectFactory().createDimensionEvent();
      event.setAction(DimensionActionType.fromValue("delete"));
      event.setType(DimensionEventType.fromValue("dimension-element"));
      event.setVisId(src.getVisId());
      return event;
   }

   private Object doConvert(InsertYearEvent src) throws Exception {
      DimensionEvent event = this.getObjectFactory().createDimensionEvent();
      event.setYearSpec(this.newYearSpec(src));
      event.setAction(DimensionActionType.fromValue("insert"));
      event.setType(DimensionEventType.fromValue("year"));
      event.setVisId(String.valueOf(src.getYear()));
      return event;
   }

   private Object doConvert(UpdateYearEvent src) throws Exception {
      DimensionEvent event = this.getObjectFactory().createDimensionEvent();
      event.setYearSpec(this.newYearSpec(src));
      event.setAction(DimensionActionType.fromValue("amend"));
      event.setType(DimensionEventType.fromValue("year"));
      event.setVisId(String.valueOf(src.getYear()));
      return event;
   }

   private Object doConvert(RemoveYearEvent src) throws Exception {
      DimensionEvent event = this.getObjectFactory().createDimensionEvent();
      event.setYearSpec(this.newYearSpec(src));
      event.setAction(DimensionActionType.fromValue("delete"));
      event.setType(DimensionEventType.fromValue("year"));
      event.setVisId(String.valueOf(src.getYear()));
      return event;
   }

   private YearSpecType newYearSpec(CalendarYearSpecImpl src) throws Exception {
      YearSpecType yearSpec = this.getObjectFactory().createYearSpecType();
      yearSpec.setYear(src.getYear());
      yearSpec.setOpening(Boolean.valueOf(src.get(6)));
      yearSpec.setHalfYear(Boolean.valueOf(src.get(1)));
      yearSpec.setQuarter(Boolean.valueOf(src.get(2)));
      yearSpec.setMonth(Boolean.valueOf(src.get(3)));
      yearSpec.setWeek(Boolean.valueOf(src.get(4)));
      yearSpec.setDay(Boolean.valueOf(src.get(5)));
      yearSpec.setAdjustment(Boolean.valueOf(src.get(7)));
      yearSpec.setPeriod13(Boolean.valueOf(src.get(8)));
      yearSpec.setPeriod14(Boolean.valueOf(src.get(9)));
      return yearSpec;
   }

   public ChangeManagementType createDimensionCMRequest(String action, String visId, String description, List events) throws Exception {
      ChangeManagementType cm = this.getObjectFactory().createChangeManagementType();
      ChangeManagementEvent cmEvent = this.getObjectFactory().createChangeManagementEvent();
      cm.setExtractDateTime(this.getDatatypeFactory().newXMLGregorianCalendar(new GregorianCalendar()));
      cm.setSourceSystemName("CP");
      cm.getEvent().add(cmEvent);
      cmEvent.setType(ChangeManagementEventType.fromValue("dimension"));
      cmEvent.setVisId(visId);
      cmEvent.setAction(ChangeManagementActionType.fromValue(action));
      Iterator i = events.iterator();

      while(i.hasNext()) {
         com.cedar.cp.api.dimension.DimensionEvent event = (com.cedar.cp.api.dimension.DimensionEvent)i.next();
         cmEvent.getEvent().add((DimensionEvent)this.convertEvent(event));
      }

      return cm;
   }

   public ChangeManagementType createHierarchyCMRequest(String action, String visId, String description, String dimVisId, List events, boolean augmentMode) throws Exception {
      ChangeManagementType cm = this.getObjectFactory().createChangeManagementType();
      ChangeManagementEvent cmEvent = this.getObjectFactory().createChangeManagementEvent();
      cm.setExtractDateTime(this.getDatatypeFactory().newXMLGregorianCalendar(new GregorianCalendar()));
      cm.setSourceSystemName("CP");
      cm.getEvent().add(cmEvent);
      cmEvent.setType(ChangeManagementEventType.fromValue("dimension"));
      cmEvent.setVisId(dimVisId);
      cmEvent.setAction(ChangeManagementActionType.fromValue("amend"));
      DimensionEvent dimEvent = this.getObjectFactory().createDimensionEvent();
      cmEvent.getEvent().add(dimEvent);
      dimEvent.setAction(DimensionActionType.fromValue(action));
      dimEvent.setVisId(visId);
      dimEvent.setDescription(description);
      dimEvent.setType(DimensionEventType.fromValue("hierarchy"));
      dimEvent.setAugment(Boolean.valueOf(augmentMode));
      Iterator i = events.iterator();

      while(i.hasNext()) {
         Object inEvent = i.next();
         Object outEvent = this.convertEvent(inEvent);
         if(outEvent != null) {
            dimEvent.getEvent().add((HierarchyEvent)outEvent);
         }
      }

      return cm;
   }

   public ChangeManagementType createCalendarCMRequest(String action, String visId, String description, List<CalendarYearSpecImpl> events) throws Exception {
      ChangeManagementType cm = this.getObjectFactory().createChangeManagementType();
      ChangeManagementEvent cmEvent = this.getObjectFactory().createChangeManagementEvent();
      cm.setExtractDateTime(this.getDatatypeFactory().newXMLGregorianCalendar(new GregorianCalendar()));
      cm.setSourceSystemName("CP");
      cm.getEvent().add(cmEvent);
      cmEvent.setType(ChangeManagementEventType.fromValue("calendar"));
      cmEvent.setVisId(visId);
      cmEvent.setAction(ChangeManagementActionType.fromValue(action));
      Iterator i = events.iterator();

      while(i.hasNext()) {
         Object event = i.next();
         cmEvent.getEvent().add((DimensionEvent)this.convertEvent(event));
      }

      return cm;
   }

   public ChangeManagementType createFinanceCubeCMRequest(String model, String action, String visId, String description, List<CubeEvent> cubeEvents) throws Exception {
      ChangeManagementType cm = this.getObjectFactory().createChangeManagementType();
      ChangeManagementEvent cmEvent = this.getObjectFactory().createChangeManagementEvent();
      cm.setExtractDateTime(this.getDatatypeFactory().newXMLGregorianCalendar(new GregorianCalendar()));
      cm.setSourceSystemName("CP");
      cm.getEvent().add(cmEvent);
      cmEvent.setModel(model);
      cmEvent.setType(ChangeManagementEventType.fromValue("finance-cube"));
      cmEvent.setVisId(visId);
      cmEvent.setAction(ChangeManagementActionType.fromValue(action));
      cmEvent.getCubeEvent().addAll(cubeEvents);
      return cm;
   }

   public CubeEvent createCubeEvent(String action, String type, String dataTypeVisId, boolean[] rollUpRules) throws Exception {
      CubeEvent cubeEvent = this.getObjectFactory().createCubeEvent();
      cubeEvent.setAction(CubeEventAction.fromValue(action));
      cubeEvent.setType(CubeEventTypeType.fromValue(type));
      cubeEvent.setVisId(dataTypeVisId);
      cubeEvent.setRollUpRules(this.convertToCVS(rollUpRules));
      return cubeEvent;
   }

   private String convertToCVS(boolean[] rollUpRules) {
      if(rollUpRules == null) {
         return null;
      } else {
         StringBuffer sb = new StringBuffer();

         for(int i = 0; i < rollUpRules.length; ++i) {
            sb.append(String.valueOf(rollUpRules[i]));
            if(i < rollUpRules.length - 1) {
               sb.append(',');
            }
         }

         return sb.toString();
      }
   }

   private Object convertEvent(Object event) throws Exception {
      for(int i = 0; i < this.mListeners.size(); i += 2) {
         Class cls = (Class)this.mListeners.get(i);
         if(cls.equals(event.getClass())) {
            DimensionEventConvertor listener = (DimensionEventConvertor)this.mListeners.get(i + 1);
            return listener.convert(event);
         }
      }

      return null;
   }

   private ObjectFactory getObjectFactory() throws Exception {
      if(this.mFactory == null) {
         this.mFactory = new ObjectFactory();
      }

      return this.mFactory;
   }

   private DatatypeFactory getDatatypeFactory() throws Exception {
      if(this.mDTFactory == null) {
         this.mDTFactory = DatatypeFactory.newInstance();
      }

      return this.mDTFactory;
   }

   private void addListener(Class cls, DimensionEventConvertor listener) {
      this.mListeners.add(cls);
      this.mListeners.add(listener);
   }

   // $FF: synthetic method
   static Object accessMethod000(EventFactory x0, InsertDimensionElementEvent x1) throws Exception {
      return x0.doConvert(x1);
   }

   // $FF: synthetic method
   static Object accessMethod100(EventFactory x0, UpdateDimensionElementEvent x1) throws Exception {
      return x0.doConvert(x1);
   }

   // $FF: synthetic method
   static Object accessMethod200(EventFactory x0, RemoveDimensionElementEvent x1) throws Exception {
      return x0.doConvert(x1);
   }

   // $FF: synthetic method
   static Object accessMethod300(EventFactory x0, InsertHierarchyElementEvent x1) throws Exception {
      return x0.doConvert(x1);
   }

   // $FF: synthetic method
   static Object accessMethod400(EventFactory x0, UpdateHierarchyElementEvent x1) throws Exception {
      return x0.doConvert(x1);
   }

   // $FF: synthetic method
   static Object accessMethod500(EventFactory x0, MoveHierarchyElementEvent x1) throws Exception {
      return x0.doConvert(x1);
   }

   // $FF: synthetic method
   static Object accessMethod600(EventFactory x0, RemoveHierarchyElementEvent x1) throws Exception {
      return x0.doConvert(x1);
   }

   // $FF: synthetic method
   static Object accessMethod700(EventFactory x0, InsertHierarchyElementFeedEvent x1) throws Exception {
      return x0.doConvert(x1);
   }

   // $FF: synthetic method
   static Object accessMethod800(EventFactory x0, MoveHierarchyElementFeedEvent x1) throws Exception {
      return x0.doConvert(x1);
   }

   // $FF: synthetic method
   static Object accessMethod900(EventFactory x0, RemoveHierarchyElementFeedEvent x1) throws Exception {
      return x0.doConvert(x1);
   }

   // $FF: synthetic method
   static Object accessMethod1000(EventFactory x0, InsertYearEvent x1) throws Exception {
      return x0.doConvert(x1);
   }

   // $FF: synthetic method
   static Object accessMethod1100(EventFactory x0, UpdateYearEvent x1) throws Exception {
      return x0.doConvert(x1);
   }

   // $FF: synthetic method
   static Object accessMethod1200(EventFactory x0, RemoveYearEvent x1) throws Exception {
      return x0.doConvert(x1);
   }
}
