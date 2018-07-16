// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.ColumnMappingEntry;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.DynamicCellCalcImportProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.CalendarFilterElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.ColumnMapElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.ColumnMappingElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.ColumnsElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.ContextColumnElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.ContextColumnsElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.ConversionMethodElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DataElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DeploymentElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DeploymentsElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcAbstractElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcImportElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcImportParser$MyContextHandler;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.EntryElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.KeyColumnElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.RowElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.RowKeyElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.AbstractImportParser;
import com.cedar.cp.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class DynamicCellCalcImportParser extends AbstractImportParser {

   static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
   static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
   static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
   private String mUpdateType;
   private Set<String> mDeployments = new HashSet();
   private List<Pair<String, String>> mCalendarFilters = new ArrayList();
   private Map<String, String> mContextColumns = new HashMap();
   private String mContextColumnDataType;
   private Set<String> mCellCalcRowKeys = new HashSet();
   private String mAutoMap;
   private Map<String, ColumnMappingEntry> mColumnMapping = new HashMap();
   private DynamicCellCalcImportProcessor mImportProcessor;


   public DynamicCellCalcImportParser(DynamicCellCalcImportProcessor importProcessor) {
      this.mImportProcessor = importProcessor;
      this.registerElementProcessor("dynamic-cell-calc-import", DynamicCellCalcImportElementProcessor.class);
      this.registerElementProcessor("deployments", DeploymentsElementProcessor.class);
      this.registerElementProcessor("deployment", DeploymentElementProcessor.class);
      this.registerElementProcessor("calendar-filter", CalendarFilterElementProcessor.class);
      this.registerElementProcessor("entry", EntryElementProcessor.class);
      this.registerElementProcessor("context-columns", ContextColumnsElementProcessor.class);
      this.registerElementProcessor("context-column", ContextColumnElementProcessor.class);
      this.registerElementProcessor("row-key", RowKeyElementProcessor.class);
      this.registerElementProcessor("key-column", KeyColumnElementProcessor.class);
      this.registerElementProcessor("column-mapping", ColumnMappingElementProcessor.class);
      this.registerElementProcessor("column-map", ColumnMapElementProcessor.class);
      this.registerElementProcessor("conversion-method", ConversionMethodElementProcessor.class);
      this.registerElementProcessor("data", DataElementProcessor.class);
      this.registerElementProcessor("columns", ColumnsElementProcessor.class);
      this.registerElementProcessor("rows", RowElementProcessor.class);
   }

   protected XMLReader makeXMLReader() throws Exception {
      SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
      saxParserFactory.setValidating(true);
      SAXParser saxParser = saxParserFactory.newSAXParser();
      saxParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
      saxParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", this.getClass().getResourceAsStream("/dynamic-cell-calc-import.xsd"));
      XMLReader xmlReader = saxParser.getXMLReader();
      return xmlReader;
   }

   public ContentHandler getContentHandler() {
      return new DynamicCellCalcImportParser$MyContextHandler(this);
   }

   private DynamicCellCalcAbstractElementProcessor getProcessorForElement(String elementName) throws ValidationException, Exception {
      Class c = this.getElementProcessor(elementName);
      return c != null?(DynamicCellCalcAbstractElementProcessor)c.newInstance():null;
   }

   public String getUpdateType() {
      return this.mUpdateType;
   }

   public void setUpdateType(String updateType) throws SAXException {
      try {
         this.mImportProcessor.setUpdateType(updateType);
         this.mUpdateType = updateType;
      } catch (ValidationException var3) {
         throw new SAXException(var3.getMessage());
      }
   }

   public void setDeployments(Set<String> deployments) throws SAXException {
      try {
         this.mImportProcessor.setDeployments(deployments);
         this.mDeployments = deployments;
      } catch (ValidationException var3) {
         throw new SAXException(var3.getMessage());
      }
   }

   public Set<String> getDeployments() {
      return this.mDeployments;
   }

   public void setCalendarFilters(List<Pair<String, String>> calendarFilters) throws SAXException {
      try {
         this.mImportProcessor.setCalendarFilters(calendarFilters);
         this.mCalendarFilters = calendarFilters;
      } catch (ValidationException var3) {
         throw new SAXException(var3.getMessage());
      }
   }

   public List<Pair<String, String>> getCalendarFilters() {
      return this.mCalendarFilters;
   }

   public void registerContextColumns(List<Pair<String, String>> contextColumns, String dataTypeColumn) throws SAXException {
      try {
         this.mImportProcessor.setContextColumns(contextColumns, dataTypeColumn);
      } catch (ValidationException var4) {
         throw new SAXException(var4.getMessage());
      }
   }

   public void setRowKeys(Set<String> rowKeys) throws SAXException {
      try {
         this.mImportProcessor.setRowKeys(rowKeys);
      } catch (ValidationException var3) {
         throw new SAXException(var3.getMessage());
      }
   }

   public void setColumnMappingAutoMap(String autoMap) throws SAXException {
      try {
         this.mImportProcessor.setColumnMappingsAutoMap(autoMap);
         this.mAutoMap = autoMap;
      } catch (ValidationException var3) {
         throw new SAXException(var3.getMessage());
      }
   }

   public void setColumnMappings(Map<String, ColumnMappingEntry> columnMappings) throws SAXException {
      try {
         this.mImportProcessor.setColumnMappings(columnMappings);
      } catch (ValidationException var3) {
         throw new SAXException(var3.getMessage());
      }
   }

   public DynamicCellCalcImportProcessor getImportProcessor() {
      return this.mImportProcessor;
   }

   public void setModel(String model) throws SAXException {
      try {
         this.mImportProcessor.setModel(model);
         super.setModel(model);
      } catch (ValidationException var3) {
         throw new SAXException(var3.getMessage());
      }
   }

   public void setFinanceCube(String financeCube) throws SAXException {
      try {
         this.mImportProcessor.setFinanceCube(financeCube);
         super.setFinanceCube(financeCube);
      } catch (ValidationException var3) {
         throw new SAXException(var3.getMessage());
      }
   }

   public void setBudgetCycle(String budgetCycle) throws SAXException {
      try {
         this.mImportProcessor.setBudgetCycle(budgetCycle);
         super.setBudgetCycle(budgetCycle);
      } catch (ValidationException var3) {
         throw new SAXException(var3.getMessage());
      }
   }

   // $FF: synthetic method
   static DynamicCellCalcAbstractElementProcessor accessMethod000(DynamicCellCalcImportParser x0, String x1) throws ValidationException, Exception {
      return x0.getProcessorForElement(x1);
   }
}
