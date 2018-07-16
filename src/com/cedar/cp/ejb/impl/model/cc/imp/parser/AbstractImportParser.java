// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.parser;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.base.sax.ElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.AbstractImportParser$1;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public abstract class AbstractImportParser {

   private String mModel;
   private String mFinanceCube;
   private String mBudgetCycle;
   private List<String> mLogLines = new ArrayList();
   private Map<String, Class> mElementProcessors = new HashMap();
   private Stack<ElementProcessor> mProcessingStack = new Stack();
   private PrintWriter mPrintWriter;


   public abstract ContentHandler getContentHandler();

   public void loadCellCalcImport(Reader isReader, PrintWriter log) throws ValidationException, Exception {
      this.setPrintWriter(log);
      XMLReader reader = this.makeXMLReader();
      reader.setErrorHandler(new AbstractImportParser$1(this, log));
      reader.setContentHandler(this.getContentHandler());
      reader.parse(new InputSource(isReader));
      Iterator i$ = this.getLogLines().iterator();

      while(i$.hasNext()) {
         String msg = (String)i$.next();
         log.print(msg);
      }

   }

   protected XMLReader makeXMLReader() throws Exception {
      SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
      SAXParser saxParser = saxParserFactory.newSAXParser();
      return saxParser.getXMLReader();
   }

   protected void registerElementProcessor(String elementName, Class klass) {
      this.mElementProcessors.put(elementName, klass);
   }

   protected Class getElementProcessor(String name) {
      return (Class)this.mElementProcessors.get(name);
   }

   public List<String> getLogLines() {
      return this.mLogLines;
   }

   public void setLogLines(List<String> logLines) {
      this.mLogLines = logLines;
   }

   public Map<String, Class> getElementProcessors() {
      return this.mElementProcessors;
   }

   public void setElementProcessors(Map<String, Class> elementProcessors) {
      this.mElementProcessors = elementProcessors;
   }

   public Stack<ElementProcessor> getProcessingStack() {
      return this.mProcessingStack;
   }

   public void setProcessingStack(Stack<ElementProcessor> processingStack) {
      this.mProcessingStack = processingStack;
   }

   public PrintWriter getPrintWriter() {
      return this.mPrintWriter;
   }

   public void setPrintWriter(PrintWriter printWriter) {
      this.mPrintWriter = printWriter;
   }

   public void log(String msg) {
      this.mLogLines.add(msg);
   }

   public String getModel() {
      return this.mModel;
   }

   public void setModel(String model) throws SAXException {
      this.mModel = model;
   }

   public String getFinanceCube() {
      return this.mFinanceCube;
   }

   public void setFinanceCube(String financeCube) throws SAXException {
      this.mFinanceCube = financeCube;
   }

   public String getBudgetCycle() {
      return this.mBudgetCycle;
   }

   public void setBudgetCycle(String budgetCycle) throws SAXException {
      this.mBudgetCycle = budgetCycle;
   }

   public ElementProcessor findElementProcessor(Class klass) {
      for(int i = this.getProcessingStack().size() - 1; i >= 0; --i) {
         if(((ElementProcessor)this.getProcessingStack().get(i)).getClass().equals(klass)) {
            return (ElementProcessor)this.getProcessingStack().get(i);
         }
      }

      return null;
   }
}
