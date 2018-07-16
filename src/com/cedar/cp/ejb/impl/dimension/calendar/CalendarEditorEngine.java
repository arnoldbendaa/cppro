// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:29
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension.calendar;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.calendar.CalendarSpec;
import com.cedar.cp.api.dimension.calendar.CalendarYearSpec;
import com.cedar.cp.dto.cm.ChangeManagementTaskRequest;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarNodeDateFormatter;
import com.cedar.cp.dto.dimension.calendar.CalendarSpecImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarYearSpecImpl;
import com.cedar.cp.dto.dimension.calendar.event.InsertYearEvent;
import com.cedar.cp.dto.dimension.calendar.event.RemoveYearEvent;
import com.cedar.cp.dto.dimension.calendar.event.UpdateYearEvent;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.task.TaskMessageLogger;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.base.TaskReportWriter;
import com.cedar.cp.ejb.base.async.cm.ChangeManagementCheckPoint;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtEngine;
import com.cedar.cp.ejb.impl.cm.frmwrk.CalendarLeafLevelUpdate;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementType;
import com.cedar.cp.ejb.impl.dimension.CalendarSpecDAG;
import com.cedar.cp.ejb.impl.dimension.CalendarYearSpecDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAG;
import com.cedar.cp.ejb.impl.dimension.EventFactory;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyElementFeedDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyNodeDAG;
import com.cedar.cp.ejb.impl.dimension.calendar.CalendarNodeFactory;
import com.cedar.cp.ejb.impl.dimension.calendar.YearScaffold;
import com.cedar.cp.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.naming.InitialContext;

public class CalendarEditorEngine implements CalendarNodeFactory {

   private List<CalendarYearSpecImpl> mCalendarEvents = new ArrayList();
   private DimensionDAG mDimension;
   private HierarchyDAG mHierarchy;
   private int mNewKey = -1;
   private boolean mIssueCMTask;
   private DAGContext mDAGContext;
   private Log mLog = new Log(this.getClass());
   private boolean mInsideCM;
   private List<HierarchyNodeDAG> mRemovedElements = new ArrayList();
   private List<CalendarLeafLevelUpdate> mLeafLevelUpdates = new ArrayList();


   public CalendarEditorEngine(InitialContext ic, boolean insideCM) {
      this.mDAGContext = new DAGContext(ic);
      this.mInsideCM = insideCM;
   }

   public void insertCalendar(CalendarImpl calendar) throws ValidationException {
      this.mDimension = new DimensionDAG(this.mDAGContext, 3);
      this.mDimension.setVisId(calendar.getVisId());
      this.mDimension.setDescription(calendar.getDescription());
      this.mDimension.setExternalSystemRef(calendar.getExternalSystemRef());
      this.mHierarchy = new HierarchyDAG(this.mDAGContext);
      this.mHierarchy.setDimension(this.mDimension);
      this.mHierarchy.setVisId(calendar.getVisId());
      this.mHierarchy.setDescription(calendar.getDescription());
      HierarchyElementDAG root = new HierarchyElementDAG(this.mDAGContext, this.mHierarchy, this.nextDimensionKey(), calendar.getVisId(), calendar.getDescription(), -1, -1, 100);
      this.mHierarchy.setRoot(root);
      CalendarSpecImpl calSpec = (CalendarSpecImpl)calendar.getCalendarSpec();
      CalendarSpecDAG specDAG = new CalendarSpecDAG(this.mDAGContext, this.mDimension, calSpec.getYearStartMonth(), calSpec.getYearVisIdFormat(), calSpec.getHalfYearVisIdFormat(), calSpec.getQuarterVisIdFormat(), calSpec.getMonthVisIdFormat(), calSpec.getWeekVisIdFormat(), calSpec.getDayVisIdFormat(), calSpec.getYearDescrFormat(), calSpec.getHalfYearDescrFormat(), calSpec.getQuarterDescrFormat(), calSpec.getMonthDescrFormat(), calSpec.getWeekDescrFormat(), calSpec.getDayDescrFormat(), calSpec.getOpenVisId(), calSpec.getOpenDescr(), calSpec.getAdjVisId(), calSpec.getAdjDescr(), calSpec.getPeriod13VisId(), calSpec.getPeriod13Descr(), calSpec.getPeriod14VisId(), calSpec.getPeriod14Descr());
      this.mDimension.setCalendarSpec(specDAG);
      CalendarNodeDateFormatter fmt = new CalendarNodeDateFormatter();
      byte index = 0;

      for(int yearNo = calendar.getStartYear(); yearNo <= calendar.getEndYear(); ++yearNo) {
         CalendarYearSpecImpl cys = calendar.getYearSpec(yearNo);
         Calendar visCal = CalendarImpl.createCalendar(yearNo, calSpec.getYearStartMonth());
         String visId = fmt.formatDate(visCal.getTime(), 0, index, calSpec.getYearVisIdFormat());
         String descr = fmt.formatDate(visCal.getTime(), 0, index, calSpec.getYearDescrFormat());
         HierarchyNodeDAG year = this.newNode(visId, descr, cys.isLeaf(0), 0);
         CalendarYearSpecDAG calendarYearSpecDAG = new CalendarYearSpecDAG(this.mDAGContext, this.mDimension, cys);
         this.mDimension.getYearSpecs().add(calendarYearSpecDAG);
         root.add(year);
         this.createYear(cys, year, index, calSpec);
      }

   }

