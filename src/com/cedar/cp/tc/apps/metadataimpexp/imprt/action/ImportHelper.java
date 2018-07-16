// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.action;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.action.ImportLookupTableAction;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.action.ImportXMLFormAction;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.XMLFormModel;
import com.cedar.cp.tc.apps.metadataimpexp.services.XMLFormService;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.util.xmlform.LookupInput;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class ImportHelper {

   private List<CommonImpExpItem> importItems = null;
   private List<CommonImpExpItem> importFailedList = new ArrayList();
   private XMLFormService formService = new XMLFormService();


   public ImportHelper(List<CommonImpExpItem> importItemsList) {
      this.importItems = importItemsList;
   }

   public List<CommonImpExpItem> performImport() {
      if(this.importItems != null && this.importItems.size() > 0) {
         ArrayList lookupTableList = new ArrayList();
         ArrayList xmlFormList = new ArrayList();
         Iterator i$ = this.importItems.iterator();

         CommonImpExpItem commonImpExpItem;
         while(i$.hasNext()) {
            commonImpExpItem = (CommonImpExpItem)i$.next();
            switch(commonImpExpItem.getTreeNodeType()) {
            case 1:
               lookupTableList.add(commonImpExpItem);
               break;
            case 2:
               xmlFormList.add(commonImpExpItem);
            }
         }

         if(lookupTableList != null && lookupTableList.size() > 0) {
            i$ = lookupTableList.iterator();

            while(i$.hasNext()) {
               commonImpExpItem = (CommonImpExpItem)i$.next();
               ImportLookupTableAction lookupInputHash = new ImportLookupTableAction(commonImpExpItem);
               int importAction = lookupInputHash.doImport();
               if(importAction == -1) {
                  this.importFailedList.add(commonImpExpItem);
               }
            }
         }

         lookupTableList.remove(this.importFailedList);
         if(xmlFormList != null && xmlFormList.size() > 0) {
            i$ = xmlFormList.iterator();

            while(i$.hasNext()) {
               commonImpExpItem = (CommonImpExpItem)i$.next();
               Hashtable lookupInputHash1 = this.formService.getLookupTableRefInForm((XMLFormModel)commonImpExpItem);
               if(lookupTableList != null && lookupTableList.size() > 0 && lookupInputHash1 != null && lookupInputHash1.size() > 0) {
                  Iterator importAction2 = lookupTableList.iterator();

                  while(importAction2.hasNext()) {
                     CommonImpExpItem result = (CommonImpExpItem)importAction2.next();
                     Object obj = lookupInputHash1.get(result.getItemName());
                     if(obj != null && result.hasAlternativeName()) {
                        LookupInput lookupObj = (LookupInput)obj;
                        lookupObj.setId(result.getAlternativeName());
                        lookupObj.setLookupName(result.getAlternativeName());
                     }
                  }
               }

               ImportXMLFormAction importAction1 = new ImportXMLFormAction(commonImpExpItem);
               int result1 = importAction1.doImport();
               if(result1 == -1) {
                  this.importFailedList.add(commonImpExpItem);
               }
            }
         }
      }

      return this.importFailedList;
   }
}
