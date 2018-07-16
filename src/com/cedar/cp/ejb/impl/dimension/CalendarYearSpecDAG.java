// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.calendar.CalendarYearSpec;
import com.cedar.cp.dto.dimension.CalendarYearSpecPK;
import com.cedar.cp.dto.dimension.calendar.CalendarYearSpecImpl;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.base.AbstractDAG;
import com.cedar.cp.ejb.impl.dimension.CalendarYearSpecEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyNodeDAG;
import java.util.Calendar;
import java.util.List;

public class CalendarYearSpecDAG extends AbstractDAG {

   private DimensionDAG mDimension;
   private int mCalendarYearSpecId;
   private int mCalendarYear;
   private boolean[] mSpecs = new boolean[10];


   public CalendarYearSpecDAG(DAGContext context, DimensionDAG dimension, CalendarYearSpecEVO yearSpecEVO) throws ValidationException {
      super(context, false);
      this.mDimension = dimension;
      this.mCalendarYearSpecId = yearSpecEVO.getCalendarYearSpecId();
      this.mCalendarYear = yearSpecEVO.getCalendarYear();
      this.mSpecs[0] = yearSpecEVO.getYearInd();
      this.mSpecs[1] = yearSpecEVO.getHalfYearInd();
      this.mSpecs[2] = yearSpecEVO.getQuarterInd();
      this.mSpecs[3] = yearSpecEVO.getMonthInd();
      this.mSpecs[4] = yearSpecEVO.getWeekInd();
      this.mSpecs[5] = yearSpecEVO.getDayInd();
      this.mSpecs[6] = yearSpecEVO.getOpeningBalanceInd();
      this.mSpecs[7] = yearSpecEVO.getAdjustmentInd();
      this.mSpecs[8] = yearSpecEVO.getPeriod13Ind();
      this.mSpecs[9] = yearSpecEVO.getPeriod14Ind();
   }

   public CalendarYearSpecDAG(DAGContext context, DimensionDAG dimension, CalendarYearSpecImpl cys) {
      super(context, true);
      this.mDimension = dimension;
      this.mCalendarYear = cys.getYear();
      this.mCalendarYearSpecId = cys.getId();
      this.mSpecs[0] = cys.get(0);
      this.mSpecs[1] = cys.get(1);
      this.mSpecs[2] = cys.get(2);
      this.mSpecs[3] = cys.get(3);
      this.mSpecs[4] = cys.get(4);
      this.mSpecs[5] = cys.get(5);
      this.mSpecs[6] = cys.get(6);
      this.mSpecs[7] = cys.get(7);
      this.mSpecs[8] = cys.get(8);
      this.mSpecs[9] = cys.get(9);
   }

   public CalendarYearSpecEVO createEVO() {
      CalendarYearSpecEVO evo = new CalendarYearSpecEVO();
      evo.setDimensionId(this.getDimension().getDimensionId());
      evo.setCalendarYearSpecId(this.getCalendarYearSpecId());
      this.updateEVO(evo);
      return evo;
   }

   public void updateEVO(CalendarYearSpecEVO yearSpecEVO) {
      yearSpecEVO.setCalendarYear(this.mCalendarYear);
      yearSpecEVO.setYearInd(this.mSpecs[0]);
      yearSpecEVO.setHalfYearInd(this.mSpecs[1]);
      yearSpecEVO.setQuarterInd(this.mSpecs[2]);
      yearSpecEVO.setMonthInd(this.mSpecs[3]);
      yearSpecEVO.setWeekInd(this.mSpecs[4]);
      yearSpecEVO.setDayInd(this.mSpecs[5]);
      yearSpecEVO.setOpeningBalanceInd(this.mSpecs[6]);
      yearSpecEVO.setAdjustmentInd(this.mSpecs[7]);
      yearSpecEVO.setPeriod13Ind(this.mSpecs[8]);
      yearSpecEVO.setPeriod14Ind(this.mSpecs[9]);
   }

   public int getCalendarYear() {
      return this.mCalendarYear;
   }

   public void setCalendarYear(int calendarYear) {
      if(this.mCalendarYear != calendarYear) {
         this.mCalendarYear = calendarYear;
         this.setDirty();
      }

   }

