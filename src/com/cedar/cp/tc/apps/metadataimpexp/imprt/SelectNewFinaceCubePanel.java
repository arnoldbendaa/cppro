// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.JTreeTable;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.celleditor.FinanceCubeListCellRenderEditor;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel.SelectNewFinanceCubeTableModel;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonPanel;
import com.cedar.cp.tc.apps.metadataimpexp.util.TreeUtils;
//import com.cedar.cp.tc.ctrl.QControl;
import java.awt.BorderLayout;
import java.util.Collection;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class SelectNewFinaceCubePanel extends CommonPanel {

   private List<CommonImpExpItem> formsWithNewCube = null;
   private SelectNewFinanceCubeTableModel tableModel = null;
   private JTreeTable treeTable = new JTreeTable();


   public String getName() {
      return "Select new Finance Cube";
   }

   public SelectNewFinaceCubePanel() {
      this.setLayout(new BorderLayout());
      JScrollPane pane = new JScrollPane(this.treeTable);
      this.add(pane, "Center");
      this.treeTable.setDefaultRenderer(CommonImpExpItem.class, new FinanceCubeListCellRenderEditor());
      this.treeTable.setDefaultEditor(CommonImpExpItem.class, new FinanceCubeListCellRenderEditor());
      this.addQControl();
   }

   private void addQControl() {
	   //arnold all
//      QControl qTreeTableControl = new QControl(this.treeTable, (JLabel)null, new String[]{"impexp.selectnewfinancecube.treetable", null});
//      super.addQControl(qTreeTableControl);
   }

   public void init() {
      this.tableModel = new SelectNewFinanceCubeTableModel(TreeUtils.buildFormsHaveCubeTree(this.formsWithNewCube));
      this.treeTable.init(this.tableModel);
      JOptionPane.showMessageDialog(this, "Some Finance Cubes do not exist", "Select new Finance Cube", 1);
   }

   public void setData(Collection itemList) {
      this.formsWithNewCube = (List)itemList;
   }

   public void setData(CommonImpExpItem[] itemList) {}
}
