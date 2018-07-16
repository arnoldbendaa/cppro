// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.base.sax.ElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.AbstractElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalCalendarElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalCalendarYearProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalCompanyProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalCurrencyProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalDimensionElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalDimensionProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalHierarchyElementFeedProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalHierarchyElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalHierarchyProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalImportRowProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalLedgerProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader$1;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader$MyContextHandler;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalValueTypeProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalValuesProcessor;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class ExternalSystemXMLDefLoader extends AbstractDAO {

   private Map<String, Class> mElementProcessors = new HashMap();
   private Stack<ElementProcessor> mProcessingStack = new Stack();
   private PrintWriter mStdOutPrintWriter;
   private PrintWriter mStdErrPrintWriter;
   private boolean mConstraintsFailed;


   public static XMLReader makeXMLReader() throws Exception {
      SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
      SAXParser saxParser = saxParserFactory.newSAXParser();
      XMLReader parser = saxParser.getXMLReader();
      return parser;
   }

   public ExternalSystemXMLDefLoader() {
      this.mElementProcessors.put("genericImport", ExternalSystemProcessor.class);
      this.mElementProcessors.put("company", ExternalCompanyProcessor.class);
      this.mElementProcessors.put("ledger", ExternalLedgerProcessor.class);
      this.mElementProcessors.put("valueType", ExternalValueTypeProcessor.class);
      this.mElementProcessors.put("currency", ExternalCurrencyProcessor.class);
      this.mElementProcessors.put("dimension", ExternalDimensionProcessor.class);
      this.mElementProcessors.put("dimensionElement", ExternalDimensionElementProcessor.class);
      this.mElementProcessors.put("hierarchy", ExternalHierarchyProcessor.class);
      this.mElementProcessors.put("hierarchyElement", ExternalHierarchyElementProcessor.class);
      this.mElementProcessors.put("hierarchyElementFeed", ExternalHierarchyElementFeedProcessor.class);
      this.mElementProcessors.put("calendarYear", ExternalCalendarYearProcessor.class);
      this.mElementProcessors.put("calendarElement", ExternalCalendarElementProcessor.class);
      this.mElementProcessors.put("values", ExternalValuesProcessor.class);
      this.mElementProcessors.put("value", ExternalImportRowProcessor.class);
   }

   public void loadExternalSystem(Reader isReader, PrintWriter stdoutWriter, PrintWriter stderrWriter) throws ValidationException, Exception {
      XMLReader reader = makeXMLReader();
      this.mStdOutPrintWriter = stdoutWriter;
      this.mStdErrPrintWriter = stderrWriter;
      reader.setErrorHandler(new ExternalSystemXMLDefLoader$1(this, stdoutWriter));
      reader.setContentHandler(new ExternalSystemXMLDefLoader$MyContextHandler(this));
      reader.parse(new InputSource(isReader));
      if(this.mConstraintsFailed) {
         throw new ValidationException("Constraint violations detected.");
      }
   }

   public int checkConstraints(int externalSystemId, int maxViolationCount) throws ValidationException {
      int constraintViolationCount = 0;

      AbstractElementProcessor processor;
      for(Iterator i$ = this.mElementProcessors.keySet().iterator(); i$.hasNext(); constraintViolationCount += processor.checkConstraints(externalSystemId, maxViolationCount - constraintViolationCount, this.mStdErrPrintWriter)) {
         String elementName = (String)i$.next();
         if(constraintViolationCount >= maxViolationCount) {
            break;
         }

         processor = null;

         try {
            processor = (AbstractElementProcessor)this.getProcessrForElement(elementName);
         } catch (Exception var8) {
            throw new ValidationException("Failed to instance process for element : " + elementName);
         }
      }

      return constraintViolationCount;
   }

   private ElementProcessor getProcessrForElement(String elementName) throws Exception {
      Class c = (Class)this.mElementProcessors.get(elementName);
      if(c == null) {
         throw new ValidationException("Failed to find element processor for:" + elementName);
      } else {
         Constructor ctor = c.getConstructor(new Class[]{ExternalSystemXMLDefLoader.class});
         return (ElementProcessor)ctor.newInstance(new Object[]{this});
      }
   }

   public ElementProcessor findElementProcessor(Class klass) {
      for(int i = this.mProcessingStack.size() - 1; i >= 0; --i) {
         if(((ElementProcessor)this.mProcessingStack.get(i)).getClass().equals(klass)) {
            return (ElementProcessor)this.mProcessingStack.get(i);
         }
      }

      return null;
   }

   public int getExternalSystemId() {
      ExternalSystemProcessor externalSystemProcessor = (ExternalSystemProcessor)this.findElementProcessor(ExternalSystemProcessor.class);
      if(externalSystemProcessor == null) {
         throw new RuntimeException("Failed to locate ExternalSystemProcessor in context processing stack");
      } else {
         return externalSystemProcessor.getExternalSystemId();
      }
   }

   public String getExternalCompanyVisId() {
      ExternalCompanyProcessor processor = (ExternalCompanyProcessor)this.findElementProcessor(ExternalCompanyProcessor.class);
      if(processor == null) {
         throw new RuntimeException("Failed to locate ExternalCompanyProcessor in context processing stack");
      } else {
         return processor.getExternalCompanyVisId();
      }
   }

   public String getExternalLedgerVisId() {
      ExternalLedgerProcessor processor = (ExternalLedgerProcessor)this.findElementProcessor(ExternalLedgerProcessor.class);
      if(processor == null) {
         throw new RuntimeException("Failed to locate ExternalLedgerProcessor in context processing stack");
      } else {
         return processor.getExternalLedgerVisId();
      }
   }

   public String getExternalValueTypeVisId() {
      ExternalValueTypeProcessor processor = (ExternalValueTypeProcessor)this.findElementProcessor(ExternalValueTypeProcessor.class);
      if(processor == null) {
         throw new RuntimeException("Failed to locate ExternalValueTypeProcessor in context processing stack");
      } else {
         return processor.getExternalValueTypeVisId();
      }
   }

   public String getExternalDimensionVisId() {
      ExternalDimensionProcessor processor = (ExternalDimensionProcessor)this.findElementProcessor(ExternalDimensionProcessor.class);
      if(processor == null) {
         throw new RuntimeException("Failed to locate ExternalDimensionProcessor in context processing stack");
      } else {
         return processor.getExternalDimensionVisId();
      }
   }

   public String getExternalHierarchyVisId() {
      ExternalHierarchyProcessor processor = (ExternalHierarchyProcessor)this.findElementProcessor(ExternalHierarchyProcessor.class);
      if(processor == null) {
         throw new RuntimeException("Failed to locate ExternalHierarchyProcessor in context processing stack");
      } else {
         return processor.getExternalHierarchyVisId();
      }
   }

   public String getExternalHierarchyElementVisId() {
      ExternalHierarchyElementProcessor processor = (ExternalHierarchyElementProcessor)this.findElementProcessor(ExternalHierarchyElementProcessor.class);
      if(processor == null) {
         throw new RuntimeException("Failed to locate ExternalHierarchyElementProcessor in context processing stack");
      } else {
         return processor.getExternalHierarchyElementVisId();
      }
   }

   public String getExternalCalendarYearVisId() {
      ExternalCalendarYearProcessor processor = (ExternalCalendarYearProcessor)this.findElementProcessor(ExternalCalendarYearProcessor.class);
      if(processor == null) {
         throw new RuntimeException("Failed to locate ExternalCalendarYearProcessor in context processing stack");
      } else {
         return processor.getExternalCalendarYearVisId();
      }
   }

   public String getEntityName() {
      return this.getClass().getName();
   }

   public void log(String msg) {
      this.mStdOutPrintWriter.print(msg);
   }

   void setConstraintsViolated(boolean violated) {
      this.mConstraintsFailed = violated;
   }

   // $FF: synthetic method
   static ElementProcessor accessMethod000(ExternalSystemXMLDefLoader x0, String x1) throws Exception {
      return x0.getProcessrForElement(x1);
   }

   // $FF: synthetic method
   static Stack accessMethod100(ExternalSystemXMLDefLoader x0) {
      return x0.mProcessingStack;
   }
}
