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
package com.softproideas.app.dashboard.auction.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cedar.cp.api.base.ValidationException;
import com.softproideas.app.dashboard.auction.chartemplates.XlsTemplate;
import com.softproideas.app.dashboard.auction.model.InterfaceComputingDataForDashboard.GraphType;
import com.softproideas.app.dashboard.auction.service.DashboardService;
import com.softproideas.app.lookuptable.auction.model.DashboardAuctionDTO;

@RequestMapping(value = "/auction", method = RequestMethod.GET)
@Controller
public class AuctionController {

    @Autowired
    DashboardService dashboardService;

    @Autowired
    XlsTemplate xlsTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView currency(HttpServletRequest request) throws Exception {
        ModelAndView mainView = new ModelAndView("auction");
        return mainView;
    }
    //doFilter
    @RequestMapping(value = "/{auctionNo}", method = RequestMethod.GET)
    public ModelAndView mainplus(@PathVariable int auctionNo) throws Exception {
        ModelAndView mainView = new ModelAndView("main");
        mainView.addObject("auctionNo", auctionNo);
        return mainView;
    }
    //doFilter
    @ResponseBody
    @RequestMapping(value = "/data/{auctionNumber}", method = RequestMethod.GET)
    public Map<String, Map<String, Map<String, String>>> browseAuctions(@PathVariable int auctionNumber) throws Exception {
        return dashboardService.browseAuctions(auctionNumber);
    }
    
    @ResponseBody
    @RequestMapping(value = "/data/auctions", method = RequestMethod.GET)
    public List<DashboardAuctionDTO> getAuctionsForCompany(
            @RequestParam(value = "model", required = true) String modelDescription,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "orderby", required = false) String orderby,
            @RequestParam(value = "filters", required = false) String[] filters,
            HttpServletResponse response) throws Exception {
        
        List<DashboardAuctionDTO> returned = null;
        if(pageSize == null && pageNumber == null) {
            returned = dashboardService.browseAuctionsForCompany(orderby, filters, modelDescription);
        } else if(pageSize == null) {
            returned = dashboardService.browseAuctionsForCompany(pageNumber, orderby, filters, modelDescription);
        } else if(pageNumber == null) {
            throw new ValidationException("Can not get data, missing parameter pageNumber");
        } else {
            returned = dashboardService.browseAuctionsForCompany(pageSize, pageNumber, orderby, filters, modelDescription);
        }
        
        return returned;
    }
    
    @ResponseBody
    @RequestMapping(value = "/models", method = RequestMethod.GET)
    public Set<String> fetchModelsForLoggedUser() throws Exception {
        return dashboardService.fetchModelsForLoggedUser();
    }
    //doFilter
    @ResponseBody
    @RequestMapping(value = "/excel/{auctionNumber}", method = RequestMethod.GET)
    public void fetchExcelMobileFlatForm(@PathVariable Integer auctionNumber, @RequestParam(value = "budgetHammer", required = false) Double budgetHammer, @RequestParam(value = "budgetBuyersPremium", required = false) Double budgetBuyersPremium, @RequestParam(value = "budgetCommision", required = false) Double budgetCommision, @RequestParam(value = "budgetInsurance", required = false) Double budgetInsurance, HttpServletResponse response) throws Exception {

        budgetHammer = budgetHammer == null ? 0 : budgetHammer;
        budgetBuyersPremium = budgetBuyersPremium == null ? 0 : budgetBuyersPremium;
        budgetCommision = budgetCommision == null ? 0 : budgetCommision;
        budgetInsurance = budgetInsurance == null ? 0 : budgetInsurance;

        // ClassLoader classLoader = getClass().getClassLoader();
        // File file = new File(classLoader.getResource("exceltemplates/sale_analysis.xlsx").getFile());
        // Workbook workbookWorkbookFactory.create(file);
        Workbook workbook = xlsTemplate.getSaleAnalysisXlsTemplate();

        // workbook.setForceFormulaRecalculation(true);

        workbook.getSheet(GraphType.SALE_RESULT.getDescription()).getRow(36).getCell(4).setCellValue(budgetHammer);
        workbook.getSheet(GraphType.SALE_RESULT.getDescription()).getRow(37).getCell(4).setCellValue(budgetBuyersPremium);
        workbook.getSheet(GraphType.SALE_RESULT.getDescription()).getRow(38).getCell(4).setCellValue(budgetCommision);
        workbook.getSheet(GraphType.SALE_RESULT.getDescription()).getRow(39).getCell(4).setCellValue(budgetInsurance);
        // set total
        workbook.getSheet(GraphType.SALE_RESULT.getDescription()).getRow(40).getCell(4).setCellValue(budgetBuyersPremium + budgetCommision + budgetInsurance);

        dashboardService.fetchExcelMobileFlatForm(workbook, auctionNumber);

        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;filename=" + "sale_analysis_" + auctionNumber + ".xlsx");
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
        workbook.write(response.getOutputStream());
        response.flushBuffer();
    }
}
