// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt.wizard;

import com.cedar.awt.wizard.WizardStep;
import java.util.EventListener;

public interface WizardModelListener extends EventListener {

   void stepUpdated(WizardStep var1);
}
