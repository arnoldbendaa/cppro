// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import java.awt.Image;
import java.beans.SimpleBeanInfo;

public class QTableElementPickerBeanInfo extends SimpleBeanInfo {

   public Image getIcon(int iconStyle) {
      return iconStyle == 2?this.loadImage("QTableElementPicker.gif"):null;
   }
}
