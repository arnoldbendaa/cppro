// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.FinanceCube;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.ImportSelectDimAndHierPanel$DimAndHierTableModel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.XMLFormModel;
import com.cedar.cp.tc.apps.metadataimpexp.services.FinanceCubeService;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.util.DefaultValueMapping;
import com.cedar.cp.util.xmlform.FinanceCubeInput;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.StructureColumnValue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.tree.TreeNode;

public class ImportSelectDimAndHierPanel extends JPanel implements ActionListener {

   private JLabel cubeStructureLabel = new JLabel("Structure:");
   private JLabel cubeVisIdLabel = new JLabel("Identifier:");
   private JLabel cubeDescriptionLabel = new JLabel("Desciption:");
   private JLabel cubeVisIdText = new JLabel();
   private JLabel cubeDesciptionText = new JLabel();
   private JTable jTable = new JTable();
   private ImportSelectDimAndHierPanel$DimAndHierTableModel tableModel = null;
   private Hashtable<String, List<String>> dimHashtable = null;
   private Hashtable<String, DefaultCellEditor> editorHashtable = null;
   private List<String> dimVisList = new ArrayList();
   private JButton saveButton = new JButton("Save");
   private JButton cancelButton = new JButton("Cancel");
   private CommonImpExpItem editObj = null;
   private JDialog owner = null;
   private int numOfDim = 0;
   private FinanceCubeService cubeService = new FinanceCubeService();


   public ImportSelectDimAndHierPanel(String cubeVisId, int numOfDim, Hashtable<String, List<String>> dimHash, CommonImpExpItem editObj, JDialog owner) {
      FinanceCube cube = null;

      try {
         cube = this.cubeService.getFinanceCubeByVisId(cubeVisId);
      } catch (ValidationException var16) {
         var16.printStackTrace();
      }

      this.numOfDim = numOfDim;
      this.owner = owner;
      this.editObj = editObj;
      this.dimHashtable = dimHash;
      this.initDimVisList();
      this.editorHashtable = this.buildHierCellEditor(this.dimVisList);
      this.setLayout(new GridBagLayout());
      JScrollPane scrollPane = new JScrollPane(this.jTable);
      GridBagConstraints visIdLabelConstraints = new GridBagConstraints();
      visIdLabelConstraints.insets = new Insets(5, 5, 0, 0);
      visIdLabelConstraints.gridwidth = -1;
      visIdLabelConstraints.anchor = 17;
      visIdLabelConstraints.gridx = 0;
      visIdLabelConstraints.gridy = 0;
      this.add(this.cubeVisIdLabel, visIdLabelConstraints);
      GridBagConstraints visIdValueConstraints = new GridBagConstraints();
      visIdLabelConstraints.insets = new Insets(5, 5, 0, 0);
      visIdValueConstraints.gridwidth = -1;
      visIdValueConstraints.anchor = 17;
      visIdValueConstraints.gridx = 1;
      visIdValueConstraints.gridy = 0;
      visIdValueConstraints.fill = 3;
      this.cubeVisIdText.setText(cubeVisId);
      this.add(this.cubeVisIdText, visIdValueConstraints);
      GridBagConstraints descriptionLabelConstraints = new GridBagConstraints();
      descriptionLabelConstraints.insets = new Insets(5, 5, 0, 0);
      descriptionLabelConstraints.gridwidth = -1;
      descriptionLabelConstraints.anchor = 17;
      descriptionLabelConstraints.gridx = 0;
      descriptionLabelConstraints.gridy = 1;
      this.add(this.cubeDescriptionLabel, descriptionLabelConstraints);
      GridBagConstraints descriptionValueConstraints = new GridBagConstraints();
      descriptionValueConstraints.insets = new Insets(5, 5, 0, 0);
      descriptionValueConstraints.gridwidth = -1;
      descriptionValueConstraints.anchor = 17;
      descriptionValueConstraints.gridx = 1;
      descriptionValueConstraints.gridy = 1;
      descriptionValueConstraints.fill = 3;
      if(cube != null) {
         this.cubeDesciptionText.setText(cube.getDescription());
      }

      this.add(this.cubeDesciptionText, descriptionValueConstraints);
      GridBagConstraints structureLabelConstraints = new GridBagConstraints();
      structureLabelConstraints.insets = new Insets(5, 5, 5, 5);
      structureLabelConstraints.gridwidth = 0;
      structureLabelConstraints.anchor = 17;
      structureLabelConstraints.gridx = 0;
      structureLabelConstraints.gridy = 2;
      this.add(this.cubeStructureLabel, structureLabelConstraints);
      GridBagConstraints tableConstraints2 = new GridBagConstraints();
      tableConstraints2.insets = new Insets(5, 5, 5, 5);
      tableConstraints2.gridwidth = 0;
      tableConstraints2.fill = 1;
      tableConstraints2.weightx = 1.0D;
      tableConstraints2.weighty = 1.0D;
      tableConstraints2.gridy = 3;
      this.add(scrollPane, tableConstraints2);
      GridBagConstraints buttonConstraints = new GridBagConstraints();
      buttonConstraints.insets = new Insets(5, 5, 5, 5);
      buttonConstraints.gridwidth = 0;
      buttonConstraints.anchor = 13;
      buttonConstraints.fill = 3;
      buttonConstraints.gridy = 4;
      buttonConstraints.weightx = 1.0D;
      scrollPane.setPreferredSize(this.jTable.getPreferredScrollableViewportSize());
      this.getTableModel().setModelData(this.dimVisList);
      this.jTable.setModel(this.getTableModel());
      JPanel buttonPanel = new JPanel();
      buttonPanel.add(this.saveButton);
      buttonPanel.add(this.cancelButton);
      this.add(buttonPanel, buttonConstraints);
      this.saveButton.addActionListener(this);
      this.cancelButton.addActionListener(this);
      this.setupColumn(this.dimVisList, this.jTable, 0);
      this.setupColumn((List)this.dimHashtable.get(this.dimVisList.get(0)), this.jTable, 1);
   }

