// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.xmlform.DefaultTrafficLight;
import com.cedar.cp.util.xmlform.TrafficLight;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

public class TrafficLights extends DefaultMutableTreeNode implements XMLWritable {

   private DefaultTrafficLight mDefaultTrafficLight;
   private List<TrafficLight> mTrafficLights = new ArrayList();
   private String mLowColor;
   private String mMidColor;
   private String mHighColor;


   public TrafficLights() {
      this.setUserObject("TrafficLights");
   }

   public void addTrafficLight(TrafficLight trafficLight) {
      this.mTrafficLights.add(trafficLight);
      super.add(trafficLight);
   }

   public List getTrafficLights() {
      return this.mTrafficLights;
   }

   public TrafficLight getTrafficLightForLevel(int raLevel) {
      return (TrafficLight)(raLevel >= 0 && raLevel < this.mTrafficLights.size()?(TrafficLight)this.mTrafficLights.get(raLevel):this.mDefaultTrafficLight);
   }

   public void removeTrafficLight(TrafficLight trafficLight) {
      this.mTrafficLights.remove(trafficLight);
      super.remove(trafficLight);
   }

   public String getLowColor() {
      return this.mLowColor != null && !this.mLowColor.equals("")?this.mLowColor:"FFFFFF";
   }

   public void setLowColor(String lowColor) {
      this.mLowColor = lowColor;
   }

   public String getMidColor() {
      return this.mMidColor != null && !this.mMidColor.equals("")?this.mMidColor:"FFFFFF";
   }

   public void setMidColor(String midColor) {
      this.mMidColor = midColor;
   }

   public String getHighColor() {
      return this.mHighColor != null && !this.mHighColor.equals("")?this.mHighColor:"FFFFFF";
   }

   public void setHighColor(String highColor) {
      this.mHighColor = highColor;
   }

   public DefaultTrafficLight getDefaultTrafficLight() {
      return this.mDefaultTrafficLight;
   }

   public void setDefaultTrafficLight(DefaultTrafficLight defaultTrafficLight) {
      if(this.mDefaultTrafficLight != null) {
         super.remove(this.mDefaultTrafficLight);
      }

      this.mDefaultTrafficLight = defaultTrafficLight;
      if(this.mDefaultTrafficLight != null) {
         super.add(this.mDefaultTrafficLight);
      }

   }

   public void removeDefaultTrafficLight() {
      if(this.mDefaultTrafficLight != null) {
         super.remove(this.mDefaultTrafficLight);
         this.mDefaultTrafficLight = null;
      }

   }

   public void writeXml(Writer out) throws IOException {
      out.write("<trafficLights ");
      if(this.mLowColor != null && this.mLowColor.trim().length() > 0) {
         out.write(" lowColor=\"" + XmlUtils.escapeStringForXML(this.mLowColor) + "\"");
      }

      if(this.mMidColor != null && this.mMidColor.trim().length() > 0) {
         out.write(" midColor=\"" + XmlUtils.escapeStringForXML(this.mMidColor) + "\"");
      }

      if(this.mHighColor != null && this.mHighColor.trim().length() > 0) {
         out.write(" highColor=\"" + XmlUtils.escapeStringForXML(this.mHighColor) + "\"");
      }

      out.write(">");
      if(this.mDefaultTrafficLight != null) {
         this.mDefaultTrafficLight.writeXml(out);
      }

      Iterator i$ = this.mTrafficLights.iterator();

      while(i$.hasNext()) {
         TrafficLight mTrafficLight = (TrafficLight)i$.next();
         mTrafficLight.writeXml(out);
      }

      out.write("</trafficLights>");
   }
}
