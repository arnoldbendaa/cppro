// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.distribution;

import com.cedar.cp.dto.report.distribution.DistributionImpl;
import java.io.Serializable;

public class DistributionEditorSessionSSO implements Serializable {

   private DistributionImpl mEditorData;


   public DistributionEditorSessionSSO() {}

   public DistributionEditorSessionSSO(DistributionImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(DistributionImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public DistributionImpl getEditorData() {
      return this.mEditorData;
   }
}
