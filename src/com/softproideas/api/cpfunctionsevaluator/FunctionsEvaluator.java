// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.softproideas.api.cpfunctionsevaluator;

import java.util.HashMap;

public interface FunctionsEvaluator {

    Object processExpression(String expression) throws Exception;

    void addBatchExpression(String expression, String cell) throws Exception;

    HashMap<String, Object> submitBatch() throws Exception;
}
