// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.admin.tidytask;

import com.cedar.cp.api.admin.tidytask.TidyTask;
import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;

public interface TidyTaskEditor extends BusinessEditor {

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   TidyTask getTidyTask();
}
