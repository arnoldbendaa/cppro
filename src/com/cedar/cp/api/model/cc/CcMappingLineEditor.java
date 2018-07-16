// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.cc;

import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.cc.CcMappingLine;
import java.util.List;
import java.util.Map;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public interface CcMappingLineEditor extends SubBusinessEditor {

   CcMappingLine getCcMappingLine();

   List<String> getFormFields() throws ValidationException;

   Map<DimensionRef, TreeModel> getTreeModels();

   List<DataTypeRef> getDataTypes() throws ValidationException;

   DimensionRef[] getExplicitMappingDimensionRefs();

   void setDataType(DataTypeRef var1);

   void setMappingEntry(DimensionRef var1, StructureElementRef var2) throws ValidationException;

   void setFormField(String var1) throws ValidationException;

   List<TreeNode> querySelectedTreeNode(DimensionRef var1, TreeModel var2);

   List<TreeNode> searchDimension(DimensionRef var1, TreeModel var2, String var3);
}
