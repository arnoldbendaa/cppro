// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.recharge;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.recharge.RechargeGroup;
import java.util.List;

public interface RechargeGroupEditor extends BusinessEditor {

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   RechargeGroup getRechargeGroup();

   void addSelectedRecharge(List var1) throws ValidationException;

   void removeSelectedRecharge(int var1) throws ValidationException;

   void setModelId(int var1);

   void setModelId(EntityRef var1);

   boolean isSameModel(EntityRef var1);
}
