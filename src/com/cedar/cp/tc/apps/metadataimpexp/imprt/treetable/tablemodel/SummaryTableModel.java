// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.treetable.tablemodel;

import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class SummaryTableModel extends DefaultTableModel {

   public static final String COLIDENTIFIER_VISID = "VisId";
   public static final String COLIDENTIFIER_DESC = "Description";
   public static final String COLIDENTIFIER_INTENT = "Intent";
   public static final String COLIDENTIFIER_RESULT = "Error detail";


   public SummaryTableModel(List<CommonImpExpItem> selectedItem, int type) {
      Vector columnIdentifiers = new Vector();
      columnIdentifiers.add("VisId");
      columnIdentifiers.add("Description");
      columnIdentifiers.add("Intent");
      columnIdentifiers.add("Error detail");
      Vector dataVector = new Vector();
      if(selectedItem != null) {
         for(Iterator i$ = selectedItem.iterator(); i$.hasNext(); this.setDataVector(dataVector, columnIdentifiers)) {
            CommonImpExpItem commonImpExpItem = (CommonImpExpItem)i$.next();
            if(commonImpExpItem.getTreeNodeType() == type && !commonImpExpItem.isIgnore()) {
               Vector row = new Vector();
               if(commonImpExpItem.hasAlternativeName()) {
                  row.add(commonImpExpItem.getAlternativeName());
               } else {
                  row.add(commonImpExpItem.getItemName());
               }

               row.add(commonImpExpItem.getDescription());
               if(commonImpExpItem.isOverwrite()) {
                  row.add("Overwrite");
               } else if(commonImpExpItem.hasAlternativeName()) {
                  row.add("Alter");
               } else {
                  row.add("Create new");
               }

               if(commonImpExpItem.getErrorMsg() != null) {
                  row.add(commonImpExpItem.getErrorMsg());
               }

               dataVector.add(row);
            }
         }
      }

   }
}
