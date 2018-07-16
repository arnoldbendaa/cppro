// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.cc;

import com.cedar.cp.dto.model.cc.CcDeploymentImpl;
import java.io.Serializable;

public class CcDeploymentEditorSessionSSO implements Serializable {

   private CcDeploymentImpl mEditorData;


   public CcDeploymentEditorSessionSSO() {}

   public CcDeploymentEditorSessionSSO(CcDeploymentImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(CcDeploymentImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public CcDeploymentImpl getEditorData() {
      return this.mEditorData;
   }
}
