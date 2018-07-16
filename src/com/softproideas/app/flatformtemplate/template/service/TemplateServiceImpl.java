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
package com.softproideas.app.flatformtemplate.template.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.tc.apps.metadataimpexp.util.ZipServices;
import com.cedar.cp.util.flatform.model.workbook.CellDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorksheetDTO;
import com.softproideas.app.flatformtemplate.template.dao.TemplateDao;
import com.softproideas.app.flatformtemplate.template.mapper.TemplateMapper;
import com.softproideas.app.flatformtemplate.template.model.MoveEvent;
import com.softproideas.app.flatformtemplate.template.model.TemplateDetailsDTO;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.FileServices;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.SelectListElementDTO;

@Service("templateService")
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    TemplateDao templateDao;

    @Autowired
    CPContextHolder cpContextHolder;

    private static Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);

    @Override
    public TemplateDetailsDTO browseTemplates(Boolean disableDirectories) throws ServiceException {
        try {
            return templateDao.browseTemplates(disableDirectories);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public TemplateDetailsDTO fetchTemplate(UUID templateUUID) throws ServiceException {
        try {
            AllUsersELO allUsers = cpContextHolder.getListSessionServer().getAllUsers();
            return templateDao.fetchTemplate(templateUUID, allUsers);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public TemplateDetailsDTO insertTemplate(TemplateDetailsDTO template) throws ServiceException {
        try {
            TemplateDetailsDTO returnedTemplate = templateDao.insertTemplate(template);
            return returnedTemplate;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }

    }

    @Override
    public ResponseMessage updateTemplate(TemplateDetailsDTO template, Boolean fullUpdate) throws ServiceException {
        ResponseMessage responseMessage;
        try {
            responseMessage = templateDao.updateTemplate(template, fullUpdate);
            return responseMessage;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    @Override
    public ResponseMessage updateTemplateIndex(final MoveEvent moveEvent) throws ServiceException {
        ResponseMessage responseMessage;
        try {
            responseMessage = templateDao.updateTemplateIndex(moveEvent);
            return responseMessage;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    @Override
    public ResponseMessage deleteTemplate(UUID templateUUID) throws ServiceException {
        ResponseMessage responseMessage;
        try {
            responseMessage = templateDao.deleteTemplate(templateUUID);
            return responseMessage;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public TemplateDetailsDTO upload(MultipartFile file) throws ServiceException {
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(is);
            is.close();

            TemplateDetailsDTO templateDetailsDTO = new TemplateDetailsDTO();
            templateDetailsDTO.setWorkbook(fileToWorkbookDTO(file));

            byte[] fileByte = workbookToByteArray(workbook);
            String jsonForm = convertExcelToJson(fileByte);
            templateDetailsDTO.setJsonForm(jsonForm);

            return templateDetailsDTO;
        } catch (Exception e) {
            logger.error("Error upload! :" + e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private byte[] workbookToByteArray(Workbook workbook) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } finally {
            bos.close();
        }
        return bos.toByteArray();
    }

    private String convertExcelToJson(byte[] fileByte) throws ServiceException {
        try {
            return cpContextHolder.getCPConnection().getExcelIOProcess().convertXlsToJson(fileByte, "");
        } catch (ValidationException e) {
            logger.error("Error during parse byte[] to JSON! :" + e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Convert json spread to apache poi Workbook
     */
    @Override
    public byte[] exportToXLSX(String jsonForm) throws ServiceException {
        try {
            byte[] excelFile = null;
            InputStream is = cpContextHolder.getCPConnection().getExcelIOProcess().convertJsonToXlsx(jsonForm, "");
            excelFile = IOUtils.toByteArray(is);
            is.close();
            return excelFile;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public File exportFlatForms(UUID templateUUID) throws ServiceException, SQLException, DaoException {
        File file = null;
        File destFile = null;
        List<TemplateDetailsDTO> templateList = templateDao.browseTemplatesForDirectory(templateUUID);
        int length = templateList.size();
        HashMap<String, File> xmlFormExcelTempFileList = new HashMap<String, File>();
        try {
            int j = 0;
            for (int i = 0; i < length; i++) {
                j++;
                TemplateDetailsDTO currentDTO = templateList.get(i);
                String name = j + " - " + currentDTO.getVisId() + ".xlsx";
                String description = currentDTO.getDescription();
                if (!description.equals("")) {
                    name = j + " - " + currentDTO.getVisId() + " - " + description + ".xlsx";
                }
                file = FileServices.createTemplateFile(name);
                xmlFormExcelTempFileList.put(name, file);
                FileServices.writeDataToFile(file, exportToXLSX(currentDTO.getJsonForm()));
            }
            destFile = FileServices.createTemplateFile("export.zip");
            ZipServices.zipFiles(xmlFormExcelTempFileList, destFile.getPath());
        } catch (IOException e) {
            logger.error("Error during Flat Forms export operation!", e);
            throw new ServiceException("Error during Flat Forms export operation!", e);
        }
        return destFile;
    }

    @Override
    public ArrayList<SelectListElementDTO> getTemplateTypes() {
        ArrayList<SelectListElementDTO> types = TemplateMapper.getTemplateTypes();
        return types;
    }

    @Override
    public WorkbookDTO fileToWorkbookDTO(MultipartFile file) throws IOException, InvalidFormatException {
        InputStream is = file.getInputStream();
        Workbook workbookPOI = WorkbookFactory.create(is);
        workbookPOI.getNumberOfSheets();
        is.close();
        WorkbookDTO workbook = new WorkbookDTO();

        List<WorksheetDTO> worksheets = new ArrayList<WorksheetDTO>();

        for (int i = 0; i < workbookPOI.getNumberOfSheets(); i++) {
            WorksheetDTO worksheet = new WorksheetDTO();
            worksheet.setName((String) (workbookPOI.getSheetName(i)));
            worksheet.setCells(new ArrayList<CellDTO>());
            worksheet.setProperties(new HashMap<String, String>());
            worksheets.add(worksheet);
        }

        workbook.setProperties(new HashMap<String, String>());
        workbook.setWorksheets(worksheets);
        return workbook;
    }

}
