// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.AbstractQTreeTriptychModel;
import com.cedar.awt.DefaultTriptychTableModel;
import com.cedar.awt.QTreeTriptychModel;
import com.cedar.awt.hierarchy.DefaultHierarchyModel;
import com.cedar.awt.hierarchy.HierarchyModel;
import java.util.Vector;
import javax.swing.table.TableModel;

public class DefaultQTreeTriptychModel extends AbstractQTreeTriptychModel implements QTreeTriptychModel {

   protected DefaultHierarchyModel mLeftTreeModel;
   protected DefaultTriptychTableModel mRightTableModel;


   public DefaultQTreeTriptychModel() {
      this.mLeftTreeModel = new DefaultHierarchyModel();
      this.mRightTableModel = new DefaultTriptychTableModel(new Object[][]{{"Selected One"}, {"Selected Two"}, {"Selected Three"}}, new String[]{"Selected"});
   }

   public DefaultQTreeTriptychModel(DefaultHierarchyModel leftModel, DefaultTriptychTableModel rightModel) {
      this.mLeftTreeModel = leftModel;
      this.mRightTableModel = rightModel;
   }

   public HierarchyModel getLeftTreeModel() {
      return this.mLeftTreeModel;
   }

   public TableModel getRightTableModel() {
      return this.mRightTableModel;
   }

   public void addRow(Object o) {
      Vector rowData = new Vector();
      rowData.add(o);
      this.mRightTableModel.addRow(rowData);
      this.fireItemStateChanged(1, rowData);
   }

   public void removeRow(int row) {
      Vector rowData = new Vector();

      for(int col = 0; col < this.mRightTableModel.getColumnCount(); ++col) {
         rowData.add(this.mRightTableModel.getValueAt(row, col));
      }

      this.mRightTableModel.removeRow(row);
      this.fireItemStateChanged(2, rowData);
   }

   public boolean isAddRowValid(int row) {
      return true;
   }

   public boolean isRemoveRowValid(int row) {
      return true;
   }

   public Object[] getSelectedObjects() {
      return null;
   }
}
