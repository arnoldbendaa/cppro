// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.fe;

import com.cedar.cp.util.fe.FunctionsEvaluator;
import java.util.HashMap;
import java.util.Map;

public class FunctionsEvaluatorHolder {

   private static final String PREFIX = "cedar.";
   private Map<String, FunctionsEvaluator> mEvaluators = new HashMap();


   public void addEvaluator(String key, FunctionsEvaluator evaluator) {
      if(!key.startsWith("cedar.")) {
         throw new IllegalArgumentException("Invalid plugin prefix");
      } else {
         String strippedKey = key.substring("cedar.".length());
         if(strippedKey.length() < 1) {
            throw new IllegalArgumentException("Invalid plugin prefix");
         } else {
            int moduleIndex = strippedKey.indexOf(".");
            if(moduleIndex >= 0) {
               throw new IllegalArgumentException("Invalid plugin prefix");
            } else {
               this.mEvaluators.put(strippedKey, evaluator);
            }
         }
      }
   }

   public Object processExpression(String expression) throws Exception {
      if(expression != null && expression.trim().length() != 0) {
         FunctionsEvaluator evaluator = this.getEvaluator(expression);
         return evaluator != null?evaluator.processExpression(expression):expression;
      } else {
         return null;
      }
   }

   public Object addBatchExpression(String expression, Object linkKey) throws Exception {
      if(expression != null && expression.trim().length() != 0) {
         FunctionsEvaluator evaluator = this.getEvaluator(expression);
         return evaluator != null?evaluator.addBatchExpression(expression, linkKey):expression;
      } else {
         return null;
      }
   }

   public Map submitBatch(String modulePrefix) throws Exception {
      if(modulePrefix != null && modulePrefix.trim().length() != 0) {
         FunctionsEvaluator evaluator = this.getEvaluator(modulePrefix);
         return evaluator != null?evaluator.submitBatch():null;
      } else {
         return null;
      }
   }

   private FunctionsEvaluator getEvaluator(String expression) {
      if(expression != null && expression.length() >= "cedar.".length()) {
         String strippedKey = expression.substring("cedar.".length());
         int moduleIndex = strippedKey.indexOf(".");
         if(moduleIndex < 0) {
            return null;
         } else {
            String module = strippedKey.substring(0, moduleIndex);
            FunctionsEvaluator evaluator = (FunctionsEvaluator)this.mEvaluators.get(module);
            return evaluator == null?null:evaluator;
         }
      } else {
         return null;
      }
   }
}
