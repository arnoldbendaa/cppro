// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.udwp;

import com.cedar.cp.dto.model.udwp.WeightingProfileImpl;
import java.io.Serializable;

public class WeightingProfileEditorSessionCSO implements Serializable {

   private int mUserId;
   private WeightingProfileImpl mEditorData;


   public WeightingProfileEditorSessionCSO(int userId, WeightingProfileImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public WeightingProfileImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
