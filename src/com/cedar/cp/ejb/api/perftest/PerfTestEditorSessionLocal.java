// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.perftest;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.perftest.PerfTestEditorSessionCSO;
import com.cedar.cp.dto.perftest.PerfTestEditorSessionSSO;
import com.cedar.cp.dto.perftest.PerfTestPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface PerfTestEditorSessionLocal extends EJBLocalObject {

   PerfTestEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   PerfTestEditorSessionSSO getNewItemData(int var1) throws EJBException;

   PerfTestPK insert(PerfTestEditorSessionCSO var1) throws ValidationException, EJBException;

   PerfTestPK copy(PerfTestEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(PerfTestEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
