// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUpload {

   private String mFileName = "";
   private InputStream mRemoteFile;
   private File mLocalFile = null;


   public FileUpload(String destination, InputStream remoteFile) {
      this.mFileName = destination;
      this.mRemoteFile = remoteFile;
      destination = destination.substring(0, destination.lastIndexOf(File.separator));

      try {
         File e = new File(destination);
         if(!e.exists()) {
            e.mkdirs();
         }
      } catch (Exception var4) {
         System.err.println("Error creating file on server :" + var4.getMessage());
      }

   }

   public boolean doUpLoad() {
      boolean result = true;
      if(this.mRemoteFile != null) {
         BufferedOutputStream bos = null;
         this.mLocalFile = new File(this.mFileName);

         try {
            bos = new BufferedOutputStream(new FileOutputStream(this.mLocalFile));
            InputStream e = this.mRemoteFile;

            int b;
            while((b = e.read()) != -1) {
               bos.write(b);
            }

            bos.flush();
            bos.close();
            e.close();
         } catch (Exception var5) {
            var5.printStackTrace();
            result = false;
         }
      } else {
         result = false;
      }

      return result;
   }

   public File getFile() {
      return this.mLocalFile;
   }
}
