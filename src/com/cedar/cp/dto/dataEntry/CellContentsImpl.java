// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dataEntry;

import com.cedar.cp.api.dataEntry.CellContents;
import java.math.BigDecimal;

public class CellContentsImpl implements CellContents {

   private String mNote;
   private BigDecimal mPublicValue;


   public CellContentsImpl(String note, BigDecimal publicValue) {
      this.mNote = note;
      this.mPublicValue = publicValue;
   }

   public String getNote() {
      return this.mNote;
   }

   public BigDecimal getPublicValue() {
      return this.mPublicValue;
   }
}
