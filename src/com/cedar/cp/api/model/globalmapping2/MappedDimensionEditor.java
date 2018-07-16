package com.cedar.cp.api.model.globalmapping2;

import java.util.List;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.globalmapping2.MappedDimension;
import com.cedar.cp.api.model.globalmapping2.MappedDimensionElementEditor;
import com.cedar.cp.api.model.globalmapping2.MappedHierarchyEditor;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;

public interface MappedDimensionEditor extends SubBusinessEditor {

   void setFinanceDimensionKey(MappingKey var1) throws ValidationException;

   void setDimensionVisId(String var1) throws ValidationException;

   void setDimensionDescription(String var1) throws ValidationException;

   void setNullElementVisId(String var1) throws ValidationException;

   void setNullElementDescription(String var1) throws ValidationException;

   void setNullElementCrDrFlag(Integer var1) throws ValidationException;

   void setDimensionType(int var1) throws ValidationException;

   void setDisabledLeafNodesExcluded(boolean var1);

   MappedDimension getDimensionMapping();

   void removeMappedHierarchy(Object var1) throws ValidationException;

   MappedHierarchyEditor getMappedHierarchyEditor(Object var1) throws ValidationException;

   MappedDimensionElementEditor getMappedDimensionElementEditor(Object var1) throws ValidationException;

   void removeMappedDimensionElement(Object var1) throws ValidationException;
   
   void setSelectedCompanies(List<FinanceCompany> companies) throws ValidationException;
   
   void setSelectedCompany(String companyVisId, Boolean value) throws ValidationException;
}
