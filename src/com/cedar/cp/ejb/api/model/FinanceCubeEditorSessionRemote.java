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
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface FinanceCubeEditorSessionRemote extends EJBObject {

   FinanceCubeEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   FinanceCubeEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   FinanceCubeCK insert(FinanceCubeEditorSessionCSO var1) throws ValidationException, RemoteException;

   EntityList getOwnershipData(int var1, Object var2) throws RemoteException;

   FinanceCubeCK copy(FinanceCubeEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(FinanceCubeEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   int issueCubeRebuildtask(int var1, List<EntityRef> var2) throws ValidationException, RemoteException;

   int issueCheckIntegrityTask(int var1, List<EntityRef> var2) throws ValidationException, RemoteException;

   URL initiateTransfer(byte[] var1) throws ValidationException, RemoteException;

   void appendToFile(URL var1, byte[] var2) throws ValidationException, RemoteException;

   int issueCellCalcImportTask(int var1, String var2, String var3, int var4) throws ValidationException, RemoteException;

   int issueDynamicCellCalcImportTask(int var1, List<Pair<String, String>> var2, int var3) throws ValidationException, RemoteException;
}
