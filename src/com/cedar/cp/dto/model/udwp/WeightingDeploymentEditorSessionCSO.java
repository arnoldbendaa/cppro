// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.udwp;

import com.cedar.cp.dto.model.udwp.WeightingDeploymentImpl;
import java.io.Serializable;

public class WeightingDeploymentEditorSessionCSO implements Serializable {

   private int mUserId;
   private WeightingDeploymentImpl mEditorData;


   public WeightingDeploymentEditorSessionCSO(int userId, WeightingDeploymentImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public WeightingDeploymentImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
