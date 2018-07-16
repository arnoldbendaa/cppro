// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.datatype;

import com.cedar.cp.dto.datatype.DataTypeImpl;
import java.io.Serializable;

public class DataTypeEditorSessionCSO implements Serializable {

   private int mUserId;
   private DataTypeImpl mEditorData;


   public DataTypeEditorSessionCSO(int userId, DataTypeImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public DataTypeImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
