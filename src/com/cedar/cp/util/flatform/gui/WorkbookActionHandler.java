// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import java.awt.Color;
import java.awt.Component;

public interface WorkbookActionHandler {

   void toggleFormatBoldFont();

   void toggleFormatItalicFont();

   void toggleFormatUnderlineFont();

   void setFormatBackground(Color var1);

   void setFormatForeground(Color var1);

   void mergeSelectedCells();

   void splitSelectedCells();

   void setBoxBorder();

   void toggleGrid();

   void setViewLayer(int var1);

   void setHorizontalAlignment(int var1);

   void setFormatFont(String var1, Integer var2);

   Component getParentComponent();

   void testMode();

   void addChart();

   void addImage();

   void editTestParams();

   void editWorksheetProperties();
}
