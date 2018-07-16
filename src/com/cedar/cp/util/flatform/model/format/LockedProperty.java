// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.format;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.flatform.model.format.AbstractProperty;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import java.io.IOException;
import java.io.Writer;

public class LockedProperty extends AbstractProperty {

   private boolean mLocked;


   public LockedProperty() {
      super("locked");
   }

   public LockedProperty(boolean locked) {
      this();
      this.mLocked = locked;
   }

   public void updateFormat(CellFormat format) {
      format.setLocked(this.mLocked);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), Boolean.valueOf(this.mLocked));
   }

   public boolean isLocked() {
      return this.mLocked;
   }

   public void setLocked(boolean locked) {
      this.mLocked = locked;
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof LockedProperty?this.mLocked == ((LockedProperty)obj).isLocked():false);
   }

   public int hashCode() {
      return this.mLocked?1:0;
   }
}
