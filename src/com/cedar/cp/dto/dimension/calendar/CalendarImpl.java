// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.calendar;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyNode;
import com.cedar.cp.api.dimension.calendar.Calendar;
import com.cedar.cp.api.dimension.calendar.CalendarSpec;
import com.cedar.cp.api.dimension.calendar.CalendarYearSpec;
import com.cedar.cp.dto.dimension.HierarchyImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarSpecImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarYearSpecImpl;
import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.xmlform.XMLWritable;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.TreeModel;

public class CalendarImpl extends HierarchyImpl implements Calendar {

   private List<CalendarYearSpec> mYears;
   private CalendarSpecImpl mCalendarSpec = new CalendarSpecImpl();
   private boolean mChangeManagementUpdateRequired;
   private transient List<TreeModel> mYearModels;


   public CalendarImpl() {
      super((Object)null);
      this.mYears = new ArrayList();
      this.mYears.add(new CalendarYearSpecImpl(-1, java.util.Calendar.getInstance().get(1), new boolean[]{true, false, false, true, false, false, false, false, false, false}));
      this.mYearModels = new ArrayList();
   }

   public CalendarImpl(Object paramKey, List<CalendarYearSpec> years) {
      super(paramKey);
      this.mYears = years;
   }

   public List<CalendarYearSpec> getYearSpecs() {
      return this.mYears;
   }

   public CalendarYearSpecImpl getYearSpec(int year) {
      Iterator i$ = this.mYears.iterator();

      CalendarYearSpec cys;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         cys = (CalendarYearSpec)i$.next();
      } while(cys.getYear() != year);

      return (CalendarYearSpecImpl)cys;
   }

   public TreeModel getYearModel(int year) {
      int index = 0;

      for(Iterator i$ = this.mYears.iterator(); i$.hasNext(); ++index) {
         CalendarYearSpec cys = (CalendarYearSpec)i$.next();
         if(cys.getYear() == year) {
            return (TreeModel)this.mYearModels.get(index);
         }
      }

      return null;
   }

   public int getStartYear() {
      return this.mYears.isEmpty()?-1:((CalendarYearSpec)this.mYears.get(0)).getYear();
   }

   public int getEndYear() {
      return this.mYears.isEmpty()?-1:((CalendarYearSpec)this.mYears.get(this.mYears.size() - 1)).getYear();
   }

   public List<TreeModel> getYearModels() {
      if(this.mYearModels == null) {
         this.mYearModels = new ArrayList();
      }

      return this.mYearModels;
   }

   public List<CalendarYearSpec> getYears() {
      return this.mYears;
   }

   public void setYears(List<CalendarYearSpec> years) {
      this.mYears = years;
   }

   public CalendarSpec getCalendarSpec() {
      return this.mCalendarSpec;
   }

   public CalendarSpecImpl getCalendarSpecImpl() {
      return this.mCalendarSpec;
   }

   public void setCalendarSpec(CalendarSpecImpl calendarSpec) {
      this.mCalendarSpec = calendarSpec;
   }

   public static int calculateMonthIndexForDay(int startMonth, java.util.Calendar cal) {
      int monthIdx = cal.get(2) + (12 - startMonth);
      if(monthIdx > 11) {
         monthIdx -= 12;
      }

      return monthIdx;
   }

   public static java.util.Calendar createCalendar(int year, int startMonth) {
      GregorianCalendar cal = new GregorianCalendar();
      cal.set(1, year);
      cal.set(2, startMonth);
      cal.set(5, 1);
      return cal;
   }

   public static int calcWeeksInYear(int year, int startMonthInYear) {
      java.util.Calendar cal = createCalendar(year, startMonthInYear);
      int maxDays = calcDaysInYear(year, startMonthInYear);
      int dayOfWeekIdx = cal.get(7) - 1;
      int dayIdx = 0;

      int weekNo;
      for(weekNo = dayOfWeekIdx; weekNo < 7; ++weekNo) {
         ++dayIdx;
      }

      for(weekNo = 1; dayIdx < maxDays; ++weekNo) {
         dayIdx += 7;
      }

      return weekNo;
   }

   public static int calcDaysInYear(int startYear, int startMonthNo) {
      java.util.Calendar cal = createCalendar(startYear, startMonthNo);
      int daysCount = 0;

      for(int months = 12; months > 0; --months) {
         daysCount += cal.getActualMaximum(5);
         cal.add(2, 1);
      }

      return daysCount;
   }

   public List<EntityRef> getLeavesForYear(int year) throws ValidationException {
      if(this.getRoot() == null) {
         return null;
      } else {
         int yearIndex = -1;

         for(int yearRoot = 0; yearRoot < this.mYears.size(); ++yearRoot) {
            if(((CalendarYearSpec)this.mYears.get(yearRoot)).getYear() == year) {
               yearIndex = yearRoot;
               break;
            }
         }

         if(yearIndex == -1) {
            throw new ValidationException("Year " + year + " not found in calendar");
         } else {
            HierarchyNode var8 = (HierarchyNode)this.getRoot().getChildAt(yearIndex);
            List yearLeaves = getLeaves(var8, new ArrayList());
            ArrayList refs = new ArrayList();
            Iterator i$ = yearLeaves.iterator();

            while(i$.hasNext()) {
               HierarchyNode leaf = (HierarchyNode)i$.next();
               refs.add(leaf.getEntityRef());
            }

            return refs;
         }
      }
   }

   private static List<HierarchyNode> getLeaves(HierarchyNode node, List<HierarchyNode> leaves) {
      if(node.isLeaf()) {
         leaves.add(node);
      } else {
         Enumeration eChildren = node.children();

         while(eChildren.hasMoreElements()) {
            HierarchyNode child = (HierarchyNode)eChildren.nextElement();
            getLeaves(child, leaves);
         }
      }

      return leaves;
   }

   public boolean isChangeManagementUpdateRequired() {
      return this.mChangeManagementUpdateRequired;
   }

   public void setChangeManagementUpdateRequired(boolean changeManagementUpdateRequired) {
      this.mChangeManagementUpdateRequired = changeManagementUpdateRequired;
   }

   @SuppressWarnings("unchecked")
   public void writeXml(Writer out) throws IOException {
      out.write("<calendar>\n");
      XmlUtils.outputElement(out, "calendarSpec", (List) this.mYears);
      this.mCalendarSpec.writeXml(out);
      out.write("</calendar>\n");
   }

   public String getYearVisId(int year) {
      return this.isTwoCalendarYears()?String.valueOf(year) + '/' + (year + 1):String.valueOf(year);
   }

   private boolean isTwoCalendarYears() {
      return this.mCalendarSpec.getYearStartMonth() != 0;
   }
}
