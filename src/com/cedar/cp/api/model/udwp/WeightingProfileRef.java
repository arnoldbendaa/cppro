// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.udwp;

import com.cedar.cp.api.base.EntityRef;

public interface WeightingProfileRef extends EntityRef {

   int WP_STATIC = 0;
   int WP_DYNAMIC = 1;
   int WP_FORCED = 2;
   int WP_REPEAT = 3;


   boolean isCustom();
}
