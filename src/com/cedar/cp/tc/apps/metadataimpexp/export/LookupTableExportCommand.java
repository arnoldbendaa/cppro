// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.udeflookup.UdefLookup;
import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpApplicationState;
import com.cedar.cp.tc.apps.metadataimpexp.export.AbstractImpExpCommand;
import com.cedar.cp.tc.apps.metadataimpexp.export.ExportException;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.tc.apps.metadataimpexp.util.FileServices;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.lookuptable.ImpExpLookupTable;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.lookuptable.ImpExpLookupTables;
//import com.cedar.cp.tc.apps.xmlform.udlookup.impexp.POIExport;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class LookupTableExportCommand extends AbstractImpExpCommand {

   private Collection mLookupTableList = new HashSet();
   private HashSet mUnExportedLookupTableList = new HashSet();
   private HashMap<String, File> mLookupTableTempFile = new HashMap();
   private HashMap<String, File> mLookupTableExpExcelTempFileList = new HashMap();


   public LookupTableExportCommand(Collection lookupTables) {
      this.mLookupTableList.addAll(lookupTables);
   }

   public void execute() throws ExportException {
      if(!this.mLookupTableList.isEmpty()) {
         ImpExpLookupTables xmlLookupTables = new ImpExpLookupTables();
         int progressStep = 100 / this.mLookupTableList.size();
         int newValue = 0;
         boolean oldValue = false;
         Iterator lookupTableFileName = this.mLookupTableList.iterator();

         while(lookupTableFileName.hasNext()) {
            CommonImpExpItem e = (CommonImpExpItem)lookupTableFileName.next();

            try {
               ImpExpLookupTable e1 = this.buildLookupTable(e);
               if(e1 != null) {
                  xmlLookupTables.addLookupTable(e1);
                  String lookupTableFileName1 = e1.getExportExcelFile();
                  File temFile = FileServices.createTemplateFile(lookupTableFileName1);
                  this.mLookupTableExpExcelTempFileList.put(lookupTableFileName1, temFile);
//                  new POIExport(temFile, e1.getTableData(), e1.getColumnDef());//arnold
               }
            } catch (ValidationException var12) {
               this.mUnExportedLookupTableList.add(e);
               continue;
            } catch (IOException var13) {
               this.mUnExportedLookupTableList.add(e);
               continue;
            }

            int oldValue1 = newValue;
            newValue += progressStep;
            this.getPropertyChangeSupport().firePropertyChange("lookupTable", oldValue1, newValue);
         }

         String lookupTableFileName2 = "lokuptbl.xml";

         try {
            File e2 = FileServices.createTemplateFile(lookupTableFileName2);
            this.mLookupTableTempFile.put(lookupTableFileName2, e2);
            FileServices.writeDataToFile(e2, xmlLookupTables.toXML());
         } catch (IOException var11) {
            throw new ExportException(var11.getMessage());
         }
      }
   }

   private ImpExpLookupTable buildLookupTable(CommonImpExpItem lookupTableImpExpItem) throws ValidationException {
      UdefLookup udefLookup = MetaDataImpExpApplicationState.getInstance().getUdefLookup(lookupTableImpExpItem.getItemName());
      if(udefLookup == null) {
         return null;
      } else {
         ImpExpLookupTable lookupTable = new ImpExpLookupTable();
         lookupTable.setVisId(udefLookup.getVisId());
         lookupTable.setDescription(udefLookup.getDescription());
         lookupTable.setGenTableName(udefLookup.getGenTableName());
         lookupTable.setAutoSubmit(udefLookup.isAutoSubmit());
         lookupTable.setTableData(udefLookup.getTableData());
         lookupTable.setColumnDef(udefLookup.getColumnDef());
         return lookupTable;
      }
   }

   public HashMap<String, File> getLookupTableTempFile() {
      return this.mLookupTableTempFile;
   }

   public void addLookupTableList(HashSet lookupTableList) {
      this.mLookupTableList.addAll(lookupTableList);
   }

   public HashMap<String, File> getLookupTableExpExcelTempFileList() {
      return this.mLookupTableExpExcelTempFileList;
   }
}
