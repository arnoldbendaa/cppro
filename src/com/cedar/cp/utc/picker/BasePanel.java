// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.picker;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

public class BasePanel extends JPanel {

   public BasePanel() {
      super(new BorderLayout());
   }

   public Dimension getPreferredSize() {
      return new Dimension(300, 300);
   }

   public Dimension getMaximumSize() {
      return new Dimension(400, 400);
   }

   public Dimension getMinimumSize() {
      return new Dimension(200, 200);
   }
}
