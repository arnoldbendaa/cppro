// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.udeflookup;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.udeflookup.UdefLookup;
import com.cedar.cp.dto.udeflookup.UdefLookupEditorSessionCSO;
import com.cedar.cp.dto.udeflookup.UdefLookupEditorSessionSSO;
import com.cedar.cp.dto.udeflookup.UdefLookupPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface UdefLookupEditorSessionLocal extends EJBLocalObject {

   UdefLookupEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   UdefLookupEditorSessionSSO getNewItemData(int var1) throws EJBException;

   UdefLookupPK insert(UdefLookupEditorSessionCSO var1) throws ValidationException, EJBException;

   UdefLookupPK copy(UdefLookupEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(UdefLookupEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;

   void saveTableData(UdefLookupEditorSessionCSO var1) throws ValidationException, EJBException;

   void issueRebuild(int var1) throws ValidationException, EJBException;

   int[] issueRebuild(int var1, String var2, UdefLookupPK var3, int var4) throws ValidationException, EJBException;

   EntityList getUdefForms(Object var1) throws ValidationException, EJBException;

   UdefLookup getUdefLookup(String var1) throws ValidationException, EJBException;
}