   private HierarchyNodeDAG createYear(CalendarYearSpecImpl cys, HierarchyNodeDAG year, int index, CalendarSpec calSpec) throws ValidationException {
      CalendarNodeDateFormatter fmt = new CalendarNodeDateFormatter();
      int yr = cys.getYear();
      int startYearStartMonth = calSpec.getYearStartMonth();
      CalendarImpl.createCalendar(yr, startYearStartMonth);
      YearScaffold scaffold = new YearScaffold(this, year, index, yr, calSpec.getYearStartMonth());
      String visId;
      String descr;
      Calendar visCal;
      if(cys.get(6)) {
         visCal = CalendarImpl.createCalendar(yr, startYearStartMonth);
         visId = fmt.formatDate(visCal.getTime(), 6, 0, calSpec.getOpenVisId());
         descr = fmt.formatDate(visCal.getTime(), 6, 0, calSpec.getOpenDescr());
         scaffold.setOpening(this.newNode(visId, descr, true, 6));
      }

      boolean weeksInYear;
      HierarchyNodeDAG[] weeksAreLeaves;
      int adjhe;
      if(cys.get(1)) {
         visCal = CalendarImpl.createCalendar(yr, startYearStartMonth);
         weeksInYear = cys.isLeaf(1);
         weeksAreLeaves = scaffold.getHalfYear();

         for(adjhe = 0; adjhe < 2; ++adjhe) {
            visId = fmt.formatDate(visCal.getTime(), 1, adjhe, calSpec.getHalfYearVisIdFormat());
            descr = fmt.formatDate(visCal.getTime(), 1, adjhe, calSpec.getHalfYearDescrFormat());
            weeksAreLeaves[adjhe] = this.newNode(visId, descr, weeksInYear, 1);
            visCal.add(2, 6);
         }
      }

      if(cys.get(2)) {
         visCal = CalendarImpl.createCalendar(yr, startYearStartMonth);
         weeksInYear = cys.isLeaf(2);
         weeksAreLeaves = scaffold.getQuarters();

         for(adjhe = 0; adjhe < 4; ++adjhe) {
            visId = fmt.formatDate(visCal.getTime(), 2, adjhe, calSpec.getQuarterVisIdFormat());
            descr = fmt.formatDate(visCal.getTime(), 2, adjhe, calSpec.getQuarterDescrFormat());
            weeksAreLeaves[adjhe] = this.newNode(visId, descr, weeksInYear, 2);
            visCal.add(2, 3);
         }
      }

      boolean var19;
      if(cys.get(3)) {
         visCal = CalendarImpl.createCalendar(yr, startYearStartMonth);
         HierarchyNodeDAG[] var18 = scaffold.getMonths();
         var19 = cys.isLeaf(3);

         for(adjhe = 0; adjhe < 12; ++adjhe) {
            visId = fmt.formatDate(visCal.getTime(), 3, adjhe, calSpec.getMonthVisIdFormat());
            descr = fmt.formatDate(visCal.getTime(), 3, adjhe, calSpec.getMonthDescrFormat());
            var18[adjhe] = this.newNode(visId, descr, var19, 3);
            visCal.add(2, 1);
         }
      }

      int var20 = CalendarImpl.calcWeeksInYear(yr, startYearStartMonth);
      var19 = cys.isLeaf(4);
      if(cys.get(4)) {
         visCal = CalendarImpl.createCalendar(yr, startYearStartMonth);
         HierarchyNodeDAG[] var23 = scaffold.getWeeks();

         for(int adjde = 0; adjde < var20; ++adjde) {
            visId = fmt.formatDate(visCal.getTime(), 4, adjde, calSpec.getWeekVisIdFormat());
            descr = fmt.formatDate(visCal.getTime(), 4, adjde, calSpec.getWeekDescrFormat());
            var23[adjde] = this.newNode(visId, descr, var19, 4);
            visCal.add(3, 1);
         }
      }

      int elemIndex;
      if(cys.get(5)) {
         visCal = CalendarImpl.createCalendar(yr, startYearStartMonth);
         adjhe = CalendarImpl.calcDaysInYear(yr, startYearStartMonth);
         HierarchyNodeDAG[] var25 = scaffold.getDays();
         boolean leaves = cys.isLeaf(5);

         for(elemIndex = 0; elemIndex < adjhe; ++elemIndex) {
            visId = fmt.formatDate(visCal.getTime(), 5, elemIndex, calSpec.getDayVisIdFormat());
            descr = fmt.formatDate(visCal.getTime(), 5, elemIndex, calSpec.getDayDescrFormat());
            var25[elemIndex] = this.newNode(visId, descr, leaves, 5);
            visCal.add(6, 1);
         }
      }

      if(cys.get(8)) {
         visCal = CalendarImpl.createCalendar(yr, startYearStartMonth);
         visCal.add(2, 11);
         visCal = CalendarImpl.createCalendar(yr, startYearStartMonth);
         visId = fmt.formatDate(visCal.getTime(), 8, 0, calSpec.getPeriod13VisId());
         descr = fmt.formatDate(visCal.getTime(), 8, 0, calSpec.getPeriod13Descr());
         scaffold.setPeriod13(this.newNode(visId, descr, true, 8));
      }

      if(cys.get(9)) {
         visCal = CalendarImpl.createCalendar(yr, startYearStartMonth);
         visCal.add(2, 11);
         visId = fmt.formatDate(visCal.getTime(), 9, 0, calSpec.getPeriod13VisId());
         descr = fmt.formatDate(visCal.getTime(), 9, 0, calSpec.getPeriod13Descr());
         scaffold.setPeriod14(this.newNode(visId, descr, true, 9));
      }

      if(cys.get(7)) {
         visCal = CalendarImpl.createCalendar(yr, startYearStartMonth);
         visCal.add(2, 11);
         visId = fmt.formatDate(visCal.getTime(), 7, 0, calSpec.getAdjVisId());
         descr = fmt.formatDate(visCal.getTime(), 7, 0, calSpec.getAdjDescr());
         scaffold.setAdjustment(this.newNode(visId, descr, true, 7));
      }

      scaffold.connectHierarchy(cys);
      HierarchyElementFeedDAG var21;
      List var22;
      DimensionElementDAG var24;
      if(cys.get(8)) {
         visCal = CalendarImpl.createCalendar(yr, startYearStartMonth);
         visCal.add(2, 11);
         var21 = (HierarchyElementFeedDAG)scaffold.getPeriod13();
         var24 = var21.getDimensionElement();
         var22 = this.getLeaves(year);
         elemIndex = var22.indexOf(var21);
         visId = fmt.formatDate(visCal.getTime(), 8, elemIndex, calSpec.getPeriod13VisId());
         descr = fmt.formatDate(visCal.getTime(), 8, elemIndex, calSpec.getPeriod13Descr());
         var24.setVisId(visId);
         var24.setDescription(descr);
      }

      if(cys.get(9)) {
         visCal = CalendarImpl.createCalendar(yr, startYearStartMonth);
         visCal.add(2, 11);
         var21 = (HierarchyElementFeedDAG)scaffold.getPeriod14();
         var24 = var21.getDimensionElement();
         var22 = this.getLeaves(year);
         elemIndex = var22.indexOf(var21);
         visId = fmt.formatDate(visCal.getTime(), 9, elemIndex, calSpec.getPeriod14VisId());
         descr = fmt.formatDate(visCal.getTime(), 9, elemIndex, calSpec.getPeriod14Descr());
         var24.setVisId(visId);
         var24.setDescription(descr);
      }

      if(cys.get(7)) {
         visCal = CalendarImpl.createCalendar(yr, startYearStartMonth);
         visCal.add(2, 11);
         var21 = (HierarchyElementFeedDAG)scaffold.getAdjustment();
         var24 = var21.getDimensionElement();
         var22 = this.getLeaves(year);
         elemIndex = var22.indexOf(var21);
         visId = fmt.formatDate(visCal.getTime(), 7, elemIndex, calSpec.getAdjVisId());
         descr = fmt.formatDate(visCal.getTime(), 7, elemIndex, calSpec.getAdjDescr());
         var24.setVisId(visId);
         var24.setDescription(descr);
      }

      return scaffold.getYear();
   }

