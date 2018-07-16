// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.udwp;

import com.cedar.cp.dto.model.udwp.WeightingProfileImpl;
import java.io.Serializable;

public class WeightingProfileEditorSessionSSO implements Serializable {

   private WeightingProfileImpl mEditorData;


   public WeightingProfileEditorSessionSSO() {}

   public WeightingProfileEditorSessionSSO(WeightingProfileImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(WeightingProfileImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public WeightingProfileImpl getEditorData() {
      return this.mEditorData;
   }
}
