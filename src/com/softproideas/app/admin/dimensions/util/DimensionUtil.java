/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/
package com.softproideas.app.admin.dimensions.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cedar.cp.dto.dimension.DimensionElementCK;
import com.cedar.cp.dto.dimension.DimensionElementEvent;
import com.cedar.cp.dto.dimension.DimensionElementPK;
import com.cedar.cp.dto.dimension.DimensionImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarYearSpecImpl;
import com.cedar.cp.dto.dimension.calendar.event.InsertYearEvent;
import com.cedar.cp.dto.dimension.calendar.event.RemoveYearEvent;
import com.cedar.cp.dto.dimension.calendar.event.UpdateYearEvent;
import com.cedar.cp.dto.dimension.event.InsertDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.RemoveDimensionElementEvent;
import com.cedar.cp.dto.dimension.event.UpdateDimensionElementEvent;
import com.softproideas.app.admin.dimensions.calendar.model.CalendarDetailsDTO;
import com.softproideas.app.admin.dimensions.calendar.model.CalendarElementDTO;
import com.softproideas.app.admin.dimensions.model.DimensionDetailsDTO;
import com.softproideas.app.admin.dimensions.model.DimensionElementDTO;

public class DimensionUtil {

    /**
     * Prepare dimension elements to save (update, insert or delete) with Dimension.
     * Dimension elements are inserted, updated or deleted all by the same request.
     * We prepare ArrayList with elements with type: InsertDimensionElementEvent, UpdateDimensionElementEvent or RemoveDimensionElementEvent.
     */
    public static HashMap<String, ArrayList<DimensionElementEvent>> prepareDimensionElementsToSave(DimensionImpl dimensionImpl, DimensionDetailsDTO dimension) {
        HashMap<String, ArrayList<DimensionElementEvent>> clientEvents = new HashMap<String, ArrayList<DimensionElementEvent>>();
        ArrayList<DimensionElementEvent> removeEvents = new ArrayList<DimensionElementEvent>();
        ArrayList<DimensionElementEvent> updateEvents = new ArrayList<DimensionElementEvent>();
        ArrayList<DimensionElementEvent> insertEvents = new ArrayList<DimensionElementEvent>();
        
        List<DimensionElementDTO> dimensionElementsDTOList = dimension.getDimensionElements();
        for (DimensionElementDTO dimensionElement: dimensionElementsDTOList) {
            String operationOnDAO = dimensionElement.getOperation();
            if (operationOnDAO != null && operationOnDAO.equals("insert")) {
                InsertDimensionElementEvent insertDimensionElementEvent = new InsertDimensionElementEvent((Object) null, dimensionElement.getDimensionElementVisId(), dimensionElement.getDimensionElementDescription(), dimensionElement.getCreditDebit(), dimensionElement.getAugCreditDebit(), dimensionElement.isNotPlannable(), dimensionElement.isDisabled(), dimensionElement.isNullElement());
                insertEvents.add(insertDimensionElementEvent);// clientEvents.add(insertDimensionElementEvent);
            } else if (operationOnDAO != null && operationOnDAO.equals("update")) {
                DimensionPK dimensionPK = (DimensionPK) dimensionImpl.getPrimaryKey();
                DimensionElementPK dimensionElementPK = new DimensionElementPK(dimensionElement.getDimensionElementId());
                DimensionElementCK dimensionElementCK = new DimensionElementCK(dimensionPK, dimensionElementPK);
                UpdateDimensionElementEvent updateDimensionElementEvent = new UpdateDimensionElementEvent(dimensionElementCK, (String) null, dimensionElement.getDimensionElementVisId(), dimensionElement.getDimensionElementDescription(), Integer.valueOf(dimensionElement.getCreditDebit()), Integer.valueOf(dimensionElement.getAugCreditDebit()), Boolean.valueOf(dimensionElement.isNotPlannable()), Boolean.valueOf(dimensionElement.isDisabled()), Boolean.valueOf(dimensionElement.isNullElement()));
                updateEvents.add(updateDimensionElementEvent);// clientEvents.add(updateDimensionElementEvent);
            } else if (operationOnDAO != null && operationOnDAO.equals("remove")) {
                DimensionPK dimensionPK = (DimensionPK) dimensionImpl.getPrimaryKey();
                DimensionElementPK dimensionElementPK = new DimensionElementPK(dimensionElement.getDimensionElementId());
                DimensionElementCK dimensionElementCK = new DimensionElementCK(dimensionPK, dimensionElementPK);
                RemoveDimensionElementEvent removeDimensionElementEvent = new RemoveDimensionElementEvent(dimensionElementCK, dimensionElement.getDimensionElementVisId());
                removeEvents.add(removeDimensionElementEvent);// clientEvents.add(removeDimensionElementEvent);
            }
        }
        clientEvents.put("remove", removeEvents);
        clientEvents.put("update", updateEvents);
        clientEvents.put("insert", insertEvents);
        return clientEvents;
    }

    public static ArrayList<CalendarYearSpecImpl> prepareCalendarYearsToSave(CalendarImpl dimensionImpl, CalendarDetailsDTO calendar) {
        ArrayList<CalendarYearSpecImpl> clientEvents = new ArrayList<CalendarYearSpecImpl>();
        List<CalendarElementDTO> calendarYearsDTOList = calendar.getYears();
        for (CalendarElementDTO calendarYear: calendarYearsDTOList) {
            String operationOnDAO = calendarYear.getOperation();
            if (operationOnDAO != null && operationOnDAO.equals("remove")) {
                RemoveYearEvent removeYearEvent = new RemoveYearEvent(calendarYear.getYear());
                clientEvents.add(removeYearEvent);
            } else if (operationOnDAO != null && operationOnDAO.equals("insert")) {
                boolean[] newSpecArray = calendarYear.getSpec();
                CalendarYearSpecImpl calendarYearSpecImpl = new CalendarYearSpecImpl(calendarYear.getYearId(), calendarYear.getYear(), newSpecArray);
                InsertYearEvent insertYearEvent = new InsertYearEvent(calendarYearSpecImpl);
                clientEvents.add(insertYearEvent);
            } else if (operationOnDAO != null && operationOnDAO.equals("update")) {
                boolean[] newSpecArray = calendarYear.getSpec();
                CalendarYearSpecImpl calendarYearSpecImpl = new CalendarYearSpecImpl(calendarYear.getYearId(), calendarYear.getYear(), newSpecArray);
                UpdateYearEvent updateYearEvent = new UpdateYearEvent(calendarYearSpecImpl);
                clientEvents.add(updateYearEvent);
            }
        }
        return clientEvents;
    }
}
