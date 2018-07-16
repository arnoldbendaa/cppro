// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.datatype;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeEditorSession;
import com.cedar.cp.api.model.FinanceCubeRef;
import javax.swing.tree.TreeModel;

public interface DataTypesProcess extends BusinessProcess {

   EntityList getAllDataTypes();

   EntityList getAllDataTypesWeb();

   EntityList getAllDataTypeForFinanceCube(int var1);

   EntityList getAllDataTypesForModel(int var1);

   EntityList getDataTypesByType(int var1);

   EntityList getDataTypesByTypeWriteable(int var1);

   EntityList getDataTypeDependencies(short var1);

   EntityList getDataTypesForImpExp();

   EntityList getDataTypeDetailsForVisID(String var1);

   DataTypeEditorSession getDataTypeEditorSession(Object var1) throws ValidationException;

   TreeModel getSelectionTree(Integer var1);

   TreeModel getSelectionTreeFromRef(FinanceCubeRef var1);

   TreeModel getSelectionTree(Integer var1, int[] var2, boolean var3);

   TreeModel getSelectionTreeFromRef(FinanceCubeRef var1, int[] var2, boolean var3);
}
