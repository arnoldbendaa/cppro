// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.udwp;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.PickerSelectionStates;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.udwp.WeightingDeployment;
import com.cedar.cp.api.model.udwp.WeightingProfileRef;
import java.util.List;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public interface WeightingDeploymentEditor extends BusinessEditor, PickerSelectionStates {

   void setProfileId(int var1) throws ValidationException;

   void setAnyAccount(boolean var1) throws ValidationException;

   void setAnyBusiness(boolean var1) throws ValidationException;

   void setAnyDataType(boolean var1) throws ValidationException;

   void setWeighting(int var1) throws ValidationException;

   void setWeightingProfileRef(WeightingProfileRef var1) throws ValidationException;

   void setModelRef(ModelRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   WeightingDeployment getWeightingDeployment();

   TreeModel getAccountTreeModel();

   TreeModel getBusinessTreeModel();

   TreeModel getDataTypeTreeModel();

   int queryAccountSelectionStatus(Object var1);

   void addAccountElement(Object var1, boolean var2);

   Object removeAccountElement(Object var1);

   int queryBusinessSelectionStatus(Object var1);

   Object removeBusinessElement(Object var1);

   void addBusinessElement(Object var1, boolean var2);

   void addDataType(Object var1);

   boolean removeDataType(Object var1);

   boolean isDataTypeSelected(Object var1);

   List<TreeNode> searchAccountTree(String var1);

   List<TreeNode> searchBusinessTree(String var1);

   List<TreeNode> querySelectedBusinessNodes();

   List<TreeNode> querySelectedAccountNodes();

   List<TreeNode> querySelectedDataTypeNodes();
}
