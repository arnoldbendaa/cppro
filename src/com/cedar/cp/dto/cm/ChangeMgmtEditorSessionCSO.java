// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.cm;

import com.cedar.cp.dto.cm.ChangeMgmtImpl;
import java.io.Serializable;

public class ChangeMgmtEditorSessionCSO implements Serializable {

   private int mUserId;
   private ChangeMgmtImpl mEditorData;


   public ChangeMgmtEditorSessionCSO(int userId, ChangeMgmtImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public ChangeMgmtImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
