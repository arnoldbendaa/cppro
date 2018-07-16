// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.destination.external;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationEditorSessionCSO;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationEditorSessionSSO;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface ExternalDestinationEditorSessionLocal extends EJBLocalObject {

   ExternalDestinationEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   ExternalDestinationEditorSessionSSO getNewItemData(int var1) throws EJBException;

   ExternalDestinationPK insert(ExternalDestinationEditorSessionCSO var1) throws ValidationException, EJBException;

   ExternalDestinationPK copy(ExternalDestinationEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(ExternalDestinationEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
