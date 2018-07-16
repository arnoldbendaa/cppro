// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.mapping;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.calendar.CalendarYearSpec;
import com.cedar.cp.api.model.mapping.MappedCalendarElement;
import com.cedar.cp.api.model.mapping.MappedCalendarYear;
import com.cedar.cp.api.model.mapping.MappedCalendarYearEditor;
import com.cedar.cp.api.model.mapping.extsys.FinanceCalendarYear;
import com.cedar.cp.api.model.mapping.extsys.FinancePeriod;
import com.cedar.cp.dto.dimension.CalendarHierarchyElementImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarYearSpecImpl;
import com.cedar.cp.dto.model.mapping.MappedCalendarElementImpl;
import com.cedar.cp.dto.model.mapping.MappedCalendarElementPK;
import com.cedar.cp.dto.model.mapping.MappedCalendarYearImpl;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.model.mapping.MappedCalendarEditorImpl;
import java.util.Iterator;
import java.util.List;

public class MappedCalendarYearEditorImpl extends SubBusinessEditorImpl implements MappedCalendarYearEditor {

   private MappedCalendarYearImpl mMappedCalendarYear;
   private FinanceCalendarYear mFinanceCalendarYear;
   private static int sElementKey = -1;


   public MappedCalendarYearEditorImpl(BusinessSession sess, MappedCalendarEditorImpl parent, MappedCalendarYearImpl mappedCalendarYear, FinanceCalendarYear financeCalendarYear, boolean newMapping) {
      super(sess, parent);
      this.mFinanceCalendarYear = financeCalendarYear;

      try {
         this.mMappedCalendarYear = (MappedCalendarYearImpl)mappedCalendarYear.clone();
      } catch (CloneNotSupportedException var8) {
         throw new CPException("Failed to clone MappedCalendarYearImpl:", var8);
      }

      this.createDefaultMappingElements();
      if(newMapping) {
         int year = this.mMappedCalendarYear.getYear();
         MappedCalendarYear mcy = parent.getMappedCalendar().findMappedCalendarYear(year - 1);
         if(mcy != null) {
            if(this.hasSameLeafSpec(parent.getCalendar().getYearSpec(year), parent.getCalendar().getYearSpec(year - 1)) && this.hasSameLeafSpec(financeCalendarYear, parent.findFinanceCalendarYear(year - 1))) {
               this.copyMapping((MappedCalendarYearImpl)mcy, parent.findFinanceCalendarYear(year - 1));
            }
         } else {
            mcy = parent.getMappedCalendar().findMappedCalendarYear(year + 1);
            if(mcy != null && this.hasSameLeafSpec(parent.getCalendar().getYearSpec(year), parent.getCalendar().getYearSpec(year + 1)) && this.hasSameLeafSpec(financeCalendarYear, parent.findFinanceCalendarYear(year + 1))) {
               this.copyMapping((MappedCalendarYearImpl)mcy, parent.findFinanceCalendarYear(year + 1));
            }
         }
      }

   }

   private void copyMapping(MappedCalendarYearImpl srcmcy, FinanceCalendarYear srcfcy) {
      List srcCPLeaves = this.getMappedCalendarEditor().getLeafElementRefs(srcfcy.getYear());
      List srcExtSysLeaves = this.getFinanceCalendarYear().getFinancePeriods();
      List trgCPLeaves = this.getMappedCalendarEditor().getLeafElementRefs(this.mMappedCalendarYear.getYear());
      List trgExtSysLeaves = this.getFinanceCalendarYear().getFinancePeriods();

      for(int index = 0; index < srcmcy.getMappedCalendarElements().size(); ++index) {
         MappedCalendarElementImpl srcmce = (MappedCalendarElementImpl)srcmcy.getMappedCalendarElements().get(index);
         MappedCalendarElementImpl trgmce = (MappedCalendarElementImpl)this.mMappedCalendarYear.getMappedCalendarElements().get(index);
         if(srcmce.getCalendarElementRef() == null) {
            trgmce.setCalendarElementRef((EntityRef)null);
         } else {
            int calElementIndex = srcCPLeaves.indexOf(srcmce.getCalendarElementRef());
            trgmce.setCalendarElementRef((EntityRef)trgCPLeaves.get(calElementIndex));
         }

         trgmce.setPeriod(srcmce.getPeriod());
      }

      this.setContentModified();
   }

