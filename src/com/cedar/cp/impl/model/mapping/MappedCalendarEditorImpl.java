// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.mapping;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.calendar.CalendarYearSpec;
import com.cedar.cp.api.model.mapping.MappedCalendar;
import com.cedar.cp.api.model.mapping.MappedCalendarEditor;
import com.cedar.cp.api.model.mapping.MappedCalendarYear;
import com.cedar.cp.api.model.mapping.MappedCalendarYearEditor;
import com.cedar.cp.api.model.mapping.extsys.FinanceCalendarYear;
import com.cedar.cp.api.model.mapping.extsys.FinanceCompany;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.dto.model.mapping.MappedCalendarImpl;
import com.cedar.cp.dto.model.mapping.MappedCalendarYearImpl;
import com.cedar.cp.dto.model.mapping.MappedCalendarYearPK;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.dimension.calendar.CalendarEditorImpl;
import com.cedar.cp.impl.model.mapping.MappedCalendarEditorImpl$1;
import com.cedar.cp.impl.model.mapping.MappedCalendarYearEditorImpl;
import com.cedar.cp.impl.model.mapping.MappedModelEditorImpl;
import com.cedar.cp.impl.model.mapping.MappedModelEditorSessionImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MappedCalendarEditorImpl extends CalendarEditorImpl implements MappedCalendarEditor {

   private MappedCalendarImpl mMappedCalendar;
   private FinanceCompany mFinanceCompany;


   public MappedCalendarEditorImpl(BusinessSessionImpl sess, MappedCalendarImpl mappedCalendar, FinanceCompany fc) {
      super(sess, (CalendarImpl)mappedCalendar.getCalendar());
      this.mMappedCalendar = mappedCalendar;
      this.mFinanceCompany = fc;
   }

   public MappedCalendar getMappedCalendar() {
      return this.mMappedCalendar;
   }

   public MappedCalendarYearEditor getMappedCalendarYearEditor(int year) throws ValidationException {
      FinanceCalendarYear fcy = this.findFinanceCalendarYear(year);
      MappedCalendarYearImpl mcy = (MappedCalendarYearImpl)this.mMappedCalendar.findMappedCalendarYear(year);
      boolean newMapping = false;
      if(mcy == null) {
         mcy = new MappedCalendarYearImpl(new MappedCalendarYearPK(-year), year, new ArrayList());
         newMapping = true;
      }

      return new MappedCalendarYearEditorImpl(this.getBusinessSession(), this, mcy, fcy, newMapping);
   }

   public FinanceCalendarYear findFinanceCalendarYear(int year) {
      Iterator i$ = this.mFinanceCompany.getFinanceCalendarYears().iterator();

      FinanceCalendarYear fcy;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         fcy = (FinanceCalendarYear)i$.next();
      } while(fcy.getYear() != year);

      return fcy;
   }

   public void removeYear(boolean end) throws ValidationException {
      int yearRemoved = end?this.getCalendar().getEndYear():this.getCalendar().getStartYear();
      super.removeYear(end);
      MappedCalendarYearImpl mappedCalendarYear = (MappedCalendarYearImpl)this.mMappedCalendar.findMappedCalendarYear(yearRemoved);
      this.mMappedCalendar.getMappedCalendarYears().remove(mappedCalendarYear);
      this.setContentModified();
   }

   public void setDetail(int year, int calendarType, boolean required) throws ValidationException {
      List oldLeafElements = this.getLeafElementRefs(year);
      super.setDetail(year, calendarType, required);
      List newLeafElements = this.getLeafElementRefs(year);
      if(oldLeafElements.size() != newLeafElements.size()) {
         MappedCalendarYearImpl mappedCalendarYear = (MappedCalendarYearImpl)this.mMappedCalendar.findMappedCalendarYear(year);
         if(mappedCalendarYear != null) {
            mappedCalendarYear.setMappedCalendarElements((List)null);
            this.setContentModified();
         }
      }

   }

   public void update(MappedCalendarYearImpl updatedMappedCalendarYear) {
      MappedCalendarYearImpl origMappedCalendarYear = (MappedCalendarYearImpl)this.mMappedCalendar.findMappedCalendarYear(updatedMappedCalendarYear.getYear());
      int index;
      if(origMappedCalendarYear == null) {
         index = Collections.binarySearch(this.mMappedCalendar.getMappedCalendarYears(), updatedMappedCalendarYear, new MappedCalendarEditorImpl$1(this));
         if(index < 0) {
            index = -(index + 1);
            if(index == this.mMappedCalendar.getMappedCalendarYears().size()) {
               this.mMappedCalendar.getMappedCalendarYears().add(updatedMappedCalendarYear);
            } else {
               this.mMappedCalendar.getMappedCalendarYears().add(index, updatedMappedCalendarYear);
            }
         }
      } else {
         index = this.mMappedCalendar.getMappedCalendarYears().indexOf(origMappedCalendarYear);
         this.mMappedCalendar.getMappedCalendarYears().set(index, updatedMappedCalendarYear);
      }

      this.setContentModified();
   }

   public FinanceCompany getFinanceCompany() {
      return this.mFinanceCompany;
   }

   public void commit() throws ValidationException {
      if(this.mMappedCalendar.getCalendar().getVisId() != null && this.mMappedCalendar.getCalendar().getVisId().trim().length() != 0) {
         if(this.mMappedCalendar.getCalendar().getDescription() != null && this.mMappedCalendar.getCalendar().getDescription().trim().length() != 0) {
            Iterator editor = this.mFinanceCompany.getFinanceCalendarYears().iterator();

            while(editor.hasNext()) {
               FinanceCalendarYear fcy = (FinanceCalendarYear)editor.next();
               CalendarYearSpec cys = this.getCalendar().getYearSpec(fcy.getYear());
               if(cys != null) {
                  MappedCalendarYear mcy = this.getMappedCalendar().findMappedCalendarYear(cys.getYear());
                  if(mcy == null) {
                     throw new ValidationException("No period mapping for year:" + cys.getYear());
                  }

                  if(!mcy.hasMappedElements()) {
                     throw new ValidationException("No mapped elements for year:" + cys.getYear());
                  }

                  if(mcy.hasUnmappedElements()) {
                     throw new ValidationException("Unmapped elements for year:" + cys.getYear());
                  }
               }
            }

            super.commit();
            MappedModelEditorImpl editor1 = (MappedModelEditorImpl)((MappedModelEditorSessionImpl)this.getBusinessSession()).getMappedModelEditor();
            editor1.getMappedModelImpl().checkMappedDataTypeYearOffsets();
         } else {
            throw new ValidationException("A calendar description must be supplied.");
         }
      } else {
         throw new ValidationException("A calendar visual id must be supplied.");
      }
   }

   public void rollback() {}
}