   public CalendarImpl primeClientEditData(DimensionEVO dEVO, CalendarImpl calendar) throws ValidationException {
      DimensionDAG dDAG = new DimensionDAG(this.mDAGContext, dEVO);
      Iterator calendarSpecImpl = dDAG.getYearSpecs().iterator();

      while(calendarSpecImpl.hasNext()) {
         CalendarYearSpecDAG cSpec = (CalendarYearSpecDAG)calendarSpecImpl.next();
         calendar.getYearSpecs().add(new CalendarYearSpecImpl(cSpec.getCalendarYearSpecId(), cSpec.getCalendarYear(), cSpec.getSpecs()));
      }

      CalendarSpecImpl calendarSpecImpl1 = new CalendarSpecImpl();
      CalendarSpecDAG cSpec1 = dDAG.getCalendarSpec();
      calendarSpecImpl1.setYearStartMonth(cSpec1.getYearStartMonth());
      calendarSpecImpl1.setYearVisIdFormat(cSpec1.getYearVisIdFormat());
      calendarSpecImpl1.setHalfYearVisIdFormat(cSpec1.getHalfYearVisIdFormat());
      calendarSpecImpl1.setQuarterVisIdFormat(cSpec1.getQuarterVisIdFormat());
      calendarSpecImpl1.setMonthVisIdFormat(cSpec1.getMonthVisIdFormat());
      calendarSpecImpl1.setWeekVisIdFormat(cSpec1.getWeekVisIdFormat());
      calendarSpecImpl1.setDayVisIdFormat(cSpec1.getDayVisIdFormat());
      calendarSpecImpl1.setYearDescrFormat(cSpec1.getYearDescrFormat());
      calendarSpecImpl1.setHalfYearDescrFormat(cSpec1.getHalfYearDescrFormat());
      calendarSpecImpl1.setQuarterDescrFormat(cSpec1.getQuarterDescrFormat());
      calendarSpecImpl1.setMonthDescrFormat(cSpec1.getMonthDescrFormat());
      calendarSpecImpl1.setWeekDescrFormat(cSpec1.getWeekDescrFormat());
      calendarSpecImpl1.setDayDescrFormat(cSpec1.getDayDescrFormat());
      calendarSpecImpl1.setOpenVisId(cSpec1.getOpenVisId());
      calendarSpecImpl1.setOpenDescr(cSpec1.getOpenDescr());
      calendarSpecImpl1.setAdjVisId(cSpec1.getAdjVisId());
      calendarSpecImpl1.setAdjDescr(cSpec1.getAdjDescr());
      calendarSpecImpl1.setPeriod13VisId(cSpec1.getPeriod13VisId());
      calendarSpecImpl1.setPeriod13Descr(cSpec1.getPeriod13Descr());
      calendarSpecImpl1.setPeriod14VisId(cSpec1.getPeriod14VisId());
      calendarSpecImpl1.setPeriod14Descr(cSpec1.getPeriod14Descr());
      calendar.setCalendarSpec(calendarSpecImpl1);
      calendar.setRoot(((HierarchyDAG)dDAG.getHierarchies().values().iterator().next()).getRoot().createLightweightDAG(calendar));
      return calendar;
   }

