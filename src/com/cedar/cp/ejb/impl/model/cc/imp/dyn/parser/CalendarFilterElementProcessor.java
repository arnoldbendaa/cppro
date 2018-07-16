// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:42
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcAbstractElementProcessor;
import com.cedar.cp.util.Pair;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CalendarFilterElementProcessor extends DynamicCellCalcAbstractElementProcessor {

   private List<Pair<String, String>> mCalendarFilterEntries = new ArrayList();


   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.mCalendarFilterEntries.clear();
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      this.getParser().setCalendarFilters(this.mCalendarFilterEntries);
   }

   public List<Pair<String, String>> getCalendarFilterEntries() {
      return this.mCalendarFilterEntries;
   }
}
