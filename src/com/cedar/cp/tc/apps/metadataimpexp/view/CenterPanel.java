// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.view;

import java.awt.CardLayout;
import javax.swing.JPanel;

public class CenterPanel extends JPanel {

   public CenterPanel() {
      try {
         this.jbInit();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void jbInit() throws Exception {
      this.setLayout(new CardLayout());
   }

   public void addPanel(JPanel panel) {
      this.add(panel, panel.getName());
   }

   public void removePanel(JPanel panel) {
      this.remove(panel);
   }

   public void showPanel(JPanel panel) {
      CardLayout cl = (CardLayout)((CardLayout)this.getLayout());
      cl.show(this, panel.getName());
   }
}
