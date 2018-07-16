// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 10:25:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lnf.fonts;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.swing.plaf.FontUIResource;
import org.jvnet.substance.fonts.FontSet;

public class COAFontSet implements FontSet {

   private FontUIResource controlFont;
   private FontUIResource menuFont;
   private FontUIResource titleFont;
   private FontUIResource messageFont;
   private FontUIResource smallFont;
   private FontUIResource windowTitleFont;


   public COAFontSet(Map<String, FontUIResource> var1) {
      Set var2 = var1.entrySet();
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         if(((String)var4.getKey()).equals("font.standard")) {
            this.controlFont = (FontUIResource)var4.getValue();
            this.menuFont = this.controlFont;
         } else if(((String)var4.getKey()).equals("font.title")) {
            this.titleFont = (FontUIResource)var4.getValue();
            this.windowTitleFont = this.titleFont;
         } else if(((String)var4.getKey()).equals("font.label")) {
            this.smallFont = (FontUIResource)var4.getValue();
         } else if(((String)var4.getKey()).equals("font.message")) {
            this.messageFont = (FontUIResource)var4.getValue();
         }
      }

   }

   public FontUIResource getControlFont() {
      return this.controlFont;
   }

   public FontUIResource getMenuFont() {
      return this.menuFont;
   }

   public FontUIResource getMessageFont() {
      return this.messageFont;
   }

   public FontUIResource getSmallFont() {
      return this.smallFont;
   }

   public FontUIResource getTitleFont() {
      return this.titleFont;
   }

   public FontUIResource getWindowTitleFont() {
      return this.windowTitleFont;
   }
}
