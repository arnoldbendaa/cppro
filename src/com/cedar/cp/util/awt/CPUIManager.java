// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.coa.lnf.COALnF;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.UIManager;

public class CPUIManager {

   public static void setLookAndFeel() {
      try {
         COALnF.initialise();
         UIManager.put("TableHeader.cellBorder", BorderFactory.createMatteBorder(0, 0, 1, 1, Color.GRAY));
         UIManager.put("TableHeader.background", new Color(13951995));
      } catch (Throwable var1) {
         var1.printStackTrace();
      }

   }
}
