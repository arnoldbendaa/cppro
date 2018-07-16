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

public class VerticalAlignmentProperty extends AbstractProperty implements FormatProperty {

   private int mVerticalAlginment;


   public VerticalAlignmentProperty() {
      super("verticalAlignment");
   }

   public VerticalAlignmentProperty(int VerticalAlginment) {
      this();
      this.mVerticalAlginment = VerticalAlginment;
   }

   public int getVerticalAlginment() {
      return this.mVerticalAlginment;
   }

   public void setVerticalAlginment(int VerticalAlginment) {
      this.mVerticalAlginment = VerticalAlginment;
   }

   public void updateFormat(CellFormat format) {
      format.setVerticalAlignment(this.mVerticalAlginment);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), Integer.valueOf(this.mVerticalAlginment));
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof VerticalAlignmentProperty?this.mVerticalAlginment == ((VerticalAlignmentProperty)obj).getVerticalAlginment():false);
   }

   public int hashCode() {
      return this.mVerticalAlginment;
   }
}
