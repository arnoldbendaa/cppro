// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.ErrorManager;
import com.cedar.awt.ErrorNotifiable;

public interface ErrorSettable extends ErrorNotifiable {

   String id = "@(#) $Archive: /fc/ui/base/ErrorSettable.java $ $Revision: 1.3 $";


   ErrorManager getErrorManager();
}
