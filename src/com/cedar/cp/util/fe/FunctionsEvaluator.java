// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.fe;

import java.util.Map;

public interface FunctionsEvaluator {

   Object processExpression(String var1) throws Exception;

   Object addBatchExpression(String var1, Object var2) throws Exception;

   Map submitBatch() throws Exception;
}
