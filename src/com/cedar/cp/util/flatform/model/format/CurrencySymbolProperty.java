// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.format;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.flatform.model.format.AbstractProperty;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import java.io.IOException;
import java.io.Writer;

public class CurrencySymbolProperty extends AbstractProperty implements FormatProperty {

   private String mCurrencySymbol;


   public CurrencySymbolProperty() {
      super("currencySymbol");
   }

   public CurrencySymbolProperty(String currencySymbol) {
      this();
      this.mCurrencySymbol = currencySymbol;
   }

   public void updateFormat(CellFormat format) {
      format.setCurrencySymbol(this.mCurrencySymbol);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), this.mCurrencySymbol);
   }

   public String getCurrencySymbol() {
      return this.mCurrencySymbol;
   }

   public void setCurrencySymbol(String currencySymbol) {
      this.mCurrencySymbol = currencySymbol;
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof CurrencySymbolProperty?this.mCurrencySymbol.equals(((CurrencySymbolProperty)obj).getCurrencySymbol()):false);
   }

   public int hashCode() {
      return this.mCurrencySymbol.hashCode();
   }
}
