// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.JTreeTable;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.celleditor.RadioCellRendererEditor;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel.DuplicatedItemsTableModel;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonPanel;
import com.cedar.cp.tc.apps.metadataimpexp.util.TreeUtils;
//import com.cedar.cp.tc.ctrl.QControl;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.tree.TreeNode;

public class ImportDuplicatedItemPanel extends CommonPanel {

   private Collection<CommonImpExpItem> duplicatedItemList = null;
   private DuplicatedItemsTableModel tableModel = null;
   private JTreeTable treeTable = new JTreeTable();
   private JLabel duplicatedItemsLabel = new JLabel("Duplicate item(s) found:");


   public ImportDuplicatedItemPanel() {
      this.setLayout(new GridBagLayout());
      this.treeTable.setDefaultRenderer(CommonImpExpItem.class, new RadioCellRendererEditor());
      this.treeTable.setDefaultEditor(CommonImpExpItem.class, new RadioCellRendererEditor());
      JScrollPane scrollPane = new JScrollPane(this.treeTable);
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.insets = new Insets(5, 5, 5, 5);
      gridBagConstraints.gridwidth = 0;
      gridBagConstraints.anchor = 17;
      this.add(this.duplicatedItemsLabel, gridBagConstraints);
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
      gridBagConstraints2.gridwidth = 0;
      gridBagConstraints2.anchor = 10;
      gridBagConstraints2.fill = 1;
      gridBagConstraints2.weightx = 1.0D;
      gridBagConstraints2.weighty = 1.0D;
      this.add(scrollPane, gridBagConstraints2);
      this.addQControl();
   }

   private void addQControl() {
//      QControl qTreeTableControl = new QControl(this.treeTable, this.duplicatedItemsLabel, new String[]{"impexp.duplicateditem.treetable", null});//arnold
//      super.addQControl(qTreeTableControl);//arnold
   }

   public void init() {
      TreeNode treeNode = TreeUtils.buildDuplicatedTreeItem(this.duplicatedItemList);
      this.tableModel = new DuplicatedItemsTableModel(treeNode);
      this.treeTable.init(this.tableModel);
      JOptionPane.showMessageDialog(this, "Some of the imported tables/forms already exist.", "Existing Items found", 1);
   }

   public void setData(CommonImpExpItem[] listItemType) {}

   public String getName() {
      return "duplicatedItemPanel";
   }

   public void setData(Collection itemList) {
      this.duplicatedItemList = itemList;
   }
}
