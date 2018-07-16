// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.udwp;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.udwp.WeightingProfileRef;
import java.util.Map;
import java.util.Set;

public interface WeightingDeployment {

   Object getPrimaryKey();

   int getProfileId();

   boolean isAnyAccount();

   boolean isAnyBusiness();

   boolean isAnyDataType();

   int getWeighting();

   WeightingProfileRef getWeightingProfileRef();

   ModelRef getModelRef();

   Map getAccountElements();

   Map getBusinessElements();

   Set getDataTypes();

   String getProfileDescription();
}
