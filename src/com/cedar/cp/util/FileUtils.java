// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileUtils {

   public static final char DOT_CHAR = '.';
   private static final String sFileURL = "file:/";


   public static String ensureExtension(String filename, String ext) {
      int flen = filename.length();
      int elen = ext.length();
      if(flen > elen + 1 && filename.charAt(flen - elen - 1) == 46 && filename.substring(flen - elen, flen).equalsIgnoreCase(ext)) {
         return filename;
      } else {
         StringBuffer sb = new StringBuffer(flen + elen + 1);
         sb.append(filename);
         sb.append('.');
         sb.append(ext);
         return sb.toString();
      }
   }

   public static void copyDirectory(File inDir, File outDir) throws IOException {
      File[] arr$ = inDir.listFiles();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         File f = arr$[i$];
         File outFile = new File(outDir, f.getName());
         if(!outFile.exists()) {
            outFile.createNewFile();
         }

         copyFile(f, outFile);
      }

   }

   public static void copyFile(File inFile, File outFile) throws IOException {
      FileInputStream fis = null;

      try {
         fis = new FileInputStream(inFile);
         copyFile((InputStream)fis, outFile);
      } finally {
         if(fis != null) {
            fis.close();
         }

      }

   }

   public static void copyFile(InputStream inFile, File outFile) throws IOException {
      File parentFile = outFile.getParentFile();
      if(!parentFile.exists()) {
         parentFile.mkdirs();
      }

      BufferedOutputStream bos = null;

      try {
         FileOutputStream fos = new FileOutputStream(outFile);
         bos = new BufferedOutputStream(fos);
         BufferedInputStream bis = new BufferedInputStream(inFile);
         byte[] buffer = new byte[1024];

         int length;
         while((length = bis.read(buffer)) >= 0) {
            if(length > 0) {
               bos.write(buffer, 0, length);
            }
         }
      } finally {
         if(bos != null) {
            bos.close();
         }

      }

   }

   public static void deleteFile(File f) {
      if(f.isDirectory()) {
         File[] files = f.listFiles();
         int size = files.length;

         for(int i = 0; i < size; ++i) {
            deleteFile(files[i]);
         }

         f.delete();
      } else {
         f.delete();
      }

   }

   public static byte[] getFileAsBytes(InputStream in) throws IOException {
      if(in == null) {
         System.err.println("input stream is null");
         return new byte[0];
      } else {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         byte[] buffer = new byte[1024];

         int length;
         while((length = in.read(buffer)) >= 0) {
            if(length > 0) {
               baos.write(buffer, 0, length);
            }
         }

         byte[] result = baos.toByteArray();
         baos.close();
         return result;
      }
   }

   public static byte[] getFileAsBytes(File f) throws IOException {
      FileInputStream fis = new FileInputStream(f);
      BufferedInputStream bis = new BufferedInputStream(fis);
      byte[] result = getFileAsBytes((InputStream)bis);
      fis.close();
      return result;
   }

   public static File createTempFromByte(String name, byte[] bytes) throws IOException {
      File f;
      if(name.lastIndexOf(".") > 0) {
         String preffix = name.substring(0, name.lastIndexOf(".") + 1);
         preffix = getEscapedName(preffix);
         preffix = preffix.replaceAll(" ", "_");
         String suffix = name.substring(name.lastIndexOf("."), name.length());
         suffix = getEscapedName(suffix, "ext");
         suffix = suffix.replaceAll(" ", "_");
         f = File.createTempFile(preffix, suffix);
      } else {
         f = new File(name);
      }

      FileOutputStream fos = new FileOutputStream(f);
      fos.write(bytes);
      fos.flush();
      fos.close();
      if(f != null) {
         f.deleteOnExit();
      }

      return f;
   }

   public static String getExtension(String fileName) {
      int index = fileName.indexOf(46);
      return index != -1?fileName.substring(index + 1):"";
   }

   public static File toFile(URL url) {
      return new File(url.getPath());
   }

   public static boolean isFileURL(String name) {
      return name.startsWith("file:/");
   }

   public static String extractFileNameFromFileURL(String exportTarget) {
      return exportTarget.indexOf("file:/") != -1?exportTarget.substring("file:/".length()):exportTarget;
   }

   public static String getEscapedName(String name) {
      return getEscapedName(name, "CP_File");
   }

   public static String getEscapedName(String name, String defaultFileName) {
      if(name != null) {
         String s = name.replaceAll("\\:", "_");
         s = s.replaceAll("\\\\", "_");
         s = s.replaceAll("\\/", "_");
         s = s.replaceAll("\\*", "_");
         s = s.replaceAll("\\<", "_");
         s = s.replaceAll("\\>", "_");
         s = s.replaceAll(" ", "_");
         return s;
      } else {
         return defaultFileName;
      }
   }
}
