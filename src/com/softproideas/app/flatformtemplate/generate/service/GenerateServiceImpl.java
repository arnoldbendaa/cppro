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
package com.softproideas.app.flatformtemplate.generate.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.naming.InitialContext;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.dto.xmlform.GenerateFlatFormsTaskRequest;
import com.cedar.cp.util.flatform.model.workbook.CellDTO;
import com.cedar.cp.util.flatform.model.workbook.TemplateCellDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorksheetDTO;
import com.cedar.cp.util.flatform.model.workbook.editor.WorkbookMapper;
import com.cedar.cp.util.task.TaskMessageFactory;
import com.softproideas.app.admin.forms.flatforms.service.FlatFormsService;
import com.softproideas.app.core.dimension.model.DimensionWithHierarchiesCoreDTO;
import com.softproideas.app.core.financecube.model.FinanceCubeModelCoreDTO;
import com.softproideas.app.core.financecube.service.FinanceCubeCoreService;
import com.softproideas.app.core.financecube.util.FinanceCubeModelCoreUtil;
import com.softproideas.app.core.flatform.model.FlatFormExtendedCoreDTO;
import com.softproideas.app.core.users.mapper.UserCoreMapper;
import com.softproideas.app.core.users.model.UserCoreDTO;
import com.softproideas.app.core.workbook.model.FlatFormExtractorDao;
import com.softproideas.app.flatformeditor.form.service.FormService;
import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationDetailsDTO;
import com.softproideas.app.flatformtemplate.configuration.model.DimensionForFlatFormTemplateDTO;
import com.softproideas.app.flatformtemplate.configuration.model.TotalDTO;
import com.softproideas.app.flatformtemplate.configuration.service.ConfigurationService;
import com.softproideas.app.flatformtemplate.generate.model.CompleteFlatFormIgredients;
import com.softproideas.app.flatformtemplate.generate.model.CompleteWorkbook;
import com.softproideas.app.flatformtemplate.generate.model.GenerateDTO;
import com.softproideas.app.flatformtemplate.generate.util.GenerateUtil;
import com.softproideas.app.flatformtemplate.template.mapper.TemplateMapper;
import com.softproideas.app.flatformtemplate.template.model.TemplateDetailsDTO;
import com.softproideas.app.flatformtemplate.template.service.TemplateService;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;

@Service("generateService")
public class GenerateServiceImpl implements GenerateService {

    @Autowired
    CPContextHolder cpContextHolder;

    @Autowired
    ConfigurationService configurationService;

    @Autowired
    TemplateService templateService;

    @Autowired
    FormService formService;

    @Autowired
    FlatFormsService flatFormsService;

    @Autowired
    FinanceCubeCoreService financeCubeCoreService;
    
    @Autowired
    private FlatFormExtractorDao flatFormExtractorDao;

    // ///////////////////////////////////////////////////

    @Override
    public ResponseMessage generateForms(GenerateDTO generateDTO) throws ServiceException, CloneNotSupportedException {
        ResponseMessage ifDuplicate = checkIfDuplicate(generateDTO);
        if (ifDuplicate != null) {
            return ifDuplicate;
        }

        CompleteFlatFormIgredients completeFlatFormIgredients = generateCompleteFlatFormIgredients(generateDTO);

        // fill templates if hierarchy based
        if (completeFlatFormIgredients.getTemplateDetailsDTO().getType().equals(TemplateMapper.mapType(2))) {
            fillFlatFormTemplateHierarchyBased(generateDTO, completeFlatFormIgredients);
        } else if (completeFlatFormIgredients.getTemplateDetailsDTO().getType().equals(TemplateMapper.mapType(1))) {
            fillFlatFormTemplateStandard(completeFlatFormIgredients);
        }

        GenerateFlatFormsTaskRequest request = buildRequestTask(generateDTO, completeFlatFormIgredients);
        return IssueNewTask(request);
    }

    private ResponseMessage checkIfDuplicate(GenerateDTO generateDTO) throws ServiceException, CloneNotSupportedException {
        List<FlatFormExtendedCoreDTO> flatForms = flatFormsService.fetchFlatForms();
        Boolean override = generateDTO.getOverride();
        List<String> duplicates = GenerateUtil.checkIfDuplicatesExists(generateDTO, flatForms);
        if (generateDTO.isLastRequest() == false) {
            if (duplicates.size() > 0) {
                String message = duplicates.toString();
                return new ResponseMessage(false, message);
            }
        } else if (duplicates.size() == generateDTO.getFinanceCubeModels().size() && override == false) {
            return new ResponseMessage(true);
        }
        return null;
    }

