// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.lookuptable.ImpExpCell;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.lookuptable.ImpExpColumn;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.lookuptable.ImpExpLookupTable;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.lookuptable.ImpExpLookupTables;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.lookuptable.ImpExpRow;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

public class LookupTableConfigRuleSet extends RuleSetBase {

   public void addRuleInstances(Digester digester) {
      digester.addObjectCreate("lookupTables", ImpExpLookupTables.class);
      digester.addObjectCreate("lookupTables/lookupTable", ImpExpLookupTable.class);
      digester.addSetProperties("lookupTables/lookupTable");
      digester.addObjectCreate("lookupTables/lookupTable/columns/column", ImpExpColumn.class);
      digester.addSetProperties("lookupTables/lookupTable/columns/column");
      digester.addSetNext("lookupTables/lookupTable/columns/column", "addColumn");
      digester.addObjectCreate("lookupTables/lookupTable/rows/row", ImpExpRow.class);
      digester.addObjectCreate("lookupTables/lookupTable/rows/row/cell", ImpExpCell.class);
      digester.addSetProperties("lookupTables/lookupTable/rows/row/cell");
      digester.addSetNext("lookupTables/lookupTable/rows/row/cell", "addCell");
      digester.addSetNext("lookupTables/lookupTable/rows/row", "addRow");
      digester.addSetNext("lookupTables/lookupTable", "addLookupTable");
   }
}
