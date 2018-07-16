// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.datatype.DataTypeRef;
import java.io.Serializable;
import java.util.List;

public interface VirementLine extends Serializable {

   int SCALE_FACTOR = 10000;


   String getKeyAsText();

   Object getKey();

   List getAddress();

   boolean isFrom();

   boolean isTo();

   double getTransferValue();

   Double getRepeatValue();

   DataTypeRef getDataTypeRef();

   boolean isSummaryLine();

   List getSpreadProfile();

   Object getSpreadProfileKey();

   String getSpreadProfileKeyAsText();

   String getSpreadProfileVisId();

   double getAllocationThreshold();
}
