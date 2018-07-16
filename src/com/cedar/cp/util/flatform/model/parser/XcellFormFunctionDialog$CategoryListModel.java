// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.parser.FlatformFunctionDialog;
import com.cedar.cp.util.flatform.model.parser.xml.FlatformFunctionCategory;
import java.util.List;
import javax.swing.AbstractListModel;

class XcellFormFunctionDialog$CategoryListModel extends AbstractListModel {

   private List<FlatformFunctionCategory> mCatList;
   // $FF: synthetic field
   final XcellFormFunctionDialog this$0;


   public XcellFormFunctionDialog$CategoryListModel(XcellFormFunctionDialog xcellFormFunctionDialog, List catList) {
      this.this$0 = xcellFormFunctionDialog;
      this.mCatList = catList;
   }

   public Object getElementAt(int arg0) {
      return this.mCatList.get(arg0);
   }

   public int getSize() {
      return this.mCatList.size();
   }
}
