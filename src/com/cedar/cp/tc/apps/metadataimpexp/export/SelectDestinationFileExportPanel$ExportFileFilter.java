// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export;

import com.cedar.cp.tc.apps.metadataimpexp.export.SelectDestinationFileExportPanel;
import java.io.File;
import javax.swing.filechooser.FileFilter;

class SelectDestinationFileExportPanel$ExportFileFilter extends FileFilter {

   // $FF: synthetic field
   final SelectDestinationFileExportPanel this$0;


   public SelectDestinationFileExportPanel$ExportFileFilter(SelectDestinationFileExportPanel var1) {
      this.this$0 = var1;
   }

   public boolean accept(File f) {
      return f != null && (f.isDirectory() || f.getName().endsWith(".zip"));
   }

   public String getDescription() {
      return "Zip file(*.zip)";
   }
}
