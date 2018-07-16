// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.action;

import com.cedar.cp.api.udeflookup.UdefLookup;
import com.cedar.cp.api.udeflookup.UdefLookupEditor;
import com.cedar.cp.api.udeflookup.UdefLookupEditorSession;
import com.cedar.cp.api.udeflookup.UdefLookupsProcess;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.action.ImportAction;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
//import com.cedar.cp.tc.apps.xmlform.udlookup.impexp.POIImport;
import com.cedar.cp.util.Log;
import de.schlichtherle.truezip.file.TFile;
import java.util.List;

public class ImportLookupTableAction extends ImportAction {

   protected UdefLookupsProcess mProcess = null;
   private transient Log mLog = new Log(this.getClass());


   public ImportLookupTableAction(CommonImpExpItem obj) {
      super(obj);
      this.mProcess = this.applicationState.getConnection().getUdefLookupsProcess();
      this.importObj = obj;
   }

   public int doImport() {
      try {
         if(this.importObj != null && this.importObj.getTreeNodeType() == 1) {
            Object e = this.applicationState.queryUdefLookupPK(this.importObj.getItemName());
            if(e == null) {
               this.importLookupTable((Object)null, false);
            } else if(e != null && this.importObj.hasAlternativeName() && this.importObj.getAlternativeName() != null) {
               this.importLookupTable((Object)null, true);
            } else if(e != null && this.importObj.isOverwrite()) {
               this.mProcess.deleteObject(e);
               this.giveTimeToFinish((UdefLookup)null, 4);
               this.importLookupTable((Object)null, false);
            }
         }

         return 1;
      } catch (Exception var2) {
         this.importObj.setErrorMsg(var2.getMessage());
         return -1;
      }
   }

   private void importLookupTable(Object key, boolean hasAlternative) throws Exception {
	   //arnold all
//      UdefLookupEditorSession session = this.mProcess.getUdefLookupEditorSession(key);
//      UdefLookupEditor editor = session.getUdefLookupEditor();
//      if(!hasAlternative) {
//         editor.setVisId(this.importObj.getItemName());
//      } else {
//         editor.setVisId(this.importObj.getAlternativeName());
//      }
//
//      editor.setDescription(this.importObj.getDescription());
//      editor.setAutoSubmit(this.importObj.isAutoSubmit());
//      TFile importFile = new TFile(this.importObj.getImportFileName());
//      POIImport importDef = new POIImport(importFile, editor);
//      importDef.getTableData();
//      editor.commit();
//      session.commit(false);
//      this.mProcess.terminateSession(session);
//      this.doImportTableData(importFile, hasAlternative);
   }

   private void doImportTableData(TFile importFile, boolean hasAlternativeName) throws Exception {
	   //arnold all
//      String visId = null;
//      if(hasAlternativeName) {
//         visId = this.importObj.getAlternativeName();
//      } else {
//         visId = this.importObj.getItemName();
//      }
//
//      Object key = this.applicationState.queryUdefLookupPK(visId);
//      UdefLookupEditorSession session = this.mProcess.getUdefLookupEditorSession(key);
//      UdefLookupEditor editor = session.getUdefLookupEditor();
//      UdefLookup udefLookup = editor.getUdefLookup();
//      this.giveTimeToFinish(udefLookup, 4);
//      POIImport importData = new POIImport(importFile, udefLookup.getColumnDef());
//      editor.setTableData(importData.getTableData());
//      session.saveTableData();
//      this.mProcess.terminateSession(session);
   }

   private void giveTimeToFinish(UdefLookup udefLookup, int retry) throws InterruptedException {
      for(; retry > 0; --retry) {
         Thread.sleep(5000L);
         if(udefLookup != null) {
            try {
               List ex = udefLookup.getColumnDef();
               if(ex != null) {
                  return;
               }
            } catch (Exception var4) {
               this.mLog.debug("UD_ table not yet created.................");
            }
         }
      }

   }
}
