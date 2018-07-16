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
/**
 * 
 */
package com.softproideas.app.admin.forms.flatforms.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cedar.cp.api.base.ValidationException;
import com.softproideas.app.admin.forms.flatforms.service.FlatFormsService;
import com.softproideas.app.core.flatform.model.FlatFormExtendedCoreDTO;
import com.softproideas.app.lookuptable.auction.model.FlatFormDTO;
import com.softproideas.commons.model.ResponseMessage;

/**
 * <p> 
 * Spring MVC controller responsible for handling requests
 * from web browser based user interface.
 * </p>
 *
 * @author Jacek Kurasiewicz
 * @email jk@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@RequestMapping(value = "/flatForms")
@Controller
public class FlatFormsController {
    private static Logger logger = LoggerFactory.getLogger(FlatFormsController.class);

    @Autowired
    private FlatFormsService flatFormsService;

    /**
     * Get all flat forms from database belonging
     * to current user or these current user has
     * rights to read/list.
     * 
     * @return list of {@link FlatFormExtendedCoreDTO}
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<FlatFormExtendedCoreDTO> fetchFlatForms() throws Exception {
        try {
            logger.debug("fetchFlatForms() has been issued...");
            long t = System.currentTimeMillis();
            List<FlatFormExtendedCoreDTO> ffs = flatFormsService.fetchFlatForms();
            logger.debug("fetchFlatForms() processing time: " + (System.currentTimeMillis() - t) + "ms");
            return ffs;
        } catch (Exception e) {
            logger.error("Error during retrieving flat forms!", e);
            throw new Exception("Error during retrieving flat forms!", e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public List<FlatFormDTO> fetchFlatFormsForFinanceCubeId(
            @RequestParam(value = "financeCube", required = true) Integer financeCubeId,
            @RequestParam(value = "pageSize", required = false) Integer pageSize, 
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber, 
            @RequestParam(value = "orderby", required = false) String orderby, 
            @RequestParam(value = "filters", required = false) String[] filters, HttpServletResponse response) throws Exception {

        List<FlatFormDTO> returned = null;
        if (pageSize == null && pageNumber == null) {
            returned = flatFormsService.fetchFlatFormsForFinanceCubeId(orderby, filters, financeCubeId);
        } else if (pageSize == null) {
            returned = flatFormsService.fetchFlatFormsForFinanceCubeId(pageNumber, orderby, filters, financeCubeId);
        } else if (pageNumber == null) {
            throw new ValidationException("Can not get data, missing parameter pageNumber");
        } else {
            returned = flatFormsService.fetchFlatFormsForFinanceCubeId(pageSize, pageNumber, orderby, filters, financeCubeId);
        }

        return returned;
    }

    /**
     * Delete selected Flat Forms.
     */
    @ResponseBody
    @RequestMapping(value = "/{flatFormId}", method = RequestMethod.DELETE)
    public ResponseMessage deleteFlatForms(@PathVariable int flatFormId) throws Exception {
        return flatFormsService.deleteFlatForms(flatFormId);
    }
}
