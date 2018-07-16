// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xlsimport;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.xlsimport.ExcelGenericImportException;
import com.cedar.cp.util.xlsimport.ExcelUtils;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import jxl.Sheet;
import jxl.Workbook;

public class FormatVersion1 {

   private Workbook mWorkbook;
   private Sheet mMainSheet;
   private PrintStream mOutputStream;
   private String mCurrentSheetName;
   private int mCurrentRow;
   private int mLedgerDimensionCount;
   private Map<String, Integer> mCompanyLedgerDimensionCounts = new HashMap();
   private static final String COMPANY = "company";
   private static final String LEDGER = "ledger";
   private static final String VALUE_TYPE = "valueType";
   private static final String CURRENCY = "currency";
   private static final String DIMENSION = "dimension";
   private static final String CALENDAR = "calendar";
   private static final String VALUES = "values";
   private static final String VALUE = "value";
   private static final String CALENDAR_YEAR = "calendarYear";
   private static final String CALENDAR_ELEMENT = "calendarElement";
   private static final String HIERARCHY = "hierarchy";
   private static final String DIMENSION_ELEMENT = "dimensionElement";
   private NumberFormat sNumberFormat = NumberFormat.getNumberInstance();


   public FormatVersion1(PrintStream ps, Workbook wb, Sheet sheet, String currentSheetName) {
      this.mOutputStream = ps;
      this.mWorkbook = wb;
      this.mMainSheet = sheet;
      this.mCurrentSheetName = currentSheetName;
   }

   public void convertFile() throws ExcelGenericImportException {
      if(!ExcelUtils.doesCellContain(this.mMainSheet, 6, (short)1, "External System Id")) {
         throw this.newExcelGenericImportException("Can\'t find External System Id tag");
      } else if(!ExcelUtils.doesCellContain(this.mMainSheet, 7, (short)1, "Extract Date")) {
         throw this.newExcelGenericImportException("Can\'t find Extract Date tag");
      } else {
         String externalSystemId = ExcelUtils.getMandatoryStringCell(this.mMainSheet, 6, (short)2);
         Date extractDate = ExcelUtils.getMandatoryDateCell(this.mMainSheet, 7, (short)2);
         this.mOutputStream.println("\t\t\t\texternalSystemVisId=\"" + externalSystemId + "\"");
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss");
         this.mOutputStream.println("\t\t\t\textractDateTime=\"" + formatter.format(extractDate) + "\">");
         this.processCompanies();
      }
   }

   private void processCompanies() throws ExcelGenericImportException {
      this.mCurrentRow = 11;

      while(this.mCurrentRow <= this.mMainSheet.getRows()) {
         String keyword = ExcelUtils.getOptionalStringCell(this.mMainSheet, this.mCurrentRow, (short)1);
         if("company".equals(keyword)) {
            this.processCompany();
         } else if("values".equals(keyword)) {
            this.processValues();
         } else {
            if(keyword.trim().length() != 0) {
               throw this.newExcelGenericImportException("Unexpected row keyword \'" + keyword + "\' in column B for row " + (this.mCurrentRow + 1));
            }

            ++this.mCurrentRow;
         }
      }

   }

