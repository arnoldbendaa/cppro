// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.ra;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ra.ResponsibilityAreaEditorSession;
import javax.swing.tree.TreeModel;

public interface ResponsibilityAreasProcess extends BusinessProcess {

   EntityList getAllResponsibilityAreas();

   ResponsibilityAreaEditorSession getResponsibilityAreaEditorSession(Object var1) throws ValidationException;

   TreeModel getResponsibilityAreaHierarchy(Object var1) throws ValidationException;

   TreeModel getResponsibilityAreaHierarchy(EntityRef var1) throws ValidationException;

   Object queryRARootKey(Object var1) throws ValidationException;
}
