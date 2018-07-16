// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.flatform.gui.WorksheetPanel;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.FlatformFunctionDialog;
import com.jxcell.View;

import java.awt.Frame;

final class XcellFormFunctionDialog$1 implements Runnable {

   public void run() {
      XcellFormFunctionDialog dialog = new XcellFormFunctionDialog(new Frame(), (View)null, (Worksheet)null, 0, 0);
      dialog.showDialog();
   }
}
