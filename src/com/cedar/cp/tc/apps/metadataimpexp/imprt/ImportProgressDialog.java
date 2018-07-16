// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.ProgressDialog;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.action.ImportHelper;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import java.awt.Component;
import java.awt.Frame;
import java.util.List;

public class ImportProgressDialog extends ProgressDialog {

   private List<CommonImpExpItem> selectedImportItems = null;
   private Frame owner = null;
   private List<CommonImpExpItem> importFailedList = null;


   public ImportProgressDialog(Frame owner, String title, Component message, List<CommonImpExpItem> selectedImportItems) {
      super(owner, title, message);
      this.owner = owner;
      this.selectedImportItems = selectedImportItems;
   }

   public void doWork() throws Exception {
      ImportHelper importHelper = new ImportHelper(this.selectedImportItems);
      this.importFailedList = importHelper.performImport();
   }

   public List<CommonImpExpItem> getImportFailedList() {
      return this.importFailedList;
   }
}
