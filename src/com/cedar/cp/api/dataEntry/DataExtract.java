// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dataEntry;

import com.cedar.cp.api.datatype.DataTypeRef;
import java.util.List;
import java.util.Map;

public interface DataExtract {

   List getData();

   void setData(List var1);

   int getUserId();

   void setUserId(int var1);

   String getModelVisId();

   void setModelVisId(String var1);

   String getFinanceCubeVisId();

   void setFinanceCubeVisId(String var1);

   boolean isReplaceMode();

   void setReplaceMode(boolean var1);

   int getTaskId();

   List<String> getHierarchyList();

   void setHierarchyList(List<String> var1);

   Map<String, DataTypeRef> getValidDataTypes();

   int getRowCount();

   List<String> getCalendarVisIds();

   boolean isNoteFormat();

   void setNoteFormat(boolean var1);

   void setNoteData(List var1);

   List getNoteData();
}
