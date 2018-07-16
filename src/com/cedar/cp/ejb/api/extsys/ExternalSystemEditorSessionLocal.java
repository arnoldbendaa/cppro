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
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface ExternalSystemEditorSessionLocal extends EJBLocalObject {

   ExternalSystemEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   ExternalSystemEditorSessionSSO getNewItemData(int var1) throws EJBException;

   ExternalSystemPK insert(ExternalSystemEditorSessionCSO var1) throws ValidationException, EJBException;

   ExternalSystemPK copy(ExternalSystemEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(ExternalSystemEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   EntityList getCompanies(int var1, int var2) throws EJBException;

   EntityList getFinanceLedgers(int var1, int var2, String var3) throws EJBException;

   EntityList getFinanceCalendarYears(int var1, int var2, String var3) throws EJBException;
   
   EntityList getGlobalFinanceCalendarYears(int var1, int var2, List<String> var3) throws EJBException;

   EntityList getFinancePeriods(int var1, int var2, String var3, int var4) throws EJBException;

   EntityList getFinanceDimensions(int var1, int var2, String var3, String var4) throws EJBException;
   
   EntityList getGlobalFinanceDimensions(int var1, int var2, List<String> var3, String var4) throws EJBException;

   EntityList getFinanceValueTypes(int var1, int var2, String var3, String var4, String var5, int var6, int var7) throws EJBException;
   
   EntityList getGlobalFinanceValueTypes(int var1, int var2, List<String> var3, String var4, String var5, int var6, int var7) throws EJBException;
   
   EntityList getFinanceHierarchies(int var1, int var2, String var3, String var4, String var5, String var6) throws EJBException;
   
   EntityList getGlobalFinanceHierarchies(int var1, int var2, List<String> var3, String var4, String var5, String var6) throws EJBException;
   
   EntityList getFinanceDimElementGroups(int var1, int var2, String var3, String var4, String var5, String var6, int var7, String var8, String var9) throws EJBException;

   EntityList getGlobalFinanceDimElementGroups(int var1, int var2, List<String> companies, String var4, String var5, String var6, int var7, String var8, String var9) throws EJBException;

   EntityList getFinanceHierarchyElems(int var1, int var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9) throws EJBException;

   EntityList getGlobalFinanceHierarchyElems(int var1, int var2, List<String> companies, String var4, String var5, String var6, String var7, String var8, String var9) throws EJBException;
   
   String getSuggestedModelVisId(int var1, int var2, String var3) throws EJBException;

   int issueExternalSystemImportTask(int var1, ExternalSystemRef var2, String var3, int var4) throws ValidationException, EJBException;

   URL initiateTransfer(URL var1, ExternalSystemRef var2, byte[] var3) throws ValidationException, EJBException;

   void appendToFile(URL var1, byte[] var2) throws ValidationException, EJBException;

   List<RemoteFileSystemResource> queryRemoteFileSystem(String var1) throws ValidationException, EJBException;

   EntityList queryDataForPushSubmission() throws EJBException;

   EntityList queryAllXactSubsystems(Object var1) throws EJBException;

   EntityList queryAllXactAvailableColumns(int var1) throws EJBException;

   EntityList queryXactColumnSelection(int var1) throws EJBException;

   int issueExtSysE5DB2PushTask(int var1, List<FinanceCubeRef> var2) throws ValidationException, EJBException;
}
