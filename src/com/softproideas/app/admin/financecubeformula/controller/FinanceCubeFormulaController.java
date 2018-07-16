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
package com.softproideas.app.admin.financecubeformula.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.financecubeformula.model.FinanceCubeFormulaDTO;
import com.softproideas.app.admin.financecubeformula.model.FinanceCubeFormulaDetailsDTO;
import com.softproideas.app.admin.financecubeformula.services.FinanceCubeFormulaService;
import com.softproideas.app.admin.financecubes.model.DimensionDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyNodeLazyDTO;
import com.softproideas.app.core.dimension.model.DimensionWithHierarchiesCoreDTO;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;

@RequestMapping(value = "/financeCubeFormula")
@Controller
public class FinanceCubeFormulaController {

    @Autowired
    CPContextHolder cpContexHolder;

    @Autowired
    FinanceCubeFormulaService financeCubeFormulaService;

    /**
     * Get list of all Finance Cubes.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<FinanceCubeFormulaDTO> browseFinanceCubeFormula() throws Exception {
        return financeCubeFormulaService.browseFinanceCubesFormula();
    }

    /**
     * Get details for selected Finance Cube.
     */
    @ResponseBody
    @RequestMapping(value = "/{modelId}/{financeCubeId}/{financeCubeFormulaId}", method = RequestMethod.GET)
    public FinanceCubeFormulaDetailsDTO getFinanceCubeFormulaDetails(@PathVariable int modelId, @PathVariable int financeCubeId, @PathVariable int financeCubeFormulaId) throws Exception {
        return financeCubeFormulaService.getFinanceCubeFormulaDetails(modelId, financeCubeId, financeCubeFormulaId);
    }

    /**
    * Get string of compiled formula
    */
    @ResponseBody
    @RequestMapping(value = "/{modelId}/{financeCubeId}/{financeCubeFormulaId}/compile/{formulaText}/{formulaType}", method = RequestMethod.GET)
    public String getFinanceCubeFormulaComplie(@PathVariable int modelId, @PathVariable int financeCubeId, @PathVariable int financeCubeFormulaId, @PathVariable String formulaText, @PathVariable int formulaType) throws Exception {
        return financeCubeFormulaService.getFinanceCubeFormulaComplie(modelId, financeCubeId, financeCubeFormulaId, formulaText, formulaType);
    }

    /**
     * Update details for selected Finance Cube from database.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMessage updateFinanceCubesFormulaDetails(@RequestBody FinanceCubeFormulaDetailsDTO financeCubeFormulaDetailsDTO) throws Exception {
        ResponseMessage responseMessage = financeCubeFormulaService.saveFinanceCubFormulaDetails(financeCubeFormulaDetailsDTO);
        return responseMessage;
    }

    /**
     * Delete selected Finance Cube from database.
     */
    @ResponseBody
    @RequestMapping(value = "/{modelId}/{financeCubeId}/{financeCubeFormulaId}", method = RequestMethod.DELETE)
    public ResponseMessage delete(@PathVariable int modelId, @PathVariable int financeCubeId, @PathVariable int financeCubeFormulaId) throws Exception {
        ResponseMessage responseMessage = financeCubeFormulaService.delete(modelId, financeCubeId, financeCubeFormulaId);
        return responseMessage;
    }

    /**
     * Create Finance Cube in database.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage createFinanceCubesDetails(@RequestBody FinanceCubeFormulaDetailsDTO financeCubeFormulaDetailsDTO) throws Exception {
        ResponseMessage responseMessage = financeCubeFormulaService.saveFinanceCubFormulaDetails(financeCubeFormulaDetailsDTO);
        return responseMessage;
    }

    /**
     * Get model DimensionsWithHierarchies
     */
    @ResponseBody
    @RequestMapping(value = "/modelDimensionsWithHierarchies/{modelId}", method = RequestMethod.GET)
    public List<DimensionWithHierarchiesCoreDTO> fetchModelDimensionsWithHierarchies(@PathVariable int modelId) throws Exception {
        return financeCubeFormulaService.fetchModelDimensionsWithHierarchies(modelId);
    }

