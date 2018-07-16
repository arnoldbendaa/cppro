// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping;

import com.cedar.cp.api.model.mapping.MappedDataType;
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
}
