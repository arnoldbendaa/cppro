// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.model.FinanceCubeImpl;
import java.io.Serializable;

public class FinanceCubeEditorSessionCSO implements Serializable {

   private int mUserId;
   private FinanceCubeImpl mEditorData;


   public FinanceCubeEditorSessionCSO(int userId, FinanceCubeImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public FinanceCubeImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
