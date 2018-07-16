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
package com.softproideas.app.flatformeditor.form.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.util.CellType;
import com.softproideas.app.core.dimension.model.DimensionCoreDTO;
import com.softproideas.app.core.dimension.model.DimensionWithHierarchiesCoreDTO;
import com.softproideas.app.core.flatform.model.FlatFormExtendedCoreDTO;
import com.softproideas.app.core.flatform.service.FlatFormCoreService;
import com.softproideas.app.flatformeditor.form.model.FlatFormDetailsDTO;
import com.softproideas.app.flatformeditor.form.service.FormService;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.handler.exceptions.WrongFileExtensionException;

/**
 * <p>Handles requests related to form retrieval, update and create operation.</p>
 * 
 * @author Jacek Kurasiewicz
 * @email jk@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Controller
public class FormFlatFormController {

    @Autowired
    FormService formService;

    @Autowired
    FlatFormCoreService flatFormCoreService;

    @Autowired
    CPContextHolder cpContextHolder;
    @Autowired
    ServletContext servletContext;


    /**
     * GET /flatForm/{flatFormId}
     * 
     * Retrieves a flat form by its id.
     */
    @ResponseBody
    @RequestMapping(value = "/flatForm/{flatFormId}", method = RequestMethod.GET)
    public FlatFormDetailsDTO fetchFlatForm(@PathVariable int flatFormId) throws Exception {
        return formService.fetchFlatForm(flatFormId, CellType.BASIC);
    }

    /**
     * PUT /flatForm
     * 
     * Update flatform
     */
    @ResponseBody
    @RequestMapping(value = "/flatForm/", method = RequestMethod.PUT)
    public ResponseMessage update(@RequestBody FlatFormDetailsDTO flatForm) throws Exception {
        return formService.save(flatForm, false);
    }

    /**
     * PUT /flatForm/saveAs/
     * 
     * Save As ... flatform
     */
    @ResponseBody
    @RequestMapping(value = "/flatForm/saveAs/", method = RequestMethod.PUT)
    public ResponseMessage saveAs(@RequestBody FlatFormDetailsDTO flatForm) throws Exception {
        return formService.save(flatForm, true);
    }

    @ResponseBody
    @RequestMapping(value = "/modelDimensions/{modelId}", method = RequestMethod.GET)
    public List<DimensionCoreDTO> fetchModelDimensions(@PathVariable int modelId) throws Exception {
        return formService.fetchModelDimensions(modelId);
    }

    @ResponseBody
    @RequestMapping(value = "/modelDimensionsWithHierarchies/{modelId}", method = RequestMethod.GET)
    public List<DimensionWithHierarchiesCoreDTO> fetchModelDimensionsWithHierarchies(@PathVariable int modelId) throws Exception {
        return formService.fetchModelDimensionsWithHierarchies(modelId);
    }
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public FlatFormDetailsDTO upload(@ModelAttribute("upload") MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new Exception("No file was sent.");
        }

        // Set<String> acceptedExtensions = new HashSet<String>(Arrays.asList("XLS", "XLSX"));
        String extension = FilenameUtils.getExtension(file.getOriginalFilename()).toUpperCase();
        if (!"XLS".equals(extension) && !"XLSX".equals(extension)) {
            throw new WrongFileExtensionException("File wasn't an excel file.");
        }

        FlatFormDetailsDTO flatFormDetaildsDTO = formService.upload(file);
        return flatFormDetaildsDTO;
    }
    @ResponseBody
    @RequestMapping(value = "/workbookTest", method = RequestMethod.PUT)
    public WorkbookDTO workbookTest(@RequestBody WorkbookDTO workbook) throws Exception {
//        MappingsValidation.validateWorkbook(workbook);
//        if (workbook.isValid() == false) {
//            return workbook;
//        }
//        if(errors.length > 0){
//            throw new MappingValidationException("Invalid mappings in workbook test.", errors);
//        }
        return formService.workbookTest(workbook);
    }

    /**
     * Get all Flat Forms from database.
     */
    @ResponseBody
    @RequestMapping(value = "/flatForms", method = RequestMethod.GET)
    public List<FlatFormExtendedCoreDTO> browseFlatForms() throws Exception {
        return flatFormCoreService.browseFlatForms();
    }

    @ResponseBody
    @RequestMapping(value = "/flatForm/copytemplate", method = RequestMethod.PUT)
    public ResponseMessage copyTemplate(@RequestBody List<Integer> flatFormIds) throws Exception {
        return formService.copyTemplate(flatFormIds);
    }
    @ResponseBody
    @RequestMapping(value = "/flatForm/toggleLockFlag/{xmlFormId}/{flag}", method = RequestMethod.GET)
    public ResponseMessage toggleLockFlag(@PathVariable int xmlFormId,@PathVariable boolean flag){
    	return formService.toggleLockFlag(xmlFormId,flag);
    }
    @ResponseBody
    @RequestMapping(value = "/flatForm/getLockFlag/{xmlFormId}", method = RequestMethod.GET)
    public ResponseMessage getLockFlag(@PathVariable int xmlFormId){
    	return formService.getLockFlag(xmlFormId);
    }

}
