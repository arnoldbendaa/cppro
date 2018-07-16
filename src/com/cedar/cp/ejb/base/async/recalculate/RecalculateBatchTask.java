package com.cedar.cp.ejb.base.async.recalculate;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.dataEntry.DataEntryProcess;
import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskRequest;
import com.cedar.cp.dto.user.DataEntryProfilePK;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.flatform.model.workbook.CellDTO;
import com.cedar.cp.util.flatform.model.workbook.CellExtendedDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.flatform.model.workbook.WorksheetDTO;
import com.softproideas.api.cpfunctionsevaluator.FlatFormExtractor;
import com.softproideas.server.recalculate.dto.ElementDTO;
import com.softproideas.server.recalculate.dto.ExcelFormDTO;
import com.softproideas.util.validation.MappingFunction;
import com.softproideas.util.validation.MappingValidator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class RecalculateBatchTask extends AbstractTask {

    private static final long serialVersionUID = -1899557923767606205L;
    private transient InitialContext mInitialContext;
    private transient Log mLog;
    protected CPConnection mConnection;

    public RecalculateBatchTask() {
        mLog = new Log(getClass());
    }

    public int getReportType() {
        return 8;
    }

    public RecalculateCheckpoint getCheckpoint() {
        return (RecalculateCheckpoint) super.getCheckpoint();
    }

    public String getEntityName() {
        return "RecalculateBatchTask";
    }

    public void runUnitOfWork(InitialContext initialContext) throws Exception {
        this.mInitialContext = initialContext;

        if (!(this.getRequest() instanceof RecalculateBatchTaskRequest)) {
            return;
        }

        mLog.debug("runUnitOfWork", "Starting RecalculateBatchTask");
        setCheckpoint(new RecalculateCheckpoint());

        RecalculateBatchTaskRequest request = (RecalculateBatchTaskRequest) this.getRequest();

        // Get data from request
        String identifier = request.getIdentifier();
        int userId = request.getUserId();
        int modelId = request.getModelId();
        int budgetCycleId = request.getBudgetCycleId();
        List<Integer> budgetRespAreas = request.getBudgetRespAreas();
        List<DataEntryProfileRef> dataEntryProfiles = request.getDataEntryProfiles();

        // Initialize processes
        DataEntryProcess dataEntryProcess = this.getCPConnection().getDataEntryProcess();

        this.logInfo("Starting RecalculateBatchTask '" + identifier + "'");

        int j = 0;
        // Budget Responsibility Area
        for (Integer budgetRespArea: budgetRespAreas) {

            j++;
            this.log("----- " + String.valueOf(j) + ". Budget Responsibility Area Id: " + budgetRespArea.toString());

            // Profiles
            for (DataEntryProfileRef dataEntryProfile: dataEntryProfiles) {

                this.log("----- " + String.valueOf(j) + "." + String.valueOf(j) + ". Recalculate profile '" + dataEntryProfile.getDisplayText() + "'");

                ExcelFormDTO excelFormDTO = fetchXcellForm(((DataEntryProfilePK) dataEntryProfile.getPrimaryKey()).getDataEntryProfileId(), budgetRespArea, modelId);

                org.apache.poi.ss.usermodel.Workbook excelWorkbook = null;
                byte[] excelFile = excelFormDTO.getExcelFile();
                if (excelFile == null) {
                    continue;
                } else {
                    excelWorkbook = byteToWorkbook(excelFile);
                }
                evaluateMappings(excelWorkbook, excelFormDTO.getWorkbook());

                // 3. Build XML Updates
                String mXMLUpdate = this.buildXMLUpdates(excelFormDTO.getWorkbook(), excelWorkbook, modelId);
                System.out.println(mXMLUpdate);
                System.out.println("####################################");
                // 4. Submit change workbook
                String xml = MessageFormat.format(mXMLUpdate, new Object[] { String.valueOf(userId), String.valueOf(budgetCycleId) });
                dataEntryProcess.executeForegroundFlatFormUpdate(xml);
            }
        }

        mLog.debug("runUnitOfWork", "Ending  task");
        setCheckpoint(null);
    }

    private ExcelFormDTO fetchXcellForm(int dataEtryProfile, int topNodeId, int modelId) throws Exception {
        ModelDAO dao = new ModelDAO();
        ExcelFormDTO xCellFormDTO = dao.getXCellFormDTO(dataEtryProfile);

        Map<Integer, Integer> selectionsMap = new HashMap<Integer, Integer>();
        selectionsMap.put(0, topNodeId);
        Integer dim2 = Integer.valueOf(xCellFormDTO.getContextVariables().get("2"));
        selectionsMap.put(2, dim2);
        xCellFormDTO.setContextVariables(null);
        Map<String, String> contextVariables = new HashMap<String, String>();
        List<ElementDTO> selectedDimensions = dao.getSelectedDimensions(selectionsMap, modelId);
        for (int i = 0; i < selectedDimensions.size(); i++) {
            contextVariables.put(WorkbookProperties.DIMENSION_$_VISID.toString().replace("$", String.valueOf(i)), selectedDimensions.get(i).getName());
        }

        xCellFormDTO.setSelectedDimension(selectedDimensions);

        contextVariables.put(WorkbookProperties.MODEL_ID.toString(), String.valueOf(modelId));

        WorkbookDTO workbook = xCellFormDTO.getWorkbook();
        workbook.getProperties().putAll(contextVariables);
        FlatFormExtractor extractor = new FlatFormExtractor(this.getCPConnection());
//        extractor.getValuesForMappings(workbook, this.getCPConnection());//commented by arnold
        

        return xCellFormDTO;
    }

    private static Boolean isTextFormat(short dataFormat) {
        // https://poi.apache.org/apidocs/org/apache/poi/ss/usermodel/BuiltinFormats.html
        return dataFormat == 49;
    }

    private static void evaluateMappings(Workbook excelWorkbook, WorkbookDTO workbook) {
        // Date format
        // DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // go through sheets in workbook
        for (WorksheetDTO worksheet: workbook.getWorksheets()) {
            Sheet excelSheet = excelWorkbook.getSheet(worksheet.getName());
            excelSheet.getRow(0).getCell(0);
            List<? extends CellDTO> cells = worksheet.getCells();

            CellStyle dateCellStyle = excelWorkbook.createCellStyle();
            short df = excelWorkbook.createDataFormat().getFormat("dd-MMM-yy");

            for (int i = 0; i < cells.size(); i++) {
                CellExtendedDTO cell = (CellExtendedDTO) cells.get(i);
                Cell excelCell = excelSheet.getRow(cell.getRow()).getCell(cell.getColumn());

                boolean formula = cell.getFormula() == null || cell.getFormula().length() <= 1 ? false : cell.getFormula().startsWith("=");

                if (formula) {
                    excelCell.setCellFormula(cell.getFormula().substring(1)); // remove "="
                } else {
                    short dataFormat = excelCell.getCellStyle().getDataFormat();
                    String text = cell.getText();
                    if (cell.getDate() != null) {
                        try {
                            Date date = dateFormat.parse(cell.getDate());
                            excelCell.setCellValue(date);
                            dateCellStyle.cloneStyleFrom(excelCell.getCellStyle());
                            dateCellStyle.setDataFormat(df);
                            excelCell.setCellStyle(dateCellStyle);
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else if (cell.getValue() != null) {
                        excelCell.setCellValue(cell.getValue());
                    } else if (text == null && isTextFormat(dataFormat)) {
                        excelCell.setCellValue("");
                    } else if (text == null && !isTextFormat(dataFormat)) {
                        excelCell.setCellValue(0);
                    } else {
                        text = text.equals("null") ? "" : text;
                        excelCell.setCellValue(text);
                    }
                }
            }
        }
    }

    private org.apache.poi.ss.usermodel.Workbook byteToWorkbook(byte[] excelFile) throws IOException, InvalidFormatException {
        InputStream is = new ByteArrayInputStream(excelFile);
        org.apache.poi.ss.usermodel.Workbook excelWorkbook = WorkbookFactory.create(is);
        is.close();
        return excelWorkbook;
    }

    private String buildXMLUpdates(WorkbookDTO workbook, org.apache.poi.ss.usermodel.Workbook poiWorkbook, int modelId) throws Exception {
        FormulaEvaluator evaluator = poiWorkbook.getCreationHelper().createFormulaEvaluator();

        StringBuffer xml = new StringBuffer();
        xml.append("<WorkbookUpdate>");
        xml.append("<UserId>{0}</UserId>");
        xml.append("<BudgetCycleId>{1}</BudgetCycleId>");
        xml.append("<Parameters>");
        xml.append("<Parameter name=\"FormType\" value=\"6\" />");

        Map<String, String> workbookProperties = workbook.getProperties();
        for (String key: workbookProperties.keySet()) {
            xml.append("<Parameter name=\"" + key + "\" value=\"" + XmlUtils.escapeStringForXML(workbookProperties.get(key)) + "\" />");
        }

        xml.append("</Parameters>");

        List<WorksheetDTO> worksheets = workbook.getWorksheets();
        for (WorksheetDTO worksheet: worksheets) {
            xml.append("<Worksheet name=\"" + XmlUtils.escapeStringForXML(worksheet.getName()) + "\" >");
            xml.append("<Properties>");
            // Iterator<Entry<String, String>> iteratorPropertiesWorksheet;
            if (worksheet.getProperties() != null) {
                Map<String, String> worksheetProperties = worksheet.getProperties();
                for (String key: worksheetProperties.keySet()) {
                    xml.append("<Property name=\"" + key + "\" value=\"" + XmlUtils.escapeStringForXML(worksheetProperties.get(key)) + "\" />");
                }
            }
            xml.append("</Properties>");
            xml.append("<Cells>");

            List<CellExtendedDTO> cells = (List<CellExtendedDTO>) worksheet.getCells();
            for (CellExtendedDTO cell: cells) {
                if ((cell != null) && (!GeneralUtils.isEmptyOrNull(cell.getOutputMapping()))) {
                    org.apache.poi.ss.usermodel.Cell poiCell = poiWorkbook.getSheet(worksheet.getName()).getRow(cell.getRow()).getCell(cell.getColumn());
                    String value = getPoiCellValue(poiCell, cell, poiCell.getCellType(), evaluator);
                    boolean isCellValueDifferent = value == null ? false : true;
                    if (isCellValueDifferent) {
                        // System.out.println("Row: "+cell.getRow()+" Column: "+cell.getColumn()+" OldValue: "+oldCell.getText()+" NewValue: "+cell.getCellText().toString());
                        String outputMapping = cell.getOutputMapping();
                        boolean isPutCell = false;
                        String postAddress = this.getPostAddressForOutputMapping(outputMapping, workbookProperties, modelId);
                        String row = String.valueOf(cell.getRow() + 1);
                        String col = org.apache.poi.ss.util.CellReference.convertNumToColString(cell.getColumn());
                        xml.append("<Cell row=\"" + row + "\" col=\"" + col + "\" addr=\"" + postAddress + "\" value=\"" + value + "\"");
                        if (outputMapping.indexOf("cedar.cp.putCell(") != -1) {
                            isPutCell = true;
                        }
                        xml.append(" putCell=\"").append(isPutCell).append("\"");
                        xml.append("/>");
                    }
                }
            }
            xml.append("</Cells>");
            xml.append("</Worksheet>");
        }
        xml.append("</WorkbookUpdate>");
        return xml.toString();
    }

    private String getPoiCellValue(org.apache.poi.ss.usermodel.Cell poiCell, CellExtendedDTO cell, int cellType, FormulaEvaluator evaluator) {
        switch (cellType) {
            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_ERROR:
                return null;
            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:
                return null;
            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC: {
                if (DateUtil.isCellDateFormatted(poiCell)) {
                    String stringDate = parseToProperDateFormat(poiCell.getDateCellValue());
                    if (stringDate != null) {
                        if (stringDate.equals(cell.getDate())) {
                            return null;
                        }
                    }
                    return stringDate;
                } else {
                    double p = poiCell.getNumericCellValue();
                    Double d = cell.getValue();
                    if (d != null && p == d) {
                        return null;
                    }
                    return String.valueOf(p);
                }
            }
            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA: {
                evaluator.evaluateFormulaCell(poiCell); // poiCell has type which is not formula
                return getPoiCellValue(poiCell, cell, poiCell.getCachedFormulaResultType(), evaluator);
            }
            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING: {
                String poiText = poiCell.getStringCellValue();
                if (poiText.equals(cell.getText())) {
                    return null;
                }
                return poiText;
            }
            default:
                return null;
        }
    }

    public static String parseToProperDateFormat(Date date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
            String formattedDate = formatter.format(date);
            return formattedDate;
        } catch (NumberFormatException ex) {

        }
        return null;
    }

    private String getPostAddressForOutputMapping(String outputMapping, Map contextVariables, int modelId) {

        String calendarContext = (String) contextVariables.get(WorkbookProperties.DIMENSION_2_VISID.toString());
        //CalendarInfo calendarInfo = (CalendarInfo) contextVariables.get(WorkbookProperties.CALENDAR_INFO.toString() + modelId);

        if (outputMapping != null && outputMapping.trim().length() > 0) {
            if (outputMapping.indexOf("cedar.cp.post(") != -1) {
                outputMapping = outputMapping.substring("cedar.cp.post(".length());
                return outputMapping.replaceAll("\\)", "");
            }

            if (outputMapping.indexOf("cedar.cp.putCell(") != -1) {
                outputMapping = outputMapping.substring("cedar.cp.putCell(".length());
                outputMapping = outputMapping.replaceAll("\\)", "");
                outputMapping = outputMapping.replaceAll("\"", "");
                String[] address = outputMapping.split(",");
                StringBuilder sb = new StringBuilder();
                
                
                for (int i = 0; i < address.length; ++i) {
                    String param = address[i];
                    String calVisId = null;
                    boolean isCalDim = false;
                    int yearOffset = 0;
                    int periodOffset = 0;
                    String[] attributes = param.split(";");
                    String[] node = attributes;
                    int adjustedNode = attributes.length;

                    for (int adjustedNode1 = 0; adjustedNode1 < adjustedNode; ++adjustedNode1) {
                        String attribute = node[adjustedNode1];
                        String[] values = attribute.split("=");
                        if (values[0].startsWith("dim")) {
                            int nfe = Integer.parseInt(values[0].substring(3, 4));
                            if (nfe == 2) {
                                isCalDim = true;
                                calVisId = values[1];
                            }
                        } else if (values[0].equalsIgnoreCase("year")) {
                            isCalDim = true;

                            try {
                                yearOffset = Integer.parseInt(values[1]);
                            } catch (NumberFormatException var18) {
                                yearOffset = 0;
                            }
                        } else if (values[0].equalsIgnoreCase("period")) {
                            isCalDim = true;

                            try {
                                periodOffset = Integer.parseInt(values[1]);
                            } catch (NumberFormatException var17) {
                                periodOffset = 0;
                            }
                        }
                    }

                    if (isCalDim) {
                        MappingValidator mv = new MappingValidator(MappingValidator.PREFIX+"."+MappingFunction.PUT_CELL.toString()+"("+outputMapping+")");
                        String[] date = com.softproideas.api.cpfunctionsevaluator.DateUtil.fillDateFromContextIfIsEmpty(mv, calendarContext);
                        Map<String, String> parsedArgMap = mv.getListOfArguments();
                        if (date[1] == null || !date[1].contains("pen")) {
                            date = com.softproideas.api.cpfunctionsevaluator.DateUtil.calculateDate(1, 12, parsedArgMap, date);
                        }
                        sb.append("dim");
                        sb.append(String.valueOf(2));
                        sb.append("=");
                        calVisId = com.softproideas.api.cpfunctionsevaluator.DateUtil.dateToString(date);
                        sb.append(calVisId);
                    } else {
                        sb.append(param);
                    }

                    if (i < address.length - 1) {
                        sb.append(",");
                    }
                }

                outputMapping = sb.toString();
            }
        }

        return XmlUtils.escapeStringForXML(outputMapping);
    }

    public InitialContext getInitialContext() {
        return this.mInitialContext;
    }

    public boolean mustComplete() {
        return false;
    }

    static class RecalculateCheckpoint extends AbstractTaskCheckpoint {
        public List toDisplay() {
            return Arrays.asList(new String[] { "RecalculateBatch Task Checkpoint number:" + getCheckpointNumber() });
        }
    }
}