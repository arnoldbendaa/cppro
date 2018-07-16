// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extsys.TransferMonitor;
import com.cedar.cp.api.model.FinanceCubeEditorSession;
import java.net.URL;
import java.util.List;

public interface FinanceCubesProcess extends BusinessProcess {

   EntityList getAllFinanceCubes();
   
   EntityList getAllFinanceCubesForLoggedUser();

   EntityList getAllSimpleFinanceCubes();

   EntityList getAllDataTypesAttachedToFinanceCubeForModel(int var1);

   EntityList getFinanceCubesForModel(int var1);

   EntityList getFinanceCubeDimensionsAndHierachies(int var1);

   EntityList getFinanceCubeAllDimensionsAndHierachies(int var1);

   EntityList getAllFinanceCubesWeb();

   EntityList getAllFinanceCubesWebForModel(int var1);

   EntityList getAllFinanceCubesWebForUser(int var1);

   EntityList getFinanceCubeDetails(int var1);

   EntityList getFinanceCubesUsingDataType(short var1);

   FinanceCubeEditorSession getFinanceCubeEditorSession(Object var1) throws ValidationException;

   FinanceCubeEditorSession getFinanceCubeEditorSession(int var1, int var2) throws ValidationException;

   int issueRebuildTask(List<EntityRef> var1) throws ValidationException;

   int issueCheckIntegrityTask(List<EntityRef> var1) throws ValidationException;

   int getFinanceCubeId(Object var1);

   int issueCellCalcImportTask(URL var1, TransferMonitor var2) throws ValidationException;

   int issueDynamicCellCalcImportTask(URL var1, TransferMonitor var2) throws ValidationException;
}
