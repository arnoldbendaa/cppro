// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.CPUIManager;
import com.cedar.cp.util.awt.QuantumCheckBox;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

final class QuantumCheckBox$1 implements Runnable {

   public void run() {
      CPUIManager.setLookAndFeel();
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(3);
      frame.setLayout(new FlowLayout());
      QuantumCheckBox cb = new QuantumCheckBox("Quantum");
      JCheckBox jcb = new JCheckBox("Normal");
      cb.setAllowIntermediateState(true);
      frame.add(jcb);
      frame.add(cb);
      frame.pack();
      frame.setVisible(true);
   }
}
