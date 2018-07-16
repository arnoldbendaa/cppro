// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xlsimport;

import com.cedar.cp.util.xlsimport.ExcelGenericImportException;
import com.cedar.cp.util.xlsimport.ExcelUtils;
import com.cedar.cp.util.xlsimport.FormatVersion1;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelGenericImport {

   private PrintStream mOutputStream;
   private Workbook mWorkbook;
   private String mCurrentSheetName;
   private static final String CEDAR_OPEN_ACCOUNTS_GENERIC_IMPORT = "CedarOpenAccounts - Generic Import";
   private static final String IMPORT_DEFINITION = "ImportDefinition";
   private static final String FORMAT_VERSION = "Format Version";
   private static final String FORMAT_VERSION_1 = "1.0";


   public static void main(String[] args) {
      FileInputStream is = null;
      PrintStream ps = null;

      try {
         is = new FileInputStream(args[0]);
         ps = new PrintStream(args[0] + ".xml");
         ExcelGenericImport ex = new ExcelGenericImport();
         ex.processStream(is, ps);
      } catch (Exception var16) {
         var16.printStackTrace();
      } finally {
         if(is != null) {
            try {
               is.close();
            } catch (Exception var15) {
               ;
            }
         }

         if(ps != null) {
            try {
               ps.close();
            } catch (Exception var14) {
               ;
            }
         }

      }

   }

   public void processStream(InputStream is, PrintStream os) throws ExcelGenericImportException, IOException {
      if(is == null) {
         throw new IllegalArgumentException("Input stream cannot be null");
      } else if(os == null) {
         throw new IllegalArgumentException("Output stream cannot be null");
      } else {
         this.mOutputStream = os;

         try {
            this.mWorkbook = Workbook.getWorkbook(is);
         } catch (BiffException var4) {
            throw new ExcelGenericImportException(var4.getMessage());
         }

         this.findAndProcessMainSheet(this.mWorkbook);
      }
   }

   private void findAndProcessMainSheet(Workbook wb) throws ExcelGenericImportException {
      Sheet mainSheet = this.getMainSheet(wb);
      if(mainSheet == null) {
         throw new ExcelGenericImportException("Can\'t find \'ImportDefinition\' sheet.");
      } else {
         this.mOutputStream.println("<?xml version=\"1.0\"?>");
         this.mOutputStream.println("<genericImport xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
         this.mOutputStream.println("\t\t\t\txsi:noNamespaceSchemaLocation=\"generic-import.xsd\"");
         if(!ExcelUtils.doesCellContain(mainSheet, 3, (short)1, "Format Version")) {
            throw this.newExcelGenericImportException("Can\'t find \'Format Version\'");
         } else {
            String format = ExcelUtils.getMandatoryStringCell(mainSheet, 3, (short)2);
            this.processFormatVersion(format, mainSheet);
            this.mOutputStream.println("</genericImport>");
         }
      }
   }

   private Sheet getMainSheet(Workbook wb) throws ExcelGenericImportException {
      this.mCurrentSheetName = "ImportDefinition";
      Sheet sheet = wb.getSheet(this.mCurrentSheetName);
      if(sheet == null) {
         sheet = wb.getSheet(0);
      }

      if(!ExcelUtils.doesCellContain(sheet, 2, (short)1, "CedarOpenAccounts - Generic Import")) {
         throw this.newExcelGenericImportException("Can\'t find ImportDefiniton sheet");
      } else {
         return sheet;
      }
   }

   private ExcelGenericImportException newExcelGenericImportException(String reason) {
      return new ExcelGenericImportException(this.mCurrentSheetName, reason);
   }

   private void processFormatVersion(String format, Sheet mainSheet) throws ExcelGenericImportException {
      if("1.0".equals(format)) {
         FormatVersion1 processor = new FormatVersion1(this.mOutputStream, this.mWorkbook, mainSheet, this.mCurrentSheetName);
         processor.convertFile();
      } else {
         throw this.newExcelGenericImportException("Don\'t understand format \'" + format + "\'");
      }
   }
}
