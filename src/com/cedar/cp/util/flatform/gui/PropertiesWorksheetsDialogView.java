// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.awt.OneColumnLayout;
import com.cedar.cp.util.awt.QuantumCheckBox;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PropertiesWorksheetsDialogView extends JPanel {

   private static final long serialVersionUID = 1L;
   private JLabel mLabelNewName;
   JTextField mNewName;
   QuantumCheckBox mHidden;


   public PropertiesWorksheetsDialogView() {
      super(new OneColumnLayout(5));
      super.setPreferredSize(new Dimension(300, 70));
      this.mLabelNewName = new JLabel("Enter new name for sheet");
      this.add(this.mLabelNewName);
      this.mNewName = new JTextField();
      this.add(this.mNewName);
      this.mHidden = new QuantumCheckBox("Hidden");
      this.add(this.mHidden);
   }
}
