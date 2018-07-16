// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import java.util.List;

public class MidbFunctionExecutor extends FunctionExecutor {

   public MidbFunctionExecutor() {
      this.setName("MIDB");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function MIDB(). Expected 3 got 0");
      } else if(params.size() != 3) {
         throw new IllegalStateException("Wrong number of parameters to function MIDB(). Expected 3 got " + params.size());
      } else {
         Object text = params.get(0);
         if(text instanceof CellRef) {
            if(((CellRef)text).getWorksheet(this.mFormulaExecutor.getWorksheet()) == null) {
               return CellErrorValue.REF;
            }

            text = this.getText((CellRef)text);
         } else if(text instanceof String) {
            text = text.toString();
         } else {
            if(!(text instanceof Number)) {
               return CellErrorValue.VALUE;
            }

            text = Double.valueOf(((Number)text).doubleValue());
         }

         Object startNum = params.get(1);
         if(startNum instanceof CellRef) {
            startNum = this.mFormulaExecutor.getNumericCellValue((CellRef)startNum);
         } else if(startNum instanceof Number) {
            startNum = Double.valueOf(((Number)startNum).doubleValue());
         } else {
            if(!(startNum instanceof String)) {
               return CellErrorValue.VALUE;
            }

            try {
               startNum = Double.valueOf(Double.parseDouble((String)startNum));
            } catch (NumberFormatException var7) {
               return CellErrorValue.VALUE;
            }
         }

         Object numBytes = params.get(2);
         if(numBytes instanceof CellRef) {
            numBytes = this.mFormulaExecutor.getNumericCellValue((CellRef)numBytes);
         } else if(numBytes instanceof Number) {
            numBytes = Double.valueOf(((Number)numBytes).doubleValue());
         } else {
            if(!(numBytes instanceof String)) {
               return CellErrorValue.VALUE;
            }

            try {
               numBytes = Double.valueOf(Double.parseDouble((String)numBytes));
            } catch (NumberFormatException var6) {
               return CellErrorValue.VALUE;
            }
         }

         return this.midb(String.valueOf(text), ((Number)startNum).intValue(), ((Number)numBytes).intValue());
      }
   }

   private String getText(CellRef cellRef) {
      String result = "";
      Worksheet refWorksheet = cellRef.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
      Cell cell = cellRef.getCell(refWorksheet, this.mFormulaExecutor.getCurrentRow(), this.mFormulaExecutor.getCurrentColumn());
      if(cell != null) {
         result = cell.getStringValue();
      }

      return result;
   }

   private Object midb(String value, int startNum, int numBytes) {
      byte[] arrByte = value.getBytes();
      if(startNum >= 1 && numBytes >= 0) {
         if(startNum > arrByte.length) {
            return "";
         } else if(startNum + numBytes > value.length()) {
            return value.substring(startNum - 1);
         } else {
            char[] arrValue = value.toCharArray();
            Object b = null;
            StringBuffer result = new StringBuffer();
            String s = "";
            int idx = 0;
            int remainByte = numBytes;

            for(int i = 0; i < arrValue.length; ++i) {
               s = String.valueOf(arrValue[i]);
               byte[] var12 = s.getBytes();
               idx += var12.length;
               if(idx > startNum + numBytes) {
                  break;
               }

               if(idx == startNum) {
                  if(var12.length == 1) {
                     result.append(s);
                  }

                  --remainByte;
               } else if(idx > startNum && var12.length <= remainByte) {
                  result.append(s);
                  remainByte -= var12.length;
               }
            }

            return result.toString();
         }
      } else {
         return CellErrorValue.VALUE;
      }
   }
}
