// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.model.CellCalcImpl;
import java.io.Serializable;

public class CellCalcEditorSessionCSO implements Serializable {

   private int mUserId;
   private CellCalcImpl mEditorData;


   public CellCalcEditorSessionCSO(int userId, CellCalcImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public CellCalcImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
