// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.udeflookup;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.udeflookup.UdefLookup;
import java.sql.Timestamp;
import java.util.List;

public interface UdefLookupEditor extends BusinessEditor {

   void setAutoSubmit(boolean var1) throws ValidationException;

   void setScenario(boolean var1) throws ValidationException;

   void setTimeLvl(int var1) throws ValidationException;

   void setYearStartMonth(int var1) throws ValidationException;

   void setTimeRange(boolean var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setGenTableName(String var1) throws ValidationException;

   void setLastSubmit(Timestamp var1) throws ValidationException;

   void setDataUpdated(Timestamp var1) throws ValidationException;

   UdefLookup getUdefLookup();

   void addColumnDef();

   void removeColumnDef(int[] var1);

   void addDataRow();

   void setTableData(List var1);

   void removeDataRow(int[] var1);

   void dataChanged();
}
