// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 10:25:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.lnf.skin;

import com.coa.lnf.skin.CustomizableCOASkin;
import java.net.URL;
import org.jvnet.substance.SubstanceLookAndFeel;

public class COALookAndFeel extends SubstanceLookAndFeel {

   private static final long serialVersionUID = -8939546161405671744L;


   public COALookAndFeel(String var1) {
      super(new CustomizableCOASkin(var1));
   }

   public COALookAndFeel(URL var1) {
      super(new CustomizableCOASkin(var1));
   }

   public COALookAndFeel() {
      super(new CustomizableCOASkin((String)null));
   }
}
