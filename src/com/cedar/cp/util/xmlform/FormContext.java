// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.inputs.InputFactory;
import java.sql.Connection;
import java.util.Map;

public interface FormContext extends InputFactory {

   Connection getSqlConnection();

   void closeSqlConnection();

   Map getVariables();
}
