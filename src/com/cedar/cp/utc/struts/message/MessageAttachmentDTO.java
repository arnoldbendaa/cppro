// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.message;

import com.cedar.cp.api.base.CPFileWrapper;
import java.io.File;
import org.apache.struts.upload.FormFile;

public class MessageAttachmentDTO {

   private FormFile mAttatch;
   private CPFileWrapper mAttatchRead;


   public FormFile getAttatch() {
      return this.mAttatch;
   }

   public CPFileWrapper getAttachAsFile() throws Exception {
      CPFileWrapper file = new CPFileWrapper(this.mAttatch.getFileData(), this.mAttatch.getFileName());
      file.setMimeType(this.mAttatch.getContentType());
      return file;
   }

   public void setAttatch(FormFile attatch) {
      this.mAttatch = attatch;
   }

   public boolean hasAttach() {
      return this.mAttatch != null?true:this.mAttatchRead != null;
   }

   public CPFileWrapper getAttatchRead() {
      return this.mAttatchRead;
   }

   public String getAttatchReadName() {
      return this.mAttatchRead.getEscapedName();
   }

   public void setAttatchRead(CPFileWrapper attatchRead) {
      this.mAttatchRead = attatchRead;
   }

   public void setAttatchRead(File attatchRead) {
      this.mAttatchRead = new CPFileWrapper(attatchRead);
   }

   public void setAttatchRead(String name, byte[] data) {
      this.mAttatchRead = new CPFileWrapper(data, name);
   }

   public void setAttatchRead(String attatchRead) {
      if(attatchRead != null && attatchRead.length() != 0) {
         this.mAttatchRead = new CPFileWrapper(new File(attatchRead));
      }
   }
}
