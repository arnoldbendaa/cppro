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
package com.softproideas.app.admin.financecubes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.financecubes.model.DimensionDTO;
import com.softproideas.app.admin.financecubes.model.FinanceCubeDetailsDTO;
import com.softproideas.app.admin.financecubes.services.FinanceCubesService;
import com.softproideas.app.core.financecube.model.FinanceCubeModelCoreDTO;
import com.softproideas.commons.model.ResponseMessage;

@RequestMapping(value = "/financeCubes")
@Controller
public class FinanceCubesController {

    @Autowired
    FinanceCubesService financeCubesService;

    /**
     * Get list of all Finance Cubes.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<FinanceCubeModelCoreDTO> browseFinanceCubes() throws Exception {
        return financeCubesService.browseFinanceCubes();
    }

    /**
     * Update details for selected Finance Cube from database.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMessage updateFinanceCubesDetails(@Valid @RequestBody FinanceCubeDetailsDTO financeCubeDetailsDTO) throws Exception {
        ResponseMessage responseMessage = financeCubesService.saveFinanceCubeDetails(financeCubeDetailsDTO);
        return responseMessage;
    }

    /**
     * Create Finance Cube in database.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage createFinanceCubesDetails(@Valid @RequestBody FinanceCubeDetailsDTO financeCubeDetailsDTO) throws Exception {
        ResponseMessage responseMessage = financeCubesService.insertFinanceCubeDetails(financeCubeDetailsDTO);
        return responseMessage;
    }

    /**
     * Get details for selected Finance Cube.
     */
    @ResponseBody
    @RequestMapping(value = "/{modelId}/{financeCubeId}", method = RequestMethod.GET)
    public FinanceCubeDetailsDTO getFinanceCube(@PathVariable int modelId, @PathVariable int financeCubeId) throws Exception {
        return financeCubesService.getFinanceCubeDetails(modelId, financeCubeId);
    }

    /**
     * Delete selected Finance Cube from database.
     */
    @ResponseBody
    @RequestMapping(value = "/{modelId}/{financeCubeId}", method = RequestMethod.DELETE)
    public ResponseMessage deleteFinanceCubesDetails(@PathVariable int modelId, @PathVariable int financeCubeId) throws Exception {
        ResponseMessage responseMessage = financeCubesService.deleteFinanceCube(modelId, financeCubeId);
        return responseMessage;
    }

    /**
     * Get list of Model's Dimensions.
     */
    @ResponseBody
    @RequestMapping(value = "/dimensions/{modelId}", method = RequestMethod.GET)
    public List<DimensionDTO> browseDimensionsForModel(@PathVariable int modelId) throws Exception {
        List<DimensionDTO> list = financeCubesService.browseDimensionsForModel(modelId);
        return list;
    }
}
