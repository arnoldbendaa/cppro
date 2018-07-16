// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.reportpack;

import com.cedar.cp.api.base.CPFileWrapper;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.ReportColumnMapDTO;
import com.cedar.cp.util.NumberUtils;
import com.cedar.cp.util.SpreadsheetUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.AreaReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

public class CellCalcSummaryReport {

   protected Workbook mResultWorkBook;
   protected String mResultWorkBookName;
   protected Sheet mSheet;
   public static final String PREFIX_NAME_RANGE = "sheet";
   public static final String SUFFIX_SUMMARY_NAME_RANGE = "_summary";
   public static final String SUFFIX_HEADER_NAME_RANGE = "_header";
   private Map<Integer, String> mSheetMap = new TreeMap();
   private int mSheetId = 0;
   private int mRowIndex = 1;
   private Map<Integer, ReportColumnMapDTO> mColumnReportMap;


   public CPFileWrapper createSummary(byte[] data) throws Exception {
      return this.createSummary(new CPFileWrapper(data, "MergedResult.xls"));
   }

   public CPFileWrapper createSummary(CPFileWrapper template) throws Exception {
      this.mResultWorkBookName = "MergedResult.xls";
      if(template != null) {
         this.mResultWorkBookName = template.getName();
         this.mResultWorkBook = WorkbookFactory.create(new ByteArrayInputStream(template.getData()));
         this.mResultWorkBook.setSheetName(0, "Summary");

         for(int out = this.mResultWorkBook.getNumberOfSheets() - 1; out > 0; --out) {
            this.mResultWorkBook.removeSheetAt(out);
         }
      } else {
         this.mResultWorkBook = new HSSFWorkbook();
         this.mResultWorkBook.createSheet("Summary");
      }

      this.mResultWorkBook.createSheet("Index");
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      this.mResultWorkBook.write(var3);
      return new CPFileWrapper(var3.toByteArray(), this.mResultWorkBookName);
   }

   public CPFileWrapper mergeFiles(CPFileWrapper original, CPFileWrapper contents) throws Exception {
      if(original == null) {
         throw new ValidationException("Cannot merge into empty file");
      } else {
         this.mResultWorkBookName = original.getName();
         this.mResultWorkBook = WorkbookFactory.create(new ByteArrayInputStream(original.getData()));
         if(contents != null) {
            this.processContents(WorkbookFactory.create(new ByteArrayInputStream(contents.getData())), contents.getName());
         }

         ByteArrayOutputStream out = new ByteArrayOutputStream();
         this.mResultWorkBook.write(out);
         return new CPFileWrapper(out.toByteArray(), this.mResultWorkBookName);
      }
   }

   public CPFileWrapper produceSummary(String name, CPFileWrapper file, String columnMap) throws Exception {
      this.mColumnReportMap = ReportColumnMapDTO.buildMap(columnMap);
      this.mResultWorkBook = WorkbookFactory.create(new ByteArrayInputStream(file.getData()));
      Sheet summary = this.mResultWorkBook.getSheet("Summary");
      Sheet index = this.mResultWorkBook.getSheet("Index");
      this.writeSummaryHeaders(index);
      this.writeSummaryTable(summary);
      this.writeSumaryRow(summary);
      return this.produceFile(name);
   }

   public CPFileWrapper produceDynamicSummary(String name, CPFileWrapper file, String columnMap) throws Exception {
      this.mColumnReportMap = ReportColumnMapDTO.buildMap(columnMap);
      this.mResultWorkBook = WorkbookFactory.create(new ByteArrayInputStream(file.getData()));
      Sheet summary = this.mResultWorkBook.getSheet("Summary");
      Sheet index = this.mResultWorkBook.getSheet("Index");
      this.writeSummaryHeaders(index);
      this.writeDynamicSummaryTable(summary);
      this.getNextRow();
      this.writeSumaryRow(summary);
      return this.produceFile(name);
   }

