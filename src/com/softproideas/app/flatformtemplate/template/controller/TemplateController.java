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
package com.softproideas.app.flatformtemplate.template.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softproideas.app.flatformtemplate.template.mapper.TemplateMapper;
import com.softproideas.app.flatformtemplate.template.model.MoveEvent;
import com.softproideas.app.flatformtemplate.template.model.TemplateDTO;
import com.softproideas.app.flatformtemplate.template.model.TemplateDetailsDTO;
import com.softproideas.app.flatformtemplate.template.model.TemplateUpload;
import com.softproideas.app.flatformtemplate.template.service.TemplateService;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.SelectListElementDTO;

@RequestMapping(value = "/templates")
@Controller
public class TemplateController {

    @Autowired
    TemplateService templateService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public TemplateDetailsDTO browseTemplates() throws Exception {
        return templateService.browseTemplates(false);
    }

    @ResponseBody
    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public TemplateDetailsDTO browseTemplatesForGenerate() throws Exception {
        return templateService.browseTemplates(true);
    }

    @ResponseBody
    @RequestMapping(value = "/{templateUUID}", method = RequestMethod.GET)
    public TemplateDetailsDTO fetchTemplate(@PathVariable UUID templateUUID) throws Exception {
        TemplateDetailsDTO templateDetailsDTO = templateService.fetchTemplate(templateUUID);
        return templateDetailsDTO;
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public TemplateDTO insertTemplate(@RequestBody TemplateDetailsDTO template) throws Exception {
        TemplateDTO returnedtemplate = templateService.insertTemplate(template);
        return returnedtemplate;
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseMessage updateTemplate(@RequestBody TemplateDetailsDTO template) throws Exception {
        ResponseMessage responseMessage = templateService.updateTemplate(template, true);
        return responseMessage;
    }

    @ResponseBody
    @RequestMapping(value = "/index", method = RequestMethod.PUT)
    public ResponseMessage updateTemplateIndex(@RequestBody MoveEvent moveEvent) throws Exception {
        ResponseMessage responseMessage = templateService.updateTemplateIndex(moveEvent);
        return responseMessage;
    }

    @ResponseBody
    @RequestMapping(value = "/rename/", method = RequestMethod.PUT)
    public ResponseMessage renameTemplate(@RequestBody TemplateDetailsDTO template) throws Exception {
        ResponseMessage responseMessage = templateService.updateTemplate(template, false);
        return responseMessage;
    }

    @ResponseBody
    @RequestMapping(value = "/{templateUUID}", method = RequestMethod.DELETE)
    public ResponseMessage deleteTemplate(@PathVariable UUID templateUUID) throws Exception {
        ResponseMessage responseMessage = templateService.deleteTemplate(templateUUID);
        return responseMessage;
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public TemplateDetailsDTO upload(@ModelAttribute("upload") MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new Exception("No file was sent.");
        }

        Set<String> acceptedExtensions = new HashSet<String>(Arrays.asList("XLS", "XLSX"));
        String extension = FilenameUtils.getExtension(file.getOriginalFilename()).toUpperCase();
        if (!acceptedExtensions.contains(extension)) {
            throw new Exception("File wasn't an excel file.");
        }

        return templateService.upload(file);
    }

    @ResponseBody
    @RequestMapping(value = "/importToDirectory", method = RequestMethod.POST)
    public List<TemplateDetailsDTO> importToDirectory(@ModelAttribute("upload") TemplateUpload upload) throws Exception {
        List<MultipartFile> files = upload.getFiles();
        for (MultipartFile file: files) {
            if (file == null || file.isEmpty()) {
                throw new Exception("No files were sent.");
            }

            Set<String> acceptedExtensions = new HashSet<String>(Arrays.asList("XLS", "XLSX"));
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            if (!acceptedExtensions.contains(extension.toUpperCase())) {
                throw new Exception("File " + file.getOriginalFilename() + " wasn't an excel file.");
            }

        }

        String directory = upload.getData();
        ObjectMapper mapper = new ObjectMapper();
        TemplateDetailsDTO parentDirectory = mapper.readValue(directory, TemplateDetailsDTO.class);

        List<TemplateDetailsDTO> returnedTemplates = new ArrayList<TemplateDetailsDTO>();
        for (MultipartFile file: files) {
            TemplateDetailsDTO newTemplate = templateService.upload(file); // jsonForm is in new template
            newTemplate.setParentUUID(parentDirectory.getTemplateUUID());
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = file.getOriginalFilename().replace("." + extension, "");
            newTemplate.setVisId(fileName);
            newTemplate.setType(TemplateMapper.mapType(1));
            newTemplate.setText(fileName);
            TemplateDetailsDTO returnedTemplate = templateService.insertTemplate(newTemplate);
            returnedTemplate.setJsonForm(null);
            returnedTemplates.add(returnedTemplate);
        }
        return returnedTemplates;
    }

    @ResponseBody
    @RequestMapping(value = "/exportToXls")
    public void exportToXls( //
            @RequestParam(value = "templateUUID", required = true) String templateUUIDString, //
            HttpServletResponse response //
    ) throws Exception {
        UUID templateUUID = UUID.fromString(templateUUIDString);
        TemplateDetailsDTO templateDetails = templateService.fetchTemplate(templateUUID);
        String description = templateDetails.getDescription();
        String fileName = templateDetails.getVisId();
        if (!description.equals("")) {
            fileName += " - " + templateDetails.getDescription();
        }
        byte[] excelFile = templateService.exportToXLSX(templateDetails.getJsonForm());

        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
        OutputStream out = response.getOutputStream();
        out.write(excelFile);
        response.flushBuffer();
    }

    @ResponseBody
    @RequestMapping(value = "/exportMany")
    public void exportMany( //
            @RequestParam(value = "templateUUID", required = true) String templateUUIDString, //
            HttpServletResponse response //
    ) throws Exception {
        UUID templateUUID = UUID.fromString(templateUUIDString);

        File file = templateService.exportFlatForms(templateUUID);
        TemplateDetailsDTO directoryDetails = templateService.fetchTemplate(templateUUID);
        response.setHeader("Content-Disposition", "attachment;filename=" + directoryDetails.getVisId() + ".zip");
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

    @ResponseBody
    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public ArrayList<SelectListElementDTO> getTemplateTypes() throws Exception {
        return templateService.getTemplateTypes();
    }

}