   public void updateCalendar(DimensionEVO dEVO, HierarchyEVO hEVO, CalendarImpl calendar) throws ValidationException {
      this.mDimension = new DimensionDAG(this.mDAGContext, dEVO);
      this.mHierarchy = new HierarchyDAG(this.mDAGContext, this.mDimension, hEVO);
      ArrayList newYearSpecs = new ArrayList(calendar.getYearSpecs());
      ArrayList oldYearSpecs = new ArrayList(this.mDimension.getYearSpecs());
      int nyIndex = 0;
      int oyIndex = 0;

      while(nyIndex < newYearSpecs.size() || oyIndex < oldYearSpecs.size()) {
         CalendarYearSpecDAG oys = oyIndex < oldYearSpecs.size()?(CalendarYearSpecDAG)oldYearSpecs.get(oyIndex):null;
         CalendarYearSpecImpl nys = nyIndex < newYearSpecs.size()?(CalendarYearSpecImpl)newYearSpecs.get(nyIndex):null;
         if(oys != null && nys != null) {
            if(oys.getCalendarYear() < nys.getYear()) {
               this.removeYear(oys, nyIndex);
               ++oyIndex;
               continue;
            }

            if(oys.getCalendarYear() > nys.getYear()) {
               int newYearCount = this.yearsAdded(oldYearSpecs, oyIndex, newYearSpecs, nyIndex);

               for(int tempNYIndex = nyIndex + newYearCount - 1; tempNYIndex >= nyIndex; --tempNYIndex) {
                  nys = (CalendarYearSpecImpl)newYearSpecs.get(tempNYIndex);
                  this.addYear(nys, false, calendar.getCalendarSpec());
               }

               nyIndex += newYearCount;
               continue;
            }

            if(oys.compareYearSpec(nys)) {
               this.updateYear(oys, nyIndex, nys, calendar.getCalendarSpec());
            }

            ++nyIndex;
            ++oyIndex;
         }

         if(oys == null && nys != null) {
            this.addYear(nys, true, calendar.getCalendarSpec());
            ++nyIndex;
         }

         if(oys != null && nys == null) {
            this.removeYear(oys, nyIndex);
            ++oyIndex;
         }
      }

   }

   private int yearsAdded(List<CalendarYearSpecDAG> oldYearSpecs, int oldYearIndex, List<CalendarYearSpec> newYearSpecs, int newYearIndex) {
      int newYearCount = 0;
      CalendarYearSpecDAG oys = (CalendarYearSpecDAG)oldYearSpecs.get(oldYearIndex);

      for(int nyIndex = newYearIndex; nyIndex < newYearSpecs.size(); ++nyIndex) {
         CalendarYearSpec nys = (CalendarYearSpec)newYearSpecs.get(nyIndex);
         if(oys.getCalendarYear() <= nys.getYear()) {
            break;
         }

         ++newYearCount;
      }

      return newYearCount;
   }

   public void updateEVOs(DimensionEVO dEVO, HierarchyEVO hEVO) {
      this.mDimension.updateEVO(dEVO);
      this.mHierarchy.updateEVO(hEVO);
      this.updateEVOFromRemovedElementList(dEVO, hEVO);
   }

