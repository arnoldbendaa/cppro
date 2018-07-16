// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.view;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HeaderPanel extends JPanel {

   private JLabel headerLabel = new JLabel();
   private BorderLayout borderLayout1 = new BorderLayout();


   public HeaderPanel() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setLayout(this.borderLayout1);
      this.headerLabel.setFont(new Font("Tahoma", 1, 14));
      this.headerLabel.setText("jLabel1");
      this.add(this.headerLabel, "Center");
   }
}
