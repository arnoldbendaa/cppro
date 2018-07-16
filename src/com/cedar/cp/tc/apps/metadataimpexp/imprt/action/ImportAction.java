// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.action;

import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpApplicationState;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;

public abstract class ImportAction {

   public static final int IMPORT_SUCCESS = 1;
   public static final int IMPORT_FAILED = -1;
   public static final int IMPORT_NOTHING = 0;
   protected CommonImpExpItem importObj = null;
   protected MetaDataImpExpApplicationState applicationState = MetaDataImpExpApplicationState.getInstance();


   public ImportAction(CommonImpExpItem importObj) {
      this.importObj = importObj;
   }

   public abstract int doImport();
}
