// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.AbstractQTriptychModel;
import com.cedar.awt.DefaultTriptychTableModel;
import java.util.Vector;
import javax.swing.table.TableModel;

public class DefaultQTriptychModel extends AbstractQTriptychModel {

   protected DefaultTriptychTableModel mLeftTableModel;
   protected DefaultTriptychTableModel mRightTableModel;


   public DefaultQTriptychModel() {
      this.mLeftTableModel = new DefaultTriptychTableModel(new Object[][]{{"Available One"}, {"Available Two"}, {"Available Three"}}, new String[]{"Available"});
      this.mRightTableModel = new DefaultTriptychTableModel(new Object[][]{{"Selected One"}, {"Selected Two"}, {"Selected Three"}}, new String[]{"Selected"});
   }

   public DefaultQTriptychModel(DefaultTriptychTableModel leftModel, DefaultTriptychTableModel rightModel) {
      this.mLeftTableModel = leftModel;
      this.mRightTableModel = rightModel;
   }

   public TableModel getLeftTableModel() {
      return this.mLeftTableModel;
   }

   public TableModel getRightTableModel() {
      return this.mRightTableModel;
   }

   public void addRow(int row) {
      Vector rowData = new Vector();

      for(int col = 0; col < this.mLeftTableModel.getColumnCount(); ++col) {
         rowData.add(this.mLeftTableModel.getValueAt(row, col));
      }

      this.mRightTableModel.addRow(rowData);
      this.mLeftTableModel.removeRow(row);
      this.fireItemStateChanged(1, rowData);
   }

   public void removeRow(int row) {
      Vector rowData = new Vector();

      for(int col = 0; col < this.mRightTableModel.getColumnCount(); ++col) {
         rowData.add(this.mRightTableModel.getValueAt(row, col));
      }

      this.mLeftTableModel.addRow(rowData);
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
