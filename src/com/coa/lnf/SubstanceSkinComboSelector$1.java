// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 10:25:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lnf;

import com.coa.lnf.SubstanceSkinComboSelector;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JList;
import org.jvnet.substance.api.renderers.SubstanceDefaultComboBoxRenderer;
import org.jvnet.substance.skin.SkinInfo;

class SubstanceSkinComboSelector$1 extends SubstanceDefaultComboBoxRenderer {

   // $FF: synthetic field
   final SubstanceSkinComboSelector this$0;


   SubstanceSkinComboSelector$1(SubstanceSkinComboSelector var1, JComboBox var2) {
      super(var2);
      this.this$0 = var1;
   }

   public Component getListCellRendererComponent(JList var1, Object var2, int var3, boolean var4, boolean var5) {
      return super.getListCellRendererComponent(var1, ((SkinInfo)var2).getDisplayName(), var3, var4, var5);
   }
}
