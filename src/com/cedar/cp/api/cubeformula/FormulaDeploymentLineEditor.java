// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.cubeformula;

import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.cubeformula.FormulaDeploymentLine;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import java.util.List;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public interface FormulaDeploymentLineEditor extends SubBusinessEditor {

   List<DataTypeRef> getAvailableDataTypes() throws ValidationException;

   void addDataType(DataTypeRef var1) throws ValidationException;

   void removeDataType(DataTypeRef var1) throws ValidationException;

   boolean setDeploymentEntry(DimensionRef var1, Object var2, Boolean var3) throws ValidationException;

   boolean setDeploymentEntry(DimensionRef var1, Object var2, Object var3, Boolean var4) throws ValidationException;

   boolean removeDeploymentEntry(DimensionRef var1, Object var2) throws ValidationException;

   int querySelectionStatus(DimensionRef var1, Object var2);

   List<TreeNode> querySelectedTreeNodes(DimensionRef var1, TreeModel var2);

   List<TreeNode> searchDimension(DimensionRef var1, TreeModel var2, String var3);

   FormulaDeploymentLine getFormulaDeploymentLine();
}
