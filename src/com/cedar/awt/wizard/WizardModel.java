// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt.wizard;

import com.cedar.awt.wizard.WizardModelListener;
import com.cedar.awt.wizard.WizardStep;

public interface WizardModel {

   String getName();

   WizardStep getFirstStep();

   boolean isFinished();

   void addWizardModelIstener(WizardModelListener var1);

   void removeWizardModelIstener(WizardModelListener var1);
}
