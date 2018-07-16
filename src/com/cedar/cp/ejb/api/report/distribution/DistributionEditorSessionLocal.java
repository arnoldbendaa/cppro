// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.report.distribution;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.distribution.DistributionDetails;
import com.cedar.cp.dto.report.distribution.DistributionEditorSessionCSO;
import com.cedar.cp.dto.report.distribution.DistributionEditorSessionSSO;
import com.cedar.cp.dto.report.distribution.DistributionPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface DistributionEditorSessionLocal extends EJBLocalObject {

   DistributionEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   DistributionEditorSessionSSO getNewItemData(int var1) throws EJBException;

   DistributionPK insert(DistributionEditorSessionCSO var1) throws ValidationException, EJBException;

   DistributionPK copy(DistributionEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(DistributionEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   DistributionDetails getDistributionDetailList(int var1, int var2, EntityRef var3) throws ValidationException, EJBException;
}
