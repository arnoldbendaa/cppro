// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt.wizard;

import com.cedar.awt.wizard.WizardModel;
import com.cedar.cp.api.base.ValidationException;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public interface WizardStep {

   WizardModel getModel();

   WizardStep getNextStep();

   WizardStep getPreviousStep();

   String getTitle();

   String getHelpText();

   ImageIcon getImage();

   JComponent getComponent();

   void validate() throws ValidationException;

   void activate();

   void deActivate();

   void finished() throws ValidationException;
}
