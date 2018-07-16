// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import java.util.List;

public class CellCalcExpressionDetails {

   private int cellCalcId;
   private String expression;
   private List range;


   public int getCellCalcId() {
      return this.cellCalcId;
   }

   public void setCellCalcId(int cellCalcId) {
      this.cellCalcId = cellCalcId;
   }

   public String getExpression() {
      return this.expression;
   }

   public void setExpression(String expression) {
      this.expression = expression;
   }

   public List getRange() {
      return this.range;
   }

   public void setRange(List range) {
      this.range = range;
   }
}
