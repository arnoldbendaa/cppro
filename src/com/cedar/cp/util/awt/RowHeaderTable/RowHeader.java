// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import com.cedar.cp.util.awt.RowHeaderTable.RowHeaderBorder;
import com.cedar.cp.util.awt.TreeTable.TreeTable;
import java.awt.BorderLayout;
import javax.swing.JPanel;

class RowHeader extends JPanel {

   RowHeader(TreeTable rowTreeTable) {
      super(new BorderLayout());
      this.setBorder(new RowHeaderBorder());
      this.add(rowTreeTable, "Center");
   }
}
