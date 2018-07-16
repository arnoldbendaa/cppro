// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.ResourceBrowserDialog;
import javax.swing.AbstractListModel;
import javax.swing.table.TableModel;

class ResourceBrowserDialog$ResourceListModel extends AbstractListModel {

   private TableModel mResources;
   // $FF: synthetic field
   final ResourceBrowserDialog this$0;


   public ResourceBrowserDialog$ResourceListModel(ResourceBrowserDialog var1, TableModel resources) {
      this.this$0 = var1;
      this.mResources = resources;
   }

   public int getSize() {
      return this.mResources.getRowCount();
   }

   public Object getElementAt(int index) {
      return this.mResources.getValueAt(index, 1);
   }

   public int getId(int index) {
      return ((Integer)this.mResources.getValueAt(index, 0)).intValue();
   }
}
