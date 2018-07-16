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
package com.softproideas.app.admin.dimensions.calendar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.dimensions.calendar.model.CalendarDTO;
import com.softproideas.app.admin.dimensions.calendar.model.CalendarDetailsDTO;
import com.softproideas.app.admin.dimensions.calendar.service.CalendarService;
import com.softproideas.app.reviewbudget.dimension.model.DimensionDTO;
import com.softproideas.commons.model.ResponseMessage;

@RequestMapping(value = "/dimensions/calendars")
@Controller
public class CalendarController {

    @Autowired
    CalendarService calendarService;

    /**
     * Get all Calendars from database.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<CalendarDTO> browseCalendars() throws Exception {
        return calendarService.browseCalendars();
    }

    /**
     * Insert Dimension Calendar
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage createCalendar(@RequestBody CalendarDetailsDTO calendar) throws Exception {
        ResponseMessage responseMessage = calendarService.save(calendar);
        return responseMessage;
    }

    /**
     * Update Dimension Calendar
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMessage updateCalendar(@RequestBody CalendarDetailsDTO calendar) throws Exception {
        ResponseMessage responseMessage = calendarService.save(calendar);
        return responseMessage;
    }

    /**
     * Get one Calendar details.
     */
    @ResponseBody
    @RequestMapping(value = "/{dimensionId}/{hierarchyId}", method = RequestMethod.GET)
    public CalendarDetailsDTO fetchCalendarDetails(@PathVariable Integer dimensionId, @PathVariable Integer hierarchyId) throws Exception {
        return calendarService.fetchCalendarDetails(dimensionId, hierarchyId);
    }

    /**
     * Delete Dimension Calendar
     */
    @ResponseBody
    @RequestMapping(value = "/{dimensionId}/{hierarchyId}", method = RequestMethod.DELETE)
    public ResponseMessage deleteCalendar(@PathVariable int dimensionId, @PathVariable int hierarchyId) throws Exception {
        ResponseMessage responseMessage = calendarService.delete(dimensionId, hierarchyId);
        return responseMessage;
    }

    @ResponseBody
    @RequestMapping(value = "/modelVisId/", method = RequestMethod.GET)
    public List<DimensionDTO> fetchCalendarsForModelVisId(@RequestParam(value = "modelVisId", required = true) String modelVisId) throws Exception {
        return calendarService.browseCalendarsForModelVisId(modelVisId);
    }
}
