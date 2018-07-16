// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.model.cc.RuntimeCcDeploymentTarget;
import com.cedar.cp.util.xmlform.inputs.FormInputModel;
import java.io.Serializable;
import java.util.List;

public interface OnDemandFinanceFormData extends Serializable {

   FormInputModel getFormInputModel();

   List<RuntimeCcDeploymentTarget>[] getCellCalculationDeployments();

   int getSheetProtectionLevel();

   boolean isNoCellCalcDeploymentsAllowed();

   boolean isForSecondaryStructure();

   List<String> getPerformanceStats();

   int[] getPositions();
}
