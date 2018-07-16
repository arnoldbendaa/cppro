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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.softproideas.app.admin.forms.flatforms.model.FlatFormsExportRequestDTO;
import com.softproideas.app.admin.forms.flatforms.model.FlatFormsExportRequestString;
import com.softproideas.app.admin.forms.flatforms.service.FlatFormsImportExportService;
import com.softproideas.commons.model.ResponseMessage;

/**
 * <p>
 * Spring MVC controller responsible for handling requests
 * from web browser based user interface.
 * </p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@RequestMapping(value = "/flatForms")
@Controller
public class FlatFormsImportExportController {

    @Autowired
    private FlatFormsImportExportService flatFormsExportService;

    /**
     * Export selected Flat Forms.
     */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void exportFlatForms(@ModelAttribute FlatFormsExportRequestString exportObject, HttpServletResponse response) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        FlatFormsExportRequestDTO flatForms = mapper.readValue(exportObject.getExportObject(), TypeFactory.defaultInstance().constructType(FlatFormsExportRequestDTO.class));
        File file = flatFormsExportService.exportFlatForms(flatForms);
        response.setHeader("Content-Disposition", "attachment;filename=export.zip");
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");

        try {
            // get your file as InputStream
            InputStream is = new FileInputStream(file);
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    /**
     * Import Flat Forms from file.
     */
    @ResponseBody
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public ResponseMessage importFlatForms(@Valid @ModelAttribute("upload") MultipartFile file) throws Exception {
        File file1 = new File(file.getOriginalFilename());
        file.transferTo(file1);
        return flatFormsExportService.importFlatForms(file1);
    }
}
