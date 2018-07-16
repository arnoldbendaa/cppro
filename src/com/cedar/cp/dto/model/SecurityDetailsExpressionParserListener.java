// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.model.SecurityAccessParserListener;
import java.text.ParseException;

public interface SecurityDetailsExpressionParserListener extends SecurityAccessParserListener {

   void registerToken(String var1) throws ParseException;
}
