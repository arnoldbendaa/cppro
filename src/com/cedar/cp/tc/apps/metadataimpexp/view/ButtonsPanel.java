// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.view;

import com.cedar.cp.tc.apps.metadataimpexp.util.ImpExpActions;
import com.cedar.cp.tc.apps.metadataimpexp.view.ButtonsPanel$1;
import com.cedar.cp.tc.apps.metadataimpexp.view.ButtonsPanel$2;
import com.cedar.cp.tc.apps.metadataimpexp.view.ButtonsPanel$3;
import com.cedar.cp.tc.apps.metadataimpexp.view.ButtonsPanel$4;
//import com.cedar.cp.tc.ctrl.QToolBarButton;
//import com.cedar.cp.tc.ctrl.StandardActions;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ButtonsPanel extends JPanel {

   private ImpExpActions impExpActions;
//   private QToolBarButton helpButton = null;
   private JButton cancelButton = new JButton();
   private JButton backButton = new JButton();
   private JLabel jLabel1 = new JLabel();
   private JButton nextButton = new JButton();
   private GridBagLayout gridBagLayout1 = new GridBagLayout();
   private Border border1;


   public ButtonsPanel(ImpExpActions impExpActions) {
      this.border1 = BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151));
      this.impExpActions = impExpActions;

      try {
         this.jbInit();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
	   //arnold all
//      this.setLayout(this.gridBagLayout1);
//      StandardActions sa = new StandardActions();
//      this.helpButton = new QToolBarButton(sa.getAction("whats-this-help-command"));
//      this.helpButton.addActionListener(new ButtonsPanel$1(this));
//      this.cancelButton.setText("Cancel");
//      this.cancelButton.addActionListener(new ButtonsPanel$2(this));
//      this.backButton.setText("<< Back");
//      this.backButton.addActionListener(new ButtonsPanel$3(this));
//      this.nextButton.setText("Next >>");
//      this.nextButton.addActionListener(new ButtonsPanel$4(this));
//      this.add(this.cancelButton, new GridBagConstraints(4, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(20, 3, 10, 3), 0, 0));
//      this.add(this.nextButton, new GridBagConstraints(3, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(20, 3, 10, 3), 0, 0));
//      this.add(this.backButton, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(20, 3, 10, 3), 0, 0));
//      this.add(this.jLabel1, new GridBagConstraints(1, 0, 1, 1, 1.0D, 0.0D, 10, 1, new Insets(20, 3, 10, 3), 0, 0));
//      this.add(this.helpButton, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(20, 3, 10, 3), 0, 0));
   }

   public void cancelButton_actionPerformed(ActionEvent actionEvent) {
      this.impExpActions.doCancel();
   }

   public void nextButton_actionPerformed(ActionEvent actionEvent) {
      this.impExpActions.doNext();
   }

   public void backButton_actionPerformed(ActionEvent actionEvent) {
      this.impExpActions.doBack();
   }

   public void setImpExpActions(ImpExpActions impExpActions) {
      this.impExpActions = impExpActions;
   }

   public void setIsLastStep() {
      this.backButton.setEnabled(true);
      this.nextButton.setText("Finish");
   }

   public void setIsFirstStep() {
      this.backButton.setEnabled(false);
      this.nextButton.setEnabled(true);
      this.nextButton.setText("Next >>");
   }

   public void setIsInMiddleStep() {
      this.backButton.setEnabled(true);
      this.nextButton.setEnabled(true);
      this.nextButton.setText("Next >>");
   }

   public void setIsInExportOperation() {
      this.backButton.setEnabled(false);
      this.nextButton.setEnabled(false);
   }

   public void setCompleteExportOperation(boolean isSuccess) {
      this.backButton.setVisible(!isSuccess);
      this.nextButton.setVisible(!isSuccess);
      if(isSuccess) {
         this.cancelButton.setText("Close");
      } else {
         this.backButton.setEnabled(true);
         this.cancelButton.setText("Cancel");
      }

   }
}
