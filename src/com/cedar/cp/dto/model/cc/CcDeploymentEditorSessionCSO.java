// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.cc;

import com.cedar.cp.dto.model.cc.CcDeploymentImpl;
import java.io.Serializable;

public class CcDeploymentEditorSessionCSO implements Serializable {

   private int mUserId;
   private CcDeploymentImpl mEditorData;


   public CcDeploymentEditorSessionCSO(int userId, CcDeploymentImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public CcDeploymentImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
