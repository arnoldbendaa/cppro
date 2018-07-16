// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.virement.VirementQueryParams;
import com.cedar.cp.api.model.virement.VirementRequestEditorSession;
import java.util.Date;
import java.util.List;

public interface VirementRequestsProcess extends BusinessProcess {

   EntityList getAllVirementRequests();

   VirementRequestEditorSession getVirementRequestEditorSession(Object var1) throws ValidationException;

   int submitVirementRequest(Object var1) throws ValidationException;

   boolean haveVirementsWhichRequireAuthorisation() throws ValidationException;

   EntityList queryVirementRequests(boolean var1) throws ValidationException;

   List<DataTypeRef> queryDataTypes(Object var1) throws ValidationException;

   VirementQueryParams getQueryParams(Object var1) throws ValidationException;

   List<String> queryVirementRequests(int var1, int var2, Object var3, Object var4, Object var5, Object var6, List var7, Double var8, Double var9, Date var10, Date var11);

   String queryVirementRequest(int var1);
}
