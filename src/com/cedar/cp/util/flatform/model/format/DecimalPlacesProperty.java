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

public class DecimalPlacesProperty extends AbstractProperty implements FormatProperty {

   private int mDecimalPlaces;


   public DecimalPlacesProperty() {
      super("decimalPlaces");
   }

   public DecimalPlacesProperty(int decimalPlaces) {
      this();
      this.mDecimalPlaces = decimalPlaces;
   }

   public void updateFormat(CellFormat format) {
      format.setDecimalPlaces(this.mDecimalPlaces);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), Integer.valueOf(this.mDecimalPlaces));
   }

   public int getDecimalPlaces() {
      return this.mDecimalPlaces;
   }

   public void setDecimalPlaces(int decimalPlaces) {
      this.mDecimalPlaces = decimalPlaces;
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof DecimalPlacesProperty?this.mDecimalPlaces == ((DecimalPlacesProperty)obj).getDecimalPlaces():false);
   }

   public int hashCode() {
      return this.mDecimalPlaces;
   }
}