   private void processCompany() throws ExcelGenericImportException {
      String visId = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mCurrentRow, (short)2);
      String description = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mCurrentRow, (short)3);
      String dummy = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mCurrentRow, (short)4);
      String calendarColumnIndex = ExcelUtils.getMandatoryIntegerCell(this.mMainSheet, this.mCurrentRow, (short)5);
      this.mOutputStream.println("\t<company companyVisId=\"" + XmlUtils.escapeStringForXML(visId) + "\" description=\"" + XmlUtils.escapeStringForXML(description) + "\" dummy=\"" + dummy + "\" importCalendarColumnIndex=\"" + calendarColumnIndex + "\">");
      ++this.mCurrentRow;
      this.processLedger(visId);
      this.mOutputStream.println("\t</company>");
   }

   private void processLedger(String companyVisId) throws ExcelGenericImportException {
      if(!ExcelUtils.doesCellContain(this.mMainSheet, this.mCurrentRow, (short)1, "ledger")) {
         throw this.newExcelGenericImportException("Expected ledger row after a company row");
      } else {
         while(this.mCurrentRow <= this.mMainSheet.getRows()) {
            String keyword = ExcelUtils.getOptionalStringCell(this.mMainSheet, this.mCurrentRow, (short)1);
            if("ledger".equals(keyword)) {
               String visId = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mCurrentRow, (short)2);
               String description = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mCurrentRow, (short)3);
               String dummy = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mCurrentRow, (short)4);
               this.mOutputStream.println("\t\t<ledger ledgerVisId=\"" + XmlUtils.escapeStringForXML(visId) + "\" description=\"" + XmlUtils.escapeStringForXML(description) + "\" dummy=\"" + dummy + "\">");
               ++this.mCurrentRow;
               this.mLedgerDimensionCount = 0;
               this.processLedgerDetails();
               this.mCompanyLedgerDimensionCounts.put(this.getCompanyLedgerKey(companyVisId, visId), Integer.valueOf(this.mLedgerDimensionCount));
               this.mOutputStream.println("\t\t</ledger>");
            } else {
               if("company".equals(keyword) || "values".equals(keyword)) {
                  break;
               }

               if(keyword.trim().length() != 0) {
                  throw this.newExcelGenericImportException("Unexpected row keyword \'" + keyword + "\' in column B for row " + (this.mCurrentRow + 1));
               }

               ++this.mCurrentRow;
            }
         }

      }
   }

   private void processLedgerDetails() throws ExcelGenericImportException {
      while(true) {
         if(this.mCurrentRow <= this.mMainSheet.getRows()) {
            String keyword = ExcelUtils.getOptionalStringCell(this.mMainSheet, this.mCurrentRow, (short)1);
            if(!"ledger".equals(keyword) && !"company".equals(keyword) && !"values".equals(keyword)) {
               if("valueType".equals(keyword)) {
                  this.processValueType();
                  continue;
               }

               if("currency".equals(keyword)) {
                  this.processCurrency();
                  continue;
               }

               if("dimension".equals(keyword)) {
                  this.processDimension();
                  continue;
               }

               if("calendar".equals(keyword)) {
                  this.processCalendar();
                  continue;
               }

               if(keyword.trim().length() != 0) {
                  throw this.newExcelGenericImportException("Unexpected row keyword \'" + keyword + "\' in column B for row " + (this.mCurrentRow + 1));
               }

               ++this.mCurrentRow;
               continue;
            }
         }

         return;
      }
   }

   private void processValueType() throws ExcelGenericImportException {
      String visId = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mCurrentRow, (short)2);
      String description = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mCurrentRow, (short)3);
      this.mOutputStream.println("\t\t\t<valueType valueTypeVisId=\"" + XmlUtils.escapeStringForXML(visId) + "\" description=\"" + XmlUtils.escapeStringForXML(description) + "\" />");
      ++this.mCurrentRow;
   }

   private void processCurrency() throws ExcelGenericImportException {
      String visId = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mCurrentRow, (short)2);
      String description = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mCurrentRow, (short)3);
      this.mOutputStream.println("\t\t\t<currency currencyVisId=\"" + XmlUtils.escapeStringForXML(visId) + "\" description=\"" + XmlUtils.escapeStringForXML(description) + "\" />");
      ++this.mCurrentRow;
   }

   private void processDimension() throws ExcelGenericImportException {
      String type = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mCurrentRow, (short)2);
      String importColumnIndex = ExcelUtils.getMandatoryIntegerCell(this.mMainSheet, this.mCurrentRow, (short)5);
      ++this.mLedgerDimensionCount;
      String sheetName = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mCurrentRow, (short)3);
      String currentSheetName = this.mCurrentSheetName;
      Sheet dimensionSheet = this.mWorkbook.getSheet(sheetName);
      if(dimensionSheet == null) {
         throw this.newExcelGenericImportException("Can\'t get sheet with name \'" + sheetName + "\' whilst processing row " + (this.mCurrentRow + 1));
      } else {
         this.mCurrentSheetName = sheetName;
         this.processDimensionSheet(sheetName, dimensionSheet, type, importColumnIndex);
         this.mCurrentSheetName = currentSheetName;
         ++this.mCurrentRow;
      }
   }

   private void processDimensionSheet(String sheetName, Sheet sheet, String dimensionType, String importColumnIndex) throws ExcelGenericImportException {
      for(int sheetRow = 1; sheetRow <= sheet.getRows(); ++sheetRow) {
         String keyword = ExcelUtils.getOptionalStringCell(sheet, sheetRow, (short)1);
         if("dimension".equals(keyword)) {
            sheetRow = this.processDimension(sheet, dimensionType, importColumnIndex, sheetRow);
         }
      }

   }

   private int processDimension(Sheet sheet, String dimensionType, String importColumnIndex, int sheetRow) throws ExcelGenericImportException {
      String visId = ExcelUtils.getMandatoryStringCell(sheet, sheetRow, (short)2);
      String description = ExcelUtils.getMandatoryStringCell(sheet, sheetRow, (short)3);
      this.mOutputStream.println("\t\t\t<dimension dimensionVisId=\"" + XmlUtils.escapeStringForXML(visId) + "\" description=\"" + XmlUtils.escapeStringForXML(description) + "\" dimensionType=\"" + dimensionType + "\" importColumnIndex=\"" + importColumnIndex + "\" >");
      ++sheetRow;

      while(sheetRow <= sheet.getRows()) {
         String keyword = ExcelUtils.getOptionalStringCell(sheet, sheetRow, (short)1);
         if(keyword.equals("dimensionElement")) {
            sheetRow = this.processDimensionElement(sheet, sheetRow);
         } else if(keyword.equals("hierarchy")) {
            sheetRow = this.processHierarchy(sheet, sheetRow);
         } else {
            if(keyword.trim().length() != 0) {
               throw new ExcelGenericImportException(sheet.getName(), "Unexpected keyword in sheet [" + sheet.getName() + "] at column B row " + (sheetRow + 1));
            }

            ++sheetRow;
         }
      }

      this.mOutputStream.println("\t\t\t</dimension>");
      return sheetRow;
   }

   private int processDimensionElement(Sheet sheet, int sheetRow) throws ExcelGenericImportException {
      String elementVisId = ExcelUtils.getMandatoryStringCell(sheet, sheetRow, (short)2);
      String elementDescription = ExcelUtils.getMandatoryStringCell(sheet, sheetRow, (short)3);
      String elementDisabled = ExcelUtils.getOptionalStringCell(sheet, sheetRow, (short)4);
      String elementNotPlannable = ExcelUtils.getOptionalStringCell(sheet, sheetRow, (short)5);
      String elementCredit = ExcelUtils.getOptionalStringCell(sheet, sheetRow, (short)6);
      this.mOutputStream.print("\t\t\t\t<dimensionElement dimensionElementVisId=\"" + XmlUtils.escapeStringForXML(elementVisId) + "\" description=\"" + XmlUtils.escapeStringForXML(elementDescription) + "\"");
      if(elementCredit.length() > 0) {
         this.mOutputStream.print(" creditDebit=\"" + elementCredit + "\"");
      }

      if(elementDisabled.length() > 0) {
         this.mOutputStream.print(" disabled=\"" + elementDisabled + "\"");
      }

      if(elementNotPlannable.length() > 0) {
         this.mOutputStream.print(" notPlannable=\"" + elementNotPlannable + "\"");
      }

      this.mOutputStream.println(" />");
      return sheetRow + 1;
   }

   private int processHierarchy(Sheet sheet, int sheetRow) throws ExcelGenericImportException {
      String visId = ExcelUtils.getMandatoryStringCell(sheet, sheetRow, (short)2);
      String description = ExcelUtils.getMandatoryStringCell(sheet, sheetRow, (short)3);
      this.mOutputStream.println("\t\t\t<hierarchy hierarchyVisId=\"" + XmlUtils.escapeStringForXML(visId) + "\" description=\"" + XmlUtils.escapeStringForXML(description) + "\" >");
      ++sheetRow;

      for(double currentDepth = 0.0D; sheetRow < sheet.getRows(); sheetRow = this.processHierarchyElement(sheet, sheetRow, 1)) {
         String keyword = ExcelUtils.getOptionalStringCell(sheet, sheetRow, (short)1);
         if(!this.isNumber(keyword)) {
            break;
         }
      }

      this.mOutputStream.println("\t\t\t</hierarchy>");
      return sheetRow;
   }

   private int processHierarchyElement(Sheet sheet, int sheetRow, int depth) throws ExcelGenericImportException {
      this.mOutputStream.print("\t\t\t");
      this.repeat("\t", depth);
      String elementVisId = ExcelUtils.getMandatoryStringCell(sheet, sheetRow, (short)2);
      String elementDescription = ExcelUtils.getOptionalStringCell(sheet, sheetRow, (short)3);
      String elementCredit = ExcelUtils.getOptionalStringCell(sheet, sheetRow, (short)4);
      String elementDisabled = ExcelUtils.getOptionalStringCell(sheet, sheetRow, (short)5);
      this.mOutputStream.print("<hierarchyElement hierarchyElementVisId=\"" + XmlUtils.escapeStringForXML(elementVisId) + "\" description=\"" + XmlUtils.escapeStringForXML(elementDescription) + "\"");
      if(elementCredit.length() > 0) {
         this.mOutputStream.print(" creditDebit=\"" + elementCredit + "\"");
      }

      if(elementDisabled.length() > 0) {
         this.mOutputStream.print(" disabled=\"" + elementDisabled + "\"");
      }

      this.mOutputStream.println(" >");
      ++sheetRow;

      while(sheetRow < sheet.getRows()) {
         String keyword = ExcelUtils.getOptionalStringCell(sheet, sheetRow, (short)1);
         elementDescription = ExcelUtils.getOptionalStringCell(sheet, sheetRow, (short)3);
         if(!this.isNumber(keyword)) {
            break;
         }

         int level = Integer.parseInt(keyword);
         if(level <= depth) {
            break;
         }

         if(elementDescription != null && elementDescription.trim().length() == 0) {
            sheetRow = this.processHierarchyElementFeed(sheet, sheetRow, depth + 1);
         } else {
            sheetRow = this.processHierarchyElement(sheet, sheetRow, depth + 1);
         }
      }

      this.mOutputStream.print("\t\t\t");
      this.repeat("\t", depth);
      this.mOutputStream.println("</hierarchyElement>");
      return sheetRow;
   }

   private void repeat(String s, int repeat) {
      for(int i = 0; i < repeat; ++i) {
         this.mOutputStream.print(s);
      }

   }

   private int processHierarchyElementFeed(Sheet sheet, int sheetRow, int depth) throws ExcelGenericImportException {
      String visId = ExcelUtils.getMandatoryStringCell(sheet, sheetRow, (short)2);
      this.mOutputStream.print("\t\t\t");
      this.repeat("\t", depth);
      this.mOutputStream.println("<hierarchyElementFeed dimensionElementVisId=\"" + XmlUtils.escapeStringForXML(visId) + "\" />");
      return sheetRow + 1;
   }

   private boolean isNumber(String keyword) {
      if(keyword.trim().length() == 0) {
         return false;
      } else {
         char[] arr$ = keyword.toCharArray();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            char c = arr$[i$];
            if(!Character.isWhitespace(c) && !Character.isDigit(c)) {
               return false;
            }
         }

         return true;
      }
   }

   private void processCalendar() throws ExcelGenericImportException {
      String sheetName = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mCurrentRow, (short)2);
      String currentSheetName = this.mCurrentSheetName;
      Sheet calendarSheet = this.mWorkbook.getSheet(sheetName);
      if(calendarSheet == null) {
         throw this.newExcelGenericImportException("Can\'t get sheet with name \'" + sheetName + "\' whilst processing row " + (this.mCurrentRow + 1));
      } else {
         this.mCurrentSheetName = sheetName;
         this.processCalendarSheet(sheetName, calendarSheet);
         this.mCurrentSheetName = currentSheetName;
         ++this.mCurrentRow;
      }
   }

   private void processCalendarSheet(String sheetName, Sheet sheet) throws ExcelGenericImportException {
      int sheetRow = 1;

      while(sheetRow <= sheet.getRows()) {
         String keyword = ExcelUtils.getOptionalStringCell(sheet, sheetRow, (short)1);
         if("calendarYear".equals(keyword)) {
            String visId = this.getMandatoryString(sheet, sheetRow, (short)2);
            int year = this.getMandatoryInt(sheet, sheetRow, (short)3);
            this.mOutputStream.println("\t\t\t<calendarYear calendarYearVisId=\"" + XmlUtils.escapeStringForXML(visId) + "\" year=\"" + year + "\" >");
            ++sheetRow;

            while(true) {
               if(sheetRow <= sheet.getRows()) {
                  keyword = ExcelUtils.getOptionalStringCell(sheet, sheetRow, (short)1);
                  if(!"calendarYear".equals(keyword)) {
                     if("calendarElement".equals(keyword)) {
                        String elementVisId = this.getMandatoryString(sheet, sheetRow, (short)2);
                        String elementDescription = this.getMandatoryString(sheet, sheetRow, (short)3);
                        String elementPeriod = this.getMandatoryIntegerOrString(sheet, sheetRow, (short)4);
                        this.mOutputStream.println("\t\t\t\t<calendarElement calendarElementVisId=\"" + XmlUtils.escapeStringForXML(elementVisId) + "\" description=\"" + XmlUtils.escapeStringForXML(elementDescription) + "\" period=\"" + elementPeriod + "\" />");
                        ++sheetRow;
                     } else {
                        if(keyword.trim().length() != 0) {
                           throw this.newExcelGenericImportException("Sheet: " + sheetName + " Unexpected row keyword \'" + keyword + "\' in column B for row " + (sheetRow + 1));
                        }

                        ++sheetRow;
                     }
                     continue;
                  }
               }

               this.mOutputStream.println("\t\t\t</calendarYear>");
               break;
            }
         } else {
            if(keyword.trim().length() != 0) {
               throw this.newExcelGenericImportException("Sheet: " + sheetName + " Unexpected row keyword in column B for row " + (sheetRow + 1));
            }

            ++sheetRow;
         }
      }

   }

   private void processValues() throws ExcelGenericImportException {
      String sheetName = ExcelUtils.getMandatoryStringCell(this.mMainSheet, this.mCurrentRow, (short)2);
      String currentSheetName = this.mCurrentSheetName;
      Sheet valuesSheet = this.mWorkbook.getSheet(sheetName);
      if(valuesSheet == null) {
         throw this.newExcelGenericImportException("Can\'t get sheet with name \'" + sheetName + "\' whilst processing row " + (this.mCurrentRow + 1));
      } else {
         this.mCurrentSheetName = sheetName;
         this.processValuesSheet(sheetName, valuesSheet);
         this.mCurrentSheetName = currentSheetName;
         ++this.mCurrentRow;
      }
   }

   private void processValuesSheet(String sheetName, Sheet sheet) throws ExcelGenericImportException {
      int sheetRow = 1;

      while(sheetRow <= sheet.getRows()) {
         String keyword = ExcelUtils.getOptionalStringCell(sheet, sheetRow, (short)1);
         if("values".equals(keyword)) {
            String companyVisId = ExcelUtils.getMandatoryStringCell(sheet, sheetRow, (short)2);
            String ledgerVisId = ExcelUtils.getMandatoryStringCell(sheet, sheetRow, (short)3);
            String key = this.getCompanyLedgerKey(companyVisId, ledgerVisId);
            Integer dimensionCount = (Integer)this.mCompanyLedgerDimensionCounts.get(key);
            if(dimensionCount == null) {
               throw this.newExcelGenericImportException("Sheet: " + sheetName + " Can\'t find associated company and ledger for  values record on row " + (sheetRow + 1));
            }

            this.mOutputStream.println("\t<values companyVisId=\"" + XmlUtils.escapeStringForXML(companyVisId) + "\" ledgerVisId=\"" + XmlUtils.escapeStringForXML(ledgerVisId) + "\" >");
            ++sheetRow;

            while(sheetRow <= sheet.getRows()) {
               keyword = ExcelUtils.getOptionalStringCell(sheet, sheetRow, (short)1);
               if("value".equals(keyword)) {
                  this.mOutputStream.print("\t\t<value");
                  short sheetCol = 2;
                  short col = 0;

                  String colValue;
                  for(colValue = null; col <= dimensionCount.shortValue(); ++sheetCol) {
                     colValue = ExcelUtils.getOptionalStringCell(sheet, sheetRow, sheetCol);
                     this.mOutputStream.print(" visId" + col + "=\"" + XmlUtils.escapeStringForXML(colValue) + "\"");
                     ++col;
                  }

                  colValue = this.getMandatoryIntegerOrString(sheet, sheetRow, sheetCol);
                  this.mOutputStream.print(" calendarYearVisId=\"" + XmlUtils.escapeStringForXML(colValue) + "\"");
                  ++sheetCol;
                  colValue = ExcelUtils.getMandatoryStringCell(sheet, sheetRow, sheetCol);
                  this.mOutputStream.print(" valueTypeVisId=\"" + XmlUtils.escapeStringForXML(colValue) + "\"");
                  ++sheetCol;
                  colValue = ExcelUtils.getMandatoryStringCell(sheet, sheetRow, sheetCol);
                  this.mOutputStream.print(" currencyVisId=\"" + XmlUtils.escapeStringForXML(colValue) + "\"");
                  ++sheetCol;
                  colValue = ExcelUtils.getMandatoryStringCell(sheet, sheetRow, sheetCol);

                  double cellValue;
                  try {
                     cellValue = this.sNumberFormat.parse(colValue).doubleValue();
                  } catch (ParseException var16) {
                     throw new ExcelGenericImportException("Unable to parse value [" + colValue + "] at " + sheetRow + "," + sheetCol + " on sheet [" + sheetName + "]");
                  }

                  long scaledValue = Math.round(cellValue * 10000.0D);
                  this.mOutputStream.print(" value=\"" + scaledValue + "\"");
                  this.mOutputStream.println(" />");
                  ++sheetRow;
               } else {
                  if(keyword.trim().length() != 0) {
                     throw this.newExcelGenericImportException("Sheet: " + sheetName + " Unexpected row keyword \'" + keyword + "\' in column B for row " + (sheetRow + 1));
                  }

                  ++sheetRow;
               }
            }

            this.mOutputStream.println("\t</values>");
         } else {
            if(keyword.trim().length() != 0) {
               throw this.newExcelGenericImportException("Sheet: " + sheetName + " Unexpected row keyword in column B for row " + (sheetRow + 1));
            }

            ++sheetRow;
         }
      }

   }

   private String getMandatoryIntegerOrString(Sheet sheet, int sheetRow, short sheetCol) throws ExcelGenericImportException {
      String colValue = "";

      try {
         colValue = ExcelUtils.getMandatoryIntegerCell(sheet, sheetRow, sheetCol);
      } catch (Exception var6) {
         colValue = ExcelUtils.getMandatoryStringCell(sheet, sheetRow, sheetCol);
      }

      return colValue;
   }

   private String getMandatoryString(Sheet sheet, int sheetRow, short sheetCol) throws ExcelGenericImportException {
      return ExcelUtils.getMandatoryStringCell(sheet, sheetRow, sheetCol);
   }

   private int getMandatoryInt(Sheet sheet, int sheetRow, short sheetCol) throws ExcelGenericImportException {
      return ExcelUtils.getMandatoryIntegerCellAsInt(sheet, sheetRow, sheetCol);
   }

   private String getCompanyLedgerKey(String company, String ledger) {
      return company + "::" + ledger;
   }

   private ExcelGenericImportException newExcelGenericImportException(String reason) {
      return new ExcelGenericImportException(this.mCurrentSheetName, reason);
   }
}
