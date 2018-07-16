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
package com.softproideas.app.flatformeditor.form.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.dto.xmlform.XmlFormEditorSessionCSO;
import com.cedar.cp.dto.xmlform.XmlFormEditorSessionSSO;
import com.cedar.cp.dto.xmlform.XmlFormImpl;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.ejb.api.xmlform.XmlFormEditorSessionServer;
import com.cedar.cp.ejb.impl.xmlform.XmlFormEditorSessionSEJB;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorksheetDTO;
import com.cedar.cp.util.flatform.model.workbook.editor.WorkbookMapper;
import com.cedar.cp.util.flatform.model.workbook.util.CellType;
import com.softproideas.app.core.dimension.model.DimensionCoreDTO;
import com.softproideas.app.core.dimension.model.DimensionWithHierarchiesCoreDTO;
import com.softproideas.app.core.dimension.service.CoreDimensionService;
import com.softproideas.app.core.financecube.service.FinanceCubeCoreService;
import com.softproideas.app.core.profile.service.ProfileService;
import com.softproideas.app.core.users.mapper.UserCoreMapper;
import com.softproideas.app.core.workbook.model.FlatFormExtractor;
import com.softproideas.app.core.workbook.model.FlatFormExtractorDao;
import com.softproideas.app.flatformeditor.form.mapper.FlatFormMapper;
import com.softproideas.app.flatformeditor.form.model.FlatFormDetailsDTO;
import com.softproideas.app.flatformeditor.form.util.XcellDistributionUtil;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.DefaultError;
import com.softproideas.commons.model.error.ValidationError;
import com.softproideas.commons.util.WorkbookUtil;

/**
 * <p>This service is aimed to take care of flat form CRUD operations using EJB services.</p>
 * 
 * @author Jacek Kurasiewicz
 * @email jk@softproideas.com
 * 2014 All rights reserved to IT Services Jacek Kurasiewicz
 */
@Service("formService")
public class FormServiceImpl implements FormService {

    private static Logger logger = LoggerFactory.getLogger(FormServiceImpl.class);

    @Autowired
    private CPContextHolder cpContextHolder;

    @Autowired
    private FinanceCubeCoreService financeCubeCoreService;

//    @Autowired
    private ProfileService profileService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private CoreDimensionService dimensionService;

    @Autowired
    private FlatFormExtractorDao flatFormExtractorDao;
    XmlFormEditorSessionSEJB server = new XmlFormEditorSessionSEJB();

    @Override
    public FlatFormDetailsDTO fetchFlatForm(int flatFormId, CellType cellType) throws Exception {
//        XmlFormEditorSessionServer server = cpContextHolder.getXmlFormEditorSessionServer();
        XmlFormImpl xmlFormImpl = getXmlFormFromServer(server, flatFormId);
        AllUsersELO allUsersELO = cpContextHolder.getListSessionServer().getAllUsers();
        return FlatFormMapper.mapXmlFormEditorSessionSSO(xmlFormImpl, allUsersELO, cellType, false);
    }

    @Override
    public FlatFormDetailsDTO fetchFlatFormWithExcelFile(int flatFormId, CellType cellType) throws Exception {
//        XmlFormEditorSessionServer server = cpContextHolder.getXmlFormEditorSessionServer();
        XmlFormImpl xmlFormImpl = getXmlFormFromServer(server, flatFormId);
        AllUsersELO allUsersELO = cpContextHolder.getListSessionServer().getAllUsers();
        return FlatFormMapper.mapXmlFormEditorSessionSSO(xmlFormImpl, allUsersELO, cellType, true);
    }

