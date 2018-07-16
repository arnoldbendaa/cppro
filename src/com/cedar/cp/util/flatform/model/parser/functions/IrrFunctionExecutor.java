// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:26
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser.functions;

import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.CellRange;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FunctionExecutor;
import com.cedar.cp.util.sparse.LinkedListSparse2DArray;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IrrFunctionExecutor extends FunctionExecutor {

   public IrrFunctionExecutor() {
      this.setName("IRR");
   }

   public Object execute(List params) {
      if(params == null) {
         throw new IllegalStateException("Wrong number of parameters to function IRR()");
      } else if(params.size() < 1) {
         throw new IllegalStateException("Wrong number of parameters to function IRR(). Expected 1 got " + params.size());
      } else if(params.size() > 2) {
         throw new IllegalStateException("Wrong number of parameters to function IRR(). Expected 2 got " + params.size());
      } else {
         double estimatedResult = 0.0D;
         if(params.size() == 2) {
            Object cashFlows = params.get(1);
            if(!(cashFlows instanceof Number)) {
               throw new IllegalStateException("Wrong format of parameters to function IRR(). Expected a number value");
            }

            estimatedResult = ((Number)cashFlows).doubleValue();
         }

         double[] cashFlows1 = new double[params.size()];
         Object param = params.get(0);
         if(!(param instanceof CellRef)) {
            if(param instanceof CellRange) {
               if(((CellRange)param).getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet()) == null) {
                  return CellErrorValue.REF;
               }

               cashFlows1 = this.getValueListFromCellRange((CellRange)param);
            } else if(!(param instanceof Number) && !(param instanceof CellErrorValue) && !(param instanceof String)) {
               throw new IllegalArgumentException("Unexpected parameter " + param + " in IRR() function");
            }
         }

         double irr = this.getIRR(cashFlows1, estimatedResult);
         String sIrr = this.roundUp(irr * 100.0D, 4) + "%";
         return sIrr;
      }
   }

   private double getIRR(double[] cashFlows, double estimatedResult) {
      double result = Double.NaN;
      if(cashFlows != null && cashFlows.length > 0 && cashFlows[0] != 0.0D) {
         double noOfCashFlows = (double)cashFlows.length;
         double sumCashFlows = 0.0D;
         int noOfNegativeCashFlows = 0;
         int noOfPositiveCashFlows = 0;

         for(int irrGuess = 0; (double)irrGuess < noOfCashFlows; ++irrGuess) {
            sumCashFlows += cashFlows[irrGuess];
            if(cashFlows[irrGuess] > 0.0D) {
               ++noOfPositiveCashFlows;
            } else if(cashFlows[irrGuess] < 0.0D) {
               ++noOfNegativeCashFlows;
            }
         }

         if(noOfNegativeCashFlows > 0 && noOfPositiveCashFlows > 0) {
            double var26 = 0.1D;
            if(estimatedResult != Double.NaN) {
               var26 = estimatedResult;
               if(estimatedResult <= 0.0D) {
                  var26 = 0.5D;
               }
            }

            double irr = 0.0D;
            if(sumCashFlows < 0.0D) {
               irr = -var26;
            } else {
               irr = var26;
            }

            double minDistance = 1.0E-7D;
            double cashFlowStart = cashFlows[0];
            boolean maxIteration = true;
            boolean wasHi = false;
            double cashValue = 0.0D;

            for(int i = 0; i <= 50; ++i) {
               cashValue = cashFlowStart;

               for(int j = 1; (double)j < noOfCashFlows; ++j) {
                  cashValue += cashFlows[j] / Math.pow(1.0D + irr, (double)j);
               }

               if(Math.abs(cashValue) < 0.01D) {
                  result = irr;
                  break;
               }

               if(cashValue > 0.0D) {
                  if(wasHi) {
                     var26 /= 2.0D;
                  }

                  irr += var26;
                  if(wasHi) {
                     var26 -= 1.0E-7D;
                     wasHi = false;
                  }
               } else {
                  var26 /= 2.0D;
                  irr -= var26;
                  wasHi = true;
               }

               if(var26 <= 1.0E-7D) {
                  result = irr;
                  break;
               }
            }
         }
      }

      return result;
   }

   private double[] getValueListFromCellRange(CellRange cellRange) {
      ArrayList cashFlows = new ArrayList();
      int startColumn = Math.min(cellRange.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), cellRange.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
      int endColumn = Math.max(cellRange.getStartRef().getAbsoluteColumn(this.getCurrentColumn()), cellRange.getEndRef().getAbsoluteColumn(this.getCurrentColumn()));
      int startRow = Math.min(cellRange.getStartRef().getAbsoluteRow(this.getCurrentRow()), cellRange.getEndRef().getAbsoluteRow(this.getCurrentRow()));
      int endRow = Math.max(cellRange.getStartRef().getAbsoluteRow(this.getCurrentRow()), cellRange.getEndRef().getAbsoluteRow(this.getCurrentRow()));
      Worksheet refWorksheet = cellRange.getStartRef().getWorksheet(this.mFormulaExecutor.getWorksheet());
      Iterator cellIter = refWorksheet.rangeIterator(startRow, startColumn, endRow, endColumn);

      while(cellIter.hasNext()) {
         LinkedListSparse2DArray.CellLink values = (LinkedListSparse2DArray.CellLink)cellIter.next();
         Cell i = (Cell)refWorksheet.get(values.getRow(), values.getColumn());
         if(i != null && i.getStringValue() != null && !i.getStringValue().equals("")) {
            Object numberValue = i.getNumericValue();
            if(numberValue instanceof Number) {
               double value = ((Number)numberValue).doubleValue();
               cashFlows.add(Double.valueOf(value));
            }
         }
      }

      double[] var15 = new double[cashFlows.size()];

      for(int var14 = 0; var14 < cashFlows.size(); ++var14) {
         var15[var14] = ((Double)cashFlows.get(var14)).doubleValue();
      }

      return var15;
   }

   private double roundUp(double Rval, int Rpl) {
      double p = Math.pow(10.0D, (double)Rpl);
      Rval *= p;
      double tmp = (double)Math.round(Rval);
      return tmp / p;
   }
}
