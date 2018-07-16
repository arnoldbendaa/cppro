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
package com.softproideas.app.admin.dimensions.business.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.dimensions.business.service.BusinessService;
import com.softproideas.app.admin.dimensions.model.DimensionDTO;
import com.softproideas.app.admin.dimensions.model.DimensionDetailsDTO;
import com.softproideas.commons.model.ResponseMessage;

@RequestMapping(value = "/dimensions/businesses")
@Controller
public class BusinessController {

    @Autowired
    BusinessService businessService;

    /**
     * Get all Business from database.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<DimensionDTO> browseBusinesses() throws Exception {
        return businessService.browseBusinesses();
    }

    /**
     * Insert Dimension Business
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage createBusiness(@RequestBody DimensionDetailsDTO business) throws Exception {
        ResponseMessage responseMessage = businessService.save(business);
        return responseMessage;
    }

    /**
     * Update Dimension Business
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMessage updateBusiness(@RequestBody DimensionDetailsDTO business) throws Exception {
        ResponseMessage responseMessage = businessService.save(business);
        return responseMessage;
    }

    /**
     * Get one Business details.
     */
    @ResponseBody
    @RequestMapping(value = "/{dimensionId}", method = RequestMethod.GET)
    public DimensionDetailsDTO fetchBusinessDetails(@PathVariable int dimensionId) throws Exception {
        return businessService.fetchBusinessDetails(dimensionId);
    }

    /**
     * Delete Dimension Business
     */
    @ResponseBody
    @RequestMapping(value = "/{dimensionId}", method = RequestMethod.DELETE)
    public ResponseMessage deleteBusiness(@PathVariable int dimensionId) throws Exception {
        ResponseMessage responseMessage = businessService.delete(dimensionId);
        return responseMessage;
    }

}
