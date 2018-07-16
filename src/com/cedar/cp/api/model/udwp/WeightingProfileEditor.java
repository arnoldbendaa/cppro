// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.udwp;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.udwp.WeightingProfile;
import java.util.List;

public interface WeightingProfileEditor extends BusinessEditor {

   void setModelId(int var1) throws ValidationException;

   void setStartLevel(int var1) throws ValidationException;

   void setLeafLevel(int var1) throws ValidationException;

   void setProfileType(int var1) throws ValidationException;

   void setDynamicOffset(int var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setDynamicDataTypeId(Integer var1) throws ValidationException;

   void setDynamicEsIfWfbtoz(Boolean var1) throws ValidationException;

   void setModelRef(ModelRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   WeightingProfile getWeightingProfile();

   int[] getValidLeaves(int var1);

   void setWeighting(int var1, int var2) throws ValidationException;

   List<DataTypeRef> getAvailableDataTypes();

   void setDynamicDataType(DataTypeRef var1) throws ValidationException;
}