   private CPFileWrapper produceFile(String name) throws Exception {
      for(int out = this.mResultWorkBook.getNumberOfSheets() - 1; out > 0; --out) {
         SpreadsheetUtils.autoSizeSheet(this.mResultWorkBook.getSheetAt(out));
      }

      SpreadsheetUtils.autoSizeSheet(this.mResultWorkBook.getSheetAt(0));
      this.mResultWorkBook.setSelectedTab(0);
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      this.mResultWorkBook.write(var3);
      return new CPFileWrapper(var3.toByteArray(), name);
   }

   private void writeDynamicSummaryTable(Sheet sheet) {
      Iterator i$ = this.mSheetMap.keySet().iterator();

      while(i$.hasNext()) {
         Integer sheetName = (Integer)i$.next();
         Sheet data = this.mResultWorkBook.getSheet(sheetName.toString());
         String headerNameId = "sheet" + sheetName.toString() + "_header";
         int headerIndex = this.mResultWorkBook.getNameIndex(headerNameId);
         Name headerNameRange = null;
         if(headerIndex != -1) {
            headerNameRange = this.mResultWorkBook.getNameAt(headerIndex);
         }

         AreaReference headerArea = null;
         if(headerNameRange != null) {
            headerArea = new AreaReference(headerNameRange.getRefersToFormula());
         }

         String summaryNameId = "sheet" + sheetName.toString() + "_summary";
         Name summaryNameRange = this.mResultWorkBook.getNameAt(this.mResultWorkBook.getNameIndex(summaryNameId));
         AreaReference summaryArea = new AreaReference(summaryNameRange.getRefersToFormula());

         for(int processRow = 0; processRow < summaryArea.getFirstCell().getRow(); ++processRow) {
            Row dataRow = data.getRow(processRow);
            if(dataRow != null && (dataRow.getRowNum() != 1 || sheetName.intValue() <= 1) && (headerArea == null || dataRow.getRowNum() < headerArea.getFirstCell().getRow() || dataRow.getRowNum() >= headerArea.getLastCell().getRow())) {
               this.getNextRow();
               Cell resultCell;
               if(processRow < 2) {
                  Iterator var22 = dataRow.cellIterator();

                  while(var22.hasNext()) {
                     Cell var21 = (Cell)var22.next();
                     Row var23 = SpreadsheetUtils.getRow(sheet, this.mRowIndex);
                     if(processRow == 0) {
                        resultCell = SpreadsheetUtils.getCell(var23, 0);
                     } else {
                        resultCell = SpreadsheetUtils.getCell(var23, var21.getColumnIndex());
                     }

                     if(var21.getCellType() == 1) {
                        resultCell.setCellValue(var21.getRichStringCellValue());
                     }
                  }
               } else {
                  int resultCellColId = 1;
                  Iterator i$1 = SpreadsheetUtils.getOrderedCells(dataRow).iterator();

                  while(i$1.hasNext()) {
                     Cell dataCell = (Cell)i$1.next();
                     int sourceCellColId = dataCell.getColumnIndex();
                     ReportColumnMapDTO dto = (ReportColumnMapDTO)this.mColumnReportMap.get(Integer.valueOf(sourceCellColId));
                     if(dto != null && !dto.getFunctionName().equals("hidden")) {
                        Row resultRow = SpreadsheetUtils.getRow(sheet, this.mRowIndex);
                        resultCell = resultRow.createCell(resultCellColId++);
                        if(dataCell.getCellType() == 1) {
                           resultCell.setCellValue(dataCell.getRichStringCellValue());
                        } else if(dataCell.getCellType() == 0) {
                           resultCell.setCellValue(dataCell.getNumericCellValue());
                        }
                     }
                  }
               }
            }
         }
      }

   }

