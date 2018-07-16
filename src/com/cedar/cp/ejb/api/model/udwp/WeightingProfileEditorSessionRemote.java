// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model.udwp;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.udwp.Profile;
import com.cedar.cp.dto.model.udwp.WeightingProfileCK;
import com.cedar.cp.dto.model.udwp.WeightingProfileEditorSessionCSO;
import com.cedar.cp.dto.model.udwp.WeightingProfileEditorSessionSSO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface WeightingProfileEditorSessionRemote extends EJBObject {

   WeightingProfileEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   WeightingProfileEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   WeightingProfileCK insert(WeightingProfileEditorSessionCSO var1) throws ValidationException, RemoteException;

   EntityList getOwnershipData(int var1, Object var2) throws RemoteException;

   WeightingProfileCK copy(WeightingProfileEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(WeightingProfileEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   Object[][] queryWeightingInfo(int var1, ModelRef var2) throws ValidationException, RemoteException;

   EntityList queryProfiles(int var1, int var2, int var3, int var4, int var5, String var6) throws RemoteException;

   EntityList queryProfiles(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) throws RemoteException;

   int[] getWeightingProfile(int var1) throws RemoteException;

   Profile getWeightingProfileDetail(int var1) throws RemoteException;

   Profile getWeightingProfileDetail(int var1, int var2) throws RemoteException;

   EntityList queryDynamicWeightingFactors(int var1, int[] var2, int var3, String var4, int var5, int var6, int var7) throws RemoteException;

   Profile getProfileDetail(int var1, int var2, int[] var3, String var4) throws ValidationException, RemoteException;
}
