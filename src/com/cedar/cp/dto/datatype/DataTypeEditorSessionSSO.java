// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.datatype;

import com.cedar.cp.dto.datatype.DataTypeImpl;
import java.io.Serializable;

public class DataTypeEditorSessionSSO implements Serializable {

   private DataTypeImpl mEditorData;


   public DataTypeEditorSessionSSO() {}

   public DataTypeEditorSessionSSO(DataTypeImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(DataTypeImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public DataTypeImpl getEditorData() {
      return this.mEditorData;
   }
}
