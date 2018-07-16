// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.extsys;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extsys.ExternalSystem;
import com.cedar.cp.api.extsys.RemoteFileSystemResource;
import java.util.List;

public interface ExternalSystemEditor extends BusinessEditor {

   void setSystemType(int var1) throws ValidationException;

   void setEnabled(boolean var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setLocation(String var1) throws ValidationException;

   void setConnectorClass(String var1) throws ValidationException;

   void setImportSource(String var1) throws ValidationException;

   void setExportTarget(String var1) throws ValidationException;

   ExternalSystem getExternalSystem();

   List<RemoteFileSystemResource> queryRemoteFileSystem() throws ValidationException;

   EntityList queryAllXactSubsystems(Object var1) throws ValidationException;

   List queryAllXactAvailableColumns(int var1) throws ValidationException;

   List queryXactColumnSelection(int var1) throws ValidationException;

   void updateXactColumnSelection(int var1, List var2, List var3) throws ValidationException;

   void setProperty(String var1, String var2);
}
