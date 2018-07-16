// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.CalendarFilterElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcAbstractElementProcessor;
import com.cedar.cp.util.Pair;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class EntryElementProcessor extends DynamicCellCalcAbstractElementProcessor {

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      String from = this.getAttributeStringValue(attributes, "from");
      String to = this.getAttributeStringValue(attributes, "to");
      CalendarFilterElementProcessor calendarFilterElementProcessor = (CalendarFilterElementProcessor)this.getParser().findElementProcessor(CalendarFilterElementProcessor.class);
      if(calendarFilterElementProcessor == null) {
         throw new IllegalStateException("Failed to locate calendar filter element processor");
      } else {
         calendarFilterElementProcessor.getCalendarFilterEntries().add(new Pair(from, to));
      }
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {}
}
