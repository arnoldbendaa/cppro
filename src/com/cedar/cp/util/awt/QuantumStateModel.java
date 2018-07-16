// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import javax.swing.ButtonModel;

public interface QuantumStateModel extends ButtonModel {

   int sOFF = 0;
   int sON = 1;
   int sBOTH = 2;


   int getQuantumState();

   void setQuantumState(int var1);

   boolean isOn();

   boolean isBoth();

   boolean isOff();

   boolean isAllowIntermediateState();

   void setAllowIntermediateState(boolean var1);
}
