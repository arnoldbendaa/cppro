// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.distribution;

import com.cedar.cp.dto.report.distribution.DistributionImpl;
import java.io.Serializable;

public class DistributionEditorSessionCSO implements Serializable {

   private int mUserId;
   private DistributionImpl mEditorData;


   public DistributionEditorSessionCSO(int userId, DistributionImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public DistributionImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
