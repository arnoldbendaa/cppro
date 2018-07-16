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

public class XMLFormListTableModel extends DefaultTableModel {

   public XMLFormListTableModel(List<CommonImpExpItem> selectedItem) {
      this.columnIdentifiers = new Vector();
      this.columnIdentifiers.add("VisId");
      this.columnIdentifiers.add("Desription");
      Vector dataVector = new Vector();
      if(selectedItem != null) {
         Iterator i$ = selectedItem.iterator();

         while(i$.hasNext()) {
            CommonImpExpItem selectedForm = (CommonImpExpItem)i$.next();
            if(selectedForm.getTreeNodeType() == 2 && !selectedForm.isIgnore()) {
               Vector row = new Vector();
               if(selectedForm.hasAlternativeName()) {
                  row.add(selectedForm.getAlternativeName());
               } else {
                  row.add(selectedForm.getItemName());
               }

               row.add(selectedForm.getDescription());
               dataVector.add(row);
            }
         }
      }

      this.setDataVector(dataVector, this.columnIdentifiers);
   }
}
