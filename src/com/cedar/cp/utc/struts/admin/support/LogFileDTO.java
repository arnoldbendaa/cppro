// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.admin.support;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LogFileDTO {

   private File mFile;
   public static SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd:HH:mm:ss");


   public LogFileDTO(File file) {
      this.mFile = file;
   }

   public File getFile() {
      return this.mFile;
   }

   public void setFile(File file) {
      this.mFile = file;
   }

   public String getName() {
      return this.getFile().getName();
   }

   public String getSize() {
      return String.valueOf(this.getFile().length());
   }

   public String getDate() {
      return sdf.format(new Date(this.getFile().lastModified()));
   }

   public Map getParam() {
      HashMap param = new HashMap();
      param.put("logName", this.getFile().getAbsolutePath());
      return param;
   }

}
