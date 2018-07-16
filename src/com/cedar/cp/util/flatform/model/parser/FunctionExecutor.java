// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FormulaExecutor;
import com.cedar.cp.util.flatform.model.parser.ValueUtils;
import java.text.ParseException;
import java.util.List;

public abstract class FunctionExecutor {

   protected String mName;
   protected FormulaExecutor mFormulaExecutor;


   public FormulaExecutor getFormulaExecutor() {
      return this.mFormulaExecutor;
   }

   public void setFormulaExecutor(FormulaExecutor formulaExecutor) {
      this.mFormulaExecutor = formulaExecutor;
   }

   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }

   public abstract Object execute(List var1);

   public void preProcessParameter(Object parameter) {}

   public Object getBooleanValue(Object param) {
      if(param instanceof CellRef) {
         param = this.mFormulaExecutor.getCellValue((CellRef)param);
      }

      if(param instanceof CellErrorValue) {
         return param;
      } else if(param instanceof String && CellErrorValue.isErrorString(String.valueOf(param))) {
         return CellErrorValue.getError(String.valueOf(param));
      } else {
         if(param instanceof Number) {
            param = Boolean.valueOf(((Number)param).doubleValue() > 0.0D);
         }

         if(param instanceof String) {
            if(ValueUtils.isNumberChars((String)param)) {
               try {
                  param = Boolean.valueOf(ValueUtils.getDoubleValue((String)param) != 0.0D);
               } catch (ParseException var3) {
                  param = CellErrorValue.VALUE;
               }
            } else {
               param = Boolean.valueOf(Boolean.parseBoolean((String)param));
            }
         }

         return param;
      }
   }

   public Object getIntegerValue(Object param) {
      if(param instanceof CellRef) {
         param = this.mFormulaExecutor.getCellValue((CellRef)param);
      }

      if(param == CellErrorValue.VALUE) {
         param = CellErrorValue.NUM;
      }

      if(param instanceof CellErrorValue) {
         return param;
      } else if(param instanceof String && CellErrorValue.isErrorString(String.valueOf(param))) {
         return CellErrorValue.getError(String.valueOf(param));
      } else {
         if(param instanceof Number) {
            param = Integer.valueOf(((Number)param).intValue());
         }

         if(param instanceof String) {
            if(!ValueUtils.isNumberChars((String)param)) {
               return CellErrorValue.NUM;
            }

            try {
               param = Integer.valueOf((int)ValueUtils.getDoubleValue((String)param));
            } catch (ParseException var3) {
               param = CellErrorValue.VALUE;
            }
         }

         return param;
      }
   }

   protected int getCurrentRow() {
      return this.mFormulaExecutor.getCurrentRow();
   }

   protected int getCurrentColumn() {
      return this.mFormulaExecutor.getCurrentColumn();
   }

   protected Worksheet getCurrentWorksheet() {
      return this.mFormulaExecutor.getWorksheet();
   }
}
