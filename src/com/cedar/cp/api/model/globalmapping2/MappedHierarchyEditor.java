package com.cedar.cp.api.model.globalmapping2;

import java.util.List;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.globalmapping2.MappedHierarchy;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;

public interface MappedHierarchyEditor extends SubBusinessEditor {

   MappedHierarchy getMappedHierarchy();

   void setFinanceHierarchyKey(MappingKey var1) throws ValidationException;

   void setNewHierarchyVisId(String var1) throws ValidationException;

   void setNewHierarchyDescription(String var1) throws ValidationException;

   void setResponsibilityAreaHierarchy(boolean var1) throws ValidationException;
   
   void setSelectedCompanies(List<FinanceCompany> companies) throws ValidationException;
   
   void setSelectedCompany(String companyVisId, Boolean value) throws ValidationException;
}
