// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dimension;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionEditorSession;
import com.cedar.cp.api.dimension.DimensionImportFormat;
import com.cedar.cp.api.dimension.DimensionRef;

public interface DimensionsProcess extends BusinessProcess {

   EntityList getAllDimensions();
   
   EntityList getDimensionsForLoggedUser();

   EntityList getAvailableDimensions();

   EntityList getImportableDimensions();

   EntityList getAllDimensionsForModel(int var1);

   DimensionEditorSession getDimensionEditorSession(Object var1) throws ValidationException;

   DimensionImportFormat getDimensionImportFormat() throws CPException;

   int getDimensionId(DimensionRef var1);
}
