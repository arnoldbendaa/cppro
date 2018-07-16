// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import javax.swing.table.TableModel;

public interface ListEditModel {

   TableModel getTableModel();

   boolean isAddAllowed();

   boolean isEditable(int var1);

   boolean isDeletable(int var1);
}
