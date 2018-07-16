// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension.calendar;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.calendar.CalendarEditor;
import com.cedar.cp.api.dimension.calendar.CalendarPrefsEditor;
import com.cedar.cp.api.dimension.calendar.CalendarYearSpec;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.dimension.CalendarHierarchyElementImpl;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarEditorUtils;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarLeafElementKey;
import com.cedar.cp.dto.dimension.calendar.CalendarSpecImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarYearSpecImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.dimension.calendar.CalendarPrefsEditorImpl;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public class CalendarEditorImpl extends BusinessEditorImpl implements CalendarEditor, SubBusinessEditorOwner {

   private CalendarEditorUtils mCalendarEditorUtils = new CalendarEditorUtils();
   private transient Map<Integer, CalendarYearSpecImpl> mDeletedYears;
   private CalendarImpl mCalendar;


   public CalendarEditorImpl(BusinessSessionImpl session, CalendarImpl calendar) {
      super(session);
      this.mCalendar = calendar;
   }

   public void saveModifications() throws ValidationException {}

   public void setDimensionVisId(String dimensionVisId) throws ValidationException {}

   public void setDimensionDescription(String dimensionDescription) {}

   public void setHierarchyVisId(String hierarchyVisId) throws ValidationException {
      this.mCalendar.setVisId(hierarchyVisId);
      this.setContentModified();
   }

   public void setHierarchyDescription(String hierarchyDescription) {
      this.mCalendar.setDescription(hierarchyDescription);
      this.setContentModified();
   }

   public void rebuildYears() throws ValidationException {
      int index = 0;

      for(int year = this.mCalendar.getStartYear(); year <= this.mCalendar.getEndYear(); ++year) {
         this.rebuildYear(index);
         ++index;
      }

   }

   public void setExternalSystemRef(Integer newExternalSystemRef) throws ValidationException {
      this.setContentModified();
      this.mCalendar.setExternalSystemRef(newExternalSystemRef);
   }

   public void addYear(boolean end) throws ValidationException {
      int newYear = this.getYearForStartOrEndOfCal(end);
      CalendarYearSpecImpl cys = (CalendarYearSpecImpl)this.getDeletedYears().get(Integer.valueOf(newYear));
      if(cys == null) {
         cys = new CalendarYearSpecImpl(this.nextKeyId(), newYear);
      } else {
         this.getDeletedYears().remove(Integer.valueOf(cys.getYear()));
      }

      this.mCalendar.getYearSpecs().add(end?this.mCalendar.getYearSpecs().size():0, cys);
      HierarchyElementImpl year = this.newNode(String.valueOf(cys.getYear()), String.valueOf(cys.getYear()), 0);
      this.mCalendar.getYearModels().add(end?this.mCalendar.getYearModels().size():0, new DefaultTreeModel(year));
      this.rebuildYear(end?this.mCalendar.getYearSpecs().size() - 1:0);
      this.setContentModified();
      if(!this.mCalendar.isNew() && this.mCalendar.getModel() != null) {
         this.mCalendar.setChangeManagementUpdateRequired(true);
      }

   }

   public void removeYear(boolean end) throws ValidationException {
      List years = this.mCalendar.getYearSpecs();
      if(years.isEmpty()) {
         throw new ValidationException("No years to remove!");
      } else {
         CalendarYearSpec removedYear = (CalendarYearSpec)years.remove(end?years.size() - 1:0);
         this.getDeletedYears().put(Integer.valueOf(removedYear.getYear()), (CalendarYearSpecImpl)removedYear);
         List tms = this.mCalendar.getYearModels();
         tms.remove(end?tms.size() - 1:0);
         this.setContentModified();
         if(!this.mCalendar.isNew() && this.mCalendar.getModel() != null) {
            this.mCalendar.setChangeManagementUpdateRequired(true);
         }

      }
   }

   private int getYearForStartOrEndOfCal(boolean end) {
      if(this.mCalendar.getYearSpecs().isEmpty()) {
         return Calendar.getInstance().get(1);
      } else {
         CalendarYearSpec cal = (CalendarYearSpec)this.mCalendar.getYearSpecs().get(end?this.mCalendar.getYearSpecs().size() - 1:0);
         return cal.getYear() + (end?1:-1);
      }
   }

   public void setDetail(int year, int calendarType, boolean required) throws ValidationException {
      CalendarYearSpecImpl cys = this.mCalendar.getYearSpec(year);
      if(cys == null) {
         throw new ValidationException("Year " + year + " not found.");
      } else {
         if(cys.get(calendarType) != required) {
            cys.set(calendarType, required);
            this.rebuildYear(this.mCalendar.getYearSpecs().indexOf(cys));
            this.setContentModified();
            if(!this.mCalendar.isNew() && this.mCalendar.getModel() != null) {
               this.mCalendar.setChangeManagementUpdateRequired(true);
            }
         }

      }
   }

   private void rebuildYear(int index) throws ValidationException {
      CalendarYearSpec cys = (CalendarYearSpec)this.mCalendar.getYearSpecs().get(index);
      HierarchyElementImpl year;
      if(this.mCalendar.getYearModels().size() <= index) {
         year = this.newNode(String.valueOf(cys.getYear()), String.valueOf(cys.getYear()), 0);
         this.mCalendar.getYearModels().add(new DefaultTreeModel(year));
      } else {
         TreeModel tm = (TreeModel)this.mCalendar.getYearModels().get(index);
         year = (HierarchyElementImpl)tm.getRoot();

         while(year.getChildCount() > 0) {
            year.removeChildElement(year.getChildAt(0));
         }
      }

      this.mCalendarEditorUtils.updateYear(year, (CalendarYearSpecImpl)cys, this.mCalendar.getCalendarSpecImpl(), this.getYearStartMonth());
      ((DefaultTreeModel)this.mCalendar.getYearModels().get(index)).nodeStructureChanged(year);
   }

   private int nextKeyId() {
      return this.mCalendarEditorUtils.nextKeyId();
   }

   public com.cedar.cp.api.dimension.calendar.Calendar getCalendar() {
      if(this.mCalendar.getYearModels() == null || this.mCalendar.getYearModels().isEmpty()) {
         try {
            this.rebuildYears();
         } catch (ValidationException var2) {
            throw new IllegalStateException("Failed to rebuildYears:", var2);
         }
      }

      return this.mCalendar;
   }

   public void setSubmitChangeManagementRequest(boolean b) {
      this.mCalendar.setSubmitChangeManagementRequest(b);
   }

   private Map<Integer, CalendarYearSpecImpl> getDeletedYears() {
      if(this.mDeletedYears == null) {
         this.mDeletedYears = new HashMap();
      }

      return this.mDeletedYears;
   }

   private int getYearStartMonth() {
      return this.mCalendar.getCalendarSpec().getYearStartMonth();
   }

   void setCalendarSpec(CalendarSpecImpl calendarSpec) {
      this.mCalendar.setCalendarSpec(calendarSpec);

      try {
         this.rebuildYears();
         this.setContentModified();
      } catch (ValidationException var3) {
         throw new IllegalStateException("Failed to rebuild years:", var3);
      }
   }

   public List<EntityRef> getLeafElementRefs(int year) {
      ArrayList leaves = new ArrayList();
      com.cedar.cp.api.dimension.calendar.Calendar calendar = this.getCalendar();
      TreeModel tm = calendar.getYearModel(year);
      TreeNode yearNode = (TreeNode)tm.getRoot();
      List rawLeaves = this.getLeaves(yearNode, new ArrayList());

      for(int i = 0; i < rawLeaves.size(); ++i) {
         HierarchyElementImpl he = (HierarchyElementImpl)rawLeaves.get(i);
         leaves.add(new EntityRefImpl(new CalendarLeafElementKey(year, i), he.getVisId() + " - " + he.getDescription()));
      }

      return leaves;
   }

   public List<CalendarHierarchyElementImpl> getLeafElements(int year) {
      com.cedar.cp.api.dimension.calendar.Calendar calendar = this.getCalendar();
      TreeModel tm = calendar.getYearModel(year);
      TreeNode yearNode = (TreeNode)tm.getRoot();
      return this.getLeaves(yearNode, new ArrayList());
   }

   private List getLeaves(TreeNode node, List leaves) {
      if(node.isLeaf()) {
         leaves.add(node);
      } else {
         Enumeration children = node.children();

         while(children.hasMoreElements()) {
            this.getLeaves((TreeNode)children.nextElement(), leaves);
         }
      }

      return leaves;
   }

   public CalendarPrefsEditor getPrefsEditor() {
      return new CalendarPrefsEditorImpl(this.getBusinessSession(), this, this.mCalendar.getCalendarSpecImpl());
   }

   public void removeSubBusinessEditor(SubBusinessEditor editor) throws CPException {}

   public HierarchyElementImpl newNode(String visId, String description, int calElemType) {
      return this.mCalendarEditorUtils.newNode(visId, description, calElemType);
   }
}
