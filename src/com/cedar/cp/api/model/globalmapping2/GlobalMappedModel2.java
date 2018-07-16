package com.cedar.cp.api.model.globalmapping2;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.globalmapping2.MappedCalendar;
import com.cedar.cp.api.model.globalmapping2.MappedDimension;
import com.cedar.cp.api.model.globalmapping2.MappedFinanceCube;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.util.List;

public interface GlobalMappedModel2 extends XMLWritable {

   Object getPrimaryKey();

   int getModelId();

   int getExternalSystemId();

   String getCompanyVisId();
   
   List<String> getCompaniesVisId();
   
   String getLedgerVisId();

   ModelRef getOwningModelRef();

   ExternalSystemRef getExternalSystemRef();

   boolean isNew();

   String getModelVisId();

   String getModelDescription();

   List<MappedDimension> getDimensionMappings();

   MappingKey getFinanceCompanyKey();

   MappingKey getFinanceLedgerKey();

   MappedDimension findMappedDimension(MappingKey var1);

   MappedCalendar getMappedCalendar();

   List<MappedFinanceCube> getMappedFinanceCubes();

   boolean isRespAreaHierarchySelected();
}
