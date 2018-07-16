// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.ParseException;
import com.cedar.cp.util.InterpreterException;
import java.util.Collections;
import java.util.Set;

public class InterpreterWrapper {

   private Interpreter mEngine = new Interpreter();


   public InterpreterWrapper() {
      this.mEngine.getClassManager().reset();
   }

   private static String getSafeName(String name) {
      return name.replaceAll("[ \t\n\r(){}\'\";]", "_");
   }

   public static String getColumnFormulaFunctionName(String id) {
      return "columnFormula" + getSafeName(id) + "()";
   }

   public static String getColumnFormulaFunction(String id, String formula) {
      return "void " + getColumnFormulaFunctionName(id) + " {\n " + formula + ";\n}\n";
   }

   public static String getSummaryFormulaFunctionName(String id) {
      return "summaryFormula" + getSafeName(id) + "()";
   }

   public static String getSummaryFormulaFunction(String id, String formula) {
      return "void " + getSummaryFormulaFunctionName(id) + " { " + formula + ";}\n";
   }

   public static String getTotalFormulaFunctionName(String id) {
      return "totalFormula" + getSafeName(id) + "()";
   }

   public static String getTotalFormulaFunction(String id, String formula) {
      return "void " + getTotalFormulaFunctionName(id) + " { " + formula + ";}\n";
   }

   public Object eval(String expression) throws InterpreterException {
      try {
         return this.mEngine.eval(expression);
      } catch (EvalError var3) {
         throw new InterpreterException("Can\'t evaluate " + var3.getMessage(), var3);
      }
   }

   public Object getVariable(String name) throws InterpreterException {
      try {
         return this.mEngine.get(name);
      } catch (EvalError var3) {
         throw new InterpreterException("Can\'t get property", var3);
      }
   }

   public void setVariable(String name, Object value) throws InterpreterException {
      try {
         this.mEngine.set(name, value);
      } catch (EvalError var4) {
         throw new InterpreterException("Can\'t set property", var4);
      }
   }

   public Set getPrimaryExpressions(String formula) throws InterpreterException {
      if(formula == null) {
         return Collections.EMPTY_SET;
      } else {
         try {
//            return ParseHelper.getPrimaryExpressions(formula + ";");
        	 return null;
         } catch (Error var3) {
            throw new InterpreterException("Can\'t get primary Expressions", var3);
         }
      }
   }
}
