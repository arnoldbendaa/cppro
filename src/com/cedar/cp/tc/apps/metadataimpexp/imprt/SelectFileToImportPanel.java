// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.ImportFileFilter;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.MetaDataImportApplication;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonPanel;
//import com.cedar.cp.tc.ctrl.QFieldControl;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SelectFileToImportPanel extends CommonPanel implements ActionListener {

   private static final long serialVersionUID = 1L;
   private JFileChooser fileChooser = new JFileChooser();
   private JLabel selectFileLabel = new JLabel("Select file to import :");
   private JTextField fileNameTextField = new JTextField();
   private JButton browseButton = new JButton("Browse..");
   MetaDataImportApplication application = null;
   private JLabel paddingLabel = new JLabel();


   public SelectFileToImportPanel(MetaDataImportApplication application) {
      this.application = application;
      this.setLayout(new GridBagLayout());
      this.add(this.selectFileLabel, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 5, 5), 0, 0));
      this.add(this.fileNameTextField, new GridBagConstraints(0, 1, 1, 1, 1.0D, 0.0D, 17, 2, new Insets(5, 5, 5, 5), 0, 0));
      this.add(this.browseButton, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 5, 5, 5), 0, 0));
      this.add(this.paddingLabel, new GridBagConstraints(0, 2, 2, 1, 0.0D, 1.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
      this.fileNameTextField.setEditable(false);
      this.browseButton.addActionListener(this);
      this.addQControl();
   }

   private void addQControl() {
//      super.addQControl(new QFieldControl(this.fileNameTextField, this.selectFileLabel, new String[]{"impexp.selectfiletoimp.filename", null}));//arnold
   }

   public void init() {}

   public void setData(CommonImpExpItem[] listItemType) {}

   public String getName() {
      return "SelectFileToImportPanel";
   }

   public void setData(Collection itemList) {}

   public void actionPerformed(ActionEvent e) {
      if(e.getSource() == this.browseButton) {
         this.fileChooser.setFileFilter(new ImportFileFilter());
         int returnValue = this.fileChooser.showOpenDialog(this);
         if(returnValue == 0) {
            File importFile = this.fileChooser.getSelectedFile();
            this.fileNameTextField.setText(importFile.getAbsolutePath());
            this.application.setImportZIPFile(new de.schlichtherle.truezip.file.TFile(importFile.getAbsolutePath()));
         }
      }

   }
}
