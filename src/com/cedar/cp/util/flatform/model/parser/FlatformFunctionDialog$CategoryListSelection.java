// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.model.parser.FlatformFunctionDialog;
import com.cedar.cp.util.flatform.model.parser.FlatformFunctionDialog$1;
import com.cedar.cp.util.flatform.model.parser.FlatformFunctionDialog$FunctionListModel;
import com.cedar.cp.util.flatform.model.parser.xml.FlatformFunctionCategory;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class FlatformFunctionDialog$CategoryListSelection implements ListSelectionListener {

   // $FF: synthetic field
   final FlatformFunctionDialog this$0;


   private FlatformFunctionDialog$CategoryListSelection(FlatformFunctionDialog var1) {
      this.this$0 = var1;
   }

   public void valueChanged(ListSelectionEvent arg0) {
      this.this$0.mCurrentCategory = (FlatformFunctionCategory)((JList)arg0.getSource()).getSelectedValue();
      Object funcList = new ArrayList();
      if(this.this$0.mCurrentCategory.getFunctionList() != null) {
         funcList = this.this$0.mCurrentCategory.getFunctionList();
      }

      FlatformFunctionDialog$FunctionListModel funcListModel = new FlatformFunctionDialog$FunctionListModel(this.this$0, (List)funcList);
      this.this$0.mFunctionList.setModel(funcListModel);
      if(((List)funcList).size() != 0) {
         this.this$0.mFunctionList.setSelectedIndex(0);
      }

   }

   // $FF: synthetic method
   FlatformFunctionDialog$CategoryListSelection(FlatformFunctionDialog x0, FlatformFunctionDialog$1 x1) {
      this(x0);
   }
}
