// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;

public class TextValidationException extends ValidationException {

   private int mIndex;
   private int mLine;
   private int mColumn;


   public TextValidationException(String reason, int index, int line, int column) {
      super(reason);
      this.mIndex = index;
      this.mLine = line;
      this.mColumn = column;
   }

   public TextValidationException(EntityRef ref, String reason, int index, int line, int column) {
      super(ref, reason);
      this.mIndex = index;
      this.mLine = line;
      this.mColumn = column;
   }

   public int getIndex() {
      return this.mIndex;
   }

   public int getLine() {
      return this.mLine;
   }

   public int getColumn() {
      return this.mColumn;
   }
}
