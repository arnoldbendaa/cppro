// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubeEditorSessionCSO;
import com.cedar.cp.dto.model.FinanceCubeEditorSessionSSO;
import com.cedar.cp.util.Pair;
import java.net.URL;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface FinanceCubeEditorSessionLocal extends EJBLocalObject {

   FinanceCubeEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   FinanceCubeEditorSessionSSO getNewItemData(int var1) throws EJBException;

   FinanceCubeCK insert(FinanceCubeEditorSessionCSO var1) throws ValidationException, EJBException;

   EntityList getOwnershipData(int var1, Object var2) throws EJBException;

   FinanceCubeCK copy(FinanceCubeEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(FinanceCubeEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   int issueCubeRebuildtask(int var1, List<EntityRef> var2) throws ValidationException, EJBException;

   int issueCheckIntegrityTask(int var1, List<EntityRef> var2) throws ValidationException, EJBException;

   URL initiateTransfer(byte[] var1) throws ValidationException, EJBException;

   void appendToFile(URL var1, byte[] var2) throws ValidationException, EJBException;

   int issueCellCalcImportTask(int var1, String var2, String var3, int var4) throws ValidationException, EJBException;

   int issueDynamicCellCalcImportTask(int var1, List<Pair<String, String>> var2, int var3) throws ValidationException, EJBException;
}
