// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import java.awt.Dimension;
import javax.swing.plaf.basic.BasicArrowButton;

class LittleButton extends BasicArrowButton {

   public LittleButton(int dir) {
      super(dir);
   }

   public Dimension getPreferredSize() {
      return new Dimension(15, 5);
   }
}
