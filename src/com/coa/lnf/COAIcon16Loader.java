// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 10:25:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lnf;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class COAIcon16Loader {

   private static final Map<String, Icon> sCache = new HashMap();
   private static final String sLock = "SyncLock";


   public static Icon getIcon(String var0) {
      return getIcon("/common", var0);
   }

   public static Icon getIcon(String var0, String var1) {
	   Icon var2 = (Icon)sCache.get(var1);
      if(var2 == null) {
         String var3 = "SyncLock";
         synchronized("SyncLock") {
            var2 = (Icon)sCache.get(var1);
            if(var2 == null) {
               String var4 = var0 + "/16/" + var1 + ".png";
               URL var5 = COAIcon16Loader.class.getResource(var4);
               var2 = new ImageIcon(var5, var1);
               sCache.put(var1, var2);
            }
         }
      }

      return (Icon)var2;
   }

   public static Icon getIcon24(String var0, String var1) {
	   Icon var2 = (Icon)sCache.get(var1);
      if(var2 == null) {
         String var3 = "SyncLock";
         synchronized("SyncLock") {
            var2 = (Icon)sCache.get(var1);
            if(var2 == null) {
               String var4 = var0 + "/24/" + var1 + ".png";
               URL var5 = COAIcon16Loader.class.getResource(var4);
               var2 = new ImageIcon(var5, var1);
               sCache.put(var1, var2);
            }
         }
      }

      return (Icon)var2;
   }

   public static Icon getIcon24(String var0) {
      return getIcon24("/common", var0);
   }

}