   private void writeSummaryHeaders(Sheet summary) {
      byte indexRow = 1;
      CreationHelper creationHelper = summary.getWorkbook().getCreationHelper();
      int var8 = indexRow + 1;
      Row row = summary.createRow(indexRow);
      Cell cell = row.createCell(0);
      cell.setCellValue(creationHelper.createRichTextString("Tab Contents"));
      row = summary.createRow(var8++);
      cell = row.createCell(0);
      cell.setCellValue(creationHelper.createRichTextString("Id"));
      cell = row.createCell(1);
      cell.setCellValue(creationHelper.createRichTextString("Value"));
      Iterator i$ = this.mSheetMap.keySet().iterator();

      while(i$.hasNext()) {
         Integer key = (Integer)i$.next();
         row = summary.createRow(var8++);
         cell = row.createCell(0);
         cell.setCellValue(creationHelper.createRichTextString(key.toString()));
         cell = row.createCell(1);
         cell.setCellValue(creationHelper.createRichTextString((String)this.mSheetMap.get(key)));
         summary.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 1, 10));
      }

   }

   private void writeSummaryTable(Sheet sheet) {
      int processOffset = this.getNextRow();
      Iterator i$ = this.mSheetMap.keySet().iterator();

      while(i$.hasNext()) {
         Integer sheetName = (Integer)i$.next();
         Sheet data = this.mResultWorkBook.getSheet(sheetName.toString());
         String headerNameId = "sheet" + sheetName.toString() + "_header";
         int headerIndex = this.mResultWorkBook.getNameIndex(headerNameId);
         Name headerNameRange = null;
         if(headerIndex != -1) {
            headerNameRange = this.mResultWorkBook.getNameAt(headerIndex);
         }

         AreaReference headerArea = null;
         if(headerNameRange != null) {
            headerArea = new AreaReference(headerNameRange.getRefersToFormula());
         }

         String summaryNameId = "sheet" + sheetName.toString() + "_summary";
         Name summaryNameRange = this.mResultWorkBook.getNameAt(this.mResultWorkBook.getNameIndex(summaryNameId));
         AreaReference summaryArea = new AreaReference(summaryNameRange.getRefersToFormula());

         for(int processRow = 1; processRow < summaryArea.getFirstCell().getRow(); ++processRow) {
            Row dataRow = data.getRow(processRow);
            if(dataRow != null && (headerArea == null || dataRow.getRowNum() < headerArea.getFirstCell().getRow() || dataRow.getRowNum() >= headerArea.getLastCell().getRow())) {
               Iterator dataCells = dataRow.cellIterator();

               while(dataCells.hasNext()) {
                  Cell dataCell = (Cell)dataCells.next();
                  ReportColumnMapDTO dto = (ReportColumnMapDTO)this.mColumnReportMap.get(Integer.valueOf(dataCell.getColumnIndex()));
                  if(dto != null && !dto.getFunctionName().equals("hidden")) {
                     Row resultRow = SpreadsheetUtils.getRow(sheet, processRow + processOffset);
                     Cell resultCell = SpreadsheetUtils.getCell(resultRow, dataCell.getColumnIndex());
                     if(dataCell.getCellType() == 1) {
                        resultCell.setCellValue(dataCell.getRichStringCellValue());
                     } else if(dataCell.getCellType() == 0) {
                        String formula = "";
                        if(resultCell.getCellType() == 2) {
                           formula = resultCell.getCellFormula();
                        }

                        StringBuffer sb = new StringBuffer();
                        StringBuffer cell;
                        switch(dto.getFunctionId()) {
                        case 2:
                           if(formula.length() > 0) {
                              sb.append(formula);
                           } else {
                              sb.append(0);
                           }

                           sb.append("+").append("\'").append(sheetName.toString()).append("\'!").append(SpreadsheetUtils.getCellId(dataCell));
                           break;
                        case 3:
                           if(formula.length() > 0) {
                              sb.append(formula);
                           } else {
                              sb.append("AVERAGE()");
                           }

                           cell = new StringBuffer();
                           if(sb.indexOf(")") > 10) {
                              cell.append(",");
                           }

                           cell.append("\'").append(sheetName.toString()).append("\'!").append(SpreadsheetUtils.getCellId(dataCell));
                           sb.insert(sb.lastIndexOf(")"), cell.toString());
                           break;
                        case 4:
                           if(formula.length() > 0) {
                              sb.append(formula);
                           } else {
                              sb.append("MIN()");
                           }

                           cell = new StringBuffer();
                           if(sb.indexOf(")") > 6) {
                              cell.append(",");
                           }

                           cell.append("\'").append(sheetName.toString()).append("\'!").append(SpreadsheetUtils.getCellId(dataCell));
                           sb.insert(sb.lastIndexOf(")"), cell.toString());
                           break;
                        case 5:
                           if(formula.length() > 0) {
                              sb.append(formula);
                           } else {
                              sb.append("MAX()");
                           }

                           cell = new StringBuffer();
                           if(sb.indexOf(")") > 6) {
                              cell.append(",");
                           }

                           cell.append("\'").append(sheetName.toString()).append("\'!").append(SpreadsheetUtils.getCellId(dataCell));
                           sb.insert(sb.lastIndexOf(")"), cell.toString());
                           break;
                        case 6:
                           resultCell.setCellValue(dataCell.getNumericCellValue());
                        }

                        if(sb.length() > 0) {
                           resultCell.setCellFormula(sb.toString());
                        }
                     }
                  }
               }
            }
         }
      }

      this.mRowIndex = SpreadsheetUtils.getMaxRowNum(sheet) + 3;
   }

   private void writeSumaryRow(Sheet sheet) {
      Row titleRow = SpreadsheetUtils.getRow(sheet, this.getNextRow());
      CellStyle titleStyle = this.mResultWorkBook.createCellStyle();
      Font titleFont = this.mResultWorkBook.createFont();
      titleFont.setBoldweight((short)700);
      titleStyle.setFont(titleFont);
      Cell titleData = SpreadsheetUtils.getCell(titleRow, 0);
      titleData.setCellValue("Total");
      titleData.setCellStyle(titleStyle);
      titleData = SpreadsheetUtils.getCell(titleRow, 1);
      titleData.setCellValue("Calculated Value");
      titleData.setCellStyle(titleStyle);
      titleData = SpreadsheetUtils.getCell(titleRow, 2);
      titleData.setCellValue("Override Value");
      titleData.setCellStyle(titleStyle);
      int targetRow = this.getNextRow();
      Iterator i$ = this.mSheetMap.keySet().iterator();

      while(i$.hasNext()) {
         Integer sheetName = (Integer)i$.next();
         Sheet data = this.mResultWorkBook.getSheet(sheetName.toString());
         String nameId = "sheet" + sheetName.toString() + "_summary";
         Name sheetNameRange = this.mResultWorkBook.getNameAt(this.mResultWorkBook.getNameIndex(nameId));
         AreaReference ar = new AreaReference(sheetNameRange.getRefersToFormula());
         int processRow = ar.getFirstCell().getRow();

         Row dataRow;
         for(int processStartRow = ar.getFirstCell().getRow(); (dataRow = data.getRow(processRow)) != null; ++processRow) {
            Iterator dataCells = dataRow.cellIterator();

            while(dataCells.hasNext()) {
               Cell dataCell = (Cell)dataCells.next();
               if(dataCell != null) {
                  Row summaryRow = SpreadsheetUtils.getRow(sheet, targetRow + (processRow - processStartRow));
                  Cell resultCell = SpreadsheetUtils.getCell(summaryRow, dataCell.getColumnIndex());
                  if(dataCell.getCellType() == 1) {
                     resultCell.setCellValue(dataCell.getRichStringCellValue());
                  } else if(dataCell.getCellType() == 0) {
                     String formula = "";
                     if(resultCell.getCellType() == 2) {
                        formula = resultCell.getCellFormula();
                     }

                     StringBuffer sb = new StringBuffer();
                     if(formula.length() > 0) {
                        sb.append(formula);
                     } else {
                        sb.append(0);
                     }

                     sb.append("+").append("\'").append(sheetName.toString()).append("\'!").append(SpreadsheetUtils.getCellId(dataCell));
                     resultCell.setCellFormula(sb.toString());
                  }
               }
            }
         }
      }

   }

   private void processContents(Workbook workbook, String name) {
      Sheet resultData = this.createSheet(this.mResultWorkBook, name);
      Sheet data = workbook.getSheetAt(0);
      CreationHelper creationHelper = resultData.getWorkbook().getCreationHelper();
      Row titleRow = resultData.createRow(0);
      Cell titleCell = titleRow.createCell(1);
      titleCell.setCellValue(creationHelper.createRichTextString(name));
      resultData.addMergedRegion(new CellRangeAddress(titleCell.getRowIndex(), titleCell.getRowIndex(), titleCell.getColumnIndex(), 20));
      Iterator rows = data.rowIterator();

      int headerIndex;
      String ref;
      while(rows.hasNext()) {
         Row sheetName = (Row)rows.next();
         headerIndex = sheetName.getRowNum();
         Row origHeaderRangeName = resultData.createRow(headerIndex);
         Iterator headerArea = sheetName.cellIterator();

         while(headerArea.hasNext()) {
            Cell origSummaryRangeName = (Cell)headerArea.next();
            Cell summaryArea = origHeaderRangeName.createCell(origSummaryRangeName.getColumnIndex());
            ref = origSummaryRangeName.getRichStringCellValue().getString();
            if(NumberUtils.isNumber(ref)) {
               BigDecimal sb = new BigDecimal(NumberUtils.removeFormattingFromString(ref));
               summaryArea.setCellValue(sb.doubleValue());
            } else {
               summaryArea.setCellValue(origSummaryRangeName.getRichStringCellValue());
            }
         }
      }

      String sheetName1 = this.mResultWorkBook.getSheetName(this.mResultWorkBook.getSheetIndex(resultData));
      headerIndex = workbook.getNameIndex("header");
      Name origHeaderRangeName1 = null;
      if(headerIndex != -1) {
         origHeaderRangeName1 = workbook.getNameAt(headerIndex);
      }

      AreaReference headerArea1 = null;
      if(origHeaderRangeName1 != null) {
         headerArea1 = new AreaReference(origHeaderRangeName1.getRefersToFormula());
      }

      Name origSummaryRangeName1 = workbook.getNameAt(workbook.getNameIndex("summary"));
      AreaReference summaryArea1 = new AreaReference(origSummaryRangeName1.getRefersToFormula());
      Name summaryRangeName;
      StringBuffer sb1;
      if(headerArea1 != null) {
         summaryRangeName = this.mResultWorkBook.createName();
         summaryRangeName.setNameName("sheet" + sheetName1 + "_header");
         ref = headerArea1.getFirstCell().getCellRefParts()[2] + headerArea1.getFirstCell().getCellRefParts()[1];
         String ref2 = headerArea1.getLastCell().getCellRefParts()[2] + headerArea1.getLastCell().getCellRefParts()[1];
         sb1 = new StringBuffer();
         sb1.append("\'").append(sheetName1).append("\'!").append(ref).append(":").append(ref2);
         summaryRangeName.setRefersToFormula(sb1.toString());
      }

      summaryRangeName = this.mResultWorkBook.createName();
      summaryRangeName.setNameName("sheet" + sheetName1 + "_summary");
      ref = summaryArea1.getFirstCell().getCellRefParts()[2] + summaryArea1.getFirstCell().getCellRefParts()[1];
      sb1 = new StringBuffer();
      sb1.append("\'").append(sheetName1).append("\'!").append(ref).append(":").append(ref);
      summaryRangeName.setRefersToFormula(sb1.toString());
   }

   public int getMaxRows() {
      int result = 0;

      for(int i = 0; i < this.mResultWorkBook.getNumberOfNames(); ++i) {
         Name name = this.mResultWorkBook.getNameAt(i);
         AreaReference ar = new AreaReference(name.getRefersToFormula());
         int row = ar.getFirstCell().getRow();
         if(row > result) {
            result = row;
         }
      }

      return result;
   }

   private Sheet createSheet(Workbook book, String name) {
      String shortSheetName = name.substring(name.indexOf(".") + 1, name.lastIndexOf("."));
      Integer sheetId = this.getNextSheetId();
      this.mSheetMap.put(sheetId, shortSheetName);
      return book.createSheet(String.valueOf(sheetId));
   }

   private Integer getNextSheetId() {
      return Integer.valueOf(++this.mSheetId);
   }

   private int getNextRow() {
      return this.mRowIndex++;
   }
}
