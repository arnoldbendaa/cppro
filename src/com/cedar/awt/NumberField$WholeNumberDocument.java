// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class NumberField$WholeNumberDocument extends PlainDocument {

   public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
      char[] source = str.toCharArray();
      char[] result = new char[source.length];
      int j = 0;

      for(int i = 0; i < result.length; ++i) {
         if(Character.isDigit(source[i])) {
            result[j++] = source[i];
         }
      }

      super.insertString(offs, new String(result, 0, j), a);
   }
}
