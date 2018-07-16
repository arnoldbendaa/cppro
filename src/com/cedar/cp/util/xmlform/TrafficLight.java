// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.Column;
import java.io.IOException;
import java.io.Writer;

public class TrafficLight extends Column {

   private Double mLowTide;
   private Double mHighTide;


   public TrafficLight() {
      this.setUserObject("TrafficLight");
   }

   public Double getLowTide() {
      return this.mLowTide;
   }

   public void setLowTide(Double lowTide) {
      this.mLowTide = lowTide;
   }

   public Double getHighTide() {
      return this.mHighTide;
   }

   public void setHighTide(Double highTide) {
      this.mHighTide = highTide;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<trafficLight ");
      if(this.mLowTide != null) {
         out.write(" lowTide=\"" + this.mLowTide + "\"");
      }

      if(this.mHighTide != null) {
         out.write(" highTide=\"" + this.mHighTide + "\"");
      }

      out.write(" />");
   }
}