    @Override
    public XmlFormImpl getXmlFormFromServer(XmlFormEditorSessionSEJB server, int flatFormId) throws ServiceException {
        XmlFormEditorSessionSSO sso = null;
        int userId = cpContextHolder.getUserId();
        try {
            if (flatFormId != -1) {
                XmlFormPK xmlFormPK = new XmlFormPK(flatFormId);
                sso = server.getItemData(userId,xmlFormPK);
            } else {
                sso = server.getNewItemData(userId);
            }
            XmlFormImpl xmlFormImpl = sso.getEditorData();
            return xmlFormImpl;
        } catch (CPException e) {
            logger.error("Error during browsing flat form with id =" + flatFormId + "!");
            throw new ServiceException("Error during browsing flat form with id =" + flatFormId + "!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during browsing flat form with id =" + flatFormId + "!");
            throw new ServiceException("Validation error during browsing flat form with id =" + flatFormId + "!", e);
        }
    }

    @Override
    public List<DimensionCoreDTO> fetchModelDimensions(int modelId) {
        return dimensionService.fetchModelDimensions(modelId);
    }

    @Override
    public List<DimensionWithHierarchiesCoreDTO> fetchModelDimensionsWithHierarchies(int modelId) {
        return dimensionService.fetchModelDimensionsWithHierarchies(modelId);
    }

    @Override
    public FlatFormDetailsDTO upload(MultipartFile file) throws ServiceException {
        InputStream is = null;
        Exception ex = null;
        FlatFormDetailsDTO flatFormDTO = null;
        try {
            is = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(is);
            is.close();

            flatFormDTO = new FlatFormDetailsDTO();
            WorkbookDTO xmlForm = manageXmlForm(workbook, false);
            flatFormDTO.setXmlForm(xmlForm);

            byte[] fileByte = workbookToByteArray(workbook);
            String jsonForm = manageJsonForm(fileByte);
            flatFormDTO.setJsonForm(jsonForm);
        } catch (Exception e) {
            ex = e;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                if (ex != null) {
                    logger.error("Error upload! :" + ex.getMessage());
                    throw new ServiceException(ex.getMessage(), ex);
                } else {
                    logger.error("Error while closing inputStream! :" + e.getMessage());
                    throw new ServiceException(e.getMessage(), e);
                }
            }
            if (ex != null) {
                logger.error("Error upload! :" + ex.getMessage());
                throw new ServiceException(ex.getMessage(), ex);
            }
        }
        return flatFormDTO;
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

    // XmlFormEditorSessionSEJB ,XmlFormApplication, XcellFormDesignView, XcellView
    @Override
    public ResponseMessage save(FlatFormDetailsDTO flatForm, boolean saveAs) throws ServiceException {
//        XmlFormEditorSessionServer server = cpContextHolder.getXmlFormEditorSessionServer();

        int flatFormId = flatForm.getFlatFormId();

        XmlFormImpl xmlFormImpl = getXmlFormFromServer(server, flatFormId);
        if (flatFormId == -1) {
            xmlFormImpl.setType(flatForm.getType());
        }
        xmlFormImpl.setVisId(flatForm.getVisId());
        xmlFormImpl.setDescription(flatForm.getDescription());
        xmlFormImpl.setFinanceCubeId(flatForm.getFinanceCubeId());

        // Users privilege to form
        List<UserRefImpl> newUserList = UserCoreMapper.mapUserCoreDTO(flatForm.getUsers());
        List<UserRefImpl> oldUserList = xmlFormImpl.getUserList();
        xmlFormImpl.setUserList(newUserList);

        String workbookString = WorkbookMapper.mapWorkbookDTOToXmlString(flatForm.getXmlForm());
        xmlFormImpl.setDefinition(workbookString);

        // Saving optimalization
        if (xmlFormImpl.getJsonForm() == null || !xmlFormImpl.getJsonForm().equals(flatForm.getJsonForm())) {
            xmlFormImpl.setJsonForm(flatForm.getJsonForm());
            byte[] excelFile = toXLSX(flatForm.getJsonForm());
            xmlFormImpl.setExcelFile(excelFile);
        }

        try {
            if (saveAs || flatFormId == -1) { // SaveAs... or new FlatForm saved for the first time
                XmlFormPK newPK = server.insert(new XmlFormEditorSessionCSO(cpContextHolder.getUserId(), xmlFormImpl));
                updateProfiles(oldUserList, newUserList, newPK.getXmlFormId());
                DefaultError message = new DefaultError("Flat Form saved", Integer.toString(newPK.getXmlFormId()));
                message.setSuccess(true);
                message.setError(false);
                return message;
            } else {
                server.update(new XmlFormEditorSessionCSO(cpContextHolder.getUserId(), xmlFormImpl));
                updateProfiles(oldUserList, newUserList, flatFormId);
                ResponseMessage success = new ResponseMessage(true);
                return success;
            }
        } catch (ValidationException e) {
            e.printStackTrace();
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            e.printStackTrace();
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        }
    }

