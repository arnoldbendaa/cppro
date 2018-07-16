// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.PreImportListPanel$SummaryJTable;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel.SummaryTableModel;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonPanel;
//import com.cedar.cp.tc.ctrl.QControl;
import com.cedar.cp.util.awt.QThinTableHeaderRenderer;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

public class PreImportListPanel extends CommonPanel {

   private JLabel lookupTableLabel = new JLabel("Selected Lookup Tables:");
   private JLabel xmlFormLabel = new JLabel("Selected XML Forms:");
   private List<CommonImpExpItem> selectedItems = null;
   private JTable lookupTable = null;
   private JTable formTable = null;
   private JSplitPane splitPane = new JSplitPane(0);
   private SummaryTableModel xmlTableModel = null;
   private SummaryTableModel lookupTableModel = null;


   public String getName() {
      return "Import Items Summary";
   }

   public void init() {
      this.setLayout(new BorderLayout());
      JPanel lookupTablePanel = new JPanel(new GridBagLayout());
      GridBagConstraints constraintLookupLabel = new GridBagConstraints();
      constraintLookupLabel.insets = new Insets(5, 5, 5, 5);
      constraintLookupLabel.gridwidth = 0;
      constraintLookupLabel.anchor = 17;
      lookupTablePanel.add(this.lookupTableLabel, constraintLookupLabel);
      this.lookupTableModel = new SummaryTableModel(this.selectedItems, 1);
      this.lookupTable = new PreImportListPanel$SummaryJTable(this, this.lookupTableModel);
      this.lookupTable.getTableHeader().setDefaultRenderer(new QThinTableHeaderRenderer());
      GridBagConstraints constraintsLookupTable = new GridBagConstraints();
      constraintsLookupTable.insets = new Insets(5, 5, 5, 5);
      constraintsLookupTable.gridwidth = 0;
      constraintsLookupTable.anchor = 10;
      constraintsLookupTable.fill = 1;
      constraintsLookupTable.weightx = 1.0D;
      constraintsLookupTable.weighty = 1.0D;
      lookupTablePanel.add(new JScrollPane(this.lookupTable), constraintsLookupTable);
      this.splitPane.setTopComponent(lookupTablePanel);
      JPanel formsTablePanel = new JPanel(new GridBagLayout());
      GridBagConstraints constraintXMLLabel = new GridBagConstraints();
      constraintXMLLabel.insets = new Insets(5, 5, 5, 5);
      constraintXMLLabel.gridwidth = 0;
      constraintXMLLabel.anchor = 17;
      formsTablePanel.add(this.xmlFormLabel, constraintXMLLabel);
      this.xmlTableModel = new SummaryTableModel(this.selectedItems, 2);
      this.formTable = new PreImportListPanel$SummaryJTable(this, this.xmlTableModel);
      this.formTable.getTableHeader().setDefaultRenderer(new QThinTableHeaderRenderer());
      GridBagConstraints constraintFormTable = new GridBagConstraints();
      constraintFormTable.insets = new Insets(5, 5, 5, 5);
      constraintFormTable.gridwidth = 0;
      constraintFormTable.anchor = 10;
      constraintFormTable.fill = 1;
      constraintFormTable.weightx = 1.0D;
      constraintFormTable.weighty = 1.0D;
      formsTablePanel.add(new JScrollPane(this.formTable), constraintFormTable);
      this.splitPane.setBottomComponent(formsTablePanel);
      this.splitPane.setDividerLocation(160);
      this.add(this.splitPane, "Center");
      this.addQControl();
   }

   private void addQControl() {
	   //arnold all
//      QControl qLookupTableControl = new QControl(this.lookupTable, this.lookupTableLabel, new String[]{"impexp.selectnewfinancecube.lookuptable", null});
//      super.addQControl(qLookupTableControl);
//      QControl qFormTableControl = new QControl(this.formTable, this.lookupTableLabel, new String[]{"impexp.selectnewfinancecube.formtable", null});
//      super.addQControl(qFormTableControl);
   }

   public void setData(Collection itemList) {
      this.selectedItems = (List)itemList;
   }

   public void displayImportResult() {
      this.init();
   }

   public void setData(CommonImpExpItem[] itemList) {}
}
