// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.ErrorNotifiable;
import javax.swing.JLabel;

public class ErrorMessageLabel extends JLabel implements ErrorNotifiable {

   public void setValid(boolean isValid, String message, Object info) {
      if(isValid) {
         this.setText((String)null);
      } else {
         this.setText(message);
      }

   }
}