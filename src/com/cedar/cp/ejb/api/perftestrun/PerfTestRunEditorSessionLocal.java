// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.perftestrun;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.perftestrun.PerfTestRunEditorSessionCSO;
import com.cedar.cp.dto.perftestrun.PerfTestRunEditorSessionSSO;
import com.cedar.cp.dto.perftestrun.PerfTestRunPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface PerfTestRunEditorSessionLocal extends EJBLocalObject {

   PerfTestRunEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   PerfTestRunEditorSessionSSO getNewItemData(int var1) throws EJBException;

   PerfTestRunPK insert(PerfTestRunEditorSessionCSO var1) throws ValidationException, EJBException;

   PerfTestRunPK copy(PerfTestRunEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(PerfTestRunEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
