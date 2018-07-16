// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PatchInstaller {

   public PatchInstaller() throws Exception {
      File ip = new File("install.props");
      if(ip.exists() && ip.isFile()) {
         File cp = new File("cp");
         if(!cp.isDirectory()) {
            System.out.println("can\'t find cp directory");
         } else if(!System.getProperty("java.version").substring(0, 3).equals("1.5")) {
            System.out.println("java version must be 1.5 - found " + System.getProperty("java.version"));
         } else {
            String jarName = this.getJarName();
            Properties props = new Properties();
            props.load(this.getClass().getResourceAsStream("patch.props"));
            String patchId = props.getProperty("patchId");
            this.unpackJar(jarName);
            this.runAnt(patchId);
         }
      } else {
         System.out.println("can\'t find install.props file");
      }
   }

   private String getJarName() {
      String classLocation = PatchInstaller.class.getName().replace('.', '/') + ".class";
      ClassLoader loader = PatchInstaller.class.getClassLoader();
      URL location;
      if(loader == null) {
         location = ClassLoader.getSystemResource(classLocation);
      } else {
         location = loader.getResource(classLocation);
      }

      String jarName = location.toString();
      jarName = jarName.substring(0, jarName.indexOf("!"));
      jarName = jarName.substring(jarName.lastIndexOf("/") + 1);
      return jarName;
   }

   public void unpackJar(String jarName) throws IOException {
      System.out.println("unpacking " + jarName);
      long startTime = System.currentTimeMillis();
      int fileCount = 0;
      JarFile jf = new JarFile(jarName);
      Enumeration enumerate = jf.entries();

      while(enumerate.hasMoreElements()) {
         JarEntry elapsedTime = (JarEntry)enumerate.nextElement();
         if(!elapsedTime.getName().startsWith("META-INF") && !elapsedTime.getName().startsWith("com/")) {
            InputStream is = jf.getInputStream(elapsedTime);
            File of = new File(elapsedTime.getName());
            if(elapsedTime.isDirectory()) {
               of.mkdir();
            } else {
               of.createNewFile();
               FileOutputStream fos = new FileOutputStream(of);
               byte[] buffer = new byte[4096];
               boolean bytesRead = false;

               int var14;
               while((var14 = is.read(buffer)) != -1) {
                  fos.write(buffer, 0, var14);
               }

               fos.close();
               is.close();
               ++fileCount;
            }
         }
      }

      jf.close();
      long var13 = (System.currentTimeMillis() - startTime) / 1000L;
      System.out.println("unpacked " + fileCount + " files in " + var13 + " seconds");
   }

   public void runAnt(String patchName) throws Exception {
      String fileSeparator = System.getProperty("file.separator");
      String patchBaseDir = System.getProperty("user.dir") + fileSeparator + patchName;
      File antLib = new File(patchBaseDir + fileSeparator + "ant" + fileSeparator + "lib");
      if(!antLib.isDirectory()) {
         throw new Exception("no ant lib?");
      } else {
         Class c = Class.forName("org.apache.tools.ant.Main");
         Method m = c.getMethod("main", new Class[]{String[].class});
         if(System.getProperty("patch.diagnostic") != null) {
            m.invoke((Object)null, new Object[]{new String[]{"-buildfile", patchBaseDir + fileSeparator + "build.xml", "-Dbasedir", patchBaseDir, "-Dant.home", patchBaseDir + fileSeparator + "ant", "-diagnostics"}});
         }

         if(System.getProperty("patch.debug") != null) {
            m.invoke((Object)null, new Object[]{new String[]{"-buildfile", patchBaseDir + fileSeparator + "build.xml", "-Dbasedir", patchBaseDir, "-Dant.home", patchBaseDir + fileSeparator + "ant", "-debug"}});
         }

         m.invoke((Object)null, new Object[]{new String[]{"-buildfile", patchBaseDir + fileSeparator + "buildnew.xml", "-Dbasedir", patchBaseDir, "-Dant.home", patchBaseDir + fileSeparator + "ant"}});
         System.out.println("deleting the patch directory...");
         File patchDir = new File(patchName);
         this.deleteFile(patchDir);
         System.out.println("finished install of patch " + patchName);
      }
   }

   public void deleteFile(File f) {
      if(f.isDirectory()) {
         File[] files = f.listFiles();
         int size = files.length;

         for(int i = 0; i < size; ++i) {
            this.deleteFile(files[i]);
         }

         f.delete();
      } else {
         f.delete();
      }

   }

   public static void main(String[] args) throws Exception {
      new PatchInstaller();
   }
}
