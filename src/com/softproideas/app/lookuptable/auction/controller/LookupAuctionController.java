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
package com.softproideas.app.lookuptable.auction.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cedar.cp.api.base.ValidationException;
import com.softproideas.app.lookuptable.auction.model.LookupAuctionDTO;
import com.softproideas.app.lookuptable.auction.service.LookupAuctionService;

/**
 * 
 * <p>Spring MVC controller responsible for handling requests from web browser based user interface.
 * Controller is available at the url <em>/lookupTable/auction</em>.</p>
 *
 * @author Jakub Piskorz
 * @email jakub.piskorz@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@RequestMapping(value = "/lookup/auction")
@Controller
public class LookupAuctionController {
    
    @Autowired
    LookupAuctionService lookupAuctionService;

    @ResponseBody
    @RequestMapping(value = "/getauction", method = RequestMethod.GET)
    public List<LookupAuctionDTO> browseAuctions(@RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "orderby", required = false) String orderby,
            @RequestParam(value = "filters", required = false) String[] filters,
            HttpServletResponse response) throws Exception {

//        Integer gridRowCount = lookupAuctionService.getRowsCount();
//        response.setHeader("X-Total-Count", ""+gridRowCount );
        
        List<LookupAuctionDTO> returned = null;
        if(pageSize == null && pageNumber == null) {
            returned = lookupAuctionService.browseAuctions(orderby, filters);
        } else if(pageSize == null) {
            returned = lookupAuctionService.browseAuctions(pageNumber, orderby, filters);
        } else if(pageNumber == null) {
            throw new ValidationException("Can not get data, missing parameter pageNumber");
        } else {
            returned = lookupAuctionService.browseAuctions(pageSize, pageNumber, orderby, filters);
        }
        
        return returned;
    }
   
    

}