// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.flatform;

import com.cedar.cp.ejb.base.cube.Cell;
import java.util.Map;

public class WorksheetCellUpdate extends Cell {

   private boolean mPutCell = false;
   private String mRow;
   private String mCol;
   private String mValue;
   private String[] mAddressElements;


   public String getRow() {
      return this.mRow;
   }

   public void setRow(String row) {
      this.mRow = row;
   }

   public String getCol() {
      return this.mCol;
   }

   public void setCol(String col) {
      this.mCol = col;
   }

   public String getValue() {
      return this.mValue;
   }

   public void setValue(String value) {
      this.mValue = value;
   }

   public void replaceParameterMacros(Map<String, String> parameters) {
      String[] addresses = this.getAddressElements();

      for(int i = 0; i < addresses.length - 1; ++i) {
         String replacementValue = (String)parameters.get(addresses[i]);
         if(replacementValue != null) {
            addresses[i] = replacementValue;
         }
      }

   }

   public String[] getAddressElements() {
      if(this.mAddressElements == null) {
          String[] addressElements = this.getAddr().split(",");
          if (addressElements.length > 4) {
              this.mAddressElements = new String[4];
              this.mAddressElements[0] = addressElements[0];
              this.mAddressElements[1] = addressElements[1];
              this.mAddressElements[2] = addressElements[2];
              this.mAddressElements[3] = addressElements[3];
          } else {
              this.mAddressElements = addressElements;
          }
          
      }

      return this.mAddressElements;
   }

   public void setAddressElements(String[] addressElements) {
      this.mAddressElements = addressElements;
   }

   public String getDataType() {
      String[] addressElements = this.getAddressElements();
      return addressElements != null?addressElements[3]:null;
   }

   public void setDataType(String dataType) {
      String[] addressElements = this.getAddressElements();
      addressElements[3] = dataType;
   }

   public boolean isPutCell() {
      return this.mPutCell;
   }

   public void setPutCell(boolean mPutCell) {
      this.mPutCell = mPutCell;
   }
}
