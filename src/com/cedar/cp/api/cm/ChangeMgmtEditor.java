// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.cm;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.cm.ChangeMgmt;
import com.cedar.cp.api.model.ModelRef;
import java.sql.Timestamp;

public interface ChangeMgmtEditor extends BusinessEditor {

   void setModelId(int var1) throws ValidationException;

   void setTaskId(int var1) throws ValidationException;

   void setCreatedTime(Timestamp var1) throws ValidationException;

   void setSourceSystem(String var1) throws ValidationException;

   void setXmlText(String var1) throws ValidationException;

   void setRelatedModelRef(ModelRef var1) throws ValidationException;

   ChangeMgmt getChangeMgmt();
}
