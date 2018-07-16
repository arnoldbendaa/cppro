// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension.calendar;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarYearSpecImpl;
import com.cedar.cp.ejb.impl.dimension.CalendarYearSpecDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyNodeDAG;
import com.cedar.cp.ejb.impl.dimension.calendar.CalendarNodeFactory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class YearScaffold {

   private int mIndex;
   private CalendarNodeFactory mFactory;
   private int mYearNo;
   private int mYearStartMonth;
   private HierarchyNodeDAG mYear;
   private HierarchyNodeDAG mOpening;
   private HierarchyNodeDAG mAdjustment;
   private HierarchyNodeDAG mPeriod13;
   private HierarchyNodeDAG mPeriod14;
   private HierarchyNodeDAG[] mHalfYear;
   private HierarchyNodeDAG[] mQuarters;
   private HierarchyNodeDAG[] mMonths;
   private HierarchyNodeDAG[] mWeeks;
   private HierarchyNodeDAG[] mDays;


   public YearScaffold(CalendarNodeFactory factory, HierarchyNodeDAG year, int index, int yearNo, int yearStartMonth) {
      this.mFactory = factory;
      this.mYear = year;
      this.mIndex = index;
      this.mYearNo = yearNo;
      this.mYearStartMonth = yearStartMonth;
      this.createScaffoldArrays();
   }

   public YearScaffold(CalendarNodeFactory factory, CalendarYearSpecDAG ys, HierarchyNodeDAG year, int index, int yearNo, int yearStartMonth) {
      this.mIndex = index;
      this.mFactory = factory;
      this.mYearNo = ys.getCalendarYear();
      this.mYear = year;
      this.mYearNo = yearNo;
      this.mYearStartMonth = yearStartMonth;
      this.createScaffoldArrays();
      Calendar cal = CalendarImpl.createCalendar(this.mYearNo, this.mYearStartMonth);
      int weeksInYear = CalendarImpl.calcWeeksInYear(this.mYearNo, this.mYearStartMonth);
      int daysInYear = cal.getActualMaximum(6);
      int startPosInYear = ys.getSpec(6)?1:0;
      if(ys.getSpec(6)) {
         this.mOpening = year.getChildAtIndex(0);
      }

      if(ys.getSpec(1)) {
         this.mHalfYear[0] = year.getChildAtIndex(startPosInYear);
         this.mHalfYear[1] = year.getChildAtIndex(startPosInYear + 1);
      }

      if(ys.getSpec(2)) {
         if(this.mHalfYear[0] != null) {
            this.mQuarters[0] = this.mHalfYear[0].getChildAtIndex(0);
            this.mQuarters[1] = this.mHalfYear[0].getChildAtIndex(1);
            this.mQuarters[2] = this.mHalfYear[1].getChildAtIndex(0);
            this.mQuarters[3] = this.mHalfYear[1].getChildAtIndex(1);
         } else {
            this.mQuarters[0] = year.getChildAtIndex(startPosInYear);
            this.mQuarters[1] = year.getChildAtIndex(startPosInYear + 1);
            this.mQuarters[2] = year.getChildAtIndex(startPosInYear + 2);
            this.mQuarters[3] = year.getChildAtIndex(startPosInYear + 3);
         }
      }

      int day;
      int arr$;
      int len$;
      if(ys.getSpec(3)) {
         if(this.mQuarters[0] != null) {
            day = 0;

            for(arr$ = 0; arr$ < 4; ++arr$) {
               for(len$ = 0; len$ < 3; ++len$) {
                  this.mMonths[day++] = this.mQuarters[arr$].getChildAtIndex(len$);
               }
            }
         } else if(this.mHalfYear[0] != null) {
            day = 0;

            for(arr$ = 0; arr$ < 2; ++arr$) {
               for(len$ = 0; len$ < 6; ++len$) {
                  this.mMonths[day++] = this.mHalfYear[arr$].getChildAtIndex(len$);
               }
            }
         } else {
            for(day = 0; day < 12; ++day) {
               this.mMonths[day] = this.mYear.getChildAtIndex(startPosInYear + day);
            }
         }
      }

      if(ys.getSpec(4)) {
         for(day = 0; day < weeksInYear; ++day) {
            this.mWeeks[day] = this.mYear.getChildAtIndex(startPosInYear + day);
         }
      }

      if(ys.getSpec(5)) {
         if(this.mWeeks[0] != null) {
            day = 0;

            for(arr$ = 0; arr$ < weeksInYear; ++arr$) {
               for(len$ = 0; len$ < 7; ++len$) {
                  if(this.mWeeks[arr$].getChildren().size() > len$) {
                     this.mDays[day++] = this.mWeeks[arr$].getChildAtIndex(len$);
                  }
               }
            }
         } else {
            int i$;
            HierarchyNodeDAG halfYear;
            HierarchyNodeDAG node;
            Iterator i$1;
            HierarchyNodeDAG[] var18;
            if(this.mMonths[0] != null) {
               day = 0;
               var18 = this.mMonths;
               len$ = var18.length;

               for(i$ = 0; i$ < len$; ++i$) {
                  halfYear = var18[i$];

                  for(i$1 = halfYear.getChildren().iterator(); i$1.hasNext(); this.mDays[day++] = node) {
                     node = (HierarchyNodeDAG)i$1.next();
                  }
               }
            } else if(this.mQuarters[0] != null) {
               day = 0;
               var18 = this.mQuarters;
               len$ = var18.length;

               for(i$ = 0; i$ < len$; ++i$) {
                  halfYear = var18[i$];

                  for(i$1 = halfYear.getChildren().iterator(); i$1.hasNext(); this.mDays[day++] = node) {
                     node = (HierarchyNodeDAG)i$1.next();
                  }
               }
            } else if(this.mHalfYear[0] != null) {
               day = 0;
               var18 = this.mHalfYear;
               len$ = var18.length;

               for(i$ = 0; i$ < len$; ++i$) {
                  halfYear = var18[i$];

                  for(i$1 = halfYear.getChildren().iterator(); i$1.hasNext(); this.mDays[day++] = node) {
                     node = (HierarchyNodeDAG)i$1.next();
                  }
               }
            } else {
               for(day = 0; day < daysInYear; ++day) {
                  this.mDays[day] = this.mYear.getChildAtIndex(startPosInYear + day);
               }
            }
         }
      }

      if(ys.getSpec(8)) {
         this.mPeriod13 = this.findCalElementByType(8, year);
      }

      if(ys.getSpec(9)) {
         this.mPeriod14 = this.findCalElementByType(9, year);
      }

      if(ys.getSpec(7)) {
         this.mAdjustment = this.mYear.getChildAtIndex(this.mYear.getChildren().size() - 1);
      }

   }

   public HierarchyNodeDAG getLastNotNull(HierarchyNodeDAG[] node) {
      for(int i = node.length - 1; i >= 0; --i) {
         if(node[i] != null) {
            return node[i];
         }
      }

      return null;
   }

   public HierarchyNodeDAG[] queryAdditionalPeriodParent(HierarchyNodeDAG year, CalendarYearSpecImpl cys) {
      switch(cys.getLeafLevel()) {
      case 0:
      case 1:
      case 4:
         return new HierarchyNodeDAG[]{year};
      case 2:
         if(this.mHalfYear[0] != null) {
            return this.mHalfYear;
         }

         return new HierarchyNodeDAG[]{year};
      case 3:
         if(this.mQuarters[0] != null) {
            return this.mQuarters;
         } else {
            if(this.mHalfYear[0] != null) {
               return this.mHalfYear;
            }

            return new HierarchyNodeDAG[]{year};
         }
      case 5:
         if(this.mWeeks[0] != null) {
            return this.mWeeks;
         } else if(this.mMonths[0] != null) {
            return this.mMonths;
         } else if(this.mQuarters != null) {
            return this.mQuarters;
         } else {
            if(this.mHalfYear[0] != null) {
               return this.mHalfYear;
            }

            return new HierarchyNodeDAG[]{year};
         }
      default:
         throw new IllegalStateException("Unexpected leaf level :" + cys.getLeafLevel());
      }
   }

   public void disconnectHierarchy() {
      if(this.mYear != null) {
         this.disconnectChildren(this.mYear);
      }

      if(this.mHalfYear[0] != null) {
         this.disconnectChildren(this.mHalfYear[0]);
         this.disconnectChildren(this.mHalfYear[1]);
      }

      int i;
      if(this.mQuarters[0] != null) {
         for(i = 0; i < this.mQuarters.length; ++i) {
            this.disconnectChildren(this.mQuarters[i]);
         }
      }

      if(this.mMonths[0] != null) {
         for(i = 0; i < this.mMonths.length; ++i) {
            this.disconnectChildren(this.mMonths[i]);
         }
      }

      if(this.mWeeks[0] != null) {
         for(i = 0; i < this.mWeeks.length; ++i) {
            this.disconnectChildren(this.mWeeks[i]);
         }
      }

   }

   private void ensureYearIsSummaryNode() throws ValidationException {
      if(this.isYearLeaf()) {
         HierarchyNodeDAG oldYear = this.mYear;
         this.mFactory.registerRemovedElement(this.mYear);
         this.mYear = this.mFactory.newNode(this.mYear.getVisId(), this.mYear.getDescription(), 0);
         this.mFactory.registerNewYearNode(oldYear, this.mYear, this.mIndex);
      }

   }

   public boolean isYearLeaf() {
      return this.mYear != null && this.mYear.isFeeder();
   }

   private void ensureHalfYearAreSummaryNodes() throws ValidationException {
      if(this.isHalfYearLeaf()) {
         this.convertFromLeaves(this.mHalfYear, 1);
      }

   }

   public boolean isHalfYearLeaf() {
      return this.mHalfYear[0] != null && this.mHalfYear[0].isFeeder();
   }

   private void ensureQuartersAreSummaryNodes() throws ValidationException {
      if(this.isQuarterLeaves()) {
         this.convertFromLeaves(this.mQuarters, 2);
      }

   }

   public boolean isQuarterLeaves() {
      return this.mQuarters[0] != null && this.mQuarters[0].isFeeder();
   }

   private void ensureMonthsAreSummaryNodes() throws ValidationException {
      if(this.isMonthLeaves()) {
         this.convertFromLeaves(this.mMonths, 3);
      }

   }

   public boolean isMonthLeaves() {
      return this.mMonths[0] != null && this.mMonths[0].isFeeder();
   }

   private void ensureWeeksAreSummaryNodes() throws ValidationException {
      if(this.isWeeksLeaf()) {
         this.convertFromLeaves(this.mWeeks, 4);
      }

   }

   public boolean isWeeksLeaf() {
      return this.mWeeks[0] != null && this.mWeeks[0].isFeeder();
   }

   private void convertFromLeaves(HierarchyNodeDAG[] nodes, int newCalElemType) throws ValidationException {
      for(int i = 0; i < nodes.length; ++i) {
         if(nodes[i] != null) {
            this.mFactory.registerRemovedElement(nodes[i]);
            nodes[i] = this.mFactory.newNode(nodes[i].getVisId(), nodes[i].getDescription(), newCalElemType);
         }
      }

   }

   public void connectHierarchy(CalendarYearSpecImpl ys) throws ValidationException {
      this.createRequiredSummaryNodes();
      if(this.mOpening != null) {
         this.mYear.add(this.mOpening);
      }

      if(this.mHalfYear[0] != null) {
         this.mYear.add(this.mHalfYear[0]);
         this.mYear.add(this.mHalfYear[1]);
      }

      HierarchyNodeDAG[] parent;
      int maxWeeks;
      int i;
      HierarchyNodeDAG i1;
      if(this.mQuarters[0] != null) {
         if(this.mHalfYear[0] != null) {
            this.mHalfYear[0].add(this.mQuarters[0]);
            this.mHalfYear[0].add(this.mQuarters[1]);
            this.mHalfYear[1].add(this.mQuarters[2]);
            this.mHalfYear[1].add(this.mQuarters[3]);
         } else {
            parent = this.mQuarters;
            maxWeeks = parent.length;

            for(i = 0; i < maxWeeks; ++i) {
               i1 = parent[i];
               this.mYear.add(i1);
            }
         }
      }

      int var9;
      if(this.mMonths[0] != null) {
         if(this.mQuarters[0] != null) {
            for(var9 = 0; var9 < 3; ++var9) {
               this.mQuarters[0].add(this.mMonths[var9]);
            }

            for(var9 = 3; var9 < 6; ++var9) {
               this.mQuarters[1].add(this.mMonths[var9]);
            }

            for(var9 = 6; var9 < 9; ++var9) {
               this.mQuarters[2].add(this.mMonths[var9]);
            }

            for(var9 = 9; var9 < 12; ++var9) {
               this.mQuarters[3].add(this.mMonths[var9]);
            }
         } else if(this.mHalfYear[0] != null) {
            for(var9 = 0; var9 < 6; ++var9) {
               this.mHalfYear[0].add(this.mMonths[var9]);
            }

            for(var9 = 6; var9 < 12; ++var9) {
               this.mHalfYear[1].add(this.mMonths[var9]);
            }
         } else {
            parent = this.mMonths;
            maxWeeks = parent.length;

            for(i = 0; i < maxWeeks; ++i) {
               i1 = parent[i];
               this.mYear.add(i1);
            }
         }
      }

      if(this.mWeeks[0] != null) {
         parent = this.mWeeks;
         maxWeeks = parent.length;

         for(i = 0; i < maxWeeks; ++i) {
            i1 = parent[i];
            if(i1 != null) {
               this.mYear.add(i1);
            }
         }
      }

      if(this.mDays[0] != null) {
         var9 = CalendarImpl.calcDaysInYear(this.mYearNo, this.mYearStartMonth);
         maxWeeks = CalendarImpl.calcWeeksInYear(this.mYearNo, this.mYearStartMonth);
         int monthIdx;
         int var10;
         Calendar var11;
         if(this.mWeeks[0] != null) {
            var11 = CalendarImpl.createCalendar(this.mYearNo, this.mYearStartMonth);
            var10 = var11.get(7) - 1;
            monthIdx = 0;

            int weekNo;
            for(weekNo = var10; weekNo < 7; ++weekNo) {
               this.mWeeks[0].add(this.mDays[monthIdx++]);
            }

            for(weekNo = 1; weekNo < maxWeeks; ++weekNo) {
               for(int i2 = 0; i2 < 7 && monthIdx < var9; ++i2) {
                  if(this.mDays[monthIdx] != null) {
                     this.mWeeks[weekNo].add(this.mDays[monthIdx++]);
                  }
               }
            }
         } else if(this.mMonths[0] != null) {
            var11 = CalendarImpl.createCalendar(this.mYearNo, this.mYearStartMonth);

            for(var10 = 0; var10 < var9; ++var10) {
               monthIdx = CalendarImpl.calculateMonthIndexForDay(this.mYearStartMonth, var11);
               this.mMonths[monthIdx].add(this.mDays[var10]);
               var11.add(6, 1);
            }
         } else if(this.mQuarters[0] != null) {
            var11 = CalendarImpl.createCalendar(this.mYearNo, this.mYearStartMonth);

            for(var10 = 0; var10 < var9; ++var10) {
               monthIdx = CalendarImpl.calculateMonthIndexForDay(this.mYearStartMonth, var11);
               this.mQuarters[monthIdx / 3].add(this.mDays[var10]);
               var11.add(6, 1);
            }
         } else if(this.mHalfYear[0] != null) {
            var11 = CalendarImpl.createCalendar(this.mYearNo, this.mYearStartMonth);

            for(var10 = 0; var10 < var9; ++var10) {
               monthIdx = CalendarImpl.calculateMonthIndexForDay(this.mYearStartMonth, var11);
               this.mHalfYear[monthIdx / 6].add(this.mDays[var10]);
               var11.add(6, 1);
            }
         } else {
            for(i = 0; i < var9; ++i) {
               this.mYear.add(this.mDays[i]);
            }
         }
      }

      HierarchyNodeDAG var12;
      if(this.mPeriod13 != null) {
         var12 = this.getLastNotNull(this.queryAdditionalPeriodParent(this.mYear, ys));
         var12.add(this.mPeriod13);
      }

      if(this.mPeriod14 != null) {
         var12 = this.getLastNotNull(this.queryAdditionalPeriodParent(this.mYear, ys));
         var12.add(this.mPeriod14);
      }

      if(this.mAdjustment != null) {
         this.mYear.add(this.mAdjustment);
      }

   }

   private void createRequiredSummaryNodes() throws ValidationException {
      if(this.mOpening != null) {
         this.ensureYearIsSummaryNode();
      }

      if(this.mHalfYear[0] != null) {
         this.ensureYearIsSummaryNode();
      }

      if(this.mQuarters[0] != null) {
         if(this.mHalfYear[0] != null) {
            this.ensureHalfYearAreSummaryNodes();
         } else {
            this.ensureYearIsSummaryNode();
         }
      }

      if(this.mMonths[0] != null) {
         if(this.mQuarters[0] != null) {
            this.ensureQuartersAreSummaryNodes();
         } else if(this.mHalfYear[0] != null) {
            this.ensureHalfYearAreSummaryNodes();
         } else {
            this.ensureYearIsSummaryNode();
         }
      }

      if(this.mWeeks[0] != null) {
         this.ensureYearIsSummaryNode();
      }

      if(this.mDays[0] != null) {
         if(this.mWeeks[0] != null) {
            this.ensureWeeksAreSummaryNodes();
         } else if(this.mMonths[0] != null) {
            this.ensureMonthsAreSummaryNodes();
         } else if(this.mQuarters[0] != null) {
            this.ensureQuartersAreSummaryNodes();
         } else if(this.mHalfYear[0] != null) {
            this.ensureHalfYearAreSummaryNodes();
         } else {
            this.ensureYearIsSummaryNode();
         }
      }

      if(this.mAdjustment != null) {
         this.ensureYearIsSummaryNode();
      }

      if(!this.mYear.isFeeder() && this.mOpening == null && this.mAdjustment == null && this.mPeriod13 == null && this.mPeriod14 == null && this.mHalfYear[0] == null && this.mQuarters[0] == null && this.mMonths[0] == null && this.mWeeks[0] == null && this.mDays[0] == null) {
         HierarchyNodeDAG oldYearNode = this.mYear;
         this.mYear = this.mFactory.newNode(this.mYear.getVisId(), this.mYear.getDescription(), true, 0);
         this.mFactory.registerNewYearNode(oldYearNode, this.mYear, this.mIndex);
         this.mFactory.registerRemovedElement(oldYearNode);
      }

   }

   private HierarchyNodeDAG findCalElementByType(int type, HierarchyNodeDAG node) {
      HierarchyNodeDAG ans = null;
      if(node.getCalElemType() == type) {
         return node;
      } else {
         if(node.getChildren() != null) {
            Iterator cIter = node.getChildren().iterator();

            while(cIter.hasNext()) {
               HierarchyNodeDAG child = (HierarchyNodeDAG)cIter.next();
               ans = this.findCalElementByType(type, child);
               if(ans != null) {
                  return ans;
               }
            }
         }

         return ans;
      }
   }

   private void disconnectChildren(HierarchyNodeDAG node) {
      while(node != null && node.getChildren() != null && !node.getChildren().isEmpty()) {
         node.remove(node.getChildAtIndex(0));
      }

   }

   public HierarchyNodeDAG getYear() {
      return this.mYear;
   }

   public HierarchyNodeDAG getOpening() {
      return this.mOpening;
   }

   public HierarchyNodeDAG getAdjustment() {
      return this.mAdjustment;
   }

   public HierarchyNodeDAG[] getHalfYear() {
      return this.mHalfYear;
   }

   public HierarchyNodeDAG[] getQuarters() {
      return this.mQuarters;
   }

   public HierarchyNodeDAG[] getMonths() {
      return this.mMonths;
   }

   public HierarchyNodeDAG[] getWeeks() {
      return this.mWeeks;
   }

   public HierarchyNodeDAG[] getDays() {
      return this.mDays;
   }

   public List<HierarchyNodeDAG> getLeaves(boolean includeOpenAndAdjustment) {
      ArrayList leaves = new ArrayList();
      if(includeOpenAndAdjustment && this.mOpening != null) {
         leaves.add(this.mOpening);
      }

      HierarchyNodeDAG[] arr$;
      int len$;
      int i$;
      HierarchyNodeDAG node;
      if(this.mDays[0] != null) {
         arr$ = this.mDays;
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            node = arr$[i$];
            leaves.add(node);
         }
      } else if(this.mWeeks[0] != null) {
         arr$ = this.mWeeks;
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            node = arr$[i$];
            leaves.add(node);
         }
      } else if(this.mMonths[0] != null) {
         arr$ = this.mMonths;
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            node = arr$[i$];
            leaves.add(node);
         }
      } else if(this.mQuarters[0] != null) {
         arr$ = this.mQuarters;
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            node = arr$[i$];
            leaves.add(node);
         }
      } else if(this.mHalfYear[0] != null) {
         arr$ = this.mHalfYear;
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            node = arr$[i$];
            leaves.add(node);
         }
      } else {
         leaves.add(this.mYear);
      }

      if(this.mPeriod13 != null) {
         leaves.add(this.mPeriod13);
      }

      if(this.mPeriod14 != null) {
         leaves.add(this.mPeriod14);
      }

      if(includeOpenAndAdjustment && this.mAdjustment != null) {
         leaves.add(this.mAdjustment);
      }

      return leaves;
   }

   public void setYear(HierarchyNodeDAG year) {
      this.mYear = year;
   }

   public int getYearNo() {
      return this.mYearNo;
   }

   public void setAdjustment(HierarchyNodeDAG adjustment) {
      this.mAdjustment = adjustment;
   }

   public void setOpening(HierarchyNodeDAG opening) {
      this.mOpening = opening;
   }

   public void setPeriod13(HierarchyNodeDAG period13) {
      this.mPeriod13 = period13;
   }

   public void setPeriod14(HierarchyNodeDAG period14) {
      this.mPeriod14 = period14;
   }

   public HierarchyNodeDAG getPeriod13() {
      return this.mPeriod13;
   }

   public HierarchyNodeDAG getPeriod14() {
      return this.mPeriod14;
   }

   private void createScaffoldArrays() {
      Calendar cal = Calendar.getInstance();
      cal.set(1, this.getYearNo());
      cal.set(6, 1);
      this.mHalfYear = new HierarchyNodeDAG[2];
      this.mQuarters = new HierarchyNodeDAG[4];
      this.mMonths = new HierarchyNodeDAG[12];
      this.mWeeks = new HierarchyNodeDAG[CalendarImpl.calcWeeksInYear(this.mYearNo, this.mYearStartMonth)];
      this.mDays = new HierarchyNodeDAG[CalendarImpl.calcDaysInYear(this.mYearNo, this.mYearStartMonth)];
   }
}
