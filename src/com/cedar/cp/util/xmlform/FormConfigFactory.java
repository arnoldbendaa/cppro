// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.reader.XMLReader;
import java.io.StringReader;

public class FormConfigFactory {

   public static FormConfig createStandardForm(String xmlFormDef) throws Exception {
      XMLReader reader = new XMLReader();
      reader.init();
      StringReader sr = new StringReader(xmlFormDef);
      reader.parseConfigFile(sr);
      return reader.getFormConfig();
   }
}