// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.cm;

import com.cedar.cp.api.model.ModelRef;
import java.sql.Timestamp;

public interface ChangeMgmt {

   Object getPrimaryKey();

   int getModelId();

   Timestamp getCreatedTime();

   int getTaskId();

   String getSourceSystem();

   String getXmlText();

   ModelRef getRelatedModelRef();
}
