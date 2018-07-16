// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import com.cedar.cp.api.base.CPFileWrapper;
import com.cedar.cp.util.FileUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;

public class CPClientFileWrapper implements Serializable {

   protected byte[] mData;
   protected String mName;
   protected long mSize;
   protected String mMimeType;
   protected String mPrefix;
   protected String mExt;


   public CPClientFileWrapper(File f) {
      try {
         this.setName(f.getName());
         this.setData(this.getFileAsBytes(f));
         this.setSize(f.length());
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   public CPClientFileWrapper(byte[] data, String name) {
      this.setData(data);
      this.setName(name);
      this.setSize((long)data.length);
   }

   public byte[] getData() {
      return this.mData;
   }

   public void setData(byte[] data) {
      this.mData = data;
   }

   public String getName() {
      return this.mName;
   }

   public String getEscapedName() {
      return FileUtils.getEscapedName(this.mName);
   }

   public void setName(String name) {
      this.mName = name;
   }

   public long getSize() {
      return this.mSize;
   }

   public void setSize(long size) {
      this.mSize = size;
   }

   public String getMimeType() {
      return this.mMimeType;
   }

   public String getMimeType(HttpServletRequest theRequest) {
      if(this.mMimeType != null && this.mMimeType.length() > 0) {
         return this.mMimeType;
      } else {
         String fileName = this.getName();
         return theRequest.getSession().getServletContext().getMimeType(fileName.toLowerCase());
      }
   }

   public void setMimeType(String mimeType) {
      this.mMimeType = mimeType;
   }

   public String getPrefix() {
      return this.mPrefix != null && this.mPrefix.length() >= 3?this.mPrefix:"cpFile";
   }

   public void setPrefix(String prefix) {
      this.mPrefix = prefix;
   }

   public String getExt() {
      if(this.mExt == null) {
         if(this.mName == null) {
            return "";
         }

         if(this.mName.length() > this.mName.lastIndexOf(".")) {
            return this.mName.substring(this.mName.lastIndexOf("."), this.mName.length());
         }
      }

      return this.mExt;
   }

   public void setExt(String ext) {
      this.mExt = ext;
   }

   public byte[] getFileAsBytes(File f) throws IOException {
      FileInputStream fis = new FileInputStream(f);
      BufferedInputStream bis = new BufferedInputStream(fis);
      byte[] result = this.getFileAsBytes((InputStream)bis);
      fis.close();
      return result;
   }

   public byte[] getFileAsBytes(InputStream in) throws IOException {
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

   public File createTempFromByte() throws IOException {
      File f;
      if(this.mName.lastIndexOf(".") > 0) {
         String preffix = this.getPrefix();
         String suffix = this.getExt();
         f = File.createTempFile(preffix, suffix);
      } else {
         f = new File(this.getName());
      }

      FileOutputStream fos = new FileOutputStream(f);
      fos.write(this.getData());
      fos.flush();
      fos.close();
      if(f != null) {
         f.deleteOnExit();
      }

      return f;
   }

   public File writeToDisk(File parentDir) throws Exception {
      File newFile;
      if(parentDir == null) {
         newFile = File.createTempFile(this.getPrefix(), this.getExt());
      } else {
         newFile = new File(parentDir.getAbsolutePath() + File.separator + FileUtils.getEscapedName(this.getName()));
      }

      FileOutputStream fos = new FileOutputStream(newFile);
      fos.write(this.getData());
      fos.flush();
      fos.close();
      return newFile;
   }

   public boolean equals(Object o) {
      if(this == o) {
         return true;
      } else if(!(o instanceof CPFileWrapper)) {
         return false;
      } else {
         CPFileWrapper that = (CPFileWrapper)o;
         boolean var10000;
         if(Arrays.equals(this.mData, that.mData)) {
            label36: {
               if(this.mName != null) {
                  if(!this.mName.equals(that.mName)) {
                     break label36;
                  }
               } else if(that.mName != null) {
                  break label36;
               }

               var10000 = true;
               return var10000;
            }
         }

         var10000 = false;
         return var10000;
      }
   }

   public int hashCode() {
      int result = this.mData != null?Arrays.hashCode(this.mData):0;
      result = 31 * result + (this.mName != null?this.mName.hashCode():0);
      return result;
   }
}
