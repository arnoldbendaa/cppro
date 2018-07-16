package com.cedar.cp.api.model.globalmapping2;

import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarYear;
import com.cedar.cp.api.model.globalmapping2.MappedDataTypeEditor;
import com.cedar.cp.api.model.globalmapping2.MappedFinanceCube;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;

import java.util.List;

public interface MappedFinanceCubeEditor extends SubBusinessEditor {

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;
   
   void setCompany(String company) throws ValidationException;

   void deleteMappedDataType(Object var1) throws ValidationException;

   MappedDataTypeEditor getMappedDataTypeEditor(Object var1) throws ValidationException;

   MappedFinanceCube getMappedFinanceCube();

   List<DataType> getAvailableDataTypes(Object var1);

   List<MappedCalendarYear> getMappedCalendarYears();
}
