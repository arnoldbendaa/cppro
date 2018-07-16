package com.cedar.cp.api.importtask;

import com.cedar.cp.api.importtask.ImportTask;
import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;

public interface ImportTaskEditor extends BusinessEditor {

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   ImportTask getImportTask();
}
