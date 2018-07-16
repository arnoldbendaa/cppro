// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.pack;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.pack.ReportPackOption;
import com.cedar.cp.api.report.pack.ReportPackProjection;
import com.cedar.cp.dto.report.pack.ReportPackEditorSessionCSO;
import com.cedar.cp.dto.report.pack.ReportPackEditorSessionSSO;
import com.cedar.cp.dto.report.pack.ReportPackPK;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface ReportPackEditorSessionRemote extends EJBObject {

   ReportPackEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, RemoteException;

   ReportPackEditorSessionSSO getNewItemData(int var1) throws RemoteException;

   ReportPackPK insert(ReportPackEditorSessionCSO var1) throws ValidationException, RemoteException;

   ReportPackPK copy(ReportPackEditorSessionCSO var1) throws ValidationException, RemoteException;

   void update(ReportPackEditorSessionCSO var1) throws ValidationException, RemoteException;

   void delete(int var1, Object var2) throws ValidationException, RemoteException;

   int issueReport(int var1, EntityRef var2, ReportPackOption var3, int var4) throws RemoteException;

   List issueReportLine(int var1, EntityRef var2, EntityRef var3, ReportPackOption var4, boolean var5, int var6) throws RemoteException;

   ReportPackProjection getReportPackProjection(int var1, Object var2) throws RemoteException;
}
