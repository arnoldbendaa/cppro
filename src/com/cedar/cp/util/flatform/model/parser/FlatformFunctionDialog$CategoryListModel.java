// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.parser.FlatformFunctionDialog;
import com.cedar.cp.util.flatform.model.parser.xml.FlatformFunctionCategory;
import java.util.List;
import javax.swing.AbstractListModel;

class FlatformFunctionDialog$CategoryListModel extends AbstractListModel {

   private List<FlatformFunctionCategory> mCatList;
   // $FF: synthetic field
   final FlatformFunctionDialog this$0;


   public FlatformFunctionDialog$CategoryListModel(FlatformFunctionDialog var1, List catList) {
      this.this$0 = var1;
      this.mCatList = catList;
   }

   public Object getElementAt(int arg0) {
      return this.mCatList.get(arg0);
   }

   public int getSize() {
      return this.mCatList.size();
   }
}
