// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.virement.VirementLine;
import com.cedar.cp.api.user.UserRef;
import java.io.Serializable;
import java.util.Set;

public interface VirementAuthPoint extends Serializable {

   int NOT_AUTHORISED = 0;
   int AUTHORISED = 1;
   int REJECTED = 2;


   Object getKey();

   String getKeyAsText();

   Set getLines();

   VirementLine getLine(StructureElementRef var1);

   Set getAvailableAuthorisers();

   UserRef getAuthUser();

   String getNotes();

   int getStatus();

   StructureElementRef getStructureElementRef();

   boolean isCanUserAuth();
}
