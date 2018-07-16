// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export;

import com.cedar.cp.tc.apps.metadataimpexp.export.SelectDestinationFileExportPanel$1;
import com.cedar.cp.tc.apps.metadataimpexp.export.SelectDestinationFileExportPanel$ExportFileFilter;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonPanel;
//import com.cedar.cp.tc.ctrl.QControl;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.Collection;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SelectDestinationFileExportPanel extends CommonPanel {

   private int mOption;
   private String desFileName = "";
   private JFileChooser fileChooser = new JFileChooser();
   private JLabel seletFileLabel = new JLabel();
   private JTextField fileNameTextField = new JTextField();
   private JButton browseButton = new JButton();
   private GridBagLayout gridBagLayout = new GridBagLayout();
   private JLabel paddingLabel = new JLabel();


   public SelectDestinationFileExportPanel() {
      SelectDestinationFileExportPanel$ExportFileFilter exportFileFilter = new SelectDestinationFileExportPanel$ExportFileFilter(this);
      this.fileChooser.setAcceptAllFileFilterUsed(false);
      this.fileChooser.addChoosableFileFilter(exportFileFilter);

      try {
         this.jbInit();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setLayout(this.gridBagLayout);
      this.seletFileLabel.setText("Select export file:");
      this.browseButton.setText("Browse..");
      this.add(this.seletFileLabel, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 5, 5), 0, 0));
      this.add(this.fileNameTextField, new GridBagConstraints(0, 1, 1, 1, 1.0D, 0.0D, 17, 2, new Insets(5, 5, 5, 5), 0, 0));
      this.add(this.browseButton, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 5, 5, 5), 0, 0));
      this.add(this.paddingLabel, new GridBagConstraints(0, 2, 2, 1, 0.0D, 1.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.fileNameTextField.setEditable(false);
      this.browseButton.addActionListener(new SelectDestinationFileExportPanel$1(this));
      this.addQControl();
   }

   private void addQControl() {
//      QControl qTreeTableControl = new QControl(this.fileNameTextField, this.seletFileLabel, new String[]{"impexp.selectdestinationfileexport.filename", null});
//      super.addQControl(qTreeTableControl);
   }

   public void init() {}

   public String getName() {
      return "SelectDestinationFileExportPanel";
   }

   private void chooseFile() {
      this.mOption = 1;

      while(this.mOption == 1) {
         int returnVal = this.fileChooser.showSaveDialog(this);
         switch(returnVal) {
         case 0:
            File file = this.fileChooser.getSelectedFile();
            String fName = file.getParent() + System.getProperty("file.separator") + this.appendZipExtension(file.getName());
            this.fileNameTextField.setText(fName);
            if(!file.exists()) {
               return;
            }

            this.mOption = JOptionPane.showConfirmDialog(this, fName + " already exists.\n" + "Do you want to replace it?", "Save as", 1, 2);
            if(this.mOption == 0) {
               this.desFileName = fName;
            }
            break;
         case 1:
            return;
         }
      }

   }

   public String getDestinationFileName() {
      String fileName = this.fileNameTextField.getText();
      return fileName != null && fileName.trim().length() != 0?fileName:null;
   }

   public boolean isDesFileExist() {
      String fileName = this.fileNameTextField.getText();
      File file = new File(fileName);
      return file.exists();
   }

   public boolean isOverwriteDesFile() {
      String fName = this.getDestinationFileName();
      return !this.isDesFileExist()?true:fName != null && this.isDesFileExist() && fName.equals(this.desFileName) && this.mOption == 0;
   }

   private String appendZipExtension(String fName) {
      int i = fName.lastIndexOf(46);
      StringBuffer sb = new StringBuffer(fName);
      if(i == -1) {
         sb.append(".zip");
      } else if(!fName.substring(i).equalsIgnoreCase(".zip")) {
         sb.append(".zip");
      }

      return sb.toString();
   }

   public void setData(Collection itemList) {}

   public void setData(CommonImpExpItem[] itemList) {}

   // $FF: synthetic method
   static void accessMethod000(SelectDestinationFileExportPanel x0) {
      x0.chooseFile();
   }
}
