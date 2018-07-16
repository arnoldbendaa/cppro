// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementAuthPoint;
import com.cedar.cp.api.model.virement.VirementLine;
import com.cedar.cp.api.model.virement.VirementRequestRef;
import com.cedar.cp.api.user.UserRef;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface VirementRequest extends Serializable {

   int NOT_SUBMITTED = 0;
   int NOT_AUTHORISED = 1;
   int AUTHORISED = 2;
   int PROCESSED = 3;


   Object getPrimaryKey();

   int getFinanceCubeId();

   int getBudgetCycleId();

   int getRequestStatus();

   int getUserId();

   String getReason();

   String getReference();

   Timestamp getDateSubmitted();

   int getBudgetActivityId();

   ModelRef getModelRef();

   UserRef getOwningUserRef();

   List getVirementGroups();

   Map getVirementAuthPoints();

   int getModelId();

   BudgetCycleRef getBudgetCycleRef();

   FinanceCubeRef getFinanceCubeRef();

   int getRequestId();

   VirementRequestRef getRequestRef();

   VirementAuthPoint queryAuthPointForLine(VirementLine var1);
}
