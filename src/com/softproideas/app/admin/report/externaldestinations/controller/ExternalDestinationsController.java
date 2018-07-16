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
package com.softproideas.app.admin.report.externaldestinations.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.report.externaldestinations.model.ExternalDestinationDTO;
import com.softproideas.app.admin.report.externaldestinations.model.ExternalDestinationDetailsDTO;
import com.softproideas.app.admin.report.externaldestinations.service.ExternalDestinationsService;
import com.softproideas.commons.model.ResponseMessage;

@RequestMapping(value = "/report/externaldestinations")
@Controller
public class ExternalDestinationsController {

    @Autowired
    ExternalDestinationsService externalDestinationsService;

    /**
     * Get all ExternalDestinations from database.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<ExternalDestinationDTO> browseExternalDestinations() throws Exception {
        return externalDestinationsService.browseExternalDestinations();
    }

    /**
     * Insert Report.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage createReport(@RequestBody ExternalDestinationDetailsDTO report) throws Exception {
        ResponseMessage responseMessage = externalDestinationsService.save(report);
        return responseMessage;
    }

    /**
     * Update Report
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMessage updateReport(@RequestBody ExternalDestinationDetailsDTO report) throws Exception {
        ResponseMessage responseMessage = externalDestinationsService.save(report);
        return responseMessage;
    }

    /**
     * Get one ExternalDestination details.
     */
    @ResponseBody
    @RequestMapping(value = "/{reportId}", method = RequestMethod.GET)
    public ExternalDestinationDetailsDTO fetchReportDetails(@PathVariable int reportId) throws Exception {
        return externalDestinationsService.fetchReportDetails(reportId);
    }

    /**
     * Delete Report
     */
    @ResponseBody
    @RequestMapping(value = "/{reportId}", method = RequestMethod.DELETE)
    public ResponseMessage deleteReport(@PathVariable int reportId) throws Exception {
        ResponseMessage responseMessage = externalDestinationsService.delete(reportId);
        return responseMessage;
    }

}
