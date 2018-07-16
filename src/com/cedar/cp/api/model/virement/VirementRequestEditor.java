// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementGroup;
import com.cedar.cp.api.model.virement.VirementGroupEditor;
import com.cedar.cp.api.model.virement.VirementRequest;
import com.cedar.cp.api.user.UserRef;
import java.sql.Timestamp;
import java.util.List;

public interface VirementRequestEditor extends BusinessEditor {

   void setFinanceCubeId(int var1) throws ValidationException;

   void setBudgetCycleId(int var1) throws ValidationException;

   void setRequestStatus(int var1) throws ValidationException;

   void setUserId(int var1) throws ValidationException;

   void setBudgetActivityId(int var1) throws ValidationException;

   void setReason(String var1) throws ValidationException;

   void setReference(String var1) throws ValidationException;

   void setDateSubmitted(Timestamp var1) throws ValidationException;

   void setModelRef(ModelRef var1) throws ValidationException;

   void setOwningUserRef(UserRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   VirementRequest getVirementRequest();

   void setFinanceCubeRef(FinanceCubeRef var1) throws ValidationException;

   void setBudgetCycleRef(BudgetCycleRef var1) throws ValidationException;

   void setModelRef(int var1, String var2) throws ValidationException;

   void setFinanceCubeRef(int var1, String var2) throws ValidationException;

   void setBudgetCycleRef(int var1, String var2) throws ValidationException;

   void setOwningUserRef(int var1, String var2) throws ValidationException;

   VirementGroupEditor getVirementRequestGroupEditor(Object var1) throws ValidationException;

   void remove(VirementGroup var1) throws ValidationException;

   Object decodeGroupKey(String var1);

   boolean authorise(Object var1, String var2) throws ValidationException;

   void reject(Object var1, String var2) throws ValidationException;

   List<DataTypeRef> getTransferDataTypes() throws ValidationException;
}
