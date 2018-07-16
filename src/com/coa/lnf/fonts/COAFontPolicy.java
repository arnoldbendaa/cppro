// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 10:25:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lnf.fonts;

import com.coa.lnf.fonts.COAFontSet;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;
import javax.swing.UIDefaults;
import javax.swing.plaf.FontUIResource;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.fonts.FontPolicy;
import org.jvnet.substance.fonts.FontSet;

public class COAFontPolicy implements FontPolicy {

   private Map<String, FontUIResource> fontMap;
   private static final String DEFAULT_PATH = "com/coa/lnf/fonts/coa-default.fonts";
   private static final String RESOURCE_NAME = "coalnf.fonts";


   public COAFontPolicy(Map<String, FontUIResource> var1) {
      this.fontMap = var1;
   }

   public static COAFontPolicy createPolicy(Map<String, FontUIResource> var0) {
      SubstanceLookAndFeel.setFontPolicy((FontPolicy)null);
      return new COAFontPolicy(var0);
   }

   public static COAFontPolicy createPolicy(String var0) {
      URL var1 = null;
      if(null != var0) {
         var1 = COAFontPolicy.class.getClassLoader().getResource(var0);
      } else {
         var1 = COAFontPolicy.class.getClassLoader().getResource("coalnf.fonts");
      }

      return new COAFontPolicy(loadFonts(var1));
   }

   public static COAFontPolicy createPolicy(URL var0) {
      return new COAFontPolicy(loadFonts(var0));
   }

   private static Map<String, FontUIResource> loadFonts(URL var0) {
      HashMap var1 = null;
      Properties var2 = new Properties();
      URL var3 = COAFontPolicy.class.getClassLoader().getResource("com/coa/lnf/fonts/coa-default.fonts");

      try {
         var2.load(var3.openStream());
         if(null != var0) {
            Properties var5 = new Properties();
            var5.load(var0.openStream());
            var2.putAll(var5);
         }

         var1 = new HashMap();
         Set var13 = var2.entrySet();
         Iterator var6 = var13.iterator();

         while(var6.hasNext()) {
            Entry var7 = (Entry)var6.next();
            String var8 = (String)var7.getKey();
            String var9 = (String)var7.getValue();
            if(var8.startsWith("font.")) {
               String[] var10 = var9.split(",");
               var10[0] = var10[0].trim();
               var10[1] = var10[1].trim();
               var10[2] = var10[2].trim();
               if(var10.length != 3) {
                  throw new IllegalArgumentException("Invalid definition at: " + var8);
               }

               if(!var10[0].matches("[\\w+ ]+")) {
                  throw new IllegalArgumentException("Invalid definition at the first value at: " + var8);
               }

               if(!var10[1].equalsIgnoreCase("plain") && !var10[1].equalsIgnoreCase("bold") && !var10[1].equalsIgnoreCase("italic") && !var10[1].equalsIgnoreCase("bold italic") && !var10[1].equalsIgnoreCase("italic bold")) {
                  throw new IllegalArgumentException("Invalid definition at the second value at: " + var8);
               }

               if(!var10[2].matches("\\d+")) {
                  throw new IllegalArgumentException("Invalid definition at the third value at: " + var8);
               }

               byte var4;
               if(var10[1].equalsIgnoreCase("italic")) {
                  var4 = 2;
               } else if(var10[1].equalsIgnoreCase("bold")) {
                  var4 = 1;
               } else if(!var10[1].equalsIgnoreCase("bold italic") && !var10[1].equalsIgnoreCase("italic bold")) {
                  var4 = 0;
               } else {
                  var4 = 3;
               }

               FontUIResource var11 = new FontUIResource(var10[0], var4, Integer.parseInt(var10[2]));
               var1.put(var8, var11);
            }
         }
      } catch (IOException var12) {
         var12.printStackTrace();
      }

      return var1;
   }

   public FontSet getFontSet(String var1, UIDefaults var2) {
      return new COAFontSet(this.fontMap);
   }
}
