// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.celleditor;

import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class RadioPanel extends JPanel implements ActionListener, FocusListener {

   private JRadioButton ignoreRadio = new JRadioButton();
   private JRadioButton overwriteRadio = new JRadioButton();
   private JRadioButton alternativeRadio = new JRadioButton();
   private JTextField alternativeNameText = new JTextField(15);
   private JLabel errorMark = new JLabel("x");
   private CommonImpExpItem editObj = null;


   public RadioPanel(CommonImpExpItem itemNode) {
      this.editObj = itemNode;
      this.init();
   }

   public void init() {
      this.setBackground(Color.WHITE);
      this.setLayout(new GridBagLayout());
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.insets = new Insets(0, 10, 0, 0);
      this.add(this.ignoreRadio, gridBagConstraints);
      GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
      gridBagConstraints2.insets = new Insets(0, 30, 0, 0);
      this.add(this.overwriteRadio, gridBagConstraints2);
      this.add(this.alternativeRadio, gridBagConstraints2);
      GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
      gridBagConstraints3.insets = new Insets(0, 0, 0, 5);
      gridBagConstraints3.fill = 1;
      gridBagConstraints3.weightx = 1.0D;
      this.add(this.alternativeNameText, gridBagConstraints3);
      ButtonGroup buttonGroup = new ButtonGroup();
      buttonGroup.add(this.ignoreRadio);
      buttonGroup.add(this.overwriteRadio);
      buttonGroup.add(this.alternativeRadio);
      this.ignoreRadio.addActionListener(this);
      this.overwriteRadio.addActionListener(this);
      this.alternativeRadio.addActionListener(this);
      this.alternativeNameText.setEditable(false);
      this.alternativeNameText.addFocusListener(this);
      if(this.editObj != null) {
         this.ignoreRadio.setSelected(this.editObj.isIgnore());
         this.overwriteRadio.setSelected(this.editObj.isOverwrite());
         this.alternativeRadio.setSelected(this.editObj.hasAlternativeName());
         this.errorMark.setVisible(this.editObj.hasError());
      }

   }

   public void actionPerformed(ActionEvent e) {
      this.editObj.setIgnore(this.ignoreRadio.isSelected());
      this.editObj.setOverwrite(this.overwriteRadio.isSelected());
      this.editObj.setHasAlternativeName(this.alternativeRadio.isSelected());
      this.alternativeNameText.setEditable(this.alternativeRadio.isSelected());
   }

   public void focusLost(FocusEvent e) {
      if(e.getSource() == this.alternativeNameText) {
         this.editObj.setAlternativeName(this.alternativeNameText.getText());
      }

   }

   public void focusGained(FocusEvent e) {}
}
