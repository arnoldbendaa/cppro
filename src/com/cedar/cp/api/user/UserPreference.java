// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.user;

import com.cedar.cp.api.user.UserRef;
import java.io.Serializable;

public interface UserPreference extends Serializable {

   String USER_INTERFACE_SKIN = "USER_INTERFACE_SKIN";


   Object getPrimaryKey();

   String getPrefName();

   String getPrefValue();

   String getHelpId();

   UserRef getUserRef();

   int getPrefType();
}