   public void updateYear(CalendarYearSpecDAG oys, int index, CalendarYearSpecImpl nys, CalendarSpec calSpec) throws ValidationException {
      this.mLog.info("updateYear", "year=" + oys.getCalendarYear());
      CalendarNodeDateFormatter fmt = new CalendarNodeDateFormatter();
      HierarchyNodeDAG year = this.mHierarchy.getRoot().getChildAtIndex(index);
      YearScaffold scaffold = new YearScaffold(this, oys, year, index, oys.getCalendarYear(), calSpec.getYearStartMonth());
      int yr = oys.getCalendarYear();
      int yearStartMonth = calSpec.getYearStartMonth();
      List originalLeaves = scaffold.getLeaves(false);
      int oldLeafLevel = oys.getLeafLevel();
      int newLeafLevel = nys.getLeafLevel();
      scaffold.disconnectHierarchy();
      String visId;
      String descr;
      Calendar ids;
      if(oys.getSpec(6) != nys.get(6)) {
         if(nys.get(6)) {
            ids = CalendarImpl.createCalendar(yr, yearStartMonth);
            visId = fmt.formatDate(ids.getTime(), 6, 0, calSpec.getOpenVisId());
            descr = fmt.formatDate(ids.getTime(), 6, 0, calSpec.getOpenDescr());
            scaffold.setOpening(this.newNode(visId, descr, true, 6));
         } else {
            this.registerRemovedElement(scaffold.getOpening());
            scaffold.setOpening((HierarchyNodeDAG)null);
         }
      }

      int adjde;
      int cllu;
      HierarchyNodeDAG leaves;
      HierarchyNodeDAG[] var22;
      if(oys.getSpec(1) != nys.get(1)) {
         if(nys.get(1)) {
            ids = CalendarImpl.createCalendar(yr, yearStartMonth);

            for(cllu = 0; cllu < 2; ++cllu) {
               visId = fmt.formatDate(ids.getTime(), 1, cllu, calSpec.getHalfYearVisIdFormat());
               descr = fmt.formatDate(ids.getTime(), 1, cllu, calSpec.getHalfYearDescrFormat());
               scaffold.getHalfYear()[cllu] = this.newNode(visId, descr, nys.isLeaf(1), 1);
               ids.add(2, 6);
            }
         } else {
            var22 = scaffold.getHalfYear();
            cllu = var22.length;

            for(adjde = 0; adjde < cllu; ++adjde) {
               leaves = var22[adjde];
               this.registerRemovedElement(leaves);
            }

            Arrays.fill(scaffold.getHalfYear(), (Object)null);
         }
      }

      Calendar var23;
      int var29;
      if(oys.getSpec(2) != nys.get(2)) {
         if(nys.get(2)) {
            var22 = scaffold.getQuarters();
            boolean var26 = nys.isLeaf(2);
            var23 = CalendarImpl.createCalendar(yr, yearStartMonth);

            for(var29 = 0; var29 < 4; ++var29) {
               visId = fmt.formatDate(var23.getTime(), 2, var29, calSpec.getQuarterVisIdFormat());
               descr = fmt.formatDate(var23.getTime(), 2, var29, calSpec.getQuarterDescrFormat());
               var22[var29] = this.newNode(visId, descr, var26, 2);
               var23.add(2, 3);
            }
         } else {
            var22 = scaffold.getQuarters();
            cllu = var22.length;

            for(adjde = 0; adjde < cllu; ++adjde) {
               leaves = var22[adjde];
               this.registerRemovedElement(leaves);
            }

            Arrays.fill(scaffold.getQuarters(), (Object)null);
         }
      }

      boolean var21;
      Calendar var24;
      HierarchyNodeDAG[] var28;
      if(oys.getSpec(3) != nys.get(3)) {
         if(nys.get(3)) {
            var21 = nys.isLeaf(3);
            var24 = CalendarImpl.createCalendar(yr, yearStartMonth);
            var28 = scaffold.getMonths();

            for(var29 = 0; var29 < 12; ++var29) {
               visId = fmt.formatDate(var24.getTime(), 3, var29, calSpec.getMonthVisIdFormat());
               descr = fmt.formatDate(var24.getTime(), 3, var29, calSpec.getMonthDescrFormat());
               var28[var29] = this.newNode(visId, descr, var21, 3);
               var24.add(2, 1);
            }
         } else {
            var22 = scaffold.getMonths();
            cllu = var22.length;

            for(adjde = 0; adjde < cllu; ++adjde) {
               leaves = var22[adjde];
               this.registerRemovedElement(leaves);
            }

            Arrays.fill(scaffold.getMonths(), (Object)null);
         }
      }

      int elemIndex;
      if(oys.getSpec(4) != nys.get(4)) {
         if(nys.get(4)) {
            int var25 = CalendarImpl.calcWeeksInYear(yr, yearStartMonth);
            var24 = CalendarImpl.createCalendar(yr, yearStartMonth);
            var28 = scaffold.getWeeks();
            boolean var30 = nys.isLeaf(4);

            for(elemIndex = 0; elemIndex < var25; ++elemIndex) {
               visId = fmt.formatDate(var24.getTime(), 4, elemIndex, calSpec.getWeekVisIdFormat());
               descr = fmt.formatDate(var24.getTime(), 4, elemIndex, calSpec.getWeekDescrFormat());
               var28[elemIndex] = this.newNode(visId, descr, var30, 4);
               var24.add(3, 1);
            }
         } else {
            var22 = scaffold.getWeeks();
            cllu = var22.length;

            for(adjde = 0; adjde < cllu; ++adjde) {
               leaves = var22[adjde];
               this.registerRemovedElement(leaves);
            }

            Arrays.fill(scaffold.getWeeks(), (Object)null);
         }
      }

      if(oys.getSpec(5) != nys.get(5)) {
         var21 = nys.isLeaf(5);
         cllu = CalendarImpl.calcDaysInYear(yr, yearStartMonth);
         if(nys.get(5)) {
            var23 = CalendarImpl.createCalendar(yr, yearStartMonth);
            var23.setMinimalDaysInFirstWeek(1);
            HierarchyNodeDAG[] var33 = scaffold.getDays();

            for(elemIndex = 0; elemIndex < cllu; ++elemIndex) {
               visId = fmt.formatDate(var23.getTime(), 5, elemIndex, calSpec.getDayVisIdFormat());
               descr = fmt.formatDate(var23.getTime(), 5, elemIndex, calSpec.getDayDescrFormat());
               var33[elemIndex] = this.newNode(visId, descr, var21, 5);
               var23.add(6, 1);
            }
         } else {
            var28 = scaffold.getDays();
            var29 = var28.length;

            for(elemIndex = 0; elemIndex < var29; ++elemIndex) {
               HierarchyNodeDAG node = var28[elemIndex];
               this.registerRemovedElement(node);
            }

            Arrays.fill(scaffold.getDays(), (Object)null);
         }
      }

      if(oys.getSpec(8) != nys.get(8)) {
         if(nys.get(8)) {
            ids = CalendarImpl.createCalendar(yr, yearStartMonth);
            ids.add(2, 11);
            visId = fmt.formatDate(ids.getTime(), 8, 0, calSpec.getPeriod13VisId());
            descr = fmt.formatDate(ids.getTime(), 8, 0, calSpec.getPeriod13Descr());
            scaffold.setPeriod13(this.newNode(visId, descr, true, 8));
         } else {
            this.registerRemovedElement(scaffold.getPeriod13());
            scaffold.setPeriod13((HierarchyNodeDAG)null);
         }
      }

      if(oys.getSpec(9) != nys.get(9)) {
         if(nys.get(9)) {
            ids = CalendarImpl.createCalendar(yr, yearStartMonth);
            ids.add(2, 11);
            visId = fmt.formatDate(ids.getTime(), 9, 0, calSpec.getPeriod14VisId());
            descr = fmt.formatDate(ids.getTime(), 9, 0, calSpec.getPeriod14Descr());
            scaffold.setPeriod14(this.newNode(visId, descr, true, 9));
         } else {
            this.registerRemovedElement(scaffold.getPeriod14());
            scaffold.setPeriod14((HierarchyNodeDAG)null);
         }
      }

      if(oys.getSpec(7) != nys.get(7)) {
         if(nys.get(7)) {
            ids = CalendarImpl.createCalendar(yr, yearStartMonth);
            ids.add(2, 11);
            visId = fmt.formatDate(ids.getTime(), 7, 0, calSpec.getAdjVisId());
            descr = fmt.formatDate(ids.getTime(), 7, 0, calSpec.getAdjDescr());
            scaffold.setAdjustment(this.newNode(visId, descr, true, 7));
         } else {
            this.registerRemovedElement(scaffold.getAdjustment());
            scaffold.setAdjustment((HierarchyNodeDAG)null);
         }
      }

      scaffold.connectHierarchy(nys);
      HierarchyElementFeedDAG var27;
      DimensionElementDAG var34;
      List var35;
      if(nys.get(8)) {
         ids = CalendarImpl.createCalendar(yr, yearStartMonth);
         ids.add(2, 11);
         var27 = (HierarchyElementFeedDAG)scaffold.getPeriod13();
         var34 = var27.getDimensionElement();
         var35 = this.getLeaves(year);
         elemIndex = var35.indexOf(var27);
         visId = fmt.formatDate(ids.getTime(), 8, elemIndex, calSpec.getPeriod13VisId());
         descr = fmt.formatDate(ids.getTime(), 8, elemIndex, calSpec.getPeriod13Descr());
         var34.setVisId(visId);
         var34.setDescription(descr);
      }

      if(nys.get(9)) {
         ids = CalendarImpl.createCalendar(yr, yearStartMonth);
         ids.add(2, 11);
         var27 = (HierarchyElementFeedDAG)scaffold.getPeriod14();
         var34 = var27.getDimensionElement();
         var35 = this.getLeaves(year);
         elemIndex = var35.indexOf(var27);
         visId = fmt.formatDate(ids.getTime(), 9, elemIndex, calSpec.getPeriod14VisId());
         descr = fmt.formatDate(ids.getTime(), 9, elemIndex, calSpec.getPeriod14Descr());
         var34.setVisId(visId);
         var34.setDescription(descr);
      }

      if(nys.get(7)) {
         ids = CalendarImpl.createCalendar(yr, yearStartMonth);
         ids.add(2, 11);
         var27 = (HierarchyElementFeedDAG)scaffold.getAdjustment();
         var34 = var27.getDimensionElement();
         var35 = this.getLeaves(year);
         elemIndex = var35.indexOf(var27);
         visId = fmt.formatDate(ids.getTime(), 7, elemIndex, calSpec.getAdjVisId());
         descr = fmt.formatDate(ids.getTime(), 7, elemIndex, calSpec.getAdjDescr());
         var34.setVisId(visId);
         var34.setDescription(descr);
      }

      if(this.mInsideCM) {
         if(oldLeafLevel != newLeafLevel) {
            int[][] var31 = oys.queryInitialLeafMapping(originalLeaves, nys.getLeafLevel());
            CalendarLeafLevelUpdate var32 = new CalendarLeafLevelUpdate(oldLeafLevel, newLeafLevel, nys.getYear(), var31);
            this.registerLeafLevelUpdate(var32);
         }
      } else {
         this.addCalendarYearEvent(new UpdateYearEvent(nys));
      }

      oys.setSpec(0, true);
      oys.setSpec(6, nys.get(6));
      oys.setSpec(1, nys.get(1));
      oys.setSpec(2, nys.get(2));
      oys.setSpec(3, nys.get(3));
      oys.setSpec(4, nys.get(4));
      oys.setSpec(5, nys.get(5));
      oys.setSpec(7, nys.get(7));
      oys.setSpec(8, nys.get(8));
      oys.setSpec(9, nys.get(9));
   }

