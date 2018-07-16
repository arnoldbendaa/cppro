// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.SecurityAccessDefRef;
import com.cedar.cp.api.model.SecurityGroup;
import java.util.List;

public interface SecurityGroupEditor extends BusinessEditor {

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setModelRef(ModelRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   SecurityGroup getSecurityGroup();

   List getAvailableUsers();

   List getSelectedUsers();

   void addUser(EntityRef var1) throws ValidationException;

   void removeUser(EntityRef var1) throws ValidationException;

   EntityList getSecurityAccessDefs();

   void setSecurityAccessDef(SecurityAccessDefRef var1) throws ValidationException;
}
