// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.parser.FlatformFunctionDialog;
import com.cedar.cp.util.flatform.model.parser.xml.FlatformFunctionDetail;
import java.util.List;
import javax.swing.AbstractListModel;

class FlatformFunctionDialog$FunctionListModel extends AbstractListModel {

   private List<FlatformFunctionDetail> mFuncList;
   // $FF: synthetic field
   final FlatformFunctionDialog this$0;


   public FlatformFunctionDialog$FunctionListModel(FlatformFunctionDialog var1, List funcList) {
      this.this$0 = var1;
      this.mFuncList = funcList;
   }

   public Object getElementAt(int arg0) {
      return this.mFuncList.get(arg0);
   }

   public int getSize() {
      return this.mFuncList.size();
   }
}