   public boolean[] getSpecs() {
      return this.mSpecs;
   }

   public void setSpecs(boolean[] specs) {
      this.mSpecs = specs;
      this.setDirty();
   }

   public boolean getSpec(int level) {
      return this.mSpecs[level];
   }

   public void setSpec(int level, boolean value) {
      if(this.mSpecs[level] != value) {
         this.mSpecs[level] = value;
         this.setDirty();
      }

   }

   public int getCalendarYearSpecId() {
      return this.mCalendarYearSpecId;
   }

   public void setCalendarYearSpecId(int calendarYearSpecId) {
      if(this.mCalendarYearSpecId != calendarYearSpecId) {
         this.mCalendarYearSpecId = calendarYearSpecId;
         this.setDirty();
      }

   }

   public DimensionDAG getDimension() {
      return this.mDimension;
   }

   public void setDimension(DimensionDAG dimension) {
      this.mDimension = dimension;
   }

   public CalendarYearSpecPK getPK() {
      return new CalendarYearSpecPK(this.mCalendarYearSpecId);
   }

   public boolean compareYearSpec(CalendarYearSpec ys) {
      return this.getCalendarYear() != ys.getYear()?true:this.mSpecs[0] != ys.get(0) || this.mSpecs[1] != ys.get(1) || this.mSpecs[2] != ys.get(2) || this.mSpecs[3] != ys.get(3) || this.mSpecs[4] != ys.get(4) || this.mSpecs[5] != ys.get(5) || this.mSpecs[6] != ys.get(6) || this.mSpecs[7] != ys.get(7) || this.mSpecs[8] != ys.get(8) || this.mSpecs[9] != ys.get(9);
   }

   public int getLeafLevel() {
      for(int i = 5; i >= 0; --i) {
         if(this.mSpecs[i]) {
            return i;
         }
      }

      return 0;
   }

   public static int queryActiveLeafElementCount(int year, int leafLevel) {
      Calendar c;
      switch(leafLevel) {
      case 0:
         return 1;
      case 1:
         return 2;
      case 2:
         return 4;
      case 3:
         return 12;
      case 4:
         c = Calendar.getInstance();
         c.set(1, year);
         return c.getActualMaximum(3);
      case 5:
         c = Calendar.getInstance();
         c.set(1, year);
         return c.getActualMaximum(6);
      default:
         throw new IllegalStateException("Unexpected leaf level:" + leafLevel);
      }
   }

