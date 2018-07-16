// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xlsimport;


public class ExcelGenericImportException extends Exception {

   private String mCurrentSheetName;


   public ExcelGenericImportException(String message) {
      this("", message);
   }

   public ExcelGenericImportException(String sheetName, String reason) {
      super(reason);
      this.mCurrentSheetName = sheetName;
   }
}
