// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.mapping.MappedCalendar;
import com.cedar.cp.api.model.mapping.MappedDimension;
import com.cedar.cp.api.model.mapping.MappedFinanceCube;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.util.List;

public interface MappedModel extends XMLWritable {

   Object getPrimaryKey();

   int getModelId();

   int getExternalSystemId();

   String getCompanyVisId();

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
