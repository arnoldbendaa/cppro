// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.virement;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.model.virement.VirementQueryParams;
import com.cedar.cp.dto.model.virement.VirementRequestCK;
import com.cedar.cp.dto.model.virement.VirementRequestEditorSessionCSO;
import com.cedar.cp.dto.model.virement.VirementRequestEditorSessionSSO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface VirementRequestEditorSessionLocal extends EJBLocalObject {

   VirementRequestEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   VirementRequestEditorSessionSSO getNewItemData(int var1) throws EJBException;

   VirementRequestCK insert(VirementRequestEditorSessionCSO var1) throws ValidationException, EJBException;

   EntityList getOwnershipData(int var1, Object var2) throws EJBException;

   VirementRequestCK copy(VirementRequestEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(VirementRequestEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   int saveAndSubmit(VirementRequestEditorSessionCSO var1) throws ValidationException, EJBException;

   int submitVirementRequest(int var1, Object var2) throws ValidationException, EJBException;

   boolean haveVirementsWhichRequireAuthorisation(int var1) throws EJBException;

   EntityList queryVirementRequests(int var1, boolean var2) throws EJBException;

   List<DataTypeRef> queryTransferDataTypes(int var1, int var2) throws ValidationException, EJBException;

   VirementQueryParams getQueryParams(int var1, int var2) throws EJBException;

   List<String> queryVirementRequests(int var1, int var2, int var3, Integer var4, Integer var5, Integer var6, Integer var7, List<StructureElementKey> var8, Double var9, Double var10, Date var11, Date var12) throws EJBException;

   String queryVirementRequest(int var1, int var2) throws EJBException;
}
