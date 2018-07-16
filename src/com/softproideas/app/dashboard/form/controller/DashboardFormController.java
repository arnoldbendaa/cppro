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
package com.softproideas.app.dashboard.form.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.softproideas.app.dashboard.form.model.DashboardDTO;
import com.softproideas.app.dashboard.form.service.DashboardFormService;
import com.softproideas.app.flatformeditor.form.model.MappingsValidation;
import com.softproideas.commons.model.ResponseMessage;

@RequestMapping(value = "/form")
@Controller
public class DashboardFormController {

    @Autowired
    DashboardFormService dashboardFormService;

    @RequestMapping(value = "/{type}", method = RequestMethod.GET)
    public ModelAndView create(@PathVariable String type) throws Exception {
        ModelAndView mainView = new ModelAndView("hierarchy");
        mainView.addObject("view", type);
        mainView.addObject("isEdit", "create");
        return mainView;
    }

    @ResponseBody
    @RequestMapping(value = "/{type}/{formUUID}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String type, @PathVariable UUID formUUID) throws Exception {
        ModelAndView mainView = new ModelAndView("hierarchy");
        mainView.addObject("view", type);
        mainView.addObject("isEdit", "open");
        mainView.addObject("uuid", formUUID.toString());
        return mainView;
    }

    @ResponseBody
    @RequestMapping(value = "/{type}/edit/{freeFormUUID}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String type, @PathVariable UUID freeFormUUID) throws Exception {
        ModelAndView mainView = new ModelAndView("hierarchy");
        mainView.addObject("view", type);
        mainView.addObject("isEdit", "edit");
        mainView.addObject("uuid", freeFormUUID.toString());
        return mainView;
    }

    @ResponseBody
    @RequestMapping(value = "/data/{freeFormUUID}", method = RequestMethod.GET)
    public DashboardDTO data(@PathVariable UUID freeFormUUID) throws Exception {
        return dashboardFormService.getFreeFormByUUID(freeFormUUID);
    }

    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseMessage insertOrUpdateForm(@RequestBody DashboardDTO freeForm) throws Exception {
        return dashboardFormService.insertOrUpdateFreeForm(freeForm);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{freeFormUUID}", method = RequestMethod.DELETE)
    public boolean deleteFreeForm(@PathVariable UUID freeFormUUID) throws Exception {
        boolean returned = dashboardFormService.deleteFreeForm(freeFormUUID);
        return returned;
    }

    @ResponseBody
    @RequestMapping(value = "/hierarchy/exchangeformodelid/{financeCubeId}", method = RequestMethod.GET)
    public Integer exchangeForModelId(@PathVariable Integer financeCubeId) throws Exception {
        Integer returned = dashboardFormService.exchangeForModelId(financeCubeId);
        return returned;
    }

    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public WorkbookDTO workbookTest(@RequestBody WorkbookDTO workbookStart) throws Exception {
//        MappingsValidation.validateWorkbook(workbookStart);
//        if (workbookStart.isValid() == false) {
//            return workbookStart;
//        }
        return dashboardFormService.prepareWorkbookToSum(workbookStart);
    }
}