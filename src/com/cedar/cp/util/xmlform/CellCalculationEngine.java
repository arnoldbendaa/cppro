// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.InterpreterException;
import com.cedar.cp.util.InterpreterWrapper;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CellCalculationEngine$MyFormFunctions;
import com.cedar.cp.util.xmlform.Footer;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.Header;
import com.cedar.cp.util.xmlform.OnFormLoad;
import com.cedar.cp.util.xmlform.Spread;
import com.cedar.cp.util.xmlform.Summary;
import com.cedar.cp.util.xmlform.SummarySpreads;
import com.cedar.cp.util.xmlform.inputs.FormDataInputModel;
import com.cedar.cp.util.xmlform.swing.FormModel;
import com.cedar.cp.util.xmlform.swing.FormTableModel;
import com.cedar.cp.util.xmlform.swing.FormulaParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CellCalculationEngine {

   private FormConfig mFormConfig;
   private FormModel mFormModel;
   private InterpreterWrapper mEngine;
   private CellCalculationEngine$MyFormFunctions mFormFunctions;


   public FormModel getFormModel() {
      return this.mFormModel;
   }

   public CellCalculationEngine(FormConfig config) throws FormulaParseException {
      this.mFormConfig = config;
      this.mFormModel = new FormModel(config);
      this.mEngine = new InterpreterWrapper();
   }

   public Map<String, Object> runCalculator(Map contextVariables, FormDataInputModel previousData, Map summaryOverrides) throws FormulaParseException {
      try {
         this.mFormFunctions = new CellCalculationEngine$MyFormFunctions(this, contextVariables);
         this.mEngine.setVariable("FormFunctions", this.mFormFunctions);
         this.mEngine.eval("import java.lang.String;\nimport java.lang.Double;\nimport java.lang.Boolean;\nimport java.util.Date;\nimport java.util.Calendar;\nimport com.cedar.cp.util.xmlform.PropertiesMap;\n;import com.cedar.cp.util.xmlform.CalendarInfo;\ndouble result = 0.0;\nString stringResult = \"\";\nDate dateResult = null;\nString defaultDateFormat = \"yyyy-MM-dd\";\nint rownum = -1;\nboolean isBlank( colValue ) { return FormFunctions.isBlank(colValue); }\nboolean flagSet( colValue ) { return FormFunctions.isFlagSet(colValue); }\nString left( colValue, colLen ) { return FormFunctions.left(colValue,colLen); }\nString right( colValue, colLen ) { return FormFunctions.right(colValue,colLen); }\ndouble round( colValue, scale ) { return FormFunctions.round(colValue,scale); }\nString sysVariable( varName ) { return FormFunctions.sysVar(varName); }\ndouble sum( colName ) { return FormFunctions.sum(colName); }\nboolean isValidDate( date ) { return FormFunctions.isValidDate(date, defaultDateFormat); }\nboolean isValidDate( date, format ) { return FormFunctions.isValidDate(date, format); }\nDate getContextDate() { return FormFunctions.getContextDate(); }\nDate getContextDate( int yearOffset, int monthOffset, int dayOffset) { return FormFunctions.getContextDate(yearOffset, monthOffset, dayOffset); }\nDate getContextColumnDate() { return FormFunctions.getContextColumnDate(); }\nDate getDate( String date ) { return FormFunctions.getDate(date, defaultDateFormat); }\nDate getDate( String date, String format ) { return FormFunctions.getDate(date, format); }\nint getYear( String date ) { return FormFunctions.getYear(date, defaultDateFormat); }\nint getYear( String date, String format ) { return FormFunctions.getYear(date, format); }\nint getYear( java.util.Date date ) { return FormFunctions.getYear(date); }\nint getMonth( String date ) { return FormFunctions.getMonth(date, defaultDateFormat); }\nint getMonth( String date, String format ) { return FormFunctions.getMonth(date, format); }\nint getMonth( java.util.Date date ) { return FormFunctions.getMonth(date); }\nint getDayOfMonth( String date ) { return FormFunctions.dayOfMonth(date, defaultDateFormat); }\nint getDayOfMonth( String date, String format ) { return FormFunctions.dayOfMonth(date, format); }\nint getDayOfMonth( java.util.Date date ) { return FormFunctions.dayOfMonth(date); }\nint getDayOfYear( String date ) { return FormFunctions.dayOfYear(date, defaultDateFormat); }\nint getDayOfYear( String date, String format ) { return FormFunctions.dayOfYear(date, format); }\nint getDayOfYear( java.util.Date date ) { return FormFunctions.dayOfYear(date); }\nint compareDates( dateOneText, dateTwoText, format ) { return FormFunctions.compareDates(dateOneText, dateTwoText, format); }\nint getDifferenceInMonths( String dateOneText, String dateTwoText) { return FormFunctions.getDifferenceInMonths(dateOneText, dateTwoText, null); }\nint getDifferenceInMonths( String dateOneText, String dateTwoText, String format ) { return FormFunctions.getDifferenceInMonths(dateOneText, dateTwoText, format); }\nint getDifferenceInMonths( java.util.Date startDate, java.util.Date endDate ) { return FormFunctions.getDifferenceInMonths(startDate, endDate); }\nint getDaysInMonth( String date ) { return FormFunctions.getDaysInMonth(date, defaultDateFormat); }\nint getDaysInMonth( String date, String format ) { return FormFunctions.getDaysInMonth(date, format); }\nint getDaysInMonth( java.util.Date date ) { return FormFunctions.getDaysInMonth(date); }\nint getDaysInYear( String date ) { return FormFunctions.getDaysInYear(date, defaultDateFormat); }\nint getDaysInYear( String date, String format ) { return FormFunctions.getDaysInYear(date, format); }\nint getDaysInYear( java.util.Date date ) { return FormFunctions.getDaysInYear(date); }\nint getDifferenceInDays( String dateOneText, String dateTwoText ) { return FormFunctions.getDifferenceInDays(dateOneText, dateTwoText, defaultDateFormat); }\nint getDifferenceInDays( String dateOneText, String dateTwoText, String format ) { return FormFunctions.getDifferenceInDays(dateOneText, dateTwoText, format); }\nint getDifferenceInDays( Date startDate, Date endDate ) { return FormFunctions.getDifferenceInDays(startDate, endDate); }\nString calcNewDate( startDate, format, days ) { return FormFunctions.calcNewDate(startDate, format, days); }\nDate adjustDate( java.util.Date date, int yearOffset, int monthOffset, int dayOffset ) { return FormFunctions.adjustDate(date, yearOffset, monthOffset, dayOffset); }\ndouble lookup( String colName, Object colValue ) { return FormFunctions.lookup(colName,colValue,null, null); }\ndouble lookup( String colName, Object colValue, Date date ) { return FormFunctions.lookup(colName,colValue,null,date); }\ndouble lookup( String colName, Object colValue, String partitionValue ) { return FormFunctions.lookup(colName,colValue,partitionValue, null); }\ndouble lookup( String colName, Object colValue, String partitionValue, Date date ) { return FormFunctions.lookup(colName,colValue,partitionValue, date); }\nString lookupString( String colName, Object colValue ) { return FormFunctions.lookupString(colName,colValue,null, null); }\nString lookupString( String colName, Object colValue, Date date ) { return FormFunctions.lookupString(colName,colValue,null, date); }\nString lookupString( String colName, Object colValue, String partitionValue ) { return FormFunctions.lookupString(colName,colValue,partitionValue, null); }\nString lookupString( String colName, Object colValue, String partitionValue, Date date ) { return FormFunctions.lookupString(colName,colValue,partitionValue, date); }\nDate lookupDate( String colName, Object colValue ) { return FormFunctions.lookupDate(colName,colValue,null, null); }\nDate lookupDate( String colName, Object colValue, Date date ) { return FormFunctions.lookupDate(colName,colValue,null, date); }\nDate lookupDate( String colName, Object colValue, String partitionValue ) { return FormFunctions.lookupDate(colName,colValue,partitionValue, null); }\nDate lookupDate( String colName, Object colValue, String partitionValue, Date date ) { return FormFunctions.lookupDate(colName,colValue,partitionValue, date); }\ndouble level() { return FormFunctions.level(); }\nint rowNumber() { return FormFunctions.rowNumber(); }\nString colDataType( colName ) { return FormFunctions.columnDataType(colName); }\nString colPeriodVisId( colName ) { return FormFunctions.columnPeriodVisId(colName); }\nString colPeriodPathToRoot( colName ) { return FormFunctions.columnPeriodPathToRoot(colName); }\nDate colPeriodDate( colName ) { return FormFunctions.columnPeriodDate(colName); }\nString elementVisId( dimIndex ) { return FormFunctions.elementVisId(dimIndex); }\nboolean isReadOnlyNominal(){ FormFunctions.isReadOnlyNominal(); }\nvoid setColumnHeader( colName, colHeaderText ){ FormFunctions.setColumnHeader( colName, colHeaderText ); }\nvoid setColumnGroupHeader( colName, colHeaderText ){ FormFunctions.setColumnGroupHeader( colName, colHeaderText ); }\nvoid registerValidationMessage( String colName, String message ){ FormFunctions.registerValidationMessage( rownum, colName, message ); }\nvoid clearValidationMessage( String colName ){ FormFunctions.clearValidationMessage( rownum, colName ); }\nvoid clearValidationMessages(){ FormFunctions.clearValidationMessage( rownum ); }\nString getBudgetCycleStateAsString(){ return FormFunctions.getBudgetCycleStateAsString(); }\n String getBudgetCycleStateAsString( String costCentre ){ return FormFunctions.getBudgetCycleStateAsString(costCentre); }\n ");
         this.evaluateOnFormLoad(this.mFormConfig.getHeader());
      } catch (InterpreterException var5) {
         throw new FormulaParseException(var5);
      }

      FormTableModel tableModel = this.mFormModel.getFormTableModel();
      tableModel.rebuildFromConfig(this.mFormConfig);
      tableModel.setPreviousData(previousData);
      return this.runSummaryFormulae(summaryOverrides);
   }

   private void evaluateOnFormLoad(Header header) throws InterpreterException {
      OnFormLoad formLoad = header.getOnFormLoad();
      if(formLoad != null) {
         String formula = formLoad.getFormula();
         if(formula != null) {
            this.mEngine.eval(formula);
         }
      }

   }

   private Map<String, Object> runSummaryFormulae(Map summaryOverrides) {
      HashMap summaries = new HashMap();
      Footer f = this.mFormConfig.getFooter();
      Iterator ss = f.getSummaries();

      while(ss.hasNext()) {
         Summary s = (Summary)ss.next();
         Object result;
         if(s.isOverridden()) {
            result = summaryOverrides.get(s.getId());
            if(result != null && result.toString().trim().length() > 0) {
               summaries.put(s.getId(), result);
               continue;
            }
         }

         result = null;

         try {
            if(s.getFormula() != null) {
               this.mEngine.eval(s.getFormula());
               result = this.mEngine.getVariable("result");
            } else if(s.getSummarySpreads() != null) {
               SummarySpreads evalError = s.getSummarySpreads();
               double spreadValue = 0.0D;
               Iterator iter = evalError.getSpreads().iterator();

               while(iter.hasNext()) {
                  Spread spread = (Spread)iter.next();
                  if(spread.getFormula() != null) {
                     this.mEngine.eval(spread.getFormula());
                     Double d = (Double)this.mEngine.getVariable("result");
                     spreadValue += d.doubleValue();
                  }
               }

               result = new Double(spreadValue);
            }
         } catch (InterpreterException var13) {
            throw new FormulaParseException(var13);
         }

         summaries.put(s.getId(), result);
      }

      return summaries;
   }

   public Map<Integer, Double> getSummarySpreads(String id, CalendarElementNode periodRootNode) {
      Footer f = this.mFormConfig.getFooter();
      Iterator ss = f.getSummaries();

      while(ss.hasNext()) {
         Summary s = (Summary)ss.next();
         if(s.getId().equals(id) && s.getSummarySpreads() != null) {
            HashMap result = new HashMap();
            SummarySpreads spreads = s.getSummarySpreads();
            Iterator iter = spreads.getSpreads().iterator();

            while(iter.hasNext()) {
               Spread spread = (Spread)iter.next();
               if(spread.getFormula() != null) {
                  try {
                     this.mEngine.eval(spread.getFormula());
                     Double ie = (Double)this.mEngine.getVariable("result");
                     Object periodId = this.getPeriodIdFromVisId(periodRootNode, spread.getPeriod());
                     if(periodId != null) {
                        result.put((Integer)periodId, ie);
                     }
                  } catch (InterpreterException var12) {
                     ;
                  }
               }
            }

            if(!result.isEmpty()) {
               return result;
            }
         }
      }

      return null;
   }

   private Object getPeriodIdFromVisId(CalendarElementNode periodRootNode, String visId) {
      Enumeration e = periodRootNode.depthFirstEnumeration();

      CalendarElementNode node;
      do {
         if(!e.hasMoreElements()) {
            return null;
         }

         node = (CalendarElementNode)e.nextElement();
      } while(!node.getVisId().equals(visId));

      return node.getKey();
   }

   // $FF: synthetic method
   static FormModel accessMethod000(CellCalculationEngine x0) {
      return x0.mFormModel;
   }
}
