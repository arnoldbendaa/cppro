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
package com.softproideas.app.reviewbudget.financesystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cedar.cp.util.xmlform.swing.ValidationMessageStore;
import com.softproideas.app.flatformeditor.form.model.MappingsValidation;
import com.softproideas.app.reviewbudget.dimension.model.ElementDTO;
import com.softproideas.app.reviewbudget.dimension.service.DimensionService;
import com.softproideas.app.reviewbudget.financesystem.model.InvoiceDTO;
import com.softproideas.app.reviewbudget.financesystem.service.FinanceSystemService;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.util.validation.MappingArguments;
import com.softproideas.util.validation.MappingValidator;

/**
 * Class manages invoices from database. Class in fact only browses invoices from database.
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
@Controller
public class FinanceSystemController {

    @Autowired
    CPContextHolder cpContextHolder;

    @Autowired
    DimensionService dimensionService;

    @Autowired
    FinanceSystemService financeSystemService;

    /**
     * GET /browseInvoices
     * 
     * Method returns invoices for specific cell, which have mapping properties. If some properties are missing, context properties will be taken.
     * @return object contains list of all invoices and additional properties (column names, selection info, dimensions info)
     */
    @ResponseBody
    @RequestMapping("/browseInvoices")
    public InvoiceDTO browseInvoices( //
            @RequestParam(value = "modelId", required = true) Integer modelId, //
            @RequestParam(value = "sheetModel", required = false) String sheetModel, //
            @RequestParam(value = "mapping", required = true) String mapping, //
            @RequestParam(value = "contextDim0", required = true) String contextDim0, //
            @RequestParam(value = "contextDim1", required = true) String contextDim1, //
            @RequestParam(value = "contextDim2", required = true) String contextDim2, //
            @RequestParam(value = "contextDataType", required = true) String contextDataType, //
            @RequestParam(value = "financeCube", required = true) String financeCubeToken //
    ) throws Exception {

        String[] context = new String[4];
        context[0] = contextDim0;
        context[1] = contextDim1;
        context[2] = contextDim2;
        context[3] = contextDataType;
        List<ElementDTO> dimensions = null;
            dimensions = dimensionService.fetchDimensionDetails(modelId, mapping, context, sheetModel);


//        if (dimensionService.checkIfDimensionAreLeafs(dimensions)) {
            ElementDTO dataType = dimensionService.fetchDataType(mapping, context[3]);
            String cellPK = dimensionService.fetchCellPK(dimensions, dataType);
            int cmpy = getCompanyFromMapping(mapping);
            
            return financeSystemService.browseInvoices(cellPK, financeCubeToken, cmpy);
//        } else {
//            InvoiceDTO invoiceDTO = new InvoiceDTO();
//            invoiceDTO.setWarningMessage("One of dimensions is not a leaf [" + dimensions.get(0).getName() + ", " + dimensions.get(1).getName() + ", " + dimensions.get(2).getName() + "]");
//            return invoiceDTO;
//        }
    }

    private int getCompanyFromMapping(String mapping) {
        int cmpy = 0;
        if (mapping.contains("cmpy=")) {
            MappingValidator mv = new MappingValidator(mapping);
            String company = mv.getListOfArguments().get(MappingArguments.CMPY.toString());
            if (company != null && !company.isEmpty()) {
                cmpy = Integer.parseInt(mv.getListOfArguments().get(MappingArguments.CMPY.toString()));
            }
        }
        return cmpy;
    }

}