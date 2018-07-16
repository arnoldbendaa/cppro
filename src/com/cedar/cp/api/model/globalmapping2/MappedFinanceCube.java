package com.cedar.cp.api.model.globalmapping2;

import com.cedar.cp.api.model.globalmapping2.MappedDataType;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.Serializable;
import java.util.List;

public interface MappedFinanceCube extends Serializable, XMLWritable {

   boolean isNew();

   Object getKey();

   String getName();

   String getDescription();

   List<MappedDataType> getMappedDataTypes();

   MappedDataType findMappedDataType(Object var1);
   
   void setFinanceCompany(String financeCompany);
   
   String getFinanceCompany();
}
