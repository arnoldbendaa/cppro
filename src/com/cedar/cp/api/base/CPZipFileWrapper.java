// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import com.cedar.cp.api.base.CPFileWrapper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CPZipFileWrapper implements Serializable {

   private List<CPFileWrapper> mFiles;
   private String mName;


   public List<CPFileWrapper> getFiles() {
      return this.mFiles == null?Collections.EMPTY_LIST:this.mFiles;
   }

   public void setFiles(List<CPFileWrapper> files) {
      this.mFiles = files;
   }

   public void addFile(CPFileWrapper file) {
      if(this.mFiles == null) {
         this.mFiles = new ArrayList();
      }

      this.mFiles.add(file);
   }

   public String getName() {
      return this.mName;
   }

   public void setName(String name) {
      this.mName = name;
   }
}