    private void updateProfiles(List<UserRefImpl> oldUserList, List<UserRefImpl> newUserList, int flatFormId) throws ServiceException {
        if (flatFormId > 0) {
            List<Integer> userToSubstractList = new ArrayList<Integer>();
            List<Integer> userToAddList = new ArrayList<Integer>();

            // Create new profiles
            newUserList: for (UserRefImpl newUserRefImpl: newUserList) {
                for (UserRefImpl oldUserRefImpl: oldUserList) {
                    if (newUserRefImpl.getUserPK().equals(oldUserRefImpl.getUserPK())) {
                        continue newUserList; // exists in old and new userlist
                    }
                }
                userToAddList.add(((UserPK) newUserRefImpl.getUserPK()).getUserId());
            }

            // Delete old profiles
            oldUserList: for (UserRefImpl oldUserRefImpl: oldUserList) {
                for (UserRefImpl newUserRefImpl: newUserList) {
                    if (newUserRefImpl.getUserPK().equals(oldUserRefImpl.getUserPK())) {
                        continue oldUserList; // exists in old and new userlist
                    }
                }
                userToSubstractList.add(((UserPK) oldUserRefImpl.getUserPK()).getUserId());
            }
            if (oldUserList.size() == 0 && newUserList.size() > 0) {
                profileService.deleteProfilesForForm(flatFormId, userToAddList, false);
            }
            if (oldUserList.size() > 0 && newUserList.size() == 0) {
                profileService.insertProfileForForm(flatFormId, userToSubstractList, false);
            }
            if (oldUserList.size() > 0 && newUserList.size() > 0) {
                if (userToSubstractList.size() > 0) {
                    profileService.deleteProfilesForForm(flatFormId, userToSubstractList, true);
                }
                if (userToAddList.size() > 0) {
                    profileService.insertProfileForForm(flatFormId, userToAddList, true);
                }
            }
        }
    }

