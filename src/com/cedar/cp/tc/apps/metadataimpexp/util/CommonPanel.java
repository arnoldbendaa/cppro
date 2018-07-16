// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util;

import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
//import com.cedar.cp.tc.ctrl.QControl;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JPanel;

public abstract class CommonPanel extends JPanel {

//   protected ArrayList<QControl> qControlList = new ArrayList();
   private int number;


   public int getNumber() {
      return this.number;
   }

   public void setNumber(int number) {
      this.number = number;
   }

//   public void addQControl(QControl control) {
//      this.qControlList.add(control);
//   }

   public abstract void init();

   public abstract void setData(Collection var1);

   public abstract void setData(CommonImpExpItem[] var1);

   public abstract String getName();
}