   private void initDimVisList() {
      if(this.dimHashtable != null && this.dimHashtable.size() > 0) {
         Enumeration enumeration = this.dimHashtable.keys();

         while(enumeration.hasMoreElements()) {
            String dimVis = (String)enumeration.nextElement();
            this.dimVisList.add(dimVis);
         }
      }

   }

   private ImportSelectDimAndHierPanel$DimAndHierTableModel getTableModel() {
      if(this.tableModel == null) {
         this.tableModel = new ImportSelectDimAndHierPanel$DimAndHierTableModel(this, this.jTable);
      }

      return this.tableModel;
   }

   public String getName() {
      return "ImportSelectDimAndHierPanel";
   }

   private void setupColumn(List<String> dimVisList, JTable table, int columnIndex) {
      TableColumn columnModel = this.jTable.getColumnModel().getColumn(columnIndex);
      JComboBox combo = new JComboBox();
      Iterator renderer = dimVisList.iterator();

      while(renderer.hasNext()) {
         String string = (String)renderer.next();
         combo.addItem(string);
      }

      if(columnIndex == 0) {
         columnModel.setCellEditor(new DefaultCellEditor(combo));
      } else {
         columnModel.setCellEditor((TableCellEditor)this.editorHashtable.get(dimVisList.get(0)));
      }

      DefaultTableCellRenderer renderer1 = new DefaultTableCellRenderer();
      renderer1.setToolTipText("Click for combo box");
      columnModel.setCellRenderer(renderer1);
   }

   private Hashtable<String, DefaultCellEditor> buildHierCellEditor(List<String> dimList) {
      Hashtable editorHash = new Hashtable();

      String dim;
      JComboBox combo;
      for(Iterator i$ = dimList.iterator(); i$.hasNext(); editorHash.put(dim, new DefaultCellEditor(combo))) {
         dim = (String)i$.next();
         List hierList = (List)this.dimHashtable.get(dim);
         combo = new JComboBox();
         if(hierList != null && hierList.size() > 0) {
            Iterator i$1 = hierList.iterator();

            while(i$1.hasNext()) {
               String hier = (String)i$1.next();
               combo.addItem(hier);
            }
         }
      }

      return editorHash;
   }

   public void actionPerformed(ActionEvent e) {
      if(e.getSource() == this.saveButton) {
         this.saveDataToEditObj();
      }

      this.owner.setVisible(false);
   }

   public void saveDataToEditObj() {
      List tableValues = this.getTableValues();
      if(this.editObj != null && this.editObj instanceof XMLFormModel) {
         XMLFormModel formModel = (XMLFormModel)this.editObj;
         FormConfig formConfig = formModel.getFormConfig();
         Iterator iterator = formConfig.getInputs();

         while(iterator.hasNext()) {
            Object input = iterator.next();
            if(input instanceof FinanceCubeInput) {
               FinanceCubeInput cubeInput = (FinanceCubeInput)input;
               cubeInput.setCubeVisId(formModel.getFinanceCubeVisId());
               DefaultValueMapping valueMapping = (new FinanceCubeService()).buildFinanceCubeMapping();
               int financeCubeId = ((Integer)valueMapping.getValue(formModel.getFinanceCubeVisId())).intValue();
               cubeInput.setCubeId(financeCubeId);
               int childCount = cubeInput.getChildCount();
               if(tableValues != null && tableValues.size() > 0) {
                  for(int i = 0; i < childCount; ++i) {
                     TreeNode node = cubeInput.getChildAt(i);
                     if(node instanceof StructureColumnValue) {
                        StructureColumnValue structureColumnValue = (StructureColumnValue)node;
                        String[] values = (String[])tableValues.get(i);
                        int dimIndx = this.dimVisList.indexOf(values[0]);
                        structureColumnValue.setDim(dimIndx);
                        structureColumnValue.setHier(values[1]);
                     }
                  }
               }
            }
         }
      }

   }

   private List<String[]> getTableValues() {
      int rowCount = this.jTable.getModel().getRowCount();
      ArrayList valueList = new ArrayList();

      for(int i = 0; i < rowCount; ++i) {
         String dimString = this.jTable.getModel().getValueAt(i, 0).toString();
         String hierString = this.jTable.getModel().getValueAt(i, 1).toString();
         String[] value = new String[this.jTable.getColumnCount()];
         value[0] = dimString;
         value[1] = hierString;
         valueList.add(value);
      }

      return valueList;
   }

   // $FF: synthetic method
   static Hashtable accessMethod000(ImportSelectDimAndHierPanel x0) {
      return x0.editorHashtable;
   }

   // $FF: synthetic method
   static Hashtable accessMethod100(ImportSelectDimAndHierPanel x0) {
      return x0.dimHashtable;
   }

   // $FF: synthetic method
   static int accessMethod200(ImportSelectDimAndHierPanel x0) {
      return x0.numOfDim;
   }

   // $FF: synthetic method
   static List accessMethod300(ImportSelectDimAndHierPanel x0) {
      return x0.dimVisList;
   }
}
