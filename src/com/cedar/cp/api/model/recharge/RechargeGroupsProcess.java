// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.recharge;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.recharge.RechargeGroupEditorSession;
import javax.swing.tree.TreeModel;

public interface RechargeGroupsProcess extends BusinessProcess {

   EntityList getAllRechargeGroups();

   RechargeGroupEditorSession getRechargeGroupEditorSession(Object var1) throws ValidationException;

   EntityList getAvailableFinanceCubes(Object var1) throws ValidationException;

   TreeModel[] getCalModel(Object var1) throws ValidationException;

   int[] getRechargeIds(Object var1) throws ValidationException;
}