   public void registerNewYearNode(HierarchyNodeDAG oldYear, HierarchyNodeDAG newYear, int index) throws ValidationException {
      this.mHierarchy.getRoot().remove(oldYear);
      this.mHierarchy.getRoot().add(index, newYear);
   }

   public void removeYear(CalendarYearSpecDAG oys, int index) {
      this.mLog.info("removeYear", "year=" + oys.getCalendarYear());
      HierarchyNodeDAG year = this.mHierarchy.getRoot().getChildAtIndex(index);
      HierarchyElementDAG root = this.mHierarchy.getRoot();
      root.remove(year);
      this.registerTreeRemoved(year);
      this.mDimension.getYearSpecs().remove(oys);
      if(this.mInsideCM) {
         CalendarLeafLevelUpdate cllu = new CalendarLeafLevelUpdate(oys.getLeafLevel(), -1, oys.getCalendarYear(), (int[][])null);
         this.registerLeafLevelUpdate(cllu);
      } else {
         this.addCalendarYearEvent(new RemoveYearEvent(oys.getCalendarYear()));
      }

   }

   public void addYear(CalendarYearSpecImpl nys, boolean end, CalendarSpec calSpec) throws ValidationException {
      this.mLog.info("addYear", "year=" + nys.getYear());
      HierarchyElementDAG root = this.mHierarchy.getRoot();
      int index = end?root.getChildren().size():0;
      Calendar visCal = CalendarImpl.createCalendar(nys.getYear(), calSpec.getYearStartMonth());
      CalendarNodeDateFormatter fmt = new CalendarNodeDateFormatter();
      String visId = fmt.formatDate(visCal.getTime(), 0, index, calSpec.getYearVisIdFormat());
      String descr = fmt.formatDate(visCal.getTime(), 0, index, calSpec.getYearDescrFormat());
      HierarchyNodeDAG year = this.newNode(visId, descr, nys.isLeaf(0), 0);
      this.createYear(nys, year, index, calSpec);
      root.add(index, year);
      this.mDimension.getYearSpecs().add(end?this.mDimension.getYearSpecs().size():0, new CalendarYearSpecDAG(this.mDAGContext, this.mDimension, nys));
      this.addCalendarYearEvent(new InsertYearEvent(nys));
   }

