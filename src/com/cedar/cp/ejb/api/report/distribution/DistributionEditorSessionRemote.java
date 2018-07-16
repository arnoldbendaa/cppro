// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.distribution;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.distribution.DistributionDetails;
import com.cedar.cp.dto.report.distribution.DistributionEditorSessionCSO;
import com.cedar.cp.dto.report.distribution.DistributionEditorSessionSSO;
import com.cedar.cp.dto.report.distribution.DistributionPK;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface DistributionEditorSessionRemote extends EJBObject {

   DistributionEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   DistributionEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   DistributionPK insert(DistributionEditorSessionCSO var1) throws ValidationException, RemoteException;

   DistributionPK copy(DistributionEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(DistributionEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   DistributionDetails getDistributionDetailList(int var1, int var2, EntityRef var3) throws ValidationException, RemoteException;
}
