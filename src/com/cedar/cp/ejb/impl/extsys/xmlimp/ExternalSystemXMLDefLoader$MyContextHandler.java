// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.ejb.impl.base.sax.ElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class ExternalSystemXMLDefLoader$MyContextHandler extends DefaultHandler {

   // $FF: synthetic field
   final ExternalSystemXMLDefLoader this$0;


   ExternalSystemXMLDefLoader$MyContextHandler(ExternalSystemXMLDefLoader var1) {
      this.this$0 = var1;
   }

   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      try {
         ElementProcessor e = ExternalSystemXMLDefLoader.accessMethod000(this.this$0, qName);
         e.processStartElement(uri, localName, qName, attributes);
         ExternalSystemXMLDefLoader.accessMethod100(this.this$0).push(e);
      } catch (Exception var6) {
         var6.printStackTrace();
         throw new SAXException(var6);
      }
   }

   public void endElement(String uri, String localName, String qName) throws SAXException {
      ElementProcessor processor = (ElementProcessor)ExternalSystemXMLDefLoader.accessMethod100(this.this$0).pop();
      processor.processEndElement(uri, localName, qName);
   }
}
