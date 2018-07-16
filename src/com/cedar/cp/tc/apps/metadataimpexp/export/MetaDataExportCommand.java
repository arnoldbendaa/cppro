// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export;

import com.cedar.cp.tc.apps.metadataimpexp.export.AbstractImpExpCommand;
import com.cedar.cp.tc.apps.metadataimpexp.export.ExportException;
import com.cedar.cp.tc.apps.metadataimpexp.util.FileServices;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.metadata.MetaData;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MetaDataExportCommand extends AbstractImpExpCommand {

   private MetaData mMetaData = new MetaData();
   private HashMap<String, File> mMetaDataTempFileList = new HashMap();


   public MetaDataExportCommand(MetaData metaData) {
      this.mMetaData = metaData;
   }

   public void execute() throws ExportException {
      try {
         File e = FileServices.createTemplateFile("META-DATA.xml");
         this.mMetaDataTempFileList.put("META-DATA.xml", e);
         FileServices.writeDataToFile(e, this.mMetaData.toXML());
      } catch (IOException var2) {
         throw new ExportException(var2.getMessage());
      }
   }

   public HashMap<String, File> getMetaDataTempFileList() {
      return this.mMetaDataTempFileList;
   }
}
