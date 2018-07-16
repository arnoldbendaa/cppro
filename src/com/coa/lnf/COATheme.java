// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 10:25:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lnf;

import java.awt.Color;
import java.util.Map;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalTheme;

public class COATheme extends MetalTheme {

   Map<String, Object> map;


   public COATheme(Map<String, Object> var1) {
      this.map = var1;
   }

   public FontUIResource getControlTextFont() {
      return (FontUIResource)this.map.get("font");
   }

   public FontUIResource getMenuTextFont() {
      return (FontUIResource)this.map.get("font");
   }

   public String getName() {
      return "COA Metal";
   }

   protected ColorUIResource getPrimary1() {
      return new ColorUIResource((Color)this.map.get("color.container.border"));
   }

   protected ColorUIResource getPrimary2() {
      return new ColorUIResource((Color)this.map.get("color.scrollBar.background"));
   }

   protected ColorUIResource getPrimary3() {
      return new ColorUIResource((Color)this.map.get("color.titleBar.background"));
   }

   protected ColorUIResource getSecondary1() {
      return new ColorUIResource((Color)this.map.get("color.button.border"));
   }

   protected ColorUIResource getSecondary2() {
      return new ColorUIResource((Color)this.map.get("color.button.disabledBorder"));
   }

   protected ColorUIResource getSecondary3() {
      return new ColorUIResource((Color)this.map.get("color.button.background"));
   }

   public FontUIResource getSubTextFont() {
      return (FontUIResource)this.map.get("font");
   }

   public FontUIResource getSystemTextFont() {
      return (FontUIResource)this.map.get("font");
   }

   public FontUIResource getUserTextFont() {
      return (FontUIResource)this.map.get("font");
   }

   public FontUIResource getWindowTitleFont() {
      return (FontUIResource)this.map.get("font");
   }
}
