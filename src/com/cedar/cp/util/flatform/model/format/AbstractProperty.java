// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.format;

import com.cedar.cp.util.flatform.model.format.FormatProperty;
import java.io.Serializable;

public abstract class AbstractProperty implements FormatProperty, Serializable {

   private String mName;


   public AbstractProperty(String name) {
      this.mName = name;
   }

   public boolean isXmlAttribute() {
      return true;
   }

   public String getName() {
      return this.mName;
   }
}
