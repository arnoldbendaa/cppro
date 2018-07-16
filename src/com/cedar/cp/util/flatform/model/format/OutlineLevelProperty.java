package com.cedar.cp.util.flatform.model.format;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.flatform.model.format.AbstractProperty;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import java.io.IOException;
import java.io.Writer;

/**
*
* @author Jaroslaw Kaczmarski
* @company Softpro.pl Sp. z o.o.
*
* Class defines the outline level property.
*/
public class OutlineLevelProperty extends AbstractProperty implements FormatProperty {
   private static final long serialVersionUID = 3127425059519442581L;
	
   private int mOutlineLevel;

   public OutlineLevelProperty() {
      super("outlineLevel");
   }

   public OutlineLevelProperty(int outlineLevel) {
      this();
      this.mOutlineLevel = outlineLevel;
   }

   public void updateFormat(CellFormat format) {
      format.setOutlineLevel(this.mOutlineLevel);
   }

   public void writeXml(Writer out) throws IOException {
      XmlUtils.outputAttribute(out, this.getName(), Integer.valueOf(this.mOutlineLevel));
   }

   public int getOutlineLevel() {
      return this.mOutlineLevel;
   }

   public void setOutlineLevel(int outlineLevel) {
      this.mOutlineLevel = outlineLevel;
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof OutlineLevelProperty?this.mOutlineLevel == ((OutlineLevelProperty)obj).getOutlineLevel():false);
   }

   public int hashCode() {
      return this.mOutlineLevel;
   }
}