    private CompleteFlatFormIgredients generateCompleteFlatFormIgredients(GenerateDTO generateDTO) throws ServiceException {
        UUID templateUUID = generateDTO.getTemplateUUID();
        TemplateDetailsDTO templateDetailsDTO = templateService.fetchTemplate(templateUUID);
        String jsonForm = templateDetailsDTO.getJsonForm();
        byte[] excelFile = toXLSX(jsonForm);
        Workbook workbook = getWorkbook(excelFile);

        // workbook.setForceFormulaRecalculation(false);
        WorkbookDTO workbookDTO = formService.manageXmlForm(workbook, true);
        CompleteWorkbook completeWorkbook = new CompleteWorkbook(workbook, workbookDTO);

        CompleteFlatFormIgredients completeFlatFormIgredients = new CompleteFlatFormIgredients(excelFile, completeWorkbook, templateDetailsDTO);
        return completeFlatFormIgredients;
    }

    private Workbook getWorkbook(byte[] file) throws ServiceException {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(file);
            Workbook workbook = WorkbookFactory.create(bis);
            bis.close();
            return workbook;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void fillFlatFormTemplateHierarchyBased(GenerateDTO generateDTO, CompleteFlatFormIgredients completeFlatFormIgredients) throws ServiceException, CloneNotSupportedException {
        UUID configurationUUID = generateDTO.getConfigurationUUID();
        ConfigurationDetailsDTO configurationDetailsDTO = null;
        if (configurationUUID == null) {
            throw new ServiceException("Configuration should be chosen for " + TemplateMapper.mapType(2) + " template [" + completeFlatFormIgredients.getTemplateDetailsDTO().getVisId() + "].");
        }
        configurationDetailsDTO = configurationService.fetchConfiguration(configurationUUID);
        fillTemplateSheets(completeFlatFormIgredients.getCompleteWorkbook(), configurationDetailsDTO);
        setWorksheetPropertiesForHierarchy(completeFlatFormIgredients.getCompleteWorkbook().getWorkbookDTO(), configurationDetailsDTO);
    }

    private void fillTemplateSheets(CompleteWorkbook completeWorkbook, ConfigurationDetailsDTO configurationDetailsDTO) throws CloneNotSupportedException {
        List<WorksheetDTO> worksheetList = completeWorkbook.getWorkbookDTO().getWorksheets();
        int sum = configurationDetailsDTO.getDimensions().size() + configurationDetailsDTO.getTotals().size();
        int sheetTemplateNum = worksheetList.size() - 1;
        WorksheetDTO lastWorksheet = worksheetList.get(sheetTemplateNum);
        Workbook workbook = completeWorkbook.getWorkbook();
        int sheetTemplateIndex = workbook.getSheetIndex(lastWorksheet.getName());

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        for (int i = 0; i < sum - 1; i++) {
            workbook.cloneSheet(sheetTemplateIndex);
            worksheetList.add((WorksheetDTO) lastWorksheet.clone());
        }

        fillTemplateCellsWithDimensions(configurationDetailsDTO, completeWorkbook, sheetTemplateNum, style);
        fillTemplateCellsWithTotals(configurationDetailsDTO, completeWorkbook, sheetTemplateNum, style);
    }

    private void fillTemplateCellsWithDimensions(ConfigurationDetailsDTO configurationDetailsDTO, CompleteWorkbook completeWorkbook, int sheetTemplateNum, CellStyle style) {
        List<DimensionForFlatFormTemplateDTO> dimensionList = configurationDetailsDTO.getDimensions();
        for (DimensionForFlatFormTemplateDTO dimension: dimensionList) {
            fillTemplateCellsWithDimension(dimension, completeWorkbook, sheetTemplateNum, style, dimensionList);
        }
    }

    private void fillTemplateCellsWithDimension(DimensionForFlatFormTemplateDTO dimension, CompleteWorkbook completeWorkbook, int sheetTemplateNum, CellStyle style, List<DimensionForFlatFormTemplateDTO> dimensionList) {
        int index = dimension.getIndex() - 1;
        // set excel worksheet name and hidden property
        completeWorkbook.getWorkbook().setSheetName(sheetTemplateNum + index, dimension.getSheetName());
        completeWorkbook.getWorkbook().setSheetHidden(sheetTemplateNum + index, dimension.isHidden());

        Sheet sheet = completeWorkbook.getWorkbook().getSheet(dimension.getSheetName());
        // Find cell A1, set its value as sheet title
        CellReference cellReference = new CellReference("A1");
        Row rowA = sheet.getRow(cellReference.getRow());
        if (rowA != null) {
            Cell cellA1 = rowA.getCell(cellReference.getCol());
            if (cellA1 != null) {
                cellA1.setCellValue(sheet.getSheetName());
                // Change font colour of A1 to white
                cellA1.setCellStyle(style);
            }
        }
        // set definition worksheet name
        WorksheetDTO worksheetDTO = completeWorkbook.getWorkbookDTO().getWorksheets().get(sheetTemplateNum + index);
        worksheetDTO.setName(dimension.getSheetName());

        String dim0 = dimension.getDimensionVisId();
        List<? extends CellDTO> cellsList = worksheetDTO.getCells();
        List<CellDTO> cellsListToDelete = new ArrayList<CellDTO>();

        for (CellDTO cellDTO: cellsList) {
            if (cellDTO instanceof TemplateCellDTO) {
                TemplateCellDTO xCellFormCellDTO = (TemplateCellDTO) cellDTO;
                if (xCellFormCellDTO.isTotal()) {
                    Cell cell = sheet.getRow(xCellFormCellDTO.getRow()).getCell(xCellFormCellDTO.getColumn());
                    setDefaultColor(cell);
                }
            }

            String inputMapping = cellDTO.getInputMapping();
            if (inputMapping != null && !inputMapping.isEmpty() && (inputMapping.contains("dim0=,") || inputMapping.contains("costc=,"))) {
                inputMapping = inputMapping.replaceAll("dim0=,", "dim0=" + dim0 + ","); // getCell IM
                inputMapping = inputMapping.replaceAll("costc=,", "costc=" + dim0 + ","); // parameterLookup IM
                if (dimension.getExcludedDimensions() == null || dimension.getExcludedDimensions().isEmpty()) {
                    cellDTO.setInputMapping(inputMapping);
                } else if (!inputMapping.startsWith("cedar.cp.getCell")) {
                    cellDTO.setInputMapping(inputMapping);
                } else {
                    // String excludedDimensionsFormula = generateExcludedDimensionFormula(inputMapping, dimension.getExcludedDimensions(), dim0, dimensionList, cellDTO);
                    ArrayList<String> excludedDimensionFormulaList;
                    ArrayList<String> excludeDimensions = (ArrayList<String>) dimension.getExcludedDimensions().clone();
                    excludedDimensionFormulaList = generateFormulaExcludedDimensionFromCells(excludeDimensions, dimensionList, cellDTO);
                    excludedDimensionFormulaList.addAll(generateFormulaExcludedDimensionToCalculate(cellDTO.getInputMapping(), excludeDimensions));
                    String excludedDimensionsFormula = generateExcludedDimensionFormula(inputMapping, excludedDimensionFormulaList);
                    cellDTO.setText(excludedDimensionsFormula);
                    cellDTO.setInputMapping(null);
                }
            }
            String outputMapping = cellDTO.getOutputMapping();
            if (outputMapping != null && !outputMapping.isEmpty() && (outputMapping.contains("dim0=,") || outputMapping.contains("costc=,"))) {
                outputMapping = outputMapping.replaceAll("dim0=,", "dim0=" + dim0 + ",");
                outputMapping = outputMapping.replaceAll("costc=,", "costc=" + dim0 + ",");
                cellDTO.setOutputMapping(outputMapping);
            }
            String text = cellDTO.getText();
            if (text != null && !text.isEmpty() && (text.contains("dim0=,") || text.contains("costc=,"))) {
                text = text.replaceAll("dim0=,", "dim0=" + dim0 + ",");
                text = text.replaceAll("costc=,", "costc=" + dim0 + ",");
                cellDTO.setText(text);
            }
            if (inputMapping == null && outputMapping == null && text == null) {
                cellsListToDelete.add(cellDTO);
            }
        }

        cellsList.removeAll(cellsListToDelete);
    }

    private ArrayList<String> generateFormulaExcludedDimensionFromCells(ArrayList<String> excludedDimensions, List<DimensionForFlatFormTemplateDTO> dimensionList, CellDTO cellDTO) {
        ArrayList<String> results = new ArrayList<String>();
        String excludedDimensionFormula;
        for (Iterator<String> it = excludedDimensions.iterator(); it.hasNext();) {
            String excludedDimension = it.next();
            for (DimensionForFlatFormTemplateDTO dimension: dimensionList) {
                if (dimension.getDimensionVisId().equals(excludedDimension) && (dimension.getExcludedDimensions() == null || dimension.getExcludedDimensions().isEmpty())) {
                    excludedDimensionFormula = "'" + dimension.getSheetName() + "'!" + CellReference.convertNumToColString(cellDTO.getColumn()) + String.valueOf(cellDTO.getRow() + 1);
                    results.add(excludedDimensionFormula);
                    it.remove();
                }
            }
        }
        return results;
    }

    private ArrayList<String> generateFormulaExcludedDimensionToCalculate(String inputMapping, ArrayList<String> excludedDimensions) {
        ArrayList<String> results = new ArrayList<String>();
        String excludedDimensionFormula;
        for (String excludedDimension: excludedDimensions) {
            excludedDimensionFormula = inputMapping.replaceAll("dim0=,", "dim0=" + excludedDimension + ",");
            excludedDimensionFormula = excludedDimensionFormula.replaceAll("costc=,", "costc=" + excludedDimension + ",");
            results.add(excludedDimensionFormula);
        }
        return results;
    }

    private String generateExcludedDimensionFormula(String inputMapping, ArrayList<String> excludedDimensions) {
        String resultText = "=" + inputMapping;
        for (String excludedDimension: excludedDimensions) {
            resultText = resultText + " - " + excludedDimension;
        }
        return resultText;
    }

    private void fillTemplateCellsWithTotals(ConfigurationDetailsDTO configurationDetailsDTO, CompleteWorkbook completeWorkbook, int sheetTemplateNum, CellStyle style) {
        List<TotalDTO> totalList = configurationDetailsDTO.getTotals();
        for (TotalDTO item: totalList) {
            int index = item.getIndex() - 1;
            // set excel worksheet name and hidden property
            completeWorkbook.getWorkbook().setSheetName(sheetTemplateNum + index, item.getSheetName());
            completeWorkbook.getWorkbook().setSheetHidden(sheetTemplateNum + index, item.isHidden());

            Sheet sheet = completeWorkbook.getWorkbook().getSheet(item.getSheetName());
            // Find cell A1, set its value as sheet title
            CellReference cellReference = new CellReference("A1");
            Row rowA = sheet.getRow(cellReference.getRow());
            if (rowA != null) {
                Cell cellA1 = rowA.getCell(cellReference.getCol());
                if (cellA1 != null) {
                    cellA1.setCellValue(sheet.getSheetName());
                    // Change font colour of A1 to white
                    cellA1.setCellStyle(style);
                }
            }
            // set definition worksheet name
            WorksheetDTO work = completeWorkbook.getWorkbookDTO().getWorksheets().get(sheetTemplateNum + index);
            work.setName(item.getSheetName());

            List<? extends CellDTO> cellsList = work.getCells();
            for (CellDTO cellDTO: cellsList) {

                String inputMapping = cellDTO.getInputMapping();
                if (inputMapping != null && !inputMapping.isEmpty() && (inputMapping.contains("dim0=,") || inputMapping.contains("costc=,"))) {
                    cellDTO.setInputMapping(null);
                }
                String outputMapping = cellDTO.getOutputMapping();
                if (outputMapping != null && !outputMapping.isEmpty() && (outputMapping.contains("dim0=,") || outputMapping.contains("costc=,"))) {
                    cellDTO.setOutputMapping(null);
                }
                String text = cellDTO.getText();
                if (text != null && !text.isEmpty() && (text.contains("dim0=,") || text.contains("costc=,"))) {
                    cellDTO.setText(null);
                }

                if (cellDTO instanceof TemplateCellDTO) {

                    TemplateCellDTO xCellFormCellDTO = (TemplateCellDTO) cellDTO;
                    if (xCellFormCellDTO.isTotal()) {
                        xCellFormCellDTO.setInputMapping(null);
                        xCellFormCellDTO.setOutputMapping(null);

                        String column = CellReference.convertNumToColString(xCellFormCellDTO.getColumn());
                        int row = xCellFormCellDTO.getRow() + 1;
                        String cellAddress = column + row;

                        text = "=";
                        if (item.isGrandTotal()) { // sum all totals
                            for (TotalDTO total: totalList) {
                                if (!total.isGrandTotal()) {
                                    text = text + " + &apos;" + total.getSheetName() + "&apos;!" + cellAddress;
                                }
                            }
                        } else { // sum specified dims
                            List<DimensionForFlatFormTemplateDTO> dimensionList = item.getDimensionList();
                            for (DimensionForFlatFormTemplateDTO dim: dimensionList) {
                                text = text + " + &apos;" + dim.getSheetName() + "&apos;!" + cellAddress;
                            }
                        }

                        Cell cell = sheet.getRow(xCellFormCellDTO.getRow()).getCell(xCellFormCellDTO.getColumn());
                        setDefaultColor(cell);
                        xCellFormCellDTO.setText(text);
                        cellDTO = xCellFormCellDTO;
                    }

                }

            }
        }
    }

    private void setWorksheetPropertiesForHierarchy(WorkbookDTO workbookDTO, ConfigurationDetailsDTO configurationDetailsDTO) {
//        List<FinanceCubeModelCoreDTO> financeCubeModelCoreListDTO = financeCubeCoreService.browseFinanceCubes();

        List<DimensionForFlatFormTemplateDTO> dimensions = configurationDetailsDTO.getDimensions();
        List<WorksheetDTO> worksheets = workbookDTO.getWorksheets();

        for (DimensionForFlatFormTemplateDTO dimension: dimensions) {
            String modelVisId = dimension.getModelVisId();
//            FinanceCubeModelCoreDTO financeCube = FinanceCubeModelCoreUtil.findFinanceCubeByModelVisId(modelVisId, financeCubeModelCoreListDTO);
//            List<DimensionWithHierarchiesCoreDTO> dimensionList = formService.fetchModelDimensionsWithHierarchies(financeCube.getModel().getModelId());
//            Map<String, String> properties = WorkbookMapper.manageWorksheetProperties(financeCube, dimensionList);
            Map<String, String> properties = flatFormExtractorDao.getPropertiesForModelVisId(modelVisId);

            for (WorksheetDTO worksheet: worksheets) {
                if (worksheet.getName().equals(dimension.getSheetName())) {
                    worksheet.setProperties(properties);
                    break;
                }
            }
        }
    }

    /**
     * @param workbookDTO
     * @param workbook
     */
    private void fillFlatFormTemplateStandard(CompleteFlatFormIgredients completeFlatFormIgredients) {
        WorkbookDTO workbookDTO = completeFlatFormIgredients.getCompleteWorkbook().getWorkbookDTO();
        WorkbookDTO workbook = completeFlatFormIgredients.getTemplateDetailsDTO().getWorkbook();
        setWorksheetPropertiesForSimple(workbookDTO, workbook);
    }

    private void setWorksheetPropertiesForSimple(WorkbookDTO workbookDTO, WorkbookDTO workbookWithProperties) {

        // workbook
        Map<String, String> propertiesWorkbook = workbookWithProperties.getProperties();
        workbookDTO.setProperties(propertiesWorkbook);

        // worksheets
        List<WorksheetDTO> worksheets = workbookDTO.getWorksheets();
        List<WorksheetDTO> worksheetsWithProperties = workbookWithProperties.getWorksheets();

        for (WorksheetDTO worksheetDTO: worksheets) {
            String worksheetName = worksheetDTO.getName();
            for (WorksheetDTO worksheetPropertiesDTO: worksheetsWithProperties) {
                String worksheetPropName = worksheetPropertiesDTO.getName();
                if (worksheetPropName.equals(worksheetName)) {
                    Map<String, String> properties = worksheetPropertiesDTO.getProperties();
                    worksheetDTO.setProperties(properties);
                }
            }
        }
    }

    private GenerateFlatFormsTaskRequest buildRequestTask(GenerateDTO generateDTO, CompleteFlatFormIgredients completeFlatFormIgredients) throws CloneNotSupportedException, ServiceException {
        Map<Integer, String> visIds = new HashMap<Integer, String>();
        Map<Integer, String> definitions = new HashMap<Integer, String>();
        List<FinanceCubeModelCoreDTO> financeCubeModels = generateDTO.getFinanceCubeModels();
        for (FinanceCubeModelCoreDTO financeCubeModel: financeCubeModels) {
            int financeCubeId = financeCubeModel.getFinanceCubeId();

            // VisIds
            String visId = GenerateUtil.getCompanyFromModelVisId(financeCubeModel.getModel().getModelVisId()) + " - " + generateDTO.getName();
            visIds.put(financeCubeId, visId);

            // Definitions
            WorkbookDTO newWorkbookDTO = (WorkbookDTO) completeFlatFormIgredients.getCompleteWorkbook().getWorkbookDTO().clone();
            setWorksheetPropertiesForFinanceCube(newWorkbookDTO, financeCubeModel.getModel().getModelVisId());
            String definition = WorkbookMapper.mapWorkbookDTOToXmlString(newWorkbookDTO);
            definitions.put(financeCubeId, definition);
        }

        List<UserRefImpl> userIds = setUserIdsToRequest(completeFlatFormIgredients.getTemplateDetailsDTO());

        byte[] excelFile = getExcelFile(completeFlatFormIgredients.getCompleteWorkbook().getWorkbook());

        String description = generateDTO.getDescription();
        GenerateFlatFormsTaskRequest request = new GenerateFlatFormsTaskRequest();
        request.setDescription(description);
        request.setOverride(generateDTO.getOverride());
        request.setExcelFile(excelFile);
        request.setVisIds(visIds);
        request.setUserIds(userIds);
        request.setDefinitions(definitions);
        return request;
    }

    private void setWorksheetPropertiesForFinanceCube(WorkbookDTO workbookDTO, String modelVisId) throws ServiceException {
//        int modelId = financeCubeModerCoreDTO.getModel().getModelId();
//        List<DimensionWithHierarchiesCoreDTO> dimensionList = formService.fetchModelDimensionsWithHierarchies(modelId);
//        Map<String, String> properties = WorkbookMapper.manageWorksheetProperties(financeCubeModerCoreDTO, dimensionList);
        Map<String, String> properties = flatFormExtractorDao.getPropertiesForModelVisId(modelVisId);

        List<WorksheetDTO> worksheets = workbookDTO.getWorksheets();
        for (WorksheetDTO worksheetDTO: worksheets) {
            if (worksheetDTO.getProperties() == null || worksheetDTO.getProperties().size() == 0) {
                String worksheetName = worksheetDTO.getName();
                if (worksheetName.equalsIgnoreCase("menu")) {
//                    List<FinanceCubeModelCoreDTO> financeCubeModelCoreListDTO = financeCubeCoreService.browseFinanceCubes();
//                    FinanceCubeModelCoreDTO financeCubeModerCoreDTOMenu = FinanceCubeModelCoreUtil.findFinanceCubeByModelVisId("1/1", financeCubeModelCoreListDTO);
//                    List<DimensionWithHierarchiesCoreDTO> dimensionListMenu = formService.fetchModelDimensionsWithHierarchies(financeCubeModerCoreDTOMenu.getModel().getModelId());
//                    Map<String, String> propertiesMenu = WorkbookMapper.manageWorksheetProperties(financeCubeModerCoreDTOMenu, dimensionListMenu);
                    Map<String, String> propertiesMenu = flatFormExtractorDao.getPropertiesForModelVisId("1/1");
                    worksheetDTO.setProperties(propertiesMenu);
                } else {
                    worksheetDTO.setProperties(properties);
                }
            }

        }
    }

    private List<UserRefImpl> setUserIdsToRequest(TemplateDetailsDTO templateDetailsDTO) {
        List<UserRefImpl> list = UserCoreMapper.mapUserCoreDTO(templateDetailsDTO.getUsers());

        List<UserCoreDTO> users = templateDetailsDTO.getUsers();
        List<Integer> userIds = new ArrayList<Integer>();
        for (UserCoreDTO user: users) {
            int userId = user.getUserId();
            userIds.add(userId);
        }
        return list;
    }

    private byte[] getExcelFile(Workbook workbook) throws ServiceException {
        try {
            return workbookToByteArray(workbook);
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private ResponseMessage IssueNewTask(GenerateFlatFormsTaskRequest request) throws ServiceException {
        try {
            int userId = cpContextHolder.getUserId();
            int issueNewTask = TaskMessageFactory.issueNewTask(new InitialContext(), true, request, userId);
            return new ResponseMessage(true, "Forms have been generated successfully with taskID = " + issueNewTask);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void setDefaultColor(Cell cell) {
        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle != null && cellStyle instanceof XSSFCellStyle) {
            XSSFCellStyle xssfCellStyle = (XSSFCellStyle) cellStyle;
            // TODO: Check after pattern connection
            // xssfCellStyle.setFillPattern(CellStyle.NO_FILL);
            xssfCellStyle.setFillForegroundColor((short) 0);
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

    /* Wijmo converter */
    private byte[] toXLSX(String json) throws ServiceException {
        try {
            InputStream is = cpContextHolder.getCPConnection().getExcelIOProcess().convertJsonToXlsx(json, "");
            byte[] excelFile = IOUtils.toByteArray(is);
            is.close();
            return excelFile;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
