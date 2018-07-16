// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dataEntry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface FinanceSystemCellData extends Serializable {

   int COLUMN_TYPE_URL = -246808642;
   int COLUMN_TYPE_TABLE = -246808641;


   String getDimSelectionSummary();

   String getOtherSelectionSummary();

   BigDecimal getTotal();

   String getTotalName();

   String getValidationMessage();

   int getRowCount();

   int getColumnCount();

   int getColumnGroupCount();

   Object getValueAt(int var1, int var2);

   String getColumnName(int var1);

   Integer getColumnType(int var1);

   int getColumnMaxScale(int var1);

   Integer[] getColumnGroupRange(int var1);

   String getColumnGroupName(int var1);

   public abstract String getFmsTransferUrl();
   
   List<String> getColumnNames();
   
   List<List<Object>> getRows();
}
