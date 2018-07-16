package com.cedar.cp.api.model.globalmapping2;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarEditor;
import com.cedar.cp.api.model.globalmapping2.MappedDimensionEditor;
import com.cedar.cp.api.model.globalmapping2.MappedFinanceCubeEditor;
import com.cedar.cp.api.model.globalmapping2.GlobalMappedModel2;
import com.cedar.cp.api.model.globalmapping2.extsys.ExternalSystem;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger;

import java.io.IOException;
import java.util.List;

public interface GlobalMappedModel2Editor extends BusinessEditor {

   void setModelId(int var1) throws ValidationException;

   void setExternalSystemId(int var1) throws ValidationException;

   void setCompanyVisId(String var1) throws ValidationException;
   
   void setCompaniesVisId(List<String> mCompaniesVisId) throws ValidationException;
   
   void setLedgerVisId(String var1) throws ValidationException;

   void setOwningModelRef(ModelRef var1) throws ValidationException;

   void setExternalSystemRef(ExternalSystemRef var1) throws ValidationException;

   GlobalMappedModel2 getMappedModel();

   List<ExternalSystem> getExternalSystems();

   void setModelVisId(String var1) throws ValidationException;

   void setModelDescription(String var1) throws ValidationException;

   void setFinanceCompanyMappingKey(MappingKey var1) throws ValidationException;
   
   void addFinanceCompanyMappingKey(MappingKey key) throws ValidationException;
   
   void removeFinanceCompanyMappingKey(MappingKey key) throws ValidationException;

   void setFinanceLedgerMappingKey(MappingKey var1) throws ValidationException;

   MappedDimensionEditor getDimensionMappingEditor(Object var1) throws ValidationException;

   void removeMappedDimension(Object var1) throws ValidationException;

   MappedCalendarEditor getMappedCalendarEditor(FinanceLedger financeLedger) throws ValidationException;

   MappedFinanceCubeEditor getMappedFinanceCubeEditor(Object var1) throws ValidationException;

   void removeMappedFinanceCube(Object var1) throws ValidationException;

   ExternalSystem getExternalSystem();

   FinanceCompany getFinanceCompany(String companyVisId);

   FinanceLedger getFinanceLedger(String companyVisId);

   String getXMLSummary() throws IOException;
}
