// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:42
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.ColumnMappingEntry;
import com.cedar.cp.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.xml.sax.SAXException;

public interface DynamicCellCalcImportProcessor {

   void setModel(String var1) throws ValidationException;

   int getModelId();

   void setFinanceCube(String var1) throws ValidationException;

   void setBudgetCycle(String var1) throws ValidationException;

   void setUpdateType(String var1) throws ValidationException;

   void setDeployments(Set<String> var1) throws ValidationException;

   void setCalendarFilters(List<Pair<String, String>> var1) throws ValidationException;

   void setRowKeys(Set<String> var1) throws ValidationException;

   void setContextColumns(List<Pair<String, String>> var1, String var2) throws ValidationException;

   void setColumnMappingsAutoMap(String var1) throws ValidationException;

   void setColumnMappings(Map<String, ColumnMappingEntry> var1) throws ValidationException;

   int importCSVFile(int var1, String var2, String var3) throws SAXException;

   void importExcelFile(int var1, String var2) throws SAXException;
}
