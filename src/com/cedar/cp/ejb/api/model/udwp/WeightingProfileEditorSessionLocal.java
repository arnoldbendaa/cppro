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
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface WeightingProfileEditorSessionLocal extends EJBLocalObject {

   WeightingProfileEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   WeightingProfileEditorSessionSSO getNewItemData(int var1) throws EJBException;

   WeightingProfileCK insert(WeightingProfileEditorSessionCSO var1) throws ValidationException, EJBException;

   EntityList getOwnershipData(int var1, Object var2) throws EJBException;

   WeightingProfileCK copy(WeightingProfileEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(WeightingProfileEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   Object[][] queryWeightingInfo(int var1, ModelRef var2) throws ValidationException, EJBException;

   EntityList queryProfiles(int var1, int var2, int var3, int var4, int var5, String var6) throws EJBException;

   EntityList queryProfiles(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) throws EJBException;

   int[] getWeightingProfile(int var1) throws EJBException;

   Profile getWeightingProfileDetail(int var1) throws EJBException;

   Profile getWeightingProfileDetail(int var1, int var2) throws EJBException;

   EntityList queryDynamicWeightingFactors(int var1, int[] var2, int var3, String var4, int var5, int var6, int var7) throws EJBException;

   Profile getProfileDetail(int var1, int var2, int[] var3, String var4) throws ValidationException, EJBException;
}
