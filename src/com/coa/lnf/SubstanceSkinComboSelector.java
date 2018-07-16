// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 10:25:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lnf;

import com.coa.lnf.SubstanceSkinComboSelector$1;
import com.coa.lnf.SubstanceSkinComboSelector$2;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JComboBox;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.api.SubstanceSkin;
import org.jvnet.substance.skin.SkinInfo;

public class SubstanceSkinComboSelector extends JComboBox {

   public SubstanceSkinComboSelector() {
      super((new ArrayList(SubstanceLookAndFeel.getAllSkins().values())).toArray());
      SubstanceSkin var1 = SubstanceLookAndFeel.getCurrentSkin();
      Iterator var2 = SubstanceLookAndFeel.getAllSkins().values().iterator();

      while(var2.hasNext()) {
         SkinInfo var3 = (SkinInfo)var2.next();
         if(var3.getDisplayName().compareTo(var1.getDisplayName()) == 0) {
            this.setSelectedItem(var3);
            break;
         }
      }

      this.setRenderer(new SubstanceSkinComboSelector$1(this, this));
      this.addActionListener(new SubstanceSkinComboSelector$2(this));
   }
}
