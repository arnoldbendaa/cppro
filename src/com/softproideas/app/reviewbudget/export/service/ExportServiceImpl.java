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
package com.softproideas.app.reviewbudget.export.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.workbook.CellDTO;
import com.cedar.cp.util.flatform.model.workbook.CellExtendedDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.flatform.model.workbook.WorksheetDTO;
import com.cedar.cp.util.sparse.LinkedListSparse2DArray.CellLink;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.handler.exceptions.ExcelIOException;

/**
 * @author Szymon Walczak
 * @email szymon.walczak@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
@Service("excelService")
public class ExportServiceImpl implements ExportService {

    @Autowired
    CPContextHolder cpContextHolder;
    int userId;
    public void Init(int userId,CPContext context){
    	this.userId=userId;
        if(cpContextHolder.getCPContext()==null){
        	cpContextHolder.init(context);
        }
    }

    @Override
    public Workbook exportToXls(byte[] excelFile, WorkbookDTO workbook) throws IOException, ServiceException {
        // Get the workbook instance for XLS file
        Workbook excelWorkbook = byteToWorkbook(excelFile);
        // Evaluate mappings
        evaluateMappings(excelWorkbook, workbook);
        
        // Unlock sheets
        if (workbook.getProperties() == null || workbook.getProperties().get(WorkbookProperties.PROTECTED.toString()) == null || !workbook.getProperties().get(WorkbookProperties.PROTECTED.toString()).equals("true")) {
            unprotectWorkbook(excelWorkbook); 
        }

        // Convert "NOW()", "TODAY()" formulas to static value
        evaluateDateFormulas(excelWorkbook);
        return excelWorkbook;
    }

    private void unprotectWorkbook(Workbook excelWorkbook) {
        for (int i = 0; i < excelWorkbook.getNumberOfSheets(); i++) {
            Sheet sheet = excelWorkbook.getSheetAt(i);
            if (sheet.getProtect()) {
                sheet.protectSheet(null);
            }
        }
    }

    @Override
    public String workbookToJson(Workbook workbook) throws ExcelIOException {
        byte[] workbookByteArray = workbookToByteArray(workbook);
        String jsonForm = convertExcelToJson(workbookByteArray);
        return jsonForm;
    }

    private byte[] workbookToByteArray(Workbook workbook) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ServiceException se = null;
        try {
            workbook.write(bos);
        } catch (IOException e) {
            se = new ServiceException("IO Exception", e);
        } finally {
            try {
                bos.close();
            } catch (Exception e) {
                if (se != null) {
                    new ServiceException(se.getMessage() + "; " + e.getMessage(), se);
                } else {
                    new ServiceException(e.getMessage(), e);
                }
            }
            if (se != null) {
                new ServiceException(se.getMessage(), se);
            }
        }
        return bos.toByteArray();
    }

    private String convertExcelToJson(byte[] fileByte) throws ExcelIOException {
        try {
            return cpContextHolder.getCPConnection().getExcelIOProcess().convertXlsToJson(fileByte, "");
        } catch (Exception e) {
            throw new ExcelIOException(e.getMessage());
        }
    }

    private static void evaluateDateFormulas(Workbook excelWorkbook) {
        FormulaEvaluator evaluator = excelWorkbook.getCreationHelper().createFormulaEvaluator();
        for (int i = 0; i < excelWorkbook.getNumberOfSheets(); i++) {
            Sheet sheet = excelWorkbook.getSheetAt(i);
            handleSheet(sheet, evaluator);
        }
    }

    public static void handleSheet(Sheet sheet, FormulaEvaluator evaluator) {
        for (Row row: sheet) {
            for (Cell cell: row) {
                handleCell(cell.getCellType(), cell, evaluator);
            }
        }
    }

    private static void handleCell(int type, Cell cell, FormulaEvaluator evaluator) {
        if (type == Cell.CELL_TYPE_FORMULA) {
            String cellFormula = cell.getCellFormula();
            if (cellFormula.toUpperCase().contains("NOW()") || cellFormula.toUpperCase().contains("TODAY()")) {
                evaluator.evaluateFormulaCell(cell);
                handleCellwithDate(cell.getCachedFormulaResultType(), cell);
            }
        }
    }

    private static void handleCellwithDate(int type, Cell cell) {
        String cellValue = "";
        switch (type) {
            case Cell.CELL_TYPE_STRING:
                cellValue = cell.getStringCellValue();
                break;

            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getDateCellValue().toString();
                } else {
                    cellValue = Double.toString(cell.getNumericCellValue());
                }
                break;

            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;

            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = Boolean.toString(cell.getBooleanCellValue());
                break;

        }
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(cellValue);
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
                    try {
                        excelCell.setCellFormula(cell.getFormula().substring(1)); // remove "="
                    } catch (Exception e) {
                        // wrong formula, do nothing!
                    }
                    
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

    private static void evaluateMappings(Workbook excelWorkbook, com.cedar.cp.util.flatform.model.Workbook workbook) {
        // Date format
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");

        // go through sheets in workbook
        for (Worksheet worksheet: workbook.getWorksheets()) {
            Sheet excelSheet = excelWorkbook.getSheet(worksheet.getName());

            Iterator cellLinkIterator = worksheet.iterator();
            Iterator<Row> excelRowIterator = excelSheet.iterator();
            int rowIndex = 0; // to start with A1
            Row row = excelRowIterator.next();

            CellStyle dateCellStyle = excelWorkbook.createCellStyle();
            short df = excelWorkbook.createDataFormat().getFormat("dd-MMM-yy");

            // iterate through cells in workbook
            while (cellLinkIterator.hasNext()) {
                // has info about cell
                CellLink cellLink = (CellLink) cellLinkIterator.next();

                // row by row in excel
                row: while (rowIndex < worksheet.getRowCount()) {
                    // match rows
                    if (rowIndex == cellLink.getRow()) {
                        int colIndex = 0;
                        Iterator<Cell> excelCellIterator = row.cellIterator();

                        // every column in row in excel
                        while (excelCellIterator.hasNext() && colIndex < worksheet.getColumnCount()) {
                            Cell excelCell = excelCellIterator.next();
                            colIndex = excelCell.getColumnIndex();

                            // match columns
                            if (colIndex == cellLink.getColumn()) {
                                // get cell data and set in excel
                                if (cellLink.getData() != null) {
                                    if (cellLink.getData() instanceof com.cedar.cp.util.flatform.model.Cell) {
                                        com.cedar.cp.util.flatform.model.Cell cell = (com.cedar.cp.util.flatform.model.Cell) cellLink.getData();

                                        String text = cell.getText();
                                        boolean formula = cell.getFormulaText() == null || cell.getFormulaText().length() <= 1 ? false : cell.getFormulaText().startsWith("=");

                                        if (formula) {
                                            excelCell.setCellFormula(cell.getFormulaText().substring(1)); // remove "="
                                        } else {
                                            short dataFormat = excelCell.getCellStyle().getDataFormat();

                                            if (text != null && isTextFormat(dataFormat)) {
                                                text = text.equals("null") ? "" : text;
                                                excelCell.setCellValue(text);
                                            } else if (text == null && !isTextFormat(dataFormat)) {
                                                excelCell.setCellValue(0);
                                            } else if (text != null) {
                                                text = (text.isEmpty() || text.equals("null")) ? "0" : text;
                                                try {
                                                    excelCell.setCellValue(Double.parseDouble(text));
                                                } catch (Exception e) {

                                                    try {
                                                        Date date = dateFormat.parse(text);
                                                        excelCell.setCellValue(date);
                                                        dateCellStyle.cloneStyleFrom(excelCell.getCellStyle());
                                                        dateCellStyle.setDataFormat(df);
                                                        excelCell.setCellStyle(dateCellStyle);
                                                    } catch (ParseException parseException) {
                                                        excelCell.setCellValue(text);
                                                    }

                                                }
                                            }
                                        }
                                        break row;
                                    }
                                }
                            }
                        }
                    } else {
                        row = excelRowIterator.next();
                        rowIndex = row.getRowNum();
                    }
                }
            }
        }
    }

    private Workbook byteToWorkbook(byte[] excelFile) throws ServiceException {
        try {
            InputStream is = new ByteArrayInputStream(excelFile);
            Workbook excelWorkbook = WorkbookFactory.create(is);
            is.close();
            return excelWorkbook;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private static Boolean isTextFormat(short dataFormat) {
        // https://poi.apache.org/apidocs/org/apache/poi/ss/usermodel/BuiltinFormats.html
        return dataFormat == 49;
    }
}