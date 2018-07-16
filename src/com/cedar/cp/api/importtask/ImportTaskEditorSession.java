package com.cedar.cp.api.importtask;

import com.cedar.cp.api.importtask.ImportTaskEditor;
import com.cedar.cp.api.base.BusinessSession;

public interface ImportTaskEditorSession extends BusinessSession {

   ImportTaskEditor getImportTaskEditor();
}
