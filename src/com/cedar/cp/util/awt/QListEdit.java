// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.DefaultListEditModel;
import com.cedar.cp.util.awt.ListEditModel;
import com.cedar.cp.util.awt.QListEdit$1;
import com.cedar.cp.util.awt.QListEdit$2;
import com.cedar.cp.util.awt.QThinTableHeaderRenderer;
import com.cedar.cp.util.awt.TableSorter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class QListEdit extends JPanel {

   public BorderLayout mBorderLayout;
   public JScrollPane mScrollPane;
   public JPanel mPanel;
   public JTable mTable;
   public JButton mAdd;
   public JButton mEdit;
   public JButton mDelete;
   public FlowLayout mFlowLayout;
   public JLabel mErrorLabel;
   public boolean mEnableKeySearch;
   public boolean mEnableTableSort;
   private TableSorter mSorter;
   private ListEditModel mModel;


   public QListEdit() {
      this(new DefaultListEditModel());
   }

   public QListEdit(boolean enableKeySearch, boolean enableTableSort) {
      this.mBorderLayout = new BorderLayout();
      this.mScrollPane = new JScrollPane();
      this.mPanel = new JPanel();
      this.mTable = new JTable();
      this.mAdd = new JButton();
      this.mEdit = new JButton();
      this.mDelete = new JButton();
      this.mFlowLayout = new FlowLayout();
      this.mErrorLabel = new JLabel();
      this.mEnableKeySearch = true;
      this.mEnableTableSort = true;
      this.mEnableKeySearch = enableKeySearch;
      this.mEnableTableSort = enableTableSort;

      try {
         this.buildGUI();
         this.setModel(new DefaultListEditModel());
         this.addListeners();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public QListEdit(ListEditModel model) {
      this.mBorderLayout = new BorderLayout();
      this.mScrollPane = new JScrollPane();
      this.mPanel = new JPanel();
      this.mTable = new JTable();
      this.mAdd = new JButton();
      this.mEdit = new JButton();
      this.mDelete = new JButton();
      this.mFlowLayout = new FlowLayout();
      this.mErrorLabel = new JLabel();
      this.mEnableKeySearch = true;
      this.mEnableTableSort = true;

      try {
         this.buildGUI();
         this.setModel(model);
         this.addListeners();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   protected void buildGUI() throws Exception {
      this.mBorderLayout.setHgap(5);
      this.mBorderLayout.setVgap(5);
      this.setLayout(this.mBorderLayout);
      this.mAdd.setMnemonic('A');
      this.mAdd.setText("Add...");
      this.mEdit.setMnemonic('E');
      this.mEdit.setText("Edit...");
      this.mDelete.setMnemonic('R');
      this.mDelete.setText("Remove");
      this.mPanel.setLayout(this.mFlowLayout);
      this.mFlowLayout.setAlignment(2);
      this.add(this.mScrollPane, "Center");
      this.mScrollPane.getViewport().add(this.getTable(), (Object)null);
      this.add(this.mPanel, "South");
      this.mPanel.add(this.mErrorLabel, (Object)null);
      this.mPanel.add(this.mAdd, (Object)null);
      this.mPanel.add(this.mEdit, (Object)null);
      this.mPanel.add(this.mDelete, (Object)null);
      this.mScrollPane.setPreferredSize(new Dimension(300, 50));
   }

   protected void addListeners() {
      this.getTable().getSelectionModel().addListSelectionListener(new QListEdit$1(this));
      this.getTable().addMouseListener(new QListEdit$2(this));
   }

   public JButton getAddButton() {
      return this.mAdd;
   }

   public JButton getEditButton() {
      return this.mEdit;
   }

   public JButton getDeleteButton() {
      return this.mDelete;
   }

   public JLabel getErrorLabel() {
      return this.mErrorLabel;
   }

   public JTable getTable() {
      return this.mTable;
   }

   public int getSelectedRow() {
      int row = this.getTable().getSelectedRow();
      return this.mEnableTableSort?(row == -1?-1:this.mSorter.getOriginalRow(row)):row;
   }

   public int[] getSelectedRows() {
      int[] rows = this.getTable().getSelectedRows();
      if(!this.mEnableTableSort) {
         return rows;
      } else {
         int[] result = new int[rows.length];

         for(int i = 0; i < rows.length; ++i) {
            result[i] = this.mSorter.getOriginalRow(rows[i]);
         }

         return result;
      }
   }

   public void updateUIState() {
      this.mAdd.setEnabled(this.mModel.isAddAllowed());
      int selectedRow = this.getTable().getSelectedRow();
      this.mEdit.setEnabled(selectedRow != -1 && this.mModel.isEditable(selectedRow) && this.getSelectedRows().length == 1);
      this.mDelete.setEnabled(selectedRow != -1 && this.mModel.isDeletable(selectedRow));
   }

   public void setModel(ListEditModel model) {
      ListEditModel oldModel = this.mModel;
      this.mModel = model;
      if(this.mEnableTableSort) {
         if(this.mSorter == null) {
            this.mSorter = new TableSorter(model.getTableModel());
            this.getTable().setModel(this.mSorter);
            this.mSorter.addMouseListenerToHeaderInTable(this.getTable(), this.mEnableKeySearch);
            this.getTable().getTableHeader().setDefaultRenderer(new QThinTableHeaderRenderer());
         } else {
            this.mSorter.setModel(model.getTableModel());
         }
      } else {
         this.getTable().setModel(model.getTableModel());
      }

      this.firePropertyChange("model", oldModel, model);
      this.updateUIState();
      this.invalidate();
   }

   public ListEditModel getModel() {
      return this.mModel;
   }

   public void fireTableDataChanged() {
      AbstractTableModel model;
      if(this.mSorter != null) {
         model = (AbstractTableModel)this.mSorter.getModel();
         model.fireTableDataChanged();
      } else {
         model = (AbstractTableModel)this.mModel.getTableModel();
         model.fireTableDataChanged();
      }

   }

   public TableSorter getSorter() {
      return this.mSorter;
   }
}
