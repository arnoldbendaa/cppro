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
package com.softproideas.app.flatformeditor.structureelement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.core.dimension.model.DimensionCoreDTO;
import com.softproideas.app.flatformeditor.form.service.FormService;
import com.softproideas.app.flatformeditor.structureelement.model.DateStructureElementDTO;
import com.softproideas.app.flatformeditor.structureelement.model.FillDimensionOption;
import com.softproideas.app.flatformeditor.structureelement.service.StructureElementService;
import com.softproideas.common.models.StructureElementCoreDTO;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Controller
public class StructureElementController {

    @Autowired
    StructureElementService structureElementService;

    @Autowired
    FormService formService;

    @ResponseBody
    @RequestMapping(value = "/structureElements/fill", method = RequestMethod.POST)
    public List<StructureElementCoreDTO> browseNextStructureElements(@RequestBody FillDimensionOption option) throws Exception {
        int modelId = option.getModelId();

        List<DimensionCoreDTO> dimensions = formService.fetchModelDimensions(modelId);
        if (dimensions.size() == 0) {
            throw new Exception("There is no dimension for model [ModelId =" + modelId + "]");
        }
        if (dimensions.size() <= option.getDimensionIndex()) {
            throw new Exception("Number of dimensions is " + dimensions.size() + ". There is no dimension with index [Index = " + option.getDimensionIndex() + "].");
        }
        
        return structureElementService.browseNextStructureElements(option, dimensions);
    }

    /**
     * Get Structure Elements independents of select structureElement and typeQuery.
     */
    @ResponseBody
    @RequestMapping(value = "structureElements/{structureId}/{structureElementIds}/{typeOfQuery}", method = RequestMethod.GET)
    public List<StructureElementCoreDTO> getStructureElements(@PathVariable int structureId, @PathVariable String structureElementIds, @PathVariable String typeOfQuery) throws Exception {
        return structureElementService.browseStructureElements(structureId, structureElementIds, typeOfQuery);
    }
    
    /**
     * Get Calendar Structure Elements independents of select structureElement and typeQuery.
     */
    @ResponseBody
    @RequestMapping(value = "structureElements/calendar/{modelId}/{structureElementIds}/{typeOfQuery}", method = RequestMethod.GET)
    public List<DateStructureElementDTO> getCalendarStructureElementsForModel(@PathVariable int modelId, @PathVariable String structureElementIds, @PathVariable String typeOfQuery) throws Exception {
        return structureElementService.getCalendarStructureElements(modelId, structureElementIds, typeOfQuery);
    }

}
