// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.NumberField$WholeNumberDocument;
import javax.swing.JTextField;
import javax.swing.text.Document;

class NumberField extends JTextField {

   protected Document createDefaultModel() {
      return new NumberField$WholeNumberDocument();
   }
}
