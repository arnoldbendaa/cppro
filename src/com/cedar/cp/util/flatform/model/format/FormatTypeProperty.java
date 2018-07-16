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

public class FormatTypeProperty extends AbstractProperty implements FormatProperty {

   private int mFormatType;


   public FormatTypeProperty() {
      super("formatType");
   }

   public FormatTypeProperty(int formatType) {
      this();
      this.mFormatType = formatType;
   }

   public void updateFormat(CellFormat format) {
      format.setFormatType(this.mFormatType);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), Integer.valueOf(this.mFormatType));
   }

   public int getFormatType() {
      return this.mFormatType;
   }

   public void setFormatType(int formatType) {
      this.mFormatType = formatType;
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof FormatTypeProperty?this.mFormatType == ((FormatTypeProperty)obj).getFormatType():false);
   }

   public int hashCode() {
      return this.mFormatType;
   }
}
