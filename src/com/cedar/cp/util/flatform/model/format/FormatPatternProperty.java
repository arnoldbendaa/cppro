// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.format;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.flatform.model.format.AbstractProperty;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import java.io.IOException;
import java.io.Writer;

public class FormatPatternProperty extends AbstractProperty implements FormatProperty {

   private String mFormatPattern;


   public FormatPatternProperty() {
      super("formatPattern");
   }

   public FormatPatternProperty(String formatPattern) {
      this();
      this.mFormatPattern = formatPattern;
   }

   public void updateFormat(CellFormat format) {
      format.setFormatPattern(this.mFormatPattern);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), this.mFormatPattern);
   }

   public String getFormatPattern() {
      return this.mFormatPattern;
   }

   public void setFormatPattern(String formatPattern) {
      this.mFormatPattern = formatPattern;
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof FormatPatternProperty?this.mFormatPattern.equals(((FormatPatternProperty)obj).getFormatPattern()):false);
   }

   public int hashCode() {
      return this.mFormatPattern.hashCode();
   }
}
