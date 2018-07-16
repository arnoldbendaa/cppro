// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.xmlform;

import com.cedar.cp.api.xmlform.FormDeploymentData;
import java.util.List;
import java.util.Map;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public interface FormDeploymentEditor {

   String getIdentifier();

   void setIdentifier(String var1);
   
   Integer getBudgetCycleId();

   void setBudgetCycle(Integer budgetCycleId);
   
   List getBudgetCycleModel();

   String getDescription();

   void setDescription(String var1);

   void setAutoExpandDepth(int var1);

   int getAutoExpandDepth();

   void setFill(boolean var1);

   boolean getFill();

   void setBold(boolean var1);

   boolean getBold();

   void setHorz(boolean var1);

   boolean getHorz();

   Map getBusinessElements();

   TreeModel getBusinessTreeModel();

   int queryBusinessSelectionStatus(Object var1);

   Object removeBusinessElement(Object var1);

   void addBusinessElement(Object var1, boolean var2);

   List<TreeNode> searchBusinessTree(String var1);

   List<TreeNode> querySelectedBusinessNodes();

   TreeModel[] getCellPickerModel();

   Map getSelection();

   void setSelection(Map var1);

   void setMailType(int var1);

   int getMailType();

   void setMailContent(String var1);

   String getMailContent();

   void setAttchmentName(String var1);

   String getAttchmentName();

   void setAttchment(byte[] var1);

   byte[] getAttatchment();

   FormDeploymentData getFormDeploymentData();
}
