// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension.calendar;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.calendar.CalendarSpec;
import com.cedar.cp.dto.dimension.CalendarHierarchyElementImpl;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;
import com.cedar.cp.dto.dimension.HierarchyElementPK;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarNodeDateFormatter;
import com.cedar.cp.dto.dimension.calendar.CalendarSpecImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarYearSpecImpl;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarEditorUtils {

   private HierarchyElementImpl[] mHalfYear;
   private HierarchyElementImpl[] mQuarters;
   private HierarchyElementImpl[] mMonths;
   private HierarchyElementImpl[] mWeeks;
   private HierarchyElementImpl[] mDays;
   private int mNextKeyId = -2;


   public HierarchyElementImpl updateYear(HierarchyElementImpl year, CalendarYearSpecImpl cys, CalendarSpec cs, int startMonth) throws ValidationException {
      CalendarNodeDateFormatter fmt = new CalendarNodeDateFormatter();
      int yr = cys.getYear();
      this.mHalfYear = new HierarchyElementImpl[2];
      Calendar visCal = CalendarImpl.createCalendar(yr, startMonth);
      year.setVisId(fmt.formatDate(visCal.getTime(), 0, 0, cs.getYearVisIdFormat()));
      year.setDescription(fmt.formatDate(visCal.getTime(), 0, 0, cs.getYearDescrFormat()));
      String visId;
      String descr;
      if(cys.get(6)) {
         visId = fmt.formatDate(visCal.getTime(), 6, 0, cs.getOpenVisId());
         descr = fmt.formatDate(visCal.getTime(), 6, 0, cs.getOpenDescr());
         year.addChildElement(this.newNode(visId, descr, 6));
      }

      if(cys.get(1)) {
         visCal = CalendarImpl.createCalendar(yr, startMonth);
         visId = fmt.formatDate(visCal.getTime(), 1, 0, cs.getHalfYearVisIdFormat());
         descr = fmt.formatDate(visCal.getTime(), 1, 0, cs.getHalfYearDescrFormat());
         year.addChildElement(this.mHalfYear[0] = this.newNode(visId, descr, 1));
         visCal.add(2, 6);
         visId = fmt.formatDate(visCal.getTime(), 1, 1, cs.getHalfYearVisIdFormat());
         descr = fmt.formatDate(visCal.getTime(), 1, 1, cs.getHalfYearDescrFormat());
         year.addChildElement(this.mHalfYear[1] = this.newNode(visId, descr, 1));
      }

      this.mQuarters = new HierarchyElementImpl[4];
      int weeksInYear;
      int maxDays;
      int leaves;
      HierarchyElementImpl index;
      HierarchyElementImpl[] var16;
      if(cys.get(2)) {
         visCal = CalendarImpl.createCalendar(yr, startMonth);

         for(weeksInYear = 0; weeksInYear < 4; ++weeksInYear) {
            visId = fmt.formatDate(visCal.getTime(), 2, weeksInYear, cs.getQuarterVisIdFormat());
            descr = fmt.formatDate(visCal.getTime(), 2, weeksInYear, cs.getQuarterDescrFormat());
            visCal.add(2, 3);
            this.mQuarters[weeksInYear] = this.newNode(visId, descr, 2);
         }

         if(this.mHalfYear[0] != null) {
            this.mHalfYear[0].addChildElement(this.mQuarters[0]);
            this.mHalfYear[0].addChildElement(this.mQuarters[1]);
            this.mHalfYear[1].addChildElement(this.mQuarters[2]);
            this.mHalfYear[1].addChildElement(this.mQuarters[3]);
         } else {
            var16 = this.mQuarters;
            maxDays = var16.length;

            for(leaves = 0; leaves < maxDays; ++leaves) {
               index = var16[leaves];
               year.addChildElement(index);
            }
         }
      }

      this.mMonths = new HierarchyElementImpl[12];
      if(cys.get(3)) {
         visCal = CalendarImpl.createCalendar(yr, startMonth);

         for(weeksInYear = 0; weeksInYear < 12; ++weeksInYear) {
            visId = fmt.formatDate(visCal.getTime(), 3, weeksInYear, cs.getMonthVisIdFormat());
            descr = fmt.formatDate(visCal.getTime(), 3, weeksInYear, cs.getMonthDescrFormat());
            this.mMonths[weeksInYear] = this.newNode(visId, descr, 3);
            visCal.add(2, 1);
         }

         if(this.mQuarters[0] != null) {
            for(weeksInYear = 0; weeksInYear < 3; ++weeksInYear) {
               this.mQuarters[0].addChildElement(this.mMonths[weeksInYear]);
            }

            for(weeksInYear = 3; weeksInYear < 6; ++weeksInYear) {
               this.mQuarters[1].addChildElement(this.mMonths[weeksInYear]);
            }

            for(weeksInYear = 6; weeksInYear < 9; ++weeksInYear) {
               this.mQuarters[2].addChildElement(this.mMonths[weeksInYear]);
            }

            for(weeksInYear = 9; weeksInYear < 12; ++weeksInYear) {
               this.mQuarters[3].addChildElement(this.mMonths[weeksInYear]);
            }
         } else if(this.mHalfYear[0] != null) {
            for(weeksInYear = 0; weeksInYear < 6; ++weeksInYear) {
               this.mHalfYear[0].addChildElement(this.mMonths[weeksInYear]);
            }

            for(weeksInYear = 6; weeksInYear < 12; ++weeksInYear) {
               this.mHalfYear[1].addChildElement(this.mMonths[weeksInYear]);
            }
         } else {
            var16 = this.mMonths;
            maxDays = var16.length;

            for(leaves = 0; leaves < maxDays; ++leaves) {
               index = var16[leaves];
               year.addChildElement(index);
            }
         }
      }

      weeksInYear = CalendarImpl.calcWeeksInYear(yr, startMonth);
      this.mWeeks = new HierarchyElementImpl[weeksInYear];
      if(cys.get(4)) {
         visCal = CalendarImpl.createCalendar(yr, startMonth);

         for(maxDays = 0; maxDays < weeksInYear; ++maxDays) {
            visId = fmt.formatDate(visCal.getTime(), 4, maxDays, cs.getWeekVisIdFormat());
            descr = fmt.formatDate(visCal.getTime(), 4, maxDays, cs.getWeekDescrFormat());
            year.addChildElement(this.mWeeks[maxDays] = this.newNode(visId, descr, 4));
            visCal.add(3, 1);
         }
      }

      maxDays = CalendarImpl.calcDaysInYear(yr, startMonth);
      this.mDays = new HierarchyElementImpl[maxDays];
      int index1;
      int var17;
      if(cys.get(5)) {
         visCal = CalendarImpl.createCalendar(yr, startMonth);
         visCal.setMinimalDaysInFirstWeek(1);

         for(leaves = 0; leaves < maxDays; ++leaves) {
            visId = fmt.formatDate(visCal.getTime(), 5, leaves, cs.getDayVisIdFormat());
            descr = fmt.formatDate(visCal.getTime(), 5, leaves, cs.getDayDescrFormat());
            this.mDays[leaves] = this.newNode(visId, descr, 5);
            visCal.add(6, 1);
         }

         if(this.mWeeks[0] != null) {
            visCal = CalendarImpl.createCalendar(yr, startMonth);
            leaves = visCal.get(7);
            var17 = 0;

            for(index1 = leaves; index1 <= 7; ++index1) {
               this.mWeeks[0].addChildElement(this.mDays[var17++]);
            }

            for(index1 = 1; index1 < this.mWeeks.length; ++index1) {
               for(int i = 0; i < 7 && var17 < this.mDays.length; ++i) {
                  this.mWeeks[index1].addChildElement(this.mDays[var17++]);
               }
            }
         } else if(this.mMonths[0] != null) {
            visCal = CalendarImpl.createCalendar(yr, startMonth);

            for(leaves = 0; leaves < maxDays; ++leaves) {
               var17 = CalendarImpl.calculateMonthIndexForDay(startMonth, visCal);
               this.mMonths[var17].addChildElement(this.mDays[leaves]);
               visCal.add(6, 1);
            }
         } else if(this.mQuarters[0] != null) {
            visCal = CalendarImpl.createCalendar(yr, startMonth);

            for(leaves = 0; leaves < maxDays; ++leaves) {
               var17 = CalendarImpl.calculateMonthIndexForDay(startMonth, visCal);
               this.mQuarters[var17 / 3].addChildElement(this.mDays[leaves]);
               visCal.add(6, 1);
            }
         } else if(this.mHalfYear[0] != null) {
            visCal = CalendarImpl.createCalendar(yr, startMonth);

            for(leaves = 0; leaves < maxDays; ++leaves) {
               var17 = CalendarImpl.calculateMonthIndexForDay(startMonth, visCal);
               this.mHalfYear[var17 / 6].addChildElement(this.mDays[leaves]);
               visCal.add(6, 1);
            }
         } else {
            for(leaves = 0; leaves < maxDays; ++leaves) {
               year.addChildElement(this.mDays[leaves]);
            }
         }
      }

      List var18;
      HierarchyElementImpl var20;
      if(cys.get(8)) {
         visCal = CalendarImpl.createCalendar(yr, startMonth);
         visCal.add(2, 11);
         var20 = this.getLastNotNull(this.queryAdditionalPeriodParent(year, cys));
         var18 = this.getLeaves(year);
         index1 = var18.size();
         visId = fmt.formatDate(visCal.getTime(), 8, index1, cs.getPeriod13VisId());
         descr = fmt.formatDate(visCal.getTime(), 8, index1, cs.getPeriod13Descr());
         var20.addChildElement(this.newNode(visId, descr, 8));
      }

      if(cys.get(9)) {
         visCal = CalendarImpl.createCalendar(yr, startMonth);
         visCal.add(2, 11);
         var20 = this.getLastNotNull(this.queryAdditionalPeriodParent(year, cys));
         var18 = this.getLeaves(year);
         index1 = var18.size();
         visId = fmt.formatDate(visCal.getTime(), 9, index1, cs.getPeriod14VisId());
         descr = fmt.formatDate(visCal.getTime(), 9, index1, cs.getPeriod14Descr());
         var20.addChildElement(this.newNode(visId, descr, 9));
      }

      if(cys.get(7)) {
         visCal = CalendarImpl.createCalendar(yr, startMonth);
         visCal.add(2, 11);
         List var19 = this.getLeaves(year);
         var17 = var19.size();
         visId = fmt.formatDate(visCal.getTime(), 7, var17, cs.getAdjVisId());
         descr = fmt.formatDate(visCal.getTime(), 7, var17, cs.getAdjDescr());
         year.addChildElement(this.newNode(visId, descr, 7));
      }

      return year;
   }

   private List<HierarchyElementImpl> getLeaves(HierarchyElementImpl node) {
      return this.addLeaves(node, new ArrayList());
   }

   private HierarchyElementImpl getLastNotNull(HierarchyElementImpl[] node) {
      for(int i = node.length - 1; i >= 0; --i) {
         if(node[i] != null) {
            return node[i];
         }
      }

      return null;
   }

   private HierarchyElementImpl[] queryAdditionalPeriodParent(HierarchyElementImpl year, CalendarYearSpecImpl cys) {
      switch(cys.getLeafLevel()) {
      case 0:
      case 1:
      case 4:
         return new HierarchyElementImpl[]{year};
      case 2:
         if(this.mHalfYear[0] != null) {
            return this.mHalfYear;
         }

         return new HierarchyElementImpl[]{year};
      case 3:
         if(this.mQuarters[0] != null) {
            return this.mQuarters;
         } else {
            if(this.mHalfYear[0] != null) {
               return this.mHalfYear;
            }

            return new HierarchyElementImpl[]{year};
         }
      case 5:
         if(this.mWeeks[0] != null) {
            return this.mWeeks;
         } else if(this.mMonths[0] != null) {
            return this.mMonths;
         } else if(this.mQuarters[0] != null) {
            return this.mQuarters;
         } else {
            if(this.mHalfYear[0] != null) {
               return this.mHalfYear;
            }

            return new HierarchyElementImpl[]{year};
         }
      default:
         throw new IllegalStateException("Unexpected leaf level :" + cys.getLeafLevel());
      }
   }

   public HierarchyElementImpl newNode(String visId, String description, int calElemType) {
      CalendarHierarchyElementImpl node = new CalendarHierarchyElementImpl(new HierarchyElementPK(this.nextKeyId()));
      node.setVisId(visId);
      node.setDescription(description);
      node.setCalElemType(calElemType);
      return node;
   }

   public List<HierarchyElementImpl> queryWeightingElements(int startElement, int leafElement, int startMonth) throws ValidationException {
      HierarchyElementImpl year = this.newNode("2000", "2000", 0);
      CalendarYearSpecImpl cys = new CalendarYearSpecImpl(this.nextKeyId(), 2000);
      cys.set(6, false);
      cys.set(7, false);
      cys.set(1, false);
      cys.set(2, false);
      cys.set(3, false);
      cys.set(4, false);
      cys.set(5, false);
      cys.set(8, true);
      cys.set(9, true);
      cys.set(startElement, true);
      cys.set(leafElement, true);
      CalendarSpecImpl cs = new CalendarSpecImpl();
      cs.setYearVisIdFormat("Year");
      cs.setYearDescrFormat("Year");
      if(startElement == 1) {
         cs.setHalfYearVisIdFormat("Half Year");
         cs.setHalfYearDescrFormat("Half Year");
      } else if(leafElement == 1) {
         cs.setHalfYearVisIdFormat("HY${Idx}");
         cs.setHalfYearDescrFormat("Half Year");
      }

      if(startElement == 2) {
         cs.setQuarterVisIdFormat("Quarters");
         cs.setQuarterDescrFormat("Quarters");
      } else if(leafElement == 2) {
         cs.setQuarterVisIdFormat("Q${Idx}");
         cs.setQuarterDescrFormat("Quarter");
      }

      if(startElement == 3) {
         cs.setMonthVisIdFormat("Month");
         cs.setMonthDescrFormat("Month");
      } else if(leafElement == 3) {
         cs.setMonthVisIdFormat("${MMM}");
         cs.setMonthDescrFormat("${MMMM}");
      }

      if(startElement == 4) {
         cs.setWeekVisIdFormat("Week");
         cs.setWeekDescrFormat("Week");
      } else if(leafElement == 4) {
         cs.setWeekVisIdFormat("${ww}");
         cs.setWeekDescrFormat("Week");
      }

      if(leafElement == 5) {
         if(startElement == 0) {
            cs.setDayVisIdFormat("${dd}-${MMM}");
            cs.setDayDescrFormat("${dd}-${MMMM}");
         } else {
            cs.setDayVisIdFormat("{Idx}");
            cs.setDayDescrFormat("Day");
         }
      }

      this.updateYear(year, cys, cs, startMonth);
      return this.addLeaves(startElement == 0?year:(HierarchyElementImpl)year.getChildAt(0), new ArrayList());
   }

   private List<HierarchyElementImpl> addLeaves(HierarchyElementImpl node, List<HierarchyElementImpl> leaves) {
      if(node.isLeaf()) {
         if(((CalendarHierarchyElementImpl)node).getCalElemType() != 6 && ((CalendarHierarchyElementImpl)node).getCalElemType() != 7) {
            leaves.add(node);
         }
      } else {
         for(int i = 0; i < node.getChildCount(); ++i) {
            this.addLeaves((HierarchyElementImpl)node.getChildAt(i), leaves);
         }
      }

      return leaves;
   }

   public int nextKeyId() {
      return this.mNextKeyId--;
   }
}
