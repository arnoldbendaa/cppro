// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.softproideas.api.cpfunctionsevaluator;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.softproideas.api.cpfunctionsevaluator.FunctionsEvaluator;

import java.math.BigDecimal;
import java.util.Map;

public interface CPFunctionsEvaluator extends FunctionsEvaluator {

   String FINANCE_CUBE_EXPRESSION = "cedar.cp.financeCube(";
   String DIM0IDENTIFIER_EXPRESSION = "cedar.cp.dim0Identifier()";
   String DIM0DESCRIPTION_EXPRESSION = "cedar.cp.dim0Description()";
   String CELL_EXPRESSION = "cedar.cp.cell(";
   String PARAM_EXPRESSION = "cedar.cp.param(";
   String STRUCTURES_EXPRESSION = "cedar.cp.structures(";
   String GETCELL_EXPRESSION = "cedar.cp.getCell(";
   String PUTCELL_EXPRESSION = "cedar.cp.putCell(";
   String GETVISID_EXPRESSION = "cedar.cp.getVisId(";
   String GETDESC_EXPRESSION = "cedar.cp.getDescription(";
   String GETLABEL_EXPRESSION = "cedar.cp.getLabel(";
   String GETLINK_EXPRESSION = "cedar.cp.formLink(";
   String GETBASEVAL_EXPRESSION = "cedar.cp.getBaseVal(";
   String GETQTY_EXPRESSION = "cedar.cp.getQuantity(";
   String GETCUMBASEVAL_EXPRESSION = "cedar.cp.getCumBaseVal(";
   String GETCUMQTY_EXPRESSION = "cedar.cp.getCumQuantity(";
   String GETCURRENCYLOOKUP_EXPRESSION = "cedar.cp.getCurrencyLookup(";
   String GETPARAMETERLOOKUP_EXPRESSION = "cedar.cp.getParameterLookup(";
   String GETAUCTIONLOOKUP_EXPRESSION = "cedar.cp.getAuctionLookup(";
   String DIM_PARA = "dim";
   String TYPE_PARA = "type";
   String DATATYPE_PARA = "dt";
   String YEAR_PARA = "year";
   String PERIOD_PARA = "period";
   String CONTEXTUAL_PARA = "Contextual";
   BigDecimal BIG_DECIMAL_ZERO = new BigDecimal(0.0D);

  
   int setModelAndFinanceCubeAndCalendar(String modelVisId, String financeCubeVisId, CalendarInfo calendarInfo) throws ValidationException;
   
   int setModelAndFinanceCube(String var1, String var2) throws ValidationException;

   void setHierarchies(String[] var1) throws ValidationException;

   void setCostCenterId(int var1);

   void setCostCenter(String var1);

   void setCostCenterDescription(String var1);

   void setParameters(Map<String, String> var1);
   
   void setCompany(String company);
   
   String getDataType();

}
