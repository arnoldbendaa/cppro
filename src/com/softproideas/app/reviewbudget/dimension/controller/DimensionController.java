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
package com.softproideas.app.reviewbudget.dimension.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.reviewbudget.dimension.model.DimensionDetailsDTO;
import com.softproideas.app.reviewbudget.dimension.model.ElementDTO;
import com.softproideas.app.reviewbudget.dimension.service.DimensionService;
import com.softproideas.commons.model.TreeNodeDTO;
import com.softproideas.commons.model.enums.DimensionType;

/**
 * Controller for dimension details.
 * 
 * @author Szymon Walczak
 * @email szymon.walczak@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
@Controller
public class DimensionController {

    @Autowired
    DimensionService dimensionService;

    /**
     * GET /dimensions/{dimensionType}/budgetCycle/{budgetCycleId}/model/{modelId}
     * 
     * Method return first three levels of dimensions in a tree structure.
     * Next levels are returned by the <code>browseChildren</code> method.
     */
    @ResponseBody
    @RequestMapping(value = "/dimensions/{dimensionType}/budgetCycle/{budgetCycleId}/model/{modelId}", method = RequestMethod.GET)
    public List<TreeNodeDTO> browseDimensions( //
            @PathVariable DimensionType dimensionType, // specific dimension type (ACCOUNT, BUSSINESS, CALENDAR)
            @PathVariable int budgetCycleId, // budget cycle identifier
            @PathVariable int modelId // model identifier, each model has its own dimensions
    ) throws Exception {

        return dimensionService.browseDimensions(dimensionType, budgetCycleId, modelId);
    }

    /**
     * GET /dimensions/{dimensionType}/budgetCycle/{budgetCycleId}/parent/{parentId}/structure/{structureId}
     * 
     * Method return children for parent element which are stored on the third level
     * in the tree dimensions. Parent element is defined by <code>parentId</code> and <code>structureId</code>.
     */
    @ResponseBody
    @RequestMapping(value = "/dimensions/{dimensionType}/budgetCycle/{budgetCycleId}/parent/{parentId}/structure/{structureId}", method = RequestMethod.GET)
    public List<TreeNodeDTO> browseChildren( //
            @PathVariable DimensionType dimensionType, // specific dimension type (ACCOUNT, BUSSINESS, CALENDAR)
            @PathVariable int budgetCycleId, // budget cycle identifier
            @PathVariable int parentId, // parent identifier
            @PathVariable int structureId // structure identifier
    ) throws Exception {

        ElementDTO elementDTO = new ElementDTO();
        elementDTO.setId(parentId);
        elementDTO.setStructureId(structureId);
        return dimensionService.browseChildren(dimensionType, budgetCycleId, elementDTO);
    }

    /**
     * GET /datatypes
     * 
     * Method return all data types in a tree structure.
     */
    @ResponseBody
    @RequestMapping(value = "/datatypes", method = RequestMethod.GET)
    public List<TreeNodeDTO> browseDataTypes() throws Exception {

        return dimensionService.browseDataTypes();
    }

    /**
     * GET /fetchCellPK
     * 
     * Returns cellPK numeric dimensions and data type separated by comma
     */
    @ResponseBody
    @RequestMapping("/fetchCellPK")
    public String fetchCellPK( //
            @RequestParam(value = "modelId", required = true) Integer modelId, // model identifier
            @RequestParam(value = "sheetModel", required = false) String sheetModel, // model string from worksheet
            @RequestParam(value = "mapping", required = true) String mapping, // input or output mapping form cell
            @RequestParam(value = "contextDim0", required = true) String contextDim0, // account dimension from context
            @RequestParam(value = "contextDim1", required = true) String contextDim1, // bussiness dimension from context
            @RequestParam(value = "contextDim2", required = true) String contextDim2, // calendar dimension from context
            @RequestParam(value = "contextDataType", required = true) String contextDataType // data type from context
    ) throws Exception {

        String[] context = new String[4];
        context[0] = contextDim0;
        context[1] = contextDim1;
        context[2] = contextDim2;
        context[3] = contextDataType;
        if (modelId == null) {
            throw new Exception("ModelId is null");
        }
        return dimensionService.fetchCellPK(modelId, mapping, context, sheetModel);
    }

    /**
     * GET /fetchDimensionDetails
     * 
     * Method returns dimension and data type details (id, name, description)
     */
    @ResponseBody
    @RequestMapping("/fetchDimensionDetails")
    public DimensionDetailsDTO fetchDimensionDetails( //
            @RequestParam(value = "modelId", required = true) Integer modelId, // model identifier
            @RequestParam(value = "sheetModel", required = false) String sheetModel, // model string from worksheet
            @RequestParam(value = "mapping", required = true) String mapping, // input or output mapping form cell
            @RequestParam(value = "contextDim0", required = true) String contextDim0, // account dimension from context
            @RequestParam(value = "contextDim1", required = true) String contextDim1, // bussiness dimension from context
            @RequestParam(value = "contextDim2", required = true) String contextDim2, // calendar dimension from context
            @RequestParam(value = "contextDataType", required = true) String contextDataType // data type from context
    ) throws Exception {

        DimensionDetailsDTO result = new DimensionDetailsDTO();

        String[] context = new String[4];
        context[0] = contextDim0;
        context[1] = contextDim1;
        context[2] = contextDim2;
        context[3] = contextDataType;

        List<ElementDTO> dimensions = dimensionService.fetchDimensionDetails(modelId, mapping, context, sheetModel);
        result.setDimensions(dimensions);
        if (dimensionService.checkIfDimensionAreLeafs(dimensions)) {
            ElementDTO dataType = dimensionService.fetchDataType(mapping, context[3]);
            result.getDimensions().add(dataType);
        } else {
            result.setWarningMessage("One of dimensions is not a leaf [" + dimensions.get(0).getName() + ", " + dimensions.get(1).getName() + ", " + dimensions.get(2).getName() + "].");
        }

        return result;
    }

}