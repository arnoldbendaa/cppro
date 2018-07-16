// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.mapping.MappedCalendarEditor;
import com.cedar.cp.api.model.mapping.MappedDimensionEditor;
import com.cedar.cp.api.model.mapping.MappedFinanceCubeEditor;
import com.cedar.cp.api.model.mapping.MappedModel;
import com.cedar.cp.api.model.mapping.extsys.ExternalSystem;
import com.cedar.cp.api.model.mapping.extsys.FinanceCompany;
import com.cedar.cp.api.model.mapping.extsys.FinanceLedger;
import java.io.IOException;
import java.util.List;

public interface MappedModelEditor extends BusinessEditor {

   void setModelId(int var1) throws ValidationException;

   void setExternalSystemId(int var1) throws ValidationException;

   void setCompanyVisId(String var1) throws ValidationException;

   void setLedgerVisId(String var1) throws ValidationException;

   void setOwningModelRef(ModelRef var1) throws ValidationException;

   void setExternalSystemRef(ExternalSystemRef var1) throws ValidationException;

   MappedModel getMappedModel();

   List<ExternalSystem> getExternalSystems();

   void setModelVisId(String var1) throws ValidationException;

   void setModelDescription(String var1) throws ValidationException;

   void setFinanceCompanyMappingKey(MappingKey var1) throws ValidationException;

   void setFinanceLedgerMappingKey(MappingKey var1) throws ValidationException;

   MappedDimensionEditor getDimensionMappingEditor(Object var1) throws ValidationException;

   void removeMappedDimension(Object var1) throws ValidationException;

   MappedCalendarEditor getMappedCalendarEditor() throws ValidationException;

   MappedFinanceCubeEditor getMappedFinanceCubeEditor(Object var1) throws ValidationException;

   void removeMappedFinanceCube(Object var1) throws ValidationException;

   ExternalSystem getExternalSystem();

   FinanceCompany getFinanceCompany();

   FinanceLedger getFinanceLedger();

   String getXMLSummary() throws IOException;
}
