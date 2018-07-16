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
package com.softproideas.app.lookuptable.parameters.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.lookuptable.parameters.model.LookupParameterChangeDTO;
import com.softproideas.app.lookuptable.parameters.model.LookupParameterDTO;
import com.softproideas.app.lookuptable.parameters.model.LookupParameterDimensionDTO;
import com.softproideas.app.lookuptable.parameters.service.LookupParametersDimensionService;
import com.softproideas.app.lookuptable.parameters.service.LookupParametersService;

@RequestMapping(value = "/lookup/parameters")
@Controller
public class LookupParametersController {

    @Autowired
    LookupParametersService lookupParametersService;

    @Autowired
    LookupParametersDimensionService lookupParametersDimensionService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<LookupParameterDTO> browseParameters() throws Exception {
        return lookupParametersService.browseParameters();
    }

    @ResponseBody
    @RequestMapping(value = "/company/{company}", method = RequestMethod.GET)
    public List<LookupParameterDTO> browseCurrencies(@PathVariable int company) throws Exception {
        return lookupParametersService.browseParameters(company);
    }

    @ResponseBody
    @RequestMapping(value = "/dimensions/company/{company}", method = RequestMethod.GET)
    public List<LookupParameterDimensionDTO> browseDimensions(@PathVariable int company) throws Exception {
        List<LookupParameterDimensionDTO> returned = lookupParametersDimensionService.browseDimensions(company);
        return returned;
    }

    @ResponseBody
    @RequestMapping(value = "/import/dimensions/company/{company}", method = RequestMethod.GET)
    public List<LookupParameterDimensionDTO> importDimensions(@PathVariable int company) throws Exception {
        if (lookupParametersDimensionService.importDimensions(company) == null) {
            throw new Exception("Cannot connect to the Open Account database.");
        } else {
            return lookupParametersDimensionService.importDimensions(company);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public List<LookupParameterDTO> saveParameters(@RequestBody List<LookupParameterDTO> parameters) throws Exception {
        return lookupParametersService.saveParameters(parameters);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public boolean updateFieldName(@RequestBody List<LookupParameterChangeDTO> changes) throws Exception {
        return lookupParametersService.updateChanges(changes);
    }

}
