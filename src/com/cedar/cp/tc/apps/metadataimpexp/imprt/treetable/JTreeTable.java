// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.JTreeTable$1;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.JTreeTable$TreeTableCellEditor;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.JTreeTable$TreeTableCellRenderer;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel.TreeTableModel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel.TreeTableModelAdapter;
import com.cedar.cp.util.awt.QThinTableHeaderRenderer;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class JTreeTable extends JTable {

   public static final String XML_FORM_NODE_TITLE = "XML Forms";
   public static final String LOOKUP_TABLES_NODE_TITLE = "Lookup Tables";
   protected JTreeTable$TreeTableCellRenderer tree = new JTreeTable$TreeTableCellRenderer(this);


   public void init(TreeTableModel treeTableModel) {
      this.tree.setModel(treeTableModel);
      super.setModel(new TreeTableModelAdapter(treeTableModel, this.tree));
      this.tree.setSelectionModel(new JTreeTable$1(this));
      this.tree.setRowHeight(this.getRowHeight());
      this.setDefaultRenderer(TreeTableModel.class, this.tree);
      this.setDefaultEditor(TreeTableModel.class, new JTreeTable$TreeTableCellEditor(this));
      this.setShowGrid(false);
      this.setIntercellSpacing(new Dimension(0, 0));
      TableColumn header1 = this.getTableHeader().getColumnModel().getColumn(0);
      header1.setPreferredWidth(200);
      this.getTableHeader().setDefaultRenderer(new QThinTableHeaderRenderer());
   }

   public int getEditingRow() {
      return this.getColumnClass(this.editingColumn) == TreeTableModel.class?-1:this.editingRow;
   }
}
