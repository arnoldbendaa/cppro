// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension;

import com.cedar.cp.api.dimension.DimensionElement;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.ModelRef;
import java.util.Collection;
import javax.swing.ListModel;

public interface Dimension {

   int ACCOUNT_TYPE = 1;
   int BUSINESS_TYPE = 2;
   int CALENDAR_TYPE = 3;


   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   int getType();

   Integer getExternalSystemRef();

   Collection getDimensionElements();

   DimensionElement getDimensionElement(Object var1);

   DimensionElement getDimensionElement(String var1);

   ListModel getListModel();

   DimensionRef getEntityRef();

   ModelRef getModelRef();

   boolean changeManagementRequestsPending();

   DimensionElement getNullElement();
}
