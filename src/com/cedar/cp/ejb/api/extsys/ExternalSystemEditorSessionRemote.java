// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.extsys;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.extsys.RemoteFileSystemResource;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.dto.extsys.ExternalSystemEditorSessionCSO;
import com.cedar.cp.dto.extsys.ExternalSystemEditorSessionSSO;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface ExternalSystemEditorSessionRemote extends EJBObject {

   ExternalSystemEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   ExternalSystemEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   ExternalSystemPK insert(ExternalSystemEditorSessionCSO var1) throws ValidationException, RemoteException;

   ExternalSystemPK copy(ExternalSystemEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(ExternalSystemEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   EntityList getCompanies(int var1, int var2) throws RemoteException;

   EntityList getFinanceLedgers(int var1, int var2, String var3) throws RemoteException;

   EntityList getFinanceCalendarYears(int var1, int var2, String var3) throws RemoteException;
   
   EntityList getGlobalFinanceCalendarYears(int var1, int var2, List<String> var3) throws RemoteException;

   EntityList getFinancePeriods(int var1, int var2, String var3, int var4) throws RemoteException;

   EntityList getFinanceDimensions(int var1, int var2, String var3, String var4) throws RemoteException;
   
   EntityList getGlobalFinanceDimensions(int var1, int var2, List<String> var3, String var4) throws RemoteException;

   EntityList getFinanceValueTypes(int var1, int var2, String var3, String var4, String var5, int var6, int var7) throws RemoteException;
   
   EntityList getGlobalFinanceValueTypes(int var1, int var2, List<String> var3, String var4, String var5, int var6, int var7) throws RemoteException;

   EntityList getFinanceHierarchies(int var1, int var2, String var3, String var4, String var5, String var6) throws RemoteException;
   
   EntityList getGlobalFinanceHierarchies(int var1, int var2, List<String> var3, String var4, String var5, String var6) throws RemoteException;
   
   EntityList getFinanceDimElementGroups(int var1, int var2, String var3, String var4, String var5, String var6, int var7, String var8, String var9) throws RemoteException;

   EntityList getGlobalFinanceDimElementGroups(int var1, int var2, List<String> companies, String var4, String var5, String var6, int var7, String var8, String var9) throws RemoteException;

   EntityList getFinanceHierarchyElems(int var1, int var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9) throws RemoteException;

   EntityList getGlobalFinanceHierarchyElems(int var1, int var2, List<String> companies, String var4, String var5, String var6, String var7, String var8, String var9) throws RemoteException;
   
   String getSuggestedModelVisId(int var1, int var2, String var3) throws RemoteException;

   int issueExternalSystemImportTask(int var1, ExternalSystemRef var2, String var3, int var4) throws ValidationException, RemoteException;

   URL initiateTransfer(URL var1, ExternalSystemRef var2, byte[] var3) throws ValidationException, RemoteException;

   void appendToFile(URL var1, byte[] var2) throws ValidationException, RemoteException;

   List<RemoteFileSystemResource> queryRemoteFileSystem(String var1) throws ValidationException, RemoteException;

   EntityList queryDataForPushSubmission() throws RemoteException;

   EntityList queryAllXactSubsystems(Object var1) throws RemoteException;

   EntityList queryAllXactAvailableColumns(int var1) throws RemoteException;

   EntityList queryXactColumnSelection(int var1) throws RemoteException;

   int issueExtSysE5DB2PushTask(int var1, List<FinanceCubeRef> var2) throws ValidationException, RemoteException;
}
