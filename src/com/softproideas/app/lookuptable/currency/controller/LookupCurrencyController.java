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
package com.softproideas.app.lookuptable.currency.controller;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.softproideas.app.lookuptable.currency.model.LookupCurrencyChangeDTO;
import com.softproideas.app.lookuptable.currency.model.LookupCurrencyDTO;
import com.softproideas.app.lookuptable.currency.model.LookupCurrencyImportDataDTO;
import com.softproideas.app.lookuptable.currency.service.LookupCurrencyService;

/**
 * 
 * <p>Spring MVC controller responsible for handling requests from web browser based user interface.
 * Controller is available at the url <em>/lookupTable/currency</em>.</p>
 *
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@RequestMapping(value = "/lookup/currency")
@Controller
public class LookupCurrencyController {
    
    @Autowired
    LookupCurrencyService lookupCurrencyService;
    
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<LookupCurrencyDTO> browseCurrencies() throws Exception {
        return lookupCurrencyService.browseCurrencies();
    }
    
    @ResponseBody
    @RequestMapping(value = "/company/{company}/year/{year}", method = RequestMethod.GET)
    public List<LookupCurrencyDTO> browseCurrencies(@PathVariable int company, @PathVariable int year) throws Exception {
        return lookupCurrencyService.browseCurrencies(company, year);
    } 
    
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public List<LookupCurrencyDTO> saveCurrencies(@RequestBody List<LookupCurrencyDTO> lookupCurrencies) throws Exception {
        return lookupCurrencyService.saveCurrencies(lookupCurrencies);
    }
    
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public LookupCurrencyImportDataDTO upload(@ModelAttribute("upload") MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new Exception("No file was sended.");
        }

        Set<String> acceptedExtensions = new HashSet<String>(Arrays.asList("XLS", "XLSX"));
        String extension = FilenameUtils.getExtension(file.getOriginalFilename()).toUpperCase();
        if (!acceptedExtensions.contains(extension)) {
            throw new Exception("File wasn't an excel file.");
        }

        InputStream is = file.getInputStream();
        Workbook workbook = WorkbookFactory.create(is);
        is.close();
        
        return lookupCurrencyService.upload(workbook);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public boolean updateCurrencyName(@RequestBody List<LookupCurrencyChangeDTO> changes) throws Exception {
        return lookupCurrencyService.updateChanges(changes);
    }

}
