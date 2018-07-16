// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.base.impexp;

import com.cedar.cp.api.base.ValidationException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImpExpUtils {

   public static boolean isSpreadsheet(String sourceURL) throws ValidationException {
      try {
         String io = extractLastPathName(new URL(sourceURL));
         if(io == null) {
            return false;
         } else {
            int periodIdx = io.indexOf(46);
            if(periodIdx != -1) {
               String ext = io.substring(periodIdx + 1);
               return ext.equalsIgnoreCase("xls");
            } else {
               return false;
            }
         }
      } catch (MalformedURLException var4) {
         throw new ValidationException("Import source is an invalid URL:" + sourceURL);
      } catch (IOException var5) {
         throw new IllegalStateException("Failed to get canoncial path for import source url:" + sourceURL);
      }
   }

   public static String extractLastPathName(URL url) {
      String file = url.getFile();
      file = file.replace("\\", "/");
      String query = url.getQuery();
      if(query != null) {
         file = file.substring(0, file.indexOf(query) - 1);
      }

      int idx = file.lastIndexOf(47);
      if(idx == file.length() - 1) {
         file = file.substring(0, file.length() - 1);
         idx = file.lastIndexOf(47);
      }

      if(idx != -1) {
         file = file.substring(idx + 1);
      }

      return file;
   }

   public static String extractDirectory(URL url) {
      String dir = url.getFile();
      dir = dir.replace("\\", "/");
      String query = url.getQuery();
      if(query != null) {
         dir = dir.substring(0, dir.indexOf(query) - 1);
      }

      File f = new File(dir);
      if(f.exists() && f.isDirectory()) {
         return dir;
      } else {
         if(f.exists() && f.isFile()) {
            String fileName = extractLastPathName(url);
            if(fileName != null && fileName.length() > 0) {
               dir = dir.substring(0, dir.indexOf(fileName) - 1);
            }

            f = new File(dir);
            if(f.exists() && f.isDirectory()) {
               return dir;
            }
         }

         return null;
      }
   }
}