   private boolean hasSameLeafSpec(FinanceCalendarYear fcy1, FinanceCalendarYear fcy2) {
      if(fcy1 != null && fcy2 != null) {
         if(fcy1.getFinancePeriods().size() != fcy2.getFinancePeriods().size()) {
            return false;
         } else {
            for(int index = 0; index < fcy1.getFinancePeriods().size(); ++index) {
               FinancePeriod fp1 = (FinancePeriod)fcy1.getFinancePeriods().get(index);
               FinancePeriod fp2 = (FinancePeriod)fcy2.getFinancePeriods().get(index);
               if(fp1.getPeriod() != fp2.getPeriod()) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private boolean hasSameLeafSpec(CalendarYearSpec cys1, CalendarYearSpec cys2) {
      CalendarYearSpecImpl cysi1 = (CalendarYearSpecImpl)cys1;
      CalendarYearSpecImpl cysi2 = (CalendarYearSpecImpl)cys2;
      return cysi1.isCompatibleLeafLevel(cysi2);
   }

   private MappedCalendarEditorImpl getMappedCalendarEditor() {
      return (MappedCalendarEditorImpl)this.getOwner();
   }

   public List<EntityRef> getCPLeafElements() {
      return this.getMappedCalendarEditor().getLeafElementRefs(this.mFinanceCalendarYear.getYear());
   }

   public MappedCalendarElement findElementByCPRef(EntityRef cpElementRef) {
      return this.mMappedCalendarYear.findMappedCalendarElement(cpElementRef);
   }

   public MappedCalendarElement findElementByExtSysRef(FinancePeriod financePeriod) {
      return this.mMappedCalendarYear.findMappedCalendarElement(Integer.valueOf(financePeriod.getPeriod()));
   }

   private void createDefaultMappingElements() {
      List cpLeaves = this.getMappedCalendarEditor().getLeafElementRefs(this.mFinanceCalendarYear.getYear());
      List extSysLeaves = this.getFinanceCalendarYear().getFinancePeriods();
      Iterator i$;
      MappedCalendarElementImpl mcei;
      if(cpLeaves.size() >= extSysLeaves.size()) {
         i$ = cpLeaves.iterator();

         while(i$.hasNext()) {
            EntityRef mce = (EntityRef)i$.next();
            if(this.mMappedCalendarYear.findMappedCalendarElement(mce) == null) {
               mcei = new MappedCalendarElementImpl();
               mcei.setKey(this.nextMappedCalendarElementPK());
               mcei.setPeriod((Integer)null);
               mcei.setCalendarElementRef(mce);
               this.mMappedCalendarYear.getMappedCalendarElements().add(mcei);
            }
         }
      } else {
         i$ = extSysLeaves.iterator();

         while(i$.hasNext()) {
            FinancePeriod mce1 = (FinancePeriod)i$.next();
            if(this.mMappedCalendarYear.findMappedCalendarElement(Integer.valueOf(mce1.getPeriod())) == null) {
               mcei = new MappedCalendarElementImpl();
               mcei.setKey(this.nextMappedCalendarElementPK());
               mcei.setPeriod(Integer.valueOf(mce1.getPeriod()));
               mcei.setCalendarElementRef((EntityRef)null);
               this.mMappedCalendarYear.getMappedCalendarElements().add(mcei);
            }
         }
      }

      i$ = this.mMappedCalendarYear.getMappedCalendarElements().iterator();

      while(i$.hasNext()) {
         MappedCalendarElement mce2 = (MappedCalendarElement)i$.next();
         mcei = (MappedCalendarElementImpl)mce2;
         if(mcei.getCalendarElementRef() != null) {
            EntityRef er = this.findCalendarElement(cpLeaves, mcei.getCalendarElementRef());
            if(er != null) {
               mcei.setCalendarElementRef(er);
            }
         }
      }

   }

   private EntityRef findCalendarElement(List<EntityRef> leaves, EntityRef ef) {
      if(leaves.contains(ef)) {
         int index = leaves.indexOf(ef);
         return index != -1?(EntityRef)leaves.get(index):null;
      } else {
         return null;
      }
   }

   private MappedCalendarElementPK nextMappedCalendarElementPK() {
      return new MappedCalendarElementPK(sElementKey--);
   }

   public MappedCalendarYear getMappedCalendarYear() {
      return this.mMappedCalendarYear;
   }

   public FinanceCalendarYear getFinanceCalendarYear() {
      return this.mFinanceCalendarYear;
   }

   public void updateMappedCalendarElement(Object key, FinancePeriod financePeriod, EntityRef cpPeriod) throws ValidationException {
      MappedCalendarElementImpl element = this.mMappedCalendarYear.findMappedCalendarElement(key);
      if(element == null) {
         throw new ValidationException("Unable to locate mapped calendar element with key:" + key);
      } else {
         element.setPeriod(financePeriod != null?Integer.valueOf(financePeriod.getPeriod()):null);
         element.setCalendarElementRef(cpPeriod);
         this.setContentModified();
      }
   }

   public void doAutoMap() throws ValidationException {
      this.createDefaultMappingElements();
      List cpRefs = this.getMappedCalendarEditor().getLeafElementRefs(this.mFinanceCalendarYear.getYear());
      List cpElements = this.getMappedCalendarEditor().getLeafElements(this.mFinanceCalendarYear.getYear());
      List extSysLeaves = this.getFinanceCalendarYear().getFinancePeriods();
      int cpIdx;
      int esIdx;
      if(cpElements.size() >= extSysLeaves.size()) {
         cpIdx = 0;

         for(esIdx = 0; esIdx < cpElements.size(); ++esIdx) {
            CalendarHierarchyElementImpl fp = (CalendarHierarchyElementImpl)cpElements.get(esIdx);
            EntityRef mce = (EntityRef)cpRefs.get(esIdx);
            MappedCalendarElementImpl che = this.mMappedCalendarYear.findMappedCalendarElement(mce);
            if(che != null && this.hasCompatibleMappingType(fp.getCalElemType(), extSysLeaves)) {
               while(cpIdx < extSysLeaves.size()) {
                  FinancePeriod cpElemRef = (FinancePeriod)extSysLeaves.get(cpIdx);
                  ++cpIdx;
                  if(this.isMappingCompatible(fp.getCalElemType(), cpElemRef.getPeriod())) {
                     che.setPeriod(Integer.valueOf(cpElemRef.getPeriod()));
                     break;
                  }
               }
            }
         }
      } else {
         cpIdx = 0;

         for(esIdx = 0; esIdx < extSysLeaves.size(); ++esIdx) {
            FinancePeriod var10 = (FinancePeriod)extSysLeaves.get(esIdx);
            MappedCalendarElementImpl var11 = this.mMappedCalendarYear.findMappedCalendarElement(new Integer(var10.getPeriod()));
            if(var11 != null && this.hasCompatibleMappingType(cpElements, var10.getPeriod())) {
               while(cpIdx < cpElements.size()) {
                  CalendarHierarchyElementImpl var12 = (CalendarHierarchyElementImpl)cpElements.get(cpIdx);
                  EntityRef var13 = (EntityRef)cpRefs.get(cpIdx);
                  ++cpIdx;
                  if(this.isMappingCompatible(var12.getCalElemType(), var10.getPeriod())) {
                     var11.setCalendarElementRef(var13);
                     break;
                  }
               }
            }
         }
      }

      this.setContentModified();
   }

   private boolean hasCompatibleMappingType(int calElemType, List<FinancePeriod> fps) {
      Iterator i$ = fps.iterator();

      FinancePeriod fp;
      do {
         if(!i$.hasNext()) {
            return false;
         }

         fp = (FinancePeriod)i$.next();
      } while(!this.isMappingCompatible(calElemType, fp.getPeriod()));

      return true;
   }

   private boolean hasCompatibleMappingType(List<CalendarHierarchyElementImpl> ches, int periodNo) {
      Iterator i$ = ches.iterator();

      CalendarHierarchyElementImpl che;
      do {
         if(!i$.hasNext()) {
            return false;
         }

         che = (CalendarHierarchyElementImpl)i$.next();
      } while(!this.isMappingCompatible(che.getCalElemType(), periodNo));

      return true;
   }

   private boolean isMappingCompatible(int cpCalElemType, int extSysPeriodNumber) {
      switch(cpCalElemType) {
      case 6:
         return extSysPeriodNumber == 0;
      case 7:
         return extSysPeriodNumber == 999;
      default:
         return extSysPeriodNumber != 0 && extSysPeriodNumber != 999;
      }
   }

   protected void saveModifications() throws ValidationException {
      if(this.isContentModified()) {
         this.getMappedCalendarEditor().update(this.mMappedCalendarYear);
      }

   }

   protected void undoModifications() throws CPException {}

}
