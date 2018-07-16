// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.udwp;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.udwp.Profile;
import com.cedar.cp.api.model.udwp.WeightingProfileEditorSession;
import com.cedar.cp.api.model.udwp.WeightingProfileRef;

public interface WeightingProfilesProcess extends BusinessProcess {

   EntityList getAllWeightingProfiles();
   
   EntityList getAllWeightingProfilesForLoggedUser();

   WeightingProfileEditorSession getWeightingProfileEditorSession(Object var1) throws ValidationException;

   EntityList queryProfiles(Object var1, int var2, int var3, Object var4, Object var5, String var6) throws ValidationException;

   EntityList queryProfiles(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, Object var8) throws ValidationException;

   Profile getStaticWeightingProfileDetail(Object var1, Object var2);

   EntityList queryDynamicWeightingFactors(Object var1, Object[] var2, int var3, String var4, int var5, int var6, Object var7);

   Profile getProfileDetail(Object var1, Object var2, Object[] var3, String var4) throws ValidationException;

   WeightingProfileRef getCustomWeightingProfile();
}
