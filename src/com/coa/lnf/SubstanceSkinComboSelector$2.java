// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 10:25:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lnf;

import com.coa.lnf.SubstanceSkinComboSelector;
import com.coa.lnf.SubstanceSkinComboSelector$2$1;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

class SubstanceSkinComboSelector$2 implements ActionListener {

   // $FF: synthetic field
   final SubstanceSkinComboSelector this$0;


   SubstanceSkinComboSelector$2(SubstanceSkinComboSelector var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent var1) {
      SwingUtilities.invokeLater(new SubstanceSkinComboSelector$2$1(this));
   }
}
