// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.CommonElement;

public abstract class CommonInput extends CommonElement {

   protected String mId;


   public String getId() {
      return this.mId;
   }

   public void setId(String id) {
      this.mId = id;
   }
}
