// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.parser.AbstractImportParser;
import java.io.PrintWriter;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

class AbstractImportParser$1 implements ErrorHandler {

   // $FF: synthetic field
   final PrintWriter val$log;
   // $FF: synthetic field
   final AbstractImportParser this$0;


   AbstractImportParser$1(AbstractImportParser var1, PrintWriter var2) {
      this.this$0 = var1;
      this.val$log = var2;
   }

   public void warning(SAXParseException exception) throws SAXException {
      this.val$log.println("XML Parser Warning: line:" + exception.getLineNumber() + " message:" + exception.getMessage());
   }

   public void error(SAXParseException exception) throws SAXException {
      if(exception.getMessage() == null || exception.getMessage().indexOf("xsi:noNamespaceSchemaLocation") == -1) {
         this.val$log.println("XML Parser Error: line:" + exception.getLineNumber() + " message:" + exception.getMessage());
      }
   }

   public void fatalError(SAXParseException exception) throws SAXException {
      this.val$log.println("XML Parser Fatal Error: line:" + exception.getLineNumber() + " message:" + exception.getMessage());
   }
}
