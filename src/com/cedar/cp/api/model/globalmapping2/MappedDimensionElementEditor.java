package com.cedar.cp.api.model.globalmapping2;

import java.util.List;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.globalmapping2.MappedDimensionElement;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;

public interface MappedDimensionElementEditor extends SubBusinessEditor {

   MappedDimensionElement getMappedDimensionElement();

   void setMappingType(int var1) throws ValidationException;

   void setFinanceMappingKey(MappingKey var1) throws ValidationException;

   void setVisId1(String var1) throws ValidationException;

   void setVisId2(String var1) throws ValidationException;
   
   void setSelectedCompanies(List<FinanceCompany> companies) throws ValidationException;
   
   void setSelectedCompany(String companyVisId, Boolean value) throws ValidationException;
}
