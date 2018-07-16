// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.TrafficLight;
import java.io.IOException;
import java.io.Writer;

public class DefaultTrafficLight extends TrafficLight {

   public DefaultTrafficLight() {
      this.setUserObject("DefaultTrafficLight");
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<defaultTrafficLight ");
      if(this.getLowTide() != null) {
         out.write(" lowTide=\"" + this.getLowTide() + "\"");
      }

      if(this.getHighTide() != null) {
         out.write(" highTide=\"" + this.getHighTide() + "\"");
      }

      out.write(" />");
   }
}