   public static int[] queryElementCounts(int year, int srcLeafLevel, int targetLeafLevel) {
      int[] counts = new int[queryActiveLeafElementCount(year, srcLeafLevel)];
      Calendar cal;
      int daysInYear;
      switch(targetLeafLevel) {
      case 1:
         counts[0] = 2;
         break;
      case 2:
         if(srcLeafLevel == 0) {
            counts[0] = 4;
         } else {
            if(srcLeafLevel != 1) {
               throw new IllegalStateException("Unexpected src:" + srcLeafLevel + " to target:" + targetLeafLevel + " level combination:");
            }

            counts[0] = counts[1] = 2;
         }
         break;
      case 3:
         if(srcLeafLevel == 0) {
            counts[0] = 12;
         } else if(srcLeafLevel == 1) {
            counts[0] = counts[1] = 6;
         } else {
            if(srcLeafLevel != 2) {
               throw new IllegalStateException("Unexpected src:" + srcLeafLevel + " to target:" + targetLeafLevel + " level combination:");
            }

            counts[0] = counts[1] = counts[2] = counts[3] = 3;
         }
         break;
      case 4:
         cal = Calendar.getInstance();
         cal.set(1, year);
         daysInYear = cal.getActualMaximum(3);
         if(srcLeafLevel == 0) {
            counts[0] = daysInYear;
         } else if(srcLeafLevel == 1) {
            spread(daysInYear, counts);
         } else if(srcLeafLevel == 2) {
            spread(daysInYear, counts);
         } else {
            if(srcLeafLevel != 3) {
               throw new IllegalStateException("Unexpected src:" + srcLeafLevel + " to target:" + targetLeafLevel + " level combination:");
            }

            spread(daysInYear, counts);
         }
         break;
      case 5:
         cal = Calendar.getInstance();
         cal.set(1, year);
         cal.set(5, 1);
         daysInYear = cal.getActualMaximum(6);
         int[] daysInMonth = new int[cal.getActualMaximum(2) + 1];

         int i;
         for(i = 0; i < daysInMonth.length; ++i) {
            cal.set(2, i);
            daysInMonth[i] = cal.getActualMaximum(5);
         }

         if(srcLeafLevel == 0) {
            counts[0] = daysInYear;
         } else if(srcLeafLevel == 1) {
            for(i = 0; i < 6; ++i) {
               counts[0] += daysInMonth[i];
            }

            for(i = 6; i < 12; ++i) {
               counts[1] += daysInMonth[i];
            }
         } else if(srcLeafLevel == 2) {
            for(i = 0; i < 3; ++i) {
               counts[0] += daysInMonth[i];
            }

            for(i = 3; i < 6; ++i) {
               counts[1] += daysInMonth[i];
            }

            for(i = 6; i < 9; ++i) {
               counts[2] += daysInMonth[i];
            }

            for(i = 9; i < 12; ++i) {
               counts[3] += daysInMonth[i];
            }
         } else if(srcLeafLevel == 3) {
            for(i = 0; i < daysInMonth.length; ++i) {
               counts[i] = daysInMonth[i];
            }
         } else {
            if(srcLeafLevel != 4) {
               throw new IllegalStateException("Unexpected src:" + srcLeafLevel + " to target:" + targetLeafLevel + " level combination:");
            }

            for(i = 0; i < counts.length; ++i) {
               cal.set(3, i + 1);
               counts[i] = cal.getActualMaximum(7);
            }
         }
      }

      return counts;
   }

   private static void spread(int value, int[] target) {
      int toAllocate = value;
      int sumOfWeightings = target.length;

      for(int i = 0; i < target.length; ++i) {
         double weighting = 1.0D;
         int alloc = (int)Math.round((double)toAllocate * (weighting / (double)sumOfWeightings));
         target[i] = alloc;
         toAllocate -= alloc;
         sumOfWeightings = (int)((double)sumOfWeightings - weighting);
      }

   }

   public static void main(String[] args) {
      int[] values = queryElementCounts(2006, 0, 4);
      int[] arr$ = values;
      int len$ = values.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         int i = arr$[i$];
         System.out.print(i + ", ");
      }

   }

   public int[][] queryInitialLeafMapping(List<HierarchyNodeDAG> originalLeaves, int newLeafLevel) {
      int oldLeafCount = queryActiveLeafElementCount(this.getCalendarYear(), this.getLeafLevel());
      int newLeafCount = queryActiveLeafElementCount(this.getCalendarYear(), newLeafLevel);
      int maxLeafCount = Math.max(oldLeafCount, newLeafCount);
      int[][] ids = new int[maxLeafCount][3];
      int[] elementCounts;
      int idx;
      int i;
      int j;
      if(newLeafCount > oldLeafCount) {
         elementCounts = queryElementCounts(this.mCalendarYear, this.getLeafLevel(), newLeafLevel);
         idx = 0;

         for(i = 0; i < elementCounts.length; ++i) {
            j = ((HierarchyNodeDAG)originalLeaves.get(i)).getId();

            for(int j1 = 0; j1 < elementCounts[i]; ++j1) {
               ids[idx][0] = j;
               ids[idx][1] = idx;
               ids[idx][2] = elementCounts[i];
               ++idx;
            }
         }
      } else {
         if(newLeafCount >= oldLeafCount) {
            throw new IllegalStateException("New and original leaf levels are the same.");
         }

         elementCounts = queryElementCounts(this.mCalendarYear, newLeafLevel, this.getLeafLevel());
         idx = 0;

         for(i = 0; i < elementCounts.length; ++i) {
            for(j = 0; j < elementCounts[i]; ++j) {
               ids[idx][0] = ((HierarchyNodeDAG)originalLeaves.get(idx)).getId();
               ids[idx][1] = i;
               ids[idx][2] = elementCounts[i];
               ++idx;
            }
         }
      }

      return ids;
   }
}
