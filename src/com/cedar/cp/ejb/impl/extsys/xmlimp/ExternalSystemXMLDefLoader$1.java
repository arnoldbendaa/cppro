// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import java.io.PrintWriter;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

class ExternalSystemXMLDefLoader$1 implements ErrorHandler {

   // $FF: synthetic field
   final PrintWriter val$stdoutWriter;
   // $FF: synthetic field
   final ExternalSystemXMLDefLoader this$0;


   ExternalSystemXMLDefLoader$1(ExternalSystemXMLDefLoader var1, PrintWriter var2) {
      this.this$0 = var1;
      this.val$stdoutWriter = var2;
   }

   public void warning(SAXParseException exception) throws SAXException {
      this.val$stdoutWriter.println("XML Parser Warning: line:" + exception.getLineNumber() + " Message:" + exception.getMessage());
   }

   public void error(SAXParseException exception) throws SAXException {
      this.val$stdoutWriter.println("XML Parser Error: line:" + exception.getLineNumber() + " Message:" + exception.getMessage());
   }

   public void fatalError(SAXParseException exception) throws SAXException {
      this.val$stdoutWriter.println("XML Parser Fatal Error: line:" + exception.getLineNumber() + " Message:" + exception.getMessage());
   }
}
