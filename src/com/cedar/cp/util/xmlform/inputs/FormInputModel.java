// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.inputs;

import java.io.Serializable;

public interface FormInputModel extends Serializable {

   int VISID_INDEX = 0;
   int LABEL_INDEX = 1;
   int ISSUBMITTED_INDEX = 2;
   int DEPTH_INDEX = 3;
   int ISLEAF_INDEX = 4;
   int ISCREDIT_INDEX = 5;
   int DISABLED_INDEX = 6;
   int NOT_PLANNABLE_INDEX = 7;
   int SECURITY_STATE_INDEX = 8;
   int ID_INDEX = 9;
   int POS_INDEX = 10;
   int AXIS_DIM_INDEX = 11;
   int COLUMNS_START_INDEX = 12;


   int getRowCount();

   int getColumnCount();

   Object getValueAt(int var1, int var2);

   void setValueAt(Object var1, int var2, int var3);

   int getSheetProtectionLevel();
}
