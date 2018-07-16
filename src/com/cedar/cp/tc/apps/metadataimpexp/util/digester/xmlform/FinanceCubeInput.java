// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.CommonInput;

public class FinanceCubeInput extends CommonInput {

   private String mCubeVisId;
   private int mCubeId;


   public String getCubeVisId() {
      return this.mCubeVisId;
   }

   public void setCubeVisId(String id) {
      this.mCubeVisId = id;
   }

   public void setCubeId(int id) {
      this.mCubeId = id;
   }

   public int getCubeId() {
      return this.mCubeId;
   }
}