    /**
     * Get node for Data Types
     */
    @ResponseBody
    @RequestMapping(value = "/dataTypes/{modelId}", method = RequestMethod.GET)
    public List<HierarchyNodeLazyDTO> browseDataTypeNode(@PathVariable Integer modelId, @RequestParam(value = "id", required = true) String id, @RequestParam(value = "disableLevel", required = false) String disableLevel) throws Exception {
        if (id.equals("1")) {// when we ask for root
            return financeCubeFormulaService.dataTypesRoot(modelId, disableLevel);
        } else if (id.startsWith("dataType")) {
            return financeCubeFormulaService.browseDataTypesNode(modelId);
        }
        return new ArrayList<HierarchyNodeLazyDTO>();
    }

    /**
     * Get node for Calendars
     */
    @ResponseBody
    @RequestMapping(value = "/calendar/{modelId}/calendars", method = RequestMethod.GET)
    public List<HierarchyNodeLazyDTO> browseCalendarNode(@PathVariable Integer modelId, @RequestParam(value = "id", required = true) String id, @RequestParam(value = "disableLevel", required = false) String disableLevel) throws Exception {
        if (id.equals("1")) {// when we ask for root
            return financeCubeFormulaService.calendarDimension(modelId, disableLevel);
        } else if (id.startsWith("dimensionElement")) {
            String[] calendarParam = id.split("/");
            int calendarId = Integer.parseInt(calendarParam[1]);
            return financeCubeFormulaService.calendarRoot(modelId, calendarId, disableLevel);
        } else if (id.startsWith("hierarchyElement")) {
            String[] hierarchyElementParam = id.split("/");
            int hierarchyElementId = Integer.parseInt(hierarchyElementParam[1]);
            return financeCubeFormulaService.browseCalendarNode(modelId, hierarchyElementId);
        }

        return new ArrayList<HierarchyNodeLazyDTO>();
    }

    /**
     * Get Node for hierarchies
     */
    @ResponseBody
    @RequestMapping(value = "/{modelId}/hierarchyCc", method = RequestMethod.GET)
    public List<HierarchyNodeLazyDTO> browseHierarchyNodeCc(@PathVariable Integer modelId, @RequestParam(value = "id", required = true) String id, @RequestParam(value = "disableLevel", required = false) String disableLevel) throws Exception {
        if (id.equals("1")) {// when we ask for root
            return financeCubeFormulaService.hierarchyDimensionCc(modelId, disableLevel);
        } else if (id.startsWith("dimensionElement")) {
            String[] hierarchyParam = id.split("/");
            Integer hierarchyId = Integer.parseInt(hierarchyParam[1]);
            return financeCubeFormulaService.hierarchyRootCc(modelId, hierarchyId, disableLevel);
        } else if (id.startsWith("hierarchyElement")) {
            String[] hierarchyElementParam = id.split("/");
            int hierarchyElementId = Integer.parseInt(hierarchyElementParam[1]);
            return financeCubeFormulaService.browseHierarchyCcNode(modelId, hierarchyElementId);
        }

        return new ArrayList<HierarchyNodeLazyDTO>();

    }

    /**
     * Get Node for hierarchies
     */
    @ResponseBody
    @RequestMapping(value = "/{modelId}/hierarchyExp", method = RequestMethod.GET)
    public List<HierarchyNodeLazyDTO> browseHierarchyNodeExp(@PathVariable Integer modelId, @RequestParam(value = "id", required = true) String id, @RequestParam(value = "disableLevel", required = false) String disableLevel) throws Exception {
        if (id.equals("1")) {// when we ask for root
            return financeCubeFormulaService.hierarchyDimensionExp(modelId, disableLevel);
        } else if (id.startsWith("dimensionElement")) {
            String[] hierarchyParam = id.split("/");
            Integer hierarchyId = Integer.parseInt(hierarchyParam[1]);
            return financeCubeFormulaService.hierarchyRootExp(modelId, hierarchyId);
        } else if (id.startsWith("hierarchyElement")) {
            String[] hierarchyElementParam = id.split("/");
            int hierarchyElementId = Integer.parseInt(hierarchyElementParam[1]);
            return financeCubeFormulaService.browseHierarchyExpNode(modelId, hierarchyElementId);
        }

        return new ArrayList<HierarchyNodeLazyDTO>();

    }

    /**
     * Get table headers - dimension
     */
    @ResponseBody
    @RequestMapping(value = "/{modelId}", method = RequestMethod.GET)
    public DimensionDTO[] getTableHeader(@PathVariable int modelId) throws Exception {
        return financeCubeFormulaService.getTableHeader(modelId);
    }

}
