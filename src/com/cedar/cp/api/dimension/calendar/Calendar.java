// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension.calendar;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.calendar.CalendarSpec;
import com.cedar.cp.api.dimension.calendar.CalendarYearSpec;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.Serializable;
import java.util.List;
import javax.swing.tree.TreeModel;

public interface Calendar extends Serializable, XMLWritable {

   List<CalendarYearSpec> getYearSpecs();

   List<TreeModel> getYearModels();

   String getVisId();

   String getDescription();

   CalendarSpec getCalendarSpec();

   CalendarYearSpec getYearSpec(int var1);

   TreeModel getYearModel(int var1);

   boolean isChangeManagementRequestsPending();

   boolean isSubmitChangeManagementRequest();

   boolean isChangeManagementUpdateRequired();

   Integer getExternalSystemRef();

   ModelRef getModel();

   int getStartYear();

   int getEndYear();

   List<EntityRef> getLeavesForYear(int var1) throws ValidationException;

   boolean isNew();

   String getYearVisId(int var1);
}
