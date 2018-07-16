// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 10:25:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lnf;

import com.coa.lnf.SubstanceSkinComboSelector$2;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.skin.SkinInfo;

class SubstanceSkinComboSelector$2$1 implements Runnable {

   // $FF: synthetic field
   final SubstanceSkinComboSelector$2 this$1;


   SubstanceSkinComboSelector$2$1(SubstanceSkinComboSelector$2 var1) {
      this.this$1 = var1;
   }

   public void run() {
      SubstanceLookAndFeel.setSkin(((SkinInfo)this.this$1.this$0.getSelectedItem()).getClassName());
   }
}
