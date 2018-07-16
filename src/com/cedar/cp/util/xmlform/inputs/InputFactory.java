// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.inputs;

import com.cedar.cp.util.performance.PerformanceDatumImpl;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.FinanceCubeInput;
import com.cedar.cp.util.xmlform.RowInput;
import com.cedar.cp.util.xmlform.inputs.FormInputModel;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface InputFactory {

   FormInputModel getFormInputModel(PerformanceDatumImpl var1, RowInput var2) throws SQLException;

   Map getLookupInputs(PerformanceDatumImpl var1, List var2) throws SQLException;

   FormInputModel getFinanceFormDataRows(int var1, FinanceCubeInput var2, int var3, String var4, int var5, int[] var6, int var7, boolean var8, CalendarInfo var9, Map var10, Map var11) throws Exception;
}