    private byte[] toXLSX(String json) throws ServiceException {
        try {
            byte[] excelFile = null;
            InputStream is = cpContextHolder.getCPConnection().getExcelIOProcess().convertJsonToXlsx(json, "");
            excelFile = IOUtils.toByteArray(is);
            is.close();
            return excelFile;
        } catch (Exception e) {
            logger.error("Error during parse workbook to XLSX! :" + e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private String manageJsonForm(byte[] fileByte) throws ServiceException {
        try {
            return cpContextHolder.getCPConnection().getExcelIOProcess().convertXlsToJson(fileByte, "");
        } catch (ValidationException e) {
            logger.error("Error during parse byte[] to JSON! :" + e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public WorkbookDTO manageXmlForm(Workbook workbook, boolean isTemplateProcess) {
        WorkbookDTO workbookDTO = new WorkbookDTO();
        List<WorksheetDTO> worksheets = manageWorkbookDTO(workbook, isTemplateProcess);
        workbookDTO.setWorksheets(worksheets);
        return workbookDTO;
    }

    private List<WorksheetDTO> manageWorkbookDTO(Workbook workbook, boolean isTemplateProcess) {
        List<WorksheetDTO> worksheetsDTO = new ArrayList<WorksheetDTO>();

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            WorksheetDTO worksheetDTO = new WorksheetDTO();
            Sheet sheet = workbook.getSheetAt(i);

            worksheetDTO.setName(sheet.getSheetName());
            RichTextString emptyTextString = null;
            if (sheet.getRow(0) != null && sheet.getRow(0).getCell(0) != null && !isTemplateProcess) {
                Cell cell = sheet.getRow(0).getCell(0);
                if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    String modelVisId = cell.getStringCellValue();
                    if (modelVisId != null && !modelVisId.trim().isEmpty()) {
                        cell.setCellValue(emptyTextString); // clear cell
                        Map<String, String> properties = flatFormExtractorDao.getPropertiesForModelVisId(modelVisId);
                        worksheetDTO.setProperties(properties);
                    }
                }
            } else if (isTemplateProcess && !workbook.isSheetHidden(i) && !workbook.isSheetVeryHidden(i)) {
                // Find cell A1, set its value as sheet title
                CellReference cellReference = new CellReference("A1");
                Row row = sheet.getRow(cellReference.getRow());
                if (row != null) {
                    Cell cell = row.getCell(cellReference.getCol());
                    if (cell != null) {
                        cell.setCellValue(sheet.getSheetName());
                        // Change font colour of A1 to white
                        cell.setCellStyle(style);
                    }
                }
            }
            worksheetsDTO.add(worksheetDTO);
        }

        WorkbookMapper.manageWorksheetsCells(workbook, worksheetsDTO, isTemplateProcess);
        return worksheetsDTO;
    }

    /* Get workbookDTO, insert values and return.
     * 
     * @see com.softproideas.app.flatformeditor.form.service.FormService#workbookTest(com.softproideas.app.core.flatform.model.WorkbookDTO) */
    @Override
    public WorkbookDTO workbookTest(WorkbookDTO workbookDTO) throws Exception {
        WorkbookUtil.convertWorkbookDTOToExtendedVersion(workbookDTO);

        if (taskExecutor == null) {
            CPConnection connection = cpContextHolder.getCPConnection();
            FlatFormExtractor flatFormExtractor = new FlatFormExtractor(connection, flatFormExtractorDao);
            flatFormExtractor.getValuesForMappings(workbookDTO, connection);
        } else {
            CPConnection connection = cpContextHolder.getCPConnection();
            FlatFormExtractor flatFormExtractor = new FlatFormExtractor(connection, flatFormExtractorDao);
            flatFormExtractor.getValuesForMappings(workbookDTO, connection);
        }

        return workbookDTO;
    }

    @Override
    public ResponseMessage copyTemplate(List<Integer> flatFormIds) throws ServiceException, CPException, ValidationException {
        int sourceFlatFormId = flatFormIds.get(0);
        XmlFormPK sourcePK = new XmlFormPK(sourceFlatFormId);
        List<XmlFormPK> privateKeys = new ArrayList<XmlFormPK>();
        for (int i = 1; i < flatFormIds.size(); i++) {
            int flatFormId = flatFormIds.get(i);
            if (flatFormId != sourceFlatFormId) {
                XmlFormPK flatFormPK = new XmlFormPK(flatFormId);
                privateKeys.add(flatFormPK);
            }
        }
        try {
            if (!privateKeys.isEmpty()) {
                CPConnection connection = cpContextHolder.getCPConnection();
                XcellDistributionUtil xcellDistributionUtil = new XcellDistributionUtil(connection);
                xcellDistributionUtil.distributeXcellForm(sourcePK, privateKeys);
                ResponseMessage success = new ResponseMessage(true);
                return success;
            }
            DefaultError error = new DefaultError("Error", "Please select any Flat Form");
            return error;
        } catch (ValidationException e) {
            e.printStackTrace();
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            e.printStackTrace();
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        }
    }
    public ResponseMessage toggleLockFlag(int xmlFormId, boolean flag){
    	flatFormExtractorDao.toggleLockFlag(cpContextHolder.getUserId(),xmlFormId,flag);
    	
    	ResponseMessage success = new ResponseMessage(true);
        return success;
    }
    public ResponseMessage getLockFlag(int xmlFormId){
    	char flag = flatFormExtractorDao.getLockFlag(xmlFormId);
    	ResponseMessage success = new ResponseMessage(true);
    	Map<String,Object> data =new HashMap<String,Object>();
    	data.put("flag", flag);
    	success.setData(data);
        return success;
    }
}
