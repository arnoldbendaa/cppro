// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.mapping;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.mapping.MappedDimensionElement;

public interface MappedDimensionElementEditor extends SubBusinessEditor {

   MappedDimensionElement getMappedDimensionElement();

   void setMappingType(int var1) throws ValidationException;

   void setFinanceMappingKey(MappingKey var1) throws ValidationException;

   void setVisId1(String var1) throws ValidationException;

   void setVisId2(String var1) throws ValidationException;
}
