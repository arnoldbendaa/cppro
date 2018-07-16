// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcImport;

public interface CellCalcImportProcessor {

   void startBatch();

   boolean processCalcUpdate(CellCalcImport var1) throws ValidationException, Exception;

   void endBatch();
}
