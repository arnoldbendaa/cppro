// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.user;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.user.DataEntryProfileEditorSession;

public interface DataEntryProfilesProcess extends BusinessProcess {

   EntityList getAllDataEntryProfiles();

   EntityList getAllDataEntryProfilesForUser(int var1, int var2, int budgetCycleId);

   EntityList getAllUsersForDataEntryProfilesForModel(int var1);

   EntityList getAllDataEntryProfilesForForm(int var1);

   EntityList getDefaultDataEntryProfile(int var1, int var2, int var3, int var4);

   DataEntryProfileEditorSession getDataEntryProfileEditorSession(Object var1) throws ValidationException;
}