   public void primeLeafLevelUpdates() {
      Iterator i$ = this.getLeafLevelUpdates().iterator();

      while(i$.hasNext()) {
         CalendarLeafLevelUpdate cllu = (CalendarLeafLevelUpdate)i$.next();
         int year = cllu.getYear();
         CalendarYearSpecDAG cypd = this.mDimension.getCalendarYearSpec(year);
         if(cypd != null) {
            int index = this.mDimension.getYearSpecs().indexOf(cypd);
            HierarchyNodeDAG root = this.mHierarchy.getRoot().getChildAtIndex(index);
            List leaves = this.queryLeaves(root);
            int[][] ids = cllu.getIds();

            for(int i = 0; i < ids.length; ++i) {
               ids[i][1] = ((HierarchyNodeDAG)leaves.get(ids[i][1])).getId();
            }
         }
      }

   }

   private List<HierarchyNodeDAG> queryLeaves(HierarchyNodeDAG start) {
      ArrayList leaves = new ArrayList();
      if(start.isLeaf()) {
         leaves.add(start);
      } else {
         Iterator i$ = start.getChildren().iterator();

         while(i$.hasNext()) {
            HierarchyNodeDAG child = (HierarchyNodeDAG)i$.next();
            leaves.addAll(this.queryLeaves(child));
         }
      }

      return leaves;
   }

