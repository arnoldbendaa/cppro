// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import java.io.File;
import javax.swing.filechooser.FileFilter;

class ImportFileFilter extends FileFilter {

   public boolean accept(File f) {
      return f.getName().toLowerCase().endsWith(".zip") || f.isDirectory();
   }

   public String getDescription() {
      return "Zip files (*.zip)";
   }
}
