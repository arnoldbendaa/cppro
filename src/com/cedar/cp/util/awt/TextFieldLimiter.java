// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class TextFieldLimiter extends PlainDocument {

   int maxChar = -1;


   public TextFieldLimiter(int len) {
      this.maxChar = len;
   }

   public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
      if(str != null && this.maxChar > 0 && this.getLength() + str.length() > this.maxChar) {
         Toolkit.getDefaultToolkit().beep();
      } else {
         super.insertString(offs, str, a);
      }
   }
}