   public int submitChangeManagementRequest(String action, String visId, String description, ModelRefImpl modelRef, int userId, boolean submit) throws ValidationException, Exception {
      EventFactory eventFactory = new EventFactory();
      ChangeManagementType cm = eventFactory.createCalendarCMRequest(action, visId, description, this.getSavedCalendarEvents());
      ChangeMgmtEngine engine = new ChangeMgmtEngine(this.mDAGContext.getInitialContext(), (ChangeManagementTaskRequest)null, (ChangeManagementCheckPoint)null, (TaskMessageLogger)null, (TaskReportWriter)null);
      engine.registerUpdateRequest(cm);
      return submit?engine.issueUpdateTask(new UserPK(userId), modelRef):-1;
   }

   private List<CalendarYearSpecImpl> getSavedCalendarEvents() {
      return this.mCalendarEvents;
   }

   public boolean hasCalendarUpdateEvents() {
      return !this.getSavedCalendarEvents().isEmpty();
   }

   private void addCalendarYearEvent(CalendarYearSpecImpl event) {
      this.mCalendarEvents.add(event);
   }

   public HierarchyNodeDAG newNode(String visId, String description, int calElemType) throws ValidationException {
      return this.newNode(visId, description, false, calElemType);
   }

   public HierarchyNodeDAG newNode(String visId, String description, boolean leaf, int calElemType) throws ValidationException {
      if(leaf) {
         DimensionElementDAG deDAG = new DimensionElementDAG(this.mDAGContext, this.mDimension, this.nextDimensionKey(), visId, description, -1, -1, false, false);
         this.mDimension.addDimensionElement(deDAG);
         return new HierarchyElementFeedDAG(this.mDAGContext, (HierarchyElementDAG)null, deDAG, 0, (HierarchyElementDAG)null, -1, calElemType);
      } else {
         return new HierarchyElementDAG(this.mDAGContext, this.mHierarchy, this.nextDimensionKey(), visId, description, -1, -1, calElemType);
      }
   }

   private List<HierarchyElementFeedDAG> getLeaves(HierarchyNodeDAG node) {
      return this.getLeaves(node, new ArrayList());
   }

   private List<HierarchyElementFeedDAG> getLeaves(HierarchyNodeDAG node, List<HierarchyElementFeedDAG> leaves) {
      if(node.isLeaf()) {
         if(node.getCalElemType() != 6 && node.getCalElemType() != 7) {
            leaves.add((HierarchyElementFeedDAG)node);
         }
      } else if(node.getChildren() != null) {
         Iterator i = node.getChildren().iterator();

         while(i.hasNext()) {
            HierarchyNodeDAG child = (HierarchyNodeDAG)i.next();
            this.getLeaves(child, leaves);
         }
      }

      return leaves;
   }

   public DimensionDAG getDimension() {
      return this.mDimension;
   }

   public void setDimension(DimensionDAG dimension) {
      this.mDimension = dimension;
   }

   public HierarchyDAG getHierarchy() {
      return this.mHierarchy;
   }

   public void setHierarchy(HierarchyDAG hierarchy) {
      this.mHierarchy = hierarchy;
   }

   private int nextDimensionKey() {
      return --this.mNewKey;
   }

   public DAGContext getDAGContext() {
      return this.mDAGContext;
   }

   private void updateEVOFromRemovedElementList(DimensionEVO dEVO, HierarchyEVO hEVO) {
      Iterator i$ = this.getRemovedElements().iterator();

      while(i$.hasNext()) {
         HierarchyNodeDAG node = (HierarchyNodeDAG)i$.next();
         if(node != null) {
            if(!node.isFeeder()) {
               HierarchyElementDAG element = (HierarchyElementDAG)node;
               element.removeFromEVO(hEVO);
            } else {
               HierarchyElementFeedDAG element1 = (HierarchyElementFeedDAG)node;
               DimensionElementDAG de = element1.getDimensionElement();
               element1.removeFromEVO(hEVO);
               de.removeFromEVO(dEVO);
            }
         }
      }

      this.mRemovedElements.clear();
   }

   public void registerTreeRemoved(HierarchyNodeDAG element) {
      if(element != null) {
         this.registerRemovedElement(element);
         Iterator i$ = element.getChildren().iterator();

         while(i$.hasNext()) {
            HierarchyNodeDAG child = (HierarchyNodeDAG)i$.next();
            this.registerTreeRemoved(child);
         }

      }
   }

   public List<CalendarLeafLevelUpdate> getLeafLevelUpdates() {
      return this.mLeafLevelUpdates;
   }

   private void registerLeafLevelUpdate(CalendarLeafLevelUpdate cllu) {
      this.mLeafLevelUpdates.add(cllu);
   }

   public void registerRemovedElement(HierarchyNodeDAG element) {
      this.mRemovedElements.add(element);
   }

   private List<HierarchyNodeDAG> getRemovedElements() {
      return this.mRemovedElements;
   }
}
